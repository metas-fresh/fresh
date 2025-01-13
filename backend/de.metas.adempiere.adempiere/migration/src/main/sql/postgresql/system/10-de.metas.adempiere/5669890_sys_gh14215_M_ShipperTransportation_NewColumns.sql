-- 2022-12-23T11:09:03.352Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581899,0,TO_TIMESTAMP('2022-12-23 13:09:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Loading Address','Loading Address',TO_TIMESTAMP('2022-12-23 13:09:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T11:09:03.395Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581899 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-23T11:09:25.468Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581900,0,TO_TIMESTAMP('2022-12-23 13:09:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Loading Date','Loading Date',TO_TIMESTAMP('2022-12-23 13:09:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T11:09:25.510Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581900 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-23T11:09:52.520Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581901,0,TO_TIMESTAMP('2022-12-23 13:09:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Address','Delivery Address',TO_TIMESTAMP('2022-12-23 13:09:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T11:09:52.562Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581901 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-23T11:10:06.606Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581902,0,TO_TIMESTAMP('2022-12-23 13:10:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Date','Delivery Date',TO_TIMESTAMP('2022-12-23 13:10:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T11:10:06.648Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581902 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-23T11:12:03.310Z
UPDATE AD_Element SET ColumnName='C_BPartner_Location_Loading_ID',Updated=TO_TIMESTAMP('2022-12-23 13:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581899
;

-- 2022-12-23T11:12:03.353Z
UPDATE AD_Column SET ColumnName='C_BPartner_Location_Loading_ID' WHERE AD_Element_ID=581899
;

-- 2022-12-23T11:12:03.422Z
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Location_Loading_ID' WHERE AD_Element_ID=581899
;

-- 2022-12-23T11:12:03.590Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581899,'en_US') 
;

-- 2022-12-23T11:12:18.855Z
UPDATE AD_Element SET ColumnName='C_BPartner_Location_Delivery_ID',Updated=TO_TIMESTAMP('2022-12-23 13:12:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581901
;

-- 2022-12-23T11:12:18.896Z
UPDATE AD_Column SET ColumnName='C_BPartner_Location_Delivery_ID' WHERE AD_Element_ID=581901
;

-- 2022-12-23T11:12:18.937Z
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Location_Delivery_ID' WHERE AD_Element_ID=581901
;

-- 2022-12-23T11:12:19.102Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581901,'en_US') 
;

-- Column: M_ShipperTransportation.C_BPartner_Location_Loading_ID
-- 2022-12-23T11:14:07.888Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585435,581899,0,30,159,540030,'C_BPartner_Location_Loading_ID',TO_TIMESTAMP('2022-12-23 13:14:07','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Loading Address',0,0,TO_TIMESTAMP('2022-12-23 13:14:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:14:07.930Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585435 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:14:08.014Z
/* DDL */  select update_Column_Translation_From_AD_Element(581899) 
;

-- 2022-12-23T11:14:14.326Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN C_BPartner_Location_Loading_ID NUMERIC(10)')
;

-- 2022-12-23T11:14:14.444Z
ALTER TABLE M_ShipperTransportation ADD CONSTRAINT CBPartnerLocationLoading_MShipperTransportation FOREIGN KEY (C_BPartner_Location_Loading_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2022-12-23T11:14:50.117Z
UPDATE AD_Element SET ColumnName='LoadingDate',Updated=TO_TIMESTAMP('2022-12-23 13:14:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581900
;

-- 2022-12-23T11:14:50.159Z
UPDATE AD_Column SET ColumnName='LoadingDate' WHERE AD_Element_ID=581900
;

-- 2022-12-23T11:14:50.200Z
UPDATE AD_Process_Para SET ColumnName='LoadingDate' WHERE AD_Element_ID=581900
;

-- 2022-12-23T11:14:50.361Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581900,'en_US') 
;

-- Column: M_ShipperTransportation.LoadingDate
-- 2022-12-23T11:15:21.376Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585436,581900,0,15,540030,'LoadingDate',TO_TIMESTAMP('2022-12-23 13:15:20','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Loading Date',0,0,TO_TIMESTAMP('2022-12-23 13:15:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:15:21.418Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585436 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:15:21.502Z
/* DDL */  select update_Column_Translation_From_AD_Element(581900) 
;

-- 2022-12-23T11:15:28.268Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN LoadingDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_ShipperTransportation.LoadingTime
-- 2022-12-23T11:15:51.617Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585437,581895,0,10,540030,'LoadingTime',TO_TIMESTAMP('2022-12-23 13:15:51','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Loading Time',0,0,TO_TIMESTAMP('2022-12-23 13:15:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:15:51.659Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585437 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:15:51.742Z
/* DDL */  select update_Column_Translation_From_AD_Element(581895) 
;

-- 2022-12-23T11:15:58.144Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN LoadingTime VARCHAR(250)')
;

-- Column: M_ShipperTransportation.C_BPartner_Location_Delivery_ID
-- 2022-12-23T11:17:26.657Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585438,581901,0,30,159,540030,'C_BPartner_Location_Delivery_ID',TO_TIMESTAMP('2022-12-23 13:17:26','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Delivery Address',0,0,TO_TIMESTAMP('2022-12-23 13:17:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:17:26.702Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585438 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:17:26.786Z
/* DDL */  select update_Column_Translation_From_AD_Element(581901) 
;

-- 2022-12-23T11:17:33.487Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN C_BPartner_Location_Delivery_ID NUMERIC(10)')
;

-- 2022-12-23T11:17:33.565Z
ALTER TABLE M_ShipperTransportation ADD CONSTRAINT CBPartnerLocationDelivery_MShipperTransportation FOREIGN KEY (C_BPartner_Location_Delivery_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_ShipperTransportation.DeliveryDate
-- 2022-12-23T11:17:57.352Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585439,541376,0,15,540030,'DeliveryDate',TO_TIMESTAMP('2022-12-23 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'N','','METAS_SHIPPING',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Shipmentdate',0,0,TO_TIMESTAMP('2022-12-23 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:17:57.394Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585439 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:17:57.504Z
/* DDL */  select update_Column_Translation_From_AD_Element(541376) 
;

-- 2022-12-23T11:18:03.955Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN DeliveryDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_ShipperTransportation.DeliveryTime
-- 2022-12-23T11:18:21.749Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585440,581896,0,10,540030,'DeliveryTime',TO_TIMESTAMP('2022-12-23 13:18:21','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Delivery Time',0,0,TO_TIMESTAMP('2022-12-23 13:18:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:18:21.791Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585440 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:18:21.876Z
/* DDL */  select update_Column_Translation_From_AD_Element(581896) 
;

-- 2022-12-23T11:18:28.391Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN DeliveryTime VARCHAR(250)')
;

-- Column: M_ShipperTransportation.C_Incoterms_ID
-- 2022-12-23T11:18:57.032Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585441,579927,0,30,540030,'C_Incoterms_ID',TO_TIMESTAMP('2022-12-23 13:18:56','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterms',0,0,TO_TIMESTAMP('2022-12-23 13:18:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:18:57.072Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585441 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:18:57.156Z
/* DDL */  select update_Column_Translation_From_AD_Element(579927) 
;

-- 2022-12-23T11:19:03.949Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN C_Incoterms_ID NUMERIC(10)')
;

-- 2022-12-23T11:19:04.038Z
ALTER TABLE M_ShipperTransportation ADD CONSTRAINT CIncoterms_MShipperTransportation FOREIGN KEY (C_Incoterms_ID) REFERENCES public.C_Incoterms DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_ShipperTransportation.IncotermLocation
-- 2022-12-23T11:19:25.221Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585442,501608,0,10,540030,'IncotermLocation',TO_TIMESTAMP('2022-12-23 13:19:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Location to be specified for commercial clause','METAS_SHIPPING',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterm Location',0,0,TO_TIMESTAMP('2022-12-23 13:19:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:19:25.264Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585442 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:19:25.349Z
/* DDL */  select update_Column_Translation_From_AD_Element(501608) 
;

-- 2022-12-23T11:19:31.885Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN IncotermLocation VARCHAR(500)')
;

-- Column: M_ShipperTransportation.M_MeansOfTransportation_ID
-- 2022-12-23T11:19:52.194Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585443,581776,0,30,540030,'M_MeansOfTransportation_ID',TO_TIMESTAMP('2022-12-23 13:19:51','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Means of Transportation',0,0,TO_TIMESTAMP('2022-12-23 13:19:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:19:52.236Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585443 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:19:52.369Z
/* DDL */  select update_Column_Translation_From_AD_Element(581776) 
;

-- 2022-12-23T11:19:58.917Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN M_MeansOfTransportation_ID NUMERIC(10)')
;

-- 2022-12-23T11:19:58.995Z
ALTER TABLE M_ShipperTransportation ADD CONSTRAINT MMeansOfTransportation_MShipperTransportation FOREIGN KEY (M_MeansOfTransportation_ID) REFERENCES public.M_MeansOfTransportation DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2022-12-23T11:20:19.650Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585444,581762,0,30,540030,'M_Forwarder_ID',TO_TIMESTAMP('2022-12-23 13:20:18','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Forwarder',0,0,TO_TIMESTAMP('2022-12-23 13:20:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:20:19.692Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585444 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:20:19.776Z
/* DDL */  select update_Column_Translation_From_AD_Element(581762) 
;

-- 2022-12-23T11:20:26.475Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN M_Forwarder_ID NUMERIC(10)')
;

-- 2022-12-23T11:20:26.559Z
ALTER TABLE M_ShipperTransportation ADD CONSTRAINT MForwarder_MShipperTransportation FOREIGN KEY (M_Forwarder_ID) REFERENCES public.M_Forwarder DEFERRABLE INITIALLY DEFERRED
;


-- Make sure the M_Shipper_ID is marked as Mandatory in all the existing, displayed fields. After introducing M_Forwarder_ID, they will not be both mandatory at the same time!

UPDATE AD_Field SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-09 19:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550685 and isdisplayed = 'Y'
;