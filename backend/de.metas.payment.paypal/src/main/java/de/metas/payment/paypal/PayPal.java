package de.metas.payment.paypal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.payments.Capture;
import de.metas.currency.Amount;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.email.MailService;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailText;
import de.metas.email.templates.MailTextBuilder;
import de.metas.money.MoneyService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.payment.paypal.client.PayPalClientExecutionContext;
import de.metas.payment.paypal.client.PayPalClientService;
import de.metas.payment.paypal.client.PayPalErrorResponse;
import de.metas.payment.paypal.client.PayPalOrder;
import de.metas.payment.paypal.client.PayPalOrderExternalId;
import de.metas.payment.paypal.client.PayPalOrderId;
import de.metas.payment.paypal.client.PayPalOrderService;
import de.metas.payment.paypal.config.PayPalConfig;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.payment.reservation.PaymentReservationCapture;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.payment.reservation.PaymentReservationRepository;
import de.metas.ui.web.WebuiURLs;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.net.URL;

/*
 * #%L
 * de.metas.payment.paypal
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

/**
 * PayPal Service Facade
 */
@Service
public class PayPal
{
	//
	// services:
	private final PayPalOrderService paypalOrderService;
	private final PayPalClientService paypalClient;
	private final PaymentReservationRepository paymentReservationRepo;
	private final MailService mailService;
	private final MoneyService moneyService;
	//
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

	@VisibleForTesting
	public static final String MAIL_VAR_ApproveURL = "ApproveURL";
	@VisibleForTesting
	public static final String MAIL_VAR_Amount = "Amount";
	@VisibleForTesting
	public static final String MAIL_VAR_SalesOrderDocumentNo = "SalesOrderDocumentNo";

	public PayPal(
			@NonNull final PayPalOrderService paypalOrderService,
			@NonNull final PayPalClientService paypalClient,
			@NonNull final PaymentReservationRepository paymentReservationRepo,
			@NonNull final MailService mailService,
			@NonNull final MoneyService moneyService)
	{
		this.paypalOrderService = paypalOrderService;
		this.paypalClient = paypalClient;
		this.paymentReservationRepo = paymentReservationRepo;
		this.mailService = mailService;
		this.moneyService = moneyService;
	}

	private PayPalOrder updatePayPalOrderFromAPI(@NonNull final PayPalOrderExternalId externalId)
	{
		final PayPalOrder paypalOrder = paypalOrderService.getByExternalId(externalId);
		return updatePayPalOrderFromAPI(paypalOrder);
	}

	public void updatePayPalOrderFromAPI(@NonNull final PayPalOrderId id)
	{
		PayPalOrder paypalOrder = paypalOrderService.getById(id);
		paypalOrder = updatePayPalOrderFromAPI(paypalOrder);

		updateReservationFromPaypalOrder(paypalOrder);
	}

	private PayPalOrder updatePayPalOrderFromAPI(final PayPalOrder paypalOrder)
	{
		final PayPalClientResponse<Order, PayPalErrorResponse> response = paypalClient.getAPIOrderById(
				paypalOrder.getExternalId(),
				PayPalClientExecutionContext.builder()
						.paymentReservationId(paypalOrder.getPaymentReservationId())
						.internalPayPalOrderId(paypalOrder.getId())
						.build());

		if (response.isOK())
		{
			return paypalOrderService.save(paypalOrder.getId(), response.getResult());
		}
		else
		{
			final PayPalErrorResponse error = response.getError();
			if (error.isResourceNotFound())
			{
				return paypalOrderService.markRemoteDeleted(paypalOrder.getId());
			}
			else
			{
				throw response.toException();
			}
		}
	}

	public boolean hasActivePaypalOrder(@NonNull final PaymentReservationId reservationId)
	{
		return paypalOrderService.getByReservationIdIfExists(reservationId).isPresent();
	}

	public void createPayPalOrderAndRequestPayerApproval(@NonNull final PaymentReservationId reservationId)
	{
		final PaymentReservation reservation = paymentReservationRepo.getById(reservationId);
		createPayPalOrderAndRequestPayerApproval(reservation);
	}

