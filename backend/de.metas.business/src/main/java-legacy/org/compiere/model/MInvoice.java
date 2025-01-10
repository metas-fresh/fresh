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

import com.google.common.base.Joiner;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatsBL;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.Msg;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IMatchInvBL;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.IMatchPOBL;
import de.metas.order.IMatchPODAO;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.report.DocumentReportService;
import de.metas.report.ReportResultData;
import de.metas.report.StandardDocumentReportType;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxUtils;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.LegacyAdapters;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.util.CustomColNames.C_Invoice_BPARTNERADDRESS;
import static org.adempiere.util.CustomColNames.C_Invoice_DESCRIPTION_BOTTOM;
import static org.adempiere.util.CustomColNames.C_Invoice_INCOTERM;
import static org.adempiere.util.CustomColNames.C_Invoice_INCOTERMLOCATION;
import static org.adempiere.util.CustomColNames.C_Invoice_ISUSE_BPARTNER_ADDRESS;

/**
 * Invoice Model.
 * Please do not set DocStatus and C_DocType_ID directly.
 * They are set in the process() method.
 * Use DocAction and C_DocTypeTarget_ID instead.
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * @version $Id: MInvoice.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 * @see [ http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962 ]
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see [ http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962 ]
 * Modifications: Added RMA functionality (Ashley Ramdass)
 */
@SuppressWarnings("serial")
public class MInvoice extends X_C_Invoice implements IDocument
{
	/**
	 * Get Payments Of BPartner
	 *
	 * @param ctx           context
	 * @param C_BPartner_ID id
	 * @param trxName       transaction
	 * @return array
	 */
	public static MInvoice[] getOfBPartner(final Properties ctx, final int C_BPartner_ID, final String trxName)
	{
		final List<MInvoice> list = new Query(ctx, Table_Name, COLUMNNAME_C_BPartner_ID + "=?", trxName)
				.setParameters(new Object[] { C_BPartner_ID })
				.list(MInvoice.class);
		return list.toArray(new MInvoice[list.size()]);
	}    // getOfBPartner

	/**
	 * Create new Invoice by copying
	 *
	 * @param from               invoice
	 * @param dateDoc            date of the document date
	 * @param dateAcct           original account date
	 * @param C_DocTypeTarget_ID target doc type
	 * @param isSOTrx            sales order
	 * @param counter            create counter links
	 * @param trxName            trx
	 * @param setOrder           set Order links
	 * @return Invoice
	 * @deprecated please use {@link IInvoiceBL#copyFrom(I_C_Invoice, Timestamp, int, boolean, boolean, boolean, boolean, boolean)} instead.
	 */
	@Deprecated
	public static MInvoice copyFrom(final MInvoice from, final Timestamp dateDoc, final Timestamp dateAcct,
			final int C_DocTypeTarget_ID, final boolean isSOTrx, final boolean counter,
			final String trxName, final boolean setOrder)
	{
		// ts: 04054: moving copyFrom business logic to the implementors of IInvoiceBL
		// NOTE: the old crap is deleted from here.... search it in SCM history
		final I_C_Invoice to = Services.get(IInvoiceBL.class).copyFrom(from, dateDoc, C_DocTypeTarget_ID, isSOTrx, counter, setOrder,
																	   false,  // setInvoiceRef == false
																	   true); // copyLines == true

		// Make sure DateAcct is set (08356)
		to.setDateAcct(dateAcct);
		InterfaceWrapperHelper.save(to);

		return LegacyAdapters.convertToPO(to);
	}

	/**
	 * Get MInvoice from Cache
	 */
	@Deprecated
	public static MInvoice get(final Properties ctx, final int C_Invoice_ID)
	{
		final Integer key = new Integer(C_Invoice_ID);
		MInvoice retValue = s_cache.get(key);
		if (retValue != null)
		{
			return retValue;
		}
		retValue = new MInvoice(ctx, C_Invoice_ID, null);
		if (retValue.get_ID() != 0)
		{
			s_cache.put(key, retValue);
		}
		return retValue;
	} // get

	private static final Logger s_log = LogManager.getLogger(MInvoice.class);
	private static CCache<Integer, MInvoice> s_cache = new CCache<>("C_Invoice", 20, 2);    // 2 minutes

	private MInvoiceLine[] m_lines;
	private MInvoiceTax[] m_taxes;
	private boolean m_justPrepared = false;
	private boolean m_reversal = false;

	public MInvoice(final Properties ctx, final int C_Invoice_ID, final String trxName)
	{
		super(ctx, C_Invoice_ID, trxName);
		if (is_new())
		{
			setDocStatus(DocStatus.Drafted.getCode());
			setDocAction(DOCACTION_Complete);
			//
			// FRESH-488: Get the default payment rule form the system configuration
			setPaymentRule(Services.get(IInvoiceBL.class).getDefaultPaymentRule().getCode());

			setDateInvoiced(new Timestamp(System.currentTimeMillis()));
			setDateAcct(new Timestamp(System.currentTimeMillis()));
			//
			setChargeAmt(BigDecimal.ZERO);
			setTotalLines(BigDecimal.ZERO);
			setGrandTotal(BigDecimal.ZERO);
			//
			setIsSOTrx(true);
			setIsTaxIncluded(false);
			setIsApproved(false);
			setIsDiscountPrinted(false);
			setIsPaid(false);
			setSendEMail(false);
			setIsPrinted(false);
			setIsTransferred(false);
			setIsSelfService(false);
			setIsPayScheduleValid(false);
			setIsInDispute(false);
			setPosted(false);
			super.setProcessed(false);
			setProcessing(false);
		}
	}    // MInvoice

