CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Forwarder(p_m_shippertransportation_id numeric)
    RETURNS
        Table
        (
            address          text,
            transportdetails text
        )
    STABLE
    LANGUAGE sql
AS
$$
SELECT (COALESCE(bp.name || E'\n', '') || COALESCE(bpl.address, '')) AS address, dp.transportdetails
FROM M_ShipperTransportation st
         JOIN M_shipper sh ON st.m_shipper_id = sh.m_shipper_id
         JOIN c_bpartner bp ON sh.c_bpartner_id = bp.c_bpartner_id
         JOIN C_BPartner_location bpl ON bpl.C_BPartner_location_id = st.shipper_location_id
         JOIN m_delivery_planning dp ON dp.releaseno = st.documentno
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$$
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_LoadingAddress(p_m_shippertransportation_id numeric)
    RETURNS
        text
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(wh.name || E'\n', '') || COALESCE(bpl.address, '')
           || E'\n' || l.value || E'\n'
           || COALESCE(wh.excisenumber, '') AS LoadingAddress
FROM M_ShipperTransportation st
         JOIN C_BPartner_location bpl ON st.c_bpartner_location_loading_id = bpl.c_bpartner_location_id
         JOIN m_warehouse wh ON wh.m_warehouse_id = bpl.m_warehouse_id
         JOIN m_locator l ON l.m_warehouse_id = wh.m_warehouse_id AND l.isdefault = 'Y'
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
    ;
$$
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_DeliveryAddress(p_m_shippertransportation_id numeric)
    RETURNS
        Table
        (
            address      text,
            contactname  varchar,
            contactphone varchar
        )
    STABLE
    LANGUAGE sql
AS
$$
SELECT (COALESCE(bp.name || E'\n', '') || COALESCE(bpl.address, '') || COALESCE(wh.excisenumber, '')) AS address, u.name, u.phone
FROM M_ShipperTransportation st
         JOIN C_BPartner_location bpl ON bpl.C_BPartner_location_id = st.c_bpartner_location_delivery_id
         LEFT JOIN m_warehouse wh ON wh.m_warehouse_id = bpl.m_warehouse_id
         JOIN c_bpartner bp ON bpl.c_bpartner_id = bp.c_bpartner_id
         LEFT JOIN ad_user u ON bp.c_bpartner_id = u.c_bpartner_id AND u.isshiptocontact_default='Y'

WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$$
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Description(p_m_shippertransportation_id numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Description(p_m_shippertransportation_id numeric)
    RETURNS
        Table
        (
            Forwarderaddress     text,
            transportdetails     text,
            deliveryaddress      text,
            deliverycontactname  varchar,
            deliverycontactphone varchar,
            LoadingAddress       text,
            loadingdate          timestamp,
            loadingtime          varchar,
            deliverydate         timestamp,
            documentno           varchar,
            Creator              varchar,
            CreatorPhone         varchar,
            CreatorFax           varchar,
            CreatorEmail         varchar,
            orderno              varchar,
            incoterms            varchar,
            incotermlocation     varchar
        )
    STABLE
    LANGUAGE sql
AS
$$
SELECT f.*,
       d.*,
       l.*,
       st.loadingdate,
       st.loadingtime,
       st.deliverydate,
       st.documentno,
       u.name       AS Creator,
       u.phone      AS CreatorPhone,
       u.fax        AS CreatorFax,
       u.email      AS CreatorEmail,
       o.documentno AS orderno,
       ic.name      AS incoterms,
       st.incotermlocation

FROM de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_LoadingAddress(p_m_shippertransportation_id) AS l,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Forwarder(p_m_shippertransportation_id) AS f,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_DeliveryAddress(p_m_shippertransportation_id) AS d,
     M_ShipperTransportation st
         JOIN ad_user u ON st.createdby = u.ad_user_id
         JOIN m_delivery_planning dp ON dp.releaseno = st.documentno
         JOIN C_order o ON o.c_order_id = dp.c_order_id
         JOIN c_incoterms ic ON ic.c_incoterms_id = st.c_incoterms_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$$
;


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


