// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_BusinessRule_Precondition
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_BusinessRule_Precondition extends org.compiere.model.PO implements I_AD_BusinessRule_Precondition, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -411771108L;

    /** Standard Constructor */
    public X_AD_BusinessRule_Precondition (final Properties ctx, final int AD_BusinessRule_Precondition_ID, @Nullable final String trxName)
    {
      super (ctx, AD_BusinessRule_Precondition_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_BusinessRule_Precondition (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_BusinessRule_Precondition_ID (final int AD_BusinessRule_Precondition_ID)
	{
		if (AD_BusinessRule_Precondition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_Precondition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BusinessRule_Precondition_ID, AD_BusinessRule_Precondition_ID);
	}

	@Override
	public int getAD_BusinessRule_Precondition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BusinessRule_Precondition_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getPrecondition_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_Precondition_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setPrecondition_Rule(final org.compiere.model.I_AD_Val_Rule Precondition_Rule)
	{
		set_ValueFromPO(COLUMNNAME_Precondition_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, Precondition_Rule);
	}

	@Override
	public void setPrecondition_Rule_ID (final int Precondition_Rule_ID)
	{
		if (Precondition_Rule_ID < 1) 
			set_Value (COLUMNNAME_Precondition_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_Precondition_Rule_ID, Precondition_Rule_ID);
	}

	@Override
	public int getPrecondition_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Precondition_Rule_ID);
	}

	@Override
	public void setPreconditionSQL (final @Nullable java.lang.String PreconditionSQL)
	{
		set_Value (COLUMNNAME_PreconditionSQL, PreconditionSQL);
	}

	@Override
	public java.lang.String getPreconditionSQL() 
	{
		return get_ValueAsString(COLUMNNAME_PreconditionSQL);
	}

	/** 
	 * PreconditionType AD_Reference_ID=541912
	 * Reference name: PreconditionType
	 */
	public static final int PRECONDITIONTYPE_AD_Reference_ID=541912;
	/** SQL = S */
	public static final String PRECONDITIONTYPE_SQL = "S";
	/** Validation Rule = R */
	public static final String PRECONDITIONTYPE_ValidationRule = "R";
	@Override
	public void setPreconditionType (final java.lang.String PreconditionType)
	{
		set_Value (COLUMNNAME_PreconditionType, PreconditionType);
	}

	@Override
	public java.lang.String getPreconditionType() 
	{
		return get_ValueAsString(COLUMNNAME_PreconditionType);
	}
}