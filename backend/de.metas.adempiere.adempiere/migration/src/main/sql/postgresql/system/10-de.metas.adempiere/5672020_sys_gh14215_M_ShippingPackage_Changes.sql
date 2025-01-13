-- Column: M_ShippingPackage.M_Product_ID
-- 2023-01-16T19:08:48.287Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585492,454,0,30,540031,'M_Product_ID',TO_TIMESTAMP('2023-01-16 21:08:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','METAS_SHIPPING',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2023-01-16 21:08:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T19:08:48.291Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585492 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T19:08:48.333Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2023-01-16T19:08:50.204Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2023-01-16T19:08:50.213Z
ALTER TABLE M_ShippingPackage ADD CONSTRAINT MProduct_MShippingPackage FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_ShippingPackage.ProductName
-- 2023-01-16T19:09:12.477Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585493,2659,0,10,540031,'ProductName',TO_TIMESTAMP('2023-01-16 21:09:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Name des Produktes','METAS_SHIPPING',0,600,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produktname',0,0,TO_TIMESTAMP('2023-01-16 21:09:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T19:09:12.479Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585493 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T19:09:12.481Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659) 
;

-- -- 2023-01-16T19:09:13.533Z
-- /* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN ProductName VARCHAR(600)')
-- ;

-- Column: M_ShippingPackage.ProductName
-- 2023-01-16T19:10:47.847Z
UPDATE AD_Column SET ColumnSQL='(SELECT p.Name from M_Product p where p.M_Product_ID=M_ShippingPackage.M_Product_ID)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-16 21:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585493
;

-- Column: M_ShippingPackage.ProductValue
-- 2023-01-16T19:11:45.466Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585494,1675,0,10,540031,'ProductValue','(SELECT p.Value from M_Product p where p.M_Product_ID=M_ShippingPackage.M_Product_ID)',TO_TIMESTAMP('2023-01-16 21:11:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID','METAS_SHIPPING',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Produktschlüssel',0,0,TO_TIMESTAMP('2023-01-16 21:11:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T19:11:45.467Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585494 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T19:11:45.470Z
/* DDL */  select update_Column_Translation_From_AD_Element(1675) 
;

-- Column: M_ShippingPackage.M_Locator_ID
-- 2023-01-16T19:12:18.789Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585495,448,0,30,540031,'M_Locator_ID',TO_TIMESTAMP('2023-01-16 21:12:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Lagerort im Lager','METAS_SHIPPING',0,10,'"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lagerort',0,0,TO_TIMESTAMP('2023-01-16 21:12:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T19:12:18.791Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585495 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T19:12:18.794Z
/* DDL */  select update_Column_Translation_From_AD_Element(448) 
;

-- 2023-01-16T19:12:19.658Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN M_Locator_ID NUMERIC(10)')
;

-- 2023-01-16T19:12:19.667Z
ALTER TABLE M_ShippingPackage ADD CONSTRAINT MLocator_MShippingPackage FOREIGN KEY (M_Locator_ID) REFERENCES public.M_Locator DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_ShippingPackage.Batch
-- 2023-01-16T19:12:47.023Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585496,581692,0,10,540031,'Batch',TO_TIMESTAMP('2023-01-16 21:12:46','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Stapel Nr.',0,0,TO_TIMESTAMP('2023-01-16 21:12:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T19:12:47.025Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585496 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T19:12:47.028Z
/* DDL */  select update_Column_Translation_From_AD_Element(581692) 
;

-- 2023-01-16T19:12:48.216Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN Batch VARCHAR(250)')
;

-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-16T19:13:30.753Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585497,581690,0,29,540031,'ActualLoadQty',TO_TIMESTAMP('2023-01-16 21:13:30','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tatsächlich verladene Menge',0,0,TO_TIMESTAMP('2023-01-16 21:13:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T19:13:30.754Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585497 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T19:13:30.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(581690) 
;

-- 2023-01-16T19:13:31.728Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN ActualLoadQty NUMERIC')
;

-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-16T19:14:05.178Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585498,581796,0,29,540031,'ActualDischargeQuantity',TO_TIMESTAMP('2023-01-16 21:14:04','YYYY-MM-DD HH24:MI:SS'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tatsächlich abgeladene Menge',0,0,TO_TIMESTAMP('2023-01-16 21:14:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T19:14:05.189Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585498 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T19:14:05.206Z
/* DDL */  select update_Column_Translation_From_AD_Element(581796) 
;

-- 2023-01-16T19:14:09.802Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN ActualDischargeQuantity NUMERIC')
;

-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-16T19:14:28.870Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-16 21:14:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585498
;

-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-16T19:14:38.791Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-16 21:14:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585497
;

-- -- 2023-01-16T19:14:40.105Z
-- INSERT INTO t_alter_column values('m_shippingpackage','ActualLoadQty','NUMERIC',null,null)
-- ;

-- -- 2023-01-16T19:14:40.109Z
-- INSERT INTO t_alter_column values('m_shippingpackage','ActualLoadQty',null,'NOT NULL',null)
-- ;

-- -- 2023-01-16T19:14:46.867Z
-- INSERT INTO t_alter_column values('m_shippingpackage','ActualDischargeQuantity','NUMERIC',null,null)
-- ;

-- -- 2023-01-16T19:14:46.871Z
-- INSERT INTO t_alter_column values('m_shippingpackage','ActualDischargeQuantity',null,'NOT NULL',null)
-- ;

-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-16T19:15:34.689Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2023-01-16 21:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585498
;

-- 2023-01-16T19:15:35.501Z
INSERT INTO t_alter_column values('m_shippingpackage','ActualDischargeQuantity','NUMERIC',null,'0')
;

-- 2023-01-16T19:15:35.507Z
UPDATE M_ShippingPackage SET ActualDischargeQuantity=0 WHERE ActualDischargeQuantity IS NULL
;

-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-16T19:15:42.730Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2023-01-16 21:15:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585497
;

-- 2023-01-16T19:15:44.836Z
INSERT INTO t_alter_column values('m_shippingpackage','ActualLoadQty','NUMERIC',null,'0')
;

-- 2023-01-16T19:15:44.839Z
UPDATE M_ShippingPackage SET ActualLoadQty=0 WHERE ActualLoadQty IS NULL
;




-- Column: M_ShippingPackage.C_UOM_ID
-- 2023-01-16T19:19:38.785Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585499,215,0,30,540031,'C_UOM_ID',TO_TIMESTAMP('2023-01-16 21:19:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','METAS_SHIPPING',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2023-01-16 21:19:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T19:19:38.787Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585499 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T19:19:38.789Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- 2023-01-16T19:19:39.777Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2023-01-16T19:19:39.784Z
ALTER TABLE M_ShippingPackage ADD CONSTRAINT CUOM_MShippingPackage FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Tab: Delivery Instruction(541657,D) -> Shipping Package
-- Table: M_ShippingPackage
-- 2023-01-16T19:26:07.155Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,540458,540097,0,546736,540031,541657,'Y',TO_TIMESTAMP('2023-01-16 21:26:07','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_ShippingPackage','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Shipping Package','N',20,1,TO_TIMESTAMP('2023-01-16 21:26:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:07.157Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546736 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-01-16T19:26:07.159Z
/* DDL */  select update_tab_translation_from_ad_element(540097) 
;

-- 2023-01-16T19:26:07.171Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546736)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Shipping Package
-- Column: M_ShippingPackage.M_ShippingPackage_ID
-- 2023-01-16T19:26:22.793Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540449,710161,0,546736,TO_TIMESTAMP('2023-01-16 21:26:22','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Shipping Package',TO_TIMESTAMP('2023-01-16 21:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:22.795Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:22.798Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540097) 
;

-- 2023-01-16T19:26:22.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710161
;

-- 2023-01-16T19:26:22.804Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710161)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Mandant
-- Column: M_ShippingPackage.AD_Client_ID
-- 2023-01-16T19:26:22.932Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540450,710162,0,546736,TO_TIMESTAMP('2023-01-16 21:26:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-01-16 21:26:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:22.935Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:22.938Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-16T19:26:23.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710162
;

-- 2023-01-16T19:26:23.160Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710162)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Sektion
-- Column: M_ShippingPackage.AD_Org_ID
-- 2023-01-16T19:26:23.260Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540451,710163,0,546736,TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:23.261Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:23.263Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-16T19:26:23.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710163
;

-- 2023-01-16T19:26:23.445Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710163)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Lieferung/Wareneingang
-- Column: M_ShippingPackage.M_InOut_ID
-- 2023-01-16T19:26:23.553Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540452,710164,0,546736,TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',22,'D','The Material Shipment / Receipt ','Y','N','N','N','N','N','N','N','Lieferung/Wareneingang',TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:23.554Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:23.556Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1025) 
;

-- 2023-01-16T19:26:23.560Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710164
;

-- 2023-01-16T19:26:23.561Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710164)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Package Net Total
-- Column: M_ShippingPackage.PackageNetTotal
-- 2023-01-16T19:26:23.654Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540453,710165,0,546736,TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Package Net Total',TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:23.656Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:23.657Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540090) 
;

-- 2023-01-16T19:26:23.660Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710165
;

-- 2023-01-16T19:26:23.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710165)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Package Weight
-- Column: M_ShippingPackage.PackageWeight
-- 2023-01-16T19:26:23.758Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540456,710166,0,546736,TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100,'Weight of a package',22,'D','The Weight indicates the weight of the package','Y','N','N','N','N','N','N','N','Package Weight',TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:23.760Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:23.762Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540096) 
;

-- 2023-01-16T19:26:23.770Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710166
;

-- 2023-01-16T19:26:23.771Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710166)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Aktiv
-- Column: M_ShippingPackage.IsActive
-- 2023-01-16T19:26:23.865Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540457,710167,0,546736,TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-01-16 21:26:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:23.866Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710167 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:23.868Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-16T19:26:24.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710167
;

-- 2023-01-16T19:26:24.141Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710167)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Transport Auftrag
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2023-01-16T19:26:24.250Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540458,710168,0,546736,TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Transport Auftrag',TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:24.251Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710168 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:24.253Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089) 
;

-- 2023-01-16T19:26:24.257Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710168
;

-- 2023-01-16T19:26:24.258Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710168)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Packstück
-- Column: M_ShippingPackage.M_Package_ID
-- 2023-01-16T19:26:24.355Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540459,710169,0,546736,TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Package',22,'D','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','N','N','N','N','N','Packstück',TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:24.357Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710169 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:24.359Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2410) 
;

-- 2023-01-16T19:26:24.362Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710169
;

-- 2023-01-16T19:26:24.363Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710169)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Geschäftspartner
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2023-01-16T19:26:24.474Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540460,710170,0,546736,TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',22,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:24.475Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710170 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:24.477Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-01-16T19:26:24.485Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710170
;

-- 2023-01-16T19:26:24.485Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710170)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Verarbeitet
-- Column: M_ShippingPackage.Processed
-- 2023-01-16T19:26:24.591Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540461,710171,0,546736,TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:24.593Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710171 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:24.596Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-01-16T19:26:24.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710171
;

-- 2023-01-16T19:26:24.614Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710171)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Standort
-- Column: M_ShippingPackage.C_BPartner_Location_ID
-- 2023-01-16T19:26:24.712Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540464,710172,0,546736,TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',22,'D','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:24.714Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710172 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:24.716Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2023-01-16T19:26:24.722Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710172
;

-- 2023-01-16T19:26:24.724Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710172)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Auftrag
-- Column: M_ShippingPackage.C_Order_ID
-- 2023-01-16T19:26:24.834Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550698,710173,0,546736,TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:24.836Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710173 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:24.838Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-01-16T19:26:24.853Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710173
;

