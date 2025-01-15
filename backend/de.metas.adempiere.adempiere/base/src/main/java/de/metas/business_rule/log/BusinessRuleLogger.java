package de.metas.business_rule.log;

import de.metas.business_rule.log.BusinessRuleLoggerContext.BusinessRuleLoggerContextBuilder;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.MessageFormatter;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class BusinessRuleLogger
{
	@NonNull private static final Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(BusinessRuleLogger.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final BusinessRuleLogRepository logRepository;

	@NonNull private static final ThreadLocal<BusinessRuleLoggerContext> contextHolder = ThreadLocal.withInitial(() -> BusinessRuleLoggerContext.ROOT);

	public void warn(String message, Object... args)
	{
		warn(null, message, args);
	}

	public void warn(@Nullable final BusinessRuleStopwatch stopwatch, String message, Object... args)
	{
		final BusinessRuleLoggerContext context = contextHolder.get();
		final Duration duration = stopwatch != null ? stopwatch.getDuration() : null;

		if (slf4jLogger.isWarnEnabled())
		{
			//noinspection StringConcatenationArgumentToLogCall
			slf4jLogger.warn(message + " -- " + context, args);
		}

		createLogEntry(BusinessRuleLogLevel.WARN, duration, message, args);
	}

	public void info(String message, Object... args)
	{
		info(null, message, args);
	}

	public void info(@Nullable final BusinessRuleStopwatch stopwatch, String message, Object... args)
	{
		final BusinessRuleLoggerContext context = contextHolder.get();
		final Duration duration = stopwatch != null ? stopwatch.getDuration() : null;

		if (slf4jLogger.isInfoEnabled())
		{
			//noinspection StringConcatenationArgumentToLogCall
			slf4jLogger.info(message + " -- " + context, args);
		}

		createLogEntry(BusinessRuleLogLevel.INFO, duration, message, args);
	}

	public void debug(String message, Object... args)
	{
		debug(null, message, args);
	}

	public void debug(@Nullable final BusinessRuleStopwatch stopwatch, final String message, final Object... args)
	{
		final BusinessRuleLoggerContext context = contextHolder.get();
		final Duration duration = stopwatch != null ? stopwatch.getDuration() : null;

		if (slf4jLogger.isDebugEnabled())
		{
			//noinspection StringConcatenationArgumentToLogCall
			slf4jLogger.debug(message + " -- " + context, args);
		}

		createLogEntry(BusinessRuleLogLevel.DEBUG, duration, message, args);
	}

	private void createLogEntry(
			@NonNull final BusinessRuleLogLevel level,
			@Nullable final Duration duration,
			@Nullable final String message,
			@Nullable final Object[] args)
	{
		final Object[] argsNorm;
		final AdIssueId errorId;
		if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable)
		{
			// Remove exception from args array
			final AdempiereException exception = AdempiereException.wrapIfNeeded((Throwable)args[args.length - 1]);
			errorId = errorManager.createIssue(exception);
			argsNorm = new Object[args.length - 1];
			System.arraycopy(args, 0, argsNorm, 0, argsNorm.length);
		}
		else
		{
			argsNorm = args;
			errorId = null;
		}

		final BusinessRuleLoggerContext context = contextHolder.get();

		logRepository.create(BusinessRuleLogEntryRequest.fromContext(context)
				.level(level)
				.message(MessageFormatter.format(message, argsNorm))
				.errorId(errorId)
				.duration(duration)
				.build());
	}

	public IAutoCloseable temporaryChangeContext(@NonNull final Consumer<BusinessRuleLoggerContextBuilder> updater)
	{
		final BusinessRuleLoggerContext previousContext = contextHolder.get();
		final BusinessRuleLoggerContext newContext = previousContext.newChild(updater);

		contextHolder.set(newContext);
		return () -> contextHolder.set(previousContext);
	}

	public void setTargetRecordRef(final TableRecordReference recordRef)
	{
		changeContext(context -> context.withTargetRecordRef(recordRef));
	}

	public BusinessRuleStopwatch newStopwatch()
	{
		return BusinessRuleStopwatch.createStarted();
	}

	private void changeContext(@NonNull final UnaryOperator<BusinessRuleLoggerContext> updater)
	{
		final BusinessRuleLoggerContext previousContext = contextHolder.get();
		if (previousContext.isRootContext())
		{
			throw new AdempiereException("Changing root context not allowed. Use temporaryChangeContext() instead");
		}

		final BusinessRuleLoggerContext newContext = updater.apply(previousContext);

		contextHolder.set(newContext);
	}

}
