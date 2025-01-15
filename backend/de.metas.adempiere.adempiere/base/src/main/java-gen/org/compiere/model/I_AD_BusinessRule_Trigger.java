package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_BusinessRule_Trigger
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_BusinessRule_Trigger 
{

	String Table_Name = "AD_BusinessRule_Trigger";

//	/** AD_Table_ID=542458 */
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

	ModelColumn<I_AD_BusinessRule_Trigger, org.compiere.model.I_AD_BusinessRule> COLUMN_AD_BusinessRule_ID = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "AD_BusinessRule_ID", org.compiere.model.I_AD_BusinessRule.class);
	String COLUMNNAME_AD_BusinessRule_ID = "AD_BusinessRule_ID";

	/**
	 * Set Business Rule Trigger.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_Trigger_ID (int AD_BusinessRule_Trigger_ID);

	/**
	 * Get Business Rule Trigger.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_Trigger_ID();

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_AD_BusinessRule_Trigger_ID = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "AD_BusinessRule_Trigger_ID", null);
	String COLUMNNAME_AD_BusinessRule_Trigger_ID = "AD_BusinessRule_Trigger_ID";

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
	 * Set Condition SQL.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConditionSQL (@Nullable java.lang.String ConditionSQL);

	/**
	 * Get Condition SQL.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getConditionSQL();

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_ConditionSQL = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "ConditionSQL", null);
	String COLUMNNAME_ConditionSQL = "ConditionSQL";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_Created = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "Created", null);
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

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set On Delete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnDelete (boolean OnDelete);

	/**
	 * Get On Delete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnDelete();

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_OnDelete = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "OnDelete", null);
	String COLUMNNAME_OnDelete = "OnDelete";

	/**
	 * Set On New.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnNew (boolean OnNew);

	/**
	 * Get On New.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnNew();

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_OnNew = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "OnNew", null);
	String COLUMNNAME_OnNew = "OnNew";

	/**
	 * Set On Update.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnUpdate (boolean OnUpdate);

	/**
	 * Get On Update.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnUpdate();

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_OnUpdate = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "OnUpdate", null);
	String COLUMNNAME_OnUpdate = "OnUpdate";

	/**
	 * Set Source Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSource_Table_ID (int Source_Table_ID);

	/**
	 * Get Source Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSource_Table_ID();

	String COLUMNNAME_Source_Table_ID = "Source_Table_ID";

	/**
	 * Set Target Record Mapping SQL.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTargetRecordMappingSQL (java.lang.String TargetRecordMappingSQL);

	/**
	 * Get Target Record Mapping SQL.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTargetRecordMappingSQL();

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_TargetRecordMappingSQL = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "TargetRecordMappingSQL", null);
	String COLUMNNAME_TargetRecordMappingSQL = "TargetRecordMappingSQL";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_BusinessRule_Trigger, Object> COLUMN_Updated = new ModelColumn<>(I_AD_BusinessRule_Trigger.class, "Updated", null);
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
