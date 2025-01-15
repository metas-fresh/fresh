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

package de.metas.document.references.related_documents.fact_acct;

import de.metas.document.references.related_documents.RelatedDocumentsCountSupplier;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.util.Services;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_Fact_Acct;

@ToString(exclude = "queryBL")
class FactAcctRelatedDocumentsCountSupplier implements RelatedDocumentsCountSupplier
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final int adTableId;
	private final int recordId;

	FactAcctRelatedDocumentsCountSupplier(final int adTableId, final int recordId)
	{
		this.adTableId = adTableId;
		this.recordId = recordId;
	}

	@Override
	public int getRecordsCount(final RelatedDocumentsPermissions permissions)
	{
		return queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, recordId)
				.create()
				.count();
	}
}
