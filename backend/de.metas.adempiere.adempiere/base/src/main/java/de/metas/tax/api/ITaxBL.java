package de.metas.tax.api;

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

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Tax;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Properties;

public interface ITaxBL extends ISingletonService
{
	Tax getTaxById(TaxId taxId);

	/**
	 * Do not attempt to retrieve the C_Tax for an order (i.e invoicing is done at a different time - 1 year - from the order)<br>
	 * Also note that packaging material receipts don't have an order line and if this one had, no IC would be created for it by this handler.<br>
	 * Instead, always rely on taxing BL to bind the tax to the invoice candidate
	 * <p>
	 * Try to rely on the tax category from the pricing result<br>
	 * 07739: If that's not available, then throw an exception; don't attempt to retrieve the German tax because that method proved to return a wrong result
	 */
	@NonNull
	TaxId getTaxNotNull(
			@Nullable Object model,
			@Nullable TaxCategoryId taxCategoryId,
			int productId,
			Timestamp shipDate,
			@NonNull OrgId orgId,
			@Nullable WarehouseId warehouseId,
			BPartnerLocationAndCaptureId shipBPartnerLocationId,
			SOTrx soTrx);

	/**
	 * Calculate Tax - no rounding
	 *
	 * @param taxIncluded if true tax is calculated from gross otherwise from net
	 * @return tax amount
	 */
	BigDecimal calculateTax(I_C_Tax tax, BigDecimal amount, boolean taxIncluded, int scale);

	/**
	 * Calculate base amount, excluding tax
	 */
	BigDecimal calculateBaseAmt(I_C_Tax tax, BigDecimal amount, boolean taxIncluded, int scale);

	/**
	 * Get Tax ID - converts parameters to call Get Tax.
	 *
	 * <pre>
	 * 	M_Product_ID/C_Charge_ID	->	C_TaxCategory_ID
	 * 	billDate, shipDate			->	billDate, shipDate
	 * 	AD_Org_ID					->	billFromC_Location_ID
	 * 	M_Warehouse_ID				->	shipFromC_Location_ID
	 * 	billC_BPartner_Location_ID  ->	billToC_Location_ID
	 * 	shipC_BPartner_Location_ID 	->	shipToC_Location_ID
	 *
	 *  if IsSOTrx is false, bill and ship are reversed
	 * </pre>
	 *
	 * @param billDate                   invoice date
	 * @param shipDate                   ship date (ignored)
	 * @param M_Warehouse_ID             warehouse (ignored)
	 * @param billC_BPartner_Location_ID invoice location
	 * @param shipC_BPartner_Location_ID ship location (ignored)
	 * @return C_Tax_ID
	 */
	int get(Properties ctx, int M_Product_ID, int C_Charge_ID,
			Timestamp billDate, Timestamp shipDate,
			int AD_Org_ID, int M_Warehouse_ID,
			BPartnerLocationAndCaptureId billC_BPartner_Location_ID,
			BPartnerLocationAndCaptureId shipC_BPartner_Location_ID,
			boolean IsSOTrx);

	/**
	 * Sets the correct flags if given tax has {@link I_C_Tax#isWholeTax()} set.
	 */
	void setupIfIsWholeTax(final I_C_Tax tax);

	TaxCategoryId retrieveRegularTaxCategoryId();

	Optional<TaxCategoryId> getTaxCategoryIdByInternalName(String internalName);

	Tax getDefaultTax(TaxCategoryId taxCategoryId);
}
