package de.metas.ui.web.window.model.lookup;

import de.metas.cache.CCacheStats;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * metasfresh-webui-api
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

public interface LookupDataSource extends LookupValueByIdSupplier
{
	int FIRST_ROW = 0;
	int DEFAULT_PageLength = 10;

	LookupValuesPage findEntities(Evaluatee ctx, int pageLength);

	LookupValuesPage findEntities(Evaluatee ctx, String filter, int firstRow, int pageLength);

	default LookupValuesPage findEntities(final Evaluatee ctx, final String filter)
	{
		return findEntities(ctx, filter, FIRST_ROW, DEFAULT_PageLength);
	}

	/**
	 * @return all lookup values
	 */
	default LookupValuesPage findEntities(final Evaluatee ctx)
	{
		return findEntities(ctx, Integer.MAX_VALUE);
	}

	@Override
	@Nullable
	LookupValue findById(Object id);

	/**
	 * @return lookup values in the same order as the collection order
	 */
	@NonNull
	LookupValuesList findByIdsOrdered(@NonNull final Collection<?> ids);

	List<CCacheStats> getCacheStats();

	DocumentZoomIntoInfo getDocumentZoomInto(final int id);

	/**
	 * @return optional WindowId to be used when zooming into
	 */
	Optional<WindowId> getZoomIntoWindowId();

	void cacheInvalidate();
}
