package de.metas.invoice.service.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.exceptions.BPartnerNoBillToAddressException;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.ContactType;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.IfNotFound;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.bpartner.service.OrgHasNoBPartnerLinkException;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.costing.ChargeId;
import de.metas.costing.impl.ChargeRepository;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.doctype.CopyDescriptionAndDocumentNote;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocCopyHandler;
import de.metas.document.IDocLineCopyHandler;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.invoice.BPartnerInvoicingInfo;
import de.metas.invoice.InvoiceCreditContext;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.invoice.service.IMatchInvBL;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.comparator.ComparatorChain;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_RMA;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Tax;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static de.metas.util.Check.assumeNotNull;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Implements those methods that are DB decoupled
 */
public abstract class AbstractInvoiceBL implements IInvoiceBL
{
	protected final transient Logger log = LogManager.getLogger(getClass());

	/**
	 * See {@link #setHasFixedLineNumber(I_C_InvoiceLine, boolean)}.
	 */
	private static final ModelDynAttributeAccessor<I_C_InvoiceLine, Boolean> HAS_FIXED_LINE_NUMBER = new ModelDynAttributeAccessor<>(Boolean.class);

	//
	// System configurations (public for testing)
	public static final String SYSCONFIG_AutoPayZeroAmt = "org.compiere.model.MInvoice.AutoPayZeroAmt";
	public static final String SYSCONFIG_SortILsByShipmentLineOrders = "org.compiere.model.MInvoice.SortILsByShipmentLineOrders";

	// FRESH-488: Payment rule from sys config
	public static final String SYSCONFIG_C_Invoice_PaymentRule = "de.metas.invoice.C_Invoice_PaymentRule";

	private static final AdMessageKey MSG_InvoiceMayNotBePaid = AdMessageKey.of("de.metas.invoice.service.impl.AbstractInvoiceBL_InvoiceMayNotBePaid");

	private static final AdMessageKey MSG_InvoiceMayNotHaveOpenAmtZero = AdMessageKey.of("de.metas.invoice.service.impl.AbstractInvoiceBL_InvoiceMayNotHaveOpenAmtZero");

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public org.compiere.model.I_C_Invoice getById(@NonNull final InvoiceId invoiceId)
	{
		return Services.get(IInvoiceDAO.class).getByIdInTrx(invoiceId);
	}

	@Override
	public final I_C_Invoice creditInvoice(@NonNull final I_C_Invoice invoice, final InvoiceCreditContext creditCtx)
	{
		Check.errorIf(isCreditMemo(invoice), "Param 'invoice'={} may not be a credit memo");
		Check.assume(invoice.getGrandTotal().signum() != 0, "GrandTotal!=0 for {}", invoice);

		if (creditCtx.isReferenceInvoice())
		{
			if (invoice.isPaid())
			{
				throw new AdempiereException(MSG_InvoiceMayNotBePaid, invoice.getDocumentNo());
			}

			//
			// 'openAmt is the amount that shall end up in the credit memo's GrandTotal
			final BigDecimal openAmt = Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice,
					false); // creditMemoAdjusted = false

			// 'invoice' is not paid, so the open amount won't be zero
			if (openAmt.signum() == 0)
			{
				throw new AdempiereException(MSG_InvoiceMayNotHaveOpenAmtZero, invoice.getDocumentNo());
			}

		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

		final DocTypeId targetDocTypeId = getTarget_DocType_ID(ctx, invoice, creditCtx.getDocTypeId());
		//
		// create the credit memo as a copy of the original invoice
		final I_C_Invoice creditMemo = InterfaceWrapperHelper.create(
				copyFrom(invoice, de.metas.common.util.time.SystemTime.asTimestamp(),
						targetDocTypeId.getRepoId(),
						invoice.isSOTrx(),
						false, // counter == false
						creditCtx.isReferenceOriginalOrder(), // setOrderRef == creditCtx.isReferenceOriginalOrder()
						creditCtx.isReferenceInvoice(), // setInvoiceRef == creditCtx.isReferenceInvoice()
						true, // copyLines == true
						new CreditMemoInvoiceCopyHandler(creditCtx)),
				I_C_Invoice.class);
		return creditMemo;
	}

