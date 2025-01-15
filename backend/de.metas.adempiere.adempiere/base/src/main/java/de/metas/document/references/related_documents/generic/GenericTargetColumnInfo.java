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

import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;

@Value
class GenericTargetColumnInfo
{
	@Nullable ITranslatableString caption;
	@Nullable String columnName;
	@With boolean isDynamic;
	@Nullable String virtualColumnSql;

	@Builder
	private GenericTargetColumnInfo(
			@Nullable final ITranslatableString caption,
			@Nullable final String columnName,
			final boolean isDynamic,
			@Nullable final String virtualColumnSql)
	{
		if (!isDynamic && Check.isBlank(columnName))
		{
			throw new IllegalArgumentException("columnName must be set when it's not dynamic");
		}

		this.caption = caption;
		this.columnName = StringUtils.trimBlankToNull(columnName);
		this.isDynamic = isDynamic;
		this.virtualColumnSql = StringUtils.trimBlankToNull(virtualColumnSql);
	}

	public boolean isVirtualTargetColumnName()
	{
		return getVirtualColumnSql() != null;
	}
}
