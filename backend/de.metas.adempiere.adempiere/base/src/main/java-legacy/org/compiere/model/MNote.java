/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import de.metas.i18n.AdMessageId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/**
 *  Note Model
 *
 *  @author Jorg Janke
 *  @version $Id: MNote.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MNote extends X_AD_Note
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -422120961441035731L;


	/**
	 * 	Standard Constructor
	 * 	@param ctx context
	 * 	@param AD_Note_ID id
	 *	@param trxName transaction
	 */
	public MNote (Properties ctx, int AD_Note_ID, String trxName)
	{
		super (ctx, AD_Note_ID, trxName);
		if (AD_Note_ID == 0)
		{
			setProcessed (false);
			setProcessing(false);
		}
	}	//	MNote

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MNote(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MNote

	/**
	 *  New Mandatory Constructor
	 * 	@param ctx context
	 *  @param AD_Message_ID message
	 *  @param AD_User_ID targeted user
	 *	@param trxName transaction
	 */
	public MNote (Properties ctx, int AD_Message_ID, int AD_User_ID, String trxName) 
	{
		this (ctx, 0, trxName);
		setAD_Message_ID (AD_Message_ID);
		setAD_User_ID(AD_User_ID);
	}	//	MNote

	/**
	 *  New Mandatory Constructor
	 * 	@param ctx context
	 *  @param AD_MessageValue message
	 *  @param AD_User_ID targeted user
	 *	@param trxName transaction
	 */
	public MNote (Properties ctx, String AD_MessageValue, int AD_User_ID, String trxName) 
	{
		this (ctx, retrieveAdMessageRepoIdByValue(ctx, AD_MessageValue), AD_User_ID, trxName);
	}	//	MNote
	
	private static int retrieveAdMessageRepoIdByValue(final Properties ctx, final String adMessage)
	{
		final AdMessageId adMessageId = Services.get(IMsgBL.class).getIdByAdMessage(AdMessageKey.of(adMessage)).orElse(null);
		return AdMessageId.toRepoId(adMessageId);
	}

	/**
	 * 	Create Note
	 *	@param ctx context
	 *	@param AD_Message_ID message
	 *	@param AD_User_ID user
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@param TextMsg text message
	 *	@param Reference reference
	 *	@param trxName transaction
	 */
	public MNote (Properties ctx, int AD_Message_ID, int AD_User_ID,
		int AD_Table_ID, int Record_ID, String Reference, String TextMsg, String trxName)
	{
		this (ctx, AD_Message_ID, AD_User_ID, trxName);
		setRecord(AD_Table_ID, Record_ID);
		setReference(Reference);
		setTextMsg(TextMsg);
	}	//	MNote

	/**
	 *  New Constructor
	 * 	@param ctx context
	 *  @param AD_MessageValue message
	 *  @param AD_User_ID targeted user
	 *  @param AD_Client_ID client
	 * 	@param AD_Org_ID org
	 *	@param trxName transaction
	 */
	public MNote (Properties ctx, String AD_MessageValue, int AD_User_ID, 
		int AD_Client_ID, int AD_Org_ID, @Nullable String trxName)
	{
		this (ctx, retrieveAdMessageRepoIdByValue(ctx, AD_MessageValue), AD_User_ID, trxName);
		setClientOrg(AD_Client_ID, AD_Org_ID);
	}	//	MNote


	/**************************************************************************
	 * 	Set Record.
	 * 	(Ss Button and defaults to String)
	 *	@param AD_Message AD_Message
	 */
	public void setAD_Message_ID (String AD_Message)
	{
		int AD_Message_ID = DB.getSQLValue(null,
			"SELECT AD_Message_ID FROM AD_Message WHERE Value=?", AD_Message);
		if (AD_Message_ID != -1)
		{
			super.setAD_Message_ID(AD_Message_ID);
		}
		else
		{
			super.setAD_Message_ID(240); //	Error
			log.error("setAD_Message_ID - ID not found for '" + AD_Message + "'");
		}
	}	//	setRecord_ID

	/**
	 * 	Set AD_Message_ID.
	 * 	Looks up No Message Found if 0
	 *	@param AD_Message_ID id
	 */
	@Override
	public void setAD_Message_ID (int AD_Message_ID)
	{
		if (AD_Message_ID <= 0)
		{
			super.setAD_Message_ID(retrieveAdMessageRepoIdByValue(getCtx(), "NoMessageFound"));
		}
		else
		{
			super.setAD_Message_ID(AD_Message_ID);
		}
	}	//	setAD_Message_ID

	/**
	 * 	Set Client Org
	 *	@param AD_Client_ID client
	 *	@param AD_Org_ID org
	 */
	@Override
	public void setClientOrg(int AD_Client_ID, int AD_Org_ID) 
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	//	setClientOrg
	
	/**
	 * 	Set Record
	 * 	@param AD_Table_ID table
	 * 	@param Record_ID record
	 */
	public void setRecord (int AD_Table_ID, int Record_ID)
	{
		setAD_Table_ID(AD_Table_ID);
		setRecord_ID(Record_ID);
	}	//	setRecord

	public void setRecord (final TableRecordReference record)
	{
		if(record != null)
		{
			setRecord(record.getAD_Table_ID(), record.getRecord_ID());
		}
		else
		{
			setRecord(-1, -1);
		}
	}


	/**
	 * 	String Representation
	 *	@return	info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer ("MNote[")
			.append(get_ID()).append(",AD_Message_ID=").append(getAD_Message_ID())
			.append(",").append(getReference())
			.append(",Processed=").append(isProcessed())
			.append("]");
		return sb.toString();
	}	//	toString

}	//	MNote
