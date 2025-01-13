DROP VIEW IF EXISTS M_Delivery_Planning_Delivery_Instructions_V;


CREATE VIEW M_Delivery_Planning_Delivery_Instructions_V
AS
SELECT di.documentno,
       di.m_shippertransportation_id,
       dp.m_delivery_planning_id,
       dp.m_sectioncode_id,
       di.docstatus,
       di.datedoc,
       di.c_bpartner_location_loading_id,
       di.loadingdate,
       di.c_bpartner_location_delivery_id,
       di.deliverydate,
       di.c_incoterms_id,
       di.incotermlocation,
       di.m_meansoftransportation_id,
       sp.M_Product_ID,
       sp.m_locator_id,
       sp.actualloadqty,
       sp.actualdischargequantity,
       di.created,
       di.createdby,
       sp.M_ShippingPackage_ID as M_Delivery_Planning_Delivery_Instructions_V_ID,
       di.updated,
       di.updatedby,
       di.isactive,
       di.ad_org_id,
       di.ad_client_id
FROM M_ShipperTransportation di
         JOIN M_ShippingPackage sp
              ON di.m_shippertransportation_id = sp.m_shippertransportation_id
         JOIN M_Delivery_Planning dp ON di.documentno = dp.releaseno
;