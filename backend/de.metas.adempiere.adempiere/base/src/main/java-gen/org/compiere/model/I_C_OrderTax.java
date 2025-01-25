package org.compiere.model;

import java.math.BigDecimal;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_OrderTax
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_OrderTax 
{

	String Table_Name = "C_OrderTax";

//	/** AD_Table_ID=314 */
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
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	org.compiere.model.I_C_Order getC_Order();

	void setC_Order(org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_OrderTax, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_OrderTax.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Order Tax.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderTax_ID (int C_OrderTax_ID);

	/**
	 * Get Order Tax.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderTax_ID();

	ModelColumn<I_C_OrderTax, Object> COLUMN_C_OrderTax_ID = new ModelColumn<>(I_C_OrderTax.class, "C_OrderTax_ID", null);
	String COLUMNNAME_C_OrderTax_ID = "C_OrderTax_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OrderTax, Object> COLUMN_Created = new ModelColumn<>(I_C_OrderTax.class, "Created", null);
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
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

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

	ModelColumn<I_C_OrderTax, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OrderTax.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Document Level.
	 * Tax is calculated on document level (rather than line by line)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDocumentLevel (boolean IsDocumentLevel);

	/**
	 * Get Document Level.
	 * Tax is calculated on document level (rather than line by line)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDocumentLevel();

	ModelColumn<I_C_OrderTax, Object> COLUMN_IsDocumentLevel = new ModelColumn<>(I_C_OrderTax.class, "IsDocumentLevel", null);
	String COLUMNNAME_IsDocumentLevel = "IsDocumentLevel";

	/**
	 * Set Packaging Tax.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPackagingTax (boolean IsPackagingTax);

	/**
	 * Get Packaging Tax.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPackagingTax();

	ModelColumn<I_C_OrderTax, Object> COLUMN_IsPackagingTax = new ModelColumn<>(I_C_OrderTax.class, "IsPackagingTax", null);
	String COLUMNNAME_IsPackagingTax = "IsPackagingTax";

	/**
	 * Set Price incl. Tax.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Price incl. Tax.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

	ModelColumn<I_C_OrderTax, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_C_OrderTax.class, "IsTaxIncluded", null);
	String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Whole Tax.
	 * If the flag is set, this tax can be used in documents where an entire line amount is a tax amount. Used, e.g., when a tax charge needs to be paid to a customs office.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWholeTax (boolean IsWholeTax);

	/**
	 * Get Whole Tax.
	 * If the flag is set, this tax can be used in documents where an entire line amount is a tax amount. Used, e.g., when a tax charge needs to be paid to a customs office.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWholeTax();

	ModelColumn<I_C_OrderTax, Object> COLUMN_IsWholeTax = new ModelColumn<>(I_C_OrderTax.class, "IsWholeTax", null);
	String COLUMNNAME_IsWholeTax = "IsWholeTax";

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

	ModelColumn<I_C_OrderTax, Object> COLUMN_Processed = new ModelColumn<>(I_C_OrderTax.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Tax Amount.
	 * Tax Amount for Credit Card transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTaxAmt (BigDecimal TaxAmt);

	/**
	 * Get Tax Amount.
	 * Tax Amount for Credit Card transaction
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxAmt();

	ModelColumn<I_C_OrderTax, Object> COLUMN_TaxAmt = new ModelColumn<>(I_C_OrderTax.class, "TaxAmt", null);
	String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Set Tax base Amount.
	 * Base for calculating the tax amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTaxBaseAmt (BigDecimal TaxBaseAmt);

	/**
	 * Get Tax base Amount.
	 * Base for calculating the tax amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTaxBaseAmt();

	ModelColumn<I_C_OrderTax, Object> COLUMN_TaxBaseAmt = new ModelColumn<>(I_C_OrderTax.class, "TaxBaseAmt", null);
	String COLUMNNAME_TaxBaseAmt = "TaxBaseAmt";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_OrderTax, Object> COLUMN_Updated = new ModelColumn<>(I_C_OrderTax.class, "Updated", null);
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
