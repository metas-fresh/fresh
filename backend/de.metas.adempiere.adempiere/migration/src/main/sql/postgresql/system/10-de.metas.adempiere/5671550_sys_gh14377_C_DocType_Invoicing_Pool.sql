-- 2023-01-09T15:31:47.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542277,'N',TO_TIMESTAMP('2023-01-09 17:31:47','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Invoicing Pool','NP','L','C_DocType_Invoicing_Pool','DTI',TO_TIMESTAMP('2023-01-09 17:31:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T15:31:47.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542277 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-01-09T15:31:47.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,
                         StartNo,Updated,UpdatedBy) 
VALUES (0,0,556188,TO_TIMESTAMP('2023-01-09 17:31:47','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_DocType_Invoicing_Pool',1,'Y','N','Y','Y','C_DocType_Invoicing_Pool',
        1000000,TO_TIMESTAMP('2023-01-09 17:31:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T15:31:47.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE C_DOCTYPE_INVOICING_POOL_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2023-01-09T15:32:02.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585455,102,0,19,542277,'AD_Client_ID',TO_TIMESTAMP('2023-01-09 17:32:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-01-09 17:32:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T15:32:02.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585455 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T15:32:02.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2023-01-09T15:32:03.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585456,113,0,30,542277,'AD_Org_ID',TO_TIMESTAMP('2023-01-09 17:32:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-01-09 17:32:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T15:32:03.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585456 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T15:32:03.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2023-01-09T15:32:03.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585457,245,0,16,542277,'Created',TO_TIMESTAMP('2023-01-09 17:32:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-01-09 17:32:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T15:32:03.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585457 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T15:32:03.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2023-01-09T15:32:04.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585458,246,0,18,110,542277,'CreatedBy',TO_TIMESTAMP('2023-01-09 17:32:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-01-09 17:32:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T15:32:04.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585458 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T15:32:04.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2023-01-09T15:32:04.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585459,348,0,20,542277,'IsActive',TO_TIMESTAMP('2023-01-09 17:32:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-01-09 17:32:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T15:32:04.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585459 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T15:32:04.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2023-01-09T15:32:05.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585460,607,0,16,542277,'Updated',TO_TIMESTAMP('2023-01-09 17:32:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-01-09 17:32:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T15:32:05.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585460 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T15:32:05.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2023-01-09T15:32:05.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585461,608,0,18,110,542277,'UpdatedBy',TO_TIMESTAMP('2023-01-09 17:32:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-01-09 17:32:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T15:32:05.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585461 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T15:32:05.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-01-09T15:32:06.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581911,0,'C_DocType_Invoicing_Pool_ID',TO_TIMESTAMP('2023-01-09 17:32:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoicing Pool','Invoicing Pool',TO_TIMESTAMP('2023-01-09 17:32:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T15:32:06.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581911 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-09T15:32:06.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585462,581911,0,13,542277,'C_DocType_Invoicing_Pool_ID',TO_TIMESTAMP('2023-01-09 17:32:06','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Invoicing Pool',0,0,TO_TIMESTAMP('2023-01-09 17:32:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T15:32:06.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585462 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T15:32:06.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581911) 
;

-- 2023-01-09T16:04:17.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581912,0,'Positive_Amt_C_DocType_ID',TO_TIMESTAMP('2023-01-09 18:04:17','YYYY-MM-DD HH24:MI:SS'),100,'DocType to use for resulting document when grand total of invoices in the pool is positive','D','Y','Positive_Amt_C_DocType_ID','Positive_Amt_C_DocType_ID',TO_TIMESTAMP('2023-01-09 18:04:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:04:17.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581912 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-09T16:04:23.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist',Updated=TO_TIMESTAMP('2023-01-09 18:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581912 AND AD_Language='de_CH'
;

-- 2023-01-09T16:04:23.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581912,'de_CH') 
;

-- 2023-01-09T16:04:25.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist',Updated=TO_TIMESTAMP('2023-01-09 18:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581912 AND AD_Language='de_DE'
;

-- 2023-01-09T16:04:25.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581912,'de_DE') 
;

-- 2023-01-09T16:04:25.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581912,'de_DE') 
;

-- 2023-01-09T16:04:25.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Positive_Amt_C_DocType_ID', Name='Positive_Amt_C_DocType_ID', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL WHERE AD_Element_ID=581912
;

-- 2023-01-09T16:04:25.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Positive_Amt_C_DocType_ID', Name='Positive_Amt_C_DocType_ID', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL, AD_Element_ID=581912 WHERE UPPER(ColumnName)='POSITIVE_AMT_C_DOCTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-09T16:04:25.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Positive_Amt_C_DocType_ID', Name='Positive_Amt_C_DocType_ID', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL WHERE AD_Element_ID=581912 AND IsCentrallyMaintained='Y'
;

-- 2023-01-09T16:04:25.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Positive_Amt_C_DocType_ID', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581912) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581912)
;

-- 2023-01-09T16:04:25.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Positive_Amt_C_DocType_ID', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581912
;

-- 2023-01-09T16:04:25.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Positive_Amt_C_DocType_ID', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL WHERE AD_Element_ID = 581912
;

-- 2023-01-09T16:04:25.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Positive_Amt_C_DocType_ID', Description = 'DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581912
;

-- 2023-01-09T16:04:28.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist',Updated=TO_TIMESTAMP('2023-01-09 18:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581912 AND AD_Language='nl_NL'
;

-- 2023-01-09T16:04:28.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581912,'nl_NL') 
;

-- 2023-01-09T16:05:09.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581913,0,'Negative_Amt_C_DocType_ID',TO_TIMESTAMP('2023-01-09 18:05:09','YYYY-MM-DD HH24:MI:SS'),100,'DocType to use for resulting document when grand total of invoices in the pool is negative','D','Y','Negative_Amt_C_DocType_ID','Negative_Amt_C_DocType_ID',TO_TIMESTAMP('2023-01-09 18:05:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:05:09.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581913 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-09T16:05:15.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist',Updated=TO_TIMESTAMP('2023-01-09 18:05:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='nl_NL'
;

-- 2023-01-09T16:05:15.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'nl_NL') 
;

-- 2023-01-09T16:05:18.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist',Updated=TO_TIMESTAMP('2023-01-09 18:05:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='de_DE'
;

-- 2023-01-09T16:05:18.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'de_DE') 
;

-- 2023-01-09T16:05:18.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581913,'de_DE') 
;

-- 2023-01-09T16:05:18.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID=581913
;

-- 2023-01-09T16:05:18.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL, AD_Element_ID=581913 WHERE UPPER(ColumnName)='NEGATIVE_AMT_C_DOCTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-09T16:05:18.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID=581913 AND IsCentrallyMaintained='Y'
;

-- 2023-01-09T16:05:18.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581913) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581913)
;

-- 2023-01-09T16:05:18.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:05:18.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:05:18.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Negative_Amt_C_DocType_ID', Description = 'DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:05:20.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist',Updated=TO_TIMESTAMP('2023-01-09 18:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='de_CH'
;

-- 2023-01-09T16:05:20.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'de_CH') 
;

-- 2023-01-09T16:07:13.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585463,581912,0,18,170,542277,540304,'Positive_Amt_C_DocType_ID',TO_TIMESTAMP('2023-01-09 18:07:13','YYYY-MM-DD HH24:MI:SS'),100,'N','DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Positive_Amt_C_DocType_ID',0,0,TO_TIMESTAMP('2023-01-09 18:07:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T16:07:13.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585463 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T16:07:13.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581912) 
;

-- 2023-01-09T16:07:15.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_DocType_Invoicing_Pool (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_DocType_Invoicing_Pool_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Positive_Amt_C_DocType_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_DocType_Invoicing_Pool_Key PRIMARY KEY (C_DocType_Invoicing_Pool_ID), CONSTRAINT PositiveAmtCDocType_CDocTypeInvoicingPool FOREIGN KEY (Positive_Amt_C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED)
;

-- 2023-01-09T16:08:03.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585464,581913,0,18,170,542277,540304,'Negative_Amt_C_DocType_ID',TO_TIMESTAMP('2023-01-09 18:08:03','YYYY-MM-DD HH24:MI:SS'),100,'N','DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Negative_Amt_C_DocType_ID',0,0,TO_TIMESTAMP('2023-01-09 18:08:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T16:08:03.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585464 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T16:08:03.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581913) 
;

-- 2023-01-09T16:08:05.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_DocType_Invoicing_Pool','ALTER TABLE public.C_DocType_Invoicing_Pool ADD COLUMN Negative_Amt_C_DocType_ID NUMERIC(10)')
;

-- 2023-01-09T16:08:05.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_DocType_Invoicing_Pool ADD CONSTRAINT NegativeAmtCDocType_CDocTypeInvoicingPool FOREIGN KEY (Negative_Amt_C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- 2023-01-09T16:08:19.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-09 18:08:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585464
;

-- 2023-01-09T16:08:20.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doctype_invoicing_pool','Negative_Amt_C_DocType_ID','NUMERIC(10)',null,null)
;

-- 2023-01-09T16:08:30.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-09 18:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585463
;

-- 2023-01-09T16:08:31.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doctype_invoicing_pool','Positive_Amt_C_DocType_ID','NUMERIC(10)',null,null)
;

-- 2023-01-09T16:10:46.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Positive Amount Document Type', PrintName='Positive Amount Document Type',Updated=TO_TIMESTAMP('2023-01-09 18:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581912 AND AD_Language='en_US'
;

-- 2023-01-09T16:10:46.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581912,'en_US') 
;

-- 2023-01-09T16:10:52.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Positiver Betrag Belegart', PrintName='Positiver Betrag Belegart',Updated=TO_TIMESTAMP('2023-01-09 18:10:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581912 AND AD_Language='de_CH'
;

-- 2023-01-09T16:10:52.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581912,'de_CH') 
;

-- 2023-01-09T16:10:54.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Positiver Betrag Belegart', PrintName='Positiver Betrag Belegart',Updated=TO_TIMESTAMP('2023-01-09 18:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581912 AND AD_Language='de_DE'
;

-- 2023-01-09T16:10:54.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581912,'de_DE') 
;

-- 2023-01-09T16:10:54.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581912,'de_DE') 
;

-- 2023-01-09T16:10:54.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Positive_Amt_C_DocType_ID', Name='Positiver Betrag Belegart', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL WHERE AD_Element_ID=581912
;

-- 2023-01-09T16:10:54.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Positive_Amt_C_DocType_ID', Name='Positiver Betrag Belegart', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL, AD_Element_ID=581912 WHERE UPPER(ColumnName)='POSITIVE_AMT_C_DOCTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-09T16:10:54.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Positive_Amt_C_DocType_ID', Name='Positiver Betrag Belegart', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL WHERE AD_Element_ID=581912 AND IsCentrallyMaintained='Y'
;

-- 2023-01-09T16:10:54.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Positiver Betrag Belegart', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581912) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581912)
;

-- 2023-01-09T16:10:54.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Positiver Betrag Belegart', Name='Positiver Betrag Belegart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581912)
;

-- 2023-01-09T16:10:54.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Positiver Betrag Belegart', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581912
;

-- 2023-01-09T16:10:54.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Positiver Betrag Belegart', Description='DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', Help=NULL WHERE AD_Element_ID = 581912
;

-- 2023-01-09T16:10:54.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Positiver Betrag Belegart', Description = 'DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581912
;

-- 2023-01-09T16:10:57.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Positiver Betrag Belegart', PrintName='Positiver Betrag Belegart',Updated=TO_TIMESTAMP('2023-01-09 18:10:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581912 AND AD_Language='nl_NL'
;

-- 2023-01-09T16:10:57.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581912,'nl_NL') 
;