	public void createPayPalOrderAndRequestPayerApproval(@NonNull final PaymentReservation reservation)
	{
		//
		// Make sure there is no other paypal order
		final PayPalOrder existingPaypalOrder = paypalOrderService.getByReservationIdIfExists(reservation.getId()).orElse(null);
		if (existingPaypalOrder != null)
		{
			throw new AdempiereException("A paypal order already exists: " + existingPaypalOrder.getId());
		}

		PayPalOrder paypalOrder = paypalOrderService.create(reservation.getId());

		final PayPalConfig config = paypalClient.getConfig();
		final OrderRequest apiRequest = toAPIOrderRequest(reservation, config);
		final Order apiOrder = paypalClient.createOrder(
				apiRequest,
				createPayPalClientExecutionContext(reservation, paypalOrder));
		paypalOrder = paypalOrderService.save(paypalOrder.getId(), apiOrder);

		final URL payerApproveUrl = paypalOrder.getPayerApproveUrl();
		sendPayerApprovalRequestEmail(reservation, payerApproveUrl, config.getOrderApproveMailTemplateId());
	}

	private OrderRequest toAPIOrderRequest(
			@NonNull final PaymentReservation reservation,
			@NonNull final PayPalConfig config)
	{
		final String webuiFrontendUrl = WebuiURLs.newInstance().getFrontendURL();
		final String approveCallbackUrl = config.getOrderApproveCallbackUrl(webuiFrontendUrl);

		return new OrderRequest()
				.intent("AUTHORIZE")
				.applicationContext(new ApplicationContext()
						.returnUrl(approveCallbackUrl)
						.cancelUrl(approveCallbackUrl))
				.purchaseUnits(ImmutableList.of(
						new PurchaseUnitRequest()
								.amount(toAmountWithBreakdown(reservation.getAmount()))));
	}

	private AmountWithBreakdown toAmountWithBreakdown(final de.metas.money.Money money)
	{
		final Amount amount = moneyService.toAmount(money);
		return new AmountWithBreakdown()
				.value(amount.getAsBigDecimal().toPlainString())
				.currencyCode(amount.getCurrencyCode().toThreeLetterCode());
	}

	private static PayPalClientExecutionContext createPayPalClientExecutionContext(
			@NonNull final PaymentReservation reservation,
			@NonNull final PayPalOrder paypalOrder)
	{
		return PayPalClientExecutionContext.builder()
				.paymentReservationId(reservation.getId())
				.salesOrderId(reservation.getSalesOrderId())
				.internalPayPalOrderId(paypalOrder.getId())
				.build();
	}

	private static PayPalClientExecutionContext createPayPalClientExecutionContext(
			@NonNull final PaymentReservationCapture capture,
			@NonNull final PayPalOrder paypalOrder)
	{
		return PayPalClientExecutionContext.builder()
				.paymentReservationId(capture.getReservationId())
				.paymentReservationCaptureId(capture.getId())
				//
				.salesOrderId(capture.getSalesOrderId())
				.salesInvoiceId(capture.getSalesInvoiceId())
				.paymentId(capture.getPaymentId())
				//
				.internalPayPalOrderId(paypalOrder.getId())
				.build();
	}

	public void sendPayerApprovalRequestEmail(final PayPalOrderId payPalOrderId)
	{
		final PayPalOrder paypalOrder = paypalOrderService.getById(payPalOrderId);

		final PaymentReservationId reservationId = paypalOrder.getPaymentReservationId();
		final PaymentReservation reservation = paymentReservationRepo.getById(reservationId);

		sendPayerApprovalRequestEmail(
				reservation,
				paypalOrder.getPayerApproveUrl(),
				paypalClient.getConfig().getOrderApproveMailTemplateId());
	}

	private void sendPayerApprovalRequestEmail(
			@NonNull final PaymentReservation reservation,
			@NonNull final URL payerApproveUrl,
			@NonNull final MailTemplateId mailTemplateId)
	{
		final MailboxQuery mailboxQuery = MailboxQuery.builder()
				.clientId(reservation.getClientId())
				.orgId(reservation.getOrgId())
				.build();
		final MailText mailText = createPayerApprovalRequestEmailText(reservation, payerApproveUrl, mailTemplateId);

		trxManager.runAfterCommit(() -> mailService.sendEMail(mailboxQuery, reservation.getPayerEmail(), mailText));
	}

