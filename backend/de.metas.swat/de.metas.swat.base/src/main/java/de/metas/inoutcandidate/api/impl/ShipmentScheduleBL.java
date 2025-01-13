package de.metas.inoutcandidate.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.deliveryplanning.DeliveryPlanningCreateRequest;
import de.metas.deliveryplanning.DeliveryPlanningType;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.freighcost.FreightCostRule;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ApplyShipmentScheduleChangesRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequest;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequestsList;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.location.ShipmentScheduleLocationsUpdater;
import de.metas.inoutcandidate.location.adapter.ShipmentScheduleDocumentLocationAdapterFactory;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.lock.api.ILockManager;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shipping.ShipperId;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AddAttributesRequest;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.adempiere.model.InterfaceWrapperHelper.createOld;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.save;
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
 * This service computes the quantities to be shipped to customers for a list of {@link I_C_OrderLine}s and their respective {@link I_M_ShipmentSchedule}s.
 *
 * @see IShipmentSchedulePA
 * @see OlAndSched
 */
@Service
public class ShipmentScheduleBL implements IShipmentScheduleBL
{
	private static final AdMessageKey MSG_SHIPMENT_SCHEDULE_ALREADY_PROCESSED = AdMessageKey.of("ShipmentScheduleAlreadyProcessed");

	private static final String SYS_Config_M_ShipmentSchedule_Close_PartiallyShipped = "M_ShipmentSchedule_Close_PartiallyShipped";

	private static final String SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS = "de.metas.inoutcandidate.M_ShipmentSchedule.canBeExportedAfterSeconds";
	private static final String SYSCONFIG_CAN_BE_REEXPORTED_IF_QTYTODELIVER_IS_INCREASED = "de.metas.inoutcandidate.M_ShipmentSchedule.canBeExportedIfQtyToDeliverIsIncreased";

	private static final ModelDynAttributeAccessor<I_M_ShipmentSchedule, Boolean> DYNATTR_DoNotInvalidatedOnChange = new ModelDynAttributeAccessor<>("NotInvalidatedOnchange", Boolean.class);

	// services
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final ThreadLocal<Boolean> postponeMissingSchedsCreationUntilClose = ThreadLocal.withInitial(() -> false);

	@Override
	public boolean allMissingSchedsWillBeCreatedLater()
	{
		return postponeMissingSchedsCreationUntilClose.get();
	}

	@Override
	public IAutoCloseable postponeMissingSchedsCreationUntilClose()
	{
		if (allMissingSchedsWillBeCreatedLater())
		{
			return NullAutoCloseable.instance; // we were already called;
		}

		postponeMissingSchedsCreationUntilClose.set(true);

		final IAutoCloseable onCloseCreateMissingScheds = //
				() -> {
					postponeMissingSchedsCreationUntilClose.set(false);
					CreateMissingShipmentSchedulesWorkpackageProcessor.scheduleIfNotPostponed(PlainContextAware.newWithThreadInheritedTrx());
				};

		return onCloseCreateMissingScheds;
	}

	@Override
	public void updateHeaderAggregationKey(@NonNull final I_M_ShipmentSchedule sched)
	{
		final ShipmentScheduleHeaderAggregationKeyBuilder shipmentScheduleKeyBuilder = mkShipmentHeaderAggregationKeyBuilder();
		final String headerAggregationKey = shipmentScheduleKeyBuilder.buildKey(sched);
		sched.setHeaderAggregationKey(headerAggregationKey);
	}

	@Override
	public void updateCapturedLocationsAndRenderedAddresses(final I_M_ShipmentSchedule sched)
	{
		// NOTE: atm we are getting it just in time instead of wiring it at construction time,
		// because if not we have to adapt >300 tests which would fail
		final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

		ShipmentScheduleLocationsUpdater.builder()
				.documentLocationBL(documentLocationBL)
				.record(sched)
				.build()
				.updateAllIfNeeded();
	}

	@Override
	public void updateQtyOrdered(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final BigDecimal newQtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(shipmentSchedule);
		shipmentSchedule.setQtyOrdered(newQtyOrdered);
	}

