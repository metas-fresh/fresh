package de.metas.util.web.security;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

class UserAuthTokenFilterConfigurationTest
{
	private static HttpServletRequest httpRequest(final String servletPath)
	{
		final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
		Mockito.doReturn(servletPath).when(httpRequest).getServletPath();
		return httpRequest;
	}

	@Test
	void getAuthResolution_StandardTests()
	{
		final UserAuthTokenFilterConfiguration config = new UserAuthTokenFilterConfiguration();
		config.addAuthResolutionForPathsContaining("/public", AuthResolution.DO_NOT_AUTHENTICATE);
		config.addAuthResolutionForPathsContaining("/maybeAuth", AuthResolution.AUTHENTICATE_IF_TOKEN_AVAILABLE);

		final SoftAssertions softly = new SoftAssertions();

		// Do not authenticate:
		softly.assertThat(config.getAuthResolution(httpRequest("/public"))).contains(AuthResolution.DO_NOT_AUTHENTICATE);
		softly.assertThat(config.getAuthResolution(httpRequest("/public/"))).contains(AuthResolution.DO_NOT_AUTHENTICATE);
		softly.assertThat(config.getAuthResolution(httpRequest("/public/aaa/bbb/ccc.jpg"))).contains(AuthResolution.DO_NOT_AUTHENTICATE);

		// Authenticate if token available:
		softly.assertThat(config.getAuthResolution(httpRequest("/maybeAuth"))).contains(AuthResolution.AUTHENTICATE_IF_TOKEN_AVAILABLE);
		softly.assertThat(config.getAuthResolution(httpRequest("/maybeAuth/"))).contains(AuthResolution.AUTHENTICATE_IF_TOKEN_AVAILABLE);
		softly.assertThat(config.getAuthResolution(httpRequest("/maybeAuth/aaa/ccc"))).contains(AuthResolution.AUTHENTICATE_IF_TOKEN_AVAILABLE);

		// Not matching
		softly.assertThat(config.getAuthResolution(httpRequest("/"))).isEmpty();
		softly.assertThat(config.getAuthResolution(httpRequest("/private"))).isEmpty();
		softly.assertThat(config.getAuthResolution(httpRequest("/other"))).isEmpty();

		//
		softly.assertAll();
	}
}