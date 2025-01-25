/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.compiere.model;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.exceptions.BPartnerNoBillToAddressException;
import de.metas.bpartner.exceptions.BPartnerNoShipToAddressException;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.location.DocumentLocation;
import de.metas.document.sequence.IDocumentNoBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.freighcost.FreightCostRule;
import de.metas.i18n.IMsgBL;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderId;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.order.payment_reservation.OrderPaymentReservationCreateResult;
import de.metas.order.payment_reservation.OrderPaymentReservationService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IStorageBL;
import de.metas.product.ProductId;
import de.metas.report.DocumentReportService;
import de.metas.report.ReportResultData;
import de.metas.report.StandardDocumentReportType;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxUtils;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Order Model.
 * Please do not set DocStatus and C_DocType_ID directly.
 * They are set in the process() method.
 * Use DocAction and C_DocTypeTarget_ID instead.
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * <li>FR [ 2520591 ] Support multiples calendar for Org
 * @author Teo Sarca, www.arhipac.ro
 * <li>BF [ 2419978 ] Voiding PO, requisition don't set on NULL
 * <li>BF [ 2892578 ] Order should autoset only active price lists
 * https://sourceforge.net/tracker/?func=detail&aid=2892578&group_id=176962&atid=879335
 * @author Michael Judd, www.akunagroup.com
 * <li>BF [ 2804888 ] Incorrect reservation of products with attributes
 * @version $Id: MOrder.java,v 1.5 2006/10/06 00:42:24 jjanke Exp $
 * @see [ http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962 ]
 */
public class MOrder extends X_C_Order implements IDocument
{
	private static final long serialVersionUID = -1575104995897726572L;

	private static final String NO_DELIVARABLE_LINES_FOUND = "NoDeliverableLinesFound";

	private final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param C_Order_ID order to load, (0 create new order)
	 * @param trxName trx name
	 */
	public MOrder(final Properties ctx, final int C_Order_ID, final String trxName)
	{
		super(ctx, C_Order_ID, trxName);
		// New
		if (is_new())
		{
			setDocStatus(DocStatus.Drafted.getCode());
			setDocAction(DOCACTION_Prepare);
			//
			setDeliveryRule(DeliveryRule.AVAILABILITY.getCode());
			setFreightCostRule(FreightCostRule.FreightIncluded.getCode());
			// The invoiceRule should be already set. Don't change it.
			if(getInvoiceRule() == null)
			{
				setInvoiceRule(INVOICERULE_AfterDelivery);
			}
			setPaymentRule(PaymentRule.OnCredit.getCode());
			setPriorityRule(PRIORITYRULE_Medium);
			setDeliveryViaRule(DeliveryViaRule.Pickup.getCode());
			//
			setIsDiscountPrinted(false);
			setIsSelected(false);
			setIsTaxIncluded(false);
			setIsSOTrx(true);
			setIsDropShip(false);
			setSendEMail(false);
			//
			setIsApproved(false);
			setIsPrinted(false);
			setIsCreditApproved(false);
			setIsDelivered(false);
			setIsInvoiced(false);
			setIsTransferred(false);
			setIsSelfService(false);
			//
			super.setProcessed(false);
			setProcessing(false);
			setPosted(false);

			setDateAcct(new Timestamp(System.currentTimeMillis()));
			setDatePromised(de.metas.common.util.time.SystemTime.asDayTimestamp()); // task 06269 (see KurzBeschreibung)
			setDateOrdered(new Timestamp(System.currentTimeMillis()));

			setChargeAmt(BigDecimal.ZERO);
			setTotalLines(BigDecimal.ZERO);
			setGrandTotal(BigDecimal.ZERO);
		}
	}    // MOrder

	/**************************************************************************
	 * Project Constructor
	 *
	 * @param project Project to create Order from
	 * @param IsSOTrx sales order
	 * @param DocSubType if SO DocType Target (default DocSubType_OnCredit)
	 */
	public MOrder(final MProject project, final boolean IsSOTrx, final String DocSubType)
	{
		this(project.getCtx(), 0, project.get_TrxName());
		setAD_Client_ID(project.getAD_Client_ID());
		setAD_Org_ID(project.getAD_Org_ID());
		setC_Campaign_ID(project.getC_Campaign_ID());
		setSalesRep_ID(project.getSalesRep_ID());
		//
		setC_Project_ID(project.getC_Project_ID());
		setDescription(project.getName());
		Timestamp ts = project.getDateContract();
		if (ts != null)
		{
			setDateOrdered(ts);
		}
		ts = project.getDateFinish();
		if (ts != null)
		{
			setDatePromised(ts);
		}
		//
		setC_BPartner_ID(project.getC_BPartner_ID());
		setC_BPartner_Location_ID(project.getC_BPartner_Location_ID());
		setAD_User_ID(project.getAD_User_ID());
		//
		setM_Warehouse_ID(project.getM_Warehouse_ID());
		setM_PriceList_ID(project.getM_PriceList_ID());
		setC_PaymentTerm_ID(project.getC_PaymentTerm_ID());
		//
		setIsSOTrx(IsSOTrx);
		if (IsSOTrx)
		{
			if (DocSubType == null || DocSubType.length() == 0)
			{
				orderBL.setSODocTypeTargetId(this, DocSubType_OnCredit);
			}
			else
			{
				orderBL.setSODocTypeTargetId(this, DocSubType);
			}
		}
		else
		{
			orderBL.setDefaultDocTypeTargetId(this);
		}
	}    // MOrder

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set record
	 * @param trxName transaction
	 */
	public MOrder(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    // MOrder

	/**
	 * Order Lines
	 */
	private ImmutableList<MOrderLine> _lines = null;
	/**
	 * Tax Lines
	 */
	private MOrderTax[] m_taxes = null;

	/**
	 * Overwrite Client/Org if required
	 *
	 * @param AD_Client_ID client
	 * @param AD_Org_ID    org
	 */
	@Override
	public void setClientOrg(final int AD_Client_ID, final int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}    // setClientOrg

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(final String description)
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
	}    // addDescription

	/**
	 * Set Business Partner (Ship+Bill)
	 *
	 * @param C_BPartner_ID bpartner
	 */
	@Override
	public void setC_BPartner_ID(final int C_BPartner_ID)
	{
		super.setC_BPartner_ID(C_BPartner_ID);
		super.setBill_BPartner_ID(C_BPartner_ID);
	}    // setC_BPartner_ID

	/**
	 * Set Business Partner Location (Ship+Bill)
	 *
	 * @param C_BPartner_Location_ID bp location
	 */
	@Override
	public void setC_BPartner_Location_ID(final int C_BPartner_Location_ID)
	{
		super.setC_BPartner_Location_ID(C_BPartner_Location_ID);
		super.setBill_Location_ID(C_BPartner_Location_ID);
	}    // setC_BPartner_Location_ID

	/**
	 * Set Business Partner Contact (Ship+Bill)
	 *
	 * @param AD_User_ID user
	 */
	@Override
	public void setAD_User_ID(final int AD_User_ID)
	{
		super.setAD_User_ID(AD_User_ID);
		super.setBill_User_ID(AD_User_ID);
	}    // setAD_User_ID

	/**
	 * Set Ship Business Partner
	 *
	 * @param C_BPartner_ID bpartner
	 */
	public void setShip_BPartner_ID(final int C_BPartner_ID)
	{
		super.setC_BPartner_ID(C_BPartner_ID);
	}    // setShip_BPartner_ID

	/**
	 * Set Ship Business Partner Location
	 *
	 * @param C_BPartner_Location_ID bp location
	 */
	public void setShip_Location_ID(final int C_BPartner_Location_ID)
	{
		super.setC_BPartner_Location_ID(C_BPartner_Location_ID);
	}    // setShip_Location_ID

	/**
	 * Set Ship Business Partner Contact
	 *
	 * @param AD_User_ID user
	 */
	public void setShip_User_ID(final int AD_User_ID)
	{
		super.setAD_User_ID(AD_User_ID);
	}    // setShip_User_ID

	/**
	 * Set Warehouse
	 *
	 * @param M_Warehouse_ID warehouse
	 */
	@Override
	public void setM_Warehouse_ID(final int M_Warehouse_ID)
	{
		super.setM_Warehouse_ID(M_Warehouse_ID);
	}    // setM_Warehouse_ID

	/**
	 * Set Drop Ship
	 *
	 * @param IsDropShip drop ship
	 */
	@Override
	public void setIsDropShip(final boolean IsDropShip)
	{
		super.setIsDropShip(IsDropShip);
	}    // setIsDropShip

