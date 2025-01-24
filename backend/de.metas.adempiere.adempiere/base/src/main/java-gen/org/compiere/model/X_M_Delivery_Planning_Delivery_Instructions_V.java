// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Delivery_Planning_Delivery_Instructions_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Delivery_Planning_Delivery_Instructions_V extends org.compiere.model.PO implements I_M_Delivery_Planning_Delivery_Instructions_V, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1662920947L;

    /** Standard Constructor */
    public X_M_Delivery_Planning_Delivery_Instructions_V (final Properties ctx, final int M_Delivery_Planning_Delivery_Instructions_V_ID, @Nullable final String trxName)
    {
      super (ctx, M_Delivery_Planning_Delivery_Instructions_V_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Delivery_Planning_Delivery_Instructions_V (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setActualDischargeQuantity (final @Nullable BigDecimal ActualDischargeQuantity)
	{
		set_ValueNoCheck (COLUMNNAME_ActualDischargeQuantity, ActualDischargeQuantity);
	}

	@Override
	public BigDecimal getActualDischargeQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualDischargeQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualLoadQty (final @Nullable BigDecimal ActualLoadQty)
	{
		set_ValueNoCheck (COLUMNNAME_ActualLoadQty, ActualLoadQty);
	}

	@Override
	public BigDecimal getActualLoadQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualLoadQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_BPartner_Location_Delivery_ID (final int C_BPartner_Location_Delivery_ID)
	{
		if (C_BPartner_Location_Delivery_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_Delivery_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_Delivery_ID, C_BPartner_Location_Delivery_ID);
	}

	@Override
	public int getC_BPartner_Location_Delivery_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_Delivery_ID);
	}

	@Override
	public void setC_BPartner_Location_Loading_ID (final int C_BPartner_Location_Loading_ID)
	{
		if (C_BPartner_Location_Loading_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_Loading_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_Loading_ID, C_BPartner_Location_Loading_ID);
	}

	@Override
	public int getC_BPartner_Location_Loading_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_Loading_ID);
	}

	@Override
	public org.compiere.model.I_C_Incoterms getC_Incoterms()
	{
		return get_ValueAsPO(COLUMNNAME_C_Incoterms_ID, org.compiere.model.I_C_Incoterms.class);
	}

	@Override
	public void setC_Incoterms(final org.compiere.model.I_C_Incoterms C_Incoterms)
	{
		set_ValueFromPO(COLUMNNAME_C_Incoterms_ID, org.compiere.model.I_C_Incoterms.class, C_Incoterms);
	}

	@Override
	public void setC_Incoterms_ID (final int C_Incoterms_ID)
	{
		if (C_Incoterms_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Incoterms_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Incoterms_ID, C_Incoterms_ID);
	}

	@Override
	public int getC_Incoterms_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Incoterms_ID);
	}

	@Override
	public void setDateDoc (final @Nullable java.sql.Timestamp DateDoc)
	{
		set_ValueNoCheck (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDeliveryDate (final @Nullable java.sql.Timestamp DeliveryDate)
	{
		set_ValueNoCheck (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
	}

	@Override
	public void setDocStatus (final @Nullable java.lang.String DocStatus)
	{
		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setIncotermLocation (final @Nullable java.lang.String IncotermLocation)
	{
		set_ValueNoCheck (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	@Override
	public java.lang.String getIncotermLocation() 
	{
		return get_ValueAsString(COLUMNNAME_IncotermLocation);
	}

	@Override
	public void setLoadingDate (final @Nullable java.sql.Timestamp LoadingDate)
	{
		set_ValueNoCheck (COLUMNNAME_LoadingDate, LoadingDate);
	}

	@Override
	public java.sql.Timestamp getLoadingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LoadingDate);
	}

	@Override
	public void setM_Delivery_Planning_Delivery_Instructions_V_ID (final int M_Delivery_Planning_Delivery_Instructions_V_ID)
	{
		if (M_Delivery_Planning_Delivery_Instructions_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_Delivery_Instructions_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_Delivery_Instructions_V_ID, M_Delivery_Planning_Delivery_Instructions_V_ID);
	}

	@Override
	public int getM_Delivery_Planning_Delivery_Instructions_V_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_Delivery_Instructions_V_ID);
	}

	@Override
	public org.compiere.model.I_M_Delivery_Planning getM_Delivery_Planning()
	{
		return get_ValueAsPO(COLUMNNAME_M_Delivery_Planning_ID, org.compiere.model.I_M_Delivery_Planning.class);
	}

	@Override
	public void setM_Delivery_Planning(final org.compiere.model.I_M_Delivery_Planning M_Delivery_Planning)
	{
		set_ValueFromPO(COLUMNNAME_M_Delivery_Planning_ID, org.compiere.model.I_M_Delivery_Planning.class, M_Delivery_Planning);
	}

	@Override
	public void setM_Delivery_Planning_ID (final int M_Delivery_Planning_ID)
	{
		if (M_Delivery_Planning_ID < 1) 
			set_Value (COLUMNNAME_M_Delivery_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_M_Delivery_Planning_ID, M_Delivery_Planning_ID);
	}

	@Override
	public int getM_Delivery_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public org.compiere.model.I_M_MeansOfTransportation getM_MeansOfTransportation()
	{
		return get_ValueAsPO(COLUMNNAME_M_MeansOfTransportation_ID, org.compiere.model.I_M_MeansOfTransportation.class);
	}

	@Override
	public void setM_MeansOfTransportation(final org.compiere.model.I_M_MeansOfTransportation M_MeansOfTransportation)
	{
		set_ValueFromPO(COLUMNNAME_M_MeansOfTransportation_ID, org.compiere.model.I_M_MeansOfTransportation.class, M_MeansOfTransportation);
	}

	@Override
	public void setM_MeansOfTransportation_ID (final int M_MeansOfTransportation_ID)
	{
		if (M_MeansOfTransportation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MeansOfTransportation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MeansOfTransportation_ID, M_MeansOfTransportation_ID);
	}

	@Override
	public int getM_MeansOfTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_MeansOfTransportation_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
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
}