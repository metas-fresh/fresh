/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.i18n;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * AD_Message.Value
 */
@EqualsAndHashCode
public final class AdMessageKey
{
	@JsonCreator
	public static AdMessageKey of(@NonNull final String value)
	{
		return new AdMessageKey(value);
	}

	@Nullable
	public static AdMessageKey ofNullable(@Nullable final String value)
	{
		return value != null && Check.isNotBlank(value) ? of(value) : null;
	}

	private final String value;

	private AdMessageKey(final String value)
	{
		Check.assumeNotEmpty(value, "value is not empty");
		this.value = value;
	}

	@Override
	public String toString()
	{
		return toAD_Message();
	}

	@JsonValue
	public String toAD_Message()
	{
		return value;
	}

	public String toAD_MessageWithMarkers()
	{
		return "@" + toAD_Message() + "@";
	}

	public boolean startsWith(final String prefix) {return value.startsWith(prefix);}
}
