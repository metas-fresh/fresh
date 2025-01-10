package de.metas.ui.web.window.model;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangeLog;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.ad.table.RecordChangeLogRepository;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

/*
 * #%L
 * metasfresh-webui-api
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

@Service
@RequiredArgsConstructor
public class DocumentChangeLogService
{
	@NonNull private final IUserDAO usersRepo = Services.get(IUserDAO.class);
	@NonNull private final DocumentCollection documentCollection;
	@NonNull private final RecordChangeLogRepository changeLogRepository;

	public JSONDocumentChangeLog getJSONDocumentChangeLog(@NonNull final DocumentPath documentPath)
	{
		final TableRecordReference recordRef = documentCollection.getTableRecordReference(documentPath);
		final RecordChangeLog changeLog = changeLogRepository.getSummaryByRecord(recordRef);
		
		return JSONDocumentChangeLog.builder()
				.path(JSONDocumentPath.ofWindowDocumentPath(documentPath))
				.createdByUsername(usersRepo.retrieveUserFullName(changeLog.getCreatedByUserId()))
				.createdTimestamp(changeLog.getCreatedTimestamp().toString())
				.lastChangedByUsername(usersRepo.retrieveUserFullName(changeLog.getLastChangedByUserId()))
				.lastChangedTimestamp(changeLog.getLastChangedTimestamp().toString())
				.build();
	}
}
