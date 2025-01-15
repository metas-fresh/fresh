package de.metas.util.web.security;

import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.security.UserNotAuthorizedException;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.requests.CreateUserAuthTokenRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class UserAuthTokenService
{
	private final UserAuthTokenRepository userAuthTokenRepo;

	public UserAuthTokenService(
			@NonNull final UserAuthTokenRepository userAuthTokenRepo)
	{
		this.userAuthTokenRepo = userAuthTokenRepo;
	}

	public UserAuthToken getByToken(@NonNull final String token)
	{
		return userAuthTokenRepo.getByToken(token);
	}

	public void run(
			@NonNull final UserAuthTokenSupplier authTokenStringSupplier,
			@NonNull final UserAuthTokenRunnable runnable)
	{
		call(authTokenStringSupplier, AuthResolution.AUTHENTICATION_REQUIRED, asCallable(runnable));
	}

	public void run(
			@NonNull final UserAuthTokenSupplier authTokenStringSupplier,
			@NonNull final AuthResolution authResolution,
			@NonNull final UserAuthTokenRunnable runnable)
	{
		call(authTokenStringSupplier, authResolution, asCallable(runnable));
	}

	@NonNull
	private static UserAuthTokenCallable<Object> asCallable(@NonNull final UserAuthTokenRunnable runnable)
	{
		return () -> {
			runnable.run();
			return null;
		};
	}

	public <R> R call(
			@NonNull final UserAuthTokenSupplier authTokenStringSupplier,
			@NonNull final UserAuthTokenCallable<R> callable)
	{
		return call(authTokenStringSupplier, AuthResolution.AUTHENTICATION_REQUIRED, callable);
	}

	public <R> R call(
			@NonNull final UserAuthTokenSupplier authTokenStringSupplier,
			@NonNull final AuthResolution authResolution,
			@NonNull final UserAuthTokenCallable<R> callable)
	{
		final UserAuthToken token = authenticate(authTokenStringSupplier, authResolution);

		try (final IAutoCloseable ignored = createContextAndSwitchIfToken(token))
		{
			return callable.call();
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	@Nullable
	private UserAuthToken authenticate(@NonNull final UserAuthTokenSupplier authTokenStringSupplier, @NonNull final AuthResolution authResolution)
	{
		if (authResolution.isDoNotAuthenticate())
		{
			return null;
		}

		try
		{
			final String authTokenString = StringUtils.trimBlankToNull(authTokenStringSupplier.getAuthToken());
			if (authTokenString == null)
			{
				if (authResolution.isAuthenticateIfTokenAvailable())
				{
					return null;
				}
				else
				{
					throw new UserNotAuthorizedException("No token provided. If you are calling from an REST API, please provide then token in `" + UserAuthTokenFilter.HEADER_Authorization + "` HTTP header" + " or `" + UserAuthTokenFilter.QUERY_PARAM_API_KEY + "` query parameter");
				}
			}
			else
			{
				return userAuthTokenRepo.getByToken(authTokenString);
			}
		}
		catch (final UserNotAuthorizedException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new UserNotAuthorizedException(ex);
		}
	}

	private IAutoCloseable createContextAndSwitchIfToken(@Nullable final UserAuthToken token)
	{
		if (token == null)
		{
			return IAutoCloseable.NOOP;
		}
		else
		{
			final Properties ctx = createContext(token);
			return Env.switchContext(ctx);
		}
	}

	private Properties createContext(final UserAuthToken token)
	{
		final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
		final IUserRolePermissions permissions = userRolePermissionsDAO.getUserRolePermissions(UserRolePermissionsKey.builder()
				.userId(token.getUserId())
				.roleId(token.getRoleId())
				.clientId(token.getClientId())
				.date(SystemTime.asDayTimestamp())
				.build());

		final Properties ctx = Env.newTemporaryCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, permissions.getClientId().getRepoId());
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, OrgId.toRepoId(token.getOrgId()));
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, UserId.toRepoId(permissions.getUserId()));
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, RoleId.toRepoId(permissions.getRoleId()));
		// TODO: set other properties like language, warehouse etc...
		return ctx;
	}

	public UserAuthToken getOrCreateNewToken(@NonNull final CreateUserAuthTokenRequest request)
	{
		return userAuthTokenRepo.getOrCreateNew(request);
	}
}
