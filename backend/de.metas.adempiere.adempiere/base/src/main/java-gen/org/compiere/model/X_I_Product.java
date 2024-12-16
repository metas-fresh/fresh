// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Product
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_Product extends org.compiere.model.PO implements I_I_Product, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 848588753L;

    /** Standard Constructor */
    public X_I_Product (final Properties ctx, final int I_Product_ID, @Nullable final String trxName)
    {
      super (ctx, I_Product_ID, trxName);
    }

    /** Load Constructor */
    public X_I_Product (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setBPartner_Value (final @Nullable java.lang.String BPartner_Value)
	{
		set_Value (COLUMNNAME_BPartner_Value, BPartner_Value);
	}

	@Override
	public java.lang.String getBPartner_Value() 
	{
		return get_ValueAsString(COLUMNNAME_BPartner_Value);
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
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(final org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (final int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, C_DataImport_ID);
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(final org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (final int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, C_DataImport_Run_ID);
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
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
	public void setCostPerOrder (final @Nullable BigDecimal CostPerOrder)
	{
		set_Value (COLUMNNAME_CostPerOrder, CostPerOrder);
	}

	@Override
	public BigDecimal getCostPerOrder() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CostPerOrder);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_TaxCategory_ID (final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
	}

	@Override
	public int getC_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
	}

	@Override
	public void setC_TaxCategory_Name (final @Nullable java.lang.String C_TaxCategory_Name)
	{
		set_Value (COLUMNNAME_C_TaxCategory_Name, C_TaxCategory_Name);
	}

	@Override
	public java.lang.String getC_TaxCategory_Name() 
	{
		return get_ValueAsString(COLUMNNAME_C_TaxCategory_Name);
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
	public void setCustomsTariff (final @Nullable java.lang.String CustomsTariff)
	{
		set_Value (COLUMNNAME_CustomsTariff, CustomsTariff);
	}

	@Override
	public java.lang.String getCustomsTariff() 
	{
		return get_ValueAsString(COLUMNNAME_CustomsTariff);
	}

	@Override
	public void setDeliveryTime_Promised (final int DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, DeliveryTime_Promised);
	}

	@Override
	public int getDeliveryTime_Promised() 
	{
		return get_ValueAsInt(COLUMNNAME_DeliveryTime_Promised);
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
	public java.sql.Timestamp getDiscontinuedBy() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DiscontinuedBy);
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
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setI_ErrorMsg (final @Nullable java.lang.String I_ErrorMsg)
	{
		set_ValueNoCheck (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_I_ErrorMsg);
	}

	@Override
	public void setI_IsImported (final boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public boolean isI_IsImported() 
	{
		return get_ValueAsBoolean(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (final @Nullable java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return get_ValueAsString(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (final int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, I_LineNo);
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
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
	public void setI_Product_ID (final int I_Product_ID)
	{
		if (I_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Product_ID, I_Product_ID);
	}

	@Override
	public int getI_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_Product_ID);
	}

	@Override
	public void setISO_Code (final @Nullable java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

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
	public void setIsScalePrice (final boolean IsScalePrice)
	{
		set_Value (COLUMNNAME_IsScalePrice, IsScalePrice);
	}

	@Override
	public boolean isScalePrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsScalePrice);
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
	public void setM_PriceList_Version_ID (final int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, M_PriceList_Version_ID);
	}

	@Override
	public int getM_PriceList_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_Version_ID);
	}

	@Override
	public void setM_PriceList_Version_Name (final @Nullable java.lang.String M_PriceList_Version_Name)
	{
		set_Value (COLUMNNAME_M_PriceList_Version_Name, M_PriceList_Version_Name);
	}

	@Override
	public java.lang.String getM_PriceList_Version_Name() 
	{
		return get_ValueAsString(COLUMNNAME_M_PriceList_Version_Name);
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
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
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
	public void setName (final @Nullable java.lang.String Name)
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
	public void setOrder_Min (final int Order_Min)
	{
		set_Value (COLUMNNAME_Order_Min, Order_Min);
	}

	@Override
	public int getOrder_Min() 
	{
		return get_ValueAsInt(COLUMNNAME_Order_Min);
	}

	@Override
	public void setOrder_Pack (final int Order_Pack)
	{
		set_Value (COLUMNNAME_Order_Pack, Order_Pack);
	}

	@Override
	public int getOrder_Pack() 
	{
		return get_ValueAsInt(COLUMNNAME_Order_Pack);
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
	public void setPriceEffective (final @Nullable java.sql.Timestamp PriceEffective)
	{
		set_Value (COLUMNNAME_PriceEffective, PriceEffective);
	}

	@Override
	public java.sql.Timestamp getPriceEffective() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PriceEffective);
	}

	@Override
	public void setPriceLimit (final @Nullable BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	@Override
	public BigDecimal getPriceLimit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceLimit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceList (final @Nullable BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	@Override
	public BigDecimal getPriceList() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceList);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPricePO (final @Nullable BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	@Override
	public BigDecimal getPricePO() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PricePO);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriceStd (final @Nullable BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	@Override
	public BigDecimal getPriceStd() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceStd);
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
	public void setProductCategory_Value (final @Nullable java.lang.String ProductCategory_Value)
	{
		set_Value (COLUMNNAME_ProductCategory_Value, ProductCategory_Value);
	}

	@Override
	public java.lang.String getProductCategory_Value() 
	{
		return get_ValueAsString(COLUMNNAME_ProductCategory_Value);
	}

	@Override
	public void setProductManufacturer (final @Nullable java.lang.String ProductManufacturer)
	{
		set_Value (COLUMNNAME_ProductManufacturer, ProductManufacturer);
	}

	@Override
	public java.lang.String getProductManufacturer() 
	{
		return get_ValueAsString(COLUMNNAME_ProductManufacturer);
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
	public void setProductType (final @Nullable java.lang.String ProductType)
	{
		set_Value (COLUMNNAME_ProductType, ProductType);
	}

	@Override
	public java.lang.String getProductType() 
	{
		return get_ValueAsString(COLUMNNAME_ProductType);
	}

	@Override
	public java.lang.String getPZN()
	{
		return get_ValueAsString(COLUMNNAME_PZN);
	}

	@Override
	public void setPZN (final @Nullable java.lang.String PZN)
	{
		set_Value (COLUMNNAME_PZN, PZN);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRawMaterialOriginCountryCode (final @Nullable java.lang.String RawMaterialOriginCountryCode)
	{
		set_Value (COLUMNNAME_RawMaterialOriginCountryCode, RawMaterialOriginCountryCode);
	}

	@Override
	public java.lang.String getRawMaterialOriginCountryCode() 
	{
		return get_ValueAsString(COLUMNNAME_RawMaterialOriginCountryCode);
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

	@Override
	public void setRoyaltyAmt (final @Nullable BigDecimal RoyaltyAmt)
	{
		set_Value (COLUMNNAME_RoyaltyAmt, RoyaltyAmt);
	}

	@Override
	public BigDecimal getRoyaltyAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RoyaltyAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setShelfDepth (final BigDecimal ShelfDepth)
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
	public void setShelfHeight (final BigDecimal ShelfHeight)
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
	public void setUnitsPerPallet (final int UnitsPerPallet)
	{
		set_Value (COLUMNNAME_UnitsPerPallet, UnitsPerPallet);
	}

	@Override
	public int getUnitsPerPallet() 
	{
		return get_ValueAsInt(COLUMNNAME_UnitsPerPallet);
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
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setVendorCategory (final @Nullable java.lang.String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	@Override
	public java.lang.String getVendorCategory() 
	{
		return get_ValueAsString(COLUMNNAME_VendorCategory);
	}

	@Override
	public void setVendorProductNo (final @Nullable java.lang.String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	@Override
	public java.lang.String getVendorProductNo() 
	{
		return get_ValueAsString(COLUMNNAME_VendorProductNo);
	}

	@Override
	public void setVolume (final BigDecimal Volume)
	{
		set_Value (COLUMNNAME_Volume, Volume);
	}

	@Override
	public BigDecimal getVolume()
	{
		return get_ValueAsBigDecimal(COLUMNNAME_Volume);
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
	public void setX12DE355 (final @Nullable java.lang.String X12DE355)
	{
		set_Value (COLUMNNAME_X12DE355, X12DE355);
	}

	@Override
	public java.lang.String getX12DE355() 
	{
		return get_ValueAsString(COLUMNNAME_X12DE355);
	}
}