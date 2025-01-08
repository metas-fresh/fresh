package de.metas.i18n.impl;

<<<<<<< HEAD
import java.util.List;
import java.util.Map;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Properties;

import javax.annotation.Nullable;

import de.metas.i18n.AdMessagesTreeLoader;
import org.compiere.util.Env;

=======
import de.metas.i18n.AdMessageId;
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Msg;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import lombok.NonNull;
<<<<<<< HEAD
=======
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))

/**
 * This implementation delegates to {@link Msg} and is therefore coupled with the database.
 */
@SuppressWarnings("deprecation")
public class MsgBL implements IMsgBL
{
	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message)
	{
		return Msg.getMsg(adLanguage, message.toAD_Message());
	}

	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message, final Object[] params)
	{
		return Msg.getMsg(adLanguage, message.toAD_Message(), params);
	}

	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message, final List<Object> params)
	{
		return Msg.getMsg(
				adLanguage,
				message.toAD_Message(),
				params != null && !params.isEmpty() ? params.toArray() : null);
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage)
	{
		return Msg.getMsg(ctx, adMessage.toAD_Message());
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage, @Nullable final Object[] params)
	{
		return Msg.getMsg(ctx, adMessage.toAD_Message(), params);
	}

	@Override
	public String getMsg(final AdMessageKey adMessage, final List<Object> params)
	{
		return getMsg(Env.getCtx(), adMessage, params != null && !params.isEmpty() ? params.toArray() : null);
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage, final boolean text)
	{
		return Msg.getMsg(ctx, adMessage.toAD_Message(), text);
	}

	@Override
	public Map<String, String> getMsgMap(final String adLanguage, final String prefix, final boolean removePrefix)
	{
		return Msg.getMsgMap(adLanguage, prefix, removePrefix);
	}

	@Override
	public String translate(final Properties ctx, final String text)
	{
		return Msg.translate(ctx, text);
	}

	@Override
	public String translate(final String adLanguage, final String text)
	{
		return Msg.translate(adLanguage, text);
	}

	@Override
	public String translate(final Properties ctx, final String text, final boolean isSOTrx)
	{
		return Msg.translate(ctx, text, isSOTrx);
	}

	@Override
	public ITranslatableString translatable(final String text)
	{
		if (Check.isEmpty(text, true))
		{
			return TranslatableStrings.constant(text);
		}
		return new ADElementOrADMessageTranslatableString(text);
	}

	@Override
	public String parseTranslation(final Properties ctx, final String message)
	{
		return Msg.parseTranslation(ctx, message);
	}

	@Override
	public String parseTranslation(final String adLanguage, final String message)
	{
		return Msg.parseTranslation(adLanguage, message);
	}

	@Override
	public ITranslatableString getTranslatableMsgText(@NonNull final AdMessageKey adMessage, final Object... msgParameters)
	{
		return new ADMessageTranslatableString(adMessage, msgParameters);
	}

	@Override
	public void cacheReset()
	{
		Msg.cacheReset();
	}
<<<<<<< HEAD
=======

	@Override
	public String getBaseLanguageMsg(@NonNull final AdMessageKey adMessage, @Nullable final Object... msgParameters)
	{
		return TranslatableStrings.adMessage(adMessage, msgParameters).translate(Language.getBaseAD_Language());
	}

	@Override
	public Optional<AdMessageId> getIdByAdMessage(@NonNull final AdMessageKey adMessage)
	{
		return Msg.toMap().getIdByAdMessage(adMessage);
	}

	@Override
	public boolean isMessageExists(AdMessageKey adMessage)
	{
		return Msg.toMap().isMessageExists(adMessage);
	}

	@Override
	public Optional<AdMessageKey> getAdMessageKeyById(final AdMessageId adMessageId)
	{
		return Msg.toMap().getAdMessageKeyById(adMessageId);
	}
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
}
