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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.apirequest.ApiAuditLoggable;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.audit.apirequest.common.HttpHeadersWrapper;
import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.config.ApiAuditConfigRepository;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.audit.apirequest.request.Status;
import de.metas.audit.apirequest.request.log.ApiAuditRequestLogDAO;
import de.metas.audit.apirequest.response.ApiResponseAudit;
import de.metas.audit.apirequest.response.ApiResponseAuditRepository;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.service.CompositeDataAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.ui_trace.UITraceExternalId;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserGroupUserAssignment;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.web.audit.dto.ApiRequest;
import de.metas.util.web.audit.dto.ApiRequestMapper;
import de.metas.util.web.audit.dto.ApiResponse;
import de.metas.util.web.audit.dto.ApiResponseMapper;
import de.metas.util.web.audit.dto.CachedBodyHttpServletRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.util.Env;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.ContentCachingResponseWrapper;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_EXTERNALSYSTEM_CONFIG_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_PINSTANCE_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class ApiAuditService
{
	/**
	 * This header is used in a http-request to indicate that this request is a repeat and there is already an {@code API_Request_Audit} record in metasfresh.
	 */
	private static final String API_REQUEST_HEADER_EXISTING_AUDIT_ID = "X-ApiFilter-Request-ID";

	/**
	 * This header is used in a http-request to indicate that metasfresh shall process the request in an async matter and return just an API_Request_Audit_ID.
	 */
	private static final String API_ASYNC_HEADER = "X-Api-Async";

	private static final String API_UI_Trace_ID = "X-Ui-Trace-Id";

	/**
	 * If the Response is not wrapped in a {@link JsonApiResponse} with a dedicated property for the request audit, then this response header contains the respective {@code API_Request_Audit_ID}.
	 */
	public static final String API_RESPONSE_HEADER_REQUEST_AUDIT_ID = "X-Api-Request-Audit-ID";

	private static final AdMessageKey MSG_SUCCESSFUL_API_INVOCATION =
			AdMessageKey.of("de.metas.util.web.audit.successful_invocation");

	private static final AdMessageKey MSG_API_INVOCATION_FAILED =
			AdMessageKey.of("de.metas.util.web.audit.invocation_failed");

	private static final String CFG_INTERNAL_HOST_NAME = "de.metas.util.web.audit.AppServerInternalHostName";
	/**
	 * this default works if you run both app and webapi locally. In our usual stack, it should be set to {@code "app"}.
	 */
	private static final String CFG_INTERNAL_HOST_NAME_DEFAULT = "localhost";

	public static final String CFG_INTERNAL_PORT = "de.metas.util.web.audit.AppServerInternalPort";
	private static final int CFG_INTERNAL_PORT_DEFAULT = 8282;

	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	private final ApiAuditConfigRepository apiAuditConfigRepository;
	private final ApiRequestAuditRepository apiRequestAuditRepository;
	private final ApiResponseAuditRepository apiResponseAuditRepository;
	private final ApiAuditRequestLogDAO apiAuditRequestLogDAO;
	private final UserGroupRepository userGroupRepository;
	private final CompositeDataAuditService compositeDataAuditService;

	private final WebClient webClient;
	private final ConcurrentHashMap<UserId, HttpCallScheduler> callerId2Scheduler;
	private final ObjectMapper objectMapper;

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public ApiAuditService(
			@NonNull final ApiAuditConfigRepository apiAuditConfigRepository,
			@NonNull final ApiRequestAuditRepository apiRequestAuditRepository,
			@NonNull final ApiResponseAuditRepository apiResponseAuditRepository,
			@NonNull final ApiAuditRequestLogDAO apiAuditRequestLogDAO,
			@NonNull final UserGroupRepository userGroupRepository,
			@NonNull final CompositeDataAuditService compositeDataAuditService)
	{
		this.apiAuditConfigRepository = apiAuditConfigRepository;
		this.apiRequestAuditRepository = apiRequestAuditRepository;
		this.apiResponseAuditRepository = apiResponseAuditRepository;
		this.apiAuditRequestLogDAO = apiAuditRequestLogDAO;
		this.userGroupRepository = userGroupRepository;
		this.compositeDataAuditService = compositeDataAuditService;

		// allow up to 50MB large results in API responses; thx to https://stackoverflow.com/a/62543241/1012103
		this.webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
						.codecs(configurer -> configurer
								.defaultCodecs()
								.maxInMemorySize(50 * 1024 * 1024))
						.build())
				.build();
		this.callerId2Scheduler = new ConcurrentHashMap<>();
		this.objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	public boolean wasAlreadyFiltered(@NonNull final HttpServletRequest httpServletRequest)
	{
		return httpServletRequest.getHeader(API_REQUEST_HEADER_EXISTING_AUDIT_ID) != null;
	}

	@NonNull
	public Optional<ApiRequestAuditId> extractApiRequestAuditId(@NonNull final HttpServletRequest httpServletRequest)
	{
		return Optional.ofNullable(httpServletRequest.getHeader(API_REQUEST_HEADER_EXISTING_AUDIT_ID))
				.map(requestId -> ApiRequestAuditId.ofRepoId(NumberUtils.asInt(requestId, -1)));
	}

	public ApiAuditLoggable createLogger(@NonNull final ApiRequestAuditId apiRequestAuditId, @NonNull final UserId userId)
	{
		return ApiAuditLoggable.builder()
				.clientId(Env.getClientId())
				.userId(userId)
				.apiRequestAuditId(apiRequestAuditId)
				.apiAuditRequestLogDAO(apiAuditRequestLogDAO)
				.bufferSize(100)
				.build();
	}

	/**
	 * Get the matching config with the smallest SeqNo.
	 */
	@NonNull
	public Optional<ApiAuditConfig> getMatchingAuditConfig(@NonNull final HttpServletRequest request)
	{
		final String fullPath = ApiRequestMapper.getFullPath(request);
		final String httpMethod = request.getMethod();

		if (Check.isBlank(fullPath) || Check.isBlank(httpMethod))
		{
			return Optional.empty();
		}

		final OrgId orgId = Env.getOrgId();
		final ImmutableList<ApiAuditConfig> apiAuditConfigs = apiAuditConfigRepository.getActiveConfigsByOrgId(orgId);

		return apiAuditConfigs.stream()
				.filter(config -> config.matchesRequest(fullPath, httpMethod))
				.min(Comparator.comparingInt(ApiAuditConfig::getSeqNo));
	}

	public void processRequest(
			@NonNull final FilterChain filterChain,
			@NonNull final HttpServletRequest request,
			@NonNull final HttpServletResponse response,
			@NonNull final ApiAuditConfig apiAuditConfig) throws IOException, ServletException
	{
		final boolean processRequestAsync = this.shouldProcessRequestAsync(request, apiAuditConfig);

		if (processRequestAsync)
		{
			processRequestAsync(request, response, apiAuditConfig);
		}
		else
		{
			processRequestSync(filterChain, request, response, apiAuditConfig);
		}
	}

	/**
	 * Executes the request that is wrapped in the given {@code apiRequestAudit} using the spring {@link WebClient}.
	 * <p>
	 * Note that it makes the call against {@code http://localhost + <our-current-port> + }{@link ApiRequestAudit#getRequestURI()}
	 * and not to the URL that we are called with from the outside (i.e. {@link ApiRequestAudit#getPath()}).
	 * <p>
	 * This is to avoid problems with our reverse proxy that would otherwise be invoked once again, but with an already rewritten URL.
	 */
	@NonNull
	public ApiResponse executeHttpCall(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		if (apiRequestAudit.getMethod() == null || apiRequestAudit.getPath() == null)
		{
			throw new AdempiereException("Missing essential data from the given ApiRequestAudit!")
					.setParameter("Method", apiRequestAudit.getMethod())
					.setParameter("Path", apiRequestAudit.getPath());
		}

		final org.springframework.http.HttpMethod httpMethod = org.springframework.http.HttpMethod.resolve(apiRequestAudit.getMethod().getCode());

		final WebClient.RequestBodyUriSpec uriSpec = webClient.method(httpMethod);

		final HttpHeaders httpHeaders = new HttpHeaders();
		apiRequestAudit.getRequestHeaders(objectMapper)
				.map(HttpHeadersWrapper::streamHeaders)
				.ifPresent(headers -> headers.forEach(headerEntry -> httpHeaders.add(headerEntry.getKey(), headerEntry.getValue())));

		httpHeaders.add(API_REQUEST_HEADER_EXISTING_AUDIT_ID, String.valueOf(apiRequestAudit.getIdNotNull().getRepoId()));

		httpHeaders.forEach((key, value) -> uriSpec.header(key, value.toArray(new String[0])));

		final String hostName = computeInternalHostName(apiRequestAudit);
		final int port = sysConfigBL.getIntValue(CFG_INTERNAL_PORT, CFG_INTERNAL_PORT_DEFAULT);

		final String[] split = apiRequestAudit.getPath().split("\\?");
		final String queryString = split.length > 1 ? "?" + split[1] : "";

		final URI uri = URI.create("http://" + hostName + ":" + port + apiRequestAudit.getRequestURI() + queryString);

		final WebClient.RequestBodySpec bodySpec = uriSpec.uri(uri);

		if (Check.isNotBlank(apiRequestAudit.getBody()))
		{
			bodySpec.body(Mono.just(apiRequestAudit.getRequestBody(objectMapper)), Object.class);
		}

		try
		{
			return bodySpec.exchangeToMono(cr -> cr
							.bodyToMono(String.class)
							.map(body -> ApiResponseMapper.map(cr.rawStatusCode(), cr.headers().asHttpHeaders(), body))
							.defaultIfEmpty(ApiResponseMapper.map(cr.rawStatusCode(), cr.headers().asHttpHeaders(), null)))
					.block();
		}
		catch (final RuntimeException rte)
		{
			throw new AdempiereException("Caught " + rte.getClass().getSimpleName() + " exception while making web-client http call", rte)
					.appendParametersToMessage()
					.setParameter("URI", uri)
					.setParameter("AD_SysConfig de.metas.util.web.audit.AppServerInternalHostName", sysConfigBL.getValue(CFG_INTERNAL_HOST_NAME, CFG_INTERNAL_HOST_NAME_DEFAULT))
					.setParameter("AD_SysConfig de.metas.util.web.audit.AppServerInternalPort", sysConfigBL.getIntValue(CFG_INTERNAL_PORT, CFG_INTERNAL_PORT_DEFAULT));
		}
	}

	public void auditResponse(
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final ApiResponse apiResponse,
			@NonNull final ApiRequestAudit apiRequestAudit)
	{
		logResponse(apiResponse, apiRequestAudit);

		final Status requestStatus = apiResponse.hasStatus2xx() ? Status.PROCESSED : Status.ERROR;

		updateRequestStatus(requestStatus, apiRequestAudit);

		notifyUserInCharge(apiAuditConfig, apiRequestAudit, !apiResponse.hasStatus2xx());

		performDataExportAudit(apiRequestAudit, apiResponse);
	}

	@NonNull
	public ApiRequestAudit updateRequestStatus(
			@NonNull final Status status,
			@NonNull final ApiRequestAudit apiRequestAudit)
	{
		final ApiRequestAudit updateApiRequestAudit = apiRequestAudit.toBuilder()
				.status(status)
				.build();

		return apiRequestAuditRepository.save(updateApiRequestAudit);
	}

	public void notifyUserInCharge(
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final ApiRequestAudit apiRequestAudit,
			final boolean isError)
	{
		final Optional<UserGroupId> userGroupToNotify = apiAuditConfig.getUserGroupToNotify(isError);

		if (!userGroupToNotify.isPresent())
		{
			Loggables.addLog("Notification skipped due to ApiAuditConfig! UserGroupInChargeId = {}, "
							+ "ApiAuditConfigId = {}, NotifyUserInChargeTrigger = {}",
					apiAuditConfig.getUserGroupInChargeId(), apiAuditConfig.getApiAuditConfigId(), apiAuditConfig.getNotifyUserInCharge());
			return;
		}

		final AdMessageKey messageKey = isError ? MSG_API_INVOCATION_FAILED : MSG_SUCCESSFUL_API_INVOCATION;

		final UserNotificationRequest.TargetRecordAction targetRecordAction = UserNotificationRequest
				.TargetRecordAction
				.of(I_API_Request_Audit.Table_Name, apiRequestAudit.getIdNotNull().getRepoId());

		userGroupRepository
				.getByUserGroupId(userGroupToNotify.get())
				.streamAssignmentsFor(userGroupToNotify.get(), Instant.now())
				.map(UserGroupUserAssignment::getUserId)
				.map(userId -> UserNotificationRequest.builder()
						.recipientUserId(userId)
						.contentADMessage(messageKey)
						.contentADMessageParam(apiRequestAudit.getPath())
						.targetAction(targetRecordAction)
						.build())
				.forEach(notificationBL::send);
	}

	public boolean bypassFilter(@NonNull final HttpServletRequest request)
	{
		final String contentType = request.getContentType();

		if (Check.isBlank(contentType))
		{
			return false;
		}

		return !contentType.contains(APPLICATION_JSON_VALUE);
	}

	private boolean shouldProcessRequestAsync(@NonNull final HttpServletRequest request, @NonNull final ApiAuditConfig apiAuditConfig)
	{
		final Optional<Boolean> callerWantsToBeProcessedAsync = Optional.ofNullable(request.getHeader(API_ASYNC_HEADER))
				.map(Boolean::parseBoolean);

		if (!callerWantsToBeProcessedAsync.isPresent())
		{
			return apiAuditConfig.isForceProcessedAsync();

		}
		else if (callerWantsToBeProcessedAsync.get())
		{
			return true;

		}
		else if (apiAuditConfig.isForceProcessedAsync())
		{
			throw new AdempiereException("Request cannot be processed synchronously due to API configs!")
					.appendParametersToMessage()
					.setParameter("API_Audit_Config_ID", apiAuditConfig.getApiAuditConfigId());
		}

		return false;
	}

	private void processRequestAsync(
			@NonNull final HttpServletRequest request,
			@NonNull final HttpServletResponse response,
			@NonNull final ApiAuditConfig apiAuditConfig) throws IOException
	{
		final OrgId orgId = apiAuditConfig.getOrgId();

		final ApiRequest apiRequest = ApiRequestMapper.map(new CachedBodyHttpServletRequest(request));

		final ApiRequestAudit requestAudit = logRequest(apiRequest, apiAuditConfig, Status.RECEIVED);

		final ApiAuditLoggable apiAuditLoggable = createLogger(requestAudit.getIdNotNull(), requestAudit.getUserId());

		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(apiAuditLoggable))
		{
			final CompletableFuture<ApiResponse> actualRestApiResponseCF = new CompletableFuture<>();

			final FutureCompletionContext futureCompletionContext = FutureCompletionContext.builder()
					.apiAuditConfig(apiAuditConfig)
					.apiAuditLoggable(apiAuditLoggable)
					.apiRequestAudit(requestAudit)
					.orgId(orgId)
					.build();

			actualRestApiResponseCF
					.whenComplete((apiResponse, throwable) -> handleFutureCompletion(apiResponse, throwable, futureCompletionContext));

			final Supplier<ApiResponse> callEndpointSupplier = () -> executeHttpCall(requestAudit);

			final ScheduledRequest scheduledRequest = new ScheduledRequest(actualRestApiResponseCF, callEndpointSupplier);

			final UserId callerUserId = Env.getLoggedUserId();

			final HttpCallScheduler httpCallScheduler = callerId2Scheduler.computeIfAbsent(callerUserId, (userId) -> new HttpCallScheduler());

			httpCallScheduler.schedule(scheduledRequest);

			ResponseHandler.writeHttpResponse(getGenericNoWaitResponse(), apiAuditConfig, requestAudit, response);
		}
		catch (final Exception e)
		{
			apiAuditLoggable.addLog("Caught {} with message={}", e.getClass().getName(), e.getMessage(), e);
			ResponseHandler.writeErrorResponse(e, response, requestAudit, apiAuditConfig);
		}
		finally
		{
			apiAuditLoggable.flush();
		}
	}

	private void processRequestSync(
			@NonNull final FilterChain filterChain,
			@NonNull final HttpServletRequest request,
			@NonNull final HttpServletResponse response,
			@NonNull final ApiAuditConfig apiAuditConfig) throws IOException, ServletException
	{
		final ContentCachingResponseWrapper contentCachedResponse = new ContentCachingResponseWrapper(response);
		final CachedBodyHttpServletRequest contentCachedRequest = new CachedBodyHttpServletRequest(request);

		if (apiAuditConfig.isPerformAuditAsync())
		{
			filterChain.doFilter(contentCachedRequest, contentCachedResponse);

			auditHttpCallAsync(apiAuditConfig, contentCachedRequest, contentCachedResponse);

			contentCachedResponse.copyBodyToResponse();
			return;
		}

		final ApiRequest apiRequest = ApiRequestMapper.map(contentCachedRequest);

		final ApiRequestAudit apiRequestAudit = logRequest(apiRequest, apiAuditConfig, Status.RECEIVED);

		final ApiAuditLoggable apiAuditLoggable = createLogger(apiRequestAudit.getIdNotNull(), apiRequestAudit.getUserId());

		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(apiAuditLoggable))
		{
			filterChain.doFilter(contentCachedRequest, contentCachedResponse);

			final ApiResponse apiResponse = ApiResponseMapper.map(contentCachedResponse);

			auditResponse(apiAuditConfig, apiResponse, apiRequestAudit);

			ResponseHandler.writeHttpResponse(apiResponse, apiAuditConfig, apiRequestAudit, response);
		}
		catch (final Exception e)
		{
			apiAuditLoggable.addLog("Caught {} with message={}", e.getClass().getName(), e.getMessage(), e);
			ResponseHandler.writeErrorResponse(e, response, apiRequestAudit, apiAuditConfig);
		}
		finally
		{
			apiAuditLoggable.flush();
		}
	}

	private ApiRequestAudit logRequest(
			@NonNull final ApiRequest apiRequest,
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final Status status)
	{
		try
		{
			final HttpHeadersWrapper requestHeaders = HttpHeadersWrapper.of(apiRequest.getHeaders());

			final ApiRequestAudit apiRequestAudit = ApiRequestAudit.builder()
					.apiAuditConfigId(apiAuditConfig.getApiAuditConfigId())
					.orgId(apiAuditConfig.getOrgId())
					.roleId(Env.getLoggedRoleId())
					.userId(Env.getLoggedUserIdIfExists().orElse(UserId.SYSTEM)) // there might be no logged in user yet in the case of /api/v2/auth
					.status(status)
					.body(apiRequest.getBody())
					.method(HttpMethod.ofCodeOptional(apiRequest.getHttpMethod()).orElse(null))
					.path(apiRequest.getFullPath())
					.remoteAddress(apiRequest.getRemoteAddr())
					.remoteHost(apiRequest.getRemoteHost())
					.time(Instant.now())
					.httpHeaders(requestHeaders.toJson(objectMapper))
					.requestURI(apiRequest.getRequestURI())
					.uiTraceExternalId(UITraceExternalId.ofNullableString(requestHeaders.getHeaderSingleValue(API_UI_Trace_ID.toLowerCase())))
					.build();

			return apiRequestAuditRepository.save(apiRequestAudit);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to create ApiRequestAudit!", e)
					.appendParametersToMessage()
					.setParameter("Path", apiRequest.getFullPath())
					.setParameter("Method", apiRequest.getHttpMethod());
		}
	}

	private void handleFutureCompletion(
			@Nullable final ApiResponse apiResponse,
			@Nullable final Throwable throwable,
			@NonNull final FutureCompletionContext completionContext)
	{
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(completionContext.getApiAuditLoggable()))
		{
			if (apiResponse != null)
			{
				auditResponse(completionContext.getApiAuditConfig(), apiResponse, completionContext.getApiRequestAudit());

				Loggables.addLog("Request routed successfully!");
			}
			else
			{
				updateRequestStatus(Status.ERROR, completionContext.getApiRequestAudit());

				final boolean isError = true;
				notifyUserInCharge(completionContext.getApiAuditConfig(), completionContext.getApiRequestAudit(), isError);

				Loggables.addLog("Error encountered while routing the request!", throwable);
			}
		}
	}

	@NonNull
	private ApiResponse getGenericNoWaitResponse()
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

		return ApiResponseMapper.map(HttpStatus.ACCEPTED.value(), httpHeaders, null);
	}

	private void performDataExportAudit(@NonNull final ApiRequestAudit apiRequestAudit, @NonNull final ApiResponse apiResponse)
	{
		if (!apiResponse.hasStatus2xx()
				|| apiRequestAudit.getRequestURI() == null
				|| apiResponse.getBody() == null)
		{
			return;
		}

		final GenericDataExportAuditRequest.GenericDataExportAuditRequestBuilder genericRequestBuilder = GenericDataExportAuditRequest
				.builder()
				.exportedObject(apiResponse.getBody())
				.requestURI(apiRequestAudit.getRequestURI());

		final Optional<HttpHeadersWrapper> requestHeaders = apiRequestAudit.getRequestHeaders(objectMapper);

		// get the AD_PInstance_ID and external-config-id if they are specified in the headers
		requestHeaders
				.map(headers -> headers.getHeaderSingleValue(HEADER_EXTERNALSYSTEM_CONFIG_ID))
				.map(Integer::parseInt)
				.map(ExternalSystemParentConfigId::ofRepoId)
				.ifPresent(genericRequestBuilder::externalSystemParentConfigId);

		requestHeaders
				.map(headers -> headers.getHeaderSingleValue(HEADER_PINSTANCE_ID))
				.map(Integer::parseInt)
				.map(PInstanceId::ofRepoId)
				.ifPresent(genericRequestBuilder::pInstanceId);

		final GenericDataExportAuditRequest auditRequest = genericRequestBuilder.build();

		compositeDataAuditService.performDataAuditForRequest(auditRequest);
	}

	private String computeInternalHostName(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		final String hostName;
		if (apiRequestAudit.getPath().startsWith("http://localhost"))
		{
			hostName = "localhost";
		}
		else
		{
			hostName = sysConfigBL.getValue(CFG_INTERNAL_HOST_NAME, CFG_INTERNAL_HOST_NAME_DEFAULT);
		}
		return hostName;
	}

	private void auditHttpCallAsync(
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final CachedBodyHttpServletRequest executedRequest,
			@NonNull final ContentCachingResponseWrapper response)
	{
		try
		{
			final ApiResponse apiResponse = ApiResponseMapper.map(response);
			final ApiRequest apiRequest = ApiRequestMapper.map(executedRequest);

			CompletableFuture.runAsync(() -> {
				try
				{
					final Status status = apiResponse.hasStatus2xx() ? Status.PROCESSED : Status.ERROR;

					final ApiRequestAudit apiRequestAudit = logRequest(apiRequest, apiAuditConfig, status);

					auditResponse(apiAuditConfig, apiResponse, apiRequestAudit);

					final ApiAuditLoggable apiAuditLogger = createLogger(apiRequestAudit.getIdNotNull(), apiRequestAudit.getUserId());
					apiAuditLogger.addLog("Async audit performed successfully!");
					apiAuditLogger.flush();
				}
				catch (final Throwable throwable)
				{
					createIssue(throwable, "Exception on storing http call audit info: path: " + ApiRequestMapper.getFullPath(executedRequest) + "; method: " + executedRequest.getMethod());
				}
			});
		}
		catch (final Throwable throwable)
		{
			createIssue(throwable, "Exception caught while trying to audit http call! path: " + ApiRequestMapper.getFullPath(executedRequest) + "; method: " + executedRequest.getMethod());
		}
	}

	private void createIssue(@NonNull final Throwable throwable, @Nullable final String msg)
	{
		final IssueCreateRequest issueCreateRequest = IssueCreateRequest.builder()
				.summary(msg)
				.throwable(throwable)
				.build();

		errorManager.createIssue(issueCreateRequest);
	}

	private void logResponse(
			@NonNull final ApiResponse apiResponse,
			@NonNull final ApiRequestAudit apiRequestAudit)
	{
		try
		{
			final String bodyAsString = apiResponse.getBody() != null
					? objectMapper.writeValueAsString(apiResponse.getBody())
					: null;

			final ImmutableMultimap.Builder<String, String> responseHeadersBuilder = new ImmutableMultimap.Builder<>();

			if (apiResponse.getHttpHeaders() != null)
			{
				apiResponse.getHttpHeaders().forEach(responseHeadersBuilder::putAll);
			}

			final HttpHeadersWrapper responseHeaders = HttpHeadersWrapper.of(responseHeadersBuilder.build());

			final ApiResponseAudit apiResponseAudit = ApiResponseAudit.builder()
					.orgId(apiRequestAudit.getOrgId())
					.apiRequestAuditId(apiRequestAudit.getIdNotNull())
					.body(bodyAsString)
					.httpCode(String.valueOf(apiResponse.getStatusCode()))
					.time(Instant.now())
					.httpHeaders(responseHeaders.toJson(objectMapper))
					.build();

			apiResponseAuditRepository.save(apiResponseAudit);
		}
		catch (final JsonProcessingException e)
		{
			final AdempiereException exception = AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("ApiResponse", apiResponse);

			Loggables.addLog("Error when trying to parse the api response body!", exception);
		}
	}

	@Value
	@Builder
	private static class FutureCompletionContext
	{
		@NonNull
		ApiAuditLoggable apiAuditLoggable;

		@NonNull
		ApiRequestAudit apiRequestAudit;

		@NonNull
		OrgId orgId;

		@NonNull
		ApiAuditConfig apiAuditConfig;
	}
}