-- 2023-01-16T19:26:24.854Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710173)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Notiz
-- Column: M_ShippingPackage.Note
-- 2023-01-16T19:26:24.949Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551102,710174,0,546736,TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',255,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','N','N','N','N','N','Notiz',TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:24.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710174 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:24.953Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2023-01-16T19:26:24.960Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710174
;

-- 2023-01-16T19:26:24.962Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710174)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Abholung
-- Column: M_ShippingPackage.IsToBeFetched
-- 2023-01-16T19:26:25.061Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569895,710175,0,546736,TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Abholung',TO_TIMESTAMP('2023-01-16 21:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:25.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710175 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:25.065Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542456) 
;

-- 2023-01-16T19:26:25.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710175
;

-- 2023-01-16T19:26:25.070Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710175)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produkt
-- Column: M_ShippingPackage.M_Product_ID
-- 2023-01-16T19:26:25.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585492,710176,0,546736,TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:25.178Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:25.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-01-16T19:26:25.212Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710176
;

-- 2023-01-16T19:26:25.213Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710176)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produktname
-- Column: M_ShippingPackage.ProductName
-- 2023-01-16T19:26:25.315Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585493,710177,0,546736,TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes',600,'D','Y','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:25.316Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710177 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:25.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659) 
;

-- 2023-01-16T19:26:25.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710177
;

