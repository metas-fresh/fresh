package de.metas.business_rule.descriptor.interceptor;

import de.metas.business_rule.descriptor.BusinessRuleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_BusinessRule;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_BusinessRule.class)
@Component
@RequiredArgsConstructor
public class AD_BusinessRule
{
	@NonNull private final BusinessRuleRepository ruleRepository;
	
	@ModelChange(types = { ModelChangeType.BEFORE_NEW, ModelChangeType.BEFORE_CHANGE })
	public void beforeSave(final I_AD_BusinessRule record)
	{
		ruleRepository.validate(record);
	}
}
