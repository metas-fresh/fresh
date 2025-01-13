package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Delivery_Planning_Delivery_Instructions_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Delivery_Planning_Delivery_Instructions_V 
{

	String Table_Name = "M_Delivery_Planning_Delivery_Instructions_V";

//	/** AD_Table_ID=542280 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Actual Discharge Quantity.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualDischargeQuantity (@Nullable BigDecimal ActualDischargeQuantity);

	/**
	 * Get Actual Discharge Quantity.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualDischargeQuantity();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_ActualDischargeQuantity = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "ActualDischargeQuantity", null);
	String COLUMNNAME_ActualDischargeQuantity = "ActualDischargeQuantity";

	/**
	 * Set Actual Loaded Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualLoadQty (@Nullable BigDecimal ActualLoadQty);

	/**
	 * Get Actual Loaded Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualLoadQty();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_ActualLoadQty = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "ActualLoadQty", null);
	String COLUMNNAME_ActualLoadQty = "ActualLoadQty";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, org.compiere.model.I_C_Incoterms> COLUMN_C_Incoterms_ID = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "C_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_C_Incoterms_ID = "C_Incoterms_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_Created = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Document Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateDoc (@Nullable java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateDoc();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_DateDoc = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Shipmentdate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryDate (@Nullable java.sql.Timestamp DeliveryDate);

	/**
	 * Get Shipmentdate.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDeliveryDate();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_DeliveryDate = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "DeliveryDate", null);
	String COLUMNNAME_DeliveryDate = "DeliveryDate";

	/**
	 * Set Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_DocStatus = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_DocumentNo = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncotermLocation();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Loading Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoadingDate (@Nullable java.sql.Timestamp LoadingDate);

	/**
	 * Get Loading Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getLoadingDate();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_LoadingDate = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "LoadingDate", null);
	String COLUMNNAME_LoadingDate = "LoadingDate";

	/**
	 * Set Delivery Instructions for Delivery Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_Delivery_Instructions_V_ID (int M_Delivery_Planning_Delivery_Instructions_V_ID);

	/**
	 * Get Delivery Instructions for Delivery Planning.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Delivery_Planning_Delivery_Instructions_V_ID();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_M_Delivery_Planning_Delivery_Instructions_V_ID = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "M_Delivery_Planning_Delivery_Instructions_V_ID", null);
	String COLUMNNAME_M_Delivery_Planning_Delivery_Instructions_V_ID = "M_Delivery_Planning_Delivery_Instructions_V_ID";

	/**
	 * Set Delivery Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_ID (int M_Delivery_Planning_ID);

	/**
	 * Get Delivery Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Delivery_Planning_ID();

	@Nullable org.compiere.model.I_M_Delivery_Planning getM_Delivery_Planning();

	void setM_Delivery_Planning(@Nullable org.compiere.model.I_M_Delivery_Planning M_Delivery_Planning);

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, org.compiere.model.I_M_Delivery_Planning> COLUMN_M_Delivery_Planning_ID = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "M_Delivery_Planning_ID", org.compiere.model.I_M_Delivery_Planning.class);
	String COLUMNNAME_M_Delivery_Planning_ID = "M_Delivery_Planning_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

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

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, org.compiere.model.I_M_MeansOfTransportation> COLUMN_M_MeansOfTransportation_ID = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "M_MeansOfTransportation_ID", org.compiere.model.I_M_MeansOfTransportation.class);
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

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Transportation Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID);

	/**
	 * Get Transportation Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipperTransportation_ID();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_M_ShipperTransportation_ID = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "M_ShipperTransportation_ID", null);
	String COLUMNNAME_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Delivery_Planning_Delivery_Instructions_V, Object> COLUMN_Updated = new ModelColumn<>(I_M_Delivery_Planning_Delivery_Instructions_V.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
