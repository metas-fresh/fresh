


-- just to be safe

CREATE TABLE backup.BKP_M_Forwarder_16012023 AS SELECT * FROM M_Forwarder;


-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> delivery details.Forwarder
-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2023-01-16T11:26:37.947Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614623
;

-- 2023-01-16T11:26:37.957Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710121
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Spediteur
-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2023-01-16T11:26:37.961Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710121
;

-- 2023-01-16T11:26:37.966Z
DELETE FROM AD_Field WHERE AD_Field_ID=710121
;




-- Table: M_Forwarder
-- 2023-01-16T11:30:49.923Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=542266
;

-- 2023-01-16T11:30:49.928Z
DELETE FROM AD_Table WHERE AD_Table_ID=542266
;


-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2023-01-16T11:35:33.448Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585444
;

-- 2023-01-16T11:35:33.452Z
DELETE FROM AD_Column WHERE AD_Column_ID=585444
;






SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation DROP COLUMN M_Forwarder_ID')
;




SELECT public.db_alter_table('M_Forwarder','DROP TABLE public.M_Forwarder')
;