	@Override
	public I_C_UOM getUomOfProduct(@NonNull final I_M_ShipmentSchedule sched)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		return productBL.getStockUOM(sched.getM_Product_ID());
	}

	@Override
	public UomId getUomIdOfProduct(@NonNull final I_M_ShipmentSchedule sched)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		return productBL.getStockUOMId(sched.getM_Product_ID());
	}

	@Override
	public boolean isSchedAllowsConsolidate(final I_M_ShipmentSchedule sched)
	{
		// task 08756: we don't really care for the ol's partner, but for the partner who will actually receive the shipment.
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

		final boolean bpAllowsConsolidate = bPartnerBL.isAllowConsolidateInOutEffective(shipmentScheduleEffectiveBL.getBPartner(sched), SOTrx.SALES);
		if (!bpAllowsConsolidate)
		{
			logger.debug("According to the effective C_BPartner of shipment candidate '" + sched + "', consolidation into one shipment is not allowed");
			return false;
		}

		return !isConsolidateVetoedByOrderOfSched(sched);
	}

	@VisibleForTesting
	boolean isConsolidateVetoedByOrderOfSched(final I_M_ShipmentSchedule sched)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(sched.getC_Order_ID());
		if (orderId == null)
		{
			return false;
		}

		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final I_C_Order order = orderBL.getById(orderId);
		if (orderBL.isPrepay(order))
		{
			logger.debug("Because '{}' is a prepay order, consolidation into one shipment is not allowed", order);
			return true;
		}

		final boolean isCustomFreightCostRule = isCustomFreightCostRule(order);
		if (isCustomFreightCostRule)
		{
			logger.debug("Because '{}' has not the standard freight cost rule,  consolidation into one shipment is not allowed", order);
			return true;
		}

		return false;
	}

	private boolean isCustomFreightCostRule(final I_C_Order order)
	{
		final FreightCostRule freightCostRule = FreightCostRule.ofCode(order.getFreightCostRule());
		return FreightCostRule.FixPrice.equals(freightCostRule)
				// || FreightCostRule.FreightIncluded.equals(freightCostRule) // 07973: included freight cost rule shall no longer be considered "custom"
				;
	}

	@Override
	public ShipmentScheduleHeaderAggregationKeyBuilder mkShipmentHeaderAggregationKeyBuilder()
	{
		return new ShipmentScheduleHeaderAggregationKeyBuilder();
	}

	@Override
	public void closeShipmentSchedule(@NonNull final I_M_ShipmentSchedule scheduleRecord)
	{
		scheduleRecord.setIsClosed(true);
		save(scheduleRecord);
	}

	@Override
	public void closeShipmentSchedule(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulePA.getById(shipmentScheduleId);

		closeShipmentSchedule(shipmentSchedule);
	}

	@Override
	public void closeShipmentSchedules(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}

		final Collection<I_M_ShipmentSchedule> shipmentSchedules = shipmentSchedulePA.getByIds(shipmentScheduleIds).values();
		shipmentSchedules.forEach(this::closeShipmentSchedule);
	}

	@Override
	public void openShipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		Check.errorUnless(shipmentScheduleRecord.isClosed(), "The given shipmentSchedule is not closed; shipmentSchedule={}", shipmentScheduleRecord);

		shipmentScheduleRecord.setIsClosed(false);

		Services.get(IShipmentScheduleHandlerBL.class).updateShipmentScheduleFromReferencedRecord(shipmentScheduleRecord);
		updateQtyOrdered(shipmentScheduleRecord);

		save(shipmentScheduleRecord);
	}

	@Override
	public boolean isJustOpened(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final boolean isClosed = shipmentScheduleRecord.isClosed();
		if (isClosed)
		{
			return false;
		}

		final boolean wasClosed = createOld(shipmentScheduleRecord, I_M_ShipmentSchedule.class).isClosed();
		if (!wasClosed)
		{
			return false;
		}

		return true; // was closed, but is now open
	}

	@Override
	public IStorageQuery createStorageQuery(
			@NonNull final I_M_ShipmentSchedule sched,
			final boolean considerAttributes,
			final boolean excludeAllReserved)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

		// Create storage query
		final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(sched);

		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		final Set<WarehouseId> warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(warehouseId);

		final IStorageEngineService storageEngineProvider = Services.get(IStorageEngineService.class);
		final IStorageEngine storageEngine = storageEngineProvider.getStorageEngine();

		final IStorageQuery storageQuery = storageEngine
				.newStorageQuery()
				.addBPartnerId(null)
				.addBPartnerId(bpartnerId)
				.addWarehouseIds(warehouseIds)
				.addProductId(ProductId.ofRepoId(sched.getM_Product_ID()));

		// Add query attributes
		if (considerAttributes)
		{
			final I_M_AttributeSetInstance asi = sched.getM_AttributeSetInstance_ID() > 0 ? sched.getM_AttributeSetInstance() : null;
			if (asi != null && asi.getM_AttributeSetInstance_ID() > 0)
			{
				final IAttributeSet attributeSet = storageEngine.getAttributeSet(asi);
				storageQuery.addAttributes(attributeSet);
			}
		}

		if (sched.getC_OrderLine_ID() <= 0 || excludeAllReserved)
		{
			storageQuery.setExcludeReserved();
		}
		else
		{
			storageQuery.setExcludeReservedToOtherThan(OrderLineId.ofRepoId(sched.getC_OrderLine_ID()));
		}
		return storageQuery;
	}

	@Override
	public Quantity getQtyToDeliver(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final BigDecimal qtyToDeliverBD = shipmentScheduleEffectiveBL.getQtyToDeliverBD(shipmentScheduleRecord);
		final I_C_UOM uom = getUomOfProduct(shipmentScheduleRecord);
		return Quantity.of(qtyToDeliverBD, uom);
	}

	@Override
	public Optional<Quantity> getCatchQtyOverride(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final boolean hasCatchOverrideQty = !isNull(shipmentScheduleRecord, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliverCatch_Override);
		final boolean hasCatchUOM = shipmentScheduleRecord.getCatch_UOM_ID() > 0;

		if (!hasCatchUOM)
		{
			return Optional.empty();
		}
		if (!hasCatchOverrideQty)
		{
			return Optional.empty();
		}

		final Quantity result = Quantitys.create(
				shipmentScheduleRecord.getQtyToDeliverCatch_Override(),
				UomId.ofRepoId(shipmentScheduleRecord.getCatch_UOM_ID()));
		return Optional.of(result);
	}

	@Override
	public void resetCatchQtyOverride(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		shipmentScheduleRecord.setQtyToDeliverCatch_Override(null);
		saveRecord(shipmentScheduleRecord);
	}

	@Override
	public void updateCatchUoms(@NonNull final ProductId productId, long delayMs)
	{
		if (delayMs < 0)
		{
			return; // doing nothing
		}

		// lambda doesn't work; see https://stackoverflow.com/questions/37970682/passing-lambda-to-a-timer-instead-of-timertask
		final TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				updateCatchUoms(productId);
			}
		};

		if (delayMs <= 0)
		{
			task.run(); // run directly
			return;
		}

		logger.info("Going to update shipment schedules for M_Product_ID={} in {}ms", productId.getRepoId(), delayMs);

		final String timerName = ShipmentScheduleBL.class.getSimpleName() + "-updateCatchUoms-productId=" + productId.getRepoId();
		new Timer(timerName).schedule(task, delayMs);
	}

	private void updateCatchUoms(@NonNull final ProductId productId)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final Integer catchUomRepoId = Services.get(IProductBL.class)
				.getCatchUOMId(productId)
				.map(UomId::getRepoId)
				.orElse(null);

		final ICompositeQueryUpdater<I_M_ShipmentSchedule> queryUpdater = queryBL
				.createCompositeQueryUpdater(I_M_ShipmentSchedule.class)
				.addSetColumnValue(I_M_ShipmentSchedule.COLUMNNAME_Catch_UOM_ID, catchUomRepoId);

		final ILockManager lockManager = Services.get(ILockManager.class);

		final int count = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID, productId)
				.addNotEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Catch_UOM_ID, catchUomRepoId)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMN_Processed, false)
				.filter(lockManager.getNotLockedFilter(I_M_ShipmentSchedule.class))
				.create()
				.update(queryUpdater);

		final long durationSecs = stopwatch.stop().elapsed(TimeUnit.SECONDS);
		logger.info("Updated {} shipment schedules for M_Product_ID={} in {}seconds", count, productId.getRepoId(), durationSecs);
	}

	@Override
	public I_M_ShipmentSchedule getById(@NonNull final ShipmentScheduleId id)
	{
		return shipmentSchedulePA.getById(id);
	}

	@Override
	public Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIds(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return shipmentSchedulePA.getByIds(ids);
	}

	@Override
	public Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIdsOutOfTrx(final Set<ShipmentScheduleId> ids)
	{
		return shipmentSchedulePA.getByIdsOutOfTrx(ids);
	}

	@Override
	public <T extends I_M_ShipmentSchedule> Map<ShipmentScheduleId, T> getByIdsOutOfTrx(
			@NonNull final Set<ShipmentScheduleId> ids,
			@NonNull final Class<T> modelType)
	{
		return shipmentSchedulePA.getByIdsOutOfTrx(ids, modelType);
	}

	@Override
	public BPartnerId getBPartnerId(@NonNull final I_M_ShipmentSchedule schedule)
	{
		return shipmentScheduleEffectiveBL.getBPartnerId(schedule);
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId(@NonNull final I_M_ShipmentSchedule schedule)
	{
		return shipmentScheduleEffectiveBL.getBPartnerLocationId(schedule);
	}

	@Override
	public WarehouseId getWarehouseId(@NonNull final I_M_ShipmentSchedule schedule)
	{
		return shipmentScheduleEffectiveBL.getWarehouseId(schedule);
	}

	@Override
	public ZonedDateTime getPreparationDate(I_M_ShipmentSchedule schedule)
	{
		return shipmentScheduleEffectiveBL.getPreparationDate(schedule);
	}

	@Override
	public ShipmentAllocationBestBeforePolicy getBestBeforePolicy(@NonNull final ShipmentScheduleId id)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getById(id);
		final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy = ShipmentAllocationBestBeforePolicy.optionalOfNullableCode(shipmentSchedule.getShipmentAllocation_BestBefore_Policy());
		if (bestBeforePolicy.isPresent())
		{
			return bestBeforePolicy.get();
		}

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final BPartnerId bpartnerId = getBPartnerId(shipmentSchedule);
		return bpartnerBL.getBestBeforePolicy(bpartnerId);
	}

	@Override
	public void applyUserChangesInTrx(@NonNull final ShipmentScheduleUserChangeRequestsList userChanges)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInThreadInheritedTrx(() -> applyUserChangesInTrx0(userChanges));
	}

	private void applyUserChangesInTrx0(@NonNull ShipmentScheduleUserChangeRequestsList userChanges)
	{
		final Set<ShipmentScheduleId> shipmentScheduleIds = userChanges.getShipmentScheduleIds();
		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> recordsById = shipmentSchedulePA.getByIds(shipmentScheduleIds);

		for (final ShipmentScheduleId shipmentScheduleId : shipmentScheduleIds)
		{
			try (final MDCCloseable shipmentScheduleMDC = TableRecordMDC.putTableRecordReference(I_M_ShipmentSchedule.Table_Name, shipmentScheduleId))
			{

				final ShipmentScheduleUserChangeRequest userChange = userChanges.getByShipmentScheduleId(shipmentScheduleId);
				final I_M_ShipmentSchedule record = recordsById.get(shipmentScheduleId);
				if (record == null)
				{
					// shall not happen
					logger.warn("No record found for {}. Skip applying user changes: {}", shipmentScheduleId, userChange);
					continue;
				}
				if (record.isProcessed())
				{
					// shall not happen
					logger.warn("Record already processed {}. Skip applying user changes: {}", shipmentScheduleId, userChange);
					continue;
				}

				updateRecord(record, userChange);
				shipmentSchedulePA.save(record);
			}
		}
	}

	private void updateRecord(
			@NonNull final I_M_ShipmentSchedule record,
			@NonNull final ShipmentScheduleUserChangeRequest from)
	{
		if (from.getQtyToDeliverStockOverride() != null)
		{
			record.setQtyToDeliver_Override(from.getQtyToDeliverStockOverride());
		}

		if (from.getQtyToDeliverCatchOverride() != null)
		{
			record.setQtyToDeliverCatch_Override(from.getQtyToDeliverCatchOverride());
		}

		if (from.getAsiId() != null)
		{
			record.setM_AttributeSetInstance_ID(from.getAsiId().getRepoId());
		}

		if (from.getBestBeforeDate() != null)
		{
			final CreateAttributeInstanceReq attributeInstanceBasicInfo = CreateAttributeInstanceReq
					.builder()
					.attributeCode(AttributeConstants.ATTR_BestBeforeDate)
					.value(from.getBestBeforeDate())
					.build();

			addAttributes(record, ImmutableList.of(attributeInstanceBasicInfo));
		}
	}

	@Override
	public void closeShipmentSchedulesFor(@NonNull final ImmutableList<TableRecordReference> recordRefs)
	{
		final ImmutableList<I_M_ShipmentSchedule> records = shipmentSchedulePA.getByReferences(recordRefs);
		for (final I_M_ShipmentSchedule record : records)
		{
			if (record.isClosed())
			{
				continue;
			}
			if (record.isProcessed())
			{
				throw new AdempiereException(
						Services.get(IMsgBL.class)
								.getTranslatableMsgText(MSG_SHIPMENT_SCHEDULE_ALREADY_PROCESSED, record.getM_ShipmentSchedule_ID()))
						.markAsUserValidationError();
			}
			closeShipmentSchedule(record);
		}
	}

	@Override
	public void openShipmentSchedulesFor(@NonNull final ImmutableList<TableRecordReference> recordRefs)
	{
		final ImmutableList<I_M_ShipmentSchedule> records = shipmentSchedulePA.getByReferences(recordRefs);
		for (final I_M_ShipmentSchedule record : records)
		{
			if (record.isClosed())
			{
				openShipmentSchedule(record);
			}
		}
	}

	@Override
	public IAttributeSetInstanceAware toAttributeSetInstanceAware(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return InterfaceWrapperHelper.create(shipmentSchedule, IAttributeSetInstanceAware.class);
	}

	@Override
	public void closePartiallyShipped_ShipmentSchedules(@NonNull final I_M_InOut inoutRecord)
	{
		if (!inoutRecord.isSOTrx())
		{
			logger.debug("M_InOut.isSOTrx=false; => not closing any shipment schedules");
			return;
		}

		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		if (inOutBL.isReversal(inoutRecord))
		{
			logger.debug("M_InOut is a reversal; => not closing any shipment schedules");
			return;
		}
		if (!isCloseIfPartiallyShipped(OrgId.ofRepoId(inoutRecord.getAD_Org_ID())))
		{
			logger.debug("isCloseIfPartiallyShipped=false for AD_Org_ID={}; => not closing any shipment schedules\"", inoutRecord.getAD_Org_ID());
			return;
		}

		for (final I_M_InOutLine iolrecord : inOutDAO.retrieveLines(inoutRecord))
		{
			try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(iolrecord))
			{
				for (final I_M_ShipmentSchedule shipmentScheduleRecord : shipmentSchedulePA.retrieveForInOutLine(iolrecord))
				{
					try (final MDCCloseable ignored1 = TableRecordMDC.putTableRecordReference(shipmentScheduleRecord))
					{
						if (iolrecord.getMovementQty().compareTo(shipmentScheduleRecord.getQtyOrdered()) < 0)
						{
							logger.debug("inoutLine.MovementQty={} is < shipmentSchedule.qtyOrdered={}; -> closing shipment schedule",
										 iolrecord.getMovementQty(), shipmentScheduleRecord.getQtyOrdered());
							closeShipmentSchedule(shipmentScheduleRecord);
						}
						else
						{
							logger.debug("inoutLine.MovementQty={} is >= shipmentSchedule.qtyOrdered={}; -> not closing shipment schedule",
										 iolrecord.getMovementQty(), shipmentScheduleRecord.getQtyOrdered());
						}
					}
				}
			}
		}
	}

	public void applyShipmentScheduleChanges(@NonNull final ApplyShipmentScheduleChangesRequest request)
	{
		if (request.isEmptyRequest())
		{
			return; //nothing to do
		}

		final I_M_ShipmentSchedule shipmentSchedule = getById(request.getShipmentScheduleId());

		if (request.getBPartnerLocationIdOverride() != null)
		{
			ShipmentScheduleDocumentLocationAdapterFactory
					.overrideLocationAdapter(shipmentSchedule)
					.setFrom(DocumentLocation.ofBPartnerLocationId(request.getBPartnerLocationIdOverride()));
		}

		if (request.getQtyToDeliverStockingUOM() != null)
		{
			shipmentSchedule.setQtyToDeliver_Override(request.getQtyToDeliverStockingUOM());
		}

		if (request.getDeliveryDate() != null)
		{
			shipmentSchedule.setDeliveryDate_Override(TimeUtil.asTimestamp(request.getDeliveryDate()));
		}

		if (request.getDeliveryRule() != null)
		{
			shipmentSchedule.setDeliveryRule_Override(request.getDeliveryRule().getCode());
		}

		if (!Check.isEmpty(request.getAttributes()))
		{
			addAttributes(shipmentSchedule, request.getAttributes());
		}

		if (request.getShipperId() != null)
		{
			shipmentSchedule.setM_Shipper_ID(request.getShipperId().getRepoId());
		}

		if (request.isDoNotInvalidateOnChange())
		{
			try (final IAutoCloseable ignored = doNotInvalidateOnChange(shipmentSchedule))
			{
				saveRecord(shipmentSchedule);
			}
		}
		else
		{
			saveRecord(shipmentSchedule);
		}
	}

	private boolean isCloseIfPartiallyShipped(@NonNull final OrgId orgId)
	{
		final boolean isCloseIfPartiallyInvoiced =
				sysConfigBL.getBooleanValue(SYS_Config_M_ShipmentSchedule_Close_PartiallyShipped, false, ClientId.METASFRESH.getRepoId(), orgId.getRepoId());

		return isCloseIfPartiallyInvoiced;
	}

	private IAutoCloseable doNotInvalidateOnChange(@NonNull final I_M_ShipmentSchedule sched)
	{
		DYNATTR_DoNotInvalidatedOnChange.setValue(sched, true);
		return () -> DYNATTR_DoNotInvalidatedOnChange.reset(sched);
	}

	@Override
	public boolean isDoNotInvalidateOnChange(@NonNull final I_M_ShipmentSchedule sched)
	{
		return DYNATTR_DoNotInvalidatedOnChange.getValue(sched, false);
	}

	@Override
	public void updateCanBeExportedAfter(@NonNull final I_M_ShipmentSchedule schedRecord)
	{
		// we see "not-yet-set" as equivalent to "pending"
		final APIExportStatus exportStatus = APIExportStatus.ofNullableCode(schedRecord.getExportStatus(), APIExportStatus.Pending);
		if (!Objects.equals(exportStatus, APIExportStatus.Pending))
		{
			logger.debug("exportStatus={}; -> set CanBeExportedFrom={}", schedRecord.getExportStatus(), Env.MAX_DATE);
			schedRecord.setCanBeExportedFrom(Env.MAX_DATE);
			return;
		}

		final int canBeExportedAfterSeconds = sysConfigBL.getIntValue(
				SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS,
				0, // default
				schedRecord.getAD_Client_ID(),
				schedRecord.getAD_Org_ID());
		if (canBeExportedAfterSeconds >= 0)
		{
			final Instant instant = SystemTime.asInstant().plusSeconds(canBeExportedAfterSeconds);
			schedRecord.setCanBeExportedFrom(TimeUtil.asTimestamp(instant));
			logger.debug("canBeExportedAfterSeconds={}; -> set CanBeExportedFrom={}", canBeExportedAfterSeconds, schedRecord.getCanBeExportedFrom());
		}
	}

	@Override
	public void updateExportStatus(final @NonNull I_M_ShipmentSchedule schedRecord)
	{
		final boolean canBeSetBackToPending = sysConfigBL.getBooleanValue(SYSCONFIG_CAN_BE_REEXPORTED_IF_QTYTODELIVER_IS_INCREASED, false, schedRecord.getAD_Client_ID(), schedRecord.getAD_Org_ID());
		if (!canBeSetBackToPending)
		{
			return;
		}

		final APIExportStatus currentExportStatus = APIExportStatus.ofNullableCode(schedRecord.getExportStatus());

		// if it's not "DontExport" and not "Pending" already, we *might* set them back to "Pending".
		final boolean maybeSetBackToPending = !Objects.equals(currentExportStatus, APIExportStatus.Pending)
				&& !Objects.equals(currentExportStatus, APIExportStatus.DontExport);
		if (maybeSetBackToPending)
		{
			final I_M_ShipmentSchedule oldSchedRecord = createOld(schedRecord, I_M_ShipmentSchedule.class);
			final boolean qtyToDeliverWasIncreased = oldSchedRecord.getQtyToDeliver().compareTo(schedRecord.getQtyToDeliver()) < 0;
			if (qtyToDeliverWasIncreased)
			{
				logger.debug("currentExportStatus={} and qtyToDeliverWasIncreased from {} to {}; -> set export status to {}",
							 APIExportStatus.toCodeOrNull(currentExportStatus), oldSchedRecord.getQtyToDeliver(), schedRecord.getQtyToDeliver(), APIExportStatus.Pending.getCode());
				schedRecord.setExportStatus(APIExportStatus.Pending.getCode());
			}
		}
	}

	private void addAttributes(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final List<CreateAttributeInstanceReq> attributeInstanceBasicInfos)
	{
		final AttributeSetInstanceId existingAttributeSetOrNone = AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID());

		final AddAttributesRequest addAttributesRequest = AddAttributesRequest.builder()
				.existingAttributeSetIdOrNone(existingAttributeSetOrNone)
				.attributeInstanceBasicInfos(attributeInstanceBasicInfos)
				.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.build();

		final AttributeSetInstanceId attributeSetInstanceId = attributeSetInstanceBL.addAttributes(addAttributesRequest);

		shipmentSchedule.setM_AttributeSetInstance_ID(attributeSetInstanceId.getRepoId());
	}

	@Override
	@NonNull
	public Quantity getQtyOrdered(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = this.shipmentScheduleEffectiveBL;

		final BigDecimal qtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(shipmentScheduleRecord);

		final I_C_UOM uom = getUomOfProduct(shipmentScheduleRecord);

		if (qtyOrdered == null)
		{
			return Quantity.zero(uom);
		}

		return Quantity.of(qtyOrdered, uom);
	}

	@Override
	@NonNull
	public Quantity getQtyDelivered(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final BigDecimal qtyDelivered = shipmentScheduleRecord.getQtyDelivered();

		final I_C_UOM uom = getUomOfProduct(shipmentScheduleRecord);

		if (qtyDelivered == null)
		{
			return Quantity.zero(uom);
		}

		return Quantity.of(qtyDelivered, uom);
	}

	@Override
	public void updateExportStatus(@NonNull final APIExportStatus newExportStatus, @NonNull final PInstanceId pinstanceId)
	{
		final AtomicInteger allCounter = new AtomicInteger(0);
		final AtomicInteger updatedCounter = new AtomicInteger(0);

		queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
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
							 updateCanBeExportedAfter(record);
							 InterfaceWrapperHelper.saveRecord(record);

							 updatedCounter.incrementAndGet();
						 });

		Loggables.withLogger(logger, Level.INFO).addLog("Updated {} out of {} M_ShipmentSchedule", updatedCounter.get(), allCounter.get());
	}

	@Override
	public void setAsyncBatch(@NonNull final ShipmentScheduleId shipmentScheduleId, @NonNull final AsyncBatchId asyncBatchId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulePA.getById(shipmentScheduleId);

		if (shipmentSchedule.getC_Async_Batch_ID() > 0)
		{
			throw new AdempiereException("Reassigning shipmentSchedule.C_Async_Batch_ID is not allowed!");
		}

		if (shipmentSchedule.isProcessed())
		{
			Loggables.withLogger(logger, Level.WARN).addLog("ShipmentScheduleBL.setAsyncBatch(): M_ShipmentScheduled already processed,"
																	+ " nothing to do! ShipmentScheduleId: {}", shipmentSchedule.getM_ShipmentSchedule_ID());
			return;
		}

		shipmentSchedule.setC_Async_Batch_ID(asyncBatchId.getRepoId());

		shipmentSchedulePA.save(shipmentSchedule);
	}

	@Override
	public DeliveryPlanningCreateRequest createDeliveryPlanningRequest(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(shipmentScheduleRecord.getC_Order_ID());
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(shipmentScheduleRecord.getC_OrderLine_ID());

		final I_C_UOM uomOfProduct = getUomOfProduct(shipmentScheduleRecord);

		final Quantity qtyOrdered = Quantity.of(shipmentScheduleRecord.getQtyOrdered(), uomOfProduct);
		final OrgId orgId = OrgId.ofRepoId(shipmentScheduleRecord.getAD_Org_ID());

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNull(shipmentScheduleRecord.getM_AttributeSetInstance_ID());

		final String originCountryCode = attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.CountryOfOrigin, asiId);
		final CountryId countryId = originCountryCode == null ? null : countryDAO.getCountryIdByCountryCode(originCountryCode);
		final String huBatchNo = attributeSetInstanceBL.getAttributeValueOrNull(AttributeConstants.HU_BatchNo, asiId);

		final Timestamp deliveryDate_effective = CoalesceUtil.coalesce(shipmentScheduleRecord.getDeliveryDate_Override(), shipmentScheduleRecord.getDeliveryDate());

		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(shipmentScheduleRecord.getC_BPartner_ID(), shipmentScheduleRecord.getC_BPartner_Location_ID());

		final DeliveryPlanningCreateRequest.DeliveryPlanningCreateRequestBuilder requestBuilder = DeliveryPlanningCreateRequest.builder()
				.orgId(orgId)
				.clientId(ClientId.ofRepoId(shipmentScheduleRecord.getAD_Client_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentScheduleRecord.getM_ShipmentSchedule_ID()))
				.deliveryPlanningType(DeliveryPlanningType.Outgoing)
				.orderId(orderId)
				.orderLineId(orderLineId)
				.warehouseId(WarehouseId.ofRepoId(shipmentScheduleRecord.getM_Warehouse_ID()))
				.productId(ProductId.ofRepoId(shipmentScheduleRecord.getM_Product_ID()))
				.partnerId(BPartnerId.ofRepoId(shipmentScheduleRecord.getC_BPartner_ID()))
				.bPartnerLocationId(
						bPartnerLocationId)
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(shipmentScheduleRecord.getM_SectionCode_ID()))
				.qtyOrdered(qtyOrdered)
				.qtyTotalOpen(qtyOrdered.subtract(getQtyDelivered(shipmentScheduleRecord)))
				.actualLoadedQty(Quantity.zero(uomOfProduct))
				.actualDeliveredQty(Quantity.zero(uomOfProduct))
				.plannedLoadedQty(Quantity.zero(uomOfProduct))
				.plannedDischargeQty(Quantity.zero(uomOfProduct))
				.actualDischargeQty(Quantity.zero(uomOfProduct))
				.uom(uomOfProduct)
				.plannedDeliveryDate(TimeUtil.asInstant(deliveryDate_effective))
				.batch(huBatchNo)
				.originCountryId(countryId);

		if (orderId != null)
		{
			final I_C_Order order = orderDAO.getById(orderId);

			requestBuilder.isB2B(order.isDropShip())
					.incotermsId(IncotermsId.ofRepoIdOrNull(order.getC_Incoterms_ID()))
					.incotermLocation(order.getIncotermLocation());

			final BPartnerLocationAndCaptureId bpartnerLocationId = OrderDocumentLocationAdapterFactory.locationAdapter(order).getBPartnerLocationAndCaptureId();
			final CountryId destinationCountryId = bPartnerBL.getCountryId(bpartnerLocationId);

			requestBuilder.destinationCountryId(destinationCountryId);
		}

		if (orderLineId != null)
		{
			final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);

			requestBuilder.actualDeliveryDate(TimeUtil.asInstant(orderLine.getDateDelivered()));

			if (deliveryDate_effective == null)
			{
				requestBuilder.plannedDeliveryDate(TimeUtil.asInstant(orderLine.getDatePromised()));
			}

			requestBuilder.shipperId(ShipperId.ofRepoIdOrNull(orderLine.getM_Shipper_ID()));
		}

		return requestBuilder.build();
	}
}
