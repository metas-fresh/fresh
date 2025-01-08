package org.adempiere.mm.attributes.spi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ISubProducerAttributeDAO;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
import de.metas.cache.CCacheStats;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

class HUSubProducerBPartnerAttributeValuesProvider implements IAttributeValuesProvider
{
	static final String ATTRIBUTEVALUETYPE = X_M_Attribute.ATTRIBUTEVALUETYPE_Number;

	private static final String CACHE_PREFIX = IAttributeDAO.CACHEKEY_ATTRIBUTE_VALUE;

	/**
	 * Cache: C_BPartner_ID to list of sub-producer partners (as KeyNamePair)
	 *
	 * NOTE: we use a static cache for optimization purposes
	 */
	private static final CCache<Integer, List<KeyNamePair>> bpartnerId2subProducers = CCache.<Integer, List<KeyNamePair>> builder()
			.cacheName(CACHE_PREFIX + "#by#" + I_C_BP_Relation.COLUMNNAME_C_BPartner_ID + "#" + I_C_BP_Relation.COLUMNNAME_Role)
			.initialCapacity(10)
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
			.additionalTableNameToResetFor(I_C_BP_Relation.Table_Name)
			.build();

	private static final CtxName CTXNAME_M_HU_ID = CtxNames.parse("M_HU_ID/-1");
	private static final CtxName CTXNAME_C_BPartner_ID = CtxNames.parse("C_BPartner_ID/-1");
	private static final CtxName CTXNAME_CurrentSubProducer_BPartner_ID = CtxNames.parse("CurrentSubProducer_BPartner_ID/-1");

	//
	//
	private final I_M_Attribute attribute;

	/**
	 * Cache M_HU_ID/C_BPartner_ID/Current_SubProducer_BPartnerId to list of sub-producer partners (as KeyNamePair).
	 *
	 * Used for short term purposes.
	 */
	private final CCache<ArrayKey, List<KeyNamePair>> hu2subProducers = CCache.<ArrayKey, List<KeyNamePair>> builder()
			.cacheName(bpartnerId2subProducers.getCacheName() + "#AndHU")
			.initialCapacity(100)
			.expireMinutes(10)
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
			.additionalTableNameToResetFor(I_C_BP_Relation.Table_Name)
			.build();

	public HUSubProducerBPartnerAttributeValuesProvider(final I_M_Attribute attribute)
	{
		Check.assumeNotNull(attribute, "Parameter attribute is not null");
		this.attribute = attribute;
	}

	@Override
	public String getCachePrefix()
	{
		return CACHE_PREFIX;
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(bpartnerId2subProducers.stats(), hu2subProducers.stats());
	}

	@Override
	public String getAttributeValueType()
	{
		return ATTRIBUTEVALUETYPE;
	}

	@Override
	public final KeyNamePair getNullValue()
	{
		return staticNullValue();
	}

	static KeyNamePair staticNullValue()
	{
		final ITranslatableString displayNameTrl = Services.get(IMsgBL.class).translatable("NoneOrEmpty");

		final String adLanguage = Env.getAD_Language(Env.getCtx());
		final String displayName = adLanguage != null ? displayNameTrl.translate(adLanguage) : displayNameTrl.getDefaultValue();

		// NOTE: we use KeyNamePair's Key=0 because "-1" is specially handled by KeyNamePair (see KeyNamePair.getID() which returns null)
		// and we run in some weird problems
		return KeyNamePair.of(0, displayName);

	}

	@Override
	public boolean isHighVolume()
	{
		// NOTE: we don't expect to have too many sub-producers for one producer
		return false;
	}

	@Override
	public boolean isAllowAnyValue()
	{
		// NOTE: we need to allow setting any BPartner no matter what.
		// Anyways, in UI user will be allowed to pick only from suggested ones,
		// and this change is mainly for the case when we are programatically set the BPartner from our BL.
		// e.g. when the HU was receipt we are setting the HU's BPartner as sub-producer (08027)
		return true;

		// final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU_OrNull(attributeSet);
		// if (hu == null)
		// {
		// // in case we don't have an uderlying HU (e.g. we have an ASI attribute set) then accept any value
		// return true;
		// }
		//
		// return false;
	}

	@Override
	public Evaluatee prepareContext(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU(attributeSet);
		final int bpartnerId = hu.getC_BPartner_ID();
		final int huId = hu.getM_HU_ID();

		int currentSubProducerBPartnerId = -1;
		if (attributeSet.hasAttribute(attribute))
		{
			currentSubProducerBPartnerId = attributeSet.getValueAsInt(attribute);
		}

		return Evaluatees.mapBuilder()
				.put(CTXNAME_M_HU_ID, huId > 0 ? huId : -1)
				.put(CTXNAME_C_BPartner_ID, bpartnerId > 0 ? bpartnerId : -1)
				.put(CTXNAME_CurrentSubProducer_BPartner_ID, currentSubProducerBPartnerId > 0 ? currentSubProducerBPartnerId : -1)
				.build();
	}

