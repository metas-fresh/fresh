-- 2025-01-14T09:35:04.378Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583427,0,'UI_Trace_ExternalId',TO_TIMESTAMP('2025-01-14 09:35:04.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','UI Trace External ID','UI Trace External ID',TO_TIMESTAMP('2025-01-14 09:35:04.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-14T09:35:04.388Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583427 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: API_Request_Audit.UI_Trace_ExternalId
-- Column: API_Request_Audit.UI_Trace_ExternalId
-- 2025-01-14T09:35:24.221Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589588,583427,0,10,541636,'XX','UI_Trace_ExternalId',TO_TIMESTAMP('2025-01-14 09:35:24.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UI Trace External ID',0,0,TO_TIMESTAMP('2025-01-14 09:35:24.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-14T09:35:24.223Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589588 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-14T09:35:24.226Z
/* DDL */  select update_Column_Translation_From_AD_Element(583427) 
;

-- 2025-01-14T09:35:24.891Z
/* DDL */ SELECT public.db_alter_table('API_Request_Audit','ALTER TABLE public.API_Request_Audit ADD COLUMN UI_Trace_ExternalId VARCHAR(255)')
;

-- Field: API Aufruf Revision -> API Aufruf Revision -> UI Trace External ID
-- Column: API_Request_Audit.UI_Trace_ExternalId
-- Field: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> UI Trace External ID
-- Column: API_Request_Audit.UI_Trace_ExternalId
-- 2025-01-14T09:35:55.327Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589588,734655,0,543896,TO_TIMESTAMP('2025-01-14 09:35:54.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','UI Trace External ID',TO_TIMESTAMP('2025-01-14 09:35:54.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-14T09:35:55.330Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734655 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-14T09:35:55.333Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583427) 
;

-- 2025-01-14T09:35:55.337Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734655
;

-- 2025-01-14T09:35:55.339Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734655)
;

-- Column: API_Request_Audit.UI_Trace_ExternalId
-- Column: API_Request_Audit.UI_Trace_ExternalId
-- 2025-01-14T09:36:05.035Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-14 09:36:05.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589588
;

-- UI Column: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> body -> 10
-- UI Element Group: UI Tracing
-- 2025-01-14T09:36:27.821Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543837,552266,TO_TIMESTAMP('2025-01-14 09:36:27.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','UI Tracing',20,TO_TIMESTAMP('2025-01-14 09:36:27.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: API Aufruf Revision -> API Aufruf Revision.UI Trace External ID
-- Column: API_Request_Audit.UI_Trace_ExternalId
-- UI Element: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> body -> 10 -> UI Tracing.UI Trace External ID
-- Column: API_Request_Audit.UI_Trace_ExternalId
-- 2025-01-14T09:36:36.691Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734655,0,543896,552266,627797,'F',TO_TIMESTAMP('2025-01-14 09:36:36.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','UI Trace External ID',10,0,0,TO_TIMESTAMP('2025-01-14 09:36:36.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: API Aufruf Revision -> API Aufruf Revision -> UI Trace External ID
-- Column: API_Request_Audit.UI_Trace_ExternalId
-- Field: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> UI Trace External ID
-- Column: API_Request_Audit.UI_Trace_ExternalId
-- 2025-01-14T09:37:14.321Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-01-14 09:37:14.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734655
;

