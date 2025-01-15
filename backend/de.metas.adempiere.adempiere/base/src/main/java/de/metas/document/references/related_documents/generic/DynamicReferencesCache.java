package de.metas.document.references.related_documents.generic;

import com.google.common.collect.ImmutableSet;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.compiere.model.I_AD_Issue;
import org.springframework.stereotype.Service;

@Service
class DynamicReferencesCache
{
	private final ITableRecordIdDAO tableRecordIdDAO = Services.get(ITableRecordIdDAO.class);
	
	private static final ImmutableSet<String> TABLENAMES_TO_AVOID_CACHING = ImmutableSet.<String>builder()
			.add(I_AD_Issue.Table_Name) // because it's often written => often cache reset
			.build();

	// we have no actual cache here, because tableRecordIdDAO has one.
	// private final CCache<String, TableInfo> byTableName = CCache.<String, TableInfo>builder()
	
	public OptionalBoolean hasReferences(@NonNull final String tableName, @NonNull final String referencedTableName)
	{
		if (TABLENAMES_TO_AVOID_CACHING.contains(tableName))
		{
			return OptionalBoolean.UNKNOWN;
		}
		return OptionalBoolean.ofBoolean(retrieveTableInfo(tableName).containsReferencedTableName(referencedTableName));
	}

	private TableInfo retrieveTableInfo(@NonNull final String tableName)
	{
		final ImmutableSet<String> referencedTableNames = tableRecordIdDAO.getTableRecordIdReferences(tableName)
				.stream()
				.map(TableRecordIdDescriptor::getTargetTableName)
				.collect(ImmutableSet.toImmutableSet());

		return TableInfo.builder()
				.tableName(tableName)
				.referencedTableNames(referencedTableNames)
				.build();
	}

	@Value
	@Builder
	private static class TableInfo
	{
		@NonNull String tableName;
		@NonNull ImmutableSet<String> referencedTableNames;

		public boolean containsReferencedTableName(@NonNull final String referencedTableName) {return referencedTableNames.contains(referencedTableName);}
	}
}