-- 2023-01-16T19:26:25.323Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710177)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produktschlüssel
-- Column: M_ShippingPackage.ProductValue
-- 2023-01-16T19:26:25.417Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585494,710178,0,546736,TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID',250,'D','Y','N','N','N','N','N','N','N','Produktschlüssel',TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:25.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710178 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:25.420Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1675) 
;

-- 2023-01-16T19:26:25.424Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710178
;

-- 2023-01-16T19:26:25.426Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710178)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Lagerort
-- Column: M_ShippingPackage.M_Locator_ID
-- 2023-01-16T19:26:25.526Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585495,710179,0,546736,TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager',10,'D','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','N','N','N','N','N','Lagerort',TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:25.527Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710179 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:25.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448) 
;

-- 2023-01-16T19:26:25.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710179
;

-- 2023-01-16T19:26:25.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710179)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Stapel Nr.
-- Column: M_ShippingPackage.Batch
-- 2023-01-16T19:26:25.634Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585496,710180,0,546736,TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','N','N','N','N','N','N','N','Stapel Nr.',TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:25.635Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710180 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:25.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581692) 
;

-- 2023-01-16T19:26:25.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710180
;

-- 2023-01-16T19:26:25.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710180)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Tatsächlich verladene Menge
-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-16T19:26:25.733Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585497,710181,0,546736,TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Tatsächlich verladene Menge',TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:25.735Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:25.737Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581690) 
;

-- 2023-01-16T19:26:25.740Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710181
;

-- 2023-01-16T19:26:25.741Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710181)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Tatsächlich abgeladene Menge
-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-16T19:26:25.839Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585498,710182,0,546736,TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Tatsächlich abgeladene Menge',TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:25.847Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710182 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:25.856Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581796) 
;

-- 2023-01-16T19:26:25.877Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710182
;

-- 2023-01-16T19:26:25.883Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710182)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Maßeinheit
-- Column: M_ShippingPackage.C_UOM_ID
-- 2023-01-16T19:26:26.066Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585499,710183,0,546736,TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2023-01-16 21:26:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T19:26:26.076Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710183 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T19:26:26.088Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-01-16T19:26:26.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710183
;

-- 2023-01-16T19:26:26.239Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710183)
;

