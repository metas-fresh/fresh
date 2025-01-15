package de.metas.ui_trace;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode
public class UITraceExternalId
{
	private final String stringValue;

	private UITraceExternalId(@NonNull final String stringValue)
	{
		this.stringValue = stringValue;
	}

	@Nullable
	@JsonCreator
	public static UITraceExternalId ofNullableString(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null ? new UITraceExternalId(stringNorm) : null;
	}

	@NonNull
	public static UITraceExternalId ofString(@NonNull final String string)
	{
		final UITraceExternalId value = ofNullableString(string);
		if (value == null)
		{
			throw new AdempiereException("empty/null external id is not allowed");
		}
		return value;
	}

	@Override
	@Deprecated
	public String toString() {return stringValue;}

	@JsonValue
	public String getAsString() {return stringValue;}
}
