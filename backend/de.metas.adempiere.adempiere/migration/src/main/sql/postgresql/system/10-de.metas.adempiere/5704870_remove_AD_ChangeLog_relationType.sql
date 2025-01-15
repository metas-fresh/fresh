-- Run mode: SWING_CLIENT

-- Column: AD_ChangeLog.Record_ID
-- 2023-10-03T07:09:09.259Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-10-03 10:09:09.259','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=8817
;

-- Name: AD_ChangeLog_TableRecordIdTarget
-- Source Reference: -
-- Target Reference: AD_ChangeLog
-- 2023-10-03T07:11:39.307Z
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-03 10:11:39.307','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540188
;

-- Name: AD_ChangeLog_TableRecordIdTarget
-- Source Reference: -
-- Target Reference: AD_ChangeLog
-- 2023-10-03T07:12:21.259Z
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540188
;

