package de.metas.pos.async;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.service.AsyncBatchService;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.InvoiceRule;
import de.metas.ordercandidate.OrderCandidate_Constants;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandValidationResult;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.payment.PaymentRule;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderId;
import de.metas.pos.POSOrderLine;
import de.metas.pos.POSOrdersRepository;
import de.metas.pos.POSPayment;
import de.metas.pos.POSPaymentMethod;
import de.metas.process.PInstanceId;
import de.metas.salesorder.candidate.ProcessOLCandsRequest;
import de.metas.salesorder.candidate.ProcessOLCandsWorkpackageEnqueuer;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_OLCand_Processing;
import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_ProcessOLCands;

public class C_POSOrder_CreateInvoiceAndShipment extends WorkpackageProcessorAdapter
{
	private static final String DATA_SOURCE_INTERNAL_NAME = "SOURCE.POS";

	@NonNull private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
	@NonNull private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	@NonNull private final POSOrdersRepository posOrdersRepository = SpringContextHolder.instance.getBean(POSOrdersRepository.class);
	@NonNull private final OLCandRepository olCandRepo = SpringContextHolder.instance.getBean(OLCandRepository.class);
	@NonNull private final OLCandValidatorService olCandValidatorService = SpringContextHolder.instance.getBean(OLCandValidatorService.class);
	@NonNull private final ProcessOLCandsWorkpackageEnqueuer processOLCandsWorkpackageEnqueuer = SpringContextHolder.instance.getBean(ProcessOLCandsWorkpackageEnqueuer.class);
	@NonNull private final AsyncBatchService asyncBatchService = SpringContextHolder.instance.getBean(AsyncBatchService.class);

	@Override
	public boolean isRunInTransaction() {return false;}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		final POSOrder posOrder = getPOSOrder();
		final List<OLCand> olCands = createOLCands(posOrder);
		Check.assumeNotEmpty(olCands, "No OLCands created for {}", posOrder);

		processOLCands(olCands);