-- 2023-01-09T16:11:27.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Negative Amount Document Type', PrintName='Negative Amount Document Type',Updated=TO_TIMESTAMP('2023-01-09 18:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='en_US'
;

-- 2023-01-09T16:11:27.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'en_US') 
;

-- 2023-01-09T16:11:32.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Negativer Betrag Belegart',Updated=TO_TIMESTAMP('2023-01-09 18:11:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='de_CH'
;

-- 2023-01-09T16:11:32.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'de_CH') 
;

-- 2023-01-09T16:11:33.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Negativer Betrag Belegart',Updated=TO_TIMESTAMP('2023-01-09 18:11:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='de_DE'
;

-- 2023-01-09T16:11:33.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'de_DE') 
;

-- 2023-01-09T16:11:33.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581913,'de_DE') 
;

-- 2023-01-09T16:11:33.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='Negativer Betrag Belegart', Help=NULL WHERE AD_Element_ID=581913
;

-- 2023-01-09T16:11:33.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='Negativer Betrag Belegart', Help=NULL, AD_Element_ID=581913 WHERE UPPER(ColumnName)='NEGATIVE_AMT_C_DOCTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-09T16:11:33.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='Negativer Betrag Belegart', Help=NULL WHERE AD_Element_ID=581913 AND IsCentrallyMaintained='Y'
;

