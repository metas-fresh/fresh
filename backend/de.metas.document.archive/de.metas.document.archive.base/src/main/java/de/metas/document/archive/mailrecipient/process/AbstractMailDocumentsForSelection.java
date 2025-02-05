package de.metas.document.archive.mailrecipient.process;

import de.metas.async.Async_Constants;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.async.spi.impl.MailWorkpackageProcessor;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.document.archive.process.ProblemCollector;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.ProcessInfo;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.MutableInt;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Contains basic utility BL needed to create processes which send mails for given selection.
 */
public abstract class AbstractMailDocumentsForSelection extends JavaProcess
{
	private static final AdMessageKey MSG_No_DocOutboundLog_Selection = AdMessageKey.of("AbstractMailDocumentsForSelection.No_DocOutboundLog_Selection");

	private static final AdMessageKey MSG_EMPTY_AD_Archive_ID = AdMessageKey.of("SendMailsForSelection.EMPTY_AD_Archive_ID");

	private static final String PARA_OnlyNotSentMails = "OnlyNotSentMails";

	// services
	private final transient IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final transient IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(parameterName = PARA_OnlyNotSentMails, mandatory=true)
	private boolean p_OnlyNotSentMails = false;

	@Override
	protected final void prepare()
	{
		final IQueryFilter<I_C_Doc_Outbound_Log> filter = getFilter();
		final PInstanceId pinstanceId = getPinstanceId();

		//
		// Create selection for PInstance and make sure we're enqueuing something
		final int selectionCount = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class, this)
				.addOnlyActiveRecordsFilter()
				.filter(filter)
				.create()
				.createSelection(pinstanceId);

		if (selectionCount == 0)
		{
			final ITranslatableString translatableMsgText = msgBL.getTranslatableMsgText(MSG_No_DocOutboundLog_Selection);
			throw new AdempiereException(translatableMsgText);
		}
	}

	protected IQueryFilter<I_C_Doc_Outbound_Log> getFilter()
	{
		final ProcessInfo pi = getProcessInfo();
		final IQueryFilter<I_C_Doc_Outbound_Log> selectedRecordsFilter = pi.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		return queryBL
				.createCompositeQueryFilter(I_C_Doc_Outbound_Log.class)
				.addNotNull(I_C_Doc_Outbound_Log.COLUMNNAME_CurrentEMailAddress)
				.addFilter(selectedRecordsFilter);

	}

	@Override
	protected final String doIt() throws Exception
	{
		final PInstanceId pinstanceId = getPinstanceId();

		// Enqueue selected archives as workpackages
		final Stream<I_C_Doc_Outbound_Log_Line> docOutboundLines = getPDFArchiveDocOutboundLinesForSelection(pinstanceId);

		final MutableInt counter = MutableInt.zero();

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(getCtx(), MailWorkpackageProcessor.class);

		docOutboundLines.forEach(docOutboundLogLine -> {
			queue
					.newWorkPackage()
					.setAD_PInstance_ID(pinstanceId)
					// .bindToThreadInheritedTrx() // let's start as soon as the workpackage is created
					.addElement(docOutboundLogLine)
					.buildAndEnqueue();

			counter.incrementAndGet();
		});

		return msgBL.getMsg(getCtx(), Async_Constants.MSG_WORKPACKAGES_CREATED, new Object[] { counter.getValue() });
	}

	private Stream<I_C_Doc_Outbound_Log_Line> getPDFArchiveDocOutboundLinesForSelection(final PInstanceId pinstanceId)
	{
		final Stream<I_C_Doc_Outbound_Log> logsIterator = retrieveSelectedDocOutboundLogs(pinstanceId);

		final ProblemCollector collector = new ProblemCollector();

		return logsIterator
				.map(this::retrieveDocumentLogLine)
				.filter(Objects::nonNull)
				.filter(docOutboundLogLine -> isEmailSendable(docOutboundLogLine, collector));
	}

	private Stream<I_C_Doc_Outbound_Log> retrieveSelectedDocOutboundLogs(final PInstanceId pinstanceId)
	{
		final Iterator<I_C_Doc_Outbound_Log> iterator = queryBL
				.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.setOnlySelection(pinstanceId)
				.create()
				.iterate(I_C_Doc_Outbound_Log.class);

		final Iterable<I_C_Doc_Outbound_Log> iterable = () -> iterator;
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	/**
	 * To be overridden where necessary
	 *
	 * @return document log line
	 */
	protected I_C_Doc_Outbound_Log_Line retrieveDocumentLogLine(final I_C_Doc_Outbound_Log log)
	{
		return docOutboundDAO.retrieveCurrentPDFArchiveLogLineOrNull(log);
	}

	private boolean isEmailSendable(
			@NonNull final I_C_Doc_Outbound_Log_Line logLine,
			@NonNull final ProblemCollector collector)
	{
		//
		// Note that the problems here come in cascade, that's why we're returning instead of keeping collecting

		//
		// Log line must have an archive to be viable
		if (logLine.getAD_Archive_ID() <= 0)
		{
			final I_C_Doc_Outbound_Log docOutBoundLogRecord = logLine.getC_Doc_Outbound_Log();
			Loggables.addLog(msgBL.getMsg(getCtx(), MSG_EMPTY_AD_Archive_ID, new Object[] { docOutBoundLogRecord.getDocumentNo() }));
			collector.collectException(MSG_EMPTY_AD_Archive_ID.toAD_Message(), docOutBoundLogRecord.getDocumentNo());
			return false;
		}

		//
		// Do not enqueue if the log was sent at least once
		if (p_OnlyNotSentMails && isSentAtLeastOnce(logLine))
		{
			return false; // don't collect this, just silently skip it
		}

		return true;
	}

	private boolean isSentAtLeastOnce(final I_C_Doc_Outbound_Log_Line docOutboundLine)
	{
		final I_C_Doc_Outbound_Log log = docOutboundLine.getC_Doc_Outbound_Log();
		return log.getEMailCount() > 0;
	}
}
