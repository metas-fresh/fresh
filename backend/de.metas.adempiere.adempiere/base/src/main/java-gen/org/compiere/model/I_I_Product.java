package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for I_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_Product 
{

	String Table_Name = "I_Product";

//	/** AD_Table_ID=532 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Set Geschäftspartner-Schlüssel.
	 * The Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartner_Value (@Nullable java.lang.String BPartner_Value);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * The Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartner_Value();

	ModelColumn<I_I_Product, Object> COLUMN_BPartner_Value = new ModelColumn<>(I_I_Product.class, "BPartner_Value", null);
	String COLUMNNAME_BPartner_Value = "BPartner_Value";

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
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_ID();

	@Nullable org.compiere.model.I_C_DataImport getC_DataImport();

	void setC_DataImport(@Nullable org.compiere.model.I_C_DataImport C_DataImport);

	ModelColumn<I_I_Product, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_Product.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_Run_ID();

	@Nullable org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	void setC_DataImport_Run(@Nullable org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

	ModelColumn<I_I_Product, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_Product.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Klassifizierung.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClassification (@Nullable java.lang.String Classification);

	/**
	 * Get Klassifizierung.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClassification();

	ModelColumn<I_I_Product, Object> COLUMN_Classification = new ModelColumn<>(I_I_Product.class, "Classification", null);
	String COLUMNNAME_Classification = "Classification";

	/**
	 * Set Bestellkosten.
	 * Fixed Cost Per Order
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostPerOrder (@Nullable BigDecimal CostPerOrder);

	/**
	 * Get Bestellkosten.
	 * Fixed Cost Per Order
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostPerOrder();

	ModelColumn<I_I_Product, Object> COLUMN_CostPerOrder = new ModelColumn<>(I_I_Product.class, "CostPerOrder", null);
	String COLUMNNAME_CostPerOrder = "CostPerOrder";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_I_Product, Object> COLUMN_Created = new ModelColumn<>(I_I_Product.class, "Created", null);
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
	 * Set Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Set MwSt-Kategorie.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_Name (@Nullable java.lang.String C_TaxCategory_Name);

	/**
	 * Get MwSt-Kategorie.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_TaxCategory_Name();

	ModelColumn<I_I_Product, Object> COLUMN_C_TaxCategory_Name = new ModelColumn<>(I_I_Product.class, "C_TaxCategory_Name", null);
	String COLUMNNAME_C_TaxCategory_Name = "C_TaxCategory_Name";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Customs Tariff.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomsTariff (@Nullable java.lang.String CustomsTariff);

	/**
	 * Get Customs Tariff.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomsTariff();

	ModelColumn<I_I_Product, Object> COLUMN_CustomsTariff = new ModelColumn<>(I_I_Product.class, "CustomsTariff", null);
	String COLUMNNAME_CustomsTariff = "CustomsTariff";

	/**
	 * Set Zugesicherte Lieferzeit.
	 * Promised days between order and delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryTime_Promised (int DeliveryTime_Promised);

	/**
	 * Get Zugesicherte Lieferzeit.
	 * Promised days between order and delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDeliveryTime_Promised();

	ModelColumn<I_I_Product, Object> COLUMN_DeliveryTime_Promised = new ModelColumn<>(I_I_Product.class, "DeliveryTime_Promised", null);
	String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

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

	ModelColumn<I_I_Product, Object> COLUMN_Description = new ModelColumn<>(I_I_Product.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Beschreibungs-URL.
	 * URL for the description
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionURL (@Nullable java.lang.String DescriptionURL);

	/**
	 * Get Beschreibungs-URL.
	 * URL for the description
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionURL();

	ModelColumn<I_I_Product, Object> COLUMN_DescriptionURL = new ModelColumn<>(I_I_Product.class, "DescriptionURL", null);
	String COLUMNNAME_DescriptionURL = "DescriptionURL";

	/**
	 * Set Diet Type.
	 * Diet Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDietType (@Nullable java.lang.String DietType);

	/**
	 * Get Diet Type.
	 * Diet Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDietType();

	ModelColumn<I_I_Product, Object> COLUMN_DietType = new ModelColumn<>(I_I_Product.class, "DietType", null);
	String COLUMNNAME_DietType = "DietType";

	/**
	 * Set Is commissioned.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCommissioned (boolean IsCommissioned);

	/**
	 * Get Is commissioned.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCommissioned();

	ModelColumn<I_I_Product, Object> COLUMN_IsCommissioned = new ModelColumn<>(I_I_Product.class, "IsCommissioned", null);
	String COLUMNNAME_IsCommissioned = "IsCommissioned";

	/**
	 * Set Eingestellt.
	 * This product is no longer available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscontinued (boolean Discontinued);

	/**
	 * Get Eingestellt.
	 * This product is no longer available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDiscontinued();

	ModelColumn<I_I_Product, Object> COLUMN_Discontinued = new ModelColumn<>(I_I_Product.class, "Discontinued", null);
	String COLUMNNAME_Discontinued = "Discontinued";

	/**
	 * Set Eingestellt durch.
	 * Discontinued By
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscontinuedBy (@Nullable java.sql.Timestamp DiscontinuedBy);

	/**
	 * Get Eingestellt durch.
	 * Discontinued By
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDiscontinuedBy();

	ModelColumn<I_I_Product, Object> COLUMN_DiscontinuedBy = new ModelColumn<>(I_I_Product.class, "DiscontinuedBy", null);
	String COLUMNNAME_DiscontinuedBy = "DiscontinuedBy";

	/**
	 * Set Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNote (@Nullable java.lang.String DocumentNote);

	/**
	 * Get Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNote();

	ModelColumn<I_I_Product, Object> COLUMN_DocumentNote = new ModelColumn<>(I_I_Product.class, "DocumentNote", null);
	String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_I_Product, Object> COLUMN_ExternalId = new ModelColumn<>(I_I_Product.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_I_Product, Object> COLUMN_Help = new ModelColumn<>(I_I_Product.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_ErrorMsg (@Nullable java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_ErrorMsg();

	ModelColumn<I_I_Product, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_Product.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isI_IsImported();

	ModelColumn<I_I_Product, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_Product.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineContent (@Nullable java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_LineContent();

	ModelColumn<I_I_Product, Object> COLUMN_I_LineContent = new ModelColumn<>(I_I_Product.class, "I_LineContent", null);
	String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_LineNo();

	ModelColumn<I_I_Product, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_Product.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Bild-URL.
	 * URL of  image
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImageURL (@Nullable java.lang.String ImageURL);

	/**
	 * Get Bild-URL.
	 * URL of  image
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImageURL();

	ModelColumn<I_I_Product, Object> COLUMN_ImageURL = new ModelColumn<>(I_I_Product.class, "ImageURL", null);
	String COLUMNNAME_ImageURL = "ImageURL";

	/**
	 * Set Import - Produkt.
	 * Import Item or Service
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_Product_ID (int I_Product_ID);

	/**
	 * Get Import - Produkt.
	 * Import Item or Service
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_Product_ID();

	ModelColumn<I_I_Product, Object> COLUMN_I_Product_ID = new ModelColumn<>(I_I_Product.class, "I_Product_ID", null);
	String COLUMNNAME_I_Product_ID = "I_Product_ID";

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

	ModelColumn<I_I_Product, Object> COLUMN_IsActive = new ModelColumn<>(I_I_Product.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setISO_Code (@Nullable java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getISO_Code();

	ModelColumn<I_I_Product, Object> COLUMN_ISO_Code = new ModelColumn<>(I_I_Product.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Purchased.
	 * Organization purchases this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPurchased (boolean IsPurchased);

	/**
	 * Get Purchased.
	 * Organization purchases this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPurchased();

	ModelColumn<I_I_Product, Object> COLUMN_IsPurchased = new ModelColumn<>(I_I_Product.class, "IsPurchased", null);
	String COLUMNNAME_IsPurchased = "IsPurchased";

	/**
	 * Set Scale Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsScalePrice (boolean IsScalePrice);

	/**
	 * Get Scale Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isScalePrice();

	ModelColumn<I_I_Product, Object> COLUMN_IsScalePrice = new ModelColumn<>(I_I_Product.class, "IsScalePrice", null);
	String COLUMNNAME_IsScalePrice = "IsScalePrice";

	/**
	 * Set Verkauft.
	 * Die Organisation verkauft dieses Produkt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSold (boolean IsSold);

	/**
	 * Get Verkauft.
	 * Die Organisation verkauft dieses Produkt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSold();

	ModelColumn<I_I_Product, Object> COLUMN_IsSold = new ModelColumn<>(I_I_Product.class, "IsSold", null);
	String COLUMNNAME_IsSold = "IsSold";

	/**
	 * Set Lagerhaltig.
	 * Die Organisation hat dieses Produkt auf Lager
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsStocked (boolean IsStocked);

	/**
	 * Get Lagerhaltig.
	 * Die Organisation hat dieses Produkt auf Lager
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isStocked();

	ModelColumn<I_I_Product, Object> COLUMN_IsStocked = new ModelColumn<>(I_I_Product.class, "IsStocked", null);
	String COLUMNNAME_IsStocked = "IsStocked";

	/**
	 * Set Manufacturer.
	 * Hersteller des Produktes
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturer_ID (int Manufacturer_ID);

	/**
	 * Get Manufacturer.
	 * Hersteller des Produktes
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getManufacturer_ID();

	String COLUMNNAME_Manufacturer_ID = "Manufacturer_ID";


	/**
	 * Set Manufacturing Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturingMethod (@Nullable java.lang.String ManufacturingMethod);

	/**
	 * Get Manufacturing Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getManufacturingMethod();

	ModelColumn<I_I_Product, Object> COLUMN_ManufacturingMethod = new ModelColumn<>(I_I_Product.class, "ManufacturingMethod", null);
	String COLUMNNAME_ManufacturingMethod = "ManufacturingMethod";

	/**
	 * Set Customs Tariff.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CustomsTariff_ID (int M_CustomsTariff_ID);

	/**
	 * Get Customs Tariff.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CustomsTariff_ID();

	@Nullable org.compiere.model.I_M_CustomsTariff getM_CustomsTariff();

	void setM_CustomsTariff(@Nullable org.compiere.model.I_M_CustomsTariff M_CustomsTariff);

	ModelColumn<I_I_Product, org.compiere.model.I_M_CustomsTariff> COLUMN_M_CustomsTariff_ID = new ModelColumn<>(I_I_Product.class, "M_CustomsTariff_ID", org.compiere.model.I_M_CustomsTariff.class);
	String COLUMNNAME_M_CustomsTariff_ID = "M_CustomsTariff_ID";

	/**
	 * Set Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_Version_ID();

	String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/**
	 * Set Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_Version_Name (@Nullable java.lang.String M_PriceList_Version_Name);

	/**
	 * Get Version Preisliste.
	 * Bezeichnet eine einzelne Version der Preisliste
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getM_PriceList_Version_Name();

	ModelColumn<I_I_Product, Object> COLUMN_M_PriceList_Version_Name = new ModelColumn<>(I_I_Product.class, "M_PriceList_Version_Name", null);
	String COLUMNNAME_M_PriceList_Version_Name = "M_PriceList_Version_Name";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set M_ProductPlanningSchema_Selector.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ProductPlanningSchema_Selector (@Nullable java.lang.String M_ProductPlanningSchema_Selector);

	/**
	 * Get M_ProductPlanningSchema_Selector.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getM_ProductPlanningSchema_Selector();

	ModelColumn<I_I_Product, Object> COLUMN_M_ProductPlanningSchema_Selector = new ModelColumn<>(I_I_Product.class, "M_ProductPlanningSchema_Selector", null);
	String COLUMNNAME_M_ProductPlanningSchema_Selector = "M_ProductPlanningSchema_Selector";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_I_Product, Object> COLUMN_Name = new ModelColumn<>(I_I_Product.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Nettogewicht.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNetWeight (@Nullable BigDecimal NetWeight);

	/**
	 * Get Nettogewicht.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getNetWeight();

	ModelColumn<I_I_Product, Object> COLUMN_NetWeight = new ModelColumn<>(I_I_Product.class, "NetWeight", null);
	String COLUMNNAME_NetWeight = "NetWeight";

	/**
	 * Set Minimum Order Qty.
	 * Minimum order quantity in UOM
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrder_Min (int Order_Min);

	/**
	 * Get Minimum Order Qty.
	 * Minimum order quantity in UOM
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrder_Min();

	ModelColumn<I_I_Product, Object> COLUMN_Order_Min = new ModelColumn<>(I_I_Product.class, "Order_Min", null);
	String COLUMNNAME_Order_Min = "Order_Min";

	/**
	 * Set Packungsgröße.
	 * Package order size in UOM (e.g. order set of 5 units)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrder_Pack (int Order_Pack);

	/**
	 * Get Packungsgröße.
	 * Package order size in UOM (e.g. order set of 5 units)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrder_Pack();

	ModelColumn<I_I_Product, Object> COLUMN_Order_Pack = new ModelColumn<>(I_I_Product.class, "Order_Pack", null);
	String COLUMNNAME_Order_Pack = "Order_Pack";

	/**
	 * Set Package Size.
	 * Size of a package
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackageSize (@Nullable java.lang.String PackageSize);

	/**
	 * Get Package Size.
	 * Size of a package
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPackageSize();

	ModelColumn<I_I_Product, Object> COLUMN_PackageSize = new ModelColumn<>(I_I_Product.class, "PackageSize", null);
	String COLUMNNAME_PackageSize = "PackageSize";

	/**
	 * Set Package UOM.
	 * UOM of the package
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackage_UOM_ID (int Package_UOM_ID);

	/**
	 * Get Package UOM.
	 * UOM of the package
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPackage_UOM_ID();

	String COLUMNNAME_Package_UOM_ID = "Package_UOM_ID";

	/**
	 * Set Price effective.
	 * Effective Date of Price
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceEffective (@Nullable java.sql.Timestamp PriceEffective);

	/**
	 * Get Price effective.
	 * Effective Date of Price
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPriceEffective();

	ModelColumn<I_I_Product, Object> COLUMN_PriceEffective = new ModelColumn<>(I_I_Product.class, "PriceEffective", null);
	String COLUMNNAME_PriceEffective = "PriceEffective";

	/**
	 * Set Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceLimit (@Nullable BigDecimal PriceLimit);

	/**
	 * Get Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceLimit();

	ModelColumn<I_I_Product, Object> COLUMN_PriceLimit = new ModelColumn<>(I_I_Product.class, "PriceLimit", null);
	String COLUMNNAME_PriceLimit = "PriceLimit";

	/**
	 * Set Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceList (@Nullable BigDecimal PriceList);

	/**
	 * Get Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceList();

	ModelColumn<I_I_Product, Object> COLUMN_PriceList = new ModelColumn<>(I_I_Product.class, "PriceList", null);
	String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Einkaufspreis.
	 * Price based on a purchase order
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPricePO (@Nullable BigDecimal PricePO);

	/**
	 * Get Einkaufspreis.
	 * Price based on a purchase order
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPricePO();

	ModelColumn<I_I_Product, Object> COLUMN_PricePO = new ModelColumn<>(I_I_Product.class, "PricePO", null);
	String COLUMNNAME_PricePO = "PricePO";

	/**
	 * Set Standard Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceStd (@Nullable BigDecimal PriceStd);

	/**
	 * Get Standard Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceStd();

	ModelColumn<I_I_Product, Object> COLUMN_PriceStd = new ModelColumn<>(I_I_Product.class, "PriceStd", null);
	String COLUMNNAME_PriceStd = "PriceStd";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_I_Product, Object> COLUMN_Processed = new ModelColumn<>(I_I_Product.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_I_Product, Object> COLUMN_Processing = new ModelColumn<>(I_I_Product.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Produktkategorie-Schlüssel.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductCategory_Value (@Nullable java.lang.String ProductCategory_Value);

	/**
	 * Get Produktkategorie-Schlüssel.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductCategory_Value();

	ModelColumn<I_I_Product, Object> COLUMN_ProductCategory_Value = new ModelColumn<>(I_I_Product.class, "ProductCategory_Value", null);
	String COLUMNNAME_ProductCategory_Value = "ProductCategory_Value";

	/**
	 * Set Product Manufacturer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductManufacturer (@Nullable java.lang.String ProductManufacturer);

	/**
	 * Get Product Manufacturer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductManufacturer();

	ModelColumn<I_I_Product, Object> COLUMN_ProductManufacturer = new ModelColumn<>(I_I_Product.class, "ProductManufacturer", null);
	String COLUMNNAME_ProductManufacturer = "ProductManufacturer";

	/**
	 * Set Product Type.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductType (@Nullable java.lang.String ProductType);

	/**
	 * Get Product Type.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductType();

	ModelColumn<I_I_Product, Object> COLUMN_ProductType = new ModelColumn<>(I_I_Product.class, "ProductType", null);
	String COLUMNNAME_ProductType = "ProductType";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_I_Product, Object> COLUMN_Qty = new ModelColumn<>(I_I_Product.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set PZN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPZN (@Nullable java.lang.String PZN);

	/**
	 * Get PZN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPZN();

	ModelColumn<I_I_Product, Object> COLUMN_PZN = new ModelColumn<>(I_I_Product.class, "PZN", null);
	String COLUMNNAME_PZN = "PZN";

	/**
	 * Set Raw Material Origin Country Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRawMaterialOriginCountryCode (@Nullable java.lang.String RawMaterialOriginCountryCode);

	/**
	 * Get Raw Material Origin Country Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRawMaterialOriginCountryCode();

	ModelColumn<I_I_Product, Object> COLUMN_RawMaterialOriginCountryCode = new ModelColumn<>(I_I_Product.class, "RawMaterialOriginCountryCode", null);
	String COLUMNNAME_RawMaterialOriginCountryCode = "RawMaterialOriginCountryCode";

	/**
	 * Set Ursprungsland.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRawMaterialOrigin_ID (int RawMaterialOrigin_ID);

	/**
	 * Get Ursprungsland.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRawMaterialOrigin_ID();

	@Nullable org.compiere.model.I_C_Country getRawMaterialOrigin();

	void setRawMaterialOrigin(@Nullable org.compiere.model.I_C_Country RawMaterialOrigin);

	ModelColumn<I_I_Product, org.compiere.model.I_C_Country> COLUMN_RawMaterialOrigin_ID = new ModelColumn<>(I_I_Product.class, "RawMaterialOrigin_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_RawMaterialOrigin_ID = "RawMaterialOrigin_ID";

	/**
	 * Set Lizenzbetrag.
	 * (Included) Amount for copyright, etc.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRoyaltyAmt (@Nullable BigDecimal RoyaltyAmt);

	/**
	 * Get Lizenzbetrag.
	 * (Included) Amount for copyright, etc.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getRoyaltyAmt();

	ModelColumn<I_I_Product, Object> COLUMN_RoyaltyAmt = new ModelColumn<>(I_I_Product.class, "RoyaltyAmt", null);
	String COLUMNNAME_RoyaltyAmt = "RoyaltyAmt";

	/**
	 * Set Regaltiefe.
	 * Shelf depth required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShelfDepth (BigDecimal ShelfDepth);

	/**
	 * Get Regaltiefe.
	 * Shelf depth required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getShelfDepth();

	ModelColumn<I_I_Product, Object> COLUMN_ShelfDepth = new ModelColumn<>(I_I_Product.class, "ShelfDepth", null);
	String COLUMNNAME_ShelfDepth = "ShelfDepth";

	/**
	 * Set Regalhöhe.
	 * Shelf height required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShelfHeight (BigDecimal ShelfHeight);

	/**
	 * Get Regalhöhe.
	 * Shelf height required
	 *
	 * <br>Type: BigDecimal
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getShelfHeight();

	ModelColumn<I_I_Product, Object> COLUMN_ShelfHeight = new ModelColumn<>(I_I_Product.class, "ShelfHeight", null);
	String COLUMNNAME_ShelfHeight = "ShelfHeight";

	/**
	 * Set Regalbreite.
	 * Shelf width required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShelfWidth (BigDecimal ShelfWidth);

	/**
	 * Get Regalbreite.
	 * Shelf width required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getShelfWidth();

	ModelColumn<I_I_Product, Object> COLUMN_ShelfWidth = new ModelColumn<>(I_I_Product.class, "ShelfWidth", null);
	String COLUMNNAME_ShelfWidth = "ShelfWidth";

	/**
	 * Set SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSKU (@Nullable java.lang.String SKU);

	/**
	 * Get SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSKU();

	ModelColumn<I_I_Product, Object> COLUMN_SKU = new ModelColumn<>(I_I_Product.class, "SKU", null);
	String COLUMNNAME_SKU = "SKU";

	/**
	 * Set Trademark.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTrademark (@Nullable java.lang.String Trademark);

	/**
	 * Get Trademark.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTrademark();

	ModelColumn<I_I_Product, Object> COLUMN_Trademark = new ModelColumn<>(I_I_Product.class, "Trademark", null);
	String COLUMNNAME_Trademark = "Trademark";

	/**
	 * Set Einheiten pro Palette.
	 * Units Per Pallet
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnitsPerPallet (int UnitsPerPallet);

	/**
	 * Get Einheiten pro Palette.
	 * Units Per Pallet
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUnitsPerPallet();

	ModelColumn<I_I_Product, Object> COLUMN_UnitsPerPallet = new ModelColumn<>(I_I_Product.class, "UnitsPerPallet", null);
	String COLUMNNAME_UnitsPerPallet = "UnitsPerPallet";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC();

	ModelColumn<I_I_Product, Object> COLUMN_UPC = new ModelColumn<>(I_I_Product.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_I_Product, Object> COLUMN_Updated = new ModelColumn<>(I_I_Product.class, "Updated", null);
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

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue();

	ModelColumn<I_I_Product, Object> COLUMN_Value = new ModelColumn<>(I_I_Product.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Vendor Category.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorCategory (@Nullable java.lang.String VendorCategory);

	/**
	 * Get Vendor Category.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVendorCategory();

	ModelColumn<I_I_Product, Object> COLUMN_VendorCategory = new ModelColumn<>(I_I_Product.class, "VendorCategory", null);
	String COLUMNNAME_VendorCategory = "VendorCategory";

	/**
	 * Set Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorProductNo (@Nullable java.lang.String VendorProductNo);

	/**
	 * Get Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVendorProductNo();

	ModelColumn<I_I_Product, Object> COLUMN_VendorProductNo = new ModelColumn<>(I_I_Product.class, "VendorProductNo", null);
	String COLUMNNAME_VendorProductNo = "VendorProductNo";

	/**
	 * Set Volume.
	 * Volume of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVolume (BigDecimal Volume);

	/**
	 * Get Volume.
	 * Volume of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getVolume();

	ModelColumn<I_I_Product, Object> COLUMN_Volume = new ModelColumn<>(I_I_Product.class, "Volume", null);
	String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Weight.
	 * Weight of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWeight (@Nullable BigDecimal Weight);

	/**
	 * Get Weight.
	 * Weight of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getWeight();

	ModelColumn<I_I_Product, Object> COLUMN_Weight = new ModelColumn<>(I_I_Product.class, "Weight", null);
	String COLUMNNAME_Weight = "Weight";

	/**
	 * Set Kodierung der Mengeneinheit.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setX12DE355 (@Nullable java.lang.String X12DE355);

	/**
	 * Get Kodierung der Mengeneinheit.
	 * UOM EDI X12 Code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getX12DE355();

	ModelColumn<I_I_Product, Object> COLUMN_X12DE355 = new ModelColumn<>(I_I_Product.class, "X12DE355", null);
	String COLUMNNAME_X12DE355 = "X12DE355";
}