-- 2023-01-09T16:11:33.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Negative_Amt_C_DocType_ID', Description='Negativer Betrag Belegart', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581913) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581913)
;

-- 2023-01-09T16:11:33.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Negative_Amt_C_DocType_ID', Description='Negativer Betrag Belegart', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:11:33.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Negative_Amt_C_DocType_ID', Description='Negativer Betrag Belegart', Help=NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:11:33.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Negative_Amt_C_DocType_ID', Description = 'Negativer Betrag Belegart', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:11:53.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist',Updated=TO_TIMESTAMP('2023-01-09 18:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='de_CH'
;

-- 2023-01-09T16:11:53.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'de_CH') 
;

-- 2023-01-09T16:11:56.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist',Updated=TO_TIMESTAMP('2023-01-09 18:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='de_DE'
;

-- 2023-01-09T16:11:56.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'de_DE') 
;

-- 2023-01-09T16:11:56.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581913,'de_DE') 
;

-- 2023-01-09T16:11:56.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID=581913
;

-- 2023-01-09T16:11:56.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL, AD_Element_ID=581913 WHERE UPPER(ColumnName)='NEGATIVE_AMT_C_DOCTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-09T16:11:56.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID=581913 AND IsCentrallyMaintained='Y'
;

-- 2023-01-09T16:11:56.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581913) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581913)
;

