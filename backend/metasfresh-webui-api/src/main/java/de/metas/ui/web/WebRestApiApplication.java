/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.CommandLineParser;
import de.metas.JsonObjectMapperHolder;
import de.metas.MetasfreshBeanNameGenerator;
import de.metas.Profiles;
import de.metas.organization.ClientAndOrgId;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelectionLine;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection_ToDelete;
import de.metas.ui.web.config.ConfigConstants;
import de.metas.ui.web.session.WebRestApiContextProvider;
import de.metas.ui.web.window.model.DocumentInterfaceWrapperHelper;
import de.metas.util.Check;
import de.metas.util.ConnectionUtil;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;

@SpringBootApplication(scanBasePackages = { "de.metas", "org.adempiere" })
@EnableAsync
@Profile(Profiles.PROFILE_Webui)
public class WebRestApiApplication
{
	private static final String SYSCONFIG_PREFIX_WEBUI_SPRING_PROFILES_ACTIVE = "de.metas.ui.web.spring.profiles.active";
	private static final String SYSTEM_PROPERTY_APP_NAME = "spring.application.name";

	/**
	 * By default, we run in headless mode. But using this system property, we can also run with headless=false.
	 * The only known use of that is that metasfresh can open the initial license & connection dialog to store the initial properties file.
	 */
	private static final String SYSTEM_PROPERTY_HEADLESS = "webui-api-run-headless";

	private final ApplicationContext applicationContext;

	public WebRestApiApplication(@NonNull final ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}

	public static void main(final String[] args)
	{
		setDefaultProperties();

		final CommandLineParser.CommandLineOptions commandLineOptions = CommandLineParser.parse(args);

		final ConnectionUtil.ConfigureConnectionsResult configureConnectionsResult = ConnectionUtil.configureConnectionsIfArgsProvided(commandLineOptions);

		try (final IAutoCloseable ignored = ModelValidationEngine.postponeInit())
		{
			Ini.setRunMode(RunMode.WEBUI);
			Ini.setIfMissingMetasfreshProperties(configureConnectionsResult.isCconnectionConfigured() ? Ini.IfMissingMetasfreshProperties.IGNORE : Ini.IfMissingMetasfreshProperties.SHOW_DIALOG);
			Adempiere.instance.startup(RunMode.WEBUI);

			final ArrayList<String> activeProfiles = retrieveActiveProfilesFromSysConfig();
			activeProfiles.add(Profiles.PROFILE_Webui);

			final String headless = System.getProperty(SYSTEM_PROPERTY_HEADLESS, Boolean.toString(true));

			new SpringApplicationBuilder(WebRestApiApplication.class)
					.headless(Boolean.parseBoolean(headless)) // we need headless=false for initial connection setup popup (if any), usually this only applies on dev workstations.
					.web(WebApplicationType.SERVLET)
					.profiles(activeProfiles.toArray(new String[0]))
					.beanNameGenerator(new MetasfreshBeanNameGenerator())
					.run(args);
		}

		// now init the model validation engine
		ModelValidationEngine.get();
	}

	private static ArrayList<String> retrieveActiveProfilesFromSysConfig()
	{
		return new ArrayList<>(Services
				.get(ISysConfigBL.class)
				.getValuesForPrefix(SYSCONFIG_PREFIX_WEBUI_SPRING_PROFILES_ACTIVE, ClientAndOrgId.SYSTEM)
				.values());
	}

	@Bean
	@Primary
	public ObjectMapper jsonObjectMapper()
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper();
	}

	@Bean(Adempiere.BEAN_NAME)
	public Adempiere adempiere(final WebRestApiContextProvider webuiContextProvider)
	{
		Env.setContextProvider(webuiContextProvider);

		AdempiereException.enableCaptureLanguageOnConstructionTime(); // because usually at the time the message is (lazy) parsed the user session context is no longer available.

		InterfaceWrapperHelper.registerHelper(new DocumentInterfaceWrapperHelper());

		final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
		migrationLogger.addTablesToIgnoreList(
				I_T_WEBUI_ViewSelection.Table_Name,
				I_T_WEBUI_ViewSelectionLine.Table_Name,
				I_T_WEBUI_ViewSelection_ToDelete.Table_Name
		);

		return Env.getSingleAdempiereInstance(applicationContext);
	}

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer()
	{
		return tomcatContainerFactory -> tomcatContainerFactory.addConnectorCustomizers(connector -> {
			final AbstractHttp11Protocol<?> httpProtocol = (AbstractHttp11Protocol<?>)connector.getProtocolHandler();
			httpProtocol.setCompression("on");
			httpProtocol.setCompressionMinSize(256);
			final String mimeTypes = httpProtocol.getCompressibleMimeType();
			final String mimeTypesWithJson = mimeTypes + "," + MediaType.APPLICATION_JSON_VALUE + ",application/javascript";
			httpProtocol.setCompressibleMimeType(mimeTypesWithJson);
		});
	}

	@Bean(ConfigConstants.BEANNAME_WebuiTaskScheduler)
	public TaskScheduler webuiTaskScheduler()
	{
		final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setThreadNamePrefix("webui-task-scheduler-");
		taskScheduler.setDaemon(true);
		taskScheduler.setPoolSize(10);
		return taskScheduler;
	}

	private static void setDefaultProperties()
	{
		if (Check.isEmpty(System.getProperty("PropertyFile"), true))
		{
			System.setProperty("PropertyFile", "./metasfresh.properties");
		}

		if (Check.isBlank(System.getProperty(SYSTEM_PROPERTY_APP_NAME)))
		{
			System.setProperty(SYSTEM_PROPERTY_APP_NAME, WebRestApiApplication.class.getSimpleName());
		}
	}
}
