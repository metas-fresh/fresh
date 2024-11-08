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

package de.metas.product.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.organization.OrgId;
import de.metas.product.CreateProductRequest;
import de.metas.product.IProductDAO;
import de.metas.product.IProductMappingAware;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaSelector;
import de.metas.product.ResourceId;
import de.metas.product.UpdateProductRequest;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_SupplierApproval_Norm;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.util.Check.isEmpty;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByIdsOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ProductDAO implements IProductDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	final static int ONE_YEAR_DAYS = 365;
	final static int TWO_YEAR_DAYS = 730;
	final static int THREE_YEAR_DAYS = 1095;
	final static int FIVE_YEAR_DAYS = 1825;

	private final CCache<Integer, ProductCategoryId> defaultProductCategoryCache = CCache.<Integer, ProductCategoryId>builder()
			.tableName(I_M_Product_Category.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	@Override
	public I_M_Product getById(@NonNull final ProductId productId)
	{
		return getById(productId, I_M_Product.class);
	}

	@Override
	public <T extends I_M_Product> T getById(@NonNull final ProductId productId, @NonNull final Class<T> productClass)
	{
		// we can't load out-of-trx, because it's possible that the product was created just now, within the current trx!
		return getByIdInTrx(productId, productClass);
	}

	@Override
	public <T extends I_M_Product> T getByIdInTrx(@NonNull final ProductId productId, @NonNull final Class<T> productClass)
	{
		final T product = load(productId, productClass);
		if (product == null)
		{
			throw new AdempiereException("@NotFound@ @M_Product_ID@: " + productId);
		}
		return product;
	}

	@Override
	public I_M_Product getByIdInTrx(@NonNull final ProductId productId)
	{
		return getByIdInTrx(productId, I_M_Product.class);
	}

	@Override
	public I_M_Product getById(final int productId)
	{
		return getById(ProductId.ofRepoId(productId), I_M_Product.class);
	}

	@Override
	public List<I_M_Product> getByIds(@NonNull final Set<ProductId> productIds)
	{
		return loadByRepoIdAwaresOutOfTrx(productIds, I_M_Product.class);
	}

	@Override
	public I_M_Product retrieveProductByValue(@NonNull final String value)
	{
		final ProductId productId = retrieveProductIdByValue(value);
		return productId != null ? getById(productId) : null;
	}

	@Nullable
	@Override
	public ProductId retrieveProductIdByValue(@NonNull final String value)
	{
		return retrieveProductIdByValueOrNull(Env.getCtx(), value);
	}

	@Nullable
	@Cached(cacheName = I_M_Product.Table_Name + "#ID#by#" + I_M_Product.COLUMNNAME_Value)
	public ProductId retrieveProductIdByValueOrNull(@CacheCtx final Properties ctx, @NonNull final String value)
	{
		final int productRepoId = queryBL.createQueryBuilder(I_M_Product.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_Product.COLUMNNAME_Value, value)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.create()
				.firstIdOnly();
		return ProductId.ofRepoIdOrNull(productRepoId);
	}

	@Nullable
	@Override
	public ProductId retrieveProductIdBy(@NonNull final ProductQuery query)
	{
		final IQueryBuilder<I_M_Product> queryBuilder;
		if (query.isOutOfTrx())
		{
			queryBuilder = Services
					.get(IQueryBL.class)
					.createQueryBuilderOutOfTrx(I_M_Product.class)
					.setOption(IQuery.OPTION_ReturnReadOnlyRecords, true);
		}
		else
		{
			queryBuilder = Services
					.get(IQueryBL.class)
					.createQueryBuilder(I_M_Product.class);
		}

		if (query.isIncludeAnyOrg())
		{
			queryBuilder
					.addInArrayFilter(I_M_Product.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY)
					.orderByDescending(I_M_Product.COLUMNNAME_AD_Org_ID);
		}
		else
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, query.getOrgId());
		}

		if (!isEmpty(query.getValue(), true))
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMNNAME_Value, query.getValue().trim());
		}
		if (query.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMNNAME_ExternalId, query.getExternalId().getValue().trim());
		}

		final int productRepoId = queryBuilder
				.addOnlyActiveRecordsFilter()
				.create()
				.firstId();

		return ProductId.ofRepoIdOrNull(productRepoId);
	}

	@Override
	public Optional<ProductCategoryId> retrieveProductCategoryIdByCategoryValue(@NonNull final String categoryValue)
	{
		final int productCategoryRepoId = queryBL
				.createQueryBuilder(I_M_Product_Category.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Value, categoryValue)
				.create()
				.firstIdOnly();

		return Optional.ofNullable(ProductCategoryId.ofRepoIdOrNull(productCategoryRepoId));
	}

	@Override
	public Stream<I_M_Product> streamAllProducts(@Nullable final Instant since)
	{
		final IQueryBuilder<I_M_Product> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_M_Product.class)
				.addOnlyActiveRecordsFilter();

		if (since != null)
		{
			final Timestamp updatedAfter = TimeUtil.asTimestamp(since);
			queryBuilder.addCompareFilter(I_M_Product.COLUMNNAME_Updated, CompareQueryFilter.Operator.GREATER_OR_EQUAL, updatedAfter);
		}

		return queryBuilder
				.orderBy(I_M_Product.COLUMNNAME_M_Product_ID)
				.create()
				.iterateAndStream();
	}

	@Override
	public @NonNull ProductCategoryId getDefaultProductCategoryId()
	{
		return defaultProductCategoryCache.getOrLoad(0, this::retrieveDefaultProductCategoryId);
	}

	/**
	 * @return All the active products with the given product planning schema selector
	 */
	@Override
	public Set<ImmutablePair<ProductId, OrgId>> retrieveProductsAndOrgsForSchemaSelector(
			@NonNull final ProductPlanningSchemaSelector productPlanningSchemaSelector)
	{
		return queryBL
				.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_ProductPlanningSchema_Selector, productPlanningSchemaSelector)
				.create()
				.listColumns(I_M_Product.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_AD_Org_ID)
				.stream()
				.map(pair -> {
					final ProductId productId = ProductId.ofRepoId((int)pair.get(I_M_Product.COLUMNNAME_M_Product_ID));
					final OrgId orgId = OrgId.ofRepoId((int)pair.get(I_M_Product.COLUMNNAME_AD_Org_ID));
					return ImmutablePair.of(productId, orgId);
				})
				.collect(Collectors.toSet());
	}

	private ProductCategoryId retrieveDefaultProductCategoryId()
	{
		final ProductCategoryId productCategoryId = queryBL
				.createQueryBuilderOutOfTrx(I_M_Product_Category.class)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_M_Product_Category.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Product_Category.COLUMNNAME_M_Product_Category_ID)
				.endOrderBy()
				.create()
				.firstId(ProductCategoryId::ofRepoIdOrNull);
		if (productCategoryId == null)
		{
			throw new AdempiereException("default product category shall exist");
		}
		return productCategoryId;
	}

	@Nullable
	@Override
	public ProductId retrieveMappedProductIdOrNull(final ProductId productId, final OrgId orgId)
	{
		final I_M_Product product = getById(productId);
		final IProductMappingAware productMappingAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(product, IProductMappingAware.class);
		if (productMappingAware.getM_Product_Mapping_ID() <= 0)
		{
			return null;
		}
		if (!productMappingAware.getM_Product_Mapping().isActive())
		{
			return null;
		}

		return queryBL.createQueryBuilderOutOfTrx(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(IProductMappingAware.COLUMNNAME_M_Product_Mapping_ID, productMappingAware.getM_Product_Mapping_ID())
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstIdOnly(ProductId::ofRepoIdOrNull);
	}

	@Override
	public List<de.metas.product.model.I_M_Product> retrieveAllMappedProducts(final I_M_Product product)
	{
		final IProductMappingAware productMappingAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(product, IProductMappingAware.class);
		if (productMappingAware.getM_Product_Mapping_ID() <= 0)
		{
			return Collections.emptyList();
		}
		if (!productMappingAware.getM_Product_Mapping().isActive())
		{
			return Collections.emptyList();
		}

		return queryBL.createQueryBuilder(de.metas.product.model.I_M_Product.class, product)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(IProductMappingAware.COLUMNNAME_M_Product_Mapping_ID, productMappingAware.getM_Product_Mapping_ID())
				.addNotEqualsFilter(I_M_Product.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.create()
				.list(de.metas.product.model.I_M_Product.class);
	}

	@Nullable
	@Override
	public ProductCategoryId retrieveProductCategoryByProductId(@Nullable final ProductId productId)
	{
		if (productId == null)
		{
			return null;
		}

		final I_M_Product product = getById(productId);
		return product != null ? ProductCategoryId.ofRepoId(product.getM_Product_Category_ID()) : null;
	}

	@Nullable
	@Override
	public ProductAndCategoryId retrieveProductAndCategoryIdByProductId(@NonNull final ProductId productId)
	{
		final ProductCategoryId productCategoryId = retrieveProductCategoryByProductId(productId);
		return productCategoryId != null ? ProductAndCategoryId.of(productId, productCategoryId) : null;
	}

	@Override
	public ProductAndCategoryAndManufacturerId retrieveProductAndCategoryAndManufacturerByProductId(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		if (!product.isActive())
		{
			throw new AdempiereException("Cannot retrieve product category and manufacturer because product is not active: " + product);
		}

		return createProductAndCategoryAndManufacturerId(product);
	}

	@Override
	public String retrieveProductValueByProductId(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		return product.getValue();
	}

	@Override
	public Set<ProductAndCategoryAndManufacturerId> retrieveProductAndCategoryAndManufacturersByProductIds(final Set<ProductId> productIds)
	{
		return loadByIdsOutOfTrx(ProductId.toRepoIds(productIds), I_M_Product.class)
				.stream()
				.map(this::createProductAndCategoryAndManufacturerId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private ProductAndCategoryAndManufacturerId createProductAndCategoryAndManufacturerId(final I_M_Product product)
	{
		return ProductAndCategoryAndManufacturerId.of(product.getM_Product_ID(), product.getM_Product_Category_ID(), product.getManufacturer_ID());
	}

	@Override
	public I_M_Product_Category getProductCategoryById(@NonNull final ProductCategoryId id)
	{
		return getProductCategoryById(id, I_M_Product_Category.class);
	}

	@Override
	public <T extends I_M_Product_Category> T getProductCategoryById(@NonNull final ProductCategoryId id, final Class<T> modelClass)
	{
		return loadOutOfTrx(id, modelClass);
	}

	@Override
	public String getProductCategoryNameById(@NonNull final ProductCategoryId id)
	{
		return getProductCategoryById(id).getName();
	}

	@Override
	public Stream<I_M_Product_Category> streamAllProductCategories()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_M_Product_Category.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_Product_Category.COLUMN_M_Product_Category_ID)
				.create()
				.iterateAndStream();
	}

	@Cached(cacheName = I_M_Product.Table_Name + "#by#" + I_M_Product.COLUMNNAME_S_Resource_ID)
	@Override
	public ProductId getProductIdByResourceId(@NonNull final ResourceId resourceId)
	{
		final ProductId productId = queryBL
				.createQueryBuilderOutOfTrx(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMN_S_Resource_ID, resourceId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(ProductId::ofRepoIdOrNull);
		if (productId == null)
		{
			throw new AdempiereException("No product found for " + resourceId);
		}
		return productId;
	}

	@Override
	public void updateProductsByResourceIds(@NonNull final Set<ResourceId> resourceIds, @NonNull final Consumer<I_M_Product> productUpdater)
	{
		updateProductsByResourceIds(resourceIds, (resourceId, product) -> {
			if (product != null)
			{
				productUpdater.accept(product);
			}
		});
	}

	@Override
	public void updateProductsByResourceIds(@NonNull final Set<ResourceId> resourceIds, @NonNull final BiConsumer<ResourceId, I_M_Product> productUpdater)
	{
		Check.assumeNotEmpty(resourceIds, "resourceIds is not empty");

		final Set<ProductId> productIds = queryBL
				.createQueryBuilder(I_M_Product.class) // in trx!
				.addInArrayFilter(I_M_Product.COLUMN_S_Resource_ID, resourceIds)
				.create()
				.listIds(ProductId::ofRepoId);
		if (productIds.isEmpty())
		{
			return;
		}

		final Map<ResourceId, I_M_Product> productsByResourceId = Maps.uniqueIndex(
				loadByRepoIdAwares(productIds, I_M_Product.class),
				product -> ResourceId.ofRepoId(product.getS_Resource_ID()));

		resourceIds.forEach(resourceId -> {
			final I_M_Product product = productsByResourceId.get(resourceId); // might be null
			productUpdater.accept(resourceId, product);
			saveRecord(product);
		});
	}

	@Override
	public void deleteProductByResourceId(@NonNull final ResourceId resourceId)
	{
		queryBL
				.createQueryBuilder(I_M_Product.class) // in trx
				.addEqualsFilter(I_M_Product.COLUMN_S_Resource_ID, resourceId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.delete();
	}

	@Override
	public I_M_Product createProduct(@NonNull final CreateProductRequest request)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		if (request.getProductValue() != null)
		{
			product.setValue(request.getProductValue());
		}
		product.setName(request.getProductName());
		product.setM_Product_Category_ID(request.getProductCategoryId().getRepoId());
		product.setProductType(request.getProductType());
		product.setC_UOM_ID(request.getUomId().getRepoId());
		product.setIsPurchased(request.isPurchased());
		product.setIsSold(request.isSold());

		if (request.getBomVerified() != null)
		{
			product.setIsVerified(request.getBomVerified());
		}

		if (request.getPlanningSchemaSelector() != null)
		{
			product.setM_ProductPlanningSchema_Selector(request.getPlanningSchemaSelector().getCode());
		}

		saveRecord(product);

		return product;
	}

	@Override
	public void updateProduct(@NonNull final UpdateProductRequest request)
	{
		final I_M_Product product = load(request.getProductId(), I_M_Product.class); // in-trx

		if (request.getIsBOM() != null)
		{
			product.setIsBOM(request.getIsBOM());
			if (!request.getIsBOM())
			{
				product.setIsVerified(false);
			}
		}

		saveRecord(product);
	}

	@Override
	public int getProductGuaranteeDaysMinFallbackProductCategory(final @NonNull ProductId productId)
	{
		final I_M_Product productRecord = getById(productId);
		if (productRecord.getGuaranteeDaysMin() > 0)
		{
			return productRecord.getGuaranteeDaysMin();
		}
		else if (Check.isNotBlank(productRecord.getGuaranteeMonths()))
		{
			return getGuaranteeMonthsInDays(productId);
		}
		else
		{
			final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(productRecord.getM_Product_Category_ID());
			final I_M_Product_Category productCategoryRecord = getProductCategoryById(productCategoryId);
			return productCategoryRecord.getGuaranteeDaysMin();
		}
	}

	@Override
	public int getGuaranteeMonthsInDays(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		if (product != null && Check.isNotBlank(product.getGuaranteeMonths()))
		{
			switch (product.getGuaranteeMonths())
			{
				case X_M_Product.GUARANTEEMONTHS_12:
					return ONE_YEAR_DAYS;
				case X_M_Product.GUARANTEEMONTHS_24:
					return TWO_YEAR_DAYS;
				case X_M_Product.GUARANTEEMONTHS_36:
					return THREE_YEAR_DAYS;
				case X_M_Product.GUARANTEEMONTHS_60:
					return FIVE_YEAR_DAYS;
				default:
					return 0;
			}
		}
		return 0;
	}

	@Override
	public Optional<ProductId> getProductIdByBarcode(@NonNull final String barcode, @NonNull final ClientId clientId)
	{
		final ProductId productId = queryBL.createQueryBuilderOutOfTrx(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Client_ID, clientId)
				.filter(queryBL.createCompositeQueryFilter(I_M_Product.class)
								.setJoinOr()
								.addEqualsFilter(I_M_Product.COLUMNNAME_UPC, barcode)
								.addEqualsFilter(I_M_Product.COLUMNNAME_Value, barcode))
				.create()
				.firstIdOnly(ProductId::ofRepoIdOrNull);

		return Optional.ofNullable(productId);
	}

	@Override
	public Optional<GroupTemplateId> getGroupTemplateIdByProductId(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		return GroupTemplateId.optionalOfRepoId(product.getC_CompensationGroup_Schema_ID());
	}

	@Override
	public void clearIndividualMasterDataFromProduct(final ProductId productId)
	{
		final I_M_Product product = getById(productId);

		product.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());

		saveRecord(product);
	}

	@Override
	public Optional<de.metas.product.model.I_M_Product> getProductOfGroupCategory(
			@NonNull final GroupCategoryId groupCategoryId,
			@NonNull final OrgId targetOrgId)
	{

		final ProductId targetProductId = queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, targetOrgId)
				.addEqualsFilter(I_M_Product.COLUMNNAME_C_CompensationGroup_Schema_Category_ID, groupCategoryId)
				.orderByDescending(I_M_Product.COLUMNNAME_M_Product_ID)
				.create()
				.firstId(ProductId::ofRepoIdOrNull);

		if (targetProductId == null)
		{
			return Optional.empty();
		}

		return Optional.of(getById(targetProductId, de.metas.product.model.I_M_Product.class));
	}

	@Override
	public ImmutableList<String> retrieveSupplierApprovalNorms(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_Product_SupplierApproval_Norm.class)
				.addEqualsFilter(I_M_Product_SupplierApproval_Norm.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream()
				.map(I_M_Product_SupplierApproval_Norm::getSupplierApproval_Norm)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public ProductCategoryId retrieveProductCategoryForGroupTemplateId(@NonNull final GroupTemplateId groupTemplateId)
	{
		return queryBL.createQueryBuilder(I_M_Product_Category.class)
				.addEqualsFilter(I_M_Product_Category.COLUMNNAME_C_CompensationGroup_Schema_ID, groupTemplateId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_Product_Category.COLUMNNAME_M_Product_Category_ID)
				.create()
				.firstId(ProductCategoryId::ofRepoIdOrNull);
	}

	@Override
	public boolean isProductUsed(@NonNull final ProductId productId)
	{
		return queryBL
				.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch()
				||
				queryBL
						.createQueryBuilder(I_C_InvoiceLine.class)
						.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_M_Product_ID, productId.getRepoId())
						.addOnlyActiveRecordsFilter()
						.create()
						.anyMatch()
				||
				queryBL
						.createQueryBuilder(I_M_InOutLine.class)
						.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_Product_ID, productId.getRepoId())
						.addOnlyActiveRecordsFilter()
						.create()
						.anyMatch();
	}

	@Override
	public void save(I_M_Product record)
	{
		InterfaceWrapperHelper.save(record);
	}

}
