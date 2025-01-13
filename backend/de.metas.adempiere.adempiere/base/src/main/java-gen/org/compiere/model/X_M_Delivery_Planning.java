// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Delivery_Planning
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Delivery_Planning extends org.compiere.model.PO implements I_M_Delivery_Planning, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1276531233L;

    /** Standard Constructor */
    public X_M_Delivery_Planning (final Properties ctx, final int M_Delivery_Planning_ID, @Nullable final String trxName)
    {
      super (ctx, M_Delivery_Planning_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Delivery_Planning (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setActualDeliveredQty (final BigDecimal ActualDeliveredQty)
	{
		set_Value (COLUMNNAME_ActualDeliveredQty, ActualDeliveredQty);
	}

	@Override
	public BigDecimal getActualDeliveredQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualDeliveredQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualDeliveryDate (final @Nullable java.sql.Timestamp ActualDeliveryDate)
	{
		set_Value (COLUMNNAME_ActualDeliveryDate, ActualDeliveryDate);
	}

	@Override
	public java.sql.Timestamp getActualDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ActualDeliveryDate);
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
	public void setActualLoadingDate (final @Nullable java.sql.Timestamp ActualLoadingDate)
	{
		set_Value (COLUMNNAME_ActualLoadingDate, ActualLoadingDate);
	}

	@Override
	public java.sql.Timestamp getActualLoadingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ActualLoadingDate);
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
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		throw new IllegalArgumentException ("BPartnerName is virtual column");	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
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
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Country getC_DestinationCountry()
	{
		return get_ValueAsPO(COLUMNNAME_C_DestinationCountry_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_DestinationCountry(final org.compiere.model.I_C_Country C_DestinationCountry)
	{
		set_ValueFromPO(COLUMNNAME_C_DestinationCountry_ID, org.compiere.model.I_C_Country.class, C_DestinationCountry);
	}

	@Override
	public void setC_DestinationCountry_ID (final int C_DestinationCountry_ID)
	{
		if (C_DestinationCountry_ID < 1) 
			set_Value (COLUMNNAME_C_DestinationCountry_ID, null);
		else 
			set_Value (COLUMNNAME_C_DestinationCountry_ID, C_DestinationCountry_ID);
	}

	@Override
	public int getC_DestinationCountry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DestinationCountry_ID);
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
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public org.compiere.model.I_C_Country getC_OriginCountry()
	{
		return get_ValueAsPO(COLUMNNAME_C_OriginCountry_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_OriginCountry(final org.compiere.model.I_C_Country C_OriginCountry)
	{
		set_ValueFromPO(COLUMNNAME_C_OriginCountry_ID, org.compiere.model.I_C_Country.class, C_OriginCountry);
	}

	@Override
	public void setC_OriginCountry_ID (final int C_OriginCountry_ID)
	{
		if (C_OriginCountry_ID < 1) 
			set_Value (COLUMNNAME_C_OriginCountry_ID, null);
		else 
			set_Value (COLUMNNAME_C_OriginCountry_ID, C_OriginCountry_ID);
	}

	@Override
	public int getC_OriginCountry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OriginCountry_ID);
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
	public void setGrade (final @Nullable java.lang.String Grade)
	{
		throw new IllegalArgumentException ("Grade is virtual column");	}

	@Override
	public java.lang.String getGrade() 
	{
		return get_ValueAsString(COLUMNNAME_Grade);
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
	public void setIsB2B (final boolean IsB2B)
	{
		set_Value (COLUMNNAME_IsB2B, IsB2B);
	}

	@Override
	public boolean isB2B() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsB2B);
	}

	@Override
	public void setIsClosed (final boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, IsClosed);
	}

	@Override
	public boolean isClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosed);
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
	public void setM_Delivery_Planning_ID (final int M_Delivery_Planning_ID)
	{
		if (M_Delivery_Planning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_ID, M_Delivery_Planning_ID);
	}

	@Override
	public int getM_Delivery_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_ID);
	}

	/** 
	 * M_Delivery_Planning_Type AD_Reference_ID=541689
	 * Reference name: M_Delivery_Planning_Types
	 */
	public static final int M_DELIVERY_PLANNING_TYPE_AD_Reference_ID=541689;
	/** Incoming = Incoming */
	public static final String M_DELIVERY_PLANNING_TYPE_Incoming = "Incoming";
	/** Outgoing = Outgoing */
	public static final String M_DELIVERY_PLANNING_TYPE_Outgoing = "Outgoing";
	@Override
	public void setM_Delivery_Planning_Type (final @Nullable java.lang.String M_Delivery_Planning_Type)
	{
		set_Value (COLUMNNAME_M_Delivery_Planning_Type, M_Delivery_Planning_Type);
	}

	@Override
	public java.lang.String getM_Delivery_Planning_Type() 
	{
		return get_ValueAsString(COLUMNNAME_M_Delivery_Planning_Type);
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
	public void setM_ReceiptSchedule_ID (final int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, M_ReceiptSchedule_ID);
	}

	@Override
	public int getM_ReceiptSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ReceiptSchedule_ID);
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
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
	}

	@Override
	public void setM_ShipmentSchedule_ID (final int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
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
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setOrderDocumentNo (final @Nullable java.lang.String OrderDocumentNo)
	{
		throw new IllegalArgumentException ("OrderDocumentNo is virtual column");	}

	@Override
	public java.lang.String getOrderDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_OrderDocumentNo);
	}

	/** 
	 * OrderStatus AD_Reference_ID=541690
	 * Reference name: Order Statuses
	 */
	public static final int ORDERSTATUS_AD_Reference_ID=541690;
	/** Arranged = Arranged */
	public static final String ORDERSTATUS_Arranged = "Arranged";
	/** Loaded = Loaded */
	public static final String ORDERSTATUS_Loaded = "Loaded";
	/** Canceled = Canceled */
	public static final String ORDERSTATUS_Canceled = "Canceled";
	@Override
	public void setOrderStatus (final @Nullable java.lang.String OrderStatus)
	{
		set_Value (COLUMNNAME_OrderStatus, OrderStatus);
	}

	@Override
	public java.lang.String getOrderStatus() 
	{
		return get_ValueAsString(COLUMNNAME_OrderStatus);
	}

	@Override
	public void setOriginCountry (final @Nullable java.lang.String OriginCountry)
	{
		throw new IllegalArgumentException ("OriginCountry is virtual column");	}

	@Override
	public java.lang.String getOriginCountry() 
	{
		return get_ValueAsString(COLUMNNAME_OriginCountry);
	}

	@Override
	public void setPlannedDeliveryDate (final @Nullable java.sql.Timestamp PlannedDeliveryDate)
	{
		set_ValueNoCheck (COLUMNNAME_PlannedDeliveryDate, PlannedDeliveryDate);
	}

	@Override
	public java.sql.Timestamp getPlannedDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PlannedDeliveryDate);
	}

	@Override
	public void setPlannedDischargeQuantity (final BigDecimal PlannedDischargeQuantity)
	{
		set_Value (COLUMNNAME_PlannedDischargeQuantity, PlannedDischargeQuantity);
	}

	@Override
	public BigDecimal getPlannedDischargeQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedDischargeQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedLoadedQuantity (final BigDecimal PlannedLoadedQuantity)
	{
		set_Value (COLUMNNAME_PlannedLoadedQuantity, PlannedLoadedQuantity);
	}

	@Override
	public BigDecimal getPlannedLoadedQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PlannedLoadedQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPlannedLoadingDate (final @Nullable java.sql.Timestamp PlannedLoadingDate)
	{
		set_Value (COLUMNNAME_PlannedLoadingDate, PlannedLoadingDate);
	}

	@Override
	public java.sql.Timestamp getPlannedLoadingDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PlannedLoadingDate);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		throw new IllegalArgumentException ("POReference is virtual column");	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
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

	@Override
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyTotalOpen (final BigDecimal QtyTotalOpen)
	{
		set_Value (COLUMNNAME_QtyTotalOpen, QtyTotalOpen);
	}

	@Override
	public BigDecimal getQtyTotalOpen() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyTotalOpen);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setReleaseNo (final @Nullable java.lang.String ReleaseNo)
	{
		set_Value (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	@Override
	public java.lang.String getReleaseNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReleaseNo);
	}

	@Override
	public void setShipToLocation_Name (final @Nullable java.lang.String ShipToLocation_Name)
	{
		throw new IllegalArgumentException ("ShipToLocation_Name is virtual column");	}

	@Override
	public java.lang.String getShipToLocation_Name() 
	{
		return get_ValueAsString(COLUMNNAME_ShipToLocation_Name);
	}

	@Override
	public void setTransportDetails (final @Nullable java.lang.String TransportDetails)
	{
		set_Value (COLUMNNAME_TransportDetails, TransportDetails);
	}

	@Override
	public java.lang.String getTransportDetails() 
	{
		return get_ValueAsString(COLUMNNAME_TransportDetails);
	}

	@Override
	public void setWarehouseName (final @Nullable java.lang.String WarehouseName)
	{
		throw new IllegalArgumentException ("WarehouseName is virtual column");	}

	@Override
	public java.lang.String getWarehouseName() 
	{
		return get_ValueAsString(COLUMNNAME_WarehouseName);
	}

	@Override
	public void setWayBillNo (final @Nullable java.lang.String WayBillNo)
	{
		set_Value (COLUMNNAME_WayBillNo, WayBillNo);
	}

	@Override
	public java.lang.String getWayBillNo() 
	{
		return get_ValueAsString(COLUMNNAME_WayBillNo);
	}
}