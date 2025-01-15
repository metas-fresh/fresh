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

package org.adempiere.util.lang.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;

import javax.annotation.Nullable;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.IntFunction;

/**
 * Simple implementation of {@link ITableRecordReference} which can:
 * <ul>
 * <li>wrap an already loaded model (if you use {@link #of(Object)} constructor)
 * <li>start from known AD_Table_ID/Record_ID and will load the underlying model only when it's needed (if you use {@link #of(int, int)} constructor)
 * </ul>
 * <p>
 * TODO: merge logic with {@link de.metas.cache.model.impl.TableRecordCacheLocal}.
 *
 * @author tsa
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class TableRecordReference implements ITableRecordReference
{
	/**
	 * Creates an {@link TableRecordReference} from the given model.
	 *
	 * @param model model interface or {@link TableRecordReference}; <code>null</code> is NOT allowed
	 * @return {@link TableRecordReference}; never returns null
	 */
	public static TableRecordReference of(@NonNull final Object model)
	{
		if (model instanceof TableRecordReference)
		{
			return (TableRecordReference)model;
		}
		else if (model instanceof ITableRecordReference)
		{
			final ITableRecordReference recordRef = (ITableRecordReference)model;
			return new TableRecordReference(recordRef.getTableName(), recordRef.getRecord_ID());
		}
		else
		{
			return new TableRecordReference(model);
		}
	}

	@Deprecated
	public static TableRecordReference of(final TableRecordReference recordRef)
	{
		return recordRef;
	}

	/**
	 * Same as {@link #of(Object)} but in case <code>model</code> is null then it will return null.
	 *
	 * @return {@link TableRecordReference} or null
	 */
	@Nullable
	public static TableRecordReference ofOrNull(@Nullable final Object model)
	{
		if (model == null)
		{
			return null;
		}
		return of(model);
	}

	/**
	 * @return immutable list of {@link TableRecordReference}s
	 */
	public static List<TableRecordReference> ofCollection(@Nullable final Collection<?> models)
	{
		if (models == null || models.isEmpty())
		{
			return ImmutableList.of();
		}

		return models
				.stream()
				.filter(Objects::nonNull)
				.map(TableRecordReference::of)
				.collect(GuavaCollectors.toImmutableList());
	}

	public static List<TableRecordReference> ofRecordIds(
			@NonNull final String tableName,
			@Nullable final Collection<Integer> recordIds)
	{
		if (recordIds == null || recordIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return recordIds
				.stream()
				.map(recordId -> of(tableName, recordId))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static Set<TableRecordReference> ofSet(@Nullable final Collection<?> models)
	{
		if (models == null || models.isEmpty())
		{
			return ImmutableSet.of();
		}

		return models
				.stream()
				.filter(Objects::nonNull)
				.map(TableRecordReference::of)
				.collect(GuavaCollectors.toImmutableSet());
	}

	/**
	 * Creates an {@link TableRecordReference} from the given {@code model}'s {@code AD_Table_ID} and {@code Record_ID}.
	 */
	public static TableRecordReference ofReferenced(@NonNull final Object model)
	{
		final Optional<Integer> adTableId = InterfaceWrapperHelper.getValue(model, ITableRecordReference.COLUMNNAME_AD_Table_ID);
		final Optional<Integer> recordId = InterfaceWrapperHelper.getValue(model, ITableRecordReference.COLUMNNAME_Record_ID);

		return new TableRecordReference(adTableId.orElse(-1), recordId.orElse(-1)); // the -1 shall cause an exception to be thrown
	}

	@Nullable
	public static ITableRecordReference ofReferencedOrNull(@Nullable final Object model)
	{
		if (model == null)
		{
			return null;
		}

		final Optional<Integer> adTableId = InterfaceWrapperHelper.getValue(model, ITableRecordReference.COLUMNNAME_AD_Table_ID);
		final Optional<Integer> recordId = InterfaceWrapperHelper.getValue(model, ITableRecordReference.COLUMNNAME_Record_ID);
		if (!adTableId.isPresent() || !recordId.isPresent())
		{
			return null;
		}
		return new TableRecordReference(adTableId.get(), recordId.get());
	}

	public static TableRecordReference of(final int adTableId, final int recordId)
	{
		return new TableRecordReference(adTableId, recordId);
	}

	public static TableRecordReference of(@NonNull final AdTableId adTableId, final int recordId)
	{
		return new TableRecordReference(adTableId.getRepoId(), recordId);
	}

	@Nullable
	public static TableRecordReference ofOrNull(final int adTableId, final int recordId)
	{
		return adTableId > 0 && recordId >= 0
				? new TableRecordReference(adTableId, recordId)
				: null;
	}

	@JsonCreator
	public static TableRecordReference of(@JsonProperty("tableName") final String tableName, @JsonProperty("recordId") final int recordId)
	{
		return new TableRecordReference(tableName, recordId);
	}

	public static TableRecordReference of(@NonNull final String tableName, @NonNull final RepoIdAware recordId)
	{
		return new TableRecordReference(tableName, recordId.getRepoId());
	}

	/**
	 * @return immutable list of {@link TableRecordReference}s
	 */
	public static List<TableRecordReference> ofRecordIds(final String tableName, final List<Integer> recordIds)
	{
		if (recordIds == null || recordIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return recordIds
				.stream()
				.map(modelId -> of(tableName, modelId))
				.collect(GuavaCollectors.toImmutableList());
	}

	@Nullable
	public static TableRecordReference ofMapOrNull(final Map<?, ?> map)
	{
		final Object tableNameObj = map.get(PROP_TableName);
		if (tableNameObj == null)
		{
			return null;
		}

		final String tableName = tableNameObj.toString().trim();
		if (Check.isEmpty(tableName, true))
		{
			return null;
		}

		final Object recordIdObj = map.get(PROP_RecordId);
		if (recordIdObj == null)
		{
			return null;
		}

		final int recordId;
		if (recordIdObj instanceof Number)
		{
			recordId = ((Number)recordIdObj).intValue();
		}
		else
		{
			try
			{
				recordId = Integer.parseInt(recordIdObj.toString());
			}
			catch (final Exception e)
			{
				return null;
			}
		}

		return new TableRecordReference(tableName, recordId);
	}

	@Nullable
	@JsonIgnore
	private transient Integer _adTableId;

	private static final String PROP_TableName = "tableName";
	@JsonProperty(PROP_TableName)
	private final String tableName;

	private static final String PROP_RecordId = "recordId";
	@JsonProperty(PROP_RecordId)
	private final int recordId;

	private transient Integer _hashcode;

	/**
	 * Cached model. Using a soft reference to avoid memory problems when *a lot* of TableRecordReference are handled.
	 * <p>
	 * Note: when changing this class, please make sure that this member is never <code>null</code>.
	 */
	private transient SoftReference<Object> modelRef = new SoftReference<>(null);

	/**
	 * Creates an instance that will be loaded on demand and is specified by the given <code>adTableId</code> and <code>recordId</code>.
	 * <p>
	 * Hint: Please consider using {@link #of(int, int)}  instead if this constructor.
	 */
	private TableRecordReference(final int adTableId, final int recordId)
	{
		Check.assume(adTableId > 0, "adTableId > 0");
		this._adTableId = adTableId;
		this.tableName = Services.get(IADTableDAO.class).retrieveTableName(adTableId);

		// NOTE: not validating with org.adempiere.model.InterfaceWrapperHelper.getFirstValidIdByColumnName(String) just for performances,
		// but we might consider it since it's not a big deal.
		Check.assume(recordId >= 0, "recordId >= 0");
		this.recordId = recordId;
	}

	/**
	 * Creates an instance that will be loaded on demand and is specified by the given <code>tableName</code> and <code>recordId</code>.
	 * <p>
	 * Hint: Please consider using {@link #of(String, int)} instead if this constructor.
	 */
	private TableRecordReference(
			final String tableName,
			final int recordId)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");

		// NOTE: not validating with org.adempiere.model.InterfaceWrapperHelper.getFirstValidIdByColumnName(String) just for performances,
		// but we might consider it since it's not a big deal.
		Check.assume(recordId >= 0, "recordId >= 0");

		this.tableName = tableName;
		this._adTableId = null; // lazy

		this.recordId = recordId;
	}

	private TableRecordReference(@NonNull final Object model)
	{
		this._adTableId = InterfaceWrapperHelper.getModelTableId(model);
		this.tableName = InterfaceWrapperHelper.getModelTableName(model);
		this.recordId = InterfaceWrapperHelper.getId(model);

		this.modelRef = new SoftReference<>(model);
	}

	@Override
	public String toString()
	{
		return "ref{" + tableName + "/" + recordId + "}";
	}

	/**
	 * Returns <code>true</code> if both this and the given <code>obj</code> are TableRecordReferences and have the same <code>AD_Table_ID</code> and <code>Record_ID</code>.
	 */
	@Override
	public boolean equals(@Nullable final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj instanceof TableRecordReference)
		{
			final TableRecordReference other = (TableRecordReference)obj;
			return Objects.equals(this.tableName, other.tableName)
					&& this.recordId == other.recordId;
		}
		else
		{
			return false;
		}
	}

	/**
	 * @return true if both o1 and o2 are null, or if both have the same table and recordId. Makes sure to ignore the internal {@link SoftReference}'s timestamp
	 */
	public static boolean equals(@Nullable final TableRecordReference o1, @Nullable final TableRecordReference o2)
	{
		if (o1 == null && o2 == null)
		{
			return true;
		}
		else if (o1 == null || o2 == null)
		{
			return false;
		}
		else
		{
			return Objects.equals(o1.tableName, o2.tableName) && o1.recordId == o2.recordId;
		}
	}

	@Override
	public int hashCode()
	{
		Integer hashcode = this._hashcode;
		if (hashcode == null)
		{
			hashcode = _hashcode = Objects.hash(tableName, recordId);
		}
		return hashcode;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	public void assertTableName(@NonNull final String expectedTableName)
	{
		if (!Objects.equals(getTableName(), expectedTableName))
		{
			throw new AdempiereException("Reference is expected to have '" + expectedTableName + "' table: " + this);
		}
	}

	@Override
	@JsonIgnore
	public int getAD_Table_ID()
	{
		Integer adTableId = this._adTableId;
		if (adTableId == null)
		{
			adTableId = this._adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		}
		return adTableId;
	}

	@JsonIgnore
	public AdTableId getAdTableId()
	{
		return AdTableId.ofRepoId(getAD_Table_ID());
	}

	@Override
	@JsonIgnore
	public int getRecord_ID()
	{
		return recordId;
	}

	public int getRecordIdAssumingTableName(@NonNull final String expectedTableName)
	{
		Check.assumeEquals(this.tableName, expectedTableName, "tableName");
		return getRecord_ID();
	}

	public <T extends RepoIdAware> T getIdAssumingTableName(
			@NonNull final String expectedTableName,
			@NonNull final IntFunction<T> mapper)
	{
		final int repoId = getRecordIdAssumingTableName(expectedTableName);
		return mapper.apply(repoId);
	}

	@Deprecated
	@Override
	public Object getModel(@NonNull final IContextAware context)
	{
		checkModelStaled(context);

		//
		// Load the model now
		final Object cachedModel = modelRef.get();
		if (cachedModel != null)
		{
			return cachedModel;
		}

		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();
		final Object loadedModel = InterfaceWrapperHelper.create(ctx, tableName, getRecord_ID(), Object.class, trxName);

		modelRef = new SoftReference<>(loadedModel);

		return loadedModel;
	}

	@Override
	public <T> T getModel(@NonNull final IContextAware context, @NonNull final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(getModel(context), modelClass);
	}

	@NonNull
	public <T> T getModelNonNull(@NonNull final IContextAware context, @NonNull final Class<T> modelClass)
	{
		final T model = InterfaceWrapperHelper.create(getModel(context), modelClass);
		return Check.assumeNotNull(model, "Model for this TableRecordReference={} may not be null", this);
	}

	@Override
	public void notifyModelStaled()
	{
		modelRef = new SoftReference<>(null);
	}

	/**
	 * Checks if underlying (and cached) model is still valid in given context. In case is no longer valid, it will be set to <code>null</code>.
	 */
	private void checkModelStaled(final IContextAware context)
	{
		final Object model = modelRef.get();
		if (model == null)
		{
			return;
		}

		final String modelTrxName = InterfaceWrapperHelper.getTrxName(model);
		if (!Services.get(ITrxManager.class).isSameTrxName(modelTrxName, context.getTrxName()))
		{
			modelRef = new SoftReference<>(null);
		}

		// TODO: why the ctx is not validated, like org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal.getValue(Class<RT>) does?
	}

	@Deprecated
	@Override
	public Object getModel()
	{
		return getModel(PlainContextAware.newWithThreadInheritedTrx());
	}

	@Override
	public <T> T getModel(final Class<T> modelClass)
	{
		return getModel(PlainContextAware.newWithThreadInheritedTrx(), modelClass);
	}

	@NonNull
	public <T> T getModelNonNull(@NonNull final Class<T> modelClass)
	{
		return getModelNonNull(PlainContextAware.newWithThreadInheritedTrx(), modelClass);
	}

	public static <T> List<T> getModels(
			@NonNull final Collection<? extends ITableRecordReference> references,
			@NonNull final Class<T> modelClass)
	{
		return references
				.stream()
				.map(ref -> ref.getModel(modelClass))
				.collect(ImmutableList.toImmutableList());
	}

	public boolean isOfType(@NonNull final Class<?> modelClass)
	{
		final String modelTableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		return modelTableName != null && modelTableName.equals(getTableName());
	}
}
