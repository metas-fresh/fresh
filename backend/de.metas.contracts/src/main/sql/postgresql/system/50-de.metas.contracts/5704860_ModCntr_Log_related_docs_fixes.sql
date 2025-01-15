-- Run mode: SWING_CLIENT

-- Column: ModCntr_Log.Record_ID
-- 2023-10-03T06:27:44.516Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-10-03 09:27:44.515','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586774
;

-- Column: ModCntr_Log_Status.Record_ID
-- 2023-10-03T06:28:58.319Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-10-03 09:28:58.318','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587340
;

-- Name: ModCntr_Log_TableRecordIdTarget
-- Source Reference: -
-- Target Reference: ModCntr_Log
-- 2023-10-03T06:36:40.671Z
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-03 09:36:40.671','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540407
;

-- Name: PP_Order -> ModCntr_Log
-- Source Reference: PP_Order
-- Target Reference: ModCntr_Log_Target_PP_Order
-- 2023-10-03T06:36:52.241Z
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-03 09:36:52.241','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540416
;

