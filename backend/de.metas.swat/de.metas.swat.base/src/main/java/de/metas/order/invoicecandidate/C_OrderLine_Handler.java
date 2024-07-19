package de.metas.order.invoicecandidate;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.InvoiceCandidateIds;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax.PriceAndTaxBuilder;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupCompensationUtils;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Converts {@link I_C_OrderLine} to {@link I_C_Invoice_Candidate}.
 */
public class C_OrderLine_Handler extends AbstractInvoiceCandidateHandler
{
	private final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepo = SpringContextHolder.instance.getBean(OrderEmailPropagationSysConfigRepository.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	/**
	 * @return <code>false</code>, the candidates will be created by {@link C_Order_Handler}.
	 */
	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.DONT;
	}

	/**
	 * @return <code>false</code>, the candidates will be created by {@link C_Order_Handler}.
	 */
	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(final Object model)
	{
		return CandidatesAutoCreateMode.DONT;
	}

	/**
	 * @see C_Order_Handler#expandRequest(InvoiceCandidateGenerateRequest)
	 */
	@Override
	public Object getModelForInvoiceCandidateGenerateScheduling(final Object model)
	{
		//
		// Retrieve order
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		return orderLine.getC_Order();
	}

	@Override
	public Iterator<?> retrieveAllModelsWithMissingCandidates(final QueryLimit limit_IGNORED)
	{
		return Services.get(IC_OrderLine_HandlerDAO.class).retrieveMissingOrderLinesQuery(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.create()
				.list(I_C_OrderLine.class)
				.iterator();
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		final I_C_OrderLine orderLine = request.getModel(I_C_OrderLine.class);

		final I_C_Invoice_Candidate ic = createCandidateForOrderLine(orderLine);
		return InvoiceCandidateGenerateResult.of(this, ic);
	}

	private I_C_Invoice_Candidate createCandidateForOrderLine(final I_C_OrderLine orderLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);

		Check.assume(Env.getAD_Client_ID(ctx) == orderLine.getAD_Client_ID(), "AD_Client_ID of " + orderLine + " and of its Ctx are the same");

		final I_C_Invoice_Candidate icRecord = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);

		icRecord.setAD_Org_ID(orderLine.getAD_Org_ID());
		icRecord.setC_ILCandHandler(getHandlerRecord());

		icRecord.setAD_Table_ID(tableDAO.retrieveTableId(org.compiere.model.I_C_OrderLine.Table_Name));
		icRecord.setRecord_ID(orderLine.getC_OrderLine_ID());

		icRecord.setC_OrderLine(orderLine);

		final int productRecordId = orderLine.getM_Product_ID();
		icRecord.setM_Product_ID(productRecordId);

		final boolean isFreightCostProduct = productBL
				.getProductType(ProductId.ofRepoId(productRecordId))
				.isFreightCost();

		icRecord.setIsFreightCost(isFreightCostProduct);
		icRecord.setIsPackagingMaterial(orderLine.isPackagingMaterial());
		icRecord.setC_Charge_ID(orderLine.getC_Charge_ID());

		setOrderedData(icRecord, orderLine);

		icRecord.setQtyToInvoice(BigDecimal.ZERO); // to be computed

		icRecord.setDescription(orderLine.getDescription()); // 03439

		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		setBPartnerData(icRecord, orderLine);
		setGroupCompensationData(icRecord, orderLine);

		//
		// Invoice Rule(s)
		icRecord.setInvoiceRule(order.getInvoiceRule());

