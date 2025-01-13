DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Description(p_m_shippertransportation_id numeric)
;

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
            referenceno          varchar,
            incoterms            varchar,
            incotermlocation     varchar,
            meansoftransport     text
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
       u.name        AS Creator,
       u.phone       AS CreatorPhone,
       u.fax         AS CreatorFax,
       u.email       AS CreatorEmail,
       o.documentno  AS orderno,
       o.poreference AS referenceno,
       ic.name       AS incoterms,
       st.incotermlocation,
       mt.name       AS meansoftransport

FROM de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_LoadingAddress(p_m_shippertransportation_id) AS l,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Forwarder(p_m_shippertransportation_id) AS f,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_DeliveryAddress(p_m_shippertransportation_id) AS d,
     M_ShipperTransportation st
         JOIN ad_user u ON st.createdby = u.ad_user_id
         JOIN m_delivery_planning dp ON dp.releaseno = st.documentno
         JOIN C_order o ON o.c_order_id = dp.c_order_id
         JOIN c_incoterms ic ON ic.c_incoterms_id = st.c_incoterms_id
         JOIN m_meansoftransportation mt ON mt.m_meansoftransportation_id = st.m_meansoftransportation_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$$
;