// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_MailBox
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_MailBox extends org.compiere.model.PO implements I_AD_MailBox, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1265550932L;

    /** Standard Constructor */
    public X_AD_MailBox (final Properties ctx, final int AD_MailBox_ID, @Nullable final String trxName)
    {
      super (ctx, AD_MailBox_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_MailBox (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_MailBox_ID (final int AD_MailBox_ID)
	{
		if (AD_MailBox_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_MailBox_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_MailBox_ID, AD_MailBox_ID);
	}

	@Override
	public int getAD_MailBox_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_MailBox_ID);
	}

	@Override
	public void setEMail (final java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
	}

	@Override
	public void setIsSmtpAuthorization (final boolean IsSmtpAuthorization)
	{
		set_Value (COLUMNNAME_IsSmtpAuthorization, IsSmtpAuthorization);
	}

	@Override
	public boolean isSmtpAuthorization() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSmtpAuthorization);
	}

	@Override
	public void setIsStartTLS (final boolean IsStartTLS)
	{
		set_Value (COLUMNNAME_IsStartTLS, IsStartTLS);
	}

	@Override
	public boolean isStartTLS() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsStartTLS);
	}

	@Override
	public void setMSGRAPH_ClientId (final @Nullable java.lang.String MSGRAPH_ClientId)
	{
		set_Value (COLUMNNAME_MSGRAPH_ClientId, MSGRAPH_ClientId);
	}

	@Override
	public java.lang.String getMSGRAPH_ClientId() 
	{
		return get_ValueAsString(COLUMNNAME_MSGRAPH_ClientId);
	}

	@Override
	public void setMSGRAPH_ClientSecret (final @Nullable java.lang.String MSGRAPH_ClientSecret)
	{
		set_Value (COLUMNNAME_MSGRAPH_ClientSecret, MSGRAPH_ClientSecret);
	}

	@Override
	public java.lang.String getMSGRAPH_ClientSecret() 
	{
		return get_ValueAsString(COLUMNNAME_MSGRAPH_ClientSecret);
	}

	@Override
	public void setMSGRAPH_TenantId (final @Nullable java.lang.String MSGRAPH_TenantId)
	{
		set_Value (COLUMNNAME_MSGRAPH_TenantId, MSGRAPH_TenantId);
	}

	@Override
	public java.lang.String getMSGRAPH_TenantId() 
	{
		return get_ValueAsString(COLUMNNAME_MSGRAPH_TenantId);
	}

	@Override
	public void setPassword (final @Nullable java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	@Override
	public java.lang.String getPassword() 
	{
		return get_ValueAsString(COLUMNNAME_Password);
	}

	@Override
	public void setSMTPHost (final @Nullable java.lang.String SMTPHost)
	{
		set_Value (COLUMNNAME_SMTPHost, SMTPHost);
	}

	@Override
	public java.lang.String getSMTPHost() 
	{
		return get_ValueAsString(COLUMNNAME_SMTPHost);
	}

	@Override
	public void setSMTPPort (final int SMTPPort)
	{
		set_Value (COLUMNNAME_SMTPPort, SMTPPort);
	}

	@Override
	public int getSMTPPort() 
	{
		return get_ValueAsInt(COLUMNNAME_SMTPPort);
	}

	/** 
	 * Type AD_Reference_ID=541904
	 * Reference name: AD_MailBox_Type
	 */
	public static final int TYPE_AD_Reference_ID=541904;
	/** SMTP = smtp */
	public static final String TYPE_SMTP = "smtp";
	/** MSGraph = msgraph */
	public static final String TYPE_MSGraph = "msgraph";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}

	@Override
	public void setUserName (final @Nullable java.lang.String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	@Override
	public java.lang.String getUserName() 
	{
		return get_ValueAsString(COLUMNNAME_UserName);
	}
}