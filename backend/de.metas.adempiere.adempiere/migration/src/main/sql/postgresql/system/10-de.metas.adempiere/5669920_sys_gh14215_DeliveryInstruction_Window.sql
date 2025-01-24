-- 2022-12-23T13:33:13.708Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581903,0,TO_TIMESTAMP('2022-12-23 15:33:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Instruction','Delivery Instruction',TO_TIMESTAMP('2022-12-23 15:33:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:33:13.751Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581903 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Delivery Instruction, InternalName=null
-- 2022-12-23T13:33:39.145Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581903,0,541657,TO_TIMESTAMP('2022-12-23 15:33:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Delivery Instruction','N',TO_TIMESTAMP('2022-12-23 15:33:38','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-12-23T13:33:39.188Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541657 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-12-23T13:33:39.352Z
/* DDL */  select update_window_translation_from_ad_element(581903) 
;

-- 2022-12-23T13:33:39.406Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541657
;

-- 2022-12-23T13:33:39.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541657)
;

-- Window: Delivery Instruction, InternalName=null
-- 2022-12-23T13:34:21.992Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='D', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=NULL, WindowType='T', WinHeight=NULL, WinWidth=NULL, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2022-12-23 15:34:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541657
;

-- Tab: Delivery Instruction(541657,D) -> Transportation Delivery
-- Table: M_ShipperTransportation
-- 2022-12-23T13:34:22.791Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573618,0,540061,546732,540030,541657,'Y',TO_TIMESTAMP('2022-12-23 15:34:22','YYYY-MM-DD HH24:MI:SS'),100,'D','N','A','M_ShipperTransportation','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Transportation Delivery','N',10,0,TO_TIMESTAMP('2022-12-23 15:34:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:22.834Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546732 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-12-23T13:34:22.878Z
/* DDL */  select update_tab_translation_from_ad_element(573618) 
;

-- 2022-12-23T13:34:22.926Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546732)
;

-- 2022-12-23T13:34:23.017Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546732
;

-- 2022-12-23T13:34:23.058Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546732, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540096
;

-- 2022-12-23T13:34:23.732Z
INSERT INTO AD_Tab_Callout (AD_Client_ID,AD_Org_ID,AD_Tab_Callout_ID,AD_Tab_ID,Classname,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540087,546732,'de.metas.shipping.callout.M_Shipper_Transportation_Tab_Callout',TO_TIMESTAMP('2022-12-23 15:34:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-12-23 15:34:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Active
-- Column: M_ShipperTransportation.IsActive
-- 2022-12-23T13:34:24.534Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540440,710072,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:23','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.',0,'Y','N','N','N','N','N','N','N','Active',10,0,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:24.578Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:24.622Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-12-23T13:34:25.386Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710072
;

-- 2022-12-23T13:34:25.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710072)
;

-- 2022-12-23T13:34:25.512Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710072
;

-- 2022-12-23T13:34:25.555Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710072, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542177
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Processed
-- Column: M_ShipperTransportation.Processed
-- 2022-12-23T13:34:26.249Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540443,710073,1001859,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'The document has been processed',1,'D','The Processed checkbox indicates that a document has been processed.',0,'Y','N','N','N','N','N','N','N','Processed',20,0,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:26.290Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:26.332Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001859) 
;

-- 2022-12-23T13:34:26.378Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710073
;

-- 2022-12-23T13:34:26.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710073)
;

-- 2022-12-23T13:34:26.503Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710073
;

-- 2022-12-23T13:34:26.544Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710073, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542178
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Process Now
-- Column: M_ShipperTransportation.Processing
-- 2022-12-23T13:34:27.209Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540444,710074,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:26','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','N','N','N','N','N','N','N','Process Now',30,0,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:27.253Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:27.295Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2022-12-23T13:34:27.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710074
;

-- 2022-12-23T13:34:27.448Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710074)
;

-- 2022-12-23T13:34:27.532Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710074
;

-- 2022-12-23T13:34:27.573Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710074, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542179
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Client
-- Column: M_ShipperTransportation.AD_Client_ID
-- 2022-12-23T13:34:28.263Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540427,710075,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:27','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',22,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.',0,'Y','Y','Y','N','N','N','Y','N','Client',10,10,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:28.305Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:28.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-12-23T13:34:28.707Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710075
;

-- 2022-12-23T13:34:28.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710075)
;

-- 2022-12-23T13:34:28.862Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710075
;

-- 2022-12-23T13:34:28.903Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710075, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542180
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Organisation
-- Column: M_ShipperTransportation.AD_Org_ID
-- 2022-12-23T13:34:29.540Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540428,710076,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',22,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.',0,'Y','Y','Y','N','N','N','N','Y','Organisation',20,20,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:29.582Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:29.624Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-12-23T13:34:29.924Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710076
;

-- 2022-12-23T13:34:29.966Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710076)
;

-- 2022-12-23T13:34:30.052Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710076
;

-- 2022-12-23T13:34:30.093Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710076, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542181
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> No.
-- Column: M_ShipperTransportation.DocumentNo
-- 2022-12-23T13:34:30.674Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540439,710077,1002218,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:30','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',20,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','Y','N','N','No.',30,30,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:30.716Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:30.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002218) 
;

-- 2022-12-23T13:34:30.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710077
;

-- 2022-12-23T13:34:30.850Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710077)
;

-- 2022-12-23T13:34:30.936Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710077
;

-- 2022-12-23T13:34:30.978Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710077, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542182
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Document Date
-- Column: M_ShipperTransportation.DateDoc
-- 2022-12-23T13:34:31.620Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540442,710078,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:31','YYYY-MM-DD HH24:MI:SS'),100,'',7,'D','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.',0,'Y','Y','Y','N','N','N','N','Y','Document Date',40,40,-1,1,1,TO_TIMESTAMP('2022-12-23 15:34:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:31.660Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:31.703Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(265) 
;

-- 2022-12-23T13:34:31.807Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710078
;

-- 2022-12-23T13:34:31.849Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710078)
;

-- 2022-12-23T13:34:31.933Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710078
;

-- 2022-12-23T13:34:31.975Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710078, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542183
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Shipper Partner
-- Column: M_ShipperTransportation.Shipper_BPartner_ID
-- 2022-12-23T13:34:32.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540430,710079,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:32','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to be used as shipper',20,'D',0,'Y','Y','Y','N','N','N','N','N','Shipper Partner',50,50,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:32.663Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:32.705Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540091) 
;

-- 2022-12-23T13:34:32.750Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710079
;

-- 2022-12-23T13:34:32.791Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710079)
;

-- 2022-12-23T13:34:32.876Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710079
;

-- 2022-12-23T13:34:32.918Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710079, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542184
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Shipper Location
-- Column: M_ShipperTransportation.Shipper_Location_ID
-- 2022-12-23T13:34:33.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540447,710080,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:32','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for Shipper',22,'D',0,'Y','Y','Y','N','N','N','N','Y','Shipper Location',60,60,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:33.588Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:33.630Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540095) 
;

-- 2022-12-23T13:34:33.674Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710080
;

-- 2022-12-23T13:34:33.716Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710080)
;

