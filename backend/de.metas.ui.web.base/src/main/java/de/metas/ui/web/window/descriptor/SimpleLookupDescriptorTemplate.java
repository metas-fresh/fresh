package de.metas.ui.web.window.descriptor;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

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

/**
 * Simple template implementation of {@link LookupDescriptor} and {@link LookupDataSourceFetcher}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public abstract class SimpleLookupDescriptorTemplate implements LookupDescriptor, LookupDataSourceFetcher
{
	@Override
	public final LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public final boolean isHighVolume()
	{
		// NOTE: method will never be called because isCached() == true
		return false;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.list;
	}

	@Override
	public boolean hasParameters()
	{
		return !getDependsOnFieldNames().isEmpty();
	}

	@Override
	public abstract boolean isNumericKey();

	@Override
	public abstract Set<String> getDependsOnFieldNames();

	//
	//
	//
	// -----------------------
	//
	//

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builderWithoutTableName();
	}

	@Override
	@Nullable
	public abstract LookupValue retrieveLookupValueById(@NonNull LookupDataSourceContext evalCtx);

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builderWithoutTableName();
	}

	@Override
	public abstract LookupValuesPage retrieveEntities(LookupDataSourceContext evalCtx);

	@Override
	@Nullable
	public final String getCachePrefix()
	{
		// NOTE: method will never be called because isCached() == true
		return null;
	}

	@Override
	public final boolean isCached()
	{
		return true;
	}

	@Override
	public void cacheInvalidate()
	{
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

}
