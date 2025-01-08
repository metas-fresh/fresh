package org.adempiere.ad.element.api;

import java.util.List;

import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_UI_Element;

import de.metas.util.ISingletonService;

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

public interface IADElementDAO extends ISingletonService
{
	List<I_AD_UI_Element> retrieveChildUIElements(I_AD_Element element);

	List<I_AD_Column> retrieveColumns(int elementId);

	void makeElementMandatoryInApplicationDictionaryTables();

	I_AD_Element getById(int elementId);

	AdElementId createNewElement(CreateADElementRequest request);

	AdElementId getADElementIdByColumnNameOrNull(String columnName);
}
