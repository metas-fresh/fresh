/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular;

import com.google.common.collect.ImmutableSet;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.shippingnotification.ShippingNotificationLineId;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.Adempiere;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.contracts.flatrate.TypeConditions.MODULAR_CONTRACT;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

@Service
@RequiredArgsConstructor
public class ModularContractProvider
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	@NonNull private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

	@NonNull private final ShippingNotificationService shippingNotificationService;

	public static ModularContractProvider newInstanceForJUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ModularContractProvider(ShippingNotificationService.newInstanceForJUnitTesting());
	}

	@NonNull
	public Stream<FlatrateTermId> streamSalesContractsForShippingNotificationLine(@NonNull final ShippingNotificationLineId shippingNotificationLineId)
	{
		final I_M_Shipping_NotificationLine notificationLine = shippingNotificationService.getLineRecordByLineId(shippingNotificationLineId);
		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(notificationLine.getC_Order_ID(), notificationLine.getC_OrderLine_ID());
		return streamSalesContractsForSalesOrderLine(orderAndLineId);
	}

	@NonNull
	public Stream<FlatrateTermId> streamSalesContractsForSalesOrderLine(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLine = orderBL.getOrderLineById(orderLineId);
		return streamSalesContractsForSalesOrderLine(OrderAndLineId.of(OrderId.ofRepoId(orderLine.getC_Order_ID()), orderLineId));
	}

	@NonNull
	public Stream<FlatrateTermId> streamSalesContractsForSalesOrderLine(@NonNull final OrderAndLineId orderAndLineId)
	{
		final I_C_Order order = orderBL.getById(orderAndLineId.getOrderId());
		if (!order.isSOTrx())
		{
			return Stream.empty();
		}

		return flatrateBL.getByOrderLineId(orderAndLineId.getOrderLineId(), MODULAR_CONTRACT)
				.map(flatrateTerm -> FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.stream();
	}

	@Nullable
	public FlatrateTermId getSinglePurchaseContractsForSalesOrderLineOrNull(@Nullable final OrderAndLineId orderAndLineId)
	{
		if (orderAndLineId == null)
		{
			return null;
		}
		return streamPurchaseContractForSalesOrderLine(orderAndLineId).findFirst().orElse(null);
	}

	@NonNull
	public Stream<FlatrateTermId> streamPurchaseContractForSalesOrderLine(@NonNull final OrderAndLineId orderAndLineId)
	{
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(orderBL.getLineById(orderAndLineId).getPurchase_Modular_Flatrate_Term_ID()));
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForPurchaseOrderLine(@NonNull final OrderLineId purchaseOrderLineId)
	{
		return flatrateBL.getByOrderLineId(purchaseOrderLineId, MODULAR_CONTRACT)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId)
				.stream();
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForReceiptLine(@NonNull final InOutLineId receiptInOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(receiptInOutLineId);
		final FlatrateTermId contractId = FlatrateTermId.ofRepoIdOrNull(inOutLineRecord.getC_Flatrate_Term_ID());

		return Stream.ofNullable(contractId)
				.map(flatrateBL::getById)
				.filter(contract -> MODULAR_CONTRACT.equalsByCode(contract.getType_Conditions()))
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId);
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForInventory(final InventoryLineId inventoryId)
	{
		final I_M_InventoryLine inventoryLine = inventoryBL.getLineById(inventoryId);
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(inventoryLine.getModular_Flatrate_Term_ID()));
	}

	@NonNull
	public Stream<FlatrateTermId> streamInterimPurchaseContractsForReceiptLine(@NonNull final InOutLineId receiptInOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(receiptInOutLineId);
		final I_M_InOut inOutRecord = inOutDAO.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final FlatrateTermId modularFlatrateTermId = FlatrateTermId.ofRepoIdOrNull(inOutLineRecord.getC_Flatrate_Term_ID());
		if (inOutRecord.isSOTrx() || modularFlatrateTermId == null || inOutLineRecord.getMovementQty().signum() < 0)
		{
			return Stream.empty();
		}
		final Instant movementDate = TimeUtil.asInstant(inOutRecord.getMovementDate());
		Check.assumeNotNull(movementDate, "Instant Movement Date of receipt shouldn't be null");
		return Stream.ofNullable(flatrateBL.getInterimContractIdByModularContractIdAndDate(modularFlatrateTermId, movementDate));
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForPPOrder(@NonNull final PPCostCollectorId ppCostCollectorId)
	{
		return Optional.of(ppCostCollectorBL.getById(ppCostCollectorId).getPP_Order_ID())
				.map(PPOrderId::ofRepoId)
				.map(ppOrderBL::getById)
				.map(I_PP_Order::getModular_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoIdOrNull)
				.stream();
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForInvoiceLine(@NonNull final InvoiceLineId invoiceLineId)
	{
		final FlatrateTermId flatrateTermId = extractFlatrateTermId(invoiceLineId);

		if (flatrateTermId == null)
		{
			return Stream.empty();
		}

		if (flatrateBL.isModularContract(flatrateTermId))
		{
			return Stream.of(flatrateTermId);
		}
		else if (flatrateBL.isInterimContract(flatrateTermId))
		{
			final I_C_Flatrate_Term interimContractRecord = flatrateBL.getById(flatrateTermId);
			return Stream.of(FlatrateTermId.ofRepoId(interimContractRecord.getModular_Flatrate_Term_ID()));
		}
		else
		{
			return Stream.empty();
		}
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularSalesContractsForInvoiceLine(@NonNull final InvoiceLineId invoiceLineId)
	{
		return Stream.ofNullable(extractFlatrateTermId(invoiceLineId));
	}

	@Nullable
	private FlatrateTermId extractFlatrateTermId(@NonNull final InvoiceLineId invoiceLineId)
	{
		final I_C_InvoiceLine invoiceLineRecord = invoiceBL.getLineById(invoiceLineId);
		final FlatrateTermId flatrateTermId;
		if (invoiceLineRecord.getC_Flatrate_Term_ID() > 0)
		{
			flatrateTermId = FlatrateTermId.ofRepoId(invoiceLineRecord.getC_Flatrate_Term_ID());
		}
		else
		{
			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveIcForIl(invoiceLineRecord);

			if (invoiceCandidates.isEmpty())
			{
				return null;
			}

			flatrateTermId = CollectionUtils.extractSingleElement(invoiceCandidates, this::extractFlatrateTermId).orElse(null);
		}
		return flatrateTermId;
	}

	private Optional<FlatrateTermId> extractFlatrateTermId(@NonNull final I_C_Invoice_Candidate ic)
	{
		if (ic.getC_Flatrate_Term_ID() > 0)
		{
			return Optional.of(FlatrateTermId.ofRepoId(ic.getC_Flatrate_Term_ID()));
		}

		final int flatrateTermTableId = getTableId(I_C_Flatrate_Term.class);
		if (ic.getAD_Table_ID() == flatrateTermTableId)
		{
			return Optional.of(FlatrateTermId.ofRepoId(ic.getRecord_ID()));
		}

		return Optional.empty();
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularPurchaseContractsForShipmentLine(@NonNull final InOutLineId inOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(inOutLineId);
		final I_M_InOut inOutRecord = inOutDAO.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIdsOrNull(inOutLineRecord.getC_Order_ID(), inOutLineRecord.getC_OrderLine_ID());
		if (!inOutRecord.isSOTrx() || inOutLineRecord.getMovementQty().signum() < 0 || orderAndLineId == null)
		{
			return Stream.empty();
		}

		return streamPurchaseContractForSalesOrderLine(orderAndLineId);
	}

	@NonNull
	public Stream<FlatrateTermId> streamModularSalesContractsForShipmentLine(@NonNull final InOutLineId inOutLineId)
	{
		final I_M_InOutLine inOutLineRecord = inOutDAO.getLineByIdInTrx(inOutLineId);
		final I_M_InOut inOutRecord = inOutDAO.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inOutLineRecord.getC_OrderLine_ID());
		if (!inOutRecord.isSOTrx() || inOutLineRecord.getMovementQty().signum() < 0 || orderLineId == null)
		{
			return Stream.empty();
		}

		return streamSalesContractsForSalesOrderLine(orderLineId);
	}

	@NonNull
	public Set<FlatrateTermId> getInitialPurchaseModularContractCandidatesForSalesOrderLine(@NonNull final OrderId orderId, @NonNull final ProductId productId)
	{
		final I_C_Order order = orderBL.getById(orderId);
		if(!order.isSOTrx())
		{
			return ImmutableSet.of();
		}

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(order.getHarvesting_Year_ID());
		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(order.getC_Harvesting_Calendar_ID());

		if (harvestingYearId == null || harvestingCalendarId == null)
		{
			return ImmutableSet.of();
		}

		final ModularFlatrateTermQuery query = ModularFlatrateTermQuery.builder()
				.warehouseId(WarehouseId.ofRepoId(order.getM_Warehouse_ID()))
				.productId(productId)
				.yearId(harvestingYearId)
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(MODULAR_CONTRACT)
				.calendarId(harvestingCalendarId)
				.build();

		return flatrateBL.streamModularFlatrateTermIdsByQuery(query).collect(Collectors.toSet());
	}


	public @NonNull Stream<FlatrateTermId> streamModularPurchaseContractsForContract(final FlatrateTermId flatrateTermId)
	{
		if(flatrateBL.isModularContract(flatrateTermId))
		{
			return Stream.of(flatrateTermId);
		}
		if(flatrateBL.isInterimContract(flatrateTermId))
		{
			final I_C_Flatrate_Term interimContract = flatrateBL.getById(flatrateTermId);
			return Stream.of(FlatrateTermId.ofRepoId(interimContract.getModular_Flatrate_Term_ID()));
		}
			return Stream.empty();
	}
}
