package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_BusinessRule_Precondition
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_BusinessRule_Precondition 
{

	String Table_Name = "AD_BusinessRule_Precondition";

//	/** AD_Table_ID=542457 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Business Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_ID (int AD_BusinessRule_ID);

	/**
	 * Get Business Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_ID();

	org.compiere.model.I_AD_BusinessRule getAD_BusinessRule();

	void setAD_BusinessRule(org.compiere.model.I_AD_BusinessRule AD_BusinessRule);

	ModelColumn<I_AD_BusinessRule_Precondition, org.compiere.model.I_AD_BusinessRule> COLUMN_AD_BusinessRule_ID = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "AD_BusinessRule_ID", org.compiere.model.I_AD_BusinessRule.class);
	String COLUMNNAME_AD_BusinessRule_ID = "AD_BusinessRule_ID";

	/**
	 * Set Business Rule Precondition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_Precondition_ID (int AD_BusinessRule_Precondition_ID);

	/**
	 * Get Business Rule Precondition.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_Precondition_ID();

	ModelColumn<I_AD_BusinessRule_Precondition, Object> COLUMN_AD_BusinessRule_Precondition_ID = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "AD_BusinessRule_Precondition_ID", null);
	String COLUMNNAME_AD_BusinessRule_Precondition_ID = "AD_BusinessRule_Precondition_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_BusinessRule_Precondition, Object> COLUMN_Created = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_BusinessRule_Precondition, Object> COLUMN_Description = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_AD_BusinessRule_Precondition, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Precondition Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrecondition_Rule_ID (int Precondition_Rule_ID);

	/**
	 * Get Precondition Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrecondition_Rule_ID();

	@Nullable org.compiere.model.I_AD_Val_Rule getPrecondition_Rule();

	void setPrecondition_Rule(@Nullable org.compiere.model.I_AD_Val_Rule Precondition_Rule);

	ModelColumn<I_AD_BusinessRule_Precondition, org.compiere.model.I_AD_Val_Rule> COLUMN_Precondition_Rule_ID = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "Precondition_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_Precondition_Rule_ID = "Precondition_Rule_ID";

	/**
	 * Set Precondition SQL.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreconditionSQL (@Nullable java.lang.String PreconditionSQL);

	/**
	 * Get Precondition SQL.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPreconditionSQL();

	ModelColumn<I_AD_BusinessRule_Precondition, Object> COLUMN_PreconditionSQL = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "PreconditionSQL", null);
	String COLUMNNAME_PreconditionSQL = "PreconditionSQL";

	/**
	 * Set Precondition Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPreconditionType (java.lang.String PreconditionType);

	/**
	 * Get Precondition Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPreconditionType();

	ModelColumn<I_AD_BusinessRule_Precondition, Object> COLUMN_PreconditionType = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "PreconditionType", null);
	String COLUMNNAME_PreconditionType = "PreconditionType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_BusinessRule_Precondition, Object> COLUMN_Updated = new ModelColumn<>(I_AD_BusinessRule_Precondition.class, "Updated", null);
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
