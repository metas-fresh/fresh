-- Column: M_Delivery_Planning.IncotermLocation
-- 2022-12-23T11:27:20.874Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585445,501608,0,10,542259,'IncotermLocation',TO_TIMESTAMP('2022-12-23 13:27:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Location to be specified for commercial clause','D',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterm Location',0,0,TO_TIMESTAMP('2022-12-23 13:27:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T11:27:20.916Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585445 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T11:27:21Z
/* DDL */  select update_Column_Translation_From_AD_Element(501608) 
;

-- 2022-12-23T11:27:27.659Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN IncotermLocation VARCHAR(500)')
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Incoterm Location
-- Column: M_Delivery_Planning.IncotermLocation
-- 2022-12-23T11:28:34.433Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585445,710071,0,546674,TO_TIMESTAMP('2022-12-23 13:28:33','YYYY-MM-DD HH24:MI:SS'),100,'Location to be specified for commercial clause',500,'D','Y','Y','N','N','N','N','N','Incoterm Location',TO_TIMESTAMP('2022-12-23 13:28:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T11:28:34.474Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T11:28:34.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608) 
;

-- 2022-12-23T11:28:34.568Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710071
;

-- 2022-12-23T11:28:34.609Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710071)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Incoterm Location
-- Column: M_Delivery_Planning.IncotermLocation
-- 2022-12-23T12:15:34.987Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710071,0,546674,550028,614583,'F',TO_TIMESTAMP('2022-12-23 14:15:34','YYYY-MM-DD HH24:MI:SS'),100,'Location to be specified for commercial clause','Y','N','N','Y','N','N','N',0,'Incoterm Location',115,0,0,TO_TIMESTAMP('2022-12-23 14:15:34','YYYY-MM-DD HH24:MI:SS'),100)
;



-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Incoterm Location
-- Column: M_Delivery_Planning.IncotermLocation
-- 2022-12-23T14:15:12.115Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-12-23 16:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614583
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Document No
-- Column: M_Delivery_Planning.OrderDocumentNo
-- 2022-12-23T14:15:12.498Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-12-23 16:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613486
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Reference
-- Column: M_Delivery_Planning.POReference
-- 2022-12-23T14:15:12.748Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-12-23 16:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613487
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Partner Name
-- Column: M_Delivery_Planning.BPartnerName
-- 2022-12-23T14:15:13.001Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-12-23 16:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613488
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Ship-to location
-- Column: M_Delivery_Planning.ShipToLocation_Name
-- 2022-12-23T14:15:13.253Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-12-23 16:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613490
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Name
-- Column: M_Delivery_Planning.ProductName
-- 2022-12-23T14:15:13.533Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-12-23 16:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613491
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Product Value
-- Column: M_Delivery_Planning.ProductValue
-- 2022-12-23T14:15:13.786Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-12-23 16:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613492
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Grade
-- Column: M_Delivery_Planning.Grade
-- 2022-12-23T14:15:14.040Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-12-23 16:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613493
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Qty Ordered
-- Column: M_Delivery_Planning.QtyOrdered
-- 2022-12-23T14:15:14.312Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-12-23 16:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613494
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Qty Total Open
-- Column: M_Delivery_Planning.QtyTotalOpen
-- 2022-12-23T14:15:14.563Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-12-23 16:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613495
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Warehouse Name
-- Column: M_Delivery_Planning.WarehouseName
-- 2022-12-23T14:15:14.812Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-12-23 16:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613496
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Order Status
-- Column: M_Delivery_Planning.OrderStatus
-- 2022-12-23T14:15:15.060Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-12-23 16:15:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613498
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Delivery Date
-- Column: M_Delivery_Planning.PlannedDeliveryDate
-- 2022-12-23T14:15:15.344Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-12-23 16:15:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613499
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Delivery Date
-- Column: M_Delivery_Planning.ActualDeliveryDate
-- 2022-12-23T14:15:15.592Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2022-12-23 16:15:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613500
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Delivery Time
-- Column: M_Delivery_Planning.DeliveryTime
-- 2022-12-23T14:15:15.842Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2022-12-23 16:15:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614580
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Release No
-- Column: M_Delivery_Planning.ReleaseNo
-- 2022-12-23T14:15:16.092Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2022-12-23 16:15:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613501
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2022-12-23T14:15:16.398Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2022-12-23 16:15:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613502
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2022-12-23T14:15:16.647Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2022-12-23 16:15:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613503
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Loading Time
-- Column: M_Delivery_Planning.LoadingTime
-- 2022-12-23T14:15:16.911Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2022-12-23 16:15:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614579
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2022-12-23T14:15:17.160Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2022-12-23 16:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613504
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2022-12-23T14:15:17.413Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2022-12-23 16:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613505
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Planned Loaded Quantity
-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2022-12-23T14:15:17.662Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2022-12-23 16:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613647
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Planned Discharge Quantity
-- Column: M_Delivery_Planning.PlannedDischargeQuantity
-- 2022-12-23T14:15:17.909Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2022-12-23 16:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613648
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Discharge Quantity
-- Column: M_Delivery_Planning.ActualDischargeQuantity
-- 2022-12-23T14:15:18.161Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2022-12-23 16:15:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613649
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Batch
-- Column: M_Delivery_Planning.Batch
-- 2022-12-23T14:15:18.456Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2022-12-23 16:15:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613506
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Country Of Origin
-- Column: M_Delivery_Planning.OriginCountry
-- 2022-12-23T14:15:18.705Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2022-12-23 16:15:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613507
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Forwarder
-- Column: M_Delivery_Planning.M_Forwarder_ID
-- 2022-12-23T14:15:18.955Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2022-12-23 16:15:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613509
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Details
-- Column: M_Delivery_Planning.TransportDetails
-- 2022-12-23T14:15:19.201Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2022-12-23 16:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613510
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Way Bill No
-- Column: M_Delivery_Planning.WayBillNo
-- 2022-12-23T14:15:19.505Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=340,Updated=TO_TIMESTAMP('2022-12-23 16:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613511
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Order
-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2022-12-23T14:15:19.754Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=350,Updated=TO_TIMESTAMP('2022-12-23 16:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613512
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.UOM
-- Column: M_Delivery_Planning.C_UOM_ID
-- 2022-12-23T14:15:20.001Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=360,Updated=TO_TIMESTAMP('2022-12-23 16:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613566
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Verarbeitet
-- Column: M_Delivery_Planning.Processed
-- 2022-12-23T14:15:20.250Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=370,Updated=TO_TIMESTAMP('2022-12-23 16:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613915
;



