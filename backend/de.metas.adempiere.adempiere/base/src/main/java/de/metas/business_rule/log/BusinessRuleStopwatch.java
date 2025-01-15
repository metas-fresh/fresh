package de.metas.business_rule.log;

import de.metas.common.util.time.SystemTime;
import lombok.ToString;

import java.time.Duration;
import java.time.Instant;

//@EqualsAndHashCode
@ToString
public class BusinessRuleStopwatch
{
	private Instant start;

	private BusinessRuleStopwatch()
	{
		start = now();
	}

	public static BusinessRuleStopwatch createStarted()
	{
		return new BusinessRuleStopwatch();
	}

	private static Instant now() {return SystemTime.asInstant();}

	public void restart()
	{
		start = now();
	}

	public Duration getDuration()
	{
		return Duration.between(start, now());
	}
}
