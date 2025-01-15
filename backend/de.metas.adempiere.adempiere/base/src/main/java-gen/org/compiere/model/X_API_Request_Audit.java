// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for API_Request_Audit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_API_Request_Audit extends org.compiere.model.PO implements I_API_Request_Audit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1142450493L;

    /** Standard Constructor */
    public X_API_Request_Audit (final Properties ctx, final int API_Request_Audit_ID, @Nullable final String trxName)
    {
      super (ctx, API_Request_Audit_ID, trxName);
    }

    /** Load Constructor */
    public X_API_Request_Audit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public org.compiere.model.I_API_Audit_Config getAPI_Audit_Config()
	{
		return get_ValueAsPO(COLUMNNAME_API_Audit_Config_ID, org.compiere.model.I_API_Audit_Config.class);
	}

	@Override
	public void setAPI_Audit_Config(final org.compiere.model.I_API_Audit_Config API_Audit_Config)
	{
		set_ValueFromPO(COLUMNNAME_API_Audit_Config_ID, org.compiere.model.I_API_Audit_Config.class, API_Audit_Config);
	}

	@Override
	public void setAPI_Audit_Config_ID (final int API_Audit_Config_ID)
	{
		if (API_Audit_Config_ID < 1) 
			set_Value (COLUMNNAME_API_Audit_Config_ID, null);
		else 
			set_Value (COLUMNNAME_API_Audit_Config_ID, API_Audit_Config_ID);
	}

	@Override
	public int getAPI_Audit_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_API_Audit_Config_ID);
	}

	@Override
	public void setAPI_Request_Audit_ID (final int API_Request_Audit_ID)
	{
		if (API_Request_Audit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_API_Request_Audit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_API_Request_Audit_ID, API_Request_Audit_ID);
	}

	@Override
	public int getAPI_Request_Audit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_API_Request_Audit_ID);
	}

	@Override
	public void setBody (final @Nullable java.lang.String Body)
	{
		set_Value (COLUMNNAME_Body, Body);
	}

	@Override
	public java.lang.String getBody() 
	{
		return get_ValueAsString(COLUMNNAME_Body);
	}

	@Override
	public void setHttpHeaders (final @Nullable java.lang.String HttpHeaders)
	{
		set_Value (COLUMNNAME_HttpHeaders, HttpHeaders);
	}

	@Override
	public java.lang.String getHttpHeaders() 
	{
		return get_ValueAsString(COLUMNNAME_HttpHeaders);
	}

	@Override
	public void setIsErrorAcknowledged (final boolean IsErrorAcknowledged)
	{
		set_Value (COLUMNNAME_IsErrorAcknowledged, IsErrorAcknowledged);
	}

	@Override
	public boolean isErrorAcknowledged() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsErrorAcknowledged);
	}

	/** 
	 * Method AD_Reference_ID=541306
	 * Reference name: Http_Method
	 */
	public static final int METHOD_AD_Reference_ID=541306;
	/** GET = GET */
	public static final String METHOD_GET = "GET";
	/** POST = POST */
	public static final String METHOD_POST = "POST";
	/** PUT = PUT */
	public static final String METHOD_PUT = "PUT";
	/** DELETE = DELETE */
	public static final String METHOD_DELETE = "DELETE";
	/** OPTIONS = OPTIONS */
	public static final String METHOD_OPTIONS = "OPTIONS";
	/** PATCH = PATCH */
	public static final String METHOD_PATCH = "PATCH";
	/** HEAD = HEAD */
	public static final String METHOD_HEAD = "HEAD";
	/** TRACE = TRACE */
	public static final String METHOD_TRACE = "TRACE";
	/** CONNECT = CONNECT */
	public static final String METHOD_CONNECT = "CONNECT";
	@Override
	public void setMethod (final @Nullable java.lang.String Method)
	{
		set_Value (COLUMNNAME_Method, Method);
	}

	@Override
	public java.lang.String getMethod() 
	{
		return get_ValueAsString(COLUMNNAME_Method);
	}

	@Override
	public void setPath (final @Nullable java.lang.String Path)
	{
		set_Value (COLUMNNAME_Path, Path);
	}

	@Override
	public java.lang.String getPath() 
	{
		return get_ValueAsString(COLUMNNAME_Path);
	}

	@Override
	public void setRemoteAddr (final @Nullable java.lang.String RemoteAddr)
	{
		set_Value (COLUMNNAME_RemoteAddr, RemoteAddr);
	}

	@Override
	public java.lang.String getRemoteAddr() 
	{
		return get_ValueAsString(COLUMNNAME_RemoteAddr);
	}

	@Override
	public void setRemoteHost (final @Nullable java.lang.String RemoteHost)
	{
		set_Value (COLUMNNAME_RemoteHost, RemoteHost);
	}

	@Override
	public java.lang.String getRemoteHost() 
	{
		return get_ValueAsString(COLUMNNAME_RemoteHost);
	}

	@Override
	public void setRequestURI (final @Nullable java.lang.String RequestURI)
	{
		set_Value (COLUMNNAME_RequestURI, RequestURI);
	}

	@Override
	public java.lang.String getRequestURI() 
	{
		return get_ValueAsString(COLUMNNAME_RequestURI);
	}

	/** 
	 * Status AD_Reference_ID=541316
	 * Reference name: StatusList
	 */
	public static final int STATUS_AD_Reference_ID=541316;
	/** Empfangen = Empfangen */
	public static final String STATUS_Empfangen = "Empfangen";
	/** Verarbeitet = Verarbeitet */
	public static final String STATUS_Verarbeitet = "Verarbeitet";
	/** Fehler = Fehler */
	public static final String STATUS_Fehler = "Fehler";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}

	@Override
	public void setTime (final java.sql.Timestamp Time)
	{
		set_Value (COLUMNNAME_Time, Time);
	}

	@Override
	public java.sql.Timestamp getTime() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Time);
	}

	@Override
	public void setUI_Trace_ExternalId (final @Nullable java.lang.String UI_Trace_ExternalId)
	{
		set_Value (COLUMNNAME_UI_Trace_ExternalId, UI_Trace_ExternalId);
	}

	@Override
	public java.lang.String getUI_Trace_ExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_UI_Trace_ExternalId);
	}
}