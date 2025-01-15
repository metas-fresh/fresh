package de.metas.ui.web.document.references.service;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocuments;
import de.metas.document.references.related_documents.RelatedDocumentsFactory;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.references.WebuiDocumentReference;
import de.metas.ui.web.document.references.WebuiDocumentReferenceCandidate;
import de.metas.ui.web.document.references.WebuiDocumentReferenceId;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Properties;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
public class WebuiDocumentReferencesService
{
	private static final Logger logger = LogManager.getLogger(WebuiDocumentReferencesService.class);
	private final DocumentCollection documentCollection;
	private final RelatedDocumentsFactory relatedDocumentsFactory;

	public WebuiDocumentReferencesService(
			@NonNull final DocumentCollection documentCollection,
			@NonNull final RelatedDocumentsFactory relatedDocumentsFactory)
	{
		this.documentCollection = documentCollection;
		this.relatedDocumentsFactory = relatedDocumentsFactory;
	}

	public ImmutableList<WebuiDocumentReferenceCandidate> getDocumentReferenceCandidates(
			@NonNull final DocumentPath documentPath,
			@NonNull final RelatedDocumentsPermissions permissions)
	{
		// Document with composed keys does not support references
		if (documentPath.isComposedKey())
		{
			return ImmutableList.of();
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final ImmutableList<WebuiDocumentReferenceCandidate> documentReferences = documentCollection.forDocumentReadonly(
				documentPath,
				document -> {
					if (document.isNew())
					{
						return ImmutableList.of();
					}

					final ITranslatableString filterCaption = extractFilterCaption(document, null);

					final DocumentAsZoomSource zoomSource = new DocumentAsZoomSource(document);
					return relatedDocumentsFactory
							.getRelatedDocumentsCandidates(zoomSource, permissions)
							.stream()
							.map(candidateGroup -> new WebuiDocumentReferenceCandidate(candidateGroup, filterCaption))
							.collect(ImmutableList.toImmutableList());
				});
		stopwatch.stop();

		logger.debug("Fetched initial document references stream for {} in {}", documentPath, stopwatch);

		return documentReferences;
	}

	public Optional<DocumentFilter> getDocumentReferenceFilter(
			@NonNull final DocumentPath sourceDocumentPath,
			@NonNull final WindowId targetWindowId,
			@Nullable final WebuiDocumentReferenceId documentReferenceId,
			@NonNull final RelatedDocumentsPermissions permissions)
	{
		return getDocumentReference(sourceDocumentPath, targetWindowId, documentReferenceId, permissions)
				.map(WebuiDocumentReference::getFilter);
	}

	private Optional<WebuiDocumentReference> getDocumentReference(
			@NonNull final DocumentPath sourceDocumentPath,
			@NonNull final WindowId targetWindowId,
			@Nullable final WebuiDocumentReferenceId documentReferenceId,
			@NonNull final RelatedDocumentsPermissions permissions)
	{
		return documentCollection.forDocumentReadonly(sourceDocumentPath, sourceDocument -> {
			if (sourceDocument.isNew())
			{
				//throw new AdempiereException("New documents cannot be referenced: " + sourceDocument);
				return Optional.empty();
			}

			final DocumentAsZoomSource zoomSource = new DocumentAsZoomSource(sourceDocument);
			return relatedDocumentsFactory.retrieveRelatedDocuments(
							zoomSource,
							targetWindowId.toAdWindowId(),
							documentReferenceId != null ? documentReferenceId.toRelatedDocumentsId() : null,
							permissions)
					.map(relatedDocument -> toWebuiDocumentReference(relatedDocument, sourceDocument));
		});
	}

	private WebuiDocumentReference toWebuiDocumentReference(final RelatedDocuments relatedDocuments, final Document sourceDocument)
	{
		final ITranslatableString filterCaption = extractFilterCaption(sourceDocument, relatedDocuments);
		return WebuiDocumentReferenceCandidate.toDocumentReference(relatedDocuments, filterCaption);
	}

	private ITranslatableString extractFilterCaption(
			@NonNull final Document sourceDocument,
			@Nullable final RelatedDocuments relatedDocuments)
	{
		final TranslatableStringBuilder result = TranslatableStrings.builder();

		//
		// Filter by Field Name / fallback to source window name
		if (relatedDocuments != null
				&& !TranslatableStrings.isBlank(relatedDocuments.getFilterByFieldCaption()))
		{
			result.append(relatedDocuments.getFilterByFieldCaption());
		}
		else
		{
			result.append(sourceDocument.getEntityDescriptor().getCaption());
		}

		result.append(": ");

		//
		// Document info
		// TODO: i think we shall use lookup to fetch the document description
		// final ITranslatableString documentSummary;
		if (sourceDocument.hasField(WindowConstants.FIELDNAME_DocumentSummary))
		{
			final String documentSummaryStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_DocumentSummary).getValueAs(String.class);
			result.append(" ").append(documentSummaryStr);
		}
		else if (sourceDocument.hasField(WindowConstants.FIELDNAME_DocumentNo))
		{
			final String documentNoStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_DocumentNo).getValueAs(String.class);
			result.append(" ").append(documentNoStr);
		}
		else if (sourceDocument.hasField(WindowConstants.FIELDNAME_Name))
		{
			final String nameStr = sourceDocument.getFieldView(WindowConstants.FIELDNAME_Name).getValueAs(String.class);
			result.append(" ").append(nameStr);
		}
		else
		{
			result.append(" ").append(sourceDocument.getDocumentId().toString());
		}

