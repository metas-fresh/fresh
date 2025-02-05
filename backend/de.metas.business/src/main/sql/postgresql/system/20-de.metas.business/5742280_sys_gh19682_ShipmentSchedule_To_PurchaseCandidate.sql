-- Column: C_PurchaseCandidate.M_ShipmentSchedule_ID
-- Column: C_PurchaseCandidate.M_ShipmentSchedule_ID
-- 2024-12-18T16:10:37.172Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589549,500221,0,19,540861,'XX','M_ShipmentSchedule_ID',TO_TIMESTAMP('2024-12-18 16:10:36.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.purchasecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferdisposition',0,0,TO_TIMESTAMP('2024-12-18 16:10:36.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T16:10:37.181Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589549 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T16:10:37.257Z
/* DDL */  select update_Column_Translation_From_AD_Element(500221) 
;

-- 2024-12-18T16:10:38.550Z
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN M_ShipmentSchedule_ID NUMERIC(10)')
;

-- 2024-12-18T16:10:38.674Z
ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT MShipmentSchedule_CPurchaseCandidate FOREIGN KEY (M_ShipmentSchedule_ID) REFERENCES public.M_ShipmentSchedule DEFERRABLE INITIALLY DEFERRED
;

-- Value: Create_Purchase_Candidates_From_Shipment_Schedules
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2024-12-18T16:35:59.931Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585442,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2024-12-18 16:35:59.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Kaufkandidat erstellen','json','Y','N','xls','select create_purchase_candidates_from_shipment_schedules(@$WEBUI_ViewSelectedIds/0@);','SQL',TO_TIMESTAMP('2024-12-18 16:35:59.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Create_Purchase_Candidates_From_Shipment_Schedules')
;

-- 2024-12-18T16:35:59.935Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585442 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Create_Purchase_Candidates_From_Shipment_Schedules(de.metas.process.ExecuteUpdateSQL)
-- Table: M_ShipmentSchedule
-- EntityType: D
-- 2024-12-18T16:36:32.922Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585442,500221,541539,TO_TIMESTAMP('2024-12-18 16:36:32.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2024-12-18 16:36:32.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Value: Create_Purchase_Candidates_From_Shipment_Schedules
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2024-12-18T16:42:29.451Z
UPDATE AD_Process SET SQLStatement='select create_purchase_candidates_from_shipment_schedules(''@$WEBUI_ViewSelectedIds/0@'');',Updated=TO_TIMESTAMP('2024-12-18 16:42:29.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585442
;

-- Process: Create_Purchase_Candidates_From_Shipment_Schedules(de.metas.process.ExecuteUpdateSQL)
-- Table: M_ShipmentSchedule
-- EntityType: D
-- 2024-12-18T17:05:52.912Z
UPDATE AD_Table_Process SET WEBUI_DocumentAction='N', WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2024-12-18 17:05:52.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_Process_ID=541539
;

-- Name: M_ShipmentSchedule_Source
-- 2024-12-18T18:39:31.293Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541913,TO_TIMESTAMP('2024-12-18 18:39:31.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ShipmentSchedule_Source',TO_TIMESTAMP('2024-12-18 18:39:31.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2024-12-18T18:39:31.298Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541913 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipmentSchedule_Source
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2024-12-18T18:42:01.403Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,500232,0,541913,500221,500221,TO_TIMESTAMP('2024-12-18 18:42:01.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2024-12-18 18:42:01.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_PurchaseCandidate_Target_For_M_ShipmentSchedule
-- 2024-12-18T18:44:44.497Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541914,TO_TIMESTAMP('2024-12-18 18:44:44.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_PurchaseCandidate_Target_For_M_ShipmentSchedule',TO_TIMESTAMP('2024-12-18 18:44:44.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2024-12-18T18:44:44.501Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541914 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_PurchaseCandidate_Target_For_M_ShipmentSchedule
-- Table: C_PurchaseCandidate
-- Key: C_PurchaseCandidate.M_ShipmentSchedule_ID
-- 2024-12-18T18:47:06.123Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,589549,0,541914,540861,TO_TIMESTAMP('2024-12-18 18:47:06.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2024-12-18 18:47:06.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T18:48:10.158Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541913,541914,540447,TO_TIMESTAMP('2024-12-18 18:48:10.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ShipmentSchedule -> C_PurchaseCandidate',TO_TIMESTAMP('2024-12-18 18:48:10.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Reference: C_PurchaseCandidate_Target_For_M_ShipmentSchedule
-- Table: C_PurchaseCandidate
-- Key: C_PurchaseCandidate.C_PurchaseCandidate_ID
-- 2024-12-18T18:52:02.542Z
UPDATE AD_Ref_Table SET AD_Key=557857, WhereClause='exists (     select 1     from  c_purchasecandidate     where             c_purchasecandidate.m_shipmentschedule_id = @M_ShipmentSchedule_ID/-1@ )',Updated=TO_TIMESTAMP('2024-12-18 18:52:02.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541914
;

-- Reference: C_PurchaseCandidate_Target_For_M_ShipmentSchedule
-- Table: C_PurchaseCandidate
-- Key: C_PurchaseCandidate.C_PurchaseCandidate_ID
-- 2024-12-18T18:53:16.067Z
UPDATE AD_Ref_Table SET AD_Window_ID=540375,Updated=TO_TIMESTAMP('2024-12-18 18:53:16.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541914
;

-- Reference: C_PurchaseCandidate_Target_For_M_ShipmentSchedule
-- Table: C_PurchaseCandidate
-- Key: C_PurchaseCandidate.C_PurchaseCandidate_ID
-- 2024-12-18T18:56:20.977Z
UPDATE AD_Ref_Table SET WhereClause='exists (     select 1     from  c_purchasecandidate candidate     join m_shipmentschedule ss on ss.m_shipmentschedule_id = candidate.m_shipmentschedule_id      where             candidate.m_shipmentschedule_id = @M_ShipmentSchedule_ID/-1@ and c_purchasecandidate.c_purchasecandidate_id = candidate.c_purchasecandidate_id )',Updated=TO_TIMESTAMP('2024-12-18 18:56:20.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541914
;

-- Name: C_PurchaseCandidate_Source
-- 2024-12-18T19:00:27.675Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541915,TO_TIMESTAMP('2024-12-18 19:00:27.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_PurchaseCandidate_Source',TO_TIMESTAMP('2024-12-18 19:00:27.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2024-12-18T19:00:27.678Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541915 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_PurchaseCandidate_Source
-- Table: C_PurchaseCandidate
-- Key: C_PurchaseCandidate.C_PurchaseCandidate_ID
-- 2024-12-18T19:01:09.607Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,557857,0,541915,540861,540375,TO_TIMESTAMP('2024-12-18 19:01:09.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2024-12-18 19:01:09.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_ShipmentSchedule_Target
-- 2024-12-18T19:01:51.116Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541916,TO_TIMESTAMP('2024-12-18 19:01:50.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_ShipmentSchedule_Target',TO_TIMESTAMP('2024-12-18 19:01:50.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2024-12-18T19:01:51.118Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541916 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipmentSchedule_Target
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2024-12-18T19:05:12.998Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,500232,0,541916,500221,500221,TO_TIMESTAMP('2024-12-18 19:05:12.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2024-12-18 19:05:12.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'exists (     select 1     from  m_shipmentschedule ss     join c_purchasecandidate pc on pc.m_shipmentschedule_id = ss.m_shipmentschedule_id      where             pc.c_purchasecandidate_id = @C_PurchaseCandidate_ID/-1@ and ss.m_shipmentschedule_id = m_shipmentschedule.m_shipmentschedule_id )')
;

-- 2024-12-18T19:05:58.482Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541915,541916,540448,TO_TIMESTAMP('2024-12-18 19:05:58.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_PurchaseCandidate -> M_ShipmentSchedule',TO_TIMESTAMP('2024-12-18 19:05:58.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Reference: M_ShipmentSchedule_Target
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2024-12-18T19:18:36.682Z
UPDATE AD_Ref_Table SET WhereClause='exists (     select 1     from  m_shipmentschedule ss     join c_purchasecandidate pc on pc.m_shipmentschedule_id = ss.m_shipmentschedule_id      where             pc.c_purchasecandidate_id = @C_PurchaseCandidate_ID/-1@ and ss.m_shipmentschedule_id = @M_ShipmentSchedule_ID/-1@ )',Updated=TO_TIMESTAMP('2024-12-18 19:18:36.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541916
;

-- 2024-12-18T19:18:42.709Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='Y',Updated=TO_TIMESTAMP('2024-12-18 19:18:42.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540448
;

-- Reference: M_ShipmentSchedule_Target
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2024-12-18T19:19:34.701Z
UPDATE AD_Ref_Table SET WhereClause='exists (     select 1     from  m_shipmentschedule ss     join c_purchasecandidate pc on pc.m_shipmentschedule_id = ss.m_shipmentschedule_id      where             pc.c_purchasecandidate_id = @C_PurchaseCandidate_ID/-1@ and ss.m_shipmentschedule_id = M_ShipmentSchedule.M_ShipmentSchedule_ID )',Updated=TO_TIMESTAMP('2024-12-18 19:19:34.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541916
;

-- 2024-12-18T19:19:40.745Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='N',Updated=TO_TIMESTAMP('2024-12-18 19:19:40.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540448
;

-- 2024-12-18T19:21:13.937Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='Y',Updated=TO_TIMESTAMP('2024-12-18 19:21:13.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540447
;

-- 2024-12-18T19:21:46.971Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='N',Updated=TO_TIMESTAMP('2024-12-18 19:21:46.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_RelationType_ID=540447
;

-- Reference: C_PurchaseCandidate_Target_For_M_ShipmentSchedule
-- Table: C_PurchaseCandidate
-- Key: C_PurchaseCandidate.C_PurchaseCandidate_ID
-- 2024-12-18T19:39:52.330Z
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1  from c_purchasecandidate candidate           JOIN m_shipmentschedule ss on ss.m_shipmentschedule_id = candidate.m_shipmentschedule_id  where candidate.m_shipmentschedule_id = @M_ShipmentSchedule_ID / -1@    AND c_purchasecandidate.c_purchasecandidate_id = candidate.c_purchasecandidate_id AND candidate.c_purchasecandidate_id != @C_PurchaseCandidate_ID / -1@)',Updated=TO_TIMESTAMP('2024-12-18 19:39:52.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541914
;

-- 2024-12-19T10:38:14.086Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583418,0,'IsPurchaseCandidate',TO_TIMESTAMP('2024-12-19 10:38:13.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Bestellkandidat','Bestellkandidat',TO_TIMESTAMP('2024-12-19 10:38:13.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T10:38:14.104Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583418 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsPurchaseCandidate
-- 2024-12-19T10:38:27.855Z
UPDATE AD_Element_Trl SET Name='Purchase Candidate', PrintName='Purchase Candidate',Updated=TO_TIMESTAMP('2024-12-19 10:38:27.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583418 AND AD_Language='en_US'
;

-- 2024-12-19T10:38:27.891Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583418,'en_US') 
;

-- Column: M_ShipmentSchedule.IsPurchaseCandidate
-- Column SQL (old): null
-- Column: M_ShipmentSchedule.IsPurchaseCandidate
-- 2024-12-19T10:40:27.980Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589552,583418,0,20,500221,'XX','IsPurchaseCandidate','(CASE WHEN EXISTS(SELECT 1 from c_purchasecandidate candidate where candidate.m_shipmentschedule_id = M_ShipmentSchedule.m_shipmentschedule_id) THEN ''Y'' ELSE ''N'' END)',TO_TIMESTAMP('2024-12-19 10:40:27.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.inoutcandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Bestellkandidat',0,0,TO_TIMESTAMP('2024-12-19 10:40:27.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T10:40:27.984Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589552 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T10:40:27.991Z
/* DDL */  select update_Column_Translation_From_AD_Element(583418) 
;

-- Column: M_ShipmentSchedule.IsPurchaseCandidate
-- Column: M_ShipmentSchedule.IsPurchaseCandidate
-- 2024-12-19T10:40:36.052Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 10:40:36.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589552
;

-- Process: Create_Purchase_Candidates_From_Shipment_Schedules(de.metas.process.ExecuteUpdateSQL)
-- 2024-12-19T10:45:30.231Z
UPDATE AD_Process_Trl SET Name='Bestellkandidat erstellen',Updated=TO_TIMESTAMP('2024-12-19 10:45:30.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585442
;

-- Process: Create_Purchase_Candidates_From_Shipment_Schedules(de.metas.process.ExecuteUpdateSQL)
-- 2024-12-19T10:45:30.231Z
UPDATE AD_Process_Trl SET Name='Bestellkandidat erstellen',Updated=TO_TIMESTAMP('2024-12-19 10:45:30.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585442
;

-- Process: Create_Purchase_Candidates_From_Shipment_Schedules(de.metas.process.ExecuteUpdateSQL)
-- 2024-12-19T10:56:27.699Z
UPDATE AD_Process_Trl SET Name='Create Purchase Candidate',Updated=TO_TIMESTAMP('2024-12-19 10:56:27.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585442
;

-- Process: Create_Purchase_Candidates_From_Shipment_Schedules(de.metas.process.ExecuteUpdateSQL)
-- 2024-12-19T11:02:24.235Z
UPDATE AD_Process_Trl SET Name='Bestellkandidat erstellen',Updated=TO_TIMESTAMP('2024-12-19 11:02:24.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585442
;

-- Field: Lieferdisposition -> Auslieferplan -> Bestellkandidat
-- Column: M_ShipmentSchedule.IsPurchaseCandidate
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Bestellkandidat
-- Column: M_ShipmentSchedule.IsPurchaseCandidate
-- 2024-12-19T12:07:20.560Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589552,734624,0,500221,TO_TIMESTAMP('2024-12-19 12:07:20.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Bestellkandidat',TO_TIMESTAMP('2024-12-19 12:07:20.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T12:07:20.564Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734624 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T12:07:20.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583418) 
;

-- 2024-12-19T12:07:20.648Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734624
;

-- 2024-12-19T12:07:20.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734624)
;

-- UI Element: Lieferdisposition -> Auslieferplan.Bestellkandidat
-- Column: M_ShipmentSchedule.IsPurchaseCandidate
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> flags.Bestellkandidat
-- Column: M_ShipmentSchedule.IsPurchaseCandidate
-- 2024-12-19T12:07:49.021Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734624,0,500221,627771,540972,'F',TO_TIMESTAMP('2024-12-19 12:07:48.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Bestellkandidat',70,0,0,TO_TIMESTAMP('2024-12-19 12:07:48.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Bestelldisposition -> Bestelldisposition -> Lieferdisposition
-- Column: C_PurchaseCandidate.M_ShipmentSchedule_ID
-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Lieferdisposition
-- Column: C_PurchaseCandidate.M_ShipmentSchedule_ID
-- 2024-12-19T12:08:25.164Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589549,734625,0,540894,TO_TIMESTAMP('2024-12-19 12:08:25.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.purchasecandidate','Y','N','N','N','N','N','N','N','Lieferdisposition',TO_TIMESTAMP('2024-12-19 12:08:25.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T12:08:25.167Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734625 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T12:08:25.171Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500221) 
;

-- 2024-12-19T12:08:25.189Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734625
;

-- 2024-12-19T12:08:25.194Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734625)
;

-- UI Element: Bestelldisposition -> Bestelldisposition.Lieferdisposition
-- Column: C_PurchaseCandidate.M_ShipmentSchedule_ID
-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> order.Lieferdisposition
-- Column: C_PurchaseCandidate.M_ShipmentSchedule_ID
-- 2024-12-19T12:08:45.788Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734625,0,540894,627772,541248,'F',TO_TIMESTAMP('2024-12-19 12:08:45.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Lieferdisposition',40,0,0,TO_TIMESTAMP('2024-12-19 12:08:45.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
