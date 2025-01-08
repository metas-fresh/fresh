package de.metas.handlingunits.materialtracking.spi.impl.attribute;

import static de.metas.materialtracking.model.I_M_Material_Tracking.COLUMNNAME_C_BPartner_ID;
import static de.metas.materialtracking.model.I_M_Material_Tracking.COLUMNNAME_M_Material_Tracking_ID;
import static de.metas.materialtracking.model.I_M_Material_Tracking.COLUMNNAME_M_Product_ID;
import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.CCacheStats;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingQuery;
import de.metas.materialtracking.MaterialTrackingId;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.product.ProductId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MaterialTrackingAttributeValuesProvider implements IAttributeValuesProvider
{
	private static final String CACHE_MAIN_TABLE_NAME = I_M_Material_Tracking.Table_Name;

	private static final String PARAM_M_Product_ID = COLUMNNAME_M_Product_ID;
	private static final String PARAM_C_BPartner_ID = COLUMNNAME_C_BPartner_ID;
	private static final ImmutableSet<String> PARAMS = ImmutableSet.of(PARAM_M_Product_ID, PARAM_C_BPartner_ID);

	@VisibleForTesting
	static final CtxName CTXNAME_C_BPartner_ID = CtxNames.ofNameAndDefaultValue(COLUMNNAME_C_BPartner_ID, "/-1");

	@VisibleForTesting
	static final CtxName CTXNAME_M_Product_ID = CtxNames.ofNameAndDefaultValue(COLUMNNAME_M_Product_ID, "/-1");

	@VisibleForTesting
	static final CtxName CTXNAME_M_Material_Tracking = CtxNames.ofNameAndDefaultValue(COLUMNNAME_M_Material_Tracking_ID, "/-1");

	private final I_M_Attribute attributeRecord;

	private final CCache<ProductAndBPartner, ImmutableList<I_M_Material_Tracking>> productAndBPartner2materialTracking = CCache.<ProductAndBPartner, ImmutableList<I_M_Material_Tracking>>builder()
			.tableName(CACHE_MAIN_TABLE_NAME)
			.initialCapacity(10)
			.build();

	private final CCache<MaterialTrackingId, KeyNamePair> materialTrackingId2KeyNamePair = CCache.<MaterialTrackingId, KeyNamePair>builder()
			.tableName(CACHE_MAIN_TABLE_NAME)
			.initialCapacity(10)
			.build();

	@Value(staticConstructor = "of")
	private static final class ProductAndBPartnerAndTracking
	{
		@Nullable
		ProductAndBPartner productAndBPartner;

		@Nullable
		MaterialTrackingId materialTrackingId;
	}

	@Value(staticConstructor = "of")
	private static final class ProductAndBPartner
	{
		@NonNull
		ProductId productId;

		@NonNull
		BPartnerId bpartnerId;
	}

	public MaterialTrackingAttributeValuesProvider(@NonNull final I_M_Attribute attributeRecord)
	{
		this.attributeRecord = attributeRecord;
	}

	/**
	 * @return {@link X_M_Attribute#ATTRIBUTEVALUETYPE_StringMax40} because in our {@code M_AttributeInstance} record, the MatrialTrackingID is store in the {@code Value} column (as opposed to {@code ValueNumber})
	 * also note that {@link de.metas.handlingunits.attribute.impl.AbstractAttributeValue} insists we don't return "List".
	 */
	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;
	}

	@Override
	public boolean isAllowAnyValue()
	{
		return false;
	}

	@Override
	public Evaluatee prepareContext(@NonNull final IAttributeSet attributeSet)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

		final Evaluatees.MapEvaluateeBuilder result = Evaluatees.mapBuilder();

		final I_M_HU hu = huAttributesBL.getM_HU_OrNull(attributeSet);
		if (hu != null)
		{
			final int bpartnerId = hu.getC_BPartner_ID();
			result.put(CTXNAME_C_BPartner_ID, idOrMinusOne(bpartnerId));

			final IHUStorage huStorage = handlingUnitsBL.getStorageFactory().getStorage(hu);
			final ProductId productId = huStorage.getSingleProductIdOrNull();
			result.put(CTXNAME_M_Product_ID, idOrMinusOne(ProductId.toRepoId(productId)));
		}
		else
		{
			result.put(CTXNAME_C_BPartner_ID, -1);
			result.put(CTXNAME_M_Product_ID, -1);
		}

		if (attributeSet.hasAttribute(attributeRecord))
		{
			final int currentMaterialTrackingId = attributeSet.getValueAsInt(attributeRecord);
			result.put(CTXNAME_M_Material_Tracking, idOrMinusOne(currentMaterialTrackingId));
		}
		else
		{
			result.put(CTXNAME_M_Material_Tracking, -1);
		}

		return result.build();
	}

	private int idOrMinusOne(final int id)
	{
		return id > 0 ? id : -1;
	}

	@Override
	public Set<String> getDependsOnContextVariables()
	{
		return PARAMS;
	}

	@Override
	public List<? extends NamePair> getAvailableValues(@NonNull final Evaluatee evalCtx)
	{
		final ProductAndBPartnerAndTracking productBPartnerAndTracking = extractProductPartnerAndTrackingOrNull(evalCtx);
		if (productBPartnerAndTracking == null)
		{
			return ImmutableList.of();
		}

		final Set<NamePair> result = new HashSet<>();
		final MaterialTrackingId materialTrackingId = productBPartnerAndTracking.getMaterialTrackingId();
		if (materialTrackingId != null)
		{
			result.add(materialTrackingId2KeyNamePair.getOrLoad(materialTrackingId, id -> createNamePair(id)));
		}

		if (productBPartnerAndTracking.getProductAndBPartner() != null)
		{
			final ImmutableList<I_M_Material_Tracking> materialTrackingRecords = productAndBPartner2materialTracking
					.getOrLoad(
							productBPartnerAndTracking.getProductAndBPartner(),
							this::retrieveForCache);
			final ImmutableList<KeyNamePair> keyNamePairs = putRecordsToMaterialTrackingId2KeyNamePairCache(materialTrackingRecords);
			result.addAll(keyNamePairs);
		}
		return ImmutableList.copyOf(result);
	}

	private ImmutableList<KeyNamePair> putRecordsToMaterialTrackingId2KeyNamePairCache(@NonNull final ImmutableList<I_M_Material_Tracking> records)
	{
		final ImmutableList.Builder<KeyNamePair> result = ImmutableList.builder();

		for (final I_M_Material_Tracking materialTrackingRecord : records)
		{
			final MaterialTrackingId materialTrackingId = MaterialTrackingId.ofRepoId(materialTrackingRecord.getM_Material_Tracking_ID());
			final KeyNamePair valueNamePair = createNamePair(materialTrackingRecord);
			materialTrackingId2KeyNamePair.put(materialTrackingId, valueNamePair);
			result.add(valueNamePair);
		}

		return result.build();
	}

	private ImmutableList<I_M_Material_Tracking> retrieveForCache(@NonNull final ProductAndBPartner productAndBPartner)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final List<I_M_Material_Tracking> materialTrackingRecords = materialTrackingDAO.retrieveMaterialTrackings(
				Env.getCtx(),
				asMaterialTrackingQuery(productAndBPartner));

		return ImmutableList.copyOf(materialTrackingRecords);
	}

	private KeyNamePair createNamePair(@NonNull final MaterialTrackingId materialTrackingId)
	{
		final I_M_Material_Tracking materialTrackingRecord = load(materialTrackingId, I_M_Material_Tracking.class);
		return createNamePair(materialTrackingRecord);
	}

	@Override
	public NamePair getAttributeValueOrNull(
			@Nullable final Evaluatee evalCtx_IGNORED,
			@Nullable final Object valueKey)
	{
		final MaterialTrackingId materialTrackingId = normalizeValueKey(valueKey);
		if (materialTrackingId == null)
		{
			return getNullValue();
		}
		// note that we ignore evalCtx because it might not be complete enough to say if the value is valid or not.
		return materialTrackingId2KeyNamePair.getOrLoad(materialTrackingId, id -> createNamePair(id));
	}

	private ProductAndBPartnerAndTracking extractProductPartnerAndTrackingOrNull(@Nullable final Evaluatee evalCtx)
	{
		if (evalCtx == null)
		{
			return null;
		}

		final Integer materialTrackingRepoId = CTXNAME_M_Material_Tracking.getValueAsInteger(evalCtx);
		final MaterialTrackingId materialTrackingId = MaterialTrackingId.ofRepoIdOrNull(materialTrackingRepoId);

		return ProductAndBPartnerAndTracking.of(
				extractProductAndBPartnerOrNull(evalCtx),
				materialTrackingId);
	}

	private ProductAndBPartner extractProductAndBPartnerOrNull(@Nullable final Evaluatee evalCtx)
	{
		if (evalCtx == null)
		{
			return null;
		}

		final Integer pPartnerRepoId = CTXNAME_C_BPartner_ID.getValueAsInteger(evalCtx);
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(pPartnerRepoId);

		final Integer productRepoId = CTXNAME_M_Product_ID.getValueAsInteger(evalCtx);
		final ProductId productId = ProductId.ofRepoIdOrNull(productRepoId);

		if (productId == null || bpartnerId == null)
		{
			return null;
		}
		return ProductAndBPartner.of(productId, bpartnerId);
	}

	private IMaterialTrackingQuery asMaterialTrackingQuery(@NonNull final ProductAndBPartner productAndBPartner)
	{
		final IMaterialTrackingDAO materialTrackingRepo = Services.get(IMaterialTrackingDAO.class);

		final IMaterialTrackingQuery materialTrackingQuery = materialTrackingRepo
				.createMaterialTrackingQuery()
				.setProcessed(false) // only show "open" trackings
				.setReturnReadOnlyRecords(true) // we are not going to alter our cached records
				.setM_Product_ID(productAndBPartner.getProductId().getRepoId())
				.setC_BPartner_ID(productAndBPartner.getBpartnerId().getRepoId());

		return materialTrackingQuery;
	}

	private KeyNamePair createNamePair(@NonNull final I_M_Material_Tracking materialTrackingRecord)
	{
		final KeyNamePair valueNamePair = KeyNamePair.of(
				materialTrackingRecord.getM_Material_Tracking_ID(),
				materialTrackingRecord.getLot());
		return valueNamePair;
	}

	@Nullable
	private static MaterialTrackingId normalizeValueKey(@Nullable final Object valueKey)
	{
		if (valueKey == null)
		{
			return null;
		}
		else if (valueKey instanceof Number)
		{
			final int valueKeyAsInt = ((Number)valueKey).intValue();
			return MaterialTrackingId.ofRepoIdOrNull(valueKeyAsInt);
		}
		else
		{
			final int valueKeyAsInt = NumberUtils.asInt(valueKey.toString(), -1);
			return MaterialTrackingId.ofRepoIdOrNull(valueKeyAsInt);
		}
	}

	@Override
	public AttributeValueId getAttributeValueIdOrNull(final Object valueKey)
	{
		return null;
	}

	/** @return {@code null}. */
	@Override
	public NamePair getNullValue()
	{
		return null;
	}

	/** @return {@code false}. */
	@Override
	public boolean isHighVolume()
	{
		return false;
	}

	@Override
	public String getCachePrefix()
	{
		return CACHE_MAIN_TABLE_NAME;
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(materialTrackingId2KeyNamePair.stats());
	}
}
