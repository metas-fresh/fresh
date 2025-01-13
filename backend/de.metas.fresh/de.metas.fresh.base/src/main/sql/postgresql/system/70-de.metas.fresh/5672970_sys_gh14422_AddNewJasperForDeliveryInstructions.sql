-- Value: Delivery instructions(Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/deliveryinstructions/report.jasper
-- 2023-01-23T10:53:36.356Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585190,'N','de.metas.report.jasper.client.process.JasperReportStarter',TO_TIMESTAMP('2023-01-23 12:53:36','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Y','Y','Y','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/deliveryinstructions/report.jasper','Delivery instructions(Jasper)','json','N','N','JasperReportsSQL',TO_TIMESTAMP('2023-01-23 12:53:36','YYYY-MM-DD HH24:MI:SS'),100,'Delivery instructions(Jasper)')
;

-- 2023-01-23T10:53:36.358Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585190 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

--
-- Script: /tmp/webui_migration_scripts_2023-01-19_1748687591539389647/5672990_migration_2023-01-23_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2023-01-23T11:06:13.375Z
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_Table_ID,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,540006,'None',540128,102,541144,TO_TIMESTAMP('2023-01-23 12:06:13','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y','Delivery instructions',TO_TIMESTAMP('2023-01-23 12:06:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-23T11:07:31.468Z
UPDATE AD_PrintFormat SET JasperProcess_ID=585190,Updated=TO_TIMESTAMP('2023-01-23 12:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540128
;

-- 2023-01-23T11:07:39.033Z
UPDATE AD_PrintFormat SET AD_Table_ID=542280,Updated=TO_TIMESTAMP('2023-01-23 12:07:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540128
;

-- 2023-01-23T11:08:47.137Z
UPDATE C_DocType SET AD_PrintFormat_ID=540128,Updated=TO_TIMESTAMP('2023-01-23 12:08:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541085
;
