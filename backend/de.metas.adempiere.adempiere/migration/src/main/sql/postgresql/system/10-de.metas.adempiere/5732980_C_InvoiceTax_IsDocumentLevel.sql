-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-11T10:44:13.641Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588962,917,0,20,334,'IsDocumentLevel',TO_TIMESTAMP('2024-09-11 13:44:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','D',0,1,'If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Dokumentbasiert',0,0,TO_TIMESTAMP('2024-09-11 13:44:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-11T10:44:13.647Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588962 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-11T10:44:13.676Z
/* DDL */  select update_Column_Translation_From_AD_Element(917) 
;

-- 2024-09-11T10:44:14.474Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceTax','ALTER TABLE public.C_InvoiceTax ADD COLUMN IsDocumentLevel CHAR(1) DEFAULT ''N'' CHECK (IsDocumentLevel IN (''Y'',''N'')) NOT NULL')
;

-- Field: Rechnung_OLD -> Rechnungs Steuer -> Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-11T11:08:15.703Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588962,729859,0,271,TO_TIMESTAMP('2024-09-11 14:08:15','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)',1,'D','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','N','N','N','N','N','N','Dokumentbasiert',TO_TIMESTAMP('2024-09-11 14:08:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-11T11:08:15.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T11:08:15.711Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(917) 
;

-- 2024-09-11T11:08:15.724Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729859
;

-- 2024-09-11T11:08:15.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729859)
;

-- UI Element: Rechnung_OLD -> Rechnungs Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-11T11:08:57.738Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729859,0,271,540024,625315,'F',TO_TIMESTAMP('2024-09-11 14:08:57','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','Dokumentbasiert',90,0,0,TO_TIMESTAMP('2024-09-11 14:08:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung_OLD -> Rechnungs Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-11T11:09:14.107Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-11 14:09:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625315
;

-- UI Element: Rechnung_OLD -> Rechnungs Steuer.Sektion
-- Column: C_InvoiceTax.AD_Org_ID
-- 2024-09-11T11:09:14.115Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-11 14:09:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540758
;

-- Field: Eingangsrechnung_OLD -> Rechnungs-Steuer -> Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-11T11:14:43.346Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588962,729862,0,292,TO_TIMESTAMP('2024-09-11 14:14:43','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)',1,'D','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','N','N','N','N','N','N','Dokumentbasiert',TO_TIMESTAMP('2024-09-11 14:14:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-11T11:14:43.347Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T11:14:43.349Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(917) 
;

-- 2024-09-11T11:14:43.353Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729862
;

-- 2024-09-11T11:14:43.354Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729862)
;

-- UI Element: Eingangsrechnung_OLD -> Rechnungs-Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-11T11:15:15.308Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729862,0,292,540220,625318,'F',TO_TIMESTAMP('2024-09-11 14:15:15','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','Dokumentbasiert',10,0,0,TO_TIMESTAMP('2024-09-11 14:15:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Eingangsrechnung_OLD -> Rechnungs-Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-11T11:15:33.545Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-11 14:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625318
;

-- UI Element: Eingangsrechnung_OLD -> Rechnungs-Steuer.Sektion
-- Column: C_InvoiceTax.AD_Org_ID
-- 2024-09-11T11:15:33.551Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-11 14:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542672
;

