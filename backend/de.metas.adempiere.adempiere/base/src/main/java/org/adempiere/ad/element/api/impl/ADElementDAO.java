package org.adempiere.ad.element.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.CreateADElementRequest;
import org.adempiere.ad.element.api.IADElementDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MColumn;

<<<<<<< HEAD
import de.metas.util.Services;
import lombok.NonNull;
=======
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ADElementDAO implements IADElementDAO
{

	@Override
	public List<I_AD_UI_Element> retrieveChildUIElements(final I_AD_Element element)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Element.class)
				.addEqualsFilter(I_AD_Element.COLUMN_AD_Element_ID, element.getAD_Element_ID())
				.andCollectChildren(I_AD_Column.COLUMN_AD_Element_ID)
				.andCollectChildren(I_AD_Field.COLUMN_AD_Column_ID)
				.andCollectChildren(I_AD_UI_Element.COLUMN_AD_Field_ID)
				.create()
				.list();

	}

	@Override
	public List<I_AD_Column> retrieveColumns(final int elementId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMN_AD_Element_ID, elementId)
				.orderBy().addColumn(I_AD_Column.COLUMNNAME_ColumnName).endOrderBy()
				.create()
				.list(I_AD_Column.class);
	}

	@Override
	public AdElementId getADElementIdByColumnNameOrNull(@NonNull final String columnName)
	{
		return queryADElementByColumnName(columnName)
				.create()
				.firstIdOnly(AdElementId::ofRepoIdOrNull);
	}

	private IQueryBuilder<I_AD_Element> queryADElementByColumnName(@NonNull final String columnName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Element.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Element.COLUMNNAME_ColumnName, columnName, UpperCaseQueryFilterModifier.instance);
	}

	@Override
	public void makeElementMandatoryInApplicationDictionaryTables()
	{
		make_AD_Element_Mandatory_In_AD_Tab();
		make_AD_Element_Mandatory_In_AD_Window();
		make_AD_Element_Mandatory_In_AD_Menu();
	}

	private void make_AD_Element_Mandatory_In_AD_Menu()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Menu.Table_Name, I_AD_Menu.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory(elementIdColumn);
	}

	private void make_AD_Element_Mandatory_In_AD_Window()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Window.Table_Name, I_AD_Window.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory(elementIdColumn);
	}

	private void make_AD_Element_Mandatory_In_AD_Tab()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Tab.Table_Name, I_AD_Tab.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory(elementIdColumn);
	}

	private void makeElementColumnMandatory(final I_AD_Column elementIdColumn)
	{
		elementIdColumn.setIsMandatory(true);
		save(elementIdColumn);

		final MColumn columnPO = LegacyAdapters.convertToPO(elementIdColumn);
		columnPO.syncDatabase();
	}

	@Override
	public I_AD_Element getById(final int elementId)
	{
		return loadOutOfTrx(elementId, I_AD_Element.class);
	}

	@Override
	public AdElementId createNewElement(@NonNull final CreateADElementRequest request)
	{
		final I_AD_Element record = newInstance(I_AD_Element.class);
		record.setName(request.getName());
		record.setPrintName(request.getPrintName());
		record.setDescription(request.getDescription());
		record.setHelp(request.getHelp());
		record.setCommitWarning(request.getTabCommitWarning());
		saveRecord(record);

		return AdElementId.ofRepoId(record.getAD_Element_ID());
	}

}
