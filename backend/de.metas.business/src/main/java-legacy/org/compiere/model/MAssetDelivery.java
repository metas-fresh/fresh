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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Asset Delivery Model
 *
 * @author Jorg Janke
 * @version $Id: MAssetDelivery.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAssetDelivery extends X_A_Asset_Delivery
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1731010685101745675L;

	/**
	 * Constructor
	 *
	 * @param ctx                 context
	 * @param A_Asset_Delivery_ID id or 0
	 * @param trxName             trx
	 */
	@SuppressWarnings("unused")
	public MAssetDelivery(Properties ctx, int A_Asset_Delivery_ID, String trxName)
	{
		super(ctx, A_Asset_Delivery_ID, trxName);
		if (A_Asset_Delivery_ID == 0)
		{
			setMovementDate(new Timestamp(System.currentTimeMillis()));
		}
	}    //	MAssetDelivery

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set record
	 * @param trxName transaction
	 */
	public MAssetDelivery(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}    //	MAssetDelivery

	/**
	 * String representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return "MAssetDelivery["
				+ get_ID()
				+ ",A_Asset_ID=" + getA_Asset_ID()
				+ ",MovementDate=" + getMovementDate()
				+ "]";
	}    //	toString

}    //	MAssetDelivery