		// If we are dealing with a non-receivable service set the InvoiceRule_Override to Immediate
		// because we want to invoice those right away (08408)
		if (isNotReceivebleService(icRecord))
		{
			icRecord.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_OVERRIDE_Immediate); // immediate
		}

		// 05265
		icRecord.setIsSOTrx(order.isSOTrx());

		icRecord.setQtyOrderedOverUnder(orderLine.getQtyOrderedOverUnder());

		// prices and tax
		final PriceAndTax priceAndTax = calculatePriceAndTax(icRecord);
		IInvoiceCandInvalidUpdater.updatePriceAndTax(icRecord, priceAndTax);

		//
		// Dimension
		final Dimension orderLineDimension = extractDimension(orderLine);
		dimensionService.updateRecord(icRecord, orderLineDimension);

		//DocType
		final DocTypeId orderDocTypeId = CoalesceUtil.coalesceSuppliersNotNull(
				() -> DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID()),
				() -> DocTypeId.ofRepoId(order.getC_DocTypeTarget_ID()));
		final I_C_DocType orderDocType = docTypeBL.getById(orderDocTypeId);
		final DocTypeId invoiceDocTypeId = DocTypeId.ofRepoIdOrNull(orderDocType.getC_DocTypeInvoice_ID());
		if (invoiceDocTypeId != null)
		{
			icRecord.setC_DocTypeInvoice_ID(invoiceDocTypeId.getRepoId());
		}
		else
		{
			setDefaultInvoiceDocType(icRecord);
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributes = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asiId);

		invoiceCandBL.setQualityDiscountPercent_Override(icRecord, attributes);

		if(orderEmailPropagationSysConfigRepo.isPropagateToCInvoice(ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID())))
		{
			icRecord.setEMail(order.getEMail());
		}

		icRecord.setC_Async_Batch_ID(order.getC_Async_Batch_ID());

		final de.metas.order.model.I_C_Order orderModel = orderDAO.getById(OrderId.ofRepoId(order.getC_Order_ID()), de.metas.order.model.I_C_Order.class);
		icRecord.setAD_InputDataSource_ID(orderModel.getAD_InputDataSource_ID());

		// external identifiers
		icRecord.setExternalLineId(orderLine.getExternalId());
		icRecord.setExternalHeaderId(order.getExternalId());

		// Don't save.
		// That's done by the invoking API-impl, because we want to avoid C_Invoice_Candidate.invalidateCandidates() from being called on every single IC that is created here.
		// Because it's a performance nightmare for orders with a lot of lines
		// InterfaceWrapperHelper.save(ic);

		return icRecord;
	}

	private Dimension extractDimension(final I_C_OrderLine orderLine)
	{
		Dimension orderLineDimension = dimensionService.getFromRecord(orderLine);
		if (orderLineDimension.getActivityId() == null)
		{
			final ActivityId activityId = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(
					ClientId.ofRepoId(orderLine.getAD_Client_ID()),
					OrgId.ofRepoId(orderLine.getAD_Org_ID()),
					ProductId.ofRepoId(orderLine.getM_Product_ID()));
			orderLineDimension = orderLineDimension.withActivityId(activityId);
		}
		return orderLineDimension;
	}

	@Override
	public String getSourceTable()
	{
		return I_C_OrderLine.Table_Name;
	}

	/**
	 * Returns <code>true</code>.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return true;
	}

	/**
	 * <ul>
	 * <li>QtyEntered := C_OrderLine.QtyEntered
	 * <li>C_UOM_ID := C_OrderLine.C_UOM_ID
	 * <li>QtyOrdered := C_OrderLine.QtyOrdered
	 * <li>DateOrdered := C_OrderLine.DateOrdered
	 * <li>C_Order_ID: C_OrderLine.C_Order_ID
	 * <li>C_PaymentTerm_ID: C_OrderLine.C_PaymentTerm_ID/C_Order.C_PaymentTerm_ID
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setOrderedData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final org.compiere.model.I_C_OrderLine orderLine = ic.getC_OrderLine();

		setOrderedData(ic, orderLine);
	}

	private void setOrderedData(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		// prefer priceUOM, if given
		if (orderLine.getPrice_UOM_ID() > 0)
		{
			ic.setQtyEntered(orderLine.getQtyEnteredInPriceUOM());
			ic.setC_UOM_ID(orderLine.getPrice_UOM_ID());
		}
		else
		{
			ic.setQtyEntered(orderLine.getQtyEntered());
			ic.setC_UOM_ID(orderLine.getC_UOM_ID());
		}

		// we use C_OrderLine.QtyOrdered which is fine, but which is also in the product's stocking UOM
		ic.setQtyOrdered(orderLine.getQtyOrdered());
		ic.setDateOrdered(orderLine.getDateOrdered());

		ic.setPresetDateInvoiced(orderLine.getPresetDateInvoiced());

		ic.setC_Order_ID(orderLine.getC_Order_ID());

		setC_PaymentTerm(ic, orderLine);

		setIncoterms(ic, orderLine);

		setC_Flatrate_Term_ID(ic, orderLine);

		setPaymentRule(ic, orderLine);

		setIncoterms(ic, orderLine);
	}

	private void setPaymentRule(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);
		final PaymentRule paymentRule;
		if (order.getPaymentRule() != null)
		{
			paymentRule = PaymentRule.ofNullableCode(order.getPaymentRule());
		}
		else
		{
			final I_C_BPartner partner = bpartnerDAO.getById(order.getC_BPartner_ID());
			paymentRule = PaymentRule.ofNullableCode(partner.getPaymentRule());
		}

		if (paymentRule != null)
		{
			ic.setPaymentRule(paymentRule.getCode());
		}
		else
		{
			ic.setPaymentRule(Services.get(IInvoiceBL.class).getDefaultPaymentRule().getCode());
		}
	}


	private void setIncoterms(@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();
		ic.setC_Incoterms_ID(order.getC_Incoterms_ID());
		ic.setIncotermLocation(order.getIncotermLocation());
	}

	private void setC_PaymentTerm(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		if (!ic.isSOTrx())
		{
			return;
		}

		final PaymentTermId paymentTermId = Services.get(IOrderLineBL.class).getPaymentTermId(orderLine);
		ic.setC_PaymentTerm_ID(paymentTermId.getRepoId());
	}

	/**
	 * Sets {@link I_C_Invoice_Candidate#COLUMNNAME_QtyDelivered C_Invoice_Candidate.QtyDelivered} to {@link I_C_OrderLine#COLUMNNAME_QtyDelivered C_OrderLine.QtyDelivered}.
	 * <p>
	 * Sets {@link I_C_Invoice_Candidate#COLUMNNAME_DeliveryDate C_Invoice_Candidate.DeliveryDate} the MovementDate of the first InOut that is referenced by this ic via
	 * {@link I_C_InvoiceCandidate_InOutLine}, and {@link I_C_Invoice_Candidate#COLUMNNAME_M_InOut_ID C_Invoice_Candidate.M_InOut_ID} to that inout's ID. <br>
	 * "First" means the one with first <code>MovementDate</code> or (if the date)the smallest <code>M_InOut_ID</code>.<br>
	 * <p>
	 * If the given ic has no InOut, then <code>QtyDelivered</code>, <code>DeliveryDate</code> and <code>M_InOut_ID</code> are set to <code>null</code>.
	 */
	@Override
	public void setDeliveredData(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final InvoiceCandidateRecordService invoiceCandidateRecordService = SpringContextHolder.instance.getBean(InvoiceCandidateRecordService.class);

		//
		// Quantity
		StockQtyAndUOMQty qtysDelivered = invoiceCandidateRecordService
				.ofRecord(icRecord)
				.computeQtysDelivered();

		if (qtysDelivered.getStockQty().isZero())
		{
			final org.compiere.model.I_C_OrderLine orderLine = icRecord.getC_OrderLine();
			if (orderLine.getQtyDelivered().signum() > 0)
			{
				// fallback to C_OrderLine.QtyDelivered...maybe there are cases with no-item-products, where we have QtyDelivered, but no shipments
				qtysDelivered = StockQtyAndUOMQtys.create(
						orderLine.getQtyDelivered(), ProductId.ofRepoId(orderLine.getM_Product_ID()),
						orderLine.getQtyEntered(), UomId.ofRepoId(orderLine.getC_UOM_ID()));
			}
		}

		icRecord.setQtyDelivered(qtysDelivered.getStockQty().toBigDecimal());
		icRecord.setQtyDeliveredInUOM(qtysDelivered.getUOMQtyNotNull().toBigDecimal());

		//
		// Date
		// Find out the first shipment/receipt
		final List<I_C_InvoiceCandidate_InOutLine> icIols = invoiceCandDAO.retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(icRecord));
		I_M_InOut firstInOut = null;
		for (final I_C_InvoiceCandidate_InOutLine icIol : icIols)
		{
			final I_M_InOut inOut = icIol.getM_InOutLine().getM_InOut();

			// Consider only completed shipments/receipts
			if (!DocStatus.ofCode(inOut.getDocStatus()).isCompletedOrClosed())
			{
				continue;
			}

			if (firstInOut == null)
			{
				firstInOut = inOut;
			}
			else if (firstInOut.getMovementDate().after(inOut.getMovementDate())
					|| firstInOut.getMovementDate().equals(inOut.getMovementDate()) && firstInOut.getM_InOut_ID() > inOut.getM_InOut_ID())
			{
				firstInOut = inOut;
			}
		}

		setDeliveredDataFromFirstInOut(icRecord, firstInOut);
	}

	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(icRecord.getC_OrderLine(), I_C_OrderLine.class);
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();

		final TaxId taxId = TaxId.ofRepoId(orderLine.getC_Tax_ID());

		// ts: we *must* use the order line's data
		final PriceAndTaxBuilder priceAndTax = PriceAndTax.builder()
				.invoicableQtyBasedOn(InvoicableQtyBasedOn.fromRecordString(orderLine.getInvoicableQtyBasedOn()))
				.pricingSystemId(PricingSystemId.ofRepoId(order.getM_PricingSystem_ID()))
				.priceEntered(orderLine.getPriceEntered())
				.priceActual(orderLine.getPriceActual())
				.priceUOMId(UomId.ofRepoIdOrNull(orderLine.getPrice_UOM_ID()))
				.taxId(taxId)
				.taxIncluded(order.isTaxIncluded())
				.currencyId(CurrencyId.ofRepoId(order.getC_Currency_ID()));

		//
		// Percent Group Compensation Line
		if (icRecord.isGroupCompensationLine()
				&& GroupCompensationAmtType.Percent.getAdRefListValue().equals(icRecord.getGroupCompensationAmtType())
				&& icRecord.getC_Invoice_Candidate_ID() > 0 /*when the IC is first created, then this IC can't have a group yet */)
		{
			final InvoiceCandidateGroupRepository groupsRepo = SpringContextHolder.instance.getBean(InvoiceCandidateGroupRepository.class);

			final GroupId groupId = groupsRepo.extractGroupId(icRecord);
			final Group group = groupsRepo.retrieveGroup(groupId);
			group.updateAllCompensationLines();

			final GroupCompensationLine compensationLine = group.getCompensationLineById(groupsRepo.extractLineId(icRecord));
			priceAndTax.priceEntered(compensationLine.getPrice());
			priceAndTax.priceActual(compensationLine.getPrice());
			priceAndTax.compensationGroupBaseAmt(compensationLine.getBaseAmt());
			// NOTE: we assume AmtType does not change so nor the Qty (which in this case shall be ONE)
		}

		return priceAndTax.build();
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final org.compiere.model.I_C_OrderLine orderLine = ic.getC_OrderLine();
		setBPartnerData(ic, orderLine);
	}

	public void setShipmentSchedule(@NonNull final I_C_Invoice_Candidate ic)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(ic.getC_OrderLine_ID());

		if (orderLineId == null)
		{
			return;
		}

		final ShipmentScheduleId shipmentScheduleId = shipmentSchedulePA.getShipmentScheduleIdByOrderLineId(orderLineId);

		if (shipmentScheduleId == null)
		{
			return;
		}

		ic.setM_ShipmentSchedule_ID(shipmentScheduleId.getRepoId());
	}

	@Override
	public final void invalidateCandidatesFor(final Object model)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(model));
	}

	private void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic, @NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();
		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.setFrom(order);
		ic.setC_BPartner_SalesRep_ID(order.getC_BPartner_SalesRep_ID());
	}

	private void setGroupCompensationData(@NonNull final I_C_Invoice_Candidate ic, @NonNull final I_C_OrderLine fromOrderLine)
	{
		if (!OrderGroupCompensationUtils.isInGroup(fromOrderLine))
		{
			return;
		}

		ic.setC_Order_CompensationGroup_ID(fromOrderLine.getC_Order_CompensationGroup_ID());
		ic.setIsGroupCompensationLine(fromOrderLine.isGroupCompensationLine());
		ic.setGroupCompensationType(fromOrderLine.getGroupCompensationType());
		ic.setGroupCompensationAmtType(fromOrderLine.getGroupCompensationAmtType());
		ic.setGroupCompensationPercentage(fromOrderLine.getGroupCompensationPercentage());
	}

	private static void setC_Flatrate_Term_ID(@NonNull final I_C_Invoice_Candidate candidate, @NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		candidate.setC_Flatrate_Term_ID(orderLine.getC_Flatrate_Term_ID());
	}
}
