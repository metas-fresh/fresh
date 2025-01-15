package org.adempiere.ad.modelvalidator;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.DocValidates;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.ModelChanges;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class AnnotatedModelInterceptorDescriptorBuilder
{
	private static final Logger logger = LogManager.getLogger(AnnotatedModelInterceptorDescriptorBuilder.class);

	private final Class<?> annotatedClass;

	/**
	 * Model class which is intercepted.
	 * <p>
	 * Specified by {@link Interceptor} annotation.
	 */
	private Class<?> _modelClass = null;
	/**
	 * List of table names on which binding is allowed
	 */
	private ImmutableSet<String> _allowedTargetTableNames = null;

	private final List<InterceptorInit> initializers = new ArrayList<>();

	/**
	 * Map PointcutType(TableName,PointcutType) -> pointcuts list.<br>
	 * FRESH-318: order then, because without a specified ordering, java7 executes them in a different ordering that java8.
	 */
	private final TreeSet<Pointcut> pointcuts = new TreeSet<>();

	AnnotatedModelInterceptorDescriptorBuilder(@NonNull final Class<?> annotatedClass)
	{
		this.annotatedClass = annotatedClass;
	}

	public AnnotatedModelInterceptorDescriptor build()
	{
		loadAnnotatedClassDefinition();
		loadPointcuts();

		return AnnotatedModelInterceptorDescriptor.builder()
				.annotatedClass(annotatedClass)
				.initializers(ImmutableSet.copyOf(initializers))
				.pointcuts(pointcuts.stream()
						.collect(ImmutableSetMultimap.toImmutableSetMultimap(this::mkKey, Function.identity())))
				.build();
	}

	private void loadAnnotatedClassDefinition()
	{
		final Class<?> modelClass = getModelClassFromAnnotationsRecursively(annotatedClass);
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (tableName == null)
		{
			throw new AdempiereException("Cannot find tableName for " + modelClass + ". Please make sure it's a model interface.");
		}
		this._modelClass = modelClass;
		this._allowedTargetTableNames = ImmutableSet.of(tableName);

		//
		// validate ModelValidator classname and:
		// * throw exception (if development mode)
		// * log warning if production mode
		// ts: relaxing the check; in package de.metas.inoutcandidate.modelvalidator we have C_Order related MVs for both shipment and receipts schedule..therefore it shall be OK if the MV's class
		// name *starts* with the table name.
		if (!annotatedClass.isAnonymousClass()
				&& !annotatedClass.getSimpleName().startsWith(tableName))
		{
			final AdempiereException ex = new AdempiereException("According to metas best practices, model validator shall have the same name as the table."
					+ "Please rename class " + annotatedClass + " to " + tableName);
			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				throw ex;
			}
			else
			{
				logger.warn(ex.getLocalizedMessage(), ex);
			}
		}
	}

	@NonNull
	private static Class<?> getModelClassFromAnnotationsRecursively(@NonNull final Class<?> annotatedClass)
	{
		Class<?> currentAnnotatedClass = annotatedClass;
		while (currentAnnotatedClass != null)
		{
			final Class<?> modelClass = getModelClassFromAnnotations(currentAnnotatedClass);
			if (modelClass != null)
			{
				return modelClass;
			}

			currentAnnotatedClass = currentAnnotatedClass.getSuperclass();
		}

		throw new AdempiereException("Each model interceptor class shall be marked with " + Interceptor.class + " annotation: " + annotatedClass);
	}

	@Nullable
	private static Class<?> getModelClassFromAnnotations(@NonNull final Class<?> annotatedClass)
	{
		final Interceptor annIterceptor = annotatedClass.getAnnotation(Interceptor.class);
		if (annIterceptor != null)
		{
			return annIterceptor.value();
		}

		// Fallback to Validator annotation
		final Validator annValidator = annotatedClass.getAnnotation(Validator.class);
		if (annValidator != null)
		{
			return annValidator.value();
		}

		return null;
	}

	private Class<?> getModelClass()
	{
		Check.assumeNotNull(_modelClass, "Parameter modelClass is not null");
		return _modelClass;
	}

	private void assertModelTableNameAllowed(final String tableName)
	{
		final ImmutableSet<String> allowedTargetTableNames = _allowedTargetTableNames;
		if (allowedTargetTableNames != null && !allowedTargetTableNames.contains(tableName))
		{
			throw new AdempiereException("Table " + tableName + " is not in the list of allowed tables names specified by " + Interceptor.class + " annotation: " + allowedTargetTableNames);
		}
	}

	private void loadPointcuts()
	{
		//
		// Get all methods from annotated class, including those from super classes
		@SuppressWarnings("unchecked") final Set<Method> annotatedClassMethods = ReflectionUtils.getAllMethods(annotatedClass);

		for (final Method method : annotatedClassMethods)
		{
			final Init annInit = method.getAnnotation(Init.class);
			if (annInit != null)
			{
				loadInitMethod(annInit, method);
			}

			final ModelChange annModelChange = method.getAnnotation(ModelChange.class);
			if (annModelChange != null)
			{
				loadPointcut(annModelChange, method);
			}

			final ModelChanges annModelChanges = method.getAnnotation(ModelChanges.class);
			if (annModelChanges != null && annModelChanges.value().length > 0)
			{
				for (ModelChange a : annModelChanges.value())
				{
					loadPointcut(a, method);
				}
			}

			final DocValidate annDocValidate = method.getAnnotation(DocValidate.class);
			if (annDocValidate != null)
			{
				loadPointcut(annDocValidate, method);
			}

			final DocValidates annDocValidates = method.getAnnotation(DocValidates.class);
			if (annDocValidates != null && annDocValidates.value().length > 0)
			{
				for (DocValidate a : annDocValidates.value())
				{
					loadPointcut(a, method);
				}
			}
		}
	}

	private void loadInitMethod(final Init annInit, final Method method)
	{

		boolean methodRequiresEngine = false;
		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length >= 1)
		{
			if (parameterTypes[0].isAssignableFrom(IModelValidationEngine.class))
			{
				methodRequiresEngine = true;
			}
			else
			{
				throw new AdempiereException("Invalid initalizer method format. First argument shall be " + IModelValidationEngine.class
						+ "\n Method: " + method
						+ "\n Annotation:" + annInit);
			}
		}

		final InterceptorInit init = new InterceptorInit(method, methodRequiresEngine);
		initializers.add(init);
	}

	private void loadPointcut(final ModelChange annModelChange, final Method method)
	{
		try
		{
			addPointcut(preparePointcut(PointcutType.ModelChange, method)
					.timings(extractTimings(annModelChange))
					.afterCommit(annModelChange.afterCommit())
					.columnNamesToCheckForChanges(annModelChange.ifColumnsChanged())
					.ignoreColumnNames(annModelChange.ignoreColumnsChanged())
					.onlyIfUIAction(annModelChange.ifUIAction())
					.skipIfCopying(annModelChange.skipIfCopying())
					.build());
		}
		catch (final AdempiereException ex)
		{
			throw ex.setParameter("annotation", annModelChange)
					.setParameter("method", method)
					.appendParametersToMessage();
		}
	}

	private static int[] extractTimings(final ModelChange annModelChange)
	{
		if (annModelChange.timings().length > 0 && annModelChange.types().length > 0)
		{
			throw new AdempiereException("Only `timings` or `types` shall be set, but not both");
		}
		else if (annModelChange.timings().length > 0)
		{
			return annModelChange.timings();
		}
		else if (annModelChange.types().length > 0)
		{
			return Stream.of(annModelChange.types())
					.mapToInt(ModelChangeType::toInt)
					.toArray();
		}
		else
		{
			throw new AdempiereException("At least `timings` or `types` shall be set");
		}
	}

	private void loadPointcut(final DocValidate annDocValidate, final Method method)
	{
		addPointcut(preparePointcut(PointcutType.DocValidate, method)
				.timings(annDocValidate.timings())
				.afterCommit(annDocValidate.afterCommit())
				.build());
	}

	private Pointcut.PointcutBuilder preparePointcut(PointcutType type, final Method method)
	{
		return Pointcut.builder()
				.type(type)
				.method(method)
				.modelClass(getModelClass());
	}

	private void addPointcut(@NonNull final Pointcut pointcut)
	{
		assertModelTableNameAllowed(pointcut.getTableName());

		if (!pointcuts.add(pointcut))
		{
			// shall not happen
			final String msg = StringUtils.formatMessage("Pointcut {} was not added because another one was found in the list: {}", pointcut, pointcuts);
			//noinspection ThrowableNotThrown
			new AdempiereException(msg).throwOrLogSevere(Services.get(IDeveloperModeBL.class).isEnabled(), logger);
		}

		logger.debug("Loaded {}", pointcut);
	}

	private PointcutKey mkKey(@NonNull final Pointcut pointcut)
	{
		return PointcutKey.of(pointcut.getTableName(), pointcut.getType());
	}
}
