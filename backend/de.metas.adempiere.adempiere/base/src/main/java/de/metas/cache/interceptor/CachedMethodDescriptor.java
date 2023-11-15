package de.metas.cache.interceptor;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CacheLabel;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheIgnore;
import de.metas.cache.annotation.CacheModel;
import de.metas.cache.annotation.CacheModelId;
import de.metas.cache.annotation.CacheReloadIfTrue;
import de.metas.cache.annotation.CacheTrx;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Util.ArrayKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@EqualsAndHashCode(of = "method")
@ToString
final class CachedMethodDescriptor
{
	private static final String DEFAULT_CacheName = CachedMethodDescriptor.class.getSimpleName() + "#defaultCache";
	private static final ImmutableSet<CacheLabel> ADDITIONAL_CACHE_LABELS = ImmutableSet.of(CacheLabel.ofString(CachedMethodDescriptor.class.getSimpleName()));
	private static final int DEFAULT_CacheInitialCapacity = 20;
	private static final int DEFAULT_CacheExpireMinutes = 10;

	@NonNull @Getter private final Method method;

	private final boolean staticMethod;
	@NonNull @Getter private final String cacheName;
	private final int expireMinutes;
	private final List<ICachedMethodPartDescriptor> descriptors;

	CachedMethodDescriptor(@NonNull final Method method)
	{
		this.method = method;
		this.staticMethod = Modifier.isStatic(method.getModifiers());
		final Cached cachedAnnotation = extractCachedAnnotation(method);
		this.cacheName = mkCacheName(cachedAnnotation);
		this.expireMinutes = extractExpireMinutes(cachedAnnotation);
		this.descriptors = extractMethodPartDescriptors(method);
	}

	@NonNull
	private static String mkCacheName(final Cached annotation)
	{
		final String cacheName = StringUtils.trimBlankToNull(annotation.cacheName());
		return cacheName != null ? cacheName : DEFAULT_CacheName;
	}

	private static Cached extractCachedAnnotation(final Method method)
	{
		return Check.assumeNotNull(method.getAnnotation(Cached.class), "cachedAnnotation not null");
	}

	private static ImmutableList<ICachedMethodPartDescriptor> extractMethodPartDescriptors(final Method method)
	{
		final Cached cachedAnnotation = extractCachedAnnotation(method);

		final Builder<ICachedMethodPartDescriptor> descriptorsBuilder = ImmutableList.builder();

		//
		// Caching part: Target PO
		{
			final TargetPOPartDescriptor descriptor = TargetPOPartDescriptor.createIfApplies(method, cachedAnnotation);
			if (descriptor != null)
			{
				descriptorsBuilder.add(descriptor);
			}
		}

		final Class<?>[] parameterTypes = method.getParameterTypes();
		final Annotation[][] parametersAnnotations = method.getParameterAnnotations();
		for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++)
		{
			final Class<?> parameterType = parameterTypes[parameterIndex];
			final List<Annotation> parameterAnnotations = Arrays.asList(parametersAnnotations[parameterIndex]);

			// Skip parameters which were marked with @CacheIgnore
			if (containsAnnotationType(parameterAnnotations, CacheIgnore.class))
			{
				continue;
			}

			//
			// Create descriptors from parameter annotations
			boolean createGenericDescriptor = true;
			for (final Annotation annotation : parameterAnnotations)
			{
				if (annotation instanceof CacheTrx)
				{
					final CacheTrxParamDescriptor paramDescriptor = new CacheTrxParamDescriptor(parameterType, parameterIndex, annotation);
					descriptorsBuilder.add(paramDescriptor);
					createGenericDescriptor = false;
				}
				else if (annotation instanceof CacheCtx)
				{
					final CacheCtxParamDescriptor paramDescriptor = new CacheCtxParamDescriptor(parameterType, parameterIndex, annotation);
					descriptorsBuilder.add(paramDescriptor);
					createGenericDescriptor = false;
				}
				else if (annotation instanceof CacheReloadIfTrue)
				{
					final CacheReloadIfTrueParamDescriptor paramDescriptor = new CacheReloadIfTrueParamDescriptor(parameterType, parameterIndex, annotation);
					descriptorsBuilder.add(paramDescriptor);
					createGenericDescriptor = false;
				}
				else if (annotation instanceof CacheModelId)
				{
					final CacheModelIdParamDescriptor paramDescriptor = new CacheModelIdParamDescriptor(parameterType, parameterIndex, annotation);
					descriptorsBuilder.add(paramDescriptor);
					createGenericDescriptor = false;
				}
				else if (annotation instanceof CacheModel)
				{
					descriptorsBuilder.add(new CacheCtxParamDescriptor(parameterType, parameterIndex, annotation));
					descriptorsBuilder.add(new CacheTrxParamDescriptor(parameterType, parameterIndex, annotation));
					descriptorsBuilder.add(new CacheModelIdParamDescriptor(parameterType, parameterIndex, annotation));
					createGenericDescriptor = false;
				}
			}

			//
			// Create a generic descriptor, if no other descriptors were created
			if (createGenericDescriptor)
			{
				descriptorsBuilder.add(GenericParamDescriptor.of(parameterType, parameterIndex, parameterAnnotations));
			}
		}

