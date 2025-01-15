/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.error.related_documents;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySupplier;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySuppliers;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCategory;
import de.metas.error.IssueCountersByCategory;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

@Component
public class AdIssueRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final ADReferenceService adReferenceService;

	private final Priority relatedDocumentsPriority = Priority.HIGHEST;
	private final boolean onlyNotAcknowledged = true;

	public AdIssueRelatedDocumentsProvider(@NonNull final ADReferenceService adReferenceService) {this.adReferenceService = adReferenceService;}

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		//
		// Get the Issues AD_Window_ID
		final AdWindowId issuesWindowId = RecordWindowFinder.findAdWindowId(I_AD_Issue.Table_Name).orElse(null);
		if (issuesWindowId == null)
		{
			return ImmutableList.of();
		}

		// If not our target window ID, return nothing
		if (targetWindowId != null && !AdWindowId.equals(targetWindowId, issuesWindowId))
		{
			return ImmutableList.of();
		}

		final TableRecordReference recordRef = TableRecordReference.of(fromDocument.getAD_Table_ID(), fromDocument.getRecord_ID());

		final Supplier<IssueCountersByCategory> issueCountersSupplier = getIssueCountersByCategorySupplier(recordRef);

		final RelatedDocumentsCandidateGroup.RelatedDocumentsCandidateGroupBuilder groupBuilder = RelatedDocumentsCandidateGroup.builder();
		for (final IssueCategory issueCategory : IssueCategory.values())
		{
			final RelatedDocumentsId id = RelatedDocumentsId.ofString("issues-" + issueCategory.getCode());
			final ITranslatableString issueCategoryDisplayName = adReferenceService.retrieveListNameTranslatableString(IssueCategory.AD_REFERENCE_ID, issueCategory.getCode());

			groupBuilder.candidate(
					RelatedDocumentsCandidate.builder()
							.id(id)
							.internalName(id.toJson())
							.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowIdAndCategory(issuesWindowId, issueCategory))
							.priority(relatedDocumentsPriority)
							.querySupplier(createQuerySupplier(recordRef, issueCategory))
							.windowCaption(issueCategoryDisplayName)
							.filterByFieldCaption(issueCategoryDisplayName)
							.documentsCountSupplier(new AdIssueRelatedDocumentsCountSupplier(issueCountersSupplier, issueCategory))
							.build());
		}

		return ImmutableList.of(groupBuilder.build());
	}

	private Supplier<IssueCountersByCategory> getIssueCountersByCategorySupplier(@NonNull final TableRecordReference recordRef)
	{
		return Suppliers.memoize(() -> errorManager.getIssueCountersByCategory(recordRef, onlyNotAcknowledged));
	}

	private RelatedDocumentsQuerySupplier createQuerySupplier(@NonNull final TableRecordReference recordRef, @NonNull final IssueCategory issueCategory)
	{
		final MQuery query = new MQuery(I_AD_Issue.Table_Name);
		query.addRestriction(I_AD_Issue.COLUMNNAME_AD_Table_ID, Operator.EQUAL, recordRef.getAD_Table_ID());
		query.addRestriction(I_AD_Issue.COLUMNNAME_Record_ID, Operator.EQUAL, recordRef.getRecord_ID());
		query.addRestriction(I_AD_Issue.COLUMNNAME_IssueCategory, Operator.EQUAL, issueCategory.getCode());
		if (onlyNotAcknowledged)
		{
			query.addRestriction(I_AD_Issue.COLUMNNAME_Processed, Operator.EQUAL, false);
		}

		return RelatedDocumentsQuerySuppliers.ofQuery(query);
	}
}
