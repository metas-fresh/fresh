package org.adempiere.ad.migration.logger;

import com.google.common.collect.ImmutableSet;
import de.metas.dao.selection.model.I_T_Query_Selection;
import de.metas.dao.selection.model.I_T_Query_Selection_ToDelete;
import de.metas.dataentry.model.I_I_DataEntry_Record;
import de.metas.logging.LogManager;
import de.metas.process.model.I_AD_PInstance_SelectedIncludedRecords;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_AD_Attachment_Log;
import org.compiere.model.I_AD_Attachment_MultiRef;
import org.compiere.model.I_AD_BusinessRule_Event;
import org.compiere.model.I_AD_Column_Access;
import org.compiere.model.I_AD_Document_Action_Access;
import org.compiere.model.I_AD_Form_Access;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_Preference;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.model.I_AD_Process_Stats;
import org.compiere.model.I_AD_Table_Access;
import org.compiere.model.I_AD_Task_Access;
import org.compiere.model.I_AD_Window_Access;
import org.compiere.model.I_AD_Workflow_Access;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.model.I_API_Request_Audit_Log;
import org.compiere.model.I_API_Response_Audit;
import org.compiere.model.I_C_DataImport_Run;
import org.compiere.model.I_I_Asset;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.I_I_BPartner_GlobalID;
import org.compiere.model.I_I_Campaign_Price;
import org.compiere.model.I_I_Conversion_Rate;
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.model.I_I_ElementValue;
import org.compiere.model.I_I_FAJournal;
import org.compiere.model.I_I_GLJournal;
import org.compiere.model.I_I_InOutLineConfirm;
import org.compiere.model.I_I_Inventory;
import org.compiere.model.I_I_Invoice;
import org.compiere.model.I_I_Order;
import org.compiere.model.I_I_Payment;
import org.compiere.model.I_I_Postal;
import org.compiere.model.I_I_PriceList;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_I_Replenish;
import org.compiere.model.I_I_ReportLine;
import org.compiere.model.I_I_Request;
import org.compiere.model.I_I_User;
import org.compiere.model.I_Mobile_Application_Access;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Set;

public class TableNamesSkipList
{
	private static final Logger logger = LogManager.getLogger(TableNamesSkipList.class);

	/**
	 * Upper case list of table names that shall be ignored when creating migration scripts logged as System
	 */
	@NonNull private ImmutableSet<String> _tablesIgnoreSystemUC;

	/**
	 * Upper case list of table names that shall be ignored when creating migration scripts logged as regular tenant/AD_Client_ID
	 */
	@NonNull private ImmutableSet<String> _tablesIgnoreClientUC;

	private static final ImmutableSet<String> DEFAULT_IGNORE_LIST = ImmutableSet.of(
			"AD_ACCESSLOG",
			"AD_ALERTPROCESSORLOG",
			I_AD_Attachment.Table_Name,
			I_AD_Attachment_Log.Table_Name,
			I_AD_Attachment_MultiRef.Table_Name,
			I_AD_AttachmentEntry.Table_Name,
			"AD_CHANGELOG",
			I_AD_BusinessRule_Event.Table_Name,
			"AD_ISSUE",
			I_AD_Note.Table_Name,
			"AD_PACKAGE_IMP",
			"AD_PACKAGE_IMP_BACKUP",
			"AD_PACKAGE_IMP_DETAIL",
			"AD_PACKAGE_IMP_INST",
			"AD_PACKAGE_IMP_PROC",
			I_AD_Process_Stats.Table_Name,
			"AD_PINSTANCE",
			"AD_PINSTANCE_LOG",
			"AD_PINSTANCE_PARA",
			I_AD_Preference.Table_Name,
			"AD_REPLICATION_LOG",
			"AD_SCHEDULERLOG",
			"AD_SESSION",
			"AD_WORKFLOWPROCESSORLOG",
			I_API_Request_Audit.Table_Name,
			I_API_Request_Audit_Log.Table_Name,
			I_API_Response_Audit.Table_Name,
			"CM_WEBACCESSLOG",
			"K_INDEXLOG",
			"R_REQUESTPROCESSORLOG",
			"T_AGING",
			// "T_ALTER_COLUMN", // this one NEEDs to be logged
			"T_DISTRIBUTIONRUNDETAIL",
			"T_INVENTORYVALUE",
			"T_INVOICEGL",
			"T_REPLENISH",
			"T_REPORT",
			"T_REPORTSTATEMENT",
			"T_SELECTION",
			"T_SELECTION2",
			I_T_Query_Selection.Table_Name,
			I_T_Query_Selection_ToDelete.Table_Name,
			"T_SPOOL",
			"T_TRANSACTION",
			"T_TRIALBALANCE",
			//
			"AD_VISIBLEFIELDS", // metas: tsa: US762
			"AD_ROLE_PERMREQUEST", // metas: ts: Ma01_US1057
			//
			// Don't log migration scripts tables - 02662
			"AD_MIGRATION",
			"AD_MIGRATIONSTEP",
			"AD_MIGRATIONDATA",

			// Don't log import tables
			I_C_DataImport_Run.Table_Name,
			I_I_Asset.Table_Name,
			I_I_BPartner.Table_Name,
			I_I_BPartner_GlobalID.Table_Name,
			I_I_Campaign_Price.Table_Name,
			I_I_Conversion_Rate.Table_Name,
			I_I_DataEntry_Record.Table_Name,
			I_I_DiscountSchema.Table_Name,
			I_I_ElementValue.Table_Name,
			I_I_FAJournal.Table_Name,
			I_I_GLJournal.Table_Name,
			I_I_InOutLineConfirm.Table_Name,
			I_I_Inventory.Table_Name,
			I_I_Invoice.Table_Name,
			I_I_Order.Table_Name,
			I_I_Payment.Table_Name,
			I_I_Postal.Table_Name,
			I_I_PriceList.Table_Name,
			I_I_Product.Table_Name,
			I_I_Replenish.Table_Name,
			I_I_ReportLine.Table_Name,
			I_I_Request.Table_Name,
			I_I_User.Table_Name,

			// Don't log AD_Sequence because these will be created automatically (at least for Table_ID)
			// NOTE: while this is applying for XML migrations, we need this to be logged in SQL migrations
			// and currently we use SQL migrations
			// FIXME: find a way to fine tune this
			// "AD_SEQUENCE"

			I_AD_PInstance_SelectedIncludedRecords.Table_Name // https://github.com/metasfresh/metasfresh-webui-api/issues/645
	);

