// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_BusinessRule_Trigger
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_BusinessRule_Trigger extends org.compiere.model.PO implements I_AD_BusinessRule_Trigger, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1538009605L;

    /** Standard Constructor */
    public X_AD_BusinessRule_Trigger (final Properties ctx, final int AD_BusinessRule_Trigger_ID, @Nullable final String trxName)
    {
      super (ctx, AD_BusinessRule_Trigger_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_BusinessRule_Trigger (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setConditionSQL (final @Nullable java.lang.String ConditionSQL)
	{
		set_Value (COLUMNNAME_ConditionSQL, ConditionSQL);
	}

	@Override
	public java.lang.String getConditionSQL() 
	{
		return get_ValueAsString(COLUMNNAME_ConditionSQL);
	}

	@Override
	public void setOnDelete (final boolean OnDelete)
	{
		set_Value (COLUMNNAME_OnDelete, OnDelete);
	}

	@Override
	public boolean isOnDelete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnDelete);
	}

	@Override
	public void setOnNew (final boolean OnNew)
	{
		set_Value (COLUMNNAME_OnNew, OnNew);
	}

	@Override
	public boolean isOnNew() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnNew);
	}

	@Override
	public void setOnUpdate (final boolean OnUpdate)
	{
		set_Value (COLUMNNAME_OnUpdate, OnUpdate);
	}

	@Override
	public boolean isOnUpdate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnUpdate);
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
	public void setTargetRecordMappingSQL (final java.lang.String TargetRecordMappingSQL)
	{
		set_Value (COLUMNNAME_TargetRecordMappingSQL, TargetRecordMappingSQL);
	}

	@Override
	public java.lang.String getTargetRecordMappingSQL() 
	{
		return get_ValueAsString(COLUMNNAME_TargetRecordMappingSQL);
	}
}