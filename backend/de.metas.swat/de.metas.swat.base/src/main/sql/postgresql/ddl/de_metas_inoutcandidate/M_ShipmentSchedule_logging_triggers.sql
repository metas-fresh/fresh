--
--
-- Log table
--
--

DROP TABLE IF EXISTS M_ShipmentSchedule_Log;

CREATE TABLE M_ShipmentSchedule_Log
(
    M_ShipmentSchedule_ID numeric                           NOT NULL,
    created               time with time zone DEFAULT NOW() NOT NULL,
    action                varchar(20), -- invalidate, validate, change
    description           text,
    QtyToDeliver          numeric,
    QtyToDeliver_Prev     numeric,
    QtyOnHand             numeric,
    QtyOnHand_Prev        numeric
)
;

CREATE INDEX M_ShipmentSchedule_Log_parent ON M_ShipmentSchedule_Log (M_ShipmentSchedule_ID)
;


--
--
-- Remove triggers
--
--
/*
DROP TRIGGER IF EXISTS m_shipmentschedule_recompute_logging_insert_tg ON m_shipmentschedule_recompute
;

DROP TRIGGER IF EXISTS m_shipmentschedule_recompute_logging_delete_tg ON m_shipmentschedule_recompute
;

DROP TRIGGER IF EXISTS m_shipmentschedule_logging_update_tg ON m_shipmentschedule
;
*/


--
--
-- Trigger functions
--
--

-- DROP FUNCTION IF EXISTS m_shipmentschedule_recompute_logging_tgfn;

CREATE OR REPLACE FUNCTION m_shipmentschedule_recompute_logging_tgfn()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF (tg_op = 'INSERT') THEN
        INSERT INTO M_ShipmentSchedule_Log (M_ShipmentSchedule_ID, action) VALUES (NEW.m_shipmentschedule_id, 'invalidate');
        RETURN NEW;
    ELSIF (tg_op = 'DELETE') THEN
        INSERT INTO M_ShipmentSchedule_Log (M_ShipmentSchedule_ID, action) VALUES (OLD.m_shipmentschedule_id, 'validate');
        RETURN OLD;
    ELSE
        RAISE EXCEPTION 'Operation % is not handled', tg_op;
    END IF;
END;
$$
    LANGUAGE plpgsql
;

--
--
--
--
--
-- DROP FUNCTION IF EXISTS m_shipmentschedule_logging_tgfn;

CREATE OR REPLACE FUNCTION m_shipmentschedule_logging_tgfn()
    RETURNS TRIGGER
AS
$$
BEGIN
    IF (tg_op = 'UPDATE') THEN
        INSERT INTO M_ShipmentSchedule_Log (M_ShipmentSchedule_ID, action, QtyToDeliver, QtyToDeliver_Prev, QtyOnHand, QtyOnHand_Prev)
        VALUES (OLD.m_shipmentschedule_id,
                'change',
                NEW.QtyToDeliver,
                OLD.QtyToDeliver,
                NEW.QtyOnHand,
                OLD.QtyOnHand);
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Operation % is not handled', tg_op;
    END IF;
END;
$$
    LANGUAGE plpgsql
;



--
-- 
-- Install triggers
--
--
/*
CREATE TRIGGER m_shipmentschedule_recompute_logging_insert_tg
    AFTER INSERT
    ON m_shipmentschedule_recompute
    FOR EACH ROW
EXECUTE FUNCTION m_shipmentschedule_recompute_logging_tgfn()
;

CREATE TRIGGER m_shipmentschedule_recompute_logging_delete_tg
    AFTER DELETE
    ON m_shipmentschedule_recompute
    FOR EACH ROW
EXECUTE FUNCTION m_shipmentschedule_recompute_logging_tgfn()
;

CREATE TRIGGER m_shipmentschedule_logging_update_tg
    BEFORE UPDATE OF QtyOnHand, QtyToDeliver
    ON m_shipmentschedule
    FOR EACH ROW
EXECUTE FUNCTION m_shipmentschedule_logging_tgfn()
;
*/