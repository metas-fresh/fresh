/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package de.metas.email;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.EmptyUtil;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.sender.MailSender;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Setter;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * EMail builder and sender.
 * <p>
 * To create a new email, please use {@link MailService}'s createMail methods.
 * <p>
 * To send the message, please use {@link #send()}.
 * <p>
 * NOTE: This is basically a reimplementation of the class <code>org.compiere.util.EMail</code> which was authored (according to the javadoc) by author Joerg Janke.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@JsonAutoDetect(getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class EMail implements Serializable
{

	private static final Logger logger = LogManager.getLogger(EMail.class);
	@Nullable @Setter private MailSender mailSender;

	@JsonProperty("mailbox")
	private final Mailbox _mailbox;
	@JsonIgnore
	private transient EMailAddress _from;

	/**
	 * To Address
	 */
	@JsonProperty("to")
	private final List<EMailAddress> _to = new ArrayList<>();
	@JsonIgnore
	private final List<InternetAddress> _toAddresses = new ArrayList<>();
	/**
	 * CC Addresses
	 */
	@JsonProperty("cc")
	private final List<EMailAddress> _cc = new ArrayList<>();
	@JsonIgnore
	private final List<InternetAddress> _ccAddresses = new ArrayList<>();
	/**
	 * BCC Addresses
	 */
	@JsonProperty("bcc")
	private final List<EMailAddress> _bcc = new ArrayList<>();
	@JsonIgnore
	private final List<InternetAddress> _bccAddresses = new ArrayList<>();
	/**
	 * Reply To Address
	 */
	@JsonProperty("replyTo")
	private EMailAddress _replyTo;
	@JsonIgnore
	private InternetAddress _replyToAddress;

	private boolean _debugMode = false;

	@JsonIgnore
	@Nullable
	private InternetAddress _debugMailToAddress;

	@JsonIgnore
	@Nullable
	private ILoggable _debugLoggable;

	/**
	 * Mail Subject
	 */
	@JsonProperty("subject")
	private String _subject;
	/**
	 * Mail Plain Message
	 */
	@JsonProperty("messageText")
	private String _messageText;
	/**
	 * Mail HTML Message
	 */
	@JsonProperty("messageHTML")
	private String _messageHTML;
	/**
	 * Attachments
	 */
	@JsonProperty("attachments")
	private final List<EMailAttachment> _attachments = new ArrayList<>();

	//
	// Status
	@JsonIgnore
	private transient boolean _valid = false;
	@JsonIgnore
	private transient EMailSentStatus _sentStatus = EMailSentStatus.NOT_SENT;

	@JsonIgnore
	private boolean _forceRealEmailRecipients = false;

	EMail(
			@NonNull final Mailbox mailbox,
			@Nullable final EMailAddress to,
			@Nullable final String subject,
			@Nullable final String message,
			final boolean html)
	{
		this(mailbox);

		addTo(to);

		if (Check.isEmpty(subject, true))
		{
			setSubject(".");    // pass validation
		}
		else
		{
			setSubject(subject);
		}

		if (!Check.isEmpty(message, true))
		{
			if (html)
			{
				setMessageHTML(message);
			}
			else
			{
				setMessageText(message);
			}
		}

		updateValidStatus();
	}

	@JsonCreator
	private EMail(
			@JsonProperty("mailbox") final Mailbox mailbox,
			@JsonProperty("to") final List<EMailAddress> to,
			@JsonProperty("cc") final List<EMailAddress> cc,
			@JsonProperty("bcc") final List<EMailAddress> bcc,
			@JsonProperty("replyTo") final EMailAddress replyTo,
			@JsonProperty("subject") final String subject,
			@JsonProperty("messageText") final String messageText,
			@JsonProperty("messageHTML") final String messageHTML,
			@JsonProperty("attachments") final List<EMailAttachment> attachments
	)
	{
		this(mailbox);

		if (to != null && !to.isEmpty())
		{
			to.forEach(this::addTo);
		}
		if (cc != null && !cc.isEmpty())
		{
			cc.forEach(this::addCc);
		}
		if (bcc != null && !bcc.isEmpty())
		{
			bcc.forEach(this::addBcc);
		}

		if (replyTo != null)
		{
			setReplyTo(replyTo);
		}

		_subject = subject;
		_messageText = messageText;
		_messageHTML = messageHTML;

		if (attachments != null && !attachments.isEmpty())
		{
			attachments.forEach(this::addAttachment);
		}

		updateValidStatus();
	}

	/**
	 * Base constructor
	 */
	private EMail(@NonNull final Mailbox mailbox)
	{
		_mailbox = mailbox;
		setFrom(mailbox.getEmail());
	}

	public void setDebugMode(final boolean debugMode) {this._debugMode = debugMode;}

	public boolean isDebugMode() {return _debugMode;}

	public void setDebugMailToAddress(@Nullable final InternetAddress debugMailToAddress) {this._debugMailToAddress = debugMailToAddress;}

	@Nullable public InternetAddress getDebugMailToAddress() {return _forceRealEmailRecipients ? null : _debugMailToAddress;}

	public void setDebugLoggable(final ILoggable loggable) {this._debugLoggable = loggable;}

	@Nullable public ILoggable getDebugLoggable() {return _debugLoggable;}

	public EMailSentStatus send()
	{
		final ILoggable debugLoggable = getDebugLoggable();
		if (debugLoggable != null)
		{
			debugLoggable.addLog("Sending {}", this);
		}

		//
		// Reset status
		setSentStatus(EMailSentStatus.NOT_SENT);

		//
		// Validate email fields
		final BooleanWithReason valid = updateValidStatus();
		if (valid.isFalse())
		{
			return setSentStatus(EMailSentStatus.invalid(valid.getReason()));
		}

		//
		// Send the email now
		final MailSender mailSender = Check.assumeNotNull(this.mailSender, "mail sender is set");
		final EMailSentStatus sentStatus = mailSender.send(this);
		return setSentStatus(sentStatus);
	}    // send

	private EMailSentStatus setSentStatus(@NonNull final EMailSentStatus status)
	{
		final ILoggable debugLoggable = getDebugLoggable();
		if (debugLoggable != null)
		{
			debugLoggable.addLog("Got sent status {}", status);
		}

		_sentStatus = status;
		return status;
	}

	public EMailSentStatus getLastSentStatus()
	{
		return _sentStatus;
	}

	public Mailbox getMailbox()
	{
		return _mailbox;
	}

	public EMailAddress getFrom()
	{
		return _from;
	}

	private void setFrom(@Nullable final EMailAddress from)
	{
		if (from == null)
		{
			markInvalid();
		}
		else
		{
			_from = from;
		}
	}

	public void addTo(@Nullable final EMailAddress to)
	{
		if (to == null)
		{
			return;
		}

		// Skip address if already added
		if (_to.contains(to))
		{
			return;
		}

		final InternetAddress ia;
		try
		{
			ia = to.toInternetAddress();
		}
		catch (final Exception e)
		{
			logger.warn("Invalid To address: {}", to, e);
			markInvalid();
			return;
		}

		_to.add(to);
		_toAddresses.add(ia);
	}

	@Nullable
	public EMailAddress getTo()
	{
		return _to.isEmpty() ? null : _to.get(0);
	}

	public List<InternetAddress> getTos()
	{
		return ImmutableList.copyOf(_toAddresses);
	}

	public void addCc(@Nullable final EMailAddress cc)
	{
		if (cc == null)
		{
			return;
		}

		final InternetAddress ia;
		try
		{
			ia = cc.toInternetAddress();
		}
		catch (final Exception e)
		{
			logger.warn("Invalid CC address: {}", cc, e);
			return;
		}

		_cc.add(cc);
		_ccAddresses.add(ia);
	}

	public List<InternetAddress> getCcs()
	{
		return ImmutableList.copyOf(_ccAddresses);
	}

	public void addBcc(@Nullable final EMailAddress bcc)
	{
		if (bcc == null)
		{
			return;
		}
		final InternetAddress ia;
		try
		{
			ia = bcc.toInternetAddress();
		}
		catch (final Exception e)
		{
			logger.warn("Invalid BCC address: {}", bcc, e);
			return;
		}

		_bcc.add(bcc);
		_bccAddresses.add(ia);
	}

	public List<InternetAddress> getBccs()
	{
		return ImmutableList.copyOf(_bccAddresses);
	}

	public void setReplyTo(@Nullable final EMailAddress replyTo)
	{
		if (replyTo == null)
		{
			return;
		}
		final InternetAddress ia;
		try
		{
			ia = replyTo.toInternetAddress();
		}
		catch (final Exception e)
		{
			logger.warn("Invalid ReplyTo address: {}", replyTo, e);
			return;
		}

		_replyTo = replyTo;
		_replyToAddress = ia;
	}

	public InternetAddress getReplyTo()
	{
		return _replyToAddress;
	}

	public void setSubject(@Nullable final String subject)
	{
		final String subjectNorm = StringUtils.trimBlankToNull(subject);
		if (subjectNorm == null)
		{
			markInvalid();
		}
		else
		{
			_subject = subjectNorm;
		}
	}

	public String getSubject()
	{
		return _subject;
	}

	public void setMessageText(final String messageText)
	{
		if (Check.isBlank(messageText))
		{
			markInvalid();
		}
		else
		{
			_messageText = messageText;
			if (!_messageText.endsWith("\n"))
			{
				_messageText += "\n";
			}
		}
	}

	/**
	 * @return message text with CRLF line endings.
	 */
	@JsonIgnore
	public String getMessageCRLF()
	{
		if (_messageText == null)
		{
			return "";
		}

		final char[] chars = _messageText.toCharArray();
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++)
		{
			final char c = chars[i];
			if (c == '\n')
			{
				final int previous = i - 1;
				if (previous >= 0 && chars[previous] == '\r')
				{
					sb.append(c);
				}
				else
				{
					sb.append("\r\n");
				}
			}
			else
			{
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public void setMessageHTML(final String messageHtml)
	{
		if (Check.isEmpty(messageHtml, true))
		{
			markInvalid();
		}
		else
		{
			_messageHTML = messageHtml;
			if (!_messageHTML.endsWith("\n"))
			{
				_messageHTML += "\n";
			}
		}
	}

	public String getMessageHTML()
	{
		return _messageHTML;
	}

	public boolean isHtmlMessage()
	{
		return !Check.isBlank(getMessageHTML());
	}

	public void addAttachment(final File file)
	{
		if (file == null)
		{
			logger.warn("Skip adding file attachment because file is null");
			return;
		}
		addAttachment(EMailAttachment.of(file));
	}

	public void addAttachment(final URI uri)
	{
		if (uri == null)
		{
			logger.warn("Skip adding URL attachment because URL is null");
			return;
		}
		addAttachment(EMailAttachment.of(uri));
	}

	public void addAttachment(final String filename, final byte[] content)
	{
		final EMailAttachment attachment = EMailAttachment.ofNullable(filename, content);
		if (attachment == null)
		{
			logger.warn("Skip adding byte attachment because the content is empty for {}", filename);
			return;
		}

		addAttachment(attachment);

	}

	public void addAttachment(@NonNull final EMailAttachment emailAttachment)
	{
		_attachments.add(emailAttachment);
	}

	public String getCharsetName()
	{
		// Local Character Set
		String charSetName = Ini.getCharset().name();
		if (EmptyUtil.isBlank(charSetName))
		{
			charSetName = "iso-8859-1";    // WebEnv.ENCODING - alternative iso-8859-1
		}
		return charSetName;
	}

	/**
	 * @return true if email is valid and can be sent
	 */
	public boolean isValid()
	{
		return _valid;
	}

	private void markInvalid()
	{
		_valid = false;
	}

	private void markValid()
	{
		_valid = true;
	}

	public BooleanWithReason updateValidStatus()
	{
		final BooleanWithReason valid = checkValid();
		if (valid.isTrue())
		{
			markValid();
		}
		else
		{
			logger.warn("Invalid mail data: {}", valid.getReason().getDefaultValue());
			markInvalid();
		}

		return valid;
	}

	/**
	 * Checks if the email is valid and can be sent.
	 * <p>
	 * NOTE: this method is NOT setting the {@link #isValid()} flag.
	 */
	private BooleanWithReason checkValid()
	{
		boolean valid = true;
		final StringBuilder notValidReasonCollector = new StringBuilder();

		// From
		final EMailAddress from = getFrom();
		if (!isValidAddress(from))
		{
			final String errmsg = "No From address";
			if (!notValidReasonCollector.isEmpty())
			{
				notValidReasonCollector.append("; ");
			}
			notValidReasonCollector.append(errmsg);
			valid = false;
		}

		// To
		final List<InternetAddress> toList = getTos();
		if (toList.isEmpty())
		{
			final String errmsg = "No To addresses";
			if (!notValidReasonCollector.isEmpty())
			{
				notValidReasonCollector.append("; ");
			}
			notValidReasonCollector.append(errmsg);
			valid = false;
		}
		else
		{
			for (final InternetAddress to : toList)
			{
				if (!isValidAddress(to))
				{
					final String errmsg = "To address is invalid (" + to + ")";
					if (!notValidReasonCollector.isEmpty())
					{
						notValidReasonCollector.append("; ");
					}
					notValidReasonCollector.append(errmsg);
					valid = false;
				}
			}
		}

		// Subject
		final String subject = getSubject();
		if (Check.isEmpty(subject, true))
		{
			final String errmsg = "Subject is empty";
			if (!notValidReasonCollector.isEmpty())
			{
				notValidReasonCollector.append("; ");
			}
			notValidReasonCollector.append(errmsg);
			valid = false;
		}

		return valid ? BooleanWithReason.TRUE : BooleanWithReason.falseBecause(notValidReasonCollector.toString());
	}

	private static boolean isValidAddress(final EMailAddress emailAddress)
	{
		if (emailAddress == null)
		{
			return false;
		}

		try
		{
			return isValidAddress(emailAddress.toInternetAddress());
		}
		catch (final AddressException e)
		{
			return false;
		}
	}

	private static boolean isValidAddress(final InternetAddress emailAddress)
	{
		if (emailAddress == null)
		{
			return false;
		}

		final String addressStr = emailAddress.getAddress();
		return addressStr != null
				&& !addressStr.isEmpty()
				&& addressStr.indexOf(' ') < 0;
	}

	/**
	 * @return attachments array or empty array. This method will never return null.
	 */
	public List<EMailAttachment> getAttachments()
	{
		return ImmutableList.copyOf(_attachments);
	}

	/**
	   *  Do send the mail to the respective mail address, even if the {@code DebugMailTo} SysConfig is set.
	   */
	public void forceRealEmailRecipients()
	{
		_forceRealEmailRecipients = true;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("to", _to)
				.add("cc", _cc.isEmpty() ? null : _cc)
				.add("bcc", _bcc.isEmpty() ? null : _bcc)
				.add("replyTo", _replyTo)
				.add("subject", _subject)
				.add("attachments", _attachments.isEmpty() ? null : _attachments)
				.add("mailbox", _mailbox)
				.toString();
	}
}    // EMail
