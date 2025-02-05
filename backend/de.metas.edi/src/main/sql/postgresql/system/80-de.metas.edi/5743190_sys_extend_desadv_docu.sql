
-- EDI_Exp_Desadv
UPDATE exp_format
SET description='If SysConfig de.metas.edi.OneDesadvPerShipment=N, then this export-format needs to be active', updated=TO_TIMESTAMP('2025-01-13 16:12:06', 'YYYY-MM-DD HH24:MI:SS'), updatedby=100
WHERE exp_format.exp_format_id = 540405
;

-- EXP_M_InOut_Desadv_V
UPDATE exp_format
SET description='If SysConfig de.metas.edi.OneDesadvPerShipment=Y, then this export-format needs to be active', updated=TO_TIMESTAMP('2025-01-13 16:12:06', 'YYYY-MM-DD HH24:MI:SS'), updatedby=100
WHERE exp_format.exp_format_id = 540428
;
