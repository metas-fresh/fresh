package de.metas.business_rule.descriptor.model;

import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder
public class Validation
{
	@NonNull ValidationType type;
	@Nullable IStringExpression sql;
	@Nullable AdValRuleId adValRuleId;

	public static Validation sql(@NonNull final String sql)
	{
		final String sqlNorm = StringUtils.trimBlankToNull(sql);
		if (sqlNorm == null)
		{
			throw new AdempiereException("SQL validation rule shall not be empty");
		}

		return new Validation(ValidationType.SQL, IStringExpression.compile(sqlNorm), null);
	}

	public static Validation adValRule(@NonNull final AdValRuleId adValRuleId)
	{
		return new Validation(ValidationType.ValidationRule, null, adValRuleId);
	}
}
