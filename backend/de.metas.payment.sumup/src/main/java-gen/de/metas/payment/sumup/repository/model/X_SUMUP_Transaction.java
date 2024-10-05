// Generated Model - DO NOT CHANGE
package de.metas.payment.sumup.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for SUMUP_Transaction
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SUMUP_Transaction extends org.compiere.model.PO implements I_SUMUP_Transaction, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1375477180L;

    /** Standard Constructor */
    public X_SUMUP_Transaction (final Properties ctx, final int SUMUP_Transaction_ID, @Nullable final String trxName)
    {
      super (ctx, SUMUP_Transaction_ID, trxName);
    }

    /** Load Constructor */
    public X_SUMUP_Transaction (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount (final BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_POS_Order_ID (final int C_POS_Order_ID)
	{
		if (C_POS_Order_ID < 1) 
			set_Value (COLUMNNAME_C_POS_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Order_ID, C_POS_Order_ID);
	}

	@Override
	public int getC_POS_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Order_ID);
	}

	@Override
	public void setC_POS_Payment_ID (final int C_POS_Payment_ID)
	{
		if (C_POS_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_POS_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Payment_ID, C_POS_Payment_ID);
	}

	@Override
	public int getC_POS_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Payment_ID);
	}

	@Override
	public void setCurrencyCode (final java.lang.String CurrencyCode)
	{
		set_Value (COLUMNNAME_CurrencyCode, CurrencyCode);
	}

	@Override
	public java.lang.String getCurrencyCode() 
	{
		return get_ValueAsString(COLUMNNAME_CurrencyCode);
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
	public void setJsonResponse (final @Nullable java.lang.String JsonResponse)
	{
		set_Value (COLUMNNAME_JsonResponse, JsonResponse);
	}

	@Override
	public java.lang.String getJsonResponse() 
	{
		return get_ValueAsString(COLUMNNAME_JsonResponse);
	}

	@Override
	public void setRefundAmt (final BigDecimal RefundAmt)
	{
		set_Value (COLUMNNAME_RefundAmt, RefundAmt);
	}

	@Override
	public BigDecimal getRefundAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_RefundAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

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
	public void setSUMUP_ClientTransactionId (final java.lang.String SUMUP_ClientTransactionId)
	{
		set_ValueNoCheck (COLUMNNAME_SUMUP_ClientTransactionId, SUMUP_ClientTransactionId);
	}

	@Override
	public java.lang.String getSUMUP_ClientTransactionId() 
	{
		return get_ValueAsString(COLUMNNAME_SUMUP_ClientTransactionId);
	}

	@Override
	public de.metas.payment.sumup.repository.model.I_SUMUP_Config getSUMUP_Config()
	{
		return get_ValueAsPO(COLUMNNAME_SUMUP_Config_ID, de.metas.payment.sumup.repository.model.I_SUMUP_Config.class);
	}

	@Override
	public void setSUMUP_Config(final de.metas.payment.sumup.repository.model.I_SUMUP_Config SUMUP_Config)
	{
		set_ValueFromPO(COLUMNNAME_SUMUP_Config_ID, de.metas.payment.sumup.repository.model.I_SUMUP_Config.class, SUMUP_Config);
	}

	@Override
	public void setSUMUP_Config_ID (final int SUMUP_Config_ID)
	{
		if (SUMUP_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SUMUP_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SUMUP_Config_ID, SUMUP_Config_ID);
	}

	@Override
	public int getSUMUP_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_Config_ID);
	}

	@Override
	public void setSUMUP_merchant_code (final java.lang.String SUMUP_merchant_code)
	{
		set_Value (COLUMNNAME_SUMUP_merchant_code, SUMUP_merchant_code);
	}

	@Override
	public java.lang.String getSUMUP_merchant_code() 
	{
		return get_ValueAsString(COLUMNNAME_SUMUP_merchant_code);
	}

	@Override
	public void setSUMUP_Transaction_ID (final int SUMUP_Transaction_ID)
	{
		if (SUMUP_Transaction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SUMUP_Transaction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SUMUP_Transaction_ID, SUMUP_Transaction_ID);
	}

	@Override
	public int getSUMUP_Transaction_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_Transaction_ID);
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
}