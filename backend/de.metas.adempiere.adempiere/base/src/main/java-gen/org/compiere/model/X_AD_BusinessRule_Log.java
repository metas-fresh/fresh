// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_BusinessRule_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_BusinessRule_Log extends org.compiere.model.PO implements I_AD_BusinessRule_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1639707445L;

    /** Standard Constructor */
    public X_AD_BusinessRule_Log (final Properties ctx, final int AD_BusinessRule_Log_ID, @Nullable final String trxName)
    {
      super (ctx, AD_BusinessRule_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_BusinessRule_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_BusinessRule_Event getAD_BusinessRule_Event()
	{
		return get_ValueAsPO(COLUMNNAME_AD_BusinessRule_Event_ID, org.compiere.model.I_AD_BusinessRule_Event.class);
	}

	@Override
	public void setAD_BusinessRule_Event(final org.compiere.model.I_AD_BusinessRule_Event AD_BusinessRule_Event)
	{
		set_ValueFromPO(COLUMNNAME_AD_BusinessRule_Event_ID, org.compiere.model.I_AD_BusinessRule_Event.class, AD_BusinessRule_Event);
	}

	@Override
	public void setAD_BusinessRule_Event_ID (final int AD_BusinessRule_Event_ID)
	{
		if (AD_BusinessRule_Event_ID < 1) 
			set_Value (COLUMNNAME_AD_BusinessRule_Event_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BusinessRule_Event_ID, AD_BusinessRule_Event_ID);
	}

	@Override
	public int getAD_BusinessRule_Event_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_Event_ID);
	}

	@Override
	public org.compiere.model.I_AD_BusinessRule getAD_BusinessRule()
	{
		return get_ValueAsPO(COLUMNNAME_AD_BusinessRule_ID, org.compiere.model.I_AD_BusinessRule.class);
	}

	@Override
	public void setAD_BusinessRule(final org.compiere.model.I_AD_BusinessRule AD_BusinessRule)
	{
		set_ValueFromPO(COLUMNNAME_AD_BusinessRule_ID, org.compiere.model.I_AD_BusinessRule.class, AD_BusinessRule);
	}

	@Override
	public void setAD_BusinessRule_ID (final int AD_BusinessRule_ID)
	{
		if (AD_BusinessRule_ID < 1) 
			set_Value (COLUMNNAME_AD_BusinessRule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BusinessRule_ID, AD_BusinessRule_ID);
	}

	@Override
	public int getAD_BusinessRule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_ID);
	}

	@Override
	public void setAD_BusinessRule_Log_ID (final int AD_BusinessRule_Log_ID)
	{
		if (AD_BusinessRule_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_Log_ID, AD_BusinessRule_Log_ID);
	}

	@Override
	public int getAD_BusinessRule_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_Log_ID);
	}

	@Override
	public org.compiere.model.I_AD_BusinessRule_Trigger getAD_BusinessRule_Trigger()
	{
		return get_ValueAsPO(COLUMNNAME_AD_BusinessRule_Trigger_ID, org.compiere.model.I_AD_BusinessRule_Trigger.class);
	}

	@Override
	public void setAD_BusinessRule_Trigger(final org.compiere.model.I_AD_BusinessRule_Trigger AD_BusinessRule_Trigger)
	{
		set_ValueFromPO(COLUMNNAME_AD_BusinessRule_Trigger_ID, org.compiere.model.I_AD_BusinessRule_Trigger.class, AD_BusinessRule_Trigger);
	}

	@Override
	public void setAD_BusinessRule_Trigger_ID (final int AD_BusinessRule_Trigger_ID)
	{
		if (AD_BusinessRule_Trigger_ID < 1) 
			set_Value (COLUMNNAME_AD_BusinessRule_Trigger_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BusinessRule_Trigger_ID, AD_BusinessRule_Trigger_ID);
	}

	@Override
	public int getAD_BusinessRule_Trigger_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_Trigger_ID);
	}

	@Override
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setDurationMillis (final int DurationMillis)
	{
		set_Value (COLUMNNAME_DurationMillis, DurationMillis);
	}

	@Override
	public int getDurationMillis() 
	{
		return get_ValueAsInt(COLUMNNAME_DurationMillis);
	}

	@Override
	public void setLevel (final java.lang.String Level)
	{
		set_ValueNoCheck (COLUMNNAME_Level, Level);
	}

	@Override
	public java.lang.String getLevel() 
	{
		return get_ValueAsString(COLUMNNAME_Level);
	}

	@Override
	public void setModule (final @Nullable java.lang.String Module)
	{
		set_Value (COLUMNNAME_Module, Module);
	}

	@Override
	public java.lang.String getModule() 
	{
		return get_ValueAsString(COLUMNNAME_Module);
	}

	@Override
	public void setMsgText (final @Nullable java.lang.String MsgText)
	{
		set_Value (COLUMNNAME_MsgText, MsgText);
	}

	@Override
	public java.lang.String getMsgText() 
	{
		return get_ValueAsString(COLUMNNAME_MsgText);
	}

	@Override
	public void setSource_Record_ID (final int Source_Record_ID)
	{
		if (Source_Record_ID < 1) 
			set_Value (COLUMNNAME_Source_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Source_Record_ID, Source_Record_ID);
	}

	@Override
	public int getSource_Record_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_Record_ID);
	}

	@Override
	public void setSource_Table_ID (final int Source_Table_ID)
	{
		if (Source_Table_ID < 1) 
			set_Value (COLUMNNAME_Source_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Source_Table_ID, Source_Table_ID);
	}

	@Override
	public int getSource_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_Table_ID);
	}

	@Override
	public void setTarget_Record_ID (final int Target_Record_ID)
	{
		if (Target_Record_ID < 1) 
			set_Value (COLUMNNAME_Target_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Target_Record_ID, Target_Record_ID);
	}

	@Override
	public int getTarget_Record_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Target_Record_ID);
	}

	@Override
	public void setTarget_Table_ID (final int Target_Table_ID)
	{
		if (Target_Table_ID < 1) 
			set_Value (COLUMNNAME_Target_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Target_Table_ID, Target_Table_ID);
	}

	@Override
	public int getTarget_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Target_Table_ID);
	}
}