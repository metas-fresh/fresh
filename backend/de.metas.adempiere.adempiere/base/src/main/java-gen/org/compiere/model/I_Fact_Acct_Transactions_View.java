package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for Fact_Acct_Transactions_View
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Fact_Acct_Transactions_View 
{

	String Table_Name = "Fact_Acct_Transactions_View";

//	/** AD_Table_ID=541485 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_ID();

	@Nullable I_A_Asset getA_Asset();

	void setA_Asset(@Nullable I_A_Asset A_Asset);

	ModelColumn<I_Fact_Acct_Transactions_View, I_A_Asset> COLUMN_A_Asset_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "A_Asset_ID", I_A_Asset.class);
	String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/**
	 * Set Account.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccount_ID (int Account_ID);

	/**
	 * Get Account.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAccount_ID();

	String COLUMNNAME_Account_ID = "Account_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

	String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Credit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmtAcctCr (BigDecimal AmtAcctCr);

	/**
	 * Get Credit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtAcctCr();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_AmtAcctCr = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "AmtAcctCr", null);
	String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Debit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmtAcctDr (BigDecimal AmtAcctDr);

	/**
	 * Get Debit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtAcctDr();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_AmtAcctDr = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "AmtAcctDr", null);
	String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Credit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmtSourceCr (@Nullable BigDecimal AmtSourceCr);

	/**
	 * Get Credit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtSourceCr();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_AmtSourceCr = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "AmtSourceCr", null);
	String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/**
	 * Set Debit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmtSourceDr (BigDecimal AmtSourceDr);

	/**
	 * Get Debit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtSourceDr();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_AmtSourceDr = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "AmtSourceDr", null);
	String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/**
	 * Set Balance.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBalance (BigDecimal Balance);

	/**
	 * Get Balance.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getBalance();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_Balance = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Balance", null);
	String COLUMNNAME_Balance = "Balance";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_AcctSchema_ID", I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner2_ID (int C_BPartner2_ID);

	/**
	 * Get Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner2_ID();

	String COLUMNNAME_C_BPartner2_ID = "C_BPartner2_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable I_C_Campaign C_Campaign);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_Campaign_ID", I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LocFrom_ID (int C_LocFrom_ID);

	/**
	 * Get Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LocFrom_ID();

	@Nullable I_C_Location getC_LocFrom();

	void setC_LocFrom(@Nullable I_C_Location C_LocFrom);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_Location> COLUMN_C_LocFrom_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_LocFrom_ID", I_C_Location.class);
	String COLUMNNAME_C_LocFrom_ID = "C_LocFrom_ID";

	/**
	 * Set Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LocTo_ID (int C_LocTo_ID);

	/**
	 * Get Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LocTo_ID();

	@Nullable I_C_Location getC_LocTo();

	void setC_LocTo(@Nullable I_C_Location C_LocTo);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_Location> COLUMN_C_LocTo_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_LocTo_ID", I_C_Location.class);
	String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderSO_ID();

	@Nullable I_C_Order getC_OrderSO();

	void setC_OrderSO(@Nullable I_C_Order C_OrderSO);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_OrderSO_ID", I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Set Counterpart Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCounterpart_Fact_Acct_ID (int Counterpart_Fact_Acct_ID);

	/**
	 * Get Counterpart Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCounterpart_Fact_Acct_ID();

	@Nullable I_Fact_Acct_Transactions_View getCounterpart_Fact_Acct();

	void setCounterpart_Fact_Acct(@Nullable I_Fact_Acct_Transactions_View Counterpart_Fact_Acct);

	ModelColumn<I_Fact_Acct_Transactions_View, I_Fact_Acct_Transactions_View> COLUMN_Counterpart_Fact_Acct_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Counterpart_Fact_Acct_ID", I_Fact_Acct_Transactions_View.class);
	String COLUMNNAME_Counterpart_Fact_Acct_ID = "Counterpart_Fact_Acct_ID";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	I_C_Period getC_Period();

	void setC_Period(I_C_Period C_Period);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_Period_ID", I_C_Period.class);
	String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/**
	 * Get Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectPhase_ID();

	@Nullable I_C_ProjectPhase getC_ProjectPhase();

	void setC_ProjectPhase(@Nullable I_C_ProjectPhase C_ProjectPhase);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_ProjectPhase_ID", I_C_ProjectPhase.class);
	String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/**
	 * Set Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/**
	 * Get Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectTask_ID();

	@Nullable I_C_ProjectTask getC_ProjectTask();

	void setC_ProjectTask(@Nullable I_C_ProjectTask C_ProjectTask);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_ProjectTask_ID", I_C_ProjectTask.class);
	String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_Created = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SalesRegion_ID();

	@Nullable I_C_SalesRegion getC_SalesRegion();

	void setC_SalesRegion(@Nullable I_C_SalesRegion C_SalesRegion);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_SalesRegion_ID", I_C_SalesRegion.class);
	String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Set Sub Account.
	 * Sub account for Element Value
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SubAcct_ID (int C_SubAcct_ID);

	/**
	 * Get Sub Account.
	 * Sub account for Element Value
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SubAcct_ID();

	@Nullable I_C_SubAcct getC_SubAcct();

	void setC_SubAcct(@Nullable I_C_SubAcct C_SubAcct);

	ModelColumn<I_Fact_Acct_Transactions_View, I_C_SubAcct> COLUMN_C_SubAcct_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "C_SubAcct_ID", I_C_SubAcct.class);
	String COLUMNNAME_C_SubAcct_ID = "C_SubAcct_ID";

	/**
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrencyRate (@Nullable BigDecimal CurrencyRate);

	/**
	 * Get Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrencyRate();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_CurrencyRate = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "CurrencyRate", null);
	String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcct();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_DateAcct = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateTrx();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_DateTrx = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "DateTrx", null);
	String COLUMNNAME_DateTrx = "DateTrx";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDescription();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_Description = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocBaseType (@Nullable String DocBaseType);

	/**
	 * Get Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDocBaseType();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_DocBaseType = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "DocBaseType", null);
	String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocStatus (@Nullable String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDocStatus();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_DocStatus = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDocumentNo();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_DocumentNo = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Accounting Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFact_Acct_ID (int Fact_Acct_ID);

	/**
	 * Get Accounting Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFact_Acct_ID();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_Fact_Acct_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Fact_Acct_ID", null);
	String COLUMNNAME_Fact_Acct_ID = "Fact_Acct_ID";

	/**
	 * Set Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGL_Budget_ID (int GL_Budget_ID);

	/**
	 * Get Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGL_Budget_ID();

	@Nullable I_GL_Budget getGL_Budget();

	void setGL_Budget(@Nullable I_GL_Budget GL_Budget);

	ModelColumn<I_Fact_Acct_Transactions_View, I_GL_Budget> COLUMN_GL_Budget_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "GL_Budget_ID", I_GL_Budget.class);
	String COLUMNNAME_GL_Budget_ID = "GL_Budget_ID";

	/**
	 * Set GL Category.
	 * General Ledger Category
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_Category_ID (int GL_Category_ID);

	/**
	 * Get GL Category.
	 * General Ledger Category
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_Category_ID();

	I_GL_Category getGL_Category();

	void setGL_Category(I_GL_Category GL_Category);

	ModelColumn<I_Fact_Acct_Transactions_View, I_GL_Category> COLUMN_GL_Category_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "GL_Category_ID", I_GL_Category.class);
	String COLUMNNAME_GL_Category_ID = "GL_Category_ID";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_IsActive = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Line ID.
	 * Transaction line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine_ID (int Line_ID);

	/**
	 * Get Line ID.
	 * Transaction line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLine_ID();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_Line_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Line_ID", null);
	String COLUMNNAME_Line_ID = "Line_ID";

	/**
	 * Set Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostElement_ID();

	@Nullable I_M_CostElement getM_CostElement();

	void setM_CostElement(@Nullable I_M_CostElement M_CostElement);

	ModelColumn<I_Fact_Acct_Transactions_View, I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "M_CostElement_ID", I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Posting Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostingType (String PostingType);

	/**
	 * Get Posting Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getPostingType();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_PostingType = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "PostingType", null);
	String COLUMNNAME_PostingType = "PostingType";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_Qty = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_Record_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set SubLine ID.
	 * Transaction sub line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSubLine_ID (int SubLine_ID);

	/**
	 * Get SubLine ID.
	 * Transaction sub line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSubLine_ID();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_SubLine_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "SubLine_ID", null);
	String COLUMNNAME_SubLine_ID = "SubLine_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_Updated = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set User Element 1.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElement1_ID (int UserElement1_ID);

	/**
	 * Get User Element 1.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUserElement1_ID();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElement1_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElement1_ID", null);
	String COLUMNNAME_UserElement1_ID = "UserElement1_ID";

	/**
	 * Set User Element 2.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElement2_ID (int UserElement2_ID);

	/**
	 * Get User Element 2.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUserElement2_ID();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElement2_ID = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElement2_ID", null);
	String COLUMNNAME_UserElement2_ID = "UserElement2_ID";

	/**
	 * Set UserElementString1.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString1 (@Nullable String UserElementString1);

	/**
	 * Get UserElementString1.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString1();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";

	/**
	 * Set UserElementString2.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString2 (@Nullable String UserElementString2);

	/**
	 * Get UserElementString2.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString2();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElementString2", null);
	String COLUMNNAME_UserElementString2 = "UserElementString2";

	/**
	 * Set UserElementString3.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString3 (@Nullable String UserElementString3);

	/**
	 * Get UserElementString3.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString3();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElementString3", null);
	String COLUMNNAME_UserElementString3 = "UserElementString3";

	/**
	 * Set UserElementString4.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString4 (@Nullable String UserElementString4);

	/**
	 * Get UserElementString4.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString4();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElementString4", null);
	String COLUMNNAME_UserElementString4 = "UserElementString4";

	/**
	 * Set UserElementString5.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString5 (@Nullable String UserElementString5);

	/**
	 * Get UserElementString5.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString5();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElementString5", null);
	String COLUMNNAME_UserElementString5 = "UserElementString5";

	/**
	 * Set UserElementString6.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString6 (@Nullable String UserElementString6);

	/**
	 * Get UserElementString6.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString6();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElementString6", null);
	String COLUMNNAME_UserElementString6 = "UserElementString6";

	/**
	 * Set UserElementString7.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString7 (@Nullable String UserElementString7);

	/**
	 * Get UserElementString7.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getUserElementString7();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";

	/**
	 * Set VAT Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVATCode (@Nullable String VATCode);

	/**
	 * Get VAT Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getVATCode();

	ModelColumn<I_Fact_Acct_Transactions_View, Object> COLUMN_VATCode = new ModelColumn<>(I_Fact_Acct_Transactions_View.class, "VATCode", null);
	String COLUMNNAME_VATCode = "VATCode";
}
