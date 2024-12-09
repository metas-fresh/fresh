package de.metas.costing;

<<<<<<< HEAD
import org.compiere.model.I_M_Product;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
<<<<<<< HEAD
=======
import org.compiere.model.I_M_Product;

import java.util.Map;
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.business
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

public interface IProductCostingBL extends ISingletonService
{
	@NonNull
	CostingLevel getCostingLevel(ProductId productId, AcctSchemaId acctSchemaId);

	@NonNull
	CostingLevel getCostingLevel(ProductId productId, AcctSchema acctSchema);

	@NonNull
	CostingLevel getCostingLevel(I_M_Product product, AcctSchema acctSchema);

<<<<<<< HEAD
	/**
	 * Get Product Costing Method
	 *
	 * @param C_AcctSchema_ID accounting schema ID
	 * @return product costing method
	 */
=======
	@NonNull Map<ProductId, CostingLevel> getCostingLevels(Set<ProductId> productIds, AcctSchema acctSchema);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@NonNull
	CostingMethod getCostingMethod(I_M_Product product, AcctSchema acctSchema);

	@NonNull
	CostingMethod getCostingMethod(ProductId productId, AcctSchema acctSchema);

	@NonNull
	CostingMethod getCostingMethod(ProductId productId, AcctSchemaId acctSchemaId);
}
