DROP VIEW IF EXISTS ModCntr_Storage_Purchase_V
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