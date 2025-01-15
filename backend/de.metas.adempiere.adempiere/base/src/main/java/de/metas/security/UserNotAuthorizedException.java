package de.metas.security;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

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

public class UserNotAuthorizedException extends AdempiereException
{
	public UserNotAuthorizedException(@NonNull final String message)
	{
		super(message);
	}

	public UserNotAuthorizedException(@Nullable final Throwable cause)
	{
		super(buildMsg(cause), cause);
		// setParameter("authTokenString", authTokenString); // NOTE: don't include token in message because it might be a security issue
	}

	private static String buildMsg(@Nullable final Throwable cause)
	{
		final StringBuilder msg = new StringBuilder();
		msg.append("Token not authorized.");
		// NOTE: don't include token in message because it might be a security issue

		if (cause != null)
		{
			msg.append(" Cause: ").append(extractMessage(cause));
		}
		return msg.toString();
	}
}
