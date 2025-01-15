package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_BusinessRule_Event
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_BusinessRule_Event 
{

	String Table_Name = "AD_BusinessRule_Event";

//	/** AD_Table_ID=542459 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Business Rule Event.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_Event_ID (int AD_BusinessRule_Event_ID);

	/**
	 * Get Business Rule Event.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_Event_ID();

	ModelColumn<I_AD_BusinessRule_Event, Object> COLUMN_AD_BusinessRule_Event_ID = new ModelColumn<>(I_AD_BusinessRule_Event.class, "AD_BusinessRule_Event_ID", null);
	String COLUMNNAME_AD_BusinessRule_Event_ID = "AD_BusinessRule_Event_ID";

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

	ModelColumn<I_AD_BusinessRule_Event, org.compiere.model.I_AD_BusinessRule> COLUMN_AD_BusinessRule_ID = new ModelColumn<>(I_AD_BusinessRule_Event.class, "AD_BusinessRule_ID", org.compiere.model.I_AD_BusinessRule.class);
	String COLUMNNAME_AD_BusinessRule_ID = "AD_BusinessRule_ID";

	/**
	 * Set Business Rule Trigger.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_BusinessRule_Trigger_ID (int AD_BusinessRule_Trigger_ID);

	/**
	 * Get Business Rule Trigger.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_BusinessRule_Trigger_ID();

	org.compiere.model.I_AD_BusinessRule_Trigger getAD_BusinessRule_Trigger();

	void setAD_BusinessRule_Trigger(org.compiere.model.I_AD_BusinessRule_Trigger AD_BusinessRule_Trigger);

	ModelColumn<I_AD_BusinessRule_Event, org.compiere.model.I_AD_BusinessRule_Trigger> COLUMN_AD_BusinessRule_Trigger_ID = new ModelColumn<>(I_AD_BusinessRule_Event.class, "AD_BusinessRule_Trigger_ID", org.compiere.model.I_AD_BusinessRule_Trigger.class);
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

	ModelColumn<I_AD_BusinessRule_Event, Object> COLUMN_Created = new ModelColumn<>(I_AD_BusinessRule_Event.class, "Created", null);
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

	ModelColumn<I_AD_BusinessRule_Event, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_BusinessRule_Event.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_AD_BusinessRule_Event, Object> COLUMN_Processed = new ModelColumn<>(I_AD_BusinessRule_Event.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Processing Tag.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessingTag (@Nullable java.lang.String ProcessingTag);

	/**
	 * Get Processing Tag.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProcessingTag();

	ModelColumn<I_AD_BusinessRule_Event, Object> COLUMN_ProcessingTag = new ModelColumn<>(I_AD_BusinessRule_Event.class, "ProcessingTag", null);
	String COLUMNNAME_ProcessingTag = "ProcessingTag";

	/**
	 * Set Source Record.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSource_Record_ID (int Source_Record_ID);

	/**
	 * Get Source Record.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSource_Record_ID();

	ModelColumn<I_AD_BusinessRule_Event, Object> COLUMN_Source_Record_ID = new ModelColumn<>(I_AD_BusinessRule_Event.class, "Source_Record_ID", null);
	String COLUMNNAME_Source_Record_ID = "Source_Record_ID";

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
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_BusinessRule_Event, Object> COLUMN_Updated = new ModelColumn<>(I_AD_BusinessRule_Event.class, "Updated", null);
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
