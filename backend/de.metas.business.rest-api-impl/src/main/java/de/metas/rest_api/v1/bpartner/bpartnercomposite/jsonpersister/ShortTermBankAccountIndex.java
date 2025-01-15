/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v1.bpartner.bpartnercomposite.jsonpersister;

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.money.CurrencyId;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.HashMap;

@ToString
public class ShortTermBankAccountIndex
{
	private final BPartnerComposite bpartnerComposite;

	private final HashMap<BPartnerBankAccountId, BPartnerBankAccount> bankAccountsById = new HashMap<>();
	private final HashMap<String, BPartnerBankAccount> bankAccountsByIBAN = new HashMap<>();

	public ShortTermBankAccountIndex(@NonNull final BPartnerComposite bpartnerComposite)
	{
		this.bpartnerComposite = bpartnerComposite;

		for (final BPartnerBankAccount bankAccount : bpartnerComposite.getBankAccounts())
		{
			bankAccountsById.put(bankAccount.getId(), bankAccount);
			if(bankAccount.getIban() != null)
			{
				bankAccountsByIBAN.put(bankAccount.getIban(), bankAccount);
			}
		}
	}

	public Collection<BPartnerBankAccount> getRemainingBankAccounts()
	{
		return bankAccountsById.values();
	}

	public void remove(@NonNull final BPartnerBankAccountId bpartnerBankAccountId)
	{
		bankAccountsById.remove(bpartnerBankAccountId);
	}

	public BPartnerBankAccount extract(final String iban)
	{
		return bankAccountsByIBAN.get(iban);
	}

	public BPartnerBankAccount newBankAccount(@NonNull final String iban, @NonNull final CurrencyId currencyId)
	{
		final BPartnerBankAccount bankAccount = BPartnerBankAccount.builder()
				.iban(iban)
				.currencyId(currencyId)
				.build();

		// bankAccountsById.put(?, bankAccount);
		bankAccountsByIBAN.put(bankAccount.getIban(), bankAccount);

		bpartnerComposite.addBankAccount(bankAccount);

		return bankAccount;
	}

}
