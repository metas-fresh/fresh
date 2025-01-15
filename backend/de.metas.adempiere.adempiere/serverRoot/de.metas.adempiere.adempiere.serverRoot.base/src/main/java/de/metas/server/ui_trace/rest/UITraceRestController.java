package de.metas.server.ui_trace.rest;

import de.metas.common.util.time.SystemTime;
import de.metas.ui_trace.UITraceEventCreateRequest;
import de.metas.ui_trace.UITraceExternalId;
import de.metas.ui_trace.UITraceService;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.AuthResolution;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import lombok.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(UITraceRestController.ENDPOINT)
public class UITraceRestController
{
	static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/trace";
	@NonNull private final UITraceService traceService;

	public UITraceRestController(
			@NonNull final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration,
			@NonNull final UITraceService traceService)
	{
		this.traceService = traceService;

		userAuthTokenFilterConfiguration.addAuthResolutionForPathsContaining(ENDPOINT, AuthResolution.AUTHENTICATE_IF_TOKEN_AVAILABLE);
	}

	@PostMapping
	public void trace(@RequestBody final JsonUITraceEventsRequest request)
	{
		traceService.create(toUITraceEventCreateRequest(request));
	}

	private static List<UITraceEventCreateRequest> toUITraceEventCreateRequest(final JsonUITraceEventsRequest request)
	{
		return request.getEvents()
				.stream()
				.map(UITraceRestController::toUITraceEventCreateRequest)
				.collect(Collectors.toList());
	}

	private static UITraceEventCreateRequest toUITraceEventCreateRequest(final JsonUITraceEvent json)
	{
		return UITraceEventCreateRequest.builder()
				.id(UITraceExternalId.ofString(json.getId()))
				.eventName(json.getEventName())
				.timestamp(json.getTimestamp() > 0 ? Instant.ofEpochMilli(json.getTimestamp()) : SystemTime.asInstant())
				.url(json.getUrl().orElse(null))
				.username(json.getUsername().orElse(null))
				.caption(json.getCaption().orElse(null))
				.applicationId(json.getApplicationId().orElse(null))
				.deviceId(json.getDeviceId().orElse(null))
				.tabId(json.getTabId().orElse(null))
				.userAgent(json.getUserAgent().orElse(null))
				.properties(json.getProperties())
				.build();
	}
}
