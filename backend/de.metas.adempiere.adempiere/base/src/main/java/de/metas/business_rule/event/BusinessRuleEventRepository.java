package de.metas.business_rule.event;

import de.metas.business_rule.descriptor.model.BusinessRuleAndTriggerId;
import de.metas.business_rule.log.BusinessRuleLogger;
import de.metas.error.AdIssueId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_BusinessRule_Event;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.function.UnaryOperator;

@Repository
@RequiredArgsConstructor
public class BusinessRuleEventRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final BusinessRuleLogger logger;

	public void create(@NonNull final BusinessRuleEventCreateRequest request)
	{
		final String sql = "INSERT INTO " + I_AD_BusinessRule_Event.Table_Name + " ("
				+ I_AD_BusinessRule_Event.COLUMNNAME_AD_BusinessRule_ID // 1
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_AD_BusinessRule_Trigger_ID //2
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_Source_Table_ID //3
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_Source_Record_ID //4
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_AD_Client_ID //5
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_AD_Org_ID //6
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_Created //7
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_CreatedBy //8
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_Updated // 9
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_UpdatedBy // 10
				+ "," + I_AD_BusinessRule_Event.COLUMNNAME_IsActive // 11
				+ ")"
				+ " VALUES (?, ?, ?, ?, ?, ?, now(), 0, now(), 0, 'Y')"
				+ " ON CONFLICT DO NOTHING";

		DB.executeUpdateAndThrowExceptionOnFail(
				sql,
				new Object[] {
						request.getBusinessRuleId(), // 1
						request.getTriggerId(), // 2
						request.getRecordRef().getAD_Table_ID(), // 3
						request.getRecordRef().getRecord_ID(), // 4
						request.getClientAndOrgId().getClientId().getRepoId(), // 5
						request.getClientAndOrgId().getOrgId().getRepoId(), //6
				},
				ITrx.TRXNAME_ThreadInherited);
	}

	public void updateAllNotProcessed(@NonNull final UnaryOperator<BusinessRuleEvent> updater, @NonNull final QueryLimit limit)
	{
		final String processingTag = UUID.randomUUID().toString();
		final int count = tagForProcessing(processingTag, limit);
		if (count <= 0)
		{
			//logger.debug("No events to update. Skip processing.");
			return;
		}

		try
		{
			int countUpdate = queryBL.createQueryBuilder(I_AD_BusinessRule_Event.class)
					.addEqualsFilter(I_AD_BusinessRule_Event.COLUMNNAME_ProcessingTag, processingTag)
					.orderBy(I_AD_BusinessRule_Event.COLUMNNAME_AD_BusinessRule_Event_ID)
					.create()
					.update(record -> {
						final BusinessRuleEvent event = fromRecord(record);
						final BusinessRuleEvent updatedEvent = updater.apply(event);
						if (updatedEvent == null || updatedEvent.equals(event))
						{
							return IQueryUpdater.MODEL_SKIPPED;
						}

						updateRecord(record, updatedEvent);
						return IQueryUpdater.MODEL_UPDATED;
					});

			logger.debug("Updated {} events", countUpdate);
		}
		finally
		{
			releaseTag(processingTag);
		}
	}

	private int tagForProcessing(final String newProcessingTag, final QueryLimit limit)
	{
		return queryBL.createQueryBuilder(I_AD_BusinessRule_Event.class)
				.addEqualsFilter(I_AD_BusinessRule_Event.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_AD_BusinessRule_Event.COLUMNNAME_ProcessingTag, null)
				.setLimit(limit)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_AD_BusinessRule_Event.COLUMNNAME_ProcessingTag, newProcessingTag)
				.execute();
	}

	private void releaseTag(final String processingTag)
	{
		queryBL.createQueryBuilder(I_AD_BusinessRule_Event.class)
				.addEqualsFilter(I_AD_BusinessRule_Event.COLUMNNAME_ProcessingTag, processingTag)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_AD_BusinessRule_Event.COLUMNNAME_ProcessingTag, null)
				.execute();
	}

	private static BusinessRuleEvent fromRecord(final I_AD_BusinessRule_Event record)
	{
		return BusinessRuleEvent.builder()
				.id(BusinessRuleEventId.ofRepoId(record.getAD_BusinessRule_Event_ID()))
				.sourceRecordRef(TableRecordReference.of(record.getSource_Table_ID(), record.getSource_Record_ID()))
				.businessRuleAndTriggerId(BusinessRuleAndTriggerId.ofRepoIds(record.getAD_BusinessRule_ID(), record.getAD_BusinessRule_Trigger_ID()))
				.processed(record.isProcessed())
				.errorId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
				.build();
	}

	private static void updateRecord(final I_AD_BusinessRule_Event record, final BusinessRuleEvent from)
	{
		record.setProcessed(from.isProcessed());
		record.setAD_Issue_ID(AdIssueId.toRepoId(from.getErrorId()));
	}
}
