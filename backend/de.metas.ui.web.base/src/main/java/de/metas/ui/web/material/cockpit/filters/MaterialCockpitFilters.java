package de.metas.ui.web.material.cockpit.filters;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Predicate;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class MaterialCockpitFilters
{
	private DocumentFilterDescriptorsProvider filterDescriptors;

	public DocumentFilterDescriptorsProvider getFilterDescriptors()
	{
		if (filterDescriptors == null)
		{
			filterDescriptors = createFilterDescriptors();
		}
		return filterDescriptors;
	}

	private DocumentFilterDescriptorsProvider createFilterDescriptors()
	{
		return ImmutableDocumentFilterDescriptorsProvider.of(
				DateFilterUtil.createFilterDescriptor(),
				ProductFilterUtil.createFilterDescriptor());
	}

	@NonNull
	public DocumentFilterList createAutoFilters()
	{
		return DocumentFilterList.of(DateFilterUtil.createFilterToday(),
									 ProductFilterUtil.createFilterActiveProducts());
	}

	public DocumentFilterList extractDocumentFilters(@NonNull final CreateViewRequest request)
	{
		final DocumentFilterDescriptorsProvider provider = getFilterDescriptors();
		return request.getFiltersUnwrapped(provider);
	}

	public IQuery<I_MD_Cockpit> createQuery(@NonNull final DocumentFilterList filters)
	{
		final IQueryBuilder<I_MD_Cockpit> queryBuilder = createInitialQueryBuilder();

		boolean anyRestrictionAdded = false;
		if (augmentQueryBuilder(queryBuilder, DateFilterUtil.extractDateFilterVO(filters)))
		{
			anyRestrictionAdded = true;
		}
		if (augmentQueryBuilder(queryBuilder, ProductFilterUtil.extractProductFilterVO(filters)))
		{
			anyRestrictionAdded = true;
		}

		if (anyRestrictionAdded)
		{
			final IQuery<I_MD_Cockpit> query = augmentQueryBuilderWithOrderBy(queryBuilder).create();
			return query;
		}
		else
		{
			// avoid memory problems in case the filters are accidentally empty
			return queryBuilder.filter(ConstantQueryFilter.of(false)).create();
		}
	}

	private IQueryBuilder<I_MD_Cockpit> createInitialQueryBuilder()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Cockpit> queryBuilder = queryBL
				.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter();
		return queryBuilder;
	}

	private boolean augmentQueryBuilder(final IQueryBuilder<I_MD_Cockpit> queryBuilder, final DateFilterVO dateFilterVO)
	{
		if (dateFilterVO == null)
		{
			return false;
		}

		final LocalDate date = dateFilterVO.getDate();
		if (date == null)
		{
			return false;
		}

		queryBuilder.addCompareFilter(I_MD_Cockpit.COLUMN_DateGeneral, CompareQueryFilter.Operator.GREATER_OR_EQUAL, date);

		return true;
	}

	private boolean augmentQueryBuilder(final IQueryBuilder<I_MD_Cockpit> queryBuilder, final ProductFilterVO productFilterVO)
	{
		final IQuery<I_M_Product> productQuery = ProductFilterUtil.createProductQueryOrNull(productFilterVO);
		if (productQuery == null)
		{
			return false;
		}

		queryBuilder.addInSubQueryFilter(I_MD_Cockpit.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID, productQuery);
		return true;
	}

	private IQueryBuilder<I_MD_Cockpit> augmentQueryBuilderWithOrderBy(@NonNull final IQueryBuilder<I_MD_Cockpit> queryBuilder)
	{
		return queryBuilder
				.orderBy()
				.addColumn(I_MD_Cockpit.COLUMN_DateGeneral)
				.addColumn(I_MD_Cockpit.COLUMNNAME_M_Product_ID)
				.addColumn(I_MD_Cockpit.COLUMN_AttributesKey)
				.endOrderBy();
	}

	public LocalDate getFilterByDate(@NonNull final DocumentFilterList filters)
	{
		final DateFilterVO dateFilterVO = DateFilterUtil.extractDateFilterVO(filters);
		return dateFilterVO.getDate();
	}

	public Predicate<I_M_Product> toProductFilterPredicate(@NonNull final DocumentFilterList filters)
	{
		return ProductFilterUtil.toPredicate(filters);
	}


}
