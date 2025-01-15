package de.metas.common.rest_api.v2.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;

@Value
@Builder
@JsonDeserialize(builder = JsonAuthResponse.JsonAuthResponseBuilder.class)
public class JsonAuthResponse
{
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonAuthResponseBuilder {}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String token;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String language;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	Integer userId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String username;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	String userFullname;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String error;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	Map<String, Object> messages;

	public static JsonAuthResponse.JsonAuthResponseBuilder ok(@NonNull final String token)
	{
		return builder().token(token);
	}

	public static JsonAuthResponse error(@NonNull final String error)
	{
		return builder().error(error).build();
	}
}
