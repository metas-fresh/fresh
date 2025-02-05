package de.metas.payment.paypal;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.email.EMailAddress;
import de.metas.email.MailService;
import de.metas.email.templates.MailTemplateId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paypal.client.PayPalClientService;
import de.metas.payment.paypal.client.PayPalOrder;
import de.metas.payment.paypal.client.PayPalOrderExternalId;
import de.metas.payment.paypal.client.PayPalOrderRepository;
import de.metas.payment.paypal.client.PayPalOrderService;
import de.metas.payment.paypal.logs.PayPalLogRepository;
import de.metas.payment.paypal.processor.PayPalPaymentProcessor;
import de.metas.payment.processor.PaymentProcessorService;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.payment.reservation.PaymentReservationCaptureRepository;
import de.metas.payment.reservation.PaymentReservationCaptureRequest;
import de.metas.payment.reservation.PaymentReservationCreateRequest;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.payment.reservation.PaymentReservationRepository;
import de.metas.payment.reservation.PaymentReservationService;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_MailText;

import java.time.LocalDate;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.payment.paypalplus
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

public class PayPalCheckoutManualTest2
{
	public static void main(final String[] args)
	{
		AdempiereTestHelper.get().init();

		new PayPalCheckoutManualTest2().run();

		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Done");
		POJOLookupMap.get().dumpStatus();
	}

	private final ClientId clientId;
	private final OrgId orgId = OrgId.ofRepoId(1);
	private final CurrencyId currencyId;

	private PayPal paypal;
	private PayPalOrderService payPalOrderService;
	private PaymentReservationService paymentReservationService;

	private PayPalCheckoutManualTest2()
	{
		clientId = createClient();
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		setupServices();
	}

	private static ClientId createClient()
	{
		final I_AD_Client record = newInstanceOutOfTrx(I_AD_Client.class);
		record.setRequestEMail("payments@metasfresh.com");
		record.setSMTPHost("localhost");
		record.setSMTPPort(25);
		record.setIsSmtpAuthorization(false);
		record.setIsServerEMail(false);

		saveRecord(record);
		return ClientId.ofRepoId(record.getAD_Client_ID());
	}

	private void setupServices()
	{
		final TestPayPalConfigProvider payPalConfigProvider = TestPayPalConfigProvider.builder()
				.approveMailTemplateId(createApproveMailTemplate())
				.build();

		final PayPalClientService payPalClient = new PayPalClientService(
				payPalConfigProvider,
				new PayPalLogRepository(Optional.empty()));

		final PayPalOrderRepository paypalOrderRepo = new PayPalOrderRepository();
		payPalOrderService = new PayPalOrderService(paypalOrderRepo);

		final MoneyService moneyService = new MoneyService(
				new CurrencyRepository());

		final MailService mailService = MailService.newInstanceForUnitTesting();

		final PaymentReservationRepository paymentReservationRepo = new PaymentReservationRepository();
		final PaymentReservationCaptureRepository paymentReservationCaptureRepo = new PaymentReservationCaptureRepository();

		paypal = new PayPal(payPalOrderService, payPalClient, paymentReservationRepo, mailService, moneyService);

		final PayPalPaymentProcessor payPalPaymentProcessor = new PayPalPaymentProcessor(paypal);

		final PaymentProcessorService paymentProcessors = new PaymentProcessorService(
				Optional.of(ImmutableList.of(payPalPaymentProcessor)));

		paymentReservationService = new PaymentReservationService(
				paymentReservationRepo,
				paymentReservationCaptureRepo,
				paymentProcessors);
	}

	private static MailTemplateId createApproveMailTemplate()
	{
		final I_R_MailText record = newInstanceOutOfTrx(I_R_MailText.class);
		record.setName("approve mail template");
		record.setMailHeader("approve payment");
		record.setMailText("please approve payment:"
				+ "\n Approve URL: @" + PayPal.MAIL_VAR_ApproveURL + "@"
				+ "\n Amount: @" + PayPal.MAIL_VAR_Amount + "@");
		saveRecord(record);
		return MailTemplateId.ofRepoId(record.getR_MailText_ID());
	}

	private Money money(final int value)
	{
		return Money.of(value, currencyId);
	}

	private static BPartnerContactId createPayerBPartnerContact()
	{
		final I_C_BPartner bpartnerRecord = newInstanceOutOfTrx(I_C_BPartner.class);
		bpartnerRecord.setValue("payer");
		bpartnerRecord.setName("payer");
		saveRecord(bpartnerRecord);

		final org.compiere.model.I_AD_User contactRecord = newInstanceOutOfTrx(I_AD_User.class);
		contactRecord.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		contactRecord.setName("payer contact");
		saveRecord(contactRecord);

		return BPartnerContactId.ofRepoId(contactRecord.getC_BPartner_ID(), contactRecord.getAD_User_ID());
	}

	private void run()
	{
		final OrderId salesOrderId = OrderId.ofRepoId(123);

		//
		// Create Reservation
		final PaymentReservationId reservationId;
		{
			final PaymentReservation reservation = paymentReservationService.createReservation(PaymentReservationCreateRequest.builder()
					.clientId(clientId)
					.orgId(orgId)
					.amount(money(100))
					.payerContactId(createPayerBPartnerContact())
					.payerEmail(EMailAddress.ofString("from@example.com"))
					.salesOrderId(salesOrderId)
					.dateTrx(LocalDate.now())
					.paymentRule(PaymentRule.PayPal)
					.build());
			reservation.getStatus().assertWaitingForPayerApproval();
			reservationId = reservation.getId();
		}

		//
		// Wait: payer approval & order authorization
		{
			final PayPalOrder payPalOrder = payPalOrderService.getByReservationId(reservationId);
			final PayPalOrderExternalId apiOrderId = payPalOrder.getExternalId();

			pollUntilCompleted(apiOrderId);
		}

		paymentReservationService.captureAmount(PaymentReservationCaptureRequest.builder()
				.salesOrderId(salesOrderId)
				.amount(money(30))
				.salesInvoiceId(InvoiceId.ofRepoId(1))
				.build());
		paymentReservationService.captureAmount(PaymentReservationCaptureRequest.builder()
				.salesOrderId(salesOrderId)
				.amount(money(40))
				.salesInvoiceId(InvoiceId.ofRepoId(2))
				.build());
		paymentReservationService.captureAmount(PaymentReservationCaptureRequest.builder()
				.salesOrderId(salesOrderId)
				.amount(money(20))
				.salesInvoiceId(InvoiceId.ofRepoId(3))
				.build());

		// TODO: refund
	}

	private void pollUntilCompleted(@NonNull final PayPalOrderExternalId apiOrderId)
	{
		while (true)
		{
			final PaymentReservation reservation = paypal.onOrderApprovedByPayer(apiOrderId);
			if (reservation.getStatus().isCompleted())
			{
				System.out.println("Reservation " + reservation.getId() + " (apiOrderId=" + apiOrderId + ") was completed!");
				return;
			}
			else
			{
				System.out.println("Reservation " + reservation.getId() + " (apiOrderId=" + apiOrderId + ")"
						+ "  has status " + reservation.getStatus() + "."
						+ " Waiting until completed.");
				try
				{
					Thread.sleep(2000);
				}
				catch (InterruptedException e)
				{
				}
			}
		}
	}

}
