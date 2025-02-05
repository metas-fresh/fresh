package de.metas.logging;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.annotation.Nullable;
import java.io.File;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Log Management.
 */
public final class LogManager
{
	public static Logger getLogger(final Class<?> clazz)
	{
		final Logger logger = LoggerFactory.getLogger(clazz);

		// Customize the logger if needed
		loggerCustomizer.customize(logger);

		return logger;
	}

	public static Logger getLogger(final String loggerName)
	{
		final Logger logger = LoggerFactory.getLogger(loggerName);

		// Customize the logger if needed
		loggerCustomizer.customize(logger);

		return logger;
	}

	private static final ILoggerCustomizer loggerCustomizer = SysConfigLoggerCustomizer.instance;

	/**
	 * See {@link ILoggerCustomizer#dumpConfig()}.
	 * <p>
	 *
	 * @implSpec <a href="https://github.com/metasfresh/metasfresh/issues/288">task</a>
	 */
	public static String dumpCustomizerConfig()
	{
		return loggerCustomizer.dumpConfig();
	}

	/**
	 * Initialize Logging
	 *
	 * @param isClient client
	 */
	public static void initialize(final boolean isClient)
	{
		if (s_initialized.getAndSet(true))
		{
			return;
		}

		//
		// Forward all logs from JUL to SLF4J
		{
			SLF4JBridgeHandler.uninstall();
			SLF4JBridgeHandler.install();
		}

		if (isClient)
		{
			// FRESH-267: in server mode, do not (re)set the loglevels. They are coming out of logback.xml and/or application properties and were probably fine-tuned.
			// resetting them to info might cause the disk to run full.
			setLevel(Level.INFO);
		}
	}

	private static final AtomicBoolean s_initialized = new AtomicBoolean(false);
	/**
	 * Current Log Level
	 */
	private static Level s_currentLevel = null;

	/**
	 * Logger
	 */
	private static final Logger log = LoggerFactory.getLogger(LogManager.class);

	public static boolean isFileLoggingEnabled()
	{
		return !MetasfreshFileLoggerHelper.get().isDisabled();
	}

	public static void setFileLoggingEnabled(final boolean fileLoggingEnabled)
	{
		Ini.setProperty(Ini.P_TRACEFILE_ENABLED, fileLoggingEnabled);
		MetasfreshFileLoggerHelper.get().setDisabled(!fileLoggingEnabled);
	}

	public static List<Level> getAvailableLoggingLevels()
	{
		return LOGBACK_LEVELS;
	}

	public static void setLevelAndUpdateIni(final Level level)
	{
		if (level == null)
		{
			return;
		}

		Ini.setProperty(Ini.P_TRACELEVEL, level.toString());
		setLevel(level);
	}

	/**
	 * Set Level
	 *
	 * @param levelString string representation of level
	 */
	public static void setLevel(final String levelString)
	{
		final Level level = asLogbackLevel(levelString);
		if (level == null)
		{
			log.info("Ignored: " + levelString);
			return;
		}

		setLevel(level);
	}

	public static void setLevel(final Level level)
	{
		if (level == null)
		{
			return;
		}

		initialize(true);

		//
		final Level levelOld = s_currentLevel;
		if (levelOld != null && level.toInt() == levelOld.toInt())
		{
			return;
		}

		// JDBC if ALL
		setJDBCDebug(level.toInt() == Level.ALL_INT);

		//
		// SLF4J (logback)
		int countChangedLoggers = 0;
		for (String ownRootLoggerName : OWNLOGGERS_NAME_PREFIXES)
		{
			final Logger ownRootLogger = LoggerFactory.getLogger(ownRootLoggerName);
			if (setLoggerLevel(ownRootLogger, level))
			{
				countChangedLoggers++;
			}
		}
		System.out.println("Changing log level " + levelOld + "->" + level + " (" + countChangedLoggers + " root loggers affected)");

		s_currentLevel = level;
	}

	public static boolean setLoggerLevel(final Logger logger, final Level level)
	{
		if (logger == null)
		{
			return false;
		}

		// NOTE: level is OK to be null (i.e. we want to parent's level), as long as it's not the root logger
		// if (level == null)
		// {
		// return false;
		// }

		// NOTE: we shall allow changing any logger. there is no point not allowing it.
		// if (!isOwnLogger(logger))
		// {
		// return false;
		// }

		if (logger instanceof ch.qos.logback.classic.Logger)
		{
			((ch.qos.logback.classic.Logger)logger).setLevel(level);
			return true;
		}

		return false;
	}

	public static boolean setLoggerLevel(final Logger logger, final String levelStr)
	{
		final Level level = asLogbackLevel(levelStr);
		return setLoggerLevel(logger, level);
	}

