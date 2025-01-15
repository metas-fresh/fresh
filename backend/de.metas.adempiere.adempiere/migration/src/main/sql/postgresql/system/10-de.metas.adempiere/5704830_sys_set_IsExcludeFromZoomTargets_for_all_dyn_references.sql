-- To preserve backwards compatibility,
-- we set IsExcludeFromZoomTargets=Y for all Record_ID columns.
-- Later, the developer, is free to un-tick for the columns he/she want.

DROP TABLE IF EXISTS tmp_columns_to_update
;

CREATE TEMPORARY TABLE tmp_columns_to_update AS
SELECT t.tablename,
       c.columnname,
       c.ad_column_id
FROM ad_column c
         INNER JOIN ad_table t ON t.ad_table_id = c.ad_table_id
         INNER JOIN ad_tab tt ON tt.ad_table_id = t.ad_table_id AND tt.isactive = 'Y' AND tt.seqno = 10 AND tt.tablevel = 0
         INNER JOIN AD_Field f ON f.ad_column_id = c.ad_column_id AND f.ad_tab_id = tt.ad_tab_id AND f.isactive = 'Y'
WHERE c.columnname = 'Record_ID'
  AND c.isactive = 'Y'
  AND t.isactive = 'Y'
  AND EXISTS (SELECT 1 FROM ad_column z WHERE z.ad_table_id = c.ad_table_id AND z.columnname = 'AD_Table_ID')
  AND COALESCE(NULLIF(f.IsExcludeFromZoomTargets, ''), c.IsExcludeFromZoomTargets) = 'N'
;

-- SELECT * FROM tmp_columns_to_update;


UPDATE ad_column c
SET IsExcludeFromZoomTargets='Y', updated='2023-10-02', updatedby=99
FROM tmp_columns_to_update s
WHERE s.ad_column_id = c.ad_column_id
;
