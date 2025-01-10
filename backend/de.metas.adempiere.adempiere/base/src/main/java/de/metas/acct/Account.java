/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.acct;

import de.metas.acct.api.AccountId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class Account
{
	@NonNull
	public static Account of(@NonNull final AccountId accountId, @NonNull final String accountConceptualName)
	{
		return Account.builder()
				.accountId(accountId)
				.accountConceptualName(accountConceptualName)
				.build();
	}

	@NonNull
	public static Account of(@NonNull final AccountId accountId, @NonNull final AccountConceptualNameAware accountConceptualNameAware)
	{
		return Account.builder()
				.accountId(accountId)
				.accountConceptualName(accountConceptualNameAware.getAccountConceptualName())
				.build();
	}

	@NonNull
	public static Account ofId(@NonNull final AccountId accountId)
	{
		return Account.builder()
				.accountId(accountId)
				.build();
	}

	@NonNull
	AccountId accountId;

	@NonNull
	@Default
	String accountConceptualName = "UNSET";
}
