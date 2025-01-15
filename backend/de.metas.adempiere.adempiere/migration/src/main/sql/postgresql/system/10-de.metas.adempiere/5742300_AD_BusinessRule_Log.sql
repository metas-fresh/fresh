-- Table: AD_BusinessRule_Log
-- Table: AD_BusinessRule_Log
-- 2024-12-19T12:29:10.604Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('7',0,0,0,542460,'A','N',TO_TIMESTAMP('2024-12-19 12:29:10.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','D','N','Y','N','N','Y','N','N','Y','N','N',0,'Business Rule Log','NP','L','AD_BusinessRule_Log','DTI',TO_TIMESTAMP('2024-12-19 12:29:10.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2024-12-19T12:29:10.609Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542460 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-12-19T12:29:10.752Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556392,TO_TIMESTAMP('2024-12-19 12:29:10.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table AD_BusinessRule_Log',1,'Y','N','Y','Y','AD_BusinessRule_Log',1000000,TO_TIMESTAMP('2024-12-19 12:29:10.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T12:29:10.765Z
CREATE SEQUENCE AD_BUSINESSRULE_LOG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_BusinessRule_Log.AD_Client_ID
-- Column: AD_BusinessRule_Log.AD_Client_ID
-- 2024-12-19T12:29:14.651Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589553,102,0,19,542460,'AD_Client_ID',TO_TIMESTAMP('2024-12-19 12:29:14.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-12-19 12:29:14.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:29:14.654Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589553 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:29:14.836Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_BusinessRule_Log.AD_Org_ID
-- Column: AD_BusinessRule_Log.AD_Org_ID
-- 2024-12-19T12:29:15.709Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589554,113,0,30,542460,'AD_Org_ID',TO_TIMESTAMP('2024-12-19 12:29:15.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-12-19 12:29:15.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:29:15.711Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589554 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:29:15.713Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_BusinessRule_Log.Created
-- Column: AD_BusinessRule_Log.Created
-- 2024-12-19T12:29:16.362Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589555,245,0,16,542460,'Created',TO_TIMESTAMP('2024-12-19 12:29:16.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-12-19 12:29:16.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:29:16.364Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589555 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:29:16.367Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_BusinessRule_Log.CreatedBy
-- Column: AD_BusinessRule_Log.CreatedBy
-- 2024-12-19T12:29:17.036Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589556,246,0,18,110,542460,'CreatedBy',TO_TIMESTAMP('2024-12-19 12:29:16.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-12-19 12:29:16.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:29:17.038Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589556 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:29:17.040Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_BusinessRule_Log.IsActive
-- Column: AD_BusinessRule_Log.IsActive
-- 2024-12-19T12:29:17.679Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589557,348,0,20,542460,'IsActive',TO_TIMESTAMP('2024-12-19 12:29:17.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-12-19 12:29:17.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:29:17.682Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589557 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:29:17.685Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_BusinessRule_Log.Updated
-- Column: AD_BusinessRule_Log.Updated
-- 2024-12-19T12:29:18.275Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589558,607,0,16,542460,'Updated',TO_TIMESTAMP('2024-12-19 12:29:18.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-12-19 12:29:18.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:29:18.277Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589558 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:29:18.279Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_BusinessRule_Log.UpdatedBy
-- Column: AD_BusinessRule_Log.UpdatedBy
-- 2024-12-19T12:29:19.002Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589559,608,0,18,110,542460,'UpdatedBy',TO_TIMESTAMP('2024-12-19 12:29:18.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-12-19 12:29:18.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:29:19.003Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589559 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:29:19.008Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-12-19T12:29:19.580Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583419,0,'AD_BusinessRule_Log_ID',TO_TIMESTAMP('2024-12-19 12:29:19.465000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Business Rule Log','Business Rule Log',TO_TIMESTAMP('2024-12-19 12:29:19.465000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T12:29:19.584Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583419 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- 2024-12-19T12:29:20.269Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589560,583419,0,13,542460,'AD_BusinessRule_Log_ID',TO_TIMESTAMP('2024-12-19 12:29:19.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Business Rule Log',0,0,TO_TIMESTAMP('2024-12-19 12:29:19.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:29:20.272Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589560 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:29:20.275Z
/* DDL */  select update_Column_Translation_From_AD_Element(583419) 
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- 2024-12-19T12:30:11.726Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589561,583397,0,30,542460,'XX','AD_BusinessRule_ID',TO_TIMESTAMP('2024-12-19 12:30:11.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Rule',0,0,TO_TIMESTAMP('2024-12-19 12:30:11.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:30:11.728Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589561 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:30:11.731Z
/* DDL */  select update_Column_Translation_From_AD_Element(583397) 
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_Trigger_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Trigger_ID
-- 2024-12-19T12:30:24.747Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589562,583404,0,30,542460,'XX','AD_BusinessRule_Trigger_ID',TO_TIMESTAMP('2024-12-19 12:30:24.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Rule Trigger',0,0,TO_TIMESTAMP('2024-12-19 12:30:24.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:30:24.749Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589562 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:30:24.751Z
/* DDL */  select update_Column_Translation_From_AD_Element(583404) 
;

-- Column: AD_BusinessRule_Event.AD_BusinessRule_Event_ID
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Event_ID
-- 2024-12-19T12:31:09.139Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=1,Updated=TO_TIMESTAMP('2024-12-19 12:31:09.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589540
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_Event_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Event_ID
-- 2024-12-19T12:31:15.201Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589563,583412,0,30,542460,'XX','AD_BusinessRule_Event_ID',TO_TIMESTAMP('2024-12-19 12:31:14.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Rule Event',0,0,TO_TIMESTAMP('2024-12-19 12:31:14.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:31:15.203Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589563 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:31:15.208Z
/* DDL */  select update_Column_Translation_From_AD_Element(583412) 
;

-- Column: AD_BusinessRule_Log.MsgText
-- Column: AD_BusinessRule_Log.MsgText
-- 2024-12-19T12:31:59.913Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589564,463,0,36,542460,'XX','MsgText',TO_TIMESTAMP('2024-12-19 12:31:59.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Textual Informational, Menu or Error Message','D',0,4000,'The Message Text indicates the message that will display ','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Message Text',0,0,TO_TIMESTAMP('2024-12-19 12:31:59.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:31:59.915Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589564 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:31:59.918Z
/* DDL */  select update_Column_Translation_From_AD_Element(463) 
;

-- Column: AD_BusinessRule_Log.AD_Issue_ID
-- Column: AD_BusinessRule_Log.AD_Issue_ID
-- 2024-12-19T12:32:19.191Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589565,2887,0,30,542460,'XX','AD_Issue_ID',TO_TIMESTAMP('2024-12-19 12:32:18.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Probleme',0,0,TO_TIMESTAMP('2024-12-19 12:32:18.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:32:19.194Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589565 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:32:19.196Z
/* DDL */  select update_Column_Translation_From_AD_Element(2887) 
;

-- Column: AD_BusinessRule_Log.Source_Table_ID
-- Column: AD_BusinessRule_Log.Source_Table_ID
-- 2024-12-19T12:42:59.467Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589566,577632,0,30,540750,542460,'XX','Source_Table_ID',TO_TIMESTAMP('2024-12-19 12:42:59.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Source Table',0,0,TO_TIMESTAMP('2024-12-19 12:42:59.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:42:59.469Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589566 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:42:59.472Z
/* DDL */  select update_Column_Translation_From_AD_Element(577632) 
;

-- Column: AD_BusinessRule_Log.Source_Record_ID
-- Column: AD_BusinessRule_Log.Source_Record_ID
-- 2024-12-19T12:43:10.230Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589567,583413,0,13,542460,'XX','Source_Record_ID',TO_TIMESTAMP('2024-12-19 12:43:10.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Source Record',0,0,TO_TIMESTAMP('2024-12-19 12:43:10.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T12:43:10.232Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589567 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T12:43:10.235Z
/* DDL */  select update_Column_Translation_From_AD_Element(583413) 
;

-- Column: AD_BusinessRule_Log.Module
-- Column: AD_BusinessRule_Log.Module
-- 2024-12-19T13:38:21.959Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589569,52010,0,10,542460,'XX','Module',TO_TIMESTAMP('2024-12-19 13:38:21.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,120,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Module',0,0,TO_TIMESTAMP('2024-12-19 13:38:21.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T13:38:21.960Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589569 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T13:38:21.963Z
/* DDL */  select update_Column_Translation_From_AD_Element(52010) 
;

-- 2024-12-19T13:38:59.731Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583420,0,'Target_Table_ID',TO_TIMESTAMP('2024-12-19 13:38:59.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Target Table','Target Table',TO_TIMESTAMP('2024-12-19 13:38:59.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T13:38:59.736Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583420 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-19T13:39:11.823Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583421,0,'Target_Record_ID',TO_TIMESTAMP('2024-12-19 13:39:11.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Target Record ID','Target Record ID',TO_TIMESTAMP('2024-12-19 13:39:11.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T13:39:11.825Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583421 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-12-19T13:39:14.283Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-19 13:39:14.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583421
;

-- 2024-12-19T13:39:14.296Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583421,'de_DE') 
;

-- Column: AD_BusinessRule_Log.Target_Table_ID
-- Column: AD_BusinessRule_Log.Target_Table_ID
-- 2024-12-19T13:39:24.881Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589570,583420,0,30,540750,542460,'XX','Target_Table_ID',TO_TIMESTAMP('2024-12-19 13:39:24.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Target Table',0,0,TO_TIMESTAMP('2024-12-19 13:39:24.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T13:39:24.883Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589570 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T13:39:24.885Z
/* DDL */  select update_Column_Translation_From_AD_Element(583420) 
;

-- Column: AD_BusinessRule_Log.Target_Record_ID
-- Column: AD_BusinessRule_Log.Target_Record_ID
-- 2024-12-19T13:39:45.781Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589571,583421,0,13,542460,'XX','Target_Record_ID',TO_TIMESTAMP('2024-12-19 13:39:45.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Target Record ID',0,0,TO_TIMESTAMP('2024-12-19 13:39:45.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T13:39:45.783Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589571 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T13:39:45.785Z
/* DDL */  select update_Column_Translation_From_AD_Element(583421) 
;

-- 2024-12-19T14:45:14.770Z
/* DDL */ CREATE TABLE public.AD_BusinessRule_Log (AD_BusinessRule_Event_ID NUMERIC(10), AD_BusinessRule_ID NUMERIC(10), AD_BusinessRule_Log_ID NUMERIC(10) NOT NULL, AD_BusinessRule_Trigger_ID NUMERIC(10), AD_Client_ID NUMERIC(10) NOT NULL, AD_Issue_ID NUMERIC(10), AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Module VARCHAR(120), MsgText TEXT, Source_Record_ID NUMERIC(10), Source_Table_ID NUMERIC(10), Target_Record_ID NUMERIC(10), Target_Table_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADBusinessRuleEvent_ADBusinessRuleLog FOREIGN KEY (AD_BusinessRule_Event_ID) REFERENCES public.AD_BusinessRule_Event DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ADBusinessRule_ADBusinessRuleLog FOREIGN KEY (AD_BusinessRule_ID) REFERENCES public.AD_BusinessRule DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_BusinessRule_Log_Key PRIMARY KEY (AD_BusinessRule_Log_ID), CONSTRAINT ADBusinessRuleTrigger_ADBusinessRuleLog FOREIGN KEY (AD_BusinessRule_Trigger_ID) REFERENCES public.AD_BusinessRule_Trigger DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ADIssue_ADBusinessRuleLog FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SourceTable_ADBusinessRuleLog FOREIGN KEY (Source_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED, CONSTRAINT TargetTable_ADBusinessRuleLog FOREIGN KEY (Target_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED)
;

-- Column: AD_BusinessRule_Log.DurationMillis
-- Column: AD_BusinessRule_Log.DurationMillis
-- 2024-12-19T15:38:37.020Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589572,543760,0,11,542460,'XX','DurationMillis',TO_TIMESTAMP('2024-12-19 15:38:36.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Duration (ms)',0,0,TO_TIMESTAMP('2024-12-19 15:38:36.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T15:38:37.030Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589572 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T15:38:37.038Z
/* DDL */  select update_Column_Translation_From_AD_Element(543760) 
;

-- 2024-12-19T15:38:38.238Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule_Log','ALTER TABLE public.AD_BusinessRule_Log ADD COLUMN DurationMillis NUMERIC(10)')
;

-- Column: AD_BusinessRule_Log.Level
-- Column: AD_BusinessRule_Log.Level
-- 2024-12-19T16:26:08.127Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589573,540459,0,10,542460,'XX','Level',TO_TIMESTAMP('2024-12-19 16:26:07.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','de.metas.commission_legacy',0,20,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Ebene',0,0,TO_TIMESTAMP('2024-12-19 16:26:07.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T16:26:08.136Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589573 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T16:26:08.144Z
/* DDL */  select update_Column_Translation_From_AD_Element(540459) 
;

-- 2024-12-19T16:26:08.900Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule_Log','ALTER TABLE public.AD_BusinessRule_Log ADD COLUMN Level VARCHAR(20) NOT NULL')
;

-- Column: AD_BusinessRule_Log.Level
-- Column: AD_BusinessRule_Log.Level
-- 2024-12-19T16:26:49.742Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-12-19 16:26:49.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589573
;

-- Window: Business Rule Log, InternalName=null
-- Window: Business Rule Log, InternalName=null
-- 2024-12-19T16:50:02.402Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583419,0,541840,TO_TIMESTAMP('2024-12-19 16:50:01.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Business Rule Log','N',TO_TIMESTAMP('2024-12-19 16:50:01.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2024-12-19T16:50:02.415Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541840 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-12-19T16:50:02.423Z
/* DDL */  select update_window_translation_from_ad_element(583419) 
;

-- 2024-12-19T16:50:02.450Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541840
;

-- 2024-12-19T16:50:02.459Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541840)
;

-- Tab: Business Rule Log -> Business Rule Log
-- Table: AD_BusinessRule_Log
-- Tab: Business Rule Log(541840,D) -> Business Rule Log
-- Table: AD_BusinessRule_Log
-- 2024-12-19T16:50:24.518Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583419,0,547733,542460,541840,'Y',TO_TIMESTAMP('2024-12-19 16:50:24.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_BusinessRule_Log','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Business Rule Log','N',10,0,TO_TIMESTAMP('2024-12-19 16:50:24.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:24.522Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547733 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-12-19T16:50:24.525Z
/* DDL */  select update_tab_translation_from_ad_element(583419) 
;

-- 2024-12-19T16:50:24.532Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547733)
;

-- Field: Business Rule Log -> Business Rule Log -> Mandant
-- Column: AD_BusinessRule_Log.AD_Client_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Mandant
-- Column: AD_BusinessRule_Log.AD_Client_ID
-- 2024-12-19T16:50:28.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589553,734626,0,547733,TO_TIMESTAMP('2024-12-19 16:50:28.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-12-19 16:50:28.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:28.953Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734626 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:28.957Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-12-19T16:50:31.080Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734626
;

-- 2024-12-19T16:50:31.082Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734626)
;

-- Field: Business Rule Log -> Business Rule Log -> Sektion
-- Column: AD_BusinessRule_Log.AD_Org_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Sektion
-- Column: AD_BusinessRule_Log.AD_Org_ID
-- 2024-12-19T16:50:31.278Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589554,734627,0,547733,TO_TIMESTAMP('2024-12-19 16:50:31.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-12-19 16:50:31.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:31.282Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734627 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:31.285Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-12-19T16:50:31.857Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734627
;

-- 2024-12-19T16:50:31.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734627)
;

-- Field: Business Rule Log -> Business Rule Log -> Aktiv
-- Column: AD_BusinessRule_Log.IsActive
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Aktiv
-- Column: AD_BusinessRule_Log.IsActive
-- 2024-12-19T16:50:31.997Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589557,734628,0,547733,TO_TIMESTAMP('2024-12-19 16:50:31.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-12-19 16:50:31.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:32Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734628 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:32.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-12-19T16:50:32.165Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734628
;

-- 2024-12-19T16:50:32.167Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734628)
;

-- Field: Business Rule Log -> Business Rule Log -> Business Rule Log
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Business Rule Log
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Log_ID
-- 2024-12-19T16:50:32.322Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589560,734629,0,547733,TO_TIMESTAMP('2024-12-19 16:50:32.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule Log',TO_TIMESTAMP('2024-12-19 16:50:32.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:32.324Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734629 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:32.326Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583419) 
;

-- 2024-12-19T16:50:32.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734629
;

-- 2024-12-19T16:50:32.330Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734629)
;

-- Field: Business Rule Log -> Business Rule Log -> Business Rule
-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Business Rule
-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- 2024-12-19T16:50:32.493Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589561,734630,0,547733,TO_TIMESTAMP('2024-12-19 16:50:32.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule',TO_TIMESTAMP('2024-12-19 16:50:32.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:32.495Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734630 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:32.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583397) 
;

-- 2024-12-19T16:50:32.505Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734630
;

-- 2024-12-19T16:50:32.506Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734630)
;

-- Field: Business Rule Log -> Business Rule Log -> Business Rule Trigger
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Trigger_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Business Rule Trigger
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Trigger_ID
-- 2024-12-19T16:50:32.650Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589562,734631,0,547733,TO_TIMESTAMP('2024-12-19 16:50:32.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule Trigger',TO_TIMESTAMP('2024-12-19 16:50:32.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:32.652Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734631 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:32.654Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583404) 
;

-- 2024-12-19T16:50:32.660Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734631
;

-- 2024-12-19T16:50:32.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734631)
;

-- Field: Business Rule Log -> Business Rule Log -> Business Rule Event
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Event_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Business Rule Event
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Event_ID
-- 2024-12-19T16:50:32.809Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589563,734632,0,547733,TO_TIMESTAMP('2024-12-19 16:50:32.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule Event',TO_TIMESTAMP('2024-12-19 16:50:32.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:32.811Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734632 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:32.813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583412) 
;

-- 2024-12-19T16:50:32.817Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734632
;

-- 2024-12-19T16:50:32.818Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734632)
;

-- Field: Business Rule Log -> Business Rule Log -> Message Text
-- Column: AD_BusinessRule_Log.MsgText
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Message Text
-- Column: AD_BusinessRule_Log.MsgText
-- 2024-12-19T16:50:32.988Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589564,734633,0,547733,TO_TIMESTAMP('2024-12-19 16:50:32.821000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Textual Informational, Menu or Error Message',4000,'D','The Message Text indicates the message that will display ','Y','N','N','N','N','N','N','N','Message Text',TO_TIMESTAMP('2024-12-19 16:50:32.821000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:32.991Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734633 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:32.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(463) 
;

-- 2024-12-19T16:50:33.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734633
;

-- 2024-12-19T16:50:33.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734633)
;

-- Field: Business Rule Log -> Business Rule Log -> Probleme
-- Column: AD_BusinessRule_Log.AD_Issue_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Probleme
-- Column: AD_BusinessRule_Log.AD_Issue_ID
-- 2024-12-19T16:50:33.204Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589565,734634,0,547733,TO_TIMESTAMP('2024-12-19 16:50:33.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'D','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2024-12-19 16:50:33.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:33.207Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734634 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:33.209Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2024-12-19T16:50:33.230Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734634
;

-- 2024-12-19T16:50:33.232Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734634)
;

-- Field: Business Rule Log -> Business Rule Log -> Source Table
-- Column: AD_BusinessRule_Log.Source_Table_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Source Table
-- Column: AD_BusinessRule_Log.Source_Table_ID
-- 2024-12-19T16:50:33.361Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589566,734635,0,547733,TO_TIMESTAMP('2024-12-19 16:50:33.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Source Table',TO_TIMESTAMP('2024-12-19 16:50:33.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:33.363Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734635 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:33.365Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577632) 
;

-- 2024-12-19T16:50:33.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734635
;

-- 2024-12-19T16:50:33.372Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734635)
;

-- Field: Business Rule Log -> Business Rule Log -> Source Record
-- Column: AD_BusinessRule_Log.Source_Record_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Source Record
-- Column: AD_BusinessRule_Log.Source_Record_ID
-- 2024-12-19T16:50:33.526Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589567,734636,0,547733,TO_TIMESTAMP('2024-12-19 16:50:33.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Source Record',TO_TIMESTAMP('2024-12-19 16:50:33.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:33.529Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734636 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:33.535Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583413) 
;

-- 2024-12-19T16:50:33.541Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734636
;

-- 2024-12-19T16:50:33.542Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734636)
;

-- Field: Business Rule Log -> Business Rule Log -> Module
-- Column: AD_BusinessRule_Log.Module
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Module
-- Column: AD_BusinessRule_Log.Module
-- 2024-12-19T16:50:33.663Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589569,734637,0,547733,TO_TIMESTAMP('2024-12-19 16:50:33.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,120,'D','Y','N','N','N','N','N','N','N','Module',TO_TIMESTAMP('2024-12-19 16:50:33.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:33.666Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734637 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:33.668Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(52010) 
;

-- 2024-12-19T16:50:33.673Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734637
;

-- 2024-12-19T16:50:33.674Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734637)
;

-- Field: Business Rule Log -> Business Rule Log -> Target Table
-- Column: AD_BusinessRule_Log.Target_Table_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Target Table
-- Column: AD_BusinessRule_Log.Target_Table_ID
-- 2024-12-19T16:50:33.798Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589570,734638,0,547733,TO_TIMESTAMP('2024-12-19 16:50:33.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Target Table',TO_TIMESTAMP('2024-12-19 16:50:33.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:33.801Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734638 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:33.803Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583420) 
;

-- 2024-12-19T16:50:33.808Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734638
;

-- 2024-12-19T16:50:33.809Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734638)
;

-- Field: Business Rule Log -> Business Rule Log -> Target Record ID
-- Column: AD_BusinessRule_Log.Target_Record_ID
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Target Record ID
-- Column: AD_BusinessRule_Log.Target_Record_ID
-- 2024-12-19T16:50:33.944Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589571,734639,0,547733,TO_TIMESTAMP('2024-12-19 16:50:33.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Target Record ID',TO_TIMESTAMP('2024-12-19 16:50:33.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:33.946Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734639 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:33.947Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583421) 
;

-- 2024-12-19T16:50:33.952Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734639
;

-- 2024-12-19T16:50:33.953Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734639)
;

-- Field: Business Rule Log -> Business Rule Log -> Duration (ms)
-- Column: AD_BusinessRule_Log.DurationMillis
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Duration (ms)
-- Column: AD_BusinessRule_Log.DurationMillis
-- 2024-12-19T16:50:34.079Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589572,734640,0,547733,TO_TIMESTAMP('2024-12-19 16:50:33.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Duration (ms)',TO_TIMESTAMP('2024-12-19 16:50:33.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:34.081Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734640 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:34.082Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543760) 
;

-- 2024-12-19T16:50:34.089Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734640
;

-- 2024-12-19T16:50:34.090Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734640)
;

-- Field: Business Rule Log -> Business Rule Log -> Ebene
-- Column: AD_BusinessRule_Log.Level
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Ebene
-- Column: AD_BusinessRule_Log.Level
-- 2024-12-19T16:50:34.234Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589573,734641,0,547733,TO_TIMESTAMP('2024-12-19 16:50:34.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,20,'D','Y','N','N','N','N','N','N','N','Ebene',TO_TIMESTAMP('2024-12-19 16:50:34.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:34.236Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734641 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:34.239Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540459) 
;

-- 2024-12-19T16:50:34.246Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734641
;

-- 2024-12-19T16:50:34.251Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734641)
;

-- Field: Business Rule Log -> Business Rule Log -> Erstellt
-- Column: AD_BusinessRule_Log.Created
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Erstellt
-- Column: AD_BusinessRule_Log.Created
-- 2024-12-19T16:50:48.330Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589555,734642,0,547733,TO_TIMESTAMP('2024-12-19 16:50:47.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-12-19 16:50:47.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:48.334Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734642 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:48.338Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2024-12-19T16:50:48.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734642
;

-- 2024-12-19T16:50:48.575Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734642)
;

-- Field: Business Rule Log -> Business Rule Log -> Erstellt durch
-- Column: AD_BusinessRule_Log.CreatedBy
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Erstellt durch
-- Column: AD_BusinessRule_Log.CreatedBy
-- 2024-12-19T16:50:58.344Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589556,734643,0,547733,TO_TIMESTAMP('2024-12-19 16:50:58.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2024-12-19 16:50:58.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T16:50:58.347Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734643 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T16:50:58.352Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2024-12-19T16:50:58.533Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734643
;

-- 2024-12-19T16:50:58.537Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734643)
;

-- Tab: Business Rule Log(541840,D) -> Business Rule Log(547733,D)
-- UI Section: main
-- 2024-12-19T16:51:27.698Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547733,546316,TO_TIMESTAMP('2024-12-19 16:51:27.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-19 16:51:27.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-12-19T16:51:27.702Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546316 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main
-- UI Column: 10
-- 2024-12-19T16:51:27.956Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547719,546316,TO_TIMESTAMP('2024-12-19 16:51:27.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-19 16:51:27.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main
-- UI Column: 20
-- 2024-12-19T16:51:28.087Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547720,546316,TO_TIMESTAMP('2024-12-19 16:51:27.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-12-19 16:51:27.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10
-- UI Element Group: default
-- 2024-12-19T16:51:28.303Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547719,552260,TO_TIMESTAMP('2024-12-19 16:51:28.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-12-19 16:51:28.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Module
-- Column: AD_BusinessRule_Log.Module
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Module
-- Column: AD_BusinessRule_Log.Module
-- 2024-12-19T16:53:03.951Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734637,0,547733,552260,627773,'F',TO_TIMESTAMP('2024-12-19 16:53:03.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Module',10,0,0,TO_TIMESTAMP('2024-12-19 16:53:03.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Ebene
-- Column: AD_BusinessRule_Log.Level
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Ebene
-- Column: AD_BusinessRule_Log.Level
-- 2024-12-19T16:53:15.769Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734641,0,547733,552260,627774,'F',TO_TIMESTAMP('2024-12-19 16:53:15.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Ebene',20,0,0,TO_TIMESTAMP('2024-12-19 16:53:15.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Message Text
-- Column: AD_BusinessRule_Log.MsgText
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Message Text
-- Column: AD_BusinessRule_Log.MsgText
-- 2024-12-19T16:53:29.599Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734633,0,547733,552260,627775,'F',TO_TIMESTAMP('2024-12-19 16:53:28.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Textual Informational, Menu or Error Message','The Message Text indicates the message that will display ','Y','N','Y','N','N','Message Text',30,0,0,TO_TIMESTAMP('2024-12-19 16:53:28.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Probleme
-- Column: AD_BusinessRule_Log.AD_Issue_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Probleme
-- Column: AD_BusinessRule_Log.AD_Issue_ID
-- 2024-12-19T16:53:44.457Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734634,0,547733,552260,627776,'F',TO_TIMESTAMP('2024-12-19 16:53:44.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Probleme',40,0,0,TO_TIMESTAMP('2024-12-19 16:53:44.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Duration (ms)
-- Column: AD_BusinessRule_Log.DurationMillis
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Duration (ms)
-- Column: AD_BusinessRule_Log.DurationMillis
-- 2024-12-19T16:53:52.585Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734640,0,547733,552260,627777,'F',TO_TIMESTAMP('2024-12-19 16:53:52.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Duration (ms)',50,0,0,TO_TIMESTAMP('2024-12-19 16:53:52.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20
-- UI Element Group: context
-- 2024-12-19T16:54:07.948Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547720,552261,TO_TIMESTAMP('2024-12-19 16:54:07.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','context',10,TO_TIMESTAMP('2024-12-19 16:54:07.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule
-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule
-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- 2024-12-19T16:54:23.004Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734630,0,547733,552261,627778,'F',TO_TIMESTAMP('2024-12-19 16:54:22.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Business Rule',10,0,0,TO_TIMESTAMP('2024-12-19 16:54:22.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule Trigger
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Trigger_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule Trigger
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Trigger_ID
-- 2024-12-19T16:54:29.197Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734631,0,547733,552261,627779,'F',TO_TIMESTAMP('2024-12-19 16:54:28.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Business Rule Trigger',20,0,0,TO_TIMESTAMP('2024-12-19 16:54:28.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule Event
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Event_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule Event
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Event_ID
-- 2024-12-19T16:54:35.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734632,0,547733,552261,627780,'F',TO_TIMESTAMP('2024-12-19 16:54:35.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Business Rule Event',30,0,0,TO_TIMESTAMP('2024-12-19 16:54:35.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Source Table
-- Column: AD_BusinessRule_Log.Source_Table_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Source Table
-- Column: AD_BusinessRule_Log.Source_Table_ID
-- 2024-12-19T16:54:55.647Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734635,0,547733,552261,627781,'F',TO_TIMESTAMP('2024-12-19 16:54:54.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Source Table',40,0,0,TO_TIMESTAMP('2024-12-19 16:54:54.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Source Record
-- Column: AD_BusinessRule_Log.Source_Record_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Source Record
-- Column: AD_BusinessRule_Log.Source_Record_ID
-- 2024-12-19T16:55:03.244Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734636,0,547733,552261,627782,'F',TO_TIMESTAMP('2024-12-19 16:55:01.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Source Record',50,0,0,TO_TIMESTAMP('2024-12-19 16:55:01.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Target Table
-- Column: AD_BusinessRule_Log.Target_Table_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Target Table
-- Column: AD_BusinessRule_Log.Target_Table_ID
-- 2024-12-19T16:55:11.006Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734638,0,547733,552261,627783,'F',TO_TIMESTAMP('2024-12-19 16:55:09.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Target Table',60,0,0,TO_TIMESTAMP('2024-12-19 16:55:09.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Target Record ID
-- Column: AD_BusinessRule_Log.Target_Record_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Target Record ID
-- Column: AD_BusinessRule_Log.Target_Record_ID
-- 2024-12-19T16:55:18.412Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734639,0,547733,552261,627784,'F',TO_TIMESTAMP('2024-12-19 16:55:17.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Target Record ID',70,0,0,TO_TIMESTAMP('2024-12-19 16:55:17.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10
-- UI Element Group: created
-- 2024-12-19T16:55:32.219Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547719,552262,TO_TIMESTAMP('2024-12-19 16:55:30.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','created',20,TO_TIMESTAMP('2024-12-19 16:55:30.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Erstellt
-- Column: AD_BusinessRule_Log.Created
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> created.Erstellt
-- Column: AD_BusinessRule_Log.Created
-- 2024-12-19T16:55:40.343Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734642,0,547733,552262,627785,'F',TO_TIMESTAMP('2024-12-19 16:55:40.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','N','N','Erstellt',10,0,0,TO_TIMESTAMP('2024-12-19 16:55:40.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Erstellt durch
-- Column: AD_BusinessRule_Log.CreatedBy
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> created.Erstellt durch
-- Column: AD_BusinessRule_Log.CreatedBy
-- 2024-12-19T16:55:48.528Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734643,0,547733,552262,627786,'F',TO_TIMESTAMP('2024-12-19 16:55:47.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','Y','N','N','Erstellt durch',20,0,0,TO_TIMESTAMP('2024-12-19 16:55:47.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Log -> Business Rule Log.Ebene
-- Column: AD_BusinessRule_Log.Level
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Ebene
-- Column: AD_BusinessRule_Log.Level
-- 2024-12-19T16:56:35.575Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-12-19 16:56:35.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627774
;

-- UI Element: Business Rule Log -> Business Rule Log.Module
-- Column: AD_BusinessRule_Log.Module
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Module
-- Column: AD_BusinessRule_Log.Module
-- 2024-12-19T16:56:35.590Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-12-19 16:56:35.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627773
;

-- UI Element: Business Rule Log -> Business Rule Log.Message Text
-- Column: AD_BusinessRule_Log.MsgText
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Message Text
-- Column: AD_BusinessRule_Log.MsgText
-- 2024-12-19T16:56:35.603Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-12-19 16:56:35.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627775
;

-- UI Element: Business Rule Log -> Business Rule Log.Duration (ms)
-- Column: AD_BusinessRule_Log.DurationMillis
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> default.Duration (ms)
-- Column: AD_BusinessRule_Log.DurationMillis
-- 2024-12-19T16:56:35.619Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-12-19 16:56:35.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627777
;

-- UI Element: Business Rule Log -> Business Rule Log.Erstellt
-- Column: AD_BusinessRule_Log.Created
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 10 -> created.Erstellt
-- Column: AD_BusinessRule_Log.Created
-- 2024-12-19T16:56:35.634Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-12-19 16:56:35.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627785
;

-- UI Element: Business Rule Log -> Business Rule Log.Business Rule
-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Business Rule
-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- 2024-12-19T16:56:35.662Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-12-19 16:56:35.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627778
;

-- Field: Business Rule Log -> Business Rule Log -> Ebene
-- Column: AD_BusinessRule_Log.Level
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Ebene
-- Column: AD_BusinessRule_Log.Level
-- 2024-12-19T16:56:45.846Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2024-12-19 16:56:45.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734641
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_Event_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Event_ID
-- 2024-12-19T16:57:02.623Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 16:57:02.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589563
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_ID
-- 2024-12-19T16:57:06.026Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 16:57:06.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589561
;

-- Column: AD_BusinessRule_Log.AD_BusinessRule_Trigger_ID
-- Column: AD_BusinessRule_Log.AD_BusinessRule_Trigger_ID
-- 2024-12-19T16:57:12.474Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 16:57:12.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589562
;

-- Column: AD_BusinessRule_Log.Created
-- Column: AD_BusinessRule_Log.Created
-- 2024-12-19T16:57:28.038Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 16:57:28.037000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589555
;

-- Column: AD_BusinessRule_Log.Created
-- Column: AD_BusinessRule_Log.Created
-- 2024-12-19T16:57:32.349Z
UPDATE AD_Column SET FilterOperator='B',Updated=TO_TIMESTAMP('2024-12-19 16:57:32.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589555
;

-- Column: AD_BusinessRule_Log.DurationMillis
-- Column: AD_BusinessRule_Log.DurationMillis
-- 2024-12-19T16:57:43.812Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 16:57:43.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589572
;

-- Column: AD_BusinessRule_Log.Level
-- Column: AD_BusinessRule_Log.Level
-- 2024-12-19T16:57:50.084Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 16:57:50.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589573
;

-- Column: AD_BusinessRule_Log.Module
-- Column: AD_BusinessRule_Log.Module
-- 2024-12-19T16:57:53.582Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 16:57:53.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589569
;

-- Column: AD_BusinessRule_Log.MsgText
-- Column: AD_BusinessRule_Log.MsgText
-- 2024-12-19T17:05:14.727Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 17:05:14.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589564
;

-- Column: AD_BusinessRule_Log.Source_Table_ID
-- Column: AD_BusinessRule_Log.Source_Table_ID
-- 2024-12-19T17:05:28.654Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 17:05:28.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589566
;

-- Field: Business Rule Log -> Business Rule Log -> Ebene
-- Column: AD_BusinessRule_Log.Level
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Ebene
-- Column: AD_BusinessRule_Log.Level
-- 2024-12-19T17:08:41.528Z
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2024-12-19 17:08:41.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734641
;

-- Field: Business Rule Log -> Business Rule Log -> Erstellt
-- Column: AD_BusinessRule_Log.Created
-- Field: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> Erstellt
-- Column: AD_BusinessRule_Log.Created
-- 2024-12-19T17:08:44.248Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2024-12-19 17:08:44.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734642
;

-- UI Element: Business Rule Log -> Business Rule Log.Source Table
-- Column: AD_BusinessRule_Log.Source_Table_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Source Table
-- Column: AD_BusinessRule_Log.Source_Table_ID
-- 2024-12-19T17:10:06.988Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-12-19 17:10:06.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627781
;

-- UI Element: Business Rule Log -> Business Rule Log.Source Record
-- Column: AD_BusinessRule_Log.Source_Record_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Source Record
-- Column: AD_BusinessRule_Log.Source_Record_ID
-- 2024-12-19T17:10:07.006Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-12-19 17:10:07.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627782
;

-- UI Element: Business Rule Log -> Business Rule Log.Target Table
-- Column: AD_BusinessRule_Log.Target_Table_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Target Table
-- Column: AD_BusinessRule_Log.Target_Table_ID
-- 2024-12-19T17:10:07.020Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-12-19 17:10:07.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627783
;

-- UI Element: Business Rule Log -> Business Rule Log.Target Record ID
-- Column: AD_BusinessRule_Log.Target_Record_ID
-- UI Element: Business Rule Log(541840,D) -> Business Rule Log(547733,D) -> main -> 20 -> context.Target Record ID
-- Column: AD_BusinessRule_Log.Target_Record_ID
-- 2024-12-19T17:10:07.034Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-12-19 17:10:07.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627784
;

-- Column: AD_BusinessRule_Log.Source_Record_ID
-- Column: AD_BusinessRule_Log.Source_Record_ID
-- 2024-12-19T17:10:51.204Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 17:10:51.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589567
;

-- Column: AD_BusinessRule_Log.Target_Record_ID
-- Column: AD_BusinessRule_Log.Target_Record_ID
-- 2024-12-19T17:10:55.608Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 17:10:55.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589571
;

-- Column: AD_BusinessRule_Log.Target_Table_ID
-- Column: AD_BusinessRule_Log.Target_Table_ID
-- 2024-12-19T17:11:07.041Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-19 17:11:07.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589570
;

