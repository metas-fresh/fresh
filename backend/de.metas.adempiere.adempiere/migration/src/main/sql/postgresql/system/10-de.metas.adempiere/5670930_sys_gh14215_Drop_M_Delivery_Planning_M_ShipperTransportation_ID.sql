-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Transportation Order
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-09T16:06:53.187Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613513
;

-- 2023-01-09T16:06:53.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708109
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Transport Order
-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-09T16:06:53.334Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708109
;

-- 2023-01-09T16:06:53.493Z
DELETE FROM AD_Field WHERE AD_Field_ID=708109
;

-- Column: M_Delivery_Planning.M_ShipperTransportation_ID
-- 2023-01-09T16:07:05.055Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585045
;

-- 2023-01-09T16:07:05.222Z
DELETE FROM AD_Column WHERE AD_Column_ID=585045
;



/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning DROP COLUMN M_ShipperTransportation_ID')


