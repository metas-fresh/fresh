package de.metas.purchasecandidate.purchaseordercreation.localorder;

import de.metas.adempiere.model.I_M_Product;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.document.engine.DocStatus;
import de.metas.document.references.zoom_into.NullCustomizedWindowInfoMapRepository;
import de.metas.email.MailService;
import de.metas.notification.INotificationRepository;
import de.metas.notification.impl.NotificationRepository;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.impl.OrderLineBL;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.document.dimension.PurchaseCandidateDimensionFactory;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.quantity.Quantity;
import de.metas.user.UserGroupRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PurchaseOrderFromItemsAggregatorTest
{
	private I_C_UOM EACH;
	private Quantity TEN;

	private Dimension dimension;

	private static class MockedOrderLineBL extends OrderLineBL
	{
		private int updatePricesCallCount;

		@Override
		public void updatePrices(@NonNull OrderLinePriceUpdateRequest request)
		{
			// mock IOrderLineBL.updatePrices() because
			// setting up the required masterdata and testing the pricing engine is out of scope.

			updatePricesCallCount++;
		}
	}

	private MockedOrderLineBL orderLineBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.EACH = createUOM("Ea");
		this.TEN = Quantity.of(BigDecimal.TEN, EACH);

		orderLineBL = new MockedOrderLineBL();
		Services.registerService(IOrderLineBL.class, orderLineBL);

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new PurchaseCandidateDimensionFactory());
		dimensionFactories.add(new OrderLineDimensionFactory());

		SpringContextHolder.registerJUnitBean(new DimensionService(dimensionFactories));
		Services.registerService(INotificationRepository.class, new NotificationRepository(AttachmentEntryService.createInstanceForUnitTesting(), NullCustomizedWindowInfoMapRepository.instance));
		SpringContextHolder.registerJUnitBean(MailService.newInstanceForUnitTesting());
		SpringContextHolder.registerJUnitBean(new UserGroupRepository());

		dimension = createDimension();

		SpringContextHolder.registerJUnitBean(new OrderLineDetailRepository());
	}

	private Dimension createDimension()
	{
		return Dimension.builder()
				.userElementString1("test1")
				.build();
	}

	private I_C_UOM createUOM(final String name)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		save(uom);
		return uom;
	}

	@Test
	public void test()
	{
		// will be needed for user notification (CreatedBy)
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		save(salesOrder);

		// needed to construct the user notification message
		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setValue("Vendor");
		vendor.setName("Vendor");
		save(vendor);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(EACH.getC_UOM_ID());
		saveRecord(productRecord);

		final ProductAndCategoryAndManufacturerId product = ProductAndCategoryAndManufacturerId.of(productRecord.getM_Product_ID(), 30, 35);

		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.product(product)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(40))
				.vendorId(BPartnerId.ofRepoId(vendor.getC_BPartner_ID()))
				.defaultVendor(false)
				.vendorProductNo("productNo")
				.vendorProductName("productName")
				.pricingConditions(PricingConditions.builder().build())
				.build();

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.groupReference(DemandGroupReference.EMPTY)
				.orgId(OrgId.ofRepoId(Env.CTXVALUE_AD_Org_ID_Any))
				.purchaseDatePromised(SystemTime.asZonedDateTime())
				.vendorId(vendorProductInfo.getVendorId())
				.aggregatePOs(vendorProductInfo.isAggregatePOs())
				.productId(vendorProductInfo.getProductId())
				.attributeSetInstanceId(vendorProductInfo.getAttributeSetInstanceId())
				.vendorProductNo(vendorProductInfo.getVendorProductNo())
				.qtyToPurchase(TEN)
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(salesOrder.getC_Order_ID(), 50))
				.warehouseId(WarehouseId.ofRepoId(60))
				.profitInfoOrNull(PurchaseCandidateTestTool.createPurchaseProfitInfo())
				.dimension(dimension)
				.build();

		final PurchaseOrderFromItemsAggregator aggregator = PurchaseOrderFromItemsAggregator.newInstance();

		Services.get(ITrxManager.class).runInNewTrx(() -> {
			aggregator.add(PurchaseOrderItem.builder()
					.purchaseCandidate(purchaseCandidate)
					.datePromised(de.metas.common.util.time.SystemTime.asZonedDateTime())
					.purchasedQty(TEN)
					.remotePurchaseOrderId(NullVendorGatewayInvoker.NO_REMOTE_PURCHASE_ID)
					.dimension(dimension)
					.build());

			aggregator.closeAllGroups();
		});

		final List<I_C_Order> createdPurchaseOrders = aggregator.getCreatedPurchaseOrders();
		assertThat(createdPurchaseOrders).hasSize(1);

		final I_C_Order purchaseOrder = createdPurchaseOrders.get(0);
		assertThat(purchaseOrder.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());

		// these properties are currently set by MOrder.beforeSafe, which is not called in our test
		// assertThat(purchaseOrder.getM_PricingSystem_ID()).isGreaterThan(0);
		// assertThat(purchaseOrder.getM_PriceList_ID()).isGreaterThan(0);
		// assertThat(purchaseOrder.getC_Currency_ID()).isGreaterThan(0);

		assertThat(orderLineBL.updatePricesCallCount).isEqualTo(1);
	}
}
