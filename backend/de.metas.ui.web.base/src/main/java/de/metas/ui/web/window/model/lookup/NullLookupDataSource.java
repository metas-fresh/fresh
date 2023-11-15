package de.metas.ui.web.window.model.lookup;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCacheStats;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

public final class NullLookupDataSource implements LookupDataSource
{
	public static final transient NullLookupDataSource instance = new NullLookupDataSource();

	private NullLookupDataSource() { }

	@Override
	public DocumentZoomIntoInfo getDocumentZoomInto(final int id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesPage findEntities(final Evaluatee ctx, final String filter, final int firstRow, final int pageLength)
	{
		return LookupValuesPage.EMPTY;
	}

	@Override
	public LookupValuesPage findEntities(final Evaluatee ctx, final int pageLength)
	{
		return LookupValuesPage.EMPTY;
	}

	@Override
	public LookupValue findById(final Object id)
	{
		return toUnknownLookupValue(id);
	}

	@Nullable
	private static LookupValue toUnknownLookupValue(final Object id)
	{
		if (id == null)
		{
			return null;
		}

		if (id instanceof Integer)
		{
			return IntegerLookupValue.unknown((int)id);
		}
		else
		{
			return StringLookupValue.unknown(id.toString());
		}
	}

	@Override
	public @NonNull LookupValuesList findByIdsOrdered(final @NonNull Collection<?> ids)
	{
		if (ids.isEmpty())
		{
			return LookupValuesList.EMPTY;
		}

		return ids.stream()
				.map(NullLookupDataSource::toUnknownLookupValue)
				.filter(Objects::nonNull)
				.collect(LookupValuesList.collect());
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of();
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
