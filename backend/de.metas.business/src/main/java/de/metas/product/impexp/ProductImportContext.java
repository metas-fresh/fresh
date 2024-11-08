package de.metas.product.impexp;

import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.compiere.model.I_I_Product;
import de.metas.product.impexp.ProductsCache.Product;

import java.util.Objects;

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

@ToString
final class ProductImportContext
{
	@Getter
	private final ProductsCache productsCache;
	@Getter
	private final boolean insertOnly;

	@Getter
	@Setter
	private I_I_Product currentImportRecord;

	@Builder
	private ProductImportContext(
			@NonNull final ProductsCache productsCache,
			final boolean insertOnly)
	{
		this.productsCache = productsCache;
		this.insertOnly = insertOnly;
	}

	public boolean isSameProduct(final I_I_Product importRecord)
	{
		final I_I_Product currentImportRecord = getCurrentImportRecord();

		return currentImportRecord != null
				&& Objects.equals(importRecord.getValue(), currentImportRecord.getValue());
	}

	public boolean isCurrentProductIdSet()
	{
		return getCurrentProductIdOrNull() != null;
	}

	public Product getCurrentProduct()
	{
		final ProductId productId = getCurrentProductIdOrNull();
		return getProductsCache().getProductById(productId);
	}

	public ProductId getCurrentProductIdOrNull()
	{
		final I_I_Product currentImportRecord = getCurrentImportRecord();
		return currentImportRecord != null
				? ProductId.ofRepoIdOrNull(currentImportRecord.getM_Product_ID())
				: null;
	}

	public void setCurrentProductId(@NonNull final ProductId productId)
	{
		final I_I_Product currentImportRecord = getCurrentImportRecord();
		Check.assumeNotNull(currentImportRecord, "Parameter currentImportRecord is not null");

		currentImportRecord.setM_Product_ID(productId.getRepoId());
	}
}
