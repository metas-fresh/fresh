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