-- 2022-12-23T13:34:33.816Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710080
;

-- 2022-12-23T13:34:33.867Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710080, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542185
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Description
-- Column: M_ShipperTransportation.Description
-- 2022-12-23T13:34:34.489Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540436,710081,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:33','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','',0,'Y','Y','Y','N','N','N','N','N','Description',70,70,0,999,1,TO_TIMESTAMP('2022-12-23 15:34:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:34.530Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:34.572Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-12-23T13:34:34.807Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710081
;

-- 2022-12-23T13:34:34.848Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710081)
;

-- 2022-12-23T13:34:34.930Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710081
;

-- 2022-12-23T13:34:34.972Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710081, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542186
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Packstücke erfassen
-- Column: M_ShipperTransportation.CreateShippingPackages
-- 2022-12-23T13:34:35.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540435,710082,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:35','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','N','N','N','N','N','N','N','Packstücke erfassen',80,80,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:35.642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:35.685Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540094) 
;

-- 2022-12-23T13:34:35.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710082
;

-- 2022-12-23T13:34:35.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710082)
;

-- 2022-12-23T13:34:35.884Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710082
;

-- 2022-12-23T13:34:35.925Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710082, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542187
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Weight
-- Column: M_ShipperTransportation.PackageWeight
-- 2022-12-23T13:34:36.543Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540448,710083,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:35','YYYY-MM-DD HH24:MI:SS'),100,'Weight of a package',22,'D','The Weight indicates the weight of the package',0,'Y','Y','Y','N','N','N','Y','N','Weight',110,110,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:36.586Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:36.628Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540096) 
;

-- 2022-12-23T13:34:36.673Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710083
;

-- 2022-12-23T13:34:36.714Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710083)
;

-- 2022-12-23T13:34:36.855Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710083
;

-- 2022-12-23T13:34:36.896Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710083, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542190
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Package Net Total
-- Column: M_ShipperTransportation.PackageNetTotal
-- 2022-12-23T13:34:37.522Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540429,710084,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:36','YYYY-MM-DD HH24:MI:SS'),100,22,'D',0,'Y','Y','Y','N','N','N','Y','Y','Package Net Total',120,120,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:37.564Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:37.606Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540090) 
;

-- 2022-12-23T13:34:37.650Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710084
;

-- 2022-12-23T13:34:37.691Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710084)
;

-- 2022-12-23T13:34:37.775Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710084
;

-- 2022-12-23T13:34:37.864Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710084, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542191
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Approved
-- Column: M_ShipperTransportation.IsApproved
-- 2022-12-23T13:34:38.498Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540441,101,710085,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:37','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document requires approval',1,'D','The Approved checkbox indicates if this document requires approval before it can be processed.',0,'Y','Y','Y','N','N','N','Y','N','Approved',130,130,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:38.540Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:38.582Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351) 
;

-- 2022-12-23T13:34:38.637Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710085
;

-- 2022-12-23T13:34:38.678Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710085)
;

-- 2022-12-23T13:34:38.762Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710085
;

-- 2022-12-23T13:34:38.871Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710085, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542192
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Status
-- Column: M_ShipperTransportation.DocStatus
-- 2022-12-23T13:34:39.512Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540438,101,710086,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:38','YYYY-MM-DD HH24:MI:SS'),100,'',2,'D','',0,'Y','Y','Y','N','N','N','Y','N','Status',140,140,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:39.554Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:39.596Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289) 
;

-- 2022-12-23T13:34:39.651Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710086
;

-- 2022-12-23T13:34:39.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710086)
;

-- 2022-12-23T13:34:39.779Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710086
;

-- 2022-12-23T13:34:39.879Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710086, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542193
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Process Batch
-- Column: M_ShipperTransportation.DocAction
-- 2022-12-23T13:34:40.508Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540437,101,710087,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:39','YYYY-MM-DD HH24:MI:SS'),100,2,'D',0,'Y','Y','Y','N','N','N','N','Y','Process Batch',150,150,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:40.550Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:40.593Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(287) 
;

-- 2022-12-23T13:34:40.643Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710087
;

-- 2022-12-23T13:34:40.684Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710087)
;

-- 2022-12-23T13:34:40.768Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710087
;

-- 2022-12-23T13:34:40.809Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710087, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542194
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Transportation Order
-- Column: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2022-12-23T13:34:41.518Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540426,710088,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:40','YYYY-MM-DD HH24:MI:SS'),100,22,'D',0,'Y','N','N','N','N','N','N','N','Transportation Order',160,0,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:41.560Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:41.602Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089) 
;

-- 2022-12-23T13:34:41.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710088
;

-- 2022-12-23T13:34:41.690Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710088)
;

-- 2022-12-23T13:34:41.775Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710088
;

-- 2022-12-23T13:34:41.816Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710088, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542195
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Document Type
-- Column: M_ShipperTransportation.C_DocType_ID
-- 2022-12-23T13:34:42.470Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,543966,710089,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:41','YYYY-MM-DD HH24:MI:SS'),100,'Document type or rules',0,'D','The Document Type determines document sequence and processing rules',0,'Y','Y','Y','N','N','N','N','N','Document Type',160,160,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:42.511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:42.553Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2022-12-23T13:34:42.622Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710089
;

-- 2022-12-23T13:34:42.664Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710089)
;

-- 2022-12-23T13:34:42.747Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710089
;

-- 2022-12-23T13:34:42.790Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710089, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 545063
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Shipper
-- Column: M_ShipperTransportation.M_Shipper_ID
-- 2022-12-23T13:34:43.452Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550685,710090,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:42','YYYY-MM-DD HH24:MI:SS'),100,'Method or manner of product delivery',0,'D','The Shipper indicates the method of delivering product',0,'Y','Y','Y','N','N','N','N','N','Shipper',125,125,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:43.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:43.535Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455) 
;

-- 2022-12-23T13:34:43.591Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710090
;

-- 2022-12-23T13:34:43.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710090)
;

-- 2022-12-23T13:34:43.716Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710090
;

-- 2022-12-23T13:34:43.760Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710090, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554262
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Account manager
-- Column: M_ShipperTransportation.SalesRep_ID
-- 2022-12-23T13:34:44.402Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551101,710091,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:43','YYYY-MM-DD HH24:MI:SS'),100,'@#SalesRep_ID@','Sales Representative or Company Agent',0,'D','The Sales Representative indicates the Sales Rep for this Region.  Any Sales Rep must be a valid internal user.',0,'Y','Y','Y','N','N','N','N','Y','Account manager',127,127,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:44.443Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:44.485Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2022-12-23T13:34:44.549Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710091
;

-- 2022-12-23T13:34:44.591Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710091)
;

-- 2022-12-23T13:34:44.675Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710091
;

-- 2022-12-23T13:34:44.717Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710091, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554690
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Date to Fetch
-- Column: M_ShipperTransportation.DateToBeFetched
-- 2022-12-23T13:34:45.377Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551157,710092,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:44','YYYY-MM-DD HH24:MI:SS'),100,'Date and time to fetch','D',0,'Y','Y','Y','N','N','N','N','Y','Date to Fetch',45,45,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:45.420Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:45.462Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542540) 
;

