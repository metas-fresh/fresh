package de.metas.server.ui_trace.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.util.Check;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUITraceEventTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	void beforeEach()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@SuppressWarnings("SameParameterValue")
	private JsonUITraceEvent fromJsonResource(String resource)
	{
		final InputStream in = Check.assumeNotNull(
				getClass().getResourceAsStream(resource),
				"Resource {} not found", resource
		);

		final String json = new String(Util.readBytes(in), StandardCharsets.UTF_8);
		return fromJsonString(json);
	}

	private JsonUITraceEvent fromJsonString(String json)
	{
		try
		{
			return jsonObjectMapper.readValue(json, JsonUITraceEvent.class);
		}
		catch (JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private JsonUITraceEvent getJsonUITraceEvent_1() {return fromJsonResource("/de/metas/server/ui_trace/rest/JsonUITraceEvent_1.json");}

	@Test
	void testDeserializeSerialize() throws JsonProcessingException
	{
		final JsonUITraceEvent event = getJsonUITraceEvent_1();

		final String json = jsonObjectMapper.writeValueAsString(event);
		final JsonUITraceEvent event2 = jsonObjectMapper.readValue(json, JsonUITraceEvent.class);
		assertThat(event2).isEqualTo(event);
	}

	@Test
	void test_getters()
	{
		final JsonUITraceEvent event = getJsonUITraceEvent_1();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(event.getUrl()).contains("http://localhost:3001/mobile/picking/wf/picking-1000006/scan/A/A1/");
		softly.assertThat(event.getUsername()).contains("teo");
		softly.assertThat(event.getCaption()).contains("107.0");
		softly.assertThat(event.getApplicationId()).contains(MobileApplicationId.ofString("picking"));
		softly.assertThat(event.getDeviceId()).contains("dabbb790-9ae9-4a90-a752-c9439b5fdf6c");
		softly.assertThat(event.getTabId()).contains("6f848a12-5b69-44e7-9f21-db13a3943980");
		softly.assertThat(event.getUserAgent()).contains("Mozilla/5.0 (Linux; Android 13; SM-G981B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36");

		softly.assertAll();
	}

	@Test
	void getByPath()
	{
		final JsonUITraceEvent event = getJsonUITraceEvent_1();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(event.getByPath("page", "url")).contains("http://localhost:3001/mobile/picking/wf/picking-1000006/scan/A/A1/");
		softly.assertThat(event.getByPath("user", "username")).contains("teo");
		softly.assertThat(event.getByPath("user", "userId")).contains(540116);
		softly.assertThat(event.getByPath("caption")).contains("107.0");
		softly.assertThat(event.getByPath("applicationId")).contains("picking");
		softly.assertThat(event.getByPath("DeViCe", "deviceId")).contains("dabbb790-9ae9-4a90-a752-c9439b5fdf6c");
		softly.assertThat(event.getByPath("DeViCe", "tabid")).contains("6f848a12-5b69-44e7-9f21-db13a3943980");
		softly.assertThat(event.getByPath("device", "userAgent")).contains("Mozilla/5.0 (Linux; Android 13; SM-G981B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36");

		// missing fields
		softly.assertThat(event.getByPath("missingField")).isEmpty();
		softly.assertThat(event.getByPath("page", "missingField")).isEmpty();
		softly.assertThat(event.getByPath("page", "url", "invalidField")).isEmpty();

		softly.assertAll();
	}
}