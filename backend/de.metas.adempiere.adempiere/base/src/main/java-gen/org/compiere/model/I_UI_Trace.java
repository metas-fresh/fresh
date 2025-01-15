package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for UI_Trace
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_UI_Trace 
{

	String Table_Name = "UI_Trace";

//	/** AD_Table_ID=542461 */
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
	 * Set Caption.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCaption (@Nullable java.lang.String Caption);

	/**
	 * Get Caption.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCaption();

	ModelColumn<I_UI_Trace, Object> COLUMN_Caption = new ModelColumn<>(I_UI_Trace.class, "Caption", null);
	String COLUMNNAME_Caption = "Caption";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_UI_Trace, Object> COLUMN_Created = new ModelColumn<>(I_UI_Trace.class, "Created", null);
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
	 * Set Daten.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEventData (@Nullable java.lang.String EventData);

	/**
	 * Get Daten.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEventData();

	ModelColumn<I_UI_Trace, Object> COLUMN_EventData = new ModelColumn<>(I_UI_Trace.class, "EventData", null);
	String COLUMNNAME_EventData = "EventData";

	/**
	 * Set Event Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEventName (java.lang.String EventName);

	/**
	 * Get Event Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEventName();

	ModelColumn<I_UI_Trace, Object> COLUMN_EventName = new ModelColumn<>(I_UI_Trace.class, "EventName", null);
	String COLUMNNAME_EventName = "EventName";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalId();

	ModelColumn<I_UI_Trace, Object> COLUMN_ExternalId = new ModelColumn<>(I_UI_Trace.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_UI_Trace, Object> COLUMN_IsActive = new ModelColumn<>(I_UI_Trace.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTimestamp (java.sql.Timestamp Timestamp);

	/**
	 * Get Timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getTimestamp();

	ModelColumn<I_UI_Trace, Object> COLUMN_Timestamp = new ModelColumn<>(I_UI_Trace.class, "Timestamp", null);
	String COLUMNNAME_Timestamp = "Timestamp";

	/**
	 * Set Application.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUI_ApplicationId (@Nullable java.lang.String UI_ApplicationId);

	/**
	 * Get Application.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUI_ApplicationId();

	ModelColumn<I_UI_Trace, Object> COLUMN_UI_ApplicationId = new ModelColumn<>(I_UI_Trace.class, "UI_ApplicationId", null);
	String COLUMNNAME_UI_ApplicationId = "UI_ApplicationId";

	/**
	 * Set Device ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUI_DeviceId (@Nullable java.lang.String UI_DeviceId);

	/**
	 * Get Device ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUI_DeviceId();

	ModelColumn<I_UI_Trace, Object> COLUMN_UI_DeviceId = new ModelColumn<>(I_UI_Trace.class, "UI_DeviceId", null);
	String COLUMNNAME_UI_DeviceId = "UI_DeviceId";

	/**
	 * Set Tab ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUI_TabId (@Nullable java.lang.String UI_TabId);

	/**
	 * Get Tab ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUI_TabId();

	ModelColumn<I_UI_Trace, Object> COLUMN_UI_TabId = new ModelColumn<>(I_UI_Trace.class, "UI_TabId", null);
	String COLUMNNAME_UI_TabId = "UI_TabId";

	/**
	 * Set UI Trace.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUI_Trace_ID (int UI_Trace_ID);

	/**
	 * Get UI Trace.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUI_Trace_ID();

	ModelColumn<I_UI_Trace, Object> COLUMN_UI_Trace_ID = new ModelColumn<>(I_UI_Trace.class, "UI_Trace_ID", null);
	String COLUMNNAME_UI_Trace_ID = "UI_Trace_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_UI_Trace, Object> COLUMN_Updated = new ModelColumn<>(I_UI_Trace.class, "Updated", null);
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
	 * Set URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL (@Nullable java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL();

	ModelColumn<I_UI_Trace, Object> COLUMN_URL = new ModelColumn<>(I_UI_Trace.class, "URL", null);
	String COLUMNNAME_URL = "URL";

	/**
	 * Set User Agent.
	 * Browser Used
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserAgent (@Nullable java.lang.String UserAgent);

	/**
	 * Get User Agent.
	 * Browser Used
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserAgent();

	ModelColumn<I_UI_Trace, Object> COLUMN_UserAgent = new ModelColumn<>(I_UI_Trace.class, "UserAgent", null);
	String COLUMNNAME_UserAgent = "UserAgent";

	/**
	 * Set UserName.
	 * UserName / Login to use for login
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserName (@Nullable java.lang.String UserName);

	/**
	 * Get UserName.
	 * UserName / Login to use for login
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserName();

	ModelColumn<I_UI_Trace, Object> COLUMN_UserName = new ModelColumn<>(I_UI_Trace.class, "UserName", null);
	String COLUMNNAME_UserName = "UserName";
}