-- Tab: Delivery Instruction(541657,D) -> Shipping Package(546736,D)
-- UI Section: main
-- 2023-01-16T19:26:32.979Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546736,545369,TO_TIMESTAMP('2023-01-16 21:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-16 21:26:32','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-01-16T19:26:32.980Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545369 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main
-- UI Column: 10
-- 2023-01-16T19:26:33.113Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546549,545369,TO_TIMESTAMP('2023-01-16 21:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-16 21:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10
-- UI Element Group: default
-- 2023-01-16T19:26:33.253Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546549,550218,TO_TIMESTAMP('2023-01-16 21:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-01-16 21:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;






-- 2023-01-17T08:45:20.941Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710161
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Shipping Package
-- Column: M_ShippingPackage.M_ShippingPackage_ID
-- 2023-01-17T08:45:20.945Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710161
;

-- 2023-01-17T08:45:20.951Z
DELETE FROM AD_Field WHERE AD_Field_ID=710161
;

-- 2023-01-17T08:45:20.998Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710162
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Mandant
-- Column: M_ShippingPackage.AD_Client_ID
-- 2023-01-17T08:45:21Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710162
;

-- 2023-01-17T08:45:21.005Z
DELETE FROM AD_Field WHERE AD_Field_ID=710162
;

-- 2023-01-17T08:45:21.049Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710163
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Sektion
-- Column: M_ShippingPackage.AD_Org_ID
-- 2023-01-17T08:45:21.052Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710163
;

-- 2023-01-17T08:45:21.058Z
DELETE FROM AD_Field WHERE AD_Field_ID=710163
;

-- 2023-01-17T08:45:21.103Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710164
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Lieferung/Wareneingang
-- Column: M_ShippingPackage.M_InOut_ID
-- 2023-01-17T08:45:21.106Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710164
;

-- 2023-01-17T08:45:21.110Z
DELETE FROM AD_Field WHERE AD_Field_ID=710164
;

-- 2023-01-17T08:45:21.152Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710165
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Package Net Total
-- Column: M_ShippingPackage.PackageNetTotal
-- 2023-01-17T08:45:21.155Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710165
;

-- 2023-01-17T08:45:21.159Z
DELETE FROM AD_Field WHERE AD_Field_ID=710165
;

-- 2023-01-17T08:45:21.200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710166
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Package Weight
-- Column: M_ShippingPackage.PackageWeight
-- 2023-01-17T08:45:21.203Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710166
;

-- 2023-01-17T08:45:21.207Z
DELETE FROM AD_Field WHERE AD_Field_ID=710166
;

-- 2023-01-17T08:45:21.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710167
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Aktiv
-- Column: M_ShippingPackage.IsActive
-- 2023-01-17T08:45:21.251Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710167
;

-- 2023-01-17T08:45:21.256Z
DELETE FROM AD_Field WHERE AD_Field_ID=710167
;

-- 2023-01-17T08:45:21.297Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710168
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Transport Auftrag
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2023-01-17T08:45:21.300Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710168
;

-- 2023-01-17T08:45:21.304Z
DELETE FROM AD_Field WHERE AD_Field_ID=710168
;

-- 2023-01-17T08:45:21.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710169
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Packstück
-- Column: M_ShippingPackage.M_Package_ID
-- 2023-01-17T08:45:21.347Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710169
;

-- 2023-01-17T08:45:21.352Z
DELETE FROM AD_Field WHERE AD_Field_ID=710169
;

-- 2023-01-17T08:45:21.393Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710170
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Geschäftspartner
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2023-01-17T08:45:21.396Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710170
;

-- 2023-01-17T08:45:21.400Z
DELETE FROM AD_Field WHERE AD_Field_ID=710170
;

-- 2023-01-17T08:45:21.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710171
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Verarbeitet
-- Column: M_ShippingPackage.Processed
-- 2023-01-17T08:45:21.445Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710171
;

-- 2023-01-17T08:45:21.450Z
DELETE FROM AD_Field WHERE AD_Field_ID=710171
;

-- 2023-01-17T08:45:21.493Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710172
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Standort
-- Column: M_ShippingPackage.C_BPartner_Location_ID
-- 2023-01-17T08:45:21.497Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710172
;

-- 2023-01-17T08:45:21.501Z
DELETE FROM AD_Field WHERE AD_Field_ID=710172
;

-- 2023-01-17T08:45:21.543Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710173
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Auftrag
-- Column: M_ShippingPackage.C_Order_ID
-- 2023-01-17T08:45:21.546Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710173
;

-- 2023-01-17T08:45:21.550Z
DELETE FROM AD_Field WHERE AD_Field_ID=710173
;

-- 2023-01-17T08:45:21.590Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710174
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Notiz
-- Column: M_ShippingPackage.Note
-- 2023-01-17T08:45:21.593Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710174
;

-- 2023-01-17T08:45:21.597Z
DELETE FROM AD_Field WHERE AD_Field_ID=710174
;

-- 2023-01-17T08:45:21.638Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710175
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Abholung
-- Column: M_ShippingPackage.IsToBeFetched
-- 2023-01-17T08:45:21.641Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710175
;

-- 2023-01-17T08:45:21.645Z
DELETE FROM AD_Field WHERE AD_Field_ID=710175
;

-- 2023-01-17T08:45:21.685Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710176
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produkt
-- Column: M_ShippingPackage.M_Product_ID
-- 2023-01-17T08:45:21.688Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710176
;

-- 2023-01-17T08:45:21.692Z
DELETE FROM AD_Field WHERE AD_Field_ID=710176
;

-- 2023-01-17T08:45:21.732Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710177
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produktname
-- Column: M_ShippingPackage.ProductName
-- 2023-01-17T08:45:21.735Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710177
;

-- 2023-01-17T08:45:21.739Z
DELETE FROM AD_Field WHERE AD_Field_ID=710177
;

-- 2023-01-17T08:45:21.780Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710178
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produktschlüssel
-- Column: M_ShippingPackage.ProductValue
-- 2023-01-17T08:45:21.783Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710178
;

-- 2023-01-17T08:45:21.787Z
DELETE FROM AD_Field WHERE AD_Field_ID=710178
;

-- 2023-01-17T08:45:21.827Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710179
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Lagerort
-- Column: M_ShippingPackage.M_Locator_ID
-- 2023-01-17T08:45:21.829Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710179
;

-- 2023-01-17T08:45:21.833Z
DELETE FROM AD_Field WHERE AD_Field_ID=710179
;

-- 2023-01-17T08:45:21.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710180
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Stapel Nr.
-- Column: M_ShippingPackage.Batch
-- 2023-01-17T08:45:21.938Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710180
;

-- 2023-01-17T08:45:21.942Z
DELETE FROM AD_Field WHERE AD_Field_ID=710180
;

-- 2023-01-17T08:45:21.982Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710181
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Tatsächlich verladene Menge
-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-17T08:45:21.984Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710181
;

-- 2023-01-17T08:45:21.988Z
DELETE FROM AD_Field WHERE AD_Field_ID=710181
;

-- 2023-01-17T08:45:22.061Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710182
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Tatsächlich abgeladene Menge
-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-17T08:45:22.063Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710182
;

-- 2023-01-17T08:45:22.067Z
DELETE FROM AD_Field WHERE AD_Field_ID=710182
;

-- 2023-01-17T08:45:22.109Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710183
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Maßeinheit
-- Column: M_ShippingPackage.C_UOM_ID
-- 2023-01-17T08:45:22.112Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710183
;

-- 2023-01-17T08:45:22.116Z
DELETE FROM AD_Field WHERE AD_Field_ID=710183
;

-- Tab: Delivery Instruction(541657,D) -> Shipping Package(546736,D)
-- UI Section: main
-- UI Section: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main
-- UI Column: 10
-- UI Column: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10
-- UI Element Group: default
-- 2023-01-17T08:45:30.060Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550218
;

-- 2023-01-17T08:45:30.066Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546549
;

-- 2023-01-17T08:45:30.068Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545369
;

-- 2023-01-17T08:45:30.073Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545369
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Shipping Package
-- Column: M_ShippingPackage.M_ShippingPackage_ID
-- 2023-01-17T08:45:41.793Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540449,710184,0,546736,TO_TIMESTAMP('2023-01-17 10:45:41','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Shipping Package',TO_TIMESTAMP('2023-01-17 10:45:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:41.794Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710184 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:41.796Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540097) 
;

-- 2023-01-17T08:45:41.800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710184
;

-- 2023-01-17T08:45:41.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710184)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Mandant
-- Column: M_ShippingPackage.AD_Client_ID
-- 2023-01-17T08:45:41.894Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540450,710185,0,546736,TO_TIMESTAMP('2023-01-17 10:45:41','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-01-17 10:45:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:41.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710185 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:41.897Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-17T08:45:42.108Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710185
;

-- 2023-01-17T08:45:42.109Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710185)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Sektion
-- Column: M_ShippingPackage.AD_Org_ID
-- 2023-01-17T08:45:42.204Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540451,710186,0,546736,TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:42.205Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710186 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:42.206Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-17T08:45:42.364Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710186
;

-- 2023-01-17T08:45:42.365Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710186)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Lieferung/Wareneingang
-- Column: M_ShippingPackage.M_InOut_ID
-- 2023-01-17T08:45:42.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540452,710187,0,546736,TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',22,'D','The Material Shipment / Receipt ','Y','Y','N','N','N','N','N','Lieferung/Wareneingang',TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:42.468Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710187 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:42.469Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1025) 
;

-- 2023-01-17T08:45:42.473Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710187
;

-- 2023-01-17T08:45:42.474Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710187)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Package Net Total
-- Column: M_ShippingPackage.PackageNetTotal
-- 2023-01-17T08:45:42.570Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540453,710188,0,546736,TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','Y','N','N','N','N','N','Package Net Total',TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:42.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710188 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:42.573Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540090) 
;

-- 2023-01-17T08:45:42.576Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710188
;

-- 2023-01-17T08:45:42.577Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710188)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Package Weight
-- Column: M_ShippingPackage.PackageWeight
-- 2023-01-17T08:45:42.669Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540456,710189,0,546736,TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100,'Weight of a package',22,'D','The Weight indicates the weight of the package','Y','Y','N','N','N','N','N','Package Weight',TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:42.670Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710189 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:42.672Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540096) 
;

