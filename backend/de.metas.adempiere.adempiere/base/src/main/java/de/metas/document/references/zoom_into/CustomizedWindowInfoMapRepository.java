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

import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;

public interface CustomizedWindowInfoMapRepository
{
	CustomizedWindowInfoMap get();

	void assertNoCycles(AdWindowId adWindowId);

	default AdWindowId getEffectiveWindowId(@NonNull final AdWindowId adWindowId)
	{
		return get()
				.getCustomizedWindowInfo(adWindowId)
				.map(CustomizedWindowInfo::getCustomizationWindowId)
				.orElse(adWindowId);
	}
}