	@Override
	public List<? extends NamePair> getAvailableValues(final Evaluatee evalCtx)
	{
		final int huId = CTXNAME_M_HU_ID.getValueAsInteger(evalCtx);
		final int bpartnerId = CTXNAME_C_BPartner_ID.getValueAsInteger(evalCtx);
		final int currentSubProducerBPartnerId = CTXNAME_CurrentSubProducer_BPartner_ID.getValueAsInteger(evalCtx);

		//
		// Create the cache key based on M_HU_ID, M_HU.C_BPartner_ID and current SubProducer_BPartner_ID
		final ArrayKey cacheKey = ArrayKey.of(
				huId <= 0 ? System.currentTimeMillis() : huId,      // if M_HU_ID <= 0 then make it unique
				bpartnerId <= 0 ? -1 : bpartnerId,      // normalize
				currentSubProducerBPartnerId <= 0 ? -1 : currentSubProducerBPartnerId // normalize
		);

		//
		// Get from cache / Load
		return hu2subProducers.getOrLoad(cacheKey, () -> retrieveSubProducerKeyNamePairs(bpartnerId, currentSubProducerBPartnerId));
	}

	@Nullable
	private static String normalizeValueKey(final Object valueKey)
	{
		if (valueKey == null)
		{
			return null;
		}
		else if (valueKey instanceof Number)
		{
			final int valueKeyAsInt = ((Number)valueKey).intValue();
			return String.valueOf(valueKeyAsInt);
		}
		else
		{
			return valueKey.toString();
		}
	}

	@Override
	@Nullable
	public NamePair getAttributeValueOrNull(final Evaluatee evalCtx, final Object valueKey)
	{
		final List<? extends NamePair> availableValues = getAvailableValues(evalCtx);
		if (availableValues.isEmpty())
		{
			return null;
		}

		// Normalize the valueKey
		final String valueKeyNormalized = normalizeValueKey(valueKey);

		for (final NamePair vnp : availableValues)
		{
			if (Objects.equals(vnp.getID(), valueKeyNormalized))
			{
				return vnp;
			}
		}

		return null;
	}

	@Nullable
	@Override
	public AttributeValueId getAttributeValueIdOrNull(final Object valueKey)
	{
		return null;
	}

	/**
	 * Retrieve allowed subproducers KeyNamePairs.
	 *
	 * Mainly it will contain following values:
	 * <ul>
	 * <li>{@link #getNullValue()}
	 * <li>allowed SubProducer BPartners for M_HU.C_BPartner_ID
	 * <li>current HU's SubProducer BPartner if not present in the list above
	 * </ul>
	 *
	 * @param bpartnerId i.e. M_HU.C_BPartner_ID
	 * @param currentSubProducerBPartnerId current subproducer BPartner ID
	 * @return available values
	 */
	private static List<KeyNamePair> retrieveSubProducerKeyNamePairs(
			final int bpartnerId,
			final int currentSubProducerBPartnerId)
	{
		final Properties ctx = Env.getCtx();

		final List<KeyNamePair> subProducerBPartners = new ArrayList<>();
		subProducerBPartners.add(staticNullValue());

		//
		// Retrieve subProducers from cache
		if (bpartnerId > 0)
		{
			final List<KeyNamePair> subProducerBPartnersCached = bpartnerId2subProducers.getOrLoad(bpartnerId, () -> retrieveSubProducerKeyNamePairs(ctx, bpartnerId));
			subProducerBPartners.addAll(subProducerBPartnersCached);
		}

		//
		// Make sure current subProducer is in our list
		// NOTE: we need to make sure that attribute exists because if we are about generating seed values, at this moment attribute is not there yet.
		addSubProducer(subProducerBPartners, ctx, currentSubProducerBPartnerId);

		return subProducerBPartners;
	}

	private static void addSubProducer(final List<KeyNamePair> subProducerBPartners, final Properties ctx, final int bpartnerId)
	{
		if (bpartnerId <= 0)
		{
			return;
		}

		//
		// Check if our BPartner is already in the list.
		// If yes, there is no point to add it.
		if (subProducerBPartners != null)
		{
			for (final KeyNamePair subProducer : subProducerBPartners)
			{
				if (subProducer == null)
				{
					continue;
				}
				if (subProducer.getKey() == bpartnerId)
				{
					// our bpartner is already in the list
					return;
				}
			}
		}

		//
		// Load the new BPartner and add it to our list
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(ctx, bpartnerId, I_C_BPartner.class, ITrx.TRXNAME_None);
		if (bpartner == null)
		{
			return;
		}
		final KeyNamePair bpartnerKNP = toKeyNamePair(bpartner);
		subProducerBPartners.add(bpartnerKNP);
	}

	/**
	 * Retrieves suggested SubProducer BPartners for given <code>bpartnerId</code>
	 *
	 * @return list of bpartner's suggested SubProducers
	 */
	private static List<KeyNamePair> retrieveSubProducerKeyNamePairs(final Properties ctx, final int bpartnerId)
	{
		return Services.get(ISubProducerAttributeDAO.class)
				.retrieveSubProducerBPartners(ctx, bpartnerId)
				.stream()
				.map(bpartner -> toKeyNamePair(bpartner))
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * Convert given {@link I_C_BPartner} to a {@link KeyNamePair}.
	 */
	private static final KeyNamePair toKeyNamePair(@NonNull final I_C_BPartner bpartner)
	{
		return KeyNamePair.of(bpartner.getC_BPartner_ID(), bpartner.getName(), bpartner.getDescription());
	}

}