	/*************************************************************************/

	/**
	 * Sales Order Sub Type - SO
	 */
	public static final String DocSubType_Standard = "SO";
	/**
	 * Sales Order Sub Type - OB
	 */
	public static final String DocSubType_Quotation = "OB";
	/**
	 * Sales Order Sub Type - ON
	 */
	public static final String DocSubType_Proposal = "ON";
	/**
	 * Sales Order Sub Type - PR
	 */
	public static final String DocSubType_Prepay = "PR";
	/**
	 * Sales Order Sub Type - WR
	 */
	public static final String DocSubType_POS = "WR";
	/**
	 * Sales Order Sub Type - WP
	 */
	public static final String DocSubType_Warehouse = "WP";
	/**
	 * Sales Order Sub Type - WI
	 */
	public static final String DocSubType_OnCredit = "WI";
	/**
	 * Sales Order Sub Type - RM
	 */
	public static final String DocSubType_RMA = "RM";

	/**
	 * Set Business Partner Defaults & Details.
	 * SOTrx should be set.
	 *
	 * FIXME: keep in sync / merge with de.metas.order.impl.{@link de.metas.order.impl.OrderBL#setBPartner(I_C_Order, I_C_BPartner)}
	 */
	public void setBPartner(final I_C_BPartner bp)
	{
		if (bp == null)
		{
			return;
		}

		setC_BPartner_ID(bp.getC_BPartner_ID());
		// Defaults Payment Term
		final int paymentTermID = isSOTrx() ? bp.getC_PaymentTerm_ID() : bp.getPO_PaymentTerm_ID();
		if (paymentTermID != 0)
		{
			setC_PaymentTerm_ID(paymentTermID);
		}
		//
		final int priceLisId = isSOTrx() ? bp.getM_PriceList_ID() : bp.getPO_PriceList_ID();
		if (priceLisId != 0)
		{
			setM_PriceList_ID(priceLisId);
		}
		// Default Delivery/Via Rule
		String ss = bp.getDeliveryRule();
		if (!Check.isEmpty(ss, true))
		{
			setDeliveryRule(ss);
		}
		ss = bp.getDeliveryViaRule();
		if (!Check.isEmpty(ss, true))
		{
			setDeliveryViaRule(ss);
		}
		// Default Invoice/Payment Rule

		final InvoiceRule invoiceRule = isSOTrx() ?
				InvoiceRule.ofNullableCode(bp.getInvoiceRule()) :
				InvoiceRule.ofNullableCode(bp.getPO_InvoiceRule());

		if (invoiceRule != null)
		{
			setInvoiceRule(invoiceRule.getCode());
		}

		if (isSOTrx() && Check.isNotBlank(bp.getPaymentRule()))
		{
			setPaymentRule(bp.getPaymentRule());
		}
		else if (!isSOTrx() && Check.isNotBlank(bp.getPaymentRulePO()))
		{
			setPaymentRule(bp.getPaymentRulePO());
		}

		// Sales Rep
		final int salesRepId = bp.getSalesRep_ID();
		if (salesRepId > 0)
		{
			setSalesRep_ID(salesRepId);
		}

		// Set Locations
		setShipToLocation(bp);
		setBillLocation(bp);

		// Set Contact
		final List<I_AD_User> contacts = InterfaceWrapperHelper.createList(bPartnerDAO.retrieveContacts(bp), I_AD_User.class);
		if (!contacts.isEmpty())
		{
			setAD_User_ID(contacts.get(0).getAD_User_ID());
		}
	}    // setBPartner

	private void setShipToLocation(final I_C_BPartner bp)
	{
		final I_C_BPartner_Location shipToLocation = bPartnerDAO.retrieveBPartnerLocation(BPartnerLocationQuery.builder()
				.bpartnerId(BPartnerId.ofRepoId(bp.getC_BPartner_ID()))
				.type(Type.SHIP_TO)
				.build());
		if (shipToLocation == null)
		{
			throw new BPartnerNoShipToAddressException(bp);
		}
		OrderDocumentLocationAdapterFactory.locationAdapter(this)
				.setLocationAndResetRenderedAddress(BPartnerLocationAndCaptureId.ofRecord(shipToLocation));
	}

	private void setBillLocation(final I_C_BPartner bp)
	{
		final I_C_BPartner_Location billToLocation = bPartnerDAO.retrieveBPartnerLocation(BPartnerLocationQuery.builder()
				.bpartnerId(BPartnerId.ofRepoId(bp.getC_BPartner_ID()))
				.type(Type.BILL_TO)
				.build());
		if (billToLocation == null)
		{
			throw new BPartnerNoBillToAddressException(bp);
		}
		final BPartnerId oldBPartnerId = BPartnerId.ofRepoIdOrNull(this.getBill_BPartner_ID());
		final BPartnerLocationAndCaptureId newBPartnerLocationId = BPartnerLocationAndCaptureId.ofRecord(billToLocation);
		final BPartnerContactId newContactId = BPartnerId.equals(oldBPartnerId, newBPartnerLocationId.getBpartnerId())
				? BPartnerContactId.ofRepoIdOrNull(oldBPartnerId, this.getBill_User_ID())
				: null;
		OrderDocumentLocationAdapterFactory.billLocationAdapter(this)
				.setFrom(DocumentLocation.builder()
						.bpartnerId(newBPartnerLocationId.getBpartnerId())
						.bpartnerLocationId(newBPartnerLocationId.getBpartnerLocationId())
						.locationId(newBPartnerLocationId.getLocationCaptureId())
						.contactId(newContactId)
						.build());
	}

	/**
	 * Copy Lines From other Order
	 *
	 * @param otherOrder order
	 * @param counter    set counter info
	 * @param copyASI    copy line attributes Attribute Set Instance, Resaouce Assignment
	 * @return number of lines copied
	 */
	public int copyLinesFrom(
			final MOrder otherOrder,
			final boolean counter,
			final boolean copyASI)
	{
		if (isProcessed() || isPosted() || otherOrder == null)
		{
			return 0;
		}
		final List<MOrderLine> fromLines = otherOrder.getLines();
		int count = 0;
		for (final MOrderLine fromLine : fromLines)
		{
			count = count + copyLineFrom(counter, copyASI, fromLine);
		}
		if (fromLines.size() != count)
		{
			log.error("Line difference - From=" + fromLines.size() + " <> Saved=" + count);
		}
		return count;
	} // copyLinesFrom

	/**
	 * Creates a new order line for this order, using the given <code>fromLine</code> as a template.
	 *
	 * @param counter  if <code>true</code>, then
	 *                 <ul>
	 *                 <li>the new other line's <code>Ref_OrderLine_ID</code> is set to <code>fromLine</code>'s ID
	 *                 <li>if <code>fromLine</code> has a product with <code>AD_Org_ID!=0</code> and of fromLine's <code>AD_Org_ID</code> is different from this order's <code>AD_Org_ID</code>, then
	 *                 {@link IProductDAO#retrieveMappedProductIdOrNull(ProductId, OrgId)} is called, to get the other org's pendant product.
	 *                 </ul>
	 * @param copyASI
	 * @param fromLine
	 * @return <code>1</code>
	 */
	public int copyLineFrom(
			final boolean counter,
			final boolean copyASI,
			final MOrderLine fromLine)
	{
		// only copy the line if it should be copied in a counter document
		if (counter)
		{
			final boolean copyCounter = Services.get(IOrderLineBL.class).isAllowedCounterLineCopy(fromLine);

			if (!copyCounter)
			{
				return 0;
			}
		}

		final MOrderLine line = new MOrderLine(this);
		PO.copyValues(fromLine, line, getAD_Client_ID(), getAD_Org_ID()); // note: this copies *all* columns, also those with IsCalculated='Y'
		line.setC_Order_ID(getC_Order_ID());
		Services.get(IOrderLineBL.class).setOrder(line, this);
		line.set_ValueNoCheck("C_OrderLine_ID", I_ZERO);    // new

		// References
		if (!copyASI)
		{
			line.setM_AttributeSetInstance_ID(0);
			line.setS_ResourceAssignment_ID(0);
		}
		else
		{
			final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
			attributeSetInstanceBL.cloneASI(line, fromLine);
		}
		if (counter)
		{
			Services.get(IOrderLineBL.class).copyOrderLineCounter(line, fromLine);

		}
		else
		{
			line.setRef_OrderLine_ID(0);
		}
		//
		line.setQtyDelivered(BigDecimal.ZERO);
		line.setQtyInvoiced(BigDecimal.ZERO);
		// task 09358: get rid of this; instead, update qtyReserved at one central place
		// line.setQtyReserved(BigDecimal.ZERO);
		line.setDateDelivered(null);
		line.setDateInvoiced(null);
		// Tax
		// MOrder otherOrder = fromLine.getC_Order ();
		// if (getC_BPartner_ID() != otherOrder.getC_BPartner_ID())
		line.setTax();        // recalculate
		//
		//
		line.setProcessed(false);
		InterfaceWrapperHelper.save(line);

		// Cross Link
		if (counter)
		{
			fromLine.setRef_OrderLine_ID(line.getC_OrderLine_ID());
			InterfaceWrapperHelper.save(fromLine);
		}

		return 1;
	}    // copyLinesFrom

