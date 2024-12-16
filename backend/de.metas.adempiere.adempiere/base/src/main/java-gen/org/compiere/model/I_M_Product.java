package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product 
{

	String Table_Name = "M_Product";

//	/** AD_Table_ID=208 */
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
	 * Set Additional Product Info.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAdditional_produktinfos (@Nullable java.lang.String Additional_produktinfos);

	/**
	 * Get Additional Product Info.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAdditional_produktinfos();

	ModelColumn<I_M_Product, Object> COLUMN_Additional_produktinfos = new ModelColumn<>(I_M_Product.class, "Additional_produktinfos", null);
	String COLUMNNAME_Additional_produktinfos = "Additional_produktinfos";

	/**
	 * Set Alternative Products.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAlternativeProducts (@Nullable java.lang.String AlternativeProducts);

	/**
	 * Get Alternative Products.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getAlternativeProducts();

	ModelColumn<I_M_Product, Object> COLUMN_AlternativeProducts = new ModelColumn<>(I_M_Product.class, "AlternativeProducts", null);
	String COLUMNNAME_AlternativeProducts = "AlternativeProducts";

	/**
	 * Set BioControlNumber.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBioControlNumber (@Nullable java.lang.String BioControlNumber);

	/**
	 * Get BioControlNumber.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBioControlNumber();

	ModelColumn<I_M_Product, Object> COLUMN_BioControlNumber = new ModelColumn<>(I_M_Product.class, "BioControlNumber", null);
	String COLUMNNAME_BioControlNumber = "BioControlNumber";

	/**
	 * Set CompensationGroup Schema Category.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_Schema_Category_ID (int C_CompensationGroup_Schema_Category_ID);

	/**
	 * Get CompensationGroup Schema Category.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_Schema_Category_ID();

	@Nullable org.compiere.model.I_C_CompensationGroup_Schema_Category getC_CompensationGroup_Schema_Category();

	void setC_CompensationGroup_Schema_Category(@Nullable org.compiere.model.I_C_CompensationGroup_Schema_Category C_CompensationGroup_Schema_Category);

	ModelColumn<I_M_Product, org.compiere.model.I_C_CompensationGroup_Schema_Category> COLUMN_C_CompensationGroup_Schema_Category_ID = new ModelColumn<>(I_M_Product.class, "C_CompensationGroup_Schema_Category_ID", org.compiere.model.I_C_CompensationGroup_Schema_Category.class);
	String COLUMNNAME_C_CompensationGroup_Schema_Category_ID = "C_CompensationGroup_Schema_Category_ID";

	/**
	 * Set Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID);

	/**
	 * Get Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_Schema_ID();

	@Nullable de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema();

	void setC_CompensationGroup_Schema(@Nullable de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema);

	ModelColumn<I_M_Product, de.metas.order.model.I_C_CompensationGroup_Schema> COLUMN_C_CompensationGroup_Schema_ID = new ModelColumn<>(I_M_Product.class, "C_CompensationGroup_Schema_ID", de.metas.order.model.I_C_CompensationGroup_Schema.class);
	String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	/**
	 * Set Revenue Recognition.
	 * Method for recording revenue
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_RevenueRecognition_ID (int C_RevenueRecognition_ID);

	/**
	 * Get Revenue Recognition.
	 * Method for recording revenue
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_RevenueRecognition_ID();

	@Nullable org.compiere.model.I_C_RevenueRecognition getC_RevenueRecognition();

	void setC_RevenueRecognition(@Nullable org.compiere.model.I_C_RevenueRecognition C_RevenueRecognition);

	ModelColumn<I_M_Product, org.compiere.model.I_C_RevenueRecognition> COLUMN_C_RevenueRecognition_ID = new ModelColumn<>(I_M_Product.class, "C_RevenueRecognition_ID", org.compiere.model.I_C_RevenueRecognition.class);
	String COLUMNNAME_C_RevenueRecognition_ID = "C_RevenueRecognition_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Classification.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClassification (@Nullable java.lang.String Classification);

	/**
	 * Get Classification.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClassification();

	ModelColumn<I_M_Product, Object> COLUMN_Classification = new ModelColumn<>(I_M_Product.class, "Classification", null);
	String COLUMNNAME_Classification = "Classification";

	/**
	 * Set Consumption Recommentation.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConsumption_Recommentation (@Nullable java.lang.String Consumption_Recommentation);

	/**
	 * Get Consumption Recommentation.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getConsumption_Recommentation();

	ModelColumn<I_M_Product, Object> COLUMN_Consumption_Recommentation = new ModelColumn<>(I_M_Product.class, "Consumption_Recommentation", null);
	String COLUMNNAME_Consumption_Recommentation = "Consumption_Recommentation";

	/**
	 * Set Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContent (@Nullable java.lang.String Content);

	/**
	 * Get Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContent();

	ModelColumn<I_M_Product, Object> COLUMN_Content = new ModelColumn<>(I_M_Product.class, "Content", null);
	String COLUMNNAME_Content = "Content";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product, Object> COLUMN_Created = new ModelColumn<>(I_M_Product.class, "Created", null);
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
	 * Set Customer Labelname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerLabelName (@Nullable java.lang.String CustomerLabelName);

	/**
	 * Get Customer Labelname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomerLabelName();

	ModelColumn<I_M_Product, Object> COLUMN_CustomerLabelName = new ModelColumn<>(I_M_Product.class, "CustomerLabelName", null);
	String COLUMNNAME_CustomerLabelName = "CustomerLabelName";

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

	ModelColumn<I_M_Product, Object> COLUMN_Description = new ModelColumn<>(I_M_Product.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Description URL.
	 * URL for the description
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionURL (@Nullable java.lang.String DescriptionURL);

	/**
	 * Get Description URL.
	 * URL for the description
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionURL();

	ModelColumn<I_M_Product, Object> COLUMN_DescriptionURL = new ModelColumn<>(I_M_Product.class, "DescriptionURL", null);
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

	ModelColumn<I_M_Product, Object> COLUMN_DietType = new ModelColumn<>(I_M_Product.class, "DietType", null);
	String COLUMNNAME_DietType = "DietType";

	/**
	 * Set Discontinued.
	 * This product is no longer available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscontinued (boolean Discontinued);

	/**
	 * Get Discontinued.
	 * This product is no longer available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDiscontinued();

	ModelColumn<I_M_Product, Object> COLUMN_Discontinued = new ModelColumn<>(I_M_Product.class, "Discontinued", null);
	String COLUMNNAME_Discontinued = "Discontinued";

	/**
	 * Set Discontinued by.
	 * Discontinued By
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscontinuedBy (@Nullable java.sql.Timestamp DiscontinuedBy);

	/**
	 * Get Discontinued by.
	 * Discontinued By
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDiscontinuedBy();

	ModelColumn<I_M_Product, Object> COLUMN_DiscontinuedBy = new ModelColumn<>(I_M_Product.class, "DiscontinuedBy", null);
	String COLUMNNAME_DiscontinuedBy = "DiscontinuedBy";

	/**
	 * Set Discontinued from.
	 * If a product is marked as discontinued via the checkbox, then this field can be used to specify from which date onwards it shall be discontinued
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscontinuedFrom (@Nullable java.sql.Timestamp DiscontinuedFrom);

	/**
	 * Get Discontinued from.
	 * If a product is marked as discontinued via the checkbox, then this field can be used to specify from which date onwards it shall be discontinued
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDiscontinuedFrom();

	ModelColumn<I_M_Product, Object> COLUMN_DiscontinuedFrom = new ModelColumn<>(I_M_Product.class, "DiscontinuedFrom", null);
	String COLUMNNAME_DiscontinuedFrom = "DiscontinuedFrom";

	/**
	 * Set Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNote (@Nullable java.lang.String DocumentNote);

	/**
	 * Get Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNote();

	ModelColumn<I_M_Product, Object> COLUMN_DocumentNote = new ModelColumn<>(I_M_Product.class, "DocumentNote", null);
	String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set Menge-Darreichung .
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDosageQty (@Nullable BigDecimal DosageQty);

	/**
	 * Get Menge-Darreichung .
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDosageQty();

	ModelColumn<I_M_Product, Object> COLUMN_DosageQty = new ModelColumn<>(I_M_Product.class, "DosageQty", null);
	String COLUMNNAME_DosageQty = "DosageQty";

	/**
	 * Set Darreichungsform-Einheit .
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDosageUOM_ID (int DosageUOM_ID);

	/**
	 * Get Darreichungsform-Einheit .
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDosageUOM_ID();

	String COLUMNNAME_DosageUOM_ID = "DosageUOM_ID";

	/**
	 * Set Drying Methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDryingMethods (@Nullable java.lang.String DryingMethods);

	/**
	 * Get Drying Methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDryingMethods();

	ModelColumn<I_M_Product, Object> COLUMN_DryingMethods = new ModelColumn<>(I_M_Product.class, "DryingMethods", null);
	String COLUMNNAME_DryingMethods = "DryingMethods";

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

	ModelColumn<I_M_Product, Object> COLUMN_ExternalId = new ModelColumn<>(I_M_Product.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set FLO ID.
	 * ID granted by FLOCERT for companies to put it on their products.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFLO_Identifier (@Nullable java.lang.String FLO_Identifier);

	/**
	 * Get FLO ID.
	 * ID granted by FLOCERT for companies to put it on their products.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFLO_Identifier();

	ModelColumn<I_M_Product, Object> COLUMN_FLO_Identifier = new ModelColumn<>(I_M_Product.class, "FLO_Identifier", null);
	String COLUMNNAME_FLO_Identifier = "FLO_Identifier";

	/**
	 * Set Grade.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGrade (@Nullable java.lang.String Grade);

	/**
	 * Get Grade.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGrade();

	ModelColumn<I_M_Product, Object> COLUMN_Grade = new ModelColumn<>(I_M_Product.class, "Grade", null);
	String COLUMNNAME_Grade = "Grade";

	/**
	 * Set Group1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroup1 (@Nullable java.lang.String Group1);

	/**
	 * Get Group1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroup1();

	ModelColumn<I_M_Product, Object> COLUMN_Group1 = new ModelColumn<>(I_M_Product.class, "Group1", null);
	String COLUMNNAME_Group1 = "Group1";

	/**
	 * Set Group2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroup2 (@Nullable java.lang.String Group2);

	/**
	 * Get Group2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroup2();

	ModelColumn<I_M_Product, Object> COLUMN_Group2 = new ModelColumn<>(I_M_Product.class, "Group2", null);
	String COLUMNNAME_Group2 = "Group2";

	/**
	 * Set Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationAmtType (@Nullable java.lang.String GroupCompensationAmtType);

	/**
	 * Get Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroupCompensationAmtType();

	ModelColumn<I_M_Product, Object> COLUMN_GroupCompensationAmtType = new ModelColumn<>(I_M_Product.class, "GroupCompensationAmtType", null);
	String COLUMNNAME_GroupCompensationAmtType = "GroupCompensationAmtType";

	/**
	 * Set Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationType (@Nullable java.lang.String GroupCompensationType);

	/**
	 * Get Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroupCompensationType();

	ModelColumn<I_M_Product, Object> COLUMN_GroupCompensationType = new ModelColumn<>(I_M_Product.class, "GroupCompensationType", null);
	String COLUMNNAME_GroupCompensationType = "GroupCompensationType";

	/**
	 * Set GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGTIN (@Nullable java.lang.String GTIN);

	/**
	 * Get GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGTIN();

	ModelColumn<I_M_Product, Object> COLUMN_GTIN = new ModelColumn<>(I_M_Product.class, "GTIN", null);
	String COLUMNNAME_GTIN = "GTIN";

	/**
	 * Set Min Guarantee Days.
	 * Minimum number of guarantee days
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGuaranteeDaysMin (int GuaranteeDaysMin);

	/**
	 * Get Min Guarantee Days.
	 * Minimum number of guarantee days
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGuaranteeDaysMin();

	ModelColumn<I_M_Product, Object> COLUMN_GuaranteeDaysMin = new ModelColumn<>(I_M_Product.class, "GuaranteeDaysMin", null);
	String COLUMNNAME_GuaranteeDaysMin = "GuaranteeDaysMin";

	/**
	 * Set BBD opened.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGuaranteeDaysMin_opened (int GuaranteeDaysMin_opened);

	/**
	 * Get BBD opened.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGuaranteeDaysMin_opened();

	ModelColumn<I_M_Product, Object> COLUMN_GuaranteeDaysMin_opened = new ModelColumn<>(I_M_Product.class, "GuaranteeDaysMin_opened", null);
	String COLUMNNAME_GuaranteeDaysMin_opened = "GuaranteeDaysMin_opened";

	/**
	 * Set GuaranteeMonths.
	 * Guarantee time in months
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGuaranteeMonths (@Nullable java.lang.String GuaranteeMonths);

	/**
	 * Get GuaranteeMonths.
	 * Guarantee time in months
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGuaranteeMonths();

	ModelColumn<I_M_Product, Object> COLUMN_GuaranteeMonths = new ModelColumn<>(I_M_Product.class, "GuaranteeMonths", null);
	String COLUMNNAME_GuaranteeMonths = "GuaranteeMonths";

	/**
	 * Set Haddex Check.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHaddexCheck (boolean HaddexCheck);

	/**
	 * Get Haddex Check.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isHaddexCheck();

	ModelColumn<I_M_Product, Object> COLUMN_HaddexCheck = new ModelColumn<>(I_M_Product.class, "HaddexCheck", null);
	String COLUMNNAME_HaddexCheck = "HaddexCheck";

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

	ModelColumn<I_M_Product, Object> COLUMN_Help = new ModelColumn<>(I_M_Product.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set HU Clearance.
	 * Initial clearance status for newly created or received handling units with this product.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHUClearanceStatus (@Nullable java.lang.String HUClearanceStatus);

	/**
	 * Get HU Clearance.
	 * Initial clearance status for newly created or received handling units with this product.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHUClearanceStatus();

	ModelColumn<I_M_Product, Object> COLUMN_HUClearanceStatus = new ModelColumn<>(I_M_Product.class, "HUClearanceStatus", null);
	String COLUMNNAME_HUClearanceStatus = "HUClearanceStatus";

	/**
	 * Set Image URL.
	 * URL of  image
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImageURL (@Nullable java.lang.String ImageURL);

	/**
	 * Get Image URL.
	 * URL of  image
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImageURL();

	ModelColumn<I_M_Product, Object> COLUMN_ImageURL = new ModelColumn<>(I_M_Product.class, "ImageURL", null);
	String COLUMNNAME_ImageURL = "ImageURL";

	/**
	 * Set Ingredients.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIngredients (@Nullable java.lang.String Ingredients);

	/**
	 * Get Ingredients.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIngredients();

	ModelColumn<I_M_Product, Object> COLUMN_Ingredients = new ModelColumn<>(I_M_Product.class, "Ingredients", null);
	String COLUMNNAME_Ingredients = "Ingredients";

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

	ModelColumn<I_M_Product, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Bill of Materials.
	 * Bill of Materials
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBOM (boolean IsBOM);

	/**
	 * Get Bill of Materials.
	 * Bill of Materials
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBOM();

	ModelColumn<I_M_Product, Object> COLUMN_IsBOM = new ModelColumn<>(I_M_Product.class, "IsBOM", null);
	String COLUMNNAME_IsBOM = "IsBOM";

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

	ModelColumn<I_M_Product, Object> COLUMN_IsCommissioned = new ModelColumn<>(I_M_Product.class, "IsCommissioned", null);
	String COLUMNNAME_IsCommissioned = "IsCommissioned";

	/**
	 * Set Different shipping address.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Different shipping address.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDropShip();

	ModelColumn<I_M_Product, Object> COLUMN_IsDropShip = new ModelColumn<>(I_M_Product.class, "IsDropShip", null);
	String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Enforce Issuing Tolerance.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEnforceIssuingTolerance (boolean IsEnforceIssuingTolerance);

	/**
	 * Get Enforce Issuing Tolerance.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEnforceIssuingTolerance();

	ModelColumn<I_M_Product, Object> COLUMN_IsEnforceIssuingTolerance = new ModelColumn<>(I_M_Product.class, "IsEnforceIssuingTolerance", null);
	String COLUMNNAME_IsEnforceIssuingTolerance = "IsEnforceIssuingTolerance";

	/**
	 * Set Exclude Auto Delivery.
	 * Exclude from automatic Delivery
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludeAutoDelivery (boolean IsExcludeAutoDelivery);

	/**
	 * Get Exclude Auto Delivery.
	 * Exclude from automatic Delivery
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeAutoDelivery();

	ModelColumn<I_M_Product, Object> COLUMN_IsExcludeAutoDelivery = new ModelColumn<>(I_M_Product.class, "IsExcludeAutoDelivery", null);
	String COLUMNNAME_IsExcludeAutoDelivery = "IsExcludeAutoDelivery";

	/**
	 * Set Print detail records on invoice .
	 * Print detail BOM elements on the invoice
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoicePrintDetails (boolean IsInvoicePrintDetails);

	/**
	 * Get Print detail records on invoice .
	 * Print detail BOM elements on the invoice
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoicePrintDetails();

	ModelColumn<I_M_Product, Object> COLUMN_IsInvoicePrintDetails = new ModelColumn<>(I_M_Product.class, "IsInvoicePrintDetails", null);
	String COLUMNNAME_IsInvoicePrintDetails = "IsInvoicePrintDetails";

	/**
	 * Set Manufactured.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsManufactured (boolean IsManufactured);

	/**
	 * Get Manufactured.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isManufactured();

	ModelColumn<I_M_Product, Object> COLUMN_IsManufactured = new ModelColumn<>(I_M_Product.class, "IsManufactured", null);
	String COLUMNNAME_IsManufactured = "IsManufactured";

	/**
	 * Set Print detail records on pick list.
	 * Print detail BOM elements on the pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickListPrintDetails (boolean IsPickListPrintDetails);

	/**
	 * Get Print detail records on pick list.
	 * Print detail BOM elements on the pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickListPrintDetails();

	ModelColumn<I_M_Product, Object> COLUMN_IsPickListPrintDetails = new ModelColumn<>(I_M_Product.class, "IsPickListPrintDetails", null);
	String COLUMNNAME_IsPickListPrintDetails = "IsPickListPrintDetails";

	/**
	 * Set Print as packing material.
	 * When activated, packing material products will be printed on documents only when also used as packing materials in the order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrintWhenPackingMaterial (boolean IsPrintWhenPackingMaterial);

	/**
	 * Get Print as packing material.
	 * When activated, packing material products will be printed on documents only when also used as packing materials in the order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrintWhenPackingMaterial();

	ModelColumn<I_M_Product, Object> COLUMN_IsPrintWhenPackingMaterial = new ModelColumn<>(I_M_Product.class, "IsPrintWhenPackingMaterial", null);
	String COLUMNNAME_IsPrintWhenPackingMaterial = "IsPrintWhenPackingMaterial";

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

	ModelColumn<I_M_Product, Object> COLUMN_IsPurchased = new ModelColumn<>(I_M_Product.class, "IsPurchased", null);
	String COLUMNNAME_IsPurchased = "IsPurchased";

	/**
	 * Set Quotation Groupping Product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsQuotationGroupping (boolean IsQuotationGroupping);

	/**
	 * Get Quotation Groupping Product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isQuotationGroupping();

	ModelColumn<I_M_Product, Object> COLUMN_IsQuotationGroupping = new ModelColumn<>(I_M_Product.class, "IsQuotationGroupping", null);
	String COLUMNNAME_IsQuotationGroupping = "IsQuotationGroupping";

	/**
	 * Set Self-Service.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Self-Service.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelfService();

	ModelColumn<I_M_Product, Object> COLUMN_IsSelfService = new ModelColumn<>(I_M_Product.class, "IsSelfService", null);
	String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Sold.
	 * Organization sells this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSold (boolean IsSold);

	/**
	 * Get Sold.
	 * Organization sells this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSold();

	ModelColumn<I_M_Product, Object> COLUMN_IsSold = new ModelColumn<>(I_M_Product.class, "IsSold", null);
	String COLUMNNAME_IsSold = "IsSold";

	/**
	 * Set Stocked.
	 * Organization stocks this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsStocked (boolean IsStocked);

	/**
	 * Get Stocked.
	 * Organization stocks this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isStocked();

	ModelColumn<I_M_Product, Object> COLUMN_IsStocked = new ModelColumn<>(I_M_Product.class, "IsStocked", null);
	String COLUMNNAME_IsStocked = "IsStocked";

	/**
	 * Set Tolerance %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssuingTolerance_Perc (@Nullable BigDecimal IssuingTolerance_Perc);

	/**
	 * Get Tolerance %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getIssuingTolerance_Perc();

	ModelColumn<I_M_Product, Object> COLUMN_IssuingTolerance_Perc = new ModelColumn<>(I_M_Product.class, "IssuingTolerance_Perc", null);
	String COLUMNNAME_IssuingTolerance_Perc = "IssuingTolerance_Perc";

	/**
	 * Set Tolerance.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssuingTolerance_Qty (@Nullable BigDecimal IssuingTolerance_Qty);

	/**
	 * Get Tolerance.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getIssuingTolerance_Qty();

	ModelColumn<I_M_Product, Object> COLUMN_IssuingTolerance_Qty = new ModelColumn<>(I_M_Product.class, "IssuingTolerance_Qty", null);
	String COLUMNNAME_IssuingTolerance_Qty = "IssuingTolerance_Qty";

	/**
	 * Set Tolerance UOM.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssuingTolerance_UOM_ID (int IssuingTolerance_UOM_ID);

	/**
	 * Get Tolerance UOM.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getIssuingTolerance_UOM_ID();

	String COLUMNNAME_IssuingTolerance_UOM_ID = "IssuingTolerance_UOM_ID";

	/**
	 * Set Tolerance Value Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIssuingTolerance_ValueType (@Nullable java.lang.String IssuingTolerance_ValueType);

	/**
	 * Get Tolerance Value Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIssuingTolerance_ValueType();

	ModelColumn<I_M_Product, Object> COLUMN_IssuingTolerance_ValueType = new ModelColumn<>(I_M_Product.class, "IssuingTolerance_ValueType", null);
	String COLUMNNAME_IssuingTolerance_ValueType = "IssuingTolerance_ValueType";

	/**
	 * Set Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_M_Product, Object> COLUMN_IsSummary = new ModelColumn<>(I_M_Product.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Verified.
	 * The BOM configuration has been verified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsVerified (boolean IsVerified);

	/**
	 * Get Verified.
	 * The BOM configuration has been verified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isVerified();

	ModelColumn<I_M_Product, Object> COLUMN_IsVerified = new ModelColumn<>(I_M_Product.class, "IsVerified", null);
	String COLUMNNAME_IsVerified = "IsVerified";

	/**
	 * Set Featured in Web Store.
	 * If selected, the product is displayed in the initial or any empty search
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWebStoreFeatured (boolean IsWebStoreFeatured);

	/**
	 * Get Featured in Web Store.
	 * If selected, the product is displayed in the initial or any empty search
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWebStoreFeatured();

	ModelColumn<I_M_Product, Object> COLUMN_IsWebStoreFeatured = new ModelColumn<>(I_M_Product.class, "IsWebStoreFeatured", null);
	String COLUMNNAME_IsWebStoreFeatured = "IsWebStoreFeatured";

	/**
	 * Set Legal Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLegalName (@Nullable java.lang.String LegalName);

	/**
	 * Get Legal Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLegalName();

	ModelColumn<I_M_Product, Object> COLUMN_LegalName = new ModelColumn<>(I_M_Product.class, "LegalName", null);
	String COLUMNNAME_LegalName = "LegalName";

	/**
	 * Set Long text description.
	 *
	 * <br>Type: Memo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLongTextDescription (@Nullable java.lang.String LongTextDescription);

	/**
	 * Get Long text description.
	 *
	 * <br>Type: Memo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLongTextDescription();

	ModelColumn<I_M_Product, Object> COLUMN_LongTextDescription = new ModelColumn<>(I_M_Product.class, "LongTextDescription", null);
	String COLUMNNAME_LongTextDescription = "LongTextDescription";

	/**
	 * Set Attribute Set.
	 * Product Attribute Set
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Attribute Set.
	 * Product Attribute Set
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSet_ID();

	@Nullable org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	void setM_AttributeSet(@Nullable org.compiere.model.I_M_AttributeSet M_AttributeSet);

	ModelColumn<I_M_Product, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new ModelColumn<>(I_M_Product.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
	String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_M_Product, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_Product.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Commodity Number.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CommodityNumber_ID (int M_CommodityNumber_ID);

	/**
	 * Get Commodity Number.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CommodityNumber_ID();

	ModelColumn<I_M_Product, Object> COLUMN_M_CommodityNumber_ID = new ModelColumn<>(I_M_Product.class, "M_CommodityNumber_ID", null);
	String COLUMNNAME_M_CommodityNumber_ID = "M_CommodityNumber_ID";

	/**
	 * Set Customs Tariff.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CustomsTariff_ID (int M_CustomsTariff_ID);

	/**
	 * Get Customs Tariff.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CustomsTariff_ID();

	@Nullable org.compiere.model.I_M_CustomsTariff getM_CustomsTariff();

	void setM_CustomsTariff(@Nullable org.compiere.model.I_M_CustomsTariff M_CustomsTariff);

	ModelColumn<I_M_Product, org.compiere.model.I_M_CustomsTariff> COLUMN_M_CustomsTariff_ID = new ModelColumn<>(I_M_Product.class, "M_CustomsTariff_ID", org.compiere.model.I_M_CustomsTariff.class);
	String COLUMNNAME_M_CustomsTariff_ID = "M_CustomsTariff_ID";

	/**
	 * Set Freight Category.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_FreightCategory_ID (int M_FreightCategory_ID);

	/**
	 * Get Freight Category.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_FreightCategory_ID();

	@Nullable org.compiere.model.I_M_FreightCategory getM_FreightCategory();

	void setM_FreightCategory(@Nullable org.compiere.model.I_M_FreightCategory M_FreightCategory);

	ModelColumn<I_M_Product, org.compiere.model.I_M_FreightCategory> COLUMN_M_FreightCategory_ID = new ModelColumn<>(I_M_Product.class, "M_FreightCategory_ID", org.compiere.model.I_M_FreightCategory.class);
	String COLUMNNAME_M_FreightCategory_ID = "M_FreightCategory_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	ModelColumn<I_M_Product, Object> COLUMN_M_Product_ID = new ModelColumn<>(I_M_Product.class, "M_Product_ID", null);
	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Product Planning Schema.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ProductPlanningSchema_Selector (@Nullable java.lang.String M_ProductPlanningSchema_Selector);

	/**
	 * Get Product Planning Schema.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getM_ProductPlanningSchema_Selector();

	ModelColumn<I_M_Product, Object> COLUMN_M_ProductPlanningSchema_Selector = new ModelColumn<>(I_M_Product.class, "M_ProductPlanningSchema_Selector", null);
	String COLUMNNAME_M_ProductPlanningSchema_Selector = "M_ProductPlanningSchema_Selector";

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

	ModelColumn<I_M_Product, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_M_Product.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Manufacturer.
	 * Manufacturer of the Product
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturer_ID (int Manufacturer_ID);

	/**
	 * Get Manufacturer.
	 * Manufacturer of the Product
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getManufacturer_ID();

	String COLUMNNAME_Manufacturer_ID = "Manufacturer_ID";

	/**
	 * Set Manufacturer number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturerArticleNumber (@Nullable java.lang.String ManufacturerArticleNumber);

	/**
	 * Get Manufacturer number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getManufacturerArticleNumber();

	ModelColumn<I_M_Product, Object> COLUMN_ManufacturerArticleNumber = new ModelColumn<>(I_M_Product.class, "ManufacturerArticleNumber", null);
	String COLUMNNAME_ManufacturerArticleNumber = "ManufacturerArticleNumber";

	/**
	 * Set Product description of manufacturer.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturerProductDescription (@Nullable java.lang.String ManufacturerProductDescription);

	/**
	 * Get Product description of manufacturer.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getManufacturerProductDescription();

	ModelColumn<I_M_Product, Object> COLUMN_ManufacturerProductDescription = new ModelColumn<>(I_M_Product.class, "ManufacturerProductDescription", null);
	String COLUMNNAME_ManufacturerProductDescription = "ManufacturerProductDescription";

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

	ModelColumn<I_M_Product, Object> COLUMN_ManufacturingMethod = new ModelColumn<>(I_M_Product.class, "ManufacturingMethod", null);
	String COLUMNNAME_ManufacturingMethod = "ManufacturingMethod";

	/**
	 * Set Exclude from MRP.
	 * Exclude from MRP calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMRP_Exclude (@Nullable java.lang.String MRP_Exclude);

	/**
	 * Get Exclude from MRP.
	 * Exclude from MRP calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMRP_Exclude();

	ModelColumn<I_M_Product, Object> COLUMN_MRP_Exclude = new ModelColumn<>(I_M_Product.class, "MRP_Exclude", null);
	String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_M_Product, Object> COLUMN_Name = new ModelColumn<>(I_M_Product.class, "Name", null);
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

	ModelColumn<I_M_Product, Object> COLUMN_NetWeight = new ModelColumn<>(I_M_Product.class, "NetWeight", null);
	String COLUMNNAME_NetWeight = "NetWeight";

	/**
	 * Set Netto-Verkaufsmengeneinheit .
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNetWeight_UOM_ID (int NetWeight_UOM_ID);

	/**
	 * Get Netto-Verkaufsmengeneinheit .
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getNetWeight_UOM_ID();

	String COLUMNNAME_NetWeight_UOM_ID = "NetWeight_UOM_ID";

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

	ModelColumn<I_M_Product, Object> COLUMN_PackageSize = new ModelColumn<>(I_M_Product.class, "PackageSize", null);
	String COLUMNNAME_PackageSize = "PackageSize";

	/**
	 * Set Per 100g.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPer100g (@Nullable BigDecimal Per100g);

	/**
	 * Get Per 100g.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPer100g();

	ModelColumn<I_M_Product, Object> COLUMN_Per100g = new ModelColumn<>(I_M_Product.class, "Per100g", null);
	String COLUMNNAME_Per100g = "Per100g";

	/**
	 * Set Maturity tolerance for picking (months after).
	 * Number of months that a pickable handling unit may be older  than specified in the shipment disposition.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPicking_AgeTolerance_AfterMonths (int Picking_AgeTolerance_AfterMonths);

	/**
	 * Get Maturity tolerance for picking (months after).
	 * Number of months that a pickable handling unit may be older  than specified in the shipment disposition.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPicking_AgeTolerance_AfterMonths();

	ModelColumn<I_M_Product, Object> COLUMN_Picking_AgeTolerance_AfterMonths = new ModelColumn<>(I_M_Product.class, "Picking_AgeTolerance_AfterMonths", null);
	String COLUMNNAME_Picking_AgeTolerance_AfterMonths = "Picking_AgeTolerance_AfterMonths";

	/**
	 * Set Maturity tolerance for picking (months before).
	 * Number of months that a pickable handling unit may be younger than specified in the shipment disposition.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPicking_AgeTolerance_BeforeMonths (int Picking_AgeTolerance_BeforeMonths);

	/**
	 * Get Maturity tolerance for picking (months before).
	 * Number of months that a pickable handling unit may be younger than specified in the shipment disposition.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPicking_AgeTolerance_BeforeMonths();

	ModelColumn<I_M_Product, Object> COLUMN_Picking_AgeTolerance_BeforeMonths = new ModelColumn<>(I_M_Product.class, "Picking_AgeTolerance_BeforeMonths", null);
	String COLUMNNAME_Picking_AgeTolerance_BeforeMonths = "Picking_AgeTolerance_BeforeMonths";

	/**
	 * Set PostControl.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostControl (@Nullable java.lang.String PostControl);

	/**
	 * Get PostControl.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostControl();

	ModelColumn<I_M_Product, Object> COLUMN_PostControl = new ModelColumn<>(I_M_Product.class, "PostControl", null);
	String COLUMNNAME_PostControl = "PostControl";

	/**
	 * Set Preparation.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparation (@Nullable java.lang.String Preparation);

	/**
	 * Get Preparation.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPreparation();

	ModelColumn<I_M_Product, Object> COLUMN_Preparation = new ModelColumn<>(I_M_Product.class, "Preparation", null);
	String COLUMNNAME_Preparation = "Preparation";

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

	ModelColumn<I_M_Product, Object> COLUMN_Processing = new ModelColumn<>(I_M_Product.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Procurement Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcurementStatus(@Nullable java.lang.String ProcurementStatus);

	/**
	 * Get Procurement Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	java.lang.String getProcurementStatus();

	ModelColumn<I_M_Product, Object> COLUMN_ProcurementStatus = new ModelColumn<>(I_M_Product.class, "ProcurementStatus", null);
	String COLUMNNAME_ProcurementStatus = "ProcurementStatus";

	/**
	 * Set Product Type.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProductType (java.lang.String ProductType);

	/**
	 * Get Product Type.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProductType();

	ModelColumn<I_M_Product, Object> COLUMN_ProductType = new ModelColumn<>(I_M_Product.class, "ProductType", null);
	String COLUMNNAME_ProductType = "ProductType";

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

	ModelColumn<I_M_Product, Object> COLUMN_PZN = new ModelColumn<>(I_M_Product.class, "PZN", null);
	String COLUMNNAME_PZN = "PZN";

	/**
	 * Set Mail Template.
	 * Text templates for mailings
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_MailText_ID (int R_MailText_ID);

	/**
	 * Get Mail Template.
	 * Text templates for mailings
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_MailText_ID();

	@Nullable org.compiere.model.I_R_MailText getR_MailText();

	void setR_MailText(@Nullable org.compiere.model.I_R_MailText R_MailText);

	ModelColumn<I_M_Product, org.compiere.model.I_R_MailText> COLUMN_R_MailText_ID = new ModelColumn<>(I_M_Product.class, "R_MailText_ID", org.compiere.model.I_R_MailText.class);
	String COLUMNNAME_R_MailText_ID = "R_MailText_ID";

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

	ModelColumn<I_M_Product, org.compiere.model.I_C_Country> COLUMN_RawMaterialOrigin_ID = new ModelColumn<>(I_M_Product.class, "RawMaterialOrigin_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_RawMaterialOrigin_ID = "RawMaterialOrigin_ID";

	/**
	 * Set Reference Text.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReferenceText (@Nullable java.lang.String ReferenceText);

	/**
	 * Get Reference Text.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReferenceText();

	ModelColumn<I_M_Product, Object> COLUMN_ReferenceText = new ModelColumn<>(I_M_Product.class, "ReferenceText", null);
	String COLUMNNAME_ReferenceText = "ReferenceText";

	/**
	 * Set Requires Supplier Approval.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequiresSupplierApproval (boolean RequiresSupplierApproval);

	/**
	 * Get Requires Supplier Approval.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRequiresSupplierApproval();

	ModelColumn<I_M_Product, Object> COLUMN_RequiresSupplierApproval = new ModelColumn<>(I_M_Product.class, "RequiresSupplierApproval", null);
	String COLUMNNAME_RequiresSupplierApproval = "RequiresSupplierApproval";

	/**
	 * Set Expense Type.
	 * Expense report type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_ExpenseType_ID (int S_ExpenseType_ID);

	/**
	 * Get Expense Type.
	 * Expense report type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_ExpenseType_ID();

	@Nullable org.compiere.model.I_S_ExpenseType getS_ExpenseType();

	void setS_ExpenseType(@Nullable org.compiere.model.I_S_ExpenseType S_ExpenseType);

	ModelColumn<I_M_Product, org.compiere.model.I_S_ExpenseType> COLUMN_S_ExpenseType_ID = new ModelColumn<>(I_M_Product.class, "S_ExpenseType_ID", org.compiere.model.I_S_ExpenseType.class);
	String COLUMNNAME_S_ExpenseType_ID = "S_ExpenseType_ID";

	/**
	 * Set Resource Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Resource_Group_ID (int S_Resource_Group_ID);

	/**
	 * Get Resource Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Resource_Group_ID();

	String COLUMNNAME_S_Resource_Group_ID = "S_Resource_Group_ID";

	/**
	 * Set Resource.
	 * Resource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Resource.
	 * Resource
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getS_Resource_ID();

	String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Product safety information.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSafetyInfo (@Nullable java.lang.String SafetyInfo);

	/**
	 * Get Product safety information.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSafetyInfo();

	ModelColumn<I_M_Product, Object> COLUMN_SafetyInfo = new ModelColumn<>(I_M_Product.class, "SafetyInfo", null);
	String COLUMNNAME_SafetyInfo = "SafetyInfo";

	/**
	 * Set Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRep_ID();

	String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set SAP Product Hierarchy.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSAP_ProductHierarchy (@Nullable java.lang.String SAP_ProductHierarchy);

	/**
	 * Get SAP Product Hierarchy.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSAP_ProductHierarchy();

	ModelColumn<I_M_Product, Object> COLUMN_SAP_ProductHierarchy = new ModelColumn<>(I_M_Product.class, "SAP_ProductHierarchy", null);
	String COLUMNNAME_SAP_ProductHierarchy = "SAP_ProductHierarchy";

	/**
	 * Set Shelf Depth.
	 * Shelf depth required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShelfDepth (BigDecimal ShelfDepth);

	/**
	 * Get Shelf Depth.
	 * Shelf depth required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getShelfDepth();

	ModelColumn<I_M_Product, Object> COLUMN_ShelfDepth = new ModelColumn<>(I_M_Product.class, "ShelfDepth", null);
	String COLUMNNAME_ShelfDepth = "ShelfDepth";

	/**
	 * Set Shelf Height.
	 * Shelf height required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShelfHeight (@Nullable BigDecimal ShelfHeight);

	/**
	 * Get Shelf Height.
	 * Shelf height required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getShelfHeight();

	ModelColumn<I_M_Product, Object> COLUMN_ShelfHeight = new ModelColumn<>(I_M_Product.class, "ShelfHeight", null);
	String COLUMNNAME_ShelfHeight = "ShelfHeight";

	/**
	 * Set Shelf Width.
	 * Shelf width required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShelfWidth (BigDecimal ShelfWidth);

	/**
	 * Get Shelf Width.
	 * Shelf width required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getShelfWidth();

	ModelColumn<I_M_Product, Object> COLUMN_ShelfWidth = new ModelColumn<>(I_M_Product.class, "ShelfWidth", null);
	String COLUMNNAME_ShelfWidth = "ShelfWidth";

	/**
	 * Set Shop Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShopDescription (@Nullable java.lang.String ShopDescription);

	/**
	 * Get Shop Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShopDescription();

	ModelColumn<I_M_Product, Object> COLUMN_ShopDescription = new ModelColumn<>(I_M_Product.class, "ShopDescription", null);
	String COLUMNNAME_ShopDescription = "ShopDescription";

	/**
	 * Set Shop inventory.
	 * Shop invenntory qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShopInventoryQty (@Nullable BigDecimal ShopInventoryQty);

	/**
	 * Get Shop inventory.
	 * Shop invenntory qty
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getShopInventoryQty();

	ModelColumn<I_M_Product, Object> COLUMN_ShopInventoryQty = new ModelColumn<>(I_M_Product.class, "ShopInventoryQty", null);
	String COLUMNNAME_ShopInventoryQty = "ShopInventoryQty";

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

	ModelColumn<I_M_Product, Object> COLUMN_SKU = new ModelColumn<>(I_M_Product.class, "SKU", null);
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

	ModelColumn<I_M_Product, Object> COLUMN_Trademark = new ModelColumn<>(I_M_Product.class, "Trademark", null);
	String COLUMNNAME_Trademark = "Trademark";

	/**
	 * Set UnitsPerPack.
	 * The Units Per Pack indicates the no of units of a product packed together.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnitsPerPack (int UnitsPerPack);

	/**
	 * Get UnitsPerPack.
	 * The Units Per Pack indicates the no of units of a product packed together.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUnitsPerPack();

	ModelColumn<I_M_Product, Object> COLUMN_UnitsPerPack = new ModelColumn<>(I_M_Product.class, "UnitsPerPack", null);
	String COLUMNNAME_UnitsPerPack = "UnitsPerPack";

	/**
	 * Set Units Per Pallet.
	 * Units Per Pallet
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnitsPerPallet (@Nullable BigDecimal UnitsPerPallet);

	/**
	 * Get Units Per Pallet.
	 * Units Per Pallet
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUnitsPerPallet();

	ModelColumn<I_M_Product, Object> COLUMN_UnitsPerPallet = new ModelColumn<>(I_M_Product.class, "UnitsPerPallet", null);
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

	ModelColumn<I_M_Product, Object> COLUMN_UPC = new ModelColumn<>(I_M_Product.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_M_Product, Object> COLUMN_Value = new ModelColumn<>(I_M_Product.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Version No.
	 * Version Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVersionNo (@Nullable java.lang.String VersionNo);

	/**
	 * Get Version No.
	 * Version Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVersionNo();

	ModelColumn<I_M_Product, Object> COLUMN_VersionNo = new ModelColumn<>(I_M_Product.class, "VersionNo", null);
	String COLUMNNAME_VersionNo = "VersionNo";

	/**
	 * Set Volume.
	 * Volume of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVolume (@Nullable BigDecimal Volume);

	/**
	 * Get Volume.
	 * Volume of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getVolume();

	ModelColumn<I_M_Product, Object> COLUMN_Volume = new ModelColumn<>(I_M_Product.class, "Volume", null);
	String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Warehouse Temperature.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouse_temperature (@Nullable java.lang.String Warehouse_temperature);

	/**
	 * Get Warehouse Temperature.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWarehouse_temperature();

	ModelColumn<I_M_Product, Object> COLUMN_Warehouse_temperature = new ModelColumn<>(I_M_Product.class, "Warehouse_temperature", null);
	String COLUMNNAME_Warehouse_temperature = "Warehouse_temperature";

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

	ModelColumn<I_M_Product, Object> COLUMN_Weight = new ModelColumn<>(I_M_Product.class, "Weight", null);
	String COLUMNNAME_Weight = "Weight";

    /**
     * Set QRCode_Configuration_ID.
     *
     * <br>Type: TableDir
     * <br>Mandatory: false
     * <br>Virtual Column: false
     */
    void setQRCode_Configuration_ID(int QRCode_Configuration_ID);

    /**
     * Get QRCode_Configuration_ID.
     *
     * <br>Type: TableDir
     * <br>Mandatory: false
     * <br>Virtual Column: false
     */
    int getQRCode_Configuration_ID();

    ModelColumn<I_M_Product, Object> COLUMN_QRCode_Configuration_ID = new ModelColumn<>(I_M_Product.class, "QRCode_Configuration_ID", null);
    String COLUMNNAME_QRCode_Configuration_ID = "QRCode_Configuration_ID";
}
