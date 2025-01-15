-- Window: Business Rule, InternalName=null
-- Window: Business Rule, InternalName=null
-- 2024-12-18T09:18:22.223Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583397,0,541837,TO_TIMESTAMP('2024-12-18 09:18:21.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Business Rule','N',TO_TIMESTAMP('2024-12-18 09:18:21.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2024-12-18T09:18:22.226Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541837 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-12-18T09:18:22.377Z
/* DDL */  select update_window_translation_from_ad_element(583397) 
;

-- 2024-12-18T09:18:22.394Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541837
;

-- 2024-12-18T09:18:22.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541837)
;

-- Tab: Business Rule -> Business Rule
-- Table: AD_BusinessRule
-- Tab: Business Rule(541837,D) -> Business Rule
-- Table: AD_BusinessRule
-- 2024-12-18T09:18:41.466Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583397,0,547699,542456,541837,'Y',TO_TIMESTAMP('2024-12-18 09:18:41.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_BusinessRule','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Business Rule','N',10,0,TO_TIMESTAMP('2024-12-18 09:18:41.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:41.469Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547699 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-12-18T09:18:41.471Z
/* DDL */  select update_tab_translation_from_ad_element(583397) 
;

-- 2024-12-18T09:18:41.477Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547699)
;

-- Field: Business Rule -> Business Rule -> Mandant
-- Column: AD_BusinessRule.AD_Client_ID
-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Mandant
-- Column: AD_BusinessRule.AD_Client_ID
-- 2024-12-18T09:18:44.746Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589493,734094,0,547699,TO_TIMESTAMP('2024-12-18 09:18:44.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-12-18 09:18:44.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:44.750Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:18:44.754Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-12-18T09:18:46.292Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734094
;

-- 2024-12-18T09:18:46.293Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734094)
;

-- Field: Business Rule -> Business Rule -> Sektion
-- Column: AD_BusinessRule.AD_Org_ID
-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Sektion
-- Column: AD_BusinessRule.AD_Org_ID
-- 2024-12-18T09:18:46.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589494,734095,0,547699,TO_TIMESTAMP('2024-12-18 09:18:46.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-12-18 09:18:46.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:46.421Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:18:46.424Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-12-18T09:18:46.860Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734095
;

-- 2024-12-18T09:18:46.862Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734095)
;

-- Field: Business Rule -> Business Rule -> Aktiv
-- Column: AD_BusinessRule.IsActive
-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Aktiv
-- Column: AD_BusinessRule.IsActive
-- 2024-12-18T09:18:46.981Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589497,734096,0,547699,TO_TIMESTAMP('2024-12-18 09:18:46.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-12-18 09:18:46.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:46.983Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:18:46.985Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-12-18T09:18:47.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734096
;

-- 2024-12-18T09:18:47.068Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734096)
;

-- Field: Business Rule -> Business Rule -> Business Rule
-- Column: AD_BusinessRule.AD_BusinessRule_ID
-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Business Rule
-- Column: AD_BusinessRule.AD_BusinessRule_ID
-- 2024-12-18T09:18:47.183Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589500,734097,0,547699,TO_TIMESTAMP('2024-12-18 09:18:47.071000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule',TO_TIMESTAMP('2024-12-18 09:18:47.071000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:47.185Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:18:47.189Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583397) 
;

-- 2024-12-18T09:18:47.195Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734097
;

-- 2024-12-18T09:18:47.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734097)
;

-- Field: Business Rule -> Business Rule -> Name
-- Column: AD_BusinessRule.Name
-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Name
-- Column: AD_BusinessRule.Name
-- 2024-12-18T09:18:47.320Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589501,734098,0,547699,TO_TIMESTAMP('2024-12-18 09:18:47.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2024-12-18 09:18:47.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:47.322Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:18:47.324Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2024-12-18T09:18:47.506Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734098
;

-- 2024-12-18T09:18:47.507Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734098)
;

-- Field: Business Rule -> Business Rule -> DB-Tabelle
-- Column: AD_BusinessRule.AD_Table_ID
-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> DB-Tabelle
-- Column: AD_BusinessRule.AD_Table_ID
-- 2024-12-18T09:18:47.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589502,734099,0,547699,TO_TIMESTAMP('2024-12-18 09:18:47.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',10,'D','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2024-12-18 09:18:47.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:47.640Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:18:47.641Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2024-12-18T09:18:47.686Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734099
;

-- 2024-12-18T09:18:47.688Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734099)
;

-- Field: Business Rule -> Business Rule -> Validation Rule
-- Column: AD_BusinessRule.Validation_Rule_ID
-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Validation Rule
-- Column: AD_BusinessRule.Validation_Rule_ID
-- 2024-12-18T09:18:47.821Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589503,734100,0,547699,TO_TIMESTAMP('2024-12-18 09:18:47.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Validation Rule',TO_TIMESTAMP('2024-12-18 09:18:47.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:47.822Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:18:47.824Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583398) 
;

-- 2024-12-18T09:18:47.829Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734100
;

-- 2024-12-18T09:18:47.830Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734100)
;

-- Field: Business Rule -> Business Rule -> Warning Message
-- Column: AD_BusinessRule.WarningMessage
-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Warning Message
-- Column: AD_BusinessRule.WarningMessage
-- 2024-12-18T09:18:47.949Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589504,734101,0,547699,TO_TIMESTAMP('2024-12-18 09:18:47.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Warning Message',TO_TIMESTAMP('2024-12-18 09:18:47.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:18:47.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:18:47.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583399) 
;

-- 2024-12-18T09:18:47.954Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734101
;

-- 2024-12-18T09:18:47.955Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734101)
;

-- Tab: Business Rule -> Business Rule Precondition
-- Table: AD_BusinessRule_Precondition
-- Tab: Business Rule(541837,D) -> Business Rule Precondition
-- Table: AD_BusinessRule_Precondition
-- 2024-12-18T09:19:16.155Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589517,583400,0,547700,542457,541837,'Y',TO_TIMESTAMP('2024-12-18 09:19:15.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_BusinessRule_Precondition','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Business Rule Precondition',589500,'N',20,1,TO_TIMESTAMP('2024-12-18 09:19:15.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:16.159Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547700 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-12-18T09:19:16.161Z
/* DDL */  select update_tab_translation_from_ad_element(583400) 
;

-- 2024-12-18T09:19:16.170Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547700)
;

-- Field: Business Rule -> Business Rule Precondition -> Mandant
-- Column: AD_BusinessRule_Precondition.AD_Client_ID
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Mandant
-- Column: AD_BusinessRule_Precondition.AD_Client_ID
-- 2024-12-18T09:19:18.967Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589505,734102,0,547700,TO_TIMESTAMP('2024-12-18 09:19:18.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-12-18 09:19:18.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:18.969Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:18.971Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-12-18T09:19:19.052Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734102
;

-- 2024-12-18T09:19:19.054Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734102)
;

-- Field: Business Rule -> Business Rule Precondition -> Sektion
-- Column: AD_BusinessRule_Precondition.AD_Org_ID
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Sektion
-- Column: AD_BusinessRule_Precondition.AD_Org_ID
-- 2024-12-18T09:19:19.193Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589506,734103,0,547700,TO_TIMESTAMP('2024-12-18 09:19:19.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-12-18 09:19:19.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:19.195Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:19.196Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-12-18T09:19:19.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734103
;

-- 2024-12-18T09:19:19.260Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734103)
;

-- Field: Business Rule -> Business Rule Precondition -> Aktiv
-- Column: AD_BusinessRule_Precondition.IsActive
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Aktiv
-- Column: AD_BusinessRule_Precondition.IsActive
-- 2024-12-18T09:19:19.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589509,734104,0,547700,TO_TIMESTAMP('2024-12-18 09:19:19.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-12-18 09:19:19.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:19.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:19.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-12-18T09:19:19.458Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734104
;

-- 2024-12-18T09:19:19.460Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734104)
;

-- Field: Business Rule -> Business Rule Precondition -> Business Rule Precondition
-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_Precondition_ID
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Business Rule Precondition
-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_Precondition_ID
-- 2024-12-18T09:19:19.579Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589512,734105,0,547700,TO_TIMESTAMP('2024-12-18 09:19:19.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule Precondition',TO_TIMESTAMP('2024-12-18 09:19:19.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:19.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:19.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583400) 
;

-- 2024-12-18T09:19:19.586Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734105
;

-- 2024-12-18T09:19:19.587Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734105)
;

-- Field: Business Rule -> Business Rule Precondition -> Precondition Rule
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Precondition Rule
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- 2024-12-18T09:19:19.700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589513,734106,0,547700,TO_TIMESTAMP('2024-12-18 09:19:19.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Precondition Rule',TO_TIMESTAMP('2024-12-18 09:19:19.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:19.701Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:19.702Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583401) 
;

-- 2024-12-18T09:19:19.704Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734106
;

-- 2024-12-18T09:19:19.705Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734106)
;

-- Field: Business Rule -> Business Rule Precondition -> Beschreibung
-- Column: AD_BusinessRule_Precondition.Description
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Beschreibung
-- Column: AD_BusinessRule_Precondition.Description
-- 2024-12-18T09:19:19.826Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589514,734107,0,547700,TO_TIMESTAMP('2024-12-18 09:19:19.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2024-12-18 09:19:19.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:19.829Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:19.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2024-12-18T09:19:19.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734107
;

-- 2024-12-18T09:19:19.923Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734107)
;

-- Field: Business Rule -> Business Rule Precondition -> Precondition Type
-- Column: AD_BusinessRule_Precondition.PreconditionType
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Precondition Type
-- Column: AD_BusinessRule_Precondition.PreconditionType
-- 2024-12-18T09:19:20.045Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589515,734108,0,547700,TO_TIMESTAMP('2024-12-18 09:19:19.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Precondition Type',TO_TIMESTAMP('2024-12-18 09:19:19.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:20.046Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:20.047Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583402) 
;

-- 2024-12-18T09:19:20.050Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734108
;

-- 2024-12-18T09:19:20.050Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734108)
;

-- Field: Business Rule -> Business Rule Precondition -> Precondition SQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Precondition SQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- 2024-12-18T09:19:20.161Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589516,734109,0,547700,TO_TIMESTAMP('2024-12-18 09:19:20.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Precondition SQL',TO_TIMESTAMP('2024-12-18 09:19:20.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:20.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:20.164Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583403) 
;

-- 2024-12-18T09:19:20.167Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734109
;

-- 2024-12-18T09:19:20.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734109)
;

-- Field: Business Rule -> Business Rule Precondition -> Business Rule
-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_ID
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Business Rule
-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_ID
-- 2024-12-18T09:19:20.279Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589517,734110,0,547700,TO_TIMESTAMP('2024-12-18 09:19:20.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule',TO_TIMESTAMP('2024-12-18 09:19:20.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:20.281Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:20.282Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583397) 
;

-- 2024-12-18T09:19:20.285Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734110
;

-- 2024-12-18T09:19:20.286Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734110)
;

-- Tab: Business Rule -> Business Rule Trigger
-- Table: AD_BusinessRule_Trigger
-- Tab: Business Rule(541837,D) -> Business Rule Trigger
-- Table: AD_BusinessRule_Trigger
-- 2024-12-18T09:19:55.180Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589526,583404,0,547701,542458,541837,'Y',TO_TIMESTAMP('2024-12-18 09:19:55.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_BusinessRule_Trigger','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Business Rule Trigger',589500,'N',30,1,TO_TIMESTAMP('2024-12-18 09:19:55.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:55.183Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547701 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-12-18T09:19:55.184Z
/* DDL */  select update_tab_translation_from_ad_element(583404) 
;

-- 2024-12-18T09:19:55.189Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547701)
;

-- Field: Business Rule -> Business Rule Trigger -> Mandant
-- Column: AD_BusinessRule_Trigger.AD_Client_ID
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> Mandant
-- Column: AD_BusinessRule_Trigger.AD_Client_ID
-- 2024-12-18T09:19:58.640Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589518,734111,0,547701,TO_TIMESTAMP('2024-12-18 09:19:58.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-12-18 09:19:58.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:58.642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:58.644Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-12-18T09:19:58.750Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734111
;

-- 2024-12-18T09:19:58.751Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734111)
;

-- Field: Business Rule -> Business Rule Trigger -> Sektion
-- Column: AD_BusinessRule_Trigger.AD_Org_ID
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> Sektion
-- Column: AD_BusinessRule_Trigger.AD_Org_ID
-- 2024-12-18T09:19:58.867Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589519,734112,0,547701,TO_TIMESTAMP('2024-12-18 09:19:58.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-12-18 09:19:58.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:58.868Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:58.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-12-18T09:19:58.946Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734112
;

-- 2024-12-18T09:19:58.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734112)
;

-- Field: Business Rule -> Business Rule Trigger -> Aktiv
-- Column: AD_BusinessRule_Trigger.IsActive
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> Aktiv
-- Column: AD_BusinessRule_Trigger.IsActive
-- 2024-12-18T09:19:59.064Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589522,734113,0,547701,TO_TIMESTAMP('2024-12-18 09:19:58.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-12-18 09:19:58.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:59.065Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:59.066Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-12-18T09:19:59.117Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734113
;

-- 2024-12-18T09:19:59.119Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734113)
;

-- Field: Business Rule -> Business Rule Trigger -> Business Rule Trigger
-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_Trigger_ID
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> Business Rule Trigger
-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_Trigger_ID
-- 2024-12-18T09:19:59.244Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589525,734114,0,547701,TO_TIMESTAMP('2024-12-18 09:19:59.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule Trigger',TO_TIMESTAMP('2024-12-18 09:19:59.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:59.245Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:59.246Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583404) 
;

-- 2024-12-18T09:19:59.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734114
;

-- 2024-12-18T09:19:59.249Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734114)
;

-- Field: Business Rule -> Business Rule Trigger -> Business Rule
-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_ID
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> Business Rule
-- Column: AD_BusinessRule_Trigger.AD_BusinessRule_ID
-- 2024-12-18T09:19:59.372Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589526,734115,0,547701,TO_TIMESTAMP('2024-12-18 09:19:59.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Business Rule',TO_TIMESTAMP('2024-12-18 09:19:59.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:59.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:59.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583397) 
;

-- 2024-12-18T09:19:59.377Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734115
;

-- 2024-12-18T09:19:59.378Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734115)
;

-- Field: Business Rule -> Business Rule Trigger -> Source Table
-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> Source Table
-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- 2024-12-18T09:19:59.500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589527,734116,0,547701,TO_TIMESTAMP('2024-12-18 09:19:59.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Source Table',TO_TIMESTAMP('2024-12-18 09:19:59.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:59.503Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:59.505Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577632) 
;

-- 2024-12-18T09:19:59.512Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734116
;

-- 2024-12-18T09:19:59.514Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734116)
;

-- Field: Business Rule -> Business Rule Trigger -> On New
-- Column: AD_BusinessRule_Trigger.OnNew
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> On New
-- Column: AD_BusinessRule_Trigger.OnNew
-- 2024-12-18T09:19:59.655Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589528,734117,0,547701,TO_TIMESTAMP('2024-12-18 09:19:59.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','On New',TO_TIMESTAMP('2024-12-18 09:19:59.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:59.656Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734117 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:59.657Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583405) 
;

-- 2024-12-18T09:19:59.660Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734117
;

-- 2024-12-18T09:19:59.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734117)
;

-- Field: Business Rule -> Business Rule Trigger -> On Update
-- Column: AD_BusinessRule_Trigger.OnUpdate
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> On Update
-- Column: AD_BusinessRule_Trigger.OnUpdate
-- 2024-12-18T09:19:59.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589529,734118,0,547701,TO_TIMESTAMP('2024-12-18 09:19:59.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','On Update',TO_TIMESTAMP('2024-12-18 09:19:59.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:59.777Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734118 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:59.779Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583406) 
;

-- 2024-12-18T09:19:59.782Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734118
;

-- 2024-12-18T09:19:59.782Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734118)
;

-- Field: Business Rule -> Business Rule Trigger -> On Delete
-- Column: AD_BusinessRule_Trigger.OnDelete
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> On Delete
-- Column: AD_BusinessRule_Trigger.OnDelete
-- 2024-12-18T09:19:59.904Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589530,734119,0,547701,TO_TIMESTAMP('2024-12-18 09:19:59.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','On Delete',TO_TIMESTAMP('2024-12-18 09:19:59.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:19:59.905Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:19:59.907Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583407) 
;

-- 2024-12-18T09:19:59.911Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734119
;

-- 2024-12-18T09:19:59.912Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734119)
;

-- Field: Business Rule -> Business Rule Trigger -> Condition SQL
-- Column: AD_BusinessRule_Trigger.ConditionSQL
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> Condition SQL
-- Column: AD_BusinessRule_Trigger.ConditionSQL
-- 2024-12-18T09:20:00.020Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589531,734120,0,547701,TO_TIMESTAMP('2024-12-18 09:19:59.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Condition SQL',TO_TIMESTAMP('2024-12-18 09:19:59.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:20:00.021Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:20:00.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583410) 
;

-- 2024-12-18T09:20:00.025Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734120
;

-- 2024-12-18T09:20:00.026Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734120)
;

-- Field: Business Rule -> Business Rule Trigger -> Target Record Mapping SQL
-- Column: AD_BusinessRule_Trigger.TargetRecordMappingSQL
-- Field: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> Target Record Mapping SQL
-- Column: AD_BusinessRule_Trigger.TargetRecordMappingSQL
-- 2024-12-18T09:20:00.133Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589532,734121,0,547701,TO_TIMESTAMP('2024-12-18 09:20:00.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,4000,'D','Y','N','N','N','N','N','N','N','Target Record Mapping SQL',TO_TIMESTAMP('2024-12-18 09:20:00.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:20:00.134Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-18T09:20:00.136Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583408) 
;

-- 2024-12-18T09:20:00.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734121
;

-- 2024-12-18T09:20:00.140Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734121)
;

-- Tab: Business Rule(541837,D) -> Business Rule(547699,D)
-- UI Section: main
-- 2024-12-18T09:20:09.306Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547699,546282,TO_TIMESTAMP('2024-12-18 09:20:09.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-18 09:20:09.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-12-18T09:20:09.309Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546282 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Business Rule(541837,D) -> Business Rule(547699,D) -> main
-- UI Column: 10
-- 2024-12-18T09:20:09.483Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547679,546282,TO_TIMESTAMP('2024-12-18 09:20:09.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-18 09:20:09.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Business Rule(541837,D) -> Business Rule(547699,D) -> main
-- UI Column: 20
-- 2024-12-18T09:20:09.611Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547680,546282,TO_TIMESTAMP('2024-12-18 09:20:09.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-12-18 09:20:09.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10
-- UI Element Group: default
-- 2024-12-18T09:20:09.788Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547679,552190,TO_TIMESTAMP('2024-12-18 09:20:09.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-12-18 09:20:09.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Business Rule(541837,D) -> Business Rule Precondition(547700,D)
-- UI Section: main
-- 2024-12-18T09:20:09.902Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547700,546283,TO_TIMESTAMP('2024-12-18 09:20:09.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-18 09:20:09.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-12-18T09:20:09.903Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546283 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main
-- UI Column: 10
-- 2024-12-18T09:20:10.022Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547681,546283,TO_TIMESTAMP('2024-12-18 09:20:09.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-18 09:20:09.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10
-- UI Element Group: default
-- 2024-12-18T09:20:10.139Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547681,552191,TO_TIMESTAMP('2024-12-18 09:20:10.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-12-18 09:20:10.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Business Rule(541837,D) -> Business Rule Trigger(547701,D)
-- UI Section: main
-- 2024-12-18T09:20:10.261Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547701,546284,TO_TIMESTAMP('2024-12-18 09:20:10.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-18 09:20:10.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-12-18T09:20:10.262Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546284 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main
-- UI Column: 10
-- 2024-12-18T09:20:10.375Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547682,546284,TO_TIMESTAMP('2024-12-18 09:20:10.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-12-18 09:20:10.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10
-- UI Element Group: default
-- 2024-12-18T09:20:10.485Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547682,552192,TO_TIMESTAMP('2024-12-18 09:20:10.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-12-18 09:20:10.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule.Name
-- Column: AD_BusinessRule.Name
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10 -> default.Name
-- Column: AD_BusinessRule.Name
-- 2024-12-18T09:21:04.945Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734098,0,547699,552190,627403,'F',TO_TIMESTAMP('2024-12-18 09:21:04.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2024-12-18 09:21:04.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule.DB-Tabelle
-- Column: AD_BusinessRule.AD_Table_ID
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10 -> default.DB-Tabelle
-- Column: AD_BusinessRule.AD_Table_ID
-- 2024-12-18T09:21:11.741Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734099,0,547699,552190,627404,'F',TO_TIMESTAMP('2024-12-18 09:21:11.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','N','N','DB-Tabelle',20,0,0,TO_TIMESTAMP('2024-12-18 09:21:11.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10
-- UI Element Group: validation
-- 2024-12-18T09:21:21.817Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547679,552193,TO_TIMESTAMP('2024-12-18 09:21:21.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','validation',20,TO_TIMESTAMP('2024-12-18 09:21:21.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule.Validation Rule
-- Column: AD_BusinessRule.Validation_Rule_ID
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10 -> validation.Validation Rule
-- Column: AD_BusinessRule.Validation_Rule_ID
-- 2024-12-18T09:21:33.788Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734100,0,547699,552193,627405,'F',TO_TIMESTAMP('2024-12-18 09:21:33.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Validation Rule',10,0,0,TO_TIMESTAMP('2024-12-18 09:21:33.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10
-- UI Element Group: warning
-- 2024-12-18T09:21:46.791Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547679,552194,TO_TIMESTAMP('2024-12-18 09:21:46.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','warning',30,TO_TIMESTAMP('2024-12-18 09:21:46.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule.Warning Message
-- Column: AD_BusinessRule.WarningMessage
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10 -> warning.Warning Message
-- Column: AD_BusinessRule.WarningMessage
-- 2024-12-18T09:21:55.488Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734101,0,547699,552194,627406,'F',TO_TIMESTAMP('2024-12-18 09:21:55.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Warning Message',10,0,0,TO_TIMESTAMP('2024-12-18 09:21:55.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20
-- UI Element Group: flags
-- 2024-12-18T09:22:11.307Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547680,552195,TO_TIMESTAMP('2024-12-18 09:22:11.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2024-12-18 09:22:11.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule.Aktiv
-- Column: AD_BusinessRule.IsActive
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_BusinessRule.IsActive
-- 2024-12-18T09:22:20.510Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734096,0,547699,552195,627407,'F',TO_TIMESTAMP('2024-12-18 09:22:20.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-12-18 09:22:20.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20
-- UI Element Group: org&client
-- 2024-12-18T09:22:28.340Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547680,552196,TO_TIMESTAMP('2024-12-18 09:22:28.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org&client',20,TO_TIMESTAMP('2024-12-18 09:22:28.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule.Sektion
-- Column: AD_BusinessRule.AD_Org_ID
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_BusinessRule.AD_Org_ID
-- 2024-12-18T09:22:39.369Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734095,0,547699,552196,627408,'F',TO_TIMESTAMP('2024-12-18 09:22:39.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2024-12-18 09:22:39.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule.Mandant
-- Column: AD_BusinessRule.AD_Client_ID
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20 -> org&client.Mandant
-- Column: AD_BusinessRule.AD_Client_ID
-- 2024-12-18T09:22:45.173Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734094,0,547699,552196,627409,'F',TO_TIMESTAMP('2024-12-18 09:22:45.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2024-12-18 09:22:45.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Precondition.Precondition Type
-- Column: AD_BusinessRule_Precondition.PreconditionType
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10 -> default.Precondition Type
-- Column: AD_BusinessRule_Precondition.PreconditionType
-- 2024-12-18T09:24:14.498Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734108,0,547700,552191,627410,'F',TO_TIMESTAMP('2024-12-18 09:24:14.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Precondition Type',10,0,0,TO_TIMESTAMP('2024-12-18 09:24:14.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Precondition.Precondition SQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10 -> default.Precondition SQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- 2024-12-18T09:24:21.499Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734109,0,547700,552191,627411,'F',TO_TIMESTAMP('2024-12-18 09:24:21.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Precondition SQL',20,0,0,TO_TIMESTAMP('2024-12-18 09:24:21.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Precondition.Precondition Rule
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10 -> default.Precondition Rule
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- 2024-12-18T09:24:32.742Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734106,0,547700,552191,627412,'F',TO_TIMESTAMP('2024-12-18 09:24:31.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Precondition Rule',30,0,0,TO_TIMESTAMP('2024-12-18 09:24:31.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10
-- UI Element Group: description
-- 2024-12-18T09:24:49.962Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547681,552197,TO_TIMESTAMP('2024-12-18 09:24:49.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','description',20,TO_TIMESTAMP('2024-12-18 09:24:49.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Precondition.Beschreibung
-- Column: AD_BusinessRule_Precondition.Description
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10 -> description.Beschreibung
-- Column: AD_BusinessRule_Precondition.Description
-- 2024-12-18T09:25:00.351Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734107,0,547700,552197,627413,'F',TO_TIMESTAMP('2024-12-18 09:25:00.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2024-12-18 09:25:00.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main
-- UI Column: 20
-- 2024-12-18T09:25:05.558Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547683,546283,TO_TIMESTAMP('2024-12-18 09:25:05.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-12-18 09:25:05.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 20
-- UI Element Group: flags
-- 2024-12-18T09:25:11.148Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547683,552198,TO_TIMESTAMP('2024-12-18 09:25:10.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2024-12-18 09:25:10.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Precondition.Aktiv
-- Column: AD_BusinessRule_Precondition.IsActive
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_BusinessRule_Precondition.IsActive
-- 2024-12-18T09:25:18.615Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734104,0,547700,552198,627414,'F',TO_TIMESTAMP('2024-12-18 09:25:18.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-12-18 09:25:18.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Business Rule -> Business Rule Precondition -> Precondition SQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Precondition SQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- 2024-12-18T09:26:02.136Z
UPDATE AD_Field SET DisplayLogic='@PreconditionType/X@=S',Updated=TO_TIMESTAMP('2024-12-18 09:26:02.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734109
;

-- Field: Business Rule -> Business Rule Precondition -> Precondition Rule
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Precondition Rule
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- 2024-12-18T09:26:14.154Z
UPDATE AD_Field SET DisplayLogic='@PreconditionType/X@=R',Updated=TO_TIMESTAMP('2024-12-18 09:26:14.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734106
;

-- UI Element: Business Rule -> Business Rule Precondition.Beschreibung
-- Column: AD_BusinessRule_Precondition.Description
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10 -> description.Beschreibung
-- Column: AD_BusinessRule_Precondition.Description
-- 2024-12-18T09:26:43.691Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-12-18 09:26:43.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627413
;

-- UI Element: Business Rule -> Business Rule Precondition.Aktiv
-- Column: AD_BusinessRule_Precondition.IsActive
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_BusinessRule_Precondition.IsActive
-- 2024-12-18T09:26:43.698Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-12-18 09:26:43.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627414
;

-- UI Element: Business Rule -> Business Rule Precondition.Precondition Type
-- Column: AD_BusinessRule_Precondition.PreconditionType
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10 -> default.Precondition Type
-- Column: AD_BusinessRule_Precondition.PreconditionType
-- 2024-12-18T09:26:43.705Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-12-18 09:26:43.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627410
;

-- UI Element: Business Rule -> Business Rule Precondition.Precondition Rule
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10 -> default.Precondition Rule
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- 2024-12-18T09:26:43.713Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-12-18 09:26:43.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627412
;

-- UI Element: Business Rule -> Business Rule Precondition.Precondition SQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- UI Element: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> main -> 10 -> default.Precondition SQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- 2024-12-18T09:26:43.720Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-12-18 09:26:43.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627411
;

-- Field: Business Rule -> Business Rule Precondition -> Business Rule Precondition
-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_Precondition_ID
-- Field: Business Rule(541837,D) -> Business Rule Precondition(547700,D) -> Business Rule Precondition
-- Column: AD_BusinessRule_Precondition.AD_BusinessRule_Precondition_ID
-- 2024-12-18T09:26:59.559Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2024-12-18 09:26:59.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734105
;

-- UI Element: Business Rule -> Business Rule.Name
-- Column: AD_BusinessRule.Name
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10 -> default.Name
-- Column: AD_BusinessRule.Name
-- 2024-12-18T09:27:39.269Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-12-18 09:27:39.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627403
;

-- UI Element: Business Rule -> Business Rule.DB-Tabelle
-- Column: AD_BusinessRule.AD_Table_ID
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 10 -> default.DB-Tabelle
-- Column: AD_BusinessRule.AD_Table_ID
-- 2024-12-18T09:27:39.275Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-12-18 09:27:39.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627404
;

-- UI Element: Business Rule -> Business Rule.Aktiv
-- Column: AD_BusinessRule.IsActive
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_BusinessRule.IsActive
-- 2024-12-18T09:27:39.282Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-12-18 09:27:39.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627407
;

-- UI Element: Business Rule -> Business Rule.Mandant
-- Column: AD_BusinessRule.AD_Client_ID
-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20 -> org&client.Mandant
-- Column: AD_BusinessRule.AD_Client_ID
-- 2024-12-18T09:27:49.227Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-12-18 09:27:49.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627409
;

-- UI Element: Business Rule -> Business Rule Trigger.Source Table
-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> default.Source Table
-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- 2024-12-18T09:28:35.907Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734116,0,547701,552192,627415,'F',TO_TIMESTAMP('2024-12-18 09:28:35.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Source Table',10,0,0,TO_TIMESTAMP('2024-12-18 09:28:35.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10
-- UI Element Group: timing
-- 2024-12-18T09:28:43.965Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547682,552199,TO_TIMESTAMP('2024-12-18 09:28:43.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','timing',20,TO_TIMESTAMP('2024-12-18 09:28:43.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Trigger.On New
-- Column: AD_BusinessRule_Trigger.OnNew
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> timing.On New
-- Column: AD_BusinessRule_Trigger.OnNew
-- 2024-12-18T09:28:55.555Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734117,0,547701,552199,627416,'F',TO_TIMESTAMP('2024-12-18 09:28:54.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','On New',10,0,0,TO_TIMESTAMP('2024-12-18 09:28:54.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Trigger.On Update
-- Column: AD_BusinessRule_Trigger.OnUpdate
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> timing.On Update
-- Column: AD_BusinessRule_Trigger.OnUpdate
-- 2024-12-18T09:29:02.407Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734118,0,547701,552199,627417,'F',TO_TIMESTAMP('2024-12-18 09:29:02.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','On Update',20,0,0,TO_TIMESTAMP('2024-12-18 09:29:02.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Trigger.On Delete
-- Column: AD_BusinessRule_Trigger.OnDelete
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> timing.On Delete
-- Column: AD_BusinessRule_Trigger.OnDelete
-- 2024-12-18T09:29:08.372Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734119,0,547701,552199,627418,'F',TO_TIMESTAMP('2024-12-18 09:29:08.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','On Delete',30,0,0,TO_TIMESTAMP('2024-12-18 09:29:08.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10
-- UI Element Group: condition
-- 2024-12-18T09:29:39.312Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547682,552200,TO_TIMESTAMP('2024-12-18 09:29:39.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','condition',30,TO_TIMESTAMP('2024-12-18 09:29:39.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Trigger.Condition SQL
-- Column: AD_BusinessRule_Trigger.ConditionSQL
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> condition.Condition SQL
-- Column: AD_BusinessRule_Trigger.ConditionSQL
-- 2024-12-18T09:29:47.935Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734120,0,547701,552200,627419,'F',TO_TIMESTAMP('2024-12-18 09:29:46.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Condition SQL',10,0,0,TO_TIMESTAMP('2024-12-18 09:29:46.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main
-- UI Column: 20
-- 2024-12-18T09:29:52.575Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547684,546284,TO_TIMESTAMP('2024-12-18 09:29:52.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-12-18 09:29:52.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 20
-- UI Element Group: flags
-- 2024-12-18T09:30:00.161Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547684,552201,TO_TIMESTAMP('2024-12-18 09:29:59.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2024-12-18 09:29:59.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Trigger.Aktiv
-- Column: AD_BusinessRule_Trigger.IsActive
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_BusinessRule_Trigger.IsActive
-- 2024-12-18T09:30:08.941Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734113,0,547701,552201,627420,'F',TO_TIMESTAMP('2024-12-18 09:30:08.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-12-18 09:30:08.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 20
-- UI Element Group: mapping
-- 2024-12-18T09:30:14.537Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547684,552202,TO_TIMESTAMP('2024-12-18 09:30:14.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','mapping',20,TO_TIMESTAMP('2024-12-18 09:30:14.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Trigger.Target Record Mapping SQL
-- Column: AD_BusinessRule_Trigger.TargetRecordMappingSQL
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 20 -> mapping.Target Record Mapping SQL
-- Column: AD_BusinessRule_Trigger.TargetRecordMappingSQL
-- 2024-12-18T09:30:25.648Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734121,0,547701,552202,627421,'F',TO_TIMESTAMP('2024-12-18 09:30:25.471000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Target Record Mapping SQL',10,0,0,TO_TIMESTAMP('2024-12-18 09:30:25.471000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Business Rule -> Business Rule Trigger.Source Table
-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> default.Source Table
-- Column: AD_BusinessRule_Trigger.Source_Table_ID
-- 2024-12-18T09:30:50.358Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-12-18 09:30:50.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627415
;

-- UI Element: Business Rule -> Business Rule Trigger.On New
-- Column: AD_BusinessRule_Trigger.OnNew
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> timing.On New
-- Column: AD_BusinessRule_Trigger.OnNew
-- 2024-12-18T09:30:50.365Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-12-18 09:30:50.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627416
;

-- UI Element: Business Rule -> Business Rule Trigger.On Update
-- Column: AD_BusinessRule_Trigger.OnUpdate
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> timing.On Update
-- Column: AD_BusinessRule_Trigger.OnUpdate
-- 2024-12-18T09:30:50.372Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-12-18 09:30:50.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627417
;

-- UI Element: Business Rule -> Business Rule Trigger.On Delete
-- Column: AD_BusinessRule_Trigger.OnDelete
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> timing.On Delete
-- Column: AD_BusinessRule_Trigger.OnDelete
-- 2024-12-18T09:30:50.379Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-12-18 09:30:50.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627418
;

-- UI Element: Business Rule -> Business Rule Trigger.Aktiv
-- Column: AD_BusinessRule_Trigger.IsActive
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_BusinessRule_Trigger.IsActive
-- 2024-12-18T09:30:50.386Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-12-18 09:30:50.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627420
;

-- UI Element: Business Rule -> Business Rule Trigger.Condition SQL
-- Column: AD_BusinessRule_Trigger.ConditionSQL
-- UI Element: Business Rule(541837,D) -> Business Rule Trigger(547701,D) -> main -> 10 -> condition.Condition SQL
-- Column: AD_BusinessRule_Trigger.ConditionSQL
-- 2024-12-18T09:30:50.392Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-12-18 09:30:50.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627419
;

-- 2024-12-18T09:38:30.857Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583411,0,TO_TIMESTAMP('2024-12-18 09:38:30.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Business Rules','Business Rules',TO_TIMESTAMP('2024-12-18 09:38:30.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:38:30.860Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583411 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: Business Rules
-- Action Type: null
-- 2024-12-18T09:38:46.065Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,583411,542187,0,TO_TIMESTAMP('2024-12-18 09:38:45.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Business Rules','Y','N','N','N','Y','Business Rules',TO_TIMESTAMP('2024-12-18 09:38:45.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:38:46.066Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542187 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-12-18T09:38:46.069Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542187, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542187)
;

-- 2024-12-18T09:38:46.083Z
/* DDL */  select update_menu_translation_from_ad_element(583411) 
;

-- Reordering children of `System`
-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2024-12-18T09:38:54.313Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `Costing (Freight etc)`
-- 2024-12-18T09:38:54.316Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2024-12-18T09:38:54.316Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2024-12-18T09:38:54.317Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2024-12-18T09:38:54.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2024-12-18T09:38:54.319Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2024-12-18T09:38:54.320Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2024-12-18T09:38:54.321Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2024-12-18T09:38:54.322Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2024-12-18T09:38:54.323Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2024-12-18T09:38:54.324Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2024-12-18T09:38:54.324Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2024-12-18T09:38:54.325Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2024-12-18T09:38:54.326Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2024-12-18T09:38:54.326Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2024-12-18T09:38:54.327Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2024-12-18T09:38:54.328Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2024-12-18T09:38:54.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2024-12-18T09:38:54.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2024-12-18T09:38:54.330Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2024-12-18T09:38:54.330Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2024-12-18T09:38:54.331Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2024-12-18T09:38:54.332Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2024-12-18T09:38:54.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2024-12-18T09:38:54.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2024-12-18T09:38:54.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2024-12-18T09:38:54.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2024-12-18T09:38:54.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2024-12-18T09:38:54.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2024-12-18T09:38:54.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2024-12-18T09:38:54.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2024-12-18T09:38:54.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2024-12-18T09:38:54.339Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2024-12-18T09:38:54.339Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2024-12-18T09:38:54.340Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2024-12-18T09:38:54.341Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2024-12-18T09:38:54.342Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2024-12-18T09:38:54.342Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2024-12-18T09:38:54.343Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2024-12-18T09:38:54.344Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2024-12-18T09:38:54.344Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2024-12-18T09:38:54.345Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2024-12-18T09:38:54.346Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2024-12-18T09:38:54.347Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2024-12-18T09:38:54.348Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2024-12-18T09:38:54.348Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2024-12-18T09:38:54.349Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2024-12-18T09:38:54.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2024-12-18T09:38:54.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2024-12-18T09:38:54.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2024-12-18T09:38:54.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2024-12-18T09:38:54.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2024-12-18T09:38:54.353Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2024-12-18T09:38:54.354Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2024-12-18T09:38:54.355Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2024-12-18T09:38:54.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2024-12-18T09:38:54.357Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2024-12-18T09:38:54.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2024-12-18T09:38:54.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2024-12-18T09:38:54.360Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2024-12-18T09:38:54.361Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2024-12-18T09:38:54.362Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2024-12-18T09:38:54.363Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2024-12-18T09:38:54.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2024-12-18T09:38:54.364Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2024-12-18T09:38:54.365Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-12-18T09:38:54.365Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-12-18T09:38:54.366Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-12-18T09:38:54.367Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2024-12-18T09:38:54.367Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2024-12-18T09:38:54.368Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2024-12-18T09:38:54.369Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2024-12-18T09:38:54.370Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2024-12-18T09:38:54.371Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2024-12-18T09:38:54.371Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2024-12-18T09:38:54.372Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2024-12-18T09:38:54.373Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `Rebuild FactAcct Summary (de.metas.acct.process.Rebuild_FactAcctSummary)`
-- 2024-12-18T09:38:54.374Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542089 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2024-12-18T09:38:54.374Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2024-12-18T09:38:54.375Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Update purchase order highest price cache (de.metas.process.ExecuteUpdateSQL)`
-- 2024-12-18T09:38:54.376Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542158 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2024-12-18T09:38:54.376Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2024-12-18T09:38:54.377Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2024-12-18T09:38:54.377Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `Issues (AD_Issue)`
-- 2024-12-18T09:38:54.378Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542151 AND AD_Tree_ID=10
;

-- Node name: `Mobile`
-- 2024-12-18T09:38:54.378Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542179 AND AD_Tree_ID=10
;

-- Node name: `Business Rules`
-- 2024-12-18T09:38:54.379Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542187 AND AD_Tree_ID=10
;

-- Window: Business Rule, InternalName=businessRule
-- Window: Business Rule, InternalName=businessRule
-- 2024-12-18T09:39:59.953Z
UPDATE AD_Window SET InternalName='businessRule',Updated=TO_TIMESTAMP('2024-12-18 09:39:59.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541837
;

-- Name: Business Rule
-- Action Type: W
-- Window: Business Rule(541837,D)
-- 2024-12-18T09:40:07.440Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583397,542188,0,541837,TO_TIMESTAMP('2024-12-18 09:40:07.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','businessRule','Y','N','N','N','N','Business Rule',TO_TIMESTAMP('2024-12-18 09:40:07.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-18T09:40:07.442Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542188 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-12-18T09:40:07.443Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542188, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542188)
;

-- 2024-12-18T09:40:07.444Z
/* DDL */  select update_menu_translation_from_ad_element(583397) 
;

-- Reordering children of `Business Rules`
-- Node name: `Business Rule`
-- 2024-12-18T09:40:08.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=542187, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542188 AND AD_Tree_ID=10
;

-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- 2024-12-18T10:06:11.798Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-12-18 10:06:11.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589513
;

-- 2024-12-18T10:06:12.753Z
INSERT INTO t_alter_column values('ad_businessrule_precondition','Precondition_Rule_ID','NUMERIC(10)',null,null)
;

-- 2024-12-18T10:06:12.761Z
INSERT INTO t_alter_column values('ad_businessrule_precondition','Precondition_Rule_ID',null,'NULL',null)
;

-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- Column: AD_BusinessRule_Precondition.Precondition_Rule_ID
-- 2024-12-18T10:08:00.789Z
UPDATE AD_Column SET MandatoryLogic='@PreconditionType/X@=R',Updated=TO_TIMESTAMP('2024-12-18 10:08:00.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589513
;

-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- Column: AD_BusinessRule_Precondition.PreconditionSQL
-- 2024-12-18T10:08:05.519Z
UPDATE AD_Column SET MandatoryLogic='@PreconditionType/X@=S',Updated=TO_TIMESTAMP('2024-12-18 10:08:05.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589516
;