	public MInvoice(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    // MInvoice

	/**
	 * Create Invoice from Order
	 *
	 * @param order              order
	 * @param C_DocTypeTarget_ID target document type
	 * @param invoiceDate        date or null
	 */
	@Deprecated
	public MInvoice(final MOrder order, final int C_DocTypeTarget_ID, final Timestamp invoiceDate)
	{
		this(order.getCtx(), 0, order.get_TrxName());
		setClientOrg(order);
		setOrder(order);    // set base settings
		//
		int docTypeId = C_DocTypeTarget_ID;
		if (docTypeId <= 0)
		{
			final MDocType odt = MDocType.get(order.getCtx(), order.getC_DocType_ID());
			if (odt != null)
			{
				docTypeId = odt.getC_DocTypeInvoice_ID();
				if (docTypeId <= 0)
				{
					throw new AdempiereException("@NotFound@ @C_DocTypeInvoice_ID@ - @C_DocType_ID@:" + odt.get_Translation(MDocType.COLUMNNAME_Name));
				}
			}
		}
		Services.get(IInvoiceBL.class).setDocTypeTargetIdAndUpdateDescription(this, docTypeId);
		if (invoiceDate != null)
		{
			setDateInvoiced(invoiceDate);
		}
		setDateAcct(getDateInvoiced());
		//
		setSalesRep_ID(order.getSalesRep_ID());
		//
		InvoiceDocumentLocationAdapterFactory
				.locationAdapter(this)
				.setFromBillLocation(order);
	}    // MInvoice

	/**
	 * Create Invoice from Shipment
	 *
	 * @param ship        shipment
	 * @param invoiceDate date or null
	 */
	public MInvoice(final MInOut ship, final Timestamp invoiceDate)
	{
		this(ship.getCtx(), 0, ship.get_TrxName());
		setClientOrg(ship);
		setShipment(ship);    // set base settings
		//
		Services.get(IInvoiceBL.class).setDocTypeTargetIdIfNotSet(this);
		if (invoiceDate != null)
		{
			setDateInvoiced(invoiceDate);
		}
		setDateAcct(getDateInvoiced());
		//
		setSalesRep_ID(ship.getSalesRep_ID());
		setAD_User_ID(ship.getAD_User_ID()); // metas

		setEMail((ship.getEMail()));

		setAD_InputDataSource_ID(ship.getAD_InputDataSource_ID());

		// metas: additional fields
		final IPOService poService = Services.get(IPOService.class);
		poService.copyValue(ship, this, I_C_Order.COLUMNNAME_C_Incoterms_ID);
		poService.copyValue(ship, this, C_Invoice_INCOTERMLOCATION);
		ship.setDescriptionBottom(getDescriptionBottom());
		poService.copyValue(ship, this, C_Invoice_ISUSE_BPARTNER_ADDRESS);
		poService.copyValue(ship, this, C_Invoice_BPARTNERADDRESS);

		InterfaceWrapperHelper.setDynAttribute(this, I_M_InOut.Table_Name, ship); // task 07286
		// metas end
	}    // MInvoice

	@Override
	public void setClientOrg(final int AD_Client_ID, final int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}    // setClientOrg

	/**
	 * Set Business Partner Defaults & Details
	 *
	 * @param bp business partner
	 */
	public void setBPartner(final I_C_BPartner bp)
	{
		if (bp == null)
		{
			return;
		}

		setC_BPartner_ID(bp.getC_BPartner_ID());

		Services.get(IInvoiceBL.class).updateFromBPartner(this);
	}    // setBPartner

	/**
	 * Set Order References
	 *
	 * @param order order
	 */
	public void setOrder(final MOrder order)
	{
		Services.get(IInvoiceBL.class).setFromOrder(this, order);
	}    // setOrder

	/**
	 * Set Shipment References
	 *
	 * @param ship shipment
	 */
	public void setShipment(final MInOut ship)
	{
		if (ship == null)
		{
			return;
		}

		setIsSOTrx(ship.isSOTrx());
		//
		final I_C_BPartner bp = Services.get(IBPartnerDAO.class).getById(ship.getC_BPartner_ID());
		setBPartner(bp);
		//
		setAD_User_ID(ship.getAD_User_ID());
		//
		setSendEMail(ship.isSendEMail());
		//
		setPOReference(ship.getPOReference());
		setDescription(ship.getDescription());
		setDateOrdered(ship.getDateOrdered());
		//
		setAD_OrgTrx_ID(ship.getAD_OrgTrx_ID());
		setC_Project_ID(ship.getC_Project_ID());
		setC_Campaign_ID(ship.getC_Campaign_ID());
		setC_Activity_ID(ship.getC_Activity_ID());
		setUser1_ID(ship.getUser1_ID());
		setUser2_ID(ship.getUser2_ID());

		setEMail(ship.getEMail());

		setAD_InputDataSource_ID(ship.getAD_InputDataSource_ID());

		// metas
		final IPOService poService = Services.get(IPOService.class);
		poService.copyValue(ship, this, I_M_InOut.COLUMNNAME_C_Incoterms_ID);
		poService.copyValue(ship, this, I_M_InOut.COLUMNNAME_IncotermLocation);
		poService.copyValue(ship, this, I_M_InOut.COLUMNNAME_DescriptionBottom);

		poService.copyValue(ship, this, C_Invoice_ISUSE_BPARTNER_ADDRESS);
		poService.copyValue(ship, this, C_Invoice_BPARTNERADDRESS);

		// metas end

		//
		if (ship.getC_Order_ID() > 0)
		{
			setC_Order_ID(ship.getC_Order_ID());
			final MOrder order = new MOrder(getCtx(), ship.getC_Order_ID(), get_TrxName());
			setIsDiscountPrinted(order.isDiscountPrinted());
			setM_PriceList_ID(order.getM_PriceList_ID());
			setIsTaxIncluded(order.isTaxIncluded());
			setC_Currency_ID(order.getC_Currency_ID());
			setC_ConversionType_ID(order.getC_ConversionType_ID());
			setPaymentRule(order.getPaymentRule());
			setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
			setC_Incoterms_ID(order.getC_Incoterms_ID());
			setIncotermLocation(order.getIncotermLocation());

			final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepo = SpringContextHolder.instance.getBean(OrderEmailPropagationSysConfigRepository.class);
			if(orderEmailPropagationSysConfigRepo.isPropagateToCInvoice(ClientAndOrgId.ofClientAndOrg(getAD_Client_ID(), getAD_Org_ID())))
			{
				setEMail(order.getEMail());
			}
			//
			final I_C_DocType dt = MDocType.get(getCtx(), order.getC_DocType_ID());
			if (dt.getC_DocTypeInvoice_ID() != 0)
			{
				Services.get(IInvoiceBL.class).setDocTypeTargetIdAndUpdateDescription(this, dt.getC_DocTypeInvoice_ID());
			}
			// Overwrite Invoice BPartner/Address/Contact
			InvoiceDocumentLocationAdapterFactory
					.locationAdapter(this)
					.setFromBillLocation(order);
			//
		}
		// Check if Shipment/Receipt is based on RMA
		if (ship.getM_RMA_ID() > 0)
		{

			final MRMA rma = new MRMA(getCtx(), ship.getM_RMA_ID(), get_TrxName());
			final MOrder rmaOrder = rma.getOriginalOrder();
			setM_RMA_ID(ship.getM_RMA_ID());
			setIsSOTrx(rma.isSOTrx());
			setM_PriceList_ID(rmaOrder.getM_PriceList_ID());
			setIsTaxIncluded(rmaOrder.isTaxIncluded());
			setC_Currency_ID(rmaOrder.getC_Currency_ID());
			setC_ConversionType_ID(rmaOrder.getC_ConversionType_ID());
			setPaymentRule(rmaOrder.getPaymentRule());
			setC_PaymentTerm_ID(rmaOrder.getC_PaymentTerm_ID());
			setEMail(rmaOrder.getEMail());

			// Retrieves the invoice DocType
			final I_C_DocType dt = MDocType.get(getCtx(), rma.getC_DocType_ID());
			if (dt.getC_DocTypeInvoice_ID() > 0)
			{
				Services.get(IInvoiceBL.class).setDocTypeTargetIdAndUpdateDescription(this, dt.getC_DocTypeInvoice_ID());
			}
			setC_BPartner_Location_ID(rmaOrder.getBill_Location_ID());
		}

	}    // setShipment

	/**
	 * Get Grand Total
	 *
	 * @param creditMemoAdjusted adjusted for CM (negative)
	 * @return grand total
	 */
	public BigDecimal getGrandTotal(final boolean creditMemoAdjusted)
	{
		if (!creditMemoAdjusted)
		{
			return super.getGrandTotal();
		}
		//
		final BigDecimal amt = getGrandTotal();
		if (isCreditMemo())
		{
			return amt.negate();
		}
		return amt;
	}    // getGrandTotal

	/**
	 * Get Invoice Lines of Invoice
	 *
	 * @param whereClause starting with AND
	 * @return lines
	 */
	private MInvoiceLine[] getLines(final String whereClause)
	{
		String whereClauseFinal = "C_Invoice_ID=? ";
		if (whereClause != null)
		{
			whereClauseFinal += whereClause;
		}
		final List<MInvoiceLine> list = new Query(getCtx(), MInvoiceLine.Table_Name, whereClauseFinal, get_TrxName())
				.setParameters(new Object[] { getC_Invoice_ID() })
				.setOrderBy(MInvoiceLine.COLUMNNAME_Line)
				.list(MInvoiceLine.class);
		// optimization: link the C_Invoice
		for (final I_C_InvoiceLine invoiceLine : list)
		{
			invoiceLine.setC_Invoice(this);
		}

		return list.toArray(new MInvoiceLine[list.size()]);
	}    // getLines

	/**
	 * Get Invoice Lines
	 *
	 * @param requery
	 * @return lines
	 */
	public MInvoiceLine[] getLines(final boolean requery)
	{
		if (m_lines == null || m_lines.length == 0 || requery)
		{
			m_lines = getLines(null);
		}
		set_TrxName(m_lines, get_TrxName());
		return m_lines;
	}    // getLines

	/**
	 * Get Lines of Invoice
	 *
	 * @return lines
	 */
	public MInvoiceLine[] getLines()
	{
		return getLines(false);
	}    // getLines

	/**
	 * Renumber Lines
	 *
	 * @param step start and step
	 */
	public void renumberLines(final int step)
	{
		int number = step;
		final MInvoiceLine[] lines = getLines(false);
		for (final MInvoiceLine line : lines)
		{
			line.setLine(number);
			line.saveEx();
			number += step;
		}
		m_lines = null;
	}    // renumberLines

	/**
	 * Copy Lines From other Invoice.
	 *
	 * @param otherInvoice invoice
	 * @param counter      create counter links
	 * @param setOrder     set order links
	 * @return number of lines copied
	 * @deprecated pls use {@link IInvoiceBL#copyLinesFrom(I_C_Invoice, I_C_Invoice, boolean, boolean, boolean)}
	 */
	@Deprecated
	public int copyLinesFrom(final MInvoice otherInvoice, final boolean counter, final boolean setOrder)
	{
		// ts: 04054: moving copyLinesFrom business logic to the implementors of IInvoiceBL
		return Services.get(IInvoiceBL.class).copyLinesFrom(otherInvoice, this, counter, setOrder,
															false); // setInvoiceRef == false
	}    // copyLinesFrom

	private void setReversal(final boolean reversal)
	{
		m_reversal = reversal;
	}

	private boolean isReversal()
	{
		return m_reversal;
	}

	public MInvoiceTax[] getTaxes(final boolean requery)
	{
		if (m_taxes != null && !requery)
		{
			return m_taxes;
		}

		final String whereClause = MInvoiceTax.COLUMNNAME_C_Invoice_ID + "=?";
		final List<MInvoiceTax> list = new Query(getCtx(), MInvoiceTax.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { get_ID() })
				.list(MInvoiceTax.class);
		m_taxes = list.toArray(new MInvoiceTax[list.size()]);
		return m_taxes;
	}    // getTaxes

	private void addDescription(final String description)
	{
		final String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}

	/**
	 * @deprecated Please use {@link IInvoiceBL#isCreditMemo(I_C_Invoice)}
	 */
	@Deprecated
	public boolean isCreditMemo()
	{
		return Services.get(IInvoiceBL.class).isCreditMemo(this);
	}

	/**
	 * Set Processed.
	 * Propergate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
		{
			return;
		}
		final String set = "SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE C_Invoice_ID=" + getC_Invoice_ID();
		final int noLine = DB.executeUpdate("UPDATE C_InvoiceLine " + set, get_TrxName());
		final int noTax = DB.executeUpdate("UPDATE C_InvoiceTax " + set, get_TrxName());
		m_lines = null;
		m_taxes = null;
		log.debug(processed + " - Lines=" + noLine + ", Tax=" + noTax);
	}    // setProcessed

	/**
	 * Validate Invoice Pay Schedule
	 *
	 * @return pay schedule is valid
	 */
	public static boolean validatePaySchedule(@NonNull final I_C_Invoice invoiceRecord)
	{
		final MInvoicePaySchedule[] schedule = MInvoicePaySchedule.getInvoicePaySchedule(
				InterfaceWrapperHelper.getCtx(invoiceRecord),
				invoiceRecord.getC_Invoice_ID(), 0,
				getTrxName(invoiceRecord));

		s_log.debug("#" + schedule.length);
		if (schedule.length == 0)
		{
			invoiceRecord.setIsPayScheduleValid(false);
			return false;
		}
		// Add up due amounts
		BigDecimal total = BigDecimal.ZERO;
		for (final MInvoicePaySchedule element : schedule)
		{
			element.setParent(invoiceRecord);
			final BigDecimal due = element.getDueAmt();
			if (due != null)
			{
				total = total.add(due);
			}
		}
		final boolean valid = invoiceRecord.getGrandTotal().compareTo(total) == 0;
		invoiceRecord.setIsPayScheduleValid(valid);

		// Update Schedule Lines
		for (final MInvoicePaySchedule element : schedule)
		{
			if (element.isValid() != valid)
			{
				element.setIsValid(valid);
				element.saveEx(getTrxName(invoiceRecord));
			}
		}
		return valid;
	}    // validatePaySchedule

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// No Partner Info - set Template
		if (getC_BPartner_ID() <= 0)
		{
			throw new FillMandatoryException(I_C_Invoice.COLUMNNAME_C_BPartner_ID);
		}
		if (getC_BPartner_Location_ID() <= 0)
		{
			final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(getC_BPartner_ID());
			setBPartner(bpartner);
		}

		// Price List
		if (getM_PriceList_ID() <= 0)
		{
			throw new FillMandatoryException(I_C_Invoice.COLUMNNAME_M_PriceList_ID);
		}

		// Currency
		if (getC_Currency_ID() <= 0)
		{
			final String sql = "SELECT C_Currency_ID FROM M_PriceList WHERE M_PriceList_ID=?";
			final int ii = DB.getSQLValue(null, sql, getM_PriceList_ID());
			if (ii != 0)
			{
				setC_Currency_ID(ii);
			}
			else
			{
				setC_Currency_ID(Env.getContextAsInt(getCtx(), "#C_Currency_ID"));
			}
		}

		// Default Sales Rep
		// NOTE: we shall not set the SalesRep from context if is not set.
		// This is not a mandatory field, so leave it like it is.

		// Document Type
		if (getC_DocType_ID() <= 0)
		{
			setC_DocType_ID(0);    // make sure it's set to 0=New
		}
		Services.get(IInvoiceBL.class).setDocTypeTargetIdIfNotSet(this);

		// Payment Term
		if (getC_PaymentTerm_ID() <= 0)
		{
			int paymentTermId = Env.getContextAsInt(getCtx(), "#C_PaymentTerm_ID");
			if (paymentTermId != 0)
			{
				setC_PaymentTerm_ID(paymentTermId);
			}
			else
			{
				final String sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE AD_Client_ID=? AND IsDefault='Y'";
				paymentTermId = DB.getSQLValue(null, sql, getAD_Client_ID());
				if (paymentTermId != 0)
				{
					setC_PaymentTerm_ID(paymentTermId);
				}
			}
		}

		if (getC_Incoterms_ID() <= 0)
		{
			setIncotermLocation("");
		}

		return true;
	}    // beforeSave

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MInvoice[")
				.append(get_ID()).append("-").append(getDocumentNo())
				.append(",GrandTotal=").append(getGrandTotal());
		if (m_lines != null)
		{
			sb.append(" (#").append(m_lines.length).append(")");
		}
		sb.append("]");
		return sb.toString();
	}    // toString

