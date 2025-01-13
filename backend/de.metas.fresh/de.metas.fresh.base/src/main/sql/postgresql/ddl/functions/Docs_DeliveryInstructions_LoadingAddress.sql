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