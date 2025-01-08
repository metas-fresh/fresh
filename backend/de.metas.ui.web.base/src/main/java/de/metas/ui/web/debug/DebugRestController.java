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

package de.metas.ui.web.debug;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.rest.JsonCacheResetRequest;
import de.metas.cache.rest.JsonCacheResetResponse;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.notification.UserNotificationRequest.UserNotificationRequestBuilder;
import de.metas.notification.UserNotificationTargetType;
import de.metas.ui.web.admin.CacheRestController;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.comments.ViewRowCommentsSummary;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.kpi.data.KPIDataProvider;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowOverridesHelper;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.websocket.WebsocketConfig;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized") })
@RestController
@RequestMapping(value = DebugRestController.ENDPOINT)
@RequiredArgsConstructor
public class DebugRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/debug";

<<<<<<< HEAD
	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentCollection;

	@Autowired
	private MenuTreeRepository menuTreeRepo;

	@Autowired
	@Lazy
	private IViewsRepository viewsRepo;

	@Autowired
	@Lazy
	private SqlViewFactory sqlViewFactory;

	@Autowired
	@Lazy
	private ProcessRestController processesController;

	@Autowired
	@Lazy
	private ObjectMapper sharedJsonObjectMapper;
=======
	@NonNull private final UserSession userSession;
	@NonNull private final DocumentCollection documentCollection;
	@NonNull @Lazy private final IViewsRepository viewsRepo;
	@NonNull @Lazy private final SqlViewFactory sqlViewFactory;
	@NonNull private final CacheRestController cacheRestController;
	@NonNull @Lazy private final ObjectMapper sharedJsonObjectMapper;
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.of(userSession);
	}

<<<<<<< HEAD
	@ApiResponses(value = { @ApiResponse(code = 200, message = "cache reset done") })
	@RequestMapping(value = "/cacheReset", method = RequestMethod.GET)
	public JSONCacheResetResult cacheReset(
			@RequestParam(name = "forgetNotSavedDocuments", defaultValue = "false", required = false) final boolean forgetNotSavedDocuments)
=======
	@GetMapping("/cacheReset")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "cache reset done") })
	@Deprecated
	public JsonCacheResetResponse cacheReset(
			@RequestParam(name = CacheRestController.CACHE_RESET_PARAM_forgetNotSavedDocuments, defaultValue = "false", required = false) final boolean forgetNotSavedDocuments)
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
	{
		userSession.assertLoggedIn();
		return cacheRestController.reset(new JsonCacheResetRequest()
				.setValue(CacheRestController.CACHE_RESET_PARAM_forgetNotSavedDocuments, forgetNotSavedDocuments));
	}

	// NOTE: using String parameter because when using boolean parameter, we get following error in swagger-ui:
	// swagger-ui.min.js:10 Uncaught TypeError: Cannot read property 'toLowerCase' of undefined
	@RequestMapping(value = "/showColumnNamesForCaption", method = RequestMethod.PUT)
	public void setShowColumnNamesForCaption(@RequestBody final String showColumnNamesForCaptionStr)
	{
		userSession.assertLoggedIn();

		final Boolean showColumnNamesForCaption = DisplayType.toBoolean(showColumnNamesForCaptionStr, null);
		if (showColumnNamesForCaption == null)
		{
			throw new AdempiereException("Invalid boolean value: `" + showColumnNamesForCaptionStr + "`");
		}
		userSession.setShowColumnNamesForCaption(showColumnNamesForCaption);

		cacheRestController.reset(
				new JsonCacheResetRequest()
						.setValue(CacheRestController.CACHE_RESET_PARAM_forgetNotSavedDocuments, true)
		);
	}

	@RequestMapping(value = "/allowDeprecatedRestAPI", method = RequestMethod.PUT)
	public void setAllowDeprecatedRestAPI(@RequestBody final String allowDeprecatedRestAPI)
	{
		userSession.assertLoggedIn();

		userSession.setAllowDeprecatedRestAPI(DisplayType.toBoolean(allowDeprecatedRestAPI));
	}

	@RequestMapping(value = "/disableDeprecatedRestAPI", method = RequestMethod.GET)
	public boolean isAllowDeprecatedRestAPI()
	{
		userSession.assertLoggedIn();

		return userSession.isAllowDeprecatedRestAPI();
	}

	@RequestMapping(value = "/debugProtocol", method = RequestMethod.GET)
	public void setDebugProtocol(@RequestParam("enabled") final boolean enabled)
	{
		userSession.assertLoggedIn();

		WindowConstants.setProtocolDebugging(enabled);
	}

	@RequestMapping(value = "/views/list", method = RequestMethod.GET)
	public List<JSONViewResult> getViewsList()
	{
		userSession.assertLoggedIn();

		final JSONOptions jsonOpts = newJSONOptions();

		return viewsRepo.getViews()
				.stream()
				.map(ViewResult::ofView)
				.map(viewResult -> JSONViewResult.of(viewResult, ViewRowOverridesHelper.NULL, jsonOpts, ViewRowCommentsSummary.EMPTY))
				.collect(GuavaCollectors.toImmutableList());
	}

	@PostMapping("/viewDefaultProfile/{windowId}")
	public void setDefaultViewProfile(@PathVariable("windowId") final String windowIdStr, @RequestBody final String profileIdStr)
	{
		userSession.assertLoggedIn();

		sqlViewFactory.setDefaultProfileId(WindowId.fromJson(windowIdStr), ViewProfileId.fromJson(profileIdStr));
	}

