/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.pricing.rules;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.CurrencyPrecision;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.impl.PricingBL;
import de.metas.pricing.tax.LookupTaxCategoryRequest;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_PriceList;
import org.slf4j.Logger;

import java.util.Optional;

public class ManualPricePricingRule implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(ManualPricePricingRule.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final ProductTaxCategoryService productTaxCategoryService = SpringContextHolder.instance.getBean(ProductTaxCategoryService.class);

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!PricingBL.isManualPrice(pricingCtx))
		{
			logger.trace("Not applying because not manual price !");
			return false;
		}

		return true;
	}

	@Override
	public void calculate(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final I_M_PriceList priceListRecord = priceListsRepo.getById(pricingCtx.getPriceListId());

		if (priceListRecord == null)
		{
			logger.trace("Not found Price List! PricingCtx: {}", pricingCtx);
			return;
		}

		final Optional<TaxCategoryId> taxCategoryIdOptional = getTaxCategoryId(priceListRecord, pricingCtx);

		if (!taxCategoryIdOptional.isPresent())
		{
			logger.trace("Not found Product Tax Category! PricingCtx: {}", pricingCtx);
			return;
		}

		final ProductPrice manualPrice;
		if (pricingCtx.getReferencedObject() instanceof I_C_OLCand)
		{
			manualPrice = getManualPrice((I_C_OLCand)pricingCtx.getReferencedObject()).orElse(null);
		}
		else if (pricingCtx.getReferencedObject() instanceof I_C_OrderLine)
		{
			manualPrice = getManualPrice((I_C_OrderLine)pricingCtx.getReferencedObject()).orElse(null);
		}
		else if (pricingCtx.getReferencedObject() instanceof I_C_InvoiceLine)
		{
			manualPrice = getManualPrice((I_C_InvoiceLine)pricingCtx.getReferencedObject()).orElse(null);
		}
		else
		{
			manualPrice = getManualPrice(pricingCtx).orElse(null);
		}

		if (manualPrice == null)
		{
			logger.trace("Manual product price couldn't be calculated! PricingCtx: {}", pricingCtx);
			return;
		}

		final ProductCategoryId productCategoryId = productsRepo.retrieveProductCategoryByProductId(pricingCtx.getProductId());

		result.setProductId(manualPrice.getProductId());
		result.setPriceUomId(manualPrice.getUomId());
		result.setCurrencyId(manualPrice.getCurrencyId());
		result.setPriceStd(manualPrice.toBigDecimal());

		result.setTaxCategoryId(taxCategoryIdOptional.get());
		result.setProductCategoryId(productCategoryId);

		result.setTaxIncluded(priceListRecord.isTaxIncluded());
		result.setPrecision(CurrencyPrecision.ofInt(priceListRecord.getPricePrecision()));

		result.setPriceEditable(false);
		result.setDiscountEditable(false);

		result.setCalculated(true);
	}

	@NonNull
	private Optional<TaxCategoryId> getTaxCategoryId(
			@NonNull final I_M_PriceList priceListRecord,
			@NonNull final IPricingContext pricingCtx)
	{
		final CountryId countryId = CountryId.ofRepoIdOrNull(priceListRecord.getC_Country_ID());

		final LookupTaxCategoryRequest lookupTaxCategoryRequest = LookupTaxCategoryRequest.builder()
				.productId(pricingCtx.getProductId())
				.targetDate(pricingCtx.getPriceDate().atStartOfDay(orgDAO.getTimeZone(pricingCtx.getOrgId())).toInstant())
				.countryId(countryId)
				.build();

		return productTaxCategoryService.findTaxCategoryId(lookupTaxCategoryRequest);
	}

	@NonNull
	private Optional<ProductPrice> getManualPrice(@NonNull final I_C_OLCand olCand)
	{
		if (!olCand.isManualPrice()
				|| olCand.getC_Currency_ID() <= 0
				|| olCand.getC_UOM_ID() <= 0
				|| InterfaceWrapperHelper.isNull(olCand, I_C_OLCand.COLUMNNAME_PriceEntered)
				|| olCand.getM_Product_ID() <= 0)
		{
			logger.trace("Missing manual product price information on C_OLCand: {}!", olCand.getC_OLCand_ID());

			return Optional.empty();
		}

		final ProductPrice productPrice = ProductPrice.builder()
				.productId(ProductId.ofRepoId(olCand.getM_Product_ID()))
				.uomId(UomId.ofRepoId(olCand.getC_UOM_ID()))
				.money(Money.of(olCand.getPriceEntered(), CurrencyId.ofRepoId(olCand.getC_Currency_ID())))
				.build();

		return Optional.of(productPrice);
	}

	@NonNull
	private Optional<ProductPrice> getManualPrice(@NonNull final I_C_OrderLine orderLine)
	{
		if (!orderLine.isManualPrice() || orderLine.getPrice_UOM_ID() <= 0)
		{
			logger.trace("Missing manual product price information on C_OrderLine: {}!", orderLine.getC_OrderLine_ID());

			return Optional.empty();
		}

		final ProductPrice productPrice = ProductPrice.builder()
				.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.uomId(UomId.ofRepoId(orderLine.getPrice_UOM_ID()))
				.money(Money.of(orderLine.getPriceEntered(), CurrencyId.ofRepoId(orderLine.getC_Currency_ID())))
				.build();

		return Optional.of(productPrice);
	}

	@NonNull
	private Optional<ProductPrice> getManualPrice(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		if (!invoiceLine.isManualPrice()
				|| invoiceLine.getPrice_UOM_ID() <= 0
				|| invoiceLine.getM_Product_ID() <= 0)
		{
			logger.trace("Missing manual product price information on C_InvoiceLine: {}!", invoiceLine.getC_InvoiceLine_ID());

			return Optional.empty();
		}

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));

		final ProductPrice productPrice = ProductPrice.builder()
				.productId(ProductId.ofRepoId(invoiceLine.getM_Product_ID()))
				.uomId(UomId.ofRepoId(invoiceLine.getPrice_UOM_ID()))
				.money(Money.of(invoiceLine.getPriceEntered(), CurrencyId.ofRepoId(invoice.getC_Currency_ID())))
				.build();

		return Optional.of(productPrice);
	}

	@NonNull
	private Optional<ProductPrice> getManualPrice(@NonNull final IPricingContext pricingContext)
	{
		if (pricingContext.getManualPriceEnabled() == null
				|| !pricingContext.getManualPriceEnabled().isTrue()
				|| pricingContext.getUomId() == null
				|| pricingContext.getProductId() == null
				|| pricingContext.getCurrencyId() == null
				|| pricingContext.getManualPrice() == null)
		{
			logger.trace("Missing manual product price information on PricingContext: {}", pricingContext);

			return Optional.empty();
		}

		final ProductPrice productPrice = ProductPrice.builder()
				.productId(pricingContext.getProductId())
				.uomId(pricingContext.getUomId())
				.money(Money.of(pricingContext.getManualPrice(), pricingContext.getCurrencyId()))
				.build();

		return Optional.of(productPrice);
	}
}