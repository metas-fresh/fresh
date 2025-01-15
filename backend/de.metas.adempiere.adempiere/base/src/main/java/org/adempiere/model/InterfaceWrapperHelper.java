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

package org.adempiere.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.model.IModelCacheService;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.impl.NullModelTranslationMap;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.model.util.IModelCopyHelper;
import org.adempiere.ad.model.util.ModelCopyHelper;
import org.adempiere.ad.persistence.IModelClassInfo;
import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.ad.persistence.ModelClassIntrospector;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.wrapper.CompositeInterfaceWrapperHelper;
import org.adempiere.ad.wrapper.GridTabInterfaceWrapperHelper;
import org.adempiere.ad.wrapper.IInterfaceWrapperHelper;
import org.adempiere.ad.wrapper.POInterfaceWrapperHelper;
import org.adempiere.ad.wrapper.POJOInterfaceWrapperHelper;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * This class is heavily used throughout metasfresh. To understand what it's all about see the javadoc of {@link #create(Object, Class)}.
 * It internally relates on a {@link CompositeInterfaceWrapperHelper} which in turn supports all the types that are supported by this class.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@UtilityClass
public class InterfaceWrapperHelper
{
	private static final Logger logger = LogManager.getLogger(InterfaceWrapperHelper.class);

	private static final CompositeInterfaceWrapperHelper helpers = new CompositeInterfaceWrapperHelper()
			.addFactory(new POInterfaceWrapperHelper())
			.addFactory(new GridTabInterfaceWrapperHelper())
			.addFactory(new POJOInterfaceWrapperHelper());

	public static final String COLUMNNAME_IsActive = "IsActive";
	public static final String COLUMNNAME_Value = "Value";
	public static final String COLUMNNAME_Name = "Name";
	public static final String COLUMNNAME_DocumentNo = "DocumentNo";
	public static final String COLUMNNAME_Description = "Description";

	public static final String COLUMNNAME_Created = "Created";
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";
	public static final String COLUMNNAME_Updated = "Updated";
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	private static POJOLookupMap getInMemoryDatabaseForModel(final Class<?> modelClass)
	{
		return POJOLookupMap.getInMemoryDatabaseForModel(modelClass);
	}

	private static POJOLookupMap getInMemoryDatabaseForTableName(final String tableName)
	{
		return POJOLookupMap.getInMemoryDatabaseForTableName(tableName);
	}

	private static boolean isInMemoryDatabaseOnly()
	{
		return org.compiere.Adempiere.isUnitTestMode();
	}

	public static void registerHelper(final IInterfaceWrapperHelper helper)
	{
		helpers.addFactory(helper);
	}

	/**
	 * Creates a new instance of given object using same context and trxName as <code>contextProvider</code>.
	 * <p>
	 * <b>IMPORTANT:</b> if the given contextProvider has the trxName <code>null</code>, then the new instance's trxName will be {@link ITrx#TRXNAME_ThreadInherited}!
	 * <p>
	 * The method invokes {@link #newInstance(Class, Object, boolean)} with <code>useCLientOrgFromProvider = true</code>.
	 * <p>
	 *
	 * @param contextProvider any object that carries a context (e.g. a PO, a wrapped PO, GridTab, a wrapped GridTab etc)
	 * @return new instance
	 */
	public static <T> T newInstance(final Class<T> cl, final Object contextProvider)
	{
		return newInstance(cl, contextProvider, true); // useClientOrgFromProvider = true
	}

	/**
	 * Creates a new instance of the given object using same context and trxName as <code>contextProvider</code>
	 *
	 * @param contextProvider          any object that carries a context (e.g. a PO, a wrapped PO, GridTab, a wrapped GridTab etc)<br>
	 *                                 <p>
	 *                                 IMPORTANT:</b> If contextProvider's transaction name is NULL and we have a thread inherited transaction, then use that one,
	 *                                 <i>if</i> the given <code>contextProvider</code> permits it. See {@link IContextAware#isAllowThreadInherited()}.
	 * @param useClientOrgFromProvider if {@code true}, then the context used to create the new instance will have the {@code contextProvider}'s {@code AD_Client_ID} and {@code AD_Org_ID} as
	 *                                 {@code #AD_Client_ID} resp. {@code #clone().AD_Org_ID}.
	 * @return new instance
	 */
	public static <T> T newInstance(final Class<T> cl,
									@NonNull final Object contextProvider,
									final boolean useClientOrgFromProvider)
	{
		final Properties ctx = getCtx(contextProvider, useClientOrgFromProvider);
		//
		// Get transaction name from contextProvider.
		// If contextProvider's transaction name is NULL and we have a thread inherited transaction, then let's use that one
		final ITrxManager trxManager = getTrxManager();
		String trxName = getTrxName(contextProvider);
		if (trxManager.isNull(trxName))
		{
			final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(contextProvider);
			if (ctxAware.isAllowThreadInherited())  // it's ok to check and if there is a thread inherited trx, use that.
			{
				final ITrx trxThreadInherited = trxManager.get(ITrx.TRXNAME_ThreadInherited, OnTrxMissingPolicy.ReturnTrxNone);
				if (trxThreadInherited != null)
				{
					trxName = ITrx.TRXNAME_ThreadInherited;
				}
			}
		}
		return create(ctx, cl, trxName);
	}

	/**
	 * Convenient method to create a new instance of given class, using current context and current transaction.
	 */
	public static <T> T newInstance(final Class<T> modelClass)
	{
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		return create(ctx, modelClass, trxName);
	}

	/**
	 * Convenient method to create a new instance of given class, using current context and no transaction.
	 */
	public static <T> T newInstanceOutOfTrx(final Class<T> modelClass)
	{
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;
		return create(ctx, modelClass, trxName);
	}

	/**
	 * This method is heavily used throughout metasfresh and allows us to do the following things:
	 * <ul>
	 * <li>Create interfaces from {@link GridTab}s (see {@link GridTabWrapper}), {@link PO}'s (see {@link POWrapper}) and POJOs (see {@link POWrapper}).<br>
	 * This way, a developer can create business logic that deals with e.g. <code>I_C_Order</code>s and does not have to care whether the actual underlying project is a <code>GridTab</code> or a <code>PO</code>.<br>
	 * She can therefore for example create one method that makes some validations and call that method from both a callout and a model interceptor/validator.<br>
	 * In both cases this method can create the required interface from the underlying <code>GridTab</code> or <code>PO</code> instance</li>
	 * <li>Logically separate columns and fields that belong to different modules.<br>
	 * An example: we have one module/project for handling units and another one for EDI. Both of them "own" different columns of the <code>C_OrderLine</code> table,<br>
	 * but they do not have a functional relationship (I doubt it, but let's say so for the sake of argument).<br>
	 * The goal is to avoid mixing the model definition and business logic. To achieve this, we have two different project specific <code>I_C_OrderLine</code> interfaces.<br>
	 * One interface is in the handling units project, where we declare the HU-related columns, getters and setters, and the other interface is in the EDI project, where we declare the EDI-related column names, getters and setters.<br>
	 * Both interfaces extend the "original" <code>org.compiere.model.I_C_OrderLine</code> interface, so they have the generic properties like <code>QtyOrdered</code>, <code>M_Product_ID</code> etc. <b>plus</b> the project specific ones.<br>
	 * The magic is once again done by this method, which returns an instance of the required <code>I_C_OrderLine</code> interface for the underlying <code>GridTab</code> or <code>PO</code>,
	 * with only the properties that are declared by that interface.<br>
	 * Also note that the interface passed to this method does not necessarily have to implement a "generic" interface from <code>org.compiere.model</code>.<br>
	 * Instead, we can also use some interface like <code>IProductAware</code> that just declares product related properties.
	 * </li>
	 * </ul>
	 *
	 * @param model the underlying {@link PO}, {@link GridTab} or POJO for which we need an instance of <code>cl</code>
	 * @param cl    the interface we need an instance of
	 * @return an instance of <code>cl</code> which actually wraps <code>model</code> or <code>null</code> if model was <code>null</code>
	 */
	public static <T> T create(@Nullable final Object model, final Class<T> cl)
	{
		final boolean useOldValues = false;
		return create(model, cl, useOldValues);
	}

