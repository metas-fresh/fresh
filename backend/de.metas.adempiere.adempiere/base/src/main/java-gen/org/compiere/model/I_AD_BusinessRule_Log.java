package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_BusinessRule_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_BusinessRule_Log 
{

	String Table_Name = "AD_BusinessRule_Log";

//	/** AD_Table_ID=542460 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Business Rule Event.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_Event_ID (int AD_BusinessRule_Event_ID);

	/**
	 * Get Business Rule Event.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_Event_ID();

	@Nullable org.compiere.model.I_AD_BusinessRule_Event getAD_BusinessRule_Event();

	void setAD_BusinessRule_Event(@Nullable org.compiere.model.I_AD_BusinessRule_Event AD_BusinessRule_Event);

	ModelColumn<I_AD_BusinessRule_Log, org.compiere.model.I_AD_BusinessRule_Event> COLUMN_AD_BusinessRule_Event_ID = new ModelColumn<>(I_AD_BusinessRule_Log.class, "AD_BusinessRule_Event_ID", org.compiere.model.I_AD_BusinessRule_Event.class);
	String COLUMNNAME_AD_BusinessRule_Event_ID = "AD_BusinessRule_Event_ID";

	/**
	 * Set Business Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_ID (int AD_BusinessRule_ID);

	/**
	 * Get Business Rule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_ID();

	@Nullable org.compiere.model.I_AD_BusinessRule getAD_BusinessRule();

	void setAD_BusinessRule(@Nullable org.compiere.model.I_AD_BusinessRule AD_BusinessRule);

	ModelColumn<I_AD_BusinessRule_Log, org.compiere.model.I_AD_BusinessRule> COLUMN_AD_BusinessRule_ID = new ModelColumn<>(I_AD_BusinessRule_Log.class, "AD_BusinessRule_ID", org.compiere.model.I_AD_BusinessRule.class);
	String COLUMNNAME_AD_BusinessRule_ID = "AD_BusinessRule_ID";

	/**
	 * Set Business Rule Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_Log_ID (int AD_BusinessRule_Log_ID);

	/**
	 * Get Business Rule Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_Log_ID();

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_AD_BusinessRule_Log_ID = new ModelColumn<>(I_AD_BusinessRule_Log.class, "AD_BusinessRule_Log_ID", null);
	String COLUMNNAME_AD_BusinessRule_Log_ID = "AD_BusinessRule_Log_ID";

	/**
	 * Set Business Rule Trigger.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_Trigger_ID (int AD_BusinessRule_Trigger_ID);

	/**
	 * Get Business Rule Trigger.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_Trigger_ID();

	@Nullable org.compiere.model.I_AD_BusinessRule_Trigger getAD_BusinessRule_Trigger();

	void setAD_BusinessRule_Trigger(@Nullable org.compiere.model.I_AD_BusinessRule_Trigger AD_BusinessRule_Trigger);

	ModelColumn<I_AD_BusinessRule_Log, org.compiere.model.I_AD_BusinessRule_Trigger> COLUMN_AD_BusinessRule_Trigger_ID = new ModelColumn<>(I_AD_BusinessRule_Log.class, "AD_BusinessRule_Trigger_ID", org.compiere.model.I_AD_BusinessRule_Trigger.class);
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

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_Created = new ModelColumn<>(I_AD_BusinessRule_Log.class, "Created", null);
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
	 * Set Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDurationMillis (int DurationMillis);

	/**
	 * Get Duration (ms).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDurationMillis();

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_DurationMillis = new ModelColumn<>(I_AD_BusinessRule_Log.class, "DurationMillis", null);
	String COLUMNNAME_DurationMillis = "DurationMillis";

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

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_BusinessRule_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Ebene.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLevel (java.lang.String Level);

	/**
	 * Get Ebene.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getLevel();

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_Level = new ModelColumn<>(I_AD_BusinessRule_Log.class, "Level", null);
	String COLUMNNAME_Level = "Level";

	/**
	 * Set Module.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModule (@Nullable java.lang.String Module);

	/**
	 * Get Module.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getModule();

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_Module = new ModelColumn<>(I_AD_BusinessRule_Log.class, "Module", null);
	String COLUMNNAME_Module = "Module";

	/**
	 * Set Message Text.
	 * Textual Informational, Menu or Error Message
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMsgText (@Nullable java.lang.String MsgText);

	/**
	 * Get Message Text.
	 * Textual Informational, Menu or Error Message
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMsgText();

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_MsgText = new ModelColumn<>(I_AD_BusinessRule_Log.class, "MsgText", null);
	String COLUMNNAME_MsgText = "MsgText";

	/**
	 * Set Source Record.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSource_Record_ID (int Source_Record_ID);

	/**
	 * Get Source Record.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSource_Record_ID();

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_Source_Record_ID = new ModelColumn<>(I_AD_BusinessRule_Log.class, "Source_Record_ID", null);
	String COLUMNNAME_Source_Record_ID = "Source_Record_ID";

	/**
	 * Set Source Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSource_Table_ID (int Source_Table_ID);

	/**
	 * Get Source Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSource_Table_ID();

	String COLUMNNAME_Source_Table_ID = "Source_Table_ID";

	/**
	 * Set Target Record ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTarget_Record_ID (int Target_Record_ID);

	/**
	 * Get Target Record ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTarget_Record_ID();

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_Target_Record_ID = new ModelColumn<>(I_AD_BusinessRule_Log.class, "Target_Record_ID", null);
	String COLUMNNAME_Target_Record_ID = "Target_Record_ID";

	/**
	 * Set Target Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTarget_Table_ID (int Target_Table_ID);

	/**
	 * Get Target Table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getTarget_Table_ID();

	String COLUMNNAME_Target_Table_ID = "Target_Table_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_BusinessRule_Log, Object> COLUMN_Updated = new ModelColumn<>(I_AD_BusinessRule_Log.class, "Updated", null);
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