	/**************************************************************************
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("C_Order[ID=").append(get_ID())
				.append("-DocumentNo=").append(getDocumentNo())
				.append(",IsSOTrx=").append(isSOTrx())
				.append(",C_DocType_ID=").append(getC_DocType_ID())
				.append(",GrandTotal=").append(getGrandTotal())
				.append("]");
		return sb.toString();
	}    // toString

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		final StringBuilder documentInfo = new StringBuilder();

		//
		// DocType
		DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(getC_DocType_ID());
		if (docTypeId == null)
		{
			docTypeId = DocTypeId.ofRepoIdOrNull(getC_DocTypeTarget_ID());
		}
		if (docTypeId != null)
		{
			final I_C_DocType docType = Services.get(IDocTypeDAO.class).getById(docTypeId);
			documentInfo.append(docType.getName());
		}

		//
		// DocumentNo
		if (documentInfo.length() > 0)
		{
			documentInfo.append(" ");
		}
		documentInfo.append(getDocumentNo());

		return documentInfo.toString();
	}    // getDocumentInfo

	@Override
	public File createPDF()
	{
		final DocumentReportService documentReportService = SpringContextHolder.instance.getBean(DocumentReportService.class);
		final ReportResultData report = documentReportService.createStandardDocumentReportData(getCtx(), StandardDocumentReportType.ORDER, getC_Order_ID());
		return report.writeToTemporaryFile(get_TableName() + get_ID());
	}    // getPDF

	/**************************************************************************
	 * Get <b>active</b> Lines of Order
	 *
	 * @param orderClause order clause
	 * @return lines
	 */
	private ImmutableList<MOrderLine> retrieveLines(final String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		final StringBuilder whereClauseFinal = new StringBuilder("(" + MOrderLine.COLUMNNAME_C_Order_ID + "=? AND " + MOrderLine.COLUMNNAME_IsActive + "='Y' )");

		final String orderBy = orderClause.length() == 0 ? MOrderLine.COLUMNNAME_Line : orderClause;

		//
		return new Query(getCtx(), MOrderLine.Table_Name, whereClauseFinal.toString(), get_TrxName())
				.setParameters(get_ID())
				.setOrderBy(orderBy)
				.listImmutable(MOrderLine.class);
	}

	public void invalidateLines()
	{
		_lines = null;
	}

	/**
	 * Get <b>active</b> Lines of Order
	 *
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	private List<MOrderLine> getLines(final boolean requery, final String orderBy)
	{
		ImmutableList<MOrderLine> lines = _lines;
		if (lines != null && !requery)
		{
			InterfaceWrapperHelper.setThreadInheritedTrxName(lines);
			return lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
		{
			orderClause += orderBy;
		}
		else
		{
			orderClause += "Line";
		}
		lines = _lines = retrieveLines(orderClause);
		return lines;
	}    // getLines

	/**
	 * Get Lines of Order.
	 * (used by web store)
	 *
	 * @return lines
	 */
	public List<MOrderLine> getLines()
	{
		return getLines(false, null);
	}    // getLines

	private List<MOrderLine> getLinesRequeryOrderedByProduct()
	{
		return getLines(true, I_C_OrderLine.COLUMNNAME_M_Product_ID);
	}

	public List<MOrderLine> getLinesRequery()
	{
		return getLines(true, null);
	}

	/**
	 * Renumber Lines
	 *
	 * @param step start and step
	 */
	public void renumberLines(final int step)
	{
		int number = step;
		final List<MOrderLine> lines = getLinesRequery();    // Line is default
		for (final MOrderLine line : lines)
		{
			line.setLine(number);
			line.save(get_TrxName());
			number += step;
		}
		invalidateLines();
	}    // renumberLines

	/**
	 * Get Taxes of Order
	 *
	 * @param requery requery
	 * @return array of taxes
	 */
	public MOrderTax[] getTaxes(final boolean requery)
	{
		if (m_taxes != null && !requery)
		{
			return m_taxes;
		}
		//
		final List<MOrderTax> list = new Query(getCtx(), MOrderTax.Table_Name, "C_Order_ID=?", get_TrxName())
				.setParameters(get_ID())
				.list(MOrderTax.class);
		m_taxes = list.toArray(new MOrderTax[list.size()]);
		return m_taxes;
	}    // getTaxes

	/**
	 * Get Invoices of Order
	 *
	 * @return invoices
	 */
	public static MInvoice[] getInvoices(@NonNull final OrderId orderId)
	{
		final String whereClause = "EXISTS (SELECT 1 FROM C_InvoiceLine il, C_OrderLine ol"
				+ " WHERE il.C_Invoice_ID=C_Invoice.C_Invoice_ID"
				+ " AND il.C_OrderLine_ID=ol.C_OrderLine_ID"
				+ " AND ol.C_Order_ID=?)";
		final List<MInvoice> list = new Query(Env.getCtx(), MInvoice.Table_Name, whereClause, ITrx.TRXNAME_ThreadInherited)
				.setParameters(orderId)
				.setOrderBy("C_Invoice_ID DESC")
				.list(MInvoice.class);
		return list.toArray(new MInvoice[list.size()]);
	}    // getInvoices

	/**
	 * Get latest Invoice of Order
	 *
	 * @return invoice id or 0
	 */
	public int getC_Invoice_ID()
	{
		final String sql = "SELECT C_Invoice_ID FROM C_Invoice "
				+ "WHERE C_Order_ID=? AND DocStatus IN ('CO','CL') "
				+ "ORDER BY C_Invoice_ID DESC";
		final int C_Invoice_ID = DB.getSQLValue(get_TrxName(), sql, get_ID());
		return C_Invoice_ID;
	}    // getC_Invoice_ID

	/**
	 * Get Shipments of Order
	 *
	 * @return shipments
	 * @deprecated Please use
	 */
	@Deprecated
	public MInOut[] getShipments()
	{
		// 05768: moved to
		final List<I_M_InOut> inOuts = Services.get(IOrderDAO.class).retrieveInOutsForMatchingOrderLines(InterfaceWrapperHelper.create(this, I_C_Order.class));
		return LegacyAdapters.convertToPOArray(inOuts, MInOut.class);
	}    // getShipments

	/**
	 * Get Document Status
	 *
	 * @return Document Status Clear Text
	 */
	public String getDocStatusName()
	{
		return MRefList.getListName(getCtx(), DocStatus.AD_REFERENCE_ID, getDocStatus());
	}    // getDocStatusName

