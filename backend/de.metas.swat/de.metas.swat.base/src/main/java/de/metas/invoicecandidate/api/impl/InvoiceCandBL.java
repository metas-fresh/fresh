/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.invoicecandidate.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IQueueProcessor;
import de.metas.async.processor.IQueueProcessorFactory;
import de.metas.async.processor.IStatefulWorkpackageProcessorFactory;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.impl.QueueProcessorService;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.IDocTypeDAO;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.spi.ModelWithoutInvoiceCandidateVetoer;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.InvoiceCandidateIds;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO.InvoiceableInvoiceCandIdResult;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateListeners;
import de.metas.invoicecandidate.api.IInvoiceGenerator;
import de.metas.invoicecandidate.api.InvoiceCandidateIdsSelection;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery.InvoiceCandidateMultiQueryBuilder;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.async.spi.impl.InvoiceCandWorkpackageProcessor;
import de.metas.invoicecandidate.exceptions.InconsistentUpdateException;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.TableRecordMDC;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.PInstanceId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.concurrent.AutoClosableThreadLocalBoolean;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MNote;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static de.metas.inoutcandidate.spi.ModelWithoutInvoiceCandidateVetoer.OnMissingCandidate.I_VETO;
import static de.metas.util.Check.assume;
import static de.metas.util.Check.assumeGreaterThanZero;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOrNull;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class InvoiceCandBL implements IInvoiceCandBL
{
	private static final AdMessageKey MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_TO_CLEAR = AdMessageKey.of("InvoiceCandBL_Invoicing_Skipped_IsToClear");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_IN_DISPUTE = AdMessageKey.of("InvoiceCandBL_Invoicing_Skipped_IsInDispute");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_DATE_TO_INVOICE = AdMessageKey.of("InvoiceCandBL_Invoicing_Skipped_DateToInvoice");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_ERROR = AdMessageKey.of("InvoiceCandBL_Invoicing_Skipped_Error");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_PROCESSED = AdMessageKey.of("InvoiceCandBL_Invoicing_Skipped_Processed");
	private static final AdMessageKey MSG_FixProblemDeleteWaitForRegeneration = AdMessageKey.of("FixProblemDeleteWaitForRegeneration");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P = AdMessageKey.of("InvoiceCandBL_Status_InvoiceSchedule_Missing");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_BELOW_INVOICE_MIN_AMT_5P = AdMessageKey.of("InvoiceCandBL_Below_Invoice_Min_Amt");
	private static final AdMessageKey MSG_INVOICE_CAND_BL_STATUS_ORDER_NOT_CO_1P = AdMessageKey.of("InvoiceCandBL_Status_Order_Not_CO");

	private static final String SYS_Config_C_Invoice_Candidate_Close_IsToClear = "C_Invoice_Candidate_Close_IsToClear";
	private static final String SYS_Config_C_Invoice_Candidate_Close_PartiallyInvoiced = "C_Invoice_Candidate_Close_PartiallyInvoiced";

	/**
	 * Blueprint for a cache that is used by {@link #isAllOtherICsInHeaderAggregationGroupDelivered(I_C_Invoice_Candidate)}.
	 * It will be created and used per-transaction.
	 */
	private final static CCache.CCacheBuilder<String, Boolean> CACHE_BUILDER_IS_ALL_CANDIDATES_IN_GROUP_DELIVERED = CCache.<String, Boolean>builder().cacheName("isAllCandidatesInGroupDelivered")
			.tableName(I_C_Invoice_Candidate.Table_Name)
			.invalidationKeysMapper(recordRef -> {

				// figure out the header aggregations keys (=> cache keys) of the cache records that need to be invalidated
				final I_C_Invoice_Candidate icRecord = recordRef.getModel(I_C_Invoice_Candidate.class);

				final boolean headerAggregationKeyWasChanged = InterfaceWrapperHelper.isValueChanged(icRecord, I_C_Invoice_Candidate.COLUMNNAME_HeaderAggregationKey);
				final boolean needToInvalidateAnything =
						headerAggregationKeyWasChanged
								|| InterfaceWrapperHelper.isValueChanged(icRecord, I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered)
								|| InterfaceWrapperHelper.isValueChanged(icRecord, I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered);
				if (!needToInvalidateAnything)
				{
					return ImmutableList.of();
				}
				if (!headerAggregationKeyWasChanged)
				{
					return ImmutableList.of(icRecord.getHeaderAggregationKey());
				}

				final String oldHeaderAggregationKey = InterfaceWrapperHelper.createOld(icRecord, I_C_Invoice_Candidate.class).getHeaderAggregationKey();
				if (Check.isBlank(oldHeaderAggregationKey))
				{
					return ImmutableList.of(icRecord.getHeaderAggregationKey());
				}

				return ImmutableList.of(
						icRecord.getHeaderAggregationKey(),
						oldHeaderAggregationKey);
			});

	// task 08927
	/* package */static final ModelDynAttributeAccessor<org.compiere.model.I_C_Invoice, Boolean> DYNATTR_INVOICING_FROM_INVOICE_CANDIDATES_IS_IN_PROGRESS = new ModelDynAttributeAccessor<>(Boolean.class);

	private final Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IQueueProcessorFactory queueProcessorFactory = Services.get(IQueueProcessorFactory.class);
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);

	private final Map<String, Collection<ModelWithoutInvoiceCandidateVetoer>> tableName2Listeners = new HashMap<>();

	@Override
	public IInvoiceCandInvalidUpdater updateInvalid()
	{
		return new InvoiceCandInvalidUpdater(this);
	}

	/**
	 * Sets the given IC's <code>DateToInvoice</code> value:
	 * <ul>
	 * <li>{@link X_C_Invoice_Candidate#INVOICERULE_AfterDelivery} or {@link X_C_Invoice_Candidate#INVOICERULE_AfterOrderDelivered}: <code>DeliveryDate</code> or {@link Env#MAX_DATE} if
	 * there was no delivery yet
	 * <li>{@link X_C_Invoice_Candidate#INVOICERULE_CustomerScheduleAfterDelivery}: basically the result of {@link InvoiceSchedule#calculateNextDateToInvoice(LocalDate)} or
	 * {@link Env#MAX_DATE} if there was no delivery yet
	 * <li>{@link X_C_Invoice_Candidate#INVOICERULE_OrderCompletelyDelivered} : <code>DateOrdered</code> if all other candidates with the same headerAggregation have been delivered, {@link Env#MAX_DATE} otherwise
	 * <li>{@link X_C_Invoice_Candidate#INVOICERULE_Immediate} : <code>DateOrdered</code>
	 * <li>else (which should not happen, unless a new invoice rule is introduced): <code>Created</code>
	 * </ul>
	 * <p>
	 * task 08542
	 */
	@Override
	public void set_DateToInvoice_DefaultImpl(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final Timestamp dateToInvoice = computeDateToInvoice(icRecord);
		icRecord.setDateToInvoice(dateToInvoice);
	}

	@VisibleForTesting
	Timestamp computeDateToInvoice(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final InvoiceRule invoiceRule = getInvoiceRule(icRecord);
		switch (invoiceRule)
		{
			case Immediate:
			case AfterPick:
				return icRecord.getDateOrdered();

			case AfterDelivery:
			case AfterOrderDelivered:
				return computeDateToInvoiceBasedOnDeliveryDate(icRecord);

			case OrderCompletelyDelivered:
				final boolean currentICDelivered = isCandidateDelivered(icRecord) || !isCandidateProductTypeItem(icRecord);
				if (currentICDelivered && isAllOtherICsInHeaderAggregationGroupDelivered(icRecord))
				{
					return computeDateToInvoiceBasedOnDeliveryDate(icRecord);
				}
				else
				{
					return Env.MAX_DATE;
				}

			case CustomerScheduleAfterDelivery:
				if (icRecord.getC_InvoiceSchedule_ID() <= 0) // that's a paddlin'
				{
					return Env.MAX_DATE;
				}
				else
				{
					final LocalDate deliveryDate = TimeUtil.asLocalDate(icRecord.getDeliveryDate()); // task 08451: when it comes to invoicing, the important date is not when it was ordered but when the delivery was made
					if (deliveryDate == null)
					{
						// task 08451: we have an invoice schedule, but no delivery yet. Set the date to the far future
						return Env.MAX_DATE;
					}
					else
					{
						final InvoiceScheduleRepository invoiceScheduleRepository = SpringContextHolder.instance.getBean(InvoiceScheduleRepository.class);
						final InvoiceSchedule invoiceSchedule = invoiceScheduleRepository.ofRecord(icRecord.getC_InvoiceSchedule());
						final LocalDate nextDateToInvoice = invoiceSchedule.calculateNextDateToInvoice(deliveryDate);
						return TimeUtil.asTimestamp(nextDateToInvoice, orgDAO.getTimeZone(OrgId.ofRepoId(icRecord.getAD_Org_ID())));
					}
				}
			default:
				throw new AdempiereException("Unexpected invoicerule=" + invoiceRule);
		}
	}

	private boolean isAllOtherICsInHeaderAggregationGroupDelivered(@NonNull final I_C_Invoice_Candidate ic)
	{
		final ITrx threadInheritedTrx = Services.get(ITrxManager.class).getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		final CCache<String, Boolean> isAllCandidatesInGroupDeliveredCache;
		if (threadInheritedTrx == null) // can happen in unit tests
		{
			isAllCandidatesInGroupDeliveredCache = CACHE_BUILDER_IS_ALL_CANDIDATES_IN_GROUP_DELIVERED.build();
		}
		else
		{
			isAllCandidatesInGroupDeliveredCache = threadInheritedTrx
					.getProperty("InvoicecandBL#isAllCandidatesInGroupDeliveredCache",
								 () -> CACHE_BUILDER_IS_ALL_CANDIDATES_IN_GROUP_DELIVERED.build());
		}
		return isAllCandidatesInGroupDeliveredCache.getOrLoad(ic.getHeaderAggregationKey(), () -> isAllCandidatesInGroupDelivered0(ic));
	}

	private boolean isAllCandidatesInGroupDelivered0(@NonNull final I_C_Invoice_Candidate ic)
	{
		final Iterator<I_C_Invoice_Candidate> candidates = invoiceCandDAO.retrieveForHeaderAggregationKey(getCtx(ic), ic.getHeaderAggregationKey(), getTrxName(ic));
		return IteratorUtils.stream(candidates)
				.filter(this::isCandidateProductTypeItem)
				.filter(currentStreamIc -> currentStreamIc.getC_Invoice_Candidate_ID() != ic.getC_Invoice_Candidate_ID())
				.allMatch(this::isCandidateDelivered);
	}

	private boolean isCandidateProductTypeItem(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		return productBL.getProductType(ProductId.ofRepoId(icRecord.getM_Product_ID())).isItem();
	}

	private boolean isCandidateDelivered(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		return icRecord
				.getQtyOrdered()
				.subtract(icRecord.getQtyDelivered()).compareTo(ZERO) <= 0;
	}

	private Timestamp computeDateToInvoiceBasedOnDeliveryDate(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final Timestamp deliveryDate = icRecord.getDeliveryDate();
		if (deliveryDate != null)
		{
			logger.debug("computedateToInvoiceBasedOnDeliveryDate -> return deliveryDate={} as dateToInvoice", deliveryDate);
			return deliveryDate;
		}

		final ProductId productId = ProductId.ofRepoId(icRecord.getM_Product_ID());
		final IProductBL productBL = Services.get(IProductBL.class);
		if (!productBL.isStocked(productId))
		{
			final Timestamp dateOrdered = icRecord.getDateOrdered();
			logger.debug("computedateToInvoiceBasedOnDeliveryDate - deliveryDate is null and M_Product_ID={} is not stocked; -> return dateOrdered= {} as dateToInvoice", productId.getRepoId(), dateOrdered);
			return dateOrdered;
		}

		// if there is no delivery yet, then we set the date to the far future
		logger.debug("computedateToInvoiceBasedOnDeliveryDate - deliveryDate is null and M_Product_ID={} is stocked; -> return {} as dateToInvoice", productId.getRepoId(), Env.MAX_DATE);
		return deliveryDate != null ? deliveryDate : Env.MAX_DATE;
	}

	void setInvoiceScheduleAmtStatus(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		//
		// services
		final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final InvoiceRule invoiceRule = getInvoiceRule(ic);
		if (!InvoiceRule.CustomerScheduleAfterDelivery.equals(invoiceRule))
		{
			// Note: the field is supposed not to be displayed on status=OK, so we don't need to localize this
			ic.setInvoiceScheduleAmtStatus("OK");
			return;
		}

		final I_C_BPartner partner = bpartnerDAO.getById(ic.getBill_BPartner_ID());

		final int invoiceSchedId = retrieveInvoiceScheduleId(ic);
		if (invoiceSchedId <= 0)
		{

			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P, new Object[] { partner.getName() });
			ic.setInvoiceScheduleAmtStatus(msg);
			return;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(ic);
		final I_C_InvoiceSchedule invoiceSched = InterfaceWrapperHelper.create(ctx, invoiceSchedId, I_C_InvoiceSchedule.class, trxName);

		if (invoiceSched.isAmount())
		{
			final LocalDate dateToday = getToday();
			final BigDecimal actualAmt = invoiceCandDAO.retrieveInvoicableAmount(partner, dateToday);

			if (actualAmt.compareTo(invoiceSched.getAmt()) < 0)
			{
				final Currency targetCurrency = currencyConversionBL.getBaseCurrency(
						ClientId.ofRepoId(ic.getAD_Client_ID()),
						OrgId.ofRepoId(ic.getAD_Org_ID()));
				final ITranslatableString currSymbol = targetCurrency.getSymbol();

				final String msg = msgBL.getMsg(ctx,
												MSG_INVOICE_CAND_BL_BELOW_INVOICE_MIN_AMT_5P,
												new Object[] { dateToday, actualAmt, currSymbol, invoiceSched.getAmt(), currSymbol });
				ic.setInvoiceScheduleAmtStatus(msg);
				return;
			}
		}

		// Note: the field is supposed not to be displayed on status=OK, so we don't need to localize this
		ic.setInvoiceScheduleAmtStatus("OK");
	}

	/**
	 * Updates 'QtyToInvoice_OverrideFulfilled'. If <code>QtyToInvoice_Override</code> is empty, it does nothing besides also resetting QtyToInvoice_OverrideFulfilled.
	 * <p>
	 * If is turns out that the fulfillment (i.e. QtyInvoiced) is now sufficient, it resets both 'QtyToInvoice_Override' and 'QtyToInvoice_OverrideFulfilled'.
	 */
	void set_QtyToInvoiceOverrideFulfilled(
			final I_C_Invoice_Candidate ic,
			final BigDecimal oldQtyInvoiced,
			final BigDecimal factor)
	{
		final BigDecimal qtyInvoicedDiff = ic.getQtyInvoiced().multiply(factor).subtract(oldQtyInvoiced);

		final BigDecimal newQtyToInvoiceOverrideFulfilled = ic.getQtyToInvoice_OverrideFulfilled().multiply(factor).add(qtyInvoicedDiff);

		final BigDecimal qtyToInvoiceOverride = getQtyToInvoice_OverrideOrNull(ic);
		if (qtyToInvoiceOverride == null || qtyToInvoiceOverride.signum() == 0)
		{
			// basically this is just for good measure. The model interceptor C_Invoice_Candidate.resetQtyToInvoiceFulFilled() actually does the job.
			ic.setQtyToInvoice_OverrideFulfilled(null);
		}
		else
		{
			if (qtyToInvoiceOverride.multiply(factor).compareTo(newQtyToInvoiceOverrideFulfilled) <= 0)
			{

				ic.setQtyToInvoice_Override(null);
				ic.setQtyToInvoice_OverrideFulfilled(null);
			}
			else
			{
				ic.setQtyToInvoice_OverrideFulfilled(newQtyToInvoiceOverrideFulfilled.multiply(factor));
			}
		}
	}

	@Override
	public void set_QtyInvoiced_NetAmtInvoiced_Aggregation(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		Check.assume(ic.isManual(), ic + " has IsManual='Y'");
		set_QtyInvoiced_NetAmtInvoiced_Aggregation0(ctx, ic);
	}

	/**
	 * Updates the 'QtyInvoiced', 'NetAmtInvoiced', 'C_Invoice_Candidate_Agg_ID', 'LineAggregationKey' values of the given 'ic'.
	 * <p>
	 * <b>Also invokes {@link #updateProcessedFlag(I_C_Invoice_Candidate)}</b>
	 */
	void set_QtyInvoiced_NetAmtInvoiced_Aggregation0(final Properties ctx, @NonNull final I_C_Invoice_Candidate ic)
	{
		if (ic.isToClear())
		{
			logger.debug(ic + "is not directly invoiced. QtyInvoiced is handled by a specific module");
		}
		else
		{
			final Optional<IPair<StockQtyAndUOMQty, Money>> qtyAndNetAmtInvoiced = sumupQtyInvoicedAndNetAmtInvoiced(ic);
			// Might be not presend, if the IC's price could not be computed. In that case there is not invoiced data to be set.
			if (qtyAndNetAmtInvoiced.isPresent())
			{
				final StockQtyAndUOMQty qtysInvoiced = qtyAndNetAmtInvoiced.get().getLeft();
				final Quantity stockQty = qtysInvoiced.getStockQty();

				ic.setQtyInvoiced(stockQty.toBigDecimal());
				ic.setQtyInvoicedInUOM(qtysInvoiced.getUOMQtyOpt().orElse(stockQty).toBigDecimal());

				final Money netAmtInvoiced = qtyAndNetAmtInvoiced.get().getRight();
				ic.setNetAmtInvoiced(netAmtInvoiced.toBigDecimal());
			}
		}

		updateProcessedFlag(ic); // #243: also update the processed flag if isToClear=Y. It might be the case that Processed_Override was set

		//
		// Update aggregation info
		Services.get(IAggregationBL.class)
				.getUpdateProcessor()
				.process(ic);
	}

	/**
	 * Sum up 'QtyInvoiced' and 'NetAmtInvoiced'.
	 * Note that <code>NetAmtInvoiced</code> is in <code>C_Invoice_Candidate.Price_UOM</code>.
	 */
	/* package */Optional<IPair<StockQtyAndUOMQty, Money>> sumupQtyInvoicedAndNetAmtInvoiced(final I_C_Invoice_Candidate ic)
	{
		if (ic.getC_Currency_ID() <= 0 || ic.getC_UOM_ID() <= 0)
		{
			// if those two columns are not yet set, then for sure nothing is invoiced yet either
			return Optional.empty();
		}

		final List<I_C_Invoice_Line_Alloc> ilas = invoiceCandDAO.retrieveIlaForIc(InvoiceCandidateIds.ofRecord(ic));

		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());

		final UomId icUomId = UomId.ofRepoId(ic.getC_UOM_ID());
		StockQtyAndUOMQty qtyInvoicedSum = StockQtyAndUOMQtys.createZero(productId, icUomId);
		final CurrencyId icCurrencyId = CurrencyId.ofRepoId(ic.getC_Currency_ID());

		Money netAmtInvoiced = Money.zero(icCurrencyId);

		for (final I_C_Invoice_Line_Alloc ila : ilas)
		{
			// we don't need to check the invoice's DocStatus. If the ila is there, we count it.
			// @formatter:off
//			final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(ila.getC_InvoiceLine(), I_C_InvoiceLine.class);
//			final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
//
//			if (docActionBL.isStatusOneOf(invoiceLine.getC_Invoice(),
//					DocAction.STATUS_Completed,
//					DocAction.STATUS_Closed,
//					DocAction.STATUS_Reversed,
//					DocAction.STATUS_InProgress))                     // 06162 InProgress invoices shall also be processed
//			{
// @formatter:on

			final StockQtyAndUOMQty ilaQtysInvoiced = StockQtyAndUOMQtys.create(
					ila.getQtyInvoiced(),
					productId,
					ila.getQtyInvoicedInUOM(),
					UomId.ofRepoIdOrNull(ila.getC_UOM_ID()));
			qtyInvoicedSum = StockQtyAndUOMQtys.add(qtyInvoicedSum, ilaQtysInvoiced);


			//
			// 12904: in case of an Adjustment Invoice(price diff), get price from invoice line
			final boolean isIlaInvoiceAnAdjInvoice=Services.get(IInvoiceBL.class)
					         .isAdjustmentCharge(ila.getC_InvoiceLine().getC_Invoice());

			final BigDecimal usedPriceActual = isIlaInvoiceAnAdjInvoice ?
					         ila.getC_InvoiceLine().getPriceActual():
					         ic.getPriceActual();

			//
			// 07202: We update the net amount invoice according to price UOM.
			// final BigDecimal priceActual = ic.getPriceActual();
			final ProductPrice priceActual = ProductPrice.builder()
							.money(Money.of(usedPriceActual, icCurrencyId))
							.productId(productId)
							.uomId(icUomId)
							.build();


			final Quantity qtyInvoicedInUOM = extractQtyInvoiced(ila);

			final Money amountInvoiced = SpringContextHolder.instance.getBean(MoneyService.class)
					.multiply(qtyInvoicedInUOM, priceActual);

			if (amountInvoiced == null)
			{
				netAmtInvoiced = null;
			}

			if (netAmtInvoiced != null)
			{
				netAmtInvoiced = netAmtInvoiced.add(amountInvoiced);
			}
			// @formatter:off
//			}
// @formatter:on
		}
		final IPair<StockQtyAndUOMQty, Money> qtyAndNetAmtInvoiced = ImmutablePair.of(qtyInvoicedSum, netAmtInvoiced);
		return Optional.of(qtyAndNetAmtInvoiced);
	}

	private static void setQtyInvoiced(
			@NonNull final I_C_Invoice_Line_Alloc ila,
			@NonNull final StockQtyAndUOMQty qtysInvoiced)
	{
		ila.setQtyInvoiced(qtysInvoiced.getStockQty().toBigDecimal());
		if (qtysInvoiced.isUOMQtySet())
		{
			final Quantity qty = qtysInvoiced.getUOMQtyNotNull();
			ila.setQtyInvoicedInUOM(qty.toBigDecimal());
			ila.setC_UOM_ID(qty.getUomId().getRepoId());
		}
	}

	private Quantity extractQtyInvoiced(@NonNull final I_C_Invoice_Line_Alloc ila)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(ila.getC_UOM_ID());
		if (uomId != null)
		{
			final I_C_UOM uom = uomsRepo.getById(uomId);
			return Quantity.of(ila.getQtyInvoicedInUOM(), uom);
		}
		else
		{
			return null;
		}
	}

	/* package */void set_Discount(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		if (ic.getC_OrderLine_ID() > 0)
		{
			final org.compiere.model.I_C_OrderLine ol = ic.getC_OrderLine();

			if (ol.isProcessed())
			{
				ic.setDiscount(ol.getDiscount());
			}
			else
			{
				final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
				final IMsgBL msgBL = Services.get(IMsgBL.class);

				amendSchedulerResult(ic,
									 msgBL.getMsg(ctx,
												  MSG_INVOICE_CAND_BL_STATUS_ORDER_NOT_CO_1P,
												  new Object[] {
														  adReferenceDAO.retrieveListNameTrl(
																  DocStatus.AD_REFERENCE_ID,
																  ol.getC_Order_ID() > 0 ? ol.getC_Order().getDocStatus() : "<null>") // "<null>" shouldn't happen
												  }));

				ic.setDiscount(ZERO);
			}
		}
	}

	/**
	 * If the ic's invoice rule is "CustomerScheduleAfterDelivery", this method tries to get the BPartner's invoice schedule id.
	 */
	private int retrieveInvoiceScheduleId(final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final InvoiceRule invoiceRule = getInvoiceRule(ic);
		Check.assume(InvoiceRule.CustomerScheduleAfterDelivery.equals(invoiceRule),
					 "Method is only called if invoice rule is " + InvoiceRule.CustomerScheduleAfterDelivery);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);

		final int C_InvoiceSchedule_ID = ic.getC_InvoiceSchedule_ID();
		if (C_InvoiceSchedule_ID <= 0)
		{
			logger.info("BPartner has no Schedule");

			final I_C_BPartner billBPartner = bpartnerDAO.getById(ic.getBill_BPartner_ID());
			final String msg = Services.get(IMsgBL.class).getMsg(ctx, MSG_INVOICE_CAND_BL_STATUS_INVOICE_SCHEDULE_MISSING_1P, new Object[] { billBPartner.getName() });
			amendSchedulerResult(ic, msg);
			ic.setInvoiceScheduleAmtStatus(msg);
			return -1;
		}

		return C_InvoiceSchedule_ID;
	}

	@Override
	public IInvoiceGenerator generateInvoices()
	{
		return new InvoiceCandBLCreateInvoices();
	}

	@Override
	public IInvoiceGenerateResult generateInvoicesFromSelection(
			final Properties ctx,
			final PInstanceId AD_PInstance_ID,
			final boolean ignoreInvoiceSchedule,
			final String trxName)
	{
		final Iterator<I_C_Invoice_Candidate> candidates =
				invoiceCandDAO.retrieveIcForSelectionStableOrdering(AD_PInstance_ID);

		return generateInvoices()
				.setContext(ctx, trxName)
				.setIgnoreInvoiceSchedule(ignoreInvoiceSchedule)
				.generateInvoices(candidates);
	}

	@Override
	public void setPaymentTermIfMissing(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		if (icRecord.getC_PaymentTerm_ID() > 0)
		{
			return;
		}

		final PaymentTermQuery paymentTermQuery = PaymentTermQuery.forPartner(BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID()), SOTrx.ofBoolean(icRecord.isSOTrx()));

		final PaymentTermId paymentTermIdToUse = paymentTermRepository
				.retrievePaymentTermId(paymentTermQuery)
				.orElseThrow(() -> new AdempiereException("Found neither a payment-term for bpartner nor a default payment term.")
						.appendParametersToMessage()
						.setParameter("C_BPartner_ID", paymentTermQuery.getBPartnerId().getRepoId())
						.setParameter("SOTrx", paymentTermQuery.getSoTrx()));

		icRecord.setC_PaymentTerm_ID(PaymentTermId.toRepoId(paymentTermIdToUse));
	}

	@Override
	public void setNetAmtToInvoice(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		// If invoice candidate has IsToClear=Y then we shall not invoice it.
		// Also we shall set the NetAmtToInvoice to ZERO, to not affect the checksum (when invoicing).
		if (icRecord.isToClear())
		{
			icRecord.setNetAmtToInvoice(ZERO);
			icRecord.setSplitAmt(ZERO);
			return;
		}

		// If invoice candidate would be skipped when enqueueing to be invoiced then set the NetAmtToInvoice=0 (Mark request)
		// Reason: if the IC would be skipped we want to have the NetAmtToInvoice=0 because we don't want to affect the overall total that is displayed on window bottom.
		final boolean ignoreInvoiceSchedule = true; // yes, we ignore the DateToInvoice when checking because that's relative to Today
		if (isSkipCandidateFromInvoicing(icRecord, ignoreInvoiceSchedule))
		{
			icRecord.setNetAmtToInvoice(ZERO);
			icRecord.setSplitAmt(ZERO);
			return;
		}

		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

		invoiceCandidateHandlerBL.setNetAmtToInvoice(icRecord);
		invoiceCandidateHandlerBL.setLineNetAmt(icRecord);
	}

	@Override
	public void setPriceActualNet(final I_C_Invoice_Candidate ic)
	{
		final Tax tax = getTaxEffective(ic);
		final ProductPrice priceActual = getPriceActual(ic);
		final boolean taxIncluded = isTaxIncluded(ic);
		final CurrencyPrecision precision = getPrecisionFromCurrency(ic);

		final BigDecimal priceActualNet = tax.calculateBaseAmt(
				priceActual.toMoney().toBigDecimal(),
				taxIncluded,
				precision.toInt());
		ic.setPriceActual_Net_Effective(priceActualNet);
	}

	@Override
	public ProductPrice getPriceActual(@NonNull final I_C_Invoice_Candidate ic)
	{
		final BigDecimal priceActual;
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceActual_Override))
		{
			priceActual = ic.getPriceActual();
		}
		else
		{
			priceActual = ic.getPriceActual_Override();
		}

		if (ic.getC_Currency_ID() <= 0 || ic.getC_UOM_ID() <= 0 || ic.getM_Product_ID() <= 0)
		{
			throw new AdempiereException("The current C_Invoice_Candidate doesn't have a valid PriceActual")
					.appendParametersToMessage()
					.setParameter("C_Currency_ID", ic.getC_Currency_ID())
					.setParameter("C_UOM_ID", ic.getC_UOM_ID())
					.setParameter("M_Product_ID", ic.getM_Product_ID())
					.setParameter("C_Invoice_Candidate", ic);
		}
		return ProductPrice.builder()
				.money(Money.of(priceActual, CurrencyId.ofRepoId(ic.getC_Currency_ID())))
				.uomId(UomId.ofRepoId(ic.getC_UOM_ID()))
				.productId(ProductId.ofRepoId(ic.getM_Product_ID()))
				.build();
	}

	private BigDecimal getQtyToInvoice_OverrideOrNull(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override))
		{
			return null;
		}
		else
		{
			return ic.getQtyToInvoice_Override();
		}
	}

	@Override
	public Percent getDiscount(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal discountOverride;
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_Discount_Override))
		{
			discountOverride = null;
		}
		else
		{
			discountOverride = ic.getDiscount_Override();
		}

		if (discountOverride == null)
		{
			return Percent.of(ic.getDiscount());
		}

		return Percent.of(discountOverride);
	}

	@Override
	public Quantity getQtyToInvoiceStockUOM(@NonNull final I_C_Invoice_Candidate ic)
	{
		final UomId stockUomId = Services.get(IProductBL.class).getStockUOMId(ProductId.ofRepoId(ic.getM_Product_ID()));

		final BigDecimal qtyToInvoiceOverride = getQtyToInvoice_OverrideOrNull(ic);
		if (qtyToInvoiceOverride != null)
		{
			return Quantitys.create(qtyToInvoiceOverride, stockUomId);
		}

		return Quantitys.create(ic.getQtyToInvoice(), stockUomId);
	}

	/**
	 * Gets Qty Ordered (target), considering the QtyDelivered too.
	 */
	private BigDecimal getQtyOrderedInStockUOM(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_UOM uomRecord = Services.get(IProductBL.class).getStockUOM(ic.getM_Product_ID());

		final Quantity qtyOrdered = Quantity.of(ic.getQtyOrdered(), uomRecord);
		final Quantity qtyDelivered = Quantity.of(getQtyDelivered_Effective(ic), uomRecord);

		final Quantity qtyOrderedActual = calculateMaxInvoicableQtyFromEnteredAndDelivered(qtyOrdered, qtyDelivered);

		return qtyOrderedActual.toBigDecimal();
	}

	/**
	 * Adds the given <code>amendment</code> to the given <code>ic</code>'s <code>SchedulerResult</code> value, <b>unless</b> the given string is already part of the <code>SchedulerResult</code>.
	 * <p>
	 * Note: the given <code>ic</code> might already contain the string because one method might be called multiple times and always try to add the same error-message.
	 */
	private void amendSchedulerResult(final I_C_Invoice_Candidate ic, final String amendment)
	{
		if (Check.isEmpty(amendment))
		{
			logger.debug("there is nothing to amend");
			return;
		}

		final String currentVal = ic.getSchedulerResult();
		if (Check.isEmpty(currentVal))
		{
			ic.setSchedulerResult(amendment);
		}
		else if (!currentVal.contains(amendment)) // this IC might already contain the given amendment
		{
			ic.setSchedulerResult(currentVal + "\n" + amendment);
		}
	}

	@Override
	public void invalidateForInvoiceSchedule(@NonNull final I_C_InvoiceSchedule invoiceSchedule)
	{
		final Iterator<I_C_Invoice_Candidate> candsForBPartner = invoiceCandDAO.retrieveForInvoiceSchedule(invoiceSchedule);

		while (candsForBPartner.hasNext())
		{
			final I_C_Invoice_Candidate ic = candsForBPartner.next();
			invoiceCandDAO.invalidateCand(ic);
		}
	}

	@Override
	public void invalidateForPartnerIfInvoiceRuleDemandsIt(final I_C_Invoice_Candidate ic)
	{

		final InvoiceRule invoiceRule = getInvoiceRule(ic);
		if (InvoiceRule.CustomerScheduleAfterDelivery.equals(invoiceRule)
				&& ic.getC_InvoiceSchedule_ID() > 0)
		{
			final I_C_InvoiceSchedule invoiceSchedule = ic.getC_InvoiceSchedule();
			if (invoiceSchedule.getAmt() != null && invoiceSchedule.getAmt().signum() != 0)
			{
				// we need to invalidate all the partner's ICs *if* there is an overall invoiceable sum-amount from which on the ICs shall be invoiced
				final BPartnerId bpartnerId = BPartnerId.ofRepoId(ic.getBill_BPartner_ID());
				invoiceCandDAO.invalidateCandsForBPartnerInvoiceRule(bpartnerId);
			}
		}
	}

	@Override
	public IInvoiceCandidateEnqueuer enqueueForInvoicing()
	{
		return new InvoiceCandidateEnqueuer();
	}

	@Override
	public boolean isSkipCandidateFromInvoicing(final I_C_Invoice_Candidate ic, final boolean ignoreInvoiceSchedule)
	{
		// 04533: ignore already processed candidates
		// task 08343: if the ic is processed (after the recent update), then skip it (this logic was in the where clause in C_Invoice_Candidate_EnqueueSelection)
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		if (ic.isProcessed())
		{
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_PROCESSED, new Object[] { ic.getC_Invoice_Candidate_ID() });
			Loggables.withLogger(logger, Level.INFO).addLog(msg);
			return true;
		}

		// ignore "error" candidates
		if (ic.isError())
		{
			final String msg = new StringBuilder()
					.append(msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_ERROR, new Object[] { ic.getC_Invoice_Candidate_ID() }))
					.append(": ")
					.append(ic.getErrorMsg())
					.toString();
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return true;
		}

		if (ic.isToClear())
		{
			// don't log (per Mark request) because those could be a lot and because user has no opportunity to react
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_TO_CLEAR,
											new Object[] { ic.getC_Invoice_Candidate_ID() });
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return true;
		}

		if (ic.isInDispute())
		{
			// don't log (per Mark request) because those could be a lot and because user has no opportunity to react
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_IS_IN_DISPUTE,
											new Object[] { ic.getC_Invoice_Candidate_ID() });
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return true;
		}

		if (ic.isSimulation())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(" #isSkipCandidateFromInvoicing: Skipping IC: {},"
																	 + " as it's a simulation and it shouldn't be invoiced!", ic.getC_Invoice_Candidate_ID());
			return true;
		}

		// flagged via field color
		// ignore candidates that can't be invoiced yet
		final LocalDate dateToInvoice = getDateToInvoice(ic);
		if (!ignoreInvoiceSchedule
				&& (dateToInvoice == null || dateToInvoice.isAfter(getToday())))
		{
			final String msg = msgBL.getMsg(ctx, MSG_INVOICE_CAND_BL_INVOICING_SKIPPED_DATE_TO_INVOICE,
											new Object[] { ic.getC_Invoice_Candidate_ID(), TimeUtil.asTimestamp(dateToInvoice), TimeUtil.asTimestamp(getToday()) });
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);
			return true;
		}

		return false; // Don't skip!
	}

	@Override
	public IInvoiceGenerateResult generateInvoicesFromQueue(final Properties ctx)
	{
		// note that we don't want to store the actual invoices in the result to omit memory-problems
		final InvoiceGenerateResult result = new InvoiceGenerateResult(false);
		final IWorkpackageProcessor packageProcessor = new InvoiceCandWorkpackageProcessor(result);

		// We use a stateful package processor factory because we don't want to create a new instance of InvoiceCandWorkpackageProcessor for each work package
		// This is because we want to preserve InvoiceGenerateResult state
		final IStatefulWorkpackageProcessorFactory packageProcessorFactory = Services.get(IStatefulWorkpackageProcessorFactory.class);
		packageProcessorFactory.registerWorkpackageProcessor(packageProcessor);

		final I_C_Queue_PackageProcessor packageProcessorConfig = queueDAO.retrievePackageProcessorDefByClass(ctx, InvoiceCandWorkpackageProcessor.class);
		final QueuePackageProcessorId packageProcessorId = QueuePackageProcessorId.ofRepoId(packageProcessorConfig.getC_Queue_PackageProcessor_ID());

		final IQueueProcessor queueProcessor = queueProcessorFactory.createAsynchronousQueueProcessor(packageProcessorId);
		queueProcessor.setWorkpackageProcessorFactory(packageProcessorFactory);

		final QueueProcessorService queueProcessorService = SpringContextHolder.instance.getBean(QueueProcessorService.class);

		queueProcessorService.runAndWait(queueProcessor);

		return result;
	}

	@Override
	public boolean isCreditMemo(final I_C_Invoice_Candidate cand)
	{
		return cand.isManual() && cand.getPriceActual_Override().signum() < 0;
	}

	@Override
	public Money calculateNetAmt(final I_C_Invoice_Candidate ic)
	{
		final ProductPrice candPriceActual = getPriceActual(ic);

		final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

		final Money candNetAmtToInvoiceCalc = moneyService.multiply(Quantitys.create(ic.getQtyToInvoiceInUOM(), UomId.ofRepoId(ic.getC_UOM_ID())), candPriceActual);

		return candNetAmtToInvoiceCalc;
	}

	@Override
	public CurrencyPrecision extractPricePrecision(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final I_M_PriceList pricelist = extractPriceListOrNull(icRecord);

		if (pricelist != null && !isNull(pricelist, I_M_PriceList.COLUMNNAME_PricePrecision) && pricelist.getPricePrecision() >= 0)
		{
			return CurrencyPrecision.ofInt(pricelist.getPricePrecision());
		}

		// fall back: get the precision from the currency
		final CurrencyPrecision result = getPrecisionFromCurrency(icRecord);
		logger.debug("C_Invoice_Candidate has no M_PriceList with a PricePrecision; -> return currency's precision={}", result);
		return result;
	}

	private I_M_PriceList extractPriceListOrNull(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		if (icRecord.getM_PriceList_Version_ID() > 0)
		{
			final I_M_PriceList result = priceListDAO.getPriceListByPriceListVersionId(PriceListVersionId.ofRepoId(icRecord.getM_PriceList_Version_ID()));
			logger.debug("C_Invoice_Candidate has M_PriceList_Version_ID={}; -> return M_PriceList={}", icRecord.getM_PriceList_Version_ID(), result);
			return result;
		}
		else if (icRecord.getM_PricingSystem_ID() > 0)
		{
			// take the precision from the bpartner price list
			final I_C_BPartner_Location partnerLocation = bpartnerDAO.getBPartnerLocationByIdEvenInactive(
					BPartnerLocationId.ofRepoIdOrNull(
							icRecord.getBill_BPartner_ID(),
							firstGreaterThanZero(icRecord.getBill_Location_Override_ID(), icRecord.getBill_Location_ID())));

			if (partnerLocation == null)
			{
				logger.debug("C_Invoice_Candidate has M_PricingSystem_ID={}, but no partnerLocation; -> return M_PriceList=null", icRecord.getM_PricingSystem_ID());
				return null;
			}

			final ZonedDateTime date = TimeUtil.asZonedDateTime(icRecord.getDateOrdered());
			final SOTrx soTrx = SOTrx.ofBoolean(icRecord.isSOTrx());

			final I_M_PriceList result = priceListBL
					.getCurrentPricelistOrNull(
							PricingSystemId.ofRepoIdOrNull(icRecord.getM_PricingSystem_ID()),
							CountryId.ofRepoId(partnerLocation.getC_Location().getC_Country_ID()),
							date,
							soTrx);
			logger.debug("C_Invoice_Candidate has M_PricingSystem_ID={}, effective C_BPartner_Location_ID={}, DateOrdered={} and SOTrx={}; -> return M_PriceList={}",
						 icRecord.getM_PricingSystem_ID(), partnerLocation.getC_BPartner_Location_ID(), date, icRecord.isSOTrx(), result);
			return result;
		}

		logger.debug("C_Invoice_Candidate has neither M_PriceList_Version_ID nor M_PricingSystem_ID; -> return M_PriceList=null");
		return null;
	}

	@Override
	public CurrencyPrecision getPrecisionFromCurrency(@NonNull final I_C_Invoice_Candidate ic)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(ic.getC_Currency_ID());
		return currencyId != null
				? Services.get(ICurrencyDAO.class).getStdPrecision(currencyId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public I_C_Invoice_Candidate splitCandidate(@NonNull final I_C_Invoice_Candidate ic)
	{
		// services
		final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);

		final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);

		final BigDecimal splitAmt = ic.getSplitAmt();
		// splitAmt may be zero, if we are going to compute&set priceactual etc later.
		// Check.assume(splitAmt.signum() != 0, "Split amount shall not be zero: {}", ic);

		final I_C_Invoice_Candidate splitCand = newInstance(I_C_Invoice_Candidate.class, ic);

		Check.assume(splitCand.getAD_Client_ID() == ic.getAD_Client_ID(), "Same AD_Client_ID (split's AD_Client_ID={}, IC's AD_Client_ID={}", splitCand.getAD_Client_ID(), ic.getAD_Client_ID());
		splitCand.setAD_Org_ID(ic.getAD_Org_ID());

		splitCand.setAD_Table_ID(ic.getAD_Table_ID());
		splitCand.setRecord_ID(ic.getRecord_ID()); // even if 0, we can't leave it empty, as the column is mandatory

		splitCand.setIsActive(true);
		splitCand.setProcessed(false);
		resetError(ic); // make sure there is no error set

		splitCand.setIsManual(ic.isManual());
		splitCand.setIsSOTrx(ic.isSOTrx());
		splitCand.setC_ILCandHandler(ic.getC_ILCandHandler());

		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(splitCand)
				.setFrom(ic);

		splitCand.setC_Invoice_Candidate_Agg_ID(ic.getC_Invoice_Candidate_Agg_ID());
		aggregationBL.setHeaderAggregationKey(splitCand);
		splitCand.setLineAggregationKey(null);
		splitCand.setLineAggregationKey_Suffix(ic.getLineAggregationKey_Suffix());
		splitCand.setDescription(ic.getDescription());

		splitCand.setInvoiceRule(ic.getInvoiceRule());
		splitCand.setInvoiceRule_Override(ic.getInvoiceRule_Override());

		splitCand.setM_PricingSystem_ID(ic.getM_PricingSystem_ID());
		splitCand.setM_Product_ID(ic.getM_Product_ID());
		splitCand.setIsPackagingMaterial(ic.isPackagingMaterial());
		splitCand.setC_Charge_ID(ic.getC_Charge_ID());

		splitCand.setInvoicableQtyBasedOn(ic.getInvoicableQtyBasedOn());

		splitCand.setQtyOrdered(ONE);
		splitCand.setQtyEntered(ONE); // "QtyOrderedInUOM"
		splitCand.setQtyDelivered(ZERO);
		splitCand.setQtyDeliveredInUOM(ZERO);
		splitCand.setQtyToInvoice(ONE);
		splitCand.setQtyToInvoiceInUOM_Calc(ONE);
		splitCand.setQtyToInvoice_Override(ONE);

		splitCand.setQtyInvoiced(ZERO);
		splitCand.setQtyInvoicedInUOM(ZERO);

		splitCand.setC_Currency_ID(ic.getC_Currency_ID());
		splitCand.setC_ConversionType_ID(ic.getC_ConversionType_ID());
		splitCand.setPriceActual(splitAmt);
		splitCand.setIsTaxIncluded(false);
		splitCand.setPrice_UOM_ID(ic.getPrice_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		splitCand.setPriceEntered(splitAmt); // cg : task 04917 -- same as price actual
		splitCand.setDiscount(ZERO);
		splitCand.setSplitAmt(ZERO);

		splitCand.setPresetDateInvoiced(ic.getPresetDateInvoiced());
		splitCand.setDateInvoiced(ic.getDateInvoiced());
		splitCand.setDateOrdered(ic.getDateOrdered());
		splitCand.setDateToInvoice(ic.getDateToInvoice());
		splitCand.setDateToInvoice_Override(ic.getDateToInvoice_Override());

		splitCand.setC_Incoterms_ID(ic.getC_Incoterms_ID());
		splitCand.setIncotermLocation(ic.getIncotermLocation());

		// 07442
		// also set  tax
		// 07814: setting both tax and tax-override to get an exact copy
		splitCand.setC_Tax_ID(ic.getC_Tax_ID());
		splitCand.setC_Tax_Override_ID(ic.getC_Tax_Override_ID());

		splitCand.setExternalHeaderId(ic.getExternalHeaderId());
		splitCand.setExternalLineId(ic.getExternalLineId());

		final Dimension icDimension = dimensionService.getFromRecord(ic);
		dimensionService.updateRecord(splitCand, icDimension);

		return splitCand;
	}

	@Override
	public InvoiceRule getInvoiceRule(@NonNull final I_C_Invoice_Candidate ic)
	{
		final InvoiceRule invoiceRuleOverride = InvoiceRule.ofNullableCode(ic.getInvoiceRule_Override());
		return invoiceRuleOverride != null
				? invoiceRuleOverride
				: InvoiceRule.ofCode(ic.getInvoiceRule());
	}

	@Override
	public LocalDate getDateToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		return TimeUtil.asLocalDate(getDateToInvoiceTS(ic));
	}

	/**
	 * For class-internal use
	 */
	private Timestamp getDateToInvoiceTS(@NonNull final I_C_Invoice_Candidate ic)
	{
		final Timestamp dateToInvoiceOverride = ic.getDateToInvoice_Override();
		if (dateToInvoiceOverride != null)
		{
			return dateToInvoiceOverride;
		}

		return ic.getDateToInvoice();
	}

	@Override
	public IInvoiceGenerateResult createInvoiceGenerateResult(final boolean shallStoreInvoices)
	{
		return new InvoiceGenerateResult(shallStoreInvoices);
	}

	private final AutoClosableThreadLocalBoolean updateProcessInProgress = AutoClosableThreadLocalBoolean.newInstance();

	@Override
	public boolean isUpdateProcessInProgress()
	{
		return updateProcessInProgress.booleanValue();
	}

	@Override
	public IAutoCloseable setUpdateProcessInProgress()
	{
		return updateProcessInProgress.enable();
	}

	@Override
	public I_C_Invoice_Line_Alloc createUpdateIla(InvoiceCandidateAllocCreateRequest request)
	{
		final I_C_Invoice_Candidate invoiceCand = request.getInvoiceCand();
		final I_C_InvoiceLine invoiceLine = request.getInvoiceLine();
		final String note = request.getNote();
		final InvoiceLineAllocType invoiceLineAllocType = request.getInvoiceLineAllocType();

		final StockQtyAndUOMQty qtysInvoiced = request.getQtysInvoiced();
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceCand);

		Check.assume(Env.getAD_Client_ID(ctx) == invoiceCand.getAD_Client_ID(), "AD_Client_ID of " + invoiceCand + " and of its CTX are the same");

		final I_C_Invoice_Line_Alloc existingIla = invoiceCandDAO.retrieveIlaForIcAndIl(invoiceCand, invoiceLine);

		if (existingIla != null)
		{
			translateAndPrependNote(existingIla, note);
			existingIla.setC_Invoice_Line_Alloc_Type(invoiceLineAllocType.getCode());

			//
			// FIXME in follow-up task! (06162)
			if (qtysInvoiced.signum() == 0)
			{
				return existingIla;
			}

			// 05475: If exists, update the qtyInvoiced.
			setQtyInvoiced(existingIla, qtysInvoiced);
			InterfaceWrapperHelper.save(existingIla);
			return existingIla;
		}

		//
		// Create new invoice line allocation
		final Object contextProvider = invoiceCand;
		final I_C_Invoice_Line_Alloc newIla = InterfaceWrapperHelper.newInstance(I_C_Invoice_Line_Alloc.class, contextProvider);
		newIla.setAD_Org_ID(invoiceCand.getAD_Org_ID());
		newIla.setC_Invoice_Candidate(invoiceCand);
		newIla.setC_InvoiceLine(invoiceLine);
		setQtyInvoiced(newIla, qtysInvoiced);
		newIla.setC_Invoice_Candidate_Agg_ID(invoiceCand.getC_Invoice_Candidate_Agg_ID());

		// #870
		// Set Qty and Price Override into the invoice line alloc:
		// Make sure the numbers are correctly taken from the database, and null is not replaced by 0
		newIla.setQtyToInvoice_Override(InterfaceWrapperHelper.getValueOrNull(invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override));
		newIla.setPriceEntered_Override(InterfaceWrapperHelper.getValueOrNull(invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override));

		translateAndPrependNote(newIla, note);
		newIla.setC_Invoice_Line_Alloc_Type(invoiceLineAllocType.getCode());

		InterfaceWrapperHelper.save(newIla); // model validator C_Invoice_Line_Alloc will invalidate 'invoiceCand'

		//
		// Assign Invoice Candidate's Details to Invoice Line
		assignInvoiceDetailsToInvoiceLine(newIla);
		return newIla;
	}

	private void translateAndPrependNote(final I_C_Invoice_Line_Alloc ila, final String note)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ila);

		if (!Check.isEmpty(note, true))
		{
			final String translatedNote = Services.get(IMsgBL.class).parseTranslation(ctx, note);
			ila.setNote(translatedNote + "; " + ila.getNote());
		}
	}

	/**
	 * Updates all {@link I_C_Invoice_Detail} records linked to {@link I_C_Invoice_Candidate} and sets {@link I_C_Invoice} and {@link I_C_InvoiceLine}.
	 */
	private void assignInvoiceDetailsToInvoiceLine(final I_C_Invoice_Line_Alloc ila)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final org.compiere.model.I_C_InvoiceLine invoiceLine = ila.getC_InvoiceLine();
		final I_C_Invoice_Candidate invoiceCand = ila.getC_Invoice_Candidate();

		final ICompositeQueryUpdater<I_C_Invoice_Detail> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_Invoice_Detail.class)
				.addSetColumnValue(org.compiere.model.I_C_Invoice_Detail.COLUMNNAME_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID())
				.addSetColumnValue(org.compiere.model.I_C_Invoice_Detail.COLUMNNAME_C_Invoice_ID, invoiceLine.getC_Invoice_ID());
		queryBL
				.createQueryBuilder(I_C_Invoice_Detail.class, ila)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCand.getC_Invoice_Candidate_ID())
				.create()
				.updateDirectly(queryUpdater);
	}

	// 0917
	@Override
	public void setPriceActual_Override(final I_C_Invoice_Candidate ic)
	{
		// task 08338: check for null, not for 0, because they are different (0 means zero, null means "not set")
		if (InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override)
				&& InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_Discount_Override))
		{
			ic.setPriceActual_Override(null);
		}
		else
		{
			final CurrencyPrecision precision = extractPricePrecision(ic);
			final ProductPrice priceEntered = getPriceEnteredEffective(ic);
			final Percent discount = getDiscount(ic);

			final BigDecimal priceActualOverride = discount
					.subtractFromBase(
							priceEntered.toMoney().toBigDecimal(),
							precision.toInt());
			ic.setPriceActual_Override(priceActualOverride);
		}
	}

	@Override
	public ProductPrice getPriceEnteredEffective(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (!InterfaceWrapperHelper.isNullOrEmpty(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override))
		{
			return createPrice(ic, ic.getPriceEntered_Override());
		}
		return createPrice(ic, ic.getPriceEntered());
	}

	@Override
	public ProductPrice getPriceEntered(@NonNull final I_C_Invoice_Candidate ic)
	{
		return createPrice(ic, ic.getPriceEntered());
	}

	@Override
	public Optional<ProductPrice> getPriceEnteredOverride(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (InterfaceWrapperHelper.isNullOrEmpty(ic, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override))
		{
			return Optional.empty();
		}

		return Optional.of(createPrice(ic, ic.getPriceEntered_Override()));
	}

	private ProductPrice createPrice(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final BigDecimal priceAmount)
	{
		final UomId uomId = UomId.ofRepoId(ic.getC_UOM_ID());
		final CurrencyId currency = CurrencyId.ofRepoId(ic.getC_Currency_ID());
		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());

		return ProductPrice.builder()
				.money(Money.of(priceAmount, currency))
				.uomId(uomId)
				.productId(productId)
				.build();
	}

	@Override
	public boolean isTaxIncluded(final I_C_Invoice_Candidate ic)
	{
		final Boolean taxIncludedOverride;
		if (InterfaceWrapperHelper.isNullOrEmpty(ic, I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded_Override))                 // note: currently, "not set" translates to the empty string, not to null
		{
			taxIncludedOverride = null;
		}
		else
		{
			taxIncludedOverride = X_C_Invoice_Candidate.ISTAXINCLUDED_OVERRIDE_Yes.equals(ic.getIsTaxIncluded_Override());
		}

		if (taxIncludedOverride == null)
		{
			return ic.isTaxIncluded();
		}

		return taxIncludedOverride.booleanValue();
	}

	@Override
	public void handleReversalForInvoice(final org.compiere.model.I_C_Invoice invoice)
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final boolean isAdjustmentChargeInvoice =invoiceBL.isAdjustmentCharge(invoice);

		final int reversalInvoiceId = invoice.getReversal_ID();
		Check.assume(reversalInvoiceId > invoice.getC_Invoice_ID(), "Invoice {} shall be the original invoice and not it's reversal", invoice);

		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);


		final I_C_Invoice invoiceExt = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);

		final boolean creditMemo = Services.get(IInvoiceBL.class).isCreditMemo(invoice);
		final boolean creditedInvoiceReinvoicable = invoiceExt.isCreditedInvoiceReinvoicable(); // task 08927: this is only relevant if isCreditMemo, see below
		final boolean creditedInvoiceIsReversed;
		final boolean creditMemoCreditsInvoice;
		if (creditMemo)
		{
			creditedInvoiceIsReversed = invoiceDAO.isReferencedInvoiceReversed(invoiceExt);
			creditMemoCreditsInvoice = invoiceExt.getRef_Invoice_ID() > 0;
		}
		else
		{
			creditedInvoiceIsReversed = false;
			creditMemoCreditsInvoice = false;
		}

		// if we deal with a credit memo, we need to thread the qtys as negative
		final BigDecimal factor = creditMemo ? ONE.negate() : ONE;

		for (final I_C_InvoiceLine il : invoiceDAO.retrieveLines(invoice))
		{
			//
			// generate reversal C_Invoice_Line_Alloc records (the reversal MatchInv records are created by InvoiceBL)
			final I_C_InvoiceLine reversalLine = invoiceDAO.retrieveReversalLine(il, reversalInvoiceId);
			Check.assumeNotNull(reversalLine, "C_InvoiceLine {} is expected to have a reversal line, like every other line of C_Invoice {}", il, invoice);

			for (final I_C_Invoice_Line_Alloc ilaToReverse : invoiceCandDAO.retrieveIlaForIl(il))
			{
				final StockQtyAndUOMQty qtyInvoicedForIla;
				final String note;

				final I_C_Invoice_Candidate invoiceCandidate = ilaToReverse.getC_Invoice_Candidate();
				invoiceCandidate.setProcessed_Override(null); // reset processed_override, because now that the invoice was reversed, the users might want to do something new with the IC.

				invoiceCandidate.setApprovalForInvoicing(false);

				// #870
				// Make sure that, when an invoice is reversed, the QtyToInvoice_Override and PriceEntered_Override are set back in the invoice candidate based on the values in the allocations
				{
					final int invoiceCandidateId = invoiceCandidate.getC_Invoice_Candidate_ID();

					// Make sure the numbers are correctly taken from the database, and null is not replaced by 0
					final BigDecimal qtyToInvoice_Override = InterfaceWrapperHelper.getValueOrNull(ilaToReverse, I_C_Invoice_Line_Alloc.COLUMNNAME_QtyToInvoice_Override);
					final BigDecimal priceEntered_Override = InterfaceWrapperHelper.getValueOrNull(ilaToReverse, I_C_Invoice_Line_Alloc.COLUMNNAME_PriceEntered_Override);

					Services.get(ITrxManager.class)
							.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
							.newEventListener(TrxEventTiming.AFTER_COMMIT)
							.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
							.registerHandlingMethod(transaction -> setQtyAndPriceOverride(invoiceCandidateId, qtyToInvoice_Override, priceEntered_Override));
				}

				final ProductId productId = ProductId.ofRepoId(il.getM_Product_ID());
				final UomId uomId = UomId.ofRepoId(il.getC_UOM_ID());

				if (creditMemo && creditedInvoiceReinvoicable && !creditedInvoiceIsReversed)
				{
					// undo/reverse the full credit memo quantity. Note that when we handled the credit memo's completion we didn't care about any overlap either, but also created an ila with the full
					// credit memo's qty.
					qtyInvoicedForIla = StockQtyAndUOMQtys
							.create(il.getQtyInvoiced().multiply(factor),
									productId,
									il.getQtyEntered().multiply(factor),
									uomId)
							.negate();

					note = "@C_InvoiceLine@  @QtyInvoiced@ = " + il.getQtyInvoiced() + " @IsCreditedInvoiceReinvoicable@='Y'; ignoring overlap, because credit memo";

				}

				else if (creditMemo && !creditedInvoiceReinvoicable && (creditedInvoiceIsReversed || creditMemoCreditsInvoice))
				{
					// the original credit memo's ila also has QtyInvoiced=0
					qtyInvoicedForIla = StockQtyAndUOMQtys.createZero(productId, uomId);
					note = "@C_InvoiceLine@  @QtyInvoiced@ = " + il.getQtyInvoiced() + " @IsCreditedInvoiceReinvoicable@='N'";

				}
				else
				{
					// default behavior: subtract something, but not more than what was originally invoiced.
					final StockQtyAndUOMQty reversalQtyInvoiced = StockQtyAndUOMQtys
							.create(reversalLine.getQtyInvoiced().multiply(factor),
									productId,
									reversalLine.getQtyEntered().multiply(factor),
									uomId);

					// task 08927: it could be that il's original qtyInvoiced was already subtracted (maybe partially)
					// we only want to subtract the qty that was not yet subtracted
					final Optional<IPair<StockQtyAndUOMQty, Money>> qtyInvoicedAndNetAmtInvoiced = sumupQtyInvoicedAndNetAmtInvoiced(invoiceCandidate);
					assume(qtyInvoicedAndNetAmtInvoiced.isPresent(), "Since the il of this ic is reversed, the ic is supposed to to have an invoiced quantity (even if zero); il={}; ic={}", il, invoiceCandidate);

					final StockQtyAndUOMQty qtyInvoicedForIc = qtyInvoicedAndNetAmtInvoiced.get().getLeft();

					// examples:
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = 3 (because of partial reinvoicable credit memo with qty 2) => overlap=-2 => create Ila with qty -5-(-2)=-3
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = 11 (maybe because of some fuckup, idk) => overlap=6 => create Ila with qty -5-(+6)=-11
					// reversalQtyInvoiced = -5, qtyInvoicedForIc = -10 (maybe because of some fuckup, idk) => overlap=-15 => create ila with qty -5-(-15)=10

					// reversalQtyInvoiced = 5 (because we reverse a credit memo), qtyInvoicedForIc = 1 => overlap=2 => create ila with qty -5-(+2)=3
					final StockQtyAndUOMQty overlap = reversalQtyInvoiced.add(qtyInvoicedForIc);

					//
					// Task 12884 (Reversing an adjustment invoice): Set reversalQtyInvoiced in ila  to have  correct  quantities( ila adj  +  reversal Ila adj = 0)
					if(isAdjustmentChargeInvoice){
						qtyInvoicedForIla = reversalQtyInvoiced;
					}
					else
					{
						qtyInvoicedForIla = reversalQtyInvoiced.subtract(overlap);
					}

					note = "@C_InvoiceLine@  @QtyInvoiced@ = " + reversalQtyInvoiced
							+ ", @C_Invoice_Candidate@ @QtyInvoiced@ = " + qtyInvoicedForIc
							+ ", (=>overlap=" + overlap + ")";

				}

				final InvoiceLineAllocType invoiceLineAllocType;
				if (qtyInvoicedForIla.signum() == 0)
				{
					invoiceLineAllocType = InvoiceLineAllocType.CreditMemoNotReinvoiceable;
				}
				else
				{
					invoiceLineAllocType = InvoiceLineAllocType.CreditMemoReinvoiceable;
				}

				invoiceCandDAO.save(invoiceCandidate);

				final InvoiceCandidateAllocCreateRequest request = InvoiceCandidateAllocCreateRequest.builder()
						.invoiceCand(invoiceCandidate)
						.invoiceLine(reversalLine)
						.qtysInvoiced(qtyInvoicedForIla)
						.note(note)
						.invoiceLineAllocType(invoiceLineAllocType)
						.build();

				createUpdateIla(request);
			}
		}
	}

	/**
	 * Set the qtyToInvoice_Override and Price_Entered_Override in the invoice candidate given by its ID.
	 */
	private static void setQtyAndPriceOverride(final int invoiceCandidateId, final BigDecimal qtyToInvoiceOverride, final BigDecimal priceEnteredOverride)
	{
		final I_C_Invoice_Candidate invoiceCandidate = InterfaceWrapperHelper.load(invoiceCandidateId, I_C_Invoice_Candidate.class);
		invoiceCandidate.setQtyToInvoice_Override(qtyToInvoiceOverride);
		invoiceCandidate.setPriceEntered_Override(priceEnteredOverride);
		invoiceCandidate.setQtyToInvoice_OverrideFulfilled(ZERO);
		InterfaceWrapperHelper.save(invoiceCandidate);
	}

	private void setApprovalForInvoicing(@NonNull final Collection<I_C_Invoice_Candidate> invoiceCandidates, final boolean approved)
	{
		if (invoiceCandidates.isEmpty())
		{
			return;
		}

		for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		{
			invoiceCandidate.setApprovalForInvoicing(approved);
			invoiceCandDAO.save(invoiceCandidate);
		}
	}

	@Override
	public void handleCompleteForInvoice(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		if (!isCreatedByInvoicingJustNow(invoice))
		{
			//
			// Skip reversal invoice completion because we handle the reversal case in "handleReversalForInvoice"
			final int reversalInvoiceId = invoice.getReversal_ID();
			final boolean isReversal = reversalInvoiceId > 0 && invoice.getC_Invoice_ID() > reversalInvoiceId;
			if (isReversal)
			{
				// This is the actual reversal invoice. Skip it. It shall be dealt with in the handleReversalForInvoice() method
				return;
			}

			final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
			final String trxName = InterfaceWrapperHelper.getTrxName(invoice);

			final boolean isCreditMemo = Services.get(IInvoiceBL.class).isCreditMemo(invoice);
			final de.metas.invoicecandidate.model.I_C_Invoice invoiceExt = InterfaceWrapperHelper.create(invoice, de.metas.invoicecandidate.model.I_C_Invoice.class);
			final boolean creditMemoReinvoicable = invoiceExt.isCreditedInvoiceReinvoicable(); // task 08927: this is only relevant if isCreditMemo, see below

			for (final I_C_InvoiceLine il : Services.get(IInvoiceDAO.class).retrieveLines(invoice))
			{
				//
				// Invalidate existing candidates that belong to 'il'
				// NOTE: in case invoice was not created from invoice candidates (e.g. Kommissionierung or Credit memo)
				// here we will get ZERO candidates
				{
					final List<I_C_Invoice_Candidate> invoiceCands = invoiceCandDAO.retrieveIcForIl(il);
					invoiceCandDAO.invalidateCands(invoiceCands);
				}

				final Set<I_C_Invoice_Candidate> toLinkAgainstIl = new TreeSet<>(Comparator.comparing(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID));

				if (il.getC_OrderLine_ID() > 0)
				{
					//
					// If 'il' has an order line, make sure that this order line also has invoice candidates and that those candidates also refer 'il'

					final TableRecordReference olReference = TableRecordReference.of(I_C_OrderLine.Table_Name, il.getC_OrderLine_ID()); // no need to load the OL just yet
					final List<I_C_Invoice_Candidate> existingICs = invoiceCandDAO.retrieveReferencing(olReference);
					if (existingICs.isEmpty())
					{
						final I_C_OrderLine ol = InterfaceWrapperHelper.create(il.getC_OrderLine(), I_C_OrderLine.class);
						// NOTE: in case 'invoice' was not created from invoice candidates, it's a big chance here to get zero existing ICs
						// so we need to create them now

						logger.debug("Current C_InvoiceLine {} has a C_OrderLine {} which is not referenced by any C_Invoice_Candidate", il, ol);
						final IInvoiceCandidateHandlerBL creatorBL = Services.get(IInvoiceCandidateHandlerBL.class);
						final List<I_C_Invoice_Candidate> invoiceCandsNew = creatorBL.createMissingCandidatesFor(ol);

						logger.debug("Created C_Invoice_Candidates for C_OrderLine {}: {}", ol, invoiceCandsNew);
						toLinkAgainstIl.addAll(invoiceCandsNew);

						//
						// Make sure the invoice candidates are valid before we link to them, because their C_Invoice_Candidate_Agg_ID is copied to the ila
						updateInvalid()
								.setContext(ctx, trxName)
								.setTaggedWithAnyTag()
								.setOnlyInvoiceCandidateIds(InvoiceCandidateIdsSelection.extractFixedIdsSet(invoiceCandsNew))
								.update();
					}
					else
					{
						// task 04868, G03T010: even if invoice candidates exist, there might be no link in case the invoice has been created "old school", like new MInvoice(...), or by crediting an
						// invoice
						toLinkAgainstIl.addAll(existingICs);
					}
				}

				if (il.getRef_InvoiceLine_ID() > 0)                                // note: this is (also) the case for credit memos, see IInvoiceBL.creditInvoice() and the invocations it makes
				{
					//
					// task 08927: if il e.g. belongs to the credit memo of an inoutLine or a quality inspection, still get the invoice candidate
					// note that if there is already an invoice, then we assume that the invoice line either has ICs, or that it shall not have any, so we don't try to create missing ICs here
					toLinkAgainstIl.addAll(invoiceCandDAO.retrieveIcForIl(il.getRef_InvoiceLine()));
				}

				for (final I_C_Invoice_Candidate icToLink : toLinkAgainstIl)
				{
					//
					// Make sure invoice candidate is fresh before we actually use it (task 06162, also see javadoc of updateInvalid method)
					InterfaceWrapperHelper.refresh(icToLink);

					// final BigDecimal qtyInvoiced = getQtyToInvoice(icToLink); // why on earth should we use the ic's QtyToInvoice, as opposed to the Qty that *was* actually invoiced to start with?
					final StockQtyAndUOMQty qtysInvoiced;
					final UomId ilUomId = UomId.ofRepoId(il.getC_UOM_ID());
					final ProductId ilProductId = ProductId.ofRepoId(il.getM_Product_ID());

					final String note;

					final InvoiceLineAllocType invoiceLineAllocType;

					if (isCreditMemo)
					{
						final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

						// task 08927
						note = "@C_DocType_ID@=" + docTypeDAO.getById(invoice.getC_DocType_ID()).getName() + ", @IsCreditedInvoiceReinvoicable@=" + creditMemoReinvoicable;
						if (creditMemoReinvoicable)
						{
							qtysInvoiced = StockQtyAndUOMQtys
									.create(il.getQtyInvoiced(), ilProductId, il.getQtyEntered(), ilUomId)
									.negate(); // this will allow the user to re-invoice, just as if the credit memo was a reversal

							invoiceLineAllocType = InvoiceLineAllocType.CreditMemoReinvoiceable;
						}
						else
						{
							qtysInvoiced = StockQtyAndUOMQtys.createZero(ilProductId, ilUomId);

							invoiceLineAllocType = InvoiceLineAllocType.CreditMemoNotReinvoiceable;
						}
					}
					else
					{
						note = "";
						qtysInvoiced = StockQtyAndUOMQtys
								.create(il.getQtyInvoiced(), ilProductId, il.getQtyEntered(), ilUomId); // the standard case

						invoiceLineAllocType = InvoiceLineAllocType.CreatedFromIC;
					}

					final InvoiceCandidateAllocCreateRequest request = InvoiceCandidateAllocCreateRequest.builder()
							.invoiceCand(icToLink)
							.invoiceLine(il)
							.qtysInvoiced(qtysInvoiced)
							.note(note)
							.invoiceLineAllocType(invoiceLineAllocType)
							.build();

					createUpdateIla(request);
					// note: if an ILA is created, the icToLink is automatically invalidated via C_Invoice_Line_Alloc model validator
				}

				setApprovalForInvoicing(toLinkAgainstIl, false);
			}
		}
		else
		{
			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidates(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
			setApprovalForInvoicing(invoiceCandidates, false);
		}
	}

	@Override
	public boolean isCreatedByInvoicingJustNow(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		return !DYNATTR_INVOICING_FROM_INVOICE_CANDIDATES_IS_IN_PROGRESS.isNull(invoice)
				&& DYNATTR_INVOICING_FROM_INVOICE_CANDIDATES_IS_IN_PROGRESS.getValue(invoice); // nothing to do for us
	}

	@Override
	public void updateProcessedFlag(final I_C_Invoice_Candidate ic)
	{
		Boolean processed = null;
		if (!InterfaceWrapperHelper.isNullOrEmpty(ic, I_C_Invoice_Candidate.COLUMNNAME_Processed_Override))
		{
			// #243: if it is set, then always go with the processed-override value; even if the IC has an error
			processed = extractProcessedOverride(ic).isTrue();
		}

		final boolean processedCalc;

		// If invoice candidate has errors, don't update the Processed_Calc value until the error is solved.
		if (ic.isError())
		{
			// ...unless (gh #428) the ic is currently processed, and there is an error, then we might want to unset the processed flag. Otherwise, the users might not notice the error.
			// if processedCalc is currently set, we want to unset it.
			// note that we still leave
			processedCalc = false;
		}
		else
		{
			//
			// if qtyInvoiced is >= qtyOrdered, then there is no further Qty to be invoiced
			final BigDecimal qtyOrdered = getQtyOrderedInStockUOM(ic);
			final boolean noOpenQty = qtyOrdered.abs().compareTo(ic.getQtyInvoiced().abs()) <= 0;

			//
			// we need to know if there are already any invoice lines which have not been reversed
			final List<I_C_Invoice_Line_Alloc> ilasForIc = invoiceCandDAO.retrieveIlaForIc(InvoiceCandidateIds.ofRecord(ic));
			int nonReversedIlas = 0;
			for (final I_C_Invoice_Line_Alloc ila : ilasForIc)
			{
				final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(ila.getDocStatus());
				if (!docStatus.isReversed())
				{
					nonReversedIlas++;
				}
			}

			// task 08567: add a way to override "processed" by the user
			processedCalc = noOpenQty && nonReversedIlas > 0;
		}

		if (processed == null)
		{
			processed = processedCalc; // processed wasn't set via Processed_Override
		}

		ic.setProcessed_Calc(processedCalc);
		ic.setProcessed(processed);

		if (processed)
		{
			// 08459
			// If the IC is processed, the qtyToInvoice must turn 0
			ic.setQtyToInvoiceInPriceUOM(ZERO);
			ic.setQtyToInvoice(ZERO);
			ic.setQtyToInvoiceInUOM(ZERO);
			ic.setQtyToInvoiceBeforeDiscount(ZERO);

			ic.setApprovalForInvoicing(false);
		}
	}

	@Override
	public void resetError(final I_C_Invoice_Candidate ic)
	{
		ic.setIsError(false);
		ic.setAD_Note_ID(-1);
		ic.setErrorMsg(null);
	}

	/* package */void discardChangesAndSetError(final I_C_Invoice_Candidate ic, final Exception e)
	{
		//
		// Backup fields that we want to keep
		final String resultMsgBkp = ic.getSchedulerResult();

		//
		// Discard all changes that we did so far in our invoice candidate
		InterfaceWrapperHelper.refresh(ic, true); // discardChanges=true

		//
		// Set back the fields we wanted to keep
		ic.setSchedulerResult(resultMsgBkp);

		//
		// Update error status
		setError(ic, e);
	}

	@Override
	public void setError(final I_C_Invoice_Candidate ic, final Throwable e)
	{
		Check.assumeNotNull(e, "e not null");

		I_AD_Note note = null;

		// 02817: Don't only handle InconsistentUpdateExeptions, but handle RuntimeExceptions in general
		// (albeit less user-friendly)
		// Reason: The system should be able to mark the problematic record and go on
		if (e instanceof InconsistentUpdateException)
		{
			final InconsistentUpdateException iue = (InconsistentUpdateException)e;
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
			note = new MNote(ctx,
							 iue.getAdMessageHeadLine().toAD_Message(),
							 iue.getAdUserToNotifyId(),
							 ITrx.TRXNAME_None // save it out-of-trx
			);
			note.setTextMsg(e.getMessage());
			note.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice_Candidate.Table_Name));
			note.setRecord_ID(ic.getC_Invoice_Candidate_ID());
			InterfaceWrapperHelper.save(note);
		}

		final boolean askForRegeneration;
		if (e instanceof ProductNotOnPriceListException)
		{
			askForRegeneration = true;
		}
		else
			askForRegeneration = e instanceof ProductNotOnPriceListException;

		String errorMsg = e.getLocalizedMessage();
		if (Check.isEmpty(errorMsg) || errorMsg.length() < 4)
		{
			errorMsg = e.toString();
		}

		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			logger.warn("Failed processing invoice candidate BUT GOING FORWARD: " + ic, e);
		}

		setError(ic, errorMsg, note, askForRegeneration);
	}

	@Override
	public void setError(final I_C_Invoice_Candidate ic, final String errorMsg, final I_AD_Note note)
	{
		final boolean askForDeleteRegeneration = false;
		setError(ic, errorMsg, note, askForDeleteRegeneration);
	}

	@Override
	public void setError(
			@NonNull final I_C_Invoice_Candidate ic,
			final String errorMsg,
			final I_AD_Note note,
			final boolean askForDeleteRegeneration)
	{
		final String errorMessageToUse;
		if (!askForDeleteRegeneration)
		{
			errorMessageToUse = errorMsg;
		}
		else
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
			final String msgAskForRegeneration = Services.get(IMsgBL.class).getMsg(ctx, MSG_FixProblemDeleteWaitForRegeneration);
			errorMessageToUse = new StringBuilder(errorMsg)
					.append("\n").append(msgAskForRegeneration)
					.toString();
		}

		ic.setIsError(true);
		ic.setErrorMsg(errorMessageToUse);
		ic.setAD_Note_ID(note != null ? note.getAD_Note_ID() : -1);

		//
		// If we are running in batch update process, append the error message to SchedulerResult just to not lose it in case more issues are coming
		if (isUpdateProcessInProgress())
		{
			amendSchedulerResult(ic, errorMessageToUse);
		}
	}

	@Override
	public void setInvoicingErrorAndSave(
			@NonNull final I_C_Invoice_Candidate ic,
			final String errorMsg,
			final I_AD_Note note)
	{
		setError(ic, errorMsg, note);

		ic.setIsInvoicingError(true);
		ic.setInvoicingErrorMsg(errorMsg);

		invoiceCandDAO.save(ic);
	}

	@Override
	public void clearInvoicingErrorAndSave(@NonNull final I_C_Invoice_Candidate ic)
	{
		ic.setIsInvoicingError(false);
		ic.setInvoicingErrorMsg(null);

		invoiceCandDAO.save(ic);
	}

	// package-visible for testing
	/* package */BigDecimal getQtyDelivered_Effective(final I_C_Invoice_Candidate ic)
	{

		final BigDecimal factor;
		if (ic.getQtyOrdered().signum() < 0)
		{
			factor = ONE.negate();
		}
		else
		{
			factor = ONE;
		}

		return ic.getQtyDelivered().subtract((ic.getQtyWithIssues_Effective()).multiply(factor));
	}

	@Override
	public Tax getTaxEffective(final I_C_Invoice_Candidate candidate)
	{
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

		final Tax taxOverride = taxDAO.getTaxByIdOrNull(candidate.getC_Tax_Override_ID());

		if (taxOverride != null)
		{
			return taxOverride;
		}
		return taxDAO.getTaxById(candidate.getC_Tax_ID());
	}

	/**
	 * Calculate Maximum invoiceable quantity by considering ordered qty and delivered qty.
	 * <p>
	 * Normally the returning value is <code>qtyOrdered</code>, but in case we have a over delivery, then we shall consider <code>qtyDelivered</code> as invoiceable quantity.
	 *
	 * @return maximum invoiceable quantity
	 * @task 07847
	 */
	// package level for testing purposes
	/* package */Quantity calculateMaxInvoicableQtyFromEnteredAndDelivered(
			@NonNull final Quantity qtyOrdered,
			@NonNull final Quantity qtyDelivered)
	{
		final Quantity maxInvoicableQty;
		final boolean negativeQtyEntered = qtyOrdered.signum() <= 0;

		//
		// Case: we deal with a negative quantity ordered (e.g. returns, "reversals", manual invoice candidates with negative Qty)
		if (negativeQtyEntered)
		{
			maxInvoicableQty = qtyOrdered.min(qtyDelivered);
		}
		//
		// Standard Case: we deal with positive quantity ordered
		else
		{
			maxInvoicableQty = qtyOrdered.max(qtyDelivered);
		}

		return maxInvoicableQty;
	}

	@Override
	public Percent getQualityDiscountPercentEffective(final I_C_Invoice_Candidate candidate)
	{
		final BigDecimal qualitDiscountPercentOverride = InterfaceWrapperHelper.getValueOrNull(candidate, I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override);

		if (qualitDiscountPercentOverride != null)
		{
			return Percent.of(qualitDiscountPercentOverride);
		}

		return Percent.of(candidate.getQualityDiscountPercent());
	}

	@Override
	public void updatePOReferenceFromOrder(final I_C_Invoice_Candidate candidate)
	{
		if (candidate.isProcessed() == true)
		{
			return; // do nothing in case of processed ICs
		}
		if (candidate.getC_Order_ID() <= 0)
		{
			return;
		}

		final I_C_Order order = InterfaceWrapperHelper.create(candidate.getC_Order(), I_C_Order.class);

		// In case the order is not completed (i.e. it was reactivated) and if it also wasn't modified by anyone,
		// then null the POReference
		if (Objects.equals(order.getPOReference(), candidate.getPOReference())
				&& !IDocument.STATUS_Completed.equals(order.getDocStatus()))
		{
			candidate.setPOReference(null);
			return;
		}

		if (!Check.isEmpty(candidate.getPOReference(), true))
		{
			// do not change an already set POReference in an invoice candidate
			return;
		}
		candidate.setPOReference(order.getPOReference());
	}

	@Override
	public LocalDate getToday()
	{
		// NOTE: use login date if available
		final Properties ctx = Env.getCtx();
		return Env.getLocalDate(ctx);
	}

	@Override
	public void setQualityDiscountPercent_Override(final I_C_Invoice_Candidate ic, final ImmutableAttributeSet attributes)
	{
		final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		final PricingConditionsId pricingConditionsId = PricingConditionsId.ofRepoIdOrNull(bpartnerBL.getDiscountSchemaId(BPartnerId.ofRepoId(ic.getBill_BPartner_ID()), SOTrx.ofBoolean(ic.isSOTrx())));
		if (pricingConditionsId == null)
		{
			// do nothing
			return;
		}

		final BigDecimal qualityDiscountPercentage;
		final PricingConditions pricingConditions = pricingConditionsRepo.getPricingConditionsById(pricingConditionsId);
		if (pricingConditions.isBreaksDiscountType())
		{
			BigDecimal qty = ic.getQtyToInvoice();
			if (qty.signum() < 0)
			{
				final org.compiere.model.I_M_InOut inout = ic.getM_InOut();

				if (inout != null)
				{
					if (Services.get(IInOutBL.class).isReturnMovementType(inout.getMovementType()))
					{
						qty = qty.negate();
					}
				}
			}

			final BigDecimal priceActual = ic.getPriceActual();
			final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());
			final ProductAndCategoryAndManufacturerId product = productsRepo.retrieveProductAndCategoryAndManufacturerByProductId(productId);

			final PricingConditionsBreak appliedBreak = pricingConditions.pickApplyingBreak(PricingConditionsBreakQuery.builder()
																									.attributes(attributes)
																									.product(product)
																									.qty(qty)
																									.price(priceActual)
																									.build());

			qualityDiscountPercentage = appliedBreak != null ? appliedBreak.getQualityDiscountPercentage() : null;
		}
		else
		{
			qualityDiscountPercentage = null;
		}

		ic.setQualityDiscountPercent_Override(qualityDiscountPercentage);
	}

	@Override
	public void closeInvoiceCandidatesByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidatesForOrderLineId(orderLineId);
		closeInvoiceCandidates(invoiceCandidates.iterator());
	}

	@Override
	public void closeInvoiceCandidates(
			@NonNull final Iterator<I_C_Invoice_Candidate> candidatesToClose)
	{
		while (candidatesToClose.hasNext())
		{
			closeInvoiceCandidate(candidatesToClose.next());
		}
	}

	@Override
	public void closeInvoiceCandidate(final I_C_Invoice_Candidate candidate)
	{
		final IInvoiceCandidateListeners invoiceCandidateListeners = Services.get(IInvoiceCandidateListeners.class);
		invoiceCandidateListeners.onBeforeClosed(candidate);
		candidate.setProcessed_Override("Y");
		candidate.setQtyToInvoice(ZERO);

		if (!InterfaceWrapperHelper.hasChanges(candidate))
		{
			return; // https://github.com/metasfresh/metasfresh/issues/3216
		}

		invoiceCandDAO.invalidateCand(candidate);
		InterfaceWrapperHelper.save(candidate);
	}

	@Override
	public boolean isCloseIfIsToClear()
	{
		final boolean isCloseIfIsToClear = Services.get(ISysConfigBL.class)
				.getBooleanValue(SYS_Config_C_Invoice_Candidate_Close_IsToClear, false);

		return isCloseIfIsToClear;
	}

	@Override
	public void closePartiallyInvoiced_InvoiceCandidates(@NonNull final I_C_Invoice invoice)
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		if (invoiceBL.isReversal(invoice))
		{
			logger.debug("C_Invoice is a reversal; => not closing any invoice candidates");
			return;
		}

		if (!isCloseIfPartiallyInvoiced(OrgId.ofRepoId(invoice.getAD_Org_ID())))
		{
			logger.debug("isCloseIfPartiallyShipped=false for AD_Org_ID={}; => not closing any invoice candidates", invoice.getAD_Org_ID());
			return;
		}

		for (final I_C_InvoiceLine ilRecord : invoiceDAO.retrieveLines(invoice))
		{
			try (final MDCCloseable ilRecordMDC = TableRecordMDC.putTableRecordReference(ilRecord))
			{
				for (final I_C_Invoice_Candidate candidate : invoiceCandDAO.retrieveIcForIl(ilRecord))
				{
					try (final MDCCloseable candidateMDC = TableRecordMDC.putTableRecordReference(candidate))
					{

						final InvoiceRule candidateInvoiceRule = InvoiceRule.ofCode(candidate.getInvoiceRule());

						if (!canCloseBasedOnInvoiceRule(candidateInvoiceRule))
						{
							logger.debug("candidate.invoiceRule={} ; => not closing invoice candidate with id={}", candidateInvoiceRule, candidate.getC_Invoice_Candidate_ID());
							continue;
						}

						if (ilRecord.getQtyInvoiced().compareTo(candidate.getQtyOrdered()) < 0)
						{
							logger.debug("invoiceLine.qtyInvoiced={} is < invoiceCandidate.qtyOrdered={}; -> closing invoice candidate",
										 ilRecord.getQtyInvoiced(), candidate.getQtyOrdered());
							closeInvoiceCandidate(candidate);
						}
						else
						{
							logger.debug("invoiceLine.qtyInvoiced={} is >= invoiceCandidate.qtyOrdered={}; -> not closing invoice candidate",
										 ilRecord.getQtyInvoiced(), candidate.getQtyOrdered());
						}
					}
				}
			}
		}
	}

	private boolean canCloseBasedOnInvoiceRule(@NonNull final InvoiceRule candidateInvoiceRule)
	{
		switch (candidateInvoiceRule)
		{
			case AfterDelivery:
			case AfterOrderDelivered:
			case CustomerScheduleAfterDelivery:
			case OrderCompletelyDelivered:
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean isCloseIfPartiallyInvoiced(@NonNull final OrgId orgId)
	{
		final boolean isCloseIfPartiallyInvoiced = Services.get(ISysConfigBL.class)
				.getBooleanValue(
						SYS_Config_C_Invoice_Candidate_Close_PartiallyInvoiced, false,
						ClientId.METASFRESH.getRepoId(), orgId.getRepoId());

		return isCloseIfPartiallyInvoiced;
	}

	@Override
	public void candidates_unProcess(final I_C_Invoice invoice)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		for (final I_C_InvoiceLine il : invoiceDAO.retrieveLines(invoice))
		{
			for (final I_C_Invoice_Candidate candidate : invoiceCandDAO.retrieveIcForIl(il))
			{
				if (candidate == null)
				{
					// shall not happen
					continue;
				}

				final OptionalBoolean processedOverride = extractProcessedOverride(candidate);
				if (processedOverride.isUnknown())
				{
					// nothing to do
					continue;
				}

				if (processedOverride.isTrue())
				{
					candidate.setProcessed_Override(null);
					invoiceCandDAO.save(candidate);
				}
			}
		}
	}

	@Override
	public void markInvoiceCandInDisputeForReceiptLine(final I_M_InOutLine receiptLine)
	{
		final IInOutBL inoutBL = Services.get(IInOutBL.class);

		if (receiptLine.getM_InOut().isSOTrx())
		{
			// not interesting. Do nothing
			return;
		}

		if (inoutBL.isReversal(receiptLine))
		{
			// not interesting, Do nothing
			return;
		}

		final List<I_C_Invoice_Candidate> icRecords = invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(receiptLine);
		for (final I_C_Invoice_Candidate icRecord : icRecords)
		{
			try (final MDCCloseable icRecordMDC = TableRecordMDC.putTableRecordReference(icRecord))
			{
				logger.debug("Set IsInDispute=true because ic belongs to M_InOutLine_ID={}", receiptLine.getM_InOutLine_ID());
				icRecord.setIsInDispute(true);
				save(icRecord);
			}
		}
	}

	public void setQtyAndDateForFreightCost(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		if (!icRecord.isFreightCost())
		{
			return;    // nothing to do
		}
		final OrderId orderId = OrderId.ofRepoIdOrNull(icRecord.getC_Order_ID());

		if (orderId == null)
		{
			return; // nothing to do
		}

		final InvoiceableInvoiceCandIdResult invoiceableInvoiceCandIdResult = invoiceCandDAO.getFirstInvoiceableInvoiceCandId(orderId);

		final boolean hasInvoiceableICs = invoiceableInvoiceCandIdResult.getFirstInvoiceableInvoiceCandId() != null;
		final boolean hasICsToWaitFor = invoiceableInvoiceCandIdResult.isOrderHasInvoiceCandidatesToWaitFor();
		if (hasInvoiceableICs)
		{
			if (icRecord.getC_OrderLine_ID() <= 0)
			{
				return; // nothing to do
			}

			final I_C_Invoice_Candidate firstInvoiceableCandRecord = invoiceCandDAO.getById(invoiceableInvoiceCandIdResult.getFirstInvoiceableInvoiceCandId());

			logger.debug("C_Order_ID={} of this freight-cost invoice candidate has other invoicable C_Invoice_Candidate_ID={}; -> set DeliveryDate its DeliveryDate={}",
						 orderId.getRepoId(), firstInvoiceableCandRecord.getC_Invoice_Candidate_ID(), firstInvoiceableCandRecord.getDeliveryDate());
			icRecord.setDeliveryDate(firstInvoiceableCandRecord.getDeliveryDate());
		}
		else if (hasICsToWaitFor)
		{
			logger.debug("C_Order_ID={} of this freight-cost invoice candidate has other not-yet-invoicable ICs to wait for; -> set QtyToInvoice to zero", orderId.getRepoId());
			icRecord.setQtyToInvoice(ZERO); // ok, let's wait for those other ICs
			icRecord.setQtyDelivered(ZERO);
			icRecord.setQtyToInvoiceInUOM(ZERO);
		}
	}

	@Override
	public OptionalBoolean extractProcessedOverride(@NonNull final I_C_Invoice_Candidate candidate)
	{
		return OptionalBoolean.ofNullableString(candidate.getProcessed_Override());
	}

	@Override
	public void updateICIOLAssociationFromIOL(
			@NonNull final I_C_InvoiceCandidate_InOutLine iciol,
			@NonNull final org.compiere.model.I_M_InOutLine inOutLine)
	{
		iciol.setAD_Org_ID(inOutLine.getAD_Org_ID());
		iciol.setM_InOutLine(inOutLine);
		// iciol.setQtyInvoiced(QtyInvoiced); // will be set during invoicing to keep track of which movementQty is already invoiced in case of partial invoicing

		iciol.setQtyDelivered(getActualDeliveredQty(inOutLine));

		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.fromRecordString(iciol.getC_Invoice_Candidate().getInvoicableQtyBasedOn());
		if (inOutLine.getCatch_UOM_ID() > 0 && invoicableQtyBasedOn.isCatchWeight())
		{
			// only if the ic is about catch-weight, then we attempt to record it in the iciol.
			// the inoutline may have a catch-weight, because the respective goods may have a weight.
			iciol.setC_UOM_ID(inOutLine.getCatch_UOM_ID());

			// if inOutLine.QtyDeliveredCatch is null, then iciol.QtyDeliveredInUOM_Catch must also be null and not zero.
			final BigDecimal catchQtyOrNull = getValueOrNull(inOutLine, I_M_InOutLine.COLUMNNAME_QtyDeliveredCatch);
			iciol.setQtyDeliveredInUOM_Catch(catchQtyOrNull);

			// make sure that both quantities have the same UOM
			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

			final BigDecimal nominalQty = uomConversionBL.convertQty(
					UOMConversionContext.of(ProductId.ofRepoId(inOutLine.getM_Product_ID())),
					inOutLine.getQtyEntered(),
					UomId.ofRepoId(inOutLine.getC_UOM_ID()) /* uomFrom */,
					UomId.ofRepoId(inOutLine.getCatch_UOM_ID()) /* uomTo */
			);
			iciol.setQtyDeliveredInUOM_Nominal(nominalQty);
		}
		else
		{
			iciol.setC_UOM_ID(assumeGreaterThanZero(inOutLine.getC_UOM_ID(), "inOutLine.getC_UOM_ID()"));
			iciol.setQtyDeliveredInUOM_Nominal(inOutLine.getQtyEntered());
		}
		saveRecord(iciol);
	}

	@Override
	public int createSelectionForInvoiceCandidates(
			@NonNull final List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds,
			@NonNull final PInstanceId pInstanceId)
	{
		final InvoiceCandidateMultiQueryBuilder multiQuery = InvoiceCandidateMultiQuery.builder();
		for (final ExternalHeaderIdWithExternalLineIds headerWithLineIds : headerAndLineIds)
		{
			multiQuery.query(InvoiceCandidateQuery.builder()
									 .externalIds(headerWithLineIds)
									 .build());
		}

		return invoiceCandDAO.createSelectionByQuery(multiQuery.build(), pInstanceId);
	}

	@Override
	public List<I_C_Queue_WorkPackage> getUnprocessedWorkPackagesForInvoiceCandidate(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		return queueDAO.retrieveUnprocessedWorkPackagesByEnqueuedRecord(
				InvoiceCandWorkpackageProcessor.class,
				TableRecordReference.of(I_C_Invoice_Candidate.Table_Name, invoiceCandidateId));
	}

	@Override
	@NonNull
	public Set<InvoiceCandidateId> voidAndReturnInvoiceCandIds(@NonNull final org.compiere.model.I_C_Invoice invoice)
	{
		// first make sure that payments have the flag auto-allocate set
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

		final I_C_Invoice inv = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);

		final List<I_C_Payment> availablePayments = allocationDAO.retrieveInvoicePayments(inv);

		for (final I_C_Payment payment : availablePayments)
		{
			payment.setIsAutoAllocateAvailableAmt(true);
			InterfaceWrapperHelper.save(payment);
		}

		// first fetch invoice candidates
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final List<I_C_Invoice_Candidate> invoiceCands = new ArrayList<>();

		final MInvoice invoicePO = InterfaceWrapperHelper.getPO(invoice);
		for (final MInvoiceLine ilPO : invoicePO.getLines(true))
		{
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(ilPO, I_C_InvoiceLine.class);
			invoiceCands.addAll(invoiceCandDAO.retrieveIcForIl(il));
		}

		if (invoiceCands.isEmpty())
		{
			return ImmutableSet.of();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);

		// void invoice
		Services.get(IDocumentBL.class).processEx(invoice, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);

		// update invalids
		invoiceCandBL.updateInvalid()
				.setContext(ctx, trxName)
				.setOnlyInvoiceCandidateIds(InvoiceCandidateIdsSelection.extractFixedIdsSet(invoiceCands))
				.update();

		for (final I_C_Invoice_Candidate ic : invoiceCands)
		{
			InterfaceWrapperHelper.refresh(ic); // this is important ;-)
			final Timestamp today = SystemTime.asDayTimestamp();
			// if the invoice was a future invoice, use same date
			if (today.before(invoicePO.getDateInvoiced()))
			{
				ic.setDateInvoiced(invoicePO.getDateInvoiced());
			}
			// we set this to null in order to have the new invoice with the current date
			else
			{
				ic.setDateInvoiced(null);
			}
			InterfaceWrapperHelper.save(ic, trxName);
		}

		return invoiceCands
				.stream()
				.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	// TODO: would be nice to use de.metas.ui.web.view.descriptor.SqlAndParams but that is in module webui-api, and here we don't have access to it
	@Override
	public @NonNull InvoiceCandidatesAmtSelectionSummary calculateAmtSelectionSummary(@Nullable final String extraWhereClause)
	{
		return new GetInvoiceCandidatesAmtSelectionSummaryCommand(extraWhereClause).execute();
	}

	/**
	 * Tests whether it makes sense to update associated records for a given {@link I_C_Invoice_Candidate}
	 * That's currently the case if:
	 * <ul>
	 *     <li>{@link I_C_Invoice_Candidate#getInvoiceRule()}  return {@link InvoiceRule#OrderCompletelyDelivered}</li>
	 *     <li>{@link I_C_Invoice_Candidate#getDateToInvoice()} is not null and not{@link Env#MAX_DATE}</li>
	 * </ul>
	 */
	public boolean isCandidateForRecalculate(final I_C_Invoice_Candidate icRecord)
	{
		final InvoiceRule invoiceRule = getInvoiceRule(icRecord);
		final Timestamp dateToInvoice = icRecord.getDateToInvoice();
		return InvoiceRule.OrderCompletelyDelivered.equals(invoiceRule) && dateToInvoice != null && dateToInvoice.before(Env.MAX_DATE);
	}

	public Collection<I_C_Invoice_Candidate> getRefreshedAssociatedInvoiceCandidates(final Iterator<I_C_Invoice_Candidate> candidates, final Collection<Integer> processedRecords)
	{
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(candidates, Spliterator.ORDERED), false)
				.filter(c -> !processedRecords.contains(c.getC_Invoice_Candidate_ID()))
				.map(ic -> {
					set_DateToInvoice_DefaultImpl(ic);
					return ic;
				})
				.collect(Collectors.toSet());
	}

	@Override
	public void setAsyncBatch(@NonNull final InvoiceCandidateId invoiceCandidateId, @NonNull final AsyncBatchId asyncBatchId)
	{
		final I_C_Invoice_Candidate invoiceCandidate = invoiceCandDAO.getById(invoiceCandidateId);

		if (invoiceCandidate.isProcessed())
		{
			Loggables.withLogger(logger, Level.WARN).addLog("InvoiceCandBL.setAsyncBatch(): C_Invoice_Candidate already processed,"
																	+ " nothing to do! InvoiceCandidateId: {}", invoiceCandidate.getC_Invoice_Candidate_ID());
			return;
		}

		invoiceCandidate.setC_Async_Batch_ID(asyncBatchId.getRepoId());

		invoiceCandDAO.save(invoiceCandidate);
	}

	@Override
	public Quantity getQtyOrderedStockUOM(@NonNull final I_C_Invoice_Candidate ic)
	{
		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());

		return Quantitys.create(ic.getQtyOrdered(), productId);
	}

	@Override
	public Quantity getQtyInvoicedStockUOM(@NonNull final I_C_Invoice_Candidate ic)
	{
		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());

		return Quantitys.create(ic.getQtyInvoiced(), productId);
	}

	@Override
	public BPartnerLocationAndCaptureId getBillLocationId(
			@NonNull final I_C_Invoice_Candidate ic,
			final boolean useDefaultBillLocationAndContactIfNotOverride)
	{
		final BPartnerLocationAndCaptureId billBPLocationOverrideId = InvoiceCandidateLocationAdapterFactory
				.billLocationOverrideAdapter(ic)
				.getBPartnerLocationAndCaptureIdIfExists()
				.orElse(null);
		if (billBPLocationOverrideId != null)
		{
			return billBPLocationOverrideId;
		}

		if (useDefaultBillLocationAndContactIfNotOverride)
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(ic.getBill_BPartner_ID());
			final I_C_BPartner_Location defaultBillLocation = bpartnerDAO.retrieveCurrentBillLocationOrNull(bpartnerId);
			if (defaultBillLocation != null)
			{
				return BPartnerLocationAndCaptureId.ofRecord(defaultBillLocation);
			}
		}

		return InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.getBPartnerLocationAndCaptureId();
	}

	@Override
	public void registerVetoer(final ModelWithoutInvoiceCandidateVetoer vetoer, final String tableName)
	{
		final Collection<ModelWithoutInvoiceCandidateVetoer> listeners = tableName2Listeners.computeIfAbsent(tableName, k -> new ArrayList<>());
		listeners.add(vetoer);
	}

	@Override
	public boolean isAllowedToCreateInvoiceCandidateFor(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final Collection<ModelWithoutInvoiceCandidateVetoer> listeners = tableName2Listeners.get(tableName);
		if (listeners == null)
		{
			return true;
		}

		for (final ModelWithoutInvoiceCandidateVetoer listener : listeners)
		{
			if (I_VETO.equals(listener.foundModelWithoutInvoiceCandidate(model)))
			{
				return false;
			}
		}

		return true;
	}

	@NonNull
	private BigDecimal getActualDeliveredQty(@NonNull final org.compiere.model.I_M_InOutLine inOutLine)
	{
		final org.compiere.model.I_M_InOut inOut = inoutBL.getById(InOutId.ofRepoId(inOutLine.getM_InOut_ID()));
		final DocStatus docStatus = DocStatus.ofCode(inOut.getDocStatus());

		Loggables.withLogger(logger, Level.DEBUG)
				.addLog("DocStatus for M_InOutLine_ID={} is {}", inOutLine.getM_InOutLine_ID(), docStatus.getCode());

		if (docStatus.equals(DocStatus.Completed) || docStatus.equals(DocStatus.Closed))
		{
			return inOutLine.getMovementQty();
		}

		return ZERO;
	}
}
