package de.metas.business_rule.server;

import de.metas.Profiles;
import de.metas.business_rule.BusinessRuleService;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class BusinessRuleEventProcessorWatcher
{
	@NonNull private static final Logger logger = LogManager.getLogger(BusinessRuleEventProcessorWatcher.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final BusinessRuleService ruleService;

	private static final String SYSCONFIG_PollIntervalInSeconds = "BusinessRuleEventProcessorWatcher.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);

	static final String SYSCONFIG_RetrieveBatchSize = "BusinessRuleEventProcessorWatcher.retrieveBatchSize";
	private static final QueryLimit DEFAULT_RetrieveBatchSize = QueryLimit.ofInt(2000);

	@PostConstruct
	public void postConstruct()
	{
		startThread();
	}

	private void startThread()
	{
		final Thread thread = new Thread(this::processEventsLoop);
		thread.setDaemon(true);
		thread.setName("BusinessRuleEventsProcessor");
		thread.start();
		logger.info("Started {}", thread);
	}

	private void processEventsLoop()
	{
		while (true)
		{
			try
			{
				sleep();
			}
			catch (final InterruptedException e)
			{
				logger.info("Got interrupt request. Exiting.");
				return;
			}

			try
			{
				ruleService.processEvents(getRetrieveBatchSize());
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
		return pollIntervalInSeconds > 0 ? Duration.ofSeconds(pollIntervalInSeconds) : DEFAULT_PollInterval;
	}

	private QueryLimit getRetrieveBatchSize()
	{
		final int retrieveBatchSize = sysConfigBL.getIntValue(SYSCONFIG_RetrieveBatchSize, -1);
		return retrieveBatchSize > 0 ? QueryLimit.ofInt(retrieveBatchSize) : DEFAULT_RetrieveBatchSize;
	}
}
