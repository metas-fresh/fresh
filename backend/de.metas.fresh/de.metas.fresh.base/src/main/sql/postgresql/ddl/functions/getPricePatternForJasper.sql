DROP FUNCTION IF EXISTS report.getPricePatternForJasper(numeric)
;

CREATE FUNCTION report.getPricePatternForJasper(p_record_id numeric)
    RETURNS TABLE
            (
                PricePattern text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT (CASE
            WHEN pl.priceprecision < 1
                THEN '#,########0'
            ELSE SUBSTRING('#,##0.000000000' FROM 0 FOR 7 + pl.priceprecision :: INTEGER)
    END)
           AS PricePattern
FROM M_PriceList pl
WHERE pl.m_pricelist_id = p_record_id
$$
;

