-- Name: M_Delivery_Instruction via ReleaseNo
-- 2023-01-17T09:00:14.456Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541706,TO_TIMESTAMP('2023-01-17 11:00:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Delivery_Instruction via ReleaseNo',TO_TIMESTAMP('2023-01-17 11:00:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-17T09:00:14.459Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541706 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Instruction via ReleaseNo
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.DocumentNo
-- 2023-01-17T09:01:13.337Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,540439,540439,0,541706,540030,541657,TO_TIMESTAMP('2023-01-17 11:01:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-01-17 11:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: M_ShipperTransportation via DocumentNo
-- 2023-01-17T09:01:33.984Z
UPDATE AD_Reference SET Name='M_ShipperTransportation via DocumentNo',Updated=TO_TIMESTAMP('2023-01-17 11:01:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541706
;

-- Field: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> Ausgabenummer
-- Column: M_Delivery_Planning.ReleaseNo
-- 2023-01-17T09:01:59.214Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=541706,Updated=TO_TIMESTAMP('2023-01-17 11:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708097
;



-- Reference: M_ShipperTransportation via DocumentNo
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-17T11:57:44.150Z
UPDATE AD_Ref_Table SET AD_Key=540426, WhereClause='M_ShipperTransportation.DocumentNo = @ReleaseNo@',Updated=TO_TIMESTAMP('2023-01-17 13:57:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541706
;

-- Reference: M_ShipperTransportation via DocumentNo
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-17T11:59:10.029Z
UPDATE AD_Ref_Table SET WhereClause='M_ShipperTransportation.DocumentNo = @ReleaseNo@::numeric',Updated=TO_TIMESTAMP('2023-01-17 13:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541706
;

-- Reference: M_ShipperTransportation via DocumentNo
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.DocumentNo
-- 2023-01-17T12:01:13.426Z
UPDATE AD_Ref_Table SET AD_Key=540439, WhereClause='M_ShipperTransportation.DocumentNo = @ReleaseNo@',Updated=TO_TIMESTAMP('2023-01-17 14:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541706
;

-- Name: M_Delivery_Planning source
-- 2023-01-17T12:37:21.958Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541707,TO_TIMESTAMP('2023-01-17 14:37:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Delivery_Planning source',TO_TIMESTAMP('2023-01-17 14:37:21','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-17T12:37:21.960Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541707 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Planning source
-- Table: M_Delivery_Planning
-- Key: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-01-17T12:38:02.934Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584986,0,541707,542259,541632,TO_TIMESTAMP('2023-01-17 14:38:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-01-17 14:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: M_ShipperTransportation target for M_Delivery_Planning
-- 2023-01-17T12:38:57.937Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541708,TO_TIMESTAMP('2023-01-17 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_ShipperTransportation target for M_Delivery_Planning',TO_TIMESTAMP('2023-01-17 14:38:57','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-17T12:38:57.938Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541708 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipperTransportation target for M_Delivery_Planning
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-17T12:42:15.639Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,540439,540426,0,541708,540030,541657,TO_TIMESTAMP('2023-01-17 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-01-17 14:42:15','YYYY-MM-DD HH24:MI:SS'),100,'      exists           (               select 1               from  M_ShipperTransportation di                         join M_Delivery_Planning dp on di.DocumentNo = di.ReleaseNo                                         where                       dp.M_Delivery_Planning_ID = @M_Delivery_Planning/-1@  and M_ShipperTransportation.M_ShipperTransportation_ID = di.M_ShipperTransportation_ID           )')
;

-- 2023-01-17T12:42:46.065Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541707,541708,540381,TO_TIMESTAMP('2023-01-17 14:42:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Delivery_Planning -> M_Delivery_Instruction',TO_TIMESTAMP('2023-01-17 14:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> Ausgabenummer
-- Column: M_Delivery_Planning.ReleaseNo
-- 2023-01-17T12:43:15.988Z
UPDATE AD_Field SET AD_Reference_ID=NULL, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2023-01-17 14:43:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708097
;

-- Name: M_ShipperTransportation via DocumentNo
-- 2023-01-17T12:43:24.160Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541706
;

-- 2023-01-17T12:43:24.167Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541706
;



-- Column: M_Delivery_Planning.M_Delivery_Planning_ID
-- 2023-01-17T13:26:29.782Z
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-17 15:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584986
;

-- Reference: M_ShipperTransportation target for M_Delivery_Planning
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-17T14:03:08.666Z
UPDATE AD_Ref_Table SET WhereClause='      exists           (               select 1               from  M_ShipperTransportation di                         join M_Delivery_Planning dp on di.DocumentNo = dp.ReleaseNo                                         where                       dp.M_Delivery_Planning_ID = @M_Delivery_Planning/-1@  and M_ShipperTransportation.M_ShipperTransportation_ID = di.M_ShipperTransportation_ID           )',Updated=TO_TIMESTAMP('2023-01-17 16:03:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541708
;

-- Reference: M_ShipperTransportation target for M_Delivery_Planning
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.M_ShipperTransportation_ID
-- 2023-01-17T14:10:55.347Z
UPDATE AD_Ref_Table SET WhereClause='      exists           (               select 1               from  M_ShipperTransportation di                         join M_Delivery_Planning dp on di.DocumentNo = dp.ReleaseNo                                         where                       dp.M_Delivery_Planning_ID = @M_Delivery_Planning_ID/-1@  and M_ShipperTransportation.M_ShipperTransportation_ID = di.M_ShipperTransportation_ID           )',Updated=TO_TIMESTAMP('2023-01-17 16:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541708
;


