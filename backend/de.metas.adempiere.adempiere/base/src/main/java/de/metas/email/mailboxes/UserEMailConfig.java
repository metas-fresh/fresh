package de.metas.email.mailboxes;

import de.metas.email.EMailAddress;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@ToString(exclude = "password")
public class UserEMailConfig
{
	@NonNull UserId userId;
	@Nullable EMailAddress email;
	@Nullable String username;
	@Nullable String password;

	@Builder
	private UserEMailConfig(
			@NonNull final UserId userId, 
			@Nullable final EMailAddress email, 
			@Nullable final String username, 
			@Nullable final String password)
	{
		this.userId = userId;
		this.email = email;
		this.username = StringUtils.trimBlankToNull(username);
		this.password = password;
	}
}
