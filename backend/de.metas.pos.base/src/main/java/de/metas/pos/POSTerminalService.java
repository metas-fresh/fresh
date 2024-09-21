package de.metas.pos;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.cache.CCache;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_POS;
import org.compiere.model.I_M_PriceList;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class POSTerminalService
{
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final POSTerminalRawRepository posTerminalRawRepository;
	@NonNull private final CurrencyRepository currencyRepository;

	private final CCache<Integer, POSTerminalId> defaultPOSTerminalId = CCache.<Integer, POSTerminalId>builder()
			.tableName(I_C_POS.Table_Name)
			.build();

	private final CCache<POSTerminalId, POSTerminal> cache = CCache.<POSTerminalId, POSTerminal>builder()
			.tableName(I_C_POS.Table_Name)
			.additionalTableNameToResetFor(I_M_PriceList.Table_Name)
			.additionalTableNameToResetFor(I_C_Currency.Table_Name)
			.build();

	@NonNull
	public POSTerminal getPOSTerminal()
	{
		return getPOSTerminalById(getPOSTerminalId());
	}

	@NonNull
	public POSTerminal getPOSTerminalById(final POSTerminalId posTerminalId)
	{
		return cache.getOrLoad(posTerminalId, this::retrievePOSTerminalById);
	}

	private POSTerminalId getPOSTerminalId()
	{
		return defaultPOSTerminalId.getOrLoad(0, posTerminalRawRepository::retrievePOSTerminalId);
	}

	@NonNull
	private POSTerminal retrievePOSTerminalById(@NonNull final POSTerminalId posTerminalId)
	{
		final POSTerminalRaw posTerminalRaw = posTerminalRawRepository.retrievePOSTerminalById(posTerminalId);
		final I_M_PriceList priceList = priceListDAO.getById(posTerminalRaw.getPriceListId());
		if (priceList == null)
		{
			throw new AdempiereException("No price list found for ID: " + posTerminalRaw.getPriceListId());
		}

		final CurrencyId currencyId = CurrencyId.ofRepoId(priceList.getC_Currency_ID());
		final Currency currency = currencyRepository.getById(currencyId);

		return POSTerminal.builder()
				.id(posTerminalRaw.getId())
				.cashbookId(posTerminalRaw.getCashbookId())
				.pricingSystemAndListId(PricingSystemAndListId.ofRepoIds(priceList.getM_PricingSystem_ID(), priceList.getM_PriceList_ID()))
				.isTaxIncluded(priceList.isTaxIncluded())
				.pricePrecision(CurrencyPrecision.ofInt(priceList.getPricePrecision()))
				.shipFrom(extractShipFrom(posTerminalRaw))
				.walkInCustomerShipToLocationId(extractWalkInCustomerShipTo(posTerminalRaw))
				.salesOrderDocTypeId(posTerminalRaw.getSalesOrderDocTypeId())
				.currency(currency)
				.cashJournalId(posTerminalRaw.getCashJournalId())
				.cashLastBalance(Money.of(posTerminalRaw.getCashLastBalance(), currencyId))
				.build();
	}

	private POSShipFrom extractShipFrom(final POSTerminalRaw posTerminalRaw)
	{
		final WarehouseId shipFromWarehouseId = posTerminalRaw.getShipFromWarehouseId();
		return POSShipFrom.builder()
				.warehouseId(shipFromWarehouseId)
				.orgId(warehouseBL.getWarehouseOrgId(shipFromWarehouseId))
				.countryId(warehouseBL.getCountryId(shipFromWarehouseId))
				.build();
	}

	private @NonNull BPartnerLocationAndCaptureId extractWalkInCustomerShipTo(final POSTerminalRaw posTerminalRaw)
	{
		return BPartnerLocationAndCaptureId.ofRecord(
				bpartnerDAO.retrieveBPartnerLocation(BPartnerLocationQuery.builder()
						.type(BPartnerLocationQuery.Type.SHIP_TO)
						.bpartnerId(posTerminalRaw.getWalkInCustomerId())
						.build())
		);
	}

	private static POSTerminalRaw toRaw(final POSTerminal posTerminal)
	{
		return POSTerminalRaw.builder()
				.id(posTerminal.getId())
				.priceListId(posTerminal.getPriceListId())
				.shipFromWarehouseId(posTerminal.getShipFrom().getWarehouseId())
				.walkInCustomerId(posTerminal.getWalkInCustomerShipToLocationId().getBpartnerId())
				.salesOrderDocTypeId(posTerminal.getSalesOrderDocTypeId())
				.cashbookId(posTerminal.getCashbookId())
				.cashJournalId(posTerminal.getCashJournalId())
				.cashLastBalance(posTerminal.getCashLastBalance().toBigDecimal())
				.build();
	}

	public void save(@NonNull final POSTerminal posTerminal)
	{
		final POSTerminalRaw raw = toRaw(posTerminal);
		posTerminalRawRepository.save(raw);
	}
}