	private MailText createPayerApprovalRequestEmailText(
			@NonNull final PaymentReservation reservation,
			@NonNull final URL payerApproveUrl,
			@NonNull final MailTemplateId mailTemplateId)
	{
		final MailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(mailTemplateId);
		mailTextBuilder.bpartnerContact(reservation.getPayerContactId());
		mailTextBuilder.customVariable(MAIL_VAR_ApproveURL, payerApproveUrl.toExternalForm());
		mailTextBuilder.customVariable(MAIL_VAR_Amount, moneyService.toTranslatableString(reservation.getAmount()));

		final I_C_Order salesOrder = ordersRepo.getById(reservation.getSalesOrderId());
		mailTextBuilder.customVariable(MAIL_VAR_SalesOrderDocumentNo, salesOrder.getDocumentNo());
		
		return mailTextBuilder.build();
	}

	public void authorizePayPalReservation(@NonNull final PaymentReservationId reservationId)
	{
		PayPalOrder paypalOrder = paypalOrderService.getByReservationId(reservationId);
		paypalOrder = updatePayPalOrderFromAPI(paypalOrder);
		final PaymentReservation reservation = updateReservationFromPaypalOrder(paypalOrder);
		authorizePayPalOrder(reservation);
	}

	public PaymentReservation onOrderApprovedByPayer(@NonNull final PayPalOrderExternalId apiOrderId)
	{
		final PaymentReservation reservation = updateReservationFromAPIOrder(apiOrderId);
		if (reservation.getStatus().isApprovedByPayer())
		{
			authorizePayPalOrder(reservation);
		}

		return reservation;
	}

	private PaymentReservation updateReservationFromAPIOrder(@NonNull final PayPalOrderExternalId apiOrderId)
	{
		final PayPalOrder paypalOrder = updatePayPalOrderFromAPI(apiOrderId);
		return updateReservationFromPaypalOrder(paypalOrder);
	}

	private PaymentReservation updateReservationFromPaypalOrder(@NonNull final PayPalOrder paypalOrder)
	{
		final PaymentReservationId reservationId = paypalOrder.getPaymentReservationId();
		final PaymentReservation reservation = paymentReservationRepo.getById(reservationId);
		updateReservationFromPayPalOrderNoSave(reservation, paypalOrder);
		paymentReservationRepo.save(reservation);
		return reservation;
	}

	public void authorizePayPalOrder(final PaymentReservation reservation)
	{
		reservation.getStatus().assertApprovedByPayer();

		PayPalOrder paypalOrder = paypalOrderService.getByReservationId(reservation.getId());
		final Order apiOrder = paypalClient.authorizeOrder(
				paypalOrder.getExternalId(),
				createPayPalClientExecutionContext(reservation, paypalOrder));

		paypalOrder = paypalOrderService.save(paypalOrder.getId(), apiOrder);
		if (!paypalOrder.isAuthorized())
		{
			throw new AdempiereException("Not authorized: " + paypalOrder);
		}

		reservation.changeStatusTo(paypalOrder.getStatus().toPaymentReservationStatus());
		paymentReservationRepo.save(reservation);

		completeSalesOrder(reservation.getSalesOrderId());
	}

	private void completeSalesOrder(@NonNull final OrderId salesOrderId)
	{
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

		final I_C_Order order = ordersRepo.getById(salesOrderId);
		final DocStatus orderDocStatus = DocStatus.ofCode(order.getDocStatus());
		if (orderDocStatus.isWaitingForPayment())
		{
			Services.get(IDocumentBL.class).processEx(order, IDocument.ACTION_WaitComplete);
			ordersRepo.save(order);
		}
	}

	public void processCapture(
			@NonNull final PaymentReservation reservation,
			@NonNull final PaymentReservationCapture capture)
	{
		reservation.getStatus().assertCompleted();

		PayPalOrder paypalOrder = paypalOrderService.getByReservationId(capture.getReservationId());
		final Boolean finalCapture = null;

		final Capture apiCapture = paypalClient.captureOrder(
				paypalOrder.getAuthorizationId(),
				moneyService.toAmount(capture.getAmount()),
				finalCapture,
				createPayPalClientExecutionContext(capture, paypalOrder));

		paypalOrder = updatePayPalOrderFromAPI(paypalOrder.getExternalId());
		updateReservationFromPayPalOrderNoSave(reservation, paypalOrder);
	}

	private static void updateReservationFromPayPalOrderNoSave(
			@NonNull final PaymentReservation reservation,
			@NonNull final PayPalOrder payPalOrder)
	{
		reservation.changeStatusTo(payPalOrder.getStatus().toPaymentReservationStatus());
	}

}