		//
		// Caching part: Target PO KeyProperties
		{
			final TargetPOKeyPropertiesPartDescriptor descriptor = TargetPOKeyPropertiesPartDescriptor.createIfApplies(method, cachedAnnotation);
			if (descriptor != null)
			{
				descriptorsBuilder.add(descriptor);
			}
		}

		return descriptorsBuilder.build();
	}

	static boolean containsAnnotationType(final List<Annotation> annotations, final Class<? extends Annotation> annotationType)
	{
		if (annotations == null || annotations.isEmpty())
		{
			return false;
		}

		for (final Annotation annotation : annotations)
		{
			if (annotationType.isAssignableFrom(annotation.getClass()))
			{
				return true;
			}
		}

		return false;
	}

	private static int extractExpireMinutes(Cached cachedAnnotation)
	{
		int expireMinutes = cachedAnnotation.expireMinutes();
		if (expireMinutes == Cached.EXPIREMINUTES_Never)
		{
			return Cached.EXPIREMINUTES_Never;
		}
		else if (expireMinutes <= 0)
		{
			return DEFAULT_CacheExpireMinutes;
		}
		else
		{
			return expireMinutes;
		}
	}

	public CacheKeyBuilder createKeyBuilder(final Object targetObject, final Object[] methodArgs)
	{
		final Class<?> methodDeclaringClass = method.getDeclaringClass();
		final Object targetObjToUse = staticMethod ? methodDeclaringClass : targetObject;

		final CacheKeyBuilder keyBuilder = new CacheKeyBuilder();

		//
		// Key: Method signature
		// NOTE: avoid adding Class/Field/Method etc to key => would lead to ClassLoader(s) memory leaks/fucked-up
		keyBuilder.add(methodDeclaringClass.getName());
		keyBuilder.add(method.getName());
		keyBuilder.add(method.getReturnType().getName());

		for (final ICachedMethodPartDescriptor descriptor : descriptors)
		{
			descriptor.extractKeyParts(keyBuilder, targetObjToUse, methodArgs);
			if (keyBuilder.isSkipCaching())
			{
				return keyBuilder;
			}
		}

		return keyBuilder;
	}

	@NonNull
	private CCache<ArrayKey, Object> createCCache()
	{
		return CCache.<ArrayKey, Object>builder()
				.cacheName(cacheName)
				.initialCapacity(DEFAULT_CacheInitialCapacity)
				.expireMinutes(expireMinutes)
				.additionalLabels(ADDITIONAL_CACHE_LABELS)
				.build();
	}

	/**
	 * Callable used to create method level cache container.
	 */
	public Callable<CCache<ArrayKey, Object>> createCCacheCallable() {return this::createCCache;}
}
