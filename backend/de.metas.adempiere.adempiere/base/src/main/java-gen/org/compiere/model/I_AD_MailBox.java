package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_MailBox
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_MailBox 
{

	String Table_Name = "AD_MailBox";

//	/** AD_Table_ID=540242 */
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
	 * Set Mail Box.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_MailBox_ID (int AD_MailBox_ID);

	/**
	 * Get Mail Box.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_MailBox_ID();

	ModelColumn<I_AD_MailBox, Object> COLUMN_AD_MailBox_ID = new ModelColumn<>(I_AD_MailBox.class, "AD_MailBox_ID", null);
	String COLUMNNAME_AD_MailBox_ID = "AD_MailBox_ID";

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

	ModelColumn<I_AD_MailBox, Object> COLUMN_Created = new ModelColumn<>(I_AD_MailBox.class, "Created", null);
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
	 * Set eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEMail (java.lang.String EMail);

	/**
	 * Get eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEMail();

	ModelColumn<I_AD_MailBox, Object> COLUMN_EMail = new ModelColumn<>(I_AD_MailBox.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

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

	ModelColumn<I_AD_MailBox, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_MailBox.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SMTP Authorization.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSmtpAuthorization (boolean IsSmtpAuthorization);

	/**
	 * Get SMTP Authorization.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSmtpAuthorization();

	ModelColumn<I_AD_MailBox, Object> COLUMN_IsSmtpAuthorization = new ModelColumn<>(I_AD_MailBox.class, "IsSmtpAuthorization", null);
	String COLUMNNAME_IsSmtpAuthorization = "IsSmtpAuthorization";

	/**
	 * Set Start TLS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsStartTLS (boolean IsStartTLS);

	/**
	 * Get Start TLS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isStartTLS();

	ModelColumn<I_AD_MailBox, Object> COLUMN_IsStartTLS = new ModelColumn<>(I_AD_MailBox.class, "IsStartTLS", null);
	String COLUMNNAME_IsStartTLS = "IsStartTLS";

	/**
	 * Set Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMSGRAPH_ClientId (@Nullable java.lang.String MSGRAPH_ClientId);

	/**
	 * Get Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMSGRAPH_ClientId();

	ModelColumn<I_AD_MailBox, Object> COLUMN_MSGRAPH_ClientId = new ModelColumn<>(I_AD_MailBox.class, "MSGRAPH_ClientId", null);
	String COLUMNNAME_MSGRAPH_ClientId = "MSGRAPH_ClientId";

	/**
	 * Set Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMSGRAPH_ClientSecret (@Nullable java.lang.String MSGRAPH_ClientSecret);

	/**
	 * Get Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMSGRAPH_ClientSecret();

	ModelColumn<I_AD_MailBox, Object> COLUMN_MSGRAPH_ClientSecret = new ModelColumn<>(I_AD_MailBox.class, "MSGRAPH_ClientSecret", null);
	String COLUMNNAME_MSGRAPH_ClientSecret = "MSGRAPH_ClientSecret";

	/**
	 * Set Tenant ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMSGRAPH_TenantId (@Nullable java.lang.String MSGRAPH_TenantId);

	/**
	 * Get Tenant ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMSGRAPH_TenantId();

	ModelColumn<I_AD_MailBox, Object> COLUMN_MSGRAPH_TenantId = new ModelColumn<>(I_AD_MailBox.class, "MSGRAPH_TenantId", null);
	String COLUMNNAME_MSGRAPH_TenantId = "MSGRAPH_TenantId";

	/**
	 * Set Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPassword (@Nullable java.lang.String Password);

	/**
	 * Get Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPassword();

	ModelColumn<I_AD_MailBox, Object> COLUMN_Password = new ModelColumn<>(I_AD_MailBox.class, "Password", null);
	String COLUMNNAME_Password = "Password";

	/**
	 * Set EMail-Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSMTPHost (@Nullable java.lang.String SMTPHost);

	/**
	 * Get EMail-Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSMTPHost();

	ModelColumn<I_AD_MailBox, Object> COLUMN_SMTPHost = new ModelColumn<>(I_AD_MailBox.class, "SMTPHost", null);
	String COLUMNNAME_SMTPHost = "SMTPHost";

	/**
	 * Set SMTP Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSMTPPort (int SMTPPort);

	/**
	 * Get SMTP Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSMTPPort();

	ModelColumn<I_AD_MailBox, Object> COLUMN_SMTPPort = new ModelColumn<>(I_AD_MailBox.class, "SMTPPort", null);
	String COLUMNNAME_SMTPPort = "SMTPPort";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_AD_MailBox, Object> COLUMN_Type = new ModelColumn<>(I_AD_MailBox.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_MailBox, Object> COLUMN_Updated = new ModelColumn<>(I_AD_MailBox.class, "Updated", null);
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

	ModelColumn<I_AD_MailBox, Object> COLUMN_UserName = new ModelColumn<>(I_AD_MailBox.class, "UserName", null);
	String COLUMNNAME_UserName = "UserName";
}
