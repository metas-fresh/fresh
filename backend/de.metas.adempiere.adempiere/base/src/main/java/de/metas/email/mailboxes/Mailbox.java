package de.metas.email.mailboxes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.email.EMailAddress;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Mailbox configuration.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class Mailbox
{
	private final static Logger logger = LogManager.getLogger(Mailbox.class);

	private static final int DEFAULT_SMTP_PORT = 25;
	private static final int DEFAULT_SMTPS_PORT = 587;

	@NonNull MailboxType type;
	@NonNull EMailAddress email;
	@Nullable String userToColumnName;
	@Nullable SMTPConfig smtpConfig;
	@Nullable MicrosoftGraphConfig microsoftGraphConfig;

	@Jacksonized
	@Builder(toBuilder = true)
	private Mailbox(
			@NonNull final MailboxType type,
			@NonNull final EMailAddress email,
			@Nullable final String userToColumnName,
			@Nullable final SMTPConfig smtpConfig,
			@Nullable final MicrosoftGraphConfig microsoftGraphConfig)
	{
		this.type = type;
		this.email = email;
		this.userToColumnName = StringUtils.trimBlankToNull(userToColumnName);

		switch (type)
		{
			case SMTP:
			{
				this.smtpConfig = Check.assumeNotNull(smtpConfig, "smtpConfig cannot be null");
				this.microsoftGraphConfig = null;
				break;
			}
			case MICROSOFT_GRAPH:
			{
				this.smtpConfig = null;
				this.microsoftGraphConfig = Check.assumeNotNull(microsoftGraphConfig, "microsoftGraphConfig cannot be null");
				break;
			}
			default:
			{
				throw new AdempiereException("Unknown type: " + type);
			}
		}
	}

	public ExplainedOptional<Mailbox> mergeFrom(@Nullable final UserEMailConfig userEmailConfig)
	{
		if (userEmailConfig == null)
		{
			logger.debug("userEmailConfig can't be used because it has no user config");
			return ExplainedOptional.of(this);
		}

		final EMailAddress userEmail = userEmailConfig.getEmail();
		if (userEmail == null)
		{
			logger.debug("userEmailConfig can't be used because it has no Mail-Address: {}", userEmailConfig);
			return ExplainedOptional.emptyBecause("userEmailConfig can't be used because it has no Mail-Address: " + userEmailConfig);
		}

		switch (type)
		{
			case SMTP:
			{
				final SMTPConfig smtpConfig = getSmtpConfigNotNull();

				if (smtpConfig.isSmtpAuthorization()
						&& (Check.isBlank(userEmailConfig.getUsername()) || Check.isBlank(userEmailConfig.getPassword())))
				{
					return ExplainedOptional.emptyBecause("userEmailConfig can't be used because it has no SMTP-UserName or SMTP-Password: " + userEmailConfig);
				}

				return ExplainedOptional.of(
						toBuilder()
								.email(userEmail)
								.smtpConfig(smtpConfig.mergeFrom(userEmailConfig))
								.build()
				);
			}
			case MICROSOFT_GRAPH:
			{
				final MicrosoftGraphConfig microsoftGraphConfig = getMicrosoftGraphConfigNotNull();
				return ExplainedOptional.of(
						toBuilder()
								.microsoftGraphConfig(microsoftGraphConfig.withDefaultReplyTo(userEmail))
								.build()
				);
			}
			default:
				throw new AdempiereException("Unknown type: " + type);
		}
	}

	public Mailbox withUserToColumnName(final String userToColumnName)
	{
		final String userToColumnNameNorm = StringUtils.trimBlankToNull(userToColumnName);
		if (Objects.equals(this.userToColumnName, userToColumnNameNorm))
		{
			return this;
		}
		else
		{
			return toBuilder().userToColumnName(userToColumnName).build();
		}
	}

	public SMTPConfig getSmtpConfigNotNull() {return Check.assumeNotNull(smtpConfig, "smtpConfig cannot be null");}

	public MicrosoftGraphConfig getMicrosoftGraphConfigNotNull() {return Check.assumeNotNull(microsoftGraphConfig, "microsoftGraphConfig cannot be null");}
}
