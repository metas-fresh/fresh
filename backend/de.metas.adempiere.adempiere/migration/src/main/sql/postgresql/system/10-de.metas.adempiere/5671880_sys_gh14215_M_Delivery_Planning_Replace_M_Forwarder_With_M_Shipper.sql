-- Column: M_Delivery_Planning.M_Shipper_ID
-- 2023-01-16T09:58:03.711Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585490,455,0,30,542259,'M_Shipper_ID',TO_TIMESTAMP('2023-01-16 11:58:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Method or manner of product delivery','D',0,10,'The Shipper indicates the method of delivering product','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Shipper',0,0,TO_TIMESTAMP('2023-01-16 11:58:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T09:58:03.744Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585490 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T09:58:03.808Z
/* DDL */  select update_Column_Translation_From_AD_Element(455) 
;

-- 2023-01-16T09:58:17.496Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN M_Shipper_ID NUMERIC(10)')
;

-- 2023-01-16T09:58:17.538Z
ALTER TABLE M_Delivery_Planning ADD CONSTRAINT MShipper_MDeliveryPlanning FOREIGN KEY (M_Shipper_ID) REFERENCES public.M_Shipper DEFERRABLE INITIALLY DEFERRED
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Shipper
-- Column: M_Delivery_Planning.M_Shipper_ID
-- 2023-01-16T10:01:08.954Z
UPDATE AD_Field SET AD_Column_ID=585490, Description='Method or manner of product delivery', Help='The Shipper indicates the method of delivering product', Name='Shipper',Updated=TO_TIMESTAMP('2023-01-16 12:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708105
;

-- 2023-01-16T10:01:08.987Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455) 
;

-- 2023-01-16T10:01:09.032Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708105
;

-- 2023-01-16T10:01:09.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708105)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Forwarder
-- Column: M_Delivery_Planning.M_Shipper_ID
-- 2023-01-16T10:01:34.502Z
UPDATE AD_Field SET AD_Name_ID=581762, Description=NULL, Help=NULL, Name='Forwarder',Updated=TO_TIMESTAMP('2023-01-16 12:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708105
;

-- 2023-01-16T10:01:34.534Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581762) 
;

-- 2023-01-16T10:01:34.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708105
;

-- 2023-01-16T10:01:34.600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708105)
;

-- Column: M_Delivery_Planning.M_Forwarder_ID
-- 2023-01-16T10:04:54.589Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585035
;

-- 2023-01-16T10:04:54.780Z
DELETE FROM AD_Column WHERE AD_Column_ID=585035
;


SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning DROP COLUMN M_Forwarder_ID')
;