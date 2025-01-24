package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Delivery_Planning
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Delivery_Planning 
{

	String Table_Name = "M_Delivery_Planning";

//	/** AD_Table_ID=542259 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Actual Delivered Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setActualDeliveredQty (BigDecimal ActualDeliveredQty);

	/**
	 * Get Actual Delivered Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualDeliveredQty();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ActualDeliveredQty = new ModelColumn<>(I_M_Delivery_Planning.class, "ActualDeliveredQty", null);
	String COLUMNNAME_ActualDeliveredQty = "ActualDeliveredQty";

	/**
	 * Set Actual Delivery Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualDeliveryDate (@Nullable java.sql.Timestamp ActualDeliveryDate);

	/**
	 * Get Actual Delivery Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getActualDeliveryDate();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ActualDeliveryDate = new ModelColumn<>(I_M_Delivery_Planning.class, "ActualDeliveryDate", null);
	String COLUMNNAME_ActualDeliveryDate = "ActualDeliveryDate";

	/**
	 * Set Actual Discharge Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setActualDischargeQuantity (BigDecimal ActualDischargeQuantity);

	/**
	 * Get Actual Discharge Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualDischargeQuantity();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ActualDischargeQuantity = new ModelColumn<>(I_M_Delivery_Planning.class, "ActualDischargeQuantity", null);
	String COLUMNNAME_ActualDischargeQuantity = "ActualDischargeQuantity";

	/**
	 * Set Actual Loading Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualLoadingDate (@Nullable java.sql.Timestamp ActualLoadingDate);

	/**
	 * Get Actual Loading Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getActualLoadingDate();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ActualLoadingDate = new ModelColumn<>(I_M_Delivery_Planning.class, "ActualLoadingDate", null);
	String COLUMNNAME_ActualLoadingDate = "ActualLoadingDate";

	/**
	 * Set Actual Loaded Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setActualLoadQty (BigDecimal ActualLoadQty);

	/**
	 * Get Actual Loaded Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualLoadQty();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ActualLoadQty = new ModelColumn<>(I_M_Delivery_Planning.class, "ActualLoadQty", null);
	String COLUMNNAME_ActualLoadQty = "ActualLoadQty";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Batch.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBatch (@Nullable java.lang.String Batch);

	/**
	 * Get Batch.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBatch();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_Batch = new ModelColumn<>(I_M_Delivery_Planning.class, "Batch", null);
	String COLUMNNAME_Batch = "Batch";

	/**
	 * Set Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setBPartnerName (@Nullable java.lang.String BPartnerName);

	/**
	 * Get Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getBPartnerName();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_BPartnerName = new ModelColumn<>(I_M_Delivery_Planning.class, "BPartnerName", null);
	String COLUMNNAME_BPartnerName = "BPartnerName";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Destination Country.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DestinationCountry_ID (int C_DestinationCountry_ID);

	/**
	 * Get Destination Country.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DestinationCountry_ID();

	@Nullable org.compiere.model.I_C_Country getC_DestinationCountry();

	void setC_DestinationCountry(@Nullable org.compiere.model.I_C_Country C_DestinationCountry);

	ModelColumn<I_M_Delivery_Planning, org.compiere.model.I_C_Country> COLUMN_C_DestinationCountry_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "C_DestinationCountry_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_DestinationCountry_ID = "C_DestinationCountry_ID";

	/**
	 * Set Incoterms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Incoterms_ID (int C_Incoterms_ID);

	/**
	 * Get Incoterms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Incoterms_ID();

	@Nullable org.compiere.model.I_C_Incoterms getC_Incoterms();

	void setC_Incoterms(@Nullable org.compiere.model.I_C_Incoterms C_Incoterms);

	ModelColumn<I_M_Delivery_Planning, org.compiere.model.I_C_Incoterms> COLUMN_C_Incoterms_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "C_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_C_Incoterms_ID = "C_Incoterms_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_M_Delivery_Planning, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_M_Delivery_Planning, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Origin Country.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OriginCountry_ID (int C_OriginCountry_ID);

	/**
	 * Get Origin Country.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OriginCountry_ID();

	@Nullable org.compiere.model.I_C_Country getC_OriginCountry();

	void setC_OriginCountry(@Nullable org.compiere.model.I_C_Country C_OriginCountry);

	ModelColumn<I_M_Delivery_Planning, org.compiere.model.I_C_Country> COLUMN_C_OriginCountry_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "C_OriginCountry_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_OriginCountry_ID = "C_OriginCountry_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_Created = new ModelColumn<>(I_M_Delivery_Planning.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Delivery Time.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryTime (@Nullable java.lang.String DeliveryTime);

	/**
	 * Get Delivery Time.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryTime();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_DeliveryTime = new ModelColumn<>(I_M_Delivery_Planning.class, "DeliveryTime", null);
	String COLUMNNAME_DeliveryTime = "DeliveryTime";

	/**
	 * Set Grade.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setGrade (@Nullable java.lang.String Grade);

	/**
	 * Get Grade.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getGrade();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_Grade = new ModelColumn<>(I_M_Delivery_Planning.class, "Grade", null);
	String COLUMNNAME_Grade = "Grade";

	/**
	 * Set IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncotermLocation();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_M_Delivery_Planning.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Delivery_Planning.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set B2B.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsB2B (boolean IsB2B);

	/**
	 * Get B2B.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isB2B();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_IsB2B = new ModelColumn<>(I_M_Delivery_Planning.class, "IsB2B", null);
	String COLUMNNAME_IsB2B = "IsB2B";

	/**
	 * Set Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosed (boolean IsClosed);

	/**
	 * Get Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosed();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_IsClosed = new ModelColumn<>(I_M_Delivery_Planning.class, "IsClosed", null);
	String COLUMNNAME_IsClosed = "IsClosed";

	/**
	 * Set Loading Time.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoadingTime (@Nullable java.lang.String LoadingTime);

	/**
	 * Get Loading Time.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLoadingTime();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_LoadingTime = new ModelColumn<>(I_M_Delivery_Planning.class, "LoadingTime", null);
	String COLUMNNAME_LoadingTime = "LoadingTime";

	/**
	 * Set Delivery Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_ID (int M_Delivery_Planning_ID);

	/**
	 * Get Delivery Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Delivery_Planning_ID();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_M_Delivery_Planning_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "M_Delivery_Planning_ID", null);
	String COLUMNNAME_M_Delivery_Planning_ID = "M_Delivery_Planning_ID";

	/**
	 * Set Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_Type (@Nullable java.lang.String M_Delivery_Planning_Type);

	/**
	 * Get Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getM_Delivery_Planning_Type();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_M_Delivery_Planning_Type = new ModelColumn<>(I_M_Delivery_Planning.class, "M_Delivery_Planning_Type", null);
	String COLUMNNAME_M_Delivery_Planning_Type = "M_Delivery_Planning_Type";

	/**
	 * Set Means of Transportation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_MeansOfTransportation_ID (int M_MeansOfTransportation_ID);

	/**
	 * Get Means of Transportation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_MeansOfTransportation_ID();

	@Nullable org.compiere.model.I_M_MeansOfTransportation getM_MeansOfTransportation();

	void setM_MeansOfTransportation(@Nullable org.compiere.model.I_M_MeansOfTransportation M_MeansOfTransportation);

	ModelColumn<I_M_Delivery_Planning, org.compiere.model.I_M_MeansOfTransportation> COLUMN_M_MeansOfTransportation_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "M_MeansOfTransportation_ID", org.compiere.model.I_M_MeansOfTransportation.class);
	String COLUMNNAME_M_MeansOfTransportation_ID = "M_MeansOfTransportation_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ReceiptSchedule_ID();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_M_ReceiptSchedule_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "M_ReceiptSchedule_ID", null);
	String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_M_Delivery_Planning, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	@Nullable org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(@Nullable org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_M_Delivery_Planning, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_M_Delivery_Planning.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Order Document No.
	 * Document Number of the Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setOrderDocumentNo (@Nullable java.lang.String OrderDocumentNo);

	/**
	 * Get Order Document No.
	 * Document Number of the Order
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getOrderDocumentNo();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_OrderDocumentNo = new ModelColumn<>(I_M_Delivery_Planning.class, "OrderDocumentNo", null);
	String COLUMNNAME_OrderDocumentNo = "OrderDocumentNo";

	/**
	 * Set Order Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderStatus (@Nullable java.lang.String OrderStatus);

	/**
	 * Get Order Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderStatus();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_OrderStatus = new ModelColumn<>(I_M_Delivery_Planning.class, "OrderStatus", null);
	String COLUMNNAME_OrderStatus = "OrderStatus";

	/**
	 * Set Country of Origin.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setOriginCountry (@Nullable java.lang.String OriginCountry);

	/**
	 * Get Country of Origin.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getOriginCountry();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_OriginCountry = new ModelColumn<>(I_M_Delivery_Planning.class, "OriginCountry", null);
	String COLUMNNAME_OriginCountry = "OriginCountry";

	/**
	 * Set Planned Delivery Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlannedDeliveryDate (@Nullable java.sql.Timestamp PlannedDeliveryDate);

	/**
	 * Get Planned Delivery Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPlannedDeliveryDate();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_PlannedDeliveryDate = new ModelColumn<>(I_M_Delivery_Planning.class, "PlannedDeliveryDate", null);
	String COLUMNNAME_PlannedDeliveryDate = "PlannedDeliveryDate";

	/**
	 * Set Planned Discharge Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedDischargeQuantity (BigDecimal PlannedDischargeQuantity);

	/**
	 * Get Planned Discharge Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedDischargeQuantity();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_PlannedDischargeQuantity = new ModelColumn<>(I_M_Delivery_Planning.class, "PlannedDischargeQuantity", null);
	String COLUMNNAME_PlannedDischargeQuantity = "PlannedDischargeQuantity";

	/**
	 * Set Planned Loaded Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPlannedLoadedQuantity (BigDecimal PlannedLoadedQuantity);

	/**
	 * Get Planned Loaded Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPlannedLoadedQuantity();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_PlannedLoadedQuantity = new ModelColumn<>(I_M_Delivery_Planning.class, "PlannedLoadedQuantity", null);
	String COLUMNNAME_PlannedLoadedQuantity = "PlannedLoadedQuantity";

	/**
	 * Set Planned Loading Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPlannedLoadingDate (@Nullable java.sql.Timestamp PlannedLoadingDate);

	/**
	 * Get Planned Loading Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPlannedLoadingDate();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_PlannedLoadingDate = new ModelColumn<>(I_M_Delivery_Planning.class, "PlannedLoadingDate", null);
	String COLUMNNAME_PlannedLoadingDate = "PlannedLoadingDate";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_POReference = new ModelColumn<>(I_M_Delivery_Planning.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_Processed = new ModelColumn<>(I_M_Delivery_Planning.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProductName (@Nullable java.lang.String ProductName);

	/**
	 * Get Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getProductName();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ProductName = new ModelColumn<>(I_M_Delivery_Planning.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProductValue (@Nullable java.lang.String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getProductValue();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ProductValue = new ModelColumn<>(I_M_Delivery_Planning.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_M_Delivery_Planning.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Qty Total Open.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyTotalOpen (BigDecimal QtyTotalOpen);

	/**
	 * Get Qty Total Open.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyTotalOpen();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_QtyTotalOpen = new ModelColumn<>(I_M_Delivery_Planning.class, "QtyTotalOpen", null);
	String COLUMNNAME_QtyTotalOpen = "QtyTotalOpen";

	/**
	 * Set Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReleaseNo (@Nullable java.lang.String ReleaseNo);

	/**
	 * Get Release No.
	 * Internal Release Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReleaseNo();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ReleaseNo = new ModelColumn<>(I_M_Delivery_Planning.class, "ReleaseNo", null);
	String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/**
	 * Set Ship-to location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setShipToLocation_Name (@Nullable java.lang.String ShipToLocation_Name);

	/**
	 * Get Ship-to location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getShipToLocation_Name();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_ShipToLocation_Name = new ModelColumn<>(I_M_Delivery_Planning.class, "ShipToLocation_Name", null);
	String COLUMNNAME_ShipToLocation_Name = "ShipToLocation_Name";

	/**
	 * Set Transport Details.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTransportDetails (@Nullable java.lang.String TransportDetails);

	/**
	 * Get Transport Details.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTransportDetails();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_TransportDetails = new ModelColumn<>(I_M_Delivery_Planning.class, "TransportDetails", null);
	String COLUMNNAME_TransportDetails = "TransportDetails";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_Updated = new ModelColumn<>(I_M_Delivery_Planning.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Warehouse Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setWarehouseName (@Nullable java.lang.String WarehouseName);

	/**
	 * Get Warehouse Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getWarehouseName();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_WarehouseName = new ModelColumn<>(I_M_Delivery_Planning.class, "WarehouseName", null);
	String COLUMNNAME_WarehouseName = "WarehouseName";

	/**
	 * Set Way Bill No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWayBillNo (@Nullable java.lang.String WayBillNo);

	/**
	 * Get Way Bill No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWayBillNo();

	ModelColumn<I_M_Delivery_Planning, Object> COLUMN_WayBillNo = new ModelColumn<>(I_M_Delivery_Planning.class, "WayBillNo", null);
	String COLUMNNAME_WayBillNo = "WayBillNo";
}
