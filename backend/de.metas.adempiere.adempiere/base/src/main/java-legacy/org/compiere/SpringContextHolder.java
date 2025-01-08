/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.compiere;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public final class SpringContextHolder
{
	public static final SpringContextHolder instance = new SpringContextHolder();

	private static final Logger logger = LogManager.getLogger(SpringContextHolder.class);

	@Nullable
	private ApplicationContext applicationContext;

	private final JUnitBeansMap junitRegisteredBeans = new JUnitBeansMap();

	private SpringContextHolder()
	{
	}

	@Nullable
	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isApplicationContextSet()
	{
		return applicationContext != null;
	}

	/**
	 * Inject the application context from outside <b>and</b> enable {@link Services} to retrieve service implementations from it.
	 * <p>
	 * Currently seems to be required because currently the client startup procedure needs to be decomposed more.
	 * See <code>SwingUIApplication</code> to know what I mean.
	 */
	public void setApplicationContext(@NonNull final ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
		logger.info("Set application context: {}", applicationContext);

		// gh #427: NOTE: the "Services.setExternalServiceImplProvider" is not called here because it might introduce a deadlock.
		// we will call it when the spring context was loaded.
	}

	public void clearApplicationContext()
	{
		this.applicationContext = null;
		logger.info("Cleared application context");

		clearJUnitRegisteredBeans();
	}

	public void clearJUnitRegisteredBeans()
	{
		this.junitRegisteredBeans.clear();
		logger.info("Cleared JUnit registered beans");
	}

	/**
	 * Allows to "statically" autowire a bean that is somehow not wired by spring. Needs the applicationContext to be set.
	 */
	public void autowire(@NonNull final Object bean)
	{
		final ApplicationContext springApplicationContext = getApplicationContext();
		if (springApplicationContext == null)
		{
			return;
		}
		springApplicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
	}

	/**
	 * When running this method from within a junit test, we need to fire up spring
	 */
	public <T> T getBean(@NonNull final Class<T> requiredType)
	{
		if (Adempiere.isUnitTestMode())
		{
			final T beanImpl = junitRegisteredBeans.getBeanOrNull(requiredType);
			if (beanImpl != null)
			{
				return beanImpl;
			}
		}

		final ApplicationContext springApplicationContext = getApplicationContext();
		try
		{
			throwExceptionIfNull(springApplicationContext);
		}
		catch (final AdempiereException e)
		{
			throw e.appendParametersToMessage()
					.setParameter("requiredType", requiredType);
		}
		// noinspection ConstantConditions
		return springApplicationContext.getBean(requiredType);
	}

	/**
	 * can be used if a service might be retrieved before the spring application context is up
	 */
	@Nullable
	public <T> T getBeanOr(@NonNull final Class<T> requiredType, @Nullable final T defaultImplementation)
	{
		if (Adempiere.isUnitTestMode())
		{
			final T beanImpl = junitRegisteredBeans.getBeanOrNull(requiredType);
			if (beanImpl != null)
			{
				return beanImpl;
			}
		}

		final ApplicationContext springApplicationContext = getApplicationContext();
		if (springApplicationContext == null)
		{
			return defaultImplementation;
		}

		try
		{
			return springApplicationContext.getBean(requiredType);
		}
		catch (final NoSuchBeanDefinitionException | IllegalStateException e)
		{
			if (Adempiere.isUnitTestMode())
			{
				return defaultImplementation; // otherwise we would need to register NoopPerformanceMonitoringService for >800 unit tests
			}
			throw e;
		}
	}

	/**
	 * When running this method from within a junit test, we need to fire up spring
	 */
	public <T> Collection<T> getBeansOfType(@NonNull final Class<T> requiredType)
	{
		if (Adempiere.isUnitTestMode())
		{
			final ImmutableList<T> beans = junitRegisteredBeans.getBeansOfTypeOrNull(requiredType);
			if (beans != null)
			{
				logger.info("JUnit testing Returning manually registered bean: {}", beans);
				return beans;
			}
		}

		final ApplicationContext springApplicationContext = getApplicationContext();
		try
		{
			throwExceptionIfNull(springApplicationContext);
		}
		catch (final AdempiereException e)
		{
			throw e.appendParametersToMessage()
					.setParameter("requiredType", requiredType);
		}
		// noinspection ConstantConditions
		return springApplicationContext.getBeansOfType(requiredType).values();
	}

	private static ApplicationContext throwExceptionIfNull(@Nullable final ApplicationContext springApplicationContext)
	{
		if (springApplicationContext != null)
		{
			return springApplicationContext;
		}
		final String message;
		if (Adempiere.isUnitTestMode())
		{
			message = "This unit test requires a spring ApplicationContext; \n"
					+ "\n"
					+ "Use org.compiere.SpringContextHolder.registerJUnitBean(T) to register your bean\n"
					+ "E.g.\n"
					+ "SpringContextHolder.registerJUnitBean(new GreetingRepository());\n";
		}
		else
		{
			message = "SpringApplicationContext not configured yet";
		}
		throw new AdempiereException(message);
	}

	public boolean isSpringProfileActive(@NonNull final String profileName)
	{
		final ApplicationContext springApplicationContext = throwExceptionIfNull(getApplicationContext());

		final String[] activeProfiles = springApplicationContext.getEnvironment().getActiveProfiles();
		return Arrays
				.stream(activeProfiles)
				.anyMatch(env -> env.equalsIgnoreCase(profileName));
	}

	public static <T> void registerJUnitBean(@NonNull final T beanImpl)
	{
		instance.junitRegisteredBeans.registerJUnitBean(beanImpl);
	}

	public static <BT, T extends BT> void registerJUnitBean(@NonNull final Class<BT> beanType, @NonNull final T beanImpl)
	{
		instance.junitRegisteredBeans.registerJUnitBean(beanType, beanImpl);
	}

	public static <BT, T extends BT> void registerJUnitBeans(@NonNull final Class<BT> beanType, @NonNull final List<T> beansToAdd)
	{
		instance.junitRegisteredBeans.registerJUnitBeans(beanType, beansToAdd);
	}

	public static <T> Lazy<T> lazyBean(@NonNull final Class<T> requiredType)
	{
		return new Lazy<>(requiredType, null);
	}

	public static <T> Lazy<T> lazyBean(@NonNull final Class<T> requiredType, @Nullable final T initialBean)
	{
		return new Lazy<>(requiredType, initialBean);
	}

	public Optional<String> getProperty(@NonNull final String name)
	{
		if (applicationContext != null)
		{
			final String springContextValue = StringUtils.trimBlankToNull(applicationContext.getEnvironment().getProperty(name));
			if (springContextValue != null)
			{
				logger.debug("Returning the spring context's value {}={} instead of looking up the AD_SysConfig record", name, springContextValue);
				return Optional.of(springContextValue);
			}
		}
		else
		{
			// If there is no Spring context then go an check JVM System Properties.
			// Usually we will get here when we will run some tools based on metasfresh framework.

			final Properties systemProperties = System.getProperties();
			final String systemPropertyValue = StringUtils.trimBlankToNull(systemProperties.getProperty(name));
			if (systemPropertyValue != null)
			{
				logger.debug("Returning the JVM system property's value {}={} instead of looking up the AD_SysConfig record", name, systemPropertyValue);
				return Optional.of(systemPropertyValue);
			}

			// If there is no JVM System Property then go and check environment variables
			return StringUtils.trimBlankToOptional(System.getenv(name));
		}

		return Optional.empty();
	}

	//
	//
	//

	@ToString
	public static final class Lazy<T>
	{
		private final Class<T> requiredType;
		private T bean;

		private Lazy(@NonNull final Class<T> requiredType, @Nullable final T initialBean)
		{
			this.requiredType = requiredType;
			this.bean = initialBean;
		}

		@NonNull
		public T get()
		{
			T bean = this.bean;
			if (bean == null)
			{
				bean = this.bean = instance.getBean(requiredType);
			}
			return bean;
		}
	}
}
