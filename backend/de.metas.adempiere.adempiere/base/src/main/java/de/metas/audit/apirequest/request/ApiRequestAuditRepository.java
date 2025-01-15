/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.audit.apirequest.request;

import de.metas.audit.apirequest.HttpMethod;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.audit.request.ApiRequestIterator;
import de.metas.audit.request.ApiRequestQuery;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.ui_trace.UITraceExternalId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ApiRequestAuditRepository
{
	private final static String SYS_CONFIG_ITERATOR_BUFFER_SIZE = "de.metas.audit.request.IteratorBufferSize";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull
	public ApiRequestAudit save(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		final I_API_Request_Audit record = InterfaceWrapperHelper.loadOrNew(apiRequestAudit.getApiRequestAuditId(), I_API_Request_Audit.class);

		record.setAD_Org_ID(apiRequestAudit.getOrgId().getRepoId());
		record.setAD_Role_ID(apiRequestAudit.getRoleId().getRepoId());
		record.setAD_User_ID(apiRequestAudit.getUserId().getRepoId());
		record.setAPI_Audit_Config_ID(apiRequestAudit.getApiAuditConfigId().getRepoId());
		record.setStatus(apiRequestAudit.getStatus().getCode());
		record.setIsErrorAcknowledged(apiRequestAudit.isErrorAcknowledged());
		record.setBody(apiRequestAudit.getBody());
		record.setMethod(apiRequestAudit.getMethod() != null ? apiRequestAudit.getMethod().getCode() : null);
		record.setPath(apiRequestAudit.getPath());
		record.setRemoteAddr(apiRequestAudit.getRemoteAddress());
		record.setRemoteHost(apiRequestAudit.getRemoteHost());
		record.setTime(TimeUtil.asTimestamp(apiRequestAudit.getTime()));
		record.setHttpHeaders(apiRequestAudit.getHttpHeaders());
		record.setRequestURI(apiRequestAudit.getRequestURI());
		record.setUI_Trace_ExternalId(apiRequestAudit.getUiTraceExternalId() != null ? apiRequestAudit.getUiTraceExternalId().getAsString() : null);

		saveRecord(record);

		return recordToRequestAudit(record);
	}

	@NonNull
	public ApiRequestAudit getById(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{
		final I_API_Request_Audit record = InterfaceWrapperHelper.load(apiRequestAuditId, I_API_Request_Audit.class);

		return recordToRequestAudit(record);
	}

	@NonNull
	public ApiRequestIterator getByQuery(@NonNull final ApiRequestQuery query)
	{
		final IQueryBuilder<I_API_Request_Audit> apiRequestQueryBuilder = queryBL.createQueryBuilder(I_API_Request_Audit.class)
				.addInArrayFilter(I_API_Request_Audit.COLUMNNAME_Status, query.getApiRequestStatusCodeSet());

		if (query.getIsErrorAcknowledged() != null)
		{
			apiRequestQueryBuilder.addEqualsFilter(I_API_Request_Audit.COLUMNNAME_IsErrorAcknowledged, query.getIsErrorAcknowledged());
		}

		final IQuery<I_API_Request_Audit> apiRequestAuditQuery = apiRequestQueryBuilder.create();

		if (query.isOrderByTimeAscending())
		{
			final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_API_Request_Audit.class)
					.addColumnAscending(I_API_Request_Audit.COLUMNNAME_Time)
					.createQueryOrderBy();

			apiRequestAuditQuery.setOrderBy(orderBy);
		}

		final int bufferSize = sysConfigBL.getIntValue(SYS_CONFIG_ITERATOR_BUFFER_SIZE, -1);
		if (bufferSize > 0)
		{
			apiRequestAuditQuery.setOption(IQuery.OPTION_IteratorBufferSize, bufferSize);
		}

		final Iterator<I_API_Request_Audit> apiRequestRecordsIterator = apiRequestAuditQuery.iterate(I_API_Request_Audit.class);

		return ApiRequestIterator.of(apiRequestRecordsIterator, ApiRequestAuditRepository::recordToRequestAudit);
	}

	public void deleteRequestAudit(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{
		final I_API_Request_Audit record = InterfaceWrapperHelper.load(apiRequestAuditId, I_API_Request_Audit.class);

		InterfaceWrapperHelper.deleteRecord(record);
	}

	@NonNull
	public static ApiRequestAudit recordToRequestAudit(@NonNull final I_API_Request_Audit record)
	{
		return ApiRequestAudit.builder()
				.apiRequestAuditId(ApiRequestAuditId.ofRepoId(record.getAPI_Request_Audit_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.roleId(RoleId.ofRepoId(record.getAD_Role_ID()))
				.userId(UserId.ofRepoId(record.getAD_User_ID()))
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(record.getAPI_Audit_Config_ID()))
				.status(Status.ofCode(record.getStatus()))
				.isErrorAcknowledged(record.isErrorAcknowledged())
				.body(record.getBody())
				.method(HttpMethod.ofNullableCode(record.getMethod()))
				.path(record.getPath())
				.remoteAddress(record.getRemoteAddr())
				.remoteHost(record.getRemoteHost())
				.time(TimeUtil.asInstantNonNull(record.getTime()))
				.httpHeaders(record.getHttpHeaders())
				.requestURI(record.getRequestURI())
				.uiTraceExternalId(UITraceExternalId.ofNullableString(record.getUI_Trace_ExternalId()))
				.build();
	}
}
