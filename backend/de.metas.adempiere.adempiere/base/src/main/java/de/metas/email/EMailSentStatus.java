package de.metas.email;

<<<<<<< HEAD
import java.io.Serializable;

=======
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import de.metas.email.impl.EMailSendException;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import javax.annotation.concurrent.Immutable;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
<<<<<<< HEAD

import org.compiere.util.Util;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.util.Check;
=======
import java.io.Serializable;
import java.util.function.Consumer;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * EMail sent status.
<<<<<<< HEAD
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @see EMail#send()
 */
@SuppressWarnings("serial")
=======
 *
 * @author metas-dev <dev@metasfresh.com>
 * @see EMail#send()
 */
@Getter
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
@Immutable
public final class EMailSentStatus implements Serializable
{
	private static final Logger logger = LogManager.getLogger(EMailSentStatus.class);

<<<<<<< HEAD
	/* package */static final EMailSentStatus NOT_SENT = new EMailSentStatus();

	/** Mail Sent OK Status */
	@VisibleForTesting
	/* package */static final String SENT_OK = new String("OK");

	/* package */static final EMailSentStatus ok(final String messageId)
	{
		final boolean sentConnectionError = false;
		return new EMailSentStatus(SENT_OK, sentConnectionError, messageId);
	}

	/* package */static final EMailSentStatus invalid(final String errorMessage)
	{
		final boolean sentConnectionError = false;
		final String messageId = null;
		return new EMailSentStatus(errorMessage, sentConnectionError, messageId);
	}

	/* package */static final EMailSentStatus error(final String errorMessage)
	{
		final boolean sentConnectionError = false;
		final String messageId = null;
		return new EMailSentStatus(errorMessage, sentConnectionError, messageId);
	}

	/* package */static final EMailSentStatus error(final MessagingException me)
=======
	static final EMailSentStatus NOT_SENT = new EMailSentStatus(null, false, null);
	private static final String SENT_OK = "OK";

	@JsonProperty("sentMsg") private final String sentMsg;
	@JsonProperty("sentConnectionError") private final boolean sentConnectionError;
	@JsonProperty("messageId") private final String messageId;

	@JsonCreator
	private EMailSentStatus(
			@JsonProperty("sentMsg") final String sentMsg,
			@JsonProperty("sentConnectionError") final boolean sentConnectionError,
			@JsonProperty("messageId") final String messageId)
	{
		this.sentMsg = sentMsg;
		this.sentConnectionError = sentConnectionError;
		this.messageId = messageId;
	}

	public static EMailSentStatus ok(@Nullable final String messageId)
	{
		return new EMailSentStatus(SENT_OK, false, messageId);
	}

	public static EMailSentStatus invalid(final String errorMessage)
	{
		return new EMailSentStatus(errorMessage, false, null);
	}

	public static EMailSentStatus invalid(final ITranslatableString errorMessage)
	{
		return invalid(errorMessage.getDefaultValue());
	}

	public static EMailSentStatus invalid(@NonNull final Exception ex)
	{
		return error(ex);
	}

	public static EMailSentStatus error(@NonNull final Exception ex)
	{
		return new EMailSentStatus(AdempiereException.extractMessage(ex), false, null);
	}

