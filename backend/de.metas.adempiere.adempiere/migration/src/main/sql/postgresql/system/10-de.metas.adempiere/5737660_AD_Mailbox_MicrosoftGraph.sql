-- Name: AD_MailBox_Type
-- 2024-10-24T08:11:25.594Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541904,TO_TIMESTAMP('2024-10-24 08:11:25.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','N','AD_MailBox_Type',TO_TIMESTAMP('2024-10-24 08:11:25.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2024-10-24T08:11:25.601Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541904 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_MailBox_Type
-- Value: smtp
-- ValueName: SMTP
-- 2024-10-24T08:11:53.162Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541904,543764,TO_TIMESTAMP('2024-10-24 08:11:53.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','SMTP',TO_TIMESTAMP('2024-10-24 08:11:53.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'smtp','SMTP')
;

-- 2024-10-24T08:11:53.165Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543764 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_MailBox_Type
-- Value: msgraph
-- ValueName: MSGraph
-- 2024-10-24T08:13:02.924Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541904,543765,TO_TIMESTAMP('2024-10-24 08:13:02.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','Microsoft Graph',TO_TIMESTAMP('2024-10-24 08:13:02.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'msgraph','MSGraph')
;

-- 2024-10-24T08:13:02.926Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543765 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: AD_MailBox.Type
-- Column: AD_MailBox.Type
-- 2024-10-24T08:13:49.365Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589356,600,0,17,541904,540242,'Type',TO_TIMESTAMP('2024-10-24 08:13:49.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','smtp','','de.metas.swat',0,20,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Art',0,0,TO_TIMESTAMP('2024-10-24 08:13:49.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-24T08:13:49.369Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589356 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-24T08:13:49.402Z
/* DDL */  select update_Column_Translation_From_AD_Element(600) 
;

-- 2024-10-24T08:13:50.178Z
/* DDL */ SELECT public.db_alter_table('AD_MailBox','ALTER TABLE public.AD_MailBox ADD COLUMN Type VARCHAR(20) DEFAULT ''smtp'' NOT NULL')
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> Art
-- Column: AD_MailBox.Type
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> Art
-- Column: AD_MailBox.Type
-- 2024-10-24T08:17:24.892Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589356,732006,0,540236,TO_TIMESTAMP('2024-10-24 08:17:24.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',20,'D','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2024-10-24 08:17:24.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-24T08:17:24.897Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-24T08:17:24.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2024-10-24T08:17:24.959Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732006
;

-- 2024-10-24T08:17:24.964Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732006)
;

-- UI Column: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10
-- UI Element Group: smtp
-- 2024-10-24T08:31:08.399Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541170,552050,TO_TIMESTAMP('2024-10-24 08:31:08.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','smtp',20,TO_TIMESTAMP('2024-10-24 08:31:08.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.EMail-Server
-- Column: AD_MailBox.SMTPHost
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> smtp.EMail-Server
-- Column: AD_MailBox.SMTPHost
-- 2024-10-24T08:32:32.628Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552050, SeqNo=10,Updated=TO_TIMESTAMP('2024-10-24 08:32:32.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553812
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.SMTP Port
-- Column: AD_MailBox.SMTPPort
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> smtp.SMTP Port
-- Column: AD_MailBox.SMTPPort
-- 2024-10-24T08:32:48.456Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552050, SeqNo=20,Updated=TO_TIMESTAMP('2024-10-24 08:32:48.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553818
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.SMTP Anmeldung
-- Column: AD_MailBox.IsSmtpAuthorization
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> smtp.SMTP Anmeldung
-- Column: AD_MailBox.IsSmtpAuthorization
-- 2024-10-24T08:33:19.637Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552050, SeqNo=30,Updated=TO_TIMESTAMP('2024-10-24 08:33:19.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553814
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.SMTP Login
-- Column: AD_MailBox.UserName
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> smtp.SMTP Login
-- Column: AD_MailBox.UserName
-- 2024-10-24T08:33:39.024Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552050, SeqNo=40,Updated=TO_TIMESTAMP('2024-10-24 08:33:39.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553815
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.SMTP Kennwort
-- Column: AD_MailBox.Password
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> smtp.SMTP Kennwort
-- Column: AD_MailBox.Password
-- 2024-10-24T08:33:47.174Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552050, SeqNo=50,Updated=TO_TIMESTAMP('2024-10-24 08:33:47.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553816
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.Start TLS
-- Column: AD_MailBox.IsStartTLS
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> smtp.Start TLS
-- Column: AD_MailBox.IsStartTLS
-- 2024-10-24T08:34:14.693Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552050, SeqNo=60,Updated=TO_TIMESTAMP('2024-10-24 08:34:14.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553817
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> SMTP Login
-- Column: AD_MailBox.UserName
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> SMTP Login
-- Column: AD_MailBox.UserName
-- 2024-10-24T08:35:26.798Z
UPDATE AD_Field SET DisplayLogic='@IsSmtpAuthorization/N@=Y',Updated=TO_TIMESTAMP('2024-10-24 08:35:26.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=545482
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> SMTP Kennwort
-- Column: AD_MailBox.Password
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> SMTP Kennwort
-- Column: AD_MailBox.Password
-- 2024-10-24T08:35:31.138Z
UPDATE AD_Field SET DisplayLogic='@IsSmtpAuthorization/N@=Y',Updated=TO_TIMESTAMP('2024-10-24 08:35:31.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=545478
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> SMTP Kennwort
-- Column: AD_MailBox.Password
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> SMTP Kennwort
-- Column: AD_MailBox.Password
-- 2024-10-24T08:36:18.807Z
UPDATE AD_Field SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-10-24 08:36:18.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=545478
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> SMTP Login
-- Column: AD_MailBox.UserName
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> SMTP Login
-- Column: AD_MailBox.UserName
-- 2024-10-24T08:36:22.743Z
UPDATE AD_Field SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-10-24 08:36:22.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=545482
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.Art
-- Column: AD_MailBox.Type
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> default.Art
-- Column: AD_MailBox.Type
-- 2024-10-24T08:37:52.035Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732006,0,540236,541876,626236,'F',TO_TIMESTAMP('2024-10-24 08:37:51.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Art',50,0,0,TO_TIMESTAMP('2024-10-24 08:37:51.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> EMail-Server
-- Column: AD_MailBox.SMTPHost
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> EMail-Server
-- Column: AD_MailBox.SMTPHost
-- 2024-10-24T08:38:34.678Z
UPDATE AD_Field SET DisplayLogic='@Type@=smtp',Updated=TO_TIMESTAMP('2024-10-24 08:38:34.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=545475
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> SMTP Anmeldung
-- Column: AD_MailBox.IsSmtpAuthorization
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> SMTP Anmeldung
-- Column: AD_MailBox.IsSmtpAuthorization
-- 2024-10-24T08:38:40.413Z
UPDATE AD_Field SET DisplayLogic='@Type@=smtp',Updated=TO_TIMESTAMP('2024-10-24 08:38:40.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=547486
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> SMTP Login
-- Column: AD_MailBox.UserName
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> SMTP Login
-- Column: AD_MailBox.UserName
-- 2024-10-24T08:38:46.479Z
UPDATE AD_Field SET DisplayLogic='@Type@=smtp & @IsSmtpAuthorization/N@=Y',Updated=TO_TIMESTAMP('2024-10-24 08:38:46.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=545482
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> SMTP Kennwort
-- Column: AD_MailBox.Password
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> SMTP Kennwort
-- Column: AD_MailBox.Password
-- 2024-10-24T08:38:50.043Z
UPDATE AD_Field SET DisplayLogic='@Type@=smtp & @IsSmtpAuthorization/N@=Y',Updated=TO_TIMESTAMP('2024-10-24 08:38:50.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=545478
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> Start TLS
-- Column: AD_MailBox.IsStartTLS
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> Start TLS
-- Column: AD_MailBox.IsStartTLS
-- 2024-10-24T08:38:53.334Z
UPDATE AD_Field SET DisplayLogic='@Type@=smtp',Updated=TO_TIMESTAMP('2024-10-24 08:38:53.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=563534
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> SMTP Port
-- Column: AD_MailBox.SMTPPort
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> SMTP Port
-- Column: AD_MailBox.SMTPPort
-- 2024-10-24T08:38:56.429Z
UPDATE AD_Field SET DisplayLogic='@Type@=smtp',Updated=TO_TIMESTAMP('2024-10-24 08:38:56.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=563533
;

-- Column: AD_MailBox.Password
-- Column: AD_MailBox.Password
-- 2024-10-24T20:01:06.861Z
UPDATE AD_Column SET MandatoryLogic='@Type/x@=smtp & @IsSmtpAuthorization@=Y',Updated=TO_TIMESTAMP('2024-10-24 20:01:06.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544218
;

-- Column: AD_MailBox.SMTPHost
-- Column: AD_MailBox.SMTPHost
-- 2024-10-24T20:01:44.386Z
UPDATE AD_Column SET IsMandatory='N', MandatoryLogic='@Type/x@=smtp',Updated=TO_TIMESTAMP('2024-10-24 20:01:44.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544216
;

-- 2024-10-24T20:01:56.717Z
INSERT INTO t_alter_column values('ad_mailbox','SMTPHost','VARCHAR(50)',null,null)
;

-- 2024-10-24T20:01:56.723Z
INSERT INTO t_alter_column values('ad_mailbox','SMTPHost',null,'NULL',null)
;

-- Column: AD_MailBox.SMTPPort
-- Column: AD_MailBox.SMTPPort
-- 2024-10-24T20:02:24.425Z
UPDATE AD_Column SET IsMandatory='N', MandatoryLogic='@Type/x@=smtp',Updated=TO_TIMESTAMP('2024-10-24 20:02:24.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=559714
;

-- Column: AD_MailBox.UserName
-- Column: AD_MailBox.UserName
-- 2024-10-24T20:04:04.864Z
UPDATE AD_Column SET MandatoryLogic='@Type/x@=smtp & @IsSmtpAuthorization@=Y',Updated=TO_TIMESTAMP('2024-10-24 20:04:04.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544217
;





















































-- 2024-10-25T05:57:53.903Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583348,0,'MSGRAPH_ClientId',TO_TIMESTAMP('2024-10-25 05:57:53.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Client ID','Client ID',TO_TIMESTAMP('2024-10-25 05:57:53.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-25T05:57:53.912Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583348 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-10-25T05:58:13.072Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583349,0,'MSGRAPH_TenantId',TO_TIMESTAMP('2024-10-25 05:58:12.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Tenant ID','Tenant ID',TO_TIMESTAMP('2024-10-25 05:58:12.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-25T05:58:13.074Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583349 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-10-25T05:58:35.012Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583350,0,'MSGRAPH_ClientSecret',TO_TIMESTAMP('2024-10-25 05:58:34.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Client Secret','Client Secret',TO_TIMESTAMP('2024-10-25 05:58:34.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-25T05:58:35.015Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583350 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_MailBox.MSGRAPH_ClientId
-- Column: AD_MailBox.MSGRAPH_ClientId
-- 2024-10-25T05:59:30.255Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589358,583348,0,10,540242,'MSGRAPH_ClientId',TO_TIMESTAMP('2024-10-25 05:59:30.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.swat',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@Type/x@=msgraph',0,'Client ID',0,0,TO_TIMESTAMP('2024-10-25 05:59:30.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-25T05:59:30.257Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589358 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-25T05:59:30.291Z
/* DDL */  select update_Column_Translation_From_AD_Element(583348) 
;

-- 2024-10-25T05:59:31.071Z
/* DDL */ SELECT public.db_alter_table('AD_MailBox','ALTER TABLE public.AD_MailBox ADD COLUMN MSGRAPH_ClientId VARCHAR(40)')
;

-- Column: AD_MailBox.MSGRAPH_TenantId
-- Column: AD_MailBox.MSGRAPH_TenantId
-- 2024-10-25T05:59:45.550Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589359,583349,0,10,540242,'MSGRAPH_TenantId',TO_TIMESTAMP('2024-10-25 05:59:45.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.swat',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@Type/x@=msgraph',0,'Tenant ID',0,0,TO_TIMESTAMP('2024-10-25 05:59:45.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-25T05:59:45.552Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589359 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-25T05:59:45.555Z
/* DDL */  select update_Column_Translation_From_AD_Element(583349) 
;

-- 2024-10-25T05:59:46.142Z
/* DDL */ SELECT public.db_alter_table('AD_MailBox','ALTER TABLE public.AD_MailBox ADD COLUMN MSGRAPH_TenantId VARCHAR(40)')
;

-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- 2024-10-25T05:59:59.742Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589360,583350,0,10,540242,'MSGRAPH_ClientSecret',TO_TIMESTAMP('2024-10-25 05:59:59.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.swat',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@Type/x@=msgraph',0,'Client Secret',0,0,TO_TIMESTAMP('2024-10-25 05:59:59.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-25T05:59:59.745Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589360 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-25T05:59:59.747Z
/* DDL */  select update_Column_Translation_From_AD_Element(583350) 
;

-- 2024-10-25T06:00:03.765Z
/* DDL */ SELECT public.db_alter_table('AD_MailBox','ALTER TABLE public.AD_MailBox ADD COLUMN MSGRAPH_ClientSecret VARCHAR(40)')
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> Client ID
-- Column: AD_MailBox.MSGRAPH_ClientId
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> Client ID
-- Column: AD_MailBox.MSGRAPH_ClientId
-- 2024-10-25T06:00:27.698Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589358,732008,0,540236,TO_TIMESTAMP('2024-10-25 06:00:27.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','Client ID',TO_TIMESTAMP('2024-10-25 06:00:27.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-25T06:00:27.700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-25T06:00:27.702Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583348) 
;

-- 2024-10-25T06:00:27.714Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732008
;

-- 2024-10-25T06:00:27.716Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732008)
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> Tenant ID
-- Column: AD_MailBox.MSGRAPH_TenantId
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> Tenant ID
-- Column: AD_MailBox.MSGRAPH_TenantId
-- 2024-10-25T06:00:27.832Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589359,732009,0,540236,TO_TIMESTAMP('2024-10-25 06:00:27.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','Tenant ID',TO_TIMESTAMP('2024-10-25 06:00:27.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-25T06:00:27.834Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-25T06:00:27.835Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583349) 
;

-- 2024-10-25T06:00:27.838Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732009
;

-- 2024-10-25T06:00:27.840Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732009)
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> Client Secret
-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> Client Secret
-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- 2024-10-25T06:00:27.955Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589360,732010,0,540236,TO_TIMESTAMP('2024-10-25 06:00:27.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','Client Secret',TO_TIMESTAMP('2024-10-25 06:00:27.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-25T06:00:27.957Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-25T06:00:27.959Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583350) 
;

-- 2024-10-25T06:00:27.963Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732010
;

-- 2024-10-25T06:00:27.964Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732010)
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> Client ID
-- Column: AD_MailBox.MSGRAPH_ClientId
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> Client ID
-- Column: AD_MailBox.MSGRAPH_ClientId
-- 2024-10-25T06:00:39.759Z
UPDATE AD_Field SET DisplayLogic='@Type/x@=msgraph',Updated=TO_TIMESTAMP('2024-10-25 06:00:39.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=732008
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> Tenant ID
-- Column: AD_MailBox.MSGRAPH_TenantId
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> Tenant ID
-- Column: AD_MailBox.MSGRAPH_TenantId
-- 2024-10-25T06:00:42.538Z
UPDATE AD_Field SET DisplayLogic='@Type/x@=msgraph',Updated=TO_TIMESTAMP('2024-10-25 06:00:42.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=732009
;

-- Field: EMail-Ausgangsserver (SMTP) -> Mail -> Client Secret
-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- Field: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> Client Secret
-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- 2024-10-25T06:00:44.994Z
UPDATE AD_Field SET DisplayLogic='@Type/x@=msgraph',Updated=TO_TIMESTAMP('2024-10-25 06:00:44.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=732010
;

-- UI Column: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10
-- UI Element Group: microsoft graph
-- 2024-10-25T06:00:58.139Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541170,552051,TO_TIMESTAMP('2024-10-25 06:00:57.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','microsoft graph',30,TO_TIMESTAMP('2024-10-25 06:00:57.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.Client ID
-- Column: AD_MailBox.MSGRAPH_ClientId
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> microsoft graph.Client ID
-- Column: AD_MailBox.MSGRAPH_ClientId
-- 2024-10-25T06:01:08.278Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732008,0,540236,552051,626240,'F',TO_TIMESTAMP('2024-10-25 06:01:08.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Client ID',10,0,0,TO_TIMESTAMP('2024-10-25 06:01:08.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.Tenant ID
-- Column: AD_MailBox.MSGRAPH_TenantId
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> microsoft graph.Tenant ID
-- Column: AD_MailBox.MSGRAPH_TenantId
-- 2024-10-25T06:01:13.814Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732009,0,540236,552051,626241,'F',TO_TIMESTAMP('2024-10-25 06:01:13.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Tenant ID',20,0,0,TO_TIMESTAMP('2024-10-25 06:01:13.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: EMail-Ausgangsserver (SMTP) -> Mail.Client Secret
-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- UI Element: EMail-Ausgangsserver (SMTP)(540080,de.metas.swat) -> Mail(540236,D) -> main -> 10 -> microsoft graph.Client Secret
-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- 2024-10-25T06:01:19.968Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732010,0,540236,552051,626242,'F',TO_TIMESTAMP('2024-10-25 06:01:19.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Client Secret',30,0,0,TO_TIMESTAMP('2024-10-25 06:01:19.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- Column: AD_MailBox.MSGRAPH_ClientSecret
-- 2024-10-25T07:08:48.159Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2024-10-25 07:08:48.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589360
;

-- 2024-10-25T07:08:48.707Z
INSERT INTO t_alter_column values('ad_mailbox','MSGRAPH_ClientSecret','VARCHAR(255)',null,null)
;

-- Column: AD_MailBox.MSGRAPH_ClientId
-- Column: AD_MailBox.MSGRAPH_ClientId
-- 2024-10-25T07:08:56.509Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2024-10-25 07:08:56.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589358
;

-- 2024-10-25T07:08:56.979Z
INSERT INTO t_alter_column values('ad_mailbox','MSGRAPH_ClientId','VARCHAR(255)',null,null)
;

-- Column: AD_MailBox.MSGRAPH_TenantId
-- Column: AD_MailBox.MSGRAPH_TenantId
-- 2024-10-25T07:09:06.685Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2024-10-25 07:09:06.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589359
;

-- 2024-10-25T07:09:07.174Z
INSERT INTO t_alter_column values('ad_mailbox','MSGRAPH_TenantId','VARCHAR(255)',null,null)
;

