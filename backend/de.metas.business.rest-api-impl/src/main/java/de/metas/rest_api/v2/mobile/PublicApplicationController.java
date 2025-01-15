/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.rest_api.v2.mobile;

import de.metas.mobile.MobileAuthMethod;
import de.metas.mobile.MobileConfig;
import de.metas.mobile.MobileConfigService;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.AuthResolution;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PublicApplicationController.ENDPOINT)
public class PublicApplicationController
{
	static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/public/mobile";

	private final MobileConfigService configService;

	public PublicApplicationController(
			@NonNull final MobileConfigService configService,
			@NonNull final UserAuthTokenFilterConfiguration userAuthTokenFilterConfiguration)
	{
		this.configService = configService;

		userAuthTokenFilterConfiguration.addAuthResolutionForPathsContaining(ENDPOINT, AuthResolution.AUTHENTICATE_IF_TOKEN_AVAILABLE);
	}

	@GetMapping("/config")
	public JsonMobileConfigResponse getConfig()
	{
		final MobileAuthMethod defaultAuthMethod = configService.getConfig()
				.map(MobileConfig::getDefaultAuthMethod)
				.orElse(MobileAuthMethod.USER_PASS);

		return JsonMobileConfigResponse.builder()
				.defaultAuthMethod(defaultAuthMethod)
				.availableAuthMethods(MobileAuthMethod.all())
				.build();
	}
}
