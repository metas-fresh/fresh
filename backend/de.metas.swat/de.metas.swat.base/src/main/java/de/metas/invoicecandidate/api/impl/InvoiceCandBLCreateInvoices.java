package de.metas.invoicecandidate.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.impex.InputDataSourceId;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IMatchInvBL;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateListeners;
import de.metas.invoicecandidate.api.IInvoiceGenerator;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.InvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.lang.ExternalIdsUtil;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.Tax;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.IdentityHashSet;
import de.metas.workflow.api.IWFExecutionFactory;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.copyValues;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

public class InvoiceCandBLCreateInvoices implements IInvoiceGenerator
{
	private static final AdMessageKey MSG_INVOICE_CAND_BL_PROCESSING_ERROR_0P = AdMessageKey.of("InvoiceCandBL_Processing_Error");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_PROCESSING_ERROR_DESC_1P = AdMessageKey.of("InvoiceCandBL_Processing_Error_Desc");

	//
	// Services
	private static final transient Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandBLCreateInvoices.class);

	private final transient IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient IInvoiceDAO invoicesRepo = Services.get(IInvoiceDAO.class);
	private final transient IInvoiceCandidateListeners invoiceCandListeners = Services.get(IInvoiceCandidateListeners.class);
	private final transient IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IWFExecutionFactory wfExecutionFactory = Services.get(IWFExecutionFactory.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IMatchInvBL matchInvBL = Services.get(IMatchInvBL.class);
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
	private final transient DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);
	private final transient IUserBL userBL = Services.get(IUserBL.class);

	//
	// Parameters
	private Properties _ctx;
	private String _trxName;
	private Class<? extends IInvoiceGeneratorRunnable> invoiceGeneratorClass = null;
	private static final boolean createInvoiceFromOrder = false; // FIXME: 08511 workaround
	private Boolean _ignoreInvoiceSchedule = null;
	private InvoicingParams _invoicingParams;
	private IInvoiceGenerateResult _collector;

	/**
	 * Implementations of this interface are responsible for converting a given {@link IInvoiceHeader} to an {@link I_C_Invoice} with lines and process it.
	 */
	protected interface IInvoiceGeneratorRunnable extends TrxRunnable
	{
		/**
		 * Initialize this processor. This method will be called by framework, don't call it directly.
		 */
		void init(Properties ctx, IInvoiceHeader header);

		/**
		 * @return generated invoice
		 */
		I_C_Invoice getC_Invoice();

		/**
		 * @return generated notifications or empty list if there are no notifications
		 */
		List<I_AD_Note> getNotifications();
	}

	/**
	 * Default {@link IInvoiceGeneratorRunnable} implementation
	 */
	// NOTE: not static because we share the services
	private class DefaultInvoiceGeneratorRunnable implements IInvoiceGeneratorRunnable, TrxRunnable2
	{
		// Input parameters
		private IInvoiceHeader header;
		private Properties ctx;

		private I_C_Invoice createdInvoice = null;
		private final List<I_AD_Note> notifications = new ArrayList<>();

		private Throwable throwable;

		public DefaultInvoiceGeneratorRunnable()
		{
		}

		@Override
		public void init(final Properties ctx, @NonNull final IInvoiceHeader header)
		{
			this.header = header;
			this.ctx = ctx;
		}

		@Override
		public void run(final String trxName)
		{
			// we need the trxName to be set because method 'createInvoiceLines' will have a nested TrxRunner
			// and that runner needs to work with a non-local trx
			trxManager.assertTrxNameNotNull(trxName);

			//
			// Create invoice header
			final I_C_Invoice invoice = createInvoice(ctx, header, trxName);

			//
			// Validate created invoice
			final String invoiceTrxName = InterfaceWrapperHelper.getTrxName(invoice);
			Check.assume(
					trxManager.isSameTrxName(trxName, invoiceTrxName),
					"Invoice {} (TrxName='{}') has TrxName={}",
					invoice, invoiceTrxName, trxName);

			//
			// Create invoice lines
			final List<I_C_InvoiceLine> lines = createInvoiceLines(invoice, header.getLines(), trxName);

			//
			// If there were no lines generated, delete the invoice because it's pointless to have it.
			if (lines.isEmpty())
			{
				invoicesRepo.delete(invoice);
				return;
			}

			//
			// Check if we shall renumber the lines and renumber them if needed.
			// We are preventing lines renumbering if there is at least one invoice with a fixed LineNo set (which actually comes from invoice candidate).
			invoiceBL.renumberLines(lines, 10);

			// task 08926: on invoice creation
			final List<I_C_Invoice_Candidate> allCands = new ArrayList<>();

			for (final IInvoiceCandAggregate aggregated : header.getLines())
			{
				allCands.addAll(aggregated.getAllCands());
			}

			if (!allCands.isEmpty())
			{
				invoiceCandListeners.onBeforeInvoiceComplete(invoice, allCands);
			}

			//
			// Complete the invoice and assume it's status is COmpleted.
			docActionBL.processEx(invoice, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			//
			// Set the created invoice which will be retrieved by the caller.
			createdInvoice = create(invoice, I_C_Invoice.class);
		}

		@Override
		public boolean doCatch(final Throwable throwable)
		{
			createdInvoice = null; // discard current invoice because it will be rolled back
			this.throwable = throwable; // store the throwable for our 'doFinally()' invocation

			return true; // rollback transaction
		}

		@Override
		public void doFinally()
		{
			final List<I_C_Invoice_Candidate> allCandidates = header.getAllInvoiceCandidates();

			//
			// Update invoice candidates
			for (final I_C_Invoice_Candidate icRecord : allCandidates)
			{
				try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(icRecord))
				{
					final ZoneId timeZone = orgDAO.getTimeZone(header.getOrgId());

					logger.debug("Set both DateInvoiced={} and DateAcct={} from IInvoiceHeader; timezone={}", header.getDateInvoiced(), header.getDateAcct(), timeZone);
					icRecord.setDateInvoiced(TimeUtil.asTimestamp(header.getDateInvoiced(), timeZone));
					icRecord.setDateAcct(TimeUtil.asTimestamp(header.getDateAcct(), timeZone));
				}
			}

			//
			// Mark and attach note to those with errors
			if (throwable != null)
			{
				final List<I_AD_Note> notice = createNoticesAndMarkICs(allCandidates, throwable);
				notifications.addAll(notice);
			}
			//
			// If no error, clear previous errors if any.
			// We had a successful invoicing of those candidates.
			else
			{
				allCandidates.forEach(invoiceCandBL::clearInvoicingErrorAndSave);
			}

			//
			// Make sure all candidates were saved
			invoiceCandDAO.saveAll(allCandidates);
		}

		@Override
		public I_C_Invoice getC_Invoice()
		{
			return createdInvoice;
		}

		@Override
		public List<I_AD_Note> getNotifications()
		{
			return notifications;
		}

		private I_C_Invoice createInvoice(
				final Properties ctx,
				final IInvoiceHeader invoiceHeader,
				final String trxName)
		{
			final I_C_Invoice invoice;

			//
			// Case: our invoice is linked to an order
			// => start creating the invoice from Order
			if (createInvoiceFromOrder && invoiceHeader.getC_Order_ID() > 0)
			{
				final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(invoiceHeader.getC_Order_ID()));

				final DocTypeId invoiceDocTypeId = invoiceHeader.getC_DocTypeInvoice() != null
						? DocTypeId.ofRepoId(invoiceHeader.getC_DocTypeInvoice().getC_DocType_ID())
						: null;
				invoice = create(
						invoiceBL.createInvoiceFromOrder(
								order,
								invoiceDocTypeId,
								invoiceHeader.getDateInvoiced(),
								invoiceHeader.getDateAcct() // task 08437
						),
						I_C_Invoice.class);

				// Validate M_PriceList_ID
				final int invoicePriceListId = invoice.getM_PriceList_ID();
				Check.assume(
						invoicePriceListId == IPriceListDAO.M_PriceList_ID_None || invoicePriceListId == order.getM_PriceList_ID(),
						"M_PriceList_ID=" + invoicePriceListId + " is inconsistent with the M_PriceList_ID of " + order);
			}
			//
			// Case: our invoice is not linked to an order
			// => start creating the invoice from scratch
			else
			{
				invoice = create(ctx, I_C_Invoice.class, trxName);
				invoice.setC_PaymentTerm_ID(PaymentTermId.toRepoId(invoiceHeader.getPaymentTermId()));

				final ZoneId timeZone = orgDAO.getTimeZone(invoiceHeader.getOrgId());
				invoice.setDateInvoiced(TimeUtil.asTimestamp(invoiceHeader.getDateInvoiced(), timeZone));
				invoice.setDateAcct(TimeUtil.asTimestamp(invoiceHeader.getDateAcct(), timeZone)); // 03905: also updating DateAcct

				invoice.setM_PriceList_ID(invoiceHeader.getM_PriceList_ID()); // #367: get M_PriceList_ID directly from invoiceHeader.
				Set<String> externalIds = invoiceHeader.getAllInvoiceCandidates().stream().map(I_C_Invoice_Candidate::getExternalHeaderId).filter(Objects::nonNull).collect(Collectors.toSet());
				Check.assume(externalIds.size() <= 1, "Unexpectedly found multiple externalId candidates for the same invoice: {}", externalIds);
				invoice.setExternalId(externalIds.stream().findFirst().orElse(null));
			}

			// 08451: we need to get the resp taxIncluded value from the IC, even if there is a C_Order_ID
			invoice.setIsTaxIncluded(invoiceHeader.isTaxIncluded()); // tasks 04119

			invoice.setAD_Org_ID(invoiceHeader.getOrgId().getRepoId());

			invoice.setSalesRep_ID(UserId.toRepoId(invoiceHeader.getSalesRepId()));

			setC_DocType(invoice, invoiceHeader);

			final BPartnerInfo billTo = invoiceHeader.getBillTo();
			InvoiceDocumentLocationAdapterFactory
					.locationAdapter(invoice)
					.setFrom(billTo.toDocumentLocation());

			invoice.setC_Currency_ID(invoiceHeader.getCurrencyId().getRepoId()); // 03805
			final BPartnerId salesRepId = invoiceHeader.getSalesPartnerId();
			if (!BPartnerId.equals(billTo.getBpartnerId(), salesRepId))
			{
				invoice.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(salesRepId));
			}
			invoiceBL.updateDescriptionFromDocTypeTargetId(invoice, invoiceHeader.getDescription(), invoiceHeader.getDescriptionBottom());

			invoice.setIsSOTrx(header.isSOTrx());

			invoice.setPOReference(invoiceHeader.getPOReference()); // task 07978
			if(Check.isBlank(invoice.getEMail()))
			{
				invoice.setEMail(invoiceHeader.getEMail());
			}
			invoice.setC_Order_ID(invoiceHeader.getC_Order_ID()); // set order reference, if any
			invoice.setC_Incoterms_ID(invoiceHeader.getC_Incoterms_ID());
			invoice.setIncotermLocation(invoiceHeader.getIncotermLocation());
			invoice.setC_Async_Batch_ID(invoiceHeader.getC_Async_Batch_ID());
			invoice.setAD_InputDataSource_ID(InputDataSourceId.toRepoId(invoiceHeader.getAD_InputDataSource_ID()));

			if (invoiceHeader.getM_InOut_ID() > 0)
			{
				final I_M_InOut inout = inoutDAO.getById(InOutId.ofRepoId(invoiceHeader.getM_InOut_ID()));
				invoice.setM_InOut_ID(inout.getM_InOut_ID()); // task 06630
				if(!Check.isBlank(inout.getEMail()))
				{
					invoice.setEMail(inout.getEMail());
				}
			}

			invoice.setPaymentRule(invoiceHeader.getPaymentRule());
			// Save and return the invoice
			invoicesRepo.save(invoice);
			return invoice;
		}

		private List<I_C_InvoiceLine> createInvoiceLines(
				final I_C_Invoice invoice,
				final List<IInvoiceCandAggregate> aggregates,
				final String trxName)
		{
			// we need the trxName to be set because we will have a nested TrxRunner and that runner needs to work with
			// a non-local trx
			Check.assume(!Check.isEmpty(trxName), "Param 'trxName' is not empty");

			final List<I_C_InvoiceLine> createdLines = new ArrayList<>();

			//
			// Set of InvoiceLineRWs which were already processed.
			// Used to avoid processing (i.e. creating invoice line) an IInvoiceLineRW more then once.
			// NOTE: we are not relying on IInvoiceLineRW's equals() method because it could be that they "look" the same but they are not.
			final Set<IInvoiceLineRW> processedLines = new IdentityHashSet<>();

			for (final IInvoiceCandAggregate aggregate : aggregates)
			{
				// In case of a problem, this two variables are used to hand the problematic candidates and the
				// exception over to the TrxRunnable's doFinally() method for logging
				final List<I_C_Invoice_Candidate> errorCandidates = new ArrayList<>();
				final AdempiereException[] errorException = { null };

				//
				// The invoice lines from 'aggregate' are generated in a common trx runner.
				// That way we can undo all invoice lines if the creation of one of them fails.
				final DefaultInvoiceLineGeneratorRunnable genLines = new DefaultInvoiceLineGeneratorRunnable(invoice,
																											 aggregate, processedLines,
																											 errorCandidates, errorException,
																											 trxName);

				// task 08927: we already do the ILAs in here, so we won't need to update them again.
				InvoiceCandBL.DYNATTR_INVOICING_FROM_INVOICE_CANDIDATES_IS_IN_PROGRESS.setValue(invoice, Boolean.TRUE);

				trxManager.run(trxName, genLines);
				createdLines.addAll(genLines.getCreatedLines());
			}

			return createdLines;
		}
	}

	private void setInvoiceLineNo(final I_C_InvoiceLine invoiceLine, final int lineNo)
	{
		if (lineNo > 0)
		{
			invoiceLine.setLine(lineNo);
			invoiceBL.setHasFixedLineNumber(invoiceLine, true);
		}
		else
		{
			invoiceLine.setLine(0); // auto
			invoiceBL.setHasFixedLineNumber(invoiceLine, false);
		}
	}

	private void setC_DocType(final I_C_Invoice invoice, @NonNull final IInvoiceHeader invoiceHeader)
	{
		final InvoiceDocBaseType invoiceHeaderDocBaseType = invoiceHeader.getDocBaseType();

		if (invoice.getC_DocTypeTarget_ID() <= 0)
		{
			final I_C_DocType invoiceHeaderDocType = invoiceHeader.getC_DocTypeInvoice();
			if (invoiceHeaderDocType != null)
			{
				invoiceBL.setDocTypeTargetIdAndUpdateDescription(invoice, invoiceHeaderDocType.getC_DocType_ID());
				invoice.setIsSOTrx(invoiceHeaderDocType.isSOTrx());
			}
			else
			{
				invoiceBL.setDocTypeTargetId(invoice, invoiceHeaderDocBaseType);
			}
		}

		//
		// In case our InvoiceHeader is about a Memo, but so far our invoice's document type is not credit memo
		// We need to correct this right away.
		//
		// NOTE: An example about when this could happen is when you create the invoice from an order
		// and the invoice document type is fetched from order's document type
		// and that document type is not a credit memo.
		{
			final boolean invoiceHeader_IsCreditMemo = invoiceHeaderDocBaseType != null && invoiceHeaderDocBaseType.isCreditMemo();
			final I_C_DocType invoiceDocType = docTypeDAO.getById(invoice.getC_DocTypeTarget_ID());
			Check.assumeNotNull(invoiceDocType, "invoiceDocType not null"); // shall not happen
			final InvoiceDocBaseType invoiceDocBaseType = InvoiceDocBaseType.ofCode(invoiceDocType.getDocBaseType());
			final boolean invoice_IsCreditMemo = invoiceDocBaseType.isCreditMemo();
			if (invoiceHeader_IsCreditMemo && !invoice_IsCreditMemo)
			{
				invoiceBL.setDocTypeTargetId(invoice, invoiceHeaderDocBaseType);
			}
		}
	}

	// NOTE: not static because we share the services
	private class DefaultInvoiceLineGeneratorRunnable implements TrxRunnable2
	{
		private final List<I_C_InvoiceLine> createdLines;
		private final I_C_Invoice invoice;
		private final List<I_C_Invoice_Candidate> errorCandidates;
		private final AdempiereException[] errorException;
		private final IInvoiceCandAggregate aggregate;
		private final Set<IInvoiceLineRW> processedLines;
		private final String trxName;

		private DefaultInvoiceLineGeneratorRunnable(final I_C_Invoice invoice,
				final IInvoiceCandAggregate aggregate, final Set<IInvoiceLineRW> processedLines,
				final List<I_C_Invoice_Candidate> errorCandidates, final AdempiereException[] errorException,
				final String trxName)
		{
			createdLines = new ArrayList<>();

			this.invoice = invoice;
			this.errorCandidates = errorCandidates;
			this.errorException = errorException;
			this.aggregate = aggregate;
			this.processedLines = processedLines;
			this.trxName = trxName;
		}

		private String getTrxName()
		{
			return trxName;
		}

		@Override
		public void run(final String localTrxName)
		{
			// We want no dedicated 'localTrxName' for this invocation.
			// Instead, we want one "global" trxName that is rolled back (including all invoice lines) if errors occur
			Check.assume(trxName.equals(localTrxName), "trxName=" + trxName + " equals localTrxName=" + localTrxName);

			for (final I_C_Invoice_Candidate cand : aggregate.getAllCands())
			{
				createInvoiceLineForCandidate(cand);

				if (cand.getSplitAmt().signum() != 0)
				{
					final I_C_Invoice_Candidate splitCand = invoiceCandBL.splitCandidate(cand);
					invoiceCandDAO.save(splitCand);
				}

				wfExecutionFactory.notifyActivityPerformed(cand, invoice); // 03745
			}
		}

		private void createInvoiceLineForCandidate(final I_C_Invoice_Candidate cand)
		{
			for (final IInvoiceLineRW ilVO : aggregate.getLinesFor(cand))
			{
				if (!processedLines.add(ilVO))
				{
					// 'ilVO' has already been processed

					// Note: this assumption makes sure that IInvoiceCandDAO.createIlaIfNotExists() has
					// been called for 'cand'
					Check.assume(aggregate.getCandsFor(ilVO).contains(cand), cand + " is returned by 'aggregate.getCandsFor(ilVO)' with ilVO=" + ilVO);
					continue;
				}

				final I_C_InvoiceLine invoiceLine = invoiceBL.createLine(invoice);
				final Properties ilCtx = InterfaceWrapperHelper.getCtx(invoiceLine);
				final String ilTrxName = InterfaceWrapperHelper.getTrxName(invoiceLine);
				Check.assume(
						trxName.equals(ilTrxName),
						invoiceLine + " (TrxName='" + ilTrxName + "') has TrxName=" + trxName);

				//
				// Set Invoice Line's LineNo if available
				final int lineNo = ilVO.getLineNo();
				setInvoiceLineNo(invoiceLine, lineNo);

				invoiceLine.setIsPrinted(ilVO.isPrinted()); // 07371

				final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(ilVO.getC_OrderLine_ID());
				if (orderLineId != null)
				{
					//
					// Special case: we can retain the 1:n relation between C_OrderLine and C_InvoiceLine

					//
					// Note that this also sets the order line's C_Tax_ID
					invoiceLine.setC_OrderLine_ID(orderLineId.getRepoId());
					final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId.getRepoId());
					if (orderLine != null)
					{
						invoiceLine.setC_Project_ID(orderLine.getC_Project_ID());
					}

					// // 07242
					// // Also set the tax from the orderLine
					// ts: don't set it here; in MInvoiceLine.beforeSave() we call MInvoiceLine.setTax() to set it on the fly. Don't just take the order line's rate, because it might have changed
					// since the order was created.
					// final I_C_OrderLine orderLine = create(ilCtx, orderLineId, I_C_OrderLine.class, ilTrxName);
					// invoiceLine.setC_Tax_ID(orderLine.getC_Tax_ID());
				}

				final StockQtyAndUOMQty qtysToInvoice = ilVO.getQtysToInvoice();

				final Collection<Integer> iciolIds = ilVO.getC_InvoiceCandidate_InOutLine_IDs();

				//
				// If there is only one IC-IOL association,
				// set the inherited InOutLine ID on invoice line if applicable
				// and rely on MInvoice/MInvoiceLine to automatically create the MatchInv record (see MInvoice.completeIt0)
				//
				// If there more IC-IOL associations, we will handle them below, also in this method.
				boolean matchInvCreatedElsewhere = false;
				if (iciolIds.size() == 1)
				{
					// Note: further down, we also deal with the case of iciolIds.size()>1
					final int iciolId = iciolIds.iterator().next();
					final I_C_InvoiceCandidate_InOutLine iciol = create(ilCtx, iciolId, I_C_InvoiceCandidate_InOutLine.class, ilTrxName);
					invoiceLine.setM_InOutLine_ID(iciol.getM_InOutLine_ID());

					// we rely on MInvoice.complete() to create the M_MatchInv record for PO and SO-Invoices, so don't create them again here.
					matchInvCreatedElsewhere = true;
				}

				invoiceLine.setDescription(ilVO.getDescription()); // 03439

				invoiceLine.setC_UOM_ID(cand.getC_UOM_ID()); // 06718
				invoiceLine.setPrice_UOM_ID(cand.getPrice_UOM_ID()); // 06942
				invoiceLine.setC_Shipping_Location_ID(cand.getC_Shipping_Location_ID());

				invoiceLine.setC_Flatrate_Term_ID(cand.getC_Flatrate_Term_ID());
				//
				// Product / Charge
				if (ilVO.getM_Product_ID() > 0)
				{
					invoiceLine.setM_Product_ID(ilVO.getM_Product_ID());
				}
				invoiceBL.setQtys(invoiceLine, qtysToInvoice); // task: 08841; note that we need to call this method *after* UOMs and product were set

				invoiceLine.setC_Charge_ID(ilVO.getC_Charge_ID());
				invoiceLine.setIsPackagingMaterial(cand.isPackagingMaterial()); // task FRESH-273

				//
				// ASI
				final I_M_AttributeSetInstance asi = createASI(ilVO.getInvoiceLineAttributes());
				invoiceLine.setM_AttributeSetInstance(asi);

				invoiceLine.setPriceEntered(ilVO.getPriceEntered().toMoney().toBigDecimal());
				invoiceLine.setDiscount(ilVO.getDiscount().toBigDecimal());
				invoiceLine.setPriceActual(ilVO.getPriceActual().toMoney().toBigDecimal());

				// set activity, tax and tax category from the invoice candidate (07442)
				invoiceLine.setC_Activity_ID(ilVO.getC_Activity_ID());

				final Tax tax = ilVO.getC_Tax();
				if (tax != null)  // guard against old ICs which might not have a tax..leave it to the MInvoiceLine BL in that case
				{
					invoiceLine.setC_Tax_ID(tax.getTaxId().getRepoId());
					invoiceLine.setC_TaxCategory_ID(tax.getTaxCategoryId().getRepoId());
				}

				// Set Line Net Amount and Tax Amount
				invoiceBL.setLineNetAmt(invoiceLine);
				invoiceBL.setTaxAmt(invoiceLine);

				final List<I_C_Invoice_Candidate> candsForIlVO = aggregate.getCandsFor(ilVO);

				final List<String> externalIds = candsForIlVO
						.stream()
						.map(I_C_Invoice_Candidate::getExternalLineId)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList());
				invoiceLine.setExternalIds(ExternalIdsUtil.joinExternalIds(externalIds));

				final Dimension invoiceCandidateDimension = dimensionService.getFromRecord(cand);
				dimensionService.updateRecord(invoiceLine, invoiceCandidateDimension);

				//
				// Notify listeners that we created a new invoice line and we are about to save it
				invoiceCandListeners.onBeforeInvoiceLineCreated(invoiceLine, ilVO, candsForIlVO);

				//
				// Save the new invoice line and create invoice line allocation for it
				try
				{
					//
					// Actually save the invoice line
					invoicesRepo.save(invoiceLine);

					//
					// Create/Update "invoice line" to "invoice candidate" allocations
					for (final I_C_Invoice_Candidate candForIlVO : candsForIlVO)
					{
						final StockQtyAndUOMQty qtysInvoiced = aggregate.getAllocatedQty(candForIlVO, ilVO);

						final InvoiceCandidateAllocCreateRequest request = InvoiceCandidateAllocCreateRequest.builder()
								.invoiceCand(candForIlVO)
								.invoiceLine(invoiceLine)
								.qtysInvoiced(qtysInvoiced)
								.note(null)
								.invoiceLineAllocType(InvoiceLineAllocType.CreatedFromIC)
								.build();
						invoiceCandBL.createUpdateIla(request); // TODO

						// #870
						// Make sure the Qty and Price override are set to null when an invoiceline is created
						{
							final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(candForIlVO.getC_Invoice_Candidate_ID());

							Services.get(ITrxManager.class)
									.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
									.newEventListener(TrxEventTiming.AFTER_COMMIT)
									.registerWeakly(false) // register "hard", because that's how it was before
									.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
									.registerHandlingMethod(localTrx -> resetQtyAndPriceOverrideOutOfTrx(invoiceCandidateId));
						}
					}

					//
					// Create M_MatchInv (link between receipt line and purchase invoice line)
					// task 08507: also create them for sale invoices, to track the invoiced Qtys per il and iol
					if (!matchInvCreatedElsewhere)
					{
						for (final InvoiceCandidateInOutLineToUpdate iciolToUpdate : ilVO.getInvoiceCandidateInOutLinesToUpdate())
						{
							final I_C_InvoiceCandidate_InOutLine icIol = iciolToUpdate.getC_InvoiceCandidate_InOutLine();
							final I_M_InOutLine inOutLine = icIol.getM_InOutLine();

							matchInvBL.createMatchInvBuilder()
									.setContext(invoiceLine)
									.setC_InvoiceLine(invoiceLine)
									.setM_InOutLine(inOutLine)
									.setDateTrx(invoice.getDateInvoiced())
									.build();
						}
					}

					//
					// Collect the created invoice line
					createdLines.add(invoiceLine);
				}

				catch (final AdempiereException e)
				{
					// store the problematic candidate and the exception so that this TrxRunner's
					// 'doFinally()' method can store the info in the DB, after rollback
					errorException[0] = e;
					errorCandidates.addAll(aggregate.getAllCands());

					// re-throw e to trigger the rollback in case some MInvoiceLines were already created
					throw e;
				}
			}
		}

		/**
		 * This method is called by a transaction listener. We run it out of trx to avoid any sort or interference this the trx the listener is fired from.
		 */
		private void resetQtyAndPriceOverrideOutOfTrx(@NonNull final InvoiceCandidateId invoiceCandidateId)
		{
			final I_C_Invoice_Candidate icRecord = invoiceCandDAO.getByIdOutOfTrx(invoiceCandidateId);
			icRecord.setQtyToInvoice_Override(null);
			icRecord.setPriceEntered_Override(null);
			invoiceCandDAO.save(icRecord);
		}

		private I_M_AttributeSetInstance createASI(final Set<IInvoiceLineAttribute> invoiceLineAttributes)
		{
			// If there are no attributes, return a null ASI
			if (Check.isEmpty(invoiceLineAttributes))
			{
				return null; // no ASI
			}

			// Create ASI
			final I_M_AttributeSetInstance asi = create(getCtx(), I_M_AttributeSetInstance.class, getTrxName());
			asi.setM_AttributeSet_ID(AttributeConstants.M_AttributeSet_ID_None);
			attributesRepo.save(asi);

			// Create one Attribute Instance for each invoice line attribute
			for (final IInvoiceLineAttribute invoiceLineAttribute : invoiceLineAttributes)
			{
				createAttributeInstance(asi, invoiceLineAttribute);
			}

			return asi;
		}

		private void createAttributeInstance(final I_M_AttributeSetInstance asi, final IInvoiceLineAttribute invoiceLineAttribute)
		{
			final I_M_AttributeInstance ai = newInstance(I_M_AttributeInstance.class, asi);
			copyValues(invoiceLineAttribute.getAttributeInstanceTemplate(), ai);
			ai.setAD_Org_ID(asi.getAD_Org_ID());
			ai.setM_AttributeSetInstance(asi);
			ai.setIsActive(true);
			attributesRepo.save(ai);
		}

		@Override
		public boolean doCatch(final Throwable e) throws Throwable
		{
			if (errorException[0] == null)
			{
				errorException[0] = new AdempiereException("Error while processing " + aggregate, e); // here we might end up with an empty errorCandidates list
			}

			// don't re-throw the exception, but rollback the trx
			return true;
		}

		@Override
		public void doFinally()
		{
			if (errorException[0] != null)
			{
				// create the note and mark the candidates
				createNoticesAndMarkICs(errorCandidates, errorException[0]);

				errorCandidates.clear();
				errorException[0] = null;
			}
		}

		public List<I_C_InvoiceLine> getCreatedLines()
		{
			return createdLines;
		}
	}

	/**
	 * <b>IMPORTANT:</b>
	 * <ul>
	 * <li>Candidates with IsError='Y' are ignored, even if they are part of the selection!</li>
	 * <li>Candidates with IsProcessed='Y' are ignored, even if they are part of the selection!</li>
	 * </ul>
	 * <br>
	 */
	@Override
	public IInvoiceGenerateResult generateInvoices(@NonNull final Iterator<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final boolean ignoreInvoiceSchedule = isIgnoreInvoiceSchedule();

		// Total net amount to invoice checker (08610)
		final ICNetAmtToInvoiceChecker netAmtToInvoiceChecker = new ICNetAmtToInvoiceChecker();

		// get our service instance to aggregate the invoice candidates
		final AggregationEngine aggregationEngine = newAggregationEngine();

		final List<I_C_Invoice_Candidate> icToUnlock = new ArrayList<>();

		//
		// Iterate invoice candidates and add them to aggregation engine
		while (invoiceCandidates.hasNext())
		{
			final I_C_Invoice_Candidate ic = invoiceCandidates.next();
			try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(ic))
			{
				icToUnlock.add(ic);

				// Skip invoice candidate if we are adviced to do so
				// TODO: i think this checking is no longer needed because we are doing it when enqueueing
				if (invoiceCandBL.isSkipCandidateFromInvoicing(ic, ignoreInvoiceSchedule))
				{
					continue;
				}

				// add 'ic' to our aggregation
				try
				{
					aggregationEngine.addInvoiceCandidate(ic);
					netAmtToInvoiceChecker.add(ic); // collect the IC's NetAmtToInvoice; later we will make sure the amount is the same as the one user expects
				}
				catch (final AdempiereException e)
				{
					createNoticesAndMarkICs(ImmutableList.of(ic), e);
					Loggables.withLogger(logger, Level.WARN).addLog("Cannot invoice C_Invoice_Candidate_ID=" + ic.getC_Invoice_Candidate_ID() + "! See preceding logged error");
				}
			}
		}

		//
		// Make sure we have the same net amt as expected (08610)
		final InvoicingParams invoicingParams = getInvoicingParams();
		final BigDecimal expectedNetAmtToInvoice = invoicingParams == null ? null : invoicingParams.getCheck_NetAmtToInvoice();
		if (expectedNetAmtToInvoice != null && expectedNetAmtToInvoice.signum() != 0)
		{
			netAmtToInvoiceChecker.assertExpectedNetAmtToInvoice(expectedNetAmtToInvoice);
		}

		//
		// Aggregate collected ICs and create the invoices
		aggregateAndInvoice(aggregationEngine);

		return getCollector();
	}

	private AggregationEngine newAggregationEngine()
	{
		final InvoicingParams invoicingParams = getInvoicingParams();

		return AggregationEngine.builder()
				.alwaysUseDefaultHeaderAggregationKeyBuilder(invoicingParams != null && invoicingParams.isConsolidateApprovedICs())
				.dateInvoicedParam(invoicingParams != null ? invoicingParams.getDateInvoiced() : null)
				.dateAcctParam(invoicingParams != null ? invoicingParams.getDateAcct() : null)
				.useDefaultBillLocationAndContactIfNotOverride(invoicingParams != null && invoicingParams.isUpdateLocationAndContactForInvoice())
				.build();
	}

	/**
	 * @param aggregationEngine note that this is a {@link de.metas.util.IMultitonService}, i.e. a service with internal state.
	 */
	private void aggregateAndInvoice(@NonNull final AggregationEngine aggregationEngine)
	{
		final List<IInvoiceHeader> aggregationResult = aggregationEngine.aggregate();

		if (getInvoicingParams() != null && getInvoicingParams().isAssumeOneInvoice())
		{
			Check.errorIf(aggregationResult.size() > 1, "The shall be only one invoice, but instead there are {}; aggregationResult={}",
						  aggregationResult.size(), aggregationResult);
		}

		//
		// generate an invoice for each aggregate (i.e. 'header')
		for (final IInvoiceHeader header : aggregationResult)
		{
			// skip invoices without lines
			if (header.getLines().isEmpty())
			{
				continue;
			}
			generateInvoice(header);
		}
	}

	private void generateInvoice(@NonNull final IInvoiceHeader header)
	{
		//
		// Instantiate invoice generator class
		final IInvoiceGeneratorRunnable gen;
		if (invoiceGeneratorClass != null)
		{
			try
			{
				gen = invoiceGeneratorClass.newInstance();
			}
			catch (final Exception e)
			{
				throw new AdempiereException("Cannot instantiate invoice generator class: " + invoiceGeneratorClass, e);
			}
		}
		else
		{
			gen = new DefaultInvoiceGeneratorRunnable();
		}

		//
		// Setup and run
		gen.init(getCtx(), header);
		trxManager.run(getTrxName(), gen);

		// Update Result
		final IInvoiceGenerateResult collector = getCollector();
		collector.addInvoice(gen.getC_Invoice());
		collector.addNotifications(gen.getNotifications());

		gen.getC_Invoice();
	}

	/**
	 * @param affectedCands the invoice candidates for which an invoice line creation has failed. Note that an aggregator can create one {@link IInvoiceLineRW} from multiple candidates.
	 */
	private List<I_AD_Note> createNoticesAndMarkICs(
			@NonNull final List<I_C_Invoice_Candidate> affectedCands,
			@NonNull final Throwable error)
	{
		Check.assume(!affectedCands.isEmpty(), "Given list of I_C_Invoice_Candidates is not empty");

		final int USERINCHARGE_NA = -100; // placeholder for user in charge not available

		final Properties ctx = InterfaceWrapperHelper.getCtx(affectedCands.get(0));

		DB.saveConstraints();
		try
		{
			DB.getConstraints().addAllowedTrxNamePrefix("POSave");

			Loggables.addLog("Caught exception " + error + " with message: " + error.getLocalizedMessage(), error);

			final List<I_AD_Note> result = new ArrayList<>();

			final Map<Integer, List<I_C_Invoice_Candidate>> userId2cands = new HashMap<>();

			//
			// find out which user should be notified about which affected candidate
			for (final I_C_Invoice_Candidate ic : affectedCands)
			{
				int userInChargeId = ic.getAD_User_InCharge_ID();
				if (userInChargeId < 0)
				{
					userInChargeId = USERINCHARGE_NA;
				}

				List<I_C_Invoice_Candidate> candsOfUserId = userId2cands.computeIfAbsent(userInChargeId, k -> new ArrayList<>());
				candsOfUserId.add(ic);
			}

			for (final int userId : userId2cands.keySet())
			{
				final List<I_C_Invoice_Candidate> affectedCandsForUser = userId2cands.get(userId);
				final StringBuilder candidates = mkCandIdsString(affectedCandsForUser);

				//
				// Create AD_Note if AD_User_ID is available
				final I_AD_Note note;
				if (userId != USERINCHARGE_NA)
				{
					note = create(ctx, I_AD_Note.class, ITrx.TRXNAME_None);
					note.setAD_Message_ID(msgBL.getIdByAdMessage(MSG_INVOICE_CAND_BL_PROCESSING_ERROR_0P)
												  .map(AdMessageId::getRepoId)
												  .orElse(-1));

					note.setAD_User_ID(userId);

					final I_AD_User user = create(ctx, userId, I_AD_User.class, ITrx.TRXNAME_None);
					note.setAD_Org_ID(user.getAD_Org_ID());
					note.setAD_User_ID(user.getAD_Client_ID());

					note.setReference(error.getLocalizedMessage());

					final Language adLanguage = userBL.getUserLanguage(user);
					final String noteMsg = msgBL.getMsg(
							adLanguage.getAD_Language(),
							MSG_INVOICE_CAND_BL_PROCESSING_ERROR_DESC_1P,
							new Object[] { candidates.toString() });
					note.setTextMsg(noteMsg);
					saveRecord(note);
					// @formatter:off
					// for the time being, always output the warning, because we don't currently use the AD_Note feature and therefore the note wont be read.
					//					if (developerModeBL.isEnabled())
					//					{
					logger.warn(error.getLocalizedMessage(), error);
					//					}
					// @formatter:on
				}
				else
				{
					// User in charge is not available, note cannot be created
					note = null;

					// ...but in this case, at least log the error message
					InvoiceCandBLCreateInvoices.logger.warn(error.getLocalizedMessage(), error);
				}

				//
				// Update invoice candidates IsError and ErrorMsg fields. Link previously created AD_Note if available.
				for (final I_C_Invoice_Candidate currentAffectedCand : affectedCandsForUser)
				{
					final String candErrorMsg = error.getLocalizedMessage();

					invoiceCandBL.setInvoicingErrorAndSave(currentAffectedCand, candErrorMsg, note);
				}

				if (note != null)
				{
					result.add(note);
				}
			}
			return result;

		}
		finally
		{
			DB.restoreConstraints();
		}
	}

	private static StringBuilder mkCandIdsString(final List<I_C_Invoice_Candidate> list)
	{
		final StringBuilder candidates = new StringBuilder();

		boolean first = true;
		int counter = 0;

		for (final I_C_Invoice_Candidate cand : list)
		{
			counter++;
			if (first)
			{
				first = false;
			}
			else
			{
				candidates.append(", ");
			}
			candidates.append(cand.getC_Invoice_Candidate_ID());
			if (counter % 10 == 0)
			{
				candidates.append("\n");
			}
		}
		return candidates;
	}

	/**
	 * Set the invoice generator to use in invoicing.
	 */
	public void setInvoiceGeneratorClass(final Class<? extends IInvoiceGeneratorRunnable> invoiceGeneratorClass)
	{
		this.invoiceGeneratorClass = invoiceGeneratorClass;
	}

	@Override
	public InvoiceCandBLCreateInvoices setContext(final Properties ctx, final String trxName)
	{
		this._ctx = ctx;
		this._trxName = trxName;
		return this;
	}

	private Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	private String getTrxName()
	{
		return _trxName;
	}

	@Override
	public InvoiceCandBLCreateInvoices setIgnoreInvoiceSchedule(final boolean ignoreInvoiceSchedule)
	{
		this._ignoreInvoiceSchedule = ignoreInvoiceSchedule;
		return this;
	}

	private boolean isIgnoreInvoiceSchedule()
	{
		if (_ignoreInvoiceSchedule != null)
		{
			return _ignoreInvoiceSchedule;
		}

		final InvoicingParams invoicingParams = getInvoicingParams();
		if (invoicingParams != null)
		{
			return invoicingParams.isIgnoreInvoiceSchedule();
		}

		return false;
	}

	@Override
	public InvoiceCandBLCreateInvoices setCollector(final IInvoiceGenerateResult collector)
	{
		this._collector = collector;
		return this;
	}

	private IInvoiceGenerateResult getCollector()
	{
		if (_collector == null)
		{
			// note that we don't want to store the actual invoices in the result if there is a change to encounter memory problems
			_collector = invoiceCandBL.createInvoiceGenerateResult(_invoicingParams != null && _invoicingParams.isStoreInvoicesInResult());
		}
		return _collector;
	}

	@Override
	public IInvoiceGenerator setInvoicingParams(final @NonNull InvoicingParams invoicingParams)
	{
		this._invoicingParams = invoicingParams;
		return this;
	}

	private InvoicingParams getInvoicingParams()
	{
		return _invoicingParams;
	}
}