-- 2023-01-09T16:11:56.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:11:56.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Negative_Amt_C_DocType_ID', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:11:56.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Negative_Amt_C_DocType_ID', Description = 'DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:12:02.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Negativer Betrag Belegart', PrintName='Negativer Betrag Belegart',Updated=TO_TIMESTAMP('2023-01-09 18:12:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='nl_NL'
;

-- 2023-01-09T16:12:02.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'nl_NL') 
;

-- 2023-01-09T16:12:07.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Negativer Betrag Belegart', PrintName='Negativer Betrag Belegart',Updated=TO_TIMESTAMP('2023-01-09 18:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='de_DE'
;

-- 2023-01-09T16:12:07.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'de_DE') 
;

-- 2023-01-09T16:12:07.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581913,'de_DE') 
;

-- 2023-01-09T16:12:07.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negativer Betrag Belegart', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID=581913
;

-- 2023-01-09T16:12:07.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negativer Betrag Belegart', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL, AD_Element_ID=581913 WHERE UPPER(ColumnName)='NEGATIVE_AMT_C_DOCTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-09T16:12:07.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Negative_Amt_C_DocType_ID', Name='Negativer Betrag Belegart', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID=581913 AND IsCentrallyMaintained='Y'
;

-- 2023-01-09T16:12:07.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Negativer Betrag Belegart', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581913) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581913)
;

-- 2023-01-09T16:12:07.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Negativer Betrag Belegart', Name='Negativer Betrag Belegart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581913)
;

-- 2023-01-09T16:12:07.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Negativer Betrag Belegart', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:12:07.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Negativer Betrag Belegart', Description='DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', Help=NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:12:07.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Negativer Betrag Belegart', Description = 'DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581913
;

-- 2023-01-09T16:12:09.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Negativer Betrag Belegart', PrintName='Negativer Betrag Belegart',Updated=TO_TIMESTAMP('2023-01-09 18:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581913 AND AD_Language='de_CH'
;

-- 2023-01-09T16:12:09.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581913,'de_CH') 
;