-- 2022-12-23T13:34:45.510Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710092
;

-- 2022-12-23T13:34:45.551Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710092)
;

-- 2022-12-23T13:34:45.635Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710092
;

-- 2022-12-23T13:34:45.676Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710092, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554746
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Tour
-- Column: M_ShipperTransportation.M_Tour_ID
-- 2022-12-23T13:34:46.332Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551431,710093,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:45','YYYY-MM-DD HH24:MI:SS'),100,22,'D',0,'Y','Y','Y','N','N','N','N','Y','Tour',65,65,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:46.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:46.415Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541366) 
;

-- 2022-12-23T13:34:46.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710093
;

-- 2022-12-23T13:34:46.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710093)
;

-- 2022-12-23T13:34:46.587Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710093
;

-- 2022-12-23T13:34:46.628Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710093, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555104
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Pickup Time From
-- Column: M_ShipperTransportation.PickupTimeFrom
-- 2022-12-23T13:34:47.324Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569384,710094,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:46','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','Pickup Time From',170,170,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:47.364Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:47.418Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577278) 
;

-- 2022-12-23T13:34:47.462Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710094
;

-- 2022-12-23T13:34:47.503Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710094)
;

-- 2022-12-23T13:34:47.588Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710094
;

-- 2022-12-23T13:34:47.629Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710094, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591229
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Abholung Uhrzeit bis
-- Column: M_ShipperTransportation.PickupTimeTo
-- 2022-12-23T13:34:48.278Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569385,710095,0,546732,0,TO_TIMESTAMP('2022-12-23 15:34:47','YYYY-MM-DD HH24:MI:SS'),100,255,'D',0,'Y','Y','Y','N','N','N','N','N','Abholung Uhrzeit bis',180,180,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:48.318Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:48.361Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577279) 
;

-- 2022-12-23T13:34:48.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710095
;

-- 2022-12-23T13:34:48.447Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710095)
;

-- 2022-12-23T13:34:48.532Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710095
;

-- 2022-12-23T13:34:48.574Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710095, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 591230
;

-- Tab: Delivery Instruction(541657,D) -> Versandpackung
-- Table: M_ShippingPackage
-- 2022-12-23T13:34:49.201Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,540458,572581,0,546733,540031,541657,'Y',TO_TIMESTAMP('2022-12-23 15:34:48','YYYY-MM-DD HH24:MI:SS'),100,'D','N','A','M_ShippingPackage','Y','N','Y','Y','N','N','N','N','Y','N','N','N','Y','Y','Y','N','N','Versandpackung','N',20,1,TO_TIMESTAMP('2022-12-23 15:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:49.244Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546733 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-12-23T13:34:49.287Z
/* DDL */  select update_tab_translation_from_ad_element(572581) 
;

-- 2022-12-23T13:34:49.332Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546733)
;

-- 2022-12-23T13:34:49.415Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546733
;

-- 2022-12-23T13:34:49.457Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546733, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540097
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Active
-- Column: M_ShippingPackage.IsActive
-- 2022-12-23T13:34:50.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540457,710096,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:49','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.',0,'Y','N','N','N','N','N','N','N','Active',10,0,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:50.533Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:50.575Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-12-23T13:34:50.856Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710096
;

-- 2022-12-23T13:34:50.898Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710096)
;

-- 2022-12-23T13:34:50.981Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710096
;

-- 2022-12-23T13:34:51.022Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710096, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542196
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Processed
-- Column: M_ShippingPackage.Processed
-- 2022-12-23T13:34:51.638Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540461,710097,1002220,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:51','YYYY-MM-DD HH24:MI:SS'),100,'The document has been processed',1,'D','The Processed checkbox indicates that a document has been processed.',0,'Y','N','N','N','N','N','N','N','Processed',20,0,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:51.680Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:51.722Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002220) 
;

-- 2022-12-23T13:34:51.769Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710097
;

-- 2022-12-23T13:34:51.811Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710097)
;

-- 2022-12-23T13:34:51.896Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710097
;

-- 2022-12-23T13:34:51.980Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710097, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542197
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Client
-- Column: M_ShippingPackage.AD_Client_ID
-- 2022-12-23T13:34:52.607Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540450,710098,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',22,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.',0,'Y','Y','Y','N','N','N','Y','N','Client',10,10,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:52.649Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:52.690Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-12-23T13:34:52.993Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710098
;

-- 2022-12-23T13:34:53.034Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710098)
;

-- 2022-12-23T13:34:53.118Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710098
;

-- 2022-12-23T13:34:53.159Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710098, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542198
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Organisation
-- Column: M_ShippingPackage.AD_Org_ID
-- 2022-12-23T13:34:53.796Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540451,710099,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',22,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.',0,'Y','Y','Y','N','N','N','N','Y','Organisation',20,20,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:53.838Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:53.883Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-12-23T13:34:54.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710099
;

-- 2022-12-23T13:34:54.171Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710099)
;

-- 2022-12-23T13:34:54.253Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710099
;

-- 2022-12-23T13:34:54.293Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710099, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542199
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Transportation Order
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2022-12-23T13:34:54.923Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540458,710100,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:54','YYYY-MM-DD HH24:MI:SS'),100,22,'D',0,'Y','Y','Y','N','N','N','N','N','Transportation Order',30,30,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:54.964Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:55.036Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089) 
;

-- 2022-12-23T13:34:55.083Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710100
;

-- 2022-12-23T13:34:55.123Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710100)
;

-- 2022-12-23T13:34:55.210Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710100
;

-- 2022-12-23T13:34:55.251Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710100, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542200
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Shipment/Receipt
-- Column: M_ShippingPackage.M_InOut_ID
-- 2022-12-23T13:34:55.852Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540452,710101,1002574,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:55','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',22,'D','The Material Shipment / Receipt ',0,'Y','Y','Y','N','N','N','N','N','Shipment/Receipt',40,40,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:55.893Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:55.935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002574) 
;

-- 2022-12-23T13:34:56.008Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710101
;

-- 2022-12-23T13:34:56.050Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710101)
;

-- 2022-12-23T13:34:56.133Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710101
;

-- 2022-12-23T13:34:56.174Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710101, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542201
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Package
-- Column: M_ShippingPackage.M_Package_ID
-- 2022-12-23T13:34:56.799Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540459,710102,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:56','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Package',22,'D','A Shipment can have one or more Packages.  A Package may be individually tracked.',0,'Y','Y','Y','N','N','N','N','Y','Package',50,50,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:56.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:56.884Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2410) 
;

-- 2022-12-23T13:34:56.930Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710102
;

-- 2022-12-23T13:34:56.972Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710102)
;

-- 2022-12-23T13:34:57.108Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710102
;

-- 2022-12-23T13:34:57.149Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710102, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542202
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Business Partner
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2022-12-23T13:34:57.730Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540460,710103,1002988,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:57','YYYY-MM-DD HH24:MI:SS'),100,'Identifies a Business Partner',22,'D','A Business Partner is anyone with whom you transact.  This can include Vendor, Customer, Employee or Salesperson',0,'Y','Y','Y','N','N','N','N','N','Business Partner ',60,60,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:57.773Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:57.815Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002988) 
;

