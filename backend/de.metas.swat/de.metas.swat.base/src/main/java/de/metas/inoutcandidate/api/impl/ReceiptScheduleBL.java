package de.metas.inoutcandidate.api.impl;

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

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.deliveryplanning.DeliveryPlanningCreateRequest;
import de.metas.deliveryplanning.DeliveryPlanningType;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.IInOutBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.ApplyReceiptScheduleChangesRequest;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleAllocBuilder;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.modelvalidator.M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleListener;
import de.metas.inoutcandidate.spi.impl.CompositeReceiptScheduleListener;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.api.LoggableTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AddAttributesRequest;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiptScheduleBL implements IReceiptScheduleBL
{
	public static final String SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS = "de.metas.inoutcandidate.M_ReceiptSchedule.canBeExportedAfterSeconds";

	private final static transient Logger logger = LogManager.getLogger(M_ReceiptSchedule.class);

	private final CompositeReceiptScheduleListener listeners = new CompositeReceiptScheduleListener();
	private final IAggregationKeyBuilder<I_M_ReceiptSchedule> headerAggregationKeyBuilder = new ReceiptScheduleHeaderAggregationKeyBuilder();

	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private IReceiptScheduleQtysBL receiptScheduleQtysBL = Services.get(IReceiptScheduleQtysBL.class);

	@Override
	public void addReceiptScheduleListener(IReceiptScheduleListener listener)
	{
		listeners.addReceiptScheduleListener(listener);
	}

	@Override
	public IAggregationKeyBuilder<I_M_ReceiptSchedule> getHeaderAggregationKeyBuilder()
	{
		return headerAggregationKeyBuilder;
	}

	@Override
	public WarehouseId getWarehouseEffectiveId(final I_M_ReceiptSchedule rs)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(rs.getM_Warehouse_Override_ID());
		if (warehouseId != null)
		{
			return warehouseId;
		}

		return WarehouseId.ofRepoId(rs.getM_Warehouse_ID());
	}

	@Override
	public I_M_Warehouse getM_Warehouse_Effective(final I_M_ReceiptSchedule rs)
	{
		final WarehouseId warehouseId = getWarehouseEffectiveId(rs);
		return Services.get(IWarehouseBL.class).getById(warehouseId);
	}

	@Override
	public LocatorId getLocatorEffectiveId(final I_M_ReceiptSchedule rs)
	{
		final WarehouseId warehouseId = getWarehouseEffectiveId(rs);
		return Services.get(IWarehouseBL.class).getOrCreateDefaultLocatorId(warehouseId);
	}

	@Override
	public int getM_AttributeSetInstance_Effective_ID(final I_M_ReceiptSchedule rs)
	{
		// TODO: introduce M_AttributeSetInstance_Override_ID
		return rs.getM_AttributeSetInstance_ID();
	}

	@Override
	public I_M_AttributeSetInstance getM_AttributeSetInstance_Effective(final I_M_ReceiptSchedule rs)
	{
		// TODO: introduce M_AttributeSetInstance_Override_ID
		return rs.getM_AttributeSetInstance();
	}

	@Override
	public void setM_AttributeSetInstance_Effective(final I_M_ReceiptSchedule rs, @NonNull final AttributeSetInstanceId asiId)
	{
		// TODO: introduce M_AttributeSetInstance_Override_ID
		rs.setM_AttributeSetInstance_ID(asiId.getRepoId());
	}

	@Override
	public BigDecimal getQtyOrdered(final I_M_ReceiptSchedule rs)
	{
		return Services.get(IReceiptScheduleQtysBL.class).getQtyOrdered(rs);
	}

	@Override
	public BigDecimal getQtyMoved(final I_M_ReceiptSchedule rs)
	{
		return Services.get(IReceiptScheduleQtysBL.class).getQtyMoved(rs);
	}

	@Override
	public BigDecimal getQtyMovedWithIssues(final I_M_ReceiptSchedule rs)
	{
		return Services.get(IReceiptScheduleQtysBL.class).getQtyMovedWithIssues(rs);
	}

	@Override
	public StockQtyAndUOMQty getQtyToMove(final I_M_ReceiptSchedule rs)
	{
		return Services.get(IReceiptScheduleQtysBL.class).getQtyToMove(rs);
	}

	@Override
	public int getC_BPartner_Location_Effective_ID(final I_M_ReceiptSchedule rs)
	{
		final int bpLocationOverrideId = rs.getC_BP_Location_Override_ID();
		if (bpLocationOverrideId > 0)
		{
			return bpLocationOverrideId;
		}

		return rs.getC_BPartner_Location_ID();
	}

	@Override
	public I_C_BPartner_Location getC_BPartner_Location_Effective(final I_M_ReceiptSchedule sched)
	{
		return InterfaceWrapperHelper.load(
				sched.getC_BP_Location_Override_ID() <= 0 ? sched.getC_BPartner_Location_ID() : sched.getC_BP_Location_Override_ID(),
				I_C_BPartner_Location.class);
	}

	@Deprecated
	@Override
	public int getC_BPartner_Effective_ID(final I_M_ReceiptSchedule rs)
	{
		return BPartnerId.toRepoId(getBPartnerEffectiveId(rs));
	}

	@Override
	public BPartnerId getBPartnerEffectiveId(final I_M_ReceiptSchedule rs)
	{
		final BPartnerId bPartnerOverrideId = BPartnerId.ofRepoIdOrNull(rs.getC_BPartner_Override_ID());
		if (bPartnerOverrideId != null)
		{
			return bPartnerOverrideId;
		}

		return BPartnerId.ofRepoId(rs.getC_BPartner_ID());
	}

	@Override
	public I_C_BPartner getC_BPartner_Effective(final I_M_ReceiptSchedule rs)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

		final BPartnerId bpartnerId = getBPartnerEffectiveId(rs);
		return bpartnersRepo.getById(bpartnerId, I_C_BPartner.class);
	}

	@Nullable
	@Override
	public BPartnerContactId getBPartnerContactID(@NonNull final I_M_ReceiptSchedule rs)
	{
		final int cBPartnerIdRepo = rs.getC_BPartner_Override_ID() > 0 ? rs.getC_BPartner_Override_ID() : rs.getC_BPartner_ID();
		final int adUserIdRepo = rs.getAD_User_Override_ID() > 0 ? rs.getAD_User_Override_ID() : rs.getAD_User_ID();

		return BPartnerContactId.ofRepoIdOrNull(cBPartnerIdRepo, adUserIdRepo);
	}

	private IDocumentLocationAdapter asDocumentLocation(final I_M_ReceiptSchedule receiptSchedule)
	{
		return new ReceiptScheduleDocumentLocationAdapter(receiptSchedule);
	}

	private IDocumentLocationAdapter asDocumentLocationEffective(final I_M_ReceiptSchedule receiptSchedule)
	{
		return new ReceiptScheduleEffectiveDocumentLocation(receiptSchedule);
	}

	@Override
	public void updateBPartnerAddress(final I_M_ReceiptSchedule receiptSchedule)
	{
		final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

		final IDocumentLocationAdapter documentLocation = asDocumentLocation(receiptSchedule);
		documentLocationBL.updateRenderedAddressAndCapturedLocation(documentLocation);
	}

	@Override
	public void updateBPartnerAddressOverride(final I_M_ReceiptSchedule receiptSchedule)
	{
		final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

		final IDocumentLocationAdapter documentLocation = asDocumentLocationEffective(receiptSchedule);
		documentLocationBL.updateRenderedAddressAndCapturedLocation(documentLocation);
	}

	@Override
	public void generateInOuts(final Properties ctx, final Iterator<I_M_ReceiptSchedule> receiptSchedules, final InOutGenerateResult result, final boolean complete)
	{
		final IInOutProducer processor = createInOutProducer(result, complete);

		generateInOuts(ctx, processor, receiptSchedules);
	}

	@Override
	public void generateInOuts(final Properties ctx,
			@NonNull final IInOutProducer producer,
			@NonNull final Iterator<I_M_ReceiptSchedule> receiptSchedules)
	{
		Services.get(ITrxItemProcessorExecutorService.class).<I_M_ReceiptSchedule, InOutGenerateResult>createExecutor()
				.setContext(ctx)
				.setProcessor(producer)
				.setExceptionHandler(LoggableTrxItemExceptionHandler.instance)
				.process(receiptSchedules);
	}

	@Override
	public IInOutProducer createInOutProducer(final InOutGenerateResult resultInitial, final boolean complete)
	{
		return new InOutProducer(resultInitial, complete);
	}

	@Override
	public I_M_ReceiptSchedule_Alloc createRsaIfNotExists(
			final I_M_ReceiptSchedule receiptSchedule,
			final I_M_InOutLine receiptLine)

	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);
		Check.assume(Env.getAD_Client_ID(ctx) == receiptSchedule.getAD_Client_ID(), "AD_Client_ID of " + receiptSchedule + " and of its CTX are the same");

		final I_M_ReceiptSchedule_Alloc existingRsa = receiptScheduleDAO.retrieveRsaForRs(receiptSchedule, receiptLine);
		if (existingRsa != null)
		{
			return existingRsa;// nothing to do
		}

		final StockQtyAndUOMQty qtyToAllocate = Services.get(IInOutBL.class).getStockQtyAndCatchQty(receiptLine);

		return createReceiptScheduleAlloc(receiptSchedule, receiptLine, qtyToAllocate);
	}

	@Override
	public IReceiptScheduleAllocBuilder createReceiptScheduleAlloc()
	{
		return new ReceiptScheduleAllocBuilder();
	}

	/**
	 * @param qtyToAllocate quantity to allocate (in stock UOM)
	 * @return receipt schedule allocation; never return null
	 */
	private I_M_ReceiptSchedule_Alloc createReceiptScheduleAlloc(
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final I_M_InOutLine receiptLine,
			@NonNull final StockQtyAndUOMQty qtyToAllocate)
	{
		final IContextAware context = InterfaceWrapperHelper.getContextAware(receiptLine);
		// Determine QtyWithIssues based on receipt line's IsInDispute flag.
		final StockQtyAndUOMQty qtyWithIssues = receiptLine.isInDispute() ? qtyToAllocate : qtyToAllocate.toZero();

		return createReceiptScheduleAlloc()
				.setContext(context)
				.setM_ReceiptSchedule(receiptSchedule)
				.setM_InOutLine(receiptLine)
				.setQtyToAllocate(qtyToAllocate)
				.setQtyWithIssues(qtyWithIssues)
				.buildAndSave();
	}

	@Override
	public List<I_M_ReceiptSchedule_Alloc> createReceiptScheduleAllocations(
			final List<? extends I_M_ReceiptSchedule> receiptSchedules,
			@NonNull final I_M_InOutLine receiptLine)
	{
		Check.assumeNotEmpty(receiptSchedules, "receipt schedules not empty");

		StockQtyAndUOMQty qtyToAllocateRemaining = Services.get(IInOutBL.class).getStockQtyAndCatchQty(receiptLine);
		if (qtyToAllocateRemaining.signum() == 0)
		{
			// Receipt Line with ZERO qty???
			// could be, but we will skip the allocations because there is nothing to allocate
			return Collections.emptyList();
		}

		//
		// Iterate receipt schedules and try to allocate on them as much as possible
		final List<I_M_ReceiptSchedule_Alloc> allocs = new ArrayList<>();
		final List<Integer> orderLineIds = new ArrayList<>();
		I_M_ReceiptSchedule lastReceiptSchedule = null;
		for (final I_M_ReceiptSchedule rs : receiptSchedules)
		{
			// Do we still have something to allocate?
			if (qtyToAllocateRemaining.signum() == 0)
			{
				break;
			}

			//
			// Calculate how much we can allocate on current receipt schedule
			// i.e. try Remaining Qty To Allocate, but not more then how much is open on this receipt schedule
			final StockQtyAndUOMQty rsQtyOpen = getQtyToMove(rs); // how much we can maximum allocate on this receipt schedule
			final StockQtyAndUOMQty rsQtyToAllocate = qtyToAllocateRemaining.min(rsQtyOpen);
			if (rsQtyToAllocate.signum() == 0)
			{
				//
				// Remember our last receipt schedule
				// This will be needed in case of over delivery
				lastReceiptSchedule = rs;

				// nothing to allocate on this receipt schedule
				continue;
			}

			//
			// Create allocation
			final I_M_ReceiptSchedule_Alloc rsa = createReceiptScheduleAlloc(rs, receiptLine, rsQtyToAllocate);
			allocs.add(rsa);

			//
			// Update Remaining Qty To Allocate
			qtyToAllocateRemaining = qtyToAllocateRemaining.subtract(rsQtyToAllocate);

			//
			// Remember receipt schedule's order line
			final int orderLineId = rs.getC_OrderLine_ID();
			if (orderLineId > 0 && !orderLineIds.contains(orderLineId))
			{
				orderLineIds.add(orderLineId);
			}

			//
			// Remember our last receipt schedule
			lastReceiptSchedule = rs;
		}

		//
		// Case: after iterating all receipt schedules it seems we could not allocate everything
		// => we allocate remaining qty to last receipt scheduler, no matter if open qty is smaller (over delivery)
		if (qtyToAllocateRemaining.signum() != 0)
		{
			final I_M_ReceiptSchedule_Alloc rsa = createReceiptScheduleAlloc(lastReceiptSchedule, receiptLine, qtyToAllocateRemaining);
			allocs.add(rsa);
		}

		//
		// Update Receipt Line's link to Order Line
		// (only if there was one and only one order line involved)
		if (orderLineIds.size() == 1)
		{
			receiptLine.setC_OrderLine_ID(orderLineIds.get(0));
		}

		//
		// Return created allocations
		return allocs;
	}

	@Override
	public void updatePreparationTime(I_M_ReceiptSchedule receiptSchedule)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(receiptSchedule.getC_Order(), I_C_Order.class);
		if (order != null)
		{
			final de.metas.tourplanning.model.I_M_ReceiptSchedule rSched = InterfaceWrapperHelper.create(receiptSchedule, de.metas.tourplanning.model.I_M_ReceiptSchedule.class);
			rSched.setPreparationTime(order.getPreparationDate());
		}
	}

	@Override
	public I_M_ReceiptSchedule_Alloc reverseAllocation(final I_M_ReceiptSchedule_Alloc rsa)
	{
		Check.assumeNotNull(rsa, "rsa not null");

		final I_M_ReceiptSchedule_Alloc reversal = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule_Alloc.class, rsa);

		InterfaceWrapperHelper.copyValues(rsa, reversal);
		reversal.setM_ReceiptSchedule_ID(rsa.getM_ReceiptSchedule_ID());
		reversal.setAD_Org_ID(rsa.getAD_Org_ID());
		reversal.setIsActive(rsa.isActive());

		reversal.setQtyAllocated(rsa.getQtyAllocated().negate());

		return reversal;
	}

	@Override
	public void close(@NonNull final I_M_ReceiptSchedule rs)
	{
		// Make sure receipt schedule was not already processed
		if (isClosed(rs))
		{
			throw new AdempiereException("@Closed@=@Y@ (" + rs + ")");
		}

		listeners.onBeforeClose(rs);

		// Mark the receipt schedule as closed (i.e. processed)
		rs.setProcessed(true);
		InterfaceWrapperHelper.save(rs);

		// this is already called by a model validator when the receipt schedule is saved
		// Services.get(IReceiptScheduleQtysBL.class).onReceiptScheduleChanged(receiptSchedule);

		listeners.onAfterClose(rs);
		InterfaceWrapperHelper.save(rs); // see javadoc on why we same two times
	}

	@Override
	public void reopen(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		//
		// Make sure receipt schedule is closed/processed
		if (!isClosed(receiptSchedule))
		{
			throw new AdempiereException("@Closed@=@N@ (" + receiptSchedule + ")");
		}

		listeners.onBeforeReopen(receiptSchedule);
		InterfaceWrapperHelper.refresh(receiptSchedule); // because

		// Mark the receipt schedule as not closed (i.e. not processed)
		receiptSchedule.setProcessed(false);
		InterfaceWrapperHelper.save(receiptSchedule);

		// this is already called by a model validator when the receipt schedule is saved
		// Services.get(IReceiptScheduleQtysBL.class).onReceiptScheduleChanged(receiptSchedule);

		listeners.onAfterReopen(receiptSchedule);
		InterfaceWrapperHelper.save(receiptSchedule);
	}

	@Override
	public boolean isClosed(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		return receiptSchedule.isProcessed();
	}

	public void applyReceiptScheduleChanges(@NonNull final ApplyReceiptScheduleChangesRequest applyReceiptScheduleChangesRequest)
	{
		final I_M_ReceiptSchedule receiptSchedule = receiptScheduleDAO.getById(applyReceiptScheduleChangesRequest.getReceiptScheduleId());

		if (applyReceiptScheduleChangesRequest.getQtyInStockingUOM() != null)
		{
			receiptSchedule.setQtyToMove_Override(applyReceiptScheduleChangesRequest.getQtyInStockingUOM());
		}

		if (applyReceiptScheduleChangesRequest.hasAttributes())
		{
			final AttributeSetInstanceId existingAttributeSetIdOrNone = AttributeSetInstanceId.ofRepoIdOrNone(receiptSchedule.getM_AttributeSetInstance_ID());

			final AddAttributesRequest addAttributesRequest = AddAttributesRequest.builder()
					.productId(ProductId.ofRepoId(receiptSchedule.getM_Product_ID()))
					.existingAttributeSetIdOrNone(existingAttributeSetIdOrNone)
					.attributeInstanceBasicInfos(applyReceiptScheduleChangesRequest.getNonNullAttributeReqList())
					.build();

			final AttributeSetInstanceId newAttributeSetInstanceId = attributeSetInstanceBL.addAttributes(addAttributesRequest);

			receiptSchedule.setM_AttributeSetInstance_ID(newAttributeSetInstanceId.getRepoId());
		}

		if (Check.isNotBlank(applyReceiptScheduleChangesRequest.getExternalResourceURL()))
		{
			receiptSchedule.setExternalResourceURL(applyReceiptScheduleChangesRequest.getExternalResourceURL());
		}

		InterfaceWrapperHelper.saveRecord(receiptSchedule);
	}

	@Override
	public void updateExportStatus(@NonNull final APIExportStatus newExportStatus, @NonNull final PInstanceId pinstanceId)
	{
		final AtomicInteger allCounter = new AtomicInteger(0);
		final AtomicInteger updatedCounter = new AtomicInteger(0);

		queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.setOnlySelection(pinstanceId)
				.create()
				.iterateAndStream()
				.forEach(record ->
						 {
							 allCounter.incrementAndGet();
							 if (Objects.equals(record.getExportStatus(), newExportStatus.getCode()))
							 {
								 return;
							 }
							 record.setExportStatus(newExportStatus.getCode());
							 updateCanBeExportedFrom(record);
							 InterfaceWrapperHelper.saveRecord(record);

							 updatedCounter.incrementAndGet();
						 });

		Loggables.withLogger(logger, Level.INFO).addLog("Updated {} out of {} M_ReceiptSchedules", updatedCounter.get(), allCounter.get());
	}

	@Override
	public void updateCanBeExportedFrom(@NonNull final I_M_ReceiptSchedule sched)
	{
		// we see "not-yet-set" as equivalent to "pending"
		final APIExportStatus exportStatus = APIExportStatus.ofNullableCode(sched.getExportStatus(), APIExportStatus.Pending);
		if (!Objects.equals(exportStatus, APIExportStatus.Pending))
		{
			sched.setCanBeExportedFrom(Env.MAX_DATE);
			logger.debug("exportStatus={}; -> set CanBeExportedFrom={}", sched.getExportStatus(), Env.MAX_DATE);
			return;
		}

		final int canBeExportedAfterSeconds = sysConfigBL.getIntValue(
				SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS,
				sched.getAD_Client_ID(),
				sched.getAD_Org_ID());
		if (canBeExportedAfterSeconds >= 0)
		{
			final Instant instant = Instant.now().plusSeconds(canBeExportedAfterSeconds);
			sched.setCanBeExportedFrom(TimeUtil.asTimestamp(instant));
			logger.debug("canBeExportedAfterSeconds={}; -> set CanBeExportedFrom={}", canBeExportedAfterSeconds, sched.getCanBeExportedFrom());
		}
	}

	private static class ReceiptScheduleDocumentLocationAdapter implements IDocumentLocationAdapter
	{
		private final I_M_ReceiptSchedule delegate;

		private ReceiptScheduleDocumentLocationAdapter(@NonNull final I_M_ReceiptSchedule delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public int getC_BPartner_ID()
		{
			return delegate.getC_BPartner_ID();
		}

		@Override
		public void setC_BPartner_ID(final int C_BPartner_ID)
		{
			delegate.setC_BPartner_ID(C_BPartner_ID);
		}

		@Override
		public int getC_BPartner_Location_ID()
		{
			return delegate.getC_BPartner_Location_ID();
		}

		@Override
		public void setC_BPartner_Location_ID(final int C_BPartner_Location_ID)
		{
			delegate.setC_BPartner_Location_ID(C_BPartner_Location_ID);
		}

		@Override
		public int getC_BPartner_Location_Value_ID()
		{
			return -1;
		}

		@Override
		public void setC_BPartner_Location_Value_ID(final int C_BPartner_Location_Value_ID)
		{
		}

		@Override
		public int getAD_User_ID()
		{
			return delegate.getAD_User_ID();
		}

		@Override
		public void setAD_User_ID(final int AD_User_ID)
		{
			delegate.setAD_User_ID(AD_User_ID);
		}

		@Override
		public String getBPartnerAddress()
		{
			return delegate.getBPartnerAddress();
		}

		@Override
		public void setBPartnerAddress(final String address)
		{
			delegate.setBPartnerAddress(address);
		}

	}

	@ToString
	private class ReceiptScheduleEffectiveDocumentLocation implements IDocumentLocationAdapter
	{
		private final I_M_ReceiptSchedule delegate;

		private ReceiptScheduleEffectiveDocumentLocation(@NonNull final I_M_ReceiptSchedule delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public int getC_BPartner_ID()
		{
			return BPartnerId.toRepoId(getBPartnerEffectiveId(delegate));
		}

		@Override
		public void setC_BPartner_ID(final int C_BPartner_ID)
		{
		}

		@Override
		public int getC_BPartner_Location_ID()
		{
			return getC_BPartner_Location_Effective_ID(delegate);
		}

		@Override
		@Deprecated
		public void setC_BPartner_Location_ID(final int C_BPartner_Location_ID)
		{
		}

		@Override
		public int getC_BPartner_Location_Value_ID()
		{
			return -1;
		}

		@Override
		public void setC_BPartner_Location_Value_ID(final int C_BPartner_Location_Value_ID)
		{
		}

		@Override
		public int getAD_User_ID()
		{
			return BPartnerContactId.toRepoId(getBPartnerContactID(delegate));
		}

		@Override
		public void setAD_User_ID(final int AD_User_ID)
		{
		}

		@Override
		public String getBPartnerAddress()
		{
			return delegate.getBPartnerAddress_Override();
		}

		@Override
		public void setBPartnerAddress(final String address)
		{
			delegate.setBPartnerAddress_Override(address);
		}
	}

	@Override
	public DeliveryPlanningCreateRequest createDeliveryPlanningRequest(@NonNull final I_M_ReceiptSchedule receiptScheduleRecord)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(receiptScheduleRecord.getC_Order_ID());
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(receiptScheduleRecord.getC_OrderLine_ID());

		final I_C_UOM uom = uomDAO.getById(receiptScheduleRecord.getC_UOM_ID());

		final Quantity qtyOrdered = Quantity.of(receiptScheduleQtysBL.getQtyOrdered(receiptScheduleRecord), uom);

		final Quantity qtyMoved = Quantity.of(receiptScheduleQtysBL.getQtyMoved(receiptScheduleRecord), uom);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(receiptScheduleRecord.getM_AttributeSetInstance_ID());

		final CountryId originCountryId = getOriginCountryId(asiId);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(receiptScheduleRecord.getM_Warehouse_ID());
		final BPartnerLocationId destinationBPLocationId = warehouseBL.getBPartnerLocationId(warehouseId);
		final CountryId destinationCountryId = warehouseBL.getCountryId(warehouseId);

		final String huBatchNo = attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.HU_BatchNo, asiId);

		final DeliveryPlanningCreateRequest.DeliveryPlanningCreateRequestBuilder requestBuilder = DeliveryPlanningCreateRequest.builder()
				.orgId(OrgId.ofRepoId(receiptScheduleRecord.getAD_Org_ID()))
				.clientId(ClientId.ofRepoId(receiptScheduleRecord.getAD_Client_ID()))
				.receiptScheduleId(ReceiptScheduleId.ofRepoId(receiptScheduleRecord.getM_ReceiptSchedule_ID()))
				.deliveryPlanningType(DeliveryPlanningType.Incoming)
				.orderId(orderId)
				.orderLineId(orderLineId)
				.warehouseId(warehouseId)
				.productId(ProductId.ofRepoId(receiptScheduleRecord.getM_Product_ID()))
				.partnerId(BPartnerId.ofRepoId(receiptScheduleRecord.getC_BPartner_ID()))
				.bPartnerLocationId(destinationBPLocationId)
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(receiptScheduleRecord.getM_SectionCode_ID()))
				.qtyOrdered(qtyOrdered)
				.qtyTotalOpen(qtyOrdered.subtract(qtyMoved))
				.actualLoadedQty(Quantity.zero(uom))
				.actualDeliveredQty(Quantity.zero(uom))
				.plannedLoadedQty(Quantity.zero(uom))
				.plannedDischargeQty(Quantity.zero(uom))
				.actualDischargeQty(Quantity.zero(uom))
				.uom(uom)
				.plannedDeliveryDate(TimeUtil.asInstant(receiptScheduleRecord.getMovementDate()))
				.batch(huBatchNo)
				.originCountryId(originCountryId)
				.destinationCountryId(destinationCountryId);

		if (orderId != null)
		{
			final I_C_Order order = orderDAO.getById(orderId);

			requestBuilder.isB2B(order.isDropShip())
					.incotermsId(IncotermsId.ofRepoIdOrNull(order.getC_Incoterms_ID()))
					.incotermLocation(order.getIncotermLocation());
		}

		if (orderLineId != null)
		{
			final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);

			requestBuilder.actualDeliveryDate(TimeUtil.asInstant(orderLine.getDateDelivered()));
			requestBuilder.shipperId(ShipperId.ofRepoIdOrNull(orderLine.getM_Shipper_ID()));
		}

		return requestBuilder.build();
	}

	@Nullable
	private CountryId getOriginCountryId(final AttributeSetInstanceId asiId)
	{
		final String originCountryCode = attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.CountryOfOrigin, asiId);
		final CountryId originCountryId = originCountryCode == null ? null : countryDAO.getCountryIdByCountryCode(originCountryCode);
		return originCountryId;
	}
}
