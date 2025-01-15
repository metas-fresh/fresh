// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for UI_Trace
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_UI_Trace extends org.compiere.model.PO implements I_UI_Trace, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1376553171L;

    /** Standard Constructor */
    public X_UI_Trace (final Properties ctx, final int UI_Trace_ID, @Nullable final String trxName)
    {
      super (ctx, UI_Trace_ID, trxName);
    }

    /** Load Constructor */
    public X_UI_Trace (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCaption (final @Nullable java.lang.String Caption)
	{
		set_Value (COLUMNNAME_Caption, Caption);
	}

	@Override
	public java.lang.String getCaption() 
	{
		return get_ValueAsString(COLUMNNAME_Caption);
	}

	@Override
	public void setEventData (final @Nullable java.lang.String EventData)
	{
		set_Value (COLUMNNAME_EventData, EventData);
	}

	@Override
	public java.lang.String getEventData() 
	{
		return get_ValueAsString(COLUMNNAME_EventData);
	}

	@Override
	public void setEventName (final java.lang.String EventName)
	{
		set_Value (COLUMNNAME_EventName, EventName);
	}

	@Override
	public java.lang.String getEventName() 
	{
		return get_ValueAsString(COLUMNNAME_EventName);
	}

	@Override
	public void setExternalId (final java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setTimestamp (final java.sql.Timestamp Timestamp)
	{
		set_Value (COLUMNNAME_Timestamp, Timestamp);
	}

	@Override
	public java.sql.Timestamp getTimestamp() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Timestamp);
	}

	@Override
	public void setUI_ApplicationId (final @Nullable java.lang.String UI_ApplicationId)
	{
		set_Value (COLUMNNAME_UI_ApplicationId, UI_ApplicationId);
	}

	@Override
	public java.lang.String getUI_ApplicationId() 
	{
		return get_ValueAsString(COLUMNNAME_UI_ApplicationId);
	}

	@Override
	public void setUI_DeviceId (final @Nullable java.lang.String UI_DeviceId)
	{
		set_Value (COLUMNNAME_UI_DeviceId, UI_DeviceId);
	}

	@Override
	public java.lang.String getUI_DeviceId() 
	{
		return get_ValueAsString(COLUMNNAME_UI_DeviceId);
	}

	@Override
	public void setUI_TabId (final @Nullable java.lang.String UI_TabId)
	{
		set_Value (COLUMNNAME_UI_TabId, UI_TabId);
	}

	@Override
	public java.lang.String getUI_TabId() 
	{
		return get_ValueAsString(COLUMNNAME_UI_TabId);
	}

	@Override
	public void setUI_Trace_ID (final int UI_Trace_ID)
	{
		if (UI_Trace_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UI_Trace_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UI_Trace_ID, UI_Trace_ID);
	}

	@Override
	public int getUI_Trace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_UI_Trace_ID);
	}

	@Override
	public void setURL (final @Nullable java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	@Override
	public java.lang.String getURL() 
	{
		return get_ValueAsString(COLUMNNAME_URL);
	}

	@Override
	public void setUserAgent (final @Nullable java.lang.String UserAgent)
	{
		set_Value (COLUMNNAME_UserAgent, UserAgent);
	}

	@Override
	public java.lang.String getUserAgent() 
	{
		return get_ValueAsString(COLUMNNAME_UserAgent);
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