package de.metas.util.web.security;

import javax.annotation.Nullable;

@FunctionalInterface
public interface UserAuthTokenSupplier
{
	/**
	 * @return actual string token or null if token not available
	 */
	@Nullable
	String getAuthToken();
}
