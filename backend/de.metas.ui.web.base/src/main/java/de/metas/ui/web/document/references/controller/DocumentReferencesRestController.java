package de.metas.ui.web.document.references.controller;

import com.google.common.collect.ImmutableList;
import de.metas.document.references.related_documents.RelatedDocumentsEvaluationContext;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.document.references.related_documents.RelatedDocumentsPermissionsFactory;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.references.WebuiDocumentReference;
import de.metas.ui.web.document.references.WebuiDocumentReferenceCandidate;
import de.metas.ui.web.document.references.service.WebuiDocumentReferencesService;
import de.metas.ui.web.menu.MenuTree;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.Services;
import io.swagger.annotations.Api;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

@Api
@RestController
@RequestMapping(DocumentReferencesRestController.ENDPOINT)
public class DocumentReferencesRestController
{
	public static final String ENDPOINT = WindowRestController.ENDPOINT;

	private static final Logger logger = LogManager.getLogger(DocumentReferencesRestController.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final UserSession userSession;
	private final WebuiDocumentReferencesService webuiDocumentReferencesService;
	private final MenuTreeRepository menuTreeRepository;

	private static final String SYSCONFIG_SSE_EXECUTOR_MAX_POOL_SIZE = "webui.documentReferencesRestController.sseExecutor.maxPoolSize";
	private final ExecutorService sseExecutor;

	public DocumentReferencesRestController(
			@NonNull final UserSession userSession,
			@NonNull final WebuiDocumentReferencesService webuiDocumentReferencesService,
			@NonNull final MenuTreeRepository menuTreeRepository)
	{
		this.userSession = userSession;
		this.webuiDocumentReferencesService = webuiDocumentReferencesService;
		this.menuTreeRepository = menuTreeRepository;

		this.sseExecutor = createSseExecutor();
		logger.info("Created {}", sseExecutor);
	}

	private static ExecutorService createSseExecutor()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int maxPoolSize = sysConfigBL.getIntValue(SYSCONFIG_SSE_EXECUTOR_MAX_POOL_SIZE, 20);

		final CustomizableThreadFactory threadFactory = CustomizableThreadFactory.builder()
				.setDaemon(true)
				.setThreadNamePrefix(DocumentReferencesRestController.class.getSimpleName() + "-SSE-")
				.build();

		return new ThreadPoolExecutor(
				5, // corePoolSize
				maxPoolSize,
				60L, // keepAliveTime
				TimeUnit.SECONDS, // keepAliveTime unit
				new LinkedBlockingQueue<>(), // workQueue
				threadFactory);

	}

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.prepareFrom(userSession).build();
	}

	@GetMapping("/{windowId}/{documentId}/references/sse")
	public SseEmitter streamRootDocumentReferences(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentId)
	{
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(
				WindowId.fromJson(windowIdStr),
				documentId);

		return streamRootDocumentReferences(documentPath);
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}/references/sse")
	public SseEmitter streamIncludedDocumentReferences(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("tabId") final String tabIdStr,
			@PathVariable("rowId") final String rowIdStr)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentIdStr, tabIdStr, rowIdStr);

		return streamRootDocumentReferences(documentPath);
	}

	private SseEmitter streamRootDocumentReferences(final DocumentPath documentPath)
	{
		final JSONDocumentReferencesEventPublisher publisher = JSONDocumentReferencesEventPublisher.newInstance();

		try
		{
			userSession.assertLoggedIn();

			final RelatedDocumentsPermissions permissions = RelatedDocumentsPermissionsFactory.ofRolePermissions(userSession.getUserRolePermissions());
			final List<WebuiDocumentReferenceCandidate> documentReferenceCandidates = webuiDocumentReferencesService.getDocumentReferenceCandidates(documentPath, permissions);
			if (documentReferenceCandidates.isEmpty())
			{
				publisher.publishCompleted();
				return publisher.getSseEmitter();
			}

			final JSONOptions jsonOpts = newJSONOptions();
			final MenuTree menuTree = menuTreeRepository.getMenuTree(
					userSession.getUserRolePermissionsKey(),
					jsonOpts.getAdLanguage());

			final AsyncRunContext context = AsyncRunContext.builder()
					.permissions(RelatedDocumentsPermissionsFactory.ofRolePermissions(userSession.getUserRolePermissions()))
					.jsonOpts(jsonOpts)
					.menuTree(menuTree)
					.publisher(publisher)
					.build();

			evaluateAndPublishAll(documentReferenceCandidates, context);
		}
		catch (final Exception ex)
		{
			publisher.publishCompletedWithError(ex);
		}

		return publisher.getSseEmitter();
	}

	private void evaluateAndPublishAll(
			@NonNull final List<WebuiDocumentReferenceCandidate> documentReferenceCandidates,
			@NonNull final AsyncRunContext context)
	{
		final CompletableFuture<?>[] futures = documentReferenceCandidates.stream()
				.map(documentReferenceCandidate -> evaluateAndPublish(documentReferenceCandidate, context))
				.toArray(CompletableFuture[]::new);

		if (futures.length == 0)
		{
			return;
		}

		CompletableFuture.allOf(futures)
				.whenComplete((voidResult, exception) -> {
					context.getPublisher().publishCompleted();

					if (exception != null)
					{
						logger.warn("Failed processing some of the partial results", exception);
					}
				});
	}

	private CompletableFuture<Void> evaluateAndPublish(
			@NonNull final WebuiDocumentReferenceCandidate documentReferenceCandidate,
			@NonNull final AsyncRunContext context)
	{
		return CompletableFuture.runAsync(
				() -> evaluateAndPublishNow(documentReferenceCandidate, context),
				sseExecutor);
	}

	private void evaluateAndPublishNow(
			@NonNull final WebuiDocumentReferenceCandidate documentReferenceCandidate,
			@NonNull final AsyncRunContext context)
	{

		final ImmutableList<WebuiDocumentReference> documentReferences = documentReferenceCandidate
				.evaluateAndStream(
						RelatedDocumentsEvaluationContext.builder()
								.permissions(context.getPermissions())
								.doNotTrackSeenWindows(true)
								.build())
				.collect(ImmutableList.toImmutableList());
		if (!documentReferences.isEmpty())
		{
			final JSONDocumentReferencesGroupsAggregator aggregator = JSONDocumentReferencesGroupsAggregator.builder()
					.menuTree(context.getMenuTree())
					.msgBL(msgBL)
					.jsonOpts(context.getJsonOpts())
					.build();

			aggregator.addAndPublish(documentReferences, context.getPublisher());
		}
	}

	@Value
	@Builder
	private static class AsyncRunContext
	{
		@NonNull
		RelatedDocumentsPermissions permissions;

		@NonNull
		JSONOptions jsonOpts;

		@NonNull
		MenuTree menuTree;

		@NonNull
		JSONDocumentReferencesEventPublisher publisher;
	}
}
