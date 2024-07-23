package de.metas.material.event.ddordercandidate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Value
@Builder(toBuilder = true)
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DDOrderCandidateData
{
	@NonNull ProductPlanningId productPlanningId;
	@Nullable DistributionNetworkAndLineId distributionNetworkAndLineId;

	@NonNull OrgId orgId;
	@NonNull WarehouseId sourceWarehouseId;
	@NonNull WarehouseId targetWarehouseId;
	@NonNull ResourceId targetPlantId;
	@NonNull ShipperId shipperId;

	int customerId;
	int salesOrderLineId;
	@Nullable PPOrderRef ppOrderRef;

	@NonNull ProductDescriptor productDescriptor;
	@Nullable MinMaxDescriptor fromWarehouseMinMaxDescriptor;

	@NonNull Instant datePromised;

	@NonNull BigDecimal qty;
	int uomId;

	int durationDays;

	boolean simulated;

	/**
	 * Not persisted in the {@code DD_Order} data record, but
	 * when material-dispo posts {@link DDOrderCandidateRequestedEvent}, it contains a group-ID,
	 * and the respective {@link DDOrderCandidateCreatedEvent} contains the same group-ID.
	 */
	@Nullable MaterialDispoGroupId materialDispoGroupId;

	@JsonIgnore
	public int getProductId() {return getProductDescriptor().getProductId();}

	@JsonIgnore
	public int getAttributeSetInstanceId() {return getProductDescriptor().getAttributeSetInstanceId();}

	@JsonIgnore
	public AttributesKey getStorageAttributesKey() {return getProductDescriptor().getStorageAttributesKey();}

	public void assertMaterialDispoGroupIdIsSet()
	{
		if (materialDispoGroupId == null)
		{
			throw new AdempiereException("Expected materialDispoGroupId to be set: " + this);
		}
	}

	public DDOrderCandidateData withPPOrderId(@Nullable final PPOrderId newPPOrderId)
	{
		final PPOrderRef ppOrderRefNew = PPOrderRef.withPPOrderId(ppOrderRef, newPPOrderId);
		if (Objects.equals(this.ppOrderRef, ppOrderRefNew))
		{
			return this;
		}

		return toBuilder().ppOrderRef(ppOrderRefNew).build();
	}

}
