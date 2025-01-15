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

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
public class RelatedDocumentsFactory
{
	private static final Logger logger = LogManager.getLogger(RelatedDocumentsFactory.class);

	private final ImmutableList<IRelatedDocumentsProvider> providers;

	public RelatedDocumentsFactory(@NonNull final List<IRelatedDocumentsProvider> providers)
	{
		this.providers = ImmutableList.copyOf(providers);

		logger.info("Providers: {}", this.providers);
	}

	public List<RelatedDocuments> retrieveRelatedDocuments(
			@NonNull final IZoomSource fromDocument,
			@NonNull final RelatedDocumentsPermissions permissions)
	{
		final RelatedDocumentsEvaluationContext context = RelatedDocumentsEvaluationContext.builder()
				.permissions(permissions)
				.build();

		return getRelatedDocumentsCandidates(fromDocument, null, permissions)
				.stream()
				.sequential()
				.flatMap(candidate -> candidate.evaluateAndStream(context))
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<RelatedDocumentsCandidateGroup> getRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@NonNull final RelatedDocumentsPermissions permissions)
	{
		return getRelatedDocumentsCandidates(fromDocument, null, permissions);
	}

	private ImmutableList<RelatedDocumentsCandidateGroup> getRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId onlyTargetWindowId,
			@NonNull final RelatedDocumentsPermissions permissions)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final ImmutableList.Builder<RelatedDocumentsCandidateGroup> result = ImmutableList.builder();
		for (final IRelatedDocumentsProvider provider : providers)
		{
			try
			{
				for (final RelatedDocumentsCandidateGroup candidatesGroup : provider.retrieveRelatedDocumentsCandidates(fromDocument, onlyTargetWindowId))
				{
					// If not our target window ID, skip it
					// This shall not happen because we asked the provider to return only those for our target window,
					// but if is happening (because of a bug of provider) we shall not be so fragile.
					final AdWindowId targetWindowId = candidatesGroup.getTargetWindowId();
					if (onlyTargetWindowId != null && !AdWindowId.equals(onlyTargetWindowId, targetWindowId))
					{
						//noinspection ThrowableNotThrown
						new AdempiereException("Got candidates which is not for our target window. Skipping it."
													   + "\n candidatesGroup: " + candidatesGroup
													   + "\n provider: " + provider
													   + "\n targetAD_Window_ID: " + onlyTargetWindowId
													   + "\n source: " + fromDocument)
								.throwIfDeveloperModeOrLogWarningElse(logger);
						continue;
					}

					//
					// Filter out those windows on given user does not have permissions
					if (!permissions.hasReadAccess(targetWindowId))
					{
						continue;
					}

					//
					// Collect eligible candidates
					result.add(candidatesGroup);
				}
			}
			catch (final Exception ex)
			{
				logger.warn("Failed retrieving candidates from {} for {}. Skipped.", provider, fromDocument, ex);
			}
		}

		stopwatch.stop();
		logger.debug("Fetched candidates for source={} in {}", fromDocument, stopwatch);

		return result.build();
	}

	/**
	 * Retrieves that {@link RelatedDocuments} which is referencing our given <code>source</code> and has given target window.
	 * <p>
	 * NOTE: Records count is not checked
	 */
	public Optional<RelatedDocuments> retrieveRelatedDocuments(
			@NonNull final IZoomSource fromDocument,
			@NonNull final AdWindowId targetWindowId,
			@Nullable final RelatedDocumentsId relatedDocumentsId,
			@NonNull final RelatedDocumentsPermissions permissions)
	{
		// NOTE: we need to check the records count because in case there are multiple RelatedDocuments for the same targetWindowId,
		// we shall pick the one which actually has some data. Usually there would be only one (see #1808)

		final RelatedDocumentsEvaluationContext context = RelatedDocumentsEvaluationContext.builder()
				.permissions(permissions)
				.onlyRelatedDocumentsId(relatedDocumentsId)
				.build();

		return getRelatedDocumentsCandidates(fromDocument, targetWindowId, permissions)
				.stream()
				.filter(candidatesGroup -> relatedDocumentsId == null || candidatesGroup.isMatching(relatedDocumentsId))
				.flatMap(candidatesGroup -> candidatesGroup.evaluateAndStream(context))
				.findFirst();
	}
}
