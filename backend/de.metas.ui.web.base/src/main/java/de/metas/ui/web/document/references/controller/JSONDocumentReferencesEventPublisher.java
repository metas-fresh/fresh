package de.metas.ui.web.document.references.controller;

import de.metas.logging.LogManager;
import de.metas.ui.web.document.references.json.JSONDocumentReferencesEvent;
import de.metas.ui.web.document.references.json.JSONDocumentReferencesGroup;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

class JSONDocumentReferencesEventPublisher
{
	public static JSONDocumentReferencesEventPublisher newInstance()
	{
		return new JSONDocumentReferencesEventPublisher();
	}

	private static final Logger logger = LogManager.getLogger(JSONDocumentReferencesEventPublisher.class);

	/**
	 * Set the timeout for this emitter to 2 minutes to avoid {@code AsyncRequestTimeoutException}s when nothing is cached yet.
	 */
	@Getter
	private final SseEmitter sseEmitter = new SseEmitter((long) (60000 * 2));

	private JSONDocumentReferencesEventPublisher()
	{
	}

	public void publishPartialResults(@NonNull final Collection<JSONDocumentReferencesGroup> groups)
	{
		if (groups.isEmpty())
		{
			return;
		}

		for (final JSONDocumentReferencesGroup group : groups)
		{
			publishPartialResult(group);
		}
	}

	public void publishPartialResult(@NonNull final JSONDocumentReferencesGroup group)
	{
		try
		{
			sseEmitter.send(JSONDocumentReferencesEvent.partialResult(group), MediaType.APPLICATION_JSON);
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed sending partial result: " + group, ex);
		}
	}

	public void publishCompleted()
	{
		try
		{
			sseEmitter.send(JSONDocumentReferencesEvent.COMPLETED, MediaType.APPLICATION_JSON);
			sseEmitter.complete();
		}
		catch (final IOException ex)
		{
			logger.warn("Failed publishing the COMPLETED event. Ignored.", ex);
		}
	}

	public void publishCompletedWithError(final Throwable ex)
	{
		try
		{
			sseEmitter.send(JSONDocumentReferencesEvent.COMPLETED, MediaType.APPLICATION_JSON);
			sseEmitter.completeWithError(ex);
		}
		catch (final IOException ioe)
		{
			ioe.addSuppressed(ex);
			logger.warn("Failed publishing the COMPLETED event. Ignored.", ioe);
		}
	}

}
