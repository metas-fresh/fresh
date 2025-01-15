DROP VIEW IF EXISTS dynamic_target_window_v
;

CREATE VIEW dynamic_target_window_v AS
SELECT t.tablename                                AS target_tableName,
       c.columnname                               AS target_columnName,
       c.columnsql                                AS target_columnSql,
       f.name                                     AS target_columnDisplayName,
       (SELECT ARRAY_AGG(ARRAY [ftrl.ad_language, ftrl.name])
        FROM ad_field_trl ftrl
        WHERE ftrl.ad_field_id = f.ad_field_id)   AS target_columnDisplayName_trls,
       (CASE
            WHEN EXISTS(SELECT 1
                        FROM ad_column zz
                        WHERE zz.ad_table_id = t.ad_table_id
                          AND zz.columnname = 'IsSOTrx'
                          AND zz.isactive = 'Y'
                          AND zz.ad_reference_id = 20)
                THEN 'Y'
                ELSE 'N'
        END)                                      AS target_has_issotrx_column,
       --
       tt.whereclause                             AS tab_whereClause,
       w.ad_window_id                             AS target_window_id,
       w.name                                     AS target_window_name,
       (SELECT ARRAY_AGG(ARRAY [wtrl.ad_language, wtrl.name])
        FROM ad_window_trl wtrl
        WHERE wtrl.ad_window_id = w.ad_window_id) AS target_window_name_trls,
       COALESCE(w.internalname, w.name)           AS target_window_internalname
FROM ad_column c
         INNER JOIN ad_table t ON t.ad_table_id = c.ad_table_id
         INNER JOIN ad_tab tt ON tt.ad_table_id = t.ad_table_id AND tt.isactive = 'Y' AND tt.seqno = 10 AND tt.tablevel = 0
         INNER JOIN AD_Field f ON f.ad_column_id = c.ad_column_id AND f.ad_tab_id = tt.ad_tab_id AND f.isactive = 'Y'
         INNER JOIN ad_window w ON w.ad_window_id = tt.ad_window_id AND w.isactive = 'Y' AND w.IsExcludeFromZoomTargets = 'N'
WHERE c.columnname = 'Record_ID'
  AND c.isactive = 'Y'
  AND t.isactive = 'Y'
  AND EXISTS (SELECT 1 FROM ad_column z WHERE z.ad_table_id = c.ad_table_id AND z.columnname = 'AD_Table_ID')
  AND COALESCE(NULLIF(f.IsExcludeFromZoomTargets, ''), c.IsExcludeFromZoomTargets) = 'N'
;

-- select * from dynamic_target_window_v;

