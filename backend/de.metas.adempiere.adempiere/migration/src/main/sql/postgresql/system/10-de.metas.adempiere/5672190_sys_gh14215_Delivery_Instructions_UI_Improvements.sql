-- Table: M_Delivery_Planning_Delivery_Instructions_V
-- 2023-01-17T15:03:47.511Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-01-17 17:03:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542280
;

-- Tab: Lieferplanung(541632,D) -> Delivery Instructions for Delivery Planning
-- Table: M_Delivery_Planning_Delivery_Instructions_V
-- 2023-01-17T15:04:33.838Z
UPDATE AD_Tab SET IsRefreshAllOnActivate='Y',Updated=TO_TIMESTAMP('2023-01-17 17:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546737
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Client
-- Column: M_ShipperTransportation.AD_Client_ID
-- 2023-01-17T15:18:47.543Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710075
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Organisation
-- Column: M_ShipperTransportation.AD_Org_ID
-- 2023-01-17T15:18:48.415Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710076
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Assign anonymously picked HUs
-- Column: M_ShipperTransportation.AssignAnonymouslyPickedHUs
-- 2023-01-17T15:18:48.907Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710111
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Delivery Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Delivery_ID
-- 2023-01-17T15:18:49.289Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710115
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Loading Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Loading_ID
-- 2023-01-17T15:18:50.329Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710112
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Loading Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Loading_ID
-- 2023-01-17T15:18:53.486Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710112
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Delivery Address
-- Column: M_ShipperTransportation.C_BPartner_Location_Delivery_ID
-- 2023-01-17T15:18:53.810Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710115
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Assign anonymously picked HUs
-- Column: M_ShipperTransportation.AssignAnonymouslyPickedHUs
-- 2023-01-17T15:18:54.099Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710111
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Organisation
-- Column: M_ShipperTransportation.AD_Org_ID
-- 2023-01-17T15:18:54.424Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710076
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Client
-- Column: M_ShipperTransportation.AD_Client_ID
-- 2023-01-17T15:18:55.856Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 17:18:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710075
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Transporteur
-- Column: M_ShipperTransportation.Shipper_BPartner_ID
-- 2023-01-17T15:21:40.701Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614584
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Ladeort
-- Column: M_ShipperTransportation.Shipper_Location_ID
-- 2023-01-17T15:21:42.808Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-01-17 17:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614585
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Incoterms
-- Column: M_ShipperTransportation.C_Incoterms_ID
-- 2023-01-17T15:22:31.180Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550198, SeqNo=40,Updated=TO_TIMESTAMP('2023-01-17 17:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614620
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Incoterm Location
-- Column: M_ShipperTransportation.IncotermLocation
-- 2023-01-17T15:22:43.369Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550198, SeqNo=50,Updated=TO_TIMESTAMP('2023-01-17 17:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614621
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10
-- UI Element Group: incoterm
-- 2023-01-17T15:22:50.157Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550207
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10
-- UI Element Group: description
-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> description.Tour
-- Column: M_ShipperTransportation.M_Tour_ID
-- 2023-01-17T15:23:13.294Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614588
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> description.Beschreibung
-- Column: M_ShipperTransportation.Description
-- 2023-01-17T15:23:13.306Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614587
;

-- 2023-01-17T15:23:13.311Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550199
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> default.Means of Transportation
-- Column: M_ShipperTransportation.M_MeansOfTransportation_ID
-- 2023-01-17T15:23:34.645Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710120,0,546732,550198,614701,'F',TO_TIMESTAMP('2023-01-17 17:23:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Means of Transportation',60,0,0,TO_TIMESTAMP('2023-01-17 17:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 20 -> delivery details.Means of Transportation
-- Column: M_ShipperTransportation.M_MeansOfTransportation_ID
-- 2023-01-17T15:24:35.245Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614622
;




-- Value: M_Delivery_Planning_GenerateDeliveryInstruction
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateDeliveryInstruction
-- 2023-01-17T15:31:17.693Z
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2023-01-17 17:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585176
;



-- Table: M_Delivery_Planning_Delivery_Instructions_V
-- 2023-01-17T16:01:40.845Z
UPDATE AD_Table SET AD_Window_ID=541632,Updated=TO_TIMESTAMP('2023-01-17 18:01:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542280
;

-- Process: M_Delivery_Planning_GenerateDeliveryInstruction(de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateDeliveryInstruction)
-- Table: M_Delivery_Planning
-- Window: Delivery Instruction(541657,D)
-- EntityType: D
-- 2023-01-17T18:05:30.149Z
UPDATE AD_Table_Process SET AD_Window_ID=541657, WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2023-01-17 20:05:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541327
;

-- Process: M_Delivery_Planning_GenerateDeliveryInstruction(de.metas.deliveryplanning.process.M_Delivery_Planning_GenerateDeliveryInstruction)
-- Table: M_Delivery_Planning
-- Window: Delivery Planning(541632,D)
-- EntityType: D
-- 2023-01-17T18:06:05.848Z
UPDATE AD_Table_Process SET AD_Window_ID=541632,Updated=TO_TIMESTAMP('2023-01-17 20:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541327
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Forwarder
-- Column: M_ShipperTransportation.M_Shipper_ID
-- 2023-01-17T18:16:06.572Z
UPDATE AD_Field SET AD_Name_ID=581379, Description=NULL, Help=NULL, Name='Forwarder',Updated=TO_TIMESTAMP('2023-01-17 20:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710090
;

-- 2023-01-17T18:16:06.576Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581379) 
;

-- 2023-01-17T18:16:06.581Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710090
;

-- 2023-01-17T18:16:06.583Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710090)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Material Name
-- Column: M_ShippingPackage.ProductName
-- 2023-01-17T18:17:03.887Z
UPDATE AD_Field SET AD_Name_ID=581739, Description=NULL, Help=NULL, Name='Material Name',Updated=TO_TIMESTAMP('2023-01-17 20:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710200
;

-- 2023-01-17T18:17:03.888Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581739) 
;

-- 2023-01-17T18:17:03.892Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710200
;

-- 2023-01-17T18:17:03.893Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710200)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Material Code
-- Column: M_ShippingPackage.ProductValue
-- 2023-01-17T18:17:15.028Z
UPDATE AD_Field SET AD_Name_ID=581747, Description=NULL, Help=NULL, Name='Material Code',Updated=TO_TIMESTAMP('2023-01-17 20:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710201
;

-- 2023-01-17T18:17:15.029Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581747) 
;

-- 2023-01-17T18:17:15.032Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710201
;

-- 2023-01-17T18:17:15.033Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710201)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Material
-- Column: M_ShippingPackage.M_Product_ID
-- 2023-01-17T18:17:59.672Z
UPDATE AD_Field SET AD_Name_ID=581748, Description=NULL, Help=NULL, Name='Material',Updated=TO_TIMESTAMP('2023-01-17 20:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710199
;

-- 2023-01-17T18:17:59.673Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581748) 
;

-- 2023-01-17T18:17:59.676Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710199
;

-- 2023-01-17T18:17:59.677Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710199)
;

-- Field: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> Material
-- Column: M_Delivery_Planning_Delivery_Instructions_V.M_Product_ID
-- 2023-01-17T18:18:45.320Z
UPDATE AD_Field SET AD_Name_ID=581748, Description=NULL, Help=NULL, Name='Material',Updated=TO_TIMESTAMP('2023-01-17 20:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710217
;

-- 2023-01-17T18:18:45.322Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581748) 
;

-- 2023-01-17T18:18:45.325Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710217
;

-- 2023-01-17T18:18:45.326Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710217)
;

-- Field: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> Delivery Instruction
-- Column: M_Delivery_Planning_Delivery_Instructions_V.M_ShipperTransportation_ID
-- 2023-01-17T18:19:00.374Z
UPDATE AD_Field SET AD_Name_ID=581903, Description=NULL, Help=NULL, Name='Delivery Instruction',Updated=TO_TIMESTAMP('2023-01-17 20:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710208
;

-- 2023-01-17T18:19:00.376Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581903) 
;

-- 2023-01-17T18:19:00.380Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710208
;

-- 2023-01-17T18:19:00.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710208)
;

-- Field: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> Delivery Date
-- Column: M_Delivery_Planning_Delivery_Instructions_V.DeliveryDate
-- 2023-01-17T18:20:18.477Z
UPDATE AD_Field SET AD_Name_ID=581902, Description=NULL, Help=NULL, Name='Delivery Date',Updated=TO_TIMESTAMP('2023-01-17 20:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710213
;

-- 2023-01-17T18:20:18.478Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581902) 
;

-- 2023-01-17T18:20:18.484Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710213
;

-- 2023-01-17T18:20:18.485Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710213)
;

