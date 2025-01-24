-- Table: M_Forwarder
-- 2023-01-16T10:13:19.237Z
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-01-16 12:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542266
;



-- Name: M_Forwarder(Active)
-- 2023-01-16T11:18:58.081Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541694
;

-- 2023-01-16T11:18:58.087Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541694
;






-- Name: Spediteur
-- Action Type: W
-- Window: Spediteur(541634,D)
-- 2023-01-16T11:29:09.090Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=542023
;

-- 2023-01-16T11:29:09.096Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=542023
;

-- 2023-01-16T11:29:09.099Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=542023 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Tab: Spediteur(541634,D) -> Spediteur
-- Table: M_Forwarder
-- UI Element: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 10 -> desc.Description
-- Column: M_Forwarder.Description
-- 2023-01-16T11:29:44.853Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613630
;

-- 2023-01-16T11:29:44.855Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708248
;

-- Field: Spediteur(541634,D) -> Spediteur(546684,D) -> Beschreibung
-- Column: M_Forwarder.Description
-- 2023-01-16T11:29:44.859Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708248
;

-- 2023-01-16T11:29:44.863Z
DELETE FROM AD_Field WHERE AD_Field_ID=708248
;

-- UI Element: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 10 -> main.Name
-- Column: M_Forwarder.Name
-- 2023-01-16T11:29:44.880Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613621
;

-- 2023-01-16T11:29:44.883Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708250
;

-- Field: Spediteur(541634,D) -> Spediteur(546684,D) -> Name
-- Column: M_Forwarder.Name
-- 2023-01-16T11:29:44.886Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708250
;

-- 2023-01-16T11:29:44.890Z
DELETE FROM AD_Field WHERE AD_Field_ID=708250
;

-- UI Element: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 10 -> section_code.Section Code
-- Column: M_Forwarder.M_SectionCode_ID
-- 2023-01-16T11:29:44.907Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613623
;

-- 2023-01-16T11:29:44.909Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708252
;

-- Field: Spediteur(541634,D) -> Spediteur(546684,D) -> Sektionskennung
-- Column: M_Forwarder.M_SectionCode_ID
-- 2023-01-16T11:29:44.912Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708252
;

-- 2023-01-16T11:29:44.917Z
DELETE FROM AD_Field WHERE AD_Field_ID=708252
;

-- UI Element: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 10 -> main.Search Key
-- Column: M_Forwarder.Value
-- 2023-01-16T11:29:44.933Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613620
;

-- 2023-01-16T11:29:44.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708251
;

-- Field: Spediteur(541634,D) -> Spediteur(546684,D) -> Suchschlüssel
-- Column: M_Forwarder.Value
-- 2023-01-16T11:29:44.938Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708251
;

-- 2023-01-16T11:29:44.943Z
DELETE FROM AD_Field WHERE AD_Field_ID=708251
;

-- UI Element: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 20 -> client.Organisation
-- Column: M_Forwarder.AD_Org_ID
-- 2023-01-16T11:29:44.960Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613625
;

-- 2023-01-16T11:29:44.962Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708246
;

-- Field: Spediteur(541634,D) -> Spediteur(546684,D) -> Sektion
-- Column: M_Forwarder.AD_Org_ID
-- 2023-01-16T11:29:44.965Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708246
;

-- 2023-01-16T11:29:44.969Z
DELETE FROM AD_Field WHERE AD_Field_ID=708246
;

-- UI Element: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 20 -> client.Client
-- Column: M_Forwarder.AD_Client_ID
-- 2023-01-16T11:29:44.987Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613626
;

-- 2023-01-16T11:29:44.989Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708245
;

-- Field: Spediteur(541634,D) -> Spediteur(546684,D) -> Mandant
-- Column: M_Forwarder.AD_Client_ID
-- 2023-01-16T11:29:44.993Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708245
;

-- 2023-01-16T11:29:44.997Z
DELETE FROM AD_Field WHERE AD_Field_ID=708245
;

-- UI Element: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 20 -> flags.Active
-- Column: M_Forwarder.IsActive
-- 2023-01-16T11:29:45.015Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613624
;

-- 2023-01-16T11:29:45.017Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708249
;

-- Field: Spediteur(541634,D) -> Spediteur(546684,D) -> Aktiv
-- Column: M_Forwarder.IsActive
-- 2023-01-16T11:29:45.020Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708249
;

-- 2023-01-16T11:29:45.024Z
DELETE FROM AD_Field WHERE AD_Field_ID=708249
;

-- UI Element: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 10 -> main.Business Partner
-- Column: M_Forwarder.C_BPartner_ID
-- 2023-01-16T11:29:45.040Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613622
;

-- 2023-01-16T11:29:45.042Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708253
;

-- Field: Spediteur(541634,D) -> Spediteur(546684,D) -> Geschäftspartner
-- Column: M_Forwarder.C_BPartner_ID
-- 2023-01-16T11:29:45.045Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708253
;

-- 2023-01-16T11:29:45.049Z
DELETE FROM AD_Field WHERE AD_Field_ID=708253
;

-- Tab: Spediteur(541634,D) -> Spediteur(546684,D)
-- UI Section: primary
-- UI Section: Spediteur(541634,D) -> Spediteur(546684,D) -> primary
-- UI Column: 20
-- UI Column: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 20
-- UI Element Group: client
-- 2023-01-16T11:29:45.175Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550063
;

-- UI Column: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 20
-- UI Element Group: flags
-- 2023-01-16T11:29:45.185Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550062
;

-- 2023-01-16T11:29:45.199Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546473
;

-- UI Section: Spediteur(541634,D) -> Spediteur(546684,D) -> primary
-- UI Column: 10
-- UI Column: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 10
-- UI Element Group: desc
-- 2023-01-16T11:29:45.214Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550065
;

-- UI Column: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 10
-- UI Element Group: section_code
-- 2023-01-16T11:29:45.223Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550064
;

-- UI Column: Spediteur(541634,D) -> Spediteur(546684,D) -> primary -> 10
-- UI Element Group: main
-- 2023-01-16T11:29:45.232Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550060
;

-- 2023-01-16T11:29:45.237Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546472
;

-- 2023-01-16T11:29:45.239Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545308
;

-- 2023-01-16T11:29:45.243Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545308
;

-- 2023-01-16T11:29:45.245Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=546684
;

-- 2023-01-16T11:29:45.250Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=546684
;

-- Window: Spediteur, InternalName=null
-- 2023-01-16T11:29:48.879Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541634
;

-- 2023-01-16T11:29:48.880Z
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=541634
;

-- 2023-01-16T11:29:48.885Z
DELETE FROM AD_Window WHERE AD_Window_ID=541634
;

