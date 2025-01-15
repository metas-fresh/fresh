package de.metas.util.web.security;

public enum AuthResolution
{
	DO_NOT_AUTHENTICATE,
	AUTHENTICATION_REQUIRED,
	AUTHENTICATE_IF_TOKEN_AVAILABLE,
	;

	public boolean isDoNotAuthenticate() {return this == DO_NOT_AUTHENTICATE;}

	public boolean isAuthenticateIfTokenAvailable() {return this == AUTHENTICATE_IF_TOKEN_AVAILABLE;}
}
