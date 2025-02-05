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

package de.metas.util.web.audit.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import de.metas.rest_api.utils.v2.JsonErrors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Value
@Builder(toBuilder = true)
public class ApiResponse
{
	int statusCode;
	@Nullable HttpHeaders httpHeaders;
	@Nullable MediaType contentType;
	@NonNull @Builder.Default Charset charset = StandardCharsets.UTF_8;
	@Nullable Object body;

	public static ApiResponse ofException(final Throwable throwable, final String adLanguage)
	{
		return ApiResponse.builder()
				.statusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON)
				.body(JsonErrors.ofThrowable(throwable, adLanguage))
				.build();
	}

	public boolean hasStatus2xx()
	{
		return getStatusCode() / 100 == HttpStatus.OK.series().value();
	}

	public boolean isJson()
	{
		return contentType != null && contentType.includes(MediaType.APPLICATION_JSON);
	}

	public String getBodyAsString()
	{
		if (body == null)
		{
			return null;
		}
		else if (isJson())
		{
			try
			{
				return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(body);
			}
			catch (JsonProcessingException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
		else if (body instanceof String)
		{
			return (String)body;
		}
		else if (body instanceof byte[])
		{
			return new String((byte[])body, charset);
		}
		else
		{
			return body.toString();
		}
	}
}
