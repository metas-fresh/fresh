package de.metas.document;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.exceptions.DocTypeNotFoundException;
import org.compiere.model.I_C_DocType;

import de.metas.document.engine.IDocumentBL;
=======
import com.google.common.collect.ImmutableSet;
import de.metas.acct.GLCategoryId;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
<<<<<<< HEAD

public interface IDocTypeDAO extends ISingletonService
{
	I_C_DocType getById(int docTypeId);

	I_C_DocType getById(DocTypeId docTypeId);

=======
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.compiere.model.I_C_DocType;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public interface IDocTypeDAO extends ISingletonService
{
	@NonNull
	I_C_DocType getById(int docTypeId);

	@NonNull
	I_C_DocType getById(DocTypeId docTypeId);

	@NonNull
	I_C_DocType getByIdInTrx(@NonNull DocTypeId docTypeId);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	/**
	 * @return C_DocType_ID or <code>null</code> if not found
	 */
	DocTypeId getDocTypeIdOrNull(final DocTypeQuery query);

<<<<<<< HEAD
=======
	@NonNull
	ImmutableSet<DocTypeId> getDocTypeIdsByInvoicingPoolId(@NonNull DocTypeInvoicingPoolId docTypeInvoicingPoolId);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	DocTypeId getDocTypeId(DocTypeQuery query) throws DocTypeNotFoundException;

	Optional<I_C_DocType> retrieveDocType(DocTypeQuery docTypeQuery);

	/**
	 * Returns {@code true} if the given {@code documentModel}'s {@link IDocumentBL#COLUMNNAME_C_DocType_ID} value
	 * is one of the ID that are matched by the given {@code docTypeQuery}.
	 */
	boolean queryMatchesDocTypeId(DocTypeQuery docTypeQuery, int docTypeId);

	/**
	 * Retrieve all the doc types of a certain base type as a list
	 *
<<<<<<< HEAD
	 * @param query
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return a list of docTypes never <code>null</code>. Those with <code>IsDefault</code> and with <code>AD_Org_ID > 0</code> will be first in the list.
	 */
	List<I_C_DocType> retrieveDocTypesByBaseType(DocTypeQuery query);

	/**
	 * Retrieve the Counter_DocBaseType that fits the given DocBaseType.
	 */
<<<<<<< HEAD
	Optional<String> getDocBaseTypeCounter(String docBaseType);

	DocTypeId createDocType(DocTypeCreateRequest request);

	@Value
	@Builder
	public static final class DocTypeCreateRequest
	{
		@NonNull
		final Properties ctx;
		@Default
		final int adOrgId = -1;
		final String entityType;
		@NonNull
		final String name;
		final String printName;
		@NonNull
		final String docBaseType;
		final String docSubType;
		final Boolean isSOTrx;
		final int docTypeShipmentId;
		final int docTypeInvoiceId;
		final int glCategoryId;

		final int docNoSequenceId;
		final int newDocNoSequenceStartNo;

		final int documentCopies;
	}

=======
	Optional<DocBaseType> getDocBaseTypeCounter(DocBaseType docBaseType);

	DocTypeId createDocType(DocTypeCreateRequest request);
	
	void save(@NonNull I_C_DocType dt);

	@Value
	@Builder
	class DocTypeCreateRequest
	{
		@NonNull Properties ctx;
		@Default int adOrgId = -1;
		String entityType;
		@NonNull String name;
		String printName;
		@NonNull DocBaseType docBaseType;
		@NonNull @Default DocSubType docSubType = DocSubType.NONE;
		Boolean isSOTrx;
		int docTypeShipmentId;
		int docTypeInvoiceId;
		@NonNull GLCategoryId glCategoryId;

		int docNoSequenceId;
		int newDocNoSequenceStartNo;

		int documentCopies;
	}

	DocBaseType getDocBaseTypeById(@NonNull DocTypeId docTypeId);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	DocBaseAndSubType getDocBaseAndSubTypeById(DocTypeId docTypeId);
}
