package de.metas.rest_api.v2.i18n;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.rest_api.v2.i18n.JsonMessages;
import de.metas.i18n.AdMessagesTree;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(I18NRestController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class I18NRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/i18n";

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ImmutableList<String> adMessagePrefixes = ImmutableList.of("mobileUI.");

	public I18NRestController(
			@NonNull final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration)
	{
		userAuthTokenFilterConfiguration.doNotAuthenticatePathsContaining(ENDPOINT);
	}

	@GetMapping("/messages")
	public JsonMessages getMessages(
			@RequestParam(name = "filter", required = false) final String filterString,
			@RequestParam(name = "lang", required = false) final String adLanguageParam)
	{
		final String adLanguage = StringUtils.trimBlankToOptional(adLanguageParam).orElseGet(Env::getADLanguageOrBaseLanguage);

		final AdMessagesTree adMessagesTree = AdMessagesTree.merge(
				adMessagePrefixes.stream()
						.map(adMessagePrefix -> msgBL.messagesTree()
								.adMessagePrefix(adMessagePrefix)
								.filterAdMessageStartingWithIgnoringCase(filterString)
								.removePrefix(false)
								.load(adLanguage))
						.collect(ImmutableList.toImmutableList()));

		return toJson(adMessagesTree);
	}

	private static JsonMessages toJson(@NonNull final AdMessagesTree adMessagesTree)
	{
		return JsonMessages.builder()
				.language(adMessagesTree.getAdLanguage())
				.messages(adMessagesTree.getMap())
				.build();
	}

}
