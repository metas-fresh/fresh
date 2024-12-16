// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product extends org.compiere.model.PO implements I_M_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1387146855L;

    /** Standard Constructor */
    public X_M_Product (final Properties ctx, final int M_Product_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAdditional_produktinfos (final @Nullable java.lang.String Additional_produktinfos)
	{
		set_Value (COLUMNNAME_Additional_produktinfos, Additional_produktinfos);
	}

	@Override
	public java.lang.String getAdditional_produktinfos() 
	{
		return get_ValueAsString(COLUMNNAME_Additional_produktinfos);
	}

	@Override
	public void setAlternativeProducts (final @Nullable java.lang.String AlternativeProducts)
	{
		throw new IllegalArgumentException ("AlternativeProducts is virtual column");	}

	@Override
	public java.lang.String getAlternativeProducts()
	{
		return get_ValueAsString(COLUMNNAME_AlternativeProducts);
	}

	/**
	 * BioControlNumber AD_Reference_ID=541516
	 * Reference name: BioControlNumber_Reference
	 */
	public static final int BIOCONTROLNUMBER_AD_Reference_ID=541516;
	/** Bio Control Number Test = BCN */
	public static final String BIOCONTROLNUMBER_BioControlNumberTest = "BCN";
	@Override
	public void setBioControlNumber (final @Nullable java.lang.String BioControlNumber)
	{
		set_Value (COLUMNNAME_BioControlNumber, BioControlNumber);
	}

	@Override
	public java.lang.String getBioControlNumber()
	{
		return get_ValueAsString(COLUMNNAME_BioControlNumber);
	}

	@Override
	public org.compiere.model.I_C_CompensationGroup_Schema_Category getC_CompensationGroup_Schema_Category()
	{
		return get_ValueAsPO(COLUMNNAME_C_CompensationGroup_Schema_Category_ID, org.compiere.model.I_C_CompensationGroup_Schema_Category.class);
	}

	@Override
	public void setC_CompensationGroup_Schema_Category(final org.compiere.model.I_C_CompensationGroup_Schema_Category C_CompensationGroup_Schema_Category)
	{
		set_ValueFromPO(COLUMNNAME_C_CompensationGroup_Schema_Category_ID, org.compiere.model.I_C_CompensationGroup_Schema_Category.class, C_CompensationGroup_Schema_Category);
	}

	@Override
	public void setC_CompensationGroup_Schema_Category_ID (final int C_CompensationGroup_Schema_Category_ID)
	{
		if (C_CompensationGroup_Schema_Category_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_Category_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_Category_ID, C_CompensationGroup_Schema_Category_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_Category_ID);
	}

	@Override
	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema()
	{
		return get_ValueAsPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class);
	}

	@Override
	public void setC_CompensationGroup_Schema(final de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema)
	{
		set_ValueFromPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class, C_CompensationGroup_Schema);
	}

	@Override
	public void setC_CompensationGroup_Schema_ID (final int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, C_CompensationGroup_Schema_ID);
	}

	@Override
	public int getC_CompensationGroup_Schema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_Schema_ID);
	}

	@Override
	public org.compiere.model.I_C_RevenueRecognition getC_RevenueRecognition()
	{
		return get_ValueAsPO(COLUMNNAME_C_RevenueRecognition_ID, org.compiere.model.I_C_RevenueRecognition.class);
	}

	@Override
	public void setC_RevenueRecognition(final org.compiere.model.I_C_RevenueRecognition C_RevenueRecognition)
	{
		set_ValueFromPO(COLUMNNAME_C_RevenueRecognition_ID, org.compiere.model.I_C_RevenueRecognition.class, C_RevenueRecognition);
	}

	@Override
	public void setC_RevenueRecognition_ID (final int C_RevenueRecognition_ID)
	{
		if (C_RevenueRecognition_ID < 1) 
			set_Value (COLUMNNAME_C_RevenueRecognition_ID, null);
		else 
			set_Value (COLUMNNAME_C_RevenueRecognition_ID, C_RevenueRecognition_ID);
	}

	@Override
	public int getC_RevenueRecognition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_RevenueRecognition_ID);
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
	public void setClassification (final @Nullable java.lang.String Classification)
	{
		set_Value (COLUMNNAME_Classification, Classification);
	}

	@Override
	public java.lang.String getClassification()
	{
		return get_ValueAsString(COLUMNNAME_Classification);
	}

	@Override
	public void setConsumption_Recommentation (final @Nullable java.lang.String Consumption_Recommentation)
	{
		set_Value (COLUMNNAME_Consumption_Recommentation, Consumption_Recommentation);
	}

	@Override
	public java.lang.String getConsumption_Recommentation()
	{
		return get_ValueAsString(COLUMNNAME_Consumption_Recommentation);
	}

	@Override
	public void setContent (final @Nullable java.lang.String Content)
	{
		set_Value (COLUMNNAME_Content, Content);
	}

	@Override
	public java.lang.String getContent()
	{
		return get_ValueAsString(COLUMNNAME_Content);
	}

	@Override
	public void setCustomerLabelName (final @Nullable java.lang.String CustomerLabelName)
	{
		set_Value (COLUMNNAME_CustomerLabelName, CustomerLabelName);
	}

	@Override
	public java.lang.String getCustomerLabelName() 
	{
		return get_ValueAsString(COLUMNNAME_CustomerLabelName);
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

	@Override
	public void setDescriptionURL (final @Nullable java.lang.String DescriptionURL)
	{
		set_Value (COLUMNNAME_DescriptionURL, DescriptionURL);
	}

	@Override
	public java.lang.String getDescriptionURL() 
	{
		return get_ValueAsString(COLUMNNAME_DescriptionURL);
	}

	/** 
	 * DietType AD_Reference_ID=541270
	 * Reference name: Ernährungsart
	 */
	public static final int DIETTYPE_AD_Reference_ID=541270;
	/** Vegan = Vegan */
	public static final String DIETTYPE_Vegan = "Vegan";
	/** Vegetarian = Vegetarian */
	public static final String DIETTYPE_Vegetarian = "Vegetarian";
	@Override
	public void setDietType (final @Nullable java.lang.String DietType)
	{
		set_Value (COLUMNNAME_DietType, DietType);
	}

	@Override
	public java.lang.String getDietType() 
	{
		return get_ValueAsString(COLUMNNAME_DietType);
	}

	@Override
	public void setDiscontinued (final boolean Discontinued)
	{
		set_Value (COLUMNNAME_Discontinued, Discontinued);
	}

	@Override
	public boolean isDiscontinued() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Discontinued);
	}

	@Override
	public void setDiscontinuedBy (final @Nullable java.sql.Timestamp DiscontinuedBy)
	{
		set_Value (COLUMNNAME_DiscontinuedBy, DiscontinuedBy);
	}

	@Override
	public java.sql.Timestamp getDiscontinuedBy() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DiscontinuedBy);
	}

	@Override
	public void setDiscontinuedFrom (final @Nullable java.sql.Timestamp DiscontinuedFrom)
	{
		set_Value (COLUMNNAME_DiscontinuedFrom, DiscontinuedFrom);
	}

	@Override
	public java.sql.Timestamp getDiscontinuedFrom()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DiscontinuedFrom);
	}

	@Override
	public void setDocumentNote (final @Nullable java.lang.String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	@Override
	public java.lang.String getDocumentNote() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNote);
	}

	@Override
	public void setDosageQty (final @Nullable BigDecimal DosageQty)
	{
		set_Value (COLUMNNAME_DosageQty, DosageQty);
	}

	@Override
	public BigDecimal getDosageQty()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DosageQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDosageUOM_ID (final int DosageUOM_ID)
	{
		if (DosageUOM_ID < 1)
			set_Value (COLUMNNAME_DosageUOM_ID, null);
		else
			set_Value (COLUMNNAME_DosageUOM_ID, DosageUOM_ID);
	}

	@Override
	public int getDosageUOM_ID()
	{
		return get_ValueAsInt(COLUMNNAME_DosageUOM_ID);
	}

	/**
	 * DryingMethods AD_Reference_ID=541511
	 * Reference name: M_Drying_Methods
	 */
	public static final int DRYINGMETHODS_AD_Reference_ID=541511;
	/** Gefriergetrocknet = Gefriergetrocknet */
	public static final String DRYINGMETHODS_Gefriergetrocknet = "Gefriergetrocknet";
	/** Luftgetrocknet = Luftgetrocknet */
	public static final String DRYINGMETHODS_Luftgetrocknet = "Luftgetrocknet";
	/** Schaufelgetrocknet = Schaufelgetrocknet */
	public static final String DRYINGMETHODS_Schaufelgetrocknet = "Schaufelgetrocknet";
	/** Sonnengetrocknet = Sonnengetrocknet */
	public static final String DRYINGMETHODS_Sonnengetrocknet = "Sonnengetrocknet";
	/** Sprühgetrocknet = Spruhgetrocknet */
	public static final String DRYINGMETHODS_Spruehgetrocknet = "Spruhgetrocknet";
	/** Vakuumgetrocknet = Vakuumgetrocknet */
	public static final String DRYINGMETHODS_Vakuumgetrocknet = "Vakuumgetrocknet";
	/** Walzengetrocknet = Walzengetrocknet */
	public static final String DRYINGMETHODS_Walzengetrocknet = "Walzengetrocknet";
	@Override
	public void setDryingMethods (final @Nullable java.lang.String DryingMethods)
	{
		set_Value (COLUMNNAME_DryingMethods, DryingMethods);
	}

	@Override
	public java.lang.String getDryingMethods()
	{
		return get_ValueAsString(COLUMNNAME_DryingMethods);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setFLO_Identifier (final @Nullable java.lang.String FLO_Identifier)
	{
		set_Value (COLUMNNAME_FLO_Identifier, FLO_Identifier);
	}

	@Override
	public java.lang.String getFLO_Identifier()
	{
		return get_ValueAsString(COLUMNNAME_FLO_Identifier);
	}

	@Override
	public void setGrade (final @Nullable java.lang.String Grade)
	{
		set_Value (COLUMNNAME_Grade, Grade);
	}

	@Override
	public java.lang.String getGrade()
	{
		return get_ValueAsString(COLUMNNAME_Grade);
	}

	@Override
	public void setGroup1 (final @Nullable java.lang.String Group1)
	{
		set_Value (COLUMNNAME_Group1, Group1);
	}

	@Override
	public java.lang.String getGroup1() 
	{
		return get_ValueAsString(COLUMNNAME_Group1);
	}

	@Override
	public void setGroup2 (final @Nullable java.lang.String Group2)
	{
		set_Value (COLUMNNAME_Group2, Group2);
	}

	@Override
	public java.lang.String getGroup2() 
	{
		return get_ValueAsString(COLUMNNAME_Group2);
	}

	/** 
	 * GroupCompensationAmtType AD_Reference_ID=540759
	 * Reference name: GroupCompensationAmtType
	 */
	public static final int GROUPCOMPENSATIONAMTTYPE_AD_Reference_ID=540759;
	/** Percent = P */
	public static final String GROUPCOMPENSATIONAMTTYPE_Percent = "P";
	/** PriceAndQty = Q */
	public static final String GROUPCOMPENSATIONAMTTYPE_PriceAndQty = "Q";
	@Override
	public void setGroupCompensationAmtType (final @Nullable java.lang.String GroupCompensationAmtType)
	{
		set_Value (COLUMNNAME_GroupCompensationAmtType, GroupCompensationAmtType);
	}

	@Override
	public java.lang.String getGroupCompensationAmtType() 
	{
		return get_ValueAsString(COLUMNNAME_GroupCompensationAmtType);
	}

	/** 
	 * GroupCompensationType AD_Reference_ID=540758
	 * Reference name: GroupCompensationType
	 */
	public static final int GROUPCOMPENSATIONTYPE_AD_Reference_ID=540758;
	/** Surcharge = S */
	public static final String GROUPCOMPENSATIONTYPE_Surcharge = "S";
	/** Discount = D */
	public static final String GROUPCOMPENSATIONTYPE_Discount = "D";
	@Override
	public void setGroupCompensationType (final @Nullable java.lang.String GroupCompensationType)
	{
		set_Value (COLUMNNAME_GroupCompensationType, GroupCompensationType);
	}

	@Override
	public java.lang.String getGroupCompensationType() 
	{
		return get_ValueAsString(COLUMNNAME_GroupCompensationType);
	}

	@Override
	public void setGTIN (final @Nullable java.lang.String GTIN)
	{
		set_Value (COLUMNNAME_GTIN, GTIN);
	}

	@Override
	public java.lang.String getGTIN() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN);
	}

	@Override
	public void setGuaranteeDaysMin (final int GuaranteeDaysMin)
	{
		set_Value (COLUMNNAME_GuaranteeDaysMin, GuaranteeDaysMin);
	}

	@Override
	public int getGuaranteeDaysMin() 
	{
		return get_ValueAsInt(COLUMNNAME_GuaranteeDaysMin);
	}

	@Override
	public void setGuaranteeDaysMin_opened (final int GuaranteeDaysMin_opened)
	{
		set_Value (COLUMNNAME_GuaranteeDaysMin_opened, GuaranteeDaysMin_opened);
	}

	@Override
	public int getGuaranteeDaysMin_opened()
	{
		return get_ValueAsInt(COLUMNNAME_GuaranteeDaysMin_opened);
	}

	/**
	 * GuaranteeMonths AD_Reference_ID=541258
	 * Reference name: GuaranteeMonths
	 */
	public static final int GUARANTEEMONTHS_AD_Reference_ID=541258;
	/** 12 = 12 */
	public static final String GUARANTEEMONTHS_12 = "12";
	/** 24 = 24 */
	public static final String GUARANTEEMONTHS_24 = "24";
	/** 36 = 36 */
	public static final String GUARANTEEMONTHS_36 = "36";
	/** 60 = 60 */
	public static final String GUARANTEEMONTHS_60 = "60";
	@Override
	public void setGuaranteeMonths (final @Nullable java.lang.String GuaranteeMonths)
	{
		set_Value (COLUMNNAME_GuaranteeMonths, GuaranteeMonths);
	}

	@Override
	public java.lang.String getGuaranteeMonths() 
	{
		return get_ValueAsString(COLUMNNAME_GuaranteeMonths);
	}

	@Override
	public void setHaddexCheck (final boolean HaddexCheck)
	{
		set_Value (COLUMNNAME_HaddexCheck, HaddexCheck);
	}

	@Override
	public boolean isHaddexCheck() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HaddexCheck);
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	/**
	 * HUClearanceStatus AD_Reference_ID=541540
	 * Reference name: Clearance
	 */
	public static final int HUCLEARANCESTATUS_AD_Reference_ID=541540;
	/** Cleared = C */
	public static final String HUCLEARANCESTATUS_Cleared = "C";
	/** Locked = L */
	public static final String HUCLEARANCESTATUS_Locked = "L";
	/** Quarantined = Q */
	public static final String HUCLEARANCESTATUS_Quarantined = "Q";
	/** Test Pending = P */
	public static final String HUCLEARANCESTATUS_TestPending = "P";
	@Override
	public void setHUClearanceStatus (final @Nullable java.lang.String HUClearanceStatus)
	{
		set_Value (COLUMNNAME_HUClearanceStatus, HUClearanceStatus);
	}

	@Override
	public java.lang.String getHUClearanceStatus()
	{
		return get_ValueAsString(COLUMNNAME_HUClearanceStatus);
	}

	@Override
	public void setImageURL (final @Nullable java.lang.String ImageURL)
	{
		set_Value (COLUMNNAME_ImageURL, ImageURL);
	}

	@Override
	public java.lang.String getImageURL() 
	{
		return get_ValueAsString(COLUMNNAME_ImageURL);
	}

	@Override
	public void setIngredients (final @Nullable java.lang.String Ingredients)
	{
		set_Value (COLUMNNAME_Ingredients, Ingredients);
	}

	@Override
	public java.lang.String getIngredients() 
	{
		return get_ValueAsString(COLUMNNAME_Ingredients);
	}

	@Override
	public void setIsBOM (final boolean IsBOM)
	{
		set_Value (COLUMNNAME_IsBOM, IsBOM);
	}

	@Override
	public boolean isBOM() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBOM);
	}

	@Override
	public void setIsCommissioned (final boolean IsCommissioned)
	{
		set_Value (COLUMNNAME_IsCommissioned, IsCommissioned);
	}

	@Override
	public boolean isCommissioned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommissioned);
	}

	@Override
	public void setIsDropShip (final boolean IsDropShip)
	{
		set_Value (COLUMNNAME_IsDropShip, IsDropShip);
	}

	@Override
	public boolean isDropShip() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDropShip);
	}

	@Override
	public void setIsEnforceIssuingTolerance (final boolean IsEnforceIssuingTolerance)
	{
		set_Value (COLUMNNAME_IsEnforceIssuingTolerance, IsEnforceIssuingTolerance);
	}

	@Override
	public boolean isEnforceIssuingTolerance()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEnforceIssuingTolerance);
	}

	@Override
	public void setIsExcludeAutoDelivery (final boolean IsExcludeAutoDelivery)
	{
		set_Value (COLUMNNAME_IsExcludeAutoDelivery, IsExcludeAutoDelivery);
	}

	@Override
	public boolean isExcludeAutoDelivery() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeAutoDelivery);
	}

	@Override
	public void setIsInvoicePrintDetails (final boolean IsInvoicePrintDetails)
	{
		set_Value (COLUMNNAME_IsInvoicePrintDetails, IsInvoicePrintDetails);
	}

	@Override
	public boolean isInvoicePrintDetails() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInvoicePrintDetails);
	}

	@Override
	public void setIsManufactured (final boolean IsManufactured)
	{
		throw new IllegalArgumentException ("IsManufactured is virtual column");	}

	@Override
	public boolean isManufactured() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManufactured);
	}

	@Override
	public void setIsPickListPrintDetails (final boolean IsPickListPrintDetails)
	{
		set_Value (COLUMNNAME_IsPickListPrintDetails, IsPickListPrintDetails);
	}

	@Override
	public boolean isPickListPrintDetails() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickListPrintDetails);
	}

	@Override
	public void setIsPrintWhenPackingMaterial (final boolean IsPrintWhenPackingMaterial)
	{
		set_Value (COLUMNNAME_IsPrintWhenPackingMaterial, IsPrintWhenPackingMaterial);
	}

	@Override
	public boolean isPrintWhenPackingMaterial()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrintWhenPackingMaterial);
	}

	@Override
	public void setIsPurchased (final boolean IsPurchased)
	{
		set_Value (COLUMNNAME_IsPurchased, IsPurchased);
	}

	@Override
	public boolean isPurchased() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPurchased);
	}

	@Override
	public void setIsQuotationGroupping (final boolean IsQuotationGroupping)
	{
		set_Value (COLUMNNAME_IsQuotationGroupping, IsQuotationGroupping);
	}

	@Override
	public boolean isQuotationGroupping() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsQuotationGroupping);
	}

	@Override
	public void setIsSelfService (final boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, IsSelfService);
	}

	@Override
	public boolean isSelfService() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSelfService);
	}

	@Override
	public void setIsSold (final boolean IsSold)
	{
		set_Value (COLUMNNAME_IsSold, IsSold);
	}

	@Override
	public boolean isSold() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSold);
	}

	@Override
	public void setIsStocked (final boolean IsStocked)
	{
		set_Value (COLUMNNAME_IsStocked, IsStocked);
	}

	@Override
	public boolean isStocked() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsStocked);
	}

	@Override
	public void setIssuingTolerance_Perc (final @Nullable BigDecimal IssuingTolerance_Perc)
	{
		set_Value (COLUMNNAME_IssuingTolerance_Perc, IssuingTolerance_Perc);
	}

	@Override
	public BigDecimal getIssuingTolerance_Perc()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_IssuingTolerance_Perc);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIssuingTolerance_Qty (final @Nullable BigDecimal IssuingTolerance_Qty)
	{
		set_Value (COLUMNNAME_IssuingTolerance_Qty, IssuingTolerance_Qty);
	}

	@Override
	public BigDecimal getIssuingTolerance_Qty()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_IssuingTolerance_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIssuingTolerance_UOM_ID (final int IssuingTolerance_UOM_ID)
	{
		if (IssuingTolerance_UOM_ID < 1)
			set_Value (COLUMNNAME_IssuingTolerance_UOM_ID, null);
		else
			set_Value (COLUMNNAME_IssuingTolerance_UOM_ID, IssuingTolerance_UOM_ID);
	}

	@Override
	public int getIssuingTolerance_UOM_ID()
	{
		return get_ValueAsInt(COLUMNNAME_IssuingTolerance_UOM_ID);
	}

	/**
	 * IssuingTolerance_ValueType AD_Reference_ID=541693
	 * Reference name: IssuingTolerance_ValueType
	 */
	public static final int ISSUINGTOLERANCE_VALUETYPE_AD_Reference_ID=541693;
	/** Percentage = P */
	public static final String ISSUINGTOLERANCE_VALUETYPE_Percentage = "P";
	/** Quantity = Q */
	public static final String ISSUINGTOLERANCE_VALUETYPE_Quantity = "Q";
	@Override
	public void setIssuingTolerance_ValueType (final @Nullable java.lang.String IssuingTolerance_ValueType)
	{
		set_Value (COLUMNNAME_IssuingTolerance_ValueType, IssuingTolerance_ValueType);
	}

	@Override
	public java.lang.String getIssuingTolerance_ValueType()
	{
		return get_ValueAsString(COLUMNNAME_IssuingTolerance_ValueType);
	}

	@Override
	public void setIsSummary (final boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public void setIsVerified (final boolean IsVerified)
	{
		set_ValueNoCheck (COLUMNNAME_IsVerified, IsVerified);
	}

	@Override
	public boolean isVerified() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsVerified);
	}

	@Override
	public void setIsWebStoreFeatured (final boolean IsWebStoreFeatured)
	{
		set_Value (COLUMNNAME_IsWebStoreFeatured, IsWebStoreFeatured);
	}

	@Override
	public boolean isWebStoreFeatured() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWebStoreFeatured);
	}

	@Override
	public void setLegalName (final @Nullable java.lang.String LegalName)
	{
		set_Value (COLUMNNAME_LegalName, LegalName);
	}

	@Override
	public java.lang.String getLegalName()
	{
		return get_ValueAsString(COLUMNNAME_LegalName);
	}

	@Override
	public void setLongTextDescription (final @Nullable java.lang.String LongTextDescription)
	{
		set_Value (COLUMNNAME_LongTextDescription, LongTextDescription);
	}

	@Override
	public java.lang.String getLongTextDescription()
	{
		return get_ValueAsString(COLUMNNAME_LongTextDescription);
	}

	@Override
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(final org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	@Override
	public void setM_AttributeSet_ID (final int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSet_ID, M_AttributeSet_ID);
	}

	@Override
	public int getM_AttributeSet_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSet_ID);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(final org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	@Override
	public void setM_AttributeSetInstance_ID (final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID);
	}

	@Override
	public int getM_AttributeSetInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSetInstance_ID);
	}

	@Override
	public void setM_CommodityNumber_ID (final int M_CommodityNumber_ID)
	{
		if (M_CommodityNumber_ID < 1) 
			set_Value (COLUMNNAME_M_CommodityNumber_ID, null);
		else 
			set_Value (COLUMNNAME_M_CommodityNumber_ID, M_CommodityNumber_ID);
	}

	@Override
	public int getM_CommodityNumber_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CommodityNumber_ID);
	}

	@Override
	public org.compiere.model.I_M_CustomsTariff getM_CustomsTariff()
	{
		return get_ValueAsPO(COLUMNNAME_M_CustomsTariff_ID, org.compiere.model.I_M_CustomsTariff.class);
	}

	@Override
	public void setM_CustomsTariff(final org.compiere.model.I_M_CustomsTariff M_CustomsTariff)
	{
		set_ValueFromPO(COLUMNNAME_M_CustomsTariff_ID, org.compiere.model.I_M_CustomsTariff.class, M_CustomsTariff);
	}

	@Override
	public void setM_CustomsTariff_ID (final int M_CustomsTariff_ID)
	{
		if (M_CustomsTariff_ID < 1) 
			set_Value (COLUMNNAME_M_CustomsTariff_ID, null);
		else 
			set_Value (COLUMNNAME_M_CustomsTariff_ID, M_CustomsTariff_ID);
	}

	@Override
	public int getM_CustomsTariff_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CustomsTariff_ID);
	}

	@Override
	public org.compiere.model.I_M_FreightCategory getM_FreightCategory()
	{
		return get_ValueAsPO(COLUMNNAME_M_FreightCategory_ID, org.compiere.model.I_M_FreightCategory.class);
	}

	@Override
	public void setM_FreightCategory(final org.compiere.model.I_M_FreightCategory M_FreightCategory)
	{
		set_ValueFromPO(COLUMNNAME_M_FreightCategory_ID, org.compiere.model.I_M_FreightCategory.class, M_FreightCategory);
	}

	@Override
	public void setM_FreightCategory_ID (final int M_FreightCategory_ID)
	{
		if (M_FreightCategory_ID < 1) 
			set_Value (COLUMNNAME_M_FreightCategory_ID, null);
		else 
			set_Value (COLUMNNAME_M_FreightCategory_ID, M_FreightCategory_ID);
	}

	@Override
	public int getM_FreightCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCategory_ID);
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
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
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

	/** 
	 * M_ProductPlanningSchema_Selector AD_Reference_ID=540829
	 * Reference name: M_ProductPlanningSchema_Selector_List
	 */
	public static final int M_PRODUCTPLANNINGSCHEMA_SELECTOR_AD_Reference_ID=540829;
	/** Normal = N */
	public static final String M_PRODUCTPLANNINGSCHEMA_SELECTOR_Normal = "N";
	/** QuotationBOMProduct = Q */
	public static final String M_PRODUCTPLANNINGSCHEMA_SELECTOR_QuotationBOMProduct = "Q";
	@Override
	public void setM_ProductPlanningSchema_Selector (final @Nullable java.lang.String M_ProductPlanningSchema_Selector)
	{
		set_Value (COLUMNNAME_M_ProductPlanningSchema_Selector, M_ProductPlanningSchema_Selector);
	}

	@Override
	public java.lang.String getM_ProductPlanningSchema_Selector() 
	{
		return get_ValueAsString(COLUMNNAME_M_ProductPlanningSchema_Selector);
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
	public void setManufacturer_ID (final int Manufacturer_ID)
	{
		if (Manufacturer_ID < 1)
			set_Value (COLUMNNAME_Manufacturer_ID, null);
		else
			set_Value (COLUMNNAME_Manufacturer_ID, Manufacturer_ID);
	}

	@Override
	public int getManufacturer_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Manufacturer_ID);
	}

	@Override
	public void setManufacturerArticleNumber (final @Nullable java.lang.String ManufacturerArticleNumber)
	{
		set_Value (COLUMNNAME_ManufacturerArticleNumber, ManufacturerArticleNumber);
	}

	@Override
	public java.lang.String getManufacturerArticleNumber()
	{
		return get_ValueAsString(COLUMNNAME_ManufacturerArticleNumber);
	}

	@Override
	public void setManufacturerProductDescription (final @Nullable java.lang.String ManufacturerProductDescription)
	{
		set_Value (COLUMNNAME_ManufacturerProductDescription, ManufacturerProductDescription);
	}

	@Override
	public java.lang.String getManufacturerProductDescription()
	{
		return get_ValueAsString(COLUMNNAME_ManufacturerProductDescription);
	}

	/**
	 * ManufacturingMethod AD_Reference_ID=541515
	 * Reference name: ManufacturingMethod_Reference
	 */
	public static final int MANUFACTURINGMETHOD_AD_Reference_ID=541515;
	/** Presslingherstellung = PR */
	public static final String MANUFACTURINGMETHOD_Presslingherstellung = "PR";
	@Override
	public void setManufacturingMethod (final @Nullable java.lang.String ManufacturingMethod)
	{
		set_Value (COLUMNNAME_ManufacturingMethod, ManufacturingMethod);
	}

	@Override
	public java.lang.String getManufacturingMethod()
	{
		return get_ValueAsString(COLUMNNAME_ManufacturingMethod);
	}

	/**
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	@Override
	public void setMRP_Exclude (final @Nullable java.lang.String MRP_Exclude)
	{
		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	@Override
	public java.lang.String getMRP_Exclude()
	{
		return get_ValueAsString(COLUMNNAME_MRP_Exclude);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setNetWeight (final @Nullable BigDecimal NetWeight)
	{
		set_Value (COLUMNNAME_NetWeight, NetWeight);
	}

	@Override
	public BigDecimal getNetWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_NetWeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setNetWeight_UOM_ID (final int NetWeight_UOM_ID)
	{
		if (NetWeight_UOM_ID < 1)
			set_Value (COLUMNNAME_NetWeight_UOM_ID, null);
		else
			set_Value (COLUMNNAME_NetWeight_UOM_ID, NetWeight_UOM_ID);
	}

	@Override
	public int getNetWeight_UOM_ID()
	{
		return get_ValueAsInt(COLUMNNAME_NetWeight_UOM_ID);
	}

	@Override
	public void setPackage_UOM_ID (final int Package_UOM_ID)
	{
		if (Package_UOM_ID < 1) 
			set_Value (COLUMNNAME_Package_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Package_UOM_ID, Package_UOM_ID);
	}

	@Override
	public int getPackage_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Package_UOM_ID);
	}

	@Override
	public void setPackageSize (final @Nullable java.lang.String PackageSize)
	{
		set_Value (COLUMNNAME_PackageSize, PackageSize);
	}

	@Override
	public java.lang.String getPackageSize()
	{
		return get_ValueAsString(COLUMNNAME_PackageSize);
	}

	@Override
	public void setPer100g (final @Nullable BigDecimal Per100g)
	{
		set_Value (COLUMNNAME_Per100g, Per100g);
	}

	@Override
	public BigDecimal getPer100g()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Per100g);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPicking_AgeTolerance_AfterMonths (final int Picking_AgeTolerance_AfterMonths)
	{
		set_Value (COLUMNNAME_Picking_AgeTolerance_AfterMonths, Picking_AgeTolerance_AfterMonths);
	}

	@Override
	public int getPicking_AgeTolerance_AfterMonths()
	{
		return get_ValueAsInt(COLUMNNAME_Picking_AgeTolerance_AfterMonths);
	}

	@Override
	public void setPicking_AgeTolerance_BeforeMonths (final int Picking_AgeTolerance_BeforeMonths)
	{
		set_Value (COLUMNNAME_Picking_AgeTolerance_BeforeMonths, Picking_AgeTolerance_BeforeMonths);
	}

	@Override
	public int getPicking_AgeTolerance_BeforeMonths()
	{
		return get_ValueAsInt(COLUMNNAME_Picking_AgeTolerance_BeforeMonths);
	}

	/**
	 * PostControl AD_Reference_ID=541517
	 * Reference name: PostControl_Reference
	 */
	public static final int POSTCONTROL_AD_Reference_ID=541517;
	/** Post Control Test = PC */
	public static final String POSTCONTROL_PostControlTest = "PC";
	@Override
	public void setPostControl (final @Nullable java.lang.String PostControl)
	{
		set_Value (COLUMNNAME_PostControl, PostControl);
	}

	@Override
	public java.lang.String getPostControl()
	{
		return get_ValueAsString(COLUMNNAME_PostControl);
	}

	@Override
	public void setPreparation (final @Nullable java.lang.String Preparation)
	{
		set_Value (COLUMNNAME_Preparation, Preparation);
	}

	@Override
	public java.lang.String getPreparation()
	{
		return get_ValueAsString(COLUMNNAME_Preparation);
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

	/**
	 * ProcurementStatus AD_Reference_ID=541842
	 * Reference name: Beschaffungsstatus
	 */
	public static final int PROCUREMENTSTATUS_AD_Reference_ID = 541842;
	/**
	 * low = low
	 */
	public static final String PROCUREMENTSTATUS_Low = "low";
	/**
	 * medium = medium
	 */
	public static final String PROCUREMENTSTATUS_Medium = "medium";
	/**
	 * high = high
	 */
	public static final String PROCUREMENTSTATUS_High = "high";

	@Override
	public void setProcurementStatus(final @Nullable java.lang.String ProcurementStatus)
	{
		set_Value(COLUMNNAME_ProcurementStatus, ProcurementStatus);
	}

	@Override
	public java.lang.String getProcurementStatus()
	{
		return get_ValueAsString(COLUMNNAME_ProcurementStatus);
	}

	/**
	 * ProductType AD_Reference_ID=270
	 * Reference name: M_Product_ProductType
	 */
	public static final int PRODUCTTYPE_AD_Reference_ID=270;
	/** Item = I */
	public static final String PRODUCTTYPE_Item = "I";
	/** Service = S */
	public static final String PRODUCTTYPE_Service = "S";
	/** Resource = R */
	public static final String PRODUCTTYPE_Resource = "R";
	/** ExpenseType = E */
	public static final String PRODUCTTYPE_ExpenseType = "E";
	/** Online = O */
	public static final String PRODUCTTYPE_Online = "O";
	/** FreightCost = F */
	public static final String PRODUCTTYPE_FreightCost = "F";
	/** Nahrung = N */
	public static final String PRODUCTTYPE_Nahrung = "N";
	@Override
	public void setProductType (final java.lang.String ProductType)
	{
		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	@Override
	public java.lang.String getProductType() 
	{
		return get_ValueAsString(COLUMNNAME_ProductType);
	}

	@Override
	public void setPZN (final @Nullable java.lang.String PZN)
	{
		set_Value (COLUMNNAME_PZN, PZN);
	}

	@Override
	public java.lang.String getPZN()
	{
		return get_ValueAsString(COLUMNNAME_PZN);
	}

	@Override
	public org.compiere.model.I_R_MailText getR_MailText()
	{
		return get_ValueAsPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setR_MailText(final org.compiere.model.I_R_MailText R_MailText)
	{
		set_ValueFromPO(COLUMNNAME_R_MailText_ID, org.compiere.model.I_R_MailText.class, R_MailText);
	}

	@Override
	public void setR_MailText_ID (final int R_MailText_ID)
	{
		if (R_MailText_ID < 1)
			set_Value (COLUMNNAME_R_MailText_ID, null);
		else
			set_Value (COLUMNNAME_R_MailText_ID, R_MailText_ID);
	}

	@Override
	public int getR_MailText_ID()
	{
		return get_ValueAsInt(COLUMNNAME_R_MailText_ID);
	}

	@Override
	public org.compiere.model.I_C_Country getRawMaterialOrigin()
	{
		return get_ValueAsPO(COLUMNNAME_RawMaterialOrigin_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setRawMaterialOrigin(final org.compiere.model.I_C_Country RawMaterialOrigin)
	{
		set_ValueFromPO(COLUMNNAME_RawMaterialOrigin_ID, org.compiere.model.I_C_Country.class, RawMaterialOrigin);
	}

	@Override
	public void setRawMaterialOrigin_ID (final int RawMaterialOrigin_ID)
	{
		if (RawMaterialOrigin_ID < 1) 
			set_Value (COLUMNNAME_RawMaterialOrigin_ID, null);
		else 
			set_Value (COLUMNNAME_RawMaterialOrigin_ID, RawMaterialOrigin_ID);
	}

	@Override
	public int getRawMaterialOrigin_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RawMaterialOrigin_ID);
	}

	/**
	 * ReferenceText AD_Reference_ID=541518
	 * Reference name: ReferenceText_Reference
	 */
	public static final int REFERENCETEXT_AD_Reference_ID=541518;
	/** Reference Text Test = RT */
	public static final String REFERENCETEXT_ReferenceTextTest = "RT";
	@Override
	public void setReferenceText (final @Nullable java.lang.String ReferenceText)
	{
		set_Value (COLUMNNAME_ReferenceText, ReferenceText);
	}

	@Override
	public java.lang.String getReferenceText()
	{
		return get_ValueAsString(COLUMNNAME_ReferenceText);
	}

	@Override
	public void setRequiresSupplierApproval (final boolean RequiresSupplierApproval)
	{
		set_Value (COLUMNNAME_RequiresSupplierApproval, RequiresSupplierApproval);
	}

	@Override
	public boolean isRequiresSupplierApproval() 
	{
		return get_ValueAsBoolean(COLUMNNAME_RequiresSupplierApproval);
	}

	@Override
	public org.compiere.model.I_S_ExpenseType getS_ExpenseType()
	{
		return get_ValueAsPO(COLUMNNAME_S_ExpenseType_ID, org.compiere.model.I_S_ExpenseType.class);
	}

	@Override
	public void setS_ExpenseType(final org.compiere.model.I_S_ExpenseType S_ExpenseType)
	{
		set_ValueFromPO(COLUMNNAME_S_ExpenseType_ID, org.compiere.model.I_S_ExpenseType.class, S_ExpenseType);
	}

	@Override
	public void setS_ExpenseType_ID (final int S_ExpenseType_ID)
	{
		if (S_ExpenseType_ID < 1)
			set_ValueNoCheck (COLUMNNAME_S_ExpenseType_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_S_ExpenseType_ID, S_ExpenseType_ID);
	}

	@Override
	public int getS_ExpenseType_ID()
	{
		return get_ValueAsInt(COLUMNNAME_S_ExpenseType_ID);
	}

	@Override
	public void setS_Resource_Group_ID (final int S_Resource_Group_ID)
	{
		if (S_Resource_Group_ID < 1)
			set_Value (COLUMNNAME_S_Resource_Group_ID, null);
		else
			set_Value (COLUMNNAME_S_Resource_Group_ID, S_Resource_Group_ID);
	}

	@Override
	public int getS_Resource_Group_ID()
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_Group_ID);
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1)
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID()
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}

	@Override
	public void setSafetyInfo (final @Nullable java.lang.String SafetyInfo)
	{
		set_Value (COLUMNNAME_SafetyInfo, SafetyInfo);
	}

	@Override
	public java.lang.String getSafetyInfo() 
	{
		return get_ValueAsString(COLUMNNAME_SafetyInfo);
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
<<<<<<< HEAD
	public void setSAP_ProductHierarchy (final @Nullable java.lang.String SAP_ProductHierarchy)
	{
		set_Value (COLUMNNAME_SAP_ProductHierarchy, SAP_ProductHierarchy);
	}

	@Override
	public java.lang.String getSAP_ProductHierarchy()
	{
		return get_ValueAsString(COLUMNNAME_SAP_ProductHierarchy);
	}

	@Override
	public void setShelfDepth (final int ShelfDepth)
=======
	public void setShelfDepth (final BigDecimal ShelfDepth)
>>>>>>> a8efc465e0f (Improve importing products (#19205))
	{
		set_Value (COLUMNNAME_ShelfDepth, ShelfDepth);
	}

	@Override
	public BigDecimal getShelfDepth()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ShelfDepth);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setShelfHeight (final @Nullable BigDecimal ShelfHeight)
	{
		set_Value (COLUMNNAME_ShelfHeight, ShelfHeight);
	}

	@Override
	public BigDecimal getShelfHeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ShelfHeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setShelfWidth (final BigDecimal ShelfWidth)
	{
		set_Value (COLUMNNAME_ShelfWidth, ShelfWidth);
	}

	@Override
	public BigDecimal getShelfWidth()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ShelfWidth);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setShopDescription (final @Nullable java.lang.String ShopDescription)
	{
		set_Value (COLUMNNAME_ShopDescription, ShopDescription);
	}

	@Override
	public java.lang.String getShopDescription() 
	{
		return get_ValueAsString(COLUMNNAME_ShopDescription);
	}

	@Override
	public void setShopInventoryQty (final @Nullable BigDecimal ShopInventoryQty)
	{
		set_Value (COLUMNNAME_ShopInventoryQty, ShopInventoryQty);
	}

	@Override
	public BigDecimal getShopInventoryQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ShopInventoryQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSKU (final @Nullable java.lang.String SKU)
	{
		set_Value (COLUMNNAME_SKU, SKU);
	}

	@Override
	public java.lang.String getSKU() 
	{
		return get_ValueAsString(COLUMNNAME_SKU);
	}

	@Override
	public void setTrademark (final @Nullable java.lang.String Trademark)
	{
		set_Value (COLUMNNAME_Trademark, Trademark);
	}

	@Override
	public java.lang.String getTrademark() 
	{
		return get_ValueAsString(COLUMNNAME_Trademark);
	}

	@Override
	public void setUnitsPerPack (final int UnitsPerPack)
	{
		set_Value (COLUMNNAME_UnitsPerPack, UnitsPerPack);
	}

	@Override
	public int getUnitsPerPack() 
	{
		return get_ValueAsInt(COLUMNNAME_UnitsPerPack);
	}

	@Override
	public void setUnitsPerPallet (final @Nullable BigDecimal UnitsPerPallet)
	{
		set_Value (COLUMNNAME_UnitsPerPallet, UnitsPerPallet);
	}

	@Override
	public BigDecimal getUnitsPerPallet() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UnitsPerPallet);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUPC (final @Nullable java.lang.String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
		return get_ValueAsString(COLUMNNAME_UPC);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setVersionNo (final @Nullable java.lang.String VersionNo)
	{
		set_Value (COLUMNNAME_VersionNo, VersionNo);
	}

	@Override
	public java.lang.String getVersionNo() 
	{
		return get_ValueAsString(COLUMNNAME_VersionNo);
	}

	@Override
	public void setVolume (final @Nullable BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	@Override
	public BigDecimal getVolume() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Volume);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setWarehouse_temperature (final @Nullable java.lang.String Warehouse_temperature)
	{
		set_Value (COLUMNNAME_Warehouse_temperature, Warehouse_temperature);
	}

	@Override
	public java.lang.String getWarehouse_temperature() 
	{
		return get_ValueAsString(COLUMNNAME_Warehouse_temperature);
	}

	@Override
	public void setWeight (final @Nullable BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	@Override
	public BigDecimal getWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Weight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

    @Override
    public void setQRCode_Configuration_ID (final int QRCode_Configuration_ID)
    {
        set_Value (COLUMNNAME_QRCode_Configuration_ID, QRCode_Configuration_ID);
    }

    @Override
    public int getQRCode_Configuration_ID()
    {
        return get_ValueAsInt(COLUMNNAME_QRCode_Configuration_ID);
    }
}