-- 2022-12-23T13:34:57.860Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710103
;

-- 2022-12-23T13:34:57.903Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710103)
;

-- 2022-12-23T13:34:57.988Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710103
;

-- 2022-12-23T13:34:58.076Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710103, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542203
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Partner Location
-- Column: M_ShippingPackage.C_BPartner_Location_ID
-- 2022-12-23T13:34:58.651Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540464,710104,1002624,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Identifies the (ship to) address for this Business Partner',22,'D','The Partner address indicates the location of a Business Partner',0,'Y','Y','Y','N','N','N','N','Y','Partner Location',70,70,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:58.692Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:58.734Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002624) 
;

-- 2022-12-23T13:34:58.779Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710104
;

-- 2022-12-23T13:34:58.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710104)
;

-- 2022-12-23T13:34:58.904Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710104
;

-- 2022-12-23T13:34:58.945Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710104, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542204
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Weight
-- Column: M_ShippingPackage.PackageWeight
-- 2022-12-23T13:34:59.583Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540456,710105,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Weight of a package',22,'D','The Weight indicates the weight of the package',0,'Y','Y','Y','N','N','N','N','N','Weight',80,80,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:34:59.625Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:34:59.668Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540096) 
;

-- 2022-12-23T13:34:59.714Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710105
;

-- 2022-12-23T13:34:59.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710105)
;

-- 2022-12-23T13:34:59.839Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710105
;

-- 2022-12-23T13:34:59.881Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710105, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542205
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Package Net Total
-- Column: M_ShippingPackage.PackageNetTotal
-- 2022-12-23T13:35:00.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540453,710106,0,546733,0,TO_TIMESTAMP('2022-12-23 15:34:59','YYYY-MM-DD HH24:MI:SS'),100,22,'D',0,'Y','Y','Y','N','N','N','N','Y','Package Net Total',90,90,0,1,1,TO_TIMESTAMP('2022-12-23 15:34:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:35:00.646Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:35:00.688Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540090) 
;

-- 2022-12-23T13:35:00.734Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710106
;

-- 2022-12-23T13:35:00.775Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710106)
;

-- 2022-12-23T13:35:00.858Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710106
;

-- 2022-12-23T13:35:00.899Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710106, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542206
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Shipping Package
-- Column: M_ShippingPackage.M_ShippingPackage_ID
-- 2022-12-23T13:35:01.625Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540449,710107,0,546733,0,TO_TIMESTAMP('2022-12-23 15:35:00','YYYY-MM-DD HH24:MI:SS'),100,22,'D',0,'Y','N','N','N','N','N','N','N','Shipping Package',100,0,0,1,1,TO_TIMESTAMP('2022-12-23 15:35:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:35:01.668Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:35:01.710Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540097) 
;

-- 2022-12-23T13:35:01.755Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710107
;

-- 2022-12-23T13:35:01.796Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710107)
;

-- 2022-12-23T13:35:01.880Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710107
;

-- 2022-12-23T13:35:01.921Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710107, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 542207
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Sales order
-- Column: M_ShippingPackage.C_Order_ID
-- 2022-12-23T13:35:02.609Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550698,710108,0,546733,0,TO_TIMESTAMP('2022-12-23 15:35:01','YYYY-MM-DD HH24:MI:SS'),100,'Order',0,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.',0,'Y','Y','Y','N','N','N','N','N','Sales order',100,100,0,1,1,TO_TIMESTAMP('2022-12-23 15:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:35:02.650Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:35:02.692Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2022-12-23T13:35:02.754Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710108
;

-- 2022-12-23T13:35:02.795Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710108)
;

-- 2022-12-23T13:35:02.881Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710108
;

