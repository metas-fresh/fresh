/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.view;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.references.related_documents.RelatedDocumentsPermissionsFactory;
import de.metas.logging.LogManager;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilter.DocumentFilterBuilder;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.document.geo_location.GeoLocationDocumentService;
import de.metas.ui.web.document.references.WebuiDocumentReferenceId;
import de.metas.ui.web.document.references.service.WebuiDocumentReferencesService;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewBindingFactory;
import de.metas.ui.web.view.descriptor.SqlViewCustomizerMap;
import de.metas.ui.web.view.descriptor.SqlViewKeyColumnNamesMap;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayoutFactory;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * View factory which is based on {@link DocumentEntityDescriptor} having SQL repository.<br>
 * Creates {@link DefaultView}s with are backed by a {@link SqlViewBinding}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
public class SqlViewFactory implements IViewFactory
{
	private static final Logger logger = LogManager.getLogger(SqlViewFactory.class);
	private final WebuiDocumentReferencesService webuiDocumentReferencesService;
	private final ViewLayoutFactory viewLayouts;
	private final CompositeDefaultViewProfileIdProvider defaultProfileIdProvider;
	private final ViewHeaderPropertiesProviderMap headerPropertiesProvider;

	public SqlViewFactory(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final WebuiDocumentReferencesService webuiDocumentReferencesService,
			@NonNull final List<SqlViewCustomizer> viewCustomizersList,
			@NonNull final List<DefaultViewProfileIdProvider> defaultViewProfileIdProviders,
			@NonNull final Optional<List<ViewHeaderPropertiesProvider>> headerPropertiesProvider,
			@NonNull final Optional<List<SqlDocumentFilterConverter>> filterConverters,
			@NonNull final Optional<List<SqlDocumentFilterConverterDecorator>> filterConverterDecorators,
			@NonNull final List<IViewInvalidationAdvisor> viewInvalidationAdvisors,
			@NonNull final GeoLocationDocumentService geoLocationDocumentService)
	{
		this.webuiDocumentReferencesService = webuiDocumentReferencesService;

		final SqlViewCustomizerMap viewCustomizers = SqlViewCustomizerMap.ofCollection(viewCustomizersList);
		logger.info("View customizers: {}", viewCustomizers);

		this.defaultProfileIdProvider = makeDefaultProfileIdProvider(defaultViewProfileIdProviders, viewCustomizers);
		logger.info("Default ProfileId providers: {}", this.defaultProfileIdProvider);

		final SqlViewBindingFactory viewBindingsFactory = SqlViewBindingFactory.builder()
				.documentDescriptorFactory(documentDescriptorFactory)
				.viewCustomizers(viewCustomizers)
				.filterConverters(filterConverters.orElseGet(ImmutableList::of))
				.filterConverterDecorators(filterConverterDecorators.orElseGet(ImmutableList::of))
				.viewInvalidationAdvisors(viewInvalidationAdvisors)
				.build();

		this.viewLayouts = ViewLayoutFactory.builder()
				.documentDescriptorFactory(documentDescriptorFactory)
				.viewBindingsFactory(viewBindingsFactory)
				.viewCustomizers(viewCustomizers)
				.geoLocationDocumentService(geoLocationDocumentService)
				.build();

		this.headerPropertiesProvider = ViewHeaderPropertiesProviderMap.of(headerPropertiesProvider);
	}

	private static CompositeDefaultViewProfileIdProvider makeDefaultProfileIdProvider(
			final List<DefaultViewProfileIdProvider> providers,
			final SqlViewCustomizerMap viewCustomizersToExtractFallbacks)
	{
		final CompositeDefaultViewProfileIdProvider result = CompositeDefaultViewProfileIdProvider.of(providers);
		viewCustomizersToExtractFallbacks.forEachWindowIdAndProfileId(result::setDefaultProfileIdFallbackIfAbsent);
		return result;
	}

	@Override
	public List<ViewProfile> getAvailableProfiles(final WindowId windowId)
	{
		return viewLayouts.getAvailableProfiles(windowId);
	}

