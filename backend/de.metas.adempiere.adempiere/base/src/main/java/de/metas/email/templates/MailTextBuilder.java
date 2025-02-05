package de.metas.email.templates;

import com.google.common.base.Joiner;
import de.metas.adempiere.service.IVariableParserBL;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

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
 * Builds mail text messages from templates using the provided context values.
 *
 * @author metas-dev <dev@metas-fresh.com>
 */
public final class MailTextBuilder
{
	public static MailTextBuilder newInstance(@NonNull final MailTemplate mailTemplate)
	{
		return new MailTextBuilder(mailTemplate);
	}

	// services
	private final transient IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final transient IUserDAO bpartnerContactsRepo = Services.get(IUserDAO.class);
	private final transient IVariableParserBL variableParserBL = Services.get(IVariableParserBL.class);

	private final MailTemplate _mailTemplate;

	//
	// Parameters
	private I_C_BPartner _bpartner = null;
	private I_AD_User _bpartnerContact = null;
	private Object _record = null;
	private String _adLanguage = null;
	private final Map<String, ITranslatableString> _customVariables = new HashMap<>();

	//
	// Cache
	private String _adLanguageEffective = null;
	private final Map<String, String> _text2parsedText = new HashMap<>();

	private MailTextBuilder(@NonNull final MailTemplate mailTemplate)
	{
		_mailTemplate = mailTemplate;
	}

	private MailTemplate getMailTemplate()
	{
		return _mailTemplate;
	}
	
	public MailText build()
	{
		return MailText.builder()
				.adLanguage(getAdLanguage())
				.mailHeader(getMailHeader())
				.fullMailText(getFullMailText())
				.html(isHtml())
				.build();
	}

	/**
	 * @return true if the mail texts are HTMLs; false if they are plain text.
	 */
	private boolean isHtml()
	{
		return getMailTemplate().isHtml();
	}

	/**
	 * @return parsed/translated mail header
	 */
	private String getMailHeader()
	{
		final String mailHeader = getMailTemplate()
				.getMailHeader()
				.translate(getAdLanguage());
		return parseText(mailHeader);
	}

	/**
	 * @return parsed/translated mail full content
	 */
	private String getFullMailText()
	{
		final MailTemplate mailTemplate = getMailTemplate();
		final String adLanguage = getAdLanguage();

		final String mailText = mailTemplate.getMailText().translate(adLanguage);
		final String mailText2 = mailTemplate.getMailText2().translate(adLanguage);
		final String mailText3 = mailTemplate.getMailText3().translate(adLanguage);

		final String fullMailText = Joiner.on("\n")
				.skipNulls()
				.join(mailText, mailText2, mailText3);

		return parseText(fullMailText);
	}	// getMailText

	public String getMailText()
	{
		final String mailText = getMailTemplate()
				.getMailText()
				.translate(getAdLanguage());
		return parseText(mailText);
	}	// getMailText

	/**
	 * Parse Text
	 *
	 * @param text text
	 * @return parsed text
	 */
	private String parseText(final String text)
	{
		if (text == null)
		{
			return "";
		}
		if (text.indexOf('@') < 0)
		{
			return text;
		}

		if (_text2parsedText.containsKey(text))
		{
			return _text2parsedText.get(text);
		}

		String textParsed = text;
		textParsed = parseTextUsingContext(textParsed, getRecord()); // first parse the record values
		textParsed = parseTextUsingContext(textParsed, getBPartnerContact());
		textParsed = parseTextUsingContext(textParsed, getBPartner());
		_text2parsedText.put(text, textParsed); // cache it
		//
		return textParsed;
	}	// parse

	private String parseTextUsingContext(@NonNull final String text, @Nullable final Object context)
	{
		if (context == null)
		{
			return text;
		}
		if (text.indexOf('@') < 0)
		{
			return text;
		}

		String inStr = text;
		String token;
		final StringBuilder outStr = new StringBuilder();

		int i = inStr.indexOf('@');
		while (i >= 0)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i + 1, inStr.length());	// from first @

			final int j = inStr.indexOf('@');						// next @
			if (j < 0)      										// no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			token = inStr.substring(0, j);
			outStr.append(parseVariable(token, context));		// replace context

			inStr = inStr.substring(j + 1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}