-- 2022-12-23T13:35:02.924Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710108, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554349
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Note
-- Column: M_ShippingPackage.Note
-- 2022-12-23T13:35:03.597Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551102,710109,0,546733,0,TO_TIMESTAMP('2022-12-23 15:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Optional additional user defined information',0,'D','The Note field allows for optional entry of user defined information regarding this record',0,'Y','Y','Y','N','N','N','N','N','Note',110,110,0,1,1,TO_TIMESTAMP('2022-12-23 15:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:35:03.640Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:35:03.682Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2022-12-23T13:35:03.736Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710109
;

-- 2022-12-23T13:35:03.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710109)
;

-- 2022-12-23T13:35:03.864Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710109
;

-- 2022-12-23T13:35:03.905Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710109, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554691
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Fetched
-- Column: M_ShippingPackage.IsToBeFetched
-- 2022-12-23T13:35:04.552Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569895,710110,0,546733,0,TO_TIMESTAMP('2022-12-23 15:35:03','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Fetched',120,120,0,1,1,TO_TIMESTAMP('2022-12-23 15:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:35:04.593Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:35:04.635Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542456) 
;

-- 2022-12-23T13:35:04.680Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710110
;

-- 2022-12-23T13:35:04.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710110)
;

-- 2022-12-23T13:35:04.805Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 710110
;

-- 2022-12-23T13:35:04.846Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 710110, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 596163
;

-- Tab: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D)
-- UI Section: main
-- 2022-12-23T13:35:05.443Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546732,545361,TO_TIMESTAMP('2022-12-23 15:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-23 15:35:05','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-12-23T13:35:05.484Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545361 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-12-23T13:35:05.569Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545361
;

-- 2022-12-23T13:35:05.611Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545361, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540284
;

-- UI Section: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main
-- UI Column: 10
-- 2022-12-23T13:35:06.171Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546539,545361,TO_TIMESTAMP('2022-12-23 15:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-23 15:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10
-- UI Element Group: default
-- 2022-12-23T13:35:06.769Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546539,550198,TO_TIMESTAMP('2022-12-23 15:35:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-12-23 15:35:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10 -> default.Transporteur
-- Column: M_ShipperTransportation.Shipper_BPartner_ID
-- 2022-12-23T13:35:07.603Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710079,0,546732,550198,614584,'F',TO_TIMESTAMP('2022-12-23 15:35:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Transporteur',10,10,10,TO_TIMESTAMP('2022-12-23 15:35:06','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10 -> default.Ladeort
-- Column: M_ShipperTransportation.Shipper_Location_ID
-- 2022-12-23T13:35:08.362Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710080,0,546732,550198,614585,'F',TO_TIMESTAMP('2022-12-23 15:35:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Ladeort',20,20,20,TO_TIMESTAMP('2022-12-23 15:35:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10 -> default.Lieferweg
-- Column: M_ShipperTransportation.M_Shipper_ID
-- 2022-12-23T13:35:09.098Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710090,0,546732,550198,614586,'F',TO_TIMESTAMP('2022-12-23 15:35:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Lieferweg',30,30,0,TO_TIMESTAMP('2022-12-23 15:35:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10
-- UI Element Group: description
-- 2022-12-23T13:35:09.695Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546539,550199,TO_TIMESTAMP('2022-12-23 15:35:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2022-12-23 15:35:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10 -> description.Beschreibung
-- Column: M_ShipperTransportation.Description
-- 2022-12-23T13:35:10.538Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710081,0,546732,550199,614587,'F',TO_TIMESTAMP('2022-12-23 15:35:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beschreibung',10,90,0,TO_TIMESTAMP('2022-12-23 15:35:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10 -> description.Tour
-- Column: M_ShipperTransportation.M_Tour_ID
-- 2022-12-23T13:35:11.313Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710093,0,546732,550199,614588,'F',TO_TIMESTAMP('2022-12-23 15:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Tour',20,40,0,TO_TIMESTAMP('2022-12-23 15:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main
-- UI Column: 20
-- 2022-12-23T13:35:11.857Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546540,545361,TO_TIMESTAMP('2022-12-23 15:35:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-12-23 15:35:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20
-- UI Element Group: doctype
-- 2022-12-23T13:35:12.449Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546540,550200,TO_TIMESTAMP('2022-12-23 15:35:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','doctype',10,TO_TIMESTAMP('2022-12-23 15:35:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Belegart
-- Column: M_ShipperTransportation.C_DocType_ID
-- 2022-12-23T13:35:13.278Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710089,0,546732,550200,614589,'F',TO_TIMESTAMP('2022-12-23 15:35:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Belegart',10,60,40,TO_TIMESTAMP('2022-12-23 15:35:12','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Beleg Nr.
-- Column: M_ShipperTransportation.DocumentNo
-- 2022-12-23T13:35:14.020Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710077,0,546732,550200,614590,'F',TO_TIMESTAMP('2022-12-23 15:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beleg Nr.',20,70,0,TO_TIMESTAMP('2022-12-23 15:35:13','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Belegdatum
-- Column: M_ShipperTransportation.DateDoc
-- 2022-12-23T13:35:14.777Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710078,0,546732,550200,614591,'F',TO_TIMESTAMP('2022-12-23 15:35:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Belegdatum',30,80,0,TO_TIMESTAMP('2022-12-23 15:35:14','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Abholung am
-- Column: M_ShipperTransportation.DateToBeFetched
-- 2022-12-23T13:35:15.501Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710092,0,546732,550200,614592,'F',TO_TIMESTAMP('2022-12-23 15:35:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','Y','N','Abholung am',40,50,30,TO_TIMESTAMP('2022-12-23 15:35:14','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Pickup Time From
-- Column: M_ShipperTransportation.PickupTimeFrom
-- 2022-12-23T13:35:16.209Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710094,0,546732,550200,614593,'F',TO_TIMESTAMP('2022-12-23 15:35:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Pickup Time From',50,0,0,TO_TIMESTAMP('2022-12-23 15:35:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Pickup Time To
-- Column: M_ShipperTransportation.PickupTimeTo
-- 2022-12-23T13:35:16.977Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710095,0,546732,550200,614594,'F',TO_TIMESTAMP('2022-12-23 15:35:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Pickup Time To',60,0,0,TO_TIMESTAMP('2022-12-23 15:35:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20
-- UI Element Group: org
-- 2022-12-23T13:35:17.527Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546540,550201,TO_TIMESTAMP('2022-12-23 15:35:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-12-23 15:35:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> org.Sektion
-- Column: M_ShipperTransportation.AD_Org_ID
-- 2022-12-23T13:35:18.362Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710076,0,546732,550201,614595,'F',TO_TIMESTAMP('2022-12-23 15:35:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Sektion',10,120,0,TO_TIMESTAMP('2022-12-23 15:35:17','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> org.Mandant
-- Column: M_ShipperTransportation.AD_Client_ID
-- 2022-12-23T13:35:19.072Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710075,0,546732,550201,614596,'F',TO_TIMESTAMP('2022-12-23 15:35:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-12-23 15:35:18','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- UI Column: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20
-- UI Element Group: rest
-- 2022-12-23T13:35:19.671Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546540,550202,TO_TIMESTAMP('2022-12-23 15:35:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','rest',30,TO_TIMESTAMP('2022-12-23 15:35:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> rest.Belegstatus
-- Column: M_ShipperTransportation.DocStatus
-- 2022-12-23T13:35:20.490Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710086,0,546732,550202,614597,'F',TO_TIMESTAMP('2022-12-23 15:35:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Belegstatus',10,130,0,TO_TIMESTAMP('2022-12-23 15:35:19','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> rest.Document Action
-- Column: M_ShipperTransportation.DocAction
-- 2022-12-23T13:35:21.209Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710087,0,546732,550202,614598,'F',TO_TIMESTAMP('2022-12-23 15:35:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Document Action',20,0,0,TO_TIMESTAMP('2022-12-23 15:35:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D)
-- UI Section: advanced edit
-- 2022-12-23T13:35:21.801Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546732,545362,TO_TIMESTAMP('2022-12-23 15:35:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-12-23 15:35:21','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2022-12-23T13:35:21.842Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545362 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-12-23T13:35:21.925Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545362
;

-- 2022-12-23T13:35:21.968Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545362, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540286
;

-- UI Section: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> advanced edit
-- UI Column: 10
-- 2022-12-23T13:35:22.475Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546541,545362,TO_TIMESTAMP('2022-12-23 15:35:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-23 15:35:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> advanced edit -> 10
-- UI Element Group: advanced edit
-- 2022-12-23T13:35:23.016Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546541,550203,TO_TIMESTAMP('2022-12-23 15:35:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2022-12-23 15:35:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> advanced edit -> 10 -> advanced edit.Gewicht Brutto
-- Column: M_ShipperTransportation.PackageWeight
-- 2022-12-23T13:35:23.849Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710083,0,546732,550203,614599,'F',TO_TIMESTAMP('2022-12-23 15:35:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','Y','N','N','Gewicht Brutto',100,100,0,TO_TIMESTAMP('2022-12-23 15:35:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> advanced edit -> 10 -> advanced edit.Gewicht Netto
-- Column: M_ShipperTransportation.PackageNetTotal
-- 2022-12-23T13:35:24.613Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710084,0,546732,550203,614600,'F',TO_TIMESTAMP('2022-12-23 15:35:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','Y','N','N','Gewicht Netto',110,110,0,TO_TIMESTAMP('2022-12-23 15:35:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> advanced edit -> 10 -> advanced edit.Ansprechpartner
-- Column: M_ShipperTransportation.SalesRep_ID
-- 2022-12-23T13:35:25.328Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710091,0,546732,550203,614601,'F',TO_TIMESTAMP('2022-12-23 15:35:24','YYYY-MM-DD HH24:MI:SS'),100,'Sales Representative or Company Agent','The Sales Representative indicates the Sales Rep for this Region.  Any Sales Rep must be a valid internal user.','Y','Y','N','Y','N','N','N','Ansprechpartner',150,0,0,TO_TIMESTAMP('2022-12-23 15:35:24','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> advanced edit -> 10 -> advanced edit.genehmigt
-- Column: M_ShipperTransportation.IsApproved
-- 2022-12-23T13:35:26.087Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710085,0,546732,550203,614602,'F',TO_TIMESTAMP('2022-12-23 15:35:25','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document requires approval','The Approved checkbox indicates if this document requires approval before it can be processed.','Y','Y','N','Y','N','N','N','genehmigt',160,0,0,TO_TIMESTAMP('2022-12-23 15:35:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Delivery Instruction(541657,D) -> Versandpackung(546733,D)
-- UI Section: main
-- 2022-12-23T13:35:26.726Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546733,545363,TO_TIMESTAMP('2022-12-23 15:35:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-23 15:35:26','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-12-23T13:35:26.767Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545363 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-12-23T13:35:26.852Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545363
;

-- 2022-12-23T13:35:26.896Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545363, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540285
;

-- UI Section: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main
-- UI Column: 10
-- 2022-12-23T13:35:27.438Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546542,545363,TO_TIMESTAMP('2022-12-23 15:35:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-23 15:35:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10
-- UI Element Group: default
-- 2022-12-23T13:35:27.981Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546542,550204,TO_TIMESTAMP('2022-12-23 15:35:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-12-23 15:35:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_ShippingPackage.M_InOut_ID
-- 2022-12-23T13:35:28.855Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710101,0,546733,550204,614603,'F',TO_TIMESTAMP('2022-12-23 15:35:28','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','N','N','Y','N','N','Lieferung/Wareneingang',10,40,0,TO_TIMESTAMP('2022-12-23 15:35:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Packstück
-- Column: M_ShippingPackage.M_Package_ID
-- 2022-12-23T13:35:29.680Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710102,0,546733,550204,614604,'F',TO_TIMESTAMP('2022-12-23 15:35:29','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Package','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','N','Y','N','N','Packstück',20,50,0,TO_TIMESTAMP('2022-12-23 15:35:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Gewicht Packstücke
-- Column: M_ShippingPackage.PackageWeight
-- 2022-12-23T13:35:30.472Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710105,0,546733,550204,614605,'F',TO_TIMESTAMP('2022-12-23 15:35:29','YYYY-MM-DD HH24:MI:SS'),100,'Weight of a package','The Weight indicates the weight of the package','Y','N','N','N','Y','N','N','Gewicht Packstücke',30,70,0,TO_TIMESTAMP('2022-12-23 15:35:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Nettogewicht Packstücke
-- Column: M_ShippingPackage.PackageNetTotal
-- 2022-12-23T13:35:31.226Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710106,0,546733,550204,614606,'F',TO_TIMESTAMP('2022-12-23 15:35:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','N','Nettogewicht Packstücke',40,80,0,TO_TIMESTAMP('2022-12-23 15:35:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Notiz
-- Column: M_ShippingPackage.Note
-- 2022-12-23T13:35:32.033Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710109,0,546733,550204,614607,'F',TO_TIMESTAMP('2022-12-23 15:35:31','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information für ein Dokument','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','N','Y','N','N','Notiz',50,100,0,TO_TIMESTAMP('2022-12-23 15:35:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Transport Auftrag
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2022-12-23T13:35:32.821Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710100,0,546733,550204,614608,'F',TO_TIMESTAMP('2022-12-23 15:35:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','N','Transport Auftrag',60,20,0,TO_TIMESTAMP('2022-12-23 15:35:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Auftrag
-- Column: M_ShippingPackage.C_Order_ID
-- 2022-12-23T13:35:33.605Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710108,0,546733,550204,614609,'F',TO_TIMESTAMP('2022-12-23 15:35:32','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','Y','N','N','Auftrag',70,90,0,TO_TIMESTAMP('2022-12-23 15:35:32','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Mandant
-- Column: M_ShippingPackage.AD_Client_ID
-- 2022-12-23T13:35:34.360Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710098,0,546733,550204,614610,'F',TO_TIMESTAMP('2022-12-23 15:35:33','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','N','Mandant',80,0,0,TO_TIMESTAMP('2022-12-23 15:35:33','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Kunde
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2022-12-23T13:35:35.146Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710103,0,546733,550204,614611,'F',TO_TIMESTAMP('2022-12-23 15:35:34','YYYY-MM-DD HH24:MI:SS'),100,'Identifies a Business Partner','A Business Partner is anyone with whom you transact.  This can include Vendor, Customer, Employee or Salesperson','Y','N','N','N','Y','N','N','Kunde',90,60,0,TO_TIMESTAMP('2022-12-23 15:35:34','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-12-23T13:35:35.997Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,710104,0,541678,614611,TO_TIMESTAMP('2022-12-23 15:35:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2022-12-23 15:35:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Sektion
-- Column: M_ShippingPackage.AD_Org_ID
-- 2022-12-23T13:35:36.740Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710099,0,546733,550204,614612,'F',TO_TIMESTAMP('2022-12-23 15:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','Y','N','N','Sektion',100,10,0,TO_TIMESTAMP('2022-12-23 15:35:36','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Abholung
-- Column: M_ShippingPackage.IsToBeFetched
-- 2022-12-23T13:35:37.547Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710110,0,546733,550204,614613,'F',TO_TIMESTAMP('2022-12-23 15:35:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','N',0,'Abholung',110,30,0,TO_TIMESTAMP('2022-12-23 15:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: PrintAllShipmentDocuments(de.metas.handlingunits.shipping.process.PrintAllShipmentDocuments)
-- Table: M_ShipperTransportation
-- Window: Delivery Instruction(541657,D)
-- EntityType: D
-- 2022-12-23T13:35:38.223Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541228,540030,541325,541657,TO_TIMESTAMP('2022-12-23 15:35:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-12-23 15:35:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Name: Delivery Instruction
-- Action Type: W
-- Window: Delivery Instruction(541657,D)
-- 2022-12-23T13:38:08.656Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581903,542032,0,541657,TO_TIMESTAMP('2022-12-23 15:38:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Delivery Instruction','Y','N','N','N','N','Delivery Instruction',TO_TIMESTAMP('2022-12-23 15:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:38:08.699Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542032 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-12-23T13:38:08.744Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542032, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542032)
;

-- 2022-12-23T13:38:08.794Z
/* DDL */  select update_menu_translation_from_ad_element(581903) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2022-12-23T13:38:11.713Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2022-12-23T13:38:11.757Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2022-12-23T13:38:11.799Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2022-12-23T13:38:11.840Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2022-12-23T13:38:11.882Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2022-12-23T13:38:11.923Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2022-12-23T13:38:11.964Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2022-12-23T13:38:12.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2022-12-23T13:38:12.091Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2022-12-23T13:38:12.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2022-12-23T13:38:12.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2022-12-23T13:38:12.215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing`
-- 2022-12-23T13:38:12.258Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning`
-- 2022-12-23T13:38:12.299Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2022-12-23T13:38:12.341Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2022-12-23T13:38:12.382Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2022-12-23T13:38:12.424Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2022-12-23T13:38:12.466Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2022-12-23T13:38:12.508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2022-12-23T13:38:12.549Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2022-12-23T13:38:12.591Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2022-12-23T13:38:12.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2022-12-23T13:38:12.677Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2022-12-23T13:38:12.718Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-12-23T13:38:12.763Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-12-23T13:38:12.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-12-23T13:38:12.845Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2022-12-23T13:38:12.886Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2022-12-23T13:38:12.927Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2022-12-23T13:38:12.968Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation`
-- 2022-12-23T13:38:13.009Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Forwarder`
-- 2022-12-23T13:38:13.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542023 AND AD_Tree_ID=10
;

-- Node name: `Delivery Instruction`
-- 2022-12-23T13:38:13.140Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542032 AND AD_Tree_ID=10
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2022-12-23T13:38:22.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2022-12-23T13:38:22.748Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2022-12-23T13:38:22.790Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2022-12-23T13:38:22.832Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2022-12-23T13:38:22.873Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2022-12-23T13:38:22.915Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2022-12-23T13:38:22.958Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2022-12-23T13:38:22.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2022-12-23T13:38:23.041Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2022-12-23T13:38:23.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2022-12-23T13:38:23.126Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2022-12-23T13:38:23.206Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing`
-- 2022-12-23T13:38:23.277Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning`
-- 2022-12-23T13:38:23.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Delivery Instruction`
-- 2022-12-23T13:38:23.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542032 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2022-12-23T13:38:23.404Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2022-12-23T13:38:23.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2022-12-23T13:38:23.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2022-12-23T13:38:23.528Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2022-12-23T13:38:23.570Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2022-12-23T13:38:23.612Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2022-12-23T13:38:23.654Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2022-12-23T13:38:23.696Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2022-12-23T13:38:23.737Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2022-12-23T13:38:23.779Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-12-23T13:38:23.821Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-12-23T13:38:23.863Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-12-23T13:38:23.905Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2022-12-23T13:38:23.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2022-12-23T13:38:23.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2022-12-23T13:38:24.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation`
-- 2022-12-23T13:38:24.075Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Forwarder`
-- 2022-12-23T13:38:24.117Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542023 AND AD_Tree_ID=10
;

-- Tab: Delivery Instruction(541657,D) -> Transportation Delivery
-- Table: M_ShipperTransportation
-- 2022-12-23T13:39:04.725Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2022-12-23 15:39:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546732
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Assign anonymously picked HUs
-- Column: M_ShipperTransportation.AssignAnonymouslyPickedHUs
-- 2022-12-23T13:39:25.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578273,710111,0,546732,TO_TIMESTAMP('2022-12-23 15:39:24','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Assign anonymously picked HUs',TO_TIMESTAMP('2022-12-23 15:39:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:25.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:25.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580175) 
;

-- 2022-12-23T13:39:25.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710111
;

-- 2022-12-23T13:39:25.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710111)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Loading Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Loading_ID
-- 2022-12-23T13:39:26.180Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585435,710112,0,546732,TO_TIMESTAMP('2022-12-23 15:39:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Loading Address',TO_TIMESTAMP('2022-12-23 15:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:26.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:26.265Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581899) 
;

-- 2022-12-23T13:39:26.310Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710112
;

-- 2022-12-23T13:39:26.351Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710112)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Loading Date
-- Column: M_ShipperTransportation.LoadingDate
-- 2022-12-23T13:39:27.024Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585436,710113,0,546732,TO_TIMESTAMP('2022-12-23 15:39:26','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Loading Date',TO_TIMESTAMP('2022-12-23 15:39:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:27.066Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:27.109Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581900) 
;

-- 2022-12-23T13:39:27.153Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710113
;

-- 2022-12-23T13:39:27.195Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710113)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Loading Time
-- Column: M_ShipperTransportation.LoadingTime
-- 2022-12-23T13:39:27.865Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585437,710114,0,546732,TO_TIMESTAMP('2022-12-23 15:39:27','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Loading Time',TO_TIMESTAMP('2022-12-23 15:39:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:27.950Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:27.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581895) 
;

-- 2022-12-23T13:39:28.038Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710114
;

-- 2022-12-23T13:39:28.079Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710114)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Delivery Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Delivery_ID
-- 2022-12-23T13:39:28.745Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585438,710115,0,546732,TO_TIMESTAMP('2022-12-23 15:39:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Delivery Address',TO_TIMESTAMP('2022-12-23 15:39:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:28.787Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:28.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581901) 
;

-- 2022-12-23T13:39:28.875Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710115
;

-- 2022-12-23T13:39:28.917Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710115)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Shipmentdate
-- Column: M_ShipperTransportation.DeliveryDate
-- 2022-12-23T13:39:29.586Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585439,710116,0,546732,TO_TIMESTAMP('2022-12-23 15:39:29','YYYY-MM-DD HH24:MI:SS'),100,'',7,'D','Y','Y','N','N','N','N','N','Shipmentdate',TO_TIMESTAMP('2022-12-23 15:39:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:29.626Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:29.668Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541376) 
;

-- 2022-12-23T13:39:29.719Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710116
;

-- 2022-12-23T13:39:29.761Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710116)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Delivery Time
-- Column: M_ShipperTransportation.DeliveryTime
-- 2022-12-23T13:39:30.472Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585440,710117,0,546732,TO_TIMESTAMP('2022-12-23 15:39:29','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Delivery Time',TO_TIMESTAMP('2022-12-23 15:39:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:30.513Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710117 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:30.555Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581896) 
;

-- 2022-12-23T13:39:30.600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710117
;

-- 2022-12-23T13:39:30.642Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710117)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Incoterms
-- Column: M_ShipperTransportation.C_Incoterms_ID
-- 2022-12-23T13:39:31.330Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585441,710118,0,546732,TO_TIMESTAMP('2022-12-23 15:39:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Incoterms',TO_TIMESTAMP('2022-12-23 15:39:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:31.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710118 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:31.413Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927) 
;

-- 2022-12-23T13:39:31.460Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710118
;

-- 2022-12-23T13:39:31.501Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710118)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Incoterm Location
-- Column: M_ShipperTransportation.IncotermLocation
-- 2022-12-23T13:39:32.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585442,710119,0,546732,TO_TIMESTAMP('2022-12-23 15:39:31','YYYY-MM-DD HH24:MI:SS'),100,'Location to be specified for commercial clause',500,'D','Y','Y','N','N','N','N','N','Incoterm Location',TO_TIMESTAMP('2022-12-23 15:39:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:32.269Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:32.311Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608) 
;

-- 2022-12-23T13:39:32.358Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710119
;

-- 2022-12-23T13:39:32.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710119)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Means of Transportation
-- Column: M_ShipperTransportation.M_MeansOfTransportation_ID
-- 2022-12-23T13:39:33.078Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585443,710120,0,546732,TO_TIMESTAMP('2022-12-23 15:39:32','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Means of Transportation',TO_TIMESTAMP('2022-12-23 15:39:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:33.119Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:33.162Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581776) 
;

-- 2022-12-23T13:39:33.207Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710120
;

-- 2022-12-23T13:39:33.249Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710120)
;

-- Field: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> Forwarder
-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2022-12-23T13:39:33.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585444,710121,0,546732,TO_TIMESTAMP('2022-12-23 15:39:33','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Forwarder',TO_TIMESTAMP('2022-12-23 15:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T13:39:33.991Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T13:39:34.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581762) 
;

-- 2022-12-23T13:39:34.080Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710121
;

-- 2022-12-23T13:39:34.121Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710121)
;




-- UI Column: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10
-- UI Element Group: address
-- 2022-12-23T14:25:55.388Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546539,550205,TO_TIMESTAMP('2022-12-23 16:25:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','address',15,TO_TIMESTAMP('2022-12-23 16:25:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10 -> address.Loading Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Loading_ID
-- 2022-12-23T14:26:26.664Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710112,0,546732,550205,614614,'F',TO_TIMESTAMP('2022-12-23 16:26:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Loading Address',10,0,0,TO_TIMESTAMP('2022-12-23 16:26:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 10 -> address.Delivery Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Delivery_ID
-- 2022-12-23T14:26:43.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710115,0,546732,550205,614615,'F',TO_TIMESTAMP('2022-12-23 16:26:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Delivery Address',20,0,0,TO_TIMESTAMP('2022-12-23 16:26:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Abholung am
-- Column: M_ShipperTransportation.DateToBeFetched
-- 2022-12-23T14:30:08.126Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-12-23 16:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614592
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Pickup Time From
-- Column: M_ShipperTransportation.PickupTimeFrom
-- 2022-12-23T14:30:10.430Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-12-23 16:30:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614593
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> doctype.Pickup Time To
-- Column: M_ShipperTransportation.PickupTimeTo
-- 2022-12-23T14:30:18.237Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-12-23 16:30:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614594
;

-- UI Column: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20
-- UI Element Group: delivery dates
-- 2022-12-23T14:30:43.213Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546540,550206,TO_TIMESTAMP('2022-12-23 16:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','delivery dates',40,TO_TIMESTAMP('2022-12-23 16:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> delivery dates.Loading Date
-- Column: M_ShipperTransportation.LoadingDate
-- 2022-12-23T14:31:14.411Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710113,0,546732,550206,614616,'F',TO_TIMESTAMP('2022-12-23 16:31:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Loading Date',10,0,0,TO_TIMESTAMP('2022-12-23 16:31:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> delivery dates.Loading Time
-- Column: M_ShipperTransportation.LoadingTime
-- 2022-12-23T14:31:33.509Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710114,0,546732,550206,614617,'F',TO_TIMESTAMP('2022-12-23 16:31:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Loading Time',20,0,0,TO_TIMESTAMP('2022-12-23 16:31:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> delivery dates.Shipmentdate
-- Column: M_ShipperTransportation.DeliveryDate
-- 2022-12-23T14:32:16.301Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710116,0,546732,550206,614618,'F',TO_TIMESTAMP('2022-12-23 16:32:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Shipmentdate',30,0,0,TO_TIMESTAMP('2022-12-23 16:32:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Transportation Delivery(546732,D) -> main -> 20 -> delivery dates.Delivery Time
-- Column: M_ShipperTransportation.DeliveryTime
-- 2022-12-23T14:32:30.059Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710117,0,546732,550206,614619,'F',TO_TIMESTAMP('2022-12-23 16:32:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Delivery Time',40,0,0,TO_TIMESTAMP('2022-12-23 16:32:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction
-- Table: M_ShipperTransportation
-- 2022-12-23T14:33:19.961Z
UPDATE AD_Tab SET AD_Element_ID=581903, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Delivery Instruction',Updated=TO_TIMESTAMP('2022-12-23 16:33:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546732
;

-- 2022-12-23T14:33:20.004Z
/* DDL */  select update_tab_translation_from_ad_element(581903) 
;

-- 2022-12-23T14:33:20.052Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546732)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Delivery Date
-- Column: M_ShipperTransportation.DeliveryDate
-- 2022-12-23T14:34:04.900Z
UPDATE AD_Field SET AD_Name_ID=581902, Description=NULL, Help=NULL, Name='Delivery Date',Updated=TO_TIMESTAMP('2022-12-23 16:34:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710116
;

-- 2022-12-23T14:34:04.942Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581902) 
;

-- 2022-12-23T14:34:04.990Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710116
;

-- 2022-12-23T14:34:05.032Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710116)
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10
-- UI Element Group: incoterm
-- 2022-12-23T14:35:08.450Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546539,550207,TO_TIMESTAMP('2022-12-23 16:35:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','incoterm',13,TO_TIMESTAMP('2022-12-23 16:35:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> incoterm.Incoterms
-- Column: M_ShipperTransportation.C_Incoterms_ID
-- 2022-12-23T14:35:26.295Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710118,0,546732,550207,614620,'F',TO_TIMESTAMP('2022-12-23 16:35:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Incoterms',10,0,0,TO_TIMESTAMP('2022-12-23 16:35:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> incoterm.Incoterm Location
-- Column: M_ShipperTransportation.IncotermLocation
-- 2022-12-23T14:35:40.720Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710119,0,546732,550207,614621,'F',TO_TIMESTAMP('2022-12-23 16:35:40','YYYY-MM-DD HH24:MI:SS'),100,'Location to be specified for commercial clause','Y','N','N','Y','N','N','N',0,'Incoterm Location',20,0,0,TO_TIMESTAMP('2022-12-23 16:35:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10
-- UI Element Group: delivery details
-- 2022-12-23T14:36:29.485Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546539,550208,TO_TIMESTAMP('2022-12-23 16:36:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','delivery details',14,TO_TIMESTAMP('2022-12-23 16:36:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> delivery details.Means of Transportation
-- Column: M_ShipperTransportation.M_MeansOfTransportation_ID
-- 2022-12-23T14:36:46.624Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710120,0,546732,550208,614622,'F',TO_TIMESTAMP('2022-12-23 16:36:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Means of Transportation',10,0,0,TO_TIMESTAMP('2022-12-23 16:36:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> delivery details.Forwarder
-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2022-12-23T14:37:02.085Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710121,0,546732,550208,614623,'F',TO_TIMESTAMP('2022-12-23 16:37:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Forwarder',20,0,0,TO_TIMESTAMP('2022-12-23 16:37:01','YYYY-MM-DD HH24:MI:SS'),100)
;







-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20
-- UI Element Group: org
-- 2022-12-23T14:39:30.059Z
UPDATE AD_UI_ElementGroup SET SeqNo=60,Updated=TO_TIMESTAMP('2022-12-23 16:39:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550201
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20
-- UI Element Group: delivery details
-- 2022-12-23T14:39:59.622Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=546540, SeqNo=70,Updated=TO_TIMESTAMP('2022-12-23 16:39:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550208
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20
-- UI Element Group: delivery details
-- 2022-12-23T14:40:22.267Z
UPDATE AD_UI_ElementGroup SET SeqNo=50,Updated=TO_TIMESTAMP('2022-12-23 16:40:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550208
;






-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> description.Tour
-- Column: M_ShipperTransportation.M_Tour_ID
-- 2022-12-23T14:42:56.709Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-12-23 16:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614588
;




