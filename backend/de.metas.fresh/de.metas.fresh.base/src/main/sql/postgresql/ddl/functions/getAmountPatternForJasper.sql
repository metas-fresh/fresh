DROP FUNCTION IF EXISTS report.getAmountPatternForJasper(numeric)
;

CREATE FUNCTION report.getAmountPatternForJasper(p_currency_id numeric)
    RETURNS TABLE
            (
                AmountPattern text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT (CASE
            WHEN cu.stdprecision < 1
                THEN '#,########0'
            ELSE SUBSTRING('#,##0.000000000' FROM 0 FOR 7 + cu.stdprecision :: INTEGER)
    END)
           AS AmountPattern
FROM C_Currency cu
WHERE cu.C_Currency_ID = p_currency_id
$$
;

