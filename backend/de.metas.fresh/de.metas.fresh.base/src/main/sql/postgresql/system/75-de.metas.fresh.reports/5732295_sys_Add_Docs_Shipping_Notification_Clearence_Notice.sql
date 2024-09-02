DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Clearence_Notice(p_record_id numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Clearence_Notice(p_record_id numeric)
    RETURNS TABLE
            (
                ad_org_id                  numeric,
                ad_user_id                 numeric,
                m_shipping_notification_id numeric,
                c_bpartner_id              numeric,
                c_bpartner_location_id     numeric,
                ShipFrom_Partner_ID        numeric,
                documentno                 varchar,
                orgName                    varchar,
                orgAddress                 varchar,
                varcharorgvataxid          varchar,
                orgPhone                   varchar,
                orgPhone2                  varchar,
                orgfax                     varchar,
                orgEmail                   varchar,
                orgURL                     varchar,
                customerAddress            varchar,
                customerPhone              varchar,
                customerFax                varchar,
                customerEmail              varchar,
                DeliveryFrom               varchar,
                deliveryfromPhone          varchar,
                deliveryfromFax            varchar,
                deliveryfromEmail          varchar
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT sn.ad_org_id,
       sn.ad_user_id,
       sn.m_shipping_notification_id,
       sn.c_bpartner_id,
       sn.c_bpartner_location_id,
       sn.ShipFrom_Partner_ID,
       sn.documentno,
       t3.name     AS orgName,
       t3.address  AS orgAddress,
       t3.vataxid  AS orgvataxid,
       t3.phone    AS orgPhone,
       t3.phone2   AS orgPhone2,
       t3.fax      AS orgfax,
       t3.email    AS orgEmail,
       t3.url      AS orgURL,
       bpl.address AS customerAddress,
       u.phone     AS customerPhone,
       u.fax       AS customerFax,
       u.email     AS customerEmail,
       TRIM(COALESCE(wbp.name || E'\n', '') ||
            COALESCE(wbploc.address1 || E'\n', '') ||
            COALESCE(wbploc.postal || E'\n', '') ||
            COALESCE(wbploc.city || E'\n', '') ||
            COALESCE(wbpc.name, '')
       )           AS DeliveryFrom,
       wu.phone    AS deliveryfromPhone,
       wu.fax      AS deliveryfromFax,
       wu.email    AS deliveryfromEmail
FROM M_Shipping_Notification sn
         JOIN C_BPartner bp ON sn.C_BPartner_ID = bp.C_BPartner_ID
         LEFT JOIN AD_user u ON sn.ad_user_id = u.ad_user_id
         INNER JOIN LATERAL (SELECT * FROM AD_user wu WHERE wu.c_bpartner_id = sn.ShipFrom_Partner_ID ORDER BY isdefaultcontact DESC LIMIT 1) wu ON TRUE
         INNER JOIN c_bpartner wbp ON sn.ShipFrom_Partner_ID = wbp.c_bpartner_id
         INNER JOIN c_bpartner_location wbpl ON wbp.c_bpartner_id = wbpl.c_bpartner_id
         INNER JOIN c_location wbploc ON wbploc.c_location_id = wbpl.c_location_id
         INNER JOIN C_Country wbpc ON wbploc.C_Country_ID = wbpc.C_Country_ID
         INNER JOIN C_BPartner_Location bpl ON sn.C_BPartner_Location_ID = bpl.c_bpartner_location_id
         JOIN de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report(NULL, 'Y', sn.ad_org_ID) AS t3 ON TRUE
WHERE sn.M_Shipping_Notification_ID = p_record_id
  AND sn.isActive = 'Y'
    ;
$$
;
