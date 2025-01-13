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

package de.metas.cucumber.stepdefs.deliveryplanning;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Shipper_StepDefData;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Shipper;

import java.sql.Timestamp;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_ShipperTransportation_StepDef
{
	private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	private final M_Shipper_StepDefData shipperTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_BPartner_StepDefData bPartnerTable;

	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	public final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_ShipperTransportation_StepDef(
			@NonNull final M_ShipperTransportation_StepDefData deliveryInstructionTable,
			@NonNull final M_Delivery_Planning_StepDefData deliveryPlanningTable,
			@NonNull final M_Shipper_StepDefData shipperTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.deliveryInstructionTable = deliveryInstructionTable;
		this.deliveryPlanningTable = deliveryPlanningTable;
		this.shipperTable = shipperTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.bPartnerTable = bPartnerTable;
	}

	@And("generate M_ShipperTransportation for M_Delivery_Planning:")
	public void generate_Delivery_Instructions(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryPlanningIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningTable.get(deliveryPlanningIdentifier);
			assertThat(deliveryPlanning).isNotNull();

			deliveryPlanningService.generateDeliveryInstructions(getQueryFilterFor(deliveryPlanning));

			InterfaceWrapperHelper.refresh(deliveryPlanning);

			assertThat(deliveryPlanning.getM_ShipperTransportation_ID()).isNotZero();
			final I_M_ShipperTransportation deliveryInstruction = load(deliveryPlanning.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);

			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			deliveryInstructionTable.putOrReplace(deliveryInstructionIdentifier, deliveryInstruction);
		}
	}

	@And("regenerate M_ShipperTransportation for M_Delivery_Planning:")
	public void regenerate_Delivery_Instructions(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryPlanningIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningTable.get(deliveryPlanningIdentifier);
			assertThat(deliveryPlanning).isNotNull();

			deliveryPlanningService.regenerateDeliveryInstructions(getQueryFilterFor(deliveryPlanning));

			InterfaceWrapperHelper.refresh(deliveryPlanning);

			assertThat(deliveryPlanning.getM_ShipperTransportation_ID()).isNotZero();
			final I_M_ShipperTransportation deliveryInstruction = load(deliveryPlanning.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);

			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			deliveryInstructionTable.putOrReplace(deliveryInstructionIdentifier, deliveryInstruction);
		}
	}

	@And("validate M_ShipperTransportation:")
	public void validateM_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);
			assertThat(deliveryInstruction).isNotNull();

			final SoftAssertions softly = new SoftAssertions();

			final String shipperIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
			softly.assertThat(shipper).isNotNull();
			softly.assertThat(deliveryInstruction.getM_Shipper_ID()).as(I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID).isEqualTo(shipper.getM_Shipper_ID());

			final String shipperBPIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner shipperBP = bPartnerTable.get(shipperBPIdentifier);
			softly.assertThat(shipperBP).isNotNull();
			softly.assertThat(deliveryInstruction.getShipper_BPartner_ID()).as(I_M_ShipperTransportation.COLUMNNAME_Shipper_BPartner_ID).isEqualTo(shipperBP.getC_BPartner_ID());

			final String shipperLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location shipperLocation = bPartnerLocationTable.get(shipperLocationIdentifier);
			softly.assertThat(shipperLocation).isNotNull();
			softly.assertThat(deliveryInstruction.getShipper_Location_ID()).as(I_M_ShipperTransportation.COLUMNNAME_Shipper_Location_ID).isEqualTo(shipperLocation.getC_BPartner_Location_ID());

			final String deliveryLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(deliveryLocationIdentifier))
			{
				final I_C_BPartner_Location deliveryLocation = bPartnerLocationTable.get(deliveryLocationIdentifier);
				softly.assertThat(deliveryLocation).isNotNull();
				softly.assertThat(deliveryInstruction.getC_BPartner_Location_Delivery_ID()).as(I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID).isEqualTo(deliveryLocation.getC_BPartner_Location_ID());
			}

			final String loadingLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(loadingLocationIdentifier))
			{
				final I_C_BPartner_Location loadingLocation = bPartnerLocationTable.get(loadingLocationIdentifier);
				softly.assertThat(loadingLocation).isNotNull();
				softly.assertThat(deliveryInstruction.getC_BPartner_Location_Loading_ID()).as(I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID).isEqualTo(loadingLocation.getC_BPartner_Location_ID());
			}

			final Timestamp deliveryDate = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_DeliveryDate);
			if (deliveryDate != null)
			{
				softly.assertThat(deliveryInstruction.getDeliveryDate()).as(I_M_ShipperTransportation.COLUMNNAME_DeliveryDate).isEqualTo(deliveryDate);
			}

			final String docStatus = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipperTransportation.COLUMNNAME_DocStatus);
			if (Check.isNotBlank(docStatus))
			{
				softly.assertThat(deliveryInstruction.getDocStatus()).as(I_M_ShipperTransportation.COLUMNNAME_DocStatus).isEqualTo(docStatus);
			}

			softly.assertAll();
		}
	}

	@NonNull
	private IQueryFilter<I_M_Delivery_Planning> getQueryFilterFor(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanning.getM_Delivery_Planning_ID());
	}
}