-- 2023-01-17T18:22:14.210Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581927,0,TO_TIMESTAMP('2023-01-17 20:22:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Quantity Load','Quantity Load',TO_TIMESTAMP('2023-01-17 20:22:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T18:22:14.211Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581927 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-17T18:22:31.805Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581928,0,TO_TIMESTAMP('2023-01-17 20:22:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Quantity Discharged','Quantity Discharged',TO_TIMESTAMP('2023-01-17 20:22:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T18:22:31.807Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581928 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> Quantity Load
-- Column: M_Delivery_Planning_Delivery_Instructions_V.ActualLoadQty
-- 2023-01-17T18:23:19.085Z
UPDATE AD_Field SET AD_Name_ID=581927, Description=NULL, Help=NULL, Name='Quantity Load',Updated=TO_TIMESTAMP('2023-01-17 20:23:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710219
;

-- 2023-01-17T18:23:19.087Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581927) 
;

-- 2023-01-17T18:23:19.089Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710219
;

-- 2023-01-17T18:23:19.090Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710219)
;

-- Element: null
-- 2023-01-17T18:23:35.995Z
UPDATE AD_Element_Trl SET Name='Quantity Loaded', PrintName='Quantity Loaded',Updated=TO_TIMESTAMP('2023-01-17 20:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581927 AND AD_Language='en_US'
;

-- 2023-01-17T18:23:36Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581927,'en_US') 
;

-- 2023-01-17T18:23:36.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581927,'en_US') 
;

