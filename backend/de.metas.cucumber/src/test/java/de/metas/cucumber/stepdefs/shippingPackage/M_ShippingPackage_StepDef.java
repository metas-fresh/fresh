/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.shippingPackage;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Package_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.deliveryplanning.M_ShipperTransportation_StepDefData;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_ShippingPackage_StepDef
{
	private final M_ShippingPackage_StepDefData shippingPackageTable;
	private final M_ShipperTransportation_StepDefData shipperTransportationTable;
	private final M_Package_StepDefData packageTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_ShippingPackage_StepDef(
			@NonNull final M_ShippingPackage_StepDefData shippingPackageTable,
			@NonNull final M_ShipperTransportation_StepDefData shipperTransportationTable,
			@NonNull final M_Package_StepDefData packageTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.shippingPackageTable = shippingPackageTable;
		this.shipperTransportationTable = shipperTransportationTable;
		this.packageTable = packageTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.bPartnerTable = bPartnerTable;
		this.orderLineTable = orderLineTable;
		this.productTable = productTable;
	}

	@And("load M_Shipping_Package:")
	public void load_M_Shipping_Package(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipperTransportation deliveryInstruction = shipperTransportationTable.get(deliveryInstructionIdentifier);
			assertThat(deliveryInstruction).isNotNull();

			final String packageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_Package_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Package packageRecord = packageTable.get(packageIdentifier);
			assertThat(packageRecord).isNotNull();

			final I_M_ShippingPackage shippingPackage = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
					.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_Package_ID, packageRecord.getM_Package_ID())
					.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstruction.getM_ShipperTransportation_ID())
					.create()
					.firstOnlyNotNull(I_M_ShippingPackage.class);

			final String shippingPackageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID + "." + TABLECOLUMN_IDENTIFIER);
			shippingPackageTable.putOrReplace(shippingPackageIdentifier, shippingPackage);
		}
	}

	@And("validate M_Shipping_Package:")
	public void validate_M_Shipping_Package(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final SoftAssertions softly = new SoftAssertions();

			final String shippingPackageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_ShippingPackage_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShippingPackage shippingPackage = shippingPackageTable.get(shippingPackageIdentifier);
			softly.assertThat(shippingPackage).isNotNull();

			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipperTransportation deliveryInstruction = shipperTransportationTable.get(deliveryInstructionIdentifier);
			softly.assertThat(deliveryInstruction).isNotNull();
			softly.assertThat(shippingPackage.getM_ShipperTransportation_ID()).as(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID).isEqualTo(deliveryInstruction.getM_ShipperTransportation_ID());

			final String packageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_Package_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Package packageRecord = packageTable.get(packageIdentifier);
			softly.assertThat(packageRecord).isNotNull();
			softly.assertThat(shippingPackage.getM_Package_ID()).as(I_M_ShippingPackage.COLUMNNAME_M_Package_ID).isEqualTo(packageRecord.getM_Package_ID());

			final String bpartnerLocIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bpartnerLocIdentifier);
			softly.assertThat(bPartnerLocation).isNotNull();
			softly.assertThat(shippingPackage.getC_BPartner_Location_ID()).as(I_M_ShippingPackage.COLUMNNAME_C_BPartner_Location_ID).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

			final BigDecimal actualLoadQty = DataTableUtil.extractBigDecimalForColumnName(row, I_M_ShippingPackage.COLUMNNAME_ActualLoadQty);
			softly.assertThat(shippingPackage.getActualLoadQty()).as(I_M_ShippingPackage.COLUMNNAME_ActualLoadQty).isEqualTo(actualLoadQty);

			final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShippingPackage.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bpartnerIdentifier))
			{
				final I_C_BPartner bPartner = bPartnerTable.get(bpartnerIdentifier);
				softly.assertThat(bPartner).isNotNull();
				softly.assertThat(shippingPackage.getC_BPartner_ID()).as(I_M_ShippingPackage.COLUMNNAME_C_BPartner_ID).isEqualTo(bPartner.getC_BPartner_ID());
			}

			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShippingPackage.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(productIdentifier))
			{
				final I_M_Product product = productTable.get(productIdentifier);
				softly.assertThat(product).isNotNull();
				softly.assertThat(shippingPackage.getM_Product_ID()).as(I_M_ShippingPackage.COLUMNNAME_M_Product_ID).isEqualTo(product.getM_Product_ID());
			}

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShippingPackage.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				softly.assertThat(orderLine).isNotNull();
				softly.assertThat(shippingPackage.getC_OrderLine_ID()).as(I_M_ShippingPackage.COLUMNNAME_C_OrderLine_ID).isEqualTo(orderLine.getC_OrderLine_ID());
			}

			softly.assertAll();
		}
	}

}
