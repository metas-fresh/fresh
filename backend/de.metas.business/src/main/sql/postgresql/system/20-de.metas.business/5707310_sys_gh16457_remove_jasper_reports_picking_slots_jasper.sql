UPDATE ad_archive
SET ad_process_id = NULL
WHERE ad_process_id = 540818
;

UPDATE c_printing_queue
SET ad_process_id = NULL
WHERE ad_process_id = 540818
;

DELETE
FROM ad_table_process
WHERE ad_process_id = 540818
;

DELETE
FROM AD_Process_Trl
WHERE AD_Process_ID = 540818
;

-- 2023-10-16T22:09:31.218Z
DELETE
FROM AD_Process
WHERE AD_Process_ID = 540818
;