	private DocTypeId getTarget_DocType_ID(final Properties ctx, final I_C_Invoice invoice, final DocTypeId docTypeId)
	{
		if (docTypeId != null)
		{
			return docTypeId;
		}

		//
		// decide on which C_DocType to use for the credit memo
		final String docBaseType;
		if (invoice.isSOTrx())
		{
			docBaseType = X_C_DocType.DOCBASETYPE_ARCreditMemo;
		}
		else
		{
			docBaseType = X_C_DocType.DOCBASETYPE_APCreditMemo;
		}
		//
		// TODO: What happens when we have multiple DocTypes per DocBaseType and nothing was selected by the user?
		return Services.get(IDocTypeDAO.class).getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(docBaseType)
						.adClientId(invoice.getAD_Client_ID())
						.adOrgId(invoice.getAD_Org_ID())
						.build());
	}

	public static final IDocCopyHandler<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_InvoiceLine> defaultDocCopyHandler = new DefaultDocCopyHandler<>(org.compiere.model.I_C_Invoice.class, org.compiere.model.I_C_InvoiceLine.class);

	@Override
	public final org.compiere.model.I_C_Invoice copyFrom(
			final org.compiere.model.I_C_Invoice from,
			final Timestamp dateDoc,
			final int C_DocTypeTarget_ID,
			final boolean isSOTrx,
			final boolean isCounterpart,
			final boolean setOrderRef,
			final boolean isSetLineInvoiceRef,
			final boolean isCopyLines)
	{
		return copyFrom(from, dateDoc, C_DocTypeTarget_ID, isSOTrx, isCounterpart, setOrderRef, isSetLineInvoiceRef, isCopyLines, AbstractInvoiceBL.defaultDocCopyHandler);
	}

	private final org.compiere.model.I_C_Invoice copyFrom(
			final org.compiere.model.I_C_Invoice from,
			final Timestamp dateDoc,
			final int C_DocTypeTarget_ID,
			final boolean isSOTrx,
			final boolean isCounterpart,
			final boolean setOrderRef,
			final boolean isSetLineInvoiceRef,
			final boolean isCopyLines,
			final IDocCopyHandler<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_InvoiceLine> additionalDocCopyHandler)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(from);
		final String trxName = InterfaceWrapperHelper.getTrxName(from);

		final I_C_Invoice to = InterfaceWrapperHelper.create(ctx, I_C_Invoice.class, trxName);
		to.setAD_Org_ID(from.getAD_Org_ID());

		// copy original values using the specified handler algorithm
		if (additionalDocCopyHandler != null)
		{
			additionalDocCopyHandler.copyPreliminaryValues(from, to);
		}
		Services.get(ICopyHandlerBL.class).copyPreliminaryValues(from, to);

		Check.errorUnless(from.getAD_Client_ID() == to.getAD_Client_ID(), "from.AD_Client_ID={}, to.AD_Client_ID={}", from.getAD_Client_ID(), to.getAD_Client_ID());
		Check.errorUnless(from.getAD_Org_ID() == to.getAD_Org_ID(), "from.AD_Org_ID={}, to.AD_Org_ID={}", from.getAD_Org_ID(), to.getAD_Org_ID());

		to.setDocStatus(IDocument.STATUS_Drafted);        // Draft
		to.setDocAction(IDocument.ACTION_Complete);
		//
		to.setC_DocType_ID(0);
		to.setC_DocTypeTarget_ID(C_DocTypeTarget_ID);
		to.setIsSOTrx(isSOTrx);
		//
		to.setDateInvoiced(dateDoc);
		to.setDateAcct(dateDoc);
		to.setDatePrinted(null);
		to.setIsPrinted(false);
		to.setPOReference(from.getPOReference());  // cg: task 05721
		//
		to.setIsApproved(false);
		to.setC_Payment_ID(0);
		to.setC_CashLine_ID(0);
		to.setIsPaid(false);
		to.setIsInDispute(false);
		//
		// Amounts are updated by trigger when adding lines
		to.setGrandTotal(BigDecimal.ZERO);
		to.setTotalLines(BigDecimal.ZERO);
		//
		to.setIsTransferred(false);
		to.setPosted(false);
		to.setProcessed(false);
		// [ 1633721 ] Reverse Documents- Processing=Y
		to.setProcessing(false);
		// delete references
		to.setIsSelfService(false);
		if (setOrderRef)
		{
			to.setC_Order_ID(from.getC_Order_ID());
		}
		else
		{
			to.setC_Order_ID(0);
		}

		if (isCounterpart)
		{
			to.setRef_Invoice_ID(from.getC_Invoice_ID());
			from.setRef_Invoice_ID(to.getC_Invoice_ID());
		}
		else
		{
			to.setRef_Invoice_ID(0);
		}

		if (isCounterpart)
		{
			// Try to find Order link
			if (from.getC_Order_ID() != 0)
			{
				final I_C_Order peer = InterfaceWrapperHelper.create(ctx, from.getC_Order_ID(), I_C_Order.class, trxName);
				if (peer.getRef_Order_ID() != 0)
				{
					to.setC_Order_ID(peer.getRef_Order_ID());
				}
			}
			// Try to find RMA link
			if (from.getM_RMA_ID() != 0)
			{
				final I_M_RMA peer = InterfaceWrapperHelper.create(ctx, from.getM_RMA_ID(), I_M_RMA.class, trxName);
				if (peer.getRef_RMA_ID() > 0)
				{
					to.setM_RMA_ID(peer.getRef_RMA_ID());
				}
			}
		}

		to.setC_Incoterms_ID(from.getC_Incoterms_ID());
		to.setIncotermLocation(from.getIncotermLocation());

		InterfaceWrapperHelper.save(to);

		final IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> additionalDocLineCopyHandler;
		if (additionalDocCopyHandler == null)
		{
			additionalDocLineCopyHandler = null;
		}
		else
		{
			additionalDocLineCopyHandler = additionalDocCopyHandler.getDocLineCopyHandler();
		}

		// Lines
		if (isCopyLines && copyLinesFrom(from, to, isCounterpart, setOrderRef, isSetLineInvoiceRef, additionalDocLineCopyHandler) == 0)
		{
			throw new IllegalStateException("Could not create Invoice Lines");
		}

		// copyValues override of the handler & save
		InterfaceWrapperHelper.refresh(to);

		Services.get(ICopyHandlerBL.class).copyValues(from, to); // invoke the "generic" handlers first; we expect them not to complete the target invoice

		if (additionalDocCopyHandler != null)
		{
			additionalDocCopyHandler.copyValues(from, to);
		}
		InterfaceWrapperHelper.save(to);

		return to;
	}

	@Override
	public final void writeOffInvoice(final org.compiere.model.I_C_Invoice invoice, final BigDecimal openAmt, final String description)
	{
		if (openAmt.signum() == 0)
		{
			return;
		}

		final BigDecimal openAmtAbs;
		if (!invoice.isSOTrx())
		{
			// API
			openAmtAbs = openAmt.negate();
		}
		else
		{
			// ARI
			openAmtAbs = openAmt;
		}

		// @formatter:off
		Services.get(IAllocationBL.class).newBuilder()
			.orgId(invoice.getAD_Org_ID())
			.currencyId(invoice.getC_Currency_ID())
			.dateAcct(invoice.getDateAcct())
			.dateTrx(invoice.getDateInvoiced())
			.addLine()
				.orgId(invoice.getAD_Org_ID())
				.bpartnerId(invoice.getC_BPartner_ID())
				.invoiceId(invoice.getC_Invoice_ID())
				.amount(BigDecimal.ZERO)
				.writeOffAmt(openAmtAbs)
			.lineDone()
			.create(true);
		// @formatter:on
	}

	@Override
	public final boolean testAllocation(final org.compiere.model.I_C_Invoice invoice, final boolean ignoreProcessed)
	{
		boolean change = false;

		if (invoice.isProcessed() || ignoreProcessed)
		{
			BigDecimal alloc = Services.get(IAllocationDAO.class).retrieveAllocatedAmt(invoice); // absolute
			final boolean hasAllocations = alloc != null; // metas: tsa: 01955
			if (alloc == null)
			{
				alloc = BigDecimal.ZERO;
			}
			BigDecimal total = invoice.getGrandTotal();
			// metas: tsa: begin: 01955:
			// If is an zero invoice, it has no allocations and the AutoPayZeroAmt is not set
			// then don't touch the invoice
			if (total.signum() == 0 && !hasAllocations
					&& !Services.get(ISysConfigBL.class).getBooleanValue(AbstractInvoiceBL.SYSCONFIG_AutoPayZeroAmt, true, invoice.getAD_Client_ID()))
			{
				// don't touch the IsPaid flag, return not changed
				return false;
			}
			// metas: tsa: end: 01955
			if (!invoice.isSOTrx())
			{
				total = total.negate();
			}
			if (isCreditMemo(invoice))
			{
				total = total.negate();
			}

			final boolean test = total.compareTo(alloc) == 0;
			change = test != invoice.isPaid();
			if (change)
			{
				invoice.setIsPaid(test);
			}

			log.debug("IsPaid={} (allocated={}, invoiceGrandTotal={})", test, alloc, total);
		}

		return change;
	}    // testAllocation

	/**
	 * Gets Invoice Grand Total (absolute value).
	 *
	 * @param invoice
	 * @return
	 */
	public final BigDecimal getGrandTotalAbs(final org.compiere.model.I_C_Invoice invoice)
	{
		BigDecimal grandTotal = invoice.getGrandTotal();
		if (grandTotal.signum() == 0)
		{
			return grandTotal;
		}

		// AP/AR adjustment
		if (!invoice.isSOTrx())
		{
			grandTotal = grandTotal.negate();
		}

		// CM adjustment
		if (isCreditMemo(invoice))
		{
			grandTotal = grandTotal.negate();
		}

		return grandTotal;
	}

	@Override
	public final I_C_Invoice createInvoiceFromOrder(
			final org.compiere.model.I_C_Order order,
			final DocTypeId docTypeTargetId,
			final LocalDate dateInvoiced,
			final LocalDate dateAcct)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, order);
		invoice.setAD_Org_ID(order.getAD_Org_ID());
		setFromOrder(invoice, order);    // set base settings

		//
		DocTypeId docTypeId = docTypeTargetId;
		if (docTypeId == null)
		{
			final I_C_DocType odt = Services.get(IOrderBL.class).getDocTypeOrNull(order);
			if (odt != null)
			{
				docTypeId = DocTypeId.ofRepoIdOrNull(odt.getC_DocTypeInvoice_ID());
				if (docTypeId == null)
				{
					throw new AdempiereException("@NotFound@ @C_DocTypeInvoice_ID@ - @C_DocType_ID@:" + odt.getName());
				}
			}
		}

		setDocTypeTargetIdAndUpdateDescription(invoice, docTypeId.getRepoId());
		if (dateInvoiced != null)
		{
			invoice.setDateInvoiced(TimeUtil.asTimestamp(dateInvoiced));
		}

		if (dateAcct != null)
		{
			invoice.setDateAcct(TimeUtil.asTimestamp(dateAcct)); // task 08437
		}
		else
		{
			invoice.setDateAcct(invoice.getDateInvoiced());
		}

		//
		invoice.setSalesRep_ID(order.getSalesRep_ID());
		//
		InvoiceDocumentLocationAdapterFactory
				.locationAdapter(invoice)
				.setFromBillLocation(order);

		invoice.setExternalId(order.getExternalId());

		return invoice;
	}

	@Override
	public final I_C_InvoiceLine createLine(final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, invoice);
		invoiceLine.setC_Invoice(invoice);

		return invoiceLine;
	}

	@Override
	public final void setFromOrder(final org.compiere.model.I_C_Invoice invoice, final org.compiere.model.I_C_Order order)
	{
		if (order == null)
		{
			return;
		}

		invoice.setC_Order_ID(order.getC_Order_ID());
		invoice.setIsSOTrx(order.isSOTrx());
		invoice.setIsDiscountPrinted(order.isDiscountPrinted());
		invoice.setIsSelfService(order.isSelfService());
		invoice.setSendEMail(order.isSendEMail());
		//
		invoice.setM_PriceList_ID(order.getM_PriceList_ID());
		invoice.setIsTaxIncluded(order.isTaxIncluded());
		invoice.setC_Currency_ID(order.getC_Currency_ID());
		invoice.setC_ConversionType_ID(order.getC_ConversionType_ID());
		//
		invoice.setPaymentRule(order.getPaymentRule());
		invoice.setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
		invoice.setPOReference(order.getPOReference());

		invoice.setDateOrdered(order.getDateOrdered());
		//
		invoice.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		invoice.setC_Project_ID(order.getC_Project_ID());
		invoice.setC_Campaign_ID(order.getC_Campaign_ID());
		invoice.setC_Activity_ID(order.getC_Activity_ID());
		invoice.setUser1_ID(order.getUser1_ID());
		invoice.setUser2_ID(order.getUser2_ID());

		//
		invoice.setSalesRep_ID(order.getSalesRep_ID());

		final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepo =
				SpringContextHolder.instance.getBean(OrderEmailPropagationSysConfigRepository.class);
		if (orderEmailPropagationSysConfigRepo.isPropagateToCInvoice(
				ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID())))
		{
			invoice.setEMail(order.getEMail());
		}

		// metas
		final I_C_Invoice invoice2 = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);
		final I_C_Order order2 = InterfaceWrapperHelper.create(order, I_C_Order.class);

		invoice2.setC_Incoterms_ID(order2.getC_Incoterms_ID());
		invoice2.setIncotermLocation(order2.getIncotermLocation());

		invoice2.setBPartnerAddress(order2.getBillToAddress());
		invoice2.setIsUseBPartnerAddress(order2.isUseBillToAddress());
		// metas end

		// metas (2009-0027-G5)
		invoice.setC_Payment_ID(order.getC_Payment_ID());

		// #4185: take description from doctype, if exists
		updateDescriptionFromDocTypeTargetId(invoice, order.getDescription(), order.getDescriptionBottom());
	}

	@Override
	public void updateFromBPartner(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(invoice.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			throw new FillMandatoryException("C_BPartner_ID");
		}

		final SOTrx soTrx = SOTrx.ofBoolean(invoice.isSOTrx());
		final ZonedDateTime date = TimeUtil.asZonedDateTime(invoice.getDateInvoiced());
		if (date == null)
		{
			throw new AdempiereException("Set DateInvoiced first");
		}

		final BPartnerInvoicingInfo invoicingInfo = getBPartnerInvoicingInfo(bpartnerId, soTrx, date);
		InvoiceDocumentLocationAdapterFactory
				.locationAdapter(invoice)
				.setFrom(invoicingInfo.getBillLocation());

		invoicingInfo.getPaymentRule().ifPresent(paymentRule -> invoice.setPaymentRule(paymentRule.getCode()));
		invoicingInfo.getPaymentTermId().ifPresent(paymentTermId -> invoice.setC_PaymentTerm_ID(paymentTermId.getRepoId()));

		invoice.setM_PriceList_ID(invoicingInfo.getPriceListId().getRepoId());
		invoice.setIsTaxIncluded(invoicingInfo.isTaxIncluded());
		invoice.setC_Currency_ID(invoicingInfo.getCurrencyId().getRepoId());
	}

	@Override
	public BPartnerInvoicingInfo getBPartnerInvoicingInfo(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final SOTrx soTrx,
			@NonNull final ZonedDateTime date)
	{
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final BPartnerLocationId billBPartnerLocationId = getBillBPartnerLocationId(bpartnerId, soTrx);
		final User billContact = bpartnerBL.retrieveContactOrNull(RetrieveContactRequest.builder()
				.onlyActive(true)
				.contactType(ContactType.BILL_TO_DEFAULT)
				.bpartnerId(billBPartnerLocationId.getBpartnerId())
				.bPartnerLocationId(billBPartnerLocationId)
				.ifNotFound(IfNotFound.RETURN_NULL)
				.build());
		final Optional<BPartnerContactId> billContactId = billContact != null
				? Optional.of(BPartnerContactId.of(billContact.getBpartnerId(), billContact.getId()))
				: Optional.empty();

		final Optional<PaymentTermId> paymentTermId = bpartnerBL.getPaymentTermIdForBPartner(bpartnerId, soTrx);
		final Optional<PaymentRule> paymentRule = bpartnerBL.getPaymentRuleForBPartner(bpartnerId);

		final I_M_PriceList priceList = getPriceList(billBPartnerLocationId, soTrx, date);

		return BPartnerInvoicingInfo.builder()
				.bpartnerId(bpartnerId)
				.billBPartnerLocationId(billBPartnerLocationId)
				.billContactId(billContactId)
				//
				.paymentRule(paymentRule)
				.paymentTermId(paymentTermId)
				//
				.priceListId(PriceListId.ofRepoId(priceList.getM_PriceList_ID()))
				.taxIncluded(priceList.isTaxIncluded())
				.currencyId(CurrencyId.ofRepoId(priceList.getC_Currency_ID()))
				//
				.build();
	}

	private BPartnerLocationId getBillBPartnerLocationId(final BPartnerId bpartnerId, final SOTrx soTrx)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
		final List<I_C_BPartner_Location> bpLocations = bpartnersRepo.retrieveBPartnerLocations(bpartnerId);
		for (final I_C_BPartner_Location loc : bpLocations)
		{
			if ((loc.isBillTo() && soTrx.isSales())
					|| (loc.isPayFrom() && soTrx.isPurchase()))
			{
				return BPartnerLocationId.ofRepoId(loc.getC_BPartner_ID(), loc.getC_BPartner_Location_ID());
			}
		}

		if (!bpLocations.isEmpty())
		{
			final I_C_BPartner_Location loc = bpLocations.get(0);
			return BPartnerLocationId.ofRepoId(loc.getC_BPartner_ID(), loc.getC_BPartner_Location_ID());
		}

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final String bpartnerName = bpartnerBL.getBPartnerValueAndName(bpartnerId);
		throw new BPartnerNoBillToAddressException(bpartnerName)
				.setParameter("SOTrx", soTrx)
				.appendParametersToMessage();
	}

	private I_M_PriceList getPriceList(
			@NonNull final BPartnerLocationId bpartnerLocationId,
			@NonNull final SOTrx soTrx,
			@NonNull final ZonedDateTime date)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

		final PricingSystemId pricingSystemId = bpartnersRepo.retrievePricingSystemIdOrNull(bpartnerLocationId.getBpartnerId(), soTrx);
		if (pricingSystemId == null)
		{
			throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@")
					.setParameter("C_BPartner_ID", bpartnerLocationId.getBpartnerId())
					.setParameter("SOTrx", soTrx)
					.appendParametersToMessage();
		}

		final CountryId countryId = bpartnersRepo.getCountryId(bpartnerLocationId);

		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
		return priceListBL.getCurrentPriceList(pricingSystemId, countryId, date, soTrx)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @M_PriceList_ID@")
						.setParameter("M_PricingSystem_ID", pricingSystemId)
						.setParameter("C_Country_ID", countryId)
						.setParameter("Date", date)
						.setParameter("soTrx", soTrx)
						.appendParametersToMessage());
	}

	@Override
	public final boolean setDocTypeTargetId(@NonNull final org.compiere.model.I_C_Invoice invoice, @NonNull final InvoiceDocBaseType docBaseType)
	{
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(docBaseType.getDocBaseType())
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(invoice.getAD_Client_ID())
				.adOrgId(invoice.getAD_Org_ID())
				.build();
		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(docTypeQuery);
		if (docTypeId == null)
		{
			log.error("Not found for {}", docTypeQuery);
			return false;
		}
		else
		{
			setDocTypeTargetIdAndUpdateDescription(invoice, docTypeId.getRepoId());
			final boolean isSOTrx = docTypeBL.isSOTrx(docBaseType.getDocBaseType());
			invoice.setIsSOTrx(isSOTrx);
			return true;
		}
	}

	@Override
	public void setDocTypeTargetIdIfNotSet(final org.compiere.model.I_C_Invoice invoice)
	{
		if (invoice.getC_DocTypeTarget_ID() > 0)
		{
			return;
		}

		final InvoiceDocBaseType docBaseType = invoice.isSOTrx() ? InvoiceDocBaseType.CustomerInvoice : InvoiceDocBaseType.VendorInvoice;
		setDocTypeTargetId(invoice, docBaseType);
	}

	@Override
	public void setDocTypeTargetIdAndUpdateDescription(org.compiere.model.I_C_Invoice invoice, int docTypeId)
	{
		invoice.setC_DocTypeTarget_ID(docTypeId);
		updateDescriptionFromDocTypeTargetId(invoice, null, null);
	}

	@Override
	public void updateDescriptionFromDocTypeTargetId(final org.compiere.model.I_C_Invoice invoice, final String defaultDescription, final String defaultDocumentNote)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

		final int docTypeId = invoice.getC_DocTypeTarget_ID();
		if (docTypeId <= 0)
		{
			return;
		}

		final org.compiere.model.I_C_DocType docType = Services.get(IDocTypeDAO.class).getById(docTypeId);
		if (docType == null)
		{
			return;
		}

		@Nullable
		final CopyDescriptionAndDocumentNote copyDescriptionAndDocumentNote = CopyDescriptionAndDocumentNote.ofNullableCode(docType.getCopyDescriptionAndDocumentNote());

		if (copyDescriptionAndDocumentNote == null)
		{
			return;
		}

		if (invoice.getC_BPartner_ID() <= 0)
		{
			// nothing to do
			return;
		}

		final I_C_BPartner bPartner = bpartnersRepo.getById(invoice.getC_BPartner_ID());

		final String adLanguage = CoalesceUtil.coalesce(bPartner.getAD_Language(), Env.getAD_Language());

		final IModelTranslationMap docTypeTrl = InterfaceWrapperHelper.getModelTranslationMap(docType);
		final ITranslatableString description = docTypeTrl.getColumnTrl(I_C_DocType.COLUMNNAME_Description, docType.getDescription());

		if (copyDescriptionAndDocumentNote.isCopyDescriptionAndDocumentNoteFromOrder() && invoice.getC_Order_ID() > 0)
		{
			final String orderDescription = orderBL.getDescriptionById(OrderId.ofRepoId(invoice.getC_Order_ID()));
			final String orderDescriptionBottom = orderBL.getDescriptionBottomById(OrderId.ofRepoId(invoice.getC_Order_ID()));
			invoice.setDescription(orderDescription);
			invoice.setDescriptionBottom(orderDescriptionBottom);
		}
		else
		{
			// // description
			if (!TranslatableStrings.isEmpty(description))
			{
				invoice.setDescription(description.translate(adLanguage));
			}
			else
			{
				invoice.setDescription(defaultDescription);
			}

			// description bottom
			final ITranslatableString documentNote = docTypeTrl.getColumnTrl(I_C_DocType.COLUMNNAME_DocumentNote, docType.getDocumentNote());

			if (!TranslatableStrings.isEmpty(documentNote))
			{

				invoice.setDescriptionBottom(documentNote.translate(adLanguage));
			}
			else
			{
				invoice.setDescriptionBottom(defaultDocumentNote);
			}
		}
	}

	@Override
	public void ensureUOMsAreNotNull(final @NonNull InvoiceId invoiceId)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(invoiceId); // // TODO tbp: create this method!
		for (final I_C_InvoiceLine line : lines)
		{
			if (line.getC_UOM_ID() < 1)
			{
				final ProductId productId = ProductId.ofRepoId(line.getM_Product_ID());
				final UomId stockUOMId = productBL.getStockUOMId(productId);
				line.setC_UOM_ID(stockUOMId.getRepoId());
			}
			if (line.getPrice_UOM_ID() < 1)
			{
				line.setPrice_UOM_ID(line.getC_UOM_ID());
			}
			InterfaceWrapperHelper.save(line);
		}
	}

	@Override
	public final void renumberLines(final I_C_Invoice invoice, final int step)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(invoice, InterfaceWrapperHelper.getTrxName(invoice));
		renumberLines(lines, step);
	}

	@Override
	public final void renumberLines(final List<I_C_InvoiceLine> lines, final int step)
	{
		// collect those line numbers that are already "taken"
		final Set<Integer> fixedNumbers = new HashSet<>();
		for (final I_C_InvoiceLine line : lines)
		{
			if (isHasFixedLineNumber(line))
			{
				fixedNumbers.add(line.getLine());
			}
		}

		// 02139: Sort InvoiceLines before renumbering.
		final List<I_C_InvoiceLine> linesToReorder = new ArrayList<>(lines);
		sortLines(linesToReorder);

		int number = step;
		int lineIdx = 0;

		while (lineIdx < linesToReorder.size())
		{
			final I_C_InvoiceLine invoiceLine = linesToReorder.get(lineIdx);

			if (invoiceLine.getLine() % step == 0)
			{
				if (!isHasFixedLineNumber(invoiceLine) && !fixedNumbers.contains(number))
				{
					// only give this line a (new) number, if its current number is not fixed
					// and only give it a number that is not yet taken as some line's fixed number
					invoiceLine.setLine(number);
					lineIdx++;
				}
				else if (isHasFixedLineNumber(invoiceLine))
				{
					// this line already has a number
					lineIdx++;
				}
				else
				{
					// this line has *no* fixed number and the current 'number' value is one of the fixed ones, so just increase 'number', but not 'lineIdx'.
					// I.e. try this invoice again, with the next 'number' value
				}

				if (!isHasFixedLineNumber(invoiceLine) || fixedNumbers.contains(number))
				{
					// this number value was just used, or is already used as a fixed-number by some line => one step forward
					number += step;
				}
			}
			else
			{
				if (invoiceLine.getLine() % 2 == 0)
				{
					if (!isHasFixedLineNumber(invoiceLine) && !fixedNumbers.contains(number - 2))
					{
						invoiceLine.setLine(number - 2);
					}
				}
				else
				{
					if (!isHasFixedLineNumber(invoiceLine) && !fixedNumbers.contains(number - 1))
					{
						invoiceLine.setLine(number - 1);
					}
				}
				lineIdx++;
			}
			InterfaceWrapperHelper.save(invoiceLine);
		}
	} // renumberLinesWithoutComment

	@Override
	public void setHasFixedLineNumber(final I_C_InvoiceLine line, final boolean value)
	{
		HAS_FIXED_LINE_NUMBER.setValue(line, value);
	}

	private boolean isHasFixedLineNumber(final I_C_InvoiceLine line)
	{
		return Boolean.TRUE.equals(HAS_FIXED_LINE_NUMBER.getValue(line));
	}

	/**
	 * Orders the InvoiceLines by their InOut. For each InOut, the FreightCostLine comes last. Lines whose M_InOut_ID equals 0, will get the M_InOut_ID of the next Line whose InOut_ID is not 0.
	 *
	 * @param lines - The unsorted array of InvoiceLines - is sorted by this method
	 */
	@VisibleForTesting
	/* package */ final void sortLines(final List<I_C_InvoiceLine> lines)
	{
		final Comparator<I_C_InvoiceLine> cmp = getInvoiceLineComparator(lines);

		Collections.sort(lines, cmp);
	}

	private final Comparator<I_C_InvoiceLine> getInvoiceLineComparator(final List<I_C_InvoiceLine> lines)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final ComparatorChain<I_C_InvoiceLine> ilComparator = new ComparatorChain<>();

		//
		// Use order line comparator if configured
		final boolean sortILsByShipmentLineOrders = sysConfigBL.getBooleanValue(SYSCONFIG_SortILsByShipmentLineOrders, false); // fallback false (if not configured)
		if (sortILsByShipmentLineOrders)
		{
			final Comparator<I_C_InvoiceLine> orderLineComparator = getShipmentLineOrderComparator(lines);
			ilComparator.addComparator(orderLineComparator);
		}

		//
		// Default comparator (original one, covered by tests)
		//
		// Note: add this at the end (first comparators are first served)
		{
			final Comparator<I_C_InvoiceLine> inOutLineComparator = getDefaultInvoiceLineComparator(lines);
			ilComparator.addComparator(inOutLineComparator);
		}

		return ilComparator;
	}

	/**
	 * Set M_InOut_ID for comment lines: The Invoice Lines are initially ordered by their M_InOut_ID, so that there is a "Block" of invoice lines for each InOut. There are 2 comment lines in front of
	 * every block, which are supposed to increase the clear arrangement in the Invoice window. None of these lines are attached to a M_InOutLine which means that the Virtual Column M_InOut_ID is
	 * NULL. This causes Problems when trying to order the lines, so first we need to allocate an InOut_ID to each InvoiceLine. To do this a hash map is used.
	 *
	 * @param lines
	 * @return comparator
	 */
	private final Comparator<I_C_InvoiceLine> getDefaultInvoiceLineComparator(final List<I_C_InvoiceLine> lines)
	{
		final HashMap<Integer, Integer> invoiceLineId2inOutId = new HashMap<>();

		for (int i = 0; i < lines.size(); i++)
		{
			final I_C_InvoiceLine il = lines.get(i);

			final int currentInOutID = il.getM_InOutLine_ID() > 0
					? il.getM_InOutLine().getM_InOut_ID()
					: 0;

			final int currentLineID = il.getC_InvoiceLine_ID();

			// if this is not a comment line:
			if (currentInOutID != 0)
			{
				invoiceLineId2inOutId.put(currentLineID, currentInOutID);
				continue;
			}

			int valueIdToUse = -1;

			// If this is a comment line: Get next line with a valid ID
			for (int j = 1; i + j < lines.size(); j++)
			{
				final I_C_InvoiceLine nextLine = lines.get(i + j);

				final int nextID = nextLine.getM_InOutLine_ID() > 0
						? nextLine.getM_InOutLine().getM_InOut_ID()
						: 0;

				if (nextID != 0) // If this is a valid ID, put it into the Map.
				{
					valueIdToUse = nextID;
					break;
				}
			}

			invoiceLineId2inOutId.put(currentLineID, valueIdToUse);
		}

		Check.assume(invoiceLineId2inOutId.size() == lines.size(), "Every line's id has been added to map '" + invoiceLineId2inOutId + "'");

		// create Comparator
		final Comparator<I_C_InvoiceLine> cmp = (line1, line2) -> {
			// InOut_ID
			final int InOut_ID1 = invoiceLineId2inOutId.get(line1.getC_InvoiceLine_ID());
			final int InOut_ID2 = invoiceLineId2inOutId.get(line2.getC_InvoiceLine_ID());

			if (InOut_ID1 > InOut_ID2)
			{
				return 1;
			}
			if (InOut_ID1 < InOut_ID2)
			{
				return -1;
			}

			// Freight cost
			final boolean fc1 = line1.isFreightCostLine();
			final boolean fc2 = line2.isFreightCostLine();

			if (fc1 && !fc2)
			{
				return 1;
			}
			if (!fc1 && fc2)
			{
				return -1;
			}

			// LineNo
			final int line1No = line1.getLine();
			final int line2No = line2.getLine();

			if (line1No > line2No)
			{
				return 1;
			}
			if (line1No < line2No)
			{
				return -1;
			}

			return 0;
		};
		return cmp;
	}

	private final Comparator<I_C_InvoiceLine> getShipmentLineOrderComparator(final List<I_C_InvoiceLine> lines)
	{
		final Comparator<I_C_InvoiceLine> comparator = (line1, line2) -> {

			final I_M_InOutLine iol1 = line1.getM_InOutLine();
			final I_M_InOutLine iol2 = line2.getM_InOutLine();
			if (Util.same(line1.getM_InOutLine_ID(), line2.getM_InOutLine_ID()))
			{
				return line1.getLine() - line2.getLine(); // keep IL order
			}
			else if (line1.getM_InOutLine_ID() <= 0 || iol1 == null)
			{
				return 1; // second line not null, put it first
			}
			else if (line2.getM_InOutLine_ID() <= 0 || iol2 == null)
			{
				return -1; // first line not null, put it first
			}

			final I_C_OrderLine ol1 = iol1.getC_OrderLine();
			final I_C_OrderLine ol2 = iol2.getC_OrderLine();
			if (Util.same(ol1, ol2))
			{
				return iol1.getLine() - iol2.getLine(); // keep IOL order
			}
			else if (ol1 == null)
			{
				return 1; // second line not null, put it first
			}
			else if (ol2 == null)
			{
				return -1; // first line not null, put it first
			}

			final I_C_Order o1 = InterfaceWrapperHelper.create(ol1.getC_Order(), I_C_Order.class);
			final I_C_Order o2 = InterfaceWrapperHelper.create(ol2.getC_Order(), I_C_Order.class);
			if (o1.getC_Order_ID() != o2.getC_Order_ID())
			{
				return o1.getC_Order_ID() - o2.getC_Order_ID(); // first orders go first
			}

			return ol1.getLine() - ol2.getLine(); // keep OL order
		};
		return comparator;
	}

	@Override
	public final void setProductAndUOM(@NonNull final I_C_InvoiceLine invoiceLine, @Nullable final ProductId productId)
	{
		if (productId != null)
		{
			final IProductBL productBL = Services.get(IProductBL.class);
			final UomId uomId = productBL.getStockUOMId(productId);

			invoiceLine.setM_Product_ID(productId.getRepoId());
			invoiceLine.setC_UOM_ID(uomId.getRepoId());
		}
		else
		{
			invoiceLine.setM_Product_ID(-1);
			invoiceLine.setC_UOM_ID(-1);
		}

		invoiceLine.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
	}

	@Override
	public final void setQtys(@NonNull final I_C_InvoiceLine invoiceLine, @NonNull final StockQtyAndUOMQty qtysInvoiced)
	{
		// for now we are lenient, because i'm not sure because strict doesn't break stuff
		// Check.assume(invoiceLine.getM_Product_ID() > 0, "invoiceLine {} has M_Product_ID > 0", invoiceLine);
		// Check.assume(invoiceLine.getC_UOM_ID() > 0, "invoiceLine {} has C_UOM_ID > 0", invoiceLine);
		// Check.assume(invoiceLine.getPrice_UOM_ID() > 0, "invoiceLine {} has Price_UOM_ID > 0", invoiceLine);

		final Quantity stockQty = qtysInvoiced.getStockQty();
		invoiceLine.setQtyInvoiced(stockQty.toBigDecimal());

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final ProductId productId = ProductId.ofRepoIdOrNull(invoiceLine.getM_Product_ID());

		final boolean fallback = productId == null;
		if (fallback)
		{
			// without a product, we have no internal UOM, so we can't do any conversions
			invoiceLine.setQtyEntered(stockQty.toBigDecimal());
			invoiceLine.setQtyInvoicedInPriceUOM(stockQty.toBigDecimal());
			return;
		}

		if (qtysInvoiced.getUOMQtyOpt().isPresent())
		{
			final Quantity uomQty = qtysInvoiced.getUOMQtyOpt().get();

			invoiceLine.setC_UOM_ID(uomQty.getUomId().getRepoId());
			invoiceLine.setQtyEntered(uomQty.toBigDecimal());
		}
		else
		{
			final BigDecimal qtyEntered = uomConversionBL.convertFromProductUOM(productId, UomId.ofRepoId(invoiceLine.getC_UOM_ID()), stockQty.toBigDecimal());
			invoiceLine.setQtyEntered(qtyEntered);
		}

		final BigDecimal qtyInvoicedInPriceUOM = uomConversionBL.convertQty(
				UOMConversionContext.of(productId),
				invoiceLine.getQtyEntered(),
				UomId.ofRepoId(invoiceLine.getC_UOM_ID()),
				UomId.ofRepoId(firstGreaterThanZero(invoiceLine.getPrice_UOM_ID(), invoiceLine.getC_UOM_ID())));
		invoiceLine.setQtyInvoicedInPriceUOM(qtyInvoicedInPriceUOM);
	}

	@Override
	public final void setLineNetAmt(final I_C_InvoiceLine invoiceLine)
	{
		// services
		final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
		final ITaxBL taxBL = Services.get(ITaxBL.class);
		final ChargeRepository chargeRepo = SpringContextHolder.instance.getBean(ChargeRepository.class);

		// // Make sure QtyInvoicedInPriceUOM is up2date
		invoiceLineBL.setQtyInvoicedInPriceUOM(invoiceLine);

		// Calculations & Rounding
		BigDecimal lineNetAmt = invoiceLine.getPriceActual().multiply(invoiceLine.getQtyInvoicedInPriceUOM());

		final TaxId taxId = TaxId.ofRepoIdOrNull(invoiceLine.getC_Tax_ID());
		final Tax invoiceTax = taxId != null ? taxBL.getTaxById(taxId) : null;
		final boolean isTaxIncluded = isTaxIncluded(invoiceLine);
		final CurrencyPrecision taxPrecision = getAmountPrecision(invoiceLine);

		final boolean documentLevel = invoiceTax != null && invoiceTax.isDocumentLevel();

		// juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		// http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded && !documentLevel)
		{
			BigDecimal taxStdAmt = BigDecimal.ZERO, taxThisAmt = BigDecimal.ZERO;

			Tax stdTax = null;
			if (invoiceLine.getM_Product_ID() > 0)
			{
				final ChargeId chargeId = ChargeId.ofRepoIdOrNull(invoiceLine.getC_Charge_ID());
				if (chargeId != null)    // Charge
				{
					final I_C_Charge chargeRecord = chargeRepo.getById(chargeId);
					final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoId(chargeRecord.getC_TaxCategory_ID());
					stdTax = taxBL.getDefaultTax(taxCategoryId);
				}
			}
			else
			// Product
			{
				// FIXME metas 05129 need proper concept (link between M_Product and C_TaxCategory_ID was removed!!!!!)
				throw new AdempiereException("Unsupported tax calculation when tax is included, but it's not on document level");
				// stdTax = createTax(ctx, taxDAO.getDefaultTax(invoiceLine.getM_Product().getC_TaxCategory()).getC_Tax_ID(), trxName);
			}

			if (stdTax != null)
			{
				log.debug("stdTax rate is {}", stdTax.getRate());
				log.debug("invoiceTax rate is {}", invoiceTax.getRate());

				taxThisAmt = taxThisAmt.add(invoiceTax.calculateTax(lineNetAmt, isTaxIncluded, taxPrecision.toInt()));
				taxStdAmt = taxThisAmt.add(stdTax.calculateTax(lineNetAmt, isTaxIncluded, taxPrecision.toInt()));

				lineNetAmt = lineNetAmt.subtract(taxStdAmt).add(taxThisAmt);

				log.debug("Price List includes Tax and Tax Changed on Invoice Line: New Tax Amt: "
						+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + lineNetAmt);
			}
		}

		lineNetAmt = taxPrecision.roundIfNeeded(lineNetAmt);

		invoiceLine.setLineNetAmt(lineNetAmt);

	}// setLineNetAmt

	@Override
	public final void setTaxAmt(final I_C_InvoiceLine invoiceLine)
	{
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

		final int taxId = invoiceLine.getC_Tax_ID();
		if (taxId <= 0)
		{
			return;
		}

		// setLineNetAmt();
		final Tax tax = taxDAO.getTaxById(taxId);
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();
		if (tax.isDocumentLevel() && invoice.isSOTrx())
		{
			return;
		}
		//
		final boolean isTaxIncluded = isTaxIncluded(invoiceLine);
		final BigDecimal lineNetAmt = invoiceLine.getLineNetAmt();
		final CurrencyPrecision taxPrecision = getTaxPrecision(invoiceLine);
		final BigDecimal TaxAmt = tax.calculateTax(lineNetAmt, isTaxIncluded, taxPrecision.toInt());
		if (isTaxIncluded)
		{
			invoiceLine.setLineTotalAmt(lineNetAmt);
		}
		else
		{
			invoiceLine.setLineTotalAmt(lineNetAmt.add(TaxAmt));
		}
		invoiceLine.setTaxAmt(TaxAmt);
	}    // setTaxAmt

	private I_C_Tax createTax(final Properties ctx, final TaxId taxId, final String trxName)
	{
		final int ctaxId = taxId.getRepoId();
		final I_C_Tax tax = InterfaceWrapperHelper.create(ctx, ctaxId, I_C_Tax.class, trxName);

		if (ctaxId == 0)
		{
			tax.setIsDefault(false);
			tax.setIsDocumentLevel(true);
			tax.setIsSummary(false);
			tax.setIsTaxExempt(false);
			tax.setRate(BigDecimal.ZERO);
			tax.setRequiresTaxCertificate(null);
			tax.setSOPOType(X_C_Tax.SOPOTYPE_Both);
			tax.setValidFrom(TimeUtil.getDay(1990, 1, 1));
			tax.setIsSalesTax(false);
		}

		return tax;
	}

	/**
	 * Calls {@link #isTaxIncluded(org.compiere.model.I_C_Invoice, Tax)} for the given <code>invoiceLine</code>'s <code>C_Invoice</code> and <code>C_Tax</code>.
	 */
	@Override
	public final boolean isTaxIncluded(@NonNull final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

		final Tax tax = taxDAO.getTaxByIdOrNull(invoiceLine.getC_Tax_ID());
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();

		return isTaxIncluded(invoice, tax);
	}

	@Override
	public final boolean isTaxIncluded(@NonNull final org.compiere.model.I_C_Invoice invoice, @Nullable final Tax tax)
	{
		if (tax != null && tax.isWholeTax())
		{
			return true;
		}

		return invoice.isTaxIncluded(); // 08486: use the invoice's flag, not whatever the PL says right now
	}

	@Override
	public void setInvoiceLineTaxes(@NonNull final I_C_Invoice invoice)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);

		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));

		for (final I_C_InvoiceLine il : lines)
		{
			final InOutLineId inoutLineId = InOutLineId.ofRepoIdOrNull(il.getM_InOutLine_ID());

			final I_M_InOutLine inoutLineRecord = inoutLineId == null ? null : inoutDAO.getLineById(inoutLineId);
			final I_M_InOut io = inoutLineRecord == null ? null : inoutDAO.getById(InOutId.ofRepoId(inoutLineRecord.getM_InOut_ID()));

			final OrgId orgId = io != null ? OrgId.ofRepoId(io.getAD_Org_ID()) : OrgId.ofRepoId(invoice.getAD_Org_ID());

			final Timestamp taxDate = io != null ? io.getMovementDate() : invoice.getDateInvoiced();

			final BPartnerLocationAndCaptureId taxBPartnerLocationId = io != null
					? InOutDocumentLocationAdapterFactory.locationAdapter(io).getBPartnerLocationAndCaptureId()
					: InvoiceDocumentLocationAdapterFactory.locationAdapter(invoice).getBPartnerLocationAndCaptureId();

			final boolean isSOTrx = io != null ? io.isSOTrx() : invoice.isSOTrx();

			final CountryId countryFromId = getFromCountryId(invoice, il);

			invoiceLineBL.setTaxForInvoiceLine(il, orgId, taxDate, countryFromId, taxBPartnerLocationId, isSOTrx);

			invoiceDAO.save(il);
		}
	}

	@Override
	public CountryId getFromCountryId(org.compiere.model.I_C_Invoice invoice, org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);

		final WarehouseId invoiceWarehouseId = WarehouseId.ofRepoIdOrNull(invoice.getM_Warehouse_ID());

		final OrgId orgId = OrgId.ofRepoId(invoice.getAD_Org_ID());

		final CountryId orgCountryId = bpartnerOrgBL.getOrgCountryId(orgId);

		final CountryId warehouseCountryId = invoiceWarehouseId == null ? null : warehouseBL.getCountryId(invoiceWarehouseId);

		final CountryId countryFromId;

		if (warehouseCountryId != null)
		{

			countryFromId = warehouseCountryId;
		}
		else if (invoiceLine.getM_InOutLine_ID() > 0)
		{
			final InOutLineId inoutLineId = InOutLineId.ofRepoId(invoiceLine.getM_InOutLine_ID());

			final I_M_InOutLine inoutLineRecord = inoutDAO.getLineById(inoutLineId);

			final I_M_InOut inout = inoutDAO.getById(InOutId.ofRepoId(inoutLineRecord.getM_InOut_ID()));

			final WarehouseId warehouseId = WarehouseId.ofRepoId(inout.getM_Warehouse_ID());
			countryFromId = warehouseBL.getCountryId(warehouseId);
		}
		else
		{
			countryFromId = orgCountryId;
			if (countryFromId == null)
			{
				throw new OrgHasNoBPartnerLinkException(orgId);
			}
		}

		return countryFromId;
	}

	@Override
	public final I_C_DocType getC_DocType(final org.compiere.model.I_C_Invoice invoice)
	{
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		if (invoice.getC_DocType_ID() > 0)
		{
			return docTypeDAO.getById(invoice.getC_DocType_ID());
		}
		else if (invoice.getC_DocTypeTarget_ID() > 0)
		{
			return docTypeDAO.getById(invoice.getC_DocTypeTarget_ID());
		}

		return null;
	}

	@Override
	public final boolean isInvoice(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_DocType docType = assumeNotNull(getC_DocType(invoice), "The given C_Invoice_ID={} needs to have a C_DocType", invoice.getC_Invoice_ID());
		final String docBaseType = docType.getDocBaseType();
		return isInvoice(docBaseType);
	}

	private boolean isInvoice(final String docBaseType)
	{
		final InvoiceDocBaseType invoiceDocBaseType = InvoiceDocBaseType.ofNullableCode(docBaseType);
		return invoiceDocBaseType != null && (invoiceDocBaseType.equals(InvoiceDocBaseType.CustomerInvoice) || invoiceDocBaseType.equals(InvoiceDocBaseType.VendorInvoice));
	}

	@Override
	public final boolean isCreditMemo(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_DocType docType = assumeNotNull(getC_DocType(invoice), "The given C_Invoice_ID={} needs to have a C_DocType", invoice.getC_Invoice_ID());
		final String docBaseType = docType.getDocBaseType();
		return isCreditMemo(docBaseType);
	}

	@Override
	public final boolean isCreditMemo(final String docBaseType)
	{
		final InvoiceDocBaseType invoiceDocBaseType = InvoiceDocBaseType.ofNullableCode(docBaseType);
		return invoiceDocBaseType != null && invoiceDocBaseType.isCreditMemo();
	}

	@Override
	public final boolean isARCreditMemo(final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_DocType docType = getC_DocType(invoice);
		final InvoiceDocBaseType invoiceDocBaseType = InvoiceDocBaseType.ofCode(docType.getDocBaseType());
		return invoiceDocBaseType.isCustomerCreditMemo();
	}

	@Override
	public final boolean isAdjustmentCharge(final org.compiere.model.I_C_Invoice invoice)
	{
		final I_C_DocType docType = getC_DocType(invoice);

		return isAdjustmentCharge(docType);
	}

	@Override
	public final boolean isAdjustmentCharge(final I_C_DocType docType)
	{
		final String docBaseType = docType.getDocBaseType();

		// only ARI base type
		if (!X_C_DocType.DOCBASETYPE_ARInvoice.equals(docBaseType))
		{
			return false;
		}

		final String docSubType = docType.getDocSubType();

		// Must have a subtype
		if (docSubType == null)
		{
			return false;
		}

		// must be one of Mengendifferenz or Preisdifferenz
		if (X_C_DocType.DOCSUBTYPE_AQ.compareTo(docSubType) != 0
				&& X_C_DocType.DOCSUBTYPE_AP.compareTo(docSubType) != 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean isReversal(final org.compiere.model.I_C_Invoice invoice)
	{
		if (invoice == null)
		{
			return false;
		}
		if (invoice.getReversal_ID() <= 0)
		{
			return false;
		}
		// the reversal is always younger than the original document
		return invoice.getC_Invoice_ID() > invoice.getReversal_ID();
	}

	@Override
	public final boolean isComplete(final org.compiere.model.I_C_Invoice invoice)
	{
		final DocStatus docStatus = DocStatus.ofCode(invoice.getDocStatus());
		return docStatus.isCompletedOrClosedOrReversed();
	}

	@Override
	public final CurrencyPrecision getPricePrecision(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(invoice.getM_PriceList_ID());
		return priceListId != null
				? Services.get(IPriceListBL.class).getPricePrecision(priceListId)
				: CurrencyPrecision.ZERO;
	}

	@Override
	public final CurrencyPrecision getPricePrecision(@NonNull final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();
		return getPricePrecision(invoice);
	}

	@Override
	public final CurrencyPrecision getAmountPrecision(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(invoice.getM_PriceList_ID());
		return priceListId != null
				? Services.get(IPriceListBL.class).getAmountPrecision(priceListId)
				: CurrencyPrecision.ZERO;
	}

	@Override
	public final CurrencyPrecision getAmountPrecision(@NonNull final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();
		return getAmountPrecision(invoice);
	}

	@Override
	public final CurrencyPrecision getTaxPrecision(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(invoice.getM_PriceList_ID());
		return priceListId != null
				? Services.get(IPriceListBL.class).getTaxPrecision(priceListId)
				: CurrencyPrecision.ZERO;
	}

	@Override
	public final CurrencyPrecision getTaxPrecision(@NonNull final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		final org.compiere.model.I_C_Invoice invoice = invoiceLine.getC_Invoice();
		return getTaxPrecision(invoice);
	}

	@Override
	public final de.metas.adempiere.model.I_C_Invoice adjustmentCharge(@NonNull final AdjustmentChargeCreateRequest adjustmentChargeCreateRequest)
	{
		final org.compiere.model.I_C_Invoice invoice = getById(adjustmentChargeCreateRequest.getInvoiceID());
		final DocBaseAndSubType docBaseAndSubType = adjustmentChargeCreateRequest.getDocBaseAndSubTYpe();
		final Boolean isSOTrx = adjustmentChargeCreateRequest.getIsSOTrx();

		final DocTypeId targetDocTypeID = Services.get(IDocTypeDAO.class).getDocTypeId(DocTypeQuery.builder()
				.docBaseType(docBaseAndSubType.getDocBaseType())
				.docSubType(docBaseAndSubType.getDocSubType())
				.adClientId(invoice.getAD_Client_ID())
				.adOrgId(invoice.getAD_Org_ID())
				.build());
		final I_C_Invoice adjustmentCharge = InterfaceWrapperHelper.create(
				copyFrom(
						invoice,
						SystemTime.asTimestamp(),
						targetDocTypeID.getRepoId(),
						isSOTrx == null ? invoice.isSOTrx() : isSOTrx,
						false, // counter == false
						true, // setOrderRef == true
						true, // setInvoiceRef == true
						true), // copyLines == true
				I_C_Invoice.class);

		adjustmentCharge.setDescription("Nachbelastung zu Rechnung " + invoice.getDocumentNo() + ", Order-Referenz " + invoice.getPOReference() + "\n\nUrsprünglicher Rechnungstext:\n"
				+ invoice.getDescription());

		adjustmentCharge.setRef_Invoice_ID(invoice.getC_Invoice_ID());
		InterfaceWrapperHelper.save(adjustmentCharge);

		return adjustmentCharge;
	}

	@Override
	public final void updateInvoiceLineIsReadOnlyFlags(@NonNull final I_C_Invoice invoice, final I_C_InvoiceLine... invoiceLines)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final boolean saveLines;
		final List<I_C_InvoiceLine> linesToUpdate;
		if (Check.isEmpty(invoiceLines))
		{
			linesToUpdate = invoiceDAO.retrieveLines(invoice);
			saveLines = true;
		}
		else
		{
			linesToUpdate = Arrays.asList(invoiceLines);
			saveLines = false;
		}

		final String docSubType;
		if (invoice.getC_DocTypeTarget_ID() > 0)
		{
			final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
			docSubType = docTypeDAO.getById(invoice.getC_DocTypeTarget_ID()).getDocSubType();
		}
		else
		{
			docSubType = null;
		}
		final boolean qtyReadOnly;
		final boolean priceReadOnly;
		final boolean orderLineReadOnly; // task 09182

		if (I_C_Invoice.DOC_SUBTYPE_ARI_AQ.equals(docSubType) || I_C_Invoice.DOC_SUBTYPE_ARC_CQ.equals(docSubType))
		{
			priceReadOnly = true;
			qtyReadOnly = false;
			orderLineReadOnly = true;
		}
		// if DocType is "Nachbelastung - Preisdifferenz" set Qty readonly
		else if (I_C_Invoice.DOC_SUBTYPE_ARI_AP.equals(docSubType) || I_C_Invoice.DOC_SUBTYPE_ARC_CR.equals(docSubType))
		{
			qtyReadOnly = true;
			priceReadOnly = false;
			orderLineReadOnly = true;
		}
		else if (I_C_Invoice.DOC_SUBTYPE_ARC_CS.equals(docSubType))
		{
			qtyReadOnly = false;
			priceReadOnly = false;
			orderLineReadOnly = false; // task 09182: the user needs to specify (on invoice line level!) the order lines referenced by the invoice
		}
		// for other doc types, we let both fields be read-write, and the orderline be read-only/not shown as usual
		else
		{
			qtyReadOnly = false;
			priceReadOnly = false;
			orderLineReadOnly = true;
		}

		for (final I_C_InvoiceLine lineToUpdate : linesToUpdate)
		{
			lineToUpdate.setIsPriceReadOnly(priceReadOnly);
			lineToUpdate.setIsQtyReadOnly(qtyReadOnly);
			lineToUpdate.setIsOrderLineReadOnly(orderLineReadOnly);

			if (saveLines)
			{
				InterfaceWrapperHelper.save(lineToUpdate);
			}
		}
	}

	@Override
	public final void registerCopyHandler(
			final IQueryFilter<ImmutablePair<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_Invoice>> filter,
			final IDocCopyHandler<org.compiere.model.I_C_Invoice, org.compiere.model.I_C_InvoiceLine> copyhandler)
	{
		Services.get(ICopyHandlerBL.class).registerCopyHandler(org.compiere.model.I_C_Invoice.class, filter, copyhandler);
	}

	@Override
	public final void registerLineCopyHandler(
			final IQueryFilter<ImmutablePair<org.compiere.model.I_C_InvoiceLine, org.compiere.model.I_C_InvoiceLine>> filter,
			final IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> copyhandler)
	{
		Services.get(ICopyHandlerBL.class).registerCopyHandler(org.compiere.model.I_C_InvoiceLine.class, filter, copyhandler);

	}

	@Override
	public final TaxCategoryId getTaxCategoryId(final I_C_InvoiceLine invoiceLine)
	{
		final ChargeRepository chargeRepo = SpringContextHolder.instance.getBean(ChargeRepository.class);
		// In case we have a charge, use the tax category from charge
		final ChargeId chargeId = ChargeId.ofRepoIdOrNull(invoiceLine.getC_Charge_ID());
		if (chargeId != null)
		{
			final I_C_Charge chargeRecord = chargeRepo.getById(chargeId);
			return TaxCategoryId.ofRepoId(chargeRecord.getC_TaxCategory_ID());
		}

		final IPricingContext pricingCtx = Services.get(IInvoiceLineBL.class).createPricingContext(invoiceLine);
		final IPricingResult pricingResult = Services.get(IPricingBL.class).calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			return null;
		}

		return pricingResult.getTaxCategoryId();
	}

	@Override
	public final void handleReversalForInvoice(final org.compiere.model.I_C_Invoice invoice)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final int reversalInvoiceId = invoice.getReversal_ID();
		Check.assume(reversalInvoiceId > invoice.getC_Invoice_ID(), "Invoice {} shall be the original invoice and not it's reversal", invoice);
		final org.compiere.model.I_C_Invoice reversalInvoice = invoice.getReversal();

		// services
		final IMatchInvBL matchInvBL = Services.get(IMatchInvBL.class);
		final IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		for (final I_C_InvoiceLine il : invoiceDAO.retrieveLines(invoice))
		{
			// task 08627: unlink possible inOutLines because the inOut might now be reactivated and they might be deleted.
			// Unlinking them now is more performant than selecting an unlinking them when the inOutLine is actually deleted.
			il.setM_InOutLine(null);
			InterfaceWrapperHelper.save(il);

			//
			// Retrieve the reversal invoice line
			final I_C_InvoiceLine reversalLine = invoiceDAO.retrieveReversalLine(il, reversalInvoiceId);

			// 08809
			// Also set the Attribute Set Instance in the reversal line
			attributeSetInstanceBL.cloneASI(reversalLine, il);
			InterfaceWrapperHelper.save(reversalLine);

			//
			// Create M_MatchInv reversal records, linked to reversal invoice line and original inout line.
			final List<I_M_MatchInv> matchInvs = matchInvDAO.retrieveForInvoiceLine(il);
			for (final I_M_MatchInv matchInv : matchInvs)
			{
				final I_M_InOutLine inoutLine = matchInv.getM_InOutLine();

				final StockQtyAndUOMQty qtyToMatchExact = StockQtyAndUOMQtys.create(
						matchInv.getQty().negate(), ProductId.ofRepoId(inoutLine.getM_Product_ID()),
						matchInv.getQtyInUOM().negate(), UomId.ofRepoId(matchInv.getC_UOM_ID()));

				matchInvBL.createMatchInvBuilder()
						.setContext(reversalLine)
						.setC_InvoiceLine(reversalLine)
						.setM_InOutLine(inoutLine)
						.setDateTrx(reversalInvoice.getDateInvoiced())
						.setQtyToMatchExact(qtyToMatchExact)
						.build();
			}
		}
	}

	@Override
	public final void allocateCreditMemo(final I_C_Invoice invoice,
										 final I_C_Invoice creditMemo,
										 final BigDecimal openAmt)
	{
		final Timestamp dateTrx = TimeUtil.max(invoice.getDateInvoiced(), creditMemo.getDateInvoiced());
		final Timestamp dateAcct = TimeUtil.max(invoice.getDateAcct(), creditMemo.getDateAcct());

		//
		// allocate the invoice against the credit memo
		// @formatter:off
		Services.get(IAllocationBL.class)
			.newBuilder()
			.orgId(invoice.getAD_Org_ID())
			.dateTrx(dateTrx)
			.dateAcct(dateAcct)
			.currencyId(invoice.getC_Currency_ID())
			.addLine()
				.orgId(invoice.getAD_Org_ID())
				.bpartnerId(invoice.getC_BPartner_ID())
				.invoiceId(invoice.getC_Invoice_ID())
				.amount(invoice.isSOTrx() ? openAmt : openAmt.negate())
			.lineDone()
			.addLine()
				.orgId(creditMemo.getAD_Org_ID())
				.bpartnerId(creditMemo.getC_BPartner_ID())
				.invoiceId(creditMemo.getC_Invoice_ID())
				.amount(invoice.isSOTrx() ? openAmt.negate() : openAmt)
			.lineDone()
			.create(true); // completeIt = true
		// @formatter:on
	}

	@Override
	public PaymentRule getDefaultPaymentRule()
	{
		final ISysConfigBL sysconfigs = Services.get(ISysConfigBL.class);
		return sysconfigs.getReferenceListAware(SYSCONFIG_C_Invoice_PaymentRule, PaymentRule.OnCredit, PaymentRule.class);
	}

	@Override
	public final void discountInvoice(final @NonNull org.compiere.model.I_C_Invoice invoice, final @NonNull Amount discountAmt, final @NonNull Timestamp date)
	{
		if (discountAmt.signum() == 0)
		{
			return;
		}

		final Amount discountAmtAbs = discountAmt.negateIfNot(invoice.isSOTrx());

		// don't declare during init; it won't be available yet
		final CurrencyRepository currenciesRepo = SpringContextHolder.instance.getBean(CurrencyRepository.class);
		final CurrencyId currencyId = currenciesRepo.getCurrencyIdByCurrencyCode(discountAmt.getCurrencyCode());

		// @formatter:off
		Services.get(IAllocationBL.class).newBuilder()
			.orgId(invoice.getAD_Org_ID())
			.currencyId(currencyId)
			.dateAcct(date)
			.dateTrx(date)
			.addLine()
				.orgId(invoice.getAD_Org_ID())
				.bpartnerId(invoice.getC_BPartner_ID())
				.invoiceId(invoice.getC_Invoice_ID())
				.amount(BigDecimal.ZERO)
				.discountAmt(discountAmtAbs.getAsBigDecimal())
			.lineDone()
			.create(true);
		// @formatter:on
	}

	@Override
	@Nullable
	public String getLocationEmail(@NonNull final InvoiceId invoiceId)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final IBPartnerDAO partnersRepo = Services.get(IBPartnerDAO.class);

		final org.compiere.model.I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final I_C_BPartner_Location bpartnerLocation = partnersRepo.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(bpartnerId, invoice.getC_BPartner_Location_ID()));

		final String locationEmail = bpartnerLocation.getEMail();
		if (!Check.isEmpty(locationEmail))
		{
			return locationEmail;
		}

		final BPartnerContactId invoiceContactId = BPartnerContactId.ofRepoIdOrNull(bpartnerId, invoice.getAD_User_ID());

		if (invoiceContactId == null)
		{
			return null;
		}

		final I_AD_User invoiceContactRecord = partnersRepo.getContactById(invoiceContactId);

		final BPartnerLocationId contactLocationId = BPartnerLocationId.ofRepoIdOrNull(bpartnerId, invoiceContactRecord.getC_BPartner_Location_ID());
		if (contactLocationId != null)
		{
			final I_C_BPartner_Location contactLocationRecord = partnersRepo.getBPartnerLocationByIdEvenInactive(contactLocationId);
			final String contactLocationEmail = contactLocationRecord.getEMail();

			if (!Check.isEmpty(contactLocationEmail))
			{
				return contactLocationEmail;
			}
		}

		return null;
	}
}
