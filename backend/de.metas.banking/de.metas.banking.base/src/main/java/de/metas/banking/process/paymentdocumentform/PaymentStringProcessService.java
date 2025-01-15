/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.process.paymentdocumentform;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.model.I_C_Payment_Request;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.banking.payment.IPaymentStringBL;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.PaymentString;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Optional;
import java.util.Properties;

@Service
public class PaymentStringProcessService
{
	private static final Logger log = LogManager.getLogger(PaymentStringProcessService.class);
	private static final AdMessageKey MSG_INVOICE_IS_NOT_COMPLETED = AdMessageKey.of("de.metas.banking.process.paymentdocumentform.AlmightyKeeperOfEverything.InvoiceIsNotCompleted");
	private static final AdMessageKey MSG_NO_INVOICE_SELECTED = AdMessageKey.of("de.metas.banking.process.paymentdocumentform.AlmightyKeeperOfEverything.NoInvoiceSelected");
	private static final AdMessageKey MSG_PAYMENT_REQUEST_FOR_INVOICE_ALREADY_EXISTS_EXCEPTION = AdMessageKey.of("PaymentAllocationForm.PaymentRequestForInvoiceAlreadyExistsException");
	private static final AdMessageKey MSG_COULD_NOT_CREATE_PAYMENT_REQUEST = AdMessageKey.of("de.metas.banking.process.paymentdocumentform.AlmightyKeeperOfEverything.CouldNotCreatePaymentRequest");

	private final IPaymentStringBL paymentStringBL = Services.get(IPaymentStringBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IPaymentRequestDAO paymentRequestDAO = Services.get(IPaymentRequestDAO.class);
	private final IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);

	private static final String SYSCONFIG_PaymentStringParserType = "de.metas.paymentallocation.form.ReadPaymentDocumentDialog.PaymentStringParserType";

	public IPaymentStringDataProvider parsePaymentString(final Properties ctx, final String currentPaymentString)
	{
		log.debug("parsePaymentString: {}", currentPaymentString);
		final IPaymentStringDataProvider dataProvider;
		dataProvider = paymentStringBL.getDataProvider(ctx, paymentStringBL.getParserForSysConfig(SYSCONFIG_PaymentStringParserType), currentPaymentString);
		return dataProvider;
	}
	
	public IPaymentStringDataProvider parseQRPaymentString(@NonNull final String qrCode )
	{
		log.debug("parsePaymentString: {}", qrCode );
		return paymentStringBL.getQRDataProvider(qrCode );
	}


	/**
	 * Calls {@link IPaymentStringDataProvider#getC_BP_BankAccounts()} and
	 * <li>filters out accounts where {@link #validateAgainstContextBPartner(I_C_BP_BankAccount, int)} returned a {@code 3}
	 * <li>orders by the result of {@link #validateAgainstContextBPartner(I_C_BP_BankAccount, int)}, i.e. prefers 1s order 2s
	 * <li>returns the first match.
	 */
	@Nullable public I_C_BP_BankAccount getAndVerifyBPartnerAccountOrNull(@NonNull final IPaymentStringDataProvider dataProvider, final int contextBPartner_ID)
	{
		final Optional<I_C_BP_BankAccount> firstBankAccount = dataProvider.getC_BP_BankAccounts().stream()
				.map(bankAccount -> ImmutablePair.of(bankAccount, validateAgainstContextBPartner(bankAccount, contextBPartner_ID)))
				.filter(pair -> pair.getRight() < 3)
				.sorted(Comparator.comparing(IPair::getRight))
				.map(ImmutablePair::getLeft)
				.findFirst();

		return firstBankAccount.orElse(null);
	}

	/**
	 * Checks the given {@code bpBankAccount} against the partner that was set via contextBPartner_ID (if any) and returns:
	 * <li>{@code 1} if no contextBPartner_ID was set, or if the given {@code bpBankAccount}'s bPartner is the one that was set
	 * <li>{@code 2} else, if the given {@code bpBankAccount}'s bPartner is a {@code RemitTo} partner of the contextBPartner_ID partner
	 * <li>{@code 3} else
	 */
	private int validateAgainstContextBPartner(@NonNull final I_C_BP_BankAccount bpBankAccount, final int contextBPartner_ID)
	{
		if (contextBPartner_ID <= 0)
		{
			// we have no BPartner-Context-Info, so we can't verify the bank account.
			return 1;
		}

		if (contextBPartner_ID == bpBankAccount.getC_BPartner_ID())
		{
			// the BPartner from the account we looked up is the one from context
			return 1;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final boolean existsRelation = queryBL.createQueryBuilder(I_C_BP_Relation.class, bpBankAccount)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsRemitTo, true)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, contextBPartner_ID)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, bpBankAccount.getC_BPartner_ID())
				.create()
				.anyMatch();
		if (existsRelation)
		{
			return 2;
		}
		return 3;
	}

	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final I_C_Invoice invoice = context.getSelectedModel(I_C_Invoice.class);
		if (invoice == null)
		{
			return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NO_INVOICE_SELECTED));
		}

		// only completed invoiced
		if (!invoiceBL.isComplete(invoice))
		{
			return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_INVOICE_IS_NOT_COMPLETED));
		}

		return ProcessPreconditionsResolution.acceptIf(!invoice.isSOTrx());
	}

	public void createPaymentRequestFromTemplate(@NonNull final org.compiere.model.I_C_Invoice invoice, @Nullable final I_C_Payment_Request template)
	{
		if (template == null)
		{
			final ITranslatableString msg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_COULD_NOT_CREATE_PAYMENT_REQUEST);
			throw new AdempiereException(msg).markAsUserValidationError();
		}

		//
		// Get the selected invoice
		if (paymentRequestDAO.hasPaymentRequests(InvoiceId.ofRepoId(invoice.getC_Invoice_ID())))
		{
			final ITranslatableString msg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_PAYMENT_REQUEST_FOR_INVOICE_ALREADY_EXISTS_EXCEPTION);
			throw new AdempiereException(msg).markAsUserValidationError();
		}

		paymentRequestBL.createPaymentRequest(invoice, template);
	}

	public I_C_Payment_Request createPaymentRequestTemplate(final I_C_BP_BankAccount bankAccount, final BigDecimal amount, final PaymentString paymentString)
	{
		final IContextAware contextProvider = PlainContextAware.newOutOfTrx(Env.getCtx());

		//
		// Create it, but do not save it!
		final I_C_Payment_Request paymentRequestTemplate = InterfaceWrapperHelper.newInstance(I_C_Payment_Request.class, contextProvider);
		InterfaceWrapperHelper.setSaveDeleteDisabled(paymentRequestTemplate, true);

		paymentRequestTemplate.setC_BP_BankAccount(bankAccount);

		paymentRequestTemplate.setAmount(amount);

		if (paymentString != null)
		{
			paymentRequestTemplate.setReference(paymentString.getReferenceNoComplete());
			paymentRequestTemplate.setFullPaymentString(paymentString.getRawPaymentString());
		}

		return paymentRequestTemplate;
	}
}
