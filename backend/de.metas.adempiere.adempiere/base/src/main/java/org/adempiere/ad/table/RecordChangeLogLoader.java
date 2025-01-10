package org.adempiere.ad.table;

import java.util.List;

import org.adempiere.ad.table.LogEntriesRepository.LogEntriesQuery;
import org.adempiere.ad.table.RecordChangeLog.RecordChangeLogBuilder;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class RecordChangeLogLoader
{
	public static RecordChangeLogLoader ofAdTableId(final int adTableId)
	{
		return new RecordChangeLogLoader(adTableId);
	}

	private final POInfo poInfo;

	private RecordChangeLogLoader(final int adTableId)
	{
		Check.assumeGreaterThanZero(adTableId, "adTableId");

		poInfo = POInfo.getPOInfo(adTableId);
		Check.assumeNotNull(poInfo, "poInfo is not null for adTableId={}", adTableId);

	}

	public RecordChangeLog getByRecordId(@NonNull final ComposedRecordId recordId)
	{
		final RecordChangeLogBuilder changeLogsBuilder = RecordChangeLog.builder()
				.tableName(poInfo.getTableName())
				.recordId(recordId);

		loadRecordSummary(changeLogsBuilder, recordId);

		final List<RecordChangeLogEntry> logEntries = retrieveLogEntries(recordId);
		changeLogsBuilder.entries(logEntries);

		return changeLogsBuilder.build();
	}

	public RecordChangeLog getSummaryByRecordId(@NonNull final ComposedRecordId recordId)
	{
		final RecordChangeLogBuilder changeLogsBuilder = RecordChangeLog.builder()
				.tableName(poInfo.getTableName())
				.recordId(recordId);

		loadRecordSummary(changeLogsBuilder, recordId);

		return changeLogsBuilder.build();
	}

	private void loadRecordSummary(final RecordChangeLogBuilder changeLogsBuilder, final ComposedRecordId recordId)
	{
		DB.forFirstRowIfAny(
				"SELECT Created, CreatedBy, Updated, UpdatedBy FROM " + poInfo.getTableName() + " WHERE " + poInfo.getSqlWhereClauseByKeys(),
				recordId.getKeysAsList(poInfo.getKeyColumnNames()),
				rs -> changeLogsBuilder.createdByUserId(UserId.ofRepoIdOrNull(rs.getInt("CreatedBy")))
						.createdTimestamp(rs.getTimestamp("Created").toInstant())
						.lastChangedByUserId(UserId.ofRepoIdOrNull(rs.getInt("UpdatedBy")))
						.lastChangedTimestamp(rs.getTimestamp("Updated").toInstant()));
	}

	private List<RecordChangeLogEntry> retrieveLogEntries(@NonNull final ComposedRecordId recordId)
	{
		if (!recordId.isSingleKey())
		{
			return ImmutableList.of();
		}

		final int singleRecordId = recordId.getSingleRecordId().orElse(-1);

		final TableRecordReference recordRef = TableRecordReference.of(poInfo.getAD_Table_ID(), singleRecordId);

		final ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> //
				logEntries = RecordChangeLogEntryLoader.retrieveLogEntries(LogEntriesQuery.of(recordRef));

		return (logEntries.get(recordRef));
	}
}
