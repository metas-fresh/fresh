-- Field: API Aufruf Revision -> API Aufruf Revision -> Http headers
-- Column: API_Request_Audit.HttpHeaders
-- Field: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> Http headers
-- Column: API_Request_Audit.HttpHeaders
-- 2025-01-14T09:30:07.122Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573844,734654,0,543896,TO_TIMESTAMP('2025-01-14 09:30:06.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10000,'D','Y','N','N','N','N','N','N','N','Http headers',TO_TIMESTAMP('2025-01-14 09:30:06.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-14T09:30:07.127Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734654 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-14T09:30:07.131Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579156) 
;

-- 2025-01-14T09:30:07.143Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734654
;

-- 2025-01-14T09:30:07.145Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734654)
;

-- UI Element: API Aufruf Revision -> API Aufruf Revision.Http headers
-- Column: API_Request_Audit.HttpHeaders
-- UI Element: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> body -> 10 -> body.Http headers
-- Column: API_Request_Audit.HttpHeaders
-- 2025-01-14T09:30:47.482Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734654,0,543896,545775,627796,'F',TO_TIMESTAMP('2025-01-14 09:30:47.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Http headers',20,0,0,TO_TIMESTAMP('2025-01-14 09:30:47.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: API Aufruf Revision -> API Aufruf Revision.Body
-- Column: API_Request_Audit.Body
-- UI Element: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> body -> 10 -> body.Body
-- Column: API_Request_Audit.Body
-- 2025-01-14T09:31:05.105Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2025-01-14 09:31:05.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=584450
;

-- UI Element: API Aufruf Revision -> API Aufruf Revision.Http headers
-- Column: API_Request_Audit.HttpHeaders
-- UI Element: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> body -> 10 -> body.Http headers
-- Column: API_Request_Audit.HttpHeaders
-- 2025-01-14T09:31:08.455Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2025-01-14 09:31:08.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627796
;

-- UI Element: API Aufruf Revision -> API Aufruf Revision.Http headers
-- Column: API_Request_Audit.HttpHeaders
-- UI Element: API Aufruf Revision(541127,D) -> API Aufruf Revision(543896,D) -> body -> 10 -> body.Http headers
-- Column: API_Request_Audit.HttpHeaders
-- 2025-01-14T09:31:20.743Z
UPDATE AD_UI_Element SET WidgetSize='XXL',Updated=TO_TIMESTAMP('2025-01-14 09:31:20.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627796
;

-- Column: API_Request_Audit.HttpHeaders
-- Column: API_Request_Audit.HttpHeaders
-- 2025-01-14T09:31:49.377Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-14 09:31:49.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=573844
;

-- Column: API_Request_Audit.Body
-- Column: API_Request_Audit.Body
-- 2025-01-14T09:32:04.578Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-01-14 09:32:04.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=573755
;

