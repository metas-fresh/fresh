/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.computing.purchasecontract.interim;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.AbstractComputingMethodHandler;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.ComputingRequest;
import de.metas.contracts.modular.computing.ComputingResponse;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.currency.ICurrencyBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class InterimComputingMethod extends AbstractComputingMethodHandler
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	@NonNull private final ComputingMethodService computingMethodService;
	@NonNull private final ModularContractProvider contractProvider;
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@Override
	public boolean applies(final @NonNull TableRecordReference recordRef, @NonNull final LogEntryContractType logEntryContractType)
	{
		switch (recordRef.getTableName())
		{
			case I_M_InOutLine.Table_Name ->
			{
				if (!logEntryContractType.isInterimContractType()) {return false;}
				final I_M_InOutLine inOutLineRecord = inoutDao.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
				final I_M_InOut inOutRecord = inoutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
				final OrderId orderId = OrderId.ofRepoIdOrNull(inOutLineRecord.getC_Order_ID());
				return SOTrx.ofBoolean(inOutRecord.isSOTrx()).isPurchase() && !(inOutLineRecord.getMovementQty().signum() < 0) && orderId != null;
			}
			case I_C_Flatrate_Term.Table_Name ->
			{
				if (!logEntryContractType.isInterimContractType()) {return false;}
				final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(FlatrateTermId.ofRepoId(recordRef.getRecord_ID()));
				if (!TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isInterimContractType())
				{
					return false;
				}

				final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(flatrateTermRecord.getC_OrderLine_Term_ID());
				if (orderLineId == null)
				{
					return false;
				}

				final OrderId orderId = orderLineBL.getOrderIdByOrderLineId(orderLineId);
				return SOTrx.ofBoolean(orderBL.getById(orderId).isSOTrx()).isPurchase();
			}
			case I_C_InvoiceLine.Table_Name ->
			{
				if (!logEntryContractType.isModularContractType()) {return false;}
				final I_C_Invoice invoiceRecord = invoiceBL.getByLineId(InvoiceLineId.ofRepoId(recordRef.getRecord_ID()));
				final SOTrx soTrx = SOTrx.ofBoolean(invoiceRecord.isSOTrx());

				return soTrx.isPurchase() && invoiceBL.isDownPayment(invoiceRecord);
			}
			default -> {return false;}
		}
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		return switch (recordRef.getTableName())
		{
			case I_M_InOutLine.Table_Name -> contractProvider.streamInterimPurchaseContractsForReceiptLine(InOutLineId.ofRepoId(recordRef.getRecord_ID()));
			case I_C_Flatrate_Term.Table_Name -> Stream.of(FlatrateTermId.ofRepoId(recordRef.getRecord_ID()));
			case I_C_InvoiceLine.Table_Name -> contractProvider.streamModularPurchaseContractsForInvoiceLine(InvoiceLineId.ofRepoId(recordRef.getRecord_ID()));
			default -> Stream.empty();
		};
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return ComputingMethodType.INTERIM_CONTRACT;
	}

	@Override
	public @NonNull ComputingResponse compute(final @NonNull ComputingRequest request)
	{
		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());
		final ModularContractLogEntriesList logs = computingMethodService.retrieveLogsForCalculation(request);

		// TODO: Check if the numbers are OK or we should rather divide the amount by the qty
		// final Money amount = logs.getAmount().orElseGet(() -> Money.zero(request.getCurrencyId()));
		final Quantity qtySumInStockUOM = computingMethodService.getQtySumInStockUOM(logs);

		final ModularContractLogEntry modularContractLogEntry = logs.getFirstEntry();

		final ProductPrice productPrice = Check.assumeNotNull(modularContractLogEntry.getPriceActual(), "productPrice shouldn't be null");
		final ProductPrice productPriceToInvoice = productPrice.convertToUom(stockUOMId,
																			 currencyBL.getStdPrecision(productPrice.getCurrencyId()),
																			 uomConversionBL);

		return ComputingResponse.builder()
				.ids(logs.getIds())
				.invoiceCandidateId(logs.getSingleInvoiceCandidateIdOrNull())
				.price(productPriceToInvoice)
				.qty(qtySumInStockUOM.negate())
				.build();
	}
}
