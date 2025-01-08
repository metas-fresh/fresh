package de.metas.ui.web.admin;

import de.metas.cache.rest.CacheRestControllerTemplate;
import de.metas.cache.rest.JsonCacheResetRequest;
import de.metas.cache.rest.JsonCacheResetResponse;
import de.metas.cache.rest.JsonCacheStats;
import de.metas.cache.rest.JsonGetStatsResponse;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfig.ENDPOINT_ROOT + "/cache")
@RequiredArgsConstructor
public class CacheRestController extends CacheRestControllerTemplate
{
	public static final String CACHE_RESET_PARAM_forgetNotSavedDocuments = "forgetNotSavedDocuments";

	@NonNull private final UserSession userSession;
	@NonNull private final DocumentCollection documentCollection;
	@NonNull private final MenuTreeRepository menuTreeRepo;
	@NonNull private final ProcessRestController processesController;
	@NonNull private final LookupDataSourceFactory lookupDataSourceFactory;

	@Override
	protected void assertAuth() {userSession.assertLoggedIn();}

	protected void resetAdditional(@NonNull final JsonCacheResetResponse response, @NonNull final JsonCacheResetRequest request)
	{
		{
			final boolean forgetNotSavedDocuments = request.getValueAsBoolean(CACHE_RESET_PARAM_forgetNotSavedDocuments);
			final String documentsResult = documentCollection.cacheReset(forgetNotSavedDocuments);
			response.addLog("documents: " + documentsResult + " (" + CACHE_RESET_PARAM_forgetNotSavedDocuments + "=" + forgetNotSavedDocuments + ")");
		}
		{
			menuTreeRepo.cacheReset();
			response.addLog("menuTreeRepo: cache invalidated");
		}
		{
			processesController.cacheReset();
			response.addLog("processesController: cache invalidated");
		}
		{
			ViewColumnHelper.cacheReset();
			response.addLog("viewColumnHelper: cache invalidated");
		}
	}

	@GetMapping("/lookups/stats")
	public JsonGetStatsResponse getLookupCacheStats()
	{
		assertAuth();

		return lookupDataSourceFactory.getCacheStats()
				.stream()
				.sorted(DEFAULT_ORDER_BY)
				.map(JsonCacheStats::of)
				.collect(JsonGetStatsResponse.collect());
	}
}
