/*
 * #%L
 * de.metas.payment.esr
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

package de.metas.payment.esr;

import de.metas.banking.model.I_C_Payment_Request;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.IPaymentStringParserFactory;
import de.metas.banking.payment.PaymentParserType;
import de.metas.banking.payment.PaymentString;
import de.metas.banking.process.paymentdocumentform.PaymentStringProcessService;
import de.metas.invoice.InvoiceId;
import de.metas.payment.spi.impl.QRCodeStringParser;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.apache.commons.io.IOUtils;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class QRCodePaymentTest
{
	private static final String expectedIBAN = "CH1589144626811245431";
	private static final BigDecimal expectedAMT = new BigDecimal("100.80");

	private IPaymentStringParserFactory paymentStringParserFactory;
	private PaymentStringProcessService paymentStringProcessService;
	private IPaymentRequestDAO paymentRequestDAO ;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		paymentStringParserFactory = Services.get(IPaymentStringParserFactory.class);
		SpringContextHolder.registerJUnitBean(PaymentStringProcessService.class, new PaymentStringProcessService());
		paymentStringProcessService = SpringContextHolder.instance.getBean(PaymentStringProcessService.class);
		paymentRequestDAO = Services.get(IPaymentRequestDAO.class);
		
		//
		paymentStringParserFactory.registerParser(PaymentParserType.QRCode.getType(), new QRCodeStringParser());
	}

	@Test
	public void testWithSampleFile() throws IOException
	{
		final InputStream inputStream = getClass().getResourceAsStream("/de/metas/payment/spi/impl/QRR_PurchaseInvoice.txt");
		assertThat(inputStream).isNotNull();

		final String qrCode = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue("123");
		partner.setName("customerBPartner");
		saveRecord(partner);
		//
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setGrandTotal(expectedAMT);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		saveRecord(invoice);
		
		final I_C_BP_BankAccount bpBankaccount = newInstance(I_C_BP_BankAccount.class);
		bpBankaccount.setQR_IBAN(expectedIBAN);
		bpBankaccount.setIsEsrAccount(true);
		bpBankaccount.setIsActive(true);
		bpBankaccount.setC_BPartner_ID(partner.getC_BPartner_ID());
		saveRecord(bpBankaccount);
		
		
		final IPaymentStringDataProvider dataProvider = getDataProvider(qrCode);
		final I_C_BP_BankAccount bpBankAccountExisting = paymentStringProcessService.getAndVerifyBPartnerAccountOrNull(dataProvider, invoice.getC_BPartner_ID());
		
		assertThat(bpBankAccountExisting).isEqualTo(bpBankaccount);
	
		
		final PaymentString paymentString = dataProvider.getPaymentString();
		final I_C_Payment_Request paymentRequestTemplate = paymentStringProcessService.createPaymentRequestTemplate(bpBankaccount, expectedAMT, paymentString);

		paymentStringProcessService.createPaymentRequestFromTemplate(invoice, paymentRequestTemplate);
		
		assertThat(paymentRequestDAO.hasPaymentRequests(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))).isTrue();

		
	}
	
	private IPaymentStringDataProvider getDataProvider(@NonNull final String qrCode)
	{
		return paymentStringProcessService.parseQRPaymentString(qrCode);
	}
}
