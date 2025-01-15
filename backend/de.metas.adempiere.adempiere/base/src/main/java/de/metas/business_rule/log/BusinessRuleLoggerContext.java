package de.metas.business_rule.log;

import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRuleTriggerId;
import de.metas.business_rule.event.BusinessRuleEventId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@Value
@Builder(toBuilder = true, builderMethodName = "_builder")
public class BusinessRuleLoggerContext
{
	public static final BusinessRuleLoggerContext ROOT = _builder().isRootContext(true).build();

	boolean isRootContext;

	@Nullable String moduleName;
	@Nullable BusinessRuleId businessRuleId;
	@Nullable BusinessRuleTriggerId triggerId;
	@Nullable TableRecordReference sourceRecordRef;
	@Nullable @With TableRecordReference targetRecordRef;
	@Nullable BusinessRuleEventId eventId;

	public BusinessRuleLoggerContextBuilder newChild()
	{
		return toBuilder().isRootContext(false);
	}

	public BusinessRuleLoggerContext newChild(@NonNull final Consumer<BusinessRuleLoggerContextBuilder> updater)
	{
		final BusinessRuleLoggerContextBuilder childContextBuilder = newChild();
		updater.accept(childContextBuilder);
		return childContextBuilder.build();
	}
}
