-- Column: UI_Trace.URL
-- Column: UI_Trace.URL
-- 2025-01-15T07:52:22.852Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589589,983,0,40,542461,'XX','URL',TO_TIMESTAMP('2025-01-15 07:52:22.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Z.B. http://www.metasfresh.com','D',0,2000,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'URL',0,0,TO_TIMESTAMP('2025-01-15 07:52:22.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-15T07:52:22.855Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589589 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-15T07:52:23.003Z
/* DDL */  select update_Column_Translation_From_AD_Element(983) 
;

-- 2025-01-15T07:52:26.787Z
/* DDL */ SELECT public.db_alter_table('UI_Trace','ALTER TABLE public.UI_Trace ADD COLUMN URL VARCHAR(2000)')
;

-- 2025-01-15T07:53:49.260Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583428,0,'UI_ApplicationId',TO_TIMESTAMP('2025-01-15 07:53:49.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Application','Application',TO_TIMESTAMP('2025-01-15 07:53:49.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T07:53:49.265Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583428 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: UI_Trace.UI_ApplicationId
-- Column: UI_Trace.UI_ApplicationId
-- 2025-01-15T07:54:00.866Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589590,583428,0,10,542461,'XX','UI_ApplicationId',TO_TIMESTAMP('2025-01-15 07:54:00.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Application',0,0,TO_TIMESTAMP('2025-01-15 07:54:00.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-15T07:54:00.867Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589590 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-15T07:54:00.871Z
/* DDL */  select update_Column_Translation_From_AD_Element(583428) 
;

-- 2025-01-15T07:54:01.472Z
/* DDL */ SELECT public.db_alter_table('UI_Trace','ALTER TABLE public.UI_Trace ADD COLUMN UI_ApplicationId VARCHAR(255)')
;

-- 2025-01-15T07:54:25.995Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583429,0,'Caption',TO_TIMESTAMP('2025-01-15 07:54:25.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Caption','Caption',TO_TIMESTAMP('2025-01-15 07:54:25.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T07:54:25.999Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583429 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: UI_Trace.Caption
-- Column: UI_Trace.Caption
-- 2025-01-15T07:54:35.558Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589591,583429,0,10,542461,'XX','Caption',TO_TIMESTAMP('2025-01-15 07:54:35.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Caption',0,0,TO_TIMESTAMP('2025-01-15 07:54:35.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-15T07:54:35.560Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589591 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-15T07:54:35.564Z
/* DDL */  select update_Column_Translation_From_AD_Element(583429) 
;

-- 2025-01-15T07:54:36.161Z
/* DDL */ SELECT public.db_alter_table('UI_Trace','ALTER TABLE public.UI_Trace ADD COLUMN Caption VARCHAR(255)')
;

-- Column: UI_Trace.UserName
-- Column: UI_Trace.UserName
-- 2025-01-15T07:55:35.511Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589592,1903,0,10,542461,'XX','UserName',TO_TIMESTAMP('2025-01-15 07:55:35.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,255,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Nutzer-ID/Login',0,0,TO_TIMESTAMP('2025-01-15 07:55:35.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-15T07:55:35.513Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589592 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-15T07:55:35.516Z
/* DDL */  select update_Column_Translation_From_AD_Element(1903) 
;

-- 2025-01-15T07:55:36.110Z
/* DDL */ SELECT public.db_alter_table('UI_Trace','ALTER TABLE public.UI_Trace ADD COLUMN UserName VARCHAR(255)')
;

-- 2025-01-15T07:58:18.466Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583430,0,'UI_DeviceId',TO_TIMESTAMP('2025-01-15 07:58:18.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Device ID','Device ID',TO_TIMESTAMP('2025-01-15 07:58:18.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T07:58:18.468Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583430 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-01-15T07:58:30.388Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583431,0,'UI_TabId',TO_TIMESTAMP('2025-01-15 07:58:30.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Tab ID','Tab ID',TO_TIMESTAMP('2025-01-15 07:58:30.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T07:58:30.389Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583431 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: UI_Trace.UI_DeviceId
-- Column: UI_Trace.UI_DeviceId
-- 2025-01-15T07:58:52.955Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589593,583430,0,10,542461,'XX','UI_DeviceId',TO_TIMESTAMP('2025-01-15 07:58:52.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Device ID',0,0,TO_TIMESTAMP('2025-01-15 07:58:52.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-15T07:58:52.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589593 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-15T07:58:52.960Z
/* DDL */  select update_Column_Translation_From_AD_Element(583430) 
;

-- 2025-01-15T07:58:55.880Z
/* DDL */ SELECT public.db_alter_table('UI_Trace','ALTER TABLE public.UI_Trace ADD COLUMN UI_DeviceId VARCHAR(255)')
;

-- Column: UI_Trace.UI_DeviceId
-- Column: UI_Trace.UI_DeviceId
-- 2025-01-15T07:58:58.930Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-15 07:58:58.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589593
;

-- Column: UI_Trace.UI_ApplicationId
-- Column: UI_Trace.UI_ApplicationId
-- 2025-01-15T07:59:03.127Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-15 07:59:03.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589590
;

-- Column: UI_Trace.Caption
-- Column: UI_Trace.Caption
-- 2025-01-15T07:59:28.760Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-15 07:59:28.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589591
;

-- Column: UI_Trace.URL
-- Column: UI_Trace.URL
-- 2025-01-15T07:59:56.978Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-15 07:59:56.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589589
;

-- Column: UI_Trace.UI_TabId
-- Column: UI_Trace.UI_TabId
-- 2025-01-15T08:00:27.893Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589594,583431,0,10,542461,'XX','UI_TabId',TO_TIMESTAMP('2025-01-15 08:00:27.729000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Tab ID',0,0,TO_TIMESTAMP('2025-01-15 08:00:27.729000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-15T08:00:27.895Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589594 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-15T08:00:27.898Z
/* DDL */  select update_Column_Translation_From_AD_Element(583431) 
;

-- 2025-01-15T08:00:31.027Z
/* DDL */ SELECT public.db_alter_table('UI_Trace','ALTER TABLE public.UI_Trace ADD COLUMN UI_TabId VARCHAR(255)')
;

-- Column: UI_Trace.UserAgent
-- Column: UI_Trace.UserAgent
-- 2025-01-15T08:00:45.562Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589595,1704,0,10,542461,'XX','UserAgent',TO_TIMESTAMP('2025-01-15 08:00:45.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Browser Used','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'User Agent',0,0,TO_TIMESTAMP('2025-01-15 08:00:45.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-15T08:00:45.564Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589595 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-15T08:00:45.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(1704) 
;

-- 2025-01-15T08:00:48.933Z
/* DDL */ SELECT public.db_alter_table('UI_Trace','ALTER TABLE public.UI_Trace ADD COLUMN UserAgent VARCHAR(255)')
;

-- Field: UI Trace -> UI Trace -> URL
-- Column: UI_Trace.URL
-- Field: UI Trace(541841,D) -> UI Trace(547734,D) -> URL
-- Column: UI_Trace.URL
-- 2025-01-15T08:01:25.836Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589589,734656,0,547734,TO_TIMESTAMP('2025-01-15 08:01:25.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Z.B. http://www.metasfresh.com',2000,'D','','Y','N','N','N','N','N','N','N','URL',TO_TIMESTAMP('2025-01-15 08:01:25.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T08:01:25.839Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734656 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-15T08:01:25.842Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(983) 
;

-- 2025-01-15T08:01:25.895Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734656
;

-- 2025-01-15T08:01:25.901Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734656)
;

-- Field: UI Trace -> UI Trace -> Application
-- Column: UI_Trace.UI_ApplicationId
-- Field: UI Trace(541841,D) -> UI Trace(547734,D) -> Application
-- Column: UI_Trace.UI_ApplicationId
-- 2025-01-15T08:01:26.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589590,734657,0,547734,TO_TIMESTAMP('2025-01-15 08:01:25.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Application',TO_TIMESTAMP('2025-01-15 08:01:25.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T08:01:26.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734657 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-15T08:01:26.032Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583428) 
;

-- 2025-01-15T08:01:26.034Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734657
;

-- 2025-01-15T08:01:26.035Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734657)
;

-- Field: UI Trace -> UI Trace -> Caption
-- Column: UI_Trace.Caption
-- Field: UI Trace(541841,D) -> UI Trace(547734,D) -> Caption
-- Column: UI_Trace.Caption
-- 2025-01-15T08:01:26.146Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589591,734658,0,547734,TO_TIMESTAMP('2025-01-15 08:01:26.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Caption',TO_TIMESTAMP('2025-01-15 08:01:26.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T08:01:26.148Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734658 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-15T08:01:26.149Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583429) 
;

-- 2025-01-15T08:01:26.152Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734658
;

-- 2025-01-15T08:01:26.153Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734658)
;

-- Field: UI Trace -> UI Trace -> Nutzer-ID/Login
-- Column: UI_Trace.UserName
-- Field: UI Trace(541841,D) -> UI Trace(547734,D) -> Nutzer-ID/Login
-- Column: UI_Trace.UserName
-- 2025-01-15T08:01:26.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589592,734659,0,547734,TO_TIMESTAMP('2025-01-15 08:01:26.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','','Y','N','N','N','N','N','N','N','Nutzer-ID/Login',TO_TIMESTAMP('2025-01-15 08:01:26.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T08:01:26.267Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734659 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-15T08:01:26.269Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1903) 
;

-- 2025-01-15T08:01:26.276Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734659
;

-- 2025-01-15T08:01:26.277Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734659)
;

-- Field: UI Trace -> UI Trace -> Device ID
-- Column: UI_Trace.UI_DeviceId
-- Field: UI Trace(541841,D) -> UI Trace(547734,D) -> Device ID
-- Column: UI_Trace.UI_DeviceId
-- 2025-01-15T08:01:26.398Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589593,734660,0,547734,TO_TIMESTAMP('2025-01-15 08:01:26.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Device ID',TO_TIMESTAMP('2025-01-15 08:01:26.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T08:01:26.400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734660 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-15T08:01:26.401Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583430) 
;

-- 2025-01-15T08:01:26.404Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734660
;

-- 2025-01-15T08:01:26.405Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734660)
;

-- Field: UI Trace -> UI Trace -> Tab ID
-- Column: UI_Trace.UI_TabId
-- Field: UI Trace(541841,D) -> UI Trace(547734,D) -> Tab ID
-- Column: UI_Trace.UI_TabId
-- 2025-01-15T08:01:26.525Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589594,734661,0,547734,TO_TIMESTAMP('2025-01-15 08:01:26.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Tab ID',TO_TIMESTAMP('2025-01-15 08:01:26.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T08:01:26.527Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734661 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-15T08:01:26.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583431) 
;

-- 2025-01-15T08:01:26.535Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734661
;

-- 2025-01-15T08:01:26.535Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734661)
;

-- Field: UI Trace -> UI Trace -> User Agent
-- Column: UI_Trace.UserAgent
-- Field: UI Trace(541841,D) -> UI Trace(547734,D) -> User Agent
-- Column: UI_Trace.UserAgent
-- 2025-01-15T08:01:26.653Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589595,734662,0,547734,TO_TIMESTAMP('2025-01-15 08:01:26.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Browser Used',255,'D','Y','N','N','N','N','N','N','N','User Agent',TO_TIMESTAMP('2025-01-15 08:01:26.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-15T08:01:26.656Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734662 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-15T08:01:26.659Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1704) 
;

-- 2025-01-15T08:01:26.667Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734662
;

-- 2025-01-15T08:01:26.668Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734662)
;

-- Tab: UI Trace(541841,D) -> UI Trace(547734,D)
-- UI Section: raw data
-- 2025-01-15T08:02:36.175Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547734,546318,TO_TIMESTAMP('2025-01-15 08:02:35.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-01-15 08:02:35.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'raw data')
;

-- 2025-01-15T08:02:36.176Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546318 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: UI Trace(541841,D) -> UI Trace(547734,D) -> raw data
-- UI Column: 10
-- 2025-01-15T08:02:41.359Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547723,546318,TO_TIMESTAMP('2025-01-15 08:02:41.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-01-15 08:02:41.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> raw data -> 10
-- UI Element Group: raw data
-- 2025-01-15T08:02:49.105Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547723,552267,TO_TIMESTAMP('2025-01-15 08:02:48.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','raw data',10,TO_TIMESTAMP('2025-01-15 08:02:48.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: UI Trace -> UI Trace.Daten
-- Column: UI_Trace.EventData
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> raw data -> 10 -> raw data.Daten
-- Column: UI_Trace.EventData
-- 2025-01-15T08:03:19.477Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552267, SeqNo=10,Updated=TO_TIMESTAMP('2025-01-15 08:03:19.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627793
;

-- UI Element: UI Trace -> UI Trace.URL
-- Column: UI_Trace.URL
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> others.URL
-- Column: UI_Trace.URL
-- 2025-01-15T08:04:10.616Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734656,0,547734,552264,627798,'F',TO_TIMESTAMP('2025-01-15 08:04:10.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Z.B. http://www.metasfresh.com','','Y','N','Y','N','N','URL',10,0,0,TO_TIMESTAMP('2025-01-15 08:04:10.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: UI Trace -> UI Trace.Application
-- Column: UI_Trace.UI_ApplicationId
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> others.Application
-- Column: UI_Trace.UI_ApplicationId
-- 2025-01-15T08:04:16.623Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734657,0,547734,552264,627799,'F',TO_TIMESTAMP('2025-01-15 08:04:16.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Application',20,0,0,TO_TIMESTAMP('2025-01-15 08:04:16.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: UI Trace -> UI Trace.Caption
-- Column: UI_Trace.Caption
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> others.Caption
-- Column: UI_Trace.Caption
-- 2025-01-15T08:04:26.857Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734658,0,547734,552264,627800,'F',TO_TIMESTAMP('2025-01-15 08:04:26.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Caption',30,0,0,TO_TIMESTAMP('2025-01-15 08:04:26.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10
-- UI Element Group: url
-- 2025-01-15T08:04:35.341Z
UPDATE AD_UI_ElementGroup SET Name='url',Updated=TO_TIMESTAMP('2025-01-15 08:04:35.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552264
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10
-- UI Element Group: user
-- 2025-01-15T08:04:44.854Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547721,552268,TO_TIMESTAMP('2025-01-15 08:04:44.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','user',30,TO_TIMESTAMP('2025-01-15 08:04:44.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: UI Trace -> UI Trace.Nutzer-ID/Login
-- Column: UI_Trace.UserName
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> user.Nutzer-ID/Login
-- Column: UI_Trace.UserName
-- 2025-01-15T08:05:04.084Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734659,0,547734,552268,627801,'F',TO_TIMESTAMP('2025-01-15 08:05:03.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Nutzer-ID/Login',10,0,0,TO_TIMESTAMP('2025-01-15 08:05:03.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10
-- UI Element Group: device
-- 2025-01-15T08:05:11.271Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547721,552269,TO_TIMESTAMP('2025-01-15 08:05:11.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','device',40,TO_TIMESTAMP('2025-01-15 08:05:11.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: UI Trace -> UI Trace.Device ID
-- Column: UI_Trace.UI_DeviceId
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> device.Device ID
-- Column: UI_Trace.UI_DeviceId
-- 2025-01-15T08:05:20.993Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734660,0,547734,552269,627802,'F',TO_TIMESTAMP('2025-01-15 08:05:20.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Device ID',10,0,0,TO_TIMESTAMP('2025-01-15 08:05:20.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: UI Trace -> UI Trace.Tab ID
-- Column: UI_Trace.UI_TabId
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> device.Tab ID
-- Column: UI_Trace.UI_TabId
-- 2025-01-15T08:05:28.229Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734661,0,547734,552269,627803,'F',TO_TIMESTAMP('2025-01-15 08:05:27.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Tab ID',20,0,0,TO_TIMESTAMP('2025-01-15 08:05:27.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: UI Trace -> UI Trace.User Agent
-- Column: UI_Trace.UserAgent
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> device.User Agent
-- Column: UI_Trace.UserAgent
-- 2025-01-15T08:05:34.236Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734662,0,547734,552269,627804,'F',TO_TIMESTAMP('2025-01-15 08:05:34.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Browser Used','Y','N','Y','N','N','User Agent',30,0,0,TO_TIMESTAMP('2025-01-15 08:05:34.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20
-- UI Element Group: user
-- 2025-01-15T08:06:15.363Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=547722, SeqNo=20,Updated=TO_TIMESTAMP('2025-01-15 08:06:15.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552268
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20
-- UI Element Group: device
-- 2025-01-15T08:06:24.427Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=547722, SeqNo=30,Updated=TO_TIMESTAMP('2025-01-15 08:06:24.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552269
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-01-15T08:06:36.354Z
UPDATE AD_UI_ElementGroup SET SeqNo=90,Updated=TO_TIMESTAMP('2025-01-15 08:06:36.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552265
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20
-- UI Element Group: user
-- 2025-01-15T08:06:38.800Z
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2025-01-15 08:06:38.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552268
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20
-- UI Element Group: device
-- 2025-01-15T08:06:41.376Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2025-01-15 08:06:41.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552269
;

-- UI Column: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-01-15T08:06:45.120Z
UPDATE AD_UI_ElementGroup SET SeqNo=999,Updated=TO_TIMESTAMP('2025-01-15 08:06:45.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552265
;

-- UI Element: UI Trace -> UI Trace.Zeitstempel
-- Column: UI_Trace.Timestamp
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> primary.Zeitstempel
-- Column: UI_Trace.Timestamp
-- 2025-01-15T08:08:16.097Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-01-15 08:08:16.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627791
;

-- UI Element: UI Trace -> UI Trace.Application
-- Column: UI_Trace.UI_ApplicationId
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> url.Application
-- Column: UI_Trace.UI_ApplicationId
-- 2025-01-15T08:08:16.104Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-01-15 08:08:16.104000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627799
;

-- UI Element: UI Trace -> UI Trace.Event Name
-- Column: UI_Trace.EventName
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> primary.Event Name
-- Column: UI_Trace.EventName
-- 2025-01-15T08:08:16.112Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-01-15 08:08:16.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627790
;

-- UI Element: UI Trace -> UI Trace.Caption
-- Column: UI_Trace.Caption
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> url.Caption
-- Column: UI_Trace.Caption
-- 2025-01-15T08:08:16.121Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-01-15 08:08:16.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627800
;

-- UI Element: UI Trace -> UI Trace.URL
-- Column: UI_Trace.URL
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 10 -> url.URL
-- Column: UI_Trace.URL
-- 2025-01-15T08:08:16.129Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-01-15 08:08:16.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627798
;

-- UI Element: UI Trace -> UI Trace.Nutzer-ID/Login
-- Column: UI_Trace.UserName
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20 -> user.Nutzer-ID/Login
-- Column: UI_Trace.UserName
-- 2025-01-15T08:08:16.137Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-01-15 08:08:16.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627801
;

-- UI Element: UI Trace -> UI Trace.Device ID
-- Column: UI_Trace.UI_DeviceId
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20 -> device.Device ID
-- Column: UI_Trace.UI_DeviceId
-- 2025-01-15T08:08:16.144Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-01-15 08:08:16.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627802
;

-- UI Element: UI Trace -> UI Trace.Tab ID
-- Column: UI_Trace.UI_TabId
-- UI Element: UI Trace(541841,D) -> UI Trace(547734,D) -> main -> 20 -> device.Tab ID
-- Column: UI_Trace.UI_TabId
-- 2025-01-15T08:08:16.150Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-01-15 08:08:16.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627803
;

