package de.metas.distribution.ddordercandidate;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Objects;

@Getter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class DDOrderCandidate
{
	@Nullable @Setter private DDOrderCandidateId id;
	@NonNull private final OrgId orgId;

	@NonNull private final Instant dateOrdered;
	@NonNull private final Instant datePromised;

	@NonNull private final ProductId productId;
	@NonNull @Builder.Default private final HUPIItemProductId hupiItemProductId = HUPIItemProductId.VIRTUAL_HU;
	@NonNull private final Quantity qty;
	@Builder.Default private final int qtyTUs = 1;

	@NonNull @Builder.Default private final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.NONE;

	@NonNull private final WarehouseId sourceWarehouseId;
	@NonNull private final WarehouseId targetWarehouseId;
	@Nullable private final ResourceId targetPlantId;
	@NonNull private final ShipperId shipperId;

	private final boolean isSimulated;
	private final boolean isAllowPush;
	private final boolean isKeepTargetPlant;

	@Nullable private final OrderLineId salesOrderLineId;
	@Nullable private final PPOrderRef ppOrderRef;

	@Nullable private final DistributionNetworkAndLineId distributionNetworkAndLineId;
	@Nullable private final ProductPlanningId productPlanningId;

	// Not persisted fields:
	@Nullable private final String traceId;
	@Nullable private final MaterialDispoGroupId materialDispoGroupId;

	public DDOrderCandidateId getIdNotNull() {return Check.assumeNotNull(getId(), "candidate shall be saved: {}", this);}

	public DDOrderCandidate withPPOrderId(@Nullable final PPOrderId newPPOrderId)
	{
		final PPOrderRef ppOrderRefNew = PPOrderRef.withPPOrderId(ppOrderRef, newPPOrderId);
		if (Objects.equals(this.ppOrderRef, ppOrderRefNew))
		{
			return this;
		}

		return toBuilder().ppOrderRef(ppOrderRefNew).build();
	}
}