	/**
	 * Set Processed.
	 * Propagate to Lines/Taxes
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
				+ "' WHERE C_Order_ID=" + getC_Order_ID();
		final int noLine = DB.executeUpdateEx("UPDATE C_OrderLine " + set, get_TrxName());
		final int noTax = DB.executeUpdateEx("UPDATE C_OrderTax " + set, get_TrxName());
		invalidateLines();
		m_taxes = null;
		log.debug("setProcessed - " + processed + " - Lines=" + noLine + ", Tax=" + noTax);
	}    // setProcessed

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord new
	 * @return save
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Client/Org Check
		if (getAD_Org_ID() == 0)
		{
			final int context_AD_Org_ID = Env.getAD_Org_ID(getCtx());
			if (context_AD_Org_ID != 0)
			{
				setAD_Org_ID(context_AD_Org_ID);
				log.warn("Changed Org to Context=" + context_AD_Org_ID);
			}
		}
		if (getAD_Client_ID() == 0)
		{
			m_processMsg = "AD_Client_ID = 0";
			return false;
		}

		// New Record Doc Type - make sure DocType set to 0
		if (newRecord && getC_DocType_ID() == 0)
		{
			setC_DocType_ID(0);
		}

		// Warehouse Org
		if (newRecord
				|| is_ValueChanged("AD_Org_ID") || is_ValueChanged("M_Warehouse_ID"))
		{
			// metas: allow different warehouse-org
			// MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			// if (wh.getAD_Org_ID() != getAD_Org_ID())
			// log.saveWarning("WarehouseOrgConflict", "");
			//
		}
		// Reservations in Warehouse
		if (!newRecord && is_ValueChanged("M_Warehouse_ID"))
		{
			for (final MOrderLine line : getLinesRequery())
			{
				if (!line.canChangeWarehouse(true))
				{
					return false;
				}
			}
		}

		// No Partner Info - set Template
		if (getC_BPartner_ID() <= 0)
		{
			throw new FillMandatoryException(I_C_Order.COLUMNNAME_C_BPartner_ID);
		}
		if (getC_BPartner_Location_ID() <= 0)
		{
			setBPartner(bPartnerDAO.getById(getC_BPartner_ID()));
		}
		// No Bill - get from Ship
		if (getBill_BPartner_ID() <= 0)
		{
			orderBL.setBillLocation(this);
		}

		//
		// Default Price List
		// metas: bpartner's pricing system (instead of price list)
		orderBL.setM_PricingSystem_ID(this, false); // overridePricingSystem=false

		//
		// Default Currency
		if (getC_Currency_ID() <= 0)
		{
			final PriceListId priceListId = PriceListId.ofRepoIdOrNull(getM_PriceList_ID());
			final I_M_PriceList priceList = priceListId != null
					? Services.get(IPriceListDAO.class).getById(priceListId)
					: null;

			final int currencyId = priceList == null ? -1 : priceList.getC_Currency_ID();
			if (currencyId > 0)
			{
				setC_Currency_ID(currencyId);
			}
			else
			{
				setC_Currency_ID(Env.getContextAsInt(getCtx(), "#C_Currency_ID"));
			}
		}

		// Default Sales Rep
		// NOTE: we shall not set the SalesRep from context if is not set.
		// This is not a mandatory field, so leave it like it is.

		// Default Document Type
		if (getC_DocTypeTarget_ID() <= 0)
		{
			setIsSOTrx(true);
			orderBL.setSODocTypeTargetId(this, DocSubType_Standard);
		}

		// Default Payment Term
		if (getC_PaymentTerm_ID() == 0)
		{
			final PaymentTermId defaultPaymentTermId = Services.get(IPaymentTermRepository.class)
					.getDefaultPaymentTermIdOrNull();
			if (defaultPaymentTermId != null)
			{
				setC_PaymentTerm_ID(defaultPaymentTermId.getRepoId());
			}
		}
		return true;
	}    // beforeSave

	/**
	 * After Save
	 *
	 * @param newRecord new
	 * @param success   success
	 * @return true if can be saved
	 */
	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success || newRecord)
		{
			return success;
		}

		// Propagate Description changes
		if (is_ValueChanged("Description") || is_ValueChanged("POReference"))
		{
			final String sql = DB.convertSqlToNative("UPDATE C_Invoice i"
															 + " SET (Description,POReference)="
															 + "(SELECT Description,POReference "
															 + "FROM C_Order o WHERE i.C_Order_ID=o.C_Order_ID) "
															 + "WHERE DocStatus NOT IN ('RE','CL') AND C_Order_ID=" + getC_Order_ID());
			final int no = DB.executeUpdateEx(sql, get_TrxName());
			log.debug("Description -> #" + no);
		}

		// Propagate Changes of Payment Info to existing (not reversed/closed) invoices
		if (is_ValueChanged("PaymentRule") || is_ValueChanged("C_PaymentTerm_ID")
				|| is_ValueChanged("DateAcct") || is_ValueChanged("C_Payment_ID")
				|| is_ValueChanged("C_CashLine_ID"))
		{
			final String sql = DB.convertSqlToNative("UPDATE C_Invoice i "
															 + "SET (PaymentRule,C_PaymentTerm_ID,DateAcct,C_Payment_ID,C_CashLine_ID)="
															 + "(SELECT PaymentRule,C_PaymentTerm_ID,DateAcct,C_Payment_ID,C_CashLine_ID "
															 + "FROM C_Order o WHERE i.C_Order_ID=o.C_Order_ID)"
															 + "WHERE DocStatus NOT IN ('RE','CL') AND C_Order_ID=" + getC_Order_ID());
			// Don't touch Closed/Reversed entries
			final int no = DB.executeUpdate(sql, get_TrxName());
			log.debug("Payment -> #" + no);
		}

		// Sync Lines
		if (is_ValueChanged("AD_Org_ID")
				// metas: tsa: 01767: don't sync bp and bplocation
				/*
				 * || is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_ID)
				 * || is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_Location_ID)
				 */
				|| is_ValueChanged(MOrder.COLUMNNAME_DateOrdered)
				|| is_ValueChanged(MOrder.COLUMNNAME_DatePromised)
				|| is_ValueChanged(MOrder.COLUMNNAME_M_Warehouse_ID)
				|| is_ValueChanged(MOrder.COLUMNNAME_M_Shipper_ID)
				|| is_ValueChanged(MOrder.COLUMNNAME_C_Currency_ID))
		{
			for (final MOrderLine line : getLines())
			{
				if (is_ValueChanged("AD_Org_ID"))
				{
					line.setAD_Org_ID(getAD_Org_ID());
				}
				// metas: tsa: 01767: don't sync bp and bplocation
				/*
				 * if (is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_ID))
				 * line.setC_BPartner_ID(getC_BPartner_ID());
				 * if (is_ValueChanged(MOrder.COLUMNNAME_C_BPartner_Location_ID))
				 * line.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
				 */
				if (is_ValueChanged(MOrder.COLUMNNAME_DateOrdered))
				{
					line.setDateOrdered(getDateOrdered());
				}
				if (is_ValueChanged(MOrder.COLUMNNAME_DatePromised))
				{
					line.setDatePromised(getDatePromised());
				}
				if (is_ValueChanged(MOrder.COLUMNNAME_M_Warehouse_ID))
				{
					line.setM_Warehouse_ID(getM_Warehouse_ID());
				}
				if (is_ValueChanged(MOrder.COLUMNNAME_M_Shipper_ID))
				{
					line.setM_Shipper_ID(getM_Shipper_ID());
				}
				if (is_ValueChanged(MOrder.COLUMNNAME_C_Currency_ID))
				{
					line.setC_Currency_ID(getC_Currency_ID());
				}
				line.saveEx();
			}
		}
		//
		return true;
	}    // afterSave

	/**
	 * Before Delete
	 *
	 * @return true of it can be deleted
	 */
	@Override
	protected boolean beforeDelete()
	{
		if (isProcessed())
		{
			return false;
		}

		for (final MOrderLine line : getLinesRequery())
		{
			line.setC_Order_CompensationGroup_ID(-1);
			line.deleteEx(true);
		}

		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_CompensationGroup.class)
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMN_C_Order_ID, getC_Order_ID())
				.create()
				.delete();

		return true;
	}    // beforeDelete

	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}

	/**
	 * Process Message
	 */
	private String m_processMsg = null;
	/**
	 * Just Prepared Flag
	 */
	private boolean m_justPrepared = false;

	/**
	 * Unlock Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		log.debug("unlockIt - {}", this);
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
		log.debug("{}", this);
		setDocAction(DOCACTION_Prepare);
		return true;
	}    // invalidateIt

	/**************************************************************************
	 * Prepare Document
	 *
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		final I_C_DocType dt = Services.get(IDocTypeDAO.class).getById(getC_DocTypeTarget_ID());

		// Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return IDocument.STATUS_Invalid;
		}

		// Lines
		final List<MOrderLine> lines = getLinesRequeryOrderedByProduct();
		if (lines.isEmpty())
		{
			m_processMsg = "@NoLines@";
			return IDocument.STATUS_Invalid;
		}

		// Bug 1564431
		if (getDeliveryRule() != null && DeliveryRule.COMPLETE_ORDER.getCode().equals(getDeliveryRule()))
		{
			for (final MOrderLine line : lines)
			{
				final MProduct product = line.getProduct();
				if (product != null && product.isExcludeAutoDelivery())
				{
					m_processMsg = "@M_Product_ID@ " + product.getValue() + " @IsExcludeAutoDelivery@";
					return IDocument.STATUS_Invalid;
				}
			}
		}

		// Convert DocType to Target
		if (getC_DocType_ID() != getC_DocTypeTarget_ID())
		{
			// Cannot change Std to anything else if different warehouses
			if (getC_DocType_ID() != 0)
			{
				final I_C_DocType dtOld = Services.get(IDocTypeDAO.class).getById(getC_DocType_ID());
				if (X_C_DocType.DOCSUBTYPE_StandardOrder.equals(dtOld.getDocSubType())        // From SO
						&& !X_C_DocType.DOCSUBTYPE_StandardOrder.equals(dt.getDocSubType()))    // To !SO
				{
					for (final MOrderLine line : lines)
					{
						if (line.getM_Warehouse_ID() != getM_Warehouse_ID())
						{
							log.warn("different Warehouse " + line);
							m_processMsg = "@CannotChangeDocType@";
							return IDocument.STATUS_Invalid;
						}
					}
				}
			}

			// New or in Progress/Invalid
			final DocStatus docStatus = DocStatus.ofCode(getDocStatus());
			if (docStatus.isDraftedInProgressOrInvalid()
					|| getC_DocType_ID() <= 0)
			{
				setC_DocType_ID(getC_DocTypeTarget_ID());
			}
			else
			// convert only if offer
			{
				if (Services.get(IDocTypeBL.class).isSalesProposalOrQuotation(dt))
				{
					setC_DocType_ID(getC_DocTypeTarget_ID());
				}
				else
				{
					m_processMsg = "@CannotChangeDocType@";
					return IDocument.STATUS_Invalid;
				}
			}
		}    // convert DocType

		// Mandatory Product Attribute Set Instance
		final String mandatoryType = "='Y'";    // IN ('Y','S')
		final String sql = "SELECT COUNT(*) "
				+ "FROM C_OrderLine ol"
				+ " INNER JOIN M_Product p ON (ol.M_Product_ID=p.M_Product_ID)"
				+ " INNER JOIN M_AttributeSet pas ON (p.M_AttributeSet_ID=pas.M_AttributeSet_ID) "
				+ "WHERE pas.MandatoryType" + mandatoryType
				+ " AND (ol.M_AttributeSetInstance_ID is NULL OR ol.M_AttributeSetInstance_ID = 0)"
				+ " AND ol.C_Order_ID=?";
		final int no = DB.getSQLValue(get_TrxName(), sql, getC_Order_ID());
		if (no != 0)
		{
			m_processMsg = "@LinesWithoutProductAttribute@ (" + no + ")";
			return IDocument.STATUS_Invalid;
		}

		// Lines
		// task 09030: we don't really want to explode the BOM, least of all this uncontrolled way.
		// if (explodeBOM())
		// lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID); Note: if we don't explode, we don't need to reload the lines
		if (!reserveStock(dt, lines))
		{
			m_processMsg = "Cannot reserve Stock";
			return IDocument.STATUS_Invalid;
		}
		if (!calculateTaxTotal())
		{
			m_processMsg = "Error calculating tax";
			return IDocument.STATUS_Invalid;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_justPrepared = true;
		// if (!DOCACTION_Complete.equals(getDocAction())) don't set for just prepare
		// setDocAction(DOCACTION_Complete);
		return IDocument.STATUS_InProgress;
	}    // prepareIt

	/**
	 * Reserve Inventory.
	 * Counterpart: MInOut.completeIt()
	 *
	 * @param docType document type or null
	 * @param lines   order lines (ordered by M_Product_ID for deadlock prevention)
	 * @return true if (un) reserved
	 */
	// metas: make reserveStock visible from MOrderLine to allow un-reservation
	// of stocks before delete.
	public boolean reserveStock(final I_C_DocType docType, final List<MOrderLine> lines)
	{
		final I_C_DocType dt = docType == null
				? Services.get(IDocTypeDAO.class).getById(getC_DocType_ID())
				: docType;

		// Binding
		boolean binding = dt != null && !Services.get(IDocTypeBL.class).isSalesProposal(dt);
		final String docSubType = dt == null ? null : dt.getDocSubType();

		// Not binding - i.e. Target=0
		if (DOCACTION_Void.equals(getDocAction())
				// Closing Binding Quotation
				|| (X_C_DocType.DOCSUBTYPE_Quotation.equals(docSubType)
				&& DOCACTION_Close.equals(getDocAction())))   // || isDropShip() )
		{

			binding = false;
		}
		final boolean isSOTrx = isSOTrx();

		log.debug("Binding=" + binding + " - IsSOTrx=" + isSOTrx);

		final WarehouseId dropShipWarehouseId = Services.get(IOrgDAO.class).getOrgDropshipWarehouseId(OrgId.ofRepoId(getAD_Org_ID()));

		// Force same WH for all but SO/PO
		WarehouseId headerWarehouseId = WarehouseId.ofRepoId(getM_Warehouse_ID());
		if (X_C_DocType.DOCSUBTYPE_StandardOrder.equals(docSubType)
				|| X_C_DocType.DOCBASETYPE_PurchaseOrder.equals(docSubType))
		{
			headerWarehouseId = null;        // don't enforce
		}

		BigDecimal Volume = BigDecimal.ZERO;
		BigDecimal Weight = BigDecimal.ZERO;

		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

		// Always check and (un) Reserve Inventory
		for (final MOrderLine line : lines)
		{
			final WarehouseId lineWarehouseIdAdviced = warehouseAdvisor.evaluateWarehouse(line);

			// Check/set WH/Org
			if (headerWarehouseId != null) // enforce WH
			{
				if (!Objects.equals(headerWarehouseId, lineWarehouseIdAdviced)
						&& !Objects.equals(lineWarehouseIdAdviced, dropShipWarehouseId))   // metas 01658 removing 'isDropShip' flag
				{
					line.setM_Warehouse_ID(headerWarehouseId.getRepoId());
				}
				if (getAD_Org_ID() != line.getAD_Org_ID())
				{
					line.setAD_Org_ID(getAD_Org_ID());
				}
			}

			// Binding
			final BigDecimal target = binding ? line.getQtyOrdered() : BigDecimal.ZERO;
			final BigDecimal difference = target
					.subtract(line.getQtyReserved())
					.subtract(line.getQtyDelivered());
			if (difference.signum() == 0)
			{
				final MProduct product = line.getProduct();
				if (product != null)
				{
					Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
					Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
				}
				continue;
			}

			log.debug("Line=" + line.getLine()
							  + " - Target=" + target + ",Difference=" + difference
							  + " - Ordered=" + line.getQtyOrdered()
							  + ",Reserved=" + line.getQtyReserved() + ",Delivered=" + line.getQtyDelivered());

			// Check Product - Stocked and Item
			final MProduct product = line.getProduct();
			if (product != null)
			{
				if (Services.get(IProductBL.class).isStocked(product))
				{
					final BigDecimal ordered = isSOTrx ? BigDecimal.ZERO : difference;
					final BigDecimal reserved = isSOTrx ? difference : BigDecimal.ZERO;
					final WarehouseId lineWarehouseId = line.getM_Warehouse_ID() > 0
							? WarehouseId.ofRepoId(line.getM_Warehouse_ID())
							: Services.get(IWarehouseAdvisor.class).evaluateWarehouse(line);
					int M_Locator_ID = 0;
					// Get Locator to reserve
					if (line.getM_AttributeSetInstance_ID() != 0)    // Get existing Location
					{
						M_Locator_ID = MStorage.getM_Locator_ID(line.getM_Warehouse_ID(),
																line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
																ordered, get_TrxName());
					}
					// Get default Location
					if (M_Locator_ID <= 0)
					{
						// try to take default locator for product first
						// if it is from the selected warehouse
						M_Locator_ID = product.getM_Locator_ID();
						if (M_Locator_ID > 0)
						{
							final I_M_Locator locator = warehousesRepo.getLocatorByRepoId(M_Locator_ID);
							// product has default locator defined but is not from the order warehouse
							if (locator.getM_Warehouse_ID() != lineWarehouseId.getRepoId())
							{
								M_Locator_ID = warehouseBL.getDefaultLocatorId(lineWarehouseId).getRepoId();
							}
						}
						else
						{
							M_Locator_ID = warehouseBL.getDefaultLocatorId(lineWarehouseId).getRepoId();
						}
					}
					// Update Storage
					// task 08999: update it async
					Services.get(IStorageBL.class).addAsync(
							getCtx(),
							lineWarehouseId.getRepoId(),
							M_Locator_ID,
							line.getM_Product_ID(),
							line.getM_AttributeSetInstance_ID(),
							line.getM_AttributeSetInstance_ID(),
							BigDecimal.ZERO,
							reserved,
							ordered,
							get_TrxName());
				}    // stockec
				// update line

				// task 09358: get rid of this; instead, update qtyReserved only in IOrderLineBL.
				// line.setQtyReserved(line.getQtyReserved().add(difference));
				// line.saveEx(get_TrxName()); // metas: use saveEx

				//
				Volume = Volume.add(product.getVolume().multiply(line.getQtyOrdered()));
				Weight = Weight.add(product.getWeight().multiply(line.getQtyOrdered()));
			}    // product
		}    // reverse inventory

		setVolume(Volume);
		setWeight(Weight);
		return true;
	}    // reserveStock

	/**
	 * Calculate Tax and Total
	 *
	 * @return true if tax total calculated
	 */
	public boolean calculateTaxTotal()
	{
		final String trxName = get_TrxName();

		log.debug("");

		// Delete Taxes
		DB.executeUpdateEx("DELETE FROM C_OrderTax WHERE C_Order_ID=" + getC_Order_ID(), trxName);
		m_taxes = null;

		// Lines
		BigDecimal totalLines = BigDecimal.ZERO;
		final Set<Integer> taxIds = new HashSet<>();
		for (final MOrderLine line : getLines())
		{
			final int taxId = line.getC_Tax_ID();
			if (!taxIds.contains(taxId))
			{
				final CurrencyPrecision taxPrecision = Services.get(IOrderLineBL.class).getTaxPrecision(line);
				final boolean taxIncluded = Services.get(IOrderLineBL.class).isTaxIncluded(line);
				final MOrderTax oTax = MOrderTax.get(line, taxPrecision.toInt(), false, trxName);    // current Tax
				oTax.setIsTaxIncluded(taxIncluded);
				if (!oTax.calculateTaxFromLines())
				{
					return false;
				}
				Check.assume(oTax.isActive(), "OrderTax shall be active: {}", oTax);

				InterfaceWrapperHelper.save(oTax);

				taxIds.add(taxId);
			}
			totalLines = totalLines.add(line.getLineNetAmt());
		}

		// Taxes
		BigDecimal grandTotal = totalLines;
		final MOrderTax[] taxes = getTaxes(true);
		for (final MOrderTax oTax : taxes)
		{
			final MTax tax = oTax.getTax();
			if (tax.isSummary())
			{
				final MTax[] cTaxes = tax.getChildTaxes(false);
				for (final MTax cTax : cTaxes)
				{
					final CurrencyPrecision taxPrecision = orderBL.getTaxPrecision(this);
					final boolean taxIncluded = orderBL.isTaxIncluded(this, TaxUtils.from(cTax));
					final BigDecimal taxAmt = Services.get(ITaxBL.class).calculateTax(cTax, oTax.getTaxBaseAmt(), taxIncluded, taxPrecision.toInt());
					//
					final MOrderTax newOTax = new MOrderTax(getCtx(), 0, trxName);
					newOTax.setClientOrg(this);
					newOTax.setC_Order_ID(getC_Order_ID());
					newOTax.setC_Tax_ID(cTax.getC_Tax_ID());
					newOTax.setPrecision(taxPrecision.toInt());
					newOTax.setIsTaxIncluded(taxIncluded);
					newOTax.setIsDocumentLevel(cTax.isDocumentLevel());
					newOTax.setTaxBaseAmt(oTax.getTaxBaseAmt());
					newOTax.setTaxAmt(taxAmt);
					if (!newOTax.save(trxName))
					{
						return false;
					}
					//
					if (!newOTax.isTaxIncluded())
					{
						grandTotal = grandTotal.add(taxAmt);
					}
				}
				if (!oTax.delete(true, trxName))
				{
					return false;
				}
				if (!oTax.save(trxName))
				{
					return false;
				}
			}
			else
			{
				if (!oTax.isTaxIncluded())
				{
					grandTotal = grandTotal.add(oTax.getTaxAmt());
				}
			}
		}
		//
		setTotalLines(totalLines);
		setGrandTotal(grandTotal);
		return true;
	}    // calculateTaxTotal

	/**
	 * Approve Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		log.debug("approveIt - {}", this);
		setIsApproved(true);
		return true;
	}    // approveIt

	/**
	 * Reject Approval
	 *
	 * @return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.debug("rejectIt - {}", this);
		setIsApproved(false);
		return true;
	}    // rejectIt

	/**************************************************************************
	 * Complete Document
	 *
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		final DocStatus docStatus = completeIt0();
		return docStatus.getCode();
	}

	private DocStatus completeIt0()
	{
		//
		// Just prepare
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return DocStatus.InProgress;
		}

		final I_C_DocType dt = Services.get(IDocTypeDAO.class).getById(getC_DocType_ID());
		final String docSubType = dt.getDocSubType();

		//
		// Offers
		if (X_C_DocType.DOCSUBTYPE_Proposal.equals(docSubType)
				|| X_C_DocType.DOCSUBTYPE_Quotation.equals(docSubType)
				|| X_C_DocType.DOCSUBTYPE_FrameAgrement.equals(docSubType))
		{
			// Binding
			if (X_C_DocType.DOCSUBTYPE_Quotation.equals(docSubType))
			{
				reserveStock(dt, getLinesRequeryOrderedByProduct());
			}
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
			if (m_processMsg != null)
			{
				return DocStatus.Invalid;
			}
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
			if (m_processMsg != null)
			{
				return DocStatus.Invalid;
			}
			// Set the definite document number after completed (if needed)
			setDefiniteDocumentNo();
			setProcessed(true);
			return DocStatus.Completed;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return DocStatus.Invalid;
		}

		//
		// Waiting Payment - until we have a payment
		if (prepareAndReturnTrueIfWaitForPaymentIsNeeded(docSubType))
		{
			setProcessed(true);
			return DocStatus.WaitingPayment;
		}

		// Re-Check
		if (!m_justPrepared)
		{
			final DocStatus docStatus = DocStatus.ofCode(prepareIt());
			if (!docStatus.isInProgress())
			{
				return docStatus;
			}
		}

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}

		invalidateLines();

		log.debug("Completed: {}", this);

		final StringBuilder info = new StringBuilder();
		final boolean realTimePOS = false;

		// Create SO Shipment - Force Shipment
		MInOut shipment = null;
		if (X_C_DocType.DOCSUBTYPE_OnCreditOrder.equals(docSubType)        // (W)illCall(I)nvoice
				|| X_C_DocType.DOCSUBTYPE_WarehouseOrder.equals(docSubType)    // (W)illCall(P)ickup
				|| X_C_DocType.DOCSUBTYPE_POSOrder.equals(docSubType))            // (W)alkIn(R)eceipt
		{
			if (!DeliveryRule.FORCE.getCode().equals(getDeliveryRule()))
			{
				setDeliveryRule(DeliveryRule.FORCE.getCode());
			}
			//
			shipment = createShipment(dt, realTimePOS ? null : getDateOrdered());
			if (shipment == null)
			{
				return DocStatus.Invalid;
			}
			info.append("@M_InOut_ID@: ").append(shipment.getDocumentNo());
			final String msg = shipment.getProcessMsg();
			if (msg != null && msg.length() > 0)
			{
				info.append(" (").append(msg).append(")");
			}
		}    // Shipment

		// Create SO Invoice - Always invoice complete Order
		if (X_C_DocType.DOCSUBTYPE_POSOrder.equals(docSubType)
				|| X_C_DocType.DOCSUBTYPE_OnCreditOrder.equals(docSubType))
		{
			final MInvoice invoice = createInvoice(dt, shipment, realTimePOS ? null : getDateOrdered());
			if (invoice == null)
			{
				return DocStatus.Invalid;
			}
			info.append(" - @C_Invoice_ID@: ").append(invoice.getDocumentNo());
			final String msg = invoice.getProcessMsg();
			if (msg != null && msg.length() > 0)
			{
				info.append(" (").append(msg).append(")");
			}
		}    // Invoice

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			if (info.length() > 0)
			{
				info.append(" - ");
			}
			info.append(valid);
			m_processMsg = info.toString();
			return DocStatus.Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		setProcessed(true);
		m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Re_Activate); // issue #347
		return DocStatus.Completed;
	}    // completeIt

	private boolean prepareAndReturnTrueIfWaitForPaymentIsNeeded(final String docSubType)
	{
		//
		// Prepayment
		if (X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(docSubType))
		{
			final boolean waitForPayment = getC_Payment_ID() <= 0 && getC_CashLine_ID() <= 0;
			return waitForPayment;
		}

		//
		// Payment Reservation
		if (isSOTrx())
		{
			final OrderPaymentReservationService orderPaymentReservationService = SpringContextHolder.instance.getBean(OrderPaymentReservationService.class);
			final OrderPaymentReservationCreateResult result = orderPaymentReservationService.createPaymentReservationIfNeeded(this);
			final boolean waitForPayment = result.isWaitingToComplete();
			return waitForPayment;
		}
		else
		{
			return false; // don't wait for payment
		}
	}

	/**
	 * Set the definite <code>DocumentNo</code> and <code>DateOrdered</code> after completed, both according to this order's <code>C_DocType</code>.<br>
	 */
	private void setDefiniteDocumentNo()
	{
		final I_C_DocType docType = Services.get(IDocTypeDAO.class).getById(getC_DocType_ID());

		if (docType.isOverwriteDateOnComplete())
		{
			setDateOrdered(SystemTime.asTimestamp());
		}

		if (docType.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String documentNo = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (documentNo != null && documentNo != IDocumentNoBuilder.NO_DOCUMENTNO)
			{
				setDocumentNo(documentNo);
				Services.get(IDocumentNoBL.class).fireDocumentNoChange(this, documentNo); // task 09776
			}
		}
	}

	/**
	 * Create Shipment
	 *
	 * @param dt           order document type
	 * @param movementDate optional movement date (default today)
	 * @return shipment or null
	 */
	private MInOut createShipment(final I_C_DocType dt, final Timestamp movementDate)
	{
		log.debug("For " + dt);
		final MInOut shipment = new MInOut(this, dt.getC_DocTypeShipment_ID(), movementDate);
		// shipment.setDateAcct(getDateAcct());
		if (!shipment.save(get_TrxName()))
		{
			m_processMsg = "Could not create Shipment";
			return null;
		}
		//
		for (final MOrderLine oLine : getLinesRequery())
		{
			final I_C_OrderLine line = InterfaceWrapperHelper.create(oLine, I_C_OrderLine.class);
			final MInOutLine ioLine = new MInOutLine(shipment);
			// Qty = Ordered - Delivered
			final BigDecimal MovementQty = oLine.getQtyOrdered().subtract(oLine.getQtyDelivered());
			// Location
			final WarehouseId warehouseId = warehouseAdvisor.evaluateWarehouse(line);
			int M_Locator_ID = MStorage.getM_Locator_ID(warehouseId.getRepoId(),
														oLine.getM_Product_ID(), oLine.getM_AttributeSetInstance_ID(),
														MovementQty, get_TrxName());
			if (M_Locator_ID <= 0)        // Get default Location
			{
				M_Locator_ID = Services.get(IWarehouseBL.class).getDefaultLocatorId(warehouseId).getRepoId();
			}
			//
			ioLine.setOrderLine(oLine, M_Locator_ID, MovementQty);
			ioLine.setQty(MovementQty);
			if (oLine.getQtyEntered().compareTo(oLine.getQtyOrdered()) != 0)
			{
				ioLine.setQtyEntered(MovementQty
											 .multiply(oLine.getQtyEntered())
											 .divide(oLine.getQtyOrdered(), 6, BigDecimal.ROUND_HALF_UP));
			}
			if (!ioLine.save(get_TrxName()))
			{
				m_processMsg = "Could not create Shipment Line";
				return null;
			}
		}
		// metas: If order has no lines or all lines are comments, show error msg
		if (shipment.getLines().length == 0)
		{
			m_processMsg = Services.get(IMsgBL.class).getMsg(Env.getCtx(), NO_DELIVARABLE_LINES_FOUND, null);
			return null;
		}
		// metas: end
		// Manually Process Shipment
		final DocStatus shipmentDocStatus = DocStatus.ofCode(shipment.completeIt());
		shipment.setDocStatus(shipmentDocStatus.getCode());
		shipment.save(get_TrxName());
		if (!shipmentDocStatus.isCompleted())
		{
			m_processMsg = "@M_InOut_ID@: " + shipment.getProcessMsg();
			return null;
		}
		return shipment;
	}    // createShipment

	/**
	 * Create Invoice
	 *
	 * @param dt          order document type
	 * @param shipment    optional shipment
	 * @param invoiceDate invoice date
	 * @return invoice or null
	 */
	private MInvoice createInvoice(final I_C_DocType dt, final MInOut shipment, final Timestamp invoiceDate)
	{
		log.debug("docType={}", dt);
		final MInvoice invoice = new MInvoice(this, dt.getC_DocTypeInvoice_ID(), invoiceDate);
		if (!invoice.save(get_TrxName()))
		{
			m_processMsg = "Could not create Invoice";
			return null;
		}

		// If we have a Shipment - use that as a base
		if (shipment != null)
		{
			if (!INVOICERULE_AfterDelivery.equals(getInvoiceRule()))
			{
				setInvoiceRule(INVOICERULE_AfterDelivery);
			}
			//
			for (final MInOutLine sLine2 : shipment.getLines())
			{
				final MInOutLine sLine = sLine2;
				//
				final MInvoiceLine iLine = new MInvoiceLine(invoice);
				iLine.setShipLine(sLine);
				// Qty = Delivered
				if (sLine.sameOrderLineUOM())
				{
					iLine.setQtyEntered(sLine.getQtyEntered());
				}
				else
				{
					iLine.setQtyEntered(sLine.getMovementQty());
				}
				iLine.setQtyInvoiced(sLine.getMovementQty());
				if (!iLine.save(get_TrxName()))
				{
					m_processMsg = "Could not create Invoice Line from Shipment Line";
					return null;
				}
				//
				sLine.setIsInvoiced(true);
				if (!sLine.save(get_TrxName()))
				{
					log.warn("Could not update Shipment line: " + sLine);
				}
			}
		}
		else
		// Create Invoice from Order
		{
			if (!INVOICERULE_Immediate.equals(getInvoiceRule()))
			{
				setInvoiceRule(INVOICERULE_Immediate);
			}
			//
			for (final MOrderLine oLine : getLines())
			{
				//
				final MInvoiceLine iLine = new MInvoiceLine(invoice);
				iLine.setOrderLine(oLine);
				// Qty = Ordered - Invoiced
				iLine.setQtyInvoiced(oLine.getQtyOrdered().subtract(oLine.getQtyInvoiced()));
				if (oLine.getQtyOrdered().compareTo(oLine.getQtyEntered()) == 0)
				{
					iLine.setQtyEntered(iLine.getQtyInvoiced());
				}
				else
				{
					iLine.setQtyEntered(iLine.getQtyInvoiced().multiply(oLine.getQtyEntered())
												.divide(oLine.getQtyOrdered(), 12, BigDecimal.ROUND_HALF_UP));
				}
				if (!iLine.save(get_TrxName()))
				{
					m_processMsg = "Could not create Invoice Line from Order Line";
					return null;
				}
			}
		}
		// Manually Process Invoice
		final DocStatus invoiceDocStatus = DocStatus.ofCode(invoice.completeIt());
		invoice.setDocStatus(invoiceDocStatus.getCode());
		invoice.save(get_TrxName());
		setC_CashLine_ID(invoice.getC_CashLine_ID());
		if (!invoiceDocStatus.isCompleted())
		{
			m_processMsg = "@C_Invoice_ID@: " + invoice.getProcessMsg();
			return null;
		}
		return invoice;
	}    // createInvoice

	/**
	 * Void Document.
	 * Set Qtys to 0 - Sales: reverse all documents
	 *
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		final List<MOrderLine> lines = getLinesRequeryOrderedByProduct();
		for (final MOrderLine line : lines)
		{
			final BigDecimal old = line.getQtyOrdered();
			if (old.signum() != 0)
			{
				line.addDescription(Services.get(IMsgBL.class).getMsg(getCtx(), "Voided") + " (" + old + ")");
				line.setQty(BigDecimal.ZERO);
				line.setLineNetAmt(BigDecimal.ZERO);
				line.save(get_TrxName());
			}
		}

		// update taxes
		final MOrderTax[] taxes = getTaxes(true);
		for (final MOrderTax tax : taxes)
		{
			if (!(tax.calculateTaxFromLines() && tax.save()))
			{
				return false;
			}
		}

		addDescription(Services.get(IMsgBL.class).getMsg(getCtx(), "Voided"));
		// Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (void)";
			return false;
		}

		// UnLink All Requisitions
		MRequisitionLine.unlinkC_Order_ID(getCtx(), get_ID(), get_TrxName());

		if (!createReversals())
		{
			return false;
		}

		/* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
		Services.get(IFactAcctDAO.class).deleteForDocument(this);
		setPosted(false);

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}    // voidIt

	/**
	 * Create Shipment/Invoice Reversals
	 *
	 * @return true if success
	 */
	private boolean createReversals()
	{
		// Cancel only Sales
		if (!isSOTrx())
		{
			return true;
		}

		log.debug("createReversals");
		final StringBuilder info = new StringBuilder();

		// Reverse All *Shipments*
		info.append("@M_InOut_ID@:");
		final MInOut[] shipments = getShipments();
		for (final MInOut shipment : shipments)
		{
			final MInOut ship = shipment;
			// if closed - ignore
			if (MInOut.DOCSTATUS_Closed.equals(ship.getDocStatus())
					|| MInOut.DOCSTATUS_Reversed.equals(ship.getDocStatus())
					|| MInOut.DOCSTATUS_Voided.equals(ship.getDocStatus()))
			{
				continue;
			}
			ship.set_TrxName(get_TrxName());

			// If not completed - void - otherwise reverse it
			if (!MInOut.DOCSTATUS_Completed.equals(ship.getDocStatus()))
			{
				if (ship.voidIt())
				{
					ship.setDocStatus(MInOut.DOCSTATUS_Voided);
				}
			}
			else if (ship.reverseCorrectIt())    // completed shipment
			{
				ship.setDocStatus(MInOut.DOCSTATUS_Reversed);
				info.append(" ").append(ship.getDocumentNo());
			}
			else
			{
				m_processMsg = "Could not reverse Shipment " + ship;
				return false;
			}
			ship.setDocAction(MInOut.DOCACTION_None);
			ship.save(get_TrxName());
		}    // for all shipments

		// Reverse All *Invoices*
		info.append(" - @C_Invoice_ID@:");
		for (final MInvoice invoice : getInvoices(OrderId.ofRepoId(getC_Order_ID())))
		{
			// if closed - ignore
			final DocStatus invoiceDocStatus = DocStatus.ofCode(invoice.getDocStatus());
			if (invoiceDocStatus.isClosedReversedOrVoided())
			{
				continue;
			}
			invoice.set_TrxName(get_TrxName());

			// If not completed - void - otherwise reverse it
			if (!invoiceDocStatus.isCompleted())
			{
				if (invoice.voidIt())
				{
					invoice.setDocStatus(DocStatus.Voided.getCode());
				}
			}
			else if (invoice.reverseCorrectIt())    // completed invoice
			{
				invoice.setDocStatus(DocStatus.Reversed.getCode());
				info.append(" ").append(invoice.getDocumentNo());
			}
			else
			{
				m_processMsg = "Could not reverse Invoice " + invoice;
				return false;
			}
			invoice.setDocAction(MInvoice.DOCACTION_None);
			invoice.save(get_TrxName());
		}    // for all shipments

		m_processMsg = info.toString();
		return true;
	}    // createReversals

	/**
	 * Close Document.
	 * Cancel not delivered Qunatities
	 *
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		// Close Not delivered Qty - SO/PO
		final List<MOrderLine> lines = getLinesRequeryOrderedByProduct();
		for (final MOrderLine line : lines)
		{
			final BigDecimal old = line.getQtyOrdered();
			if (old.compareTo(line.getQtyDelivered()) != 0)
			{
				line.setQtyLostSales(line.getQtyOrdered().subtract(line.getQtyDelivered()));
				line.setQtyOrdered(line.getQtyDelivered());
				// QtyEntered unchanged
				line.addDescription("Close (" + old + ")");
				InterfaceWrapperHelper.save(line, get_TrxName());
			}
		}
		// Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (close)";
			return false;
		}
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}    // closeIt

	/**
	 * @author: phib
	 * re-open a closed order
	 * (reverse steps of close())
	 */
	public String reopenIt()
	{
		final DocStatus docStatus = DocStatus.ofCode(getDocStatus());
		if (!docStatus.isClosed())
		{
			return "Not closed - can't reopen";
		}

		//
		final List<MOrderLine> lines = getLinesRequeryOrderedByProduct();
		for (final MOrderLine line2 : lines)
		{
			final MOrderLine line = line2;
			if (BigDecimal.ZERO.compareTo(line.getQtyLostSales()) != 0)
			{
				line.setQtyOrdered(line.getQtyLostSales().add(line.getQtyDelivered()));
				line.setQtyLostSales(BigDecimal.ZERO);
				// QtyEntered unchanged

				// Strip Close() tags from description
				String desc = line.getDescription();
				if (desc == null)
				{
					desc = "";
				}
				final Pattern pattern = Pattern.compile("( \\| )?Close \\(.*\\)");
				final String[] parts = pattern.split(desc);
				desc = "";
				for (final String s : parts)
				{
					desc = desc.concat(s);
				}
				line.setDescription(desc);
				if (!line.save(get_TrxName()))
				{
					return "Couldn't save orderline";
				}
			}
		}
		// Clear Reservations
		if (!reserveStock(null, lines))
		{
			m_processMsg = "Cannot unreserve Stock (close)";
			return "Failed to update reservations";
		}

		setDocStatus(DocStatus.Completed.getCode());
		setDocAction(DOCACTION_Close);
		if (!this.save(get_TrxName()))
		{
			return "Couldn't save reopened order";
		}
		else
		{
			return "";
		}
	}    // reopenIt

	/**
	 * Reverse Correction - same void
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		return voidIt();
	}    // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	}    // reverseAccrualIt

	/**
	 * Re-activate.
	 *
	 * @return true if success
	 */
	@Override
	public boolean reActivateIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);

		final DocTypeId docTypeId = DocTypeId.ofRepoId(getC_DocType_ID());
		final I_C_DocType dt = Services.get(IDocTypeDAO.class).getById(docTypeId);
		final String docSubType = dt.getDocSubType();

		if (X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(docSubType))
		{
			// Replace Prepay with POS to revert all doc
			I_C_DocType newDT = null;
			for (final I_C_DocType type : MDocType.getOfClient(getCtx()))
			{
				if (X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(type.getDocSubType()))
				{
					if (type.isDefault() || newDT == null)
					{
						newDT = type;
					}
				}
			}
			if (newDT == null)
			{
				return false;
			}
			else
			{
				setC_DocType_ID(newDT.getC_DocType_ID());
			}
		}

		// PO - just re-open
		if (!isSOTrx())
		{
			log.debug("Existing documents not modified - {}", dt);
		}
		else if (X_C_DocType.DOCSUBTYPE_OnCreditOrder.equals(docSubType)    // (W)illCall(I)nvoice
				|| X_C_DocType.DOCSUBTYPE_WarehouseOrder.equals(docSubType)    // (W)illCall(P)ickup
				|| X_C_DocType.DOCSUBTYPE_POSOrder.equals(docSubType))            // (W)alkIn(R)eceipt
		{
			if (!createReversals())
			{
				return false;
			}
		}
		else
		{
			log.debug("Existing documents not modified - SubType=" + docSubType);
		}

		/* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
		Services.get(IFactAcctDAO.class).deleteForDocument(this);
		setPosted(false);
		// metas: after reactivate put to the end of this method
		setDocAction(DOCACTION_Complete);
		setProcessed(false);

		// After reActivate
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);

		// metas: commented out (legacy purposes)
		// TODO: metas: evaluate if we can uncommented this and remove the setDocAction above
		// setDocAction(DOCACTION_Complete);
		// setProcessed(false);
		return true;
	}    // reActivateIt

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
		sb.append(": ").append(Services.get(IMsgBL.class).translate(getCtx(), "GrandTotal")).append("=").append(getGrandTotal());

		final List<MOrderLine> lines = _lines;
		if (lines != null)
		{
			sb.append(" (#").append(lines.size()).append(")");
		}

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
		return TimeUtil.asLocalDate(getDateOrdered());
	}

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}    // getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getSalesRep_ID();
	}

	/**
	 * Get Document Approval Amount
	 *
	 * @return amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getGrandTotal();
	}    // getApprovalAmt

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		final DocStatus docStatus = DocStatus.ofCode(getDocStatus());
		return docStatus.isCompletedOrClosedOrReversed();
	}    // isComplete
} // MOrder
