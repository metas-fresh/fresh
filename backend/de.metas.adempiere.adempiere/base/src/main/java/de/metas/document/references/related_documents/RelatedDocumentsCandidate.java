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

package de.metas.document.references.related_documents;

import com.google.common.base.MoreObjects;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.lang.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.slf4j.Logger;

import javax.annotation.Nullable;

@Getter //(AccessLevel.PACKAGE) visible for cucumber tests
public final class RelatedDocumentsCandidate
{
	private static final Logger logger = LogManager.getLogger(RelatedDocumentsCandidate.class);

	private final RelatedDocumentsId id;
	private final String internalName;
	private final ITranslatableString windowCaption;
	private final ITranslatableString filterByFieldCaption;
	private final RelatedDocumentsTargetWindow targetWindow;
	private final Priority priority;

	private final RelatedDocumentsQuerySupplier querySupplier;
	private final RelatedDocumentsCountSupplier documentsCountSupplier;

	@Builder
	private RelatedDocumentsCandidate(
			@NonNull final RelatedDocumentsId id,
			@NonNull final String internalName,
			@NonNull final RelatedDocumentsTargetWindow targetWindow,
			@NonNull final Priority priority,
			@NonNull final ITranslatableString windowCaption,
			@Nullable final ITranslatableString filterByFieldCaption,
			@NonNull final RelatedDocumentsQuerySupplier querySupplier,
			@NonNull final RelatedDocumentsCountSupplier documentsCountSupplier)
	{
		Check.assumeNotEmpty(internalName, "internalName is not empty");

		this.id = id;
		this.internalName = internalName;
		this.targetWindow = targetWindow;
		this.priority = priority;

		this.windowCaption = windowCaption;
		this.filterByFieldCaption = filterByFieldCaption;

		this.querySupplier = querySupplier;
		this.documentsCountSupplier = documentsCountSupplier;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("internalName", internalName)
				.add("windowCaption", windowCaption.getDefaultValue())
				.add("additionalCaption", filterByFieldCaption != null ? filterByFieldCaption.getDefaultValue() : null)
				.add("adWindowId", targetWindow)
				.toString();
	}

	public AdWindowId getTargetWindowId()
	{
		return targetWindow.getAdWindowId();
	}

	public boolean isMatching(@NonNull final RelatedDocumentsId id)
	{
		return RelatedDocumentsId.equals(this.id, id);
	}
}
