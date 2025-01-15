// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_BusinessRule_Event
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_BusinessRule_Event extends org.compiere.model.PO implements I_AD_BusinessRule_Event, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1166482534L;

    /** Standard Constructor */
    public X_AD_BusinessRule_Event (final Properties ctx, final int AD_BusinessRule_Event_ID, @Nullable final String trxName)
    {
      super (ctx, AD_BusinessRule_Event_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_BusinessRule_Event (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_BusinessRule_Event_ID (final int AD_BusinessRule_Event_ID)
	{
		if (AD_BusinessRule_Event_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_Event_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_Event_ID, AD_BusinessRule_Event_ID);
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
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_ID, AD_BusinessRule_ID);
	}

	@Override
	public int getAD_BusinessRule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_ID);
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
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_Trigger_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_Trigger_ID, AD_BusinessRule_Trigger_ID);
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
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessingTag (final @Nullable java.lang.String ProcessingTag)
	{
		set_Value (COLUMNNAME_ProcessingTag, ProcessingTag);
	}

	@Override
	public java.lang.String getProcessingTag() 
	{
		return get_ValueAsString(COLUMNNAME_ProcessingTag);
	}

	@Override
	public void setSource_Record_ID (final int Source_Record_ID)
	{
		if (Source_Record_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Source_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Source_Record_ID, Source_Record_ID);
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
			set_ValueNoCheck (COLUMNNAME_Source_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Source_Table_ID, Source_Table_ID);
	}

	@Override
	public int getSource_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_Table_ID);
	}
}