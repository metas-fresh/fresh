package de.metas.i18n;

import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public interface IMsgBL extends ISingletonService
{
	AdMessageKey MSG_Yes = AdMessageKey.of("Y");
	AdMessageKey MSG_No = AdMessageKey.of("N");

	String getMsg(String adLanguage, AdMessageKey message);

	/**
	 * Get translated text message for AD_Message
	 *
	 * @return translated text
	 */
	String getMsg(String adLanguage, AdMessageKey message, Object[] params);

	String getMsg(String adLanguage, AdMessageKey message, List<Object> params);

	/**
	 * Get translated text message for AD_Message
	 *
	 * @param ctx       Context to retrieve language
	 * @param adMessage AD_Message
	 * @return translated text
	 */
	String getMsg(Properties ctx, AdMessageKey adMessage);

	@Deprecated
	default String getMsg(final Properties ctx, final String adMessage)
	{
		return getMsg(ctx, AdMessageKey.of(adMessage));
	}

	/**
	 * Get translated text message for AD_Message
	 *
	 * @return translated text
	 */
	String getMsg(Properties ctx, AdMessageKey adMessage, Object[] params);

	@Deprecated
	default String getMsg(final Properties ctx, final String adMessage, final Object[] params)
	{
		return getMsg(ctx, AdMessageKey.of(adMessage), params);
	}

	String getMsg(final AdMessageKey adMessage, final List<Object> params);

	@Deprecated
	default String getMsg(final String adMessage, final List<Object> params)
	{
		return getMsg(AdMessageKey.of(adMessage), params);
	}

	/**
	 * Get translated text message for AD_Message
	 *
	 * @return translated text
	 */
	String getMsg(Properties ctx, AdMessageKey adMessage, boolean text);

	@Deprecated
	default String getMsg(final Properties ctx, final String adMessage, final boolean text)
	{
		return getMsg(ctx, AdMessageKey.of(adMessage), text);
	}

	String parseTranslation(Properties ctx, String message);

	String parseTranslation(String adLanguage, String message);

	/**
	 * "Translate" text.
	 *
	 * <ul>
	 * <li>Checks AD_Message.AD_Message, if exists returns AD_Message.MsgText
	 * <li>Checks AD_Element.ColumnName, if exists returns AD_Element.Name
	 * </ul>
	 *
	 * @param ctx  context, used to fetch the AD_Language and IsSOTrx flag
	 * @param text text to be translated
	 * @return translated text or original text if not found
	 */
	String translate(Properties ctx, String text);

	String translate(String adLanguage, String text);

	String translate(Properties ctx, String text, boolean isSOTrx);

	/**
	 * "Translate" text.
	 *
	 * <ul>
	 * <li>Checks AD_Message.AD_Message, if exists returns AD_Message.MsgText
	 * <li>Checks AD_Element.ColumnName, if exists returns AD_Element.Name
	 * </ul>
	 *
	 * @param text text to be translated
	 * @return translatable string
	 * @see #translate(Properties, String, boolean)
	 */
	ITranslatableString translatable(String text);

	/**
	 * @return AD_Message as translatable string
	 * @see #translatable(String)
	 */
	ITranslatableString getTranslatableMsgText(AdMessageKey adMessage, @Nullable Object... msgParameters);

	@Deprecated
	default ITranslatableString getTranslatableMsgText(@NonNull final String adMessage, @Nullable final Object... msgParameters)
	{
		return getTranslatableMsgText(AdMessageKey.of(adMessage), msgParameters);
	}

	default ITranslatableString getTranslatableMsgText(final AdMessageKey adMessage, final List<Object> msgParameters)
	{
		final Object[] msgParametersArr = msgParameters != null ? msgParameters.toArray() : new Object[] {};
		return getTranslatableMsgText(adMessage, msgParametersArr);
	}

	@Deprecated
	default ITranslatableString getTranslatableMsgText(final String adMessage, final List<Object> msgParameters)
	{
		return getTranslatableMsgText(AdMessageKey.of(adMessage), msgParameters);
	}

	default ITranslatableString getTranslatableMsgText(final boolean booleanValue)
	{
		return getTranslatableMsgText(booleanValue ? MSG_Yes : MSG_No);
	}

	/**
	 * Gets AD_Language/message map
	 *
	 * @param adLanguage   language key
	 * @param prefix       prefix used to match the AD_Messages (keys)
	 * @param removePrefix if true, the prefix will be cut out from the AD_Message keys that will be returned
	 * @return a map of "AD_Message" (might be with the prefix removed) to "translated message" pairs
	 */
	Map<String, String> getMsgMap(String adLanguage, String prefix, boolean removePrefix);

	void cacheReset();

	default AdMessagesTreeLoader.Builder messagesTree()
	{
		return AdMessagesTreeLoader.builder()
				.msgBL(this);
	}

<<<<<<< HEAD
=======
	String getBaseLanguageMsg(@NonNull AdMessageKey adMessage, @Nullable Object... msgParameters);

	Optional<AdMessageId> getIdByAdMessage(@NonNull AdMessageKey value);

	boolean isMessageExists(AdMessageKey adMessage);

	Optional<AdMessageKey> getAdMessageKeyById(AdMessageId adMessageId);
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
}