<<<<<<< HEAD
	@RequestMapping(value = "/lookups/cacheStats", method = RequestMethod.GET)
	public List<String> getLookupCacheStats()
	{
		userSession.assertLoggedIn();

		return LookupDataSourceFactory.instance.getCacheStats()
				.stream()
				.map(CCache.CCacheStats::toString)
				.collect(GuavaCollectors.toImmutableList());
	}

=======
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
	@RequestMapping(value = "/eventBus/postEvent", method = RequestMethod.GET)
	public void postEvent(
			@RequestParam(name = "topicName", defaultValue = "de.metas.event.GeneralNotifications") final String topicName //
			//, @RequestParam(name = "message", defaultValue = "test message") final String message//
			, @RequestParam(name = "toUserId", defaultValue = "-1") final int toUserId//
			, @RequestParam(name = "important", defaultValue = "false") final boolean important//
			//
			, @RequestParam(name = "targetType", required = false) final String targetTypeStr//
			, @RequestParam(name = "targetDocumentType", required = false, defaultValue = "143") final String targetDocumentType//
			, @RequestParam(name = "targetDocumentId", required = false) final String targetDocumentId//
	)
	{
		userSession.assertLoggedIn();

		final Topic topic = Topic.builder()
				.name(topicName)
				.type(Type.LOCAL)
				.build();

		final UserNotificationRequestBuilder request = UserNotificationRequest.builder()
				.topic(topic)
				.recipientUserId(UserId.ofRepoId(toUserId))
				.important(important);

		final UserNotificationTargetType targetType = Check.isEmpty(targetTypeStr) ? null : UserNotificationTargetType.forJsonValue(targetTypeStr);
		if (targetType == UserNotificationTargetType.Window)
		{
			final String targetTableName = documentCollection.getDocumentDescriptorFactory()
					.getDocumentDescriptor(WindowId.fromJson(targetDocumentType))
					.getEntityDescriptor()
					.getTableName();

			final TableRecordReference targetRecord = TableRecordReference.of(targetTableName, Integer.parseInt(targetDocumentId));
			request.targetAction(TargetRecordAction.of(targetRecord));
		}

		Services.get(INotificationBL.class).send(request.build());
	}

	@GetMapping("/logger/{loggerName}/_getUpToRoot")
	public List<Map<String, Object>> getLoggersUpToRoot(@PathVariable("loggerName") final String loggerName)
	{
		userSession.assertLoggedIn();

		final Logger logger = LogManager.getLogger(loggerName);
		if (logger == null)
		{
			throw new EntityNotFoundException("No logger found for " + loggerName);
		}

		final List<Map<String, Object>> loggerInfos = new ArrayList<>();
		//
		LogManager.forAllLevelsUpToRoot(logger, currentLogger -> {
			final Map<String, Object> info = new HashMap<>();
			info.put("name", currentLogger.getName());
			info.put("id", System.identityHashCode(currentLogger));
			if (currentLogger instanceof ch.qos.logback.classic.Logger)
			{
				final ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger)currentLogger;
				final Level level = logbackLogger.getLevel();
				final Level effectiveLevel = logbackLogger.getEffectiveLevel();
				info.put("level", level == null ? null : level.toString());
				info.put("level-effective", effectiveLevel == null ? null : effectiveLevel.toString());
			}
			else
			{
				info.put("warning", "unknown level for logger object " + currentLogger + " (" + currentLogger.getClass() + ")");
			}

			loggerInfos.add(info);
		});
		//
		return loggerInfos;
	}

	public enum LoggingModule
	{
		websockets_and_invalidation(
				WebsocketConfig.class.getPackage().getName(),
				de.metas.ui.web.window.invalidation.DocumentCacheInvalidationDispatcher.class.getName()),
		view(de.metas.ui.web.view.IView.class.getPackage().getName()),
		cache(
				de.metas.cache.CCache.class.getName(),
				de.metas.cache.CacheMgt.class.getName(),
				de.metas.cache.model.IModelCacheService.class.getName() // model caching
		),
		model_interceptors(
				org.compiere.model.ModelValidationEngine.class.getName(),
				org.adempiere.ad.modelvalidator.IModelValidationEngine.class.getPackage().getName() // for annotated interceptors etc
		),
		dashboard(
				"de.metas.ui.web.dashboard",
				KPIDataProvider.class.getPackage().getName()
		),
		elasticsearch(
				"de.metas.fulltextsearch",
				de.metas.ui.web.document.filter.provider.fullTextSearch.FTSDocumentFilterConverter.class.getPackage().getName()
		),
		;

		private final Set<String> loggerNames;

		LoggingModule(final String... loggerNames)
		{
			this.loggerNames = ImmutableSet.copyOf(loggerNames);
		}

		// @JsonValue
		public Set<String> getLoggerNames()
		{
			return loggerNames;
		}
	}

	@GetMapping("/logger/_setLevel/{level}")
	public Set<String> setLoggerLevel(
			@RequestParam(name = "module", required = false) final LoggingModule module //
			, @RequestParam(name = "loggerName", required = false) final String loggerName //
			, @PathVariable("level") final String levelStr //
	)
	{
		userSession.assertLoggedIn();

		//
		// Get Level to set
		final Level level;
		if (Check.isBlank(levelStr))
		{
			throw new AdempiereException("Please provide a `level`");
		}
		else
		{
			level = LogManager.asLogbackLevel(levelStr);
			if (level == null)
			{
				throw new AdempiereException("level is not valid: " + levelStr);
			}
		}

		//
		// Get logger names
		final Set<String> loggerNamesEffective = new LinkedHashSet<>();
		if (module != null)
		{
			loggerNamesEffective.addAll(module.getLoggerNames());
		}
		if (!Check.isEmpty(loggerName, true))
		{
			loggerNamesEffective.add(loggerName.trim());
		}

		//
		// Set level to effective logger names
		for (final String loggerNameEffective : loggerNamesEffective)
		{
			final Logger logger = LogManager.getLogger(loggerNameEffective);
			if (logger == null)
			{
				throw new EntityNotFoundException("No logger found for " + loggerNameEffective);
			}

			final boolean set = LogManager.setLoggerLevel(logger, level);
			if (!set)
			{
				throw new IllegalStateException("For some reason " + logger + " could not be set to level " + level);
			}
		}

		return loggerNamesEffective;
	}

	@GetMapping("http.cache.maxAge")
	public Map<String, Object> setHttpCacheMaxAge(@RequestParam("value") @ApiParam("Cache-control's max age in seconds") final int httpCacheMaxAge)
	{
		userSession.assertLoggedIn();

		final long httpCacheMaxAgeOld = userSession.getHttpCacheMaxAge();
		userSession.setHttpCacheMaxAge(httpCacheMaxAge);
		return ImmutableMap.of("value", httpCacheMaxAge, "valueOld", httpCacheMaxAgeOld);
	}

	@GetMapping("http.use.AcceptLanguage")
	public Map<String, Object> setUseHttpAcceptLanguage(@RequestParam("value") @ApiParam("Cache-control's max age in seconds") final boolean useHttpAcceptLanguage)
	{
		userSession.assertLoggedIn();

		final boolean useHttpAcceptLanguageOld = userSession.isUseHttpAcceptLanguage();
		userSession.setUseHttpAcceptLanguage(useHttpAcceptLanguage);
		return ImmutableMap.of("value", useHttpAcceptLanguage, "valueOld", useHttpAcceptLanguageOld);
	}

	@PostMapping("/view/{viewId}/deleteRows")
	public String viewDeleteRowIds(
			@PathVariable("viewId") final String viewIdStr,
			@RequestParam("ids") final String rowIdsStr)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);
		final DocumentIdsSelection rowIds = DocumentIdsSelection.ofCommaSeparatedString(rowIdsStr);

		//
		// Delete from DB selection
		final String sql = "DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(viewId.getViewId())
				+ " AND " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey1 + "=" + DB.buildSqlList(rowIds.toIntSet());
		final int countDeleted = DB.executeUpdateEx(sql, ITrx.TRXNAME_None);

		//
		// Clear view's cache
		final IView view = viewsRepo.getView(viewId);
		rowIds.forEach(view::invalidateRowById);

		//
		// Notify
		ViewChangesCollector.getCurrentOrAutoflush().collectRowsChanged(view, rowIds);

		return "Deleted " + countDeleted + " rows";
	}

	@GetMapping("/changeJsonEngineCofiguration")
	public void changeJsonEngineConfiguration(
			@RequestParam(value = "failOnUnknownProperties", required = false) final Boolean failOnUnknownProperties)
	{
		if (failOnUnknownProperties != null)
		{
			sharedJsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties);
		}
	}

	@GetMapping("/conext")
	public Map<String, String> getContext()
	{
		userSession.assertLoggedIn();

		final LinkedHashMap<String, String> map = new LinkedHashMap<>();

		final Properties ctx = Env.getCtx();

		final ArrayList<String> keys = new ArrayList<>(ctx.stringPropertyNames());
		Collections.sort(keys);

		for (final String key : keys)
		{
			final String value = ctx.getProperty(key);
			map.put(key, value);
		}

		return map;
	}

	@GetMapping("/stressTest/massCacheInvalidation")
	public void stressTest_massCacheInvalidation(
			@RequestParam(name = "tableName") @NonNull final String tableName,
			@RequestParam(name = "eventsCount", defaultValue = "1000") final int eventsCount,
			@RequestParam(name = "batchSize", defaultValue = "10") final int batchSize)
	{
		userSession.assertLoggedIn();
		Check.assumeGreaterThanZero(eventsCount, "eventsCount");
		Check.assumeGreaterThanZero(batchSize, "batchSize");

		final CacheMgt cacheMgt = CacheMgt.get();

		final int tableSize = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT COUNT(1) FROM " + tableName);
		if (tableSize <= 0)
		{
			throw new AdempiereException("Table " + tableName + " is empty");
		}
		if (tableSize < batchSize)
		{
			throw new AdempiereException("Table size(" + tableSize + ") shall be bigger than batch size(" + batchSize + ")");
		}

		int countEventsGenerated = 0;

		final ArrayList<CacheInvalidateRequest> buffer = new ArrayList<>(batchSize);

		while (countEventsGenerated <= eventsCount)
		{
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement("SELECT " + tableName + "_ID FROM " + tableName + " ORDER BY " + tableName + "_ID", ITrx.TRXNAME_None);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					if (buffer.size() >= batchSize)
					{
						final Stopwatch stopwatch = Stopwatch.createStarted();
						cacheMgt.reset(CacheInvalidateMultiRequest.of(buffer));
						stopwatch.stop();

						// logger.info("Sent a CacheInvalidateMultiRequest of {} events. So far we sent {} of {}. It took {}.",
						// 		buffer.size(), countEventsGenerated, eventsCount, stopwatch);

						buffer.clear();
					}

					final int recordId = rs.getInt(1);
					buffer.add(CacheInvalidateRequest.rootRecord(tableName, recordId));
					countEventsGenerated++;
				}
			}
			catch (final SQLException ex)
			{
				throw new DBException(ex);
			}
			finally
			{
				DB.close(rs, pstmt);
			}
		}

		if (buffer.size() >= batchSize)
		{
			cacheMgt.reset(CacheInvalidateMultiRequest.of(buffer));
			buffer.clear();
		}
	}
}
