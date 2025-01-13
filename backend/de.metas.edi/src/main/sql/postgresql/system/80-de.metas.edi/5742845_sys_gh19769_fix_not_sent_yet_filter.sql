-- Column: EDI_Desadv.IsShipmentsNotSent
-- Column SQL (old): (CASE WHEN (EXISTS(SELECT 1 FROM m_inout shipment WHERE shipment.edi_desadv_id = EDI_Desadv.edi_desadv_id AND shipment.edi_exportstatus NOT IN ('S'))) THEN 'Y' ELSE 'N' END)
-- Column: EDI_Desadv.IsShipmentsNotSent
-- Column SQL (old): (CASE WHEN (EXISTS(SELECT 1 FROM m_inout shipment WHERE shipment.edi_desadv_id = EDI_Desadv.edi_desadv_id AND shipment.edi_exportstatus NOT IN ('S'))) THEN 'Y' ELSE 'N' END)
-- 2025-01-10T15:55:59.666Z
UPDATE AD_Column SET ColumnSQL='(CASE WHEN (EXISTS(SELECT 1 FROM m_inout shipment WHERE shipment.edi_desadv_id = EDI_Desadv.edi_desadv_id AND shipment.edi_exportstatus NOT IN (''S'', ''N'') AND EDI_Desadv.edi_exportstatus NOT IN (''N''))) THEN ''Y'' ELSE ''N'' END)',Updated=TO_TIMESTAMP('2025-01-10 15:55:59.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589161
;

