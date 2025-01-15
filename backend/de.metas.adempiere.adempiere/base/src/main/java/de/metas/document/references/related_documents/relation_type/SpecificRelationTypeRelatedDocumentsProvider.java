/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.references.related_documents.relation_type;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADRefTable;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySuppliers;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.CustomizedWindowInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.PORelationException;
import org.compiere.model.MQuery;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Related documents provider for one single relation type
 */
public class SpecificRelationTypeRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	static Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(SpecificRelationTypeRelatedDocumentsProvider.class);

	private final RelatedDocumentsId relatedDocumentsId;
	private final String internalName;
	private final boolean isTableRecordIdTarget;

	@Nullable
	private final ZoomProviderDestination source;
	private final ZoomProviderDestination target;

	private final Priority relatedDocumentsPriority = Priority.MEDIUM;

	private SpecificRelationTypeRelatedDocumentsProvider(@NonNull final Builder builder)
	{
		relatedDocumentsId = builder.getRelatedDocumentsId();
		internalName = builder.getInternalName();

		isTableRecordIdTarget = builder.isTableRecordIdTarget();

		source = isTableRecordIdTarget
				? null
				: ZoomProviderDestination.builder()
				.adReferenceId(builder.getSource_Reference_ID())
				.tableRefInfo(builder.getSourceTableRefInfoOrNull())
				.roleDisplayName(builder.getTargetRoleDisplayName())
				.tableRecordIdTarget(isTableRecordIdTarget)
				.build();

		target = ZoomProviderDestination.builder()
				.adReferenceId(builder.getTarget_Reference_ID())
				.tableRefInfo(builder.getTargetTableRefInfoOrNull())
				.roleDisplayName(builder.getTargetRoleDisplayName())
				.tableRecordIdTarget(isTableRecordIdTarget)
				.build();

	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("relatedDocumentsId", relatedDocumentsId)
				.add("internalName", internalName)
				.add("source", source)
				.add("target", target)
				.add("isReferenceTarget", isTableRecordIdTarget)
				.toString();
	}

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		final AdWindowId adWindowId;
		final ITranslatableString display;

		// #2340 Reference Target relation type: There is no source, only a target that contains the table and
		// the reference target column to be linked with the "fromDocument"
		if (isTableRecordIdTarget)
		{
			final ZoomProviderDestination referenceTarget = getTarget();

			adWindowId = getRefTableAD_Window_ID(referenceTarget.getTableRefInfo(), fromDocument.isSOTrx());

			display = referenceTarget.getRoleDisplayName(adWindowId);
		}
		else
		{
			final IPair<ZoomProviderDestination, ZoomProviderDestination> sourceAndTarget = findSourceAndTargetEffective(fromDocument);

			final ZoomProviderDestination source = sourceAndTarget.getLeft();
			Check.assumeNotNull(source, "Source cannot be left empty");

			final ZoomProviderDestination target = sourceAndTarget.getRight();

			if (!source.matchesAsSource(fromDocument))
			{
				logger.debug("Skip {} because {} is not matching source={}", this, fromDocument, source);
				return ImmutableList.of();
			}

			adWindowId = getRefTableAD_Window_ID(target.getTableRefInfo(), fromDocument.isSOTrx());

			display = target.getRoleDisplayName(adWindowId);
		}

		if (targetWindowId != null && !AdWindowId.equals(targetWindowId, adWindowId))
		{
			return ImmutableList.of();
		}

		final MQuery query = mkZoomOriginQuery(fromDocument);

		return ImmutableList.of(
				RelatedDocumentsCandidateGroup.of(
						RelatedDocumentsCandidate.builder()
								.id(getRelatedDocumentsId())
								.internalName(getInternalName())
								.targetWindow(
										RelatedDocumentsTargetWindow.ofAdWindowIdAndCategory(
												adWindowId,
												getSource() != null ? getSource().getTableRefInfo().getKeyColumn() : null))
								.priority(relatedDocumentsPriority)
								.querySupplier(RelatedDocumentsQuerySuppliers.ofQuery(query))
								.windowCaption(display)
								.documentsCountSupplier(new RelationTypeRelatedDocumentsCountSupplier(query))
								.build()));
	}

	public boolean isTableRecordIdTarget()
	{
		return isTableRecordIdTarget;
	}

	private RelatedDocumentsId getRelatedDocumentsId()
	{
		return relatedDocumentsId;
	}

	String getInternalName()
	{
		return internalName;
	}

	private ZoomProviderDestination getTarget()
	{
		return target;
	}

	@Nullable
	private ZoomProviderDestination getSource()
	{
		return source;
	}

	/**
	 * @return effective source and target pair
	 */
	private IPair<ZoomProviderDestination, ZoomProviderDestination> findSourceAndTargetEffective(final IZoomSource fromDocument)
	{
		final ZoomProviderDestination target = getTarget();
		final ZoomProviderDestination source = getSource();

		Check.assumeNotNull(source, "The Source cannot be null");

		// our destination is always the *target* reference
		return ImmutablePair.of(source, target);
	}

	private MQuery mkZoomOriginQuery(final IZoomSource fromDocument)
	{
		final String queryWhereClause = createZoomOriginQueryWhereClause(fromDocument);

		final ADRefTable refTable = getTarget().getTableRefInfo();

		final String tableName = refTable.getTableName();
		final String columnName = refTable.getKeyColumn();

		final MQuery query = new MQuery();
		query.addRestriction(queryWhereClause);
		query.setZoomTableName(tableName);
		query.setZoomColumnName(columnName);

		return query;
	}

	private String createZoomOriginQueryWhereClause(final IZoomSource fromDocument)
	{
		// services
		final ITableRecordIdDAO tableRecordIdDAO = Services.get(ITableRecordIdDAO.class);

		final StringBuilder queryWhereClause = new StringBuilder();

		final ADRefTable refTable = getTarget().getTableRefInfo();

		final String targetTableName = fromDocument.getTableName();
		final String originTableName = refTable.getTableName();

		final String whereClause = refTable.getWhereClause();
		if (isTableRecordIdTarget)
		{
			String originRecordIdName = null;

			final List<TableRecordIdDescriptor> tableRecordIdDescriptors = tableRecordIdDAO.getTableRecordIdReferences(originTableName);

			for (final TableRecordIdDescriptor tableRecordIdDescriptor : tableRecordIdDescriptors)
			{
				if (targetTableName.equals(tableRecordIdDescriptor.getTargetTableName()))
				{
					originRecordIdName = tableRecordIdDescriptor.getRecordIdColumnName();

					break;
				}
			}

			if (originRecordIdName != null)
			{
				final String tableIdColumnName = POInfo.getPOInfoNotNull(originTableName).getTableIdColumnName(originRecordIdName).orElse(null);
				Check.assumeNotEmpty(tableIdColumnName, "The table {} must have an AD_Table_ID column", originTableName);

				queryWhereClause

						.append(fromDocument.getAD_Table_ID())
						.append(" = ")
						.append(originTableName)
						.append(".")
						.append(tableIdColumnName)
						.append(" AND ")
						.append(fromDocument.getRecord_ID())
						.append(" = ")
						.append(originTableName)
						.append(".")
						.append(originRecordIdName);
				if (Check.isNotBlank(whereClause))
				{
					queryWhereClause.append(" AND ").append(whereClause);
				}

			}
			else
			{
				queryWhereClause.append(" 1=2 ");
			}

		}
		else
		{
			final String refTableWhereClause = whereClause;
			if (!Check.isEmpty(refTableWhereClause))
			{
				queryWhereClause.append(parseWhereClause(fromDocument, refTableWhereClause, true));
			}
			else
			{
				throw new AdempiereException("RefTable " + refTable + " has no whereClause, so RelationType " + this + " needs to be explicit");
			}
		}

		return queryWhereClause.toString();
	}

	/**
	 * Parses given <code>where</code>
	 *
	 * @param fromDocument zoom source
	 * @param where        where clause to be parsed
	 * @param throwEx      true if an exception shall be thrown in case the parsing failed.
	 * @return parsed where clause or empty string in case parsing failed and throwEx is <code>false</code>
	 */
	private static String parseWhereClause(@NonNull final IZoomSource fromDocument, final String where, final boolean throwEx)
	{
		final IStringExpression whereExpr = IStringExpression.compileOrDefault(where, IStringExpression.NULL);
		if (whereExpr.isNullExpression())
		{
			return "";
		}

		final Evaluatee evalCtx = fromDocument.createEvaluationContext();
		final OnVariableNotFound onVariableNotFound = throwEx ? OnVariableNotFound.Fail : OnVariableNotFound.ReturnNoResult;
		final String whereParsed = whereExpr.evaluate(evalCtx, onVariableNotFound);
		if (whereExpr.isNoResult(whereParsed))
		{
			// NOTE: logging as debug instead of warning because this might be a standard use case,
			// i.e. we are checking if a given where clause has some results, so the method was called with throwEx=false
			// and if we reached this point it means one of the context variables were not present and it has no default value.
			// This is perfectly normal, a default value is really not needed because we don't want to execute an SQL command
			// which would return no result. It's much more efficient to stop here.
			logger.debug("Could not parse where clause:\n{} \n EvalCtx: {} \n ZoomSource: {}", where, evalCtx, fromDocument);
			return "";
		}

		return whereParsed;
	}

	@Value
	private static class ZoomProviderDestination
	{
		ReferenceId adReferenceId;
		ADRefTable tableRefInfo;
		ITranslatableString roleDisplayName;

		/**
		 * {@code true} means that this instance is based on an {@code AD_RelationType} whose target is a {@code AD_Table_ID/Record_ID} reference.
		 */
		boolean tableRecordIdTarget;

		@lombok.Builder
		private ZoomProviderDestination(
				@NonNull final ReferenceId adReferenceId,
				@NonNull final ADRefTable tableRefInfo,
				@Nullable final ITranslatableString roleDisplayName,
				@NonNull final Boolean tableRecordIdTarget)
		{
			this.adReferenceId = adReferenceId;
			this.tableRefInfo = tableRefInfo;
			this.roleDisplayName = roleDisplayName;
			this.tableRecordIdTarget = tableRecordIdTarget;
		}

		public ITranslatableString getRoleDisplayName(final AdWindowId fallbackAD_Window_ID)
		{
			if (roleDisplayName != null)
			{
				return roleDisplayName;
			}

			// Fallback to window name
			final ITranslatableString windowName = Services.get(IADWindowDAO.class).retrieveWindowName(fallbackAD_Window_ID);
			Check.errorIf(windowName == null, "Found no display string for, destination={}, AD_Window_ID={}", this, fallbackAD_Window_ID);
			return windowName;
		}

		public boolean matchesAsSource(@NonNull final IZoomSource zoomSource)
		{
			if(!tableRefInfo.getTableName().equals(zoomSource.getTableName()))
			{
				logger.debug("matchesAsSource - return false because zoomSource.tableName={}; this={}", zoomSource.getTableName(), this);
				return false;
			}

			if (tableRecordIdTarget)
			{
				// the source always matches if the target is ReferenceTarget
				logger.debug("matchesAsSource - return true because tableRecordIdTarget=true; this={}", this);
				return true;
			}

			if (!zoomSource.isGenericZoomOrigin())
			{
				logger.warn("matchesAsSource - return false because zoomSource.isGenericZoomOrigin()==false; this={}; zoomSource={}", this, zoomSource);
				return false;
			}

			final String keyColumnName = zoomSource.getKeyColumnNameOrNull();
			if (Check.isBlank(keyColumnName))
			{
				logger.warn("matchesAsSource - return false because zoomSource.getKeyColumnNameOrNull()==null; this={}; zoomSource={}", this, zoomSource);
				return false;
			}
			
			final String whereClause = tableRefInfo.getWhereClause();
			if (Check.isBlank(whereClause))
			{
				logger.debug("matchesAsSource - return true because tableRefInfo.whereClause is empty; this={}", this);
				return true;
			}

			final String parsedWhere = parseWhereClause(zoomSource, whereClause, false);
			if (Check.isBlank(parsedWhere))
			{
				return false;
			}

			final StringBuilder whereClauseEffective = new StringBuilder();
			whereClauseEffective.append(parsedWhere);
			whereClauseEffective.append(" AND ( ").append(keyColumnName).append("=").append(zoomSource.getRecord_ID()).append(" )");
			
			final boolean match = new Query(zoomSource.getCtx(), zoomSource.getTableName(), whereClauseEffective.toString(), zoomSource.getTrxName())
					.anyMatch();

			logger.debug("whereClause='{}' matches source='{}': {}", parsedWhere, zoomSource, match);
			return match;
		}
	}

	/**
	 * @return the <code>AD_Window_ID</code>
	 * @throws PORelationException if no <code>AD_Window_ID</code> can be found.
	 */
	private AdWindowId getRefTableAD_Window_ID(final ADRefTable tableRefInfo, final boolean isSOTrx)
	{
		AdWindowId windowId = tableRefInfo.getZoomAD_Window_ID_Override();
		if (windowId != null)
		{
			return windowId;
		}

		if (isSOTrx)
		{
			windowId = tableRefInfo.getZoomSO_Window_ID();
		}
		else
		{
			windowId = tableRefInfo.getZoomPO_Window_ID();
		}
		if (windowId == null)
		{
			throw PORelationException.throwMissingWindowId(tableRefInfo.getIdentifier(), tableRefInfo.getTableName(), isSOTrx);
		}

		return windowId;
	}

	@ToString(exclude = "adReferenceService")
	public static final class Builder
	{
		private ADReferenceService adReferenceService = null;

		private CustomizedWindowInfoMap customizedWindowInfoMap = CustomizedWindowInfoMap.empty();

		private String internalName;
		private int adRelationTypeId;
		private boolean isTableRecordIDTarget = false;

		private ReferenceId sourceReferenceId = null;
		private ITranslatableString sourceRoleDisplayName;
		@Nullable
		private ADRefTable sourceTableRefInfo = null; // lazy

		private ReferenceId targetReferenceId = null;
		private ITranslatableString targetRoleDisplayName;
		@Nullable
		private ADRefTable targetTableRefInfo = null; // lazy

		private Builder()
		{
		}

		@Nullable
		public SpecificRelationTypeRelatedDocumentsProvider buildOrNull()
		{
			if (!isTableRecordIdTarget() && getSourceTableRefInfoOrNull() == null)
			{

				logger.info("Skip building {} because source tableRefInfo is null", this);
				return null;

			}
			if (getTargetTableRefInfoOrNull() == null)
			{
				logger.info("Skip building {} because target tableRefInfo is null", this);
				return null;
			}

			return new SpecificRelationTypeRelatedDocumentsProvider(this);
		}

		public Builder setAdReferenceService(@NonNull final ADReferenceService adReferenceService)
		{
			this.adReferenceService = adReferenceService;
			return this;
		}

		public Builder setCustomizedWindowInfoMap(final CustomizedWindowInfoMap customizedWindowInfoMap)
		{
			this.customizedWindowInfoMap = customizedWindowInfoMap;
			this.sourceTableRefInfo = null;
			this.targetTableRefInfo = null;
			return this;
		}

		public Builder setAD_RelationType_ID(final int adRelationTypeId)
		{
			this.adRelationTypeId = adRelationTypeId;
			return this;
		}

		private int getAD_RelationType_ID()
		{
			return Check.assumeGreaterThanZero(adRelationTypeId, "adRelationTypeId");
		}

		private RelatedDocumentsId getRelatedDocumentsId()
		{
			return RelatedDocumentsId.ofString("AD_RelationType_ID-" + getAD_RelationType_ID());
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		private String getInternalName()
		{
			if (Check.isEmpty(internalName, true))
			{
				return getRelatedDocumentsId().toJson();
			}
			return internalName;
		}

		public Builder setSource_Reference_ID(final int sourceReferenceId)
		{
			this.sourceReferenceId = ReferenceId.ofRepoIdOrNull(sourceReferenceId);
			sourceTableRefInfo = null; // reset
			return this;
		}

		private ReferenceId getSource_Reference_ID()
		{
			return Check.assumeNotNull(sourceReferenceId, "sourceReferenceId is set");
		}

		@Nullable
		private ADRefTable getSourceTableRefInfoOrNull()
		{
			if (sourceTableRefInfo == null)
			{
				sourceTableRefInfo = retrieveTableRefInfo(getSource_Reference_ID());
			}
			return sourceTableRefInfo;
		}

		@Nullable
		private ADRefTable retrieveTableRefInfo(final ReferenceId adReferenceId)
		{
			final ADRefTable tableRefInfo = adReferenceService.retrieveTableRefInfo(adReferenceId);
			if (tableRefInfo == null)
			{
				return null;
			}

			return tableRefInfo.mapWindowIds(this::getCustomizationWindowId);
		}

		@Nullable
		private AdWindowId getCustomizationWindowId(@Nullable final AdWindowId adWindowId)
		{
			return adWindowId != null
					? customizedWindowInfoMap.getCustomizedWindowInfo(adWindowId).map(CustomizedWindowInfo::getCustomizationWindowId).orElse(adWindowId)
					: null;
		}

		public Builder setSourceRoleDisplayName(final ITranslatableString sourceRoleDisplayName)
		{
			this.sourceRoleDisplayName = sourceRoleDisplayName;
			return this;
		}

		public Builder setTarget_Reference_AD(final int targetReferenceId)
		{
			this.targetReferenceId = ReferenceId.ofRepoIdOrNull(targetReferenceId);
			targetTableRefInfo = null; // lazy
			return this;
		}

		private ReferenceId getTarget_Reference_ID()
		{
			return Check.assumeNotNull(targetReferenceId, "targetReferenceId is set");
		}

		private ADRefTable getTargetTableRefInfoOrNull()
		{
			if (targetTableRefInfo == null)
			{
				targetTableRefInfo = retrieveTableRefInfo(getTarget_Reference_ID());
			}
			return targetTableRefInfo;
		}

		public Builder setTargetRoleDisplayName(final ITranslatableString targetRoleDisplayName)
		{
			this.targetRoleDisplayName = targetRoleDisplayName;
			return this;
		}

		public ITranslatableString getTargetRoleDisplayName()
		{
			return targetRoleDisplayName;
		}

		public Builder setIsTableRecordIdTarget(final boolean isReferenceTarget)
		{
			isTableRecordIDTarget = isReferenceTarget;
			return this;
		}

		private boolean isTableRecordIdTarget()
		{
			return isTableRecordIDTarget;
		}
	}
}
