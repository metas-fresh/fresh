/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Invoice Tax Model
 *
 * @author Jorg Janke
 * @version $Id: MInvoiceTax.java,v 1.5 2006/10/06 00:42:24 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 */
public class MInvoiceTax extends X_C_InvoiceTax
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5560880305482497098L;

	/**
	 * Get Tax Line for Invoice Line
	 *
	 * @param line invoice line
	 * @param precision currency precision
	 * @param oldTax if true old tax is returned
	 * @param trxName transaction name
	 * @return existing or new tax
	 */
	public static MInvoiceTax get(MInvoiceLine line, int precision, boolean oldTax, String trxName)
	{
		if (line == null || line.getC_Invoice_ID() <= 0)
		{
			return null;
		}

		int C_Tax_ID = line.getC_Tax_ID();
		final boolean isOldTax = oldTax && line.is_ValueChanged(MInvoiceLine.COLUMNNAME_C_Tax_ID);
		if (isOldTax)
		{
			Object old = line.get_ValueOld(MInvoiceLine.COLUMNNAME_C_Tax_ID);
			if (old == null)
			{
				return null;
			}
			C_Tax_ID = (Integer)old;
		}

		if (C_Tax_ID <= 0)
		{
			final AdempiereException e = new AdempiereException("@NotFound@ @C_Tax_ID@ (" + line + ")");
			s_log.warn(e.getLocalizedMessage(), e);
			return null;
		}

		MInvoiceTax retValue = new Query(line.getCtx(), Table_Name, "C_Invoice_ID=? AND C_Tax_ID=?", trxName)
				.setParameters(line.getC_Invoice_ID(), C_Tax_ID)
				.firstOnly();
		if (retValue != null)
		{
			retValue.set_TrxName(trxName);
			retValue.setPrecision(precision);
			s_log.debug("(old=" + oldTax + ") " + retValue);
			return retValue;
		}
		// If the old tax was required and there is no MInvoiceTax for that
		// return null, and not create another MInvoiceTax - teo_sarca [ 1583825 ]
		else
		{
			if (isOldTax)
			{
				return null;
			}
		}

		final boolean taxIncluded = Services.get(IInvoiceBL.class).isTaxIncluded(line);
		
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
		final Tax tax = taxDAO.getTaxByIdOrNull(line.getC_Tax_ID());


		// Create New
		retValue = new MInvoiceTax(line.getCtx(), 0, trxName);
		retValue.set_TrxName(trxName);
		retValue.setClientOrg(line);
		retValue.setC_Invoice_ID(line.getC_Invoice_ID());
		retValue.setC_Tax_ID(tax.getTaxId().getRepoId());
		retValue.setIsWholeTax(tax.isWholeTax());
		retValue.setIsDocumentLevel(tax.isDocumentLevel());
		retValue.setPrecision(precision);
		retValue.setIsTaxIncluded(taxIncluded);
		s_log.debug("(new) {}", retValue);
		return retValue;
	}	// get

	/** Static Logger */
	private static final Logger s_log = LogManager.getLogger(MInvoiceTax.class);

	/**************************************************************************
	 * Persistency Constructor
	 */
	public MInvoiceTax(Properties ctx, int id, String trxName)
	{
		super(ctx, id, trxName);
		if(id <= 0)
		{
			setTaxAmt(BigDecimal.ZERO);
			setTaxBaseAmt(BigDecimal.ZERO);
			setIsTaxIncluded(false);
		}
	}	// MInvoiceTax

	@SuppressWarnings("unused")
	public MInvoiceTax(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Tax */
	private MTax m_tax = null;
	/** Cached Precision */
	private Integer m_precision = null;

	/**
	 * Get Precision
	 *
	 * @return Returns the precision or 2
	 */
	private int getPrecision()
	{
		final Integer precision = this.m_precision;
		return precision != null ? precision : 2;
	}	// getPrecision

	/**
	 * @param precision The precision to set.
	 */
	protected void setPrecision(int precision)
	{
		m_precision = precision;
	}

	/**
	 * Get Tax
	 *
	 * @return tax
	 */
	protected MTax getTax()
	{
		if (m_tax == null)
		{
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		}
		return m_tax;
	}	// getTax

	/**************************************************************************
	 * Calculate/Set Tax Base Amt from Invoice Lines.
	 * If there were no invoice lines found for this tax, this record will be inactivated. In this way, the caller method can know about this and it can decide if this record will be deleted.
	 *
	 * @return true if tax calculated
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean calculateTaxFromLines()
	{
		final ITaxBL taxBL = Services.get(ITaxBL.class);

		BigDecimal taxBaseAmt = BigDecimal.ZERO;
		BigDecimal taxAmt = BigDecimal.ZERO;
		boolean foundInvoiceLines = false;
		//
		final boolean documentLevel = getTax().isDocumentLevel();
		final I_C_Tax tax = getTax();
		//
		boolean havePackingMaterialLines = false;
		boolean haveNonPackingMaterialLines = false;

		final String sql = "SELECT "
				+ I_C_InvoiceLine.COLUMNNAME_LineNetAmt // 1
				+ ", COALESCE( " + I_C_InvoiceLine.COLUMNNAME_TaxAmt + ",0), " // 2
				+ I_C_Invoice.COLUMNNAME_IsSOTrx + ", " // 3
				+ de.metas.adempiere.model.I_C_InvoiceLine.COLUMNNAME_IsPackagingMaterial // 4
				+ " FROM " + I_C_InvoiceLine.Table_Name
				+ " INNER JOIN " + I_C_Invoice.Table_Name
				+ " ON (" + I_C_InvoiceLine.Table_Name + "." + I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID
				+ "=" + I_C_Invoice.Table_Name + "." + I_C_Invoice.COLUMNNAME_C_Invoice_ID + " ) "
				+ " WHERE " + I_C_InvoiceLine.Table_Name + "." + I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID + "=?"
				+ " AND " + I_C_InvoiceLine.Table_Name + "." + I_C_InvoiceLine.COLUMNNAME_C_Tax_ID
				+ " =? AND " + I_C_InvoiceLine.Table_Name + "." + I_C_InvoiceLine.COLUMNNAME_IsActive + " ='Y' ";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_Invoice_ID());
			pstmt.setInt(2, getC_Tax_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				foundInvoiceLines = true;
				// BaseAmt
				BigDecimal baseAmt = rs.getBigDecimal(1);
				taxBaseAmt = taxBaseAmt.add(baseAmt);
				// TaxAmt
				BigDecimal amt = rs.getBigDecimal(2);
				if (amt == null)
				{
					amt = BigDecimal.ZERO;
				}
				boolean isSOTrx = "Y".equals(rs.getString(3));
				//
				// phib [ 1702807 ]: manual tax should never be amended
				// on line level taxes
				if (!documentLevel && amt.signum() != 0 && !isSOTrx)
				{
					;
				}
				else if (documentLevel || baseAmt.signum() == 0)
				{
					amt = BigDecimal.ZERO;
				}
				else
				{
					amt = taxBL.calculateTax(tax, baseAmt, isTaxIncluded(), getPrecision());
				}
				//
				taxAmt = taxAmt.add(amt);

				final boolean lineIsPackingMaterial = DisplayType.toBoolean(rs.getString(4));
				if (lineIsPackingMaterial)
				{
					havePackingMaterialLines = true;
				}
				else
				{
					haveNonPackingMaterialLines = true;
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		// Calculate Tax
		if (documentLevel || taxAmt.signum() == 0)
		{
			taxAmt = taxBL.calculateTax(tax, taxBaseAmt, isTaxIncluded(), getPrecision());
		}
		setTaxAmt(taxAmt);

		// Set Base
		if (isTaxIncluded())
		{
			setTaxBaseAmt(taxBaseAmt.subtract(taxAmt));
		}
		else
		{
			setTaxBaseAmt(taxBaseAmt);
		}

		// Deactivate InvoiceTax if there were no invoice lines matching our C_Tax_ID
		// Active it otherwise
		setIsActive(foundInvoiceLines);

		setIsPackagingTax(checkIsPackagingMaterialTax(havePackingMaterialLines, haveNonPackingMaterialLines));

		return true;
	}	// calculateTaxFromLines

	@Override
	public String toString()
	{
		return "MInvoiceTax[" + "C_Invoice_ID=" + getC_Invoice_ID()
				+ ",C_Tax_ID=" + getC_Tax_ID()
				+ ", Base=" + getTaxBaseAmt() + ",Tax=" + getTaxAmt()
				+ "]";
	}

	/**
	 * @return true if there are no non packing material lines with the same tax as the packing material lines, false otherwise
	 */
	private boolean checkIsPackagingMaterialTax(final boolean havePackingMaterialLines, final boolean haveNonPackingMaterialLines)
	{
		if (havePackingMaterialLines)
		{
			if (haveNonPackingMaterialLines)
			{
				log.warn("Found lines with packaging materials and lines without packing materials for C_Invoice_ID={} and C_Tax_ID={}. Considering {} not a packing material tax.", getC_Invoice_ID(), getC_Tax_ID(), this);
				return false;
			}

			return true;
		}
		else
		{
			return false;
		}
	}

}	// MInvoiceTax