	/**
	 * See {@link #create(Object, Class)} for additional infos.
	 *
	 * @param modelClass   model class
	 * @param useOldValues <ul>
	 *                     <li>true if old values shall be used
	 *                     <li>false if model's old values flag shall BE PRESERVED. i.e. if it was "true" we shall use old values, if it was "false" we shall NOT use old values.
	 *                     </ul>
	 * @deprecated Because this method is tricky and we consider to make it private, please use:
	 * <ul>
	 * <li>{@link #create(Object, Class)}
	 * <li>or {@link #createOld(Object, Class)}
	 * </ul>
	 */
	@Deprecated
	public static <T> T create(
			@Nullable final Object model,
			@NonNull final Class<T> modelClass,
			final boolean useOldValues)
	{
		if (model == null)
		{
			return null;
		}
		else if (modelClass.isInstance(model) && !useOldValues)
		{
			@SuppressWarnings("unchecked") final T modelCasted = (T)model;
			return modelCasted;
		}
		else
		{
			return helpers.wrap(model, modelClass, useOldValues);
		}
	}

	/**
	 * Wraps given the <code>model</code> and uses the <b>old</b> values for all model getters.
	 * See {@link #create(Object, Class)} for more informations.
	 */
	public static <T> T createOld(final Object model, final Class<T> cl)
	{
		final boolean useOldValues = true;
		return create(model, cl, useOldValues);
	}

	public static <T> T create(final Properties ctx, final Class<T> cl, @Nullable final String trxName)
	{
		if (getInMemoryDatabaseForModel(cl) != null)
		{
			return POJOWrapper.create(ctx, cl, trxName);
		}
		return POWrapper.create(ctx, cl, trxName);
	}

	/**
	 * Loads the record with the given <code>id</code>.
	 * Also see {@link #create(Object, Class)} for more informations.
	 * <p>
	 * Note: if you want to load a record from <code>(AD_Table_ID, Reference_ID)</code>,<br>
	 * then it's probably better to use e.g. {@link org.adempiere.util.lang.impl.TableRecordReference#of(int, int)}.
	 */
	public static <T> T create(final Properties ctx, final int id, @NonNull final Class<T> cl, @Nullable final String trxName)
	{
		if (getInMemoryDatabaseForModel(cl) != null)
		{
			return POJOWrapper.create(ctx, id, cl, trxName);
		}
		return POWrapper.create(ctx, id, cl, trxName);
	}

	/**
	 * Loads the record with the given <code>id</code>. Similar to {@link #create(Object, Class)}, but explicitly specifies the table name.<br>
	 * This is useful in case the table name can't be deduced from the given <code>cl</code>.
	 * <p>
	 * Notes:
	 * <li>this method might or might not benefit from caching, depending on how {@link IModelCacheService} was configured.
	 * <li>if you want to load a record from <code>(AD_Table_ID, Reference_ID)</code>,<br>
	 * then it's probably better to use {@link org.adempiere.util.lang.impl.TableRecordReference#of(int, int)}.
	 */
	public static <T> T create(final Properties ctx, final String tableName, final int id, final Class<T> cl, final String trxName)
	{
		if (getInMemoryDatabaseForTableName(tableName) != null)
		{
			return POJOWrapper.create(ctx, tableName, id, cl, trxName);
		}
		return POWrapper.create(ctx, tableName, id, cl, trxName);
	}

	public static <T> T loadOutOfTrx(@NonNull final RepoIdAware id, final Class<T> modelClass)
	{
		return loadOutOfTrx(id.getRepoId(), modelClass);
	}

	/**
	 * Loads given model, out of transaction.
	 * NOTE: to be used, mainly for loading master data models.
	 * NOTE: when we are where we want to be, this will only be invoked from repositories!
	 *
	 * @param id model's ID
	 * @return loaded model
	 */
	public static <T> T loadOutOfTrx(final int id, final Class<T> modelClass)
	{
		return create(Env.getCtx(), id, modelClass, ITrx.TRXNAME_None);
	}

	public static <T> T load(@NonNull final RepoIdAware id, @NonNull final Class<T> modelClass)
	{
		return load(id.getRepoId(), modelClass);
	}

	public static <T> T loadNotNull(@NonNull final RepoIdAware id, @NonNull final Class<T> modelClass)
	{
		final T record = load(id, modelClass);
		if (record == null)
		{
			throw new AdempiereException("No " + modelClass.getSimpleName() + " found for " + id);
		}
		return record;
	}

	/**
	 * Loads given model, using thread inherited transaction.
	 *
	 * @param id model's ID
	 * @return loaded model
	 */
	public static <T> T load(final int id, @NonNull final Class<T> modelClass)
	{
		return create(Env.getCtx(), id, modelClass, ITrx.TRXNAME_ThreadInherited);
	}

	public static <T> T loadOrNew(@Nullable final RepoIdAware id, final Class<T> modelClass)
	{
		return id == null ? newInstance(modelClass) : load(id.getRepoId(), modelClass);
	}

	@Deprecated
	public static <T> T loadOrNew(@Nullable final RepoIdAware id, final Class<T> modelClass, final Object contextProvider)
	{
		return id == null ? newInstance(modelClass, contextProvider) : load(id.getRepoId(), modelClass);
	}

	public static <T> List<T> loadByIds(final Set<Integer> ids, final Class<T> modelClass)
	{
		return loadByIds(ids, modelClass, ITrx.TRXNAME_ThreadInherited, UnaryOperator.identity());
	}

	public static <T> List<T> loadByRepoIdAwares(@NonNull final Set<? extends RepoIdAware> repoIdAwares, final Class<T> modelClass)
	{
		final ImmutableSet<Integer> ids = RepoIdAwares.asRepoIdsSet(repoIdAwares);
		return loadByIds(ids, modelClass, ITrx.TRXNAME_ThreadInherited, UnaryOperator.identity());
	}

