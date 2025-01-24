/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.copy_with_details.CopyRecordRequest;
import de.metas.copy_with_details.CopyRecordService;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import de.metas.cucumber.stepdefs.sectioncode.M_SectionCode_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.process.C_Order_CreatePOFromSOs;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_SectionCode;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocBaseType;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocSubType;
import static org.compiere.model.I_C_Order.COLUMNNAME_AD_Org_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_Bill_BPartner_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_Bill_Location_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_Bill_User_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_Order_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_DocStatus;
import static org.compiere.model.I_C_Order.COLUMNNAME_DropShip_BPartner_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_DropShip_Location_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_Link_Order_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_PricingSystem_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_Warehouse_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_PaymentRule;
import static org.compiere.model.I_C_Order.COLUMNNAME_Processing;

public class C_Order_StepDef
{
	private final Logger logger = LogManager.getLogger(C_Order_StepDef.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final CopyRecordService copyRecordService = SpringContextHolder.instance.getBean(CopyRecordService.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

	private final C_BPartner_StepDefData bpartnerTable;
	private final C_Order_StepDefData orderTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final AD_User_StepDefData userTable;
	private final M_PricingSystem_StepDefData pricingSystemDataTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final AD_Org_StepDefData orgTable;

	public C_Order_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_BPartner_Location_StepDefData bpartnerLocationTable,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final M_PricingSystem_StepDefData pricingSystemDataTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.orderTable = orderTable;
		this.bpartnerLocationTable = bpartnerLocationTable;
		this.userTable = userTable;
		this.pricingSystemDataTable = pricingSystemDataTable;
		this.warehouseTable = warehouseTable;
		this.orgTable = orgTable;
	}

	@Given("metasfresh contains C_Orders:")
	public void metasfresh_contains_c_orders(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_C_Order_ID)
				.forEach(tableRow -> {
					final String poReference = tableRow.getAsOptionalName(I_C_Order.COLUMNNAME_POReference).orElse(null);
					final int paymentTermId = tableRow.getAsOptionalInt(I_C_Order.COLUMNNAME_C_PaymentTerm_ID).orElse(-1);
					final String pricingSystemIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_PricingSystem_ID + "." + TABLECOLUMN_IDENTIFIER);
					final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocBaseType);

					final int dropShipPartnerId = DataTableUtil.extractIntOrMinusOneForColumnName(tableRow, "OPT." + COLUMNNAME_DropShip_BPartner_ID);
					final boolean isDropShip = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_Order.COLUMNNAME_IsDropShip, false);

