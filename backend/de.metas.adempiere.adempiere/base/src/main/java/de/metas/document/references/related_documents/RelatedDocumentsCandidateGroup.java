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
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Priority;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import lombok.experimental.Delegate;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Value
public class RelatedDocumentsCandidateGroup
{
	private static final Logger logger = LogManager.getLogger(RelatedDocumentsCandidateGroup.class);

	@NonNull ImmutableList<RelatedDocumentsCandidate> candidates;
	@NonNull AdWindowId targetWindowId;

	@Builder
	private RelatedDocumentsCandidateGroup(
			@NonNull @Singular final ImmutableList<RelatedDocumentsCandidate> candidates)
	{
		Check.assumeNotEmpty(candidates, "candidates");
		this.candidates = candidates;

		targetWindowId = CollectionUtils.extractSingleElement(candidates, RelatedDocumentsCandidate::getTargetWindowId);
	}

	public static RelatedDocumentsCandidateGroup of(@NonNull RelatedDocumentsCandidate candidate)
	{
		return builder().candidate(candidate).build();
	}

	public boolean isMatching(@NonNull final RelatedDocumentsId relatedDocumentsId)
	{
		return candidates.stream().anyMatch(candidate -> candidate.isMatching(relatedDocumentsId));
	}

	public Stream<RelatedDocuments> evaluateAndStream(@NonNull final RelatedDocumentsEvaluationContext context)
	{
		final ImmutableList<RelatedDocumentsCandidateWithRecordsCount> candidatesWithRecordCount = candidates
				.stream()
				.map(candidate -> evaluateOrNull(candidate, context))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		if (candidatesWithRecordCount.isEmpty())
		{
			return Stream.empty();
		}
		else if (candidatesWithRecordCount.size() == 1)
		{
			final RelatedDocuments relatedDocuments = toRelatedDocuments(candidatesWithRecordCount.get(0).withAppendFilterByFieldCaption(false));
			return Stream.of(relatedDocuments);
		}
		else
		{
			return candidatesWithRecordCount.stream()
					.map(candidateWithRecordsCount -> candidateWithRecordsCount.withAppendFilterByFieldCaption(true))
					.map(this::toRelatedDocuments);
		}
	}

	private RelatedDocumentsCandidateWithRecordsCount evaluateOrNull(
			@NonNull final RelatedDocumentsCandidate candidate,
			@NonNull final RelatedDocumentsEvaluationContext context)
	{
		try
		{
			// Apply onlyRelatedDocumentsId
			if (context.getOnlyRelatedDocumentsId() != null && !RelatedDocumentsId.equals(context.getOnlyRelatedDocumentsId(), candidate.getId()))
			{
				return null;
			}

			//
			// Only consider a window already seen if it actually has record count > 0 (task #1062)
			final Priority alreadySeenRelatedDocumentsPriority = context.getPriorityOrNull(candidate.getTargetWindow());
			if (alreadySeenRelatedDocumentsPriority != null
					&& alreadySeenRelatedDocumentsPriority.isHigherThan(candidate.getPriority()))
			{
				logger.debug("Skipping related documents {} because there is already one for destination '{}'", this, candidate.getTargetWindow());
				return null;
			}

			//
			// Compute records count
			final Stopwatch stopwatch = Stopwatch.createStarted();
			final int recordsCount = candidate.getDocumentsCountSupplier().getRecordsCount(context.getPermissions());
			stopwatch.stop();
			logger.debug("Computed records count for {} in {}", this, stopwatch);
			if (recordsCount <= 0)
			{
				logger.debug("No records found for {}", this);
				return null;
			}
			final Duration recordsCountDuration = Duration.ofNanos(stopwatch.elapsed(TimeUnit.NANOSECONDS));

			context.putAlreadySeenWindow(candidate.getTargetWindow(), candidate.getPriority());

			return RelatedDocumentsCandidateWithRecordsCount.builder()
					.candidate(candidate)
					.recordsCount(recordsCount)
					.recordsCountDuration(recordsCountDuration)
					.build();
		}
		catch (final Exception ex)
		{
			logger.warn("Failed evaluating {}", candidate, ex);
			return null;
		}
	}

	private RelatedDocuments toRelatedDocuments(@NonNull final RelatedDocumentsCandidateGroup.RelatedDocumentsCandidateWithRecordsCount candidateWithRecordsCount)
	{
		final RelatedDocumentsCandidate candidate = candidateWithRecordsCount.getCandidate();
		final int recordsCount = candidateWithRecordsCount.getRecordsCount();
		final Duration recordsCountDuration = candidateWithRecordsCount.getRecordsCountDuration();

		final TranslatableStringBuilder captionBuilder = TranslatableStrings.builder()
				.append(candidate.getWindowCaption());
		if (candidateWithRecordsCount.isAppendFilterByFieldCaption()
				&& !TranslatableStrings.isBlank(candidate.getFilterByFieldCaption())
				&& !Objects.equals(candidate.getWindowCaption(), candidate.getFilterByFieldCaption()))
		{
			captionBuilder.append(" / ").append(candidate.getFilterByFieldCaption());
		}

		final ITranslatableString caption = captionBuilder
				.append(" (#").append(recordsCount).append(")")
				.build();

		final MQuery queryCopy = candidate.getQuerySupplier().getQuery().deepCopy();
		queryCopy.setRecordCount(recordsCount, recordsCountDuration);

		return RelatedDocuments.builder()
				.id(candidate.getId())
				.internalName(candidate.getInternalName())
				.targetWindow(candidate.getTargetWindow())
				.priority(candidate.getPriority())
				.caption(caption)
				.filterByFieldCaption(candidate.getFilterByFieldCaption())
				.query(queryCopy)
				.build();
	}

	@Value
	@Builder
	private static class RelatedDocumentsCandidateWithRecordsCount
	{
		@Delegate
		@NonNull RelatedDocumentsCandidate candidate;

		int recordsCount;
		Duration recordsCountDuration;

		@With
		boolean appendFilterByFieldCaption;
	}
}
