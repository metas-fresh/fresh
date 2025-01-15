/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct.aggregation;

import com.google.common.annotations.VisibleForTesting;
import de.metas.acct.aggregation.legacy.FactAcctLogProcessResult;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import java.time.Duration;

public class FactAcctLogDBTableWatcher implements Runnable
{
	private static final Logger logger = LogManager.getLogger(FactAcctLogDBTableWatcher.class);
	private final ISysConfigBL sysConfigBL;
	private final FactAcctLogService factAcctLogService;

	private static final String SYSCONFIG_PollIntervalInSeconds = "de.metas.acct.aggregation.FactAcctLogDBTableWatcher.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);

	@VisibleForTesting
	static final String SYSCONFIG_RetrieveBatchSize = "de.metas.acct.aggregation.FactAcctLogDBTableWatcher.retrieveBatchSize";
	private static final QueryLimit DEFAULT_RetrieveBatchSize = QueryLimit.ofInt(2000);

	@Builder
	private FactAcctLogDBTableWatcher(
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final FactAcctLogService factAcctLogService)
	{
		this.sysConfigBL = sysConfigBL;
		this.factAcctLogService = factAcctLogService;
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				sleep();
			}
			catch (final InterruptedException e)
			{

				LOGGER.log(Level.WARN, "Thread interrupted", e);
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "An error occurred", e);
			}
			}

			try
			{
				processNow();
			}
			catch (final Exception ex)
			{
				logger.warn("Failed to process. Ignored.", ex);
			}
		}

	}

	private void sleep() throws InterruptedException
	{
		final Duration pollInterval = getPollInterval();
		logger.debug("Sleeping {}", pollInterval);
		Thread.sleep(pollInterval.toMillis());
	}

	private Duration getPollInterval()
	{
		final int pollIntervalInSeconds = sysConfigBL.getIntValue(SYSCONFIG_PollIntervalInSeconds, -1);
		return pollIntervalInSeconds > 0
				? Duration.ofSeconds(pollIntervalInSeconds)
				: DEFAULT_PollInterval;
	}

	private QueryLimit getRetrieveBatchSize()
	{
		final int retrieveBatchSize = sysConfigBL.getIntValue(SYSCONFIG_RetrieveBatchSize, -1);
		return retrieveBatchSize > 0 ? QueryLimit.ofInt(retrieveBatchSize) : DEFAULT_RetrieveBatchSize;
	}

	@VisibleForTesting
	FactAcctLogProcessResult processNow()
	{
		FactAcctLogProcessResult finalResult = FactAcctLogProcessResult.ZERO;

		boolean mightHaveMore;
		do
		{
			final QueryLimit retrieveBatchSize = getRetrieveBatchSize();
			final FactAcctLogProcessResult result = factAcctLogService.processAll(retrieveBatchSize);

			finalResult = finalResult.combineWith(result);
			mightHaveMore = retrieveBatchSize.isLessThanOrEqualTo(result.getProcessedLogRecordsCount());
			logger.debug("processNow: retrieveBatchSize={}, result={}, mightHaveMore={}", retrieveBatchSize, result, mightHaveMore);
		}
		while (mightHaveMore);

		logger.debug("processNow: DONE. Processed {}", finalResult);
		return finalResult;
	}

}
