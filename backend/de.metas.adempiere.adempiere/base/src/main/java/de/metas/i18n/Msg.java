package de.metas.i18n;

<<<<<<< HEAD
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
=======
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
<<<<<<< HEAD
import de.metas.util.Services;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
=======
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Element;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Reads all Messages and stores them in a HashMap
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version of Jorg Janke
 * @deprecated Please use {@link IMsgBL}
 */
@Deprecated
public final class Msg
{
	private static final Msg instance = new Msg();

	/**
	 * Logger
	 */
	private static final Logger s_log = LogManager.getLogger(Msg.class);
	private final MessagesMapRepository messagesMapRepository = new MessagesMapRepository();

	private static Msg get()
	{
		return instance;
	}

	private Msg()
	{
	}

	private final CCache<String, Element> elementsByElementName = CCache.newLRUCache(I_AD_Element.Table_Name, 500, CCache.EXPIREMINUTES_Never);

	/**
	 * @return given adLanguage if not null or base language
	 */
	private static String notNullOrBaseLanguage(@Nullable final String adLanguage)
	{
		return Check.isBlank(adLanguage) ? Language.getBaseAD_Language() : adLanguage;
	}

	public static MessagesMap toMap() {return get().getMessagesMap();}

	private MessagesMap getMessagesMap()
	{
		return messagesMapRepository.getMap();
	}

	public void reset()
	{
		messagesMapRepository.cacheReset();
		elementsByElementName.reset();
	}

	public static void cacheReset()
	{
		get().reset();
	}

	@Nullable
	private Message lookup(final String text)
	{
		if (text == null)
		{
			return Message.EMPTY;
		}

		//
		// hardcoded trl
		if (text.equals("/") || text.equals("\\"))
		{
			return Message.ofMissingADMessage(File.separator);
		}
		if (text.equals(";") || text.equals(":"))
		{
			return Message.ofMissingADMessage(File.pathSeparator);
		}
		if (Ini.METASFRESH_HOME.equals(text) || Ini.ADEMPIERE_HOME.equals(text))
		{
			return Message.ofMissingADMessage(Ini.getMetasfreshHome());
		}
		if (text.equals("bat") || text.equals("sh"))
		{
			if (System.getProperty("os.name").startsWith("Win"))
			{
				return Message.ofMissingADMessage("bat");
			}
			return Message.ofMissingADMessage("sh");
		}
		if (text.equals("CopyRight"))
		{
			return Message.ofMissingADMessage(Adempiere.getCopyright());
		}

		// metas-tsa: if we are in unit test mode, don't load anything from database, just return same text
		if (org.compiere.Adempiere.isUnitTestMode())
		{
			return Message.ofMissingADMessage(text);
		}

		return getMessagesMap().getByAdMessage(text);
	}

	/**************************************************************************
	 * Get translated text for AD_Message
	 *
	 * @return translated text
	 */
	public static String getMsg(final String adLanguage, final String adMessage)
	{
		return getMessage(adMessage).getMsgTextAndTip(adLanguage);
	}

	private static Message getMessage(final String adMessage)
	{
		if (adMessage == null || adMessage.isEmpty())
		{
			return Message.EMPTY;
		}

		final Message message = get().lookup(adMessage);
		if (message == null)
		{
			s_log.warn("AD_Message not found: {}", adMessage);
			return Message.ofMissingADMessage(adMessage);
		}

		return message;
	}    // getMsg

	/**
	 * Get translated text message for AD_Message
	 *
	 * @param ctx        Context to retrieve language
	 * @param AD_Message - Message Key
	 * @return translated text
	 */
	public static String getMsg(final Properties ctx, final String AD_Message)
	{
		return getMsg(Env.getAD_Language(ctx), AD_Message);
	}   // getMeg

	/**
	 * Get translated text message for AD_Message
	 *
	 * @param language   Language
	 * @param AD_Message - Message Key
	 * @return translated text
	 */
	public static String getMsg(final Language language, final String AD_Message)
	{
		return getMsg(language.getAD_Language(), AD_Message);
	}   // getMeg

	/**
	 * Get translated text message for AD_Message
	 *
	 * @param adLanguage - Language
	 * @param adMessage  - Message Key
	 * @param getText    if true only return Text, if false only return Tip
	 * @return translated text
	 */
	private static String getMsg(final String adLanguage, final String adMessage, final boolean getText)
	{
		final Message message = getMessage(adMessage);
		return getText ? message.getMsgText(adLanguage) : message.getMsgTip(adLanguage);
	}

