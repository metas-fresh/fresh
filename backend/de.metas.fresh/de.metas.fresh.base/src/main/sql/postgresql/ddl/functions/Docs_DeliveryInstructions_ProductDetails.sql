CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_ProductDetails(p_m_shippertransportation_id numeric, p_ad_language varchar)
    RETURNS
        Table
        (
            warehouseName             varchar,
            plannedloadedquantity     numeric,
            qtyordered                numeric,
            productValue              varchar,
            productName               varchar,
            grade                     varchar,
            uom                       varchar
        )
    STABLE
    LANGUAGE sql
AS
$$
SELECT wh.name                                  AS warehouseName,
       dp.plannedloadedquantity,
       dp.qtyordered,
       p.value                                  AS productValue,
       p.name                                   AS productName,
       p.grade,
       COALESCE(uomt.uomsymbol, uomt.uomsymbol) AS uom
FROM M_ShipperTransportation st
         JOIN m_delivery_planning dp ON dp.releaseno = st.documentno
         JOIN m_warehouse wh ON dp.m_warehouse_id = wh.m_warehouse_id
         JOIN M_product p ON dp.m_product_id = p.m_product_id
         JOIN C_UOM uom ON dp.c_uom_id = uom.c_uom_id
         JOIN C_UOM_trl uomt ON dp.c_uom_id = uomt.c_uom_id and uomt.ad_language=p_ad_language
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$$
;


