// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_InvoiceTax
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_InvoiceTax extends org.compiere.model.PO implements I_C_InvoiceTax, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -871159980L;

    /** Standard Constructor */
    public X_C_InvoiceTax (final Properties ctx, final int C_InvoiceTax_ID, @Nullable final String trxName)
    {
      super (ctx, C_InvoiceTax_ID, trxName);
    }

    /** Load Constructor */
    public X_C_InvoiceTax (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setC_InvoiceTax_ID (final int C_InvoiceTax_ID)
	{
		if (C_InvoiceTax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceTax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceTax_ID, C_InvoiceTax_ID);
	}

	@Override
	public int getC_InvoiceTax_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceTax_ID);
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