	public static boolean setLoggerLevel(final Class<?> clazz, final Level level)
	{
		return setLoggerLevel(getLogger(clazz), level);
	}

	public static boolean setLoggerLevel(final String loggerName, final Level level)
	{
		return setLoggerLevel(getLogger(loggerName), level);
	}

	@Nullable
	public static String getLoggerLevelName(@Nullable final Logger logger)
	{
		final Level level = getLoggerLevel(logger);
		return level == null ? null : level.toString();
	}

	@Nullable
	public static Level getLoggerLevel(@Nullable final Logger logger)
	{
		if (logger == null)
		{
			return null;
		}
		else if (logger instanceof ch.qos.logback.classic.Logger)
		{
			return ((ch.qos.logback.classic.Logger)logger).getEffectiveLevel();
		}
		else
		{
			return null;
		}
	}

	private static final List<String> OWNLOGGERS_NAME_PREFIXES = ImmutableList.of("de.metas", "org.adempiere", "org.compiere", "org.eevolution");

	/**
	 * Set JDBC Debug
	 */
	private static void setJDBCDebug(final boolean enable)
	{
		if (enable)
		{
			DriverManager.setLogWriter(new PrintWriter(System.err));
		}
		else
		{
			DriverManager.setLogWriter(null);
		}
	}    // setJDBCDebug

	/**
	 * @return current log level or <code>null</code> if not initialized
	 */
	public static Level getLevel()
	{
		return s_currentLevel;
	}

	/**
	 * Is Logging Level logged
	 *
	 * @param level level
	 * @return true if it is logged
	 */
	public static boolean isLevel(final Level level)
	{
		if (level == null)
		{
			return false;
		}

		final Level currentLevel = getLevel();
		if (currentLevel == null)
		{
			return false;
		}

		return level.toInt() >= currentLevel.toInt();
	}    // isLevel

	/**
	 * Is Logging Level FINEST logged
	 *
	 * @return true if it is logged
	 */
	public static boolean isLevelFinest()
	{
		return isLevel(Level.TRACE);
	}    // isLevelFinest

	/**
	 * Is Logging Level FINER logged
	 *
	 * @return true if it is logged
	 */
	public static boolean isLevelFiner()
	{
		return isLevelFinest();
	}    // isLevelFiner

	/**
	 * Is Logging Level FINE logged
	 *
	 * @return true if it is logged
	 */
	public static boolean isLevelFine()
	{
		return isLevel(Level.DEBUG);
	}

	/**
	 * Shutdown Logging system
	 */
	public static void shutdown()
	{
		// final LogManager mgr = LogManager.getLogManager();
		// mgr.reset();
	}    // shutdown

	public static File getActiveLogFile()
	{
		return MetasfreshFileLoggerHelper.get().getActiveFileOrNull();
	}

	public static List<File> getLogFiles()
	{
		return MetasfreshFileLoggerHelper.get().getLogFiles();
	}

	public static boolean isActiveLogFile(final File file)
	{
		return MetasfreshFileLoggerHelper.get().isActiveLogFile(file);
	}

	public static void flushLogFile()
	{
		MetasfreshFileLoggerHelper.get().flushActiveLogFile();
	}

	public static void rotateLogFile()
	{
		MetasfreshFileLoggerHelper.get().rotateLogFile();
	}

	public static IAutoCloseable temporaryDisableIssueReporting()
	{
		final MetasfreshIssueAppender issueAppender = MetasfreshIssueAppender.get();
		if (issueAppender == null)
		{
			return NullAutoCloseable.instance;
		}
		return issueAppender.temporaryDisableIssueReporting();
	}

	public static void skipIssueReportingForLoggerName(final Logger logger)
	{
		if (logger == null)
		{
			return;
		}
		skipIssueReportingForLoggerName(logger.getName());
	}

	public static void skipIssueReportingForLoggerName(final String loggerName)
	{
		final MetasfreshIssueAppender issueAppender = MetasfreshIssueAppender.get();
		if (issueAppender == null)
		{
			return;
		}
		issueAppender.skipIssueReportingForLoggerName(loggerName);
	}

	private static final List<ch.qos.logback.classic.Level> LOGBACK_LEVELS = ImmutableList.<ch.qos.logback.classic.Level>builder()
			.add(ch.qos.logback.classic.Level.OFF)
			.add(ch.qos.logback.classic.Level.ERROR)
			.add(ch.qos.logback.classic.Level.WARN)
			.add(ch.qos.logback.classic.Level.INFO)
			.add(ch.qos.logback.classic.Level.DEBUG)
			.add(ch.qos.logback.classic.Level.TRACE)
			.add(ch.qos.logback.classic.Level.ALL)
			.build();

