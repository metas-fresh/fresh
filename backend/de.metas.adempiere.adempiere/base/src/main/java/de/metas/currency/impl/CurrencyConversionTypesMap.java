package de.metas.currency.impl;

<<<<<<< HEAD
import java.time.LocalDate;
import java.util.Collection;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

=======
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyConversionType;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
<<<<<<< HEAD
=======
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import java.time.Instant;
import java.util.Collection;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class CurrencyConversionTypesMap
{
	private final ImmutableList<CurrencyConversionTypeRouting> routings;
	private final ImmutableMap<CurrencyConversionTypeId, CurrencyConversionType> typesById;
	private final ImmutableMap<ConversionTypeMethod, CurrencyConversionType> typesByMethod;

	@Builder
	private CurrencyConversionTypesMap(
			final Collection<CurrencyConversionTypeRouting> routings,
			final Collection<CurrencyConversionType> types)
	{
		this.routings = ImmutableList.copyOf(routings);
		typesById = Maps.uniqueIndex(types, CurrencyConversionType::getId);
		typesByMethod = Maps.uniqueIndex(types, CurrencyConversionType::getMethod);
	}

	public CurrencyConversionType getById(@NonNull final CurrencyConversionTypeId id)
	{
		final CurrencyConversionType conversionType = typesById.get(id);
		if (conversionType == null)
		{
			throw new AdempiereException("@NotFound@ @C_ConversionType_ID@: " + id);
		}
		return conversionType;
	}

	public CurrencyConversionType getByMethod(@NonNull final ConversionTypeMethod method)
	{
		final CurrencyConversionType conversionType = typesByMethod.get(method);
		if (conversionType == null)
		{
			throw new AdempiereException("@NotFound@ @C_ConversionType_ID@: " + method);
		}
		return conversionType;
	}

	@NonNull
	public CurrencyConversionType getDefaultConversionType(
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId,
<<<<<<< HEAD
			@NonNull final LocalDate date)
	{
		final CurrencyConversionTypeRouting bestMatchingRouting = routings.stream()
				.filter(routing -> routing.isMatching(adClientId, adOrgId, date))
				.sorted(CurrencyConversionTypeRouting.moreSpecificFirstComparator())
				.findFirst()
=======
			@NonNull final Instant date)
	{
		final CurrencyConversionTypeRouting bestMatchingRouting = routings.stream()
				.filter(routing -> routing.isMatching(adClientId, adOrgId, date))
				.min(CurrencyConversionTypeRouting.moreSpecificFirstComparator())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.orElseThrow(() -> new AdempiereException("@NotFound@ @C_ConversionType_ID@")
						.setParameter("adClientId", adClientId)
						.setParameter("adOrgId", adOrgId)
						.setParameter("date", date)
						.appendParametersToMessage());

		return getById(bestMatchingRouting.getConversionTypeId());
	}
}
