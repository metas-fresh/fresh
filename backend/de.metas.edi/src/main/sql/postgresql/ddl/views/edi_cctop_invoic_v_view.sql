-- View: EDI_Cctop_INVOIC_v

DROP VIEW IF EXISTS EDI_Cctop_INVOIC_v
;

CREATE OR REPLACE VIEW EDI_Cctop_INVOIC_v AS
SELECT i.C_Invoice_ID                                                                                       AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , REGEXP_REPLACE(i.DocumentNo, '\s+$', '')                                                             AS Invoice_DocumentNo
     , i.DateInvoiced
     , i.DateAcct
     , (CASE
            WHEN REGEXP_REPLACE(i.POReference::TEXT, '\s+$', '') <> ''::TEXT AND i.POReference IS NOT NULL /* task 09182: if there is a POReference, then export it */
                THEN REGEXP_REPLACE(i.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END)                                                                                                AS POReference
     , (CASE
            WHEN COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered) IS NOT NULL /* task 09182: if there is an orderDate, then export it */
                THEN COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered)
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END)                                                                                                AS DateOrdered
     , (CASE dt.DocBaseType
            WHEN 'ARI'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = ''   THEN '380'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AQ' THEN '383'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AP' THEN '84'::TEXT
                                                                                                                ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
            WHEN 'ARC'::BPChar THEN (CASE
                /* CQ => "GS - Delivery-Difference"; CS => "GS - Retoure"; CR => Price-Difference */
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ', 'CS') THEN '381'
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'CR'          THEN '83'
                                                                                                                         ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
                               ELSE 'ERROR EAN_DocType'::TEXT
        END)                                                                                                AS EANCOM_DocType
     , i.GrandTotal
     , i.TotalLines
    /* IF docSubType is CS, the we don't reference the original shipment*/
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE COALESCE(shipment.MovementDate, iomd.movementdate) END AS MovementDate
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE COALESCE(shipment.DocumentNo, iodn.documentno) END     AS Shipment_DocumentNo
     , taxAndSurchage.TotalVAT
     , taxAndSurchage.TotalTaxBaseAmt
     , COALESCE(rbp.EdiInvoicRecipientGLN, rl.GLN)                                                          AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (SELECT DISTINCT ON (REGEXP_REPLACE(sl.GLN, '\s+$', '')) REGEXP_REPLACE(sl.GLN, '\s+$', '') AS GLN
        FROM C_BPartner_Location sl
        WHERE TRUE
          AND sl.C_BPartner_ID = sp.C_BPartner_ID
          AND sl.IsRemitTo = 'Y'::BPChar
          AND sl.GLN IS NOT NULL
          AND sl.IsActive = 'Y'::BPChar)                                                                    AS SenderGLN
     , REGEXP_REPLACE(sp.VATaxId, '\s+$', '')                                                               AS VATaxId
     , c.ISO_Code
     , REGEXP_REPLACE(i.CreditMemoReason, '\s+$', '')                                                       AS CreditMemoReason
     , (SELECT REGEXP_REPLACE(Name, '\s+$', '') AS Name
        FROM AD_Ref_List
        WHERE AD_Reference_ID = 540014 -- C_CreditMemo_Reason
          AND Value = i.CreditMemoReason)                                                                   AS CreditMemoReasonText
     , (SELECT CASE
                   WHEN ARRAY_LENGTH(ARRAY_AGG(DISTINCT ol.invoicableqtybasedon), 1) = 1
                       THEN (ARRAY_AGG(DISTINCT ol.invoicableqtybasedon))[1]
                       ELSE NULL
               END
        FROM c_orderline ol
        WHERE ol.c_order_id = o.c_order_id)                                                                 AS invoicableqtybasedon
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode                                                                                       AS AD_Language
     , i.AD_Client_ID
     , i.AD_Org_ID
     , i.Created
     , i.CreatedBy
     , i.Updated
     , i.UpdatedBy
     , i.IsActive
     , (SELECT STRING_AGG(DISTINCT edi.DocumentNo, ',')
        FROM c_invoiceline icl
                 INNER JOIN c_invoice_line_alloc inalloc ON icl.c_invoiceline_id = inalloc.c_invoiceline_id
                 INNER JOIN C_InvoiceCandidate_InOutLine candinout
                            ON inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id
                 INNER JOIN M_InOutLine inoutline ON candinout.m_inoutline_id = inoutline.m_inoutline_id
                 INNER JOIN m_inout inout ON inoutline.m_inout_id = inout.m_inout_id
                 INNER JOIN EDI_Desadv edi ON inout.EDI_Desadv_ID = edi.EDI_Desadv_ID
        WHERE icl.c_invoice_id = i.c_invoice_id)                                                            AS EDIDesadvDocumentNo
     , taxAndSurchage.SurchargeAmt
     , taxAndSurchage.TotalLinesWithSurchargeAmt
     , taxAndSurchage.TotalVatWithSurchargeAmt
     , taxAndSurchage.GrandTotalWithSurchargeAmt
FROM C_Invoice i
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
         LEFT JOIN C_Order o ON o.C_Order_ID = i.C_Order_ID
         LEFT JOIN LATERAL ( SELECT io.DocumentNo,
                                    io.MovementDate
                             FROM M_InOut io
                             WHERE io.C_Order_ID = o.C_Order_ID
                               AND io.DocStatus IN ('CO', 'CL')
                             ORDER BY io.Created
                             LIMIT 1 ) shipment ON TRUE -- for the case of missing EDI_Desadv, we still get the first M_InOut; DESADV can be switched off for individual C_BPartners
         LEFT JOIN C_BPartner rbp ON rbp.C_BPartner_ID = i.C_BPartner_ID
         LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
         LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
         LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(o.dateordered) AS dateordered --only add values if there is only one unique value
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT ol.dateordered) = 1 ) ol ON ol.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(io.movementdate) AS movementdate
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                                      INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT io.movementdate) = 1 ) iomd ON iomd.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(io.documentno) AS documentno
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                                      INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT io.documentno) = 1 ) iodn ON iodn.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT C_Invoice_ID
                                  , SUM(TaxAmt)                                              AS TotalVAT
                                  , SUM(TaxBaseAmt)                                          AS TotalTaxBaseAmt
                                  , SUM(SurchargeAmt)                                        AS SurchargeAmt
                                  , SUM(TaxBaseAmtWithSurchargeAmt)                          AS TotalLinesWithSurchargeAmt
                                  , SUM(TaxAmtWithSurchargeAmt)                              AS TotalVatWithSurchargeAmt
                                  , SUM(TaxBaseAmtWithSurchargeAmt + TaxAmtWithSurchargeAmt) AS GrandTotalWithSurchargeAmt
                             FROM edi_cctop_901_991_v
                             WHERE c_invoice_id = i.c_invoice_id
                             GROUP BY C_Invoice_ID) taxAndSurchage ON TRUE
;