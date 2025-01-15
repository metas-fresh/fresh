package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_BusinessRule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_BusinessRule 
{

	String Table_Name = "AD_BusinessRule";

//	/** AD_Table_ID=542456 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Business Rule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_ID (int AD_BusinessRule_ID);

	/**
	 * Get Business Rule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_ID();

	ModelColumn<I_AD_BusinessRule, Object> COLUMN_AD_BusinessRule_ID = new ModelColumn<>(I_AD_BusinessRule.class, "AD_BusinessRule_ID", null);
	String COLUMNNAME_AD_BusinessRule_ID = "AD_BusinessRule_ID";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_BusinessRule, Object> COLUMN_Created = new ModelColumn<>(I_AD_BusinessRule.class, "Created", null);
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

	ModelColumn<I_AD_BusinessRule, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_BusinessRule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_AD_BusinessRule, Object> COLUMN_Name = new ModelColumn<>(I_AD_BusinessRule.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_BusinessRule, Object> COLUMN_Updated = new ModelColumn<>(I_AD_BusinessRule.class, "Updated", null);
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
	 * Set Validation Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidation_Rule_ID (int Validation_Rule_ID);

	/**
	 * Get Validation Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getValidation_Rule_ID();

	@Nullable org.compiere.model.I_AD_Val_Rule getValidation_Rule();

	void setValidation_Rule(@Nullable org.compiere.model.I_AD_Val_Rule Validation_Rule);

	ModelColumn<I_AD_BusinessRule, org.compiere.model.I_AD_Val_Rule> COLUMN_Validation_Rule_ID = new ModelColumn<>(I_AD_BusinessRule.class, "Validation_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_Validation_Rule_ID = "Validation_Rule_ID";

	/**
	 * Set Warning Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarningMessage (@Nullable java.lang.String WarningMessage);

	/**
	 * Get Warning Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWarningMessage();

	ModelColumn<I_AD_BusinessRule, Object> COLUMN_WarningMessage = new ModelColumn<>(I_AD_BusinessRule.class, "WarningMessage", null);
	String COLUMNNAME_WarningMessage = "WarningMessage";
}
