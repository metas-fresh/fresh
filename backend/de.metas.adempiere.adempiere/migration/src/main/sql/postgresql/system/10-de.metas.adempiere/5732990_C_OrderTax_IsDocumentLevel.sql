-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:11:15.160Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588963,917,0,20,314,'IsDocumentLevel',TO_TIMESTAMP('2024-09-11 14:11:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','D',0,1,'If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Dokumentbasiert',0,0,TO_TIMESTAMP('2024-09-11 14:11:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-11T11:11:15.161Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588963 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-11T11:11:15.165Z
/* DDL */  select update_Column_Translation_From_AD_Element(917) 
;

-- 2024-09-11T11:11:15.839Z
/* DDL */ SELECT public.db_alter_table('C_OrderTax','ALTER TABLE public.C_OrderTax ADD COLUMN IsDocumentLevel CHAR(1) DEFAULT ''N'' CHECK (IsDocumentLevel IN (''Y'',''N'')) NOT NULL')
;

-- Field: Auftrag_OLD -> Steuer -> Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:11:41.185Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588963,729860,0,236,TO_TIMESTAMP('2024-09-11 14:11:41','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)',1,'D','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','N','N','N','N','N','N','Dokumentbasiert',TO_TIMESTAMP('2024-09-11 14:11:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-11T11:11:41.186Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T11:11:41.188Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(917) 
;

-- 2024-09-11T11:11:41.190Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729860
;

-- 2024-09-11T11:11:41.191Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729860)
;

-- UI Element: Auftrag_OLD -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:12:05.368Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729860,0,236,1000006,625316,'F',TO_TIMESTAMP('2024-09-11 14:12:05','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','Dokumentbasiert',90,0,0,TO_TIMESTAMP('2024-09-11 14:12:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftrag_OLD -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:12:28.402Z
UPDATE AD_UI_Element SET SeqNo=65,Updated=TO_TIMESTAMP('2024-09-11 14:12:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625316
;

-- UI Element: Auftrag_OLD -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:12:36.158Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-11 14:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625316
;

-- UI Element: Auftrag_OLD -> Steuer.Sektion
-- Column: C_OrderTax.AD_Org_ID
-- 2024-09-11T11:12:36.165Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-11 14:12:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000053
;

-- Field: Bestellung_OLD -> Steuer -> Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:12:54.546Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588963,729861,0,295,TO_TIMESTAMP('2024-09-11 14:12:54','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)',1,'D','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','N','N','N','N','N','N','Dokumentbasiert',TO_TIMESTAMP('2024-09-11 14:12:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-11T11:12:54.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T11:12:54.550Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(917) 
;

-- 2024-09-11T11:12:54.553Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729861
;

-- 2024-09-11T11:12:54.554Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729861)
;

-- UI Element: Bestellung_OLD -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:13:17.737Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729861,0,295,540071,625317,'F',TO_TIMESTAMP('2024-09-11 14:13:17','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','Dokumentbasiert',100,0,0,TO_TIMESTAMP('2024-09-11 14:13:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Bestellung_OLD -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:13:41.051Z
UPDATE AD_UI_Element SET SeqNo=65,Updated=TO_TIMESTAMP('2024-09-11 14:13:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625317
;

-- UI Element: Bestellung_OLD -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-11T11:13:47.850Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-11 14:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625317
;

-- UI Element: Bestellung_OLD -> Steuer.Sektion
-- Column: C_OrderTax.AD_Org_ID
-- 2024-09-11T11:13:47.856Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-11 14:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541271
;

