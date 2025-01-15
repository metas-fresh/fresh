package de.metas.banking.payment.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.banking.BankAccountId;
import de.metas.banking.model.I_C_Payment_Request;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.invoice.InvoiceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author al
 */
public class PaymentRequestDAO implements IPaymentRequestDAO
{
	private IQueryBuilder<I_C_Payment_Request> retrieveRequestForInvoiceQuery(@NonNull final InvoiceId invoiceId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_Payment_Request.class)
				.addEqualsFilter(I_C_Payment_Request.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addOnlyActiveRecordsFilter(); // NOTE: very important, BL relies on this (i.e. assumes it gets only active requests)
	}

	@Override
	public void createOrReplace(@NonNull final I_C_Invoice invoice, @NonNull final BankAccountId bankAccountId)
	{
		final I_C_Payment_Request existingRequest = retrieveRequestForInvoiceQuery(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.create()
				.firstOnly(I_C_Payment_Request.class);

		final I_C_Payment_Request request = existingRequest != null ? existingRequest : InterfaceWrapperHelper.newInstance(I_C_Payment_Request.class);
		request.setC_BP_BankAccount_ID(bankAccountId.getRepoId());
		request.setAmount(invoice.getGrandTotal());
		request.setC_Invoice_ID(invoice.getC_Invoice_ID());
		InterfaceWrapperHelper.save(request);
	}

	@Override
	@Nullable
	public I_C_Payment_Request retrieveSingularRequestOrNull(@NonNull final InvoiceId invoiceId)
	{
		return retrieveRequestForInvoiceQuery(invoiceId)
				.create()
				.firstOnly(I_C_Payment_Request.class);
	}

	@Override
	public boolean hasPaymentRequests(@NonNull final InvoiceId invoiceId)
	{
		return retrieveRequestForInvoiceQuery(invoiceId)
				.create()
				.anyMatch();
	}
	
	@Override
	public boolean checkPaymentRequestsExists(final Properties ctx, final Set<Integer> paymentRequestIds)
	{
		Check.assumeNotEmpty(paymentRequestIds, "paymentRequestIds not empty");
		
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<Integer> actualPaymentRequestIdsList = queryBL.createQueryBuilder(I_C_Payment_Request.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter() // NOTE: very important, BL relies on this (i.e. assumes it gets only active requests)
				.addOnlyContextClient()
				.addInArrayOrAllFilter(I_C_Payment_Request.COLUMN_C_Payment_Request_ID, paymentRequestIds)
				.create()
				.listIds();
		
		final Set<Integer> actualPaymentRequestIds = new HashSet<>(actualPaymentRequestIdsList);
		
		return paymentRequestIds.equals(actualPaymentRequestIds);
	}
}
