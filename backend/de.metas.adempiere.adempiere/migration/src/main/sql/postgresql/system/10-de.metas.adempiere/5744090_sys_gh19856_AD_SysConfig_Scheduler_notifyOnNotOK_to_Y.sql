
--
-- make the scheduler notify users on error "in general"
--
-- org.compiere.server.Scheduler.notifyOnNotOK 
-- 2025-01-20T09:57:27.529Z
UPDATE AD_SysConfig SET Value='Y', ConfigurationLevel='S', Description='Notify the supervisor (!! only if one is set via AD_Scheduler.SuperVisor_ID !!) if a process, report etc run by AD_Scheduler failed.',Updated=TO_TIMESTAMP('2025-01-20 10:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540918
;