		return result.build();
	}

	private static final class DocumentAsZoomSource implements IZoomSource
	{
		private final Properties ctx;
		private final Evaluatee evaluationContext;

		private final AdWindowId adWindowId;
		private final int adTableId;
		private final int recordId;
		private final String keyColumnName;
		private final Document document;

		@Getter
		private final boolean genericZoomOrigin;

		@Getter
		private final String tableName;

		private DocumentAsZoomSource(@NonNull final Document document)
		{
			ctx = document.getCtx();
			this.document = document;
			evaluationContext = document.asEvaluatee();

			final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
			adWindowId = entityDescriptor.getWindowId().toAdWindowId();
			tableName = entityDescriptor.getTableName();

			adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			recordId = document.getDocumentId().toInt();
			keyColumnName = extractSingleKeyColumnNameOrNull(entityDescriptor);

			genericZoomOrigin = extractGenericZoomOrigin(tableName, keyColumnName);
		}

		@Nullable
		private static String extractSingleKeyColumnNameOrNull(final DocumentEntityDescriptor entityDescriptor)
		{
			final DocumentFieldDescriptor idField = entityDescriptor.getSingleIdFieldOrNull();
			if (idField == null)
			{
				return null;
			}

			final DocumentFieldDataBindingDescriptor idFieldBinding = idField.getDataBinding().orElse(null);
			if (idFieldBinding == null)
			{
				return null;
			}

			return idFieldBinding.getColumnName();
		}

		private boolean extractGenericZoomOrigin(
				@NonNull final String tableName,
				@Nullable final String keyColumnName)
		{
			if (keyColumnName != null)
			{
				final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
				return adTableDAO.getMinimalColumnInfo(tableName, keyColumnName).isGenericZoomOrigin();
			}
			return false;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("tableName", tableName)
					.add("recordId", recordId)
					.add("AD_Window_ID", adWindowId)
					.toString();
		}

		@Override
		public Properties getCtx()
		{
			return ctx;
		}

		@Override
		public String getTrxName()
		{
			return ITrx.TRXNAME_ThreadInherited;
		}

		@Override
		public AdWindowId getAD_Window_ID()
		{
			return adWindowId;
		}

		@Override
		public int getAD_Table_ID()
		{
			return adTableId;
		}

		@Override
		public String getKeyColumnNameOrNull()
		{
			return keyColumnName;
		}

		@Override
		public int getRecord_ID()
		{
			return recordId;
		}

		@Override
		public Evaluatee createEvaluationContext()
		{
			return evaluationContext;
		}

		@Override
		public boolean hasField(final String columnName)
		{
			return document.hasField(columnName);
		}

		@Override
		public Object getFieldValue(final String columnName)
		{
			return document.getFieldView(columnName).getValue();
		}

		@Override
		public boolean getFieldValueAsBoolean(final String columnName)
		{
			return document.getFieldView(columnName).getValueAsBoolean();
		}
	}
}
