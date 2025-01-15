/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.banking.process.paymentdocumentform;

import de.metas.banking.BankAccountId;
import de.metas.banking.model.X_C_Payment_Request;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.jetbrains.annotations.Nullable;

public class C_Invoice_Create_Payment_Request extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private static final String PARAM_Bank_Account_ID = X_C_Payment_Request.COLUMNNAME_C_BP_BankAccount_ID;

	private final IPaymentRequestDAO paymentRequestDAO = Services.get(IPaymentRequestDAO.class);
	private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@Param(parameterName = PARAM_Bank_Account_ID, mandatory = true)
	private BankAccountId p_BankAccountID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(context.getSingleSelectedRecordId()));
		if (invoice.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only purchase");
		}
		if (!invoiceBL.isComplete(invoice))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only completed");
		}

		if (invoiceBL.isCreditMemo(invoice))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("credit memo");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		paymentRequestDAO.createOrReplace(invoiceBL.getById(InvoiceId.ofRepoId(getRecord_ID())), p_BankAccountID);
		return MSG_OK;
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (parameter.getColumnName().equals(PARAM_Bank_Account_ID))
		{
			final InvoiceId invoiceId = InvoiceId.ofRepoId(getRecord_ID());
			final CurrencyId currencyId = CurrencyId.ofRepoId(invoiceBL.getById(invoiceId).getC_Currency_ID());

			return BankAccountId.ofRepoIdOrNull(BPartnerBankAccountId.toRepoId(paySelectionBL.getBPartnerBankAccountId(invoiceId, currencyId)));
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}
}
