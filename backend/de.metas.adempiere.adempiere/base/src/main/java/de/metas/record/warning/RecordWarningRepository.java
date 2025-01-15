package de.metas.record.warning;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Record_Warning;
import org.springframework.stereotype.Repository;

@Repository
public class RecordWarningRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createOrUpdate(@NonNull RecordWarningCreateRequest request)
	{
		final I_AD_Record_Warning record = toSqlQuery(RecordWarningQuery.builder()
				.recordRef(request.getRecordRef())
				.businessRuleId(request.getBusinessRuleId())
				.build())
				.create()
				.firstOnlyOptional(I_AD_Record_Warning.class)
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_AD_Record_Warning.class));

		record.setAD_Table_ID(request.getRecordRef().getAD_Table_ID());
		record.setRecord_ID(request.getRecordRef().getRecord_ID());
		record.setAD_BusinessRule_ID(request.getBusinessRuleId().getRepoId());
		record.setMsgText(request.getMessage());
		InterfaceWrapperHelper.save(record);
	}

	public void deleteByRecordRef(@NonNull RecordWarningQuery query)
	{
		toSqlQuery(query).create().delete();
	}

	private IQueryBuilder<I_AD_Record_Warning> toSqlQuery(final RecordWarningQuery query)
	{
		final IQueryBuilder<I_AD_Record_Warning> queryBuilder = queryBL.createQueryBuilder(I_AD_Record_Warning.class);

		final TableRecordReference recordRef = query.getRecordRef();
		queryBuilder.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_Record_ID, recordRef.getRecord_ID());

		if (query.getBusinessRuleId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_AD_BusinessRule_ID, query.getBusinessRuleId());
		}

		return queryBuilder;
	}
}
