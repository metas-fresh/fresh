package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.dao.ValueRestriction;
import de.metas.document.DocumentNoFilter;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderId;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class PickingJobRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PickingJob createNewAndGet(
			@NonNull final PickingJobCreateRepoRequest request,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return new PickingJobCreateRepoHelper(loadingSupportServices).createPickingJob(request);
	}

	public PickingJobStepId newPickingJobStepId()
	{
		final int repoId = DB.getNextID(ClientId.METASFRESH.getRepoId(), I_M_Picking_Job_Step.Table_Name, ITrx.TRXNAME_None);
		return PickingJobStepId.ofRepoId(repoId);
	}

	public void save(@NonNull final PickingJob pickingJob)
	{
		PickingJobLoaderAndSaver.forSaving().save(pickingJob);
	}

	public List<PickingJob> getDraftJobsByPickerId(@NonNull final ValueRestriction<UserId> pickerId, @NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final Set<PickingJobId> pickingJobIds = queryBuilderDraftJobsByPickerId(pickerId)
				.create()
				.listIds(PickingJobId::ofRepoId);

		if (pickingJobIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadByIds(pickingJobIds);
	}

	@NonNull
	private IQueryBuilder<I_M_Picking_Job> queryBuilderDraftJobsByPickerId(@NonNull final ValueRestriction<UserId> pickerId)
	{
		final IQueryBuilder<I_M_Picking_Job> queryBuilder = queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode());

		pickerId.appendFilter(queryBuilder, I_M_Picking_Job.COLUMNNAME_Picking_User_ID);

		return queryBuilder;
	}

	public PickingJob getById(
			@NonNull final PickingJobId pickingJobId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadById(pickingJobId);
	}

	@NonNull
	public Stream<PickingJobReference> streamDraftPickingJobReferences(
			@NonNull final PickingJobReferenceQuery query,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final IQueryBuilder<I_M_Picking_Job> queryBuilder = queryBuilderDraftJobsByPickerId(ValueRestriction.equalsToOrNull(query.getPickerId()));
		final Set<BPartnerId> onlyCustomerIds = query.getOnlyBPartnerIds();
		if (!onlyCustomerIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_Picking_Job.COLUMNNAME_C_BPartner_ID, onlyCustomerIds);
		}

		final WarehouseId warehouseId = query.getWarehouseId();
		final DocumentNoFilter salesOrderDocumentNo = query.getSalesOrderDocumentNo();
		if (warehouseId != null || salesOrderDocumentNo != null)
		{
			final IQueryBuilder<I_C_Order> salesOrderQuery = queryBL.createQueryBuilder(I_C_Order.class)
					.addOnlyActiveRecordsFilter();
			if (warehouseId != null)
			{
				salesOrderQuery.addEqualsFilter(I_C_Order.COLUMNNAME_M_Warehouse_ID, warehouseId);
			}
			if (salesOrderDocumentNo != null)
			{
				salesOrderQuery.filter(salesOrderDocumentNo.toSqlFilter(I_C_Order.COLUMN_DocumentNo));
			}

			queryBuilder.addInSubQueryFilter(I_M_Picking_Job.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, salesOrderQuery.create());
		}

		final Set<PickingJobId> pickingJobIds = queryBuilder
				.create()
				.listIds(PickingJobId::ofRepoId);

		if (pickingJobIds.isEmpty())
		{
			return Stream.empty();
		}

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.streamPickingJobReferences(pickingJobIds);
	}

	public boolean hasDraftJobsUsingPickingSlot(
			@NonNull final PickingSlotId pickingSlotId,
			@Nullable final PickingJobId excludePickingJobId)
	{
		final IQueryBuilder<I_M_Picking_Job> queryBuilder = queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_M_PickingSlot_ID, pickingSlotId);

		if (excludePickingJobId != null)
		{
			queryBuilder.addNotEqualsFilter(I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, excludePickingJobId);
		}

		return queryBuilder.create().anyMatch();
	}

	public Optional<PickingJob> getDraftBySalesOrderId(
			@NonNull final OrderId salesOrderId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return queryBL.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_C_Order_ID, salesOrderId)
				.create()
				.firstIdOnlyOptional(PickingJobId::ofRepoIdOrNull)
				.map(pickingJobId -> PickingJobLoaderAndSaver.forLoading(loadingSupportServices).loadById(pickingJobId));
	}

	@NonNull
	public Map<ShipmentScheduleId, List<PickingJobId>> getPickingJobIdsByScheduleId(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return queryBL.createQueryBuilder(I_M_Picking_Job_Step.class)
				.addInArrayFilter(I_M_Picking_Job_Step.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						step -> ShipmentScheduleId.ofRepoId(step.getM_ShipmentSchedule_ID()),
						Collectors.mapping(step -> PickingJobId.ofRepoId(step.getM_Picking_Job_ID()),
										   Collectors.toList())));
	}

	@NonNull
	public List<PickingJob> getDraftedByPickingSlotId(
			@NonNull final PickingSlotId slotId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final ImmutableSet<PickingJobId> pickingJobIds = queryBL.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_M_PickingSlot_ID, slotId)
				.create()
				.listIds(PickingJobId::ofRepoId);

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadByIds(pickingJobIds);
	}

	public boolean hasReadyToReview(@NonNull final ImmutableSet<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return false;
		}

		return queryReadyToReview(pickingJobIds).anyMatch();
	}

	public List<PickingJob> getByIsReadyToReview(
			@NonNull final ImmutableSet<PickingJobId> pickingJobIds,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		if (pickingJobIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<PickingJobId> pickingJobIdsEffective = queryReadyToReview(pickingJobIds).listIds(PickingJobId::ofRepoId);

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices).loadByIds(pickingJobIdsEffective);
	}

	private IQuery<I_M_Picking_Job> queryReadyToReview(final ImmutableSet<PickingJobId> pickingJobIds)
	{
		Check.assumeNotEmpty(pickingJobIds, "pickingJobIds is not empty");

		return queryBL.createQueryBuilder(I_M_Picking_Job.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_IsReadyToReview, true)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_IsApproved, false)
				.addInArrayFilter(I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create();
	}
}
