-- View: edi_cctop_901_991_v

DROP VIEW IF EXISTS edi_cctop_901_991_v
;

CREATE OR REPLACE VIEW edi_cctop_901_991_v AS
SELECT i.c_invoice_id                                                                                       AS edi_cctop_901_991_v_id,
       i.c_invoice_id,
       i.c_invoice_id                                                                                       AS edi_cctop_invoic_v_id,
       SUM(it.taxamt + it.taxbaseamt)                                                                       AS totalamt,
       SUM(it.taxamt)                                                                                       AS taxamt,
       SUM(it.taxbaseamt)                                                                                   AS taxbaseamt,
       t.rate /* we support the case of having two different C_Tax_IDs with the same tax-rate */,
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
       ROUND((ROUND(SUM(it.TaxBaseAmt) * -pterm.discount / 100, 2) + SUM(it.TaxBaseAmt)) * t.rate / 100, 2) AS TaxAmtWithSurchargeAmt
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
         pterm.discount,
         i.ad_org_id,
         i.created,
         i.createdby,
         i.updated,
         i.updatedby,
         i.isactive,
         rn.referenceno
;
