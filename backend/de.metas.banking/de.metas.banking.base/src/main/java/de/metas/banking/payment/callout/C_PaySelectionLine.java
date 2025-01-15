package de.metas.banking.payment.callout;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Callout(I_C_PaySelectionLine.class)
public class C_PaySelectionLine
{
	public static final C_PaySelectionLine instance = new C_PaySelectionLine();
	private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	private C_PaySelectionLine()
	{
	}

	@CalloutMethod(columnNames = I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID)
	public void invoice(final I_C_PaySelectionLine psl)
	{
		if (InvoiceId.ofRepoIdOrNull(psl.getC_Invoice_ID()) != null)
		{
			// NOTE!!! Please keep in sync with de.metas.banking.payment.impl.PaySelectionUpdater.buildSelectSQL

			paySelectionBL.updateFromInvoice(psl);

			final I_C_PaySelection paySelection = getPaySelection(psl);
			final int C_BP_BankAccount_ID = paySelection.getC_BP_BankAccount_ID();

			Timestamp PayDate = paySelection.getPayDate();
			if (PayDate == null)
			{
				PayDate = new Timestamp(System.currentTimeMillis());
			}

			BigDecimal OpenAmt = BigDecimal.ZERO;
			BigDecimal DiscountAmt = BigDecimal.ZERO;
			boolean IsSOTrx = false;
			final String sql = "SELECT currencyConvert(invoiceOpen(i.C_Invoice_ID, 0), i.C_Currency_ID,"
					+ "ba.C_Currency_ID, i.DateInvoiced, i.C_ConversionType_ID, i.AD_Client_ID, i.AD_Org_ID),"
					+ " paymentTermDiscount(i.GrandTotal,i.C_Currency_ID,i.C_PaymentTerm_ID,i.DateInvoiced, ?), i.IsSOTrx "
					+ "FROM C_Invoice_v i, C_BP_BankAccount ba "
					+ "WHERE i.C_Invoice_ID=? AND ba.C_BP_BankAccount_ID=?";	// #1..2
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt.setTimestamp(1, PayDate);
				pstmt.setInt(2, psl.getC_Invoice_ID());
				pstmt.setInt(3, C_BP_BankAccount_ID);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					OpenAmt = rs.getBigDecimal(1);
					DiscountAmt = rs.getBigDecimal(2);
					IsSOTrx = DisplayType.toBoolean(rs.getString(3));
				}
			}
			catch (final SQLException e)
			{
				throw new DBException(e, sql);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}

			psl.setOpenAmt(OpenAmt);
			psl.setPayAmt(OpenAmt.subtract(DiscountAmt));
			psl.setDiscountAmt(DiscountAmt);
			psl.setDifferenceAmt(BigDecimal.ZERO);
			psl.setIsSOTrx(IsSOTrx);
		}
	}

	private I_C_PaySelection getPaySelection(@NonNull final I_C_PaySelectionLine psl)
	{
		return paySelectionBL.getByIdNotNull(PaySelectionId.ofRepoId(psl.getC_PaySelection_ID()));
	}

	@CalloutMethod(columnNames = { I_C_PaySelectionLine.COLUMNNAME_PayAmt })
	public void payAmt(final I_C_PaySelectionLine psl)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(psl.getC_Invoice_ID());
		if (invoiceId != null)
		{
			final BigDecimal payAmt = psl.getPayAmt();

			final I_C_PaySelection paySelection = getPaySelection(psl);
			final Timestamp payDate = paySelection.getPayDate();

			final BigDecimal discountAmt = calculateDiscountAmt(invoiceBL.getById(invoiceId), payAmt, payDate);
			psl.setDiscountAmt(discountAmt);

			setDifferenceAmt(psl);
		}
	}

	private static BigDecimal calculateDiscountAmt(final I_C_Invoice invoice, final BigDecimal payAmt, final Timestamp payDate)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		final int currencyId = invoice.getC_Currency_ID();
		final int paymentTermId = invoice.getC_PaymentTerm_ID();
		final Timestamp dateInvoiced = invoice.getDateInvoiced();
		final BigDecimal discountAmt = DB.getSQLValueBDEx(trxName,
				"SELECT paymentTermDiscount(?, ?, ?, ?, ?)",
				payAmt, currencyId, paymentTermId, dateInvoiced, payDate);
		if (discountAmt == null)
		{
			return BigDecimal.ZERO;
		}

		return discountAmt;
	}

	@CalloutMethod(columnNames = { I_C_PaySelectionLine.COLUMNNAME_DiscountAmt })
	public void discountAmt(final I_C_PaySelectionLine paySelectionLine)
	{
		setDifferenceAmt(paySelectionLine);
	}

	private void setDifferenceAmt(final I_C_PaySelectionLine paySelectionLine)
	{
		final BigDecimal OpenAmt = paySelectionLine.getOpenAmt();
		final BigDecimal PayAmt = paySelectionLine.getPayAmt();
		final BigDecimal DiscountAmt = paySelectionLine.getDiscountAmt();
		final BigDecimal DifferenceAmt = OpenAmt.subtract(PayAmt).subtract(DiscountAmt);

		paySelectionLine.setDifferenceAmt(DifferenceAmt);
	}
}
