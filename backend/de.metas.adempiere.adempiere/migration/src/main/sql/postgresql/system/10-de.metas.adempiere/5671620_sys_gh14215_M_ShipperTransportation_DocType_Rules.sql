-- Name: C_Doctype Shippingorder only, exclude delivery instruction
-- 2023-01-13T09:33:17.930Z
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType = ''MST'' AND (C_DocType.AD_Org_ID = @AD_Org_ID@ OR C_DocType.AD_Org_ID = 0) AND C_DocType.AD_Client_ID = @AD_Client_ID@ AND C_DocType.DocSubType <> ''DI''', Name='C_Doctype Shippingorder only, exclude delivery instruction',Updated=TO_TIMESTAMP('2023-01-13 11:33:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540057
;

-- Name: C_Doctype Shippingorder only
-- 2023-01-13T09:33:45.965Z
UPDATE AD_Val_Rule SET Description='Exclude delivery instructions', Name='C_Doctype Shippingorder only',Updated=TO_TIMESTAMP('2023-01-13 11:33:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540057
;

-- Name: C_Doctype Shippingorder only
-- 2023-01-13T09:41:36.965Z
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType = ''MST'' AND (C_DocType.AD_Org_ID = @AD_Org_ID@ OR C_DocType.AD_Org_ID = 0) AND C_DocType.AD_Client_ID = @AD_Client_ID@ ', Description='',Updated=TO_TIMESTAMP('2023-01-13 11:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540057
;

-- Name: C_Doctype Shippingorder Exclude Delivery Instruction
-- 2023-01-13T09:42:06.092Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540614,'C_DocType.DocBaseType = ''MST'' AND (C_DocType.AD_Org_ID = @AD_Org_ID@ OR C_DocType.AD_Org_ID = 0) AND C_DocType.AD_Client_ID = @AD_Client_ID@ AND C_DocType.DocSubType <> ''DI''',TO_TIMESTAMP('2023-01-13 11:42:05','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','C_Doctype Shippingorder Exclude Delivery Instruction','S',TO_TIMESTAMP('2023-01-13 11:42:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Doctype  Delivery Instruction
-- 2023-01-13T09:42:54.497Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540615,'C_DocType.DocBaseType = ''MST'' AND (C_DocType.AD_Org_ID = @AD_Org_ID@ OR C_DocType.AD_Org_ID = 0) AND C_DocType.AD_Client_ID = @AD_Client_ID@ AND C_DocType.DocSubType =  ''DI''',TO_TIMESTAMP('2023-01-13 11:42:54','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','C_Doctype  Delivery Instruction','S',TO_TIMESTAMP('2023-01-13 11:42:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Doctype Shippingorder Exclude Delivery Instruction
-- 2023-01-13T09:43:12.219Z
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType = ''MST'' AND (C_DocType.AD_Org_ID = @AD_Org_ID@ OR C_DocType.AD_Org_ID = 0) AND C_DocType.AD_Client_ID = @AD_Client_ID@ AND C_DocType.DocSubType <>  ''DI''',Updated=TO_TIMESTAMP('2023-01-13 11:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540614
;

-- Field: Transportation Order(540020,METAS_SHIPPING) -> Transportation Delivery(540096,METAS_SHIPPING) -> Document Type
-- Column: M_ShipperTransportation.C_DocType_ID
-- 2023-01-13T09:46:58.023Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540614,Updated=TO_TIMESTAMP('2023-01-13 11:46:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545063
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Document Type
-- Column: M_ShipperTransportation.C_DocType_ID
-- 2023-01-13T09:48:50.617Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540615,Updated=TO_TIMESTAMP('2023-01-13 11:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710089
;



-- Value: de.metas.deliveryplanning.DeliveryPlanningService.NoForwarder
-- 2023-01-17T19:47:45.691Z
UPDATE AD_Message SET MsgText='At least one lin has no forwarder.',Updated=TO_TIMESTAMP('2023-01-17 21:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545221
;

-- Tab: Transportation Order(540020,METAS_SHIPPING) -> Transportation Delivery
-- Table: M_ShipperTransportation
-- 2023-01-17T20:09:42.805Z
UPDATE AD_Tab SET WhereClause='exists(select 1 from c_doctype dt where M_ShipperTransportation.c_doctype_id = dt.c_doctype_id and dt.docsubtype <> ''DI'')',Updated=TO_TIMESTAMP('2023-01-17 22:09:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540096
;

-- Tab: Transportation Order(540020,METAS_SHIPPING) -> Transportation Delivery
-- Table: M_ShipperTransportation
-- 2023-01-17T20:12:39.365Z
UPDATE AD_Tab SET WhereClause='exists(select 1 from c_doctype dt where M_ShipperTransportation.c_doctype_id = dt.c_doctype_id and dt.docsubtype != ''DI'')',Updated=TO_TIMESTAMP('2023-01-17 22:12:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540096
;

-- Tab: Transportation Order(540020,METAS_SHIPPING) -> Transportation Delivery
-- Table: M_ShipperTransportation
-- 2023-01-17T20:18:19.821Z
UPDATE AD_Tab SET WhereClause='',Updated=TO_TIMESTAMP('2023-01-17 22:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540096
;

-- Tab: Transportation Order(540020,METAS_SHIPPING) -> Transportation Delivery
-- Table: M_ShipperTransportation
-- 2023-01-17T20:20:26.459Z
UPDATE AD_Tab SET WhereClause='exists(select 1 from c_doctype dt where M_InOut.c_doctype_id = dt.c_doctype_id and dt.docsubtype != ''DI'')',Updated=TO_TIMESTAMP('2023-01-17 22:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540096
;

-- Tab: Transportation Order(540020,METAS_SHIPPING) -> Transportation Delivery
-- Table: M_ShipperTransportation
-- 2023-01-17T20:21:19.077Z
UPDATE AD_Tab SET WhereClause='exists(select 1 from c_doctype dt where M_ShipperTransportation.c_doctype_id = dt.c_doctype_id and dt.docsubtype != ''DI'')',Updated=TO_TIMESTAMP('2023-01-17 22:21:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540096
;

-- Tab: Transportation Order(540020,METAS_SHIPPING) -> Transportation Delivery
-- Table: M_ShipperTransportation
-- 2023-01-17T20:25:34.988Z
UPDATE AD_Tab SET WhereClause='exists(select 1 from c_doctype dt where M_ShipperTransportation.c_doctype_id = dt.c_doctype_id and (dt.docsubtype is null OR dt.docsubtype != ''DI''))',Updated=TO_TIMESTAMP('2023-01-17 22:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540096
;