-- 2023-01-09T16:15:54.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierungspool', PrintName='Fakturierungspool',Updated=TO_TIMESTAMP('2023-01-09 18:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='de_CH'
;

-- 2023-01-09T16:15:54.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'de_CH') 
;

-- 2023-01-09T16:15:56.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierungspool', PrintName='Fakturierungspool',Updated=TO_TIMESTAMP('2023-01-09 18:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='de_DE'
;

-- 2023-01-09T16:15:56.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'de_DE') 
;

-- 2023-01-09T16:15:56.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581911,'de_DE') 
;

-- 2023-01-09T16:15:56.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Fakturierungspool', Description=NULL, Help=NULL WHERE AD_Element_ID=581911
;

-- 2023-01-09T16:15:56.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Fakturierungspool', Description=NULL, Help=NULL, AD_Element_ID=581911 WHERE UPPER(ColumnName)='C_DOCTYPE_INVOICING_POOL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-09T16:15:56.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Fakturierungspool', Description=NULL, Help=NULL WHERE AD_Element_ID=581911 AND IsCentrallyMaintained='Y'
;

-- 2023-01-09T16:15:56.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fakturierungspool', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581911) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581911)
;

-- 2023-01-09T16:15:56.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Fakturierungspool', Name='Fakturierungspool' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581911)
;

-- 2023-01-09T16:15:56.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Fakturierungspool', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581911
;

-- 2023-01-09T16:15:56.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Fakturierungspool', Description=NULL, Help=NULL WHERE AD_Element_ID = 581911
;

-- 2023-01-09T16:15:56.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Fakturierungspool', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581911
;

-- 2023-01-09T16:15:59.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierungspool', PrintName='Fakturierungspool',Updated=TO_TIMESTAMP('2023-01-09 18:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='nl_NL'
;

-- 2023-01-09T16:15:59.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'nl_NL') 
;

