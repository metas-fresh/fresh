package de.metas.i18n.impl;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import de.metas.i18n.AdMessageId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;

<<<<<<< HEAD
=======
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
public class PlainMsgBL implements IMsgBL
{
	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message)
	{
		return adLanguage + "_" + message.toAD_Message();
	}

	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message, final Object[] params)
	{
		return adLanguage + "_" + message.toAD_Message() + "_" + Arrays.toString(params);
	}

	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message, final List<Object> params)
	{
		return adLanguage + "_" + message.toAD_Message() + "_" + params;
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage)
	{
		return adMessage.toAD_Message();
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage, final boolean text)
	{
		return adMessage.toAD_Message() + "_" + (text ? "Text" : "Tooltip");
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage, final Object[] params)
	{
		if (params == null || params.length == 0)
		{
			return adMessage.toAD_Message();
		}
		return adMessage + "_" + Arrays.toString(params);
	}

	@Override
	public String getMsg(@NonNull final AdMessageKey adMessage, final List<Object> params)
	{
		if (params == null || params.isEmpty())
		{
			return adMessage.toAD_Message();
		}
		return adMessage.toAD_Message() + "_" + params;
	}

	@Override
	public Map<String, String> getMsgMap(final String adLanguage, final String prefix, final boolean removePrefix)
	{
		return ImmutableMap.of();
	}

	@Override
	public String parseTranslation(final Properties ctx, final String message)
	{
		return message;
	}

	@Override
	public String parseTranslation(final String adLanguage, final String message)
	{
		return message;
	}

	@Override
	public String translate(final Properties ctx, final String text)
	{
		return text;
	}

	@Override
	public String translate(final String adLanguage, final String text)
	{
		return text;
	}

	@Override
	public String translate(final Properties ctx, final String text, final boolean isSOTrx)
	{
		return text;
	}

	@Override
	public ITranslatableString translatable(final String text)
	{
		return TranslatableStrings.constant(text);
	}

	@Override
	public ITranslatableString getTranslatableMsgText(@NonNull final AdMessageKey adMessage, final Object... msgParameters)
	{
		if (msgParameters == null || msgParameters.length == 0)
		{
			return TranslatableStrings.constant(adMessage.toAD_Message());
		}
		else
		{
			return TranslatableStrings.constant(adMessage.toAD_Message() + " - " + Joiner.on(", ").join(msgParameters));
		}
	}

	@Override
	public void cacheReset()
	{
		// nothing
	}
<<<<<<< HEAD
=======

	@Override
	public String getBaseLanguageMsg(@NonNull final AdMessageKey adMessage, @Nullable final Object... msgParameters)
	{
		return TranslatableStrings.adMessage(adMessage, msgParameters)
				.translate(Language.getBaseAD_Language());
	}

	@Override
	public Optional<AdMessageId> getIdByAdMessage(@NonNull final AdMessageKey value)
	{
		return Optional.empty();
	}

	@Override
	public boolean isMessageExists(final AdMessageKey adMessage)
	{
		return false;
	}

	@Override
	public Optional<AdMessageKey> getAdMessageKeyById(final AdMessageId adMessageId)
	{
		return Optional.empty();
	}
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
}
