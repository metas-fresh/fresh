package de.metas.email;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class EMailAddress
{
	@Nullable
	public static EMailAddress ofNullableString(@Nullable final String emailStr)
	{
		final String emailStrNorm = StringUtils.trimBlankToNull(emailStr);
		return emailStrNorm != null ? new EMailAddress(emailStrNorm) : null;
	}

	public static Optional<EMailAddress> optionalOfNullable(@Nullable final String emailStr)
	{
		return Optional.ofNullable(ofNullableString(emailStr));
	}

	@JsonCreator
	public static EMailAddress ofString(@NonNull final String emailStr)
	{
		return new EMailAddress(emailStr);
	}

	public static List<EMailAddress> ofSemicolonSeparatedList(@Nullable final String emailsListStr)
	{
		if (emailsListStr == null || Check.isBlank(emailsListStr))
		{
			return ImmutableList.of();
		}

		return Splitter.on(";")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(emailsListStr)
				.stream()
				.map(EMailAddress::ofNullableString)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	public static ITranslatableString checkEMailValid(@Nullable final EMailAddress email)
	{
		if (email == null)
		{
			return TranslatableStrings.constant("no email");
		}
		return checkEMailValid(email.getAsString());
	}

	@Nullable
	public static ITranslatableString checkEMailValid(@Nullable final String email)
	{
		if (email == null || Check.isBlank(email))
		{
			return TranslatableStrings.constant("no email");
		}
		try
		{
			final InternetAddress ia = new InternetAddress(email, true);
			ia.validate();    // throws AddressException

			if (ia.getAddress() == null)
			{
				return TranslatableStrings.constant("invalid email");
			}

			return null; // OK
		}
		catch (final AddressException ex)
		{
			logger.warn("Invalid email address: {}", email, ex);
			return TranslatableStrings.constant(ex.getLocalizedMessage());
		}
	}

	private static final Logger logger = LogManager.getLogger(EMailAddress.class);

	private final String emailStr;

	private EMailAddress(@NonNull final String emailStr)
	{
		this.emailStr = emailStr.trim();
		if (this.emailStr.isEmpty())
		{
			throw new AdempiereException("Empty email address is not valid");
		}
	}

	@JsonValue
	public String getAsString()
	{
		return emailStr;
	}

	@Nullable
	public static String toStringOrNull(@Nullable final EMailAddress emailAddress)
	{
		return emailAddress != null ? emailAddress.getAsString() : null;
	}

	@Deprecated
	@Override
	public String toString()
	{
		return getAsString();
	}

	public InternetAddress toInternetAddress() throws AddressException
	{
		return new InternetAddress(emailStr, true);
	}
}
