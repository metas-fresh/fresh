-- 2024-09-24T19:11:58.811Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583286,0,'CC_User_ID',TO_TIMESTAMP('2024-09-24 19:11:58.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','CC Nutzer/Kontakt','CC Nutzer/Kontakt',TO_TIMESTAMP('2024-09-24 19:11:58.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-24T19:11:58.817Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583286 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CC_User_ID
-- 2024-09-24T19:12:14.872Z
UPDATE AD_Element_Trl SET Name='CC User', PrintName='CC User',Updated=TO_TIMESTAMP('2024-09-24 19:12:14.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='en_US'
;

-- 2024-09-24T19:12:14.899Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'en_US') 
;

-- Name: AD_User BPartner with mail
-- 2024-09-24T19:14:08.123Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540691,'AD_User.C_BPartner_ID=@C_BPartner_ID@ AND COALESCE (AD_User.Email, "") != ""',TO_TIMESTAMP('2024-09-24 19:14:08.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','AD_User BPartner with mail','S',TO_TIMESTAMP('2024-09-24 19:14:08.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: AD_User.CC_User_ID
-- Column: AD_User.CC_User_ID
-- 2024-09-24T19:14:25.890Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589163,583286,0,30,110,114,540691,'CC_User_ID',TO_TIMESTAMP('2024-09-24 19:14:25.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','CC Nutzer/Kontakt',0,TO_TIMESTAMP('2024-09-24 19:14:25.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-09-24T19:14:25.891Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589163 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-24T19:14:25.895Z
/* DDL */  select update_Column_Translation_From_AD_Element(583286) 
;

-- 2024-09-24T19:14:30.765Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN CC_User_ID NUMERIC(10)')
;

-- 2024-09-24T19:14:31.135Z
ALTER TABLE AD_User ADD CONSTRAINT CCUser_ADUser FOREIGN KEY (CC_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: Geschäftspartner -> Nutzer / Kontakt -> CC Nutzer/Kontakt
-- Column: AD_User.CC_User_ID
-- Field: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> CC Nutzer/Kontakt
-- Column: AD_User.CC_User_ID
-- 2024-09-24T19:25:44.728Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589163,731780,0,496,0,TO_TIMESTAMP('2024-09-24 19:25:44.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','Y','Y','N','N','N','N','N','CC Nutzer/Kontakt',0,320,0,1,1,TO_TIMESTAMP('2024-09-24 19:25:44.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-24T19:25:44.729Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731780 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-24T19:25:44.731Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583286) 
;

-- 2024-09-24T19:25:44.742Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731780
;

-- 2024-09-24T19:25:44.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731780)
;

-- UI Element: Geschäftspartner -> Nutzer / Kontakt.CC Nutzer/Kontakt
-- Column: AD_User.CC_User_ID
-- UI Element: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.CC Nutzer/Kontakt
-- Column: AD_User.CC_User_ID
-- 2024-09-24T19:30:02.583Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731780,0,496,540578,626088,'F',TO_TIMESTAMP('2024-09-24 19:30:02.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'CC Nutzer/Kontakt',165,0,0,TO_TIMESTAMP('2024-09-24 19:30:02.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: AD_User BPartner with mail
-- 2024-09-24T19:50:55.637Z
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=@C_BPartner_ID@ AND COALESCE(AD_User.Email, "") != ""',Updated=TO_TIMESTAMP('2024-09-24 19:50:55.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540691
;

-- Name: AD_User BPartner with mail
-- 2024-09-24T19:52:36.395Z
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=@C_BPartner_ID@ AND COALESCE(AD_User.Email, '''') !=''''',Updated=TO_TIMESTAMP('2024-09-24 19:52:36.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540691
;

-- Name: AD_User BPartner with mail
-- 2024-09-24T19:53:32.361Z
UPDATE AD_Val_Rule SET Code='AD_User.C_BPartner_ID=@C_BPartner_ID@ AND COALESCE(AD_User.Email, '''') !='''' AND AD_User.AD_User_ID != @AD_User_ID@',Updated=TO_TIMESTAMP('2024-09-24 19:53:32.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540691
;








-- Element: CC_User_ID
-- 2024-09-27T09:36:42.047Z
UPDATE AD_Element_Trl SET Description='Selet a user that will receive emails as CC  if the current user receives a mail. This is only applied to emails and not notifications.', Help='Selet a user that will receive emails as CC  if the current user receives a mail. This is only applied to emails and not notifications.',Updated=TO_TIMESTAMP('2024-09-27 09:36:42.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='en_US'
;

-- 2024-09-27T09:36:42.075Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'en_US') 
;

-- Element: CC_User_ID
-- 2024-09-27T09:39:31.490Z
UPDATE AD_Element_Trl SET Description='Select a user that will receive emails as CC  if the current user receives a mail. This is only applied to emails and not notifications.', Help='Select a user that will receive emails as CC  if the current user receives a mail. This is only applied to emails and not notifications.',Updated=TO_TIMESTAMP('2024-09-27 09:39:31.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='en_US'
;

-- 2024-09-27T09:39:31.493Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'en_US') 
;

-- Element: CC_User_ID
-- 2024-09-27T09:40:21.402Z
UPDATE AD_Element_Trl SET Description='Select a user that will receive emails as CC  if the current user receives a mail. This is only applied to emails and not notifications.', Help='Select a user that will receive emails as CC  if the current user receives a mail. This is only applied to emails and not notifications.',Updated=TO_TIMESTAMP('2024-09-27 09:40:21.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='de_DE'
;

-- 2024-09-27T09:40:21.404Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583286,'de_DE') 
;

-- 2024-09-27T09:40:21.405Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'de_DE') 
;

-- Element: CC_User_ID
-- 2024-09-27T09:40:47.189Z
UPDATE AD_Element_Trl SET Description='Select a user that will receive emails as CC  if the current user receives a mail. This is only applied to emails and not notifications.', Help='Select a user that will receive emails as CC  if the current user receives a mail. This is only applied to emails and not notifications.',Updated=TO_TIMESTAMP('2024-09-27 09:40:47.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='de_CH'
;

-- 2024-09-27T09:40:47.191Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'de_CH') 
;

-- Element: CC_User_ID
-- 2024-09-27T13:18:27.263Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mail-CC an Nutzer', PrintName='Mail-CC an Nutzer',Updated=TO_TIMESTAMP('2024-09-27 13:18:27.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='de_DE'
;

-- 2024-09-27T13:18:27.267Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583286,'de_DE') 
;

-- 2024-09-27T13:18:27.272Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'de_DE') 
;

-- Element: CC_User_ID
-- 2024-09-27T13:18:39.672Z
UPDATE AD_Element_Trl SET Name='Mail-CC an Nutzer', PrintName='Mail-CC an Nutzer',Updated=TO_TIMESTAMP('2024-09-27 13:18:39.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='de_CH'
;

-- 2024-09-27T13:18:39.675Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'de_CH') 
;

-- Element: CC_User_ID
-- 2024-09-27T13:18:54.701Z
UPDATE AD_Element_Trl SET Description='Wählen Sie einen Benutzer aus, der E-Mails als CC erhalten soll, wenn der aktuelle Benutzer eine E-Mail erhält. Dies wird nur auf E-Mails und nicht auf Benachrichtigungen angewendet.`', Help='Wählen Sie einen Benutzer aus, der E-Mails als CC erhalten soll, wenn der aktuelle Benutzer eine E-Mail erhält. Dies wird nur auf E-Mails und nicht auf Benachrichtigungen angewendet.`',Updated=TO_TIMESTAMP('2024-09-27 13:18:54.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='de_CH'
;

-- 2024-09-27T13:18:54.712Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'de_CH') 
;

-- Element: CC_User_ID
-- 2024-09-27T13:19:02.412Z
UPDATE AD_Element_Trl SET Description='Wählen Sie einen Benutzer aus, der E-Mails als CC erhalten soll, wenn der aktuelle Benutzer eine E-Mail erhält. Dies wird nur auf E-Mails und nicht auf Benachrichtigungen angewendet.`', Help='Wählen Sie einen Benutzer aus, der E-Mails als CC erhalten soll, wenn der aktuelle Benutzer eine E-Mail erhält. Dies wird nur auf E-Mails und nicht auf Benachrichtigungen angewendet.`',Updated=TO_TIMESTAMP('2024-09-27 13:19:02.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583286 AND AD_Language='de_DE'
;

-- 2024-09-27T13:19:02.415Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583286,'de_DE') 
;

-- 2024-09-27T13:19:02.417Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583286,'de_DE') 
;

