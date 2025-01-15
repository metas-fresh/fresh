DROP VIEW IF EXISTS ModCntr_Storage_Sales_V
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