package de.metas.cache.rest;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.util.StringUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonCacheResetRequest
{
	@JsonAnySetter
	@JsonAnyGetter
	@NonNull private final HashMap<String, String> map = new HashMap<>();

	public static JsonCacheResetRequest extractFrom(HttpServletRequest httpRequest)
	{
		final JsonCacheResetRequest request = new JsonCacheResetRequest();
		httpRequest.getParameterMap()
				.forEach((name, values) -> request.setValue(name, values != null && values.length > 0 ? values[0] : null));

		return request;
	}

	public JsonCacheResetRequest setValue(@NonNull String name, @Nullable String value)
	{
		map.put(name, value);
		return this;
	}

	public JsonCacheResetRequest setValue(@NonNull String name, boolean value)
	{
		return setValue(name, String.valueOf(value));
	}

	public boolean getValueAsBoolean(@NonNull final String name)
	{
		return StringUtils.toBoolean(map.get(name));
	}
}
