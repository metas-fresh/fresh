/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.references.related_documents.generic;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Delegate;
import org.adempiere.exceptions.AdempiereException;

import java.util.Objects;
import java.util.Optional;

@Value
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
class GenericRelatedDocumentDescriptor
{
	@NonNull @Delegate GenericTargetWindowInfo genericTargetWindowInfo;

	@NonNull @Getter(AccessLevel.NONE) ImmutableMap<String, GenericTargetColumnInfo> targetColumnsByColumnName;
	@NonNull ImmutableList<GenericTargetColumnInfo> dynamicColumns;
	@NonNull ImmutableList<GenericTargetColumnInfo> nonDynamicColumns;
	@NonNull Optional<GenericTargetColumnInfo> singleNonDynamicColumn;

	@Builder
	private GenericRelatedDocumentDescriptor(
			@NonNull final GenericTargetWindowInfo targetWindow,
			@NonNull @Singular final ImmutableList<GenericTargetColumnInfo> targetColumns)
	{
		Check.assumeNotEmpty(targetColumns, "targetColumns is not empty");

		this.genericTargetWindowInfo = targetWindow;
		this.targetColumnsByColumnName = Maps.uniqueIndex(targetColumns, GenericTargetColumnInfo::getColumnName);
		this.dynamicColumns = targetColumns.stream()
				.filter(GenericTargetColumnInfo::isDynamic)
				.collect(ImmutableList.toImmutableList());
		this.nonDynamicColumns = targetColumns.stream()
				.filter(column -> !column.isDynamic())
				.collect(ImmutableList.toImmutableList());
		this.singleNonDynamicColumn = CollectionUtils.singleElementOrEmpty(nonDynamicColumns);
	}

	public ImmutableCollection<GenericTargetColumnInfo> getTargetColumns() {return targetColumnsByColumnName.values();}

	@NonNull
	public GenericTargetColumnInfo getColumnByName(final String columnName)
	{
		final GenericTargetColumnInfo column = targetColumnsByColumnName.get(columnName);
		if (column == null)
		{
			throw new AdempiereException("No column found for `" + columnName + "`. Available columns are: " + targetColumnsByColumnName.keySet());
		}
		return column;
	}

	public boolean isSingleNonDynamicColumn(@NonNull final GenericTargetColumnInfo column)
	{
		final GenericTargetColumnInfo singleNonDynamicColumn = this.singleNonDynamicColumn.orElse(null);
		return singleNonDynamicColumn != null && Objects.equals(singleNonDynamicColumn, column);
	}

	public boolean hasSingleNonDynamicColumn() {return singleNonDynamicColumn.isPresent();}
}
