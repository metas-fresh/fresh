DROP FUNCTION IF EXISTS create_purchase_candidates_from_shipment_schedules(
    p_m_shipmentschedule_ids varchar
)
;

CREATE OR REPLACE FUNCTION create_purchase_candidates_from_shipment_schedules(IN p_m_shipmentschedule_ids varchar) RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- Use a single static SQL query with a CASE statement to handle the filtering condition
    INSERT INTO c_purchasecandidate (ad_client_id,
                                     ad_org_id,
                                     c_orderlineso_id,
                                     c_purchasecandidate_id,
                                     c_uom_id,
                                     created,
                                     createdby,
                                     purchasedatepromised,
                                     isactive,
                                     m_product_id,
                                     processed,
                                     qtytopurchase,
                                     updated,
                                     updatedby,
                                     vendor_id,
                                     m_warehousepo_id,
                                     c_orderso_id,
                                     purchasedqty,
                                     reminderdate,
                                     isaggregatepo,
                                     c_currency_id,
                                     purchasepriceactual,
                                     profitsalespriceactual,
                                     m_attributesetinstance_id,
                                     demandreference,
                                     isprepared,
                                     profitpurchasepriceactual,
                                     purchasedateordered,
                                     m_shipmentschedule_id)
    SELECT ms.ad_client_id,
           ms.ad_org_id,
           ms.c_orderline_id,
           NEXTVAL('c_purchasecandidate_seq'),
           p.c_uom_id,
           NOW(),
           100,
           ms.deliverydate,
           ms.isactive,
           ms.m_product_id,
           'N',
           ms.qtydelivered,
           NOW(),
           100,
           ms.c_bpartner_id,
           ms.m_warehouse_id,
           ms.c_order_id,
           NULL,
           NULL,
           'N',
           102,
           NULL,
           NULL,
           NULL,
           'AUTO-GENERATED',
           'Y',
           NULL,
           NOW(),
           ms.m_shipmentschedule_id
    FROM m_shipmentschedule ms
             JOIN m_product p ON ms.m_product_id = p.m_product_id
    WHERE ms.processed = 'Y'
      AND NOT EXISTS(
            SELECT 1
            FROM c_purchasecandidate pc
            WHERE pc.m_shipmentschedule_id = ms.m_shipmentschedule_id
        )
      AND (
        -- Handle the "all" case
                p_m_shipmentschedule_ids = 'all'
            OR
            -- Handle the specific IDs case
                ms.m_shipmentschedule_id = ANY (REGEXP_SPLIT_TO_ARRAY(p_m_shipmentschedule_ids, ',')::int[])
        );
END;
$$
;
