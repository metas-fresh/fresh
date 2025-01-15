package de.metas.banking.payment;

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
import de.metas.invoice.InvoiceId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;

import java.util.Properties;
import java.util.Set;

/**
 * Provides retrieval methods {@link I_C_Payment_Request}s
 *
 * @author al
 */
public interface IPaymentRequestDAO extends ISingletonService
{
	void createOrReplace(@NonNull I_C_Invoice invoice, @NonNull BankAccountId bankAccountId);

	/**
	 * Retrieve payment request for given invoice or null if none is found.
	 *
	 * @param invoiceId
	 * @return payment request
	 *
	 * @throws AdempiereException if more than one payment request is found for given invoice
	 */
	I_C_Payment_Request retrieveSingularRequestOrNull(@NonNull final InvoiceId invoiceId) throws AdempiereException;

	boolean hasPaymentRequests(@NonNull final InvoiceId invoiceId);

	/**
	 * Checks if {@link I_C_Payment_Request}s identified by given IDs still exist in database and are still active/valid.
	 * 
	 * @param ctx
	 * @param paymentRequestIds C_Payment_Request_IDs; empty set is not allowed
	 * @return true if ALL of the {@link I_C_Payment_Request} still exist and are still active;
	 *         in case at least one is missing, this method will return false.
	 */
	boolean checkPaymentRequestsExists(Properties ctx, Set<Integer> paymentRequestIds);
}