	/**
	 * Get translated text message for AD_Message
	 *
	 * @param ctx       Context to retrieve language
	 * @param adMessage Message Key
	 * @param getText   if true only return Text, if false only return Tip
	 * @return translated text
	 */
	public static String getMsg(final Properties ctx, final String adMessage, final boolean getText)
	{
		return getMsg(Env.getAD_Language(ctx), adMessage, getText);
	}   // getMsg

	/**
	 * Get clear text for AD_Message with parameters
	 *
	 * @param ctx        Context to retrieve language
	 * @param AD_Message Message key
	 * @param args       MessageFormat arguments
	 * @return translated text
	 * @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(final Properties ctx, final String AD_Message, final Object[] args)
	{
		return getMsg(Env.getAD_Language(ctx), AD_Message, args);
	}    // getMsg

	/**
	 * Get clear text for AD_Message with parameters
	 *
	 * @param language   Language
	 * @param AD_Message Message key
	 * @param args       MessageFormat arguments
	 * @return translated text
	 * @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(final Language language, final String AD_Message, final Object[] args)
	{
		return getMsg(language.getAD_Language(), AD_Message, args);
	}    // getMsg

	/**
	 * Get clear text for AD_Message with parameters
	 *
	 * @param adLanguage Language
	 * @param adMessage  Message key
	 * @param args       MessageFormat arguments
	 * @return translated text
	 * @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(final String adLanguage, final String adMessage, final Object[] args)
	{
		final String adLanguageToUse = notNullOrBaseLanguage(adLanguage);
		final Message message = getMessage(adMessage);
		return MessageFormatter.format(adLanguageToUse, message, args);
	}    // getMsg

<<<<<<< HEAD
	private static String format(@NonNull final String adLanguage, @NonNull final Message message, @Nullable final Object[] args)
	{
		final String messageStr = message.getMsgTextAndTip();
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
		else if (arg instanceof Amount)
		{
			final Amount amount = (Amount)arg;
			return TranslatableStrings.amount(amount).translate(adLanguage);
		}
		else if (arg instanceof ReferenceListAwareEnum)
		{
			final ReferenceListAwareEnum referenceListAwareEnum = (ReferenceListAwareEnum)arg;
			return normalizeSingleArgumentBeforeFormat_ReferenceListAwareEnum(referenceListAwareEnum, adLanguage);
		}
		else if (arg instanceof Iterable)
		{
			@SuppressWarnings("unchecked")
			final Iterable<Object> iterable = (Iterable<Object>)arg;
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
			final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
			final IADReferenceDAO.ADRefListItem adRefListItem = adReferenceDAO.retrieveListItemOrNull(adReferenceId, referenceListAwareEnum.getCode());
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
			String itemNormStr = null;
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

			if (result.length() > 0)
			{
				result.append(", ");
			}
			result.append(itemNormStr);
		}

		return result.toString();

	}

=======
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
	public static Map<String, String> getMsgMap(final String adLanguage, final String prefix, boolean removePrefix)
	{
		return get().getMessagesMap().toStringMap(adLanguage, prefix, removePrefix);
	}

	/**************************************************************************
	 * Get Translation for Element
	 *
	 * @param adLanguage language
	 * @param columnName column name
	 * @param isSOTrx if false PO terminology is used (if exists)
	 * @return Name of the Column or "" if not found
	 */
	public static String getElement(final String adLanguage, final String columnName, final boolean isSOTrx)
	{
		if (columnName == null || columnName.isEmpty())
		{
			return "";
		}

		final String elementName;
		String displayColumnName = I_AD_Element.COLUMNNAME_Name;
		{
			final int idx = columnName.indexOf("/");
			if (idx > 0)
			{
				final String display = columnName.substring(idx + 1);
				if ("Name".equalsIgnoreCase(display))
				{
					displayColumnName = I_AD_Element.COLUMNNAME_Name;
				}
				else if ("PrintName".equalsIgnoreCase(display))
				{
					displayColumnName = I_AD_Element.COLUMNNAME_PrintName;
				}
				else if ("Description".equalsIgnoreCase(display))
				{
					displayColumnName = I_AD_Element.COLUMNNAME_Description;
				}
				else
				{
					s_log.warn("Unknow element field {} in {}", display, columnName);
				}
				elementName = columnName.substring(0, idx);
			}
			else
			{
				elementName = columnName;
			}
		}

		final ITranslatableString text = get().getElement(elementName)
				.getText(displayColumnName, isSOTrx);

		final boolean isBaseLanguage = adLanguage == null || adLanguage.isEmpty() || Env.isBaseLanguage(adLanguage, "AD_Element");
		return isBaseLanguage ? text.getDefaultValue() : text.translate(adLanguage);
	}