	public static <RT, MT> List<MT> loadByRepoIdAwares(@NonNull final Set<? extends RepoIdAware> repoIdAwares, @NonNull final Class<RT> modelClass, @NonNull Function<RT, MT> modelMapper)
	{
		final ImmutableSet<Integer> ids = RepoIdAwares.asRepoIdsSet(repoIdAwares);
		return loadByIds(ids, modelClass, ITrx.TRXNAME_ThreadInherited, modelMapper);
	}

	public static <T> List<T> loadByIdsOutOfTrx(final Set<Integer> ids, final Class<T> modelClass)
	{
		return loadByIds(ids, modelClass, ITrx.TRXNAME_None, UnaryOperator.identity());
	}

	public static <T> List<T> loadByRepoIdAwaresOutOfTrx(@NonNull final Collection<? extends RepoIdAware> repoIdAwares, final Class<T> modelClass)
	{
		final ImmutableSet<Integer> ids = RepoIdAwares.asRepoIdsSet(repoIdAwares);
		return loadByIds(ids, modelClass, ITrx.TRXNAME_None, UnaryOperator.identity());
	}

	private static <RT, MT> List<MT> loadByIds(final Set<Integer> ids, final Class<RT> modelClass, final String trxName, final Function<RT, MT> modelMapper)
	{
		if (getInMemoryDatabaseForModel(modelClass) != null)
		{
			return POJOWrapper.loadByIds(ids, modelClass, trxName, modelMapper);
		}
		return POWrapper.loadByIds(ids, modelClass, trxName, modelMapper);
	}

	/**
	 * Converts given list to target type by calling {@link #create(Object, Class)} for each item.
	 *
	 * @param list  list to be converted
	 * @param clazz target model class
	 * @return converted list to given model
	 */
	public static <T, S> List<T> createList(final List<S> list, final Class<T> clazz)
	{
		if (list == null)
		{
			return null;
		}

		return list.stream()
				.map(item -> create(item, clazz))
				.collect(Collectors.toList());
	}

