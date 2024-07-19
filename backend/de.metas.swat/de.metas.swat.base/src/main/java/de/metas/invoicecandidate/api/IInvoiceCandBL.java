/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.invoicecandidate.api;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.spi.ModelWithoutInvoiceCandidateVetoer;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.impl.InvoiceCandidateAllocCreateRequest;
import de.metas.invoicecandidate.api.impl.InvoiceCandidatesAmtSelectionSummary;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.money.Money;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.tax.api.Tax;
import de.metas.util.ISingletonService;
import de.metas.util.OptionalBoolean;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_InvoiceSchedule;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public interface IInvoiceCandBL extends ISingletonService
{
	void registerVetoer(ModelWithoutInvoiceCandidateVetoer vetoer, String tableName);

	boolean isAllowedToCreateInvoiceCandidateFor(Object model);

	interface IInvoiceGenerateResult
	{
		int getInvoiceCount();

		List<I_C_Invoice> getC_Invoices();

		int getNotificationCount();

		List<I_AD_Note> getNotifications();

		String getNotificationsWhereClause();

		void addInvoice(I_C_Invoice c_Invoice);

		void addNotifications(List<I_AD_Note> notifications);

		/**
		 * @param ctx context (for translation)
		 * @return result summary (using context language)
		 */
		String getSummary(Properties ctx);
	}

	/**
	 * @return new invoice candidates updater
	 */
	IInvoiceCandInvalidUpdater updateInvalid();

	/**
	 * Start generating invoices
	 *
	 * @return invoice generator
	 */
	IInvoiceGenerator generateInvoices();

	/**
	 * Creates invoices from the given selection.
	 * <p>
	 * <b>IMPORTANT:</b> Candidates with {@link I_C_Invoice_Candidate#isError()} are ignored, even if they are part of the selection!
	 */
	IInvoiceGenerateResult generateInvoicesFromSelection(Properties ctx, PInstanceId AD_PInstance_ID, boolean ignoreInvoiceSchedule, String trxName);

	/**
	 * Creates <code>de.metas.async</code> work packages from for those invoice candidates that are selected via <code>T_Selection</code> with the given <code>AD_PInstance_ID</code>.
	 */
	IInvoiceCandidateEnqueuer enqueueForInvoicing();

	/**
	 * Checks if given invoice candidate is eligible for invoicing.
	 * <p>
	 * It checks: Processed, IsError, DateToInvoice (if not <code>ignoreInvoiceSchedule</code>).
	 * <p>
	 * NOTE: This method is called both when invoice candidates are enqueued for invoicing and during the actual invoicing.
	 *
	 * @return true if the invoice candidate is NOT eligible and shall be skipped.
	 */
	boolean isSkipCandidateFromInvoicing(I_C_Invoice_Candidate ic, boolean ignoreInvoiceSchedule);

	IInvoiceGenerateResult generateInvoicesFromQueue(Properties ctx);

	void setPaymentTermIfMissing(@NonNull I_C_Invoice_Candidate icRecord);

	void setNetAmtToInvoice(I_C_Invoice_Candidate ic);

	/**
	 * Calculate actual net price.
	 */
	void setPriceActualNet(I_C_Invoice_Candidate ic);

	/**
	 * Sets the given invoice candidate's {@code PriceActual_Override} value using
	 * <ul>
	 * <li>Discount</li>
	 * <li>Discount_Override</li>
	 * <li>C_Currency_ID</li>
	 * <li>C_OrderLine.PriceEntered.</li>
	 * </ul>
	 * <p>
	 * If the given ic is manual, if it doesn't reference a C_OrderLine or if its {@code Discount} is equal to its {@code Discount_Override}, then the method does nothing.
	 */
	void setPriceActual_Override(I_C_Invoice_Candidate ic);

	ProductPrice getPriceActual(I_C_Invoice_Candidate ic);

	Percent getDiscount(I_C_Invoice_Candidate ic);

	ProductPrice getPriceEnteredEffective(I_C_Invoice_Candidate ic);

	ProductPrice getPriceEntered(I_C_Invoice_Candidate ic);

	Optional<ProductPrice> getPriceEnteredOverride(I_C_Invoice_Candidate ic);

	boolean isTaxIncluded(I_C_Invoice_Candidate ic);

	/**
	 * Gets amounts precision. Taken from currency, default 2.
	 *
	 * @return precision used to calculate amounts
	 */
	CurrencyPrecision getPrecisionFromCurrency(I_C_Invoice_Candidate ic);

	/**
	 * Invalidates those invoice candidates that reference the given invoice schedule
	 */
	void invalidateForInvoiceSchedule(I_C_InvoiceSchedule invoiceSchedule);

	/**
	 * This method updates certain fields of the given invoice candidate. It's available for invocation from the outside for the case of 'manual' invoice candidates
	 *
	 * @param ic  the candidate whose values shall be updated. It is assumed that the candidate has <code>IsManual='Y'</code>.
	 */
	void set_QtyInvoiced_NetAmtInvoiced_Aggregation(Properties ctx, I_C_Invoice_Candidate ic);

	/**
	 * @return true if given candidate is a credit memo (i.e. is manual and price actual < 0)
	 */
	boolean isCreditMemo(I_C_Invoice_Candidate cand);

	Money calculateNetAmt(I_C_Invoice_Candidate ic);

	/**
	 * Create a copy of the given {@code icRecord}, set the copy's quantities to {@code ONE}, and the copy's prices to the given given {@code icRecord}'s {@code splitAmount}.
	 *
	 * @return the newly created, but not yet saved invoice candidate record.
	 */
	I_C_Invoice_Candidate splitCandidate(I_C_Invoice_Candidate icRecord);

	InvoiceRule getInvoiceRule(I_C_Invoice_Candidate ic);

	LocalDate getDateToInvoice(I_C_Invoice_Candidate ic);

	/**
	 * Determine if the candidate has been changed manually or by the background process.<br>
	 * This information is currently used by {@link de.metas.invoicecandidate.process.C_Invoice_Candidate_Update}.
	 * <p>
	 * Used inside the invalidate code within {@link IInvoiceCandDAO}, to avoid invalidating candidates while the process validates or creates them.
	 */
	boolean isUpdateProcessInProgress();

	/**
	 * Enables "update in progress" flag and returns an {@link IAutoCloseable} to put it back to off.
	 * <p>
	 * It is important to call this method in any block where we are updating the invoice candidates and we want to avoid them to be invalidated after.
	 *
	 * @return auto closable
	 * @see #isUpdateProcessInProgress()
	 */
	IAutoCloseable setUpdateProcessInProgress();

	/**
	 * Creates initial {@link IInvoiceGenerateResult}
	 *
	 * @param shallStoreInvoices if true, a link to all invoices will be stored; if false then only some counting/aggregation info will be stored
	 * @return initial {@link IInvoiceGenerateResult} instance
	 */
	IInvoiceGenerateResult createInvoiceGenerateResult(boolean shallStoreInvoices);

	/**
	 * Retrieves or creates the invoice line allocation record for the given invoice line and invoice candidate.
	 * <p>
	 * IMPORTANT: as of now we suppose this to be the only way of creating ilas! Please don't create them yourself somewhere in the code.
	 *
	 * @param note may be null or empty. Use it to provide a user-friendly note that can be displayed to the customer admin/user
	 * @return returns the invoiceLine allocation that was created or updated never returns <code>null</code>
	 */
	I_C_Invoice_Line_Alloc createUpdateIla(InvoiceCandidateAllocCreateRequest request);

	void handleReversalForInvoice(org.compiere.model.I_C_Invoice invoice);

	/**
	 * Updates/Creates {@link I_C_Invoice_Line_Alloc}s for the case of an invoice (including credit memo) completion. Also makes sure that ICs are created on the fly if they are still missing.
	 */
	void handleCompleteForInvoice(org.compiere.model.I_C_Invoice invoice);

	/**
	 * Set the {@value I_C_Invoice_Candidate#COLUMNNAME_Processed_Calc} and <code>Processed</code> flags of the given <code>candidate</code>.<br>
	 * <code>Processed_Calc</code> can be overridden by <code>Processed_Override</code>.
	 * If it is not overridden, then the <code>Processed_Calc</code> value is copied into <code>Processed</code>.
	 * <p>
	 * The <code>Processed_Calc</code> shall be set to <code>true</code> if
	 * <ul>
	 * <li>the candidate's {@link I_C_Invoice_Candidate#COLUMN_QtyOrdered QtyOrdered} has the same amount as its {@link I_C_Invoice_Candidate#COLUMN_QtyInvoiced QtyInvoiced} <b>and</b></li>
	 * <li>there is at least one not-reversed {@link I_C_InvoiceLine} allocated to the candidate</li>
	 * </ul>
	 * The second condition is important because we might e.g. have a <code>C_OrderLine</code> with <code>QtyOrdered=0</code>, either because the order was reactivated, or because the user simply
	 * needs to document that a Qty or ZERO was ordered for a certain product. In both case we don't want the candidate to be flagged as processed.
	 * <p>
	 * Note that if <code>Processed_Override</code> is set, then its value shall be copied to <code>Processed</code>, no matter what (issue <a href="https://github.com/metasfresh/metasfresh/issues/243">#243</a>).
	 */
	void updateProcessedFlag(I_C_Invoice_Candidate candidate);

	/**
	 * Resets {@link I_C_Invoice_Candidate#COLUMNNAME_IsError} field together with some other depending fields:
	 * <ul>
	 * <li>{@link I_C_Invoice_Candidate#COLUMNNAME_AD_Note_ID}
	 * <li>{@link I_C_Invoice_Candidate#COLUMNNAME_ErrorMsg}
	 * </ul>
	 * <p>
	 * NOTE: this method is NOT saving the invoice candidate
	 *
	 * @param ic invoice candidate
	 */
	void resetError(I_C_Invoice_Candidate ic);

	/**
	 * Flags given invoice candidate as it has errors.
	 * <p>
	 * NOTE: this method is NOT saving the invoice candidate
	 *
	 * @param ic       invoice candidate
	 * @param errorMsg error message to be set
	 * @param note     error note (optional)
	 */
	void setError(I_C_Invoice_Candidate ic, String errorMsg, I_AD_Note note);

	/**
	 * See {@link #setError(I_C_Invoice_Candidate, String, I_AD_Note)}
	 *
	 * @param askForDeleteRegeneration error message will append request to the user asking him/her to delete invoice candidate after problem was fixed and wait for its regeneration
	 */
	void setError(I_C_Invoice_Candidate ic, String errorMsg, @Nullable I_AD_Note note, boolean askForDeleteRegeneration);

	void setError(I_C_Invoice_Candidate ic, Throwable e);

	void setInvoicingErrorAndSave(
			I_C_Invoice_Candidate ic,
			String errorMsg,
			I_AD_Note note);

	void clearInvoicingErrorAndSave(@NonNull I_C_Invoice_Candidate ic);

	/**
	 * Retrieve tax override if set, C_Tax otherwise
	 *
	 * @return tax override if set, C_Tax otherwise; never return null
	 */
	Tax getTaxEffective(I_C_Invoice_Candidate candidate);

	/**
	 * Get quality percent override if set, quality percent otherwise. Never returns <code>null</code>.
	 */
	// TODO kick out
	Percent getQualityDiscountPercentEffective(I_C_Invoice_Candidate candidate);

	/**
	 * Update the POReference of a candidate based on the POReference from the order.
	 * <p>
	 * For both sales and purchase orders (purchases added as of https://github.com/metasfresh/metasfresh/issues/292).
	 * <p>
	 * Candidate will not be saved.
	 */
	void updatePOReferenceFromOrder(I_C_Invoice_Candidate candidate);

	/**
	 * For the given invoice candidate, make sure that itself and all candidates partner are invalidated, <b>if</b> the partner has a certain invoice schedule.
	 */
	void invalidateForPartnerIfInvoiceRuleDemandsIt(I_C_Invoice_Candidate ic);

	/**
	 * @return today date (without time!) to be used by invoicing BLs
	 */
	LocalDate getToday();

	/**
	 * @return current QtyToInvoice_Override or QtyToInvoice
	 */
	Quantity getQtyToInvoiceStockUOM(I_C_Invoice_Candidate ic);

	/**
	 * Set the QualityDiscountPercent_Override based on the QualityIssuePercentage from the discount schema.<br>
	 * If the value does not exist, leave the field on null.
	 * <p>
	 * Note: ic not saved
	 */
	void setQualityDiscountPercent_Override(I_C_Invoice_Candidate ic, ImmutableAttributeSet attributes);

	/**
	 * Precision is taken from the current pricelist of the partner. If it is not found, it is taken from the currency as fallback
	 */
	CurrencyPrecision extractPricePrecision(I_C_Invoice_Candidate ic);

	void closeInvoiceCandidatesByOrderLineId(OrderLineId orderLineId);

	/**
	 * Close the given invoice candidate.
	 * Closing an invoice candidate means setting its Processed_Override to Y and invalidating the invoice candidate.
	 * Also close the shipment schedules on which the invoice candidates are based
	 */
	void closeInvoiceCandidate(I_C_Invoice_Candidate candidate);

	/**
	 * Iterate the candidates to close and close them one by one.
	 */
	void closeInvoiceCandidates(Iterator<I_C_Invoice_Candidate> candidatesToClose);

	/**
	 * Find out if invoice candidates with flag IsToCLear are supposed to be closed
	 * The decision is made based on the System Configuration "C_Invoice_Candidate_Close_IsToClear"
	 *
	 * @return the value of the SYS_Config if found, false by default
	 */
	boolean isCloseIfIsToClear();

	/**
	 * Find out if invoice candidates that were partially invoiced are supposed to be closed
	 * The decision is made based on the System Configuration "C_Invoice_Candidate_Close_PartiallyInvoiced"
	 *
	 * @return the value of the SYS_Config if found, false by default
	 */
	boolean isCloseIfPartiallyInvoiced(OrgId orgId);

	/**
	 * If the invoice candidates linked to an invoice have Processed_Override on true, the flag must be unset in case of invoice reversal
	 */
	void candidates_unProcess(I_C_Invoice invoice);

	/**
	 * Close linked invoice candidates if they were partially invoiced
	 * Note: This behavior is determined by the value of the sys config {@code C_Invoice_Candidate_Close_PartiallyInvoice}.
	 * The candidates will be closed only if the sys config is set to 'Y'.
	 *
	 * @see IInvoiceCandBL#isCloseIfPartiallyInvoiced(OrgId)
	 */
	void closePartiallyInvoiced_InvoiceCandidates(I_C_Invoice invoice);

	void markInvoiceCandInDisputeForReceiptLine(I_M_InOutLine inOutLine);

	void set_DateToInvoice_DefaultImpl(I_C_Invoice_Candidate ic);

	OptionalBoolean extractProcessedOverride(I_C_Invoice_Candidate candidate);

	void updateICIOLAssociationFromIOL(I_C_InvoiceCandidate_InOutLine iciol, org.compiere.model.I_M_InOutLine inOutLine);

	int createSelectionForInvoiceCandidates(List<ExternalHeaderIdWithExternalLineIds> headerAndLineIds, PInstanceId pInstanceId);

	List<I_C_Queue_WorkPackage> getUnprocessedWorkPackagesForInvoiceCandidate(InvoiceCandidateId invoiceCandidateId);

	/**
	 * @return {@code true} if the given {@code invoiceRecord} is currently created via invoicing from invoice candidates.
	 */
	boolean isCreatedByInvoicingJustNow(org.compiere.model.I_C_Invoice invoiceRecord);

	Set<InvoiceCandidateId> voidAndReturnInvoiceCandIds(org.compiere.model.I_C_Invoice invoice);

	@NonNull
	InvoiceCandidatesAmtSelectionSummary calculateAmtSelectionSummary(@Nullable String extraWhereClause);

	void setAsyncBatch(InvoiceCandidateId invoiceCandidateId, AsyncBatchId asyncBatchId);

	Quantity getQtyOrderedStockUOM(I_C_Invoice_Candidate ic);

	Quantity getQtyInvoicedStockUOM(I_C_Invoice_Candidate ic);

	/**
	 * @param useDefaultBillLocationAndContactIfNotOverride if true and not override-location&contact is given, then take the *current* masterdata values instead of the ic's values. This is actually an invoicing-feature.
	 */
	BPartnerLocationAndCaptureId getBillLocationId(@NonNull I_C_Invoice_Candidate ic, boolean useDefaultBillLocationAndContactIfNotOverride);
}
