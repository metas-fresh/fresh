-- Run mode: SWING_CLIENT

-- Value: C_Invoice_Create_Payment_Request
-- Classname: de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request
-- 2025-01-13T14:01:06.251Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585446,'Y','de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request','N',TO_TIMESTAMP('2025-01-13 15:01:06.007','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Bankkonto zuweisen','json','N','N','xls','Java',TO_TIMESTAMP('2025-01-13 15:01:06.007','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Invoice_Create_Payment_Request')
;

-- 2025-01-13T14:01:06.257Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585446 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- 2025-01-13T14:01:58.989Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Assign Bank Account',Updated=TO_TIMESTAMP('2025-01-13 15:01:58.989','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585446
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- 2025-01-13T14:01:59.718Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-01-13 15:01:59.718','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585446
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- 2025-01-13T14:02:01.395Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-01-13 15:02:01.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585446
;

-- Name: C_BP_BankAccounts of C_BPartner with use Credit and/or Debit
-- 2025-01-13T14:23:42.831Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540698,'C_BP_BankAccount.C_BP_BankAccount_ID IN (SELECT C_BP_BankAccount_ID FROM C_BP_BankAccount WHERE C_BPartner_ID = @C_BPartner_ID@ AND bpbankacctuse IN (''B'', ''D'', ''T'' ) AND C_Currency_ID = @C_Currency_ID@ AND isactive = ''Y'')'
,TO_TIMESTAMP('2025-01-13 15:23:42.681','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.banking','Y','C_BP_BankAccounts of C_BPartner with use Credit and/or Debit','S',TO_TIMESTAMP('2025-01-13 15:23:42.681','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- ParameterName: C_BP_BankAccount_ID
-- 2025-01-13T14:25:54.514Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,837,0,585446,542913,13,269,'C_BP_BankAccount_ID',TO_TIMESTAMP('2025-01-13 15:25:54.379','YYYY-MM-DD HH24:MI:SS.US'),100,'Bankverbindung des Geschäftspartners','U',0,'Y','N','Y','N','N','N','Bankverbindung',10,TO_TIMESTAMP('2025-01-13 15:25:54.379','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-01-13T14:25:54.522Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542913 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-01-13T14:25:54.557Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(837)
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- Table: C_Invoice
-- EntityType: de.metas.banking
-- 2025-01-13T14:26:24.329Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585446,318,541541,TO_TIMESTAMP('2025-01-13 15:26:24.184','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.banking','Y',TO_TIMESTAMP('2025-01-13 15:26:24.184','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Value: C_Invoice_Create_Payment_Request
-- Classname: de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request
-- 2025-01-13T14:26:53.331Z
UPDATE AD_Process SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2025-01-13 15:26:53.328','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585446
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- ParameterName: C_BP_BankAccount_ID
-- 2025-01-13T14:27:47.559Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540698,Updated=TO_TIMESTAMP('2025-01-13 15:27:47.559','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542913
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- ParameterName: C_BP_BankAccount_ID
-- 2025-01-13T14:49:08.493Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540069,Updated=TO_TIMESTAMP('2025-01-13 15:49:08.493','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542913
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- ParameterName: C_BP_BankAccount_ID
-- 2025-01-13T14:50:42.126Z
UPDATE AD_Process_Para SET AD_Reference_ID=30, IsMandatory='Y',Updated=TO_TIMESTAMP('2025-01-13 15:50:42.126','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542913
;

-- Process: C_Invoice_Create_Payment_Request(de.metas.banking.process.paymentdocumentform.C_Invoice_Create_Payment_Request)
-- Table: C_Invoice
-- Window: Eingangsrechnung(183,D)
-- EntityType: de.metas.banking
-- 2025-01-13T15:09:46.349Z
UPDATE AD_Table_Process SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2025-01-13 16:09:46.349','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541541
;