-- Field: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> Quantity Discharged
-- Column: M_Delivery_Planning_Delivery_Instructions_V.ActualDischargeQuantity
-- 2023-01-17T18:23:58.917Z
UPDATE AD_Field SET AD_Name_ID=581928, Description=NULL, Help=NULL, Name='Quantity Discharged',Updated=TO_TIMESTAMP('2023-01-17 20:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710220
;

-- 2023-01-17T18:23:58.919Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581928) 
;

-- 2023-01-17T18:23:58.923Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710220
;

-- 2023-01-17T18:23:58.923Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710220)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Quantity Loaded
-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-17T18:24:07.372Z
UPDATE AD_Field SET AD_Name_ID=581927, Description=NULL, Help=NULL, Name='Quantity Loaded',Updated=TO_TIMESTAMP('2023-01-17 20:24:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710204
;

-- 2023-01-17T18:24:07.374Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581927) 
;

-- 2023-01-17T18:24:07.377Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710204
;

-- 2023-01-17T18:24:07.378Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710204)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Quantity Discharged
-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-17T18:24:17.422Z
UPDATE AD_Field SET AD_Name_ID=581928, Description=NULL, Help=NULL, Name='Quantity Discharged',Updated=TO_TIMESTAMP('2023-01-17 20:24:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710205
;

-- 2023-01-17T18:24:17.424Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581928) 
;

