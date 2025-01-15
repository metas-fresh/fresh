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

package org.adempiere.ad.table.api;

import lombok.NonNull;
import lombok.Value;

import java.util.Objects;

@Value(staticConstructor = "ofTableAndColumn")
public class TableAndColumnName
{
	@NonNull TableName tableName;
	@NonNull ColumnName columnName;

	public static TableAndColumnName ofTableAndColumnStrings(@NonNull final String tableName, @NonNull final String columnName)
	{
		return ofTableAndColumn(TableName.ofString(tableName), ColumnName.ofString(columnName));
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return tableName.getAsString() + "." + columnName.getAsString();
	}

	public String getTableNameAsString()
	{
		return tableName.getAsString();
	}

	public String getColumnNameAsString()
	{
		return columnName.getAsString();
	}

	public boolean equalsByColumnName(final String otherColumnName)
	{
		return Objects.equals(columnName.getAsString(), otherColumnName);
	}
}
