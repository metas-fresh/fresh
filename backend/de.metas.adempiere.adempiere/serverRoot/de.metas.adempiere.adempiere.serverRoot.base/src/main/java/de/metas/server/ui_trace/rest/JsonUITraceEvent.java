package de.metas.server.ui_trace.rest;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.mobile.application.MobileApplicationId;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonUITraceEvent
{
	private String id;
	private String eventName;
	private long timestamp;
	private final LinkedHashMap<String, Object> properties = new LinkedHashMap<>();

	@JsonAnyGetter
	public LinkedHashMap<String, Object> getProperties() {return properties;}

	@JsonAnySetter
	public void putProperty(final String key, final Object value) {properties.put(key, value);}

	public Optional<String> getUrl() {return getByPathAsString("page", "url");}

	public Optional<String> getUsername() {return getByPathAsString("user", "username");}

	public Optional<String> getCaption() {return getByPathAsString("caption");}

	public Optional<MobileApplicationId> getApplicationId() {return getByPathAsString("applicationId").map(MobileApplicationId::ofNullableString);}

	public Optional<String> getDeviceId() {return getByPathAsString("device", "deviceId");}

	public Optional<String> getTabId() {return getByPathAsString("device", "tabId");}

	public Optional<String> getUserAgent() {return getByPathAsString("device", "userAgent");}

	public Optional<String> getByPathAsString(final String... path)
	{
		return getByPath(path).map(Object::toString);
	}

	public Optional<Object> getByPath(final String... path)
	{
		Object currentValue = properties;
		for (final String pathElement : path)
		{
			if (!(currentValue instanceof Map))
			{
				//throw new AdempiereException("Invalid path " + Arrays.asList(path) + " in " + properties);
				return Optional.empty();
			}

			//noinspection unchecked
			final Object value = getByKey((Map<String, Object>)currentValue, pathElement).orElse(null);
			if (value == null)
			{
				return Optional.empty();
			}
			else
			{
				currentValue = value;
			}
		}

		return Optional.of(currentValue);
	}

	private static Optional<Object> getByKey(final Map<String, Object> map, final String key)
	{
		return map.entrySet()
				.stream()
				.filter(e -> e.getKey().equalsIgnoreCase(key))
				.map(Map.Entry::getValue)
				.filter(Objects::nonNull)
				.findFirst();
	}
}