	public void setDefaultProfileId(@NonNull final WindowId windowId, final ViewProfileId profileId)
	{
		defaultProfileIdProvider.setDefaultProfileIdOverride(windowId, profileId);
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			@Nullable final ViewProfileId profileId)
	{
		final ViewProfileId profileIdEffective = !ViewProfileId.isNull(profileId) ? profileId : defaultProfileIdProvider.getDefaultProfileIdByWindowId(windowId);
		return viewLayouts.getViewLayout(windowId, viewDataType, profileIdEffective);
	}

	@Override
	public DefaultView createView(final @NonNull CreateViewRequest request)
	{
		final WindowId windowId = request.getViewId().getWindowId();

		final JSONViewDataType viewType = request.getViewType();
		final ViewProfileId profileId = !ViewProfileId.isNull(request.getProfileId()) ? request.getProfileId() : defaultProfileIdProvider.getDefaultProfileIdByWindowId(windowId);
		final SqlViewBinding sqlViewBinding = viewLayouts.getViewBinding(windowId, viewType.getRequiredFieldCharacteristic(), profileId);
		final SqlViewDataRepository viewDataRepository = new SqlViewDataRepository(sqlViewBinding);

		final DefaultView.Builder viewBuilder = DefaultView.builder(viewDataRepository)
				.setViewId(request.getViewId())
				.setViewType(viewType)
				.setProfileId(profileId)
				.setHeaderPropertiesProvider(headerPropertiesProvider.getProvidersByTableName(sqlViewBinding.getTableName()))
				.setReferencingDocumentPaths(request.getReferencingDocumentPaths())
				.setDocumentReferenceId(request.getDocumentReferenceId())
				.setParentViewId(request.getParentViewId())
				.setParentRowId(request.getParentRowId())
				.addStickyFilters(request.getStickyFilters())
				.addStickyFilterSkipDuplicates(extractReferencedDocumentFilter(
						windowId,
						request.getSingleReferencingDocumentPathOrNull(),
						request.getDocumentReferenceId()))
				.applySecurityRestrictions(request.isApplySecurityRestrictions())
				.viewInvalidationAdvisor(sqlViewBinding.getViewInvalidationAdvisor())
				.refreshViewOnChangeEvents(sqlViewBinding.isRefreshViewOnChangeEvents());

		final DocumentFilterList filters = request.getFiltersUnwrapped(viewDataRepository.getViewFilterDescriptors());
		viewBuilder.setFilters(filters);

		if (request.isUseAutoFilters())
		{
			final List<DocumentFilter> autoFilters = createAutoFilters(sqlViewBinding.getViewFilterDescriptors().getAll());
			viewBuilder.addFiltersIfAbsent(autoFilters);
		}

		if (!request.getFilterOnlyIds().isEmpty())
		{
			final String keyColumnName = sqlViewBinding.getSqlViewKeyColumnNamesMap().getSingleKeyColumnName();
			viewBuilder.addStickyFilterSkipDuplicates(DocumentFilter.inArrayFilter(keyColumnName, keyColumnName, request.getFilterOnlyIds()));
		}

		return viewBuilder.build();
	}

	@Nullable
	private DocumentFilter extractReferencedDocumentFilter(
			@NonNull final WindowId targetWindowId,
			@Nullable final DocumentPath referencedDocumentPath,
			@Nullable final WebuiDocumentReferenceId documentReferenceId)
	{
		if (referencedDocumentPath == null)
		{
			return null;
		}
		else if (referencedDocumentPath.isComposedKey())
		{
			// document with composed keys does not support references
			return null;
		}
		else
		{
			return webuiDocumentReferencesService.getDocumentReferenceFilter(
							referencedDocumentPath,
							targetWindowId,
							documentReferenceId,
							RelatedDocumentsPermissionsFactory.allowAll())
					.orElseThrow(() -> new AdempiereException("No related documents found")
							.setParameter("targetWindowId", targetWindowId)
							.setParameter("referencedDocumentPath", referencedDocumentPath)
							.setParameter("documentReferenceId", documentReferenceId));
		}
	}

