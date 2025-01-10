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

package de.metas.ui.web.window.controller;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.document.NewRecordContext;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.logging.LogManager;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.reflist.ReferenceId;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.ui.web.cache.ETagResponseEntityBuilder;
import de.metas.ui.web.comments.CommentsService;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.debug.DebugRestController;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.DocumentPreconditionsAsContext;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentAdvSearch;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangeLog;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions.JSONDocumentLayoutOptionsBuilder;
import de.metas.ui.web.window.datatypes.json.JSONDocumentList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentOptions.JSONDocumentOptionsBuilder;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPatchResult;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONOptions.JSONOptionsBuilder;
import de.metas.ui.web.window.datatypes.json.JSONZoomInto;
import de.metas.ui.web.window.datatypes.json.JsonWindowsHealthResponse;
import de.metas.ui.web.window.descriptor.ButtonFieldActionDescriptor;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.factory.AdvancedSearchDescriptorsProvider;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentChangeLogService;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.ui.web.window.model.lookup.DocumentZoomIntoInfo;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.TableRefInfo;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Api
@RestController
@RequestMapping(value = WindowRestController.ENDPOINT)
public class WindowRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/window";

	private static final String PARAM_Advanced = "advanced";
	private static final String PARAM_Advanced_DefaultValue = "false";
	private static final String PARAM_FieldsList = "fields";

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	@NonNull private static final Logger logger = LogManager.getLogger(DebugRestController.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	@NonNull private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	private final UserSession userSession;
	private final DocumentCollection documentCollection;
	private final DocumentChangeLogService documentChangeLogService;
	private final NewRecordDescriptorsProvider newRecordDescriptorsProvider;
	private final AdvancedSearchDescriptorsProvider advancedSearchDescriptorsProvider;
	private final ProcessRestController processRestController;
	private final DocumentWebsocketPublisher websocketPublisher;
	private final CommentsService commentsService;
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;

	public WindowRestController(
			@NonNull final UserSession userSession,
			@NonNull final DocumentCollection documentCollection,
			@NonNull final DocumentChangeLogService documentChangeLogService,
			@NonNull final NewRecordDescriptorsProvider newRecordDescriptorsProvider,
			@NonNull final AdvancedSearchDescriptorsProvider advancedSearchDescriptorsProvider,
			@NonNull final ProcessRestController processRestController,
			@NonNull final DocumentWebsocketPublisher websocketPublisher,
			@NonNull final CommentsService commentsService,
			@NonNull final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository)
	{
		this.userSession = userSession;
		this.documentCollection = documentCollection;
		this.documentChangeLogService = documentChangeLogService;
		this.newRecordDescriptorsProvider = newRecordDescriptorsProvider;
		this.advancedSearchDescriptorsProvider = advancedSearchDescriptorsProvider;
		this.processRestController = processRestController;
		this.websocketPublisher = websocketPublisher;
		this.commentsService = commentsService;
		this.customizedWindowInfoMapRepository = customizedWindowInfoMapRepository;
	}

	private JSONOptionsBuilder newJSONOptions()
	{
		return JSONOptions.prepareFrom(userSession);
	}

	private JSONDocumentLayoutOptionsBuilder newJSONLayoutOptions()
	{
		return JSONDocumentLayoutOptions.prepareFrom(userSession)
				.newRecordDescriptorsProvider(newRecordDescriptorsProvider)
				.advancedSearchDescriptorsProvider(advancedSearchDescriptorsProvider);
	}

	private JSONDocumentOptionsBuilder newJSONDocumentOptions()
	{
		return JSONDocumentOptions.builder()
				.userSession(userSession);
	}

	@GetMapping("/{windowId}/layout")
	public ResponseEntity<JSONDocumentLayout> getLayout(
			@PathVariable("windowId") final String windowIdStr,
			@RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced,
			final WebRequest request)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentDescriptor descriptor = documentCollection.getDocumentDescriptorFactory().getDocumentDescriptor(windowId);
		DocumentPermissionsHelper.checkWindowAccess(descriptor.getEntityDescriptor(), userSession.getUserRolePermissions());

		return ETagResponseEntityBuilder.ofETagAware(request, descriptor)
				.includeLanguageInETag()
				.cacheMaxAge(userSession.getHttpCacheMaxAge())
				.map(DocumentDescriptor::getLayout)
				//
				.jsonLayoutOptions(() -> newJSONLayoutOptions().showAdvancedFields(advanced).build())
				.toLayoutJson(JSONDocumentLayout::ofHeaderLayout);
	}

	@GetMapping("/{windowId}/{tabId}/layout")
	public ResponseEntity<JSONDocumentLayout> getLayout(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("tabId") final String tabIdStr,
			@RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced,
			final WebRequest request)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DetailId detailId = DetailId.fromJson(tabIdStr);

		final DocumentDescriptor descriptor = documentCollection.getDocumentDescriptorFactory().getDocumentDescriptor(windowId);
		DocumentPermissionsHelper.checkWindowAccess(descriptor.getEntityDescriptor(), userSession.getUserRolePermissions());

		return ETagResponseEntityBuilder.ofETagAware(request, descriptor)
				.includeLanguageInETag()
				.cacheMaxAge(userSession.getHttpCacheMaxAge())
				.map(desc -> desc.getLayout().getDetail(detailId))
				//
				.jsonLayoutOptions(() -> newJSONLayoutOptions().showAdvancedFields(advanced).build())
				.toLayoutJson(JSONDocumentLayout::ofDetailTab);
	}

	@GetMapping("/{windowId}/{documentId}")
	public List<JSONDocument> getRootDocuments(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@RequestParam(name = PARAM_FieldsList, required = false) @ApiParam("comma separated field names") final String fieldsListStr,
			@RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);
		final JSONDocumentOptions jsonOpts = newJSONDocumentOptions()
				.showOnlyFieldsListStr(fieldsListStr)
				.showAdvancedFields(advanced)
				.build();
		return getData(documentPath, DocumentQueryOrderByList.EMPTY, jsonOpts);
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}")
	public JSONDocumentList getIncludedTabRows(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("tabId") final String tabIdStr,
			@RequestParam(name = "ids", required = false) @ApiParam("comma separated rowIds") final String rowIdsListStr,
			@RequestParam(name = PARAM_FieldsList, required = false) @ApiParam("comma separated field names") final String fieldsListStr,
			@RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced,
			@RequestParam(name = "orderBy", required = false) final String orderBysListStr)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentId documentId = DocumentId.of(documentIdStr);
		final DetailId tabId = DetailId.fromJson(tabIdStr);

		final DocumentIdsSelection onlyRowIds = DocumentIdsSelection.ofCommaSeparatedString(rowIdsListStr);
		final DocumentPath documentPath;
		if (onlyRowIds.isEmpty() || onlyRowIds.isAll())
		{
			documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId);
		}
		else
		{
			documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, onlyRowIds);
		}

		final DocumentQueryOrderByList orderBys = DocumentQueryOrderByList.parse(orderBysListStr);

		final JSONDocumentOptions jsonOpts = newJSONDocumentOptions()
				.showOnlyFieldsListStr(fieldsListStr)
				.showAdvancedFields(advanced)
				.build();

		final List<JSONDocument> rows = getData(documentPath, orderBys, jsonOpts);

		final Set<DocumentId> missingRowIds;
		if (!onlyRowIds.isEmpty() && !onlyRowIds.isAll())
		{
			final ImmutableSet<DocumentId> foundRowIds = rows.stream()
					.map(JSONDocument::getRowId)
					.collect(ImmutableSet.toImmutableSet());

			missingRowIds = Sets.difference(onlyRowIds.toSet(), foundRowIds);
		}
		else
		{
			missingRowIds = null;
		}

		return JSONDocumentList.builder()
				.result(rows)
				.missingIds(missingRowIds)
				.build();

	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}")
	public List<JSONDocument> getIncludedTabRow(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentIdStr
			//
			,
			@PathVariable("tabId") final String tabIdStr
			//
			,
			@PathVariable("rowId") final String rowIdStr
			//
			,
			@RequestParam(name = PARAM_FieldsList, required = false) @ApiParam("comma separated field names") final String fieldsListStr
			//
			,
			@RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentIdStr, tabIdStr, rowIdStr);
		final JSONDocumentOptions jsonOpts = newJSONDocumentOptions()
				.showOnlyFieldsListStr(fieldsListStr)
				.showAdvancedFields(advanced)
				.build();

		return getData(documentPath, DocumentQueryOrderByList.EMPTY, jsonOpts);
	}

	private List<JSONDocument> getData(
			@NonNull final DocumentPath documentPath,
			@Nullable final DocumentQueryOrderByList orderBys,
			@NonNull final JSONDocumentOptions jsonOpts)
	{
		userSession.assertLoggedIn();

		return documentCollection.forRootDocumentReadonly(documentPath, rootDocument -> {
			final List<Document> documents;
			if (documentPath.isRootDocument())
			{
				documents = ImmutableList.of(rootDocument);
			}
			else if (documentPath.isAnyIncludedDocument())
			{
				documents = rootDocument.getIncludedDocuments(documentPath.getDetailId(), orderBys).toList();
			}
			else if (documentPath.isSingleIncludedDocument())
			{
				// IMPORTANT: in case the document was not found, don't fail but return empty.
				final Document document = rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId()).orElse(null);
				documents = document != null
						? ImmutableList.of(document)
						: ImmutableList.of();
			}
			else
			{
				documents = rootDocument.getIncludedDocuments(documentPath.getDetailId(), documentPath.getRowIds()).toList();
			}

			final Boolean hasComments = documentPath.isRootDocument() ? commentsService.hasComments(documentPath) : null;
			return JSONDocument.ofDocumentsList(documents, jsonOpts, hasComments);
		});
	}

	/**
	 * @param documentIdStr the string to identify the document to be returned. May also be {@link DocumentId#NEW_ID_STRING}, if a new record shall be created.
	 */
	@PatchMapping("/{windowId}/{documentId}")
	public JSONDocumentPatchResult patchRootDocument(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced,
			@RequestBody final List<JSONDocumentChangedEvent> events)
	{
		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowId.fromJson(windowIdStr))
				.setDocumentId(documentIdStr)
				.allowNewDocumentId()
				.build();

		return patchDocument(documentPath, advanced, events);
	}

	@PatchMapping("/{windowId}/{documentId}/{tabId}/{rowId}")
	public JSONDocumentPatchResult patchIncludedDocument(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("tabId") final String detailIdStr,
			@PathVariable("rowId") final String rowIdStr,
			@RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced,
			@RequestBody final List<JSONDocumentChangedEvent> events)
	{
		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowId.fromJson(windowIdStr))
				.setDocumentId(documentIdStr)
				.setDetailId(detailIdStr)
				.setRowId(rowIdStr)
				.allowNewRowId()
				.build();

		return patchDocument(documentPath, advanced, events);
	}

	private JSONDocumentPatchResult patchDocument(
			final DocumentPath documentPath,
			final boolean advanced,
			final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final JSONDocumentOptions jsonOpts = newJSONDocumentOptions()
				.showAdvancedFields(advanced)
				.build();

		return Execution.callInNewExecution("window.commit", () -> patchDocument0(documentPath, events, jsonOpts));
	}

	private JSONDocumentPatchResult patchDocument0(
			final DocumentPath documentPath,
			final List<JSONDocumentChangedEvent> events,
			final JSONDocumentOptions jsonOpts)
	{
		final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull();
		documentCollection.forDocumentWritable(
				documentPath,
				changesCollector,
				document -> {
					document.processValueChanges(events, REASON_Value_DirectSetFromCommitAPI);
					changesCollector.setPrimaryChange(document.getDocumentPath());
					return null; // void
				});

		// Extract and send websocket events
		final List<JSONDocument> jsonDocumentEvents = JSONDocument.ofEvents(changesCollector, jsonOpts);
		websocketPublisher.convertAndPublish(jsonDocumentEvents);

		return JSONDocumentPatchResult.builder()
				.documents(jsonDocumentEvents)
				.triggerActions(Execution.getCurrentFrontendTriggerActionsOrEmpty())
				.build();
	}

	@PostMapping("/{windowId}/{documentId}/duplicate")
	public JSONDocument duplicate(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced)
	{
		userSession.assertLoggedIn();

		final DocumentPath fromDocumentPath = DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), DocumentId.of(documentIdStr));

		final Document documentCopy = documentCollection.duplicateDocument(fromDocumentPath);
		final JSONDocumentOptions jsonOpts = newJSONDocumentOptions().showAdvancedFields(advanced).build();
		return JSONDocument.ofDocument(documentCopy, jsonOpts);
	}

	@DeleteMapping("/{windowId}/{documentId}")
	public List<JSONDocument> deleteRootDocument(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return deleteDocuments(ImmutableList.of(documentPath));
	}

	@DeleteMapping("/{windowId}")
	public List<JSONDocument> deleteRootDocumentsList(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@RequestParam(name = "ids") @ApiParam("comma separated documentIds") final String idsListStr
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final List<DocumentPath> documentPaths = DocumentPath.rootDocumentPathsList(windowId, idsListStr);
		if (documentPaths.isEmpty())
		{
			throw new IllegalArgumentException("No ids provided");
		}

		return deleteDocuments(documentPaths);
	}

	@DeleteMapping("/{windowId}/{documentId}/{tabId}/{rowId}")
	public List<JSONDocument> deleteIncludedDocument(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
			,
			@PathVariable("tabId") final String tabId
			//
			,
			@PathVariable("rowId") final String rowId
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowId);
		return deleteDocuments(ImmutableList.of(documentPath));
	}

	@DeleteMapping("/{windowId}/{documentId}/{tabId}")
	public List<JSONDocument> deleteIncludedDocumentsList(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
			,
			@PathVariable("tabId") final String tabId
			//
			,
			@RequestParam(name = "ids") @ApiParam("comma separated rowIds") final String rowIdsListStr
			//
	)
	{
		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowId.fromJson(windowIdStr))
				.setDocumentId(documentId)
				.setDetailId(tabId)
				.setRowIdsList(rowIdsListStr)
				.build();
		if (documentPath.getRowIds().isEmpty())
		{
			throw new IllegalArgumentException("No rowId(s) specified");
		}
		return deleteDocuments(ImmutableList.of(documentPath));
	}

	private List<JSONDocument> deleteDocuments(final List<DocumentPath> documentPaths)
	{
		userSession.assertLoggedIn();

		final JSONDocumentOptions jsonOpts = newJSONDocumentOptions()
				.showAdvancedFields(false)
				.build();

		return Execution.callInNewExecution("window.delete", () -> {
			final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull();
			documentCollection.deleteAll(documentPaths, changesCollector);
			return JSONDocument.ofEvents(changesCollector, jsonOpts);
		});
	}

	/**
	 * Typeahead for root document's field
	 */
	@GetMapping("/{windowId}/{documentId}/field/{fieldName}/typeahead")
	public JSONLookupValuesPage getDocumentFieldTypeahead(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
			,
			@PathVariable("fieldName") final String fieldName
			//
			,
			@RequestParam("query") final String query
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return getDocumentFieldTypeahead(documentPath, fieldName, query);
	}

	/**
	 * Typeahead for included document's field
	 */
	@GetMapping(value = "/{windowId}/{documentId}/{tabId}/{rowId}/field/{fieldName}/typeahead")
	public JSONLookupValuesPage getDocumentFieldTypeahead(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
			,
			@PathVariable("tabId") final String tabId
			//
			,
			@PathVariable("rowId") final String rowId
			//
			,
			@PathVariable("fieldName") final String fieldName
			//
			,
			@RequestParam("query") final String query
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowId);
		return getDocumentFieldTypeahead(documentPath, fieldName, query);
	}

	/**
	 * Typeahead: unified implementation
	 */
	private JSONLookupValuesPage getDocumentFieldTypeahead(
			final DocumentPath documentPath,
			final String fieldName,
			final String query)
	{
		userSession.assertLoggedIn();

		return documentCollection.forDocumentReadonly(documentPath, document -> document.getFieldLookupValuesForQuery(fieldName, query))
				.transform(page -> JSONLookupValuesPage.of(page, userSession.getAD_Language(), userSession.isAlwaysShowNewBPartner()));
	}

	private JSONLookupValuesList toJSONLookupValuesList(final LookupValuesList lookupValuesList)
	{
		return JSONLookupValuesList.ofLookupValuesList(lookupValuesList, userSession.getAD_Language());
	}

	@GetMapping("/{windowId}/{documentId}/field/{fieldName}/dropdown")
	public JSONLookupValuesList getDocumentFieldDropdown(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
			,
			@PathVariable("fieldName") final String fieldName
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return getDocumentFieldDropdown(documentPath, fieldName);
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}/field/{fieldName}/dropdown")
	public JSONLookupValuesList getDocumentFieldDropdown(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
			,
			@PathVariable("tabId") final String tabId
			//
			,
			@PathVariable("rowId") final String rowId
			//
			,
			@PathVariable("fieldName") final String fieldName
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowId);
		return getDocumentFieldDropdown(documentPath, fieldName);
	}

	private JSONLookupValuesList getDocumentFieldDropdown(
			final DocumentPath documentPath,
			final String fieldName)
	{
		userSession.assertLoggedIn();

		return documentCollection
				.forDocumentReadonly(
						documentPath,
						document -> document.getFieldLookupValues(fieldName))
				.transform(this::toJSONLookupValuesList);
	}

	@ApiOperation("field current value's window layout to zoom into")
	@GetMapping("/{windowId}/{documentId}/field/{fieldName}/zoomInto")
	public JSONZoomInto getDocumentFieldZoomInto(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
			,
			@PathVariable("fieldName") final String fieldName
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return getDocumentFieldZoomInto(documentPath, fieldName);
	}

	@ApiOperation("field current value's window layout to zoom into")
	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}/field/{fieldName}/zoomInto")
	public JSONZoomInto getDocumentFieldZoomInto(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentId
			//
			,
			@PathVariable("tabId") final String tabId
			//
			,
			@PathVariable("rowId") final String rowId
			//
			,
			@PathVariable("fieldName") final String fieldName
			//
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowId);
		return getDocumentFieldZoomInto(documentPath, fieldName);
	}

	private JSONZoomInto getDocumentFieldZoomInto(
			final DocumentPath documentPath,
			final String fieldName)
	{
		userSession.assertLoggedIn();

		final JSONDocumentPath jsonZoomIntoDocumentPath;
		final DocumentZoomIntoInfo zoomIntoInfo = documentCollection.forDocumentReadonly(documentPath, document -> getDocumentFieldZoomInto(document, fieldName));
		if (zoomIntoInfo == null)
		{
			throw new EntityNotFoundException("ZoomInto not supported")
					.setParameter("documentPath", documentPath)
					.setParameter("fieldName", fieldName);
		}
		else if (!zoomIntoInfo.isRecordIdPresent())
		{
			final WindowId windowId = documentCollection.getWindowId(zoomIntoInfo);
			jsonZoomIntoDocumentPath = JSONDocumentPath.newWindowRecord(windowId);
		}
		else
		{
			final DocumentPath zoomIntoDocumentPath = documentCollection.getDocumentPath(zoomIntoInfo);
			jsonZoomIntoDocumentPath = JSONDocumentPath.ofWindowDocumentPath(zoomIntoDocumentPath);
		}

		return JSONZoomInto.builder()
				.documentPath(jsonZoomIntoDocumentPath)
				.source(JSONDocumentPath.ofWindowDocumentPath(documentPath, fieldName))
				.build();
	}

	private DocumentZoomIntoInfo getDocumentFieldZoomInto(
			@NonNull final Document document,
			@NonNull final String fieldName)
	{
		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
		final DocumentFieldDescriptor singleKeyFieldDescriptor = entityDescriptor.getSingleIdFieldOrNull();

		final IDocumentFieldView field = document.getFieldView(fieldName);

		// Generic ZoomInto button
		if (field.getDescriptor().getWidgetType() == DocumentFieldWidgetType.ZoomIntoButton)
		{
			final ButtonFieldActionDescriptor buttonActionDescriptor = field.getDescriptor().getButtonActionDescriptor();
			final String zoomIntoTableIdFieldName = buttonActionDescriptor.getZoomIntoTableIdFieldName();

			final Integer adTableId = document.getFieldView(zoomIntoTableIdFieldName).getValueAs(Integer.class);
			if (adTableId == null || adTableId <= 0)
			{
				throw new EntityNotFoundException("Cannot fetch ZoomInto infos from a null value. No AD_Table_ID.")
						.setParameter("documentPath", document.getDocumentPath())
						.setParameter("fieldName", fieldName)
						.setParameter("zoomIntoTableIdFieldName", zoomIntoTableIdFieldName);
			}

			final Integer recordId = field.getValueAs(Integer.class);
			if (recordId == null)
			{
				throw new EntityNotFoundException("Cannot fetch ZoomInto infos from a null value. No Record_ID.")
						.setParameter("documentPath", document.getDocumentPath())
						.setParameter("fieldName", fieldName)
						.setParameter("zoomIntoTableIdFieldName", zoomIntoTableIdFieldName);
			}

			final String tableName = adTableDAO.retrieveTableName(adTableId);
			return DocumentZoomIntoInfo.of(tableName, recordId);
		}
		// label field
		else if (field.getDescriptor().getWidgetType() == DocumentFieldWidgetType.Labels)
		{
			final LabelsLookup lookup = LabelsLookup.cast(field.getDescriptor()
																  .getLookupDescriptor()
																  .orElseThrow(() -> new AdempiereException("Because the widget type is Labels, expect a LookupDescriptor")
																		  .setParameter("field", field)));
			final String labelsValueColumnName = lookup.getLabelsValueColumnName();

			if (labelsValueColumnName.endsWith("_ID"))
			{
				final ILookupDAO lookupDAO = Services.get(ILookupDAO.class);

				final ReferenceId labelsValueReferenceId = lookup.getLabelsValueReferenceId();
				final TableRefInfo tableRefInfo = labelsValueReferenceId != null
						? lookupDAO.retrieveTableRefInfo(labelsValueReferenceId.getRepoId())
						: lookupDAO.retrieveTableDirectRefInfo(labelsValueColumnName);

				return DocumentZoomIntoInfo.of(tableRefInfo.getTableName(), -1);
			}
			else
			{
				return DocumentZoomIntoInfo.of(lookup.getLabelsTableName(), -1);
			}
		}
		// Key Field
		else if (singleKeyFieldDescriptor != null && singleKeyFieldDescriptor.getFieldName().equals(fieldName))
		{
			// Allow zooming into key column. It shall open precisely this record in a new window.
			// (see https://github.com/metasfresh/metasfresh/issues/1687 to understand the use-case)
			final String tableName = entityDescriptor.getTableName();
			final int recordId = document.getDocumentIdAsInt();
			return DocumentZoomIntoInfo.of(tableName, recordId);
		}
		// Regular lookup value
		else
		{
			return field.getZoomIntoInfo()
					.overrideWindowIdIfPossible(customizedWindowInfoMapRepository.get());
		}
	}

	@GetMapping("/{windowId}/{documentId}/actions")
	public JSONDocumentActionsList getDocumentActions(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@RequestParam(name = "selectedTabId", required = false) final String selectedTabIdStr,
			@RequestParam(name = "selectedRowIds", required = false) final String selectedRowIdsAsStr,
			@RequestParam(name = "disabled", defaultValue = "false") final boolean returnDisabled)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath rootDocumentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);
		final DetailId selectedTabId = DetailId.fromJson(selectedTabIdStr);
		final DocumentIdsSelection selectedRowIds = DocumentIdsSelection.ofCommaSeparatedString(selectedRowIdsAsStr);
		final ImmutableSet<TableRecordReference> selectedIncludedRecords = getTableRecordReferences(rootDocumentPath, selectedTabId, selectedRowIds);

		return getDocumentActions(
				rootDocumentPath,
				selectedTabId,
				selectedIncludedRecords,
				returnDisabled,
				DisplayPlace.SingleDocumentActionsMenu);
	}

	private ImmutableSet<TableRecordReference> getTableRecordReferences(
			@NonNull final DocumentPath rootDocumentPath,
			@Nullable final DetailId tabId,
			@NonNull final DocumentIdsSelection selectedRowIds)
	{
		if (selectedRowIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		if (tabId == null)
		{
			throw new AdempiereException("selectedTabId shall be specified when selectedRowIds is set");
		}

		return selectedRowIds.stream()
				.filter(DocumentId::isInt) // consider only int keys because only those can be converted to TableRecordReference
				.map(rowId -> rootDocumentPath.createChildPath(tabId, rowId))
				.map(documentCollection::getTableRecordReference)
				.collect(ImmutableSet.toImmutableSet());
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/topActions")
	public JSONDocumentActionsList getIncludedTabTopActions(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("tabId") final String tabIdStr)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath rootDocumentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);
		final DetailId selectedTabId = DetailId.fromJson(tabIdStr);
		final Set<TableRecordReference> selectedIncludedRecords = ImmutableSet.of();
		final boolean returnDisabled = false;

		return getDocumentActions(
				rootDocumentPath,
				selectedTabId,
				selectedIncludedRecords,
				returnDisabled,
				DisplayPlace.IncludedTabTopActionsMenu);
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}/actions")
	public JSONDocumentActionsList getIncludedDocumentActions(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("tabId") final String tabIdStr,
			@PathVariable("rowId") final String rowIdStr,
			@RequestParam(name = "disabled", defaultValue = "false") final boolean returnDisabled)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DetailId selectedTabId = DetailId.fromJson(tabIdStr);

		final DocumentPath includedDocumentPath = DocumentPath.includedDocumentPath(windowId, documentIdStr, selectedTabId.toJson(), rowIdStr);
		final Set<TableRecordReference> selectedIncludedRecords = ImmutableSet.of();
		return getDocumentActions(
				includedDocumentPath,
				selectedTabId,
				selectedIncludedRecords,
				returnDisabled,
				DisplayPlace.SingleDocumentActionsMenu);
	}

	private JSONDocumentActionsList getDocumentActions(
			@NonNull final DocumentPath documentPath,
			final DetailId selectedTabId,
			@NonNull final Set<TableRecordReference> selectedIncludedRecords,
			final boolean returnDisabled,
			@NonNull final DisplayPlace displayPlace)
	{
		userSession.assertLoggedIn();

		final Predicate<WebuiRelatedProcessDescriptor> isEnabled = returnDisabled
				? WebuiRelatedProcessDescriptor::isEnabledOrNotSilent
				: WebuiRelatedProcessDescriptor::isEnabled;

		return documentCollection.forDocumentReadonly(documentPath, document -> {
			final DocumentPreconditionsAsContext preconditionsContext = DocumentPreconditionsAsContext.builder()
					.document(document)
					.selectedTabId(selectedTabId)
					.selectedIncludedRecords(selectedIncludedRecords)
					.displayPlace(displayPlace)
					.build();

			return processRestController.streamDocumentRelatedProcesses(preconditionsContext)
					.filter(descriptor -> descriptor.isDisplayedOn(displayPlace))
					.filter(isEnabled)
					.collect(JSONDocumentActionsList.collect(newJSONOptions().build()));
		});
	}

	/**
	 * task https://github.com/metasfresh/metasfresh/issues/1090
	 */
	@GetMapping("/{windowId}/{documentId}/processNewRecord")
	public int processRecord(
			@PathVariable("windowId") final String windowIdStr
			//
			,
			@PathVariable("documentId") final String documentIdStr
			//
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);

		final IDocumentChangesCollector changesCollector = NullDocumentChangesCollector.instance;
		return Execution.callInNewExecution("window.processTemplate", () -> documentCollection.forDocumentWritable(documentPath, changesCollector, document -> {
			document.saveIfValidAndHasChanges();
			if (document.hasChangesRecursivelly())
			{
				throw new AdempiereException("Not saved");
			}

			final NewRecordContext newRecordContext = NewRecordContext.builder()
					.loginOrgId(userSession.getOrgId())
					.loggedUserId(userSession.getLoggedUserId())
					.loginLanguage(userSession.getAD_Language())
					.build();

			return newRecordDescriptorsProvider.getNewRecordDescriptor(document.getEntityDescriptor())
					.getProcessor()
					.processNewRecordDocument(document,
											  newRecordContext);
		}));
	}

	@PostMapping("/{windowId}/{documentId}/field/{fieldName}/advSearchResult")
	public List<JSONDocument> advSearchResult(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("fieldName") final String fieldName,
			@RequestBody final JSONDocumentAdvSearch body
	)
	{
		userSession.assertLoggedIn();

		final String advSearchWindowIdStr = body.getAdvSearchWindowId();
		final String selectionIdStr = body.getSelectedId();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);

		return Execution.callInNewExecution("window.advSearch", () -> advSearchResult0(WindowId.fromJson(advSearchWindowIdStr), selectionIdStr, documentPath, fieldName));
	}

	private List<JSONDocument> advSearchResult0(final WindowId windowId, final String selectionIdStr, final DocumentPath documentPath, final String fieldName)
	{
		final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull();
		final JSONDocumentOptions jsonOpts = newJSONDocumentOptions().build();

		documentCollection.forDocumentWritable(documentPath, changesCollector, document -> {
			advancedSearchDescriptorsProvider.getAdvancedSearchDescriptor(windowId)
					.getProcessor()
					.processSelection(windowId, document, fieldName, selectionIdStr);
			return null;
		});
		final List<JSONDocument> jsonDocumentEvents = JSONDocument.ofEvents(changesCollector, jsonOpts);
		websocketPublisher.convertAndPublish(jsonDocumentEvents);
		return jsonDocumentEvents;
	}

	@PostMapping("/{windowId}/{documentId}/discardChanges")
	public void discardChanges(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr)
	{
		userSession.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), DocumentId.of(documentIdStr));
		documentCollection.invalidateRootDocument(documentPath);
	}

	@PostMapping("/{windowId}/{documentId}/{tabId}/{rowId}/discardChanges")
	public void discardChanges(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("tabId") @SuppressWarnings("unused") final String tabIdStr_NOT_USED,
			@PathVariable("rowId") @SuppressWarnings("unused") final String rowIdStr_NOT_USED)
	{
		userSession.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), DocumentId.of(documentIdStr));

		// For now it's OK if we invalidate the whole root document
		documentCollection.invalidateRootDocument(documentPath);
	}

	@GetMapping("/{windowId}/{documentId}/changeLog")
	public JSONDocumentChangeLog getDocumentChangeLog(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);
		return documentChangeLogService.getJSONDocumentChangeLog(documentPath);
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}/changeLog")
	public JSONDocumentChangeLog getDocumentChangeLog(
			@PathVariable("windowId") final String windowIdStr,
			@PathVariable("documentId") final String documentIdStr,
			@PathVariable("tabId") final String tabIdStr,
			@PathVariable("rowId") final String rowIdStr)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentId documentId = DocumentId.of(documentIdStr);
		final DetailId tabId = DetailId.fromJson(tabIdStr);
		final DocumentId rowId = DocumentId.of(rowIdStr);
		final DocumentPath documentPath = DocumentPath.singleWindowDocumentPath(windowId, documentId, tabId, rowId);
		
		return documentChangeLogService.getJSONDocumentChangeLog(documentPath);
	}

	@GetMapping("/health")
	public JsonWindowsHealthResponse healthCheck(
			@RequestParam(name = "windowIds", required = false) final String windowIdsCommaSeparated
	)
	{
		final DocumentDescriptorFactory documentDescriptorFactory = documentCollection.getDocumentDescriptorFactory();
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		final ImmutableSet<AdWindowId> skipAdWindowIds = ImmutableSet.of(
				AdWindowId.ofRepoId(540371), // Picking Tray Clearing - placeholder window
				AdWindowId.ofRepoId(540674), // Shipment Schedule Editor - placeholder window
				AdWindowId.ofRepoId(540759), // Payment Allocation - placeholder window
				AdWindowId.ofRepoId(540485) // Picking Terminal (v2) - placeholder window
		);

		final ImmutableSet<AdWindowId> onlyAdWindowIds = RepoIdAwares.ofCommaSeparatedSet(windowIdsCommaSeparated, AdWindowId.class);
		final ImmutableSet<AdWindowId> allAdWidowIds = adWindowDAO.retrieveAllActiveAdWindowIds();
		final ImmutableSet<AdWindowId> adWindowIds = !onlyAdWindowIds.isEmpty() ? onlyAdWindowIds : allAdWidowIds;

		final ArrayList<JsonWindowsHealthResponse.Entry> errors = new ArrayList<>();
		final int countTotal = adWindowIds.size();
		int countCurrent = 0;
		final Stopwatch stopwatch = Stopwatch.createStarted();
		for (final AdWindowId adWindowId : adWindowIds)
		{
			countCurrent++;

			if (skipAdWindowIds.contains(adWindowId))
			{
				continue;
			}

			final WindowId windowId = WindowId.of(adWindowId);
			try
			{
				if (!allAdWidowIds.contains(adWindowId))
				{
					throw new AdempiereException("Not an existing/active window");
				}

				documentDescriptorFactory.invalidateForWindow(windowId);
				final DocumentDescriptor documentDescriptor = documentDescriptorFactory.getDocumentDescriptor(windowId);
				documentDescriptorFactory.invalidateForWindow(windowId);

				final String windowName = documentDescriptor.getEntityDescriptor().getCaption().translate(adLanguage);
				logger.info("testWindows [{}/{}] Window `{}` ({}) is OK", countCurrent, countTotal, windowName, windowId);
			}
			catch (final Exception ex)
			{
				final String windowName = adWindowDAO.retrieveWindowName(adWindowId).translate(adLanguage);
				logger.info("testWindows [{}/{}] Window `{}` ({}) is NOK: {}", countCurrent, countTotal, windowName, windowId, ex.getLocalizedMessage());

				final Throwable cause = DocumentLayoutBuildException.extractCause(ex);
				errors.add(JsonWindowsHealthResponse.Entry.builder()
						.windowId(windowId)
						.windowName(windowName)
						.error(JsonErrors.ofThrowable(cause, adLanguage))
						.build());
			}
		}

		stopwatch.stop();

		return JsonWindowsHealthResponse.builder()
				.took(stopwatch.toString())
				.countTotal(countTotal)
				.errors(errors)
				.build();
	}
}
