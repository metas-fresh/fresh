-- View: edi_cctop_111_v

DROP VIEW IF EXISTS edi_cctop_111_v;
CREATE OR REPLACE VIEW edi_cctop_111_v AS
SELECT i.c_invoice_id AS edi_cctop_111_v_id, ol.c_order_id,
       CASE
           WHEN REGEXP_REPLACE(opo.poreference::text, '\s+$', '') <> ''::text AND opo.poreference IS NOT NULL AND odo.dateordered IS NOT NULL THEN REGEXP_REPLACE(opo.poreference, '\s+$', '')
                                                                                                                                              ELSE NULL::character varying
       END AS poreference,
       CASE
           WHEN REGEXP_REPLACE(opo.poreference::text, '\s+$', '') <> ''::text AND opo.poreference IS NOT NULL AND odo.dateordered IS NOT NULL THEN odo.dateordered
                                                                                                                                              ELSE NULL::timestamp without time zone
       END AS dateordered,
       s.m_inout_id,
       s.documentno AS shipment_documentno, --// *not* send in edi-marshal-compudata-fresh.ftl, so won't do harm; TODO replace with EDI_DESADV
       sd.movementdate,
       oc.ad_client_id,
       oo.ad_org_id,
       ocr.created,
       ocb.createdby,
       ou.updated,
       oub.updatedby,
       oia.isactive
FROM c_invoice i
         LEFT JOIN LATERAL ( --only add values if there is only one unique value
    SELECT i.c_invoice_id, min(ol.c_order_id) AS c_order_id
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT ol.c_order_id)=1
    ) ol ON ol.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.dateordered) AS dateordered
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT ol.dateordered)=1
    ) odo ON odo.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.poreference) AS poreference
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT o.poreference)=1
    ) opo ON opo.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(io.movementdate) AS movementdate
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
             INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
    GROUP BY i.c_invoice_id HAVING count(DISTINCT io.movementdate)=1
    ) sd ON sd.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(io.m_inout_id) AS m_inout_id, min(io.documentno) AS documentno
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
             INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
    GROUP BY i.c_invoice_id HAVING count(DISTINCT io.m_inout_id)=1
    ) s ON s.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.ad_client_id) AS ad_client_id
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT o.ad_client_id)=1
    ) oc ON oc.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.ad_org_id) AS ad_org_id
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT o.ad_org_id)=1
    ) oo ON oo.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.created) AS created
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT o.created)=1
    ) ocr ON ocr.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.createdby) AS createdby
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT o.createdby)=1
    ) ocb ON ocb.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.updated) AS updated
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT o.updated)=1
    ) ou ON ou.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.updatedby) AS updatedby
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT o.updatedby)=1
    ) oub ON oub.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL (
    SELECT i.c_invoice_id, min(o.isactive) AS isactive
    FROM c_invoice i
             INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
             INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
    GROUP BY i.c_invoice_id HAVING count(DISTINCT o.isactive)=1
    ) oia ON oia.c_invoice_id = i.c_invoice_id
;