	public TableNamesSkipList()
	{
		this._tablesIgnoreSystemUC = this._tablesIgnoreClientUC = addTableNamesUC(ImmutableSet.of(), DEFAULT_IGNORE_LIST);

		//
		// Do not log *Access records - teo_Sarca BF [ 2782095 ]
		// NOTE: Only if we are running as system. If user is logged in regular Tenant, we want to log them (07122)
		this._tablesIgnoreSystemUC = addTableNamesUC(this._tablesIgnoreSystemUC, ImmutableSet.of(
						I_AD_Window_Access.Table_Name,
						I_AD_Process_Access.Table_Name,
						I_AD_Workflow_Access.Table_Name,
						I_AD_Form_Access.Table_Name,
						I_AD_Task_Access.Table_Name,
						I_AD_Document_Action_Access.Table_Name,
						I_AD_Table_Access.Table_Name,
						I_AD_Column_Access.Table_Name,
						I_Mobile_Application_Access.Table_Name
				)
		);

		logger.info("Skip migration scripts logging for (init system): {}", this._tablesIgnoreSystemUC);
	}

	public boolean isLogTableName(final String tableName, final ClientId clientId)
	{
		final Set<String> tablesToIgnoreUC = getTablesToIgnoreUC(clientId);
		return !tablesToIgnoreUC.contains(tableName.toUpperCase());
	}

	public void addTablesToIgnoreList(@NonNull final String... tableNames)
	{
		addTablesToIgnoreList(ImmutableSet.copyOf(tableNames));
	}

	public synchronized void addTablesToIgnoreList(@NonNull final Collection<String> tableNames)
	{
		if (tableNames.isEmpty())
		{
			return;
		}

		this._tablesIgnoreSystemUC = addTableNamesUC(this._tablesIgnoreSystemUC, tableNames);
		this._tablesIgnoreClientUC = addTableNamesUC(this._tablesIgnoreClientUC, tableNames);

		logger.info("Skip migration scripts logging for: {}", tableNames);
	}

	public ImmutableSet<String> getTablesToIgnoreUC(final ClientId clientId)
	{
		return clientId.isSystem() ? _tablesIgnoreSystemUC : _tablesIgnoreClientUC;
	}

	private static ImmutableSet<String> addTableNamesUC(
			@NonNull final ImmutableSet<String> targetListUC,
			@NonNull final Collection<String> tableNamesToAdd)
	{
		if (tableNamesToAdd.isEmpty())
		{
			return targetListUC;
		}

		final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
		builder.addAll(targetListUC);
		tableNamesToAdd.stream().map(String::toUpperCase).forEach(builder::add);
		final ImmutableSet<String> result = builder.build();
		if (targetListUC.size() == result.size())
		{
			return targetListUC; // no change
		}

		return result;
	}
}
