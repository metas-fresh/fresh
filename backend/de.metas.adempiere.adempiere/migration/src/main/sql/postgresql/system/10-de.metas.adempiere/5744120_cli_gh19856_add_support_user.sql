
--
-- Insert a dedicated suppport-user
--
-- 2025-01-20T08:44:24.097Z
INSERT INTO AD_User (AD_Client_ID,AD_Language,AD_Org_ID,AD_User_ID,Created,CreatedBy,Firstname,Fresh_Gift,IsAccountLocked,IsActive,IsAuthorizedSignatory,IsBillToContact_Default,IsDecider,IsDefaultContact,IsFullBPAccess,IsInPayroll,IsLoginAsHostKey,IsManagement,IsMembershipContact,IsMFProcurementUser,IsMultiplier,IsNewsletter,IsPurchaseContact,IsPurchaseContact_Default,IsSalesContact,IsSalesContact_Default,IsShipToContact_Default,IsSubjectMatterContact,IsSystemUser,Name,NotificationType,Processing,SeqNo,Title,Updated,UpdatedBy,Value) VALUES (1000000,'de_DE',1000000,540186,TO_TIMESTAMP('2025-01-20 09:44:24','YYYY-MM-DD HH24:MI:SS'),100,'Automatik-Benutzer','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Automatik-Benutzer','N','N',10,'',TO_TIMESTAMP('2025-01-20 09:44:24','YYYY-MM-DD HH24:MI:SS'),100,'1000079')
;

-- 2025-01-20T08:44:29.575Z
UPDATE AD_User SET Lastname='Support', Name='Support, Automatik-Benutzer',Updated=TO_TIMESTAMP('2025-01-20 09:44:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540186
;


-- 2025-01-20T08:45:07.223Z
UPDATE AD_User SET Value='supportm',Updated=TO_TIMESTAMP('2025-01-20 09:45:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540186
;

-- 2025-01-20T08:46:18.044Z
UPDATE AD_User SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-01-20 09:46:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540186
;

-- 2025-01-20T08:46:30.321Z
UPDATE AD_User SET Lastname='Support-Mail', Name='Support-Mail, Automatik-Benutzer',Updated=TO_TIMESTAMP('2025-01-20 09:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=540186
;

