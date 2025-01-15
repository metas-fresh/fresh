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

package de.metas.audit.apirequest.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.audit.apirequest.common.HttpHeadersWrapper;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.ui_trace.UITraceExternalId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class ApiRequestAudit
{
	@Nullable // null, when the entity was not persisted yet
	ApiRequestAuditId apiRequestAuditId;

	@NonNull
	OrgId orgId;

	@NonNull
	RoleId roleId;

	@NonNull
	UserId userId;

	@NonNull
	ApiAuditConfigId apiAuditConfigId;

	@NonNull
	Status status;

	boolean isErrorAcknowledged;

	@Nullable
	String body;

	@Nullable
	HttpMethod method;

	@Nullable
	String path;

	@Nullable
	String remoteAddress;

	@Nullable
	String remoteHost;

	@NonNull
	Instant time;

	@Nullable
	String httpHeaders;

	@Nullable
	String requestURI;
	
	@Nullable UITraceExternalId uiTraceExternalId;

	@NonNull
	public ApiRequestAuditId getIdNotNull()
	{
		if (this.apiRequestAuditId == null)
		{
			throw new AdempiereException("getIdNotNull() should be called only for already persisted ApiRequestAudit objects!")
					.appendParametersToMessage()
					.setParameter("ApiRequestAudit", this);
		}

		return apiRequestAuditId;
	}

	@NonNull
	public Optional<HttpHeadersWrapper> getRequestHeaders(@NonNull final ObjectMapper objectMapper)
	{
		if (Check.isBlank(httpHeaders))
		{
			return Optional.empty();
		}

		try
		{
			return Optional.of(objectMapper.readValue(httpHeaders, HttpHeadersWrapper.class));
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to parse httpHeaders!")
					.appendParametersToMessage()
					.setParameter("ApiAuditRequest", this);
		}
	}

	@NonNull
	public Optional<Object> getRequestBody(@NonNull final ObjectMapper objectMapper)
	{
		if (Check.isBlank(body))
		{
			return Optional.empty();
		}

		try
		{
			return Optional.of(objectMapper.readValue(body, Object.class));
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to parse body!")
					.appendParametersToMessage()
					.setParameter("ApiAuditRequest", this);
		}
	}
}
