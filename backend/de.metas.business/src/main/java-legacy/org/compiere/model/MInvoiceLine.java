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

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;

/**
 * Invoice Line Model
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <ul>
 * <li>BF [ 2804142 ] MInvoice.setRMALine should work only for CreditMemo invoices https://sourceforge.net/tracker/?func=detail&aid=2804142&group_id=176962&atid=879332
 * @author Michael Judd, www.akunagroup.com
 * <ul>
 * <li>BF [ 1733602 ] Price List including Tax Error - when a user changes the orderline or invoice line for a product on a price list that includes tax, the net amount is incorrectly
 * calculated.
 * @author tobi42 "metas us1064"
 * <ul>
 * <li>We changed MProductPricing such that getPriceStd returns the "real" PriceStd from the PL (without subtracting the discount)
 * <li>Making sure that setPrice still works the way it used to
 * @version $Id: MInvoiceLine.java,v 1.5 2006/07/30 00:51:03 jjanke Exp $
 */
public class MInvoiceLine extends X_C_InvoiceLine
{

	/**
	 *
	 */
	private static final long serialVersionUID = 4264055057724565805L;

	/**
	 * Get Invoice Line referencing InOut Line
	 *
	 * @param sLine shipment line
	 * @return (first) invoice line
	 */
	public static MInvoiceLine getOfInOutLine(MInOutLine sLine)
	{
		if (sLine == null)
		{
			return null;
		}

		MInvoiceLine retValue = null;
		final String sql = "SELECT * FROM C_InvoiceLine WHERE M_InOutLine_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, sLine.get_TrxName());
			pstmt.setInt(1, sLine.getM_InOutLine_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = new MInvoiceLine(sLine.getCtx(), rs, sLine.get_TrxName());
				if (rs.next())
				{
					// metas-tsa: If there were more then one invoice line found, it's better to return null then to return randomly one of them.
					s_log.warn("More than one C_InvoiceLine of " + sLine + ". Returning null.");
					return null;
				}
			}
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return retValue;
	}    // getOfInOutLine

	/**
	 * Static Logger
	 */
	private static Logger s_log = LogManager.getLogger(MInvoiceLine.class);

	/**
	 * Tax
	 */
	private MTax m_tax = null;

	/**************************************************************************
	 * Invoice Line Constructor
	 *
	 * @param ctx context
	 * @param C_InvoiceLine_ID invoice line or 0
	 * @param trxName transaction name
	 */
	public MInvoiceLine(Properties ctx, int C_InvoiceLine_ID, String trxName)
	{
		super(ctx, C_InvoiceLine_ID, trxName);
		if (C_InvoiceLine_ID == 0)
		{
			setIsDescription(false);
			setIsPrinted(true);
			setLineNetAmt(ZERO);
			setPriceEntered(ZERO);
			setPriceActual(ZERO);
			setPriceLimit(ZERO);
			setPriceList(ZERO);
			setM_AttributeSetInstance_ID(0);
			setTaxAmt(ZERO);
			//
			setQtyEntered(ZERO);
			setQtyInvoiced(ZERO);
		}
	}    // MInvoiceLine

	/**
	 * Parent Constructor
	 *
	 * @param invoice parent
	 */
	public MInvoiceLine(MInvoice invoice)
	{
		this(invoice.getCtx(), 0, invoice.get_TrxName());
		if (invoice.get_ID() == 0)
		{
			throw new IllegalArgumentException("Header not saved");
		}
		setClientOrg(invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
		setC_Invoice_ID(invoice.getC_Invoice_ID());
		setInvoice(invoice);
	}    // MInvoiceLine

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set record
	 * @param trxName transaction
	 */
	public MInvoiceLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}    // MInvoiceLine

	private int m_M_PriceList_ID = 0;
	private Timestamp m_DateInvoiced = null;
	private int m_C_BPartner_ID = 0;
	// private int m_C_BPartner_Location_ID = 0;
	private boolean m_IsSOTrx = true;
	private boolean m_priceSet = false;
	private MProduct m_product = null;
	/**
	 * Charge
	 */
	private MCharge m_charge = null;

	/**
	 * Cached Name of the line
	 */
	private String m_name = null;
	// /** Cached Precision */
	// private Integer m_precision = null;
	/**
	 * Product Pricing
	 */
	private MProductPricing m_productPricing = null;
	/**
	 * Parent
	 */
	private I_C_Invoice m_parent = null;

	/**
	 * Set Defaults from Order. Called also from copy lines from invoice Does not set Parent !!
	 *
	 * @param invoice invoice
	 */
	public void setInvoice(I_C_Invoice invoice)
	{
		m_parent = invoice;
		m_M_PriceList_ID = invoice.getM_PriceList_ID();
		m_DateInvoiced = invoice.getDateInvoiced();
		m_C_BPartner_ID = invoice.getC_BPartner_ID();
		// m_C_BPartner_Location_ID = invoice.getC_BPartner_Location_ID();
		m_IsSOTrx = invoice.isSOTrx();
		// m_precision = Services.get(IInvoiceBL.class).getPrecision(invoice);
	}    // setOrder

	/**
	 * Get Parent
	 *
	 * @return parent
	 * @deprecated Please use {@link #getC_Invoice()}
	 */
	@Deprecated
	public I_C_Invoice getParent()
	{
		if (m_parent == null)
		{
			m_parent = super.getC_Invoice();
		}
		return m_parent;
	}    // getParent

	/**
	 * Set values from Order Line. Does not set quantity!
	 *
	 * @param oLine line
	 */
	public void setOrderLine(MOrderLine oLine)
	{
		final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);

