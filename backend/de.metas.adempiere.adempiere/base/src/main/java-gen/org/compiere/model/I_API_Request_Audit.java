package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for API_Request_Audit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_API_Request_Audit 
{

	String Table_Name = "API_Request_Audit";

//	/** AD_Table_ID=541636 */
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
	 * Set Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Role_ID();

	org.compiere.model.I_AD_Role getAD_Role();

	void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

	ModelColumn<I_API_Request_Audit, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new ModelColumn<>(I_API_Request_Audit.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set API Audit Config.
	 * API audit config that matched the request and lead to the creation of this audit record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAPI_Audit_Config_ID (int API_Audit_Config_ID);

	/**
	 * Get API Audit Config.
	 * API audit config that matched the request and lead to the creation of this audit record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAPI_Audit_Config_ID();

	org.compiere.model.I_API_Audit_Config getAPI_Audit_Config();

	void setAPI_Audit_Config(org.compiere.model.I_API_Audit_Config API_Audit_Config);

	ModelColumn<I_API_Request_Audit, org.compiere.model.I_API_Audit_Config> COLUMN_API_Audit_Config_ID = new ModelColumn<>(I_API_Request_Audit.class, "API_Audit_Config_ID", org.compiere.model.I_API_Audit_Config.class);
	String COLUMNNAME_API_Audit_Config_ID = "API_Audit_Config_ID";

	/**
	 * Set API Request Audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAPI_Request_Audit_ID (int API_Request_Audit_ID);

	/**
	 * Get API Request Audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAPI_Request_Audit_ID();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_API_Request_Audit_ID = new ModelColumn<>(I_API_Request_Audit.class, "API_Request_Audit_ID", null);
	String COLUMNNAME_API_Request_Audit_ID = "API_Request_Audit_ID";

	/**
	 * Set Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBody (@Nullable java.lang.String Body);

	/**
	 * Get Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBody();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_Body = new ModelColumn<>(I_API_Request_Audit.class, "Body", null);
	String COLUMNNAME_Body = "Body";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_Created = new ModelColumn<>(I_API_Request_Audit.class, "Created", null);
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
	 * Set Http headers.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHttpHeaders (@Nullable java.lang.String HttpHeaders);

	/**
	 * Get Http headers.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHttpHeaders();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_HttpHeaders = new ModelColumn<>(I_API_Request_Audit.class, "HttpHeaders", null);
	String COLUMNNAME_HttpHeaders = "HttpHeaders";

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

	ModelColumn<I_API_Request_Audit, Object> COLUMN_IsActive = new ModelColumn<>(I_API_Request_Audit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Error acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsErrorAcknowledged (boolean IsErrorAcknowledged);

	/**
	 * Get Error acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isErrorAcknowledged();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_IsErrorAcknowledged = new ModelColumn<>(I_API_Request_Audit.class, "IsErrorAcknowledged", null);
	String COLUMNNAME_IsErrorAcknowledged = "IsErrorAcknowledged";

	/**
	 * Set Method.
	 * HTTP method matched by this line. An empty value matches all methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMethod (@Nullable java.lang.String Method);

	/**
	 * Get Method.
	 * HTTP method matched by this line. An empty value matches all methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMethod();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_Method = new ModelColumn<>(I_API_Request_Audit.class, "Method", null);
	String COLUMNNAME_Method = "Method";

	/**
	 * Set Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPath (@Nullable java.lang.String Path);

	/**
	 * Get Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPath();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_Path = new ModelColumn<>(I_API_Request_Audit.class, "Path", null);
	String COLUMNNAME_Path = "Path";

	/**
	 * Set Remote Address.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRemoteAddr (@Nullable java.lang.String RemoteAddr);

	/**
	 * Get Remote Address.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRemoteAddr();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_RemoteAddr = new ModelColumn<>(I_API_Request_Audit.class, "RemoteAddr", null);
	String COLUMNNAME_RemoteAddr = "RemoteAddr";

	/**
	 * Set Remote Host.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRemoteHost (@Nullable java.lang.String RemoteHost);

	/**
	 * Get Remote Host.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRemoteHost();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_RemoteHost = new ModelColumn<>(I_API_Request_Audit.class, "RemoteHost", null);
	String COLUMNNAME_RemoteHost = "RemoteHost";

	/**
	 * Set URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestURI (@Nullable java.lang.String RequestURI);

	/**
	 * Get URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestURI();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_RequestURI = new ModelColumn<>(I_API_Request_Audit.class, "RequestURI", null);
	String COLUMNNAME_RequestURI = "RequestURI";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStatus();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_Status = new ModelColumn<>(I_API_Request_Audit.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Set Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTime (java.sql.Timestamp Time);

	/**
	 * Get Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getTime();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_Time = new ModelColumn<>(I_API_Request_Audit.class, "Time", null);
	String COLUMNNAME_Time = "Time";

	/**
	 * Set UI Trace External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUI_Trace_ExternalId (@Nullable java.lang.String UI_Trace_ExternalId);

	/**
	 * Get UI Trace External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUI_Trace_ExternalId();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_UI_Trace_ExternalId = new ModelColumn<>(I_API_Request_Audit.class, "UI_Trace_ExternalId", null);
	String COLUMNNAME_UI_Trace_ExternalId = "UI_Trace_ExternalId";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_API_Request_Audit, Object> COLUMN_Updated = new ModelColumn<>(I_API_Request_Audit.class, "Updated", null);
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
