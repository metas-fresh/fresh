DROP FUNCTION IF EXISTS "de_metas_acct".assert_period_open_by_record(
    p_TableName varchar(255),
    p_Record_ID numeric)
;


CREATE OR REPLACE FUNCTION "de_metas_acct".assert_period_open_by_record(
    p_TableName varchar(255),
    p_Record_ID numeric)
    RETURNS void
AS
$BODY$
DECLARE
    v_DateAcct     timestamp WITH TIME ZONE;
    v_DocBaseType  varchar(3);
    v_AD_Client_ID numeric;
    v_AD_Org_ID    numeric;
BEGIN
    RAISE DEBUG 'Checking document %/% if period is open', p_TableName, p_Record_ID;

    IF (p_TableName = 'C_Order') THEN
        SELECT o.dateacct, dt.docbasetype, o.ad_client_id, o.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM C_Order o
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = o.c_doctype_id
        WHERE o.C_Order_ID = p_Record_ID;
    ELSIF (p_TableName = 'C_Invoice') THEN
        SELECT i.dateacct, dt.docbasetype, i.ad_client_id, i.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM c_invoice i
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = i.c_doctype_id
        WHERE i.c_invoice_id = p_Record_ID;
    ELSIF (p_TableName = 'C_Payment') THEN
        SELECT p.dateacct, dt.docbasetype, p.ad_client_id, p.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM c_payment p
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = p.c_doctype_id
        WHERE p.c_payment_id = p_Record_ID;
    ELSIF (p_TableName = 'C_AllocationHdr') THEN
        SELECT ah.dateacct, 'CMA' AS docbasetype, ah.ad_client_id, ah.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM C_AllocationHdr ah
        WHERE ah.C_AllocationHdr_ID = p_Record_ID;
    ELSIF (p_TableName = 'C_BankStatement') THEN
        SELECT bs.statementdate, 'CMB' AS docbasetype, bs.ad_client_id, bs.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM c_bankstatement bs
        WHERE bs.c_bankstatement_id = p_Record_ID;
    ELSIF (p_TableName = 'M_InOut') THEN
        SELECT io.dateacct, dt.docbasetype, io.ad_client_id, io.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM m_inout io
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = io.c_doctype_id
        WHERE io.m_inout_id = p_Record_ID;
    ELSIF (p_TableName = 'M_MatchPO') THEN
        SELECT mpo.dateacct, 'MXP', mpo.ad_client_id, mpo.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM m_matchpo mpo
        WHERE mpo.m_matchpo_id = p_Record_ID;
    ELSIF (p_TableName = 'M_MatchInv') THEN
        SELECT mi.dateacct, 'MXI', mi.ad_client_id, mi.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM m_matchinv mi
        WHERE mi.m_matchinv_id = p_Record_ID;
    ELSIF (p_TableName = 'M_Inventory') THEN
        SELECT inv.movementdate, dt.docbasetype, inv.ad_client_id, inv.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM m_inventory inv
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = inv.c_doctype_id
        WHERE inv.m_inventory_id = p_Record_ID;
    ELSIF (p_TableName = 'M_Movement') THEN
        SELECT m.movementdate, dt.docbasetype, m.ad_client_id, m.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM m_movement m
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = m.c_doctype_id
        WHERE m.m_movement_id = p_Record_ID;
    ELSIF (p_TableName = 'PP_Order') THEN
        SELECT mo.dateordered, dt.docbasetype, mo.ad_client_id, mo.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM pp_order mo
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = mo.c_doctype_id
        WHERE mo.pp_order_id = p_Record_ID;
    ELSIF (p_TableName = 'PP_Cost_Collector') THEN
        SELECT cc.dateacct, dt.docbasetype, cc.ad_client_id, cc.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM pp_cost_collector cc
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = cc.c_doctype_id
        WHERE cc.pp_cost_collector_id = p_Record_ID;
    ELSIF (p_TableName = 'M_CostRevaluation') THEN
        SELECT cr.dateacct, dt.docbasetype, cr.ad_client_id, cr.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM m_costrevaluation cr
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = cr.c_doctype_id
        WHERE cr.m_costrevaluation_id = p_Record_ID;
    ELSIF (p_TableName = 'SAP_GLJournal') THEN
        SELECT glj.dateacct, dt.docbasetype, glj.ad_client_id, glj.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM SAP_GLJournal glj
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = glj.c_doctype_id
        WHERE glj.SAP_GLJournal_ID = p_Record_ID;
    ELSIF (p_TableName = 'GL_Journal') THEN
        SELECT glj.dateacct, dt.docbasetype, glj.ad_client_id, glj.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM GL_Journal glj
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = glj.c_doctype_id
        WHERE glj.GL_Journal_ID = p_Record_ID;
    ELSIF (p_TableName = 'M_Shipping_Notification') THEN
        SELECT sn.dateacct, dt.docbasetype, sn.ad_client_id, sn.ad_org_id
        INTO v_DateAcct, v_DocBaseType, v_AD_Client_ID, v_AD_Org_ID
        FROM M_Shipping_Notification sn
                 INNER JOIN c_doctype dt ON dt.c_doctype_id = sn.c_doctype_id
        WHERE sn.M_Shipping_Notification_ID = p_Record_ID;
    ELSE
        RAISE EXCEPTION 'Document %/% is not handled when checking if the period is open', p_TableName, p_Record_ID;
    END IF;

    IF (v_DateAcct IS NULL) THEN
        RAISE EXCEPTION 'No document found for %/%', p_TableName, p_Record_ID;
    END IF;

    PERFORM "de_metas_acct".assert_period_open(
            p_DateAcct := v_DateAcct,
            p_DocBaseType := v_DocBaseType,
            p_AD_Client_ID := v_AD_Client_ID,
            p_AD_Org_ID := v_AD_Org_ID);
END;
$BODY$
    LANGUAGE plpgsql
    STABLE
    COST 100
;
