package de.metas.shipping.api;

import de.metas.document.DocTypeId;
import de.metas.shipping.model.MMShipperTransportation;
import org.compiere.model.I_M_Package;

import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.ISingletonService;

public interface IShipperTransportationBL extends ISingletonService
{
	/**
	 * Links given {@link I_M_Package} to shipper transportation.
	 *
	 * @param shipperTransportation
	 * @param mpackage
	 * @return
	 */
	I_M_ShippingPackage createShippingPackage(I_M_ShipperTransportation shipperTransportation, I_M_Package mpackage);

	/**
	 * Finds and set suitable document type to given shipper transportation.
	 *
	 * @param shipperTransportation
	 */
	void setC_DocType(I_M_ShipperTransportation shipperTransportation);

	boolean isDeliveryInstruction(DocTypeId docTypeId);
}
