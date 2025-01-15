package de.metas.banking.payment.impl;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.banking.BankAccountId;
import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.banking.payment.InvoiceMatchingMode;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.document.engine.DocStatus;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentRule;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PaySelectionUpdater implements IPaySelectionUpdater
{
	// services
	private static final Logger logger = LogManager.getLogger(PaySelectionUpdater.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IPaySelectionDAO paySelectionsRepo = Services.get(IPaySelectionDAO.class);
	private final transient ModelCacheInvalidationService modelCacheInvalidationService = ModelCacheInvalidationService.get();
	private final ADReferenceService adReferenceService = ADReferenceService.get();

	private boolean _configurable = true;

	/**
	 * Only When Discount
	 */
	private boolean _onlyDiscount = false;
	/**
	 * Only when Due
	 */
	private boolean _onlyDue = false;
	/**
	 * Include Disputed
	 */
	private Boolean _includeInDispute = null;
	/**
	 * Match Requirement
	 */
	private Optional<InvoiceMatchingMode> _matchRequirement = Optional.empty();
	/**
	 * Match Requirement
	 */
	private Timestamp _payDate = null;
	/**
	 * Payment Rule
	 */
	private PaymentRule _paymentRule = null;
	/**
	 * BPartner
	 */
	private int _bpartnerId = 0;
	/**
	 * BPartner Group
	 */
	private int _bpGroupId = 0;

	private Timestamp _dateInvoiced;

	private String _poReference;

	/**
	 * Payment Selection
	 */
	private I_C_PaySelection _paySelection;

	private final Set<Integer> paySelectionLineIdsToUpdate = new HashSet<>();
	/**
	 * Provides a map of C_Invoice_ID to {@link I_C_PaySelectionLine} which were enqueued to be updated
	 */
	private final Supplier<Map<Integer, I_C_PaySelectionLine>> _invoiceId2paySelectionLineToUpdateSupplier = Suppliers.memoize(() -> {
		if (paySelectionLineIdsToUpdate.isEmpty())
		{
			return new HashMap<>();
		}

		final List<I_C_PaySelectionLine> paySelectionLines = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaySelectionLine.class)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, getPaySelectionId())
				.addInArrayOrAllFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelectionLine_ID, paySelectionLineIdsToUpdate)
				.create()
				.list();

		final Map<Integer, I_C_PaySelectionLine> invoiceId2paySelectionLine = new HashMap<>(paySelectionLines.size());
		for (final I_C_PaySelectionLine paySelectionLine : paySelectionLines)
		{
			final int invoiceId = paySelectionLine.getC_Invoice_ID();
			final I_C_PaySelectionLine paySelectionLineOld = invoiceId2paySelectionLine.put(invoiceId, paySelectionLine);
			Check.assumeNull(paySelectionLineOld, "Only one pay selection line shall exist for an invoice but we found: {}, {}", paySelectionLine, paySelectionLineOld); // shall not happen
		}

		return invoiceId2paySelectionLine;
	});

	//
	// Statistics
	private int countCreated = 0;
	private int countUpdated = 0;
	private int countDeleted = 0;

	PaySelectionUpdater()
	{
	}

	@Override
	public void update()
	{
		trxManager.runInThreadInheritedTrx(this::updateInTrx);
	}

	private void updateInTrx()
	{
		assertConfigurable();
		_configurable = false; // Lock this updater. Shall not be configurable anymore.

		//
		// Validate InvoiceMatchingMode vs PaySelectionTrxType
		final I_C_PaySelection paySelection = getC_PaySelection();
		final InvoiceMatchingMode invoiceMatchingMode = getMatchRequirement().orElse(null);
		if (invoiceMatchingMode != null)
		{
			final PaySelectionTrxType paySelectionTrxType = PaySelectionTrxType.ofNullableCode(paySelection.getPaySelectionTrxType());
			if (paySelectionTrxType != null && !invoiceMatchingMode.isCompatibleWith(paySelectionTrxType))
			{
				throw new AdempiereException("@Invalid@ @PaySelectionTrxType@");
			}
		}

		//
		// Create/Update PaySelection lines
		final ArrayList<Object> sqlParams = new ArrayList<>();
		final String sql = buildSelectSQL(sqlParams);
		DB.forEachRow(sql, sqlParams, this::createOrUpdatePaySelectionLine);

		//
		// Delete remaining pay selection lines (which were enqueued to be updated),
		// because if they were not updated until now, for sure no invoice matched so it's safe to delete them
		for (final I_C_PaySelectionLine paySelectionLine : dequeueAllPaySelectionLinesToUpdate())
		{
			deletePaySelectionLine(paySelectionLine);
		}

		//
		// Update the PaySelection
		if (invoiceMatchingMode != null)
		{
			paySelection.setPaySelectionTrxType(invoiceMatchingMode.getPaySelectionTrxType().getCode());

			InterfaceWrapperHelper.save(paySelection);
		}

		// make sure pay selection is invalidated
		cacheInvalidationForCurrentPaySelection();
	}

	private void assertConfigurable()
	{
		Check.assume(_configurable, "Not already executed");
	}

	@Override
	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder("@C_PaySelectionLine_ID@ - ");

		sb.append("@Created@: ").append(countCreated);
		if (countUpdated > 0)
		{
			sb.append(", @Updated@: ").append(countUpdated);
		}
		if (countDeleted > 0)
		{
			sb.append(", @Deleted@: ").append(countDeleted);
		}
		return sb.toString();
	}

	private String buildSelectSQL(final List<Object> sqlParams)
	{
		// NOTE!!! Please keep in sync with de.metas.banking.payment.callout.C_PaySelectionLine.invoice

		Check.assume(sqlParams != null && sqlParams.isEmpty(), "instantiated empty list");

		if (logger.isInfoEnabled())
		{
			logger.info("C_PaySelection_ID=" + getC_PaySelection()
					+ ", OnlyDiscount=" + isOnlyDiscount() + ", OnlyDue=" + isOnlyDue()
					+ ", IncludeInDispute=" + getIncludeInDispute()
					+ ", MatchRequirement=" + getMatchRequirement()
					+ ", PaymentRule=" + getPaymentRule()
					+ ", C_BP_Group_ID=" + getC_BP_Group_ID() + ", C_BPartner_ID=" + getC_BPartner_ID());
		}

		final I_C_PaySelection paySelection = getC_PaySelection();
		final CurrencyId C_CurrencyTo_ID = CurrencyId.ofRepoId(paySelection.getC_BP_BankAccount().getC_Currency_ID());
		final Timestamp payDate;

		//
		// 08419: depending on whether onlyDue is checked, consider p_Date either DateTo or DateFrom
		if (getPayDate() != null)
		{
			payDate = getPayDate();
		}
		else
		{
			payDate = paySelection.getPayDate();
		}

		String sql = "SELECT "
				+ " C_Invoice_ID,"
				// OpenAmt
				+ " currencyConvert(invoiceOpen(i.C_Invoice_ID, 0)"
				+ ",i.C_Currency_ID, ?,?, i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) as OpenAmt," // ##1/2 Currency_To,PayDate
				// DiscountAmt
				+ " currencyConvert(paymentTermDiscount(i.GrandTotal,i.C_Currency_ID,i.C_PaymentTerm_ID,i.DateInvoiced, ?)" // ##3 PayDate
				+ ",i.C_Currency_ID, ?,?,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) as DiscountAmt," // ##4/5 Currency_To,PayDate
				+ " i.PaymentRule, " // 4
				+ " i.IsSOTrx, " // 5
				+ " i.C_Bpartner_ID," // 6

				// C_BP_BankAccount_ID
				+ " (SELECT max(bpb.C_BP_BankAccount_ID) FROM C_BP_BankAccount bpb WHERE bpb.C_BPartner_ID = i.C_BPartner_ID AND bpb.IsActive='Y' "
				+ " AND bpb.BPBankAcctUse = (CASE WHEN EXISTS(SELECT 1 FROM C_BP_BankAccount sub WHERE sub.BPBankAcctUse = i.PaymentRule)"
				+ " THEN i.PaymentRule ELSE 'B' END)) as C_BP_BankAccount_ID "
				//
				+ " FROM C_Invoice i "
				+ " LEFT JOIN C_Doctype dt on i.C_Doctype_ID = dt.C_Doctype_ID "
				+ " WHERE true " //
		;
		sqlParams.add(C_CurrencyTo_ID); // #1
		sqlParams.add(payDate); // #2
		sqlParams.add(payDate); // #3
		sqlParams.add(C_CurrencyTo_ID); // #4
		sqlParams.add(payDate); // #5


		// filter Org_BP_Account_ID if set
		{
			final BankAccountId orgBankAccountId = BankAccountId.ofRepoId(paySelection.getC_BP_BankAccount_ID());
			sql += "AND (i.Org_BP_Account_ID = ? OR i.Org_BP_Account_ID is null)";
			sqlParams.add(orgBankAccountId);
		}

		// Not already paid invoices
		{
			sql += " AND i.IsPaid=?";
			sqlParams.add(false);
		}

		// Only COmpleted/CLosed invoices
		{
			sql += " AND i.DocStatus IN (?,?)";
			sqlParams.add(DocStatus.Completed);
			sqlParams.add(DocStatus.Closed);
		}

		// Only those invoices which are matching C_PaySelection's currency (07885)
		{
			sql += " AND i.C_Currency_ID=?";
			sqlParams.add(C_CurrencyTo_ID);
		}

		// Only for Pay Selection's Organization (if set)
		if (paySelection.getAD_Org_ID() > 0)
		{
			sql += " AND i.AD_Org_ID=? ";
			sqlParams.add(paySelection.getAD_Org_ID());
		}

		// Only those invoices from our tenant (guard, shall not happen)
		{
			sql += " AND i.AD_Client_ID=?";
			sqlParams.add(paySelection.getAD_Client_ID());
		}

		//
		// Exclude invoices from existing pay selections if we were not explicitelly asked to just update a couple of pay selection lines
		// or, Include only the pay selection lines that we were adviced to include.
		if (paySelectionLineIdsToUpdate.isEmpty())
		{
			sql += " AND NOT EXISTS (" //
					+ "         SELECT 1 FROM C_PaySelectionLine psl " //
					+ "         WHERE psl.C_Invoice_ID=i.C_Invoice_ID AND psl.IsActive='Y' " //
					+ " )";
		}
		else
		{
			sql += " AND EXISTS (" //
					+ " SELECT 1 FROM C_PaySelectionLine psl "
					+ " WHERE psl.C_Invoice_ID=i.C_Invoice_ID"
					+ " AND " + DB.buildSqlList("psl.C_PaySelectionLine_ID", paySelectionLineIdsToUpdate, sqlParams)
					+ " )";
		}

		// Disputed
		final Boolean includeInDispute = getIncludeInDispute();
		if (includeInDispute != null && !includeInDispute)
		{
			sql += " AND i.IsInDispute='N'";
		}

		// PaymentRule (optional)
		if (getPaymentRule() != null)
		{
			sql += " AND PaymentRule=?"; // ##
			sqlParams.add(getPaymentRule());
		}

		// OnlyDiscount
		if (isOnlyDiscount())
		{
			if (isOnlyDue())
			{
				sql += " AND (";
			}
			else
			{
				sql += " AND ";
			}
			sql += "paymentTermDiscount(invoiceOpen(C_Invoice_ID, 0), C_Currency_ID, C_PaymentTerm_ID, DateInvoiced, ?) > 0"; // ##
			sqlParams.add(payDate);
		}
		// OnlyDue
		if (isOnlyDue())
		{
			if (isOnlyDiscount())
			{
				sql += " OR ";
			}
			else
			{
				sql += " AND ";
			}

			sql += "daysbetween(?,  i.duedate::timestamp WITH TIME ZONE)  >= 0"; // ##
			sqlParams.add(payDate);

			if (isOnlyDiscount())
			{
				sql += ")";
			}
		}

		// Business Partner
		if (getC_BPartner_ID() > 0)
		{
			sql += " AND C_BPartner_ID=?"; // ##
			sqlParams.add(getC_BPartner_ID());
		}
		// Business Partner Group
		else if (getC_BP_Group_ID() > 0)
		{
			sql += " AND EXISTS (SELECT * FROM C_BPartner bp "
					+ "WHERE bp.C_BPartner_ID=i.C_BPartner_ID AND bp.C_BP_Group_ID=?)"; // ##
			sqlParams.add(getC_BP_Group_ID());
		}

		// DateTrx
		if (getDateInvoiced() != null)
		{
			sql += " AND i.DateAcct=?";
			sqlParams.add(getDateInvoiced());
		}

		// Reference
		if (getPOReference() != null)
		{
			sql += " AND i.poreference=?";
			sqlParams.add(getPOReference());
		}

		sql += buildSelectSQL_MatchRequirement();

		return sql;
	}

	private String buildSelectSQL_MatchRequirement()
	{
		final String whereCreditTransferToVendor = " i.IsSOTrx='N' AND i.PaymentRule IN ('" + PaymentRule.DirectDeposit.getCode() + "','" + PaymentRule.OnCredit.getCode() + "') ";
		final String whereDirectDebitFromCustomer = " i.IsSOTrx='Y' AND dt.DocBaseType !='ARC' AND i.PaymentRule = '" + PaymentRule.DirectDebit.getCode() + "'";
		final String whereCreditTransferToCustomer = " i.IsSOTrx='Y' AND dt.DocBaseType='ARC' AND i.PaymentRule IN ('" + PaymentRule.DirectDeposit.getCode() + "','" + PaymentRule.OnCredit.getCode() + "') ";

		final InvoiceMatchingMode matchRequirement = getMatchRequirement().orElse(null);
		if (matchRequirement == null) // ALL
		{
			return "AND ( (" + whereCreditTransferToVendor + ") OR ( " + whereDirectDebitFromCustomer + " ) OR (" + whereCreditTransferToCustomer + ") )";
		}
		else if (InvoiceMatchingMode.CREDIT_TRANSFER_TO_VENDOR.equals(matchRequirement))
		{
			return " AND " + whereCreditTransferToVendor;
		}
		else if (InvoiceMatchingMode.DIRECT_DEBIT_FROM_CUSTOMER.equals(matchRequirement))
		{
			return " AND " + whereDirectDebitFromCustomer;
		}
		else if (InvoiceMatchingMode.CREDIT_TRANSFER_TO_CUSTOMER.equals(matchRequirement))
		{
			return " AND " + whereCreditTransferToCustomer;
		}
		else
		{
			throw new AdempiereException("Unknown matchRequirement: " + matchRequirement);
		}
	}

	private void createOrUpdatePaySelectionLine(final ResultSet rs) throws SQLException
	{
		final PaySelectionLineCandidate candidate = retrievePaySelectionLineCandidate(rs);
		createOrUpdatePaySelectionLine(candidate);
	}

	private PaySelectionLineCandidate retrievePaySelectionLineCandidate(final ResultSet rs) throws SQLException
	{
		final PaySelectionLineCandidate.Builder candidateBuilder = PaySelectionLineCandidate.builder();
		final int invoiceId = rs.getInt("C_Invoice_ID");
		candidateBuilder.setC_Invoice_ID(invoiceId);

		final BigDecimal invoiceOpenAmt = rs.getBigDecimal("OpenAmt");
		candidateBuilder.setOpenAmt(invoiceOpenAmt);

		final BigDecimal payDiscountAmt = rs.getBigDecimal("DiscountAmt");
		candidateBuilder.setDiscountAmt(payDiscountAmt);

		final boolean isSOTrx = DisplayType.toBoolean(rs.getString("IsSOTrx"), false);
		candidateBuilder.setIsSOTrx(isSOTrx);

		final int bpartnerId = rs.getInt("C_BPartner_ID");
		candidateBuilder.setC_BPartner_ID(bpartnerId);

		final int bpBankAccountId = rs.getInt("C_BP_BankAccount_ID");
		candidateBuilder.setC_BP_BankAccount_ID(bpBankAccountId);

		PaymentRule paymentRule = PaymentRule.ofNullableCode(rs.getString("PaymentRule"));
		// check active payment rules
		final ImmutableSet<PaymentRule> paymentRules = getInvoicePaymentRules();
		if (paymentRule == null || !paymentRules.contains(paymentRule))
		{
			paymentRule = PaymentRule.DirectDeposit;
		}
		candidateBuilder.setPaymentRule(paymentRule.getCode());

		return candidateBuilder.build();
	}

	private void createOrUpdatePaySelectionLine(final PaySelectionLineCandidate candidate)
	{
		if (candidate.getC_Invoice_ID() <= 0) // shall not happen
		{
			return;
		}

		final I_C_PaySelection paySelection = getC_PaySelection();

		//
		// Dequeue existing C_PaySelectionLine to be updated.
		// Also make sure the existing pay selection is valid in our context.
		final I_C_PaySelectionLine existingPaySelectionLine = dequeuePaySelectionLineToUpdateByInvoice(candidate.getC_Invoice_ID()).orElse(null);
		if (existingPaySelectionLine != null && existingPaySelectionLine.getC_PaySelection_ID() != paySelection.getC_PaySelection_ID())
		{
			throw new AdempiereException("PaySelectionLine to update it's not matching the PaySelection header"
					+ "\n Expected: " + paySelection
					+ "\n Actual: " + existingPaySelectionLine.getC_PaySelection());
		}

		// If the candidate is no longer eligible for a pay selection line, delete the existing pay selection line (if any)
		if (candidate.getPayAmt().signum() <= 0)
		{
			deletePaySelectionLine(existingPaySelectionLine);
			return;
		}

		//
		// Create a new C_PaySelectionLine or re-use existing (if found).
		final I_C_PaySelectionLine paySelectionLine;
		final boolean isNewPaySelectionLine;
		if (existingPaySelectionLine == null)
		{
			paySelectionLine = newInstance(I_C_PaySelectionLine.class);
			isNewPaySelectionLine = true;
		}
		else
		{
			paySelectionLine = existingPaySelectionLine;
			isNewPaySelectionLine = false;
		}

		// Update from candidate
		updatePaySelectionLine(paySelectionLine, candidate);

		// Save & update statistics
		InterfaceWrapperHelper.save(paySelectionLine);
		if (isNewPaySelectionLine)
		{
			countCreated++;
			logger.debug("Created {}", paySelectionLine);
		}
		else
		{
			countUpdated++;
			logger.debug("Updated {}", paySelectionLine);
		}
	}

	private void deletePaySelectionLine(final I_C_PaySelectionLine paySelectionLine)
	{
		if (paySelectionLine == null)
		{
			return;
		}
		if (InterfaceWrapperHelper.isNew(paySelectionLine))
		{
			return;
		}

		// Actually delete the pay selection line & update statistics
		InterfaceWrapperHelper.delete(paySelectionLine);
		countDeleted++;
		logger.debug("Deleted {}", paySelectionLine);
	}

	/**
	 * Updates {@link I_C_PaySelectionLine} from: given candidate and current pay selection header
	 */
	private void updatePaySelectionLine(final I_C_PaySelectionLine paySelectionLine, final PaySelectionLineCandidate candidate)
	{
		//
		// Update from C_PaySelection header
		final I_C_PaySelection paySelection = getC_PaySelection();
		paySelectionLine.setAD_Org_ID(paySelection.getAD_Org_ID());
		paySelectionLine.setC_PaySelection(paySelection);

		//
		// Update from candidate
		paySelectionLine.setPaymentRule(candidate.getPaymentRule());
		paySelectionLine.setC_Invoice_ID(candidate.getC_Invoice_ID());
		paySelectionLine.setIsSOTrx(candidate.isSOTrx());
		paySelectionLine.setOpenAmt(candidate.getOpenAmt());
		paySelectionLine.setPayAmt(candidate.getPayAmt());
		paySelectionLine.setDiscountAmt(candidate.getDiscountAmt());
		paySelectionLine.setDifferenceAmt(candidate.getDifferenceAmt());
		paySelectionLine.setC_BPartner_ID(candidate.getC_BPartner_ID());
		final int bpBankAccountId = candidate.getC_BP_BankAccount_ID();
		if (bpBankAccountId > 0)
		{
			// task 09500: only set the account if it has the same currency as the one of the header
			// note that this checking is just for extra precaution. the candidate shall already have that currency

			final int paySelectionCurrencyID = paySelection.getC_BP_BankAccount().getC_Currency_ID();

			final Properties ctx = InterfaceWrapperHelper.getCtx(paySelectionLine);
			final String trxName = InterfaceWrapperHelper.getTrxName(paySelectionLine);

			final I_C_BP_BankAccount candidateAccount = InterfaceWrapperHelper.create(ctx, bpBankAccountId, I_C_BP_BankAccount.class, trxName);

			final int candidateCurrencyID = candidateAccount.getC_Currency_ID();

			if (paySelectionCurrencyID == candidateCurrencyID)
			{
				paySelectionLine.setC_BP_BankAccount_ID(bpBankAccountId);
			}
		}

		// Set line no (if not already set)
		if (paySelectionLine.getLine() <= 0)
		{
			final int lineNo = getAndIncrementNextLineNo();
			paySelectionLine.setLine(lineNo);
		}
	}

	private ImmutableSet<PaymentRule> getInvoicePaymentRules()
	{
		return _paymentRulesSupplier.get();
	}

	/**
	 * Payment Rules (from C_Invoice.PaymentRule's available values list)
	 */
	private final Supplier<ImmutableSet<PaymentRule>> _paymentRulesSupplier = Suppliers.memoize(this::retrieveInvoicePaymentRules);

	private ImmutableSet<PaymentRule> retrieveInvoicePaymentRules()
	{
		final POInfo invoicePOInfo = POInfo.getPOInfo(I_C_Invoice.Table_Name);
		final ReferenceId paymentRuleReferenceId = invoicePOInfo.getColumnReferenceValueId(I_C_Invoice.COLUMNNAME_PaymentRule);

		final Set<String> paymentRules = adReferenceService.getRefListById(paymentRuleReferenceId).getValues();
		if (paymentRules == null || paymentRules.isEmpty())
		{
			throw new AdempiereException("No active payment rules were found");
		}

		return paymentRules.stream()
				.map(PaymentRule::ofCode)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void cacheInvalidationForCurrentPaySelection()
	{
		final PaySelectionId paySelectionId = getPaySelectionId();

		modelCacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.of(
						CacheInvalidateRequest.rootRecord(I_C_PaySelection.Table_Name, paySelectionId),
						CacheInvalidateRequest.allChildRecords(I_C_PaySelection.Table_Name, paySelectionId, I_C_PaySelectionLine.Table_Name)),
				ModelCacheInvalidationTiming.AFTER_CHANGE);
	}

	private boolean isOnlyDiscount()
	{
		return _onlyDiscount;
	}

	@Override
	public IPaySelectionUpdater setOnlyDiscount(final boolean onlyDiscount)
	{
		assertConfigurable();
		_onlyDiscount = onlyDiscount;
		return this;
	}

	private boolean isOnlyDue()
	{
		return _onlyDue;
	}

	@Override
	public IPaySelectionUpdater setOnlyDue(final boolean onlyDue)
	{
		assertConfigurable();
		_onlyDue = onlyDue;
		return this;
	}

	private Boolean getIncludeInDispute()
	{
		return _includeInDispute;
	}

	@Override
	public IPaySelectionUpdater setIncludeInDispute(final boolean includeInDispute)
	{
		assertConfigurable();
		_includeInDispute = includeInDispute;
		return this;
	}

	private Optional<InvoiceMatchingMode> getMatchRequirement()
	{
		return _matchRequirement;
	}

	@Override
	public IPaySelectionUpdater setMatchRequirement(@Nullable final InvoiceMatchingMode matchRequirement)
	{
		assertConfigurable();
		_matchRequirement = Optional.ofNullable(matchRequirement);
		return this;
	}

	private Timestamp getPayDate()
	{
		return _payDate;
	}

	@Override
	public IPaySelectionUpdater setPayDate(final Timestamp payDate)
	{
		assertConfigurable();
		_payDate = TimeUtil.copyOf(payDate);
		return this;
	}

	private PaymentRule getPaymentRule()
	{
		return _paymentRule;
	}

	@Override
	public IPaySelectionUpdater setPaymentRule(final PaymentRule paymentRule)
	{
		assertConfigurable();
		_paymentRule = paymentRule;
		return this;
	}

	private int getC_BPartner_ID()
	{
		return _bpartnerId;
	}

	@Override
	public IPaySelectionUpdater setC_BPartner_ID(final int bpartnerId)
	{
		assertConfigurable();
		_bpartnerId = bpartnerId;
		return this;
	}

	private int getC_BP_Group_ID()
	{
		return _bpGroupId;
	}

	@Override
	public IPaySelectionUpdater setC_BP_Group_ID(final int bpGroupId)
	{
		assertConfigurable();
		_bpGroupId = bpGroupId;
		return this;
	}

	private Timestamp getDateInvoiced()
	{
		return _dateInvoiced;
	}

	@Override
	public IPaySelectionUpdater setDateInvoiced(final Timestamp dateAcct)
	{
		assertConfigurable();
		_dateInvoiced = TimeUtil.copyOf(dateAcct);
		return this;
	}

	private String getPOReference()
	{
		return _poReference;
	}

	@Override
	public IPaySelectionUpdater setPOReference(final String POReference)
	{
		assertConfigurable();
		_poReference = POReference;
		return this;
	}


	@Override
	public IPaySelectionUpdater setC_PaySelection(@NonNull final I_C_PaySelection paySelection)
	{
		assertConfigurable();
		_paySelection = paySelection;

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(paySelection.getDocStatus());
		if (!docStatus.isDraftedOrInProgress())
		{
			throw new AdempiereException("Document already processed");
		}

		return this;
	}

	private I_C_PaySelection getC_PaySelection()
	{
		Check.assumeNotNull(_paySelection, "_paySelection not null");
		return _paySelection;
	}

	private PaySelectionId getPaySelectionId()
	{
		return PaySelectionId.ofRepoId(getC_PaySelection().getC_PaySelection_ID());
	}

	private Integer _nextLineNo = null;

	private int getAndIncrementNextLineNo()
	{
		if (_nextLineNo == null)
		{
			final int lastLineNo = paySelectionsRepo.retrieveLastPaySelectionLineNo(getPaySelectionId());
			_nextLineNo = lastLineNo + 10;
		}

		final int nextLineNoToReturn = _nextLineNo;
		_nextLineNo += 10;

		return nextLineNoToReturn;
	}

	@Override
	public IPaySelectionUpdater addPaySelectionLineToUpdate(final int paySelectionLineId)
	{
		assertConfigurable();
		paySelectionLineIdsToUpdate.add(paySelectionLineId);
		return this;
	}

	@Override
	public IPaySelectionUpdater addPaySelectionLinesToUpdate(final Iterable<? extends I_C_PaySelectionLine> paySelectionLines)
	{
		assertConfigurable();

		for (final I_C_PaySelectionLine paySelectionLine : paySelectionLines)
		{
			paySelectionLineIdsToUpdate.add(paySelectionLine.getC_PaySelectionLine_ID());
		}

		return this;
	}

	/**
	 * Dequeue (gets and removes from internal list) the {@link I_C_PaySelectionLine} which was scheduled to be updated.
	 *
	 * @param invoiceId C_Invoice_ID
	 * @return existing {@link I_C_PaySelectionLine} which was scheduled to be updated
	 */
	private Optional<I_C_PaySelectionLine> dequeuePaySelectionLineToUpdateByInvoice(final int invoiceId)
	{
		final Map<Integer, I_C_PaySelectionLine> invoiceId2paySelectionLineToUpdate = _invoiceId2paySelectionLineToUpdateSupplier.get();
		final I_C_PaySelectionLine paySelectionLine = invoiceId2paySelectionLineToUpdate.remove(invoiceId);
		return Optional.ofNullable(paySelectionLine);
	}

	/**
	 * Dequeue (gets and removes from internal list) all remaining {@link I_C_PaySelectionLine}s which were scheduled to be updated.
	 */
	private List<I_C_PaySelectionLine> dequeueAllPaySelectionLinesToUpdate()
	{
		final Map<Integer, I_C_PaySelectionLine> invoiceId2paySelectionLineToUpdate = _invoiceId2paySelectionLineToUpdateSupplier.get();
		final List<I_C_PaySelectionLine> paySelectionLines = new ArrayList<>(invoiceId2paySelectionLineToUpdate.values());
		invoiceId2paySelectionLineToUpdate.clear();
		return paySelectionLines;
	}
}
