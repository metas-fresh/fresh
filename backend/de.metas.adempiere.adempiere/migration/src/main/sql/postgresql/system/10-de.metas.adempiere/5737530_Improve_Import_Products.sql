-- Run mode: SWING_CLIENT


-- Column: I_Product.VendorCategory
-- 2024-10-22T07:05:49.843Z
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2024-10-22 10:05:49.843','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=7833
;

-- 2024-10-22T07:05:51.424Z
INSERT INTO t_alter_column values('i_product','VendorCategory','VARCHAR(100)',null,null)
;


-- Column: I_Product.ShelfDepth
-- 2024-10-22T12:48:17.723Z
UPDATE AD_Column SET AD_Reference_ID=12, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-10-22 15:48:17.723','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=7850
;

-- 2024-10-22T12:48:19.563Z
INSERT INTO t_alter_column values('i_product','ShelfDepth','NUMERIC',null,null)
;

-- Column: I_Product.ShelfHeight
-- 2024-10-22T12:48:30.052Z
UPDATE AD_Column SET AD_Reference_ID=12, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-10-22 15:48:30.052','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=7848
;

-- 2024-10-22T12:48:32.683Z
INSERT INTO t_alter_column values('i_product','ShelfHeight','NUMERIC',null,null)
;

-- Column: I_Product.ShelfWidth
-- 2024-10-22T12:48:45.804Z
UPDATE AD_Column SET AD_Reference_ID=12, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-10-22 15:48:45.803','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=7853
;

-- 2024-10-22T12:48:49.513Z
INSERT INTO t_alter_column values('i_product','ShelfWidth','NUMERIC',null,null)
;

-- Column: I_Product.Name
-- 2024-10-22T07:11:04.504Z
UPDATE AD_Column SET FieldLength=600,Updated=TO_TIMESTAMP('2024-10-22 10:11:04.504','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=7819
;

-- 2024-10-22T07:11:05.967Z
INSERT INTO t_alter_column values('i_product','Name','VARCHAR(600)',null,null)
;


-- Column: M_Product.ShelfWidth
-- 2024-10-22T08:55:08.252Z
UPDATE AD_Column SET AD_Reference_ID=12, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-10-22 11:55:08.252','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=2307
;

-- 2024-10-22T08:55:10.005Z
INSERT INTO t_alter_column values('m_product','ShelfWidth','NUMERIC',null,null)
;

-- Column: M_Product.ShelfDepth
-- 2024-10-22T08:55:21.236Z
UPDATE AD_Column SET AD_Reference_ID=12, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-10-22 11:55:21.236','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=2309
;

-- 2024-10-22T08:55:24.276Z
INSERT INTO t_alter_column values('m_product','ShelfDepth','NUMERIC',null,null)
;


-- Column: C_BPartner_Product.ProductNo
-- 2024-10-22T10:11:07.271Z
UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2024-10-22 13:11:07.271','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=549660
;

-- 2024-10-22T10:11:09.012Z
INSERT INTO t_alter_column values('c_bpartner_product','ProductNo','VARCHAR(60)',null,null)
;
