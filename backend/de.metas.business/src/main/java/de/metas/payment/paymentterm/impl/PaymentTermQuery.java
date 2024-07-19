package de.metas.payment.paymentterm.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

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

@Value
public class PaymentTermQuery
{
	OrgId orgId;

	ExternalId externalId;

	String value;

	BPartnerId bPartnerId;

	/**
	 * Ignored, unless bPartnerId is given.
	 */
	SOTrx soTrx;

	boolean fallBackToDefault;

	@Builder(toBuilder = true)
	private PaymentTermQuery(
			@Nullable final OrgId orgId,
			@Nullable final ExternalId externalId,
			@Nullable final String value,
			@Nullable final BPartnerId bPartnerId,
			@Nullable final SOTrx soTrx,
			@Nullable final Boolean fallBackToDefault)
	{
		if (bPartnerId == null && orgId == null)
		{
			throw new AdempiereException("Either bPartnerId or orgId needs to be not-null");
		}

		this.orgId = orgId;
		this.externalId = externalId;
		this.value = value;

		this.bPartnerId = bPartnerId;
		this.soTrx = CoalesceUtil.coalesceNotNull(soTrx, SOTrx.SALES);

		this.fallBackToDefault = CoalesceUtil.coalesceNotNull(fallBackToDefault, false);
	}

	/**
	 * Convenience method. If there is no payment term for the given bpartner and soTrx, it tries to fall back to the default term.
	 */
	public static PaymentTermQuery forPartner(@NonNull final BPartnerId bPartnerId, @NonNull final SOTrx soTrx)
	{
		return PaymentTermQuery.builder()
				.bPartnerId(bPartnerId)
				.soTrx(soTrx)
				.fallBackToDefault(true)
				.build();
	}
}