	private Element getElement(final String elementName)
	{
		return elementsByElementName.getOrLoad(elementName.toUpperCase(), () -> retrieveElement(elementName));
	}

	private static Element retrieveElement(final String elementName)
	{
		final String sql = "SELECT"
				+ " e.ColumnName"
				//
				+ ", e.Name"
				+ ", e.PO_Name"
				+ ", e.PrintName"
				+ ", e.PO_PrintName"
				+ ", e.Description"
				+ ", e.PO_Description"
				//
				+ ", t.AD_Language"
				+ ", t.Name as TRL_Name"
				+ ", t.PO_Name as TRL_PO_Name"
				+ ", t.PrintName as TRL_PrintName"
				+ ", t.PO_PrintName as TRL_PO_PrintName"
				+ ", t.Description as TRL_Description"
				+ ", t.PO_Description as TRL_PO_Description"
				//
				+ " FROM AD_Element e"
				+ " LEFT OUTER JOIN AD_Element_Trl_Effective_v t ON (t.AD_Element_ID=e.AD_Element_ID)"
				+ " WHERE UPPER(e.ColumnName)=UPPER(?)";
		final Object[] sqlParams = new Object[] { elementName };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			final Element.ElementBuilder elementBuilder = Element.builder().elementName(elementName);
			while (rs.next())
			{
				elementBuilder
						.name(Element.DEFAULT_LANG, rs.getString("Name"))
						.printName(Element.DEFAULT_LANG, rs.getString("PrintName"))
						.description(Element.DEFAULT_LANG, rs.getString("Description"))
						.poName(Element.DEFAULT_LANG, rs.getString("PO_Name"))
						.poPrintName(Element.DEFAULT_LANG, rs.getString("PO_PrintName"))
						.poDescription(Element.DEFAULT_LANG, rs.getString("PO_Description"));

				final String adLanguage = rs.getString("AD_Language");
				elementBuilder
						.name(adLanguage, rs.getString("TRL_Name"))
						.printName(adLanguage, rs.getString("TRL_PrintName"))
						.description(adLanguage, rs.getString("TRL_Description"))
						.poName(adLanguage, rs.getString("TRL_PO_Name"))
						.poPrintName(adLanguage, rs.getString("TRL_PO_PrintName"))
						.poDescription(adLanguage, rs.getString("TRL_PO_Description"));
			}

			return elementBuilder.build();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 * Get Translation for Element using Sales terminology
	 *
	 * @param ctx        context
	 * @param ColumnName column name
	 * @return Name of the Column or "" if not found
	 */
	public static String getElement(final Properties ctx, final String ColumnName)
	{
		return getElement(Env.getAD_Language(ctx), ColumnName, true);
	}   // getElement

	/**
	 * Get Translation for Element
	 *
	 * @param ctx        context
	 * @param ColumnName column name
	 * @param isSOTrx    sales transaction
	 * @return Name of the Column or "" if not found
	 */
	public static String getElement(final Properties ctx, final String ColumnName, final boolean isSOTrx)
	{
		return getElement(Env.getAD_Language(ctx), ColumnName, isSOTrx);
	}   // getElement

	/**************************************************************************
	 * "Translate" text.
	 *
	 * <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 *
	 * If checking AD_Element, the SO terminology is used.
	 *
	 * @param adLanguage Language
	 * @param isSOTrx sales order context
	 * @param text Text - AD_Message or Element Name
	 * @return translated text or original text if not found
	 */
	public static String translate(final String adLanguage, final boolean isSOTrx, final String text)
	{
		if (text == null || text.isEmpty())
		{
			return "";
		}
		final String adLanguageToUse = notNullOrBaseLanguage(adLanguage);

		// Check AD_Message
		final Message message = get().lookup(text);
		if (message != null && !message.isMissing())
		{
			return message.getMsgTextAndTip(adLanguageToUse);
		}

		// Check AD_Element
		String retStr = getElement(adLanguageToUse, text, isSOTrx);
		if (!Check.isEmpty(retStr))
		{
			return retStr.trim();
		}

		// metas: begin: try to find tablename primary key
		// e.g. if we query for "C_Invoice" then "C_Invoice_ID" shall be returned
		if (!text.startsWith("*") && !text.endsWith("_ID"))
		{
			retStr = getElement(adLanguageToUse, text + "_ID", isSOTrx);
			if (!Check.isEmpty(retStr))
			{
				return retStr.trim();
			}
		}
		// metas: end

		// Nothing found
		if (!text.startsWith("*"))
		{
			s_log.warn("NOT found: {}", text);
		}
		return text;
	}    // translate

	/***
	 * "Translate" text (SO Context).
	 *
	 * <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 *
	 * If checking AD_Element, the SO terminology is used.
	 *
	 * @param adLanguage Language
	 * @param text Text - MsgText or Element Name
	 * @return translated text or original text if not found
	 */
	public static String translate(final String adLanguage, final String text)
	{
		return translate(adLanguage, true, text);
	}    // translate

	/**
	 * "Translate" text.
	 *
	 * <pre>
	 * 		- Check AD_Message.AD_Message 	->	MsgText
	 * 		- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 *
	 * @param ctx  Context
	 * @param text Text - MsgText or Element Name
	 * @return translated text or original text if not found
	 */
	public static String translate(final Properties ctx, final String text)
	{
		final boolean isSOTrx = Env.isSOTrx(ctx);
		return translate(ctx, text, isSOTrx);
	}

	public static String translate(final Properties ctx, final String text, final boolean isSOTrx)
	{
		if (text == null || text.isEmpty())
		{
			return text;
		}
		final String s = ctx.getProperty(text);
		if (s != null && !s.isEmpty())
		{
			return s;
		}
		return translate(Env.getAD_Language(ctx), isSOTrx, text);
	}   // translate

	/**
	 * "Translate" text.
	 *
	 * <pre>
	 * 		- Check AD_Message.AD_Message 	->	MsgText
	 * 		- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 *
	 * @param language Language
	 * @param text     Text
	 * @return translated text or original text if not found
	 */
	public static String translate(final Language language, final String text)
	{
		final boolean isSOTrx = false;
		return translate(language.getAD_Language(), isSOTrx, text);
	}   // translate

	/**
	 * Translate elements enclosed in "@" (at sign)
	 *
	 * @param ctx  Context
	 * @param text Text
	 * @return translated text or original text if not found
	 */
	public static String parseTranslation(final Properties ctx, final String text)
	{
		final String adLanguage = Env.getAD_Language(ctx);
		return parseTranslation(adLanguage, text);
	}

	/**
	 * Translate elements enclosed in "@" (at sign)
	 *
	 * @return translated text or original text if not found
	 */
	public static String parseTranslation(final String adLanguage, final String text)
	{
		if (text == null || text.isEmpty())
		{
			return text;
		}

		String inStr = text;
		String token;
		final StringBuilder outStr = new StringBuilder();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));            // up to @
			inStr = inStr.substring(i + 1);    // from first @

