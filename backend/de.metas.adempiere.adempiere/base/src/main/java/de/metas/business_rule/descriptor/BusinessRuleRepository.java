package de.metas.business_rule.descriptor;

import de.metas.business_rule.descriptor.interceptor.AD_BusinessRule_Trigger;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.cache.ICacheResetListener;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_BusinessRule;
import org.compiere.model.I_AD_BusinessRule_Precondition;
import org.compiere.model.I_AD_BusinessRule_Trigger;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessRuleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, BusinessRulesCollection> cache = CCache.<Integer, BusinessRulesCollection>builder()
			.tableName(I_AD_BusinessRule.Table_Name)
			.additionalTableNameToResetFor(I_AD_BusinessRule_Precondition.Table_Name)
			.additionalTableNameToResetFor(I_AD_BusinessRule_Trigger.Table_Name)
			.build();

	public void addCacheResetListener(@NonNull final BusinessRulesChangedListener listener)
	{
		final ICacheResetListener cacheResetListener = (request) -> {
			listener.onRulesChanged();
			return 1L;
		};

		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.addCacheResetListener(I_AD_BusinessRule.Table_Name, cacheResetListener);
		cacheMgt.addCacheResetListener(I_AD_BusinessRule_Precondition.Table_Name, cacheResetListener);
		cacheMgt.addCacheResetListener(I_AD_BusinessRule_Trigger.Table_Name, cacheResetListener);
	}

	public BusinessRulesCollection getAll()
	{
		return cache.getOrLoad(0, this::retrieveAll);
	}

	private BusinessRulesCollection retrieveAll() {return newLoader().retrieveAll();}

	private BusinessRuleLoader newLoader()
	{
		return BusinessRuleLoader.builder()
				.queryBL(queryBL)
				.build();
	}

	public void validate(final I_AD_BusinessRule record) {newLoader().validate(record);}

	public void validate(final I_AD_BusinessRule_Precondition record) {newLoader().validate(record);}

	public void validate(final I_AD_BusinessRule_Trigger record) {newLoader().validate(record);}
}
