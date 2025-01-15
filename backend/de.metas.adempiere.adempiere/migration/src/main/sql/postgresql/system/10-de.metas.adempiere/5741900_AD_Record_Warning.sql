-- Table: AD_Record_Warning
-- Table: AD_Record_Warning
-- 2024-12-17T11:15:36.349Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('7',0,0,0,542455,'A','N',TO_TIMESTAMP('2024-12-17 11:15:36.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','D','N','Y','N','N','Y','N','Y','Y','N','N',0,'Warnings','NP','L','AD_Record_Warning','DTI',TO_TIMESTAMP('2024-12-17 11:15:36.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2024-12-17T11:15:36.354Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542455 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-12-17T11:15:36.542Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556387,TO_TIMESTAMP('2024-12-17 11:15:36.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table AD_Record_Warning',1,'Y','N','Y','Y','AD_Record_Warning',1000000,TO_TIMESTAMP('2024-12-17 11:15:36.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:15:36.561Z
CREATE SEQUENCE AD_RECORD_WARNING_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_Record_Warning.AD_Client_ID
-- Column: AD_Record_Warning.AD_Client_ID
-- 2024-12-17T11:15:43.850Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589482,102,0,19,542455,'AD_Client_ID',TO_TIMESTAMP('2024-12-17 11:15:43.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-12-17 11:15:43.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:15:43.855Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589482 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:15:44.020Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_Record_Warning.AD_Org_ID
-- Column: AD_Record_Warning.AD_Org_ID
-- 2024-12-17T11:15:45.066Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589483,113,0,30,542455,'AD_Org_ID',TO_TIMESTAMP('2024-12-17 11:15:44.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-12-17 11:15:44.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:15:45.069Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589483 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:15:45.073Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_Record_Warning.Created
-- Column: AD_Record_Warning.Created
-- 2024-12-17T11:15:45.778Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589484,245,0,16,542455,'Created',TO_TIMESTAMP('2024-12-17 11:15:45.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-12-17 11:15:45.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:15:45.779Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589484 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:15:45.782Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_Record_Warning.CreatedBy
-- Column: AD_Record_Warning.CreatedBy
-- 2024-12-17T11:15:46.517Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589485,246,0,18,110,542455,'CreatedBy',TO_TIMESTAMP('2024-12-17 11:15:46.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-12-17 11:15:46.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:15:46.520Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589485 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:15:46.523Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_Record_Warning.IsActive
-- Column: AD_Record_Warning.IsActive
-- 2024-12-17T11:15:47.162Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589486,348,0,20,542455,'IsActive',TO_TIMESTAMP('2024-12-17 11:15:46.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-12-17 11:15:46.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:15:47.164Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589486 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:15:47.166Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_Record_Warning.Updated
-- Column: AD_Record_Warning.Updated
-- 2024-12-17T11:15:47.865Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589487,607,0,16,542455,'Updated',TO_TIMESTAMP('2024-12-17 11:15:47.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-12-17 11:15:47.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:15:47.867Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589487 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:15:47.870Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_Record_Warning.UpdatedBy
-- Column: AD_Record_Warning.UpdatedBy
-- 2024-12-17T11:15:48.547Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589488,608,0,18,110,542455,'UpdatedBy',TO_TIMESTAMP('2024-12-17 11:15:48.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-12-17 11:15:48.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:15:48.549Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589488 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:15:48.552Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-12-17T11:15:49.063Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583396,0,'AD_Record_Warning_ID',TO_TIMESTAMP('2024-12-17 11:15:48.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Warnings','Warnings',TO_TIMESTAMP('2024-12-17 11:15:48.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:15:49.067Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583396 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Record_Warning.AD_Record_Warning_ID
-- Column: AD_Record_Warning.AD_Record_Warning_ID
-- 2024-12-17T11:15:49.638Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589489,583396,0,13,542455,'AD_Record_Warning_ID',TO_TIMESTAMP('2024-12-17 11:15:48.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Warnings',0,0,TO_TIMESTAMP('2024-12-17 11:15:48.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:15:49.640Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589489 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:15:49.643Z
/* DDL */  select update_Column_Translation_From_AD_Element(583396) 
;

-- Column: AD_Record_Warning.AD_Table_ID
-- Column: AD_Record_Warning.AD_Table_ID
-- 2024-12-17T11:16:07.727Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589490,126,0,30,542455,'XX','AD_Table_ID',TO_TIMESTAMP('2024-12-17 11:16:07.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'DB-Tabelle',0,0,TO_TIMESTAMP('2024-12-17 11:16:07.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:16:07.729Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589490 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:16:07.733Z
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- Column: AD_Record_Warning.Record_ID
-- Column: AD_Record_Warning.Record_ID
-- 2024-12-17T11:16:27.087Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589491,538,0,13,542455,'XX','Record_ID',TO_TIMESTAMP('2024-12-17 11:16:26.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Direct internal record ID','D',0,10,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Datensatz-ID',0,0,TO_TIMESTAMP('2024-12-17 11:16:26.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:16:27.089Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589491 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:16:27.091Z
/* DDL */  select update_Column_Translation_From_AD_Element(538) 
;

-- Column: AD_Record_Warning.Record_ID
-- Column: AD_Record_Warning.Record_ID
-- 2024-12-17T11:21:03.049Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2024-12-17 11:21:03.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589491
;

-- Column: AD_Record_Warning.MsgText
-- Column: AD_Record_Warning.MsgText
-- 2024-12-17T11:21:58.772Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589492,463,0,36,542455,'XX','MsgText',TO_TIMESTAMP('2024-12-17 11:21:58.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Textual Informational, Menu or Error Message','D',0,4000,'The Message Text indicates the message that will display ','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Message Text',0,0,TO_TIMESTAMP('2024-12-17 11:21:58.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-17T11:21:58.775Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589492 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-17T11:21:58.777Z
/* DDL */  select update_Column_Translation_From_AD_Element(463) 
;

-- 2024-12-17T11:24:51.523Z
/* DDL */ CREATE TABLE public.AD_Record_Warning (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Record_Warning_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MsgText TEXT, Record_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT AD_Record_Warning_Key PRIMARY KEY (AD_Record_Warning_ID), CONSTRAINT ADTable_ADRecordWarning FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED)
;

-- Window: Warnings, InternalName=null
-- Window: Warnings, InternalName=null
-- 2024-12-17T11:27:50.097Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583396,0,541836,TO_TIMESTAMP('2024-12-17 11:27:49.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','Y','N','N','N','Y','Warnings','N',TO_TIMESTAMP('2024-12-17 11:27:49.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2024-12-17T11:27:50.101Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541836 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-12-17T11:27:50.104Z
/* DDL */  select update_window_translation_from_ad_element(583396) 
;

-- 2024-12-17T11:27:50.122Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541836
;

-- 2024-12-17T11:27:50.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541836)
;

-- Tab: Warnings -> Warnings
-- Table: AD_Record_Warning
-- Tab: Warnings(541836,D) -> Warnings
-- Table: AD_Record_Warning
-- 2024-12-17T11:28:31.666Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583396,0,547698,542455,541836,'Y',TO_TIMESTAMP('2024-12-17 11:28:31.447000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_Record_Warning','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Warnings','N',10,0,TO_TIMESTAMP('2024-12-17 11:28:31.447000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:28:31.670Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547698 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-12-17T11:28:31.674Z
/* DDL */  select update_tab_translation_from_ad_element(583396) 
;

-- 2024-12-17T11:28:31.681Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547698)
;

-- Field: Warnings -> Warnings -> Mandant
-- Column: AD_Record_Warning.AD_Client_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Mandant
-- Column: AD_Record_Warning.AD_Client_ID
-- 2024-12-17T11:28:36.823Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589482,734083,0,547698,TO_TIMESTAMP('2024-12-17 11:28:36.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-12-17 11:28:36.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:28:36.827Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:28:36.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-12-17T11:28:38.456Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734083
;

-- 2024-12-17T11:28:38.465Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734083)
;

-- Field: Warnings -> Warnings -> Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2024-12-17T11:28:38.593Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589483,734084,0,547698,TO_TIMESTAMP('2024-12-17 11:28:38.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-12-17 11:28:38.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:28:38.595Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:28:38.596Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-12-17T11:28:39.071Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734084
;

-- 2024-12-17T11:28:39.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734084)
;

-- Field: Warnings -> Warnings -> Aktiv
-- Column: AD_Record_Warning.IsActive
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Aktiv
-- Column: AD_Record_Warning.IsActive
-- 2024-12-17T11:28:39.194Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589486,734085,0,547698,TO_TIMESTAMP('2024-12-17 11:28:39.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-12-17 11:28:39.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:28:39.196Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:28:39.198Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-12-17T11:28:39.356Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734085
;

-- 2024-12-17T11:28:39.357Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734085)
;

-- Field: Warnings -> Warnings -> Warnings
-- Column: AD_Record_Warning.AD_Record_Warning_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Warnings
-- Column: AD_Record_Warning.AD_Record_Warning_ID
-- 2024-12-17T11:28:39.482Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589489,734086,0,547698,TO_TIMESTAMP('2024-12-17 11:28:39.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Warnings',TO_TIMESTAMP('2024-12-17 11:28:39.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:28:39.483Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:28:39.485Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583396) 
;

-- 2024-12-17T11:28:39.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734086
;

-- 2024-12-17T11:28:39.488Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734086)
;

-- Field: Warnings -> Warnings -> DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- 2024-12-17T11:28:39.605Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589490,734087,0,547698,TO_TIMESTAMP('2024-12-17 11:28:39.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',10,'D','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2024-12-17 11:28:39.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:28:39.606Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:28:39.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2024-12-17T11:28:39.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734087
;

-- 2024-12-17T11:28:39.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734087)
;

-- Field: Warnings -> Warnings -> Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- 2024-12-17T11:28:39.785Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589491,734088,0,547698,TO_TIMESTAMP('2024-12-17 11:28:39.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID',10,'D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2024-12-17 11:28:39.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:28:39.788Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:28:39.790Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2024-12-17T11:28:39.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734088
;

-- 2024-12-17T11:28:39.810Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734088)
;

-- Field: Warnings -> Warnings -> Message Text
-- Column: AD_Record_Warning.MsgText
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Message Text
-- Column: AD_Record_Warning.MsgText
-- 2024-12-17T11:28:39.917Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589492,734089,0,547698,TO_TIMESTAMP('2024-12-17 11:28:39.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Textual Informational, Menu or Error Message',4000,'D','The Message Text indicates the message that will display ','Y','N','N','N','N','N','N','N','Message Text',TO_TIMESTAMP('2024-12-17 11:28:39.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:28:39.918Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:28:39.920Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(463) 
;

-- 2024-12-17T11:28:39.925Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734089
;

-- 2024-12-17T11:28:39.925Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734089)
;

-- 2024-12-17T11:29:20.130Z
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- Tab: Warnings(541836,D) -> Warnings(547698,D)
-- UI Section: main
-- 2024-12-17T11:29:27.033Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547698,546281,TO_TIMESTAMP('2024-12-17 11:29:26.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-17 11:29:26.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-12-17T11:29:27.036Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546281 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Warnings(541836,D) -> Warnings(547698,D) -> main
-- UI Column: 10
-- 2024-12-17T11:29:27.210Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547677,546281,TO_TIMESTAMP('2024-12-17 11:29:27.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-17 11:29:27.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Warnings(541836,D) -> Warnings(547698,D) -> main
-- UI Column: 20
-- 2024-12-17T11:29:27.317Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547678,546281,TO_TIMESTAMP('2024-12-17 11:29:27.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-12-17 11:29:27.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10
-- UI Element Group: default
-- 2024-12-17T11:29:27.480Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547677,552186,TO_TIMESTAMP('2024-12-17 11:29:27.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-12-17 11:29:27.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnings -> Warnings.Message Text
-- Column: AD_Record_Warning.MsgText
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10 -> default.Message Text
-- Column: AD_Record_Warning.MsgText
-- 2024-12-17T11:30:07.135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734089,0,547698,552186,627396,'F',TO_TIMESTAMP('2024-12-17 11:30:05.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Textual Informational, Menu or Error Message','The Message Text indicates the message that will display ','Y','N','Y','N','N','Message Text',10,0,0,TO_TIMESTAMP('2024-12-17 11:30:05.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10
-- UI Element Group: record
-- 2024-12-17T11:34:06.470Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547677,552187,TO_TIMESTAMP('2024-12-17 11:34:06.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','record',20,TO_TIMESTAMP('2024-12-17 11:34:06.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnings -> Warnings.DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10 -> record.DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- 2024-12-17T11:34:14.391Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734087,0,547698,552187,627397,'F',TO_TIMESTAMP('2024-12-17 11:34:14.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','N','N','DB-Tabelle',10,0,0,TO_TIMESTAMP('2024-12-17 11:34:14.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnings -> Warnings.Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10 -> record.Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- 2024-12-17T11:34:21.180Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734088,0,547698,552187,627398,'F',TO_TIMESTAMP('2024-12-17 11:34:21.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','Datensatz-ID',20,0,0,TO_TIMESTAMP('2024-12-17 11:34:21.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Warnings(541836,D) -> Warnings(547698,D) -> main -> 20
-- UI Element Group: flags
-- 2024-12-17T11:34:31.246Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547678,552188,TO_TIMESTAMP('2024-12-17 11:34:31.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2024-12-17 11:34:31.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnings -> Warnings.Aktiv
-- Column: AD_Record_Warning.IsActive
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Record_Warning.IsActive
-- 2024-12-17T11:34:39.975Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734085,0,547698,552188,627399,'F',TO_TIMESTAMP('2024-12-17 11:34:38.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-12-17 11:34:38.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Warnings(541836,D) -> Warnings(547698,D) -> main -> 20
-- UI Element Group: org&client
-- 2024-12-17T11:34:48.817Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547678,552189,TO_TIMESTAMP('2024-12-17 11:34:48.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org&client',20,TO_TIMESTAMP('2024-12-17 11:34:48.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnings -> Warnings.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2024-12-17T11:34:59.923Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734084,0,547698,552189,627400,'F',TO_TIMESTAMP('2024-12-17 11:34:59.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2024-12-17 11:34:59.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnings -> Warnings.Mandant
-- Column: AD_Record_Warning.AD_Client_ID
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 20 -> org&client.Mandant
-- Column: AD_Record_Warning.AD_Client_ID
-- 2024-12-17T11:35:06.464Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734083,0,547698,552189,627401,'F',TO_TIMESTAMP('2024-12-17 11:35:06.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2024-12-17 11:35:06.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnings -> Warnings.Message Text
-- Column: AD_Record_Warning.MsgText
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10 -> default.Message Text
-- Column: AD_Record_Warning.MsgText
-- 2024-12-17T11:35:26.502Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-12-17 11:35:26.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627396
;

-- UI Element: Warnings -> Warnings.DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10 -> record.DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- 2024-12-17T11:35:26.509Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-12-17 11:35:26.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627397
;

-- UI Element: Warnings -> Warnings.Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10 -> record.Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- 2024-12-17T11:35:26.515Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-12-17 11:35:26.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627398
;

-- UI Element: Warnings -> Warnings.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2024-12-17T11:35:26.521Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-12-17 11:35:26.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627400
;

-- Field: Warnings -> Warnings -> Erstellt
-- Column: AD_Record_Warning.Created
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Erstellt
-- Column: AD_Record_Warning.Created
-- 2024-12-17T11:36:00.001Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589484,734090,0,547698,TO_TIMESTAMP('2024-12-17 11:35:58.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-12-17 11:35:58.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:36:00.004Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:36:00.005Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2024-12-17T11:36:00.068Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734090
;

-- 2024-12-17T11:36:00.069Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734090)
;

-- Field: Warnings -> Warnings -> Erstellt durch
-- Column: AD_Record_Warning.CreatedBy
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Erstellt durch
-- Column: AD_Record_Warning.CreatedBy
-- 2024-12-17T11:36:12.194Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589485,734091,0,547698,TO_TIMESTAMP('2024-12-17 11:36:12.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2024-12-17 11:36:12.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:36:12.196Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:36:12.198Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2024-12-17T11:36:12.265Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734091
;

-- 2024-12-17T11:36:12.266Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734091)
;

-- Field: Warnings -> Warnings -> Aktualisiert
-- Column: AD_Record_Warning.Updated
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Aktualisiert
-- Column: AD_Record_Warning.Updated
-- 2024-12-17T11:36:12.384Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589487,734092,0,547698,TO_TIMESTAMP('2024-12-17 11:36:12.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2024-12-17 11:36:12.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:36:12.385Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:36:12.386Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2024-12-17T11:36:12.435Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734092
;

-- 2024-12-17T11:36:12.436Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734092)
;

-- Field: Warnings -> Warnings -> Aktualisiert durch
-- Column: AD_Record_Warning.UpdatedBy
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Aktualisiert durch
-- Column: AD_Record_Warning.UpdatedBy
-- 2024-12-17T11:36:12.554Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589488,734093,0,547698,TO_TIMESTAMP('2024-12-17 11:36:12.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2024-12-17 11:36:12.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-17T11:36:12.556Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-17T11:36:12.557Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2024-12-17T11:36:12.591Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734093
;

-- 2024-12-17T11:36:12.592Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734093)
;

-- UI Element: Warnings -> Warnings.Erstellt
-- Column: AD_Record_Warning.Created
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10 -> default.Erstellt
-- Column: AD_Record_Warning.Created
-- 2024-12-17T11:36:34.994Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734090,0,547698,552186,627402,'F',TO_TIMESTAMP('2024-12-17 11:36:34.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','N','N','Erstellt',20,0,0,TO_TIMESTAMP('2024-12-17 11:36:34.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Warnings -> Warnings -> Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2024-12-17T11:36:50.353Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:36:50.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734084
;

-- Field: Warnings -> Warnings -> Aktiv
-- Column: AD_Record_Warning.IsActive
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Aktiv
-- Column: AD_Record_Warning.IsActive
-- 2024-12-17T11:36:51.482Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:36:51.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734085
;

-- Field: Warnings -> Warnings -> Warnings
-- Column: AD_Record_Warning.AD_Record_Warning_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Warnings
-- Column: AD_Record_Warning.AD_Record_Warning_ID
-- 2024-12-17T11:36:52.571Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:36:52.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734086
;

-- Field: Warnings -> Warnings -> DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> DB-Tabelle
-- Column: AD_Record_Warning.AD_Table_ID
-- 2024-12-17T11:36:53.990Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:36:53.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734087
;

-- Field: Warnings -> Warnings -> Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Datensatz-ID
-- Column: AD_Record_Warning.Record_ID
-- 2024-12-17T11:36:55.173Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:36:55.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734088
;

-- Field: Warnings -> Warnings -> Message Text
-- Column: AD_Record_Warning.MsgText
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Message Text
-- Column: AD_Record_Warning.MsgText
-- 2024-12-17T11:36:56.377Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:36:56.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734089
;

-- Field: Warnings -> Warnings -> Erstellt
-- Column: AD_Record_Warning.Created
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Erstellt
-- Column: AD_Record_Warning.Created
-- 2024-12-17T11:36:57.723Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:36:57.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734090
;

-- Field: Warnings -> Warnings -> Erstellt durch
-- Column: AD_Record_Warning.CreatedBy
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Erstellt durch
-- Column: AD_Record_Warning.CreatedBy
-- 2024-12-17T11:36:58.992Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:36:58.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734091
;

-- Field: Warnings -> Warnings -> Aktualisiert
-- Column: AD_Record_Warning.Updated
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Aktualisiert
-- Column: AD_Record_Warning.Updated
-- 2024-12-17T11:37:00.146Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:37:00.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734092
;

-- Field: Warnings -> Warnings -> Aktualisiert durch
-- Column: AD_Record_Warning.UpdatedBy
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Aktualisiert durch
-- Column: AD_Record_Warning.UpdatedBy
-- 2024-12-17T11:37:02.230Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:37:02.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734093
;

-- Tab: Warnings -> Warnings
-- Table: AD_Record_Warning
-- Tab: Warnings(541836,D) -> Warnings
-- Table: AD_Record_Warning
-- 2024-12-17T11:37:09.889Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-17 11:37:09.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547698
;

-- Field: Warnings -> Warnings -> Erstellt
-- Column: AD_Record_Warning.Created
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Erstellt
-- Column: AD_Record_Warning.Created
-- 2024-12-17T11:37:29.539Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2024-12-17 11:37:29.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734090
;

