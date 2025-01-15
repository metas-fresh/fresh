package de.metas.i18n;

import com.google.common.annotations.VisibleForTesting;
import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.currency.Amount;
import de.metas.logging.LogManager;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.text.MessageFormat;

@UtilityClass
public class MessageFormatter
{
	private static final Logger s_log = LogManager.getLogger(MessageFormatter.class);

	static String format(@NonNull final String adLanguage, @NonNull final Message message, @Nullable final Object[] args)
	{
		final String messageStr = message.getMsgTextAndTip(adLanguage);
		return format0(adLanguage, messageStr, args);
	}

	public static String format(@Nullable final String message, @Nullable final Object[] args)
	{
		final String messageNorm = normalizeToJavaMessageFormat(message);
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		return format0(adLanguage, messageNorm, args);
	}

	private static String format0(@NonNull final String adLanguage, @NonNull final String message, @Nullable final Object[] args)
	{
		if (args == null || args.length == 0)
		{
			return message;
		}

		String retStr = message;
		try
		{
			normalizeArgsBeforeFormat(args, adLanguage);
			retStr = MessageFormat.format(message, args);    // format string
		}
		catch (final Exception e)
		{
			s_log.error(message, e);
		}
		return retStr;
	}

	@VisibleForTesting
	static String normalizeToJavaMessageFormat(@Nullable final String text)
	{
		if (text == null)
		{
			return "";
		}
		if (text.isEmpty())
		{
			return text;
		}

		int firstIdx = text.indexOf("{}");
		if (firstIdx < 0)
		{
			return text;
		}

		String inStr = text;
		int idx = firstIdx;
		final StringBuilder outStr = new StringBuilder();
		int nextPlaceholderIndex = 0;
		while (idx != -1)
		{
			outStr.append(inStr, 0, idx);            // up to {}
			inStr = inStr.substring(idx + 2);    // continue after current {}

			final int placeholderIndex = nextPlaceholderIndex;
			nextPlaceholderIndex++;
			outStr.append("{").append(placeholderIndex).append("}");

			idx = inStr.indexOf("{}");
		}

		outStr.append(inStr);                            // add remainder
		return outStr.toString();
	}

	private static void normalizeArgsBeforeFormat(final Object[] args, final String adLanguage)
	{
		if (args == null || args.length == 0)
		{
			return;
		}

		for (int i = 0; i < args.length; i++)
		{
			final Object arg = args[i];
			final Object argNorm = normalizeSingleArgumentBeforeFormat(arg, adLanguage);
			args[i] = argNorm;
		}
	}

	private static Object normalizeSingleArgumentBeforeFormat(@Nullable final Object arg, final String adLanguage)
	{
		if (arg == null)
		{
			return null;
		}
		else if (arg instanceof ITranslatableString)
		{
			return ((ITranslatableString)arg).translate(adLanguage);
		}
		else if (arg instanceof Amount)
		{
			return TranslatableStrings.amount((Amount)arg).translate(adLanguage);
		}
		else if (arg instanceof ReferenceListAwareEnum)
		{
			return normalizeSingleArgumentBeforeFormat_ReferenceListAwareEnum((ReferenceListAwareEnum)arg, adLanguage);
		}
		else if (arg instanceof Iterable)
		{
			@SuppressWarnings("unchecked") final Iterable<Object> iterable = (Iterable<Object>)arg;
			return normalizeSingleArgumentBeforeFormat_Iterable(iterable, adLanguage);
		}
		else
		{
			return arg;
		}
	}

	private static Object normalizeSingleArgumentBeforeFormat_ReferenceListAwareEnum(
			@NonNull final ReferenceListAwareEnum referenceListAwareEnum,
			final String adLanguage)
	{
		final int adReferenceId = ReferenceListAwareEnums.getAD_Reference_ID(referenceListAwareEnum);
		if (adReferenceId > 0)
		{
			final ADReferenceService adReferenceService = ADReferenceService.get();
			final ADRefListItem adRefListItem = adReferenceService.retrieveListItemOrNull(adReferenceId, referenceListAwareEnum.getCode());
			if (adRefListItem != null)
			{
				return adRefListItem.getName().translate(adLanguage);
			}
		}

		// Fallback
		return referenceListAwareEnum.toString();
	}

	private static String normalizeSingleArgumentBeforeFormat_Iterable(
			@NonNull final Iterable<Object> iterable,
			final String adLanguage)
	{
		final StringBuilder result = new StringBuilder();

		for (final Object item : iterable)
		{
			String itemNormStr;
			try
			{
				final Object itemNormObj = normalizeSingleArgumentBeforeFormat(item, adLanguage);
				itemNormStr = itemNormObj != null ? itemNormObj.toString() : "-";
			}
			catch (Exception ex)
			{
				s_log.warn("Failed normalizing argument `{}`. Using toString().", item, ex);
				itemNormStr = item.toString();
			}

			if (!(result.length() == 0))
			{
				result.append(", ");
			}
			result.append(itemNormStr);
		}

		return result.toString();

	}
}
