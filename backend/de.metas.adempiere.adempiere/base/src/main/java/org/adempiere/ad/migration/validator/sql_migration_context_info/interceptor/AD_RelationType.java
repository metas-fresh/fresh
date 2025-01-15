package org.adempiere.ad.migration.validator.sql_migration_context_info.interceptor;

import de.metas.ad_reference.ReferenceId;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.validator.sql_migration_context_info.names.Names;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_RelationType;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_RelationType.class)
@Component
public class AD_RelationType
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void logSqlMigrationContextInfo(final I_AD_RelationType record)
	{
		if (MigrationScriptFileLoggerHolder.isDisabled())
		{
			return;
		}

		MigrationScriptFileLoggerHolder.logComment("Name: " + record.getName());
		MigrationScriptFileLoggerHolder.logComment("Source Reference: " + getReferenceName(record.getAD_Reference_Source_ID()));
		MigrationScriptFileLoggerHolder.logComment("Target Reference: " + getReferenceName(record.getAD_Reference_Target_ID()));
	}

	private static String getReferenceName(final int AD_Reference_ID)
	{
		final ReferenceId referenceId = ReferenceId.ofRepoIdOrNull(AD_Reference_ID);
		return referenceId == null ? "-" : Names.ADReferenceName_Loader.retrieve(referenceId);
	}
}
