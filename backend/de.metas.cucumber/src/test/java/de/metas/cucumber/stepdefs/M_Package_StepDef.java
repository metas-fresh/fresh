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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.deliveryplanning.M_ShipperTransportation_StepDefData;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;

import java.sql.Timestamp;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_Package_StepDef
{
	private final M_Package_StepDefData packageTable;
	private final M_Shipper_StepDefData shipperTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	private final M_Product_StepDefData productTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_Package_StepDef(
			@NonNull final M_Package_StepDefData packageTable,
			@NonNull final M_Shipper_StepDefData shipperTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final M_ShipperTransportation_StepDefData deliveryInstructionTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.packageTable = packageTable;
		this.shipperTable = shipperTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.bPartnerTable = bPartnerTable;
		this.deliveryInstructionTable = deliveryInstructionTable;
		this.productTable = productTable;
	}

	@And("^load M_Package for M_ShipperTransportation: (.*)$")
	public void load_M_Package(@NonNull final String deliveryInstructionIdentifier, @NonNull final DataTable dataTable)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);
		assertThat(deliveryInstruction).isNotNull();

		final I_M_ShippingPackage shippingPackage = queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstruction.getM_ShipperTransportation_ID())
				.create()
				.firstOnlyNotNull(I_M_ShippingPackage.class);

		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShippingPackage.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			softly.assertThat(product).isNotNull();

			softly.assertThat(shippingPackage.getM_Product_ID()).as(I_M_ShippingPackage.COLUMNNAME_M_Product_ID).isEqualTo(product.getM_Product_ID());

			final I_M_Package packageRecord =  InterfaceWrapperHelper.load(shippingPackage.getM_Package_ID(), I_M_Package.class);
			softly.assertThat(packageRecord).isNotNull();

			final String packageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Package.COLUMNNAME_M_Package_ID + "." + TABLECOLUMN_IDENTIFIER);
			packageTable.putOrReplace(packageIdentifier, packageRecord);
		}

		softly.assertAll();
	}

	@And("validate M_Package:")
	public void validate_M_Package(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final SoftAssertions softly = new SoftAssertions();

			final String packageIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Package.COLUMNNAME_M_Package_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Package packageRecord = packageTable.get(packageIdentifier);
			softly.assertThat(packageRecord).isNotNull();

			final String shipperIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Package.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
			softly.assertThat(shipper).isNotNull();
			softly.assertThat(packageRecord.getM_Shipper_ID()).as(I_M_Package.COLUMNNAME_M_Shipper_ID).isEqualTo(shipper.getM_Shipper_ID());

			final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Package.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bpartnerIdentifier))
			{
				final I_C_BPartner bPartner = bPartnerTable.get(bpartnerIdentifier);
				softly.assertThat(bPartner).isNotNull();
				softly.assertThat(packageRecord.getC_BPartner_ID()).as(I_M_Package.COLUMNNAME_C_BPartner_ID).isEqualTo(bPartner.getC_BPartner_ID());
			}

			final String bpartnerLocIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Package.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bpartnerLocIdentifier))
			{
				final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bpartnerLocIdentifier);
				softly.assertThat(bPartnerLocation).isNotNull();
				softly.assertThat(packageRecord.getC_BPartner_Location_ID()).as(I_M_Package.COLUMNNAME_C_BPartner_Location_ID).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());
			}

			final Timestamp shipDate = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_M_Package.COLUMNNAME_ShipDate);
			if (shipDate != null)
			{
				softly.assertThat(packageRecord.getShipDate()).as(I_M_Package.COLUMNNAME_ShipDate).isEqualTo(shipDate);
			}

			softly.assertAll();
		}
	}
}
