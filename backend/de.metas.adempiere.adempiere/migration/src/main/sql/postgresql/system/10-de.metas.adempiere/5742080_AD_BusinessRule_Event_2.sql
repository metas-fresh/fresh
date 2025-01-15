-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- 2024-12-18T17:29:21.814Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589550,583397,0,30,542455,'XX','AD_BusinessRule_ID',TO_TIMESTAMP('2024-12-18 17:29:21.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Rule',0,0,TO_TIMESTAMP('2024-12-18 17:29:21.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-18T17:29:21.821Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589550 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-18T17:29:21.825Z
/* DDL */  select update_Column_Translation_From_AD_Element(583397) 
;

-- 2024-12-18T17:29:22.734Z
/* DDL */ SELECT public.db_alter_table('AD_Record_Warning','ALTER TABLE public.AD_Record_Warning ADD COLUMN AD_BusinessRule_ID NUMERIC(10)')
;

-- 2024-12-18T17:29:22.744Z
ALTER TABLE AD_Record_Warning ADD CONSTRAINT ADBusinessRule_ADRecordWarning FOREIGN KEY (AD_BusinessRule_ID) REFERENCES public.AD_BusinessRule DEFERRABLE INITIALLY DEFERRED
;

-- Field: Warnings -> Warnings -> Business Rule
-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Business Rule
-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- 2024-12-18T17:29:57.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589550,734622,0,547698,TO_TIMESTAMP('2024-12-18 17:29:57.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule',TO_TIMESTAMP('2024-12-18 17:29:57.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T17:29:57.713Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734622 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T17:29:57.715Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583397) 
;

-- 2024-12-18T17:29:57.721Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734622
;

-- 2024-12-18T17:29:57.723Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734622)
;

-- UI Column: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10
-- UI Element Group: business rule
-- 2024-12-18T17:30:19.535Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547677,552259,TO_TIMESTAMP('2024-12-18 17:30:19.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','business rule',30,TO_TIMESTAMP('2024-12-18 17:30:19.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Warnings -> Warnings.Business Rule
-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- UI Element: Warnings(541836,D) -> Warnings(547698,D) -> main -> 10 -> business rule.Business Rule
-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- 2024-12-18T17:30:29.831Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734622,0,547698,552259,627769,'F',TO_TIMESTAMP('2024-12-18 17:30:29.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Business Rule',10,0,0,TO_TIMESTAMP('2024-12-18 17:30:29.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Warnings -> Warnings -> Business Rule
-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- Field: Warnings(541836,D) -> Warnings(547698,D) -> Business Rule
-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- 2024-12-18T17:30:44.120Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-12-18 17:30:44.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734622
;

-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- Column: AD_Record_Warning.AD_BusinessRule_ID
-- 2024-12-18T17:30:52.650Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-18 17:30:52.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589550
;

-- Column: AD_Record_Warning.AD_Table_ID
-- Column: AD_Record_Warning.AD_Table_ID
-- 2024-12-18T17:31:04.522Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-18 17:31:04.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589490
;

-- Column: AD_Record_Warning.MsgText
-- Column: AD_Record_Warning.MsgText
-- 2024-12-18T17:31:11.832Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-12-18 17:31:11.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589492
;

-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- 2024-12-19T07:38:13.968Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589551,2887,0,30,542459,'XX','AD_Issue_ID',TO_TIMESTAMP('2024-12-19 07:38:13.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Probleme',0,0,TO_TIMESTAMP('2024-12-19 07:38:13.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-19T07:38:13.971Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589551 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-19T07:38:13.976Z
/* DDL */  select update_Column_Translation_From_AD_Element(2887) 
;

-- 2024-12-19T07:38:14.666Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule_Event','ALTER TABLE public.AD_BusinessRule_Event ADD COLUMN AD_Issue_ID NUMERIC(10)')
;

-- 2024-12-19T07:38:14.677Z
ALTER TABLE AD_BusinessRule_Event ADD CONSTRAINT ADIssue_ADBusinessRuleEvent FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED
;

-- Field: Business Rule Event -> Business Rule Event -> Probleme
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Probleme
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- 2024-12-19T07:38:34.602Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589551,734623,0,547702,TO_TIMESTAMP('2024-12-19 07:38:34.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'D','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2024-12-19 07:38:34.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-19T07:38:34.604Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734623 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-19T07:38:34.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2024-12-19T07:38:34.635Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734623
;

-- 2024-12-19T07:38:34.636Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734623)
;

-- UI Element: Business Rule Event -> Business Rule Event.Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- 2024-12-19T07:39:14.797Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2024-12-19 07:39:14.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627428
;

-- UI Element: Business Rule Event -> Business Rule Event.Verarbeitet
-- Column: AD_BusinessRule_Event.Processed
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Verarbeitet
-- Column: AD_BusinessRule_Event.Processed
-- 2024-12-19T07:39:24.344Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2024-12-19 07:39:24.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627427
;

-- UI Element: Business Rule Event -> Business Rule Event.Probleme
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Probleme
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- 2024-12-19T07:39:36.347Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734623,0,547702,552206,627770,'F',TO_TIMESTAMP('2024-12-19 07:39:36.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Probleme',30,0,0,TO_TIMESTAMP('2024-12-19 07:39:36.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule Event -> Business Rule Event.Probleme
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Probleme
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- 2024-12-19T07:39:59.640Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-12-19 07:39:59.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627770
;

-- UI Element: Business Rule Event -> Business Rule Event.Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> status.Processing Tag
-- Column: AD_BusinessRule_Event.ProcessingTag
-- 2024-12-19T07:39:59.650Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-12-19 07:39:59.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627428
;

-- UI Element: Business Rule Event -> Business Rule Event.Sektion
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- UI Element: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_BusinessRule_Event.AD_Org_ID
-- 2024-12-19T07:39:59.658Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-12-19 07:39:59.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627429
;

-- Field: Business Rule Event -> Business Rule Event -> Probleme
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- Field: Business Rule Event(541838,D) -> Business Rule Event(547702,D) -> Probleme
-- Column: AD_BusinessRule_Event.AD_Issue_ID
-- 2024-12-19T07:40:23.674Z
UPDATE AD_Field SET DisplayLogic='@AD_Issue_ID/0@>0',Updated=TO_TIMESTAMP('2024-12-19 07:40:23.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734623
;

