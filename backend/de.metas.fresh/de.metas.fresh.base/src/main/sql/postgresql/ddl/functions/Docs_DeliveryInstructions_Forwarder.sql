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