		return Result.SUCCESS;
	}

	private POSOrder getPOSOrder()
	{
		final Set<Integer> posOrderRepoIds = retrieveAllItemIds();
		if (posOrderRepoIds.isEmpty())
		{
			throw new AdempiereException("No elements found");
		}
		else if (posOrderRepoIds.size() > 1)
		{
			throw new AdempiereException("More than one elements found");
		}

		final POSOrderId posOrderId = POSOrderId.ofRepoId(posOrderRepoIds.iterator().next());
		return posOrdersRepository.getById(posOrderId);
	}

	private List<OLCand> createOLCands(@NonNull final POSOrder posOrder)
	{
		return olCandRepo.create(
				posOrder.getLines()
						.stream()
						.map(posLine -> toOLCandCreateRequest(posLine, posOrder))
						.collect(ImmutableList.toImmutableList())
		);
	}

	private OLCandCreateRequest toOLCandCreateRequest(final POSOrderLine posLine, final POSOrder posOrder)
	{
		final BPartnerInfo bpartner = BPartnerInfo.ofLocationAndContact(posOrder.getShipToCustomerAndLocationId(), null);
		return OLCandCreateRequest.builder()
				.docTypeOrderId(posOrder.getSalesOrderDocTypeId())
				.dateOrdered(TimeUtil.asLocalDate(posOrder.getDate()))
				.dateRequired(TimeUtil.asLocalDate(posOrder.getDate()))
				//
				.pricingSystemId(posOrder.getPricingSystemId())
				// TODO make sure we use the right price list and the right taxId
				//
				// Ship From
				.orgId(posOrder.getShipFrom().getOrgId())
				.warehouseId(posOrder.getShipFrom().getWarehouseId())
				//
				// Ship To
				.bpartner(bpartner)
				//
				.productId(posLine.getProductId())
				.qty(posLine.getQty().toBigDecimal())
				.uomId(posLine.getQty().getUomId())
				// TODO: implement catch weight support
				.isManualPrice(true)
				.price(posLine.getPrice().toBigDecimal())
				.currencyId(posLine.getPrice().getCurrencyId())
				//
				.deliveryRule(DeliveryRule.FORCE.getCode())
				.deliveryViaRule(DeliveryViaRule.Pickup.getCode())
				.invoiceRule(InvoiceRule.Immediate)
				.paymentRule(getPaymentRule(posOrder))
				//
				// TODO optimize the retrieves below
				.dataSourceId(inputDataSourceDAO.retrieveInputDataSourceIdByInternalName(DATA_SOURCE_INTERNAL_NAME))
				.dataDestId(inputDataSourceDAO.retrieveInputDataSourceIdByInternalName(OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME))
				//
				.build();
	}

	private static PaymentRule getPaymentRule(final POSOrder posOrder)
	{
		final ImmutableSet<POSPaymentMethod> paymentMethods = posOrder.getPayments().stream().map(POSPayment::getPaymentMethod).collect(ImmutableSet.toImmutableSet());
		if (paymentMethods.size() == 1)
		{
			return paymentMethods.iterator().next().getPaymentRule();
		}
		else
		{
			// NOTE: because we don't have a thing like "mix payments" we go with OnCredit here.
			return PaymentRule.OnCredit;
		}
	}

	//
	//
	//
	//
	//

	public void processOLCands(@NonNull List<OLCand> olCands)
	{
		Check.assumeNotEmpty(olCands, "No Order Candidates");

		final AsyncBatchId asyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_OLCand_Processing);

		// clear the olCands and at the same time assign them to asyncBatchId
		// the asyncBatchId will be used *not* by ProcessOLCandsWorkpackageProcessor,
		// but by the WP-processors that it enqueues its packages for, to create actual the actual orders, shipments and invoices
		clearOLCandidates(olCands, asyncBatchId);

		//
		// To the actual order/shipment/invoice creation:
		// start another async-batch - just to wait for
		// ProcessOLCandsWorkpackageProcessor to finish doing the work and enqueing sub-processors.
		final AsyncBatchId processOLCandsAsyncBatchId = asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_ProcessOLCands);
		asyncBatchService.executeBatch(
				() -> {
					processOLCandsWorkpackageEnqueuer.enqueue(
							ProcessOLCandsRequest.builder()
									.pInstanceId(createOLCandsSelection(olCands))
									.ship(true)
									.invoice(true)
									//.closeOrder(true)
									.build(),
							processOLCandsAsyncBatchId);
					return () -> 1; // we always enqueue one workpackage
				},
				processOLCandsAsyncBatchId);
	}

	private void clearOLCandidates(@NonNull List<OLCand> olCands, @Nullable final AsyncBatchId asyncBatchId)
	{
		Check.assumeNotEmpty(olCands, "No Order Candidates");

		final List<I_C_OLCand> olCandRecords = unbox(olCands);
		final List<OLCandValidationResult> olCandValidationResults = olCandValidatorService.clearOLCandidates(olCandRecords, asyncBatchId);

		olCandValidationResults.forEach(olCandValidationResult -> {
			if (!olCandValidationResult.isOk())
			{
				throw new AdempiereException("Invalid Order Candidate")
						.setParameter("olCandValidationResult", olCandValidationResult);
			}
		});
	}

	private static List<I_C_OLCand> unbox(final List<OLCand> olCands)
	{
		return olCands.stream().map(OLCand::unbox).collect(Collectors.toList());
	}

	private static PInstanceId createOLCandsSelection(final List<OLCand> olCands)
	{
		final ImmutableSet<OLCandId> olCandIds = extractOLCandIds(olCands);
		return DB.createT_Selection(olCandIds, ITrx.TRXNAME_None);
	}

	private static ImmutableSet<OLCandId> extractOLCandIds(final List<OLCand> olCands)
	{
		return olCands.stream().map(olCand -> OLCandId.ofRepoId(olCand.getId())).collect(ImmutableSet.toImmutableSet());
	}
}