-- 2023-01-17T08:45:42.678Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710189
;

-- 2023-01-17T08:45:42.679Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710189)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Aktiv
-- Column: M_ShippingPackage.IsActive
-- 2023-01-17T08:45:42.760Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540457,710190,0,546736,TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:42.762Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710190 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:42.764Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-17T08:45:42.973Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710190
;

-- 2023-01-17T08:45:42.974Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710190)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Transport Auftrag
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2023-01-17T08:45:43.123Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540458,710191,0,546736,TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','Y','N','N','N','N','N','Transport Auftrag',TO_TIMESTAMP('2023-01-17 10:45:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.124Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710191 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.125Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089) 
;

-- 2023-01-17T08:45:43.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710191
;

-- 2023-01-17T08:45:43.130Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710191)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Packstück
-- Column: M_ShippingPackage.M_Package_ID
-- 2023-01-17T08:45:43.220Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540459,710192,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Package',22,'D','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','Y','N','N','N','N','N','Packstück',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710192 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.223Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2410) 
;

-- 2023-01-17T08:45:43.226Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710192
;

-- 2023-01-17T08:45:43.227Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710192)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Geschäftspartner
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2023-01-17T08:45:43.312Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540460,710193,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',22,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.313Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710193 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.315Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-01-17T08:45:43.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710193
;

-- 2023-01-17T08:45:43.323Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710193)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Verarbeitet
-- Column: M_ShippingPackage.Processed
-- 2023-01-17T08:45:43.410Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540461,710194,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.411Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710194 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.413Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-01-17T08:45:43.420Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710194
;

