/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.pattribute;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.cache.CCacheStats;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.IdsToFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.ui.web.window.model.lookup.LookupValueFilterPredicates.LookupValueFilterPredicate;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.NamePair;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString
public final class ASILookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	public static ASILookupDescriptor of(final I_M_Attribute attribute)
	{
		final IAttributeValuesProvider attributeValuesProvider = Services.get(IAttributesBL.class).createAttributeValuesProvider(attribute);
		return new ASILookupDescriptor(attributeValuesProvider);
	}

	private static final Optional<String> LookupTableName = Optional.of(I_M_AttributeValue.Table_Name);
	private static final String CONTEXT_LookupTableName = I_M_AttributeValue.Table_Name;

	private final IAttributeValuesProvider attributeValuesProvider;
	private final ImmutableSet<CtxName> dependsOnContextVariables;

	private ASILookupDescriptor(@NonNull final IAttributeValuesProvider attributeValuesProvider)
	{
		this.attributeValuesProvider = attributeValuesProvider;

		this.dependsOnContextVariables = attributeValuesProvider.getDependsOnContextVariables()
				.stream()
				.map(CtxNames::parse)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return LookupTableName;
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public boolean isHighVolume()
	{
		return attributeValuesProvider.isHighVolume();
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.list;
	}

	@Override
	public boolean hasParameters()
	{
		return false;
	}

	@Override
	public boolean isNumericKey()
	{
		return false;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return ImmutableSet.of();
	}

	@Override
	public String getCachePrefix()
	{
		return attributeValuesProvider.getCachePrefix();
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	@Override
	public void cacheInvalidate()
	{
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return attributeValuesProvider.getCacheStats();
	}

	public int getM_AttributeValue_ID(final LookupValue lookupValue)
	{
		if (lookupValue == null)
		{
			return -1;
		}

		final String valueKey = lookupValue.getIdAsString();
		final AttributeValueId attributeValueId = attributeValuesProvider.getAttributeValueIdOrNull(valueKey);
		return AttributeValueId.toRepoId(attributeValueId);
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return prepareNewContext()
				.putFilterById(IdsToFilter.ofSingleValue(id));
	}

	@Override
	public LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		final Object id = evalCtx.getSingleIdToFilterAsObject();
		final TooltipType tooltipType = Services.get(IADTableDAO.class).getTooltipTypeByTableName(evalCtx.getTableName());
		final NamePair valueNP = attributeValuesProvider.getAttributeValueOrNull(evalCtx, id);
		return LookupValue.fromNamePair(valueNP, evalCtx.getAD_Language(), LOOKUPVALUE_NULL, tooltipType);
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return prepareNewContext()
				.requiresParameters(dependsOnContextVariables)
				.requiresFilterAndLimit();
	}

	private LookupDataSourceContext.Builder prepareNewContext()
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName);
	}

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final LookupValueFilterPredicate filter = evalCtx.getFilterPredicate();
		final int limit = evalCtx.getLimit(Integer.MAX_VALUE);
		final int offset = evalCtx.getOffset(0);

		return attributeValuesProvider.getAvailableValues(evalCtx)
				.stream()
				.map(namePair -> StringLookupValue.of(
						namePair.getID(),
						TranslatableStrings.constant(namePair.getName()),
						TranslatableStrings.constant(namePair.getDescription())))
				.filter(filter)
				.collect(LookupValuesList.collect())
				.pageByOffsetAndLimit(offset, limit);


	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}
}
