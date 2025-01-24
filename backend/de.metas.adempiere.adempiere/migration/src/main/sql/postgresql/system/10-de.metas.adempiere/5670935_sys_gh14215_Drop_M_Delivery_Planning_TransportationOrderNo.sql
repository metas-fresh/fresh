-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Order
-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2023-01-10T12:33:30.693Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613512
;

-- 2023-01-10T12:33:30.841Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708108
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Transport Order
-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2023-01-10T12:33:30.927Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708108
;

-- 2023-01-10T12:33:31.096Z
DELETE FROM AD_Field WHERE AD_Field_ID=708108
;

-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2023-01-10T12:33:56.430Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585038
;

-- 2023-01-10T12:33:56.596Z
DELETE FROM AD_Column WHERE AD_Column_ID=585038
;