			final int j = inStr.indexOf('@');                        // next @
			if (j < 0)                                        // no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			token = inStr.substring(0, j);
			// metas: begin: "@@" shall be interpretated as "@"
			if (token.isEmpty())
			{
				outStr.append("@");
			}
			else
			{
				// metas: end
				outStr.append(translate(adLanguage, token));            // replace context
			}

			inStr = inStr.substring(j + 1);    // from second @
			i = inStr.indexOf('@');
		}

		outStr.append(inStr);                            // add remainder
		return outStr.toString();
	}   // parseTranslation

<<<<<<< HEAD
	/**
	 * Internal translated message representation (immutable)
	 */
	private static final class Message
	{
		/**
		 * Empty message
		 */
		public static final Message EMPTY = ofMissingADMessage("");

		/**
		 * @return instance for given message text and tip
		 */
		public static Message ofTextAndTip(final String adMessage, final String msgText, final String msgTip)
		{
			final boolean missing = false;
			return new Message(adMessage, msgText, msgTip, missing);
		}

		/**
		 * @return instance of given missing adMessage
		 */
		public static Message ofMissingADMessage(final String adMessage)
		{
			final String msgText = adMessage;
			final String msgTip = null; // no tip
			final boolean missing = true;
			return new Message(adMessage, msgText, msgTip, missing);
		}

		private final String adMessage;
		private final String msgText;
		private final String msgTip;
		private final String msgTextAndTip;
		private final boolean missing;

		private Message(@NonNull final String adMessage, final String msgText, final String msgTip, final boolean missing)
		{
			super();
			this.adMessage = adMessage;
			this.msgText = msgText == null ? "" : msgText;
			this.msgTip = msgTip == null ? "" : msgTip;

			final StringBuilder msgTextAndTip = new StringBuilder();
			msgTextAndTip.append(this.msgText);
			if (!Check.isEmpty(this.msgTip, true))            // messageTip on next line, if exists
			{
				msgTextAndTip.append(" ").append(SEPARATOR).append(this.msgTip);
			}
			this.msgTextAndTip = msgTextAndTip.toString();

			this.missing = missing;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("adMessage", adMessage)
					.add("msgText", msgText)
					.add("msgTip", msgTip)
					.add("missing", missing)
					.toString();
		}

		public String getAD_Message()
		{
			return adMessage;
		}

		public String getMsgText()
		{
			return msgText;
		}

		public String getMsgTip()
		{
			return msgTip;
		}

		public String getMsgTextAndTip()
		{
			return msgTextAndTip;
		}

		public boolean isMissing()
		{
			return missing;
		}
	}

