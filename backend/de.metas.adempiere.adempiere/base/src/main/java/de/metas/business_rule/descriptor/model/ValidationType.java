package de.metas.business_rule.descriptor.model;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_AD_BusinessRule_Precondition;

@RequiredArgsConstructor
@Getter
public enum ValidationType implements ReferenceListAwareEnum
{
	SQL(X_AD_BusinessRule_Precondition.PRECONDITIONTYPE_SQL),
	ValidationRule(X_AD_BusinessRule_Precondition.PRECONDITIONTYPE_ValidationRule),
	;

	private static final ValuesIndex<ValidationType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static ValidationType ofCode(@NonNull final String code) {return index.ofCode(code);}
}
