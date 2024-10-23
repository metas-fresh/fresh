package org.adempiere.warehouse.spi.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.lang.SOTrx;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/**
 * Default implementation of {@link IWarehouseAdvisor}.
 * <p>
 * It's just fetching the warehouse from record's M_Warehouse_ID field and if nothing found then organization's warehouse is returned.
 *
 * @author tsa
 */
public class WarehouseAdvisor implements IWarehouseAdvisor
{
	final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@Override
	public WarehouseId evaluateWarehouse(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLineRecord = load(orderLineId, I_C_OrderLine.class);
		return evaluateWarehouse(orderLineRecord);
	}

	@Override
	public WarehouseId evaluateWarehouse(@NonNull final I_C_OrderLine orderLine)
	{
		final WarehouseId orderLineWarehouseId = WarehouseId.ofRepoIdOrNull(orderLine.getM_Warehouse_ID());
		if (orderLineWarehouseId != null)
		{
			return orderLineWarehouseId;
		}

		return evaluateOrderWarehouse(orderLine.getC_Order());
	}

	@Override
	public WarehouseId evaluateOrderWarehouse(@NonNull final I_C_Order order)
	{
		final WarehouseId orderWarehouseId = WarehouseId.ofRepoIdOrNull(order.getM_Warehouse_ID());
		if (orderWarehouseId != null)
		{
			return orderWarehouseId;
		}

		return findOrderWarehouseId(order);
	}

	protected WarehouseId findOrderWarehouseId(@NonNull final I_C_Order order)
	{

		final OrgId adOrgId = OrgId.ofRepoId(order.getAD_Org_ID());

		final boolean isSOTrx = order.isSOTrx();
		// task 07014: for a dropship purchase order, we take the org info's dropship warehouse. our vendor will send the good directly to our customer and it will never enter any of our physical
		// warehouses, but none the less we own it for a certain time. That'S what the dropship warehouse is for.
		// For a sales order, "dropship" means that the order's receiver is someone other than the partner who ordered. For this scenario, we don't need a particular dropship warehouse.

		if (order.isDropShip() && !isSOTrx)
		{
			final WarehouseId dropShipWarehouseId = orgDAO.getOrgDropshipWarehouseId(adOrgId);
			if (dropShipWarehouseId == null)
			{
				final String orgName = orgDAO.retrieveOrgName(adOrgId);
				throw new AdempiereException("@NotFound@ @DropShip_Warehouse_ID@ (@AD_Org_ID@: " + orgName + ")");
			}
			return dropShipWarehouseId;
		}

		// first check for picking warehouse
		// this check is valid only for sales order; for purchase order will return null
		final WarehouseId pickingWarehouseId = findPickingWarehouseId(order);
		if (pickingWarehouseId != null)
		{
			return pickingWarehouseId;
		}

		final WarehouseId purchaseWarehouseId = findPurchaseWarehouseId(order);
		if (purchaseWarehouseId != null)
		{
			return purchaseWarehouseId;
		}

		final WarehouseId orgPOWarehouseId = orgDAO.getOrgPOWarehouseId(adOrgId);

		return !isSOTrx && orgPOWarehouseId != null ? orgPOWarehouseId : orgDAO.getOrgWarehouseId(adOrgId);
	}

	@Nullable
	private WarehouseId findPurchaseWarehouseId(final @NonNull I_C_Order order)
	{
		if (order.isSOTrx())
		{
			return null;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return null;
		}

		return findWarehouseIdForSOTrx(bpartnerId, SOTrx.PURCHASE);
	}

	/**
	 * Retrieve the picking warehouse based on the order's bPartner. Returns <code>null</code> if the partner is not customer, has no warehouse assigned or the order is not a sales order.
	 */
	@Nullable
	private WarehouseId findPickingWarehouseId(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return null;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return null;
		}

		final WarehouseId customerWarehouseId = findWarehouseIdForSOTrx(bpartnerId, SOTrx.SALES);
		if (customerWarehouseId != null && isPickingWarehouse(customerWarehouseId))
		{
			return customerWarehouseId;
		}

		return null;
	}

	@Nullable
	private WarehouseId findWarehouseIdForSOTrx(@NonNull final BPartnerId bpartnerId, @NonNull final SOTrx soTrx)
	{
		final I_C_BPartner bp = partnerDAO.getById(bpartnerId);

		if (soTrx.isSales() && bp.isCustomer())
		{
			return WarehouseId.ofRepoIdOrNull(bp.getM_Warehouse_ID());
		}
		else if (soTrx.isPurchase() && bp.isVendor())
		{
			return WarehouseId.ofRepoIdOrNull(bp.getM_WarehousePO_ID());
		}

		return null;
	}

	private boolean isPickingWarehouse(final WarehouseId warehouseId)
	{

		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);
		return warehouse.isPickingWarehouse();
	}
}
