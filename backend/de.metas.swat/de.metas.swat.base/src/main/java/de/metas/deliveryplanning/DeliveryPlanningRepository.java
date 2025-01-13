/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.CacheMgt;
import de.metas.incoterms.IncotermsId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.location.CountryId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.sectionCode.SectionCodeId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Delivery_Instructions_V;
import org.compiere.model.I_M_Package;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class DeliveryPlanningRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);


	protected I_M_Delivery_Planning getById(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return load(deliveryPlanningId, I_M_Delivery_Planning.class);
	}

	public void generateDeliveryPlanning(@NonNull final DeliveryPlanningCreateRequest request)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = newInstance(I_M_Delivery_Planning.class);

		deliveryPlanningRecord.setAD_Org_ID(request.getOrgId().getRepoId());
		deliveryPlanningRecord.setM_ReceiptSchedule_ID(ReceiptScheduleId.toRepoId(request.getReceiptScheduleId()));
		deliveryPlanningRecord.setM_ShipmentSchedule_ID(ShipmentScheduleId.toRepoId(request.getShipmentScheduleId()));
		deliveryPlanningRecord.setC_Order_ID(OrderId.toRepoId(request.getOrderId()));
		deliveryPlanningRecord.setC_OrderLine_ID(OrderLineId.toRepoId(request.getOrderLineId()));
		deliveryPlanningRecord.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
		deliveryPlanningRecord.setM_Warehouse_ID(WarehouseId.toRepoId(request.getWarehouseId()));
		deliveryPlanningRecord.setC_BPartner_ID(BPartnerId.toRepoId(request.getPartnerId()));
		deliveryPlanningRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(request.getBPartnerLocationId()));
		deliveryPlanningRecord.setC_Incoterms_ID(IncotermsId.toRepoId(request.getIncotermsId()));
		deliveryPlanningRecord.setIncotermLocation(request.getIncotermLocation());
		deliveryPlanningRecord.setM_SectionCode_ID(SectionCodeId.toRepoId(request.getSectionCodeId()));

		deliveryPlanningRecord.setPlannedDeliveryDate(TimeUtil.asTimestamp(request.getPlannedDeliveryDate()));
		deliveryPlanningRecord.setActualDeliveryDate(TimeUtil.asTimestamp(request.getActualDeliveryDate()));
		deliveryPlanningRecord.setPlannedLoadingDate(TimeUtil.asTimestamp(request.getPlannedLoadingDate()));
		deliveryPlanningRecord.setActualLoadingDate(TimeUtil.asTimestamp(request.getActualLoadingDate()));

		deliveryPlanningRecord.setLoadingTime(request.getLoadingTime());
		deliveryPlanningRecord.setDeliveryTime(request.getDeliveryTime());

		final Quantity qtyOrdered = request.getQtyOrdered();
		final Quantity qtyTotalOpen = request.getQtyTotalOpen();
		final Quantity actualDeliveredQty = request.getActualDeliveredQty();
		final Quantity actualLoadedQty = request.getActualLoadedQty();

		final Quantity plannedLoadedQty = request.getPlannedLoadedQty();
		final Quantity plannedDischargeQty = request.getPlannedDischargeQty();
		final Quantity actualDischargeQty = request.getActualDischargeQty();

		deliveryPlanningRecord.setC_UOM_ID(request.getUom().getC_UOM_ID());

		deliveryPlanningRecord.setQtyOrdered(qtyOrdered.toBigDecimal());
		deliveryPlanningRecord.setQtyTotalOpen(qtyTotalOpen.toBigDecimal());
		deliveryPlanningRecord.setActualDeliveredQty(actualDeliveredQty.toBigDecimal());
		deliveryPlanningRecord.setActualLoadQty(actualLoadedQty.toBigDecimal());

		deliveryPlanningRecord.setPlannedLoadedQuantity(plannedLoadedQty.toBigDecimal());
		deliveryPlanningRecord.setPlannedDischargeQuantity(plannedDischargeQty.toBigDecimal());
		deliveryPlanningRecord.setActualDischargeQuantity(actualDischargeQty.toBigDecimal());

		deliveryPlanningRecord.setM_Shipper_ID(ShipperId.toRepoId(request.getShipperId()));

		deliveryPlanningRecord.setWayBillNo(request.getWayBillNo());
		deliveryPlanningRecord.setReleaseNo(request.getReleaseNo());
		deliveryPlanningRecord.setTransportDetails(request.getTransportDetails());

		deliveryPlanningRecord.setIsB2B(request.isB2B());

		deliveryPlanningRecord.setM_MeansOfTransportation_ID(MeansOfTransportationId.toRepoId(request.getMeansOfTransportationId()));
		deliveryPlanningRecord.setOrderStatus(OrderStatus.toCodeOrNull(request.getOrderStatus()));
		deliveryPlanningRecord.setM_Delivery_Planning_Type(DeliveryPlanningType.toCodeOrNull(request.getDeliveryPlanningType()));

		deliveryPlanningRecord.setBatch(request.getBatch());
		deliveryPlanningRecord.setC_OriginCountry_ID(CountryId.toRepoId(request.getOriginCountryId()));
		deliveryPlanningRecord.setC_DestinationCountry_ID(CountryId.toRepoId(request.getDestinationCountryId()));

		save(deliveryPlanningRecord);
	}

	public boolean isOtherDeliveryPlanningsExistForOrderLine(@NonNull final OrderLineId orderLineId, @NonNull final DeliveryPlanningId excludeDeliveryPlanningId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.addNotEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, excludeDeliveryPlanningId)
				.create()
				.anyMatch();
	}

	public void deleteForReceiptSchedule(@NonNull final ReceiptScheduleId receiptScheduleId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_ReceiptSchedule_ID, receiptScheduleId)
				.create()
				.delete();
	}

	public void deleteForShipmentSchedule(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.delete();
	}

	public Stream<I_M_Delivery_Planning> retrieveForOrderLine(@NonNull final OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.create()
				.stream();

	}

	public void closeSelectedDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.filter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, false)
				.create()
				.iterate(I_M_Delivery_Planning.class);

		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setIsClosed(true);
			deliveryPlanningRecord.setProcessed(true);
			save(deliveryPlanningRecord);
		}
	}

	public void reOpenSelectedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.filter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, true)
				.create()
				.iterate(I_M_Delivery_Planning.class);

		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setIsClosed(false);
			deliveryPlanningRecord.setProcessed(false);
			save(deliveryPlanningRecord);
		}
	}

	public boolean isExistsClosedDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.filter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, true)
				.create()
				.anyMatch();
	}

	public boolean isExistsOpenDeliveryPlannings(@NonNull final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.filter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, false)
				.create()
				.anyMatch();
	}

	public boolean isExistNoShipperDeliveryPlannings(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.filter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Shipper_ID, null)
				.create()
				.anyMatch();
	}

	public boolean isExistDeliveryPlanningsWithoutReleaseNo(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.filter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, null)
				.create()
				.anyMatch();
	}

	public I_M_ShipperTransportation generateDeliveryInstruction(@NonNull final DeliveryInstructionCreateRequest request)
	{
		final I_M_ShipperTransportation deliveryInstructionRecord = newInstance(I_M_ShipperTransportation.class);

		deliveryInstructionRecord.setAD_Org_ID(request.getOrgId().getRepoId());

		deliveryInstructionRecord.setShipper_BPartner_ID(request.getShipperBPartnerId().getRepoId());
		deliveryInstructionRecord.setShipper_Location_ID(request.getShipperLocationId().getRepoId());

		deliveryInstructionRecord.setProcessed(request.isProcessed());

		deliveryInstructionRecord.setC_Incoterms_ID(IncotermsId.toRepoId(request.getIncotermsId()));
		deliveryInstructionRecord.setIncotermLocation(request.getIncotermLocation());

		deliveryInstructionRecord.setLoadingTime(request.getLoadingTime());
		deliveryInstructionRecord.setDeliveryTime(request.getDeliveryTime());

		deliveryInstructionRecord.setM_Shipper_ID(request.getShipperId().getRepoId());

		deliveryInstructionRecord.setM_MeansOfTransportation_ID(MeansOfTransportationId.toRepoId(request.getMeansOfTransportationId()));

		deliveryInstructionRecord.setDeliveryDate(TimeUtil.asTimestamp(request.getDeliveryDate()));
		deliveryInstructionRecord.setDateDoc(TimeUtil.asTimestamp(request.getDateDoc()));
		deliveryInstructionRecord.setC_DocType_ID(request.getDocTypeId().getRepoId());

		deliveryInstructionRecord.setLoadingDate(TimeUtil.asTimestamp(request.getLoadingDate()));

		deliveryInstructionRecord.setC_BPartner_Location_Delivery_ID(request.getDeliveryPartnerLocationId().getRepoId());
		deliveryInstructionRecord.setC_BPartner_Location_Loading_ID(request.getLoadingPartnerLocationId().getRepoId());

		save(deliveryInstructionRecord);

		final I_M_ShippingPackage shippingPackageRecord = newInstance(I_M_ShippingPackage.class);

		shippingPackageRecord.setM_ShipperTransportation_ID(deliveryInstructionRecord.getM_ShipperTransportation_ID());

		final I_M_Package mpackage = newInstance(I_M_Package.class);
		mpackage.setM_Shipper_ID(request.getShipperId().getRepoId());
		mpackage.setShipDate((TimeUtil.asTimestamp(request.getDeliveryDate())));
		mpackage.setC_BPartner_ID(request.getShipperBPartnerId().getRepoId());
		mpackage.setC_BPartner_Location_ID(request.getShipperLocationId().getRepoId());
		save(mpackage);

		shippingPackageRecord.setM_Package_ID(mpackage.getM_Package_ID());
		shippingPackageRecord.setIsToBeFetched(request.isToBeFetched());
		shippingPackageRecord.setM_Product_ID(request.getProductId().getRepoId());

		shippingPackageRecord.setActualDischargeQuantity(request.getQtyDischarged().toBigDecimal());
		shippingPackageRecord.setActualLoadQty(request.getQtyLoaded().toBigDecimal());
		shippingPackageRecord.setBatch(request.getBatchNo());
		shippingPackageRecord.setC_UOM_ID(request.getUom().getC_UOM_ID());

		shippingPackageRecord.setC_BPartner_ID(request.getShipperBPartnerId().getRepoId());
		shippingPackageRecord.setC_BPartner_Location_ID(request.getShipperLocationId().getRepoId());

		saveRecord(shippingPackageRecord);

		CacheMgt.get().reset(I_M_Delivery_Planning_Delivery_Instructions_V.Table_Name, shippingPackageRecord.getM_ShippingPackage_ID() );

		return deliveryInstructionRecord;
	}

	public void updateDeliveryPlanningReleaseNo(@NonNull final DeliveryPlanningId deliveryPlanningId, @NonNull final String documentNo)
	{
		final I_M_Delivery_Planning deliveryPlanningRecord = getById(deliveryPlanningId);
		deliveryPlanningRecord.setReleaseNo(documentNo);
		saveRecord(deliveryPlanningRecord);
	}

	public Iterator<I_M_Delivery_Planning> extractDeliveryPlanningsSuitableForDeliveryInstruction(final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.filter(selectedDeliveryPlanningsFilter)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, null)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_IsClosed, false)
				.create()
				.iterate(I_M_Delivery_Planning.class);
	}

	private Iterator<I_M_Delivery_Planning> retrieveForReleaseNo(@NonNull final String releaseNo)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_ReleaseNo, releaseNo)
				.create()
				.iterate(I_M_Delivery_Planning.class);
	}

	public void unlinkDeliveryPlannings(final String documentNo)
	{
		final Iterator<I_M_Delivery_Planning> deliveryPlanningIterator = retrieveForReleaseNo(documentNo);
		while (deliveryPlanningIterator.hasNext())
		{
			final I_M_Delivery_Planning deliveryPlanningRecord = deliveryPlanningIterator.next();
			deliveryPlanningRecord.setReleaseNo(null);
			saveRecord(deliveryPlanningRecord);
		}
	}
}
