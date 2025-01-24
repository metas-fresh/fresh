-- View: edi_cctop_901_991_v

DROP VIEW IF EXISTS edi_cctop_901_991_v
;

CREATE OR REPLACE VIEW edi_cctop_901_991_v AS
SELECT i.c_invoice_id                                                                                       AS edi_cctop_901_991_v_id,
       i.c_invoice_id,
       i.c_invoice_id                                                                                       AS edi_cctop_invoic_v_id,
       SUM(it.taxamt + it.taxbaseamt)                                                                       AS TotalAmt,
       SUM(it.taxamt)                                                                                       AS TaxAmt,
       SUM(it.taxbaseamt)                                                                                   AS TaxBaseAmt,
       t.Rate /* we support the case of having two different C_Tax_IDs with the same tax-rate */,
       t.IsTaxExempt,
       i.ad_client_id,
       i.ad_org_id,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.isactive,
       REGEXP_REPLACE(rn.referenceno, '\s+$', '')                                                           AS ESRReferenceNumber,
       ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2)                                                 AS SurchargeAmt, -- may be be positve or negative
       ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2) + SUM(it.TaxBaseAmt)                            AS TaxBaseAmtWithSurchargeAmt,
       ROUND((ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2) + SUM(it.TaxBaseAmt)) * t.rate / 100, 2) AS TaxAmtWithSurchargeAmt,
       CASE
           WHEN EXISTS (SELECT SUM(it_bigger.taxbaseamt), t.rate
                        FROM c_invoicetax it_bigger
                                 JOIN c_tax t_bigger ON it_bigger.c_tax_id = t_bigger.c_tax_id
                        WHERE it_bigger.isactive = 'Y'
                          AND it_bigger.c_invoice_id = i.c_invoice_id
                        GROUP BY t_bigger.rate
                        HAVING SUM(it_bigger.taxbaseamt) > SUM(it.taxbaseamt))
               THEN 'N' /*If there is a bigger tax-base-amount, then this is not the main tax*/
               ELSE 'Y'
       END                                                                                                  AS IsMainVAT     -- we mark the tax-rate with the biggest baseAmt as the invoice's main-tax
FROM c_invoice i
         LEFT JOIN c_invoicetax it ON it.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax t ON t.c_tax_id = it.c_tax_id
         LEFT JOIN (SELECT sit.c_invoice_id,
                           COUNT(DISTINCT st.rate
                           ) AS tax_count
                    FROM c_invoicetax sit
                             LEFT JOIN c_tax st ON st.c_tax_id = sit.c_tax_id
                    GROUP BY sit.c_invoice_id) tc ON tc.c_invoice_id = it.c_invoice_id
         LEFT JOIN c_referenceno_doc rnd ON rnd.record_id = i.c_invoice_id AND rnd.ad_table_id = 318 /* C_Invoice DocumentType */
         LEFT JOIN c_referenceno rn ON rn.c_referenceno_id = rnd.c_referenceno_id
         LEFT JOIN C_PaymentTerm pterm ON i.c_paymentterm_id = pterm.c_paymentterm_id
WHERE it.IsActive = 'Y'
  AND tc.tax_count > 0
  -- can either be an ESRReferenceNumber, or we may not have it at all. Regardless, both cases should work.
  AND (rn.c_referenceno_type_id = 540005 OR rnd.c_referenceno_doc_id IS NULL
    ) /* c_referenceno_type_id = 540005 (ESRReferenceNumber) */
GROUP BY i.c_invoice_id, i.ad_client_id,
         t.rate,
         t.IsTaxExempt,
         i.ad_org_id,
         i.created,
         i.createdby,
         i.updated,
         i.updatedby,
         i.isactive,
         pterm.discount,
         rn.referenceno
;
