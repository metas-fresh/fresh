package de.metas.ui_trace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_UI_Trace;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

@Repository
public class UITraceRepository
{
	@NonNull private static final Logger logger = LogManager.getLogger(UITraceRepository.class);
	@NonNull private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	public void create(@NonNull final Collection<UITraceEventCreateRequest> requests)
	{
		for (final UITraceEventCreateRequest request : requests)
		{
			final I_UI_Trace record = InterfaceWrapperHelper.newInstance(I_UI_Trace.class);
			updateRecord(record, request);
			InterfaceWrapperHelper.save(record);
		}
	}

	private void updateRecord(final I_UI_Trace record, final UITraceEventCreateRequest from)
	{
		record.setExternalId(from.getId().getAsString());
		record.setEventName(from.getEventName());
		record.setTimestamp(Timestamp.from(from.getTimestamp()));
		record.setURL(from.getUrl());
		record.setUserName(from.getUsername());
		record.setCaption(from.getCaption());
		record.setUI_ApplicationId(from.getApplicationId() != null ? from.getApplicationId().getAsString() : null);
		record.setUI_DeviceId(from.getDeviceId());
		record.setUI_TabId(from.getTabId());
		record.setUserAgent(from.getUserAgent());
		record.setEventData(extractPropertiesAsJsonString(from));
	}

	private String extractPropertiesAsJsonString(final UITraceEventCreateRequest request)
	{
		final Map<String, Object> properties = request.getProperties();
		if (properties == null)
		{
			return "";
		}

		try
		{
			return jsonObjectMapper.writeValueAsString(properties);
		}
		catch (JsonProcessingException e)
		{
			logger.warn("Failed converting request's properties to string: {}", request, e);
			return properties.toString();
		}
	}
}
