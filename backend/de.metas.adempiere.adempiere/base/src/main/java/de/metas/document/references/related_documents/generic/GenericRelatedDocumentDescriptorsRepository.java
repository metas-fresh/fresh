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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.cache.CCache;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class GenericRelatedDocumentDescriptorsRepository
{
	private static final Logger logger = LogManager.getLogger(GenericRelatedDocumentDescriptorsRepository.class);

	private final CCache<Integer, DynamicTargetsMap> dynamicTargetsMapCache = CCache.<Integer, DynamicTargetsMap>builder()
			.initialCapacity(1)
			.build();

	public List<GenericRelatedDocumentDescriptor> getRelatedDocumentDescriptors(@NonNull final String sourceKeyColumnName)
	{
		final String sourceTableName = MQuery.getZoomTableName(sourceKeyColumnName);

		final ArrayListMultimap<GenericTargetWindowInfo, GenericTargetColumnInfo> windowAndColumns = ArrayListMultimap.create();
		windowAndColumns.putAll(retrieveFrom_AD_Table_Related_Windows_V(sourceTableName));
		windowAndColumns.putAll(getDynamicTargetsMap().toMultimap());

		return windowAndColumns.keySet()
				.stream()
				.map(window -> GenericRelatedDocumentDescriptor.builder()
						.targetWindow(window)
						.targetColumns(windowAndColumns.get(window))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableListMultimap<GenericTargetWindowInfo, GenericTargetColumnInfo> retrieveFrom_AD_Table_Related_Windows_V(final String sourceTableName)
	{
		return DB.retrieveRows(
						"SELECT * FROM ad_table_related_windows_v WHERE source_tableName=?",
						ImmutableList.of(sourceTableName),
						this::retrieveRowFrom_AD_Table_Related_Windows_V)
				.stream()
				.flatMap(List::stream)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						TargetWindowAndColumn::getWindow,
						TargetWindowAndColumn::getColumn));
	}

	@Value
	private static class TargetWindowAndColumn
	{
		@NonNull GenericTargetWindowInfo window;
		@NonNull GenericTargetColumnInfo column;
	}

	private List<TargetWindowAndColumn> retrieveRowFrom_AD_Table_Related_Windows_V(final ResultSet rs) throws SQLException
	{
		final boolean isDefaultSOWindow = StringUtils.toBoolean(rs.getString("IsDefaultSOWindow"));
		final boolean isDefaultPOWindow = StringUtils.toBoolean(rs.getString("IsDefaultPOWindow"));

		GenericTargetWindowInfo targetWindow = extractWindowInfo(rs);

		final String tabSqlWhereClause = targetWindow.getTabSqlWhereClause();
		// SQL where clauses with context variables are not supported
		if (tabSqlWhereClause != null && tabSqlWhereClause.contains("@"))
		{
			// In case of default windows, we cannot exclude them, but we just take out the where clause
			if (isDefaultSOWindow || isDefaultPOWindow)
			{
				targetWindow = targetWindow.withTabSqlWhereClause(null);
			}
			else
			{
				logger.info("Excluding zoom to {}/{} because SQL where clause contains context variables which are not supported atm: {}", targetWindow.getTargetWindowId(), targetWindow.getName(), targetWindow.getTabSqlWhereClause());
				return ImmutableList.of();
			}
		}

		final GenericTargetColumnInfo targetColumn = extractColumnInfo(rs);

		final ArrayList<TargetWindowAndColumn> result = new ArrayList<>();
		if (targetWindow.isTargetHasIsSOTrxColumn() && (isDefaultSOWindow || isDefaultPOWindow))
		{
			if (isDefaultSOWindow)
			{
				result.add(new TargetWindowAndColumn(targetWindow.withSoTrx(SOTrx.SALES), targetColumn));
			}
			if (isDefaultPOWindow)
			{
				result.add(new TargetWindowAndColumn(targetWindow.withSoTrx(SOTrx.PURCHASE), targetColumn));
			}
		}
		else
		{
			result.add(new TargetWindowAndColumn(targetWindow.withSoTrx(null), targetColumn));
		}

		return result;
	}

	private static GenericTargetWindowInfo extractWindowInfo(final ResultSet rs) throws SQLException
	{
		return GenericTargetWindowInfo.builder()
				.name(retrieveTranslatableString(rs, "target_window_name", "target_window_name_trls"))
				.targetTableName(rs.getString("target_tablename"))
				.targetWindowId(AdWindowId.ofRepoId(rs.getInt("target_window_id")))
				.targetWindowInternalName(rs.getString("target_window_internalname"))
				.soTrx(null)
				.targetHasIsSOTrxColumn(StringUtils.toBoolean(rs.getString("target_has_issotrx_column")))
				.tabSqlWhereClause(rs.getString("tab_whereclause"))
				.build();
	}

	private static GenericTargetColumnInfo extractColumnInfo(final ResultSet rs) throws SQLException
	{
		return GenericTargetColumnInfo.builder()
				.caption(retrieveTranslatableString(rs, "target_columnDisplayName", "target_columnDisplayName_trls"))
				.columnName(rs.getString("target_columnname"))
				.virtualColumnSql(rs.getString("target_columnsql"))
				.build();
	}

	private static ImmutableTranslatableString retrieveTranslatableString(
			final ResultSet rs,
			final String nameColumn,
			final String trlColumn) throws SQLException
	{
		final ImmutableTranslatableString.ImmutableTranslatableStringBuilder result = ImmutableTranslatableString.builder()
				.defaultValue(rs.getString(nameColumn));

		final Array sqlArray = rs.getArray(trlColumn);
		if (sqlArray != null)
		{
			final String[][] trls = (String[][])sqlArray.getArray();
			for (final String[] languageAndName : trls)
			{
				final String adLanguage = languageAndName[0];
				final String name = languageAndName[1];

				result.trl(adLanguage, name);
			}
		}

		return result.build();
	}

	private DynamicTargetsMap getDynamicTargetsMap()
	{
		return dynamicTargetsMapCache.getOrLoad(0, this::retrieveDynamicTargetsMap);
	}

	private DynamicTargetsMap retrieveDynamicTargetsMap()
	{
		final ImmutableListMultimap<GenericTargetWindowInfo, GenericTargetColumnInfo> map = DB.retrieveRows(
						"SELECT * FROM dynamic_target_window_v",
						ImmutableList.of(),
						this::retrieveRowFrom_Dynamic_Target_Window_V)
				.stream()
				.filter(Objects::nonNull)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						TargetWindowAndColumn::getWindow,
						TargetWindowAndColumn::getColumn));

		return new DynamicTargetsMap(map);
	}

	@Nullable
	private TargetWindowAndColumn retrieveRowFrom_Dynamic_Target_Window_V(@NonNull final ResultSet rs) throws SQLException
	{
		final GenericTargetWindowInfo targetWindow = extractWindowInfo(rs);

		// SQL where clauses with context variables are not supported
		if (targetWindow.getTabSqlWhereClause() != null && targetWindow.getTabSqlWhereClause().contains("@"))
		{
			logger.info("Excluding zoom to {}/{} because SQL where clause contains context variables which are not supported atm: {}", targetWindow.getTargetWindowId(), targetWindow.getName(), targetWindow.getTabSqlWhereClause());
			return null;
		}

		final GenericTargetColumnInfo targetColumn = extractColumnInfo(rs).withDynamic(true);

		return new TargetWindowAndColumn(targetWindow, targetColumn);
	}

	//
	//
	//

	@Value
	private static class DynamicTargetsMap
	{
		ImmutableListMultimap<GenericTargetWindowInfo, GenericTargetColumnInfo> multimap;

		public ImmutableListMultimap<GenericTargetWindowInfo, GenericTargetColumnInfo> toMultimap() {return multimap;}
	}
}
