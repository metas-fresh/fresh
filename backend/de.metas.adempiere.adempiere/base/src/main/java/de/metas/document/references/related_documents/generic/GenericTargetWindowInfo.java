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

import de.metas.i18n.ImmutableTranslatableString;
import de.metas.lang.SOTrx;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;

@Value
class GenericTargetWindowInfo
{
	@NonNull ImmutableTranslatableString name;
	@NonNull AdWindowId targetWindowId;

	@NonNull String targetWindowInternalName;
	@NonNull String targetTableName;
	@Nullable @With SOTrx soTrx;
	boolean targetHasIsSOTrxColumn;
	@Nullable @With String tabSqlWhereClause;

	@Builder
	private GenericTargetWindowInfo(
			@NonNull final ImmutableTranslatableString name,
			@NonNull final AdWindowId targetWindowId,
			@NonNull final String targetWindowInternalName,
			@NonNull final String targetTableName,
			@Nullable final SOTrx soTrx,
			final boolean targetHasIsSOTrxColumn,
			@Nullable final String tabSqlWhereClause)
	{
		Check.assumeNotEmpty(targetTableName, "targetTableName is not empty");

		this.name = name;
		this.targetWindowId = targetWindowId;
		this.targetWindowInternalName = targetWindowInternalName;
		this.targetTableName = targetTableName;
		this.soTrx = soTrx;
		this.targetHasIsSOTrxColumn = targetHasIsSOTrxColumn;
		this.tabSqlWhereClause = tabSqlWhereClause;
	}
}
