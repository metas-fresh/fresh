/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.deliveryplanning;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.incoterms.IncotermsId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@Builder
public class DeliveryInstructionCreateRequest
{
	@NonNull OrgId orgId;

	@NonNull ClientId clientId;

	@NonNull BPartnerId shipperBPartnerId;

	@NonNull BPartnerLocationId shipperLocationId;

	@NonNull BPartnerLocationId loadingPartnerLocationId;

	@NonNull BPartnerLocationId deliveryPartnerLocationId;

	@Nullable
	IncotermsId incotermsId;

	@Nullable String incotermLocation;

	@Nullable
	Instant loadingDate;

	@Nullable
	String loadingTime;

	@Nullable
	Instant deliveryDate;

	@Nullable
	String deliveryTime;

	@NonNull
	Instant dateDoc;

	@NonNull
	ShipperId shipperId;

	@NonNull DocTypeId docTypeId;

	@Nullable
	MeansOfTransportationId meansOfTransportationId;

	boolean processed;

	boolean isToBeFetched;

	@NonNull ProductId productId;

	@Nullable String batchNo;

	@Nullable LocatorId locatorId;

	@NonNull Quantity qtyLoaded;

	@NonNull Quantity qtyDischarged;

	@NonNull I_C_UOM uom;

}
