// Generated Model - DO NOT CHANGE
package de.metas.shipping.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ShippingPackage
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ShippingPackage extends org.compiere.model.PO implements I_M_ShippingPackage, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1356199036L;

    /** Standard Constructor */
    public X_M_ShippingPackage (final Properties ctx, final int M_ShippingPackage_ID, @Nullable final String trxName)
    {
      super (ctx, M_ShippingPackage_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShippingPackage (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setActualDischargeQuantity (final BigDecimal ActualDischargeQuantity)
	{
		set_Value (COLUMNNAME_ActualDischargeQuantity, ActualDischargeQuantity);
	}

	@Override
	public BigDecimal getActualDischargeQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualDischargeQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualLoadQty (final BigDecimal ActualLoadQty)
	{
		set_Value (COLUMNNAME_ActualLoadQty, ActualLoadQty);
	}

	@Override
	public BigDecimal getActualLoadQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualLoadQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBatch (final @Nullable java.lang.String Batch)
	{
		set_Value (COLUMNNAME_Batch, Batch);
	}

	@Override
	public java.lang.String getBatch() 
	{
		return get_ValueAsString(COLUMNNAME_Batch);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setIsToBeFetched (final boolean IsToBeFetched)
	{
		set_Value (COLUMNNAME_IsToBeFetched, IsToBeFetched);
	}

	@Override
	public boolean isToBeFetched() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsToBeFetched);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public org.compiere.model.I_M_Package getM_Package()
	{
		return get_ValueAsPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class);
	}

	@Override
	public void setM_Package(final org.compiere.model.I_M_Package M_Package)
	{
		set_ValueFromPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class, M_Package);
	}

	@Override
	public void setM_Package_ID (final int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, M_Package_ID);
	}

	@Override
	public int getM_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Package_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public de.metas.shipping.model.I_M_ShipperTransportation getM_ShipperTransportation()
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipperTransportation_ID, de.metas.shipping.model.I_M_ShipperTransportation.class);
	}

	@Override
	public void setM_ShipperTransportation(final de.metas.shipping.model.I_M_ShipperTransportation M_ShipperTransportation)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipperTransportation_ID, de.metas.shipping.model.I_M_ShipperTransportation.class, M_ShipperTransportation);
	}

	@Override
	public void setM_ShipperTransportation_ID (final int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, M_ShipperTransportation_ID);
	}

	@Override
	public int getM_ShipperTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipperTransportation_ID);
	}

	@Override
	public void setM_ShippingPackage_ID (final int M_ShippingPackage_ID)
	{
		if (M_ShippingPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShippingPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShippingPackage_ID, M_ShippingPackage_ID);
	}

	@Override
	public int getM_ShippingPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShippingPackage_ID);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setPackageNetTotal (final BigDecimal PackageNetTotal)
	{
		set_Value (COLUMNNAME_PackageNetTotal, PackageNetTotal);
	}

	@Override
	public BigDecimal getPackageNetTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PackageNetTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPackageWeight (final @Nullable BigDecimal PackageWeight)
	{
		set_Value (COLUMNNAME_PackageWeight, PackageWeight);
	}

	@Override
	public BigDecimal getPackageWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PackageWeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		throw new IllegalArgumentException ("ProductName is virtual column");	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setProductValue (final @Nullable java.lang.String ProductValue)
	{
		throw new IllegalArgumentException ("ProductValue is virtual column");	}

	@Override
	public java.lang.String getProductValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}
}