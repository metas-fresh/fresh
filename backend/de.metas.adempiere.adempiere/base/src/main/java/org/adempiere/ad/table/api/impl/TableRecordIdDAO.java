package org.adempiere.ad.table.api.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import lombok.NonNull;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.ad.table.api.TableAndColumnName;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.POInfo;
import org.compiere.model.POInfo.POInfoMap;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@SuppressWarnings("unused")
public class TableRecordIdDAO implements ITableRecordIdDAO
{
	private static final String DB_FUNCTION_RETRIEVE_DISTINCT_IDS = "table_record_reference_retrieve_distinct_ids";

	private final CCache<String, ImmutableList<TableRecordIdDescriptor>> tableRecordIdDescriptorsByOriginTableName = CCache.<String, ImmutableList<TableRecordIdDescriptor>>builder()
			.cacheName("TableRecordIdDAO_tableRecordIdDescriptorsByOriginTableName")
			.build();

	public TableRecordIdDAO()
	{
		CacheMgt.get().addCacheResetListener(this::onCacheReset);
	}

	private long onCacheReset(@NonNull final CacheInvalidateMultiRequest multiRequest)
	{
		if (multiRequest.isResetAll())
		{
			return tableRecordIdDescriptorsByOriginTableName.reset();
		}
		else
		{
			final Set<String> tableNamesEffective = multiRequest.getTableNamesEffective();
			tableRecordIdDescriptorsByOriginTableName.removeAll(tableNamesEffective);
			return tableNamesEffective.size();
		}
	}

	@Override
	public List<TableRecordIdDescriptor> getTableRecordIdReferences(@NonNull final String tableName)
	{
		return tableRecordIdDescriptorsByOriginTableName.getOrLoad(tableName, this::retrieveTableRecordIdReferences);
	}

	@Override
	public List<TableRecordIdDescriptor> retrieveAllTableRecordIdReferences()
	{
		return retrieveTableRecordIdReferences(null);
	}

	private ImmutableList<TableRecordIdDescriptor> retrieveTableRecordIdReferences(@Nullable final String onlyTableName)
	{
		final POInfoMap poInfoMap = POInfo.getPOInfoMap();

		final ImmutableCollection<POInfo> selectedPOInfos;
		if (onlyTableName != null)
		{
			final POInfo poInfo = POInfo.getPOInfoIfPresent(onlyTableName).orElse(null);
			if (poInfo == null)
			{
				return ImmutableList.of();
			}

			selectedPOInfos = ImmutableList.of(poInfo);
		}
		else
		{
			selectedPOInfos = poInfoMap.toCollection();
		}

		final ImmutableList.Builder<TableRecordIdDescriptor> result = ImmutableList.builder();
		for (final POInfo poInfo : selectedPOInfos)
		{
			if (poInfo.isView())
			{
				continue;
			}

			final String tableName = poInfo.getTableName();
			for (final TableAndColumnName tableAndRecordIdColumnName : poInfo.getTableAndRecordColumnNames())
			{
				for (final AdTableId referencedTableId : retrieveDistinctIds(tableName, tableAndRecordIdColumnName.getTableNameAsString()))
				{
					final POInfo referencedPOInfo = poInfoMap.getByTableIdOrNull(referencedTableId);
					if (referencedPOInfo == null)
					{
						continue;
					}

					result.add(
							TableRecordIdDescriptor.builder()
									.originTableName(tableName)
									.recordIdColumnName(tableAndRecordIdColumnName.getColumnNameAsString())
									.targetTableName(referencedPOInfo.getTableName())
									.build()
					);
				}
			}
		}

		return result.build();
	}

	/**
	 * This method executes the equivalent of the following query:
	 * <p>
	 * {@code SELECT DISTINCT(<p_id_columnname>) FROM <p_tablename> WHERE COALESCE(<p_id_columnname>,0)!=0}
	 * <p>
	 * ...just faster.
	 * <p>
	 * See <a href="https://github.com/metasfresh/metasfresh/issues/3389">https://github.com/metasfresh/metasfresh/issues/3389</a>
	 */
	private ImmutableSet<AdTableId> retrieveDistinctIds(
			@NonNull final String tableName,
			@NonNull final String idColumnName)
	{
		final String sql = "SELECT " + DB_FUNCTION_RETRIEVE_DISTINCT_IDS + "(?,?)";
		final Object[] sqlParams = new Object[] { tableName, idColumnName };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, ImmutableList.of(tableName, idColumnName));
			rs = pstmt.executeQuery();

			final ImmutableSet.Builder<AdTableId> result = ImmutableSet.builder();
			while (rs.next())
			{
				final AdTableId adTableId = AdTableId.ofRepoIdOrNull(rs.getInt(1));
				if (adTableId != null)
				{
					result.add(adTableId);
				}
			}
			return result.build();
		}
		catch (final SQLException ex)
		{
			throw DBException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("sql", sql)
					.setParameter("sqlParams", sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
