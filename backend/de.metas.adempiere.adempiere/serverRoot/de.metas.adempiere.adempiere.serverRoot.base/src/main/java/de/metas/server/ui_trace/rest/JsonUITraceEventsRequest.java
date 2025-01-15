package de.metas.server.ui_trace.rest;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonUITraceEventsRequest
{
	@NonNull List<JsonUITraceEvent> events;
}
