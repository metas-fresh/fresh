-- Table: AD_BusinessRule
-- Table: AD_BusinessRule
-- 2024-12-17T13:05:13.207Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('4',0,0,0,542456,'A','N',TO_TIMESTAMP('2024-12-17 13:05:12.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','U','N','Y','N','Y','Y','N','N','N','N','N',0,'Business Rule','NP','L','AD_BusinessRule','DTI',TO_TIMESTAMP('2024-12-17 13:05:12.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2024-12-17T13:05:13.212Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542456 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-12-17T13:05:13.321Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556388,TO_TIMESTAMP('2024-12-17 13:05:13.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table AD_BusinessRule',1,'Y','N','Y','Y','AD_BusinessRule',1000000,TO_TIMESTAMP('2024-12-17 13:05:13.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:05:13.328Z
CREATE SEQUENCE AD_BUSINESSRULE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_BusinessRule.AD_Client_ID
-- Column: AD_BusinessRule.AD_Client_ID
-- 2024-12-17T13:05:16.271Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589493,102,0,19,542456,'AD_Client_ID',TO_TIMESTAMP('2024-12-17 13:05:16.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','U',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-12-17 13:05:16.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:16.275Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589493 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:16.279Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_BusinessRule.AD_Org_ID
-- Column: AD_BusinessRule.AD_Org_ID
-- 2024-12-17T13:05:17.348Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589494,113,0,30,542456,'AD_Org_ID',TO_TIMESTAMP('2024-12-17 13:05:16.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','U',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-12-17 13:05:16.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:17.351Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589494 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:17.362Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_BusinessRule.Created
-- Column: AD_BusinessRule.Created
-- 2024-12-17T13:05:18.027Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589495,245,0,16,542456,'Created',TO_TIMESTAMP('2024-12-17 13:05:17.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','U',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-12-17 13:05:17.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:18.030Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589495 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:18.034Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_BusinessRule.CreatedBy
-- Column: AD_BusinessRule.CreatedBy
-- 2024-12-17T13:05:18.744Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589496,246,0,18,110,542456,'CreatedBy',TO_TIMESTAMP('2024-12-17 13:05:18.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','U',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-12-17 13:05:18.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:18.748Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589496 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:18.750Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_BusinessRule.IsActive
-- Column: AD_BusinessRule.IsActive
-- 2024-12-17T13:05:19.380Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589497,348,0,20,542456,'IsActive',TO_TIMESTAMP('2024-12-17 13:05:19.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','U',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-12-17 13:05:19.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:19.383Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589497 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:19.388Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_BusinessRule.Updated
-- Column: AD_BusinessRule.Updated
-- 2024-12-17T13:05:20.011Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589498,607,0,16,542456,'Updated',TO_TIMESTAMP('2024-12-17 13:05:19.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','U',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-12-17 13:05:19.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:20.012Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589498 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:20.016Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_BusinessRule.UpdatedBy
-- Column: AD_BusinessRule.UpdatedBy
-- 2024-12-17T13:05:20.708Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589499,608,0,18,110,542456,'UpdatedBy',TO_TIMESTAMP('2024-12-17 13:05:20.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','U',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-12-17 13:05:20.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:20.710Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589499 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:20.713Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-12-17T13:05:21.240Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583397,0,'AD_BusinessRule_ID',TO_TIMESTAMP('2024-12-17 13:05:21.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Business Rule','Business Rule',TO_TIMESTAMP('2024-12-17 13:05:21.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:05:21.243Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583397 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule.AD_BusinessRule_ID
-- Column: AD_BusinessRule.AD_BusinessRule_ID
-- 2024-12-17T13:05:21.858Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589500,583397,0,13,542456,'AD_BusinessRule_ID',TO_TIMESTAMP('2024-12-17 13:05:21.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Business Rule',0,0,TO_TIMESTAMP('2024-12-17 13:05:21.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:21.860Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589500 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:21.865Z
/* DDL */  select update_Column_Translation_From_AD_Element(583397) 
;

-- Column: AD_BusinessRule.Name
-- Column: AD_BusinessRule.Name
-- 2024-12-17T13:05:45.463Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589501,469,0,10,542456,'XX','Name',TO_TIMESTAMP('2024-12-17 13:05:45.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','U',0,255,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2024-12-17 13:05:45.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:05:45.466Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589501 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:05:45.469Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: AD_BusinessRule.AD_Table_ID
-- Column: AD_BusinessRule.AD_Table_ID
-- 2024-12-17T13:06:29.847Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589502,126,0,30,542456,'XX','AD_Table_ID',TO_TIMESTAMP('2024-12-17 13:06:29.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Database Table information','U',0,10,'The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'DB-Tabelle',0,0,TO_TIMESTAMP('2024-12-17 13:06:29.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:06:29.849Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589502 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:06:29.851Z
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- 2024-12-17T13:07:28.561Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583398,0,'Validation_Rule_ID',TO_TIMESTAMP('2024-12-17 13:07:28.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Validation Rule','Validation Rule',TO_TIMESTAMP('2024-12-17 13:07:28.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:07:28.564Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583398 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule.Validation_Rule_ID
-- Column: AD_BusinessRule.Validation_Rule_ID
-- 2024-12-17T13:08:21.897Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589503,583398,0,30,53462,542456,'XX','Validation_Rule_ID',TO_TIMESTAMP('2024-12-17 13:08:21.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Validation Rule',0,0,TO_TIMESTAMP('2024-12-17 13:08:21.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:08:21.899Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589503 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:08:21.902Z
/* DDL */  select update_Column_Translation_From_AD_Element(583398) 
;

-- 2024-12-17T13:09:26.618Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583399,0,'WarningMessage',TO_TIMESTAMP('2024-12-17 13:09:26.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Warning Message','Warning Message',TO_TIMESTAMP('2024-12-17 13:09:26.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:09:26.620Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583399 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule.WarningMessage
-- Column: AD_BusinessRule.WarningMessage
-- 2024-12-17T13:09:45.795Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589504,583399,0,36,542456,'XX','WarningMessage',TO_TIMESTAMP('2024-12-17 13:09:45.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Warning Message',0,0,TO_TIMESTAMP('2024-12-17 13:09:45.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:09:45.797Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589504 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:09:45.799Z
/* DDL */  select update_Column_Translation_From_AD_Element(583399) 
;

-- 2024-12-17T13:10:20.757Z
/* DDL */ CREATE TABLE public.AD_BusinessRule (AD_BusinessRule_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(255) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Validation_Rule_ID NUMERIC(10), WarningMessage TEXT, CONSTRAINT AD_BusinessRule_Key PRIMARY KEY (AD_BusinessRule_ID), CONSTRAINT ADTable_ADBusinessRule FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ValidationRule_ADBusinessRule FOREIGN KEY (Validation_Rule_ID) REFERENCES public.AD_Val_Rule DEFERRABLE INITIALLY DEFERRED)
;

-- Table: AD_BusinessRule_Precondition
-- Table: AD_BusinessRule_Precondition
-- 2024-12-17T13:10:42.594Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('4',0,0,0,542457,'A','N',TO_TIMESTAMP('2024-12-17 13:10:42.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'Business Rule Precondition','NP','L','AD_BusinessRule_Precondition','DTI',TO_TIMESTAMP('2024-12-17 13:10:42.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2024-12-17T13:10:42.598Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542457 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-12-17T13:10:42.704Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556389,TO_TIMESTAMP('2024-12-17 13:10:42.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table AD_BusinessRule_Precondition',1,'Y','N','Y','Y','AD_BusinessRule_Precondition',1000000,TO_TIMESTAMP('2024-12-17 13:10:42.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:10:42.710Z
CREATE SEQUENCE AD_BUSINESSRULE_PRECONDITION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: AD_BusinessRule_Precondition
-- Table: AD_BusinessRule_Precondition
-- 2024-12-17T13:10:46.168Z
UPDATE AD_Table SET IsChangeLog='Y', IsHighVolume='Y',Updated=TO_TIMESTAMP('2024-12-17 13:10:46.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542457
;

-- Table: AD_BusinessRule
-- Table: AD_BusinessRule
-- 2024-12-17T13:10:48.551Z
UPDATE AD_Table SET IsHighVolume='Y',Updated=TO_TIMESTAMP('2024-12-17 13:10:48.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542456
;

-- Table: AD_BusinessRule
-- Table: AD_BusinessRule
-- 2024-12-17T13:10:53.225Z
UPDATE AD_Table SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-17 13:10:53.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542456
;

update AD_Column set entitytype='D' where ad_table_id=542456;


-- Column: AD_BusinessRule_Precondition.AD_Client_ID
-- Column: AD_BusinessRule_Precondition.AD_Client_ID
-- 2024-12-17T13:12:25.641Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589505,102,0,19,542457,'AD_Client_ID',TO_TIMESTAMP('2024-12-17 13:12:25.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-12-17 13:12:25.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:12:25.643Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589505 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:12:25.646Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_BusinessRule_Precondition.AD_Org_ID
-- Column: AD_BusinessRule_Precondition.AD_Org_ID
-- 2024-12-17T13:12:26.247Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589506,113,0,30,542457,'AD_Org_ID',TO_TIMESTAMP('2024-12-17 13:12:25.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-12-17 13:12:25.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:12:26.248Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589506 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:12:26.250Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_BusinessRule_Precondition.Created
-- Column: AD_BusinessRule_Precondition.Created
-- 2024-12-17T13:12:26.836Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589507,245,0,16,542457,'Created',TO_TIMESTAMP('2024-12-17 13:12:26.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-12-17 13:12:26.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:12:26.838Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589507 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:12:26.841Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_BusinessRule_Precondition.CreatedBy
-- Column: AD_BusinessRule_Precondition.CreatedBy
-- 2024-12-17T13:12:27.438Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589508,246,0,18,110,542457,'CreatedBy',TO_TIMESTAMP('2024-12-17 13:12:27.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-12-17 13:12:27.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:12:27.441Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589508 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:12:27.444Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_BusinessRule_Precondition.IsActive
-- Column: AD_BusinessRule_Precondition.IsActive
-- 2024-12-17T13:12:28.026Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589509,348,0,20,542457,'IsActive',TO_TIMESTAMP('2024-12-17 13:12:27.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-12-17 13:12:27.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:12:28.029Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589509 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:12:28.032Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_BusinessRule_Precondition.Updated
-- Column: AD_BusinessRule_Precondition.Updated
-- 2024-12-17T13:12:28.624Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589510,607,0,16,542457,'Updated',TO_TIMESTAMP('2024-12-17 13:12:28.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-12-17 13:12:28.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:12:28.625Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589510 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:12:28.627Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_BusinessRule_Precondition.UpdatedBy
-- Column: AD_BusinessRule_Precondition.UpdatedBy
-- 2024-12-17T13:12:29.212Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589511,608,0,18,110,542457,'UpdatedBy',TO_TIMESTAMP('2024-12-17 13:12:28.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-12-17 13:12:28.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:12:29.213Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589511 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:12:29.216Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-12-17T13:12:29.653Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583400,0,'AD_BusinessRule_Precondition_ID',TO_TIMESTAMP('2024-12-17 13:12:29.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Business Rule Precondition','Business Rule Precondition',TO_TIMESTAMP('2024-12-17 13:12:29.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:12:29.654Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583400 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_Precondition_ID
-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_Precondition_ID
-- 2024-12-17T13:12:30.235Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589512,583400,0,13,542457,'AD_BusinessRule_Precondition_ID',TO_TIMESTAMP('2024-12-17 13:12:29.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Business Rule Precondition',0,0,TO_TIMESTAMP('2024-12-17 13:12:29.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:12:30.238Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589512 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:12:30.240Z
/* DDL */  select update_Column_Translation_From_AD_Element(583400) 
;

-- 2024-12-17T13:13:24.688Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583401,0,'Precondition_Rule_ID',TO_TIMESTAMP('2024-12-17 13:13:24.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Precondition Rule','Precondition Rule',TO_TIMESTAMP('2024-12-17 13:13:24.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:13:24.691Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583401 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- 2024-12-17T13:13:44.319Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589513,583401,0,30,53462,542457,'XX','Precondition_Rule_ID',TO_TIMESTAMP('2024-12-17 13:13:44.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Precondition Rule',0,0,TO_TIMESTAMP('2024-12-17 13:13:44.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:13:44.321Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589513 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:13:44.323Z
/* DDL */  select update_Column_Translation_From_AD_Element(583401) 
;

-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- 2024-12-17T13:13:54.057Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-12-17 13:13:54.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589513
;

-- Column: AD_BusinessRule_Precondition.Description
-- Column: AD_BusinessRule_Precondition.Description
-- 2024-12-17T13:14:14.240Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589514,275,0,36,542457,'XX','Description',TO_TIMESTAMP('2024-12-17 13:14:14.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2024-12-17 13:14:14.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:14:14.241Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589514 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:14:14.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2024-12-17T13:14:57.018Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583402,0,'PreconditionType',TO_TIMESTAMP('2024-12-17 13:14:56.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Precondition Type','Precondition Type',TO_TIMESTAMP('2024-12-17 13:14:56.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:14:57.020Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583402 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-17T13:15:09.294Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583403,0,'PreconditionSQL',TO_TIMESTAMP('2024-12-17 13:15:09.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Precondition SQL','Precondition SQL',TO_TIMESTAMP('2024-12-17 13:15:09.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T13:15:09.295Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583403 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: PreconditionType
-- 2024-12-17T13:15:49.869Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541912,TO_TIMESTAMP('2024-12-17 13:15:49.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','PreconditionType',TO_TIMESTAMP('2024-12-17 13:15:49.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2024-12-17T13:15:49.871Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541912 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PreconditionType
-- Value: S
-- ValueName: SQL
-- 2024-12-17T13:16:03.218Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541912,543784,TO_TIMESTAMP('2024-12-17 13:16:03.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','SQL',TO_TIMESTAMP('2024-12-17 13:16:03.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','SQL')
;

-- 2024-12-17T13:16:03.220Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543784 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PreconditionType
-- Value: R
-- ValueName: Validation Rule
-- 2024-12-17T13:16:17.997Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541912,543785,TO_TIMESTAMP('2024-12-17 13:16:17.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Validation Rule',TO_TIMESTAMP('2024-12-17 13:16:17.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'R','Validation Rule')
;

-- 2024-12-17T13:16:17.999Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543785 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: AD_BusinessRule_Precondition.PreconditionType
-- Column: AD_BusinessRule_Precondition.PreconditionType
-- 2024-12-17T13:16:33.766Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589515,583402,0,17,541912,542457,'XX','PreconditionType',TO_TIMESTAMP('2024-12-17 13:16:33.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Precondition Type',0,0,TO_TIMESTAMP('2024-12-17 13:16:33.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:16:33.768Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589515 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:16:33.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(583402) 
;

-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- 2024-12-17T13:16:54.920Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589516,583403,0,36,542457,'XX','PreconditionSQL',TO_TIMESTAMP('2024-12-17 13:16:53.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Precondition SQL',0,0,TO_TIMESTAMP('2024-12-17 13:16:53.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T13:16:54.921Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589516 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T13:16:54.923Z
/* DDL */  select update_Column_Translation_From_AD_Element(583403) 
;

-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_ID
-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_ID
-- 2024-12-17T14:51:54.213Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589517,583397,0,30,542457,'XX','AD_BusinessRule_ID',TO_TIMESTAMP('2024-12-17 14:51:53.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Business Rule',0,0,TO_TIMESTAMP('2024-12-17 14:51:53.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T14:51:54.217Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589517 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T14:51:54.223Z
/* DDL */  select update_Column_Translation_From_AD_Element(583397) 
;

-- 2024-12-17T14:51:56.300Z
/* DDL */ CREATE TABLE public.AD_BusinessRule_Precondition (AD_BusinessRule_ID NUMERIC(10) NOT NULL, AD_BusinessRule_Precondition_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Precondition_Rule_ID NUMERIC(10) NOT NULL, PreconditionSQL TEXT, PreconditionType CHAR(1) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADBusinessRule_ADBusinessRulePrecondition FOREIGN KEY (AD_BusinessRule_ID) REFERENCES public.AD_BusinessRule DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_BusinessRule_Precondition_Key PRIMARY KEY (AD_BusinessRule_Precondition_ID), CONSTRAINT PreconditionRule_ADBusinessRulePrecondition FOREIGN KEY (Precondition_Rule_ID) REFERENCES public.AD_Val_Rule DEFERRABLE INITIALLY DEFERRED)
;

-- Table: AD_BusinessRule_Trigger
-- Table: AD_BusinessRule_Trigger
-- 2024-12-17T15:21:54.892Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('4',0,0,0,542458,'A','N',TO_TIMESTAMP('2024-12-17 15:21:54.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'Business Rule Trigger','NP','L','AD_BusinessRule_Trigger','DTI',TO_TIMESTAMP('2024-12-17 15:21:54.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2024-12-17T15:21:54.895Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542458 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-12-17T15:21:55.017Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556390,TO_TIMESTAMP('2024-12-17 15:21:54.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table AD_BusinessRule_Trigger',1,'Y','N','Y','Y','AD_BusinessRule_Trigger',1000000,TO_TIMESTAMP('2024-12-17 15:21:54.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T15:21:55.022Z
CREATE SEQUENCE AD_BUSINESSRULE_TRIGGER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_BusinessRule_Trigger.AD_Client_ID
-- Column: AD_BusinessRule_Trigger.AD_Client_ID
-- 2024-12-17T15:22:12.627Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589518,102,0,19,542458,'AD_Client_ID',TO_TIMESTAMP('2024-12-17 15:22:12.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-12-17 15:22:12.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:12.630Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589518 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:12.636Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_BusinessRule_Trigger.AD_Org_ID
-- Column: AD_BusinessRule_Trigger.AD_Org_ID
-- 2024-12-17T15:22:13.311Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589519,113,0,30,542458,'AD_Org_ID',TO_TIMESTAMP('2024-12-17 15:22:13.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-12-17 15:22:13.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:13.312Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589519 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:13.315Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_BusinessRule_Trigger.Created
-- Column: AD_BusinessRule_Trigger.Created
-- 2024-12-17T15:22:13.885Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589520,245,0,16,542458,'Created',TO_TIMESTAMP('2024-12-17 15:22:13.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-12-17 15:22:13.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:13.887Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589520 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:13.889Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_BusinessRule_Trigger.CreatedBy
-- Column: AD_BusinessRule_Trigger.CreatedBy
-- 2024-12-17T15:22:14.469Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589521,246,0,18,110,542458,'CreatedBy',TO_TIMESTAMP('2024-12-17 15:22:14.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-12-17 15:22:14.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:14.470Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589521 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:14.474Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_BusinessRule_Trigger.IsActive
-- Column: AD_BusinessRule_Trigger.IsActive
-- 2024-12-17T15:22:15.065Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589522,348,0,20,542458,'IsActive',TO_TIMESTAMP('2024-12-17 15:22:14.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-12-17 15:22:14.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:15.068Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589522 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:15.071Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_BusinessRule_Trigger.Updated
-- Column: AD_BusinessRule_Trigger.Updated
-- 2024-12-17T15:22:15.648Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589523,607,0,16,542458,'Updated',TO_TIMESTAMP('2024-12-17 15:22:15.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-12-17 15:22:15.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:15.651Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589523 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:15.654Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_BusinessRule_Trigger.UpdatedBy
-- Column: AD_BusinessRule_Trigger.UpdatedBy
-- 2024-12-17T15:22:16.289Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589524,608,0,18,110,542458,'UpdatedBy',TO_TIMESTAMP('2024-12-17 15:22:16.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-12-17 15:22:16.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:16.290Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589524 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:16.293Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-12-17T15:22:16.777Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583404,0,'AD_BusinessRule_Trigger_ID',TO_TIMESTAMP('2024-12-17 15:22:16.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Business Rule Trigger','Business Rule Trigger',TO_TIMESTAMP('2024-12-17 15:22:16.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T15:22:16.779Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583404 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_Trigger_ID
-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_Trigger_ID
-- 2024-12-17T15:22:17.353Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589525,583404,0,13,542458,'AD_BusinessRule_Trigger_ID',TO_TIMESTAMP('2024-12-17 15:22:16.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Business Rule Trigger',0,0,TO_TIMESTAMP('2024-12-17 15:22:16.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:17.354Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589525 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:17.357Z
/* DDL */  select update_Column_Translation_From_AD_Element(583404) 
;

-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_ID
-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_ID
-- 2024-12-17T15:22:40.235Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589526,583397,0,30,542458,'XX','AD_BusinessRule_ID',TO_TIMESTAMP('2024-12-17 15:22:40.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Business Rule',0,0,TO_TIMESTAMP('2024-12-17 15:22:40.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:22:40.237Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589526 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:22:40.239Z
/* DDL */  select update_Column_Translation_From_AD_Element(583397) 
;

-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- 2024-12-17T15:26:48.060Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589527,577632,0,30,540750,542458,'XX','Source_Table_ID',TO_TIMESTAMP('2024-12-17 15:26:47.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Source Table',0,0,TO_TIMESTAMP('2024-12-17 15:26:47.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:26:48.062Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589527 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:26:48.064Z
/* DDL */  select update_Column_Translation_From_AD_Element(577632) 
;

-- 2024-12-17T15:30:05.504Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583405,0,'InOnNew',TO_TIMESTAMP('2024-12-17 15:30:05.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','On New','On New',TO_TIMESTAMP('2024-12-17 15:30:05.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T15:30:05.507Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583405 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-17T15:30:19.139Z
UPDATE AD_Element SET ColumnName='IsOnNew',Updated=TO_TIMESTAMP('2024-12-17 15:30:19.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583405
;

-- 2024-12-17T15:30:19.141Z
UPDATE AD_Column SET ColumnName='IsOnNew' WHERE AD_Element_ID=583405
;

-- 2024-12-17T15:30:19.142Z
UPDATE AD_Process_Para SET ColumnName='IsOnNew' WHERE AD_Element_ID=583405
;

-- 2024-12-17T15:30:19.146Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583405,'de_DE') 
;

-- 2024-12-17T15:30:23.046Z
UPDATE AD_Element SET ColumnName='OnNew',Updated=TO_TIMESTAMP('2024-12-17 15:30:23.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583405
;

-- 2024-12-17T15:30:23.047Z
UPDATE AD_Column SET ColumnName='OnNew' WHERE AD_Element_ID=583405
;

-- 2024-12-17T15:30:23.048Z
UPDATE AD_Process_Para SET ColumnName='OnNew' WHERE AD_Element_ID=583405
;

-- 2024-12-17T15:30:23.058Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583405,'de_DE') 
;

-- 2024-12-17T15:30:38.806Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583406,0,'OnUpdate',TO_TIMESTAMP('2024-12-17 15:30:38.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','On Update','On Update',TO_TIMESTAMP('2024-12-17 15:30:38.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T15:30:38.808Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583406 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-17T15:30:49.925Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583407,0,'OnDelete',TO_TIMESTAMP('2024-12-17 15:30:49.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','On Delete','On Delete',TO_TIMESTAMP('2024-12-17 15:30:49.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T15:30:49.927Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583407 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-17T15:31:12.751Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583408,0,'TargetRecordMappingSQL',TO_TIMESTAMP('2024-12-17 15:31:12.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Target Record Mapping SQL','Target Record Mapping SQL',TO_TIMESTAMP('2024-12-17 15:31:12.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T15:31:12.753Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583408 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Trigger.OnNew
-- Column: AD_BusinessRule_Trigger.OnNew
-- 2024-12-17T15:31:26.516Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589528,583405,0,20,542458,'XX','OnNew',TO_TIMESTAMP('2024-12-17 15:31:26.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'On New',0,0,TO_TIMESTAMP('2024-12-17 15:31:26.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:31:26.518Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589528 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:31:26.520Z
/* DDL */  select update_Column_Translation_From_AD_Element(583405) 
;

-- Column: AD_BusinessRule_Trigger.OnUpdate
-- Column: AD_BusinessRule_Trigger.OnUpdate
-- 2024-12-17T15:31:37.802Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589529,583406,0,20,542458,'XX','OnUpdate',TO_TIMESTAMP('2024-12-17 15:31:37.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'On Update',0,0,TO_TIMESTAMP('2024-12-17 15:31:37.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:31:37.803Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589529 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:31:37.806Z
/* DDL */  select update_Column_Translation_From_AD_Element(583406) 
;

-- Column: AD_BusinessRule_Trigger.OnDelete
-- Column: AD_BusinessRule_Trigger.OnDelete
-- 2024-12-17T15:31:48.855Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589530,583407,0,20,542458,'XX','OnDelete',TO_TIMESTAMP('2024-12-17 15:31:48.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'On Delete',0,0,TO_TIMESTAMP('2024-12-17 15:31:48.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:31:48.857Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589530 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:31:48.860Z
/* DDL */  select update_Column_Translation_From_AD_Element(583407) 
;

-- 2024-12-17T15:32:22.568Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583409,0,'ConditionType',TO_TIMESTAMP('2024-12-17 15:32:22.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Condition Type','Condition Type',TO_TIMESTAMP('2024-12-17 15:32:22.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T15:32:22.570Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583409 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-17T15:32:36.919Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583410,0,'ConditionSQL',TO_TIMESTAMP('2024-12-17 15:32:35.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Condition SQL','Condition SQL',TO_TIMESTAMP('2024-12-17 15:32:35.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T15:32:36.920Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583410 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Trigger.ConditionSQL
-- Column: AD_BusinessRule_Trigger.ConditionSQL
-- 2024-12-17T15:32:59.865Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589531,583410,0,36,542458,'XX','ConditionSQL',TO_TIMESTAMP('2024-12-17 15:32:59.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Condition SQL',0,0,TO_TIMESTAMP('2024-12-17 15:32:59.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:32:59.866Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589531 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:32:59.870Z
/* DDL */  select update_Column_Translation_From_AD_Element(583410) 
;

-- Column: AD_BusinessRule_Trigger.TargetRecordMappingSQL
-- Column: AD_BusinessRule_Trigger.TargetRecordMappingSQL
-- 2024-12-17T15:33:20.588Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589532,583408,0,36,542458,'XX','TargetRecordMappingSQL',TO_TIMESTAMP('2024-12-17 15:33:20.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Target Record Mapping SQL',0,0,TO_TIMESTAMP('2024-12-17 15:33:20.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T15:33:20.591Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589532 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T15:33:20.595Z
/* DDL */  select update_Column_Translation_From_AD_Element(583408) 
;

-- Column: AD_BusinessRule_Trigger.TargetRecordMappingSQL
-- Column: AD_BusinessRule_Trigger.TargetRecordMappingSQL
-- 2024-12-17T15:33:23.643Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-12-17 15:33:23.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589532
;

-- 2024-12-18T07:22:22.833Z
/* DDL */ CREATE TABLE public.AD_BusinessRule_Trigger (AD_BusinessRule_ID NUMERIC(10) NOT NULL, AD_BusinessRule_Trigger_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, ConditionSQL TEXT, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, OnDelete CHAR(1) DEFAULT 'N' CHECK (OnDelete IN ('Y','N')) NOT NULL, OnNew CHAR(1) DEFAULT 'N' CHECK (OnNew IN ('Y','N')) NOT NULL, OnUpdate CHAR(1) DEFAULT 'N' CHECK (OnUpdate IN ('Y','N')) NOT NULL, Source_Table_ID NUMERIC(10) NOT NULL, TargetRecordMappingSQL TEXT NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADBusinessRule_ADBusinessRuleTrigger FOREIGN KEY (AD_BusinessRule_ID) REFERENCES public.AD_BusinessRule DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_BusinessRule_Trigger_Key PRIMARY KEY (AD_BusinessRule_Trigger_ID), CONSTRAINT SourceTable_ADBusinessRuleTrigger FOREIGN KEY (Source_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED)
;

