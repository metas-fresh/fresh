-- Table: AD_BusinessRule_Event
-- Table: AD_BusinessRule_Event
-- 2024-12-18T12:40:30.448Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('7',0,0,0,542459,'A','N',TO_TIMESTAMP('2024-12-18 12:40:30.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','D','N','Y','N','N','Y','N','N','Y','N','N',0,'Business Rule Event','NP','L','AD_BusinessRule_Event','DTI',TO_TIMESTAMP('2024-12-18 12:40:30.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2024-12-18T12:40:30.454Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542459 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-12-18T12:40:30.593Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556391,TO_TIMESTAMP('2024-12-18 12:40:30.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table AD_BusinessRule_Event',1,'Y','N','Y','Y','AD_BusinessRule_Event',1000000,TO_TIMESTAMP('2024-12-18 12:40:30.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:40:30.605Z
CREATE SEQUENCE AD_BUSINESSRULE_EVENT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_BusinessRule_Event.AD_Client_ID
-- Column: AD_BusinessRule_Event.AD_Client_ID
-- 2024-12-18T12:40:37.798Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589533,102,0,19,542459,'AD_Client_ID',TO_TIMESTAMP('2024-12-18 12:40:37.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-12-18 12:40:37.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:40:37.800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589533 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:40:37.954Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_BusinessRule_Event.AD_Org_ID
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- 2024-12-18T12:40:38.816Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589534,113,0,30,542459,'AD_Org_ID',TO_TIMESTAMP('2024-12-18 12:40:38.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-12-18 12:40:38.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:40:38.818Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589534 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:40:38.821Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_BusinessRule_Event.Created
-- Column: AD_BusinessRule_Event.Created
-- 2024-12-18T12:40:39.458Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589535,245,0,16,542459,'Created',TO_TIMESTAMP('2024-12-18 12:40:39.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-12-18 12:40:39.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:40:39.461Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589535 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:40:39.463Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_BusinessRule_Event.CreatedBy
-- Column: AD_BusinessRule_Event.CreatedBy
-- 2024-12-18T12:40:40.118Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589536,246,0,18,110,542459,'CreatedBy',TO_TIMESTAMP('2024-12-18 12:40:39.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-12-18 12:40:39.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:40:40.121Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589536 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:40:40.123Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_BusinessRule_Event.IsActive
-- Column: AD_BusinessRule_Event.IsActive
-- 2024-12-18T12:40:40.705Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589537,348,0,20,542459,'IsActive',TO_TIMESTAMP('2024-12-18 12:40:40.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-12-18 12:40:40.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:40:40.706Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589537 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:40:40.709Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_BusinessRule_Event.Updated
-- Column: AD_BusinessRule_Event.Updated
-- 2024-12-18T12:40:41.294Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589538,607,0,16,542459,'Updated',TO_TIMESTAMP('2024-12-18 12:40:41.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-12-18 12:40:41.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:40:41.295Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589538 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:40:41.297Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_BusinessRule_Event.UpdatedBy
-- Column: AD_BusinessRule_Event.UpdatedBy
-- 2024-12-18T12:40:41.950Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589539,608,0,18,110,542459,'UpdatedBy',TO_TIMESTAMP('2024-12-18 12:40:41.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-12-18 12:40:41.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:40:41.953Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589539 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:40:41.956Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-12-18T12:40:42.410Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583412,0,'AD_BusinessRule_Event_ID',TO_TIMESTAMP('2024-12-18 12:40:42.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Business Rule Event','Business Rule Event',TO_TIMESTAMP('2024-12-18 12:40:42.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:40:42.412Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583412 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Event.AD_BusinessRule_Event_ID
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Event_ID
-- 2024-12-18T12:40:43.064Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589540,583412,0,13,542459,'AD_BusinessRule_Event_ID',TO_TIMESTAMP('2024-12-18 12:40:42.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Business Rule Event',0,0,TO_TIMESTAMP('2024-12-18 12:40:42.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:40:43.066Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589540 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:40:43.068Z
/* DDL */  select update_Column_Translation_From_AD_Element(583412) 
;

-- Column: AD_BusinessRule_Event.Source_Table_ID
-- Column: AD_BusinessRule_Event.Source_Table_ID
-- 2024-12-18T12:41:08.546Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589541,577632,0,30,540750,542459,'XX','Source_Table_ID',TO_TIMESTAMP('2024-12-18 12:41:08.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Source Table',0,0,TO_TIMESTAMP('2024-12-18 12:41:08.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:41:08.550Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589541 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:41:08.553Z
/* DDL */  select update_Column_Translation_From_AD_Element(577632) 
;

-- 2024-12-18T12:42:10.396Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583413,0,'Source_Record_ID',TO_TIMESTAMP('2024-12-18 12:42:10.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Source Record','Source Record',TO_TIMESTAMP('2024-12-18 12:42:10.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:42:10.399Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583413 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Event.Source_Record_ID
-- Column: AD_BusinessRule_Event.Source_Record_ID
-- 2024-12-18T12:42:29.151Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589542,583413,0,13,542459,'XX','Source_Record_ID',TO_TIMESTAMP('2024-12-18 12:42:28.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Source Record',0,0,TO_TIMESTAMP('2024-12-18 12:42:28.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:42:29.152Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589542 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:42:29.156Z
/* DDL */  select update_Column_Translation_From_AD_Element(583413) 
;

-- Column: AD_BusinessRule_Event.Source_Record_ID
-- Column: AD_BusinessRule_Event.Source_Record_ID
-- 2024-12-18T12:42:37.352Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-12-18 12:42:37.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589542
;

-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- 2024-12-18T12:42:51.666Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589543,583397,0,30,542459,'XX','AD_BusinessRule_ID',TO_TIMESTAMP('2024-12-18 12:42:51.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Business Rule',0,0,TO_TIMESTAMP('2024-12-18 12:42:51.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:42:51.668Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589543 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:42:51.670Z
/* DDL */  select update_Column_Translation_From_AD_Element(583397) 
;

-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_ID
-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_ID
-- 2024-12-18T12:44:27.136Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2024-12-18 12:44:27.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589526
;

-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- 2024-12-18T12:44:38.569Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-12-18 12:44:38.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589527
;

-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- 2024-12-18T12:44:46.455Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589544,583404,0,30,542459,'XX','AD_BusinessRule_Trigger_ID',TO_TIMESTAMP('2024-12-18 12:44:46.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Business Rule Trigger',0,0,TO_TIMESTAMP('2024-12-18 12:44:46.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:44:46.456Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589544 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:44:46.460Z
/* DDL */  select update_Column_Translation_From_AD_Element(583404) 
;

-- Column: AD_BusinessRule_Event.Processed
-- Column: AD_BusinessRule_Event.Processed
-- 2024-12-18T12:45:49.164Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589545,1047,0,20,542459,'XX','Processed',TO_TIMESTAMP('2024-12-18 12:45:48.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2024-12-18 12:45:48.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:45:49.165Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589545 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:45:49.169Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- Column: AD_BusinessRule_Event.ProcessingTag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- 2024-12-18T12:46:04.401Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589546,542935,0,10,542459,'XX','ProcessingTag',TO_TIMESTAMP('2024-12-18 12:46:04.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,60,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Processing Tag',0,0,TO_TIMESTAMP('2024-12-18 12:46:04.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T12:46:04.403Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589546 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T12:46:04.405Z
/* DDL */  select update_Column_Translation_From_AD_Element(542935) 
;

-- 2024-12-18T12:46:40.185Z
/* DDL */ CREATE TABLE public.AD_BusinessRule_Event (AD_BusinessRule_Event_ID NUMERIC(10) NOT NULL, AD_BusinessRule_ID NUMERIC(10) NOT NULL, AD_BusinessRule_Trigger_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')) NOT NULL, ProcessingTag VARCHAR(60), Source_Record_ID NUMERIC(10) NOT NULL, Source_Table_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT AD_BusinessRule_Event_Key PRIMARY KEY (AD_BusinessRule_Event_ID), CONSTRAINT ADBusinessRule_ADBusinessRuleEvent FOREIGN KEY (AD_BusinessRule_ID) REFERENCES public.AD_BusinessRule DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ADBusinessRuleTrigger_ADBusinessRuleEvent FOREIGN KEY (AD_BusinessRule_Trigger_ID) REFERENCES public.AD_BusinessRule_Trigger DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SourceTable_ADBusinessRuleEvent FOREIGN KEY (Source_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED)
;

-- Window: Business Rule Event, InternalName=businessRuleEvent
-- Window: Business Rule Event, InternalName=businessRuleEvent
-- 2024-12-18T12:47:19.907Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583412,0,541838,TO_TIMESTAMP('2024-12-18 12:47:19.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','businessRuleEvent','Y','N','N','N','N','N','N','Y','Business Rule Event','N',TO_TIMESTAMP('2024-12-18 12:47:19.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2024-12-18T12:47:19.911Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541838 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-12-18T12:47:19.915Z
/* DDL */  select update_window_translation_from_ad_element(583412) 
;

-- 2024-12-18T12:47:19.931Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541838
;

-- 2024-12-18T12:47:19.936Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541838)
;

-- Tab: Business Rule Event -> Business Rule Event
-- Table: AD_BusinessRule_Event
-- Tab: Business Rule Event(541838,D) -> Business Rule Event
-- Table: AD_BusinessRule_Event
-- 2024-12-18T12:48:00.318Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583412,0,547702,542459,541838,'Y',TO_TIMESTAMP('2024-12-18 12:48:00.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_BusinessRule_Event','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Business Rule Event','N',10,0,TO_TIMESTAMP('2024-12-18 12:48:00.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:00.320Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547702 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-12-18T12:48:00.323Z
/* DDL */  select update_tab_translation_from_ad_element(583412) 
;

-- 2024-12-18T12:48:00.327Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547702)
;

-- Field: Business Rule Event -> Business Rule Event -> Mandant
-- Column: AD_BusinessRule_Event.AD_Client_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Mandant
-- Column: AD_BusinessRule_Event.AD_Client_ID
-- 2024-12-18T12:48:07.821Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589533,734122,0,547702,TO_TIMESTAMP('2024-12-18 12:48:07.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-12-18 12:48:07.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:07.825Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734122 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:07.827Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-12-18T12:48:09.347Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734122
;

-- 2024-12-18T12:48:09.349Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734122)
;

-- Field: Business Rule Event -> Business Rule Event -> Sektion
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Sektion
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- 2024-12-18T12:48:09.470Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589534,734123,0,547702,TO_TIMESTAMP('2024-12-18 12:48:09.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-12-18 12:48:09.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:09.473Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734123 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:09.474Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-12-18T12:48:09.911Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734123
;

-- 2024-12-18T12:48:09.911Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734123)
;

-- Field: Business Rule Event -> Business Rule Event -> Aktiv
-- Column: AD_BusinessRule_Event.IsActive
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Aktiv
-- Column: AD_BusinessRule_Event.IsActive
-- 2024-12-18T12:48:10.036Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589537,734124,0,547702,TO_TIMESTAMP('2024-12-18 12:48:09.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-12-18 12:48:09.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:10.037Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:10.039Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-12-18T12:48:10.161Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734124
;

-- 2024-12-18T12:48:10.162Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734124)
;

-- Field: Business Rule Event -> Business Rule Event -> Business Rule Event
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Event_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Business Rule Event
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Event_ID
-- 2024-12-18T12:48:10.284Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589540,734125,0,547702,TO_TIMESTAMP('2024-12-18 12:48:10.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule Event',TO_TIMESTAMP('2024-12-18 12:48:10.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:10.287Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734125 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:10.289Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583412) 
;

-- 2024-12-18T12:48:10.294Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734125
;

-- 2024-12-18T12:48:10.294Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734125)
;

-- Field: Business Rule Event -> Business Rule Event -> Source Table
-- Column: AD_BusinessRule_Event.Source_Table_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Source Table
-- Column: AD_BusinessRule_Event.Source_Table_ID
-- 2024-12-18T12:48:10.416Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589541,734126,0,547702,TO_TIMESTAMP('2024-12-18 12:48:10.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Source Table',TO_TIMESTAMP('2024-12-18 12:48:10.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:10.419Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734126 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:10.422Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577632) 
;

-- 2024-12-18T12:48:10.427Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734126
;

-- 2024-12-18T12:48:10.427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734126)
;

-- Field: Business Rule Event -> Business Rule Event -> Source Record
-- Column: AD_BusinessRule_Event.Source_Record_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Source Record
-- Column: AD_BusinessRule_Event.Source_Record_ID
-- 2024-12-18T12:48:10.546Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589542,734127,0,547702,TO_TIMESTAMP('2024-12-18 12:48:10.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Source Record',TO_TIMESTAMP('2024-12-18 12:48:10.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:10.547Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734127 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:10.549Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583413) 
;

-- 2024-12-18T12:48:10.555Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734127
;

-- 2024-12-18T12:48:10.556Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734127)
;

-- Field: Business Rule Event -> Business Rule Event -> Business Rule
-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Business Rule
-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- 2024-12-18T12:48:10.669Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589543,734128,0,547702,TO_TIMESTAMP('2024-12-18 12:48:10.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule',TO_TIMESTAMP('2024-12-18 12:48:10.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:10.671Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:10.673Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583397) 
;

-- 2024-12-18T12:48:10.677Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734128
;

-- 2024-12-18T12:48:10.677Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734128)
;

-- Field: Business Rule Event -> Business Rule Event -> Business Rule Trigger
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Business Rule Trigger
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- 2024-12-18T12:48:10.786Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589544,734129,0,547702,TO_TIMESTAMP('2024-12-18 12:48:10.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule Trigger',TO_TIMESTAMP('2024-12-18 12:48:10.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:10.788Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734129 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:10.791Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583404) 
;

-- 2024-12-18T12:48:10.794Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734129
;

-- 2024-12-18T12:48:10.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734129)
;

-- Field: Business Rule Event -> Business Rule Event -> Verarbeitet
-- Column: AD_BusinessRule_Event.Processed
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Verarbeitet
-- Column: AD_BusinessRule_Event.Processed
-- 2024-12-18T12:48:10.917Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589545,734130,0,547702,TO_TIMESTAMP('2024-12-18 12:48:10.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2024-12-18 12:48:10.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:10.919Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:10.921Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2024-12-18T12:48:10.949Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734130
;

-- 2024-12-18T12:48:10.950Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734130)
;

-- Field: Business Rule Event -> Business Rule Event -> Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- 2024-12-18T12:48:11.061Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589546,734131,0,547702,TO_TIMESTAMP('2024-12-18 12:48:10.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Processing Tag',TO_TIMESTAMP('2024-12-18 12:48:10.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:48:11.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734131 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:48:11.065Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542935) 
;

-- 2024-12-18T12:48:11.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734131
;

-- 2024-12-18T12:48:11.069Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734131)
;

-- Tab: Business Rule Event(541838,D) -> Business Rule Event(547702,D)
-- UI Section: main
-- 2024-12-18T12:48:28.818Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547702,546285,TO_TIMESTAMP('2024-12-18 12:48:28.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-18 12:48:28.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-12-18T12:48:28.820Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546285 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main
-- UI Column: 10
-- 2024-12-18T12:48:28.992Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547685,546285,TO_TIMESTAMP('2024-12-18 12:48:28.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-18 12:48:28.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main
-- UI Column: 20
-- 2024-12-18T12:48:29.116Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547686,546285,TO_TIMESTAMP('2024-12-18 12:48:28.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-12-18 12:48:28.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10
-- UI Element Group: default
-- 2024-12-18T12:48:29.287Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547685,552203,TO_TIMESTAMP('2024-12-18 12:48:29.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-12-18 12:48:29.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Business Rule
-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> default.Business Rule
-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- 2024-12-18T12:49:27.166Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734128,0,547702,552203,627422,'F',TO_TIMESTAMP('2024-12-18 12:49:26.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Business Rule',10,0,0,TO_TIMESTAMP('2024-12-18 12:49:26.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Business Rule Trigger
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> default.Business Rule Trigger
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- 2024-12-18T12:49:33.249Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734129,0,547702,552203,627423,'F',TO_TIMESTAMP('2024-12-18 12:49:33.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Business Rule Trigger',20,0,0,TO_TIMESTAMP('2024-12-18 12:49:33.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10
-- UI Element Group: source record
-- 2024-12-18T12:49:43.548Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547685,552204,TO_TIMESTAMP('2024-12-18 12:49:43.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','source record',20,TO_TIMESTAMP('2024-12-18 12:49:43.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Source Table
-- Column: AD_BusinessRule_Event.Source_Table_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> source record.Source Table
-- Column: AD_BusinessRule_Event.Source_Table_ID
-- 2024-12-18T12:49:51.125Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734126,0,547702,552204,627424,'F',TO_TIMESTAMP('2024-12-18 12:49:50.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Source Table',10,0,0,TO_TIMESTAMP('2024-12-18 12:49:50.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Source Record
-- Column: AD_BusinessRule_Event.Source_Record_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> source record.Source Record
-- Column: AD_BusinessRule_Event.Source_Record_ID
-- 2024-12-18T12:49:56.603Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734127,0,547702,552204,627425,'F',TO_TIMESTAMP('2024-12-18 12:49:56.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Source Record',20,0,0,TO_TIMESTAMP('2024-12-18 12:49:56.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20
-- UI Element Group: flags
-- 2024-12-18T12:50:16.530Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547686,552205,TO_TIMESTAMP('2024-12-18 12:50:16.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2024-12-18 12:50:16.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Aktiv
-- Column: AD_BusinessRule_Event.IsActive
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_BusinessRule_Event.IsActive
-- 2024-12-18T12:50:23.989Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734124,0,547702,552205,627426,'F',TO_TIMESTAMP('2024-12-18 12:50:23.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-12-18 12:50:23.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20
-- UI Element Group: status
-- 2024-12-18T12:50:29.728Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547686,552206,TO_TIMESTAMP('2024-12-18 12:50:29.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','status',20,TO_TIMESTAMP('2024-12-18 12:50:29.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Verarbeitet
-- Column: AD_BusinessRule_Event.Processed
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Verarbeitet
-- Column: AD_BusinessRule_Event.Processed
-- 2024-12-18T12:50:39.593Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734130,0,547702,552206,627427,'F',TO_TIMESTAMP('2024-12-18 12:50:39.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2024-12-18 12:50:39.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- 2024-12-18T12:50:46.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734131,0,547702,552206,627428,'F',TO_TIMESTAMP('2024-12-18 12:50:45.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Processing Tag',20,0,0,TO_TIMESTAMP('2024-12-18 12:50:45.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20
-- UI Element Group: org&client
-- 2024-12-18T12:50:53.311Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547686,552207,TO_TIMESTAMP('2024-12-18 12:50:53.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org&client',30,TO_TIMESTAMP('2024-12-18 12:50:53.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Sektion
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- 2024-12-18T12:51:01.284Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734123,0,547702,552207,627429,'F',TO_TIMESTAMP('2024-12-18 12:51:01.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2024-12-18 12:51:01.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Mandant
-- Column: AD_BusinessRule_Event.AD_Client_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> org&client.Mandant
-- Column: AD_BusinessRule_Event.AD_Client_ID
-- 2024-12-18T12:51:07.980Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734122,0,547702,552207,627430,'F',TO_TIMESTAMP('2024-12-18 12:51:07.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2024-12-18 12:51:07.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Business Rule
-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> default.Business Rule
-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- 2024-12-18T12:51:31.380Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-12-18 12:51:31.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627422
;

-- UI Element: Business Rule Event -> Business Rule Event.Business Rule Trigger
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> default.Business Rule Trigger
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- 2024-12-18T12:51:31.387Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-12-18 12:51:31.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627423
;

-- UI Element: Business Rule Event -> Business Rule Event.Source Table
-- Column: AD_BusinessRule_Event.Source_Table_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> source record.Source Table
-- Column: AD_BusinessRule_Event.Source_Table_ID
-- 2024-12-18T12:51:31.393Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-12-18 12:51:31.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627424
;

-- UI Element: Business Rule Event -> Business Rule Event.Source Record
-- Column: AD_BusinessRule_Event.Source_Record_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> source record.Source Record
-- Column: AD_BusinessRule_Event.Source_Record_ID
-- 2024-12-18T12:51:31.400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-12-18 12:51:31.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627425
;

-- UI Element: Business Rule Event -> Business Rule Event.Verarbeitet
-- Column: AD_BusinessRule_Event.Processed
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Verarbeitet
-- Column: AD_BusinessRule_Event.Processed
-- 2024-12-18T12:51:31.406Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-12-18 12:51:31.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627427
;

-- UI Element: Business Rule Event -> Business Rule Event.Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- 2024-12-18T12:51:31.413Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-12-18 12:51:31.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627428
;

-- UI Element: Business Rule Event -> Business Rule Event.Sektion
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- 2024-12-18T12:51:31.419Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-12-18 12:51:31.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627429
;

-- Field: Business Rule Event -> Business Rule Event -> Business Rule Event
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Event_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Business Rule Event
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Event_ID
-- 2024-12-18T12:51:48.426Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2024-12-18 12:51:48.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734125
;

-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- Column: AD_BusinessRule_Event.AD_BusinessRule_ID
-- 2024-12-18T12:52:03.652Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-18 12:52:03.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589543
;

-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- Column: AD_BusinessRule_Event.AD_BusinessRule_Trigger_ID
-- 2024-12-18T12:52:11.374Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-18 12:52:11.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589544
;

-- Column: AD_BusinessRule_Event.Processed
-- Column: AD_BusinessRule_Event.Processed
-- 2024-12-18T12:52:30.840Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-18 12:52:30.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589545
;

-- Column: AD_BusinessRule_Event.ProcessingTag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- 2024-12-18T12:52:41.271Z
UPDATE AD_Column SET FilterOperator='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-18 12:52:41.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589546
;

-- Column: AD_BusinessRule_Event.Source_Table_ID
-- Column: AD_BusinessRule_Event.Source_Table_ID
-- 2024-12-18T12:52:47.118Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-18 12:52:47.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589541
;

-- Field: Business Rule Event -> Business Rule Event -> Erstellt
-- Column: AD_BusinessRule_Event.Created
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Erstellt
-- Column: AD_BusinessRule_Event.Created
-- 2024-12-18T12:52:59.979Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589535,734132,0,547702,TO_TIMESTAMP('2024-12-18 12:52:59.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-12-18 12:52:59.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:52:59.981Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734132 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:52:59.982Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2024-12-18T12:53:00.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734132
;

-- 2024-12-18T12:53:00.140Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734132)
;

-- Field: Business Rule Event -> Business Rule Event -> Erstellt durch
-- Column: AD_BusinessRule_Event.CreatedBy
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Erstellt durch
-- Column: AD_BusinessRule_Event.CreatedBy
-- 2024-12-18T12:53:00.283Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589536,734133,0,547702,TO_TIMESTAMP('2024-12-18 12:53:00.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2024-12-18 12:53:00.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:53:00.285Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734133 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:53:00.287Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2024-12-18T12:53:00.375Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734133
;

-- 2024-12-18T12:53:00.376Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734133)
;

-- Field: Business Rule Event -> Business Rule Event -> Aktualisiert
-- Column: AD_BusinessRule_Event.Updated
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Aktualisiert
-- Column: AD_BusinessRule_Event.Updated
-- 2024-12-18T12:53:00.495Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589538,734134,0,547702,TO_TIMESTAMP('2024-12-18 12:53:00.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2024-12-18 12:53:00.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:53:00.497Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734134 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:53:00.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2024-12-18T12:53:00.582Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734134
;

-- 2024-12-18T12:53:00.583Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734134)
;

-- Field: Business Rule Event -> Business Rule Event -> Aktualisiert durch
-- Column: AD_BusinessRule_Event.UpdatedBy
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Aktualisiert durch
-- Column: AD_BusinessRule_Event.UpdatedBy
-- 2024-12-18T12:53:00.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589539,734135,0,547702,TO_TIMESTAMP('2024-12-18 12:53:00.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2024-12-18 12:53:00.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:53:00.712Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T12:53:00.713Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2024-12-18T12:53:00.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734135
;

-- 2024-12-18T12:53:00.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734135)
;

-- UI Column: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10
-- UI Element Group: created
-- 2024-12-18T12:53:22.176Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547685,552208,TO_TIMESTAMP('2024-12-18 12:53:22.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','created',30,TO_TIMESTAMP('2024-12-18 12:53:22.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Erstellt
-- Column: AD_BusinessRule_Event.Created
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> created.Erstellt
-- Column: AD_BusinessRule_Event.Created
-- 2024-12-18T12:53:33.744Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734132,0,547702,552208,627431,'F',TO_TIMESTAMP('2024-12-18 12:53:33.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','N','N','Erstellt',10,0,0,TO_TIMESTAMP('2024-12-18 12:53:33.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Erstellt durch
-- Column: AD_BusinessRule_Event.CreatedBy
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 10 -> created.Erstellt durch
-- Column: AD_BusinessRule_Event.CreatedBy
-- 2024-12-18T12:53:40.314Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734133,0,547702,552208,627432,'F',TO_TIMESTAMP('2024-12-18 12:53:40.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','Y','N','N','Erstellt durch',20,0,0,TO_TIMESTAMP('2024-12-18 12:53:40.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: Business Rule Event
-- Action Type: W
-- Window: Business Rule Event(541838,D)
-- 2024-12-18T12:54:30.665Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583412,542189,0,541838,TO_TIMESTAMP('2024-12-18 12:54:30.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','businessRuleEvent','Y','N','N','N','N','Business Rule Event',TO_TIMESTAMP('2024-12-18 12:54:30.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T12:54:30.667Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542189 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-12-18T12:54:30.671Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542189, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542189)
;

-- 2024-12-18T12:54:30.689Z
/* DDL */  select update_menu_translation_from_ad_element(583412) 
;

-- Reordering children of `Business Rules`
-- Node name: `Business Rule (AD_BusinessRule)`
-- 2024-12-18T12:54:31.270Z
UPDATE AD_TreeNodeMM SET Parent_ID=542187, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542188 AND AD_Tree_ID=10
;

-- Node name: `Business Rule Event`
-- 2024-12-18T12:54:31.273Z
UPDATE AD_TreeNodeMM SET Parent_ID=542187, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542189 AND AD_Tree_ID=10
;






































































ALTER TABLE ad_businessrule_event
    ALTER COLUMN ad_businessrule_event_id
        SET DEFAULT nextval('ad_businessrule_event_seq');


drop index if exists ad_businessrule_event_processed;
drop index if exists ad_businessrule_event_processingtag;
drop index if exists ad_businessrule_event_uq;

create index ad_businessrule_event_processed on ad_businessrule_event(processed);

create index ad_businessrule_event_processingtag on ad_businessrule_event(processingtag);

create unique index ad_businessrule_event_uq on ad_businessrule_event (ad_businessrule_id, ad_businessrule_trigger_id, source_table_id, source_record_id)
where processed='N' and processingtag is null;



































