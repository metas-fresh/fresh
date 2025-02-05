package de.metas.material;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_InOut;

@Getter
@AllArgsConstructor
public enum MovementType implements ReferenceListAwareEnum
{
	CustomerShipment(X_M_InOut.MOVEMENTTYPE_CustomerShipment, true),
	CustomerReturns(X_M_InOut.MOVEMENTTYPE_CustomerReturns, false),
	VendorReceipts(X_M_InOut.MOVEMENTTYPE_VendorReceipts, false),
	VendorReturns(X_M_InOut.MOVEMENTTYPE_VendorReturns, true),
	InventoryOut(X_M_InOut.MOVEMENTTYPE_InventoryOut, true),
	InventoryIn(X_M_InOut.MOVEMENTTYPE_InventoryIn, false),
	MovementFrom(X_M_InOut.MOVEMENTTYPE_MovementFrom, true),
	MovementTo(X_M_InOut.MOVEMENTTYPE_MovementTo, false),
	ProductionPlus(X_M_InOut.MOVEMENTTYPE_ProductionPlus, false),
	ProductionMinus(X_M_InOut.MOVEMENTTYPE_ProductionMinus, true),
	WorkOrderPlus(X_M_InOut.MOVEMENTTYPE_WorkOrderPlus, false),
	WorkOrderMinus(X_M_InOut.MOVEMENTTYPE_WorkOrderMinus, true),
	;

	@NonNull private final String code;
	final boolean isOutboundTransaction;

	private static final ReferenceListAwareEnums.ValuesIndex<MovementType> index = ReferenceListAwareEnums.index(values());

	public static MovementType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isMaterialReturn() {return CustomerReturns.equals(this) || VendorReturns.equals(this);}

	public static boolean isMaterialReturn(@NonNull final String movementType) {return ofCode(movementType).isMaterialReturn();}
}
