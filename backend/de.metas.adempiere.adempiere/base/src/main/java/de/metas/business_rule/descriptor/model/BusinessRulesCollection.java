package de.metas.business_rule.descriptor.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@EqualsAndHashCode
@ToString(of = "list")
public class BusinessRulesCollection
{
	public final static BusinessRulesCollection EMPTY = new BusinessRulesCollection(ImmutableList.of());

	@NonNull private final ImmutableList<BusinessRule> list;
	@NonNull private final ImmutableMap<BusinessRuleId, BusinessRule> byId;
	@NonNull private final ImmutableListMultimap<AdTableId, BusinessRuleAndTrigger> byTriggerTableId;

	BusinessRulesCollection(List<BusinessRule> list)
	{
		this.list = ImmutableList.copyOf(list);
		this.byId = Maps.uniqueIndex(list, BusinessRule::getId);
		this.byTriggerTableId = indexByTriggerTableId(list);
	}

	private static ImmutableListMultimap<AdTableId, BusinessRuleAndTrigger> indexByTriggerTableId(final List<BusinessRule> list)
	{
		return list.stream()
				.flatMap(rule -> rule.getTriggers()
						.stream()
						.map(trigger -> BusinessRuleAndTrigger.of(rule, trigger)))
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						BusinessRuleAndTrigger::getTriggerTableId,
						ruleAndTrigger -> ruleAndTrigger));
	}

	public static BusinessRulesCollection ofList(List<BusinessRule> list)
	{
		return !list.isEmpty() ? new BusinessRulesCollection(list) : EMPTY;
	}

	public ImmutableSet<AdTableId> getTriggerTableIds() {return byTriggerTableId.keySet();}

	public ImmutableList<BusinessRuleAndTrigger> getByTriggerTableId(@NonNull final AdTableId triggerTableId) {return this.byTriggerTableId.get(triggerTableId);}

	public BusinessRule getById(final BusinessRuleId id)
	{
		final BusinessRule rule = byId.get(id);
		if (rule == null)
		{
			throw new AdempiereException("No rule found for id=" + id);
		}
		return rule;
	}
}