-- 2023-01-09T16:16:12.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581911,0,541658,TO_TIMESTAMP('2023-01-09 18:16:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Fakturierungspool','N',TO_TIMESTAMP('2023-01-09 18:16:12','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-01-09T16:16:12.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541658 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-01-09T16:16:12.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(581911) 
;

-- 2023-01-09T16:16:12.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541658
;

-- 2023-01-09T16:16:12.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541658)
;

-- 2023-01-09T16:17:54.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581911,0,546734,542277,541658,'Y',TO_TIMESTAMP('2023-01-09 18:17:54','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_DocType_Invoicing_Pool','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Fakturierungspool','N',10,0,TO_TIMESTAMP('2023-01-09 18:17:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:17:54.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546734 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-01-09T16:17:54.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(581911) 
;

-- 2023-01-09T16:17:54.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546734)
;

-- 2023-01-09T16:17:58.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585455,710138,0,546734,TO_TIMESTAMP('2023-01-09 18:17:58','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-01-09 18:17:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:17:58.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710138 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-09T16:17:58.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-09T16:17:59.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710138
;

-- 2023-01-09T16:17:59.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(710138)
;

-- 2023-01-09T16:17:59.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585456,710139,0,546734,TO_TIMESTAMP('2023-01-09 18:17:59','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-01-09 18:17:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:17:59.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710139 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-09T16:17:59.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-09T16:17:59.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710139
;

-- 2023-01-09T16:17:59.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(710139)
;

-- 2023-01-09T16:17:59.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585459,710140,0,546734,TO_TIMESTAMP('2023-01-09 18:17:59','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-01-09 18:17:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:17:59.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710140 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-09T16:17:59.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-09T16:17:59.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710140
;

-- 2023-01-09T16:17:59.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(710140)
;

-- 2023-01-09T16:17:59.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585462,710141,0,546734,TO_TIMESTAMP('2023-01-09 18:17:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Fakturierungspool',TO_TIMESTAMP('2023-01-09 18:17:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:17:59.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-09T16:17:59.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581911) 
;

-- 2023-01-09T16:17:59.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710141
;

-- 2023-01-09T16:17:59.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(710141)
;

-- 2023-01-09T16:18:00.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585463,710142,0,546734,TO_TIMESTAMP('2023-01-09 18:17:59','YYYY-MM-DD HH24:MI:SS'),100,'DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist',10,'D','Y','N','N','N','N','N','N','N','Positiver Betrag Belegart',TO_TIMESTAMP('2023-01-09 18:17:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:18:00.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-09T16:18:00.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581912) 
;

-- 2023-01-09T16:18:00.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710142
;

-- 2023-01-09T16:18:00.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(710142)
;

-- 2023-01-09T16:18:00.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585464,710143,0,546734,TO_TIMESTAMP('2023-01-09 18:18:00','YYYY-MM-DD HH24:MI:SS'),100,'DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist',10,'D','Y','N','N','N','N','N','N','N','Negativer Betrag Belegart',TO_TIMESTAMP('2023-01-09 18:18:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:18:00.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-09T16:18:00.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581913) 
;

-- 2023-01-09T16:18:00.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710143
;

-- 2023-01-09T16:18:00.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(710143)
;

-- 2023-01-09T16:18:25.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546734,545367,TO_TIMESTAMP('2023-01-09 18:18:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-09 18:18:25','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-01-09T16:18:25.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545367 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-01-09T16:18:29.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546545,545367,TO_TIMESTAMP('2023-01-09 18:18:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-09 18:18:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:18:31.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546546,545367,TO_TIMESTAMP('2023-01-09 18:18:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-01-09 18:18:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:18:41.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546545,550211,TO_TIMESTAMP('2023-01-09 18:18:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-01-09 18:18:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:18:55.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710142,0,546734,614639,550211,'F',TO_TIMESTAMP('2023-01-09 18:18:55','YYYY-MM-DD HH24:MI:SS'),100,'DocType, die für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool positiv ist','Y','N','N','Y','N','N','N',0,'Positiver Betrag Belegart',10,0,0,TO_TIMESTAMP('2023-01-09 18:18:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:19:02.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710143,0,546734,614640,550211,'F',TO_TIMESTAMP('2023-01-09 18:19:02','YYYY-MM-DD HH24:MI:SS'),100,'DocType, der für das Ergebnisdokument zu verwenden ist, wenn die Gesamtsumme der Rechnungen im Pool negativ ist','Y','N','N','Y','N','N','N',0,'Negativer Betrag Belegart',20,0,0,TO_TIMESTAMP('2023-01-09 18:19:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:19:10.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546546,550212,TO_TIMESTAMP('2023-01-09 18:19:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-01-09 18:19:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:19:22.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710140,0,546734,614641,550212,'F',TO_TIMESTAMP('2023-01-09 18:19:22','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-01-09 18:19:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:21:35.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546546,550213,TO_TIMESTAMP('2023-01-09 18:21:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2023-01-09 18:21:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:21:49.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710139,0,546734,614642,550213,'F',TO_TIMESTAMP('2023-01-09 18:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2023-01-09 18:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:21:59.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710138,0,546734,614643,550213,'F',TO_TIMESTAMP('2023-01-09 18:21:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-01-09 18:21:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:24:58.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-09 18:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585463
;

-- 2023-01-09T16:25:06.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doctype_invoicing_pool','Positive_Amt_C_DocType_ID','NUMERIC(10)',null,null)
;

-- 2023-01-09T16:25:06.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doctype_invoicing_pool','Positive_Amt_C_DocType_ID',null,'NOT NULL',null)
;

-- 2023-01-09T16:25:19.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-09 18:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585464
;

-- 2023-01-09T16:25:20.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doctype_invoicing_pool','Negative_Amt_C_DocType_ID','NUMERIC(10)',null,null)
;

-- 2023-01-09T16:25:20.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doctype_invoicing_pool','Negative_Amt_C_DocType_ID',null,'NOT NULL',null)
;

-- 2023-01-09T16:31:02.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581911,542033,0,541658,TO_TIMESTAMP('2023-01-09 18:31:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Invoicing Pool','Y','N','N','N','N','Fakturierungspool',TO_TIMESTAMP('2023-01-09 18:31:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T16:31:02.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542033 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-09T16:31:02.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542033, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542033)
;

-- 2023-01-09T16:31:02.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(581911) 
;

-- 2023-01-09T16:31:03.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- 2023-01-09T16:31:03.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542033 AND AD_Tree_ID=10
;

-- 2023-01-09T17:14:29.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-09 19:14:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585462
;

-- 2023-01-09T17:14:35.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585465,581911,0,19,217,'C_DocType_Invoicing_Pool_ID',TO_TIMESTAMP('2023-01-09 19:14:34','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fakturierungspool',0,0,TO_TIMESTAMP('2023-01-09 19:14:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T17:14:35.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585465 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T17:14:35.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581911) 
;

-- 2023-01-09T17:14:37.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN C_DocType_Invoicing_Pool_ID NUMERIC(10)')
;

-- 2023-01-09T17:14:38.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_DocType ADD CONSTRAINT CDocTypeInvoicingPool_CDocType FOREIGN KEY (C_DocType_Invoicing_Pool_ID) REFERENCES public.C_DocType_Invoicing_Pool DEFERRABLE INITIALLY DEFERRED
;

-- 2023-01-09T17:15:39.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585465,710144,0,167,TO_TIMESTAMP('2023-01-09 19:15:39','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Fakturierungspool',TO_TIMESTAMP('2023-01-09 19:15:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T17:15:39.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710144 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-09T17:15:39.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581911) 
;

-- 2023-01-09T17:15:39.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710144
;

-- 2023-01-09T17:15:39.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(710144)
;

-- 2023-01-09T17:19:18.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''API'' | @DocBaseType@=''APC'' | @DocBaseType@=''ARI'' | @DocBaseType@=''ARC''',Updated=TO_TIMESTAMP('2023-01-09 19:19:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710144
;

-- 2023-01-09T17:19:38.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710144,0,167,614644,540405,'F',TO_TIMESTAMP('2023-01-09 19:19:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fakturierungspool',80,0,0,TO_TIMESTAMP('2023-01-09 19:19:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-09T17:40:49.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585467,581911,0,19,540270,'C_DocType_Invoicing_Pool_ID','(select t.C_DocType_Invoicing_Pool_ID from c_doctype t where t.c_doctype_id=C_Invoice_Candidate.c_doctypeinvoice_id)',TO_TIMESTAMP('2023-01-09 19:40:49','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Fakturierungspool',0,0,TO_TIMESTAMP('2023-01-09 19:40:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-09T17:40:49.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585467 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-09T17:40:49.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(581911) 
;

-- 2023-01-11T14:49:54.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,585467,1000000,1000000,540065,TO_TIMESTAMP('2023-01-11 16:49:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-01-11 16:49:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-11T14:49:36.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_AggregationItem SET IncludeLogic='@C_DocType_Invoicing_Pool_ID/0@=0',Updated=TO_TIMESTAMP('2023-01-11 16:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_AggregationItem_ID=1000014
;

-- Column: C_DocType_Invoicing_Pool.IsSOTrx
-- 2023-01-12T10:52:42.675Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585482,1106,0,20,542277,'IsSOTrx',TO_TIMESTAMP('2023-01-12 12:52:42','YYYY-MM-DD HH24:MI:SS'),100,'N','','Dies ist eine Verkaufstransaktion','D',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Verkaufstransaktion','NP',0,0,TO_TIMESTAMP('2023-01-12 12:52:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T10:52:42.682Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585482 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T10:52:42.715Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106) 
;

-- 2023-01-12T11:04:21.925Z
/* DDL */ SELECT public.db_alter_table('C_DocType_Invoicing_Pool','ALTER TABLE public.C_DocType_Invoicing_Pool ADD COLUMN IsSOTrx CHAR(1) CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

-- Field: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> Verkaufstransaktion
-- Column: C_DocType_Invoicing_Pool.IsSOTrx
-- 2023-01-12T11:08:52.638Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585482,710147,0,546734,TO_TIMESTAMP('2023-01-12 13:08:52','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufstransaktion',1,'D','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2023-01-12 13:08:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:08:52.642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710147 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:08:52.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106) 
;

-- 2023-01-12T11:08:52.673Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710147
;

-- 2023-01-12T11:08:52.679Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710147)
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 20 -> main.Verkaufstransaktion
-- Column: C_DocType_Invoicing_Pool.IsSOTrx
-- 2023-01-12T11:09:15.812Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710147,0,546734,614648,550212,'F',TO_TIMESTAMP('2023-01-12 13:09:15','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','N','N',0,'Verkaufstransaktion',20,0,0,TO_TIMESTAMP('2023-01-12 13:09:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_DocType_Invoicing_Pool.Name
-- 2023-01-12T12:21:19.993Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585484,469,0,10,542277,'Name',TO_TIMESTAMP('2023-01-12 14:21:19','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,60,'E','','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','N','N','N','N','N','Y','N',0,'Name','NP',0,1,TO_TIMESTAMP('2023-01-12 14:21:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T12:21:20Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585484 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T12:21:20.008Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2023-01-12T12:21:23.085Z
/* DDL */ SELECT public.db_alter_table('C_DocType_Invoicing_Pool','ALTER TABLE public.C_DocType_Invoicing_Pool ADD COLUMN Name VARCHAR(60) NOT NULL')
;

-- Field: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> Name
-- Column: C_DocType_Invoicing_Pool.Name
-- 2023-01-12T12:22:04.040Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585484,710157,0,546734,TO_TIMESTAMP('2023-01-12 14:22:03','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-01-12 14:22:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T12:22:04.043Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T12:22:04.047Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-01-12T12:22:04.165Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710157
;

-- 2023-01-12T12:22:04.166Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710157)
;

-- UI Element: Fakturierungspool(541658,D) -> Fakturierungspool(546734,D) -> main -> 10 -> main.Name
-- Column: C_DocType_Invoicing_Pool.Name
-- 2023-01-12T12:22:30.456Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710157,0,546734,614657,550211,'F',TO_TIMESTAMP('2023-01-12 14:22:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',5,0,0,TO_TIMESTAMP('2023-01-12 14:22:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_DocType_Invoicing_Pool_ID
-- 2023-01-12T15:15:28.523Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541705,TO_TIMESTAMP('2023-01-12 17:15:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_DocType_Invoicing_Pool_ID',TO_TIMESTAMP('2023-01-12 17:15:28','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-12T15:15:28.525Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541705 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Reference: C_DocType_Invoicing_Pool_ID
-- Table: C_DocType_Invoicing_Pool
-- Key: C_DocType_Invoicing_Pool.C_DocType_Invoicing_Pool_ID
-- 2023-01-12T15:16:06.367Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,585462,0,541705,542277,541658,TO_TIMESTAMP('2023-01-12 17:16:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-01-12 17:16:06','YYYY-MM-DD HH24:MI:SS'),100,'( issotrx = @issotrx@ )')
;

-- Column: C_DocType.C_DocType_Invoicing_Pool_ID
-- 2023-01-12T15:16:39.748Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541705, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-01-12 17:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585465
;

-- 2023-01-12T15:16:41.425Z
INSERT INTO t_alter_column values('c_doctype','C_DocType_Invoicing_Pool_ID','NUMERIC(10)',null,null)
;

-- Reference: C_DocType_Invoicing_Pool_ID
-- Table: C_DocType_Invoicing_Pool
-- Key: C_DocType_Invoicing_Pool.C_DocType_Invoicing_Pool_ID
-- 2023-01-12T15:24:02.153Z
UPDATE AD_Ref_Table SET WhereClause='C_DocType_Invoicing_Pool.ISSOTRX =''@IsSOTrx@''',Updated=TO_TIMESTAMP('2023-01-12 17:24:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541705
;

-- 2023-01-12T15:38:10.562Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (0,551286,0,540003,540066,TO_TIMESTAMP('2023-01-12 17:38:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2023-01-12 17:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_DocType_Invoicing_Pool_ID
-- Table: C_DocType_Invoicing_Pool
-- Key: C_DocType_Invoicing_Pool.C_DocType_Invoicing_Pool_ID
-- 2023-01-16T16:57:54.813Z
UPDATE AD_Ref_Table SET WhereClause='C_DocType_Invoicing_Pool.ISSOTRX =''@IsSOTrx@'' AND C_DocType_Invoicing_Pool.IsActive=''Y''',Updated=TO_TIMESTAMP('2023-01-16 18:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541705
;