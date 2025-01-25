// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_OrderTax
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_OrderTax extends org.compiere.model.PO implements I_C_OrderTax, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -743694283L;

    /** Standard Constructor */
    public X_C_OrderTax (final Properties ctx, final int C_OrderTax_ID, @Nullable final String trxName)
    {
      super (ctx, C_OrderTax_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OrderTax (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_OrderTax_ID (final int C_OrderTax_ID)
	{
		if (C_OrderTax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderTax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderTax_ID, C_OrderTax_ID);
	}

	@Override
	public int getC_OrderTax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderTax_ID);
	}

	@Override
	public void setC_Tax_ID (final int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public int getC_Tax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
	}

	@Override
	public void setIsDocumentLevel (final boolean IsDocumentLevel)
	{
		set_Value (COLUMNNAME_IsDocumentLevel, IsDocumentLevel);
	}

	@Override
	public boolean isDocumentLevel() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDocumentLevel);
	}

	@Override
	public void setIsPackagingTax (final boolean IsPackagingTax)
	{
		set_Value (COLUMNNAME_IsPackagingTax, IsPackagingTax);
	}

	@Override
	public boolean isPackagingTax() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPackagingTax);
	}

	@Override
	public void setIsTaxIncluded (final boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, IsTaxIncluded);
	}

	@Override
	public boolean isTaxIncluded() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxIncluded);
	}

	@Override
	public void setIsWholeTax (final boolean IsWholeTax)
	{
		set_Value (COLUMNNAME_IsWholeTax, IsWholeTax);
	}

	@Override
	public boolean isWholeTax() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWholeTax);
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
	public void setTaxAmt (final BigDecimal TaxAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxAmt, TaxAmt);
	}

	@Override
	public BigDecimal getTaxAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTaxBaseAmt (final BigDecimal TaxBaseAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	@Override
	public BigDecimal getTaxBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TaxBaseAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}