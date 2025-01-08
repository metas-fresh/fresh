package de.metas.i18n;

import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.currency.Amount;
import de.metas.logging.LogManager;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.text.MessageFormat;

@UtilityClass
class MessageFormatter
{
	private static final Logger s_log = LogManager.getLogger(MessageFormatter.class);

	static String format(@NonNull final String adLanguage, @NonNull final Message message, @Nullable final Object[] args)
	{
		final String messageStr = message.getMsgTextAndTip(adLanguage);
		if (args == null || args.length == 0)
		{
			return messageStr;
		}

		String retStr = messageStr;
		try
		{
			normalizeArgsBeforeFormat(args, adLanguage);
			retStr = MessageFormat.format(messageStr, args);    // format string
		}
		catch (final Exception e)
		{
			s_log.error(messageStr, e);
		}
		return retStr;
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
		else if (arg instanceof final Amount amount)
		{
			return TranslatableStrings.amount(amount).translate(adLanguage);
		}
		else if (arg instanceof final ReferenceListAwareEnum referenceListAwareEnum)
		{
			return normalizeSingleArgumentBeforeFormat_ReferenceListAwareEnum(referenceListAwareEnum, adLanguage);
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

			if (!result.isEmpty())
			{
				result.append(", ");
			}
			result.append(itemNormStr);
		}

		return result.toString();

	}
}
