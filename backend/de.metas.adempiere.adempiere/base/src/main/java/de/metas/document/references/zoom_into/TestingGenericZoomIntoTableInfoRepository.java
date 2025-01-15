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

package de.metas.document.references.zoom_into;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Table;

public class TestingGenericZoomIntoTableInfoRepository implements GenericZoomIntoTableInfoRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public GenericZoomIntoTableInfo retrieveTableInfo(
			@NonNull final String tableName,
			final boolean ignoreExcludeFromZoomTargetsFlag)
	{
		if (!Adempiere.isUnitTestMode())
		{
			throw new AdempiereException("Repository " + this + " shall be used only for JUnit testing!!!");
		}

		return GenericZoomIntoTableInfo.builder()
				.tableName(tableName)
				.keyColumnNames(ImmutableSet.of(InterfaceWrapperHelper.getKeyColumnName(tableName)))
				.windows(genericZoomIntoTableWindows(tableName))
				.build();
	}

	@NonNull
	private ImmutableList<GenericZoomIntoTableWindow> genericZoomIntoTableWindows(@NonNull final String tableName)
	{
		final AdWindowId windowId = queryBL.createQueryBuilder(I_AD_Table.class)
				.addEqualsFilter(I_AD_Table.COLUMNNAME_TableName, tableName)
				.create()
				.firstOnlyOptional(I_AD_Table.class)
				.map(I_AD_Table::getAD_Window_ID)
				.map(AdWindowId::ofRepoIdOrNull)
				.orElse(null);

		if (windowId == null)
		{
			return ImmutableList.of();
		}

		return ImmutableList.of(
				GenericZoomIntoTableWindow.builder()
						.adWindowId(windowId)
						.build()
		);
	}
}