		outStr.append(inStr);           					// add remainder
		return outStr.toString();
	}	// parse

	/**
	 * Parse Variable
	 *
	 * @return translated variable or if not found the original tag
	 */
	private String parseVariable(
			@NonNull final String variable,
			@Nullable final Object context)
	{
		if (_customVariables.containsKey(variable))
		{
			final ITranslatableString valueTrl = _customVariables.get(variable);
			return valueTrl.translate(getAdLanguage());
		}

		final Object value = variableParserBL.resolveVariable(variable, context, "@" + variable + "@");
		return value != null ? value.toString() : "";
	}

	public MailTextBuilder bpartnerContact(final I_AD_User bpartnerContact)
	{
		_bpartnerContact = bpartnerContact;
		invalidateCache();
		return this;
	}

	public MailTextBuilder bpartnerContact(final UserId bpartnerContactId)
	{
		final I_AD_User bpartnerContact = bpartnerContactsRepo.getById(bpartnerContactId);
		return bpartnerContact(bpartnerContact);
	}

	public MailTextBuilder bpartnerContact(@NonNull final BPartnerContactId bpartnerContactId)
	{
		bpartner(bpartnerContactId.getBpartnerId());
		bpartnerContact(bpartnerContactId.getUserId());
		return this;
	}

	private I_AD_User getBPartnerContact()
	{
		return _bpartnerContact;
	}

	public MailTextBuilder bpartner(final I_C_BPartner bpartner)
	{
		_bpartner = bpartner;
		invalidateCache();
		return this;
	}

	public MailTextBuilder bpartner(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		return bpartner(bpartner);
	}

	private I_C_BPartner getBPartner()
	{
		return _bpartner;
	}

	@NonNull
	private String getAdLanguage()
	{
		String adLanguageEffective = _adLanguageEffective;
		if (adLanguageEffective == null)
		{
			adLanguageEffective = _adLanguageEffective = computeEffectiveAdLanguage();
		}
		return adLanguageEffective;
	}

	@NonNull
	private String computeEffectiveAdLanguage()
	{
		if (_adLanguage != null)
		{
			return _adLanguage;
		}

		//
		// Contact/User Language
		final I_AD_User user = getBPartnerContact();
		if (user != null && !Check.isEmpty(user.getAD_Language(), true))
		{
			final String userAdLanguage = StringUtils.trimBlankToNull(user.getAD_Language());
			if (userAdLanguage != null)
			{
				return userAdLanguage;
			}
		}

		//
		// BPartner Language
		final I_C_BPartner bpartner = getBPartner();
		if (bpartner != null)
		{
			final String bpAdLanguage = StringUtils.trimBlankToNull(bpartner.getAD_Language());
			if (bpAdLanguage != null)
			{
				return bpAdLanguage;
			}
		}

		//
		// Fallback to system base language
		return Language.getBaseAD_Language();
	}

	/** Optionally, set the AD_Language to be used. If not set, the partner language will be used */
	public MailTextBuilder adLanguage(final String adLanguage)
	{
		_adLanguage = adLanguage;
		invalidateCache();
		return this;
	}

	private void invalidateCache()
	{
		_adLanguageEffective = null;
		_text2parsedText.clear();
	}

	public MailTextBuilder record(final Object record)
	{
		_record = record;
		invalidateCache();

		return this;
	}

	public MailTextBuilder recordAndUpdateBPartnerAndContact(final Object record)
	{
		record(record);

		if (record != null)
		{
			updateBPartnerAndContactFromRecord(record);
		}

		return this;
	}

	private void updateBPartnerAndContactFromRecord(@NonNull final Object record)
	{
		final BPartnerId bpartnerId = extractBPartnerId(record);
		if (bpartnerId != null)
		{
			bpartner(bpartnerId);
		}

		final UserId bpartnerContactId = extractBPartnerContactId(record);
		if (bpartnerContactId != null)
		{
			bpartnerContact(bpartnerContactId);
		}
	}

	private BPartnerId extractBPartnerId(final Object record)
	{
		final Object bpartnerIdObj = InterfaceWrapperHelper.getValueOrNull(record, "C_BPartner_ID");
		if (!(bpartnerIdObj instanceof Integer))
		{
			return null;
		}

		final int bpartnerRepoId = (Integer)bpartnerIdObj;
		return BPartnerId.ofRepoIdOrNull(bpartnerRepoId);
	}

	private UserId extractBPartnerContactId(final Object record)
	{
		final Integer userIdObj = InterfaceWrapperHelper.getValueOrNull(record, "AD_User_ID");
		if (!(userIdObj instanceof Integer))
		{
			return null;
		}

		final int userRepoId = userIdObj.intValue();
		return UserId.ofRepoIdOrNull(userRepoId);
	}

	private Object getRecord()
	{
		return _record;
	}

	public MailTextBuilder customVariable(@NonNull final String name, @Nullable final String value)
	{
		return customVariable(name, TranslatableStrings.anyLanguage(value));
	}

	public MailTextBuilder customVariable(@NonNull final String name, @NonNull final ITranslatableString value)
	{
		_customVariables.put(name, value);
		invalidateCache();
		return this;
	}

}