		setC_OrderLine_ID(oLine.getC_OrderLine_ID());
		//
		setLine(oLine.getLine());
		setIsDescription(oLine.isDescription());
		setDescription(oLine.getDescription());
		//
		if (oLine.getM_Product_ID() == 0)
		{
			setC_Charge_ID(oLine.getC_Charge_ID());
		}
		//
		setM_Product_ID(oLine.getM_Product_ID());
		setM_AttributeSetInstance_ID(oLine.getM_AttributeSetInstance_ID());
		setS_ResourceAssignment_ID(oLine.getS_ResourceAssignment_ID());
		setC_UOM_ID(oLine.getC_UOM_ID());
		final I_C_InvoiceLine il = create(this, I_C_InvoiceLine.class);
		final I_C_OrderLine ol = create(this, I_C_OrderLine.class);
		il.setPrice_UOM_ID(ol.getPrice_UOM_ID());
		il.setIsPackagingMaterial(ol.isPackagingMaterial());
		//
		setPriceEntered(oLine.getPriceEntered());
		setPriceActual(oLine.getPriceActual());
		setPriceLimit(oLine.getPriceLimit());
		setPriceList(oLine.getPriceList());
		create(this, I_C_InvoiceLine.class).setDiscount(oLine.getDiscount()); // metas cg: task 05052
		//
		// 07442
		// Do not change the tax (or tax category) if it was already set

		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
		final Tax tax = taxDAO.getTaxByIdOrNull(getC_Tax_ID());

		if (tax == null)
		{
			setC_Tax_ID(oLine.getC_Tax_ID());
			setC_TaxCategory_ID(oLine.getC_TaxCategory_ID());
		}

		else
		{
			setC_TaxCategory_ID(tax.getTaxCategoryId().getRepoId());
		}
		setLineNetAmt(oLine.getLineNetAmt());
		//
		setC_ProjectPhase_ID(oLine.getC_ProjectPhase_ID());
		setC_ProjectTask_ID(oLine.getC_ProjectTask_ID());

		// 07442
		// Do not change the activity if it was already set

		int activityId = getC_Activity_ID();
		if (activityId <= 0)
		{
			activityId = oLine.getC_Activity_ID();
		}

		Dimension orderlineDimension = dimensionService.getFromRecord(oLine);
		orderlineDimension = orderlineDimension.withActivityId(ActivityId.ofRepoIdOrNull(activityId));

		dimensionService.updateRecord(this, orderlineDimension);