	/**
	 * @return {@link Level} or null
	 */
	public static ch.qos.logback.classic.Level asLogbackLevel(String levelStr)
	{
		if (Check.isEmpty(levelStr, true))
		{
			return null;
		}

		levelStr = levelStr.trim();

		// Try logback
		{
			final Level level = Level.toLevel(levelStr, null);
			if (level != null)
			{
				return level;
			}
		}

		// Try JUL
		try
		{
			final java.util.logging.Level julLevel = java.util.logging.Level.parse(levelStr);
			final Level level = asLogbackLevel(julLevel);
			if (level != null)
			{
				return level;
			}
		}
		catch (final Exception e)
		{
			//
		}

		return null;
	}

	private static ch.qos.logback.classic.Level asLogbackLevel(final java.util.logging.Level julLevel)
	{
		if (julLevel == null)
		{
			return null;
		}

		final int julLevelValue = julLevel.intValue();
		if (julLevelValue <= JUL_TRACE_LEVEL_THRESHOLD)
		{
			return ch.qos.logback.classic.Level.TRACE;
		}
		else if (julLevelValue <= JUL_DEBUG_LEVEL_THRESHOLD)
		{
			return ch.qos.logback.classic.Level.DEBUG;
		}
		else if (julLevelValue <= JUL_INFO_LEVEL_THRESHOLD)
		{
			return ch.qos.logback.classic.Level.INFO;
		}
		else if (julLevelValue <= JUL_WARN_LEVEL_THRESHOLD)
		{
			return ch.qos.logback.classic.Level.WARN;
		}
		else
		{
			return ch.qos.logback.classic.Level.ERROR;
		}
	}

	private static final int JUL_TRACE_LEVEL_THRESHOLD = java.util.logging.Level.FINEST.intValue();
	private static final int JUL_DEBUG_LEVEL_THRESHOLD = java.util.logging.Level.FINE.intValue();
	private static final int JUL_INFO_LEVEL_THRESHOLD = java.util.logging.Level.INFO.intValue();
	private static final int JUL_WARN_LEVEL_THRESHOLD = java.util.logging.Level.WARNING.intValue();

	public static void updateConfigurationFromIni()
	{
		final String logLevelStr = Ini.getProperty(Ini.P_TRACELEVEL);
		final Level level = asLogbackLevel(logLevelStr);
		setLevel(level);

		if (Ini.isSwingClient())
		{
			final boolean fileLoggingEnabled = Ini.isPropertyBool(Ini.P_TRACEFILE_ENABLED);
			MetasfreshFileLoggerHelper.get().setDisabled(!fileLoggingEnabled);
		}
	}

	/**
	 * @see #dumpAllLevelsUpToRoot(Logger)
	 */
	public static void dumpAllLevelsUpToRoot(final String loggerName)
	{
		final Logger logger = getLogger(loggerName);
		if (logger == null)
		{
			return;
		}
		dumpAllLevelsUpToRoot(logger);
	}

	/**
	 * @see #dumpAllLevelsUpToRoot(Logger)
	 */
	public static void dumpAllLevelsUpToRoot(final Class<?> clazz)
	{
		final Logger logger = getLogger(clazz);
		if (logger == null)
		{
			return;
		}
		dumpAllLevelsUpToRoot(logger);
	}

	/**
	 * Helper method to print (on System.out) all log levels starting from given logger, up to the root.
	 * <p>
	 * This method is useful in case you are debugging why a given logger does not have the correct effective level.
	 */
	public static void dumpAllLevelsUpToRoot(final Logger logger)
	{
		final Consumer<Logger> dumpLevel = (currentLogger) -> {
			final String currentLoggerInfo;
			if (currentLogger instanceof ch.qos.logback.classic.Logger)
			{
				final ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger)currentLogger;
				currentLoggerInfo = "effectiveLevel=" + logbackLogger.getEffectiveLevel() + ", level=" + logbackLogger.getLevel();
			}
			else
			{
				currentLoggerInfo = "unknown level for logger object " + currentLogger + " (" + currentLogger.getClass() + ")";
			}
			System.out.println(" * " + currentLogger.getName() + "(" + System.identityHashCode(currentLogger) + "): " + currentLoggerInfo);
		};

		System.out.println("\nDumping all log levels starting from " + logger);
		forAllLevelsUpToRoot(logger, dumpLevel);
	}

	public static void forAllLevelsUpToRoot(final Logger logger, final Consumer<Logger> consumer)
	{
		Logger currentLogger = logger;
		String currentLoggerName = currentLogger.getName();
		while (currentLogger != null)
		{
			consumer.accept(currentLogger);

			final int idx = currentLoggerName.lastIndexOf(".");
			if (idx < 0)
			{
				break;
			}

			currentLoggerName = currentLoggerName.substring(0, idx);
			currentLogger = getLogger(currentLoggerName);
		}

		consumer.accept(LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME));
	}

	private LogManager()
	{
		super();
	}
}