-- 2023-01-17T18:24:17.427Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710205
;

-- 2023-01-17T18:24:17.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710205)
;





-- Value: de.metas.deliveryplanning.DeliveryPlanningService.NoForwarder
-- 2023-01-17T19:47:45.691Z
UPDATE AD_Message SET MsgText='At least one lin has no forwarder.',Updated=TO_TIMESTAMP('2023-01-17 21:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545221
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D)
-- UI Section: advanced edit
-- 2023-01-17T20:48:49.104Z
UPDATE AD_UI_Section SET IsActive='N',Updated=TO_TIMESTAMP('2023-01-17 22:48:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=545362
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Nr.
-- Column: M_Delivery_Planning_Delivery_Instructions_V.DocumentNo
-- 2023-01-17T20:58:52.639Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614681
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Belegstatus
-- Column: M_Delivery_Planning_Delivery_Instructions_V.DocStatus
-- 2023-01-17T20:58:53.096Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614684
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Belegdatum
-- Column: M_Delivery_Planning_Delivery_Instructions_V.DateDoc
-- 2023-01-17T20:58:53.755Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614685
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Loading Date
-- Column: M_Delivery_Planning_Delivery_Instructions_V.LoadingDate
-- 2023-01-17T20:58:54.676Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614686
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Lieferdatum
-- Column: M_Delivery_Planning_Delivery_Instructions_V.DeliveryDate
-- 2023-01-17T20:58:55.328Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614687
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Incoterms
-- Column: M_Delivery_Planning_Delivery_Instructions_V.C_Incoterms_ID
-- 2023-01-17T20:58:55.717Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614688
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.IncotermLocation
-- Column: M_Delivery_Planning_Delivery_Instructions_V.IncotermLocation
-- 2023-01-17T20:58:56.438Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614689
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Means of Transportation
-- Column: M_Delivery_Planning_Delivery_Instructions_V.M_MeansOfTransportation_ID
-- 2023-01-17T20:58:58.360Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614690
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Produkt
-- Column: M_Delivery_Planning_Delivery_Instructions_V.M_Product_ID
-- 2023-01-17T20:58:59.046Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614691
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Lagerort
-- Column: M_Delivery_Planning_Delivery_Instructions_V.M_Locator_ID
-- 2023-01-17T20:58:59.569Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:58:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614692
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Tatsächlich verladene Menge
-- Column: M_Delivery_Planning_Delivery_Instructions_V.ActualLoadQty
-- 2023-01-17T20:59:00.068Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:59:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614693
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Tatsächlich abgeladene Menge
-- Column: M_Delivery_Planning_Delivery_Instructions_V.ActualDischargeQuantity
-- 2023-01-17T20:59:03.307Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614694
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> main -> 10 -> default.Sektionskennung
-- Column: M_Delivery_Planning_Delivery_Instructions_V.M_SectionCode_ID
-- 2023-01-17T20:59:12.737Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 22:59:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614683
;

-- 2023-01-17T21:00:02.526Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581929,0,TO_TIMESTAMP('2023-01-17 23:00:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Document Status','Document Status',TO_TIMESTAMP('2023-01-17 23:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T21:00:02.532Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581929 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Delivery Planning(541632,D) -> Delivery Instructions for Delivery Planning(546737,D) -> Document Status
-- Column: M_Delivery_Planning_Delivery_Instructions_V.DocStatus
-- 2023-01-17T21:00:13.480Z
UPDATE AD_Field SET AD_Name_ID=581929, Description=NULL, Help=NULL, Name='Document Status',Updated=TO_TIMESTAMP('2023-01-17 23:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710210
;

-- 2023-01-17T21:00:13.482Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581929) 
;

-- 2023-01-17T21:00:13.486Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710210
;

-- 2023-01-17T21:00:13.487Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710210)
;