	@Override
	public String getDocumentInfo()
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(getC_DocType_ID());
		final I_C_DocType dt = docTypeId != null
				? Services.get(IDocTypeDAO.class).getById(docTypeId)
				: null;
		final String docTypeName = dt != null ? dt.getName() : null;
		return Joiner.on(" ").skipNulls().join(docTypeName, getDocumentNo());
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success || newRecord)
		{
			return success;
		}

		if (is_ValueChanged("AD_Org_ID"))
		{
			final String sql = "UPDATE C_InvoiceLine ol"
					+ " SET AD_Org_ID ="
					+ "(SELECT AD_Org_ID"
					+ " FROM C_Invoice o WHERE ol.C_Invoice_ID=o.C_Invoice_ID) "
					+ "WHERE C_Invoice_ID=" + getC_Invoice_ID();
			final int no = DB.executeUpdate(sql, get_TrxName());
			log.debug("Lines -> #" + no);
		}
		return true;
	}    // afterSave

	/**
	 * Set Price List (and Currency) when valid
	 *
	 * @param M_PriceList_ID price list
	 */
	@Override
	public void setM_PriceList_ID(final int M_PriceList_ID)
	{
		final I_M_PriceList pl = Services.get(IPriceListDAO.class).getById(M_PriceList_ID);
		if (pl != null)
		{
			setC_Currency_ID(pl.getC_Currency_ID());
			super.setM_PriceList_ID(M_PriceList_ID);
		}
	}    // setM_PriceList_ID

	/**
	 * Test Allocation (and set paid flag)
	 *
	 * @return true if updated
	 */
	@Deprecated
	public boolean testAllocation()
	{
		final boolean ignoreProcessed = false;
		return Services.get(IInvoiceBL.class).testAllocation(this, ignoreProcessed);
	}    // testAllocation

	@Override
	public File createPDF()
	{
		final DocumentReportService documentReportService = SpringContextHolder.instance.getBean(DocumentReportService.class);
		final ReportResultData report = documentReportService.createStandardDocumentReportData(getCtx(), StandardDocumentReportType.INVOICE, getC_Invoice_ID());
		return report.writeToTemporaryFile(get_TableName() + get_ID());
	}    // getPDF

	@Override
	public boolean processIt(final String processAction)
	{
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}    // process

	@Override
	public boolean unlockIt()
	{
		setProcessing(false);
		return true;
	}    // unlockIt

	/**
	 * Invalidate Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		setDocAction(DOCACTION_Prepare);
		return true;
	}    // invalidateIt

	@Override
	public String prepareIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocTypeTarget_ID(), getAD_Org_ID());

		// Lines
		final MInvoiceLine[] lines = getLines(true);
		if (lines.length == 0)
		{
			throw new AdempiereException("@NoLines@");
		}

		// No Cash Book
		final PaymentRule paymentRule = PaymentRule.ofCode(getPaymentRule());
		if (paymentRule.isCash()
				&& MCashBook.get(getCtx(), getAD_Org_ID(), getC_Currency_ID()) == null)
		{
			throw new AdempiereException("@NoCashBook@");
		}

		// Convert/Check DocType
		if (getC_DocType_ID() != getC_DocTypeTarget_ID())
		{
			setC_DocType_ID(getC_DocTypeTarget_ID());
		}
		if (getC_DocType_ID() <= 0)
		{
			throw new AdempiereException("No Document Type");
		}

		// explodeBOM(); // task 09030: we don't really want to explode the BOM, least of all this uncontrolled way after invoice-candidates-way.

		if (!calculateTaxTotal())    // setTotals
		{
			throw new AdempiereException("Error calculating Tax");
		}

		createPaySchedule();

		// Credit Status
		if (isSOTrx() && !isReversal())
		{
			// task FRESH-152
			final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
			final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(getC_BPartner_ID());
			if (!X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck.equals(stats.getSOCreditStatus()))
			{
				final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
				final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(getC_BPartner_ID(), getDateInvoiced());

				if (Services.get(IBPartnerStatsBL.class).isCreditStopSales(stats, getGrandTotal(true), getDateInvoiced()))
				{
					throw new AdempiereException("@BPartnerCreditStop@ - @SO_CreditUsed@="
														 + stats.getSOCreditUsed()
														 + ", @SO_CreditLimit@=" + creditLimit);
				}
			}
		}

		// Landed Costs
		if (!isSOTrx())
		{
			for (final MInvoiceLine line : lines)
			{
				final String error = line.allocateLandedCosts();
				if (error != null && error.length() > 0)
				{
					throw new AdempiereException(error);
				}
			}
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);

		// Add up Amounts
		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		return IDocument.STATUS_InProgress;
	}    // prepareIt

	/**
	 * @return true if calculated
	 */
	public boolean calculateTaxTotal()
	{
		final String trxName = get_TrxName();

		// Delete Taxes
		DB.executeUpdateEx("DELETE FROM C_InvoiceTax WHERE C_Invoice_ID=" + getC_Invoice_ID(), trxName);
		m_taxes = null;

		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final CurrencyPrecision taxPrecision = invoiceBL.getTaxPrecision(this);

		// Lines
		BigDecimal totalLines = BigDecimal.ZERO;
		final Set<Integer> taxIds = new HashSet<>();
		final MInvoiceLine[] lines = getLines(false);
		for (final MInvoiceLine line : lines)
		{
			totalLines = totalLines.add(line.getLineNetAmt());

			final int taxId = line.getC_Tax_ID();
			if (taxIds.contains(taxId))
			{
				continue;
			}

			final MInvoiceTax iTax = MInvoiceTax.get(line, taxPrecision.toInt(), false, trxName); // current Tax
			if (iTax == null)
			{
				continue;
			}

			iTax.setIsTaxIncluded(invoiceBL.isTaxIncluded(line));
			if (!iTax.calculateTaxFromLines())
			{
				return false;
			}
			Check.assume(iTax.isActive(), "InvoiceTax shall be active: {}", iTax);
			InterfaceWrapperHelper.save(iTax);
			taxIds.add(taxId);
		}

		// Taxes
		BigDecimal grandTotal = totalLines;
		final MInvoiceTax[] taxes = getTaxes(true);
		for (final MInvoiceTax iTax : taxes)
		{
			final MTax tax = iTax.getTax();
			if (tax.isSummary())
			{
				final MTax[] cTaxes = tax.getChildTaxes(false);    // Multiple taxes
				for (final MTax cTax : cTaxes)
				{
					final boolean taxIncluded = Services.get(IInvoiceBL.class).isTaxIncluded(this, TaxUtils.from(cTax));
					final BigDecimal taxBaseAmt = iTax.getTaxBaseAmt();
					final BigDecimal taxAmt = Services.get(ITaxBL.class).calculateTax(cTax, taxBaseAmt, taxIncluded, taxPrecision.toInt());
					//
					final MInvoiceTax newITax = new MInvoiceTax(getCtx(), 0, trxName);
					newITax.setClientOrg(this);
					newITax.setC_Invoice(this);
					newITax.setC_Tax_ID(cTax.getC_Tax_ID());
					newITax.setPrecision(taxPrecision.toInt());
					newITax.setIsTaxIncluded(taxIncluded);
					newITax.setIsDocumentLevel(cTax.isDocumentLevel());
					newITax.setTaxBaseAmt(taxBaseAmt);
					newITax.setTaxAmt(taxAmt);
					newITax.saveEx(trxName);
					//
					if (!taxIncluded)
					{
						grandTotal = grandTotal.add(taxAmt);
					}
				}

				iTax.setProcessed(false);
				InterfaceWrapperHelper.delete(iTax);
			}
			else
			{
				if (!iTax.isTaxIncluded())
				{
					grandTotal = grandTotal.add(iTax.getTaxAmt());
				}
			}
		}
		//
		setTotalLines(totalLines);
		setGrandTotal(grandTotal);
		return true;
	}    // calculateTaxTotal

	/**
	 * (Re) Create Pay Schedule
	 *
	 * @return true if valid schedule
	 */
	private boolean createPaySchedule()
	{
		if (getC_PaymentTerm_ID() <= 0)
		{
			return false;
		}
		final MPaymentTerm pt = new MPaymentTerm(getCtx(), getC_PaymentTerm_ID(), null);
		return pt.apply(this);        // calls validate pay schedule
	}    // createPaySchedule

	@Override
	public boolean approveIt()
	{
		setIsApproved(true);
		return true;
	}    // approveIt

	@Override
	public boolean rejectIt()
	{
		setIsApproved(false);
		return true;
	}    // rejectIt

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			final String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}

		// POS supports multiple payments
		boolean fromPOS = false;
		if (getC_Order_ID() > 0)
		{
			fromPOS = getC_Order().getC_POS_ID() > 0;
		}

		// Create Cash
		final PaymentRule paymentRule = PaymentRule.ofCode(getPaymentRule());
		if (paymentRule.isCash() && !fromPOS)
		{
			// Modifications for POSterita
			//
			// MCash cash = MCash.get (getCtx(), getAD_Org_ID(),
			// getDateInvoiced(), getC_Currency_ID(), get_TrxName());

			MCash cash;

			final int posId = Env.getContextAsInt(getCtx(), Env.POS_ID);

			if (posId != 0)
			{
				final MPOS pos = new MPOS(getCtx(), posId, get_TrxName());
				final int cashBookId = pos.getC_CashBook_ID();
				cash = MCash.get(getCtx(), cashBookId, getDateInvoiced(), get_TrxName());
			}
			else
			{
				cash = MCash.get(getCtx(), getAD_Org_ID(),
								 getDateInvoiced(), getC_Currency_ID(), get_TrxName());
			}

			// End Posterita Modifications

			if (cash == null || cash.get_ID() <= 0)
			{
				throw new AdempiereException("@NoCashBook@");
			}
			final MCashLine cl = new MCashLine(cash);
			cl.setInvoice(this);
			cl.saveEx(get_TrxName());
			setC_CashLine_ID(cl.getC_CashLine_ID());
		}    // CashBook

		// Update Order & Match
		int matchInv = 0;
		int matchPO = 0;
		final MInvoiceLine[] lines = getLines(false);
		for (final MInvoiceLine line : lines)
		{
			// Update Order Line
			MOrderLine ol = null;
			if (line.getC_OrderLine_ID() > 0)
			{
				if (isSOTrx() // note: the PO invoices' order lines are updated by the MatchPO business logic
						|| line.getM_Product_ID() <= 0)
				{
					ol = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
					if (line.getQtyInvoiced() != null)
					{
						ol.setQtyInvoiced(ol.getQtyInvoiced().add(line.getQtyInvoiced()));
					}

					ol.saveEx(get_TrxName());
				}
				// Order Invoiced Qty updated via Matching Inv-PO
				else if (!isSOTrx()
						&& line.getM_Product_ID() > 0
						&& !isReversal())
				{
					// MatchPO is created also from MInOut when Invoice exists before Shipment
					final BigDecimal matchQty = line.getQtyInvoiced();
					Services.get(IMatchPOBL.class).create(line, null, getDateInvoiced(), matchQty);
					matchPO++;
				}
			}

			// Update QtyInvoiced RMA Line
			if (line.getM_RMALine_ID() > 0)
			{
				final MRMALine rmaLine = new MRMALine(getCtx(), line.getM_RMALine_ID(), get_TrxName());
				if (rmaLine.getQtyInvoiced() != null)
				{
					rmaLine.setQtyInvoiced(rmaLine.getQtyInvoiced().add(line.getQtyInvoiced()));
				}
				else
				{
					rmaLine.setQtyInvoiced(line.getQtyInvoiced());
				}

				rmaLine.saveEx(get_TrxName());
			}
			//

			// Matching - Inv-Shipment
			// task: 08529 Note that we also do this for SOTrx invoices as of now
			if (line.getM_InOutLine_ID() > 0
					&& line.getM_Product_ID() > 0
					&& !isReversal() // in case of reversal, the job is done by IInvoiceBL.handleReversalForInvoice()
			)
			{
				final boolean matchInvCreated = Services.get(IMatchInvBL.class).createMatchInvBuilder()
						.setContext(this)
						.setC_InvoiceLine(line)
						.setM_InOutLine(line.getM_InOutLine())
						.setDateTrx(getDateInvoiced())
						.setConsiderQtysAlreadyMatched(false) // backward compatibility
						.setAllowQtysOfOppositeSigns(true)// backward compatibility
						.build();
				if (matchInvCreated)
				{
					matchInv++;
				}
			}
		}    // for all lines

		// Update Project
		if (isSOTrx() && getC_Project_ID() > 0)
		{
			final MProject project = new MProject(getCtx(), getC_Project_ID(), get_TrxName());
			BigDecimal amt = getGrandTotal(true);
			final int C_CurrencyTo_ID = project.getC_Currency_ID();
			if (C_CurrencyTo_ID != getC_Currency_ID())
			{
				amt = Services.get(ICurrencyBL.class).convert(
						amt,
						CurrencyId.ofRepoId(getC_Currency_ID()),
						CurrencyId.ofRepoId(C_CurrencyTo_ID),
						TimeUtil.asLocalDate(getDateAcct()),
						(CurrencyConversionTypeId)null,
						ClientId.ofRepoId(getAD_Client_ID()),
						OrgId.ofRepoId(getAD_Org_ID()));
			}
			BigDecimal newAmt = project.getInvoicedAmt();
			if (newAmt == null)
			{
				newAmt = amt;
			}
			else
			{
				newAmt = newAmt.add(amt);
			}
			log.debug("GrandTotal=" + getGrandTotal(true) + "(" + amt
							  + ") Project " + project.getName()
							  + " - Invoiced=" + project.getInvoicedAmt() + "->" + newAmt);
			project.setInvoicedAmt(newAmt);
			project.saveEx(get_TrxName());
		}    // project

		// Make sure is flagged as processed.
		// Else, APIs like checking the allocated amount will ignore this invoice.
		setProcessed(true);
		saveEx();

		//
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		// Counter Documents
		final MInvoice counter = createCounterDoc();

		setProcessed(true);
		setDocAction(DOCACTION_Reverse_Correct); // issue #347
		return IDocument.STATUS_Completed;
	}    // completeIt

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		final I_C_DocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete())
		{
			setDateInvoiced(SystemTime.asTimestamp());
		}
		if (dt.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
			{
				setDocumentNo(value);
			}
		}
	}

	/**
	 * Create Counter Document
	 *
	 * @return counter invoice
	 */
	private MInvoice createCounterDoc()
	{
		// Is this a counter doc ?
		if (getRef_Invoice_ID() != 0)
		{
			return null;
		}

		// Org Must be linked to BPartner
		final MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		final int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
		if (counterC_BPartner_ID <= 0)
		{
			return null;
		}
		// Business Partner needs to be linked to Org
		final I_C_BPartner bp = Services.get(IBPartnerDAO.class).getById(getC_BPartner_ID());
		final int counterAD_Org_ID = bp.getAD_OrgBP_ID();
		if (counterAD_Org_ID == 0)
		{
			return null;
		}

		final I_C_BPartner counterBP = Services.get(IBPartnerDAO.class).getById(counterC_BPartner_ID);
		// MOrgInfo counterOrgInfo = MOrgInfo.get(getCtx(), counterAD_Org_ID);
		log.debug("Counter BP=" + counterBP.getName());

		// Document Type
		int C_DocTypeTarget_ID = 0;
		final MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(getCtx(), getC_DocType_ID());
		if (counterDT != null)
		{
			log.debug(counterDT.toString());
			if (!counterDT.isCreateCounter() || !counterDT.isValid())
			{
				return null;
			}
			C_DocTypeTarget_ID = counterDT.getCounter_C_DocType_ID();
		}
		else    // indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(getCtx(), getC_DocType_ID());
			log.debug("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID <= 0)
			{
				return null;
			}
		}

		// Deep Copy
		final MInvoice counter = copyFrom(this, getDateInvoiced(), getDateAcct(),
										  C_DocTypeTarget_ID, !isSOTrx(), true, get_TrxName(), true);
		//
		counter.setAD_Org_ID(counterAD_Org_ID);
		// counter.setM_Warehouse_ID(counterOrgInfo.getM_Warehouse_ID());
		//
		counter.setBPartner(counterBP);
		// Refernces (Should not be required
		counter.setSalesRep_ID(getSalesRep_ID());

		// metas
		final IPOService poService = Services.get(IPOService.class);
		poService.copyValue(this, counter, C_Invoice_INCOTERM);
		poService.copyValue(this, counter, C_Invoice_INCOTERMLOCATION);
		poService.copyValue(this, counter, C_Invoice_DESCRIPTION_BOTTOM);
		// metas end

		counter.save(get_TrxName());

		// Update copied lines
		final MInvoiceLine[] counterLines = counter.getLines(true);
		for (final MInvoiceLine counterLine : counterLines)
		{
			counterLine.setClientOrg(counter);
			counterLine.setInvoice(counter);    // copies header values (BP, etc.)
			counterLine.setPrice();
			counterLine.setTax();
			//
			counterLine.save(get_TrxName());
		}

		log.debug(counter.toString());

		// Document Action
		if (counterDT != null)
		{
			if (counterDT.getDocAction() != null)
			{
				counter.setDocAction(counterDT.getDocAction());
				counter.processIt(counterDT.getDocAction());
				counter.save(get_TrxName());
			}
		}
		return counter;
	}    // createCounterDoc

	@Override
	public boolean voidIt()
	{
		// Before Void
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);

		final DocStatus docStatus = DocStatus.ofCode(getDocStatus());
		if (docStatus.isClosedReversedOrVoided())
		{
			throw new AdempiereException("Document Closed: " + docStatus);
		}

		// Not Processed
		if (docStatus.isNotProcessed())
		{
			// Set lines to 0
			final MInvoiceLine[] lines = getLines(false);
			for (final MInvoiceLine line : lines)
			{
				final BigDecimal old = line.getQtyInvoiced();
				if (old.compareTo(BigDecimal.ZERO) != 0)
				{
					line.setQty(BigDecimal.ZERO);
					line.setTaxAmt(BigDecimal.ZERO);
					line.setLineNetAmt(BigDecimal.ZERO);
					line.setLineTotalAmt(BigDecimal.ZERO);
					line.addDescription(Msg.getMsg(getCtx(), "Voided") + " (" + old + ")");
					// Unlink Shipment
					if (line.getM_InOutLine_ID() > 0)
					{
						final I_M_InOutLine ioLine = line.getM_InOutLine();
						ioLine.setIsInvoiced(false);
						InterfaceWrapperHelper.save(ioLine);
						line.setM_InOutLine(null);
					}
					//
					if (line.getC_OrderLine_ID() > 0)
					{
						final I_C_OrderLine ol = line.getC_OrderLine();
						ol.setQtyInvoiced(BigDecimal.ZERO);
						InterfaceWrapperHelper.save(ol);
					}
					line.save(get_TrxName());
				}
			}
			addDescription(Msg.getMsg(getCtx(), "Voided"));
			setIsPaid(true);
			setC_Payment_ID(0);
		}
		else
		{
			return reverseCorrectIt();
		}

		// After Void
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}    // voidIt

	@Override
	public boolean closeIt()
	{
		// Before Close
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);

		// After Close
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}    // closeIt

	/**
	 * Reverse Correction - same date
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		// Before reverseCorrect
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);

		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		//
		final MAllocationHdr[] allocations = MAllocationHdr.getOfInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		for (final MAllocationHdr allocation : allocations)
		{
			allocation.setDocAction(IDocument.ACTION_Reverse_Correct);
			allocation.reverseCorrectIt();
			allocation.saveEx(get_TrxName()); // metas: tsa: always use saveEx
		}
		// Reverse/Delete Matching
		if (!isSOTrx())
		{
			// task 08529: don't delete the MMatchInv; there is even accounting done with it! Instead, IInvoiceCand.handleReversalForInvoice() creates a counter matchInv
			// final MMatchInv[] mInv = MMatchInv.getInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
			// for (int i = 0; i < mInv.length; i++)
			// mInv[i].delete(true);

			for (final I_M_MatchPO matchPO : Services.get(IMatchPODAO.class).getByInvoiceId(InvoiceId.ofRepoId(getC_Invoice_ID())))
			{
				if (matchPO.getM_InOutLine_ID() <= 0)
				{
					matchPO.setProcessed(false);
					InterfaceWrapperHelper.delete(matchPO);
				}
				else
				{
					matchPO.setC_InvoiceLine_ID(-1);
					InterfaceWrapperHelper.save(matchPO);
				}
			}
		}
		//
		load(get_TrxName());    // reload allocation reversal info

		// Deep Copy
		final boolean setOrder = true;
		final boolean counter = false;
		final MInvoice reversal = copyFrom(this, getDateInvoiced(), getDateAcct(), getC_DocType_ID(), isSOTrx(), counter, get_TrxName(), setOrder);
		if (reversal == null)
		{
			throw new AdempiereException("Could not create Invoice Reversal");
		}
		reversal.setReversal(true);

		// Reverse Line Qty
		final MInvoiceLine[] rLines = reversal.getLines(false); // don't requery, work with the reversals that we just created and have have in memory
		for (final MInvoiceLine rLine2 : rLines)
		{
			final MInvoiceLine rLine = rLine2;
			rLine.setQtyEntered(rLine.getQtyEntered().negate());
			rLine.setQtyInvoiced(rLine.getQtyInvoiced().negate());
			rLine.setLineNetAmt(rLine.getLineNetAmt().negate());
			if (rLine.getTaxAmt() != null && rLine.getTaxAmt().compareTo(BigDecimal.ZERO) != 0)
			{
				rLine.setTaxAmt(rLine.getTaxAmt().negate());
			}
			if (rLine.getLineTotalAmt() != null && rLine.getLineTotalAmt().compareTo(BigDecimal.ZERO) != 0)
			{
				rLine.setLineTotalAmt(rLine.getLineTotalAmt().negate());
			}
			rLine.saveEx(get_TrxName());
		}
		reversal.setC_Order_ID(getC_Order_ID());
		reversal.addDescription("{->" + getDocumentNo() + ")");
		// FR1948157
		// metas: we need to set the Reversal_ID, before we process (and other model validators are invoked)
		reversal.setReversal_ID(getC_Invoice_ID());
		//
		if (!reversal.processIt(IDocument.ACTION_Complete))
		{
			throw new AdempiereException("Reversal ERROR: " + reversal.getProcessMsg());
		}
		reversal.setC_Payment_ID(0);
		reversal.setIsPaid(true);
		reversal.closeIt();
		reversal.setProcessing(false);
		reversal.setDocStatus(DocStatus.Reversed.getCode());
		reversal.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reversal);

		//
		addDescription("(" + reversal.getDocumentNo() + "<-)");

		// Clean up Reversed (this)
		final MInvoiceLine[] iLines = getLines(false);
		for (final MInvoiceLine iLine : iLines)
		{
			if (iLine.getM_InOutLine_ID() > 0)
			{
				final I_M_InOutLine ioLine = iLine.getM_InOutLine();
				ioLine.setIsInvoiced(false);
				InterfaceWrapperHelper.save(ioLine);

				// Reconsiliation
				iLine.setM_InOutLine(null);
				InterfaceWrapperHelper.save(iLine);
			}
			if ((isSOTrx() // task 06022: the orderlines for purchase invoices are updated in the MatchPO business logic
					|| iLine.getM_Product_ID() <= 0) // ts: i don't really understand this or-condition, but it is also in completeIt() and i'm convinced it should be in sync
					&& iLine.getC_OrderLine_ID() > 0)
			{
				final I_C_OrderLine ol = iLine.getC_OrderLine();
				// task 09266: not setting the qty to zero, but decreasing it here, the same way it is increased in completeIt()
				ol.setQtyInvoiced(ol.getQtyInvoiced().subtract(iLine.getQtyInvoiced()));
				InterfaceWrapperHelper.save(ol);
			}
		}
		setProcessed(true);
		// FR1948157
		setReversal_ID(reversal.getC_Invoice_ID());
		setDocStatus(DocStatus.Reversed.getCode());    // may come from void
		setDocAction(DOCACTION_None);
		setC_Payment_ID(0);
		setIsPaid(true);

		//
		// Create Allocation: allocate the reversal invoice against the original invoice
		final MAllocationHdr alloc = new MAllocationHdr(getCtx(), false, getDateAcct(),
														getC_Currency_ID(),
														Msg.translate(getCtx(), "C_Invoice_ID") + ": " + getDocumentNo() + "/" + reversal.getDocumentNo(),
														get_TrxName());
		alloc.setAD_Org_ID(getAD_Org_ID());
		alloc.saveEx();  // metas: tsa: always use saveEx
		// if (alloc.save()) // metas: tsa: always use saveEx
		{
			// Amount
			BigDecimal gt = getGrandTotal(true);
			if (!isSOTrx())
			{
				gt = gt.negate();
			}
			// Orig Line
			final MAllocationLine aLine = new MAllocationLine(alloc, gt, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
			aLine.setC_Invoice_ID(getC_Invoice_ID());
			aLine.saveEx(); // metas: tsa: always use saveEx
			// Reversal Line
			final MAllocationLine rLine = new MAllocationLine(alloc, gt.negate(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
			rLine.setC_Invoice_ID(reversal.getC_Invoice_ID());
			rLine.saveEx(); // metas: tsa: always use saveEx
			// Process It
			if (alloc.processIt(IDocument.ACTION_Complete))
			{
				alloc.saveEx(); // metas: tsa: always use saveEx
			}
		}

		// After reverseCorrect
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);

		return true;
	}    // reverseCorrectIt

	@Override
	public boolean reverseAccrualIt()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean reActivateIt()
	{
		throw new UnsupportedOperationException();
	}

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "GrandTotal")).append("=").append(getGrandTotal())
				.append(" (#").append(getLines(false).length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	}    // getSummary

	@Override
	public LocalDate getDocumentDate()
	{
		return TimeUtil.asLocalDate(getDateInvoiced());
	}

	@Override
	public String getProcessMsg()
	{
		return null;
	}

	@Override
	public int getDoc_User_ID()
	{
		return getSalesRep_ID();
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		return getGrandTotal();
	}

	public void setRMA(final MRMA rma)
	{
		final MInvoice originalInvoice = rma.getOriginalInvoice();
		if (originalInvoice == null)
		{
			throw new AdempiereException("Not invoiced - RMA: " + rma.getDocumentNo());
		}

		setM_RMA_ID(rma.getM_RMA_ID());
		setAD_Org_ID(rma.getAD_Org_ID());
		setDescription(rma.getDescription());

		InvoiceDocumentLocationAdapterFactory
				.locationAdapter(this)
				.setFrom(originalInvoice);

		setSalesRep_ID(rma.getSalesRep_ID());

		setGrandTotal(rma.getAmt());
		setIsSOTrx(rma.isSOTrx());
		setTotalLines(rma.getAmt());

		setC_Currency_ID(originalInvoice.getC_Currency_ID());
		setIsTaxIncluded(originalInvoice.isTaxIncluded());
		setM_PriceList_ID(originalInvoice.getM_PriceList_ID());
		setC_Project_ID(originalInvoice.getC_Project_ID());
		setC_Activity_ID(originalInvoice.getC_Activity_ID());
		setC_Campaign_ID(originalInvoice.getC_Campaign_ID());
		setUser1_ID(originalInvoice.getUser1_ID());
		setUser2_ID(originalInvoice.getUser2_ID());
	}

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 * @deprecated Please use {@link IInvoiceBL#isComplete(I_C_Invoice)}
	 */
	@Deprecated
	public boolean isComplete()
	{
		return Services.get(IInvoiceBL.class).isComplete(this);
	}    // isComplete

}    // MInvoice