-- 2023-01-17T08:45:43.421Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710194)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Standort
-- Column: M_ShippingPackage.C_BPartner_Location_ID
-- 2023-01-17T08:45:43.515Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,540464,710195,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',22,'D','Identifiziert die Adresse des Geschäftspartners','Y','Y','N','N','N','N','N','Standort',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.517Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.518Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2023-01-17T08:45:43.522Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710195
;

-- 2023-01-17T08:45:43.523Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710195)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Auftrag
-- Column: M_ShippingPackage.C_Order_ID
-- 2023-01-17T08:45:43.606Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550698,710196,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','Y','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.607Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.608Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-01-17T08:45:43.620Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710196
;

-- 2023-01-17T08:45:43.621Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710196)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Notiz
-- Column: M_ShippingPackage.Note
-- 2023-01-17T08:45:43.721Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551102,710197,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',255,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','Y','N','N','N','N','N','Notiz',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.722Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710197 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.723Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2023-01-17T08:45:43.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710197
;

-- 2023-01-17T08:45:43.729Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710197)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Abholung
-- Column: M_ShippingPackage.IsToBeFetched
-- 2023-01-17T08:45:43.815Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569895,710198,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Abholung',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.817Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.818Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542456) 
;

-- 2023-01-17T08:45:43.821Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710198
;

-- 2023-01-17T08:45:43.822Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710198)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produkt
-- Column: M_ShippingPackage.M_Product_ID
-- 2023-01-17T08:45:43.908Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585492,710199,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:43.909Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710199 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:43.910Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-01-17T08:45:43.939Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710199
;

-- 2023-01-17T08:45:43.940Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710199)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produktname
-- Column: M_ShippingPackage.ProductName
-- 2023-01-17T08:45:44.030Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585493,710200,0,546736,TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes',600,'D','Y','Y','N','N','N','N','N','Produktname',TO_TIMESTAMP('2023-01-17 10:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:44.031Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:44.032Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659) 
;

-- 2023-01-17T08:45:44.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710200
;

-- 2023-01-17T08:45:44.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710200)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Produktschlüssel
-- Column: M_ShippingPackage.ProductValue
-- 2023-01-17T08:45:44.169Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585494,710201,0,546736,TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID',250,'D','Y','Y','N','N','N','N','N','Produktschlüssel',TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:44.170Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:44.172Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1675) 
;

-- 2023-01-17T08:45:44.175Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710201
;

-- 2023-01-17T08:45:44.175Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710201)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Lagerort
-- Column: M_ShippingPackage.M_Locator_ID
-- 2023-01-17T08:45:44.262Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585495,710202,0,546736,TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager',10,'D','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','Y','N','N','N','N','N','Lagerort',TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:44.263Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:44.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448) 
;

-- 2023-01-17T08:45:44.271Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710202
;

-- 2023-01-17T08:45:44.272Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710202)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Stapel Nr.
-- Column: M_ShippingPackage.Batch
-- 2023-01-17T08:45:44.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585496,710203,0,546736,TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Stapel Nr.',TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:44.365Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:44.366Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581692) 
;

-- 2023-01-17T08:45:44.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710203
;

-- 2023-01-17T08:45:44.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710203)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Tatsächlich verladene Menge
-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-17T08:45:44.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585497,710204,0,546736,TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Tatsächlich verladene Menge',TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:44.466Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:44.467Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581690) 
;

-- 2023-01-17T08:45:44.470Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710204
;

