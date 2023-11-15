package de.metas.server.admin;

import de.metas.Profiles;
import de.metas.cache.rest.CacheRestControllerTemplate;
import de.metas.util.web.MetasfreshRestAPIConstants;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/cache")
@RestController
@Profile(Profiles.PROFILE_App)
public class CacheRestController extends CacheRestControllerTemplate
{
	@Override
	protected void assertAuth() {}
}
