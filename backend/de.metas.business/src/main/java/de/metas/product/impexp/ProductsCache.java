package de.metas.product.impexp;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

import java.util.HashMap;
import java.util.Map;

/*
 * #%L
 * de.metas.business
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

final class ProductsCache
{
	private final IProductDAO productsRepo;

	private final Map<ProductId, Product> productsCache = new HashMap<>();

	@Builder
	private ProductsCache(@NonNull final IProductDAO productsRepo)
	{
		this.productsRepo = productsRepo;
	}

	public Product getProductById(final ProductId productId)
	{
		return productsCache.computeIfAbsent(productId, this::retrieveProductById);
	}

	private Product retrieveProductById(final ProductId productId)
	{
		final I_M_Product productRecord = productsRepo.getByIdInTrx(productId);
		return new ProductsCache.Product(productRecord);
	}

	public Product newProduct(@NonNull final I_M_Product productRecord)
	{
		return new ProductsCache.Product(productRecord);
	}

	public void clear()
	{
		productsCache.clear();
	}

	final class Product
	{
		@Getter
		private final I_M_Product record;

		private Product(@NonNull final I_M_Product record)
		{
			this.record = record;
		}

		public ProductId getIdOrNull()
		{
			return ProductId.ofRepoIdOrNull(record.getM_Product_ID());
		}

		public int getOrgId()
		{
			return record.getAD_Org_ID();
		}

		public void save()
		{
			productsRepo.save(record);
			productsCache.put(getIdOrNull(), this);
		}
	}
}
