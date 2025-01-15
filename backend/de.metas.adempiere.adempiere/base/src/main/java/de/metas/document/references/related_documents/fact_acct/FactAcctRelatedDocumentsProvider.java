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

package de.metas.document.references.related_documents.fact_acct;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySuppliers;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class FactAcctRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private static final Logger logger = LogManager.getLogger(FactAcctRelatedDocumentsProvider.class);
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

	private static final String COLUMNNAME_Posted = "Posted";
	private final Priority relatedDocumentsPriority = Priority.HIGHEST;

	private final AtomicBoolean enabled = new AtomicBoolean(true);

	private static final String TABLENAME_Fact_Acct_Transactions_View = "Fact_Acct_Transactions_View";

	public FactAcctRelatedDocumentsProvider()
	{
	}

	/**
	 * @deprecated Needed only for Swing
	 */
	@Deprecated
	public void disable()
	{
		enabled.set(false);
		logger.info("Disabled FactAcctRelatedDocumentsProvider");
	}

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		// Return empty if not enabled
		if (!enabled.get())
		{
			return ImmutableList.of();
		}

		//
		// Get the Fact_Acct AD_Window_ID
		final AdWindowId factAcctWindowId = CoalesceUtil.coalesceSuppliers(
				() -> RecordWindowFinder.findAdWindowId(TABLENAME_Fact_Acct_Transactions_View).orElse(null),
				() -> RecordWindowFinder.findAdWindowId(I_Fact_Acct.Table_Name).orElse(null)
		);
		if (factAcctWindowId == null)
		{
			return ImmutableList.of();
		}

		// If not our target window ID, return nothing
		if (targetWindowId != null && !AdWindowId.equals(targetWindowId, factAcctWindowId))
		{
			return ImmutableList.of();
		}

		// Return nothing if source is not Posted
		if (fromDocument.hasField(COLUMNNAME_Posted))
		{
			final boolean posted = fromDocument.getFieldValueAsBoolean(COLUMNNAME_Posted);
			if (!posted)
			{
				return ImmutableList.of();
			}
		}

		//
		// Build query and check count if needed
		final MQuery query = new MQuery(I_Fact_Acct.Table_Name);
		query.addRestriction(I_Fact_Acct.COLUMNNAME_AD_Table_ID, Operator.EQUAL, fromDocument.getAD_Table_ID());
		query.addRestriction(I_Fact_Acct.COLUMNNAME_Record_ID, Operator.EQUAL, fromDocument.getRecord_ID());

		final ITranslatableString windowCaption = adWindowDAO.retrieveWindowName(factAcctWindowId);

		final RelatedDocumentsCountSupplier recordsCountSupplier = new FactAcctRelatedDocumentsCountSupplier(fromDocument.getAD_Table_ID(), fromDocument.getRecord_ID());

		return ImmutableList.of(
				RelatedDocumentsCandidateGroup.of(
						RelatedDocumentsCandidate.builder()
								.id(RelatedDocumentsId.ofString(I_Fact_Acct.Table_Name))
								.internalName(I_Fact_Acct.Table_Name)
								.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(factAcctWindowId))
								.priority(relatedDocumentsPriority)
								.querySupplier(RelatedDocumentsQuerySuppliers.ofQuery(query))
								.windowCaption(windowCaption)
								.documentsCountSupplier(recordsCountSupplier)
								.build()));
	}
}
