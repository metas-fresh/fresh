package de.metas.banking.payment.modelvalidator;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.ModelValidator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Interceptor(I_C_PaySelection.class)
public class C_PaySelection
{
	private static final AdMessageKey MSG_PaySelection_CannotChangeBPBankAccount_InvalidCurrency = AdMessageKey.of("PaySelection.CannotChangeBPBankAccount.InvalidCurrency");

	private final BankAccountService bankAccountService;

	public C_PaySelection(
			@NonNull final BankAccountService bankAccountService)
	{
		this.bankAccountService = bankAccountService;
	}

	/**
	 * Remove line if currency does not match.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_PaySelection.COLUMNNAME_C_BP_BankAccount_ID })
	public void validateBankAccountCurrency(final I_C_PaySelection paySelection)
	{
		final I_C_BP_BankAccount bankAccount = InterfaceWrapperHelper.create(paySelection.getC_BP_BankAccount(), I_C_BP_BankAccount.class);

		final List<I_C_PaySelectionLine> paySelectionLines = Services.get(IPaySelectionDAO.class).retrievePaySelectionLines(paySelection);
		if (paySelectionLines.isEmpty())
		{
			return;
		}

		for (final I_C_PaySelectionLine paySelectionLine : paySelectionLines)
		{
			final I_C_Invoice invoice = paySelectionLine.getC_Invoice();

			//
			// Match currency
			if (invoice.getC_Currency_ID() == bankAccount.getC_Currency_ID())
			{
				continue;
			}

			final CurrencyCode invoiceCurrencyCode = getCurrencyCodeById(invoice.getC_Currency_ID());
			final CurrencyCode bankAccountCurrencyCode = getCurrencyCodeById(bankAccount.getC_Currency_ID());

			throw new AdempiereException(MSG_PaySelection_CannotChangeBPBankAccount_InvalidCurrency, new Object[] {
					paySelection.getName(), // name of the record we deal with
					bankAccountCurrencyCode.toThreeLetterCode(), // BPBA Actual Currency (actual)
					invoiceCurrencyCode.toThreeLetterCode() }); // Invoice Currency (expected)
		}
	}

	private CurrencyCode getCurrencyCodeById(final int currencyRepoId)
	{
		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		return currenciesRepo.getCurrencyCodeById(CurrencyId.ofRepoId(currencyRepoId));
	}

	/**
	 * Updates the pay selection's name if paydate or the bank account are changed. the name is set to be <PayDate>_<Bank>_<Currency>.
	 *
	 * @param paySelection
	 * @task 08267
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_PaySelection.COLUMNNAME_C_BP_BankAccount_ID, I_C_PaySelection.COLUMNNAME_PayDate })
	public void updateNameIfNotSet(final I_C_PaySelection paySelection)
	{

		final StringBuilder name = new StringBuilder();

		if (paySelection.getPayDate() != null)
		{
			final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			final String formattedDate = dateFormat.format(paySelection.getPayDate());
			name.append(formattedDate);
		}

		if (!name.isEmpty())
		{
			name.append("_");
		}

		final BankAccountId bankAccountId = BankAccountId.ofRepoIdOrNull(paySelection.getC_BP_BankAccount_ID());
		if (bankAccountId != null)
		{
			final String bankAccountName = bankAccountService.createBankAccountName(bankAccountId);
			name.append(bankAccountName);
		}

		if (!name.isEmpty() && !paySelection.getName().startsWith(name.toString()))
		{
			paySelection.setName(name.toString());
		}
	}

	// TODO: Fix this in the followup https://github.com/metasfresh/metasfresh/issues/2841
	// @DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	// public void createPayments(final I_C_PaySelection paySelection)
	// {
	// Services.get(IPaySelectionBL.class).createPayments(paySelection);
	// }
}
