-- Tab: Delivery Instruction(541657,D) -> Versandpackung
-- Table: M_ShippingPackage
-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Notiz
-- Column: M_ShippingPackage.Note
-- 2023-01-13T09:52:11.678Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614607
;

-- 2023-01-13T09:52:11.766Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710109
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Note
-- Column: M_ShippingPackage.Note
-- 2023-01-13T09:52:11.934Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710109
;

-- 2023-01-13T09:52:12.185Z
DELETE FROM AD_Field WHERE AD_Field_ID=710109
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Transport Auftrag
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2023-01-13T09:52:12.949Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614608
;

-- 2023-01-13T09:52:13.031Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710100
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Transportation Order
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2023-01-13T09:52:13.153Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710100
;

-- 2023-01-13T09:52:13.403Z
DELETE FROM AD_Field WHERE AD_Field_ID=710100
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Gewicht Packstücke
-- Column: M_ShippingPackage.PackageWeight
-- 2023-01-13T09:52:14.165Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614605
;

-- 2023-01-13T09:52:14.253Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710105
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Weight
-- Column: M_ShippingPackage.PackageWeight
-- 2023-01-13T09:52:14.378Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710105
;

-- 2023-01-13T09:52:14.621Z
DELETE FROM AD_Field WHERE AD_Field_ID=710105
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Packstück
-- Column: M_ShippingPackage.M_Package_ID
-- 2023-01-13T09:52:15.340Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614604
;

-- 2023-01-13T09:52:15.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710102
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Package
-- Column: M_ShippingPackage.M_Package_ID
-- 2023-01-13T09:52:15.544Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710102
;

-- 2023-01-13T09:52:15.786Z
DELETE FROM AD_Field WHERE AD_Field_ID=710102
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Nettogewicht Packstücke
-- Column: M_ShippingPackage.PackageNetTotal
-- 2023-01-13T09:52:16.513Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614606
;

-- 2023-01-13T09:52:16.596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710106
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Package Net Total
-- Column: M_ShippingPackage.PackageNetTotal
-- 2023-01-13T09:52:16.720Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710106
;

-- 2023-01-13T09:52:17.029Z
DELETE FROM AD_Field WHERE AD_Field_ID=710106
;

-- 2023-01-13T09:52:17.277Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710107
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Shipping Package
-- Column: M_ShippingPackage.M_ShippingPackage_ID
-- 2023-01-13T09:52:17.402Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710107
;

-- 2023-01-13T09:52:17.646Z
DELETE FROM AD_Field WHERE AD_Field_ID=710107
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Abholung
-- Column: M_ShippingPackage.IsToBeFetched
-- 2023-01-13T09:52:18.374Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614613
;

-- 2023-01-13T09:52:18.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710110
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Fetched
-- Column: M_ShippingPackage.IsToBeFetched
-- 2023-01-13T09:52:18.578Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710110
;

-- 2023-01-13T09:52:18.822Z
DELETE FROM AD_Field WHERE AD_Field_ID=710110
;

-- 2023-01-13T09:52:19.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710096
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Active
-- Column: M_ShippingPackage.IsActive
-- 2023-01-13T09:52:19.250Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710096
;

-- 2023-01-13T09:52:19.498Z
DELETE FROM AD_Field WHERE AD_Field_ID=710096
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Sektion
-- Column: M_ShippingPackage.AD_Org_ID
-- 2023-01-13T09:52:20.253Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614612
;

-- 2023-01-13T09:52:20.335Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710099
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Organisation
-- Column: M_ShippingPackage.AD_Org_ID
-- 2023-01-13T09:52:20.461Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710099
;

-- 2023-01-13T09:52:20.702Z
DELETE FROM AD_Field WHERE AD_Field_ID=710099
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Mandant
-- Column: M_ShippingPackage.AD_Client_ID
-- 2023-01-13T09:52:21.421Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614610
;

-- 2023-01-13T09:52:21.504Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710098
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Client
-- Column: M_ShippingPackage.AD_Client_ID
-- 2023-01-13T09:52:21.627Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710098
;

-- 2023-01-13T09:52:21.867Z
DELETE FROM AD_Field WHERE AD_Field_ID=710098
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Auftrag
-- Column: M_ShippingPackage.C_Order_ID
-- 2023-01-13T09:52:22.560Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614609
;

-- 2023-01-13T09:52:22.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710108
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Sales order
-- Column: M_ShippingPackage.C_Order_ID
-- 2023-01-13T09:52:22.762Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710108
;

-- 2023-01-13T09:52:23.072Z
DELETE FROM AD_Field WHERE AD_Field_ID=710108
;

-- 2023-01-13T09:52:23.318Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710097
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Processed
-- Column: M_ShippingPackage.Processed
-- 2023-01-13T09:52:23.439Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710097
;

-- 2023-01-13T09:52:23.679Z
DELETE FROM AD_Field WHERE AD_Field_ID=710097
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_ShippingPackage.M_InOut_ID
-- 2023-01-13T09:52:24.374Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614603
;

-- 2023-01-13T09:52:24.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710101
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Shipment/Receipt
-- Column: M_ShippingPackage.M_InOut_ID
-- 2023-01-13T09:52:24.578Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710101
;

-- 2023-01-13T09:52:24.826Z
DELETE FROM AD_Field WHERE AD_Field_ID=710101
;

-- 2023-01-13T09:52:25.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710104
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Partner Location
-- Column: M_ShippingPackage.C_BPartner_Location_ID
-- 2023-01-13T09:52:25.253Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710104
;

-- 2023-01-13T09:52:25.503Z
DELETE FROM AD_Field WHERE AD_Field_ID=710104
;

-- UI Element: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10 -> default.Kunde
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2023-01-13T09:52:26.387Z
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=541678
;

-- 2023-01-13T09:52:26.668Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614611
;

-- 2023-01-13T09:52:26.749Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710103
;

-- Field: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> Business Partner
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2023-01-13T09:52:26.870Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710103
;

-- 2023-01-13T09:52:27.148Z
DELETE FROM AD_Field WHERE AD_Field_ID=710103
;

-- Tab: Delivery Instruction(541657,D) -> Versandpackung(546733,D)
-- UI Section: main
-- UI Section: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main
-- UI Column: 10
-- UI Column: Delivery Instruction(541657,D) -> Versandpackung(546733,D) -> main -> 10
-- UI Element Group: default
-- 2023-01-13T09:52:28.197Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550204
;

-- 2023-01-13T09:52:28.481Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546542
;

-- 2023-01-13T09:52:28.563Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545363
;

-- 2023-01-13T09:52:28.808Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545363
;

-- 2023-01-13T09:52:28.889Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=546733
;

-- 2023-01-13T09:52:29.158Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=546733
;

