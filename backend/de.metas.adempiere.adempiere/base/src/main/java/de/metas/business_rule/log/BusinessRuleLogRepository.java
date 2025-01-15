package de.metas.business_rule.log;

import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRuleTriggerId;
import de.metas.business_rule.event.BusinessRuleEventId;
import de.metas.error.AdIssueId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_BusinessRule_Log;
import org.springframework.stereotype.Repository;

@Repository
class BusinessRuleLogRepository
{
	public void create(final BusinessRuleLogEntryRequest request)
	{
		final I_AD_BusinessRule_Log record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_BusinessRule_Log.class);
		record.setLevel(request.getLevel().toString());
		record.setMsgText(request.getMessage());
		record.setAD_Issue_ID(AdIssueId.toRepoId(request.getErrorId()));

		if (request.getDuration() != null)
		{
			record.setDurationMillis((int)request.getDuration().toMillis());
		}

		record.setModule(request.getModuleName());
		record.setAD_BusinessRule_ID(BusinessRuleId.toRepoId(request.getBusinessRuleId()));
		record.setAD_BusinessRule_Trigger_ID(BusinessRuleTriggerId.toRepoId(request.getTriggerId()));

		final TableRecordReference sourceRecordRef = request.getSourceRecordRef();
		record.setSource_Table_ID(sourceRecordRef != null ? sourceRecordRef.getAD_Table_ID() : -1);
		record.setSource_Record_ID(sourceRecordRef != null ? sourceRecordRef.getRecord_ID() : -1);

		final TableRecordReference targetRecordRef = request.getTargetRecordRef();
		record.setTarget_Table_ID(targetRecordRef != null ? targetRecordRef.getAD_Table_ID() : -1);
		record.setTarget_Record_ID(targetRecordRef != null ? targetRecordRef.getRecord_ID() : -1);

		record.setAD_BusinessRule_Event_ID(BusinessRuleEventId.toRepoId(request.getEventId()));

		InterfaceWrapperHelper.save(record);
	}
}