	/*
	 * Iterates the given {@code filters} and invokes this factory's createAutoFilter method on each one.
	 */
	public static List<DocumentFilter> createAutoFilters(@NonNull final Collection<DocumentFilterDescriptor> filters)
	{
		return filters
				.stream()
				.filter(DocumentFilterDescriptor::isAutoFilter)
				.map(SqlViewFactory::createAutoFilter)
				.collect(ImmutableList.toImmutableList());
	}

	private static DocumentFilter createAutoFilter(final DocumentFilterDescriptor filterDescriptor)
	{
		if (!filterDescriptor.isAutoFilter())
		{
			throw new AdempiereException("Not an auto filter: " + filterDescriptor);
		}

		final DocumentFilterBuilder filterBuilder = DocumentFilter.builder()
				.setFilterId(filterDescriptor.getFilterId());

		filterDescriptor.getParameters()
				.stream()
				.filter(DocumentFilterParamDescriptor::isAutoFilter)
				.map(SqlViewFactory::createAutoFilterParam)
				.forEach(filterBuilder::addParameter);

		return filterBuilder.build();
	}

	private static DocumentFilterParam createAutoFilterParam(final DocumentFilterParamDescriptor filterParamDescriptor)
	{
		final Object value;
		if (filterParamDescriptor.isAutoFilterInitialValueIsDateNow())
		{
			final DocumentFieldWidgetType widgetType = filterParamDescriptor.getWidgetType();
			if (widgetType == DocumentFieldWidgetType.LocalDate)
			{
				value = SystemTime.asLocalDate();
			}
			else
			{
				value = de.metas.common.util.time.SystemTime.asZonedDateTime();
			}
		}
		else if (filterParamDescriptor.isAutoFilterInitialValueIsCurrentLoggedUser())
		{
			// FIXME: we shall get the current logged user or context as parameter
			final UserId loggedUserId = Env.getLoggedUserId();

			value = filterParamDescriptor.getLookupDataSource()
					.get() // we assume we always have a lookup data source
					.findById(loggedUserId);
		}
		else
		{
			value = filterParamDescriptor.getAutoFilterInitialValue();
		}

		return DocumentFilterParam.builder()
				.setFieldName(filterParamDescriptor.getFieldName())
				.setOperator(Operator.EQUAL)
				.setValue(value)
				.build();
	}

	@Override
	public DefaultView filterView(
			@NonNull final IView view,
			@NonNull final JSONFilterViewRequest filterViewRequest,
			@NonNull final Supplier<IViewsRepository> viewsRepo_NOTUSED)
	{
		return filterView(DefaultView.cast(view), filterViewRequest);
	}

	private DefaultView filterView(
			@NonNull final DefaultView view,
			@NonNull final JSONFilterViewRequest filterViewRequest)
	{
		final DocumentFilterDescriptorsProvider filterDescriptors = view.getViewDataRepository().getViewFilterDescriptors();
		final DocumentFilterList newFilters = filterViewRequest.getFiltersUnwrapped(filterDescriptors);
		// final DocumentFilterList newFiltersExcludingFacets = newFilters.retainOnlyNonFacetFilters();
		//
		// final DocumentFilterList currentFiltersExcludingFacets = view.getFilters().retainOnlyNonFacetFilters();
		//
		// if (DocumentFilterList.equals(currentFiltersExcludingFacets, newFiltersExcludingFacets))
		// {
		// // TODO
		// throw new AdempiereException("TODO");
		// }
		// else
		{
			return createView(CreateViewRequest.filterViewBuilder(view)
					.setFilters(newFilters)
					.build());
		}
	}

	public SqlViewKeyColumnNamesMap getKeyColumnNamesMap(@NonNull final WindowId windowId)
	{
		final SqlViewBinding sqlBindings = viewLayouts.getViewBinding(windowId, DocumentFieldDescriptor.Characteristic.PublicField, ViewProfileId.NULL);
		return sqlBindings.getSqlViewKeyColumnNamesMap();
	}

}
