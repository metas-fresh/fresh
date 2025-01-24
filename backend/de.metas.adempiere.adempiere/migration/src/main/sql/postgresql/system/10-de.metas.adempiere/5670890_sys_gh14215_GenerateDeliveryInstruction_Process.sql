-- Value: M_Delivery_Planning_GenerateDeliveryInstruction
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateDeliveryInstruction
-- 2023-01-09T15:00:36.117Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585176,'Y','de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateDeliveryInstruction','N',TO_TIMESTAMP('2023-01-09 17:00:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Generate Delivery Instruction','json','N','N','xls','Java',TO_TIMESTAMP('2023-01-09 17:00:34','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_GenerateDeliveryInstruction')
;

-- 2023-01-09T15:00:36.148Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585176 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_Delivery_Planning_GenerateDeliveryInstruction(de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateDeliveryInstruction)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2023-01-09T15:02:25.860Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585176,542259,541327,TO_TIMESTAMP('2023-01-09 17:02:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-01-09 17:02:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;



-- Value: de.metas.deliveryplanning.DeliveryPlanningService.NoForwarder
-- 2023-01-09T15:05:53.973Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545221,0,TO_TIMESTAMP('2023-01-09 17:05:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','At least one selected delivery planning has no forwarder.','I',TO_TIMESTAMP('2023-01-09 17:05:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.deliveryplanning.DeliveryPlanningService.NoForwarder')
;

-- 2023-01-09T15:05:54.001Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545221 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;



