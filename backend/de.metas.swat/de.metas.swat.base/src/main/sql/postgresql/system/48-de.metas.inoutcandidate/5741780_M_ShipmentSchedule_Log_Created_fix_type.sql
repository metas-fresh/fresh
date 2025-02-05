ALTER TABLE M_ShipmentSchedule_Log
    ALTER COLUMN created TYPE timestamp with time zone USING ('1970-01-01'::date + created::time);
