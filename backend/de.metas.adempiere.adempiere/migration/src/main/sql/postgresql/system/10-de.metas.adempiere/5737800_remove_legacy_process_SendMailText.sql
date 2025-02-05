-- Name: EMail-Text senden
-- Action Type: P
-- Process: R_MailText Send(org.compiere.process.SendMailText)
-- 2024-10-25T12:23:52.843Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=396
;

-- 2024-10-25T12:23:52.849Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=396
;

-- 2024-10-25T12:23:52.854Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=396 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: R_MailText Send
-- Classname: org.compiere.process.SendMailText
-- 2024-10-25T12:23:58.941Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=209
;

-- 2024-10-25T12:23:58.945Z
DELETE FROM AD_Process WHERE AD_Process_ID=209
;

