package de.metas.i18n;

import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.util.Env;

import javax.annotation.Nullable;

/**
 * Internal translated message representation (immutable)
 */
@ToString
public final class Message
{
	/**
	 * Separator between Msg and optional Tip
	 */
	private static final String SEPARATOR = Env.NL + Env.NL;

	/**
	 * Empty message
	 */
	public static final Message EMPTY = new Message();
	private static final AdMessageKey EMPTY_AD_Message = AdMessageKey.of("$EMPTY$");

	/**
	 * @return instance for given message text and tip
	 */
	public static Message ofTextAndTip(
			@NonNull final AdMessageId adMessageId,
			@NonNull final AdMessageKey adMessage,
			@NonNull final ImmutableTranslatableString msgText,
			@NonNull final ImmutableTranslatableString msgTip)
	{
		return new Message(adMessageId, adMessage, msgText, msgTip, false);
	}

	public static Message ofMissingADMessage(@Nullable final String text)
	{
		final String textNorm = StringUtils.trimBlankToNull(text);
		if (textNorm == null)
		{
			return EMPTY;
		}

		return new Message(
				null,
				AdMessageKey.of(textNorm),
				ImmutableTranslatableString.ofDefaultValue(text),
				null,
				true);
	}

	@Nullable @Getter private final AdMessageId adMessageId;
	@NonNull @Getter private final AdMessageKey adMessage;
	@NonNull private final ITranslatableString msgText;
	@Nullable private final ITranslatableString msgTip;
	@NonNull private final ITranslatableString msgTextAndTip;
	@Getter private final boolean missing;

	private Message(
			@Nullable final AdMessageId adMessageId,
			@NonNull final AdMessageKey adMessage,
			@NonNull final ImmutableTranslatableString msgText,
			@Nullable final ImmutableTranslatableString msgTip,
			final boolean missing)
	{
		this.adMessageId = adMessageId;
		this.adMessage = adMessage;
		this.msgText = msgText;
		this.msgTip = msgTip;

		if (!TranslatableStrings.isBlank(this.msgTip))            // messageTip on next line, if exists
		{
			this.msgTextAndTip = TranslatableStrings.builder()
					.append(this.msgText)
					.append(SEPARATOR)
					.append(this.msgTip)
					.build();
		}
		else
		{
			this.msgTextAndTip = msgText;
		}

		this.missing = missing;
	}

	private Message()
	{
		this.adMessageId = null;
		this.adMessage = EMPTY_AD_Message;
		this.msgText = TranslatableStrings.empty();
		this.msgTip = null;
		this.msgTextAndTip = this.msgText;
		this.missing = true;
	}

	public String getMsgText(@NonNull final String adLanguage)
	{
		return msgText.translate(adLanguage);
	}

	public String getMsgTip(@NonNull final String adLanguage)
	{
		return msgTip != null ? msgTip.translate(adLanguage) : "";
	}

	public String getMsgTextAndTip(@NonNull final String adLanguage)
	{
		return msgTextAndTip.translate(adLanguage);
	}
}
