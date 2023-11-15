package de.metas.cache.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;

@EqualsAndHashCode
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonCacheResetResponse
{
	@NonNull private final ArrayList<String> logs = new ArrayList<>();

	public void addLog(@NonNull final String log)
	{
		logs.add(log);
	}
}
