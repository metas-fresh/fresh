package de.metas.rest_api.v2;

import de.metas.Profiles;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import lombok.NonNull;
import org.adempiere.ad.service.ISystemBL;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(VersionRestController.ENDPOINT)
@RestController
@Profile(Profiles.PROFILE_App)
public class VersionRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/version";

	private final ISystemBL systemBL = Services.get(ISystemBL.class);

	public VersionRestController(
			@NonNull final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration)
	{
		userAuthTokenFilterConfiguration.doNotAuthenticatePathsContaining(ENDPOINT);
	}

	@GetMapping
	public String getVersionString()
	{
		return systemBL.get().getDbVersion();
	}
}
