-- 2022-12-23T10:38:07.650Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581895,0,'LoadingTime',TO_TIMESTAMP('2022-12-23 12:38:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Loading Time','Loading Time',TO_TIMESTAMP('2022-12-23 12:38:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T10:38:07.696Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581895 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-23T10:38:25.111Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581896,0,'DeliveryTime',TO_TIMESTAMP('2022-12-23 12:38:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Time','Delivery Time',TO_TIMESTAMP('2022-12-23 12:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T10:38:25.153Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581896 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Delivery_Planning.LoadingTime
-- 2022-12-23T10:39:20.123Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585431,581895,0,10,542259,'LoadingTime',TO_TIMESTAMP('2022-12-23 12:39:19','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Loading Time',0,0,TO_TIMESTAMP('2022-12-23 12:39:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T10:39:20.167Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585431 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T10:39:20.284Z
/* DDL */  select update_Column_Translation_From_AD_Element(581895) 
;

-- 2022-12-23T10:39:26.769Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN LoadingTime VARCHAR(250)')
;

-- Column: M_Delivery_Planning.DeliveryTime
-- 2022-12-23T10:39:55.944Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585432,581896,0,10,542259,'DeliveryTime',TO_TIMESTAMP('2022-12-23 12:39:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Delivery Time',0,0,TO_TIMESTAMP('2022-12-23 12:39:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-23T10:39:55.984Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585432 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-23T10:39:56.074Z
/* DDL */  select update_Column_Translation_From_AD_Element(581896) 
;

-- 2022-12-23T10:40:02.265Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN DeliveryTime VARCHAR(250)')
;




-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Loading Time
-- Column: M_Delivery_Planning.LoadingTime
-- 2022-12-23T10:42:04.887Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585431,710067,0,546674,TO_TIMESTAMP('2022-12-23 12:42:04','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Loading Time',TO_TIMESTAMP('2022-12-23 12:42:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T10:42:04.930Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T10:42:04.973Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581895) 
;

-- 2022-12-23T10:42:05.028Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710067
;

-- 2022-12-23T10:42:05.071Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710067)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Delivery Time
-- Column: M_Delivery_Planning.DeliveryTime
-- 2022-12-23T10:42:05.801Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585432,710068,0,546674,TO_TIMESTAMP('2022-12-23 12:42:05','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Delivery Time',TO_TIMESTAMP('2022-12-23 12:42:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-23T10:42:05.842Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-23T10:42:05.884Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581896) 
;

-- 2022-12-23T10:42:05.928Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710068
;

-- 2022-12-23T10:42:05.968Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710068)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Loading Time
-- Column: M_Delivery_Planning.LoadingTime
-- 2022-12-23T10:46:25.748Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710067,0,546674,550030,614579,'F',TO_TIMESTAMP('2022-12-23 12:46:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Loading Time',25,0,0,TO_TIMESTAMP('2022-12-23 12:46:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Delivery Time
-- Column: M_Delivery_Planning.DeliveryTime
-- 2022-12-23T10:47:37.411Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710068,0,546674,550030,614580,'F',TO_TIMESTAMP('2022-12-23 12:47:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Delivery Time',45,0,0,TO_TIMESTAMP('2022-12-23 12:47:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Delivery Time
-- Column: M_Delivery_Planning.DeliveryTime
-- 2022-12-23T10:48:14.670Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2022-12-23 12:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614580
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Release No
-- Column: M_Delivery_Planning.ReleaseNo
-- 2022-12-23T10:48:14.920Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2022-12-23 12:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613501
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Planned Loading Date
-- Column: M_Delivery_Planning.PlannedLoadingDate
-- 2022-12-23T10:48:15.169Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2022-12-23 12:48:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613502
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Actual Loading Date
-- Column: M_Delivery_Planning.ActualLoadingDate
-- 2022-12-23T10:48:15.486Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2022-12-23 12:48:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613503
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> dates.Loading Time
-- Column: M_Delivery_Planning.LoadingTime
-- 2022-12-23T10:48:15.738Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2022-12-23 12:48:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614579
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Load Qty
-- Column: M_Delivery_Planning.ActualLoadQty
-- 2022-12-23T10:48:15.991Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2022-12-23 12:48:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613504
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Delivered Qty
-- Column: M_Delivery_Planning.ActualDeliveredQty
-- 2022-12-23T10:48:16.244Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2022-12-23 12:48:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613505
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Planned Loaded Quantity
-- Column: M_Delivery_Planning.PlannedLoadedQuantity
-- 2022-12-23T10:48:16.540Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2022-12-23 12:48:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613647
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Planned Discharge Quantity
-- Column: M_Delivery_Planning.PlannedDischargeQuantity
-- 2022-12-23T10:48:16.790Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2022-12-23 12:48:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613648
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.Actual Discharge Quantity
-- Column: M_Delivery_Planning.ActualDischargeQuantity
-- 2022-12-23T10:48:17.048Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2022-12-23 12:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613649
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Batch
-- Column: M_Delivery_Planning.Batch
-- 2022-12-23T10:48:17.297Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2022-12-23 12:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613506
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Country Of Origin
-- Column: M_Delivery_Planning.OriginCountry
-- 2022-12-23T10:48:17.593Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2022-12-23 12:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613507
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Forwarder
-- Column: M_Delivery_Planning.M_Forwarder_ID
-- 2022-12-23T10:48:17.846Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2022-12-23 12:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613509
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Details
-- Column: M_Delivery_Planning.TransportDetails
-- 2022-12-23T10:48:18.098Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2022-12-23 12:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613510
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Way Bill No
-- Column: M_Delivery_Planning.WayBillNo
-- 2022-12-23T10:48:18.394Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2022-12-23 12:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613511
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Transport Order
-- Column: M_Delivery_Planning.TransportationOrderNo
-- 2022-12-23T10:48:18.643Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=340,Updated=TO_TIMESTAMP('2022-12-23 12:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613512
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> qtys.UOM
-- Column: M_Delivery_Planning.C_UOM_ID
-- 2022-12-23T10:48:18.891Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=350,Updated=TO_TIMESTAMP('2022-12-23 12:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613566
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 20 -> flags.Verarbeitet
-- Column: M_Delivery_Planning.Processed
-- 2022-12-23T10:48:19.139Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=360,Updated=TO_TIMESTAMP('2022-12-23 12:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613915
;

