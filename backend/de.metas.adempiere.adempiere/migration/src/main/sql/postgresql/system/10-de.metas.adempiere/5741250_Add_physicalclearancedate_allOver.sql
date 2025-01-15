DROP VIEW IF EXISTS ModCntr_Storage_Purchase_V
;

DROP VIEW IF EXISTS ModCntr_Storage_Sales_V
;


DROP VIEW IF EXISTS ModCntr_Storage_V
;

CREATE OR REPLACE VIEW ModCntr_Storage_V AS
SELECT t.modularcontracthandlertype,
       il.productname,
       il.qtyinvoicedinpriceuom,
       log.priceactual,
       il.linenetamt,
       log.datetrx,
	   log.physicalclearancedate,
       log.storagedays,
       log.qty,
       uom.x12de355                                      AS uom,
       log.amount,
       p.name                                            AS initialproduct,
       p.value                                           AS initialproducevalue,
       il.c_invoice_id,
       report.getPricePatternForJasper(i.m_pricelist_id) AS PricePattern,
       report.getAmountPatternForJasper(i.c_currency_id) AS amountpattern
FROM c_invoiceline il
         JOIN c_invoice i ON il.c_invoice_id = i.c_invoice_id
         JOIN c_invoice_line_alloc ila ON il.c_invoiceline_id = ila.c_invoiceline_id
         JOIN c_invoice_candidate ic ON ila.c_invoice_candidate_id = ic.c_invoice_candidate_id
         JOIN modcntr_log log ON ic.c_invoice_candidate_id = log.c_invoice_candidate_id
         JOIN modcntr_type t ON log.modcntr_type_id = t.modcntr_type_id
         JOIN m_product p ON log.initial_product_id = p.m_product_id
         JOIN c_uom uom ON log.c_uom_id = uom.c_uom_id
ORDER BY log.datetrx, p.name
;



CREATE OR REPLACE VIEW ModCntr_Storage_Purchase_V AS
SELECT modularcontracthandlertype,
       productname,
       qtyinvoicedinpriceuom,
       priceactual,
       linenetamt,
       datetrx,
       physicalclearancedate,
       storagedays,
       qty,
       uom,
       amount,
       initialproduct,
       initialproducevalue,
       c_invoice_id,
       PricePattern,
       amountpattern
FROM ModCntr_Storage_V
WHERE modularcontracthandlertype::text = 'StorageCost'::text
ORDER BY datetrx, initialproduct
;


CREATE OR REPLACE VIEW ModCntr_Storage_Sales_V AS
SELECT modularcontracthandlertype,
       productname,
       qtyinvoicedinpriceuom,
       priceactual,
       linenetamt,
       datetrx,
       physicalclearancedate,
       storagedays,
       qty,
       uom,
       amount,
       initialproduct,
       initialproducevalue,
       c_invoice_id,
       PricePattern,
       amountpattern
FROM ModCntr_Storage_V
WHERE modularcontracthandlertype::text = 'SalesStorageCost'::text
ORDER BY datetrx, initialproduct
;