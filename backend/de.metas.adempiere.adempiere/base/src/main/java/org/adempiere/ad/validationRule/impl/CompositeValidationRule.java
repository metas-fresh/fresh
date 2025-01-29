package org.adempiere.ad.validationRule.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.GuavaCollectors;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.NamePairPredicates;
import org.compiere.util.ValueNamePair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

/**
 * Immutable composite validation rule consist of a collection of child validation rules.
 *
 * @author tsa
 */
public final class CompositeValidationRule implements IValidationRule
{
	public static IValidationRule compose(final IValidationRule rule1, final IValidationRule rule2)
	{
		return builder()
				.add(rule1)
				.add(rule2)
				.build();
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static Collector<IValidationRule, ?, IValidationRule> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(CompositeValidationRule::ofList);
	}

	public static IValidationRule ofList(final List<IValidationRule> rules)
	{
		return builder().addAll(rules).build();
	}

	private final List<IValidationRule> rules;
	private final boolean immutable;
	private final IStringExpression prefilterWhereClause;
	private final INamePairPredicate postQueryPredicates;
	private final ImmutableSet<String> dependsOnTableNames;

	private Set<String> _allParameters; // lazy

	private CompositeValidationRule(final Builder builder)
	{
		super();
		rules = ImmutableList.copyOf(builder.rules); // at this point, we assume that we have more than one rule
		immutable = builder.immutable;
		prefilterWhereClause = builder.buildPrefilterWhereClause();
		postQueryPredicates = builder.postQueryPredicates.build();
		dependsOnTableNames = rules.stream()
				.flatMap(rule -> rule.getDependsOnTableNames().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	private List<IValidationRule> getValidationRules()
	{
		return rules;
	}

	public boolean isEmpty()
	{
		return rules.isEmpty();
	}

	/**
	 * @return true if all child validation rules are immutable
	 */
	@Override
	public boolean isImmutable()
	{
		return immutable;
	}

	@Override
	public Set<String> getAllParameters()
	{
		if (_allParameters == null)
		{
			_allParameters = ImmutableSet.<String>builder()
					.addAll(prefilterWhereClause.getParameterNames())
					.addAll(postQueryPredicates.getParameters())
					.build();
		}
		return _allParameters;
	}

	@Override
	public IStringExpression getPrefilterWhereClause()
	{
		return prefilterWhereClause;
	}

	@Override
	public INamePairPredicate getPostQueryFilter()
	{
		return postQueryPredicates;
	}

	@Override
	public List<ValueNamePair> getExceptionTableAndColumns()
	{
		final List<ValueNamePair> exceptionTableAndColumns = new ArrayList<>();

		for (final IValidationRule rule : rules)
		{
			exceptionTableAndColumns.addAll(rule.getExceptionTableAndColumns());
		}

		return exceptionTableAndColumns;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(rules)
				.toString();
	}

	@Override
	public Set<String> getDependsOnTableNames()
	{
		return dependsOnTableNames;
	}

	@SuppressWarnings("UnusedReturnValue")
	public static final class Builder
	{
		private final List<IValidationRule> rules = new ArrayList<>();
		private final NamePairPredicates.Composer postQueryPredicates = NamePairPredicates.compose();
		private boolean immutable = true;

		private Builder()
		{
			super();
		}

		public IValidationRule build()
		{
			if (rules.isEmpty())
			{
				return NullValidationRule.instance;
			}
			else if (rules.size() == 1)
			{
				return rules.get(0);
			}
			else
			{
				return new CompositeValidationRule(this);
			}
		}

		private IStringExpression buildPrefilterWhereClause()
		{
			final CompositeStringExpression.Builder builder = CompositeStringExpression.builder();
			for (final IValidationRule rule : rules)
			{
				final IStringExpression ruleWhereClause = rule.getPrefilterWhereClause();
				if (ruleWhereClause == null || ruleWhereClause == IStringExpression.NULL)
				{
					continue;
				}

				builder.appendIfNotEmpty(" AND ");
				builder.append("(").append(ruleWhereClause).append(")");
			}

			return builder.build();
		}

		public Builder add(final IValidationRule rule)
		{
			final boolean explodeComposite = false;
			add(rule, explodeComposite);
			return this;
		}

		public Builder addExploded(final IValidationRule rule)
		{
			final boolean explodeComposite = true;
			add(rule, explodeComposite);
			return this;
		}

		private Builder add(final IValidationRule rule, final boolean explodeComposite)
		{
			// Don't add null rules
			if (NullValidationRule.isNull(rule))
			{
				return this;
			}

			// Don't add if already exists
			if (rules.contains(rule))
			{
				return this;
			}

			if (explodeComposite && rule instanceof CompositeValidationRule)
			{
				final CompositeValidationRule compositeRule = (CompositeValidationRule)rule;
				addAll(compositeRule.getValidationRules(), true);
			}
			else
			{
				rules.add(rule);
				immutable = immutable && rule.isImmutable();
				postQueryPredicates.add(rule.getPostQueryFilter());
			}

			return this;
		}

		private Builder addAll(final Collection<IValidationRule> rules)
		{
			return addAll(rules, false);
		}

		private Builder addAll(final Collection<IValidationRule> rules, final boolean explodeComposite)
		{
			rules.forEach(includedRule -> add(includedRule, explodeComposite));
			return this;
		}
	}
}
