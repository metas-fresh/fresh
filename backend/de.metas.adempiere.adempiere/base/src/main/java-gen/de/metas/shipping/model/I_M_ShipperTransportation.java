package de.metas.shipping.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_ShipperTransportation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ShipperTransportation 
{

	String Table_Name = "M_ShipperTransportation";

//	/** AD_Table_ID=540030 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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
	 * Set Assign anonymously picked HUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssignAnonymouslyPickedHUs (boolean AssignAnonymouslyPickedHUs);

	/**
	 * Get Assign anonymously picked HUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isAssignAnonymouslyPickedHUs();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_AssignAnonymouslyPickedHUs = new ModelColumn<>(I_M_ShipperTransportation.class, "AssignAnonymouslyPickedHUs", null);
	String COLUMNNAME_AssignAnonymouslyPickedHUs = "AssignAnonymouslyPickedHUs";

	/**
	 * Set Delivery Address.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Delivery_ID (int C_BPartner_Location_Delivery_ID);

	/**
	 * Get Delivery Address.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_Delivery_ID();

	String COLUMNNAME_C_BPartner_Location_Delivery_ID = "C_BPartner_Location_Delivery_ID";

	/**
	 * Set Loading Address.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Loading_ID (int C_BPartner_Location_Loading_ID);

	/**
	 * Get Loading Address.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_Loading_ID();

	String COLUMNNAME_C_BPartner_Location_Loading_ID = "C_BPartner_Location_Loading_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Sammelrechnung erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCollectiveBillReport(java.lang.String CollectiveBillReport);

	/**
	 * Get Sammelrechnung erstellen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getCollectiveBillReport();

	/** Column definition for CollectiveBillReport */
	org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object> COLUMN_CollectiveBillReport = new org.adempiere.model.ModelColumn<I_M_ShipperTransportation, Object>(I_M_ShipperTransportation.class, "CollectiveBillReport", null);
	/** Column name CollectiveBillReport */
	String COLUMNNAME_CollectiveBillReport = "CollectiveBillReport";

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

	ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_C_Incoterms> COLUMN_C_Incoterms_ID = new ModelColumn<>(I_M_ShipperTransportation.class, "C_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_C_Incoterms_ID = "C_Incoterms_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Created = new ModelColumn<>(I_M_ShipperTransportation.class, "Created", null);
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
	 * Set Packstücke erfassen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateShippingPackages (@Nullable java.lang.String CreateShippingPackages);

	/**
	 * Get Packstücke erfassen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateShippingPackages();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_CreateShippingPackages = new ModelColumn<>(I_M_ShipperTransportation.class, "CreateShippingPackages", null);
	String COLUMNNAME_CreateShippingPackages = "CreateShippingPackages";

	/**
	 * Set Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateDoc();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DateDoc = new ModelColumn<>(I_M_ShipperTransportation.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Date to Fetch.
	 * Date and time to fetch
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateToBeFetched (@Nullable java.sql.Timestamp DateToBeFetched);

	/**
	 * Get Date to Fetch.
	 * Date and time to fetch
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateToBeFetched();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DateToBeFetched = new ModelColumn<>(I_M_ShipperTransportation.class, "DateToBeFetched", null);
	String COLUMNNAME_DateToBeFetched = "DateToBeFetched";

	/**
	 * Set Shipmentdate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryDate (@Nullable java.sql.Timestamp DeliveryDate);

	/**
	 * Get Shipmentdate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDeliveryDate();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DeliveryDate = new ModelColumn<>(I_M_ShipperTransportation.class, "DeliveryDate", null);
	String COLUMNNAME_DeliveryDate = "DeliveryDate";

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

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DeliveryTime = new ModelColumn<>(I_M_ShipperTransportation.class, "DeliveryTime", null);
	String COLUMNNAME_DeliveryTime = "DeliveryTime";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Description = new ModelColumn<>(I_M_ShipperTransportation.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocAction = new ModelColumn<>(I_M_ShipperTransportation.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocStatus = new ModelColumn<>(I_M_ShipperTransportation.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_DocumentNo = new ModelColumn<>(I_M_ShipperTransportation.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

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

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_M_ShipperTransportation.class, "IncotermLocation", null);
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

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ShipperTransportation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApproved();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_IsApproved = new ModelColumn<>(I_M_ShipperTransportation.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Loading Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoadingDate (@Nullable java.sql.Timestamp LoadingDate);

	/**
	 * Get Loading Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLoadingDate();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_LoadingDate = new ModelColumn<>(I_M_ShipperTransportation.class, "LoadingDate", null);
	String COLUMNNAME_LoadingDate = "LoadingDate";

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

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_LoadingTime = new ModelColumn<>(I_M_ShipperTransportation.class, "LoadingTime", null);
	String COLUMNNAME_LoadingTime = "LoadingTime";

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

	ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_M_MeansOfTransportation> COLUMN_M_MeansOfTransportation_ID = new ModelColumn<>(I_M_ShipperTransportation.class, "M_MeansOfTransportation_ID", org.compiere.model.I_M_MeansOfTransportation.class);
	String COLUMNNAME_M_MeansOfTransportation_ID = "M_MeansOfTransportation_ID";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_M_ShipperTransportation, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_M_ShipperTransportation.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Transportation Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transportation Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipperTransportation_ID();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_M_ShipperTransportation_ID = new ModelColumn<>(I_M_ShipperTransportation.class, "M_ShipperTransportation_ID", null);
	String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Set Tour.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Tour_ID (int M_Tour_ID);

	/**
	 * Get Tour.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Tour_ID();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_M_Tour_ID = new ModelColumn<>(I_M_ShipperTransportation.class, "M_Tour_ID", null);
	String COLUMNNAME_M_Tour_ID = "M_Tour_ID";

	/**
	 * Set Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackageNetTotal (@Nullable BigDecimal PackageNetTotal);

	/**
	 * Get Package Net Total.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPackageNetTotal();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PackageNetTotal = new ModelColumn<>(I_M_ShipperTransportation.class, "PackageNetTotal", null);
	String COLUMNNAME_PackageNetTotal = "PackageNetTotal";

	/**
	 * Set Weight.
	 * Weight of a package
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackageWeight (@Nullable BigDecimal PackageWeight);

	/**
	 * Get Weight.
	 * Weight of a package
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPackageWeight();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PackageWeight = new ModelColumn<>(I_M_ShipperTransportation.class, "PackageWeight", null);
	String COLUMNNAME_PackageWeight = "PackageWeight";

	/**
	 * Set Pickup Time From.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickupTimeFrom (@Nullable java.sql.Timestamp PickupTimeFrom);

	/**
	 * Get Pickup Time From.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPickupTimeFrom();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PickupTimeFrom = new ModelColumn<>(I_M_ShipperTransportation.class, "PickupTimeFrom", null);
	String COLUMNNAME_PickupTimeFrom = "PickupTimeFrom";

	/**
	 * Set Abholung Uhrzeit bis.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickupTimeTo (@Nullable java.sql.Timestamp PickupTimeTo);

	/**
	 * Get Abholung Uhrzeit bis.
	 *
	 * <br>Type: Time
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPickupTimeTo();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_PickupTimeTo = new ModelColumn<>(I_M_ShipperTransportation.class, "PickupTimeTo", null);
	String COLUMNNAME_PickupTimeTo = "PickupTimeTo";

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

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Processed = new ModelColumn<>(I_M_ShipperTransportation.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Processing = new ModelColumn<>(I_M_ShipperTransportation.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRep_ID();

	String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Shipper Partner.
	 * Business Partner to be used as shipper
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShipper_BPartner_ID (int Shipper_BPartner_ID);

	/**
	 * Get Shipper Partner.
	 * Business Partner to be used as shipper
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getShipper_BPartner_ID();

	String COLUMNNAME_Shipper_BPartner_ID = "Shipper_BPartner_ID";

	/**
	 * Set Shipper Location.
	 * Business Partner Location for Shipper
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShipper_Location_ID (int Shipper_Location_ID);

	/**
	 * Get Shipper Location.
	 * Business Partner Location for Shipper
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getShipper_Location_ID();

	String COLUMNNAME_Shipper_Location_ID = "Shipper_Location_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ShipperTransportation, Object> COLUMN_Updated = new ModelColumn<>(I_M_ShipperTransportation.class, "Updated", null);
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
}