-- 2023-01-17T08:45:44.471Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710204)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Tatsächlich abgeladene Menge
-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-17T08:45:44.565Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585498,710205,0,546736,TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Tatsächlich abgeladene Menge',TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:44.566Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:44.567Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581796) 
;

-- 2023-01-17T08:45:44.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710205
;

-- 2023-01-17T08:45:44.570Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710205)
;

-- Field: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> Maßeinheit
-- Column: M_ShippingPackage.C_UOM_ID
-- 2023-01-17T08:45:44.664Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585499,710206,0,546736,TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','Y','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2023-01-17 10:45:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:44.665Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-17T08:45:44.666Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-01-17T08:45:44.681Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710206
;

-- 2023-01-17T08:45:44.681Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710206)
;

-- Tab: Delivery Instruction(541657,D) -> Shipping Package(546736,D)
-- UI Section: main
-- 2023-01-17T08:45:52.701Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546736,545370,TO_TIMESTAMP('2023-01-17 10:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-17 10:45:52','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-01-17T08:45:52.703Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545370 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main
-- UI Column: 10
-- 2023-01-17T08:45:52.788Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546550,545370,TO_TIMESTAMP('2023-01-17 10:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-17 10:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10
-- UI Element Group: default
-- 2023-01-17T08:45:52.859Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546550,550219,TO_TIMESTAMP('2023-01-17 10:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-01-17 10:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Mandant
-- Column: M_ShippingPackage.AD_Client_ID
-- 2023-01-17T08:45:53.063Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710185,0,546736,550219,614660,'F',TO_TIMESTAMP('2023-01-17 10:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2023-01-17 10:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Sektion
-- Column: M_ShippingPackage.AD_Org_ID
-- 2023-01-17T08:45:53.153Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710186,0,546736,550219,614661,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_ShippingPackage.M_InOut_ID
-- 2023-01-17T08:45:53.242Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710187,0,546736,550219,614662,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','N','Y','N','Lieferung/Wareneingang',0,30,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Package Net Total
-- Column: M_ShippingPackage.PackageNetTotal
-- 2023-01-17T08:45:53.330Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710188,0,546736,550219,614663,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Package Net Total',0,40,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Package Weight
-- Column: M_ShippingPackage.PackageWeight
-- 2023-01-17T08:45:53.420Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710189,0,546736,550219,614664,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Weight of a package','The Weight indicates the weight of the package','Y','N','N','Y','N','Package Weight',0,50,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Aktiv
-- Column: M_ShippingPackage.IsActive
-- 2023-01-17T08:45:53.507Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710190,0,546736,550219,614665,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,60,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Transport Auftrag
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2023-01-17T08:45:53.586Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710191,0,546736,550219,614666,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Transport Auftrag',0,70,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Packstück
-- Column: M_ShippingPackage.M_Package_ID
-- 2023-01-17T08:45:53.673Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710192,0,546736,550219,614667,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Shipment Package','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','Y','N','Packstück',0,80,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Geschäftspartner
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2023-01-17T08:45:53.763Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710193,0,546736,550219,614668,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','Geschäftspartner',0,90,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Verarbeitet
-- Column: M_ShippingPackage.Processed
-- 2023-01-17T08:45:53.852Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710194,0,546736,550219,614669,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','Verarbeitet',0,100,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T08:45:53.995Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,710195,0,541679,614668,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Auftrag
-- Column: M_ShippingPackage.C_Order_ID
-- 2023-01-17T08:45:54.085Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710196,0,546736,550219,614670,'F',TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','Auftrag',0,110,0,TO_TIMESTAMP('2023-01-17 10:45:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Notiz
-- Column: M_ShippingPackage.Note
-- 2023-01-17T08:45:54.176Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710197,0,546736,550219,614671,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','Y','N','Notiz',0,120,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Abholung
-- Column: M_ShippingPackage.IsToBeFetched
-- 2023-01-17T08:45:54.290Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710198,0,546736,550219,614672,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Abholung',0,130,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produkt
-- Column: M_ShippingPackage.M_Product_ID
-- 2023-01-17T08:45:54.378Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710199,0,546736,550219,614673,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','Produkt',0,140,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produktname
-- Column: M_ShippingPackage.ProductName
-- 2023-01-17T08:45:54.461Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710200,0,546736,550219,614674,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes','Y','N','N','Y','N','Produktname',0,150,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produktschlüssel
-- Column: M_ShippingPackage.ProductValue
-- 2023-01-17T08:45:54.552Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710201,0,546736,550219,614675,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID','Y','N','N','Y','N','Produktschlüssel',0,160,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Lagerort
-- Column: M_ShippingPackage.M_Locator_ID
-- 2023-01-17T08:45:54.640Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710202,0,546736,550219,614676,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','Y','N','Lagerort',0,170,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Stapel Nr.
-- Column: M_ShippingPackage.Batch
-- 2023-01-17T08:45:54.725Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710203,0,546736,550219,614677,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Stapel Nr.',0,180,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Tatsächlich verladene Menge
-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-17T08:45:54.816Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710204,0,546736,550219,614678,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Tatsächlich verladene Menge',0,190,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Tatsächlich abgeladene Menge
-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-17T08:45:54.906Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710205,0,546736,550219,614679,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Tatsächlich abgeladene Menge',0,200,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Maßeinheit
-- Column: M_ShippingPackage.C_UOM_ID
-- 2023-01-17T08:45:54.989Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710206,0,546736,550219,614680,'F',TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','Maßeinheit',0,210,0,TO_TIMESTAMP('2023-01-17 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Mandant
-- Column: M_ShippingPackage.AD_Client_ID
-- 2023-01-17T08:48:04.470Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614660
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Sektion
-- Column: M_ShippingPackage.AD_Org_ID
-- 2023-01-17T08:48:04.476Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614661
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Geschäftspartner
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2023-01-17T08:48:04.483Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614668
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Auftrag
-- Column: M_ShippingPackage.C_Order_ID
-- 2023-01-17T08:48:04.489Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614670
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Aktiv
-- Column: M_ShippingPackage.IsActive
-- 2023-01-17T08:48:04.495Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614665
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Abholung
-- Column: M_ShippingPackage.IsToBeFetched
-- 2023-01-17T08:48:04.501Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614672
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_ShippingPackage.M_InOut_ID
-- 2023-01-17T08:48:04.507Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614662
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Packstück
-- Column: M_ShippingPackage.M_Package_ID
-- 2023-01-17T08:48:04.514Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614667
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produkt
-- Column: M_ShippingPackage.M_Product_ID
-- 2023-01-17T08:48:04.520Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614673
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Transport Auftrag
-- Column: M_ShippingPackage.M_ShipperTransportation_ID
-- 2023-01-17T08:48:04.527Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614666
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Notiz
-- Column: M_ShippingPackage.Note
-- 2023-01-17T08:48:04.532Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614671
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Package Net Total
-- Column: M_ShippingPackage.PackageNetTotal
-- 2023-01-17T08:48:04.537Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614663
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Package Weight
-- Column: M_ShippingPackage.PackageWeight
-- 2023-01-17T08:48:04.542Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614664
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Verarbeitet
-- Column: M_ShippingPackage.Processed
-- 2023-01-17T08:48:04.547Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614669
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produktschlüssel
-- Column: M_ShippingPackage.ProductValue
-- 2023-01-17T08:48:04.552Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614675
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produktname
-- Column: M_ShippingPackage.ProductName
-- 2023-01-17T08:48:04.557Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614674
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Lagerort
-- Column: M_ShippingPackage.M_Locator_ID
-- 2023-01-17T08:48:04.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614676
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Stapel Nr.
-- Column: M_ShippingPackage.Batch
-- 2023-01-17T08:48:04.567Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614677
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Tatsächlich verladene Menge
-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-17T08:48:04.572Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614678
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Tatsächlich abgeladene Menge
-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-17T08:48:04.576Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614679
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Maßeinheit
-- Column: M_ShippingPackage.C_UOM_ID
-- 2023-01-17T08:48:04.582Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-17 10:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614680
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Maßeinheit
-- Column: M_ShippingPackage.C_UOM_ID
-- 2023-01-17T08:49:03.205Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614680
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Tatsächlich abgeladene Menge
-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-17T08:49:05.870Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 10:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614679
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Tatsächlich verladene Menge
-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-17T08:49:07.316Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 10:49:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614678
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Stapel Nr.
-- Column: M_ShippingPackage.Batch
-- 2023-01-17T08:49:09.130Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 10:49:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614677
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Lagerort
-- Column: M_ShippingPackage.M_Locator_ID
-- 2023-01-17T08:49:10.255Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 10:49:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614676
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produktschlüssel
-- Column: M_ShippingPackage.ProductValue
-- 2023-01-17T08:49:11.235Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 10:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614675
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produktname
-- Column: M_ShippingPackage.ProductName
-- 2023-01-17T08:49:22.455Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-17 10:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614674
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produktschlüssel
-- Column: M_ShippingPackage.ProductValue
-- 2023-01-17T08:49:26.008Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-01-17 10:49:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614675
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Produktname
-- Column: M_ShippingPackage.ProductName
-- 2023-01-17T08:49:29.934Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-01-17 10:49:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614674
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Lagerort
-- Column: M_ShippingPackage.M_Locator_ID
-- 2023-01-17T08:49:39.143Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-01-17 10:49:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614676
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Stapel Nr.
-- Column: M_ShippingPackage.Batch
-- 2023-01-17T08:49:45.241Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-01-17 10:49:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614677
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Tatsächlich verladene Menge
-- Column: M_ShippingPackage.ActualLoadQty
-- 2023-01-17T08:49:48.884Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-01-17 10:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614678
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Tatsächlich abgeladene Menge
-- Column: M_ShippingPackage.ActualDischargeQuantity
-- 2023-01-17T08:49:51.987Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2023-01-17 10:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614679
;

-- UI Element: Delivery Instruction(541657,D) -> Shipping Package(546736,D) -> main -> 10 -> default.Maßeinheit
-- Column: M_ShippingPackage.C_UOM_ID
-- 2023-01-17T08:49:55.571Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2023-01-17 10:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614680
;

