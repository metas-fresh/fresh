package org.adempiere.ad.table.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySuppliers;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * Provides all windows that have at least one tab pointing to a given AD_Table.
 */
@Component
public class TableRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		if (!I_AD_Table.Table_Name.equals(fromDocument.getTableName()))
		{
			return ImmutableList.of();
		}
		final AdTableId adTableId = AdTableId.ofRepoId(fromDocument.getRecord_ID());

		final AdWindowId windowsWindowId = RecordWindowFinder.findAdWindowId(I_AD_Window.Table_Name).orElse(null);
		if (windowsWindowId == null)
		{
			return ImmutableList.of();
		}

		final Set<AdWindowId> targetWindowIds = getTargetAdWindowIds(adTableId, targetWindowId);
		if (targetWindowIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final MQuery query = new MQuery(I_AD_Window.Table_Name);
		query.addRestriction(DB.buildSqlList(I_AD_Window.COLUMNNAME_AD_Window_ID, targetWindowIds, null));

		final RelatedDocumentsId id = RelatedDocumentsId.ofString("AD_Windows_by_AD_Table_ID");
		return ImmutableList.of(
				RelatedDocumentsCandidateGroup.builder()
						.candidate(
								RelatedDocumentsCandidate.builder()
										.id(id)
										.internalName(id.toJson())
										.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(windowsWindowId))
										.priority(Priority.HIGHEST)
										.querySupplier(RelatedDocumentsQuerySuppliers.ofQuery(query))
										.windowCaption(buildFilterCaption(windowsWindowId))
										.filterByFieldCaption(buildFilterByFieldCaption(adTableId))
										.documentsCountSupplier((permissions) -> targetWindowIds.size())
										.build())

						.build()
		);
	}

	private ImmutableSet<AdWindowId> getTargetAdWindowIds(final AdTableId adTableId, final @Nullable AdWindowId onlyWindowId)
	{
		final ImmutableSet<AdWindowId> eligibleWindowIds = adWindowDAO.retrieveAllAdWindowIdsByTableId(adTableId);
		if (onlyWindowId == null)
		{
			return eligibleWindowIds;
		}
		else if (eligibleWindowIds.contains(onlyWindowId))
		{
			return ImmutableSet.of(onlyWindowId);
		}
		else
		{
			return ImmutableSet.of();
		}
	}

	private ITranslatableString buildFilterCaption(final AdWindowId windowsWindowId)
	{
		return adWindowDAO.retrieveWindowName(windowsWindowId);
	}

	private ITranslatableString buildFilterByFieldCaption(@NonNull final AdTableId adTableId)
	{
		final String tableName = TableIdsCache.instance.getTableName(adTableId);

		return TranslatableStrings.builder()
				.appendADElement("TableName")
				.append(": ")
				.append(tableName)
				.build();
	}
}
