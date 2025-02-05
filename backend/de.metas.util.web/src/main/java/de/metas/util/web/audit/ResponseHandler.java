/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.audit;

import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.util.Loggables;
import de.metas.util.web.audit.dto.ApiResponse;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.Env;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static de.metas.util.web.audit.ApiAuditService.API_RESPONSE_HEADER_REQUEST_AUDIT_ID;

@UtilityClass
public class ResponseHandler
{
	public void writeHttpResponse(
			@NonNull final ApiResponse apiResponse,
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final ApiRequestAudit apiRequestAudit,
			@NonNull final HttpServletResponse httpServletResponse) throws IOException
	{
		if (!isResetServletResponse(httpServletResponse))
		{
			//dev-note: it basically means the response was already written and committed
			return;
		}

		final ApiResponse apiResponseWrapped = wrapBodyIfNeeded(apiAuditConfig, apiRequestAudit, apiResponse);

		writeResponse(httpServletResponse, apiResponseWrapped);
	}

	public void writeErrorResponse(
			@NonNull final Throwable throwable,
			@NonNull final HttpServletResponse httpServletResponse,
			@Nullable final ApiRequestAudit apiRequestAudit,
			@Nullable final ApiAuditConfig apiAuditConfig) throws IOException
	{
		if (!isResetServletResponse(httpServletResponse))
		{
			//dev-note: it basically means the response was already written and committed
			return;
		}

		final ApiResponse apiResponse = ApiResponse.ofException(throwable, Env.getADLanguageOrBaseLanguage());
		final ApiResponse apiResponseWrapped = wrapBodyIfNeeded(apiAuditConfig, apiRequestAudit, apiResponse);

		writeResponse(httpServletResponse, apiResponseWrapped);
	}

	private void writeResponse(
			@NonNull final HttpServletResponse httpServletResponse,
			@NonNull final ApiResponse apiResponse) throws IOException
	{
		forwardSomeResponseHttpHeaders(httpServletResponse, apiResponse.getHttpHeaders());

		httpServletResponse.setStatus(apiResponse.getStatusCode());
		httpServletResponse.setContentType(apiResponse.getContentType() != null ? apiResponse.getContentType().toString() : null);
		httpServletResponse.setCharacterEncoding(apiResponse.getCharset().name());

		final String body = apiResponse.getBodyAsString();
		if (body != null)
		{
			httpServletResponse.getWriter().write(body);
		}

		httpServletResponse.flushBuffer();
	}

	private void forwardSomeResponseHttpHeaders(@NonNull final HttpServletResponse servletResponse, @Nullable final HttpHeaders httpHeaders)
	{
		if (httpHeaders == null || httpHeaders.isEmpty())
		{
			return;
		}

		httpHeaders.keySet()
				.stream()
				.filter(key -> !key.equals(HttpHeaders.CONNECTION))
				.filter(key -> !key.equals(HttpHeaders.CONTENT_LENGTH))
				.filter(key -> !key.equals(HttpHeaders.CONTENT_TYPE))
				.filter(key -> !key.equals(HttpHeaders.TRANSFER_ENCODING)) // if we forwarded this without knowing what we do, we would annoy a possible nginx reverse proxy
				.forEach(key -> {
					final List<String> values = httpHeaders.get(key);
					if (values != null)
					{
						values.forEach(value -> servletResponse.addHeader(key, value));
					}
				});
	}

	@NonNull
	private ApiResponse wrapBodyIfNeeded(
			@Nullable final ApiAuditConfig apiAuditConfig,
			@Nullable final ApiRequestAudit apiRequestAudit,
			@NonNull final ApiResponse apiResponse)
	{
		if (apiAuditConfig != null && apiRequestAudit != null && apiAuditConfig.isWrapApiResponse() && apiResponse.isJson())
		{
			return apiResponse.toBuilder()
					.contentType(MediaType.APPLICATION_JSON)
					.charset(StandardCharsets.UTF_8)
					.body(JsonApiResponse.builder()
							.requestId(JsonMetasfreshId.of(apiRequestAudit.getIdNotNull().getRepoId()))
							.endpointResponse(apiResponse.getBody())
							.build())
					.build();
		}
		else
		{
			final HttpHeaders httpHeaders = apiResponse.getHttpHeaders() != null ? new HttpHeaders(apiResponse.getHttpHeaders()) : new HttpHeaders();
			if (apiRequestAudit != null)
			{
				httpHeaders.add(API_RESPONSE_HEADER_REQUEST_AUDIT_ID, String.valueOf(apiRequestAudit.getIdNotNull().getRepoId()));
			}

			return apiResponse.toBuilder().httpHeaders(httpHeaders).build();
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean isResetServletResponse(@NonNull final HttpServletResponse response)
	{
		if (!response.isCommitted())
		{
			response.reset();
			return true;
		}

		Loggables.addLog("HttpServletResponse has already been committed -> cannot be altered! response status = {}", response.getStatus());
		return false;
	}
}
