UPDATE c_ordertax ot
SET IsDocumentLevel=(SELECT t.IsDocumentLevel FROM c_tax t WHERE t.c_tax_id = ot.c_tax_id)
;

UPDATE c_invoicetax it
SET IsDocumentLevel=(SELECT t.IsDocumentLevel FROM c_tax t WHERE t.c_tax_id = it.c_tax_id)
;
