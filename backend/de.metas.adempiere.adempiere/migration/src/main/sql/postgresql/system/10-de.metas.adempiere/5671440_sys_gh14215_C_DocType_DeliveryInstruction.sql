

-- Field: Document Type(135,D) -> Document Type(167,D) -> SO Sub Type
-- Column: C_DocType.DocSubType
-- 2023-01-12T14:06:26.797Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''SOO'' | @DocBaseType@=''POO'' | @DocBaseType@=''ARI'' | @DocBaseType@=''ARC'' | @DocBaseType@=''MOP'' | @DocBaseType@=''MMR'' | @DocBaseType@=''MMS'' | @DocBaseType@=''API'' | @DocBaseType@=''MMI'' | @DocBaseType@ = ''APC'' | @DocBaseType@=''SDD'' | @DocBaseType@ = ''CMB'' | @DocBaseType@ = ''MST''',Updated=TO_TIMESTAMP('2023-01-12 16:06:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2581
;



-- Reference: C_DocType SubTypeSO
-- Value: DI
-- ValueName: Delivery Instruction
-- 2023-01-12T14:09:01.743Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,543373,TO_TIMESTAMP('2023-01-12 16:09:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Instruction',TO_TIMESTAMP('2023-01-12 16:09:01','YYYY-MM-DD HH24:MI:SS'),100,'DI','Delivery Instruction')
;

-- 2023-01-12T14:09:01.773Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543373 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;








-- 2023-01-12T13:50:44.727Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,541085,TO_TIMESTAMP('2023-01-12 15:50:44','YYYY-MM-DD HH24:MI:SS'),100,'MST',1,'D',1000000,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Delivery Instruction','Delivery Instruction',TO_TIMESTAMP('2023-01-12 15:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T13:50:44.772Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541085 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2023-01-12T13:50:44.774Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541085 AND rol.IsManual='N')
;






-- 2023-01-12T14:10:00.060Z
UPDATE C_DocType SET DocSubType='DI',Updated=TO_TIMESTAMP('2023-01-12 16:10:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541085
;




-- 2023-01-16T16:59:05.547Z
UPDATE C_DocType SET DocNoSequence_ID=545440,Updated=TO_TIMESTAMP('2023-01-16 18:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541085
;



-- 2023-01-17T20:01:31.548Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,556190,TO_TIMESTAMP('2023-01-17 22:01:31','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,'DocumentNo/Value for Table M_ShipperTransportation, doctype Delivery Instruction',1,'Y','N','N','N','DocumentNo_DeliveryInstruction','N',1000000,TO_TIMESTAMP('2023-01-17 22:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-17T20:01:31.640Z
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2023-01-17 22:01:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=556190
;



-- 2023-01-17T20:05:51.391Z
UPDATE C_DocType SET DocNoSequence_ID=556190,Updated=TO_TIMESTAMP('2023-01-17 22:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541085
;





