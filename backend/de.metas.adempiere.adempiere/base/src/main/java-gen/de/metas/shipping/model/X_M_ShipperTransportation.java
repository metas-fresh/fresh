// Generated Model - DO NOT CHANGE
package de.metas.shipping.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipperTransportation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ShipperTransportation extends org.compiere.model.PO implements I_M_ShipperTransportation, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 510905355L;

    /** Standard Constructor */
    public X_M_ShipperTransportation (final Properties ctx, final int M_ShipperTransportation_ID, @Nullable final String trxName)
    {
      super (ctx, M_ShipperTransportation_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipperTransportation (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAssignAnonymouslyPickedHUs (final boolean AssignAnonymouslyPickedHUs)
	{
		set_Value (COLUMNNAME_AssignAnonymouslyPickedHUs, AssignAnonymouslyPickedHUs);
	}

	@Override
	public boolean isAssignAnonymouslyPickedHUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AssignAnonymouslyPickedHUs);
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
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	/** Set Sammelrechnung erstellen.
	 @param CollectiveBillReport Sammelrechnung erstellen	  */
	@Override
	public void setCollectiveBillReport (java.lang.String CollectiveBillReport)
	{
		set_Value (COLUMNNAME_CollectiveBillReport, CollectiveBillReport);
	}

	/** Get Sammelrechnung erstellen.
	 @return Sammelrechnung erstellen	  */
	@Override
	public java.lang.String getCollectiveBillReport ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CollectiveBillReport);
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
			set_Value (COLUMNNAME_C_Incoterms_ID, null);
		else 
			set_Value (COLUMNNAME_C_Incoterms_ID, C_Incoterms_ID);
	}

	@Override
	public int getC_Incoterms_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Incoterms_ID);
	}

	@Override
	public void setCreateShippingPackages (final @Nullable java.lang.String CreateShippingPackages)
	{
		set_Value (COLUMNNAME_CreateShippingPackages, CreateShippingPackages);
	}

	@Override
	public java.lang.String getCreateShippingPackages() 
	{
		return get_ValueAsString(COLUMNNAME_CreateShippingPackages);
	}

	@Override
	public void setDateDoc (final java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDateToBeFetched (final @Nullable java.sql.Timestamp DateToBeFetched)
	{
		set_Value (COLUMNNAME_DateToBeFetched, DateToBeFetched);
	}

	@Override
	public java.sql.Timestamp getDateToBeFetched() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateToBeFetched);
	}

	@Override
	public void setDeliveryDate (final @Nullable java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
	}

	@Override
	public void setDeliveryTime (final @Nullable java.lang.String DeliveryTime)
	{
		set_Value (COLUMNNAME_DeliveryTime, DeliveryTime);
	}

	@Override
	public java.lang.String getDeliveryTime() 
	{
		return get_ValueAsString(COLUMNNAME_DeliveryTime);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction() 
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setIncotermLocation (final @Nullable java.lang.String IncotermLocation)
	{
		set_Value (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	@Override
	public java.lang.String getIncotermLocation() 
	{
		return get_ValueAsString(COLUMNNAME_IncotermLocation);
	}

	@Override
	public void setIsApproved (final boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, IsApproved);
	}

	@Override
	public boolean isApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
	}

	@Override
	public void setLoadingDate (final @Nullable java.sql.Timestamp LoadingDate)
	{
		set_Value (COLUMNNAME_LoadingDate, LoadingDate);
	}

	@Override
	public java.sql.Timestamp getLoadingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LoadingDate);
	}

	@Override
	public void setLoadingTime (final @Nullable java.lang.String LoadingTime)
	{
		set_Value (COLUMNNAME_LoadingTime, LoadingTime);
	}

	@Override
	public java.lang.String getLoadingTime() 
	{
		return get_ValueAsString(COLUMNNAME_LoadingTime);
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
			set_Value (COLUMNNAME_M_MeansOfTransportation_ID, null);
		else 
			set_Value (COLUMNNAME_M_MeansOfTransportation_ID, M_MeansOfTransportation_ID);
	}

	@Override
	public int getM_MeansOfTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_MeansOfTransportation_ID);
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(final org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
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
	public void setM_Tour_ID (final int M_Tour_ID)
	{
		if (M_Tour_ID < 1) 
			set_Value (COLUMNNAME_M_Tour_ID, null);
		else 
			set_Value (COLUMNNAME_M_Tour_ID, M_Tour_ID);
	}

	@Override
	public int getM_Tour_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Tour_ID);
	}

	@Override
	public void setPackageNetTotal (final @Nullable BigDecimal PackageNetTotal)
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
	public void setPickupTimeFrom (final @Nullable java.sql.Timestamp PickupTimeFrom)
	{
		set_Value (COLUMNNAME_PickupTimeFrom, PickupTimeFrom);
	}

	@Override
	public java.sql.Timestamp getPickupTimeFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PickupTimeFrom);
	}

	@Override
	public void setPickupTimeTo (final @Nullable java.sql.Timestamp PickupTimeTo)
	{
		set_Value (COLUMNNAME_PickupTimeTo, PickupTimeTo);
	}

	@Override
	public java.sql.Timestamp getPickupTimeTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PickupTimeTo);
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
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setSalesRep_ID (final int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, SalesRep_ID);
	}

	@Override
	public int getSalesRep_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SalesRep_ID);
	}

	@Override
	public void setShipper_BPartner_ID (final int Shipper_BPartner_ID)
	{
		if (Shipper_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Shipper_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Shipper_BPartner_ID, Shipper_BPartner_ID);
	}

	@Override
	public int getShipper_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Shipper_BPartner_ID);
	}

	@Override
	public void setShipper_Location_ID (final int Shipper_Location_ID)
	{
		if (Shipper_Location_ID < 1) 
			set_Value (COLUMNNAME_Shipper_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Shipper_Location_ID, Shipper_Location_ID);
	}

	@Override
	public int getShipper_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Shipper_Location_ID);
	}
}