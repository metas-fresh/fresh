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

package de.metas.document.references.related_documents.generic;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySuppliers;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.util.Check;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic provider of zoom targets.
 *
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 */
@Component
@RequiredArgsConstructor
public class GenericRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;
	private final DynamicReferencesCache dynamicReferencesCache;

	private final CCache<String, ImmutableList<GenericRelatedDocumentDescriptor>> descriptorsBySourceKeyColumnName = CCache.<String, ImmutableList<GenericRelatedDocumentDescriptor>>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(1000) // NOTE this is confusing BUT in case of LRU, initialCapacity is used as maxSize
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.tableName(I_AD_Window.Table_Name)
			.additionalTableNameToResetFor(I_AD_Table.Table_Name)
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.build();

	private final GenericRelatedDocumentDescriptorsRepository genericRelatedDocumentDescriptorsRepository = new GenericRelatedDocumentDescriptorsRepository();

	private final Priority relatedDocumentsPriority = Priority.LOWEST;

	@Override
	public ImmutableList<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		final List<GenericRelatedDocumentDescriptor> descriptors = getDescriptors(fromDocument.getKeyColumnNameOrNull());
		if (descriptors.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<RelatedDocumentsCandidateGroup> result = ImmutableList.builder();
		for (final GenericRelatedDocumentDescriptor descriptor : descriptors)
		{
			final AdWindowId windowId = descriptor.getTargetWindowId();
			if (targetWindowId != null && !AdWindowId.equals(targetWindowId, windowId))
			{
				continue;
			}

			final RelatedDocumentsCandidateGroup group = createCandidatesGroup(descriptor, fromDocument);
			result.add(group);
		}

		return result.build();
	}

	private RelatedDocumentsCandidateGroup createCandidatesGroup(
			@NonNull final GenericRelatedDocumentDescriptor descriptor,
			@NonNull final IZoomSource fromDocument)
	{
		final AdWindowId windowId = descriptor.getTargetWindowId();

		final RelatedDocumentsCandidateGroup.RelatedDocumentsCandidateGroupBuilder groupBuilder = RelatedDocumentsCandidateGroup.builder();
		for (final GenericTargetColumnInfo targetColumn : descriptor.getTargetColumns())
		{
			final MQuery query = buildMQuery(targetColumn, descriptor, fromDocument);
			if (query == null)
			{
				continue;
			}

			final GenericTargetWindowInfo targetWindow = descriptor.getGenericTargetWindowInfo();

			final RelatedDocumentsCountSupplier recordsCountSupplier = new GenericRelatedDocumentsCountSupplier(
					dynamicReferencesCache,
					targetWindow,
					targetColumn,
					query,
					fromDocument.getTableName());

			groupBuilder.candidate(
					RelatedDocumentsCandidate.builder()
							.id(RelatedDocumentsId.ofString(Joiner.on("-")
									.skipNulls()
									.join("generic", windowId.getRepoId(), targetColumn.getColumnName())))
							.internalName(descriptor.getTargetWindowInternalName())
							.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowIdAndCategory(windowId, targetColumn.getColumnName()))
							.priority(relatedDocumentsPriority)
							.querySupplier(RelatedDocumentsQuerySuppliers.ofQuery(query))
							.windowCaption(descriptor.getName())
							.filterByFieldCaption(targetColumn.getCaption())
							.documentsCountSupplier(recordsCountSupplier)
							.build());
		}

		return groupBuilder.build();
	}

	private List<GenericRelatedDocumentDescriptor> getDescriptors(@Nullable final String sourceKeyColumnName)
	{
		if (sourceKeyColumnName == null)
		{
			return ImmutableList.of();
		}

		return descriptorsBySourceKeyColumnName.getOrLoad(sourceKeyColumnName, this::retrieveDescriptors);
	}

	private ImmutableList<GenericRelatedDocumentDescriptor> retrieveDescriptors(@NonNull final String sourceKeyColumnName)
	{
		final CustomizedWindowInfoMap customizedWindowInfoMap = customizedWindowInfoMapRepository.get();
		return genericRelatedDocumentDescriptorsRepository
				.getRelatedDocumentDescriptors(sourceKeyColumnName)
				.stream()
				.filter(descriptor -> customizedWindowInfoMap.isTopLevelCustomizedWindow(descriptor.getTargetWindowId()))
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	@VisibleForTesting
	static MQuery buildMQuery(
			final GenericTargetColumnInfo targetColumn,
			final GenericRelatedDocumentDescriptor descriptor,
			final IZoomSource fromDocument)
	{
		//
		// Zoom by dynamic references AD_Table_ID/Record_ID
		final String sqlWhereClause;
		if (targetColumn.isDynamic())
		{
			if (descriptor.hasSingleNonDynamicColumn())
			{
				// because this was already included in that non-dynamic reference.
				return null;
			}

			final ArrayList<String> sqlWhereClauses = new ArrayList<>();

			sqlWhereClauses.add(toSqlWhereClause(targetColumn, fromDocument));

			//
			// Exclude results matched by non-dynamic targets
			descriptor.getNonDynamicColumns().stream()
					.map(otherTargetColumn -> "NOT (" + toSqlWhereClause(otherTargetColumn, fromDocument) + ")")
					.forEach(sqlWhereClauses::add);

			sqlWhereClause = joinSqlWhereClauses("AND", sqlWhereClauses);
		}
		//
		// Zoom by standard (non-dynamic) column
		else
		{
			final ArrayList<String> sqlWhereClauses = new ArrayList<>();

			sqlWhereClauses.add(toSqlWhereClause(targetColumn, fromDocument));

			if (descriptor.isSingleNonDynamicColumn(targetColumn))
			{
				descriptor.getDynamicColumns()
						.stream()
						.map(dynamicColumn -> toSqlWhereClause(dynamicColumn, fromDocument))
						.forEach(sqlWhereClauses::add);
			}

			sqlWhereClause = joinSqlWhereClauses("OR", sqlWhereClauses);
		}

		final GenericTargetWindowInfo targetWindow = descriptor.getGenericTargetWindowInfo();

		final MQuery query = new MQuery(targetWindow.getTargetTableName());
		query.setZoomTableName(targetWindow.getTargetTableName());
		query.setZoomColumnName(targetColumn.isDynamic() ? null : targetColumn.getColumnName());
		query.setZoomValue(fromDocument.getRecord_ID());

		query.addRestriction(sqlWhereClause);

		if (!Check.isBlank(targetWindow.getTabSqlWhereClause()))
		{
			query.addRestriction("(" + targetWindow.getTabSqlWhereClause() + ")");
		}

		return query;
	}

	private static String toSqlWhereClause(@NonNull final GenericTargetColumnInfo targetColumn, @NonNull final IZoomSource fromDocument)
	{
		if (targetColumn.isDynamic())
		{
			return "AD_Table_ID=" + fromDocument.getAD_Table_ID() + " AND Record_ID=" + fromDocument.getRecord_ID();
		}
		else
		{
			final String columnSql = targetColumn.isVirtualTargetColumnName()
					? "(" + targetColumn.getVirtualColumnSql() + ")"
					: targetColumn.getColumnName();
			return columnSql + "=" + fromDocument.getRecord_ID();
		}
	}

	private static String joinSqlWhereClauses(final String join, final List<String> sqlWhereClauses)
	{
		if (sqlWhereClauses.isEmpty())
		{
			return "";
		}
		else if (sqlWhereClauses.size() == 1)
		{
			return sqlWhereClauses.get(0);
		}
		else
		{
			final StringBuilder result = new StringBuilder();
			for (final String sqlWhereClause : sqlWhereClauses)
			{
                if (result.length() > 0)
				{
					result.append(" ").append(join).append(" ");
				}
				result.append("(").append(sqlWhereClause).append(")");
			}
			return result.insert(0, "(").append(")").toString();
		}
	}

}