		setAD_OrgTrx_ID(oLine.getAD_OrgTrx_ID());
		//
		setRRAmt(oLine.getRRAmt());
		setRRStartDate(oLine.getRRStartDate());
	}    // setOrderLine

	/**
	 * Set values from Shipment Line. Does not set quantity!
	 *
	 * @param sLine ship line
	 */
	public void setShipLine(MInOutLine sLine)
	{
		// 07442
		// get tax and activity. they will be checked in several places in this method
		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
		final Tax tax = taxDAO.getTaxByIdOrNull(getC_Tax_ID());
		final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);

		setM_InOutLine_ID(sLine.getM_InOutLine_ID());
		setC_OrderLine_ID(sLine.getC_OrderLine_ID());
		// Set RMALine ID if shipment/receipt is based on RMA Doc
		setM_RMALine_ID(sLine.getM_RMALine_ID());

		//
		setLine(sLine.getLine());
		setIsDescription(sLine.isDescription());
		setDescription(sLine.getDescription());
		//
		setM_Product_ID(sLine.getM_Product_ID());
		if (sLine.sameOrderLineUOM())
		{
			setC_UOM_ID(sLine.getC_UOM_ID());
		}
		else
		{
			// use product UOM if the shipment hasn't the same uom than the order
			setC_UOM_ID(getProduct().getC_UOM_ID());
		}
		setM_AttributeSetInstance_ID(sLine.getM_AttributeSetInstance_ID());
		// setS_ResourceAssignment_ID(sLine.getS_ResourceAssignment_ID());
		if (getM_Product_ID() == 0)
		{
			setC_Charge_ID(sLine.getC_Charge_ID());
		}
		//
		int C_OrderLine_ID = sLine.getC_OrderLine_ID();
		if (C_OrderLine_ID != 0)
		{
			I_C_OrderLine oLine = create(sLine.getC_OrderLine(), I_C_OrderLine.class);                                    // metas: changed for better future caching
			// MOrderLine oLine = new MOrderLine (getCtx(), C_OrderLine_ID, get_TrxName()); // metas: changed for better future caching
			setS_ResourceAssignment_ID(oLine.getS_ResourceAssignment_ID());
			//
			if (sLine.sameOrderLineUOM())
			{
				setPriceEntered(oLine.getPriceEntered());
			}
			else
			{
				setPriceEntered(oLine.getPriceActual());
			}
			setPriceActual(oLine.getPriceActual());
			setPriceLimit(oLine.getPriceLimit());
			setPriceList(oLine.getPriceList());
			// metas: begin: US1184
			if (getPriceActual().compareTo(getPriceList()) != 0)
			{
				create(this, I_C_InvoiceLine.class).setIsManualPrice(true);
				// metas: end
				//
			}

			// 07442
			// Do not change the tax (or tax category) if it was already set

			if (tax == null)
			{
				create(this, I_C_InvoiceLine.class).setDiscount(oLine.getDiscount()); // metas cg: task 05052
				setC_Tax_ID(oLine.getC_Tax_ID());
				setC_TaxCategory_ID(oLine.getC_TaxCategory_ID());
			}

			else
			{
				setC_TaxCategory_ID(tax.getTaxCategoryId().getRepoId());
			}
			setLineNetAmt(oLine.getLineNetAmt());

			Dimension sLineDimension = dimensionService.getFromRecord(sLine);

			if (sLineDimension.getProjectId() == null)
			{
				sLineDimension = sLineDimension.withProjectId(ProjectId.ofRepoIdOrNull(oLine.getC_Project_ID()));
			}
			dimensionService.updateRecord(this, sLineDimension);
		}
		// Check if shipment line is based on RMA
		else if (sLine.getM_RMALine_ID() != 0)
		{
			// Set Pricing details from the RMA Line on which it is based
			MRMALine rmaLine = new MRMALine(getCtx(), sLine.getM_RMALine_ID(), get_TrxName());

			setPrice();
			setPrice(rmaLine.getAmt());
			setC_Tax_ID(rmaLine.getC_Tax_ID());

			if (tax == null)
			{
				final I_C_Tax rmaTax = create(getCtx(), rmaLine.getC_Tax_ID(), I_C_Tax.class, get_TrxName());
				setC_TaxCategory_ID(rmaTax.getC_TaxCategory_ID());
			}
			else
			{
				setC_TaxCategory_ID(tax.getTaxCategoryId().getRepoId());
			}
			setLineNetAmt(rmaLine.getLineNetAmt());
		}
		else
		{
			setPrice();
			setTax();
		}
		//
		setC_Project_ID(sLine.getC_Project_ID());
		setC_ProjectPhase_ID(sLine.getC_ProjectPhase_ID());
		setC_ProjectTask_ID(sLine.getC_ProjectTask_ID());
		// 07442
		// Do not change the activity if it was already set
		if (getC_Activity_ID() <= 0)
		{
			setC_Activity_ID(sLine.getC_Activity_ID());
		}
		setC_Campaign_ID(sLine.getC_Campaign_ID());
		setAD_OrgTrx_ID(sLine.getAD_OrgTrx_ID());
		setUser1_ID(sLine.getUser1_ID());
		setUser2_ID(sLine.getUser2_ID());

		// task FRESH-273
		final I_C_InvoiceLine il = create(this, I_C_InvoiceLine.class);
		final de.metas.inout.model.I_M_InOutLine sl = create(sLine, de.metas.inout.model.I_M_InOutLine.class);
		il.setIsPackagingMaterial(sl.isPackagingMaterial());

	}    // setShipLine

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}    // addDescription

	/**
	 * Set M_AttributeSetInstance_ID
	 *
	 * @param M_AttributeSetInstance_ID id
	 */
	@Override
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID == 0)
		{
			set_Value("M_AttributeSetInstance_ID", new Integer(0));
		}
		else
		{
			super.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		}
	}    // setM_AttributeSetInstance_ID

	/**************************************************************************
	 * Set Price for Product and PriceList. Uses standard SO price list of not set by invoice constructor
	 */
	public void setPrice()
	{
		if (getM_Product_ID() == 0 || isDescription())
		{
			return;
		}
		if (m_M_PriceList_ID == 0 || m_C_BPartner_ID == 0)
		{
			setInvoice(getParent());
		}
		if (m_M_PriceList_ID == 0 || m_C_BPartner_ID == 0)
		{
			throw new IllegalStateException("setPrice - PriceList unknown!");
		}
		setPrice(m_M_PriceList_ID, m_C_BPartner_ID);
	}    // setPrice

	/**
	 * Set Price for Product and PriceList
	 *
	 * @param M_PriceList_ID price list
	 * @param C_BPartner_ID  business partner
	 */
	private void setPrice(int M_PriceList_ID, int C_BPartner_ID)
	{
		if (getM_Product_ID() == 0 || isDescription())
		{
			return;
		}
		//
		log.debug("M_PriceList_ID={}", M_PriceList_ID);

		final CountryId countryId = getC_Invoice().getC_BPartner_Location_ID() > 0
				? Services.get(IBPartnerDAO.class).getCountryId(BPartnerLocationId.ofRepoId(getC_Invoice().getC_BPartner_ID(), getC_Invoice().getC_BPartner_Location_ID()))
				: null;
		
		m_productPricing = new MProductPricing(
				OrgId.ofRepoId(getAD_Org_ID()),
				getM_Product_ID(), 
				C_BPartner_ID,
				countryId,						   
				getQtyInvoiced(), 
				m_IsSOTrx);
		m_productPricing.setM_PriceList_ID(M_PriceList_ID);
		m_productPricing.setPriceDate(m_DateInvoiced);

		final I_C_InvoiceLine il = create(this, I_C_InvoiceLine.class);
		
		// Set the IsManualPrice in the pricing engine based on the value in the invoice Line
		m_productPricing.setManualPrice(il.isManualPrice());

		if (m_productPricing.isManualPrice())
		{
			log.debug("Do not calculate the prices for " + m_productPricing + " because they were already manually set");
			return;
		}
		//
		// metas: begin: US1184
		if (!m_productPricing.recalculatePrice())
		{
			log.debug("Cannot calculate prices for " + m_productPricing + " [SKIP]");
			return;
		}
		// metas: end
		// metas us1064
		// setPriceActual (m_productPricing.getPriceStd());
		final BigDecimal priceActual = m_productPricing.getDiscount().subtractFromBase(m_productPricing.getPriceStd(), getAmountPrecision().toInt());
		setPriceActual(priceActual);
		// metas us1064 end
		setPriceList(m_productPricing.getPriceList());
		setPriceLimit(m_productPricing.getPriceLimit());
		//
		if (getQtyEntered().compareTo(getQtyInvoiced()) == 0)
		{
			setPriceEntered(m_productPricing.getPriceStd());
		}
		else
		{
			setPriceEntered(m_productPricing.getPriceStd().multiply(getQtyInvoiced()
																			.divide(getQtyEntered(), 6, BigDecimal.ROUND_HALF_UP)));    // precision
		}

		setC_TaxCategory_ID(m_productPricing.getC_TaxCategory_ID());

		//
		if (getC_UOM_ID() == 0)
		{
			setC_UOM_ID(m_productPricing.getC_UOM_ID());
		}

		if (il.getPrice_UOM_ID() == 0)
		{
			il.setPrice_UOM_ID(m_productPricing.getC_UOM_ID());
		}
		//
		m_priceSet = true;
	}    // setPrice

	/**
	 * Set Price Entered/Actual. Use this Method if the Line UOM is the Product UOM
	 *
	 * @param PriceActual price
	 */
	public void setPrice(BigDecimal PriceActual)
	{
		setPriceEntered(PriceActual);
		setPriceActual(PriceActual);
		m_priceSet = true; // 03272 Note that a zero price is also OK, if set with this method
	}    // setPrice

	/**
	 * Set Price Actual. (actual price is not updateable)
	 *
	 * @param PriceActual actual price
	 */
	@Override
	public void setPriceActual(BigDecimal PriceActual)
	{
		if (PriceActual == null)
		{
			throw new IllegalArgumentException("PriceActual is mandatory");
		}
		set_ValueNoCheck("PriceActual", PriceActual);
	}    // setPriceActual

	/**
	 * Set Tax - requires Warehouse
	 *
	 * @return true if found
	 */
	public boolean setTax()
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		if (isDescription())
		{
			return true;
		}

		//
		// metas-ts: guessing and using #M_Warehouse_ID seems worse than useless
		// int M_Warehouse_ID = Env.getContextAsInt(getCtx(), "#M_Warehouse_ID");
		//
		//

		// metas: we need to fetch the Tax based on pricing tax category and not directly
		// int C_Tax_ID = Tax.get(getCtx(), getM_Product_ID(), getC_Charge_ID() , m_DateInvoiced, m_DateInvoiced,
		// getAD_Org_ID(), 0,
		// m_C_BPartner_Location_ID, // should be bill to
		// m_C_BPartner_Location_ID, m_IsSOTrx);

		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(getC_TaxCategory_ID());

		if (taxCategoryId == null)
		{
			log.error("No Tax Category found");
			return false;
		}

		setC_TaxCategory_ID(taxCategoryId.getRepoId());

		final I_C_Invoice invoice = getC_Invoice();

		final I_C_InvoiceLine invoiceLine = create(this, I_C_InvoiceLine.class);
		final CountryId fromCountryId = invoiceBL.getFromCountryId(invoice, invoiceLine);

		final InOutLineId inoutLineId = InOutLineId.ofRepoIdOrNull(getM_InOutLine_ID());

		final I_M_InOutLine inoutLineRecord = inoutLineId == null ? null : inoutDAO.getLineById(inoutLineId);
		final I_M_InOut io = inoutLineRecord == null ? null : inoutDAO.getById(InOutId.ofRepoId(inoutLineRecord.getM_InOut_ID()));

		final OrgId fromOrgId = io != null ? OrgId.ofRepoId(io.getAD_Org_ID()) : OrgId.ofRepoId(invoice.getAD_Org_ID());

		final Timestamp taxDate = io != null ? io.getMovementDate() : invoice.getDateInvoiced();

		final BPartnerLocationAndCaptureId taxBPartnerLocationId = io != null
				? InOutDocumentLocationAdapterFactory.locationAdapter(io).getBPartnerLocationAndCaptureId()
				: InvoiceDocumentLocationAdapterFactory.locationAdapter(invoice).getBPartnerLocationAndCaptureId();

		final boolean isSOTrx = io != null ? io.isSOTrx() : invoice.isSOTrx();

		final Tax tax = Services.get(ITaxDAO.class).getBy(TaxQuery.builder()
				.fromCountryId(fromCountryId)
				.orgId(fromOrgId)
				.bPartnerLocationId(taxBPartnerLocationId)
				.dateOfInterest(taxDate)
				.taxCategoryId(taxCategoryId)
				.soTrx(SOTrx.ofBoolean(isSOTrx))
				.build());

		if (tax == null)
		{
			TaxNotFoundException.builder()
					.taxCategoryId(taxCategoryId)
					.isSOTrx(isSOTrx)
					.billDate(taxDate)
					.billFromCountryId(fromCountryId)
					.build()
					.throwOrLogWarning(true, log);
		}
		setC_Tax_ID(tax.getTaxId().getRepoId());

		return true;
	}    // setTax

	/**
	 * Calculate Tax Amt. Assumes Line Net is calculated
	 */
	public void setTaxAmt()
	{
		final TaxId taxId = TaxId.ofRepoIdOrNull(getC_Tax_ID());
		if (taxId == null)
		{
			return;
		}
		// setLineNetAmt();
		final Tax tax = Services.get(ITaxBL.class).getTaxById(taxId);
		if (tax.isDocumentLevel() && m_IsSOTrx)
		{
			return;
		}
		//
		final BigDecimal TaxAmt = tax.calculateTax(getLineNetAmt(), isTaxIncluded(), getAmountPrecision().toInt());
		if (isTaxIncluded())
		{
			setLineTotalAmt(getLineNetAmt());
		}
		else
		{
			setLineTotalAmt(getLineNetAmt().add(TaxAmt));
		}
		
		super.setTaxAmt(TaxAmt);
	}    // setTaxAmt

	/**
	 * Calculate Extended Amt. May or may not include tax
	 */
	@Deprecated
	public void setLineNetAmt()
	{
		// metas 05129: removed duplicate code, using metas version
		final I_C_InvoiceLine invoiceLine = create(this, I_C_InvoiceLine.class);
		Services.get(IInvoiceBL.class).setLineNetAmt(invoiceLine);
	}    // setLineNetAmt

	/**
	 * Get Charge
	 *
	 * @return product or null
	 */
	public MCharge getCharge()
	{
		if (m_charge == null && getC_Charge_ID() != 0)
		{
			m_charge = MCharge.get(getCtx(), getC_Charge_ID());
		}
		return m_charge;
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
	}    // getTax

	/**
	 * Set Qty Invoiced/Entered.
	 *
	 * @param Qty Invoiced/Ordered
	 */
	public void setQty(int Qty)
	{
		setQty(new BigDecimal(Qty));
	}    // setQtyInvoiced

	/**
	 * Set Qty Invoiced
	 *
	 * @param Qty Invoiced/Entered
	 */
	public void setQty(BigDecimal Qty)
	{
		setQtyEntered(Qty);
		setQtyInvoiced(getQtyEntered());
	}    // setQtyInvoiced

	/**
	 * Set Qty Entered - enforce entered UOM
	 *
	 * @param QtyEntered
	 */
	@Override
	public void setQtyEntered(BigDecimal QtyEntered)
	{
		if (QtyEntered != null && getC_UOM_ID() != 0)
		{
			int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
			QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyEntered(QtyEntered);
	}    // setQtyEntered

	/**
	 * Set Qty Invoiced - enforce Product UOM
	 *
	 * @param QtyInvoiced
	 */
	@Override
	public void setQtyInvoiced(BigDecimal QtyInvoiced)
	{
		MProduct product = getProduct();
		if (QtyInvoiced != null && product != null)
		{
			int precision = product.getUOMPrecision();
			QtyInvoiced = QtyInvoiced.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyInvoiced(QtyInvoiced);
	}    // setQtyInvoiced

	/**
	 * Set Product
	 *
	 * @param product product
	 */
	public void setProduct(MProduct product)
	{
		m_product = product;
		if (m_product != null)
		{
			setM_Product_ID(m_product.getM_Product_ID());
			setC_UOM_ID(m_product.getC_UOM_ID());
			// note: we set the UOM allright, but not the price-UOM, because that would only make sense if we also set the prices themselves.
		}
		else
		{
			setM_Product_ID(0);
			setC_UOM_ID(0);
		}
		setM_AttributeSetInstance_ID(0);
	}    // setProduct

	/**
	 * Set M_Product_ID
	 *
	 * @param M_Product_ID product
	 * @param setUOM       set UOM from product
	 */
	public void setM_Product_ID(int M_Product_ID, boolean setUOM)
	{
		if (setUOM)
		{
			setProduct(MProduct.get(getCtx(), M_Product_ID));
		}
		else
		{
			super.setM_Product_ID(M_Product_ID);
		}
		setM_AttributeSetInstance_ID(0);
	}    // setM_Product_ID

	/**
	 * Set Product and UOM
	 *
	 * @param M_Product_ID product
	 * @param C_UOM_ID     uom
	 */
	public void setM_Product_ID(int M_Product_ID, int C_UOM_ID)
	{
		super.setM_Product_ID(M_Product_ID);
		super.setC_UOM_ID(C_UOM_ID);
		setM_AttributeSetInstance_ID(0);
	}    // setM_Product_ID

	/**
	 * Get Product
	 *
	 * @return product or null
	 */
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
		{
			m_product = MProduct.get(getCtx(), getM_Product_ID());
		}
		return m_product;
	}    // getProduct

	/**
	 * Get C_Project_ID
	 *
	 * @return project
	 */
	@Override
	public int getC_Project_ID()
	{
		int ii = super.getC_Project_ID();
		if (ii == 0)
		{
			ii = getParent().getC_Project_ID();
		}
		return ii;
	}    // getC_Project_ID

	/**
	 * Get C_Activity_ID
	 *
	 * @return Activity
	 */
	@Override
	public int getC_Activity_ID()
	{
		int ii = super.getC_Activity_ID();
		if (ii == 0)
		{
			ii = getParent().getC_Activity_ID();
		}
		return ii;
	}    // getC_Activity_ID

	/**
	 * Get C_Campaign_ID
	 *
	 * @return Campaign
	 */
	@Override
	public int getC_Campaign_ID()
	{
		int ii = super.getC_Campaign_ID();
		if (ii == 0)
		{
			ii = getParent().getC_Campaign_ID();
		}
		return ii;
	}    // getC_Campaign_ID

	/**
	 * Get User2_ID
	 *
	 * @return User2
	 */
	@Override
	public int getUser1_ID()
	{
		int ii = super.getUser1_ID();
		if (ii == 0)
		{
			ii = getParent().getUser1_ID();
		}
		return ii;
	}    // getUser1_ID

	/**
	 * Get User2_ID
	 *
	 * @return User2
	 */
	@Override
	public int getUser2_ID()
	{
		int ii = super.getUser2_ID();
		if (ii == 0)
		{
			ii = getParent().getUser2_ID();
		}
		return ii;
	}    // getUser2_ID

	/**
	 * Get AD_OrgTrx_ID
	 *
	 * @return trx org
	 */
	@Override
	public int getAD_OrgTrx_ID()
	{
		int ii = super.getAD_OrgTrx_ID();
		if (ii == 0)
		{
			ii = getParent().getAD_OrgTrx_ID();
		}
		return ii;
	}    // getAD_OrgTrx_ID

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MInvoiceLine[")
				.append(get_ID()).append(",").append(getLine())
				.append(",QtyInvoiced=").append(getQtyInvoiced())
				.append(",LineNetAmt=").append(getLineNetAmt())
				.append("]");
		return sb.toString();
	}    // toString

	/**
	 * Get (Product/Charge) Name
	 *
	 * @return name
	 */
	public String getName()
	{
		if (m_name == null)
		{
			String sql = "SELECT COALESCE (p.Name, c.Name) "
					+ "FROM C_InvoiceLine il"
					+ " LEFT OUTER JOIN M_Product p ON (il.M_Product_ID=p.M_Product_ID)"
					+ " LEFT OUTER JOIN C_Charge C ON (il.C_Charge_ID=c.C_Charge_ID) "
					+ "WHERE C_InvoiceLine_ID=?";
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, getC_InvoiceLine_ID());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					m_name = rs.getString(1);
				}
				rs.close();
				pstmt.close();
				pstmt = null;
				if (m_name == null)
				{
					m_name = "??";
				}
			}
			catch (Exception e)
			{
				log.error("getName", e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
					{
						pstmt.close();
					}
				}
				catch (Exception e)
				{
				}
				pstmt = null;
			}
		}
		return m_name;
	}    // getName

	/**
	 * Set Temporary (cached) Name
	 *
	 * @param tempName Cached Name
	 */
	public void setName(String tempName)
	{
		m_name = tempName;
	}    // setName

	/**
	 * Get Description Text. For jsp access (vs. isDescription)
	 *
	 * @return description
	 */
	public String getDescriptionText()
	{
		return super.getDescription();
	}    // getDescriptionText

	/**
	 * Get Currency Precision
	 *
	 * @return precision
	 * @deprecated Please use {@link IInvoiceBL#getAmountPrecision(org.compiere.model.I_C_InvoiceLine)}.
	 */
	@Deprecated
	private CurrencyPrecision getAmountPrecision()
	{
		return Services.get(IInvoiceBL.class).getAmountPrecision(this);
	}    // getPrecision

	/**
	 * Is Tax Included in Amount
	 *
	 * @return true if tax is included
	 * @deprecated Please use {@link IInvoiceBL#isTaxIncluded(org.compiere.model.I_C_InvoiceLine)}.
	 */
	@Deprecated
	public boolean isTaxIncluded()
	{
		return Services.get(IInvoiceBL.class).isTaxIncluded(this);
	}    // isTaxIncluded

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord
	 * @return true if save
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		log.debug("New=" + newRecord);
		if (newRecord
				&& Services.get(IInvoiceBL.class).isComplete(getC_Invoice()))
		{
			throw new AdempiereException("@ParentComplete@ @C_InvoiceLine_ID@");
		}

		// Re-set invoice header (need to update m_IsSOTrx flag) - phib [ 1686773 ]
		setInvoice(getC_Invoice());
		// Charge
		if (getC_Charge_ID() != 0)
		{
			if (getM_Product_ID() != 0)
			{
				setM_Product_ID(0);
			}
		}
		else
		{

			if (!m_priceSet
					&& ZERO.compareTo(getPriceActual()) == 0
					&& ZERO.compareTo(getPriceList()) == 0
					&& ZERO.compareTo(getQtyInvoiced()) == 0) // 04836: In case of full discount, don't recalculate.
			{
				setPrice();
			}

		}

		if (getC_TaxCategory_ID() <= 0)
		{
			final I_C_OrderLine orderLine = create(getC_OrderLine(), I_C_OrderLine.class);

			final int taxCategoryID;

			if (orderLine != null)
			{
				taxCategoryID = orderLine.getC_TaxCategory_ID();
			}

			else
			{
				final I_C_InvoiceLine invoiceLine = create(this, I_C_InvoiceLine.class);

				taxCategoryID = TaxCategoryId.toRepoId(Services.get(IInvoiceBL.class).getTaxCategoryId(invoiceLine));
			}

			setC_TaxCategory_ID(taxCategoryID);
		}

		// Set Tax
		if (getC_Tax_ID() == 0)
		{
			setTax();
		}

		// Get Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_InvoiceLine WHERE C_Invoice_ID=?";
			int ii = DB.getSQLValue(get_TrxName(), sql, getC_Invoice_ID());
			setLine(ii);
		}
		// UOM
		if (getC_UOM_ID() == 0)
		{
			int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
			if (C_UOM_ID > 0)
			{
				setC_UOM_ID(C_UOM_ID);
			}
		}

		// price UOM
		// note: we do not the price-UOM here, because that would only make sense if we also set the prices themselves.

		// Qty Precision
		if (newRecord || is_ValueChanged("QtyEntered"))
		{
			setQtyEntered(getQtyEntered());
		}
		if (newRecord || is_ValueChanged("QtyInvoiced"))
		{
			setQtyInvoiced(getQtyInvoiced());
		}

		// Calculations & Rounding
		setLineNetAmt();
		// TaxAmt recalculations should be done if the TaxAmt is zero
		// or this is an Invoice(Customer) - teo_sarca, globalqss [ 1686773 ]
		if (m_IsSOTrx || getTaxAmt().signum() == 0)
		{
			setTaxAmt();
		}
		//
		return true;
	}    // beforeSave

	/**
	 * Recalculate invoice tax
	 *
	 * @param oldTax true if the old C_Tax_ID should be used
	 * @return true if success, false otherwise
	 * @author teo_sarca [ 1583825 ]
	 */
	private boolean updateInvoiceTax(final boolean oldTax)
	{
		// NOTE: keep in sync with org.compiere.model.MOrderLine.updateOrderTax(boolean)

		final String trxName = get_TrxName();
		final CurrencyPrecision taxPrecision = Services.get(IInvoiceBL.class).getTaxPrecision(this);
		final MInvoiceTax tax = MInvoiceTax.get(this, taxPrecision.toInt(), oldTax, trxName);
		if (tax == null)
		{
			return true;
		}

		if (!tax.calculateTaxFromLines())
		{
			return false;
		}

		//
		// If tax has invoice lines behind => fine, save it
		if (tax.isActive())
		{
			InterfaceWrapperHelper.save(tax, trxName);
		}
		// Tax has no longer any invoice lines behind => deleted it if it's not new
		else
		{
			if (!InterfaceWrapperHelper.isNew(tax))
			{
				InterfaceWrapperHelper.delete(tax);
			}
		}

		return true;
	}

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success   success
	 * @return saved
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return success;
		}
		if (!newRecord && is_ValueChanged("C_Tax_ID"))
		{
			// Recalculate Tax for old Tax
			if (!updateInvoiceTax(true))
			{
				return false;
			}
		}
		return updateHeaderTax();
	}    // afterSave

	/**
	 * After Delete
	 *
	 * @param success success
	 * @return deleted
	 */
	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
		{
			return success;
		}

		// reset shipment line invoiced flag
		if (getM_InOutLine_ID() > 0)
		{
			MInOutLine sLine = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName());
			sLine.setIsInvoiced(false);
			sLine.saveEx();
		}

		return updateHeaderTax();
	}    // afterDelete

	/**
	 * Update Tax & Header
	 *
	 * @return true if header updated with tax
	 */
	private boolean updateHeaderTax()
	{
		// Update header only if the document is not processed - teo_sarca BF [ 2317305 ]
		if (isProcessed() && !is_ValueChanged(COLUMNNAME_Processed))
		{
			return true;
		}

		// Recalculate Tax for this Tax
		if (!updateInvoiceTax(false))
		{
			return false;
		}

		// Update Invoice Header: TotalLines
		{
			final String sql = "UPDATE C_Invoice i"
					+ " SET TotalLines="
					+ " (SELECT COALESCE(SUM(LineNetAmt),0) FROM C_InvoiceLine il WHERE i.C_Invoice_ID=il.C_Invoice_ID) "
					+ " WHERE C_Invoice_ID=?";
			final int no = DB.executeUpdateEx(sql, new Object[] { getC_Invoice_ID() }, get_TrxName());
			if (no != 1)
			{
				throw new AdempiereException("Updating TotalLines failed; updated records=" + no + "; sql=" + sql);
			}
		}

		//
		// Update Invoice Header: GrandTotal
		{
			final String sql = "UPDATE C_Invoice i "
					+ " SET GrandTotal=TotalLines+"
					// SUM up C_InvoiceTax.TaxAmt only for those lines which does not have Tax Included
					+ " (SELECT COALESCE(SUM(TaxAmt),0) FROM C_InvoiceTax it WHERE i.C_Invoice_ID=it.C_Invoice_ID AND it.IsActive='Y' AND it.IsTaxIncluded='N') "
					+ " WHERE C_Invoice_ID=?";
			final int no = DB.executeUpdateEx(sql, new Object[] { getC_Invoice_ID() }, get_TrxName());
			if (no != 1)
			{
				throw new AdempiereException("Updating GrandTotal failed; updated records=" + no + "; sql=" + sql);
			}
		}
		m_parent = null;

		return true;
	}    // updateHeaderTax

	/**************************************************************************
	 * Allocate Landed Costs
	 *
	 * @return error message or ""
	 */
	public String allocateLandedCosts()
	{
		if (isProcessed())
		{
			return "Processed";
		}
		MLandedCost[] lcs = MLandedCost.getLandedCosts(this);
		if (lcs.length == 0)
		{
			return "";
		}
		String sql = "DELETE FROM C_LandedCostAllocation WHERE C_InvoiceLine_ID=" + getC_InvoiceLine_ID();
		int no = DB.executeUpdate(sql, get_TrxName());
		if (no != 0)
		{
			log.debug("Deleted #" + no);
		}

		int inserted = 0;
		// *** Single Criteria ***
		if (lcs.length == 1)
		{
			MLandedCost lc = lcs[0];
			if (lc.getM_InOut_ID() != 0)
			{
				// Create List
				ArrayList<MInOutLine> list = new ArrayList<>();
				MInOut ship = new MInOut(getCtx(), lc.getM_InOut_ID(), get_TrxName());
				for (MInOutLine line : ship.getLines())
				{
					if (line.isDescription() || line.getM_Product_ID() == 0)
					{
						continue;
					}
					if (lc.getM_Product_ID() == 0
							|| lc.getM_Product_ID() == line.getM_Product_ID())
					{
						list.add(line);
					}
				}
				if (list.size() == 0)
				{
					return "No Matching Lines (with Product) in Shipment";
				}
				// Calculate total & base
				BigDecimal total = ZERO;
				for (MInOutLine iol : list)
				{
					total = total.add(iol.getBase(lc.getLandedCostDistribution()));
				}
				if (total.signum() == 0)
				{
					return "Total of Base values is 0 - " + lc.getLandedCostDistribution();
				}
				// Create Allocations
				for (MInOutLine iol : list)
				{
					MLandedCostAllocation lca = new MLandedCostAllocation(this, lc.getM_CostElement_ID());
					lca.setM_Product_ID(iol.getM_Product_ID());
					lca.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
					BigDecimal base = iol.getBase(lc.getLandedCostDistribution());
					lca.setBase(base);
					// MZ Goodwill
					// add set Qty from InOutLine
					lca.setQty(iol.getMovementQty());
					// end MZ
					if (base.signum() != 0)
					{
						double result = getLineNetAmt().multiply(base).doubleValue();
						result /= total.doubleValue();
						lca.setAmt(result, getAmountPrecision());
					}
					if (!lca.save())
					{
						return "Cannot save line Allocation = " + lca;
					}
					inserted++;
				}
				log.debug("Inserted " + inserted);
				allocateLandedCostRounding();
				return "";
			}
			// Single Line
			else if (lc.getM_InOutLine_ID() != 0)
			{
				MInOutLine iol = new MInOutLine(getCtx(), lc.getM_InOutLine_ID(), get_TrxName());
				if (iol.isDescription() || iol.getM_Product_ID() == 0)
				{
					return "Invalid Receipt Line - " + iol;
				}
				MLandedCostAllocation lca = new MLandedCostAllocation(this, lc.getM_CostElement_ID());
				lca.setM_Product_ID(iol.getM_Product_ID());
				lca.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
				lca.setAmt(getLineNetAmt());
				// MZ Goodwill
				// add set Qty from InOutLine
				lca.setQty(iol.getMovementQty());
				// end MZ
				if (lca.save())
				{
					return "";
				}
				return "Cannot save single line Allocation = " + lc;
			}
			// Single Product
			else if (lc.getM_Product_ID() != 0)
			{
				MLandedCostAllocation lca = new MLandedCostAllocation(this, lc.getM_CostElement_ID());
				lca.setM_Product_ID(lc.getM_Product_ID());    // No ASI
				lca.setAmt(getLineNetAmt());
				if (lca.save())
				{
					return "";
				}
				return "Cannot save Product Allocation = " + lc;
			}
			else
			{
				return "No Reference for " + lc;
			}
		}

		// *** Multiple Criteria ***
		String LandedCostDistribution = lcs[0].getLandedCostDistribution();
		int M_CostElement_ID = lcs[0].getM_CostElement_ID();
		for (MLandedCost lc2 : lcs)
		{
			MLandedCost lc = lc2;
			if (!LandedCostDistribution.equals(lc.getLandedCostDistribution()))
			{
				return "Multiple Landed Cost Rules must have consistent Landed Cost Distribution";
			}
			if (lc.getM_Product_ID() != 0 && lc.getM_InOut_ID() == 0 && lc.getM_InOutLine_ID() == 0)
			{
				return "Multiple Landed Cost Rules cannot directly allocate to a Product";
			}
			if (M_CostElement_ID != lc.getM_CostElement_ID())
			{
				return "Multiple Landed Cost Rules cannot different Cost Elements";
			}
		}
		// Create List
		ArrayList<MInOutLine> list = new ArrayList<>();
		for (MLandedCost lc : lcs)
		{
			if (lc.getM_InOut_ID() != 0 && lc.getM_InOutLine_ID() == 0)        // entire receipt
			{
				MInOut ship = new MInOut(getCtx(), lc.getM_InOut_ID(), get_TrxName());
				MInOutLine[] lines = ship.getLines();
				for (MInOutLine line : lines)
				{
					if (line.isDescription()        // decription or no product
							|| line.getM_Product_ID() == 0)
					{
						continue;
					}
					if (lc.getM_Product_ID() == 0        // no restriction or product match
							|| lc.getM_Product_ID() == line.getM_Product_ID())
					{
						list.add(line);
					}
				}
			}
			else if (lc.getM_InOutLine_ID() != 0)    // receipt line
			{
				MInOutLine iol = new MInOutLine(getCtx(), lc.getM_InOutLine_ID(), get_TrxName());
				if (!iol.isDescription() && iol.getM_Product_ID() != 0)
				{
					list.add(iol);
				}
			}
		}
		if (list.size() == 0)
		{
			return "No Matching Lines (with Product)";
		}
		// Calculate total & base
		BigDecimal total = ZERO;
		for (MInOutLine iol : list)
		{
			total = total.add(iol.getBase(LandedCostDistribution));
		}
		if (total.signum() == 0)
		{
			return "Total of Base values is 0 - " + LandedCostDistribution;
		}
		// Create Allocations
		for (MInOutLine iol : list)
		{
			MLandedCostAllocation lca = new MLandedCostAllocation(this, lcs[0].getM_CostElement_ID());
			lca.setM_Product_ID(iol.getM_Product_ID());
			lca.setM_AttributeSetInstance_ID(iol.getM_AttributeSetInstance_ID());
			BigDecimal base = iol.getBase(LandedCostDistribution);
			lca.setBase(base);
			// MZ Goodwill
			// add set Qty from InOutLine
			lca.setQty(iol.getMovementQty());
			// end MZ
			if (base.signum() != 0)
			{
				double result = getLineNetAmt().multiply(base).doubleValue();
				result /= total.doubleValue();
				lca.setAmt(result, getAmountPrecision());
			}
			if (!lca.save())
			{
				return "Cannot save line Allocation = " + lca;
			}
			inserted++;
		}

		log.debug("Inserted " + inserted);
		allocateLandedCostRounding();
		return "";
	}    // allocate Costs

	/**
	 * Allocate Landed Cost - Enforce Rounding
	 */
	private void allocateLandedCostRounding()
	{
		MLandedCostAllocation[] allocations = MLandedCostAllocation.getOfInvoiceLine(
				getCtx(), getC_InvoiceLine_ID(), get_TrxName());
		MLandedCostAllocation largestAmtAllocation = null;
		BigDecimal allocationAmt = ZERO;
		for (MLandedCostAllocation allocation : allocations)
		{
			if (largestAmtAllocation == null
					|| allocation.getAmt().compareTo(largestAmtAllocation.getAmt()) > 0)
			{
				largestAmtAllocation = allocation;
			}
			allocationAmt = allocationAmt.add(allocation.getAmt());
		}
		BigDecimal difference = getLineNetAmt().subtract(allocationAmt);
		if (difference.signum() != 0)
		{
			largestAmtAllocation.setAmt(largestAmtAllocation.getAmt().add(difference));
			largestAmtAllocation.save();
			log.debug("Difference=" + difference
							  + ", C_LandedCostAllocation_ID=" + largestAmtAllocation.getC_LandedCostAllocation_ID()
							  + ", Amt" + largestAmtAllocation.getAmt());
		}
	}    // allocateLandedCostRounding

	// MZ Goodwill

	/**
	 * Get LandedCost of InvoiceLine
	 *
	 * @param whereClause starting with AND
	 * @return landedCost
	 */
	public MLandedCost[] getLandedCost(String whereClause)
	{
		ArrayList<MLandedCost> list = new ArrayList<>();
		String sql = "SELECT * FROM C_LandedCost WHERE C_InvoiceLine_ID=? ";
		if (whereClause != null)
		{
			sql += whereClause;
		}
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_InvoiceLine_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MLandedCost lc = new MLandedCost(getCtx(), rs, get_TrxName());
				list.add(lc);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error("getLandedCost", e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
				{
					pstmt.close();
				}
			}
			catch (Exception e)
			{
			}
			pstmt = null;
		}

		//
		MLandedCost[] landedCost = new MLandedCost[list.size()];
		list.toArray(landedCost);
		return landedCost;
	}    // getLandedCost

	/**
	 * Copy LandedCost From other InvoiceLine.
	 *
	 * @param otherInvoiceLine invoiceline
	 * @return number of lines copied
	 */
	public int copyLandedCostFrom(MInvoiceLine otherInvoiceLine)
	{
		if (otherInvoiceLine == null)
		{
			return 0;
		}
		MLandedCost[] fromLandedCosts = otherInvoiceLine.getLandedCost(null);
		int count = 0;
		for (MLandedCost fromLandedCost : fromLandedCosts)
		{
			MLandedCost landedCost = new MLandedCost(getCtx(), 0, get_TrxName());
			PO.copyValues(fromLandedCost, landedCost, fromLandedCost.getAD_Client_ID(), fromLandedCost.getAD_Org_ID());
			landedCost.setC_InvoiceLine_ID(getC_InvoiceLine_ID());
			landedCost.set_ValueNoCheck("C_LandedCost_ID", I_ZERO);    // new
			if (landedCost.save(get_TrxName()))
			{
				count++;
			}
		}
		if (fromLandedCosts.length != count)
		{
			log.error("LandedCost difference - From=" + fromLandedCosts.length + " <> Saved=" + count);
		}
		return count;
	}    // copyLinesFrom

	// end MZ

	public void setRMALine(MRMALine rmaLine)
	{
		// Check if this invoice is CreditMemo - teo_sarca [ 2804142 ]
		final I_C_Invoice invoice = getC_Invoice();
		if (!Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			throw new AdempiereException("InvoiceNotCreditMemo");
		}
		setAD_Org_ID(rmaLine.getAD_Org_ID());
		setM_RMALine_ID(rmaLine.getM_RMALine_ID());
		setDescription(rmaLine.getDescription());
		setLine(rmaLine.getLine());
		setC_Charge_ID(rmaLine.getC_Charge_ID());
		setM_Product_ID(rmaLine.getM_Product_ID());
		setC_UOM_ID(rmaLine.getC_UOM_ID());

		// 07442
		// Do not change the tax if it was already set

		final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
		final Tax tax = taxDAO.getTaxByIdOrNull(getC_Tax_ID());
		if (tax == null)
		{
			setC_Tax_ID(rmaLine.getC_Tax_ID());

			final I_C_Tax rmaTax = create(getCtx(), rmaLine.getC_Tax_ID(), I_C_Tax.class, get_TrxName());
			setC_TaxCategory_ID(rmaTax.getC_TaxCategory_ID());
		}
		else
		{
			setC_TaxCategory_ID(tax.getTaxCategoryId().getRepoId());
		}

		setPrice(rmaLine.getAmt());
		BigDecimal qty = rmaLine.getQty();
		if (rmaLine.getQtyInvoiced() != null)
		{
			qty = qty.subtract(rmaLine.getQtyInvoiced());
		}
		setQty(qty);
		setLineNetAmt();
		setTaxAmt();
		setLineTotalAmt(rmaLine.getLineNetAmt());
		setC_Project_ID(rmaLine.getC_Project_ID());
		// 07442
		// Do not change the activity if it was already set

		final int activityId = getC_Activity_ID();
		if (activityId <= 0)
		{
			setC_Activity_ID(rmaLine.getC_Activity_ID());
		}
		setC_Campaign_ID(rmaLine.getC_Campaign_ID());

		final I_C_InvoiceLine il = create(this, I_C_InvoiceLine.class);
		// task FRESH-273
		il.setIsPackagingMaterial(true);
	}

	@Deprecated
	public StockQtyAndUOMQty getMatchedQty()
	{
		return Services.get(IMatchInvDAO.class).retrieveQtyMatched(this);
	}

	// metas: begin
	@Override
	public I_C_Invoice getC_Invoice()
	{
		return getParent();
	}

	// metas: end
}    // MInvoiceLine
