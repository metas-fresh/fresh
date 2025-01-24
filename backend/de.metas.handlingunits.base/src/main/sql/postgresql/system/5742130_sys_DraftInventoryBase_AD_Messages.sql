/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Value: de.metas.handlingunits.inventory.process.Requieres_SingleHUInventory
-- 2024-12-19T08:05:53.429Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545485,0,TO_TIMESTAMP('2024-12-19 09:05:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Erfordert Belegart "Inventur"','I',TO_TIMESTAMP('2024-12-19 09:05:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.inventory.process.Requieres_SingleHUInventory')
;

-- 2024-12-19T08:05:53.447Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545485 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.inventory.process.Requieres_SingleHUInventory
-- 2024-12-19T08:05:58.343Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-19 09:05:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545485
;

-- Value: de.metas.handlingunits.inventory.process.Requieres_SingleHUInventory
-- 2024-12-19T08:06:10.855Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Requires document type ‘Inventory’',Updated=TO_TIMESTAMP('2024-12-19 09:06:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545485
;

-- Value: de.metas.handlingunits.inventory.process.Requieres_SingleHUInventory
-- 2024-12-19T08:06:12.960Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-19 09:06:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545485
;

-- Value: de.metas.handlingunits.inventory.process.Requires_Warehouse
-- 2024-12-19T08:08:57.385Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545486,0,TO_TIMESTAMP('2024-12-19 09:08:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Feld "Lager" muss gesetzt sein','I',TO_TIMESTAMP('2024-12-19 09:08:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.inventory.process.Requires_Warehouse')
;

-- 2024-12-19T08:08:57.387Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545486 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.inventory.process.Requires_Warehouse
-- 2024-12-19T08:09:04.649Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-19 09:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545486
;

-- Value: de.metas.handlingunits.inventory.process.Requires_Warehouse
-- 2024-12-19T08:09:08.853Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-19 09:09:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545486
;

-- Value: de.metas.handlingunits.inventory.process.Requires_Warehouse
-- 2024-12-19T08:09:29.391Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText=' "Warehouse"-Field needs to be set',Updated=TO_TIMESTAMP('2024-12-19 09:09:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545486
;

