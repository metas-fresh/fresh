package de.metas.util.web.security;

import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.security.UserNotAuthorizedException;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserAuthTokenServiceTest
{
	private static final RoleId roleId = RoleId.ofRepoId(123);
	private UserAuthToken token;
	private UserAuthTokenService userAuthTokenService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final UserAuthTokenRepository userAuthTokenRepo = new UserAuthTokenRepository();
		this.userAuthTokenService = new UserAuthTokenService(userAuthTokenRepo);

		this.token = userAuthTokenRepo.createNew(CreateUserAuthTokenRequest.builder()
				.userId(UserId.METASFRESH)
				.clientId(ClientId.METASFRESH)
				.orgId(OrgId.MAIN)
				.roleId(roleId)
				.build());
		createAndRegisterUserRolePermissionsDAO(token);

	}

	private static void createAndRegisterUserRolePermissionsDAO(final UserAuthToken token)
	{
		final IUserRolePermissions permissions = Mockito.mock(IUserRolePermissions.class);
		Mockito.doReturn(token.getClientId()).when(permissions).getClientId();
		Mockito.doReturn(token.getUserId()).when(permissions).getUserId();
		Mockito.doReturn(token.getRoleId()).when(permissions).getRoleId();

		final IUserRolePermissionsDAO userRolePermissionsDAO = Mockito.mock(IUserRolePermissionsDAO.class);
		Mockito.doReturn(permissions).when(userRolePermissionsDAO).getUserRolePermissions(Mockito.any());

		Services.registerService(IUserRolePermissionsDAO.class, userRolePermissionsDAO);
	}

	private void assertContextMatchesToken(final SoftAssertions softly)
	{
		final Properties ctx = Env.getCtx();
		softly.assertThat(Env.getClientId(ctx)).isEqualTo(token.getClientId());
		softly.assertThat(Env.getOrgId(ctx)).isEqualTo(token.getOrgId());
		softly.assertThat(Env.getLoggedUserId(ctx)).isEqualTo(token.getUserId());
		softly.assertThat(Env.getLoggedRoleId(ctx)).isEqualTo(token.getRoleId());
	}

	private void assertContextNotLoggedIn(final SoftAssertions softly)
	{
		final Properties ctx = Env.getCtx();
		softly.assertThat(Env.getLoggedUserIdIfExists(ctx)).isEmpty();
		softly.assertThat(Env.getLoggedRoleIdIfExists(ctx)).isEmpty();
	}

	@Nested
	class call
	{
		@Test
		void authRequired_tokenProvided()
		{
			final SoftAssertions softly = new SoftAssertions();
			final String result = userAuthTokenService.call(
					() -> token.getAuthToken(),
					AuthResolution.AUTHENTICATION_REQUIRED,
					() -> {
						assertContextMatchesToken(softly);
						return "executed";
					}
			);
			softly.assertThat(result).isEqualTo("executed");
			softly.assertAll();
		}

		@Test
		void authRequired_tokenNotProvided()
		{
			assertThatThrownBy(() -> userAuthTokenService.call(
					() -> null,
					AuthResolution.AUTHENTICATION_REQUIRED,
					() -> {throw new AdempiereException("This method shall not be called");}
			))
					.isInstanceOf(UserNotAuthorizedException.class)
					.hasMessageStartingWith("No token provided.");
		}

		@Test
		void authRequired_tokenProvidedButInvalid()
		{
			assertThatThrownBy(() -> userAuthTokenService.call(
					() -> "some_wrong_token",
					AuthResolution.AUTHENTICATION_REQUIRED,
					() -> {throw new AdempiereException("This method shall not be called");}
			))
					.isInstanceOf(UserNotAuthorizedException.class)
					.hasMessageStartingWith("Token not authorized.");
		}

		@Test
		void authIfTokenAvailable_tokenProvided()
		{
			final SoftAssertions softly = new SoftAssertions();
			final String result = userAuthTokenService.call(
					() -> token.getAuthToken(),
					AuthResolution.AUTHENTICATE_IF_TOKEN_AVAILABLE,
					() -> {
						assertContextMatchesToken(softly);
						return "executed";
					}
			);
			softly.assertThat(result).isEqualTo("executed");
			softly.assertAll();
		}

		@Test
		void authIfTokenAvailable_tokenNotProvided()
		{
			final SoftAssertions softly = new SoftAssertions();
			final String result = userAuthTokenService.call(
					() -> null,
					AuthResolution.AUTHENTICATE_IF_TOKEN_AVAILABLE,
					() -> {
						assertContextNotLoggedIn(softly);
						return "executed";
					}
			);
			softly.assertThat(result).isEqualTo("executed");
			softly.assertAll();
		}

		@Test
		void authIfTokenAvailable_tokenProvidedButInvalid()
		{
			assertThatThrownBy(() -> userAuthTokenService.call(
					() -> "some_wrong_token",
					AuthResolution.AUTHENTICATION_REQUIRED,
					() -> {throw new AdempiereException("This method shall not be called");}
			))
					.isInstanceOf(UserNotAuthorizedException.class)
					.hasMessageStartingWith("Token not authorized.");
		}

		@Test
		void doNotAuth_tokenProvided()
		{
			final SoftAssertions softly = new SoftAssertions();
			final String result = userAuthTokenService.call(
					() -> token.getAuthToken(),
					AuthResolution.DO_NOT_AUTHENTICATE,
					() -> {
						assertContextNotLoggedIn(softly);
						return "executed";
					}
			);
			softly.assertThat(result).isEqualTo("executed");
			softly.assertAll();
		}

		@Test
		void doNotAuth_tokenNotProvided()
		{
			final SoftAssertions softly = new SoftAssertions();
			final String result = userAuthTokenService.call(
					() -> null,
					AuthResolution.DO_NOT_AUTHENTICATE,
					() -> {
						assertContextNotLoggedIn(softly);
						return "executed";
					}
			);
			softly.assertThat(result).isEqualTo("executed");
			softly.assertAll();
		}

		@Test
		void doNotAuth_tokenProvidedButInvalid()
		{
			final SoftAssertions softly = new SoftAssertions();
			final String result = userAuthTokenService.call(
					() -> "some_wrong_token", // even if is wrong, does not matter
					AuthResolution.DO_NOT_AUTHENTICATE,
					() -> {
						assertContextNotLoggedIn(softly);
						return "executed";
					}
			);
			softly.assertThat(result).isEqualTo("executed");
			softly.assertAll();
		}

	}
}