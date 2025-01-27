package de.metas.product;

import com.google.common.collect.ImmutableList;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.Check.assume;
import static de.metas.util.Check.isEmpty;

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

public interface IProductDAO extends ISingletonService
{
	<T extends I_M_Product> T getById(ProductId productId, Class<T> productClass);

	I_M_Product getById(ProductId productId);

	<T extends I_M_Product> T getByIdInTrx(@NonNull ProductId productId, @NonNull Class<T> productClass);

	I_M_Product getByIdInTrx(@NonNull ProductId productId);

	I_M_Product getById(final int productId);

	List<I_M_Product> getByIds(final Set<ProductId> productIds);

	/**
	 * @return default product category
	 */
	@NonNull
	ProductCategoryId getDefaultProductCategoryId();

	/**
	 * @return All the active products with the given product planning schema selector
	 */
	Set<ImmutablePair<ProductId, OrgId>> retrieveProductsAndOrgsForSchemaSelector(@NonNull ProductPlanningSchemaSelector productPlanningSchemaSelector);

	/**
	 * @return the product of the given <code>org</code> that is mapped to the given <code>product</code> or <code>null</code> if the given product references no mapping, or the mapping is not active
	 * or if there is no pendant in the given <code>org</code>.
	 * task http://dewiki908/mediawiki/index.php/09700_Counter_Documents_%28100691234288%29
	 */
	@Nullable
	ProductId retrieveMappedProductIdOrNull(ProductId productId, OrgId orgId);

	/**
	 * Retrieve all the products from all the organizations that have the same mapping as the given product
	 *
	 * @return list of the products if found, empty list otherwise
	 */
	List<de.metas.product.model.I_M_Product> retrieveAllMappedProducts(I_M_Product product);

	@Nullable
	I_M_Product retrieveProductByValue(String value);

	/**
	 * @deprecated assumes that different AD_Orgs always have different {@code M_Product.Value}s. Better use {@link #retrieveProductIdBy(ProductQuery)}.
	 */
	@Deprecated
	@Nullable
	ProductId retrieveProductIdByValue(String value);

	@Nullable
	ProductId retrieveProductIdBy(ProductQuery query);

	Optional<ProductCategoryId> retrieveProductCategoryIdByCategoryValue(@NonNull String categoryValue);

	Optional<ProductId> getProductIdByBarcode(@NonNull String barcode, @NonNull ClientId clientId);
	
	void clearIndividualMasterDataFromProduct(ProductId productId);

	Optional<GroupTemplateId> getGroupTemplateIdByProductId(@NonNull ProductId productId);

	Optional<de.metas.product.model.I_M_Product> getProductOfGroupCategory(
			@NonNull GroupCategoryId groupCategoryId,
			@NonNull OrgId targetOrgId);

	ProductCategoryId retrieveProductCategoryForGroupTemplateId(@NonNull GroupTemplateId groupTemplateId);

	void save(I_M_Product record);

	@Value
	class ProductQuery
	{
		/**
		 * Applied if not empty. {@code AND}ed with {@code externalId} if given. At least one of {@code value} or {@code externalId} needs to be given.
		 */
		String value;

		/**
		 * Applied if not {@code null}. {@code AND}ed with {@code value} if given. At least one of {@code value} or {@code externalId} needs to be given.
		 */
		ExternalId externalId;

		OrgId orgId;

		boolean includeAnyOrg;
		boolean outOfTrx;

		@Builder
		private ProductQuery(
				@Nullable final String value,
				@Nullable final ExternalId externalId,
				@NonNull final OrgId orgId,
				@Nullable final Boolean includeAnyOrg,
				@Nullable final Boolean outOfTrx)
		{
			final boolean valueIsSet = !isEmpty(value, true);
			final boolean externalIdIsSet = externalId != null;
			assume(valueIsSet || externalIdIsSet, "At least one of value or externalId need to be specified");

			this.value = value;
			this.externalId = externalId;
			this.orgId = orgId;
			this.includeAnyOrg = coalesce(includeAnyOrg, false);
			this.outOfTrx = coalesce(outOfTrx, false);
		}
	}

	Stream<I_M_Product> streamAllProducts(@Nullable Instant since);

	/**
	 * @return product category or null if the productId is null
	 */
	@Nullable
	ProductCategoryId retrieveProductCategoryByProductId(@Nullable ProductId productId);

	@Nullable
	ProductAndCategoryId retrieveProductAndCategoryIdByProductId(ProductId productId);

	ProductAndCategoryAndManufacturerId retrieveProductAndCategoryAndManufacturerByProductId(ProductId productId);

	Set<ProductAndCategoryAndManufacturerId> retrieveProductAndCategoryAndManufacturersByProductIds(Set<ProductId> productIds);

	String retrieveProductValueByProductId(ProductId productId);

	I_M_Product_Category getProductCategoryById(ProductCategoryId id);

	<T extends I_M_Product_Category> T getProductCategoryById(ProductCategoryId id, Class<T> modelClass);

	Stream<I_M_Product_Category> streamAllProductCategories();

	String getProductCategoryNameById(ProductCategoryId id);

	ProductId getProductIdByResourceId(ResourceId resourceId);

	void updateProductsByResourceIds(Set<ResourceId> resourceIds, Consumer<I_M_Product> productUpdater);

	void updateProductsByResourceIds(Set<ResourceId> resourceIds, BiConsumer<ResourceId, I_M_Product> productUpdater);

	void deleteProductByResourceId(ResourceId resourceId);

	I_M_Product createProduct(CreateProductRequest request);

	void updateProduct(UpdateProductRequest request);

	int getProductGuaranteeDaysMinFallbackProductCategory(@NonNull final ProductId productId);

	int getGuaranteeMonthsInDays(ProductId productId);

	ImmutableList<String> retrieveSupplierApprovalNorms(ProductId productId);

	/**
	 *
	 * @param productId
	 * @return true if product is used in orders, invoices, shipments,..
	 */
	boolean isProductUsed(ProductId productId);
}