	public static <T, S> List<T> wrapToImmutableList(final List<S> list, final Class<T> clazz)
	{
		if (list == null || list.isEmpty())
		{
			return ImmutableList.of();
		}

		return list.stream()
				.map(model -> create(model, clazz))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static void refresh(final Object model)
	{
		final boolean discardChanges = false;
		refresh(model, discardChanges);
	}

	/**
	 * Refresh all models that were given using {@link #refresh(Object)}.
	 * <p>
	 * NOTE: developers are encouraged to use this method because here we would be able to do more optimizations.
	 */
	public static <T> void refreshAll(final Iterable<T> models)
	{
		if (models == null)
		{
			return;
		}

		for (final Object model : models)
		{
			refresh(model);
		}
	}

	/**
	 * Like {@link #refreshAll(Iterable)}, but uses {@link #refresh(Object, String)} instead.
	 */
	public static <T> void refreshAll(final Iterable<T> models, final String trxName)
	{
		if (models == null)
		{
			return;
		}

		for (final Object model : models)
		{
			refresh(model, trxName);
		}
	}

	/**
	 * Mark the model as staled. It means that it needs to be reloaded first in case some values need to be retrieved.
	 * <p>
	 * NOTE: this method is currently refreshing the model right away, because we did not implement it.
	 */
	public static void markStaled(final Object model)
	{
		// TODO: we need to implement it
		refresh(model);
	}

	/**
	 * Reload given model from database.
	 *
	 * @param discardChanges hint for actual handler to tell that if there are any unsaved changes, it's ok to discard them.
	 */
	public static void refresh(final Object model, final boolean discardChanges)
	{
		helpers.refresh(model, discardChanges);
	}

	/**
	 * Refreshes the given model, and if the model is handled by {@link POWrapper} or {@link POJOWrapper}, then uses the given <code>trxName</code>.
	 */
	public static void refresh(final Object model, final String trxName)
	{
		helpers.refresh(model, trxName);
	}

	public static void setTrxName(final Object model, @Nullable final String trxName)
	{
		final boolean ignoreIfNotHandled = false;
		setTrxName(model, trxName, ignoreIfNotHandled);
	}

	/**
	 * @param ignoreIfNotHandled <code>true</code> and the given model can not be handled (no PO, GridTab etc), then don't throw an exception,
	 * @throws AdempiereException if the given model is neither handled by {@link POWrapper} nor by {@link POJOWrapper} and ignoreIfNotHandled is <code>false</code>.
	 */
	public static void setTrxName(final Object model, final String trxName, final boolean ignoreIfNotHandled)
	{
		helpers.setTrxName(model, trxName, ignoreIfNotHandled);
	}

	private static ITrxManager getTrxManager()
	{
		// TODO: consider using as a class field because this method is/will accessed many times
		return Services.get(ITrxManager.class);
	}

	/**
	 * Sets trxName to {@link ITrx#TRXNAME_ThreadInherited}.
	 */
	public static void setThreadInheritedTrxName(final Object model)
	{
		setTrxName(model, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Set current thread inerited transaction name to given models.
	 */
	public static void setThreadInheritedTrxName(final Collection<?> models)
	{
		if (models == null || models.isEmpty())
		{
			return;
		}

		for (final Object model : models)
		{
			setTrxName(model, ITrx.TRXNAME_ThreadInherited);
		}
	}

	public static void saveAll(@NonNull final Collection<?> models)
	{
		if (models.isEmpty())
		{
			return;
		}

		models.forEach(InterfaceWrapperHelper::saveRecord);
	}

	/**
	 * Does the same as {@link #save(Object)},
	 * but this method can be static-imported into repository implementations which usually have their own method named "save()".
	 */
	public static void saveRecord(final Object model)
	{
		save(model);
	}

	public static void save(final Object model)
	{
		final Object modelToSave = extractModelToSave(model);

		if (modelToSave == null)
		{
			final AdempiereException ex = new AdempiereException("Saving null model ignored. Possible development issue. Ignored.");
			logger.warn(ex.getLocalizedMessage(), ex);
		}
		else if (GridTabWrapper.isHandled(modelToSave))
		{
			GridTabWrapper.save(modelToSave);
		}
		else if (POWrapper.isHandled(modelToSave))
		{
			POWrapper.save(modelToSave);
		}
		else if (POJOWrapper.isHandled(modelToSave))
		{
			POJOWrapper.save(modelToSave);
		}
		else
		{
			final AdempiereException ex = new AdempiereException("Model not handled: " + modelToSave + "(class=" + modelToSave.getClass() + "). Ignored.");
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(ex);
			logger.warn("Could not save the given model; message={}; AD_Issue_ID={}", ex.getLocalizedMessage(), issueId);
		}
	}

	public static void setCtx(@NonNull final Object model, @NonNull final Properties ctx)
	{
		if (POWrapper.isHandled(model))
		{
			POWrapper.setCtx(model, ctx);
		}
		else if (POJOWrapper.isHandled(model))
		{
			POJOWrapper.setCtx(model, ctx);
		}
		else
		{
			final AdempiereException ex = new AdempiereException("Model not handled: " + model + "(class=" + model.getClass() + "). Ignored.");
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(ex);
			logger.warn("Could not set ctx to given model; message={}; AD_Issue_ID={}", ex.getLocalizedMessage(), issueId);
		}
	}

	private static Object extractModelToSave(final Object model)
	{
		final Object modelToSave;
		if (model instanceof IModelWrapper)
		{
			modelToSave = ((IModelWrapper)model).getModel();
		}
		else
		{
			modelToSave = model;
		}
		return modelToSave;
	}

	public static void save(final Object model, @Nullable final String trxName)
	{
		if (model == null)
		{
			final AdempiereException ex = new AdempiereException("Saving null model ignored. Possible development issue. Ignored.");
			logger.warn(ex.getLocalizedMessage(), ex);
		}

		final String trxNameOld = getTrxName(model);
		setTrxName(model, trxName);
		try
		{
			save(model);
		}
		finally
		{
			// Restore the trxName
			setTrxName(model, trxNameOld);
		}
	}

	/**
	 * Get context from model.
	 */
	public static Properties getCtx(@Nullable final Object model)
	{
		return getCtx(model, false);
	}

	/**
	 * Get context from model and setting in context AD_Client_ID and AD_Org_ID according to the model if useClientOrgFromModel is true
	 *
	 * @param model                 may be null
	 * @param useClientOrgFromModel ignored, unless the given model is {@link ModelContextAware} or just a "normal" model. See {@link #getCtx(Object, boolean)}
	 */
	public static Properties getCtx(
			@Nullable final Object model,
			final boolean useClientOrgFromModel)
	{
		if (model == null)
		{
			return Env.getCtx();
		}
		else if (model instanceof ModelContextAware)
		{
			// we have an IContextAware that is based on a model, so we can act on the value of the given useClientOrgFromModel
			return ((ModelContextAware)model).getCtx(useClientOrgFromModel);
		}
		else if (model instanceof IContextAware)
		{
			return ((IContextAware)model).getCtx();
		}
		else if (model instanceof Properties)
		{
			return (Properties)model; // this *is* already the ctx
		}
		else
		{
			return helpers.getCtx(model, useClientOrgFromModel);
		}
	}

	/**
	 * IMPORTANT: only call with <b>model interfaces</b> such as {@code I_AD_Table}, {@code C_Order} (legacy classes like `MProduct` and {@link IContextAware}s will also work) and the like.
	 * Despite the parameter type being "Object" it does not work with all objects.
	 */
	public static String getTrxName(@Nullable final Object model)
	{
		final boolean ignoreIfNotHandled = false;
		return getTrxName(model, ignoreIfNotHandled);
	}

	/**
	 * IMPORTANT: only call with <b>model interfaces</b> such as {@code I_AD_Table}, {@code C_Order} (legacy classes like `MProduct` and {@link IContextAware}s will also work) and the like.
	 * Despite the parameter type being "Object" it does not work with all objects.
	 *
	 * @param ignoreIfNotHandled if <code>true</code> and the given model can not be handeled (no PO, GridTab etc), then just return {@link ITrx#TRXNAME_None} without throwing an exception.
	 */
	@Nullable
	public static String getTrxName(@Nullable final Object model, final boolean ignoreIfNotHandled)
	{
		if (model == null)
		{
			return ITrx.TRXNAME_None;
		}
		else if (model instanceof IContextAware)
		{
			return ((IContextAware)model).getTrxName();
		}
		else if (model instanceof Properties)
		{
			return ITrx.TRXNAME_None;
		}
		else
		{
			return helpers.getTrxName(model, ignoreIfNotHandled);
		}
	}

	public static IContextAware getContextAware(final Object model)
	{
		if (model instanceof IContextAware)
		{
			return (IContextAware)model;
		}
		else
		{
			return new ModelContextAware(model);
		}
	}

	/**
	 * Does the same as {@link #delete(Object)},
	 * but this method can be static-imported into repository implementations which usually have their own method named "delete()".
	 */
	public static void deleteRecord(@NonNull final Object model)
	{
		delete(model);
	}

	public static void delete(@NonNull final Object model)
	{
		final boolean failIfProcessed = true;
		delete(model, failIfProcessed);
	}

	public static void delete(@NonNull final Object model, final boolean failIfProcessed)
	{
		if (POWrapper.isHandled(model))
		{
			POWrapper.delete(model, failIfProcessed);
		}
		else if (POJOWrapper.isHandled(model))
		{
			POJOWrapper.delete(model, failIfProcessed);
		}
		else
		{
			throw new IllegalStateException("delete is not supported for " + model);
		}
	}

	public static void deleteAll(@NonNull final Collection<?> models, final boolean failIfProcessed)
	{
		if (models.isEmpty())
		{
			return;
		}

		models.forEach(model -> InterfaceWrapperHelper.delete(model, failIfProcessed));
	}

	public static void deleteAll(@NonNull final Collection<?> models)
	{
		if (models.isEmpty())
		{
			return;
		}

		models.forEach(InterfaceWrapperHelper::delete);
	}

	/**
	 * @return underlying {@link PO} or null
	 */
	public static <T extends PO> T getPO(final Object model)
	{
		final boolean strict = false;
		return helpers.getPO(model, strict);
	}

	public static <T extends PO> T getStrictPO(final Object model)
	{
		final boolean strict = true;
		return helpers.getPO(model, strict);
	}

	public static int getId(@Nullable final Object model)
	{
		if (model == null)
		{
			return -1;
		}
		else if (model instanceof ITableRecordReference)
		{
			return ((ITableRecordReference)model).getRecord_ID();
		}
		else
		{
			return helpers.getId(model);
		}
	}

	public static boolean isActive(@NonNull final Object model)
	{
		final boolean throwExIfColumnNotFound = false;
		final boolean useOverrideColumnIfAvailable = false;
		final Object valueObj = getValue(model, COLUMNNAME_IsActive, throwExIfColumnNotFound, useOverrideColumnIfAvailable);
		return StringUtils.toBoolean(valueObj);
	}

	/**
	 * Introducing this exception to be thrown instead of ADempiereException. Reason: It's a pain if you have a breakpoint on "AdempiereException" and the debugger stops every 2 seconds because
	 * InterfaceWrapperHelper throws it.
	 */
	/* package */
	@SuppressWarnings("serial")
	static class MissingTableNameException extends AdempiereException
	{
		private static MissingTableNameException notFound(final Class<?> modelClass)
		{
			return new MissingTableNameException("@NotFound@ @TableName@ (class=" + modelClass + ")");
		}

		private static MissingTableNameException notFound(final Class<?> modelClass, final String fallbackTableName)
		{
			return new MissingTableNameException("@NotFound@ @TableName@ (class=" + modelClass + ", fallbackTableName=" + fallbackTableName + ")");
		}

		private static MissingTableNameException notMatching(final Class<?> modelClass, final String modelClassTableName, final String expectedTableName)
		{
			return new MissingTableNameException("modelClass's table name is not matching the expected table name:"
					+ "\n modelClass=" + modelClass
					+ "\n modelClassTableName=" + modelClassTableName
					+ "\n expectedTableName=" + expectedTableName);
		}

		private MissingTableNameException(final String message)
		{
			super(message);
		}
	}

	private static IModelClassInfo getModelClassInfoOrNull(@Nullable final Class<?> clazz)
	{
		return ModelClassIntrospector
				.getInstance()
				.getModelClassInfo(clazz);
	}

	/**
	 * Checks static variable "Table_Name" of given interface and returns it's content.
	 *
	 * @return tableName associated with given interface
	 * @throws AdempiereException if "Table_Name" static variable is not defined or is not accessible
	 */
	@NonNull
	public static String getTableName(final Class<?> modelClass) throws AdempiereException
	{
		final String modelClassTableName = getTableNameOrNull(modelClass);
		if (modelClassTableName == null)
		{
			throw MissingTableNameException.notFound(modelClass);
		}
		return modelClassTableName;
	}

	/**
	 * Checks static variable "Table_Name" of given interface and returns it's content.
	 *
	 * @return tableName associated with given interface or null if interface has no Table_Name
	 */
	public static String getTableNameOrNull(final Class<?> clazz)
	{
		final IModelClassInfo modelClassInfo = getModelClassInfoOrNull(clazz);
		if (modelClassInfo == null)
		{
			return null;
		}
		return modelClassInfo.getTableName();
	}

	/**
	 * Extracts the table name from given modelClass.
	 * If the modelClass does not have a table name it will return <code>expectedTableName</code> if that's not null.
	 * If the modelClass has a table name but it's not matching the expectedTableName (if not null) an exception will be thrown.
	 * If the modelClass does not hava a table name and <code>expectedTableName</code> is null an exception will be thrown.
	 *
	 * @return model table name; never returns null
	 */
	public static String getTableName(final Class<?> modelClass, @Nullable final String expectedTableName)
	{
		final String modelClassTableName = getTableNameOrNull(modelClass);

		// Case: there is no expected/default table name
		// => fail if modelClass has no table name either.
		if (expectedTableName == null)
		{
			if (modelClassTableName == null)
			{
				throw MissingTableNameException.notFound(modelClass, expectedTableName);
			}

			return modelClassTableName;
		}
		// Case: there is an expected/default table name
		else
		{
			// Sub-case: no model class table name => return the default/expected one
			if (modelClassTableName == null)
			{
				return expectedTableName;
			}
			// Sub-case: model class table name matches the expected one => perfect, return it
			else if (modelClassTableName.equals(expectedTableName))
			{
				return modelClassTableName;
			}
			// Sub-case: model class table name DOES NOT match the expected one => fail
			else
			{
				throw MissingTableNameException.notMatching(modelClass, modelClassTableName, expectedTableName);
			}
		}
	}

	public static boolean isModelInterface(@Nullable final Class<?> modelClass)
	{
		if (modelClass == null)
		{
			return false;
		}
		final IModelClassInfo modelClassInfo = getModelClassInfoOrNull(modelClass);
		if (modelClassInfo == null)
		{
			return false;
		}
		return modelClassInfo.getTableName() != null;
	}

	public static int getTableId(@NonNull final Class<?> clazz)
	{
		final String tableName = getTableName(clazz);
		return TableIdsCache.instance.getTableId(tableName)
				.map(AdTableId::getRepoId)
				.orElse(-1);
	}

	public static AdTableId getAdTableId(@NonNull final Class<?> clazz)
	{
		final String tableName = getTableName(clazz);
		return TableIdsCache.instance.getTableId(tableName)
				.orElseThrow(() -> new AdempiereException("No AD_Table_ID found for " + tableName));
	}

	/**
	 * @return AD_Table_ID or <code>-1</code>.
	 */
	public static int getTableIdOrNull(final Class<?> clazz)
	{
		final String tableName = getTableNameOrNull(clazz);
		if (tableName == null)
		{
			return -1;
		}

		return TableIdsCache.instance.getTableId(tableName)
				.map(AdTableId::getRepoId)
				.orElse(-1);
	}

	public static String getKeyColumnName(final Class<?> clazz)
	{
		final String tableName = getTableName(clazz);
		return getKeyColumnName(tableName);
	}

	/**
	 * Returns <code>tableName + "_ID"</code>.
	 * <p>
	 * Hint: if you need a method that does not just assume, but actually verifies the key column name, use {@link de.metas.adempiere.service.IColumnBL#getSingleKeyColumn(String)}.
	 */
	public static String getKeyColumnName(final String tableName)
	{
		// NOTE: we assume the key column name is <TableName>_ID
		return tableName + "_ID";
	}

	public static String getModelKeyColumnName(final Object model)
	{
		final String tableName = getModelTableName(model);
		return getKeyColumnName(tableName);
	}

	/**
	 * Get Table_ID of wrapped model. If model is null, an exception will be thrown
	 *
	 * @return Table_ID
	 * @throws AdempiereException if model is null or model is not supported
	 */
	public static int getModelTableId(final Object model)
	{
		final String modelTableName = getModelTableName(model);
		return TableIdsCache.instance.getTableId(modelTableName)
				.map(AdTableId::getRepoId)
				.orElse(-1);
	}

	/**
	 * Get TableName of wrapped model. If model is null or is not supported, an exception will be thrown.
	 *
	 * @return table name
	 * @throws AdempiereException if model is null or model is not supported
	 */
	public static String getModelTableName(@NonNull final Object model)
	{
		final String modelTableName = getModelTableNameOrNull(model);
		if (modelTableName == null)
		{
			throw new AdempiereException("Cannot get TableName from model: " + model);
		}

		return modelTableName;
	}

	/**
	 * Get TableName of wrapped model.
	 * <p>
	 * This method returns null when:
	 * <ul>
	 * <li>model is null
	 * <li>model is not supported
	 * </ul>
	 *
	 * @param model persistent object, wrapped model, {@link ITableRecordReference}
	 * @return table name or null
	 */
	public static String getModelTableNameOrNull(final Object model)
	{
		if (model == null)
		{
			return null;
		}
		else if (model instanceof ITableRecordReference)
		{
			return ((ITableRecordReference)model).getTableName();
		}
		else
		{
			return helpers.getModelTableNameOrNull(model);
		}
	}

	public static void copyValues(final Object from, final Object to)
	{
		// 03710 we must honor IsCalculated unless we definitely know what we do. Otherwise all kind of problems will occur.
		final boolean honorIsCalculated = true;

		copyValues(from, to, honorIsCalculated);
	}

	public static void copyValues(final Object from, final Object to, final boolean honorIsCalculated)
	{
		if (POWrapper.isHandled(to))
		{
			final PO fromPO = getPO(from);
			final PO toPO = getPO(to);
			PO.copyValues(fromPO, toPO, honorIsCalculated);
		}
		else if (POJOWrapper.isHandled(from) && POJOWrapper.isHandled(to))
		{
			// NOTE: commented out because some tests are failing because of this
			// Check.assume(!honorIsCalculated, "honorIsCalculated=true not supported by {}", POJOWrapper.class);

			final POJOWrapper fromWrapper = POJOWrapper.getWrapper(from);
			final POJOWrapper toWrapper = POJOWrapper.getWrapper(to);
			toWrapper.copyFrom(fromWrapper);
		}
		else
		{
			throw new AdempiereException("Unsupported models: from=" + from + ", to=" + to);
		}
	}

	/**
	 * Checks if given columnName's value is <code>null</code>
	 *
	 * @return <code>true</code> if columnName's value is <code>null</code>
	 */
	public static boolean isNull(final Object model, final String columnName)
	{
		return helpers.isNull(model, columnName);
	}

	/**
	 * Checks if given columnName's value is <code>null</code> or (only in case it is a string!) empty.
	 *
	 * @return <code>true</code> if columnName's value is <code>null</code> or an empty string.
	 */
	public static boolean isNullOrEmpty(final Object model, final String columnName)
	{
		if (isNull(model, columnName))
		{
			return true;
		}

		final Object value = getValue(model, columnName).orElse(null);
		if (value instanceof String)
		{
			return Check.isEmpty((String)value);
		}

		return false;
	}

	public static boolean hasModelColumnName(final Object model, final String columnName)
	{
		Check.assumeNotNull(model, "model is not null");
		Check.assumeNotNull(columnName, "columnName is not null");

		return helpers.hasModelColumnName(model, columnName);
	}

	public static boolean hasColumnName(final Class<?> modelClass, final String columnName)
	{
		Check.assumeNotNull(modelClass, "modelClass is not null");
		Check.assumeNotNull(columnName, "columnName is not null");

		if (isInMemoryDatabaseOnly())
		{
			return POJOWrapper.hasColumnName(modelClass, columnName);
		}
		else
		{
			return POWrapper.hasColumnName(modelClass, columnName);
		}
	}

	public static Optional<ClientId> getClientId(final Object model)
	{
		final Object clientIdObj = getValue(model, "AD_Client_ID").orElse(null);
		if (clientIdObj == null)
		{
			return Optional.empty();
		}

		final int clientIdInt = NumberUtils.asInt(clientIdObj, -1);
		return ClientId.optionalOfRepoId(clientIdInt);
	}

	public static Optional<OrgId> getOrgId(final Object model)
	{
		final Object orgIdObj = getValue(model, "AD_Org_ID").orElse(null);
		if (orgIdObj == null)
		{
			return Optional.empty();
		}

		final int orgIdInt = NumberUtils.asInt(orgIdObj, -1);
		return OrgId.optionalOfRepoId(orgIdInt);
	}

	public static ClientAndOrgId getClientAndOrgId(@NonNull final Object model)
	{
		final ClientId clientId = getClientId(model).orElseThrow(() -> new AdempiereException("Cannot determine AD_Client_ID from " + model));
		final OrgId orgId = getOrgId(model).orElseThrow(() -> new AdempiereException("Cannot determine AD_Org_ID from " + model));
		return ClientAndOrgId.ofClientAndOrg(clientId, orgId);
	}

	public static <T> T getValueByColumnId(@NonNull final Object model, @NonNull final AdColumnId adColumnId)
	{
		if (GridTabWrapper.isHandled(model))
		{
			final GridTab gridTab = GridTabWrapper.getGridTab(model);
			for (final GridField field : gridTab.getFields())
			{
				if (field.getAD_Column_ID() == adColumnId.getRepoId())
				{
					@SuppressWarnings("unchecked") final T value = (T)field.getValue();
					return value;
				}
			}

			throw new AdempiereException("No field with AD_Column_ID=" + adColumnId + " found in " + gridTab + " for " + model);
		}
		else if (POWrapper.isHandled(model))
		{
			final PO po = POWrapper.getStrictPO(model);
			@SuppressWarnings("unchecked") final T value = (T)po.get_ValueOfColumn(adColumnId);
			return value;
		}
		// else if (POJOWrapper.isHandled(model))
		// {
		// final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		// // MColumn.getColumnName(ctx, AD_Column_ID)
		// }
		else
		{
			throw new AdempiereException("Model wrapping is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	public static <T> T getValueOrNull(@NonNull final Object model, final String columnName)
	{
		final boolean throwExIfColumnNotFound = false;
		final boolean useOverrideColumnIfAvailable = false;
		return getValue(model, columnName, throwExIfColumnNotFound, useOverrideColumnIfAvailable);
	}

	public static <T> Optional<T> getValue(@NonNull final Object model, final String columnName)
	{
		final boolean throwExIfColumnNotFound = true;
		final boolean useOverrideColumnIfAvailable = false;
		final T value = getValue(model, columnName, throwExIfColumnNotFound, useOverrideColumnIfAvailable);
		return Optional.ofNullable(value);
	}

	@NonNull
	public static <T> Optional<T> getValueOptional(@NonNull final Object model, final String columnName)
	{
		final boolean throwExIfColumnNotFound = false;
		final boolean useOverrideColumnIfAvailable = false;
		final T value = getValue(model, columnName, throwExIfColumnNotFound, useOverrideColumnIfAvailable);
		return Optional.ofNullable(value);
	}

	@NonNull
	public static <T extends RepoIdAware> Optional<T> getRepoIdOptional(@NonNull final Object model, @NonNull final String columnName, @NonNull final Function<Integer, T> idMapper)
	{
		return getValueOptional(model, columnName)
				.map(columnValue -> (Integer)columnValue)
				.map(idMapper);
	}

	/**
	 * Gets [columnName]_Override if the override column is available and not null, else column name value is returned.
	 *
	 * @return value of [columnName]_Override or [columnName]; <b>might return null</b>, so don't blindly use as int.
	 * @throws AdempiereException if neither the "normal" value nor the override value is available.
	 * @deprecated Favor using the actual getters. It's easier to trace/debug later.
	 */
	@Deprecated
	public static <T> T getValueOverrideOrValue(@NonNull final Object model, final String columnName)
	{
		final boolean throwExIfColumnNotFound = true;
		final boolean useOverrideColumnIfAvailable = true;
		return getValue(model, columnName, throwExIfColumnNotFound, useOverrideColumnIfAvailable);
	}

	private static <T> T getValue(@NonNull final Object model,
								  final String columnName,
								  final boolean throwExIfColumnNotFound,
								  final boolean useOverrideColumnIfAvailable)
	{
		return helpers.getValue(model, columnName, throwExIfColumnNotFound, useOverrideColumnIfAvailable);
	}

	public static <ModelType> ModelType getModelValue(@NonNull final Object model, final String columnName, final Class<ModelType> columnModelType)
	{
		if (POWrapper.isHandled(model))
		{
			return POWrapper.getModelValue(model, columnName, columnModelType);
		}
		else if (POJOWrapper.isHandled(model))
		{
			return POJOWrapper.getWrapper(model).getModelValue(columnName, columnModelType);
		}
		else
		{
			throw new AdempiereException("Model wrapping is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	/**
	 * Set value for given <code>columnName</code>.
	 * <p>
	 * If column was not found in <code>model</code> a warning will be logged but no exception will be thrown
	 *
	 * @return true if value was set
	 */
	public static boolean setValue(
			@NonNull final Object model,
			@NonNull final String columnName,
			@Nullable final Object value)
	{
		final boolean throwExIfColumnNotFound = false;
		return setValue(model, columnName, value, throwExIfColumnNotFound);
	}

	/**
	 * Set values for given <code>Map<columnName, value></code>.
	 * <p>
	 * If a column was not found in <code>model</code>, an exception will be thrown.
	 *
	 * @return true if all values were set
	 */
	public static boolean setValues(final Object model, final Map<String, Object> values)
	{
		//
		// Assume all values will be set
		boolean isAllValuesSet = true;

		//
		// Do NOT allow any value to be set if the column was not found
		// These cases must be covered when using this method
		final boolean throwExIfColumnNotFound = true;
		for (final Entry<String, Object> valueEntry : values.entrySet())
		{
			//
			// If a value could not be set, modifier will be changed to false
			isAllValuesSet = isAllValuesSet && setValue(model, valueEntry.getKey(), valueEntry.getValue(), throwExIfColumnNotFound);
		}
		return isAllValuesSet;
	}

	private static boolean setValue(
			@NonNull final Object model,
			@NonNull final String columnName,
			@Nullable final Object value,
			final boolean throwExIfColumnNotFound)
	{
		return helpers.setValue(model, columnName, value, throwExIfColumnNotFound);
	}

	/**
	 * Explicitly mark a column that was changed.
	 * <p>
	 * It is helpful to do this when:
	 * <ul>
	 * <li>you set a value for a column but the new value can be the same as the old value
	 * <li>and you really really what to trigger the database UPDATE or you really really want to trigger the model validators
	 * </ul>
	 * <p>
	 * NOTE:
	 * <ul>
	 * <li>if you are marking the column as changed but you are not explicitly set a value (i.e. a new value), this command will have no effect
	 * <li>this command has effect only for {@link POWrapper}ed objects
	 * </ul>
	 *
	 * @param model
	 * @param columnName column name to be marked as changed
	 */
	public static void markColumnChanged(final Object model, final String columnName)
	{
		if (POWrapper.isHandled(model))
		{
			final PO po = POWrapper.getStrictPO(model);
			po.markColumnChanged(columnName);
		}

		// Other wrappers are not supporting this feature
	}

	/**
	 * <b>IMPORTANT:</b> Please consider using {@link org.adempiere.ad.persistence.ModelDynAttributeAccessor} instead if this method. It's typesafe.
	 *
	 * @return old value or null
	 */
	public static Object setDynAttribute(final Object model, final String attributeName, final Object value)
	{
		return helpers.setDynAttribute(model, attributeName, value);
	}

	/**
	 * <b>IMPORTANT:</b> Please consider using {@link org.adempiere.ad.persistence.ModelDynAttributeAccessor} instead if this method. It's typesafe.
	 */
	@Nullable
	public static <T> T getDynAttribute(@NonNull final Object model, @NonNull final String attributeName)
	{
		return helpers.getDynAttribute(model, attributeName);
	}

	@Nullable
	public static <T> T computeDynAttributeIfAbsent(@NonNull final Object model, @NonNull final String attributeName, @NonNull final Supplier<T> supplier)
	{
		return helpers.computeDynAttributeIfAbsent(model, attributeName, supplier);
	}

	/**
	 * Check if given <code>model</code> can be casted to <code>interfaceClass</code>. NOTE: by casted we mean using create(...) methods.
	 *
	 * @return true if we can cast the model to given interface.
	 */
	public static boolean isInstanceOf(final Object model, final Class<?> interfaceClass)
	{
		if (model == null)
		{
			return false;
		}

		final String interfaceTableName = getTableNameOrNull(interfaceClass);
		if (interfaceTableName == null)
		{
			return interfaceClass.isAssignableFrom(model.getClass());
		}

		final String modelTableName = getModelTableNameOrNull(model); // make sure not to fail if 'model' has some unrelated class

		return interfaceTableName.equals(modelTableName);
	}

	/**
	 * Boolean dynamic attribute. If set underlying record cannot be saved or deleted.
	 */
	private static final String DYNATTR_SaveDeleteDisabled = "SaveDeleteDisabled";

	/**
	 * @param model
	 * @return true if save/delete was not disabled on purpose for given model
	 * @see #DYNATTR_SaveDeleteDisabled
	 */
	public static boolean isSaveDeleteDisabled(final Object model)
	{
		if (model == null)
		{
			return false;
		}
		final Object saveDeleteDisabled = getDynAttribute(model, InterfaceWrapperHelper.DYNATTR_SaveDeleteDisabled);
		if (saveDeleteDisabled == null)
		{
			return false;
		}
		if (!(saveDeleteDisabled instanceof Boolean))
		{
			return false;
		}

		return (boolean)(Boolean)saveDeleteDisabled;
	}

	/**
	 * Sets the dynamic attribute {@link #DYNATTR_SaveDeleteDisabled} to the given <code>disabled</code> value if <code>true</code> or resets it if <code>false</code>.<br>
	 * If set to <code>true</code>, both {@link PO} and {@link POJOLookupMap} will throw an {@link AdempiereException} on save/delete invocations for the given <code>model</code>.
	 * <p>
	 * this feature can be used when the given model shall be just a temporary "pojo" that may not be persisted.
	 *
	 * @param model
	 * @param disabled
	 * @see #isSaveDeleteDisabled(Object)
	 */
	public static void setSaveDeleteDisabled(final Object model, final boolean disabled)
	{
		if (disabled)
		{
			setDynAttribute(model, DYNATTR_SaveDeleteDisabled, true);
		}
		else
		{
			setDynAttribute(model, DYNATTR_SaveDeleteDisabled, null);
		}
	}

	public static <T> T translate(final T model, final Class<T> cl)
	{
		final String adLanguage = null; // autodetect from context
		return translate(model, cl, adLanguage);
	}

	public static <T> T translate(final T model, final Class<T> cl, final String adLanguage)
	{
		if (POWrapper.isHandled(model))
		{
			return POWrapper.translate(model, cl, adLanguage);
		}
		else if (POJOWrapper.isHandled(model))
		{
			// nothing to translate in testing
			return POJOWrapper.create(model, cl);
		}
		else
		{
			throw new AdempiereException("Model translation is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	public static IModelTranslationMap getModelTranslationMap(@NonNull final Object model)
	{
		if (POWrapper.isHandled(model))
		{
			return POWrapper.getModelTranslationMap(model);
		}
		else
		{
			return NullModelTranslationMap.instance;
		}
	}

	/**
	 * @return true if model is a new record (not yet saved in database)
	 */
	public static boolean isNew(final Object model)
	{
		return helpers.isNew(model);
	}

	/**
	 * @return true if this object was just created (saved or not). Compared to {@link #isNew(Object)} this method will return <code>true</code> even if the model was already saved.
	 */
	public static boolean isJustCreated(final Object model)
	{
		if (POWrapper.isHandled(model))
		{
			return POWrapper.getStrictPO(model).is_JustCreated();
		}
		else if (POJOWrapper.isHandled(model))
		{
			return POJOWrapper.getWrapper(model).isJustCreated();
		}
		else
		{
			throw new AdempiereException("is_JustCreated() is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	/**
	 * @return true if this model is created, updated or deleted by a manual user action (from UI window)
	 */
	public static boolean isUIAction(final Object model)
	{
		if (GridTabWrapper.isHandled(model))
		{
			return true;
		}
		else if (POWrapper.isHandled(model))
		{
			return POWrapper.isUIAction(model);
		}
		else if (POJOWrapper.isHandled(model))
		{
			return false;
		}
		else
		{
			throw new AdempiereException("Model wrapping is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	public static boolean recordChanged(final Object model, final String columnName)
	{
		Check.assumeNotNull(model, "model not null");

		if (POWrapper.isHandled(model))
		{
			return POWrapper.isRecordChanged(model);
		}
		else if (POJOWrapper.isHandled(model))
		{
			return POJOWrapper.isRecordChanged(model);
		}
		else
		{
			throw new AdempiereException("Model wrapping is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	public static boolean isValueChanged(final Object model, final String columnName)
	{
		return helpers.isValueChanged(model, columnName);
	}

	/**
	 * @return true if <i>any</i> of the given column names where changed
	 */
	public static boolean isValueChanged(final Object model, final Set<String> columnNames)
	{
		return helpers.isValueChanged(model, columnNames);
	}

	/**
	 * @return true if <i>any</i> of the given column names where changed
	 */
	public static boolean isValueChanged(final Object model, final String... columnNames)
	{
		return helpers.isValueChanged(model, ImmutableSet.copyOf(columnNames));
	}

	@Deprecated
	public static boolean isPOValueChanged(final Object model, final String columnName)
	{
		final PO po = POWrapper.getStrictPO(model);
		if (po == null)
		{
			return false;
		}

		return POWrapper.isValueChanged(po, columnName);
	}

	public static boolean hasChanges(@NonNull final Object model)
	{
		if (POWrapper.isHandled(model))
		{
			return POWrapper.hasChanges(model);
		}
		else if (POJOWrapper.isHandled(model))
		{
			return POJOWrapper.getWrapper(model).hasChanges();
		}
		else
		{
			throw new AdempiereException("Model wrapping is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	/**
	 * @param model
	 * @return how many times given model was loaded/reloaded
	 */
	public static int getLoadCount(final Object model)
	{
		Check.assumeNotNull(model, "model not null");

		if (POWrapper.isHandled(model))
		{
			return POWrapper.getStrictPO(model).getLoadCount();
		}
		else if (POJOWrapper.isHandled(model))
		{
			return POJOWrapper.getWrapper(model).getLoadCount();
		}
		else
		{
			throw new AdempiereException("Model wrapping is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	public static Evaluatee getEvaluatee(final Object model)
	{
		return helpers.getEvaluatee(model);
	}

	/**
	 * @param modelClass
	 * @return immutable list of column names of modelClass's table
	 */
	public static Set<String> getModelColumnNames(final Class<?> modelClass)
	{
		if (Adempiere.isUnitTestMode())
		{
			return ModelClassIntrospector.getInstance()
					.getModelClassInfo(modelClass)
					.getDefinedColumnNames();
		}

		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		Check.assumeNotNull(poInfo, "poInfo not null for {}", tableName); // shall not happen

		return poInfo.getColumnNames();
	}

	public static IModelInternalAccessor getModelInternalAccessor(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		if (POWrapper.isHandled(model))
		{
			final IModelInternalAccessor modelInternalAccessor = POWrapper.getModelInternalAccessor(model);
			Check.assumeNotNull(modelInternalAccessor, "modelInternalAccessor not null");
			return modelInternalAccessor;
		}
		else if (POJOWrapper.isHandled(model))
		{
			final IModelInternalAccessor modelInternalAccessor = POJOWrapper.getModelInternalAccessor(model);
			Check.assumeNotNull(modelInternalAccessor, "modelInternalAccessor not null");
			return modelInternalAccessor;
		}
		else
		{
			throw new AdempiereException("Model wrapping is not supported for " + model + " (class:" + model.getClass() + ")");
		}
	}

	public static IModelCopyHelper copy()
	{
		return new ModelCopyHelper();
	}

	public static boolean isOldValues(@NonNull final Object model)
	{
		if (POWrapper.isHandled(model))
		{
			return POWrapper.isOldValues(model);
		}
		else if (GridTabWrapper.isHandled(model))
		{
			return GridTabWrapper.isOldValues(model);
		}
		else if (POJOWrapper.isHandled(model))
		{
			return POJOWrapper.isOldValues(model);
		}
		else
		{
			throw new AdempiereException("Model wrapping is not supported for " + model
					+ "\n Class: " + (model == null ? null : model.getClass()));
		}
	}

	public static void assertNotOldValues(@NonNull final Object model)
	{
		if (isOldValues(model))
		{
			throw new AdempiereException("Model was expected to not use old values: " + model + " (" + model.getClass() + ")");
		}
	}

	/**
	 * If the given <code>model</code> is not null and has all the columns which are defined inside the given <code>clazz</code>'s {@link IModelClassInfo},<br>
	 * then return an instance using {@link #create(Object, Class)}.<br>
	 * Otherwise, return <code>null</code> .
	 *
	 * @param model
	 * @param clazz
	 * @return
	 */
	public static <T> T asColumnReferenceAwareOrNull(final Object model,
													 final Class<T> clazz)
	{
		if (model == null)
		{
			return null;
		}
		if (clazz.isAssignableFrom(model.getClass()))
		{
			return clazz.cast(model);
		}

		final IModelClassInfo clazzInfo = ModelClassIntrospector
				.getInstance()
				.getModelClassInfo(clazz);
		for (final String columnName : clazzInfo.getDefinedColumnNames())
		{
			if (!hasModelColumnName(model, columnName))
			{
				// not all columns of clazz are also in model => we can't do it.
				return null;
			}
		}

		return InterfaceWrapperHelper.create(model, clazz);
	}

	/**
	 * Disables the read only (i.e. not updateable) columns enforcement.
	 * So basically, after you are calling this method you will be able to change the values for any not updateable column.
	 * <p>
	 * WARNING: please make sure you know what are you doing before calling this method. If you are not sure, please don't use it.
	 */
	public static void disableReadOnlyColumnCheck(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		ATTR_ReadOnlyColumnCheckDisabled.setValue(model, Boolean.TRUE);
	}

	public static final ModelDynAttributeAccessor<Object, Boolean> ATTR_ReadOnlyColumnCheckDisabled = new ModelDynAttributeAccessor<>(InterfaceWrapperHelper.class.getName(), "ReadOnlyColumnCheckDisabled", Boolean.class);

	public static int getFirstValidIdByColumnName(final String columnName)
	{
		return POWrapper.getFirstValidIdByColumnName(columnName);
	}

	// NOTE: public until we move everything to "org.adempiere.ad.model.util" package.
	public static Object checkZeroIdValue(final String columnName, final Object value)
	{
		return POWrapper.checkZeroIdValue(columnName, value);
	}

	public static boolean isCopy(final Object model)
	{
		return helpers.isCopy(model);
	}

	public static boolean isCopying(final Object model)
	{
		return helpers.isCopying(model);
	}
}