	public static EMailSentStatus error(@NonNull final MessagingException me)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		Exception ex = me;
		final StringBuilder errorMsgBuilder = new StringBuilder("(ME)");
		boolean isSentConnectionError = false;
		boolean printed = false;
		do
		{
			if (ex instanceof SendFailedException)
			{
				final SendFailedException sfex = (SendFailedException)ex;
				final Address[] invalid = sfex.getInvalidAddresses();
				if (!printed)
				{
					if (invalid != null && invalid.length > 0)
					{
						errorMsgBuilder.append(" - Invalid:");
<<<<<<< HEAD
						for (int i = 0; i < invalid.length; i++)
						{
							errorMsgBuilder.append(" ").append(invalid[i]);
=======
						for (final Address address : invalid)
						{
							errorMsgBuilder.append(" ").append(address);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
						}

					}
					final Address[] validUnsent = sfex.getValidUnsentAddresses();
					if (validUnsent != null && validUnsent.length > 0)
					{
						errorMsgBuilder.append(" - ValidUnsent:");
<<<<<<< HEAD
						for (int i = 0; i < validUnsent.length; i++)
						{
							errorMsgBuilder.append(" ").append(validUnsent[i]);
=======
						for (final Address address : validUnsent)
						{
							errorMsgBuilder.append(" ").append(address);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
						}
					}
					final Address[] validSent = sfex.getValidSentAddresses();
					if (validSent != null && validSent.length > 0)
					{
						errorMsgBuilder.append(" - ValidSent:");
<<<<<<< HEAD
						for (int i = 0; i < validSent.length; i++)
						{
							errorMsgBuilder.append(" ").append(validSent[i]);
=======
						for (final Address address : validSent)
						{
							errorMsgBuilder.append(" ").append(address);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
						}
					}
					printed = true;
				}
				if (sfex.getNextException() == null)
				{
					errorMsgBuilder.append(" ").append(sfex.getLocalizedMessage());
				}
			}
			else if (ex instanceof AuthenticationFailedException)
			{
<<<<<<< HEAD
				errorMsgBuilder.append(" - Invalid Username/Password");
			}
			else if (ex instanceof java.net.ConnectException)
			{
				errorMsgBuilder.append(" - Connection error: " + ex.getLocalizedMessage());
=======
				errorMsgBuilder.append(" - Invalid Username/Password: ").append(ex.getLocalizedMessage());
			}
			else if (ex instanceof java.net.ConnectException)
			{
				errorMsgBuilder.append(" - Connection error: ").append(ex.getLocalizedMessage());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				isSentConnectionError = true;
			}
			else
			// other MessagingException
			{
				String msg = ex.getLocalizedMessage();
				if (msg == null)
				{
<<<<<<< HEAD
					errorMsgBuilder.append(": ").append(ex.toString());
				}
				else
				{
					if (msg.indexOf("Could not connect to SMTP host:") != -1)
=======
					errorMsgBuilder.append(": ").append(ex);
				}
				else
				{
					if (msg.contains("Could not connect to SMTP host:"))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					{
						final int index = msg.indexOf('\n');
						if (index != -1)
						{
							msg = msg.substring(0, index);
						}
					}
					final String className = ex.getClass().getName();
<<<<<<< HEAD
					if (className.indexOf("MessagingException") != -1)
=======
					if (className.contains("MessagingException"))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
					{
						errorMsgBuilder.append(": ").append(msg);
					}
					else
					{
						errorMsgBuilder.append(" ").append(className).append(": ").append(msg);
					}
				}
			}
			// Next Exception
			if (ex instanceof MessagingException)
			{
				ex = ((MessagingException)ex).getNextException();
			}
			else
			{
				ex = null;
			}
		}
<<<<<<< HEAD
		while (ex != null);	// error loop
=======
		while (ex != null);    // error loop
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		//
		// Build the final error message
		String errorMsg = errorMsgBuilder.toString();
<<<<<<< HEAD
		if (Check.isEmpty(errorMsg, true))
=======
		if (Check.isBlank(errorMsg))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			errorMsg = me.toString();
		}

		// Log it
		if (LogManager.isLevelFinest())
		{
			logger.warn(errorMsg, me);
		}
		else
		{
			logger.warn(errorMsg);
		}

		//
		//
		final String messageId = null;
		return new EMailSentStatus(errorMsg, isSentConnectionError, messageId);
	}

<<<<<<< HEAD
	@JsonProperty("sentMsg")
	private final String sentMsg;
	@JsonProperty("sentConnectionError")
	private final boolean sentConnectionError;
	@JsonProperty("messageId")
	private final String messageId;

	@JsonCreator
	private EMailSentStatus(
			@JsonProperty("sentMsg") final String sentMsg //
			, @JsonProperty("sentConnectionError") final boolean sentConnectionError //
			, @JsonProperty("messageId") final String messageId //
	)
	{
		super();
		this.sentMsg = sentMsg;
		this.sentConnectionError = sentConnectionError;
		this.messageId = messageId;
	}

	/** Null/new constructor */
	private EMailSentStatus()
	{
		super();
		sentMsg = null;
		sentConnectionError = false;
		messageId = null;
=======
	public static boolean isConnectionError(final Exception e)
	{
		if (e instanceof EMailSendException)
		{
			return ((EMailSendException)e).isConnectionError();
		}
		else
			return e instanceof java.net.ConnectException;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("sentMsg", sentMsg)
				.add("sentConnectionError", sentConnectionError)
				.add("messageId", messageId)
				.toString();
	}

<<<<<<< HEAD
	public String getSentMsg()
	{
		return sentMsg;
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@JsonIgnore
	public boolean isSentOK()
	{
		return Util.same(sentMsg, SENT_OK);
	}

<<<<<<< HEAD
	public boolean isSentConnectionError()
	{
		return sentConnectionError;
	}

	public String getMessageId()
	{
		return messageId;
	}
=======
	public void throwIfNotOK()
	{
		throwIfNotOK(null);
	}

	public void throwIfNotOK(@Nullable final Consumer<EMailSendException> exceptionDecorator)
	{
		if (!isSentOK())
		{
			final EMailSendException exception = new EMailSendException(this);
			if (exceptionDecorator != null)
			{
				exceptionDecorator.accept(exception);
			}

			throw exception;
		}
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
