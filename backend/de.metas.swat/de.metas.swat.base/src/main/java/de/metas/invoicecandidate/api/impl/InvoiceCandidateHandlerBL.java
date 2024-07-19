package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import ch.qos.logback.classic.Level;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.inout.ShipmentScheduleId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.OnInvalidateForModelAction;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockAutoCloseable;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService.SpanMetadata;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.workflow.api.IWFExecutionFactory;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.compiere.SpringContextHolder;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class InvoiceCandidateHandlerBL implements IInvoiceCandidateHandlerBL
{
	private static final Object NO_MODEL = new Object()
	{
		@Override
		public String toString()
		{
			return getClass().getName() + "_NO_MODEL";
		}
	};

	private static final transient Logger logger = InvoiceCandidate_Constants.getLogger(InvoiceCandidateHandlerBL.class);

	@Override
	public List<IInvoiceCandidateHandler> retrieveImplementationsForTable(final Properties ctx, final String tableName)
	{
		final List<IInvoiceCandidateHandler> result = new ArrayList<>();

		final List<I_C_ILCandHandler> handlerRecordsForTable = Services.get(IInvoiceCandidateHandlerDAO.class).retrieveForTable(ctx, tableName);
		for (final I_C_ILCandHandler record : handlerRecordsForTable)
		{
			result.add(mkInstance(record));
		}
		return result;
	}

	@Override
	public void evalClassName(final I_C_ILCandHandler ilCandGenerator, final boolean failIfClassNotFound)
	{
		if (Check.isEmpty(ilCandGenerator.getClassname()))
		{
			ilCandGenerator.setTableName(null);
			return;
		}

		final IInvoiceCandidateHandler handlerClass;
		try
		{
			handlerClass = mkInstance(ilCandGenerator);
		}
		catch (final AdempiereException e)
		{
			if (failIfClassNotFound)
			{
				throw e;
			}
			ilCandGenerator.setTableName(null);
			return;
		}

		ilCandGenerator.setTableName(handlerClass.getSourceTable());
		ilCandGenerator.setIs_AD_User_InCharge_UI_Setting(handlerClass.isUserInChargeUserEditable());
	}

	@Override
	public IInvoiceCandidateHandler mkInstance(final I_C_ILCandHandler handlerRecord)
	{
		final IInvoiceCandidateHandler instance = Util.getInstance(IInvoiceCandidateHandler.class, handlerRecord.getClassname());
		instance.setHandlerRecord(handlerRecord);

		return instance;
	}

	@Override
	public void createMissingCandidates(@NonNull final List<I_C_ILCandHandler> handlerRecords)
	{
		Services.get(ITrxManager.class).runInNewTrx(() -> createInvoiceCandidates(handlerRecords, InvoiceCandidateHandlerBL.NO_MODEL));
	}

	@Override
	public List<I_C_Invoice_Candidate> createMissingCandidatesFor(@NonNull final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);

		final List<I_C_ILCandHandler> icCandHandlers = Services.get(IInvoiceCandidateHandlerDAO.class).retrieveForTable(ctx, tableName);
		return createInvoiceCandidates(icCandHandlers, model);
	}

	@Override
	public void scheduleCreateMissingCandidatesFor(final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);

		final List<IInvoiceCandidateHandler> handlersForTable = retrieveImplementationsForTable(ctx, tableName);
		for (final IInvoiceCandidateHandler handler : handlersForTable)
		{
			scheduleCreateMissingCandidatesFor(model, handler);
		}
	}

	/**
	 * Schedule invoice candidates generation for given model (asynchronously).
	 */
	private void scheduleCreateMissingCandidatesFor(
			@NonNull final Object model,
			@NonNull final IInvoiceCandidateHandler handler)
	{
		final Object modelToSchedule = handler.getModelForInvoiceCandidateGenerateScheduling(model);
		CreateMissingInvoiceCandidatesWorkpackageProcessor.schedule(modelToSchedule);
	}

	/**
	 * This method does the actual invoice candidate creation by calling the given <code>creatorRecords</code>.
	 * <p>
	 * Note that each <code>creatorRecord</code> is called multiple times, until it returns the empty list.<br>
	 * That way it is possible to for a creator to create only a limited number of invoice candidates at a time and thus avoid memory issues.
	 *
	 * @param model may be <code>{@link #NO_MODEL}</code>. If it not {@link #NO_MODEL}, then the method only created missing candidates for the given model.
	 * @return if model is <code>{@link #NO_MODEL}</code>, then we return the empty list. If not, then we return the created invoice candidates.
	 */
	private List<I_C_Invoice_Candidate> createInvoiceCandidates(
			@NonNull final List<I_C_ILCandHandler> handlerRecords,
			final Object model)
	{
		final List<I_C_Invoice_Candidate> result = new ArrayList<>();
		if (handlerRecords.isEmpty())
		{
			logger.warn("No C_ILCandHandler were provided for '{}'. Nothing to do.", model);
		}

		// services
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ILoggable loggable = Loggables.getLoggableOrLogger(logger, Level.INFO);

		for (final I_C_ILCandHandler handlerRecord : handlerRecords)
		{
			loggable.addLog("Invoking handler: " + handlerRecord.getName() + " (" + handlerRecord + ")");
			try
			{
				final IInvoiceCandidateHandler creatorImpl = mkInstance(handlerRecord);

				// HARDCODED BufferSize/Limit to be used when we are creating missing candidates
				final QueryLimit bufferSize = QueryLimit.ofInt(500);

				List<I_C_Invoice_Candidate> newCandidates = createCandidates(model, bufferSize, creatorImpl);
				final int candidatesCount = newCandidates.size();
				while (!newCandidates.isEmpty())
				{
					if (Util.same(model, InvoiceCandidateHandlerBL.NO_MODEL))
					{
						trxManager.commit(ITrx.TRXNAME_ThreadInherited);
					}
					else
					{
						result.addAll(newCandidates);
						break;
					}

					newCandidates = createCandidates(model, bufferSize, creatorImpl);
				}

				loggable.addLog("Handler " + handlerRecord.getName() + ": @Created@ #" + candidatesCount);
			}
			catch (final RuntimeException e)
			{
				// log the error, but go on
				final Throwable rootCause = Throwables.getRootCause(e);
				final String errmsg = "Caught " + (rootCause != null ? rootCause : e).getClass()
						+ " calling createMissingCandidates() method of handler " + handlerRecord.getName() + " (class " + handlerRecord.getClassname() + ") : "
						+ ExceptionUtils.getRootCauseMessage(e);
				loggable.addLog(errmsg, e);

				logger.warn(errmsg, e);
				throw e; // rethrow. that ways, at least the trx is rolled back
			}

		}

		if (Util.same(model, InvoiceCandidateHandlerBL.NO_MODEL))
		{
			Check.assume(result.isEmpty(), "Internal error: result shall be empty");
			trxManager.commit(ITrx.TRXNAME_ThreadInherited);
		}

		return result;
	}

	/**
	 * Create candidates. If model is {@link #NO_MODEL} then all missing candidates will be created.
	 *
	 * @param modelOrNoModel if set to a value != {@link #NO_MODEL}, then only candidates for the given model are created,
	 * @param bufferSize     used only when creating missing candidates.
	 * @return created candidates
	 */
	private List<I_C_Invoice_Candidate> createCandidates(
			final Object modelOrNoModel,
			final QueryLimit bufferSize,
			final IInvoiceCandidateHandler invoiceCandiateHandler)
	{
		//
		// Retrieve actual models for whom we will generate invoice candidates
		final Iterator<?> models;
		if (Util.same(modelOrNoModel, InvoiceCandidateHandlerBL.NO_MODEL))
		{
			models = invoiceCandiateHandler.retrieveAllModelsWithMissingCandidates(bufferSize);
		}
		else
		{
			models = Iterators.singletonIterator(modelOrNoModel);
		}

		//
		// Locking
		final LockOwner lockOwner = LockOwner.newOwner(getClass().getSimpleName() + "#generateInvoiceCandidates");

		//
		// Iterate retrieved models and generate invoice candidates for them
		final List<I_C_Invoice_Candidate> invoiceCandidatesAll = new ArrayList<>();
		while (models.hasNext())
		{
			final Object model = models.next();
			invoiceCandidatesAll.addAll(createForModel(model, lockOwner, invoiceCandiateHandler));
		}

		// Invalidate all generated invoice candidates in one run.
		invalidateNewCandidates(invoiceCandidatesAll);

		return invoiceCandidatesAll;
	}

	private ImmutableList<I_C_Invoice_Candidate> createForModel(
			final Object model,
			final LockOwner lockOwner,
			final IInvoiceCandidateHandler invoiceCandiateHandler)
	{
		final PerformanceMonitoringService performanceMonitoringService = SpringContextHolder.instance.getBean(PerformanceMonitoringService.class);
		final SpanMetadata request = SpanMetadata.builder()
				.type("createMissingInvoiceCandidates")
				.name("createMissingInvoiceCandidatesForModel")
				.build();
		return performanceMonitoringService.monitorSpan(() -> createForModel0(model, lockOwner, invoiceCandiateHandler), request);

	}

	private ImmutableList<I_C_Invoice_Candidate> createForModel0(
			final Object model,
			final LockOwner lockOwner,
			final IInvoiceCandidateHandler invoiceCandiateHandler)
	{
			if (!invoiceCandiateHandler.getSpecificCandidatesAutoCreateMode(model).isDoSomething())
		{
			return ImmutableList.of();
		}

		final ILockManager lockManager = Services.get(ILockManager.class);

		//
		// Create the initial request and then ask the handler to expand it to proper models to be used.
		final InvoiceCandidateGenerateRequest requestInitial = InvoiceCandidateGenerateRequest.of(invoiceCandiateHandler, model);
		final List<InvoiceCandidateGenerateRequest> requests = invoiceCandiateHandler.expandRequest(requestInitial);

		final ImmutableList.Builder<I_C_Invoice_Candidate> invoiceCandidatesAll = ImmutableList.builder();

		//
		// Iterate each request and generate the invoice candidates
		for (final InvoiceCandidateGenerateRequest request : requests)
		{
			// Lock the "model" to make sure nobody else would generate invoice candidates for it.
			final ILock lock = lockManager.lock()
					.setOwner(lockOwner)
					.setRecordByModel(model)
					.setAutoCleanup(true)
					.setFailIfAlreadyLocked(true)
					.acquire();

			try (final ILockAutoCloseable ignored = lock.asAutoCloseable())
			{
				final IInvoiceCandidateHandler handler = request.getHandler();
				final InvoiceCandidateGenerateResult result = handler.createCandidatesFor(request);

				// Update generated invoice candidates
				updateDefaultsAndSave(result);

				// Collect candidates (we will invalidate them all together)
				invoiceCandidatesAll.addAll(result.getC_Invoice_Candidates());
			}
			catch (final Exception ex)
			{
				Loggables.withWarnLoggerToo(logger)
						.addLog("Caught {} while trying to create candidate for request={} with requestInitial={}",
								ex.getClass(), request, requestInitial, ex);

				throw AdempiereException.wrapIfNeeded(ex);
			}
		}

		return invoiceCandidatesAll.build();
	}

	/**
	 * Make sure all candidates are persisted to database. It also:
	 * <ul>
	 * <li>{@link I_C_Invoice_Candidate#setC_ILCandHandler(I_C_ILCandHandler)}
	 * <li>{@link I_C_Invoice_Candidate#setAD_User_InCharge_ID(int)}
	 * </ul>
	 *
	 * @param result invoice candidate generate result
	 */
	private void updateDefaultsAndSave(final InvoiceCandidateGenerateResult result)
	{
		final IInvoiceCandidateHandler handler = result.getHandler();

		for (final I_C_Invoice_Candidate ic : result.getC_Invoice_Candidates())
		{
			updateDefaultsAndSaveSingleCandidate(handler, ic);
		}
	}

	private void updateDefaultsAndSaveSingleCandidate(
			@NonNull final IInvoiceCandidateHandler handler,
			@NonNull final I_C_Invoice_Candidate ic)
	{
		Check.assumeNotNull(handler, "handler not null");

		//
		// Make sure there is a link to creator/handler.
		// We are setting the handler only if it was not set because it might be that the handler was set by a delegated handler which is not this one.
		final I_C_ILCandHandler handlerDef = handler.getHandlerRecord();
		if (handlerDef != null && ic.getC_ILCandHandler_ID() <= 0)
		{
			ic.setC_ILCandHandler(handlerDef);
		}

		// Make sure User InCharge is set
		if (ic.getAD_User_InCharge_ID() <= 0)
		{
			final int adUserInChargeId = handler.getAD_User_InCharge_ID(ic);
			ic.setAD_User_InCharge_ID(adUserInChargeId);
		}

		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class); // not having this as field bc there might be problems with circular dependencies
		invoiceCandBL.setPaymentTermIfMissing(ic);

		// Save it
		InterfaceWrapperHelper.save(ic);

		// task 05791: notify the system whenever this new ic references an existing PO
		final Object fromModel = TableRecordCacheLocal.getReferencedValue(ic, Object.class);
		if (fromModel != null)
		{
			Services.get(IWFExecutionFactory.class).notifyActivityPerformed( // 03745
																			 fromModel,
																			 ic);
		}
	}

	private void invalidateNewCandidates(final List<I_C_Invoice_Candidate> candidates)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		// group them by
		// * HeaderAggregationKey
		// * Bill_BPartner_ID
		// * AD_Table_ID
		// * Record_ID
		// add one representative for each combination to our map 'key2IC'
		final Map<ArrayKey, I_C_Invoice_Candidate> headerAndPartnerKey2IC = new HashMap<>();
		final Map<ArrayKey, I_C_Invoice_Candidate> referencedRecordKey2IC = new HashMap<>();
		for (final I_C_Invoice_Candidate ic : candidates)
		{
			final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
			final OnInvalidateForModelAction onInvalidateForModelAction = handler.getOnInvalidateForModelAction();
			if (onInvalidateForModelAction == OnInvalidateForModelAction.RECREATE_ASYNC)
			{
				// just plainly invalidate the actual IC at hand.
				invoiceCandDAO.invalidateCand(ic);
				continue;
			}

			final ArrayKey headerAndPartnerKey = Util.mkKey(ic.getHeaderAggregationKey(), ic.getBill_BPartner_ID());
			if (!headerAndPartnerKey2IC.containsKey(headerAndPartnerKey))
			{
				// we done have such a representative yet, now add it
				headerAndPartnerKey2IC.put(headerAndPartnerKey, ic);
			}

			final ArrayKey referencedRecordKey = Util.mkKey(ic.getAD_Table_ID(), ic.getRecord_ID());
			if (!referencedRecordKey2IC.containsKey(headerAndPartnerKey))
			{
				// we done have such a representative yet, now add it
				referencedRecordKey2IC.put(referencedRecordKey, ic);
			}
		}

		// now call the api for each representative, assuming that this way *all* candidates that need it are invalidated, but not each one a thousand times.
		for (final I_C_Invoice_Candidate ic : headerAndPartnerKey2IC.values())
		{
			invoiceCandBL.invalidateForPartnerIfInvoiceRuleDemandsIt(ic);
		}

		for (final I_C_Invoice_Candidate ic : referencedRecordKey2IC.values())
		{
			final Object model = TableRecordCacheLocal.getReferencedValue(ic, Object.class);
			createInvoiceCandidateHandler(ic).invalidateCandidatesFor(model);
		}
	}

	/**
	 * Create an instance of {@link IInvoiceCandidateHandler} implementation that can handle given invoice candidate.
	 *
	 * @return handler instance
	 */
	private IInvoiceCandidateHandler createInvoiceCandidateHandler(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);
		final I_C_ILCandHandler handler = invoiceCandidateHandlerDAO.retrieveFor(ic);
		return mkInstance(handler);
	}

	@Override
	public void setNetAmtToInvoice(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setNetAmtToInvoice(ic);
	}

	@Override
	public void setLineNetAmt(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setLineNetAmt(ic);
	}

	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setOrderedData(ic);
	}

	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setDeliveredData(ic);
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);

		final List<IInvoiceCandidateHandler> handlersForTable = retrieveImplementationsForTable(ctx, tableName);
		for (final IInvoiceCandidateHandler handler : handlersForTable)
		{
			final OnInvalidateForModelAction onInvalidateForModelAction = handler.getOnInvalidateForModelAction();
			switch (onInvalidateForModelAction)
			{
				case RECREATE_ASYNC:
					scheduleCreateMissingCandidatesFor(model, handler);
					break;
				case REVALIDATE:
					handler.invalidateCandidatesFor(model);
					break;
				default:
					// nothing
					logger.warn("Got no OnInvalidateForModelAction for " + model + ". Doing nothing.");
					break;
			}
		}
	}

	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		return handler.calculatePriceAndTax(ic);
	}

	@Override
	public void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setBPartnerData(ic);
	}

	@Override
	public void setInvoiceScheduleAndDateToInvoice(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(icRecord);
		handler.setInvoiceScheduleAndDateToInvoice(icRecord);
	}

	@Override
	public void setPickedData(final I_C_Invoice_Candidate ic)
	{
		final InvoiceCandidateRecordService invoiceCandidateRecordService = SpringContextHolder.instance.getBean(InvoiceCandidateRecordService.class);

		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setShipmentSchedule(ic);

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(ic.getM_ShipmentSchedule_ID());

		if (shipmentScheduleId == null)
		{
			return;
		}

		final StockQtyAndUOMQty qtysPicked = invoiceCandidateRecordService.ofRecord(ic).computeQtysPicked();

		ic.setQtyPicked(qtysPicked.getStockQty().toBigDecimal());
		ic.setQtyPickedInUOM(qtysPicked.getUOMQtyNotNull().toBigDecimal());
	}
}