					final int orgId = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER))
							.map(orgTable::get)
							.map(I_AD_Org::getAD_Org_ID)
							.orElse(StepDefConstants.ORG_ID.getRepoId());

					final StepDefDataIdentifier bpartnerIdentifier = tableRow.getAsIdentifier(COLUMNNAME_C_BPartner_ID);
					final BPartnerId bpartnerId = bpartnerTable.getIdOptional(bpartnerIdentifier)
							.orElseGet(() -> bpartnerIdentifier.getAsId(BPartnerId.class));

					final I_C_Order order = newInstance(I_C_Order.class);
					order.setC_BPartner_ID(bpartnerId.getRepoId());
					order.setIsSOTrx(tableRow.getAsBoolean(I_C_Order.COLUMNNAME_IsSOTrx));
					order.setDateOrdered(tableRow.getAsLocalDateTimestamp(I_C_Order.COLUMNNAME_DateOrdered));
					order.setDropShip_BPartner_ID(dropShipPartnerId);
					order.setIsDropShip(isDropShip);
					order.setAD_Org_ID(orgId);

					if (paymentTermId > 0)
					{
						order.setC_PaymentTerm_ID(paymentTermId);
					}

					tableRow.getAsOptionalIdentifier(I_C_Order.COLUMNNAME_C_BPartner_Location_ID)
							.map(bpartnerLocationTable::getId)
							.ifPresent(bpLocationId -> order.setC_BPartner_Location_ID(bpLocationId.getRepoId()));

					final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
					if (Check.isNotBlank(userIdentifier))
					{
						final I_AD_User user = userTable.get(userIdentifier);
						order.setAD_User_ID(user.getAD_User_ID());
					}

					final String billBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
					if (Check.isNotBlank(billBPartnerIdentifier))
					{
						final I_C_BPartner billBPartner = bpartnerTable.get(billBPartnerIdentifier);
						order.setC_BPartner_ID(billBPartner.getC_BPartner_ID());
					}

					final String bpBillLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
					if (Check.isNotBlank(bpBillLocationIdentifier))
					{
						final I_C_BPartner_Location billBPartnerLocation = bpartnerLocationTable.get(bpBillLocationIdentifier);
						order.setBill_BPartner_ID(billBPartnerLocation.getC_BPartner_ID());
						order.setBill_Location_ID(billBPartnerLocation.getC_BPartner_Location_ID());
					}

					final String deliveryRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_DeliveryRule);
					if (Check.isNotBlank(deliveryRule))
					{
						// note that IF the C_BPartner has a deliveryRule set (not-mandatory there), this values will be overwritten by it
						order.setDeliveryRule(deliveryRule);
					}

					final String deliveryViaRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_DeliveryViaRule);
					if (Check.isNotBlank(deliveryViaRule))
					{
						order.setDeliveryViaRule(deliveryViaRule);
					}

					final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_InvoiceRule);
					if (Check.isNotBlank(invoiceRule))
					{
						order.setInvoiceRule(invoiceRule);
					}

					final String paymentTermValue = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_C_PaymentTerm_ID + ".Value");
					if (de.metas.util.Check.isNotBlank(paymentTermValue))
					{
						final I_C_PaymentTerm paymentTerm = queryBL.createQueryBuilder(I_C_PaymentTerm.class)
								.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Value, paymentTermValue)
								.create()
								.firstOnlyNotNull(I_C_PaymentTerm.class);

						order.setC_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());
					}

					final String email = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_EMail);
					if (Check.isNotBlank(email))
					{
						order.setEMail(email);
					}

					final Instant preparationDate = tableRow.getAsOptionalInstant(I_C_Order.COLUMNNAME_PreparationDate).orElse(null);
					final Instant datePromised = tableRow.getAsOptionalInstant(I_C_Order.COLUMNNAME_DatePromised).orElse(null);

					final Instant preparationDateToBeSet = CoalesceUtil.coalesce(preparationDate, datePromised);
					if (preparationDateToBeSet != null)
					{
						order.setPreparationDate(Timestamp.from(preparationDateToBeSet));
					}

					final Instant datePromisedToBeSet = CoalesceUtil.coalesce(datePromised, preparationDate);
					if (datePromisedToBeSet != null)
					{
						order.setDatePromised(Timestamp.from(datePromisedToBeSet));
					}

					if (EmptyUtil.isNotBlank(poReference))
					{
						order.setPOReference(poReference);
					}

					if (EmptyUtil.isNotBlank(pricingSystemIdentifier))
					{
						final I_M_PricingSystem pricingSystem = pricingSystemDataTable.get(pricingSystemIdentifier);
						assertThat(pricingSystem).isNotNull();
						order.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());

					}

					if (EmptyUtil.isNotBlank(docBaseType))
					{
						final String docSubType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocSubType);

						final I_C_DocType docType = queryBL.createQueryBuilder(I_C_DocType.class)
								.addEqualsFilter(COLUMNNAME_DocBaseType, docBaseType)
								.addEqualsFilter(COLUMNNAME_DocSubType, docSubType)
								.create()
								.firstOnlyNotNull(I_C_DocType.class);

						assertThat(docType).isNotNull();

						order.setC_DocType_ID(docType.getC_DocType_ID());
						order.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
					}

					final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PaymentRule);
					if (Check.isNotBlank(paymentRule))
					{
						order.setPaymentRule(paymentRule);
					}

					tableRow.getAsOptionalIdentifier(COLUMNNAME_M_Warehouse_ID)
							.map(warehouseIdentifier -> warehouseTable.getIdOptional(warehouseIdentifier).orElseGet(() -> warehouseIdentifier.getAsId(WarehouseId.class)))
							.ifPresent(warehouseId -> order.setM_Warehouse_ID(warehouseId.getRepoId()));

					final String billUserIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
					if (Check.isNotBlank(billUserIdentifier))
					{
						final I_AD_User billUser = userTable.get(billUserIdentifier);
						order.setBill_User_ID(billUser.getAD_User_ID());
					}

					final String dropShipLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DropShip_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
					if (Check.isNotBlank(dropShipLocationIdentifier))
					{
						final I_C_BPartner_Location dropShipLocation = bpartnerLocationTable.get(dropShipLocationIdentifier);
						order.setDropShip_Location_ID(dropShipLocation.getC_BPartner_Location_ID());
						order.setDropShip_BPartner_ID(dropShipLocation.getC_BPartner_ID());
					}

					saveRecord(order);

					orderTable.putOrReplace(tableRow.getAsIdentifier(), order);
				});
	}

	@And("^the order identified by (.*) is (reactivated|completed|closed|voided|reversed)$")
	public void order_action(@NonNull final String orderIdentifier, @NonNull final String action)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case reactivated:
				order.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
				documentBL.processEx(order, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
				logger.info("Order {} was reactivated", order);
				break;
			case completed:
				order.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
				documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				logger.info("Order {} was completed", order);
				break;
			case closed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Close, IDocument.STATUS_Closed);
				break;
			case voided:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Void, IDocument.STATUS_Voided);
				break;
			case reversed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
				break;
			default:
				throw new AdempiereException("Unhandled C_Order action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	@Given("generate PO from SO is invoked with parameters:")
	public void generate_PO_from_SO_invoked(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final String purchaseType = DataTableUtil.extractStringForColumnName(tableRow, "PurchaseType");
			final boolean purchaseBomComponents = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "IsPurchaseBOMComponents", false);

			final I_C_Order order = orderTable.get(orderIdentifier);
			final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);

			final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(C_Order_CreatePOFromSOs.class);

			final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder();
			processInfoBuilder.setAD_Process_ID(processId.getRepoId());
			processInfoBuilder.addParameter("DatePromised_From", Timestamp.from(Instant.now()));
			processInfoBuilder.addParameter("DatePromised_To", Timestamp.from(Instant.now()));
			processInfoBuilder.addParameter("C_BPartner_ID", bpartner.getC_BPartner_ID());
			processInfoBuilder.addParameter("C_Order_ID", order.getC_Order_ID());
			processInfoBuilder.addParameter("TypeOfPurchase", purchaseType);
			processInfoBuilder.addParameter("IsPurchaseBOMComponents", purchaseBomComponents);

			processInfoBuilder
					.buildAndPrepareExecution()
					.executeSync()
					.getResult();
		}

	}

	@Then("the order is created:")
	public void thePurchaseOrderIsCreated(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String linkedOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Link_Order_ID + ".Identifier");

			final I_C_Order purchaseOrder = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_Order.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Order.COLUMNNAME_Link_Order_ID, orderTable.get(linkedOrderIdentifier).getC_Order_ID())
					.create()
					.firstOnly(I_C_Order.class);

			final boolean isSOTrx = DataTableUtil.extractBooleanForColumnName(tableRow, I_C_Order.COLUMNNAME_IsSOTrx);
			softly.assertThat(purchaseOrder).isNotNull();
			softly.assertThat(purchaseOrder.isSOTrx()).as(I_C_Order.COLUMNNAME_IsSOTrx).isEqualTo(isSOTrx);

			final I_C_DocType docType = load(purchaseOrder.getC_DocTypeTarget_ID(), I_C_DocType.class);

			final String docBaseType = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocBaseType);
			softly.assertThat(docType.getDocBaseType()).as(COLUMNNAME_DocBaseType).isEqualTo(docBaseType);

			final String docSubType = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_DocSubType);
			softly.assertThat(docType.getDocSubType()).as(COLUMNNAME_DocSubType).isEqualTo(docSubType);

			final String docStatus = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocStatus);
			if (docStatus != null)
			{
				softly.assertThat(purchaseOrder.getDocStatus()).as(COLUMNNAME_DocStatus).isEqualTo(docStatus);
			}

			final boolean isDropShip = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_Order.COLUMNNAME_IsDropShip, false);
			softly.assertThat(purchaseOrder.isDropShip()).as(I_C_Order.COLUMNNAME_IsDropShip).isEqualTo(isDropShip);

			final String dropShipBPIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Order.COLUMNNAME_DropShip_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropShipBPIdentifier))
			{
				final I_C_BPartner dropShipBP = bpartnerTable.get(dropShipBPIdentifier);
				softly.assertThat(dropShipBP).isNotNull();

				softly.assertThat(purchaseOrder.getDropShip_BPartner_ID()).as(I_C_Order.COLUMNNAME_DropShip_BPartner_ID).isEqualTo(dropShipBP.getC_BPartner_ID());
			}

			final String dropShipLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DropShip_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropShipLocationIdentifier))
			{
				final I_C_BPartner_Location dropShipBPLocation = bpartnerLocationTable.get(dropShipLocationIdentifier);
				softly.assertThat(dropShipBPLocation).isNotNull();

				softly.assertThat(purchaseOrder.getDropShip_Location_ID()).as(COLUMNNAME_DropShip_Location_ID).isEqualTo(dropShipBPLocation.getC_BPartner_Location_ID());
				softly.assertThat(purchaseOrder.getDropShip_BPartner_ID()).as(I_C_Order.COLUMNNAME_DropShip_BPartner_ID).isEqualTo(dropShipBPLocation.getC_BPartner_ID());
			}
		}

		softly.assertAll();
	}

	@Then("the sales order identified by {string} is closed")
	public void salesOrderIsClosed(
			@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final I_C_Order salesOrder = orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()));

		assertThat(salesOrder.getDocStatus()).isEqualTo(IDocument.STATUS_Closed);
	}

	@Then("the sales order identified by {string} is not closed")
	public void salesOrderIsNotClosed(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final I_C_Order salesOrder = orderBL.getById(OrderId.ofRepoId(order.getC_Order_ID()));

		assertThat(salesOrder.getDocStatus()).isNotEqualTo(IDocument.STATUS_Closed);
	}

	@Then("a PurchaseOrder with externalId {string} is created after not more than {int} seconds and has values")
	public void verifyOrder(final String externalId, final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final Map<String, String> dataTableRow = dataTable.asMaps().get(0);

		final Supplier<Boolean> purchaseOrderQueryExecutor = () -> {

			final I_C_Order purchaseOrderRecord = queryBL.createQueryBuilder(I_C_Order.class)
					.addEqualsFilter(I_C_Order.COLUMNNAME_ExternalId, externalId)
					.create()
					.firstOnly(I_C_Order.class);

			return purchaseOrderRecord != null;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, purchaseOrderQueryExecutor);

		final I_C_Order purchaseOrderRecord = queryBL.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_ExternalId, externalId)
				.create()
				.firstOnlyNotNull(I_C_Order.class);

		final String externalPurchaseOrderUrl = DataTableUtil.extractStringForColumnName(dataTableRow, I_C_Order.COLUMNNAME_ExternalPurchaseOrderURL);
		assertThat(purchaseOrderRecord.getExternalPurchaseOrderURL()).isEqualTo(externalPurchaseOrderUrl);

		final String poReference = DataTableUtil.extractStringForColumnName(dataTableRow, I_C_Order.COLUMNNAME_POReference);
		assertThat(purchaseOrderRecord.getPOReference()).isEqualTo(poReference);

		final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (EmptyUtil.isNotBlank(orderIdentifier))
		{
			orderTable.putOrReplace(orderIdentifier, purchaseOrderRecord);
		}
	}

	@And("validate the created orders")
	public void validate_created_order(
			@NonNull final DataTable table)
	{
		final Map<String, String> row = table.asMaps().get(0);
		validateOrder(row);
	}

	@And("update order")
	public void update_order(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Order order = orderTable.get(orderIdentifier);

			final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocBaseType);
			final String docSubType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_DocSubType);

			if (Check.isNotBlank(docBaseType) && Check.isNotBlank(docSubType))
			{
				final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
						.docBaseType(docBaseType)
						.docSubType(docSubType)
						.adClientId(order.getAD_Client_ID())
						.adOrgId(order.getAD_Org_ID())
						.build();

				final DocTypeId docTypeId = docTypeDAO.getDocTypeId(docTypeQuery);

				order.setC_DocType_ID(docTypeId.getRepoId());
				order.setC_DocTypeTarget_ID(docTypeId.getRepoId());
			}

			final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PaymentRule);
			if (Check.isNotBlank(paymentRule))
			{
				order.setPaymentRule(paymentRule);
			}

			InterfaceWrapperHelper.saveRecord(order);

			orderTable.putOrReplace(orderIdentifier, order);
		}
	}

	@And("^after not more than (.*)s the order is found$")
	public void lookup_order(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> retrieveOrder(tableRow));
		}
	}

	private void validateOrder(
			@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);

		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer expectedBPartnerId = bpartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));

		final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer expectedBPartnerLocation = bpartnerLocationTable.getOptional(bpartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerLocationIdentifier));
		final Timestamp dateOrdered = DataTableUtil.extractDateTimestampForColumnName(row, "dateordered");
		final String docbasetype = DataTableUtil.extractStringForColumnName(row, "docbasetype");
		final String currencyCode = DataTableUtil.extractStringForColumnName(row, "currencyCode");
		final String deliveryRule = DataTableUtil.extractStringForColumnName(row, "deliveryRule");
		final String deliveryViaRule = DataTableUtil.extractStringForColumnName(row, "deliveryViaRule");
		final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(row, "processed", false);
		final String externalId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_ExternalId);
		final String docStatus = DataTableUtil.extractStringForColumnName(row, "docStatus");
		final String bpartnerName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_BPartnerName);

		final I_C_Order order = orderTable.get(identifier);
		InterfaceWrapperHelper.refresh(order);

		if (Check.isNotBlank(externalId))
		{
			assertThat(order.getExternalId()).isEqualTo(externalId);
		}
		assertThat(order.getC_BPartner_ID()).isEqualTo(expectedBPartnerId);
		assertThat(order.getC_BPartner_Location_ID()).isEqualTo(expectedBPartnerLocation);
		assertThat(order.getDateOrdered()).isEqualTo(dateOrdered);
		assertThat(order.getDeliveryRule()).isEqualTo(deliveryRule);
		assertThat(order.getDeliveryViaRule()).isEqualTo(deliveryViaRule);
		assertThat(order.isProcessed()).isEqualTo(processed);
		assertThat(order.getDocStatus()).isEqualTo(docStatus);

		if (Check.isNotBlank(bpartnerName))
		{
			assertThat(order.getBPartnerName()).isEqualTo(bpartnerName);
		}

		final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(currencyCode));
		assertThat(order.getC_Currency_ID()).isEqualTo(currency.getId().getRepoId());

		final I_C_DocType docType = docTypeDAO.getById(order.getC_DocType_ID());
		assertThat(docType).isNotNull();
		assertThat(docType.getDocBaseType()).isEqualTo(docbasetype);

		final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(userIdentifier))
		{
			final I_AD_User user = userTable.get(userIdentifier);
			assertThat(user).isNotNull();

			assertThat(order.getAD_User_ID()).isEqualTo(user.getAD_User_ID());
		}

		final String bpBillIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpBillIdentifier))
		{
			final I_C_BPartner billBPRecord = bpartnerTable.get(bpBillIdentifier);
			assertThat(billBPRecord).isNotNull();

			assertThat(order.getBill_BPartner_ID()).isEqualTo(billBPRecord.getC_BPartner_ID());
		}

		final String bpBillLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpBillLocationIdentifier))
		{
			final I_C_BPartner_Location billBPLocationRecord = bpartnerLocationTable.get(bpBillLocationIdentifier);
			assertThat(billBPLocationRecord).isNotNull();

			assertThat(order.getBill_Location_ID()).isEqualTo(billBPLocationRecord.getC_BPartner_Location_ID());
		}

		final String billUserIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(billUserIdentifier))
		{
			final I_AD_User billUser = userTable.get(billUserIdentifier);
			assertThat(billUser).isNotNull();

			assertThat(order.getBill_User_ID()).isEqualTo(billUser.getAD_User_ID());
		}

		final String email = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_EMail);
		if (Check.isNotBlank(email))
		{
			assertThat(order.getEMail()).isEqualTo(email);
		}

		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_InvoiceRule);
		if (Check.isNotBlank(invoiceRule))
		{
			assertThat(order.getInvoiceRule()).isEqualTo(invoiceRule);
		}

		final String paymentTermValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_C_PaymentTerm_ID + ".Value");
		if (de.metas.util.Check.isNotBlank(paymentTermValue))
		{
			final I_C_PaymentTerm paymentTerm = queryBL.createQueryBuilder(I_C_PaymentTerm.class)
					.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Value, paymentTermValue)
					.create()
					.firstOnlyNotNull(I_C_PaymentTerm.class);

			assertThat(order.getC_PaymentTerm_ID()).isEqualTo(paymentTerm.getC_PaymentTerm_ID());
		}

		final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_PaymentRule);
		if (Check.isNotBlank(paymentRule))
		{
			assertThat(order.getPaymentRule()).isEqualTo(paymentRule);
		}

		final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_POReference);
		if (Check.isNotBlank(poReference))
		{
			assertThat(order.getPOReference()).isEqualTo(poReference);
		}

		final String internalName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_AD_InputDataSource_ID + "." + I_AD_InputDataSource.COLUMNNAME_InternalName);
		if (Check.isNotBlank(internalName))
		{
			final I_AD_InputDataSource dataSource = inputDataSourceDAO.retrieveInputDataSource(Env.getCtx(), internalName, true, Trx.TRXNAME_None);
			assertThat(order.getAD_InputDataSource_ID()).isEqualTo(dataSource.getAD_InputDataSource_ID());
		}
	}

	@Then("the following group compensation order lines were created for externalHeaderId: {string}")
	public void verifyOrderLines(final String externalHeaderId,
			@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final int line = DataTableUtil.extractIntForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_Line);
			final Boolean isGroupCompensationLine = DataTableUtil.extractBooleanForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine);
			final BigDecimal groupCompensationPercentage = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage);
			final String groupCompensationType = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_GroupCompensationType);
			final String groupCompensationAmtType = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_GroupCompensationAmtType);

			final I_C_Order orderRecord = queryBL.createQueryBuilder(I_C_Order.class)
					.addEqualsFilter(I_C_Order.COLUMNNAME_ExternalId, externalHeaderId)
					.create()
					.firstOnlyNotNull(I_C_Order.class);

			final Optional<I_C_OrderLine> orderLine = queryBL.createQueryBuilder(I_C_OrderLine.class)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_Line, line)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, isGroupCompensationLine)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage, groupCompensationPercentage)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_GroupCompensationType, groupCompensationType)
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_GroupCompensationAmtType, groupCompensationAmtType)
					.create()
					.firstOnlyOptional(I_C_OrderLine.class);

			assertThat(orderLine).isPresent();
		}
	}

	@When("C_Order is cloned")
	public void clone_order(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String clonedOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "ClonedOrder." + COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Order order = orderTable.get(orderIdentifier);

			assertThat(order).isNotNull();

			final TableRecordReference recordReference = TableRecordReference.of(I_C_Order.Table_Name, order.getC_Order_ID());
			final CopyRecordRequest copyRecordRequest = CopyRecordRequest.builder()
					.tableRecordReference(recordReference)
					.build();

			final PO po = copyRecordService.copyRecord(copyRecordRequest);

			final I_C_Order clonedOrder = load(po.get_ID(), I_C_Order.class);
			orderTable.putOrReplace(clonedOrderIdentifier, clonedOrder);
		}
	}

	@NonNull
	private Boolean retrieveOrder(@NonNull final Map<String, String> row)
	{
		final String docStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_DocStatus);

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Order order = orderTable.get(orderIdentifier);

		final I_C_Order orderRecord = queryBL.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.addEqualsFilter(COLUMNNAME_DocStatus, docStatus)
				.addEqualsFilter(COLUMNNAME_Processing, false)
				.create()
				.firstOnlyOrNull(I_C_Order.class);

		if (orderRecord == null)
		{
			return false;
		}

		orderTable.putOrReplace(orderIdentifier, orderRecord);
		return true;
	}
}