=======
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
	@lombok.Value
	private static final class Element
	{
		public static String DEFAULT_LANG = "";

		String elementName;

		ITranslatableString name;
		ITranslatableString printName;
		ITranslatableString description;

		ITranslatableString poName;
		ITranslatableString poPrintName;
		ITranslatableString poDescription;

		@lombok.Builder
		private Element(
				@NonNull final String elementName,
				@Singular final Map<String, String> names,
				@Singular final Map<String, String> printNames,
				@Singular final Map<String, String> descriptions,
				@Singular final Map<String, String> poNames,
				@Singular final Map<String, String> poPrintNames,
				@Singular final Map<String, String> poDescriptions)
		{
			this.elementName = elementName;
			this.name = toTranslatableString(names, TranslatableStrings.empty());
			this.printName = toTranslatableString(printNames, TranslatableStrings.empty());
			this.description = toTranslatableString(descriptions, TranslatableStrings.empty());
			this.poName = toTranslatableString(poNames, this.name);
			this.poPrintName = toTranslatableString(poPrintNames, this.printName);
			this.poDescription = toTranslatableString(poDescriptions, this.description);
		}

		private static ITranslatableString toTranslatableString(final Map<String, String> map, final ITranslatableString fallback)
		{
			final String defaultValue = normalizeString(map.get(DEFAULT_LANG), fallback.getDefaultValue());

			final Set<String> adLanguages = ImmutableSet.<String>builder()
					.addAll(map.keySet())
					.addAll(fallback.getAD_Languages())
					.build();

			final Map<String, String> trlMap = adLanguages.stream()
					.filter(adLanguage -> !DEFAULT_LANG.equals(adLanguage))
					.map(adLanguage -> {
						final String text = normalizeString(map.get(adLanguage), fallback.translate(adLanguage));
						return GuavaCollectors.entry(adLanguage, text);
					})
					.collect(GuavaCollectors.toImmutableMap());
			return TranslatableStrings.ofMap(trlMap, defaultValue);
		}

		private static String normalizeString(final String str, final String fallback)
		{
			if (!Check.isEmpty(str, true))
			{
				return str.trim();
			}
			if (fallback != null)
			{
				return fallback;
			}
			return "";
		}

		public ITranslatableString getText(final String displayColumnName, final boolean isSOTrx)
		{
			return switch (displayColumnName)
			{
				case I_AD_Element.COLUMNNAME_Description -> isSOTrx ? description : poDescription;
				case I_AD_Element.COLUMNNAME_PrintName -> isSOTrx ? printName : poPrintName;
				default -> isSOTrx ? name : poName;
			};
		}
	}
}    // Msg
