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

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.logging.LogManager;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

/**
 *  Asset Model
 *
 *  @author Jorg Janke
 *  @version $Id: MAsset.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAsset extends X_A_Asset
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7537696364072606170L;


	/**
	 * 	Get Asset From Shipment
	 *	@param ctx context
	 *	@param M_InOutLine_ID shipment line
	 *	@param trxName transaction
	 *	@return asset or null
	 */
	public static MAsset getFromShipment (Properties ctx, int M_InOutLine_ID, String trxName)
	{
		MAsset retValue = null;
		String sql = "SELECT * FROM A_Asset WHERE M_InOutLine_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, M_InOutLine_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = new MAsset (ctx, rs, trxName);
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return retValue;
	}	//	getFromShipment
	
	/**	Logger							*/
	private static Logger	s_log = LogManager.getLogger(MAsset.class);

	
	/**************************************************************************
	 * 	Asset Constructor
	 *	@param ctx context
	 *	@param A_Asset_ID asset
	 *	@param trxName transaction name 
	 */
	public MAsset (Properties ctx, int A_Asset_ID, String trxName)
	{
		super (ctx, A_Asset_ID, trxName);
		if (A_Asset_ID == 0)
		{
		//	setIsDepreciated (false);
		//	setIsFullyDepreciated (false);
		//	setValue (null);
		//	setName (null);
		//	setIsInPosession (false);
		//	setIsOwned (false);
		//	setA_Asset_Group_ID (0);
		//	setIsDisposed (false);
		//	setM_AttributeSetInstance_ID(0);
			setQty(Env.ONE);
		}
	}	//	MAsset

	/**
	 * 	Discontinued Asset Constructor - DO NOT USE (but don't delete either)
	 *	@param ctx context
	 *	@param A_Asset_ID asset
	 */
	public MAsset (Properties ctx, int A_Asset_ID)
	{
		this (ctx, A_Asset_ID, null);
	}	//	MAsset

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *	@param trxName transaction
	 */
	public MAsset (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAsset

	/**
	 * 	Shipment Constructor
	 * 	@param shipment shipment
	 *	@param shipLine shipment line
	 *	@param deliveryCount 0 or number of delivery
	 */
	public MAsset (MInOut shipment, MInOutLine shipLine, int deliveryCount)
	{
		this (shipment.getCtx(), 0, shipment.get_TrxName());
		setClientOrg(shipment);
		
		setValueNameDescription(shipment, shipLine, deliveryCount);
		//	Header
		setAssetServiceDate(shipment.getMovementDate());
		setIsOwned(false);
		setC_BPartner_ID(shipment.getC_BPartner_ID());
		setC_BPartner_Location_ID(shipment.getC_BPartner_Location_ID());
		setAD_User_ID(shipment.getAD_User_ID());
		
		//	Line
		MProduct product = shipLine.getProduct();
		setM_Product_ID(product.getM_Product_ID());

		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(product.getM_Product_Category_ID());
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final I_M_Product_Category pc = productDAO.getProductCategoryById(productCategoryId);
		setA_Asset_Group_ID(pc.getA_Asset_Group_ID());
		//	Version
		setVersionNo(product.getVersionNo());
		if (shipLine.getM_AttributeSetInstance_ID() != 0)
		{
			MAttributeSetInstance asi = new MAttributeSetInstance (getCtx(), shipLine.getM_AttributeSetInstance_ID(), get_TrxName()); 
			setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		}
		setHelp(shipLine.getDescription());
		if (deliveryCount != 0)
			setQty(shipLine.getMovementQty());
		setM_InOutLine_ID(shipLine.getM_InOutLine_ID());
		
		//	Activate
		MAssetGroup ag = MAssetGroup.get(getCtx(), getA_Asset_Group_ID());
		if (!ag.isCreateAsActive())
			setIsActive(false);
	}	//	MAsset
	
	
	/**	Product Info					*/
	private MProduct		m_product = null;

	/**
	 * 	Set Value Name Description
	 *	@param shipment shipment
	 *	@param line line
	 *	@param deliveryCount
	 */
	public void setValueNameDescription(MInOut shipment, MInOutLine line, 
		int deliveryCount)
	{
		MProduct product = line.getProduct(); 
		I_C_BPartner partner = Services.get(IBPartnerDAO.class).getById(shipment.getC_BPartner_ID());
		setValueNameDescription(shipment, deliveryCount, product, partner);
	}	//	setValueNameDescription
	
	/**
	 * 	Set Value, Name, Description
	 *	@param shipment shipment
	 *	@param deliveryCount count
	 *	@param product product
	 *	@param partner partner
	 */
	public void setValueNameDescription (MInOut shipment,  
		int deliveryCount, MProduct product, I_C_BPartner partner)
	{
		String documentNo = "_" + shipment.getDocumentNo();
		if (deliveryCount > 1)
			documentNo += "_" + deliveryCount;
		//	Value
		String value = partner.getValue() + "_" + product.getValue();
		if (value.length() > 40-documentNo.length())
			value = value.substring(0,40-documentNo.length()) + documentNo;
		setValue(value);
		
		//	Name		MProduct.afterSave
		String name = partner.getName() + " - " + product.getName();
		if (name.length() > 60)
			name = name.substring(0,60);
		setName(name);
		//	Description
		String description = product.getDescription();
		setDescription(description);
	}	//	setValueNameDescription
	
	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription

	/**
	 * 	Get Qty
	 *	@return 1 or Qty
	 */
	@Override
	public BigDecimal getQty ()
	{
		BigDecimal qty = super.getQty();
		if (qty == null || qty.equals(Env.ZERO))
			setQty(Env.ONE);
		return super.getQty();
	}	//	getQty
	
	/**
	 * 	String representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MAsset[")
			.append (get_ID ())
			.append("-").append(getValue())
			.append ("]");
		return sb.toString ();
	}	//	toString

	
	/**************************************************************************
	 * 	Get Deliveries
	 * 	@return deliveries
	 */
	public MAssetDelivery[] getDeliveries()
	{
		ArrayList<MAssetDelivery> list = new ArrayList<>();

		String sql = "SELECT * FROM A_Asset_Delivery WHERE A_Asset_ID=? ORDER BY Created DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getA_Asset_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MAssetDelivery(getCtx(), rs, get_TrxName()));
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		MAssetDelivery[] retValue = new MAssetDelivery[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getDeliveries

	/**
	 * 	Get Delivery count
	 * 	@return delivery count
	 */
	public int getDeliveryCount()
	{
		String sql = "SELECT COUNT(*) FROM A_Asset_Delivery WHERE A_Asset_ID=?";
		return DB.getSQLValue(get_TrxName(),
			sql, getA_Asset_ID());
	}	//	getDeliveries

	/**
	 * 	Get Product R_MailText_ID
	 *	@return R_MailText_ID
	 */
	public int getProductR_MailText_ID()
	{
		return getProduct().getR_MailText_ID();
	}	//	getProductR_MailText_ID

	/**
	 * 	Get Product Info
	 * 	@return product
	 */
	private MProduct getProduct()
	{
		if (m_product == null)
			m_product = MProduct.get (getCtx(), getM_Product_ID()); 
		return m_product;
	}	//	getProductInfo
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		getQty();		//	set to 1
		if (getA_Parent_Asset_ID() < 1 ) 
		{				
			setA_Parent_Asset_ID(getA_Asset_ID());			
		}	
		return true;
	}	//	beforeSave
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return saved
	 */
	@Override
	protected boolean afterSave (boolean newRecord,boolean success)
	{
		log.info("afterSave");

		int		p_A_Asset_ID = 0;
		p_A_Asset_ID = getA_Asset_ID();

		String sql = "SELECT COUNT(*) FROM A_Depreciation_Workfile WHERE A_Asset_ID=?";
		PreparedStatement pstmt = null;	


		if (DB.getSQLValue(get_TrxName(),sql, p_A_Asset_ID)== 0) 
		{		    	
			sql ="SELECT * FROM A_Asset_Group_Acct WHERE A_Asset_Group_ID = ? AND IsActive='Y'";	
			pstmt = null;
			pstmt = DB.prepareStatement(sql,get_TrxName());				
			try {
				pstmt.setInt(1, getA_Asset_Group_ID());		
				ResultSet rs = pstmt.executeQuery();
				int uselifemonths = 0;
				int uselifeyears = 0;	
				boolean isdepreciate = false;


				MAssetChange change = new MAssetChange (getCtx(), 0, get_TrxName());	
				X_A_Asset asset = new X_A_Asset (getCtx(), p_A_Asset_ID, get_TrxName());

				if (getA_Parent_Asset_ID() < 1 ) 
				{				
					asset.setA_Parent_Asset_ID(getA_Asset_ID());
					asset.save();
				}	


				while (rs.next()){
					MAssetGroupAcct assetgrpacct = new MAssetGroupAcct (getCtx(), rs, get_TrxName());
					MAssetAcct assetacct = new MAssetAcct (getCtx(), 0, get_TrxName());			
					isdepreciate = assetgrpacct.isProcessing();
					if (isDepreciated()== true | isdepreciate == true)
					{			
						assetacct.setPostingType(assetgrpacct.getPostingType());
						assetacct.setA_Split_Percent(assetgrpacct.getA_Split_Percent());
						assetacct.setA_Depreciation_Conv_ID(assetgrpacct.getConventionType());
						assetacct.setA_Salvage_Value(new BigDecimal(0.0));
						assetacct.setA_Asset_ID(p_A_Asset_ID);								
						assetacct.setA_Depreciation_ID(assetgrpacct.getDepreciationType());
						assetacct.setA_Asset_Spread_ID(assetgrpacct.getA_Asset_Spread_Type());
						assetacct.setA_Period_Start(1);


						if (getUseLifeMonths() == 0 & getUseLifeYears() == 0){
							assetacct.setA_Period_End(assetgrpacct.getUseLifeMonths());
							asset.setUseLifeYears(assetgrpacct.getUseLifeYears());
							asset.setUseLifeMonths(assetgrpacct.getUseLifeMonths());
							asset.setIsDepreciated(true);
							asset.setIsOwned(true);
							asset.save();
							uselifemonths = assetgrpacct.getUseLifeMonths();
							uselifeyears = assetgrpacct.getUseLifeYears();

						}
						else if(getUseLifeMonths() == 0){
							assetacct.setA_Period_End(getUseLifeYears()*12);
							asset.setUseLifeMonths(getUseLifeYears()*12);
							asset.setIsDepreciated(true);
							asset.setIsOwned(true);
							asset.save();
							uselifemonths = getUseLifeYears()*12;
							uselifeyears = getUseLifeYears();						
						}
						else{
							assetacct.setA_Period_End(getUseLifeMonths());
							uselifemonths = getUseLifeMonths();
							uselifeyears = getUseLifeYears();}

						assetacct.setA_Depreciation_Method_ID(assetgrpacct.getA_Depreciation_Calc_Type());
						assetacct.setA_Asset_Acct(assetgrpacct.getA_Asset_Acct());
						assetacct.setC_AcctSchema_ID(assetgrpacct.getC_AcctSchema_ID());
						assetacct.setA_Accumdepreciation_Acct(assetgrpacct.getA_Accumdepreciation_Acct());
						assetacct.setA_Depreciation_Acct(assetgrpacct.getA_Depreciation_Acct());
						assetacct.setA_Disposal_Revenue(assetgrpacct.getA_Disposal_Revenue());
						assetacct.setA_Disposal_Loss(assetgrpacct.getA_Disposal_Loss());
						assetacct.setA_Reval_Accumdep_Offset_Cur(assetgrpacct.getA_Reval_Accumdep_Offset_Cur());
						assetacct.setA_Reval_Accumdep_Offset_Prior(assetgrpacct.getA_Reval_Accumdep_Offset_Prior());
						if (assetgrpacct.getA_Reval_Cal_Method() == null)
							assetacct.setA_Reval_Cal_Method("DFT");
						else
							assetacct.setA_Reval_Cal_Method(assetgrpacct.getA_Reval_Cal_Method());					
						assetacct.setA_Reval_Cost_Offset(assetgrpacct.getA_Reval_Cost_Offset());
						assetacct.setA_Reval_Cost_Offset_Prior(assetgrpacct.getA_Reval_Cost_Offset_Prior());
						assetacct.setA_Reval_Depexp_Offset(assetgrpacct.getA_Reval_Depexp_Offset());
						assetacct.setA_Depreciation_Manual_Amount(assetgrpacct.getA_Depreciation_Manual_Amount());
						assetacct.setA_Depreciation_Manual_Period(assetgrpacct.getA_Depreciation_Manual_Period());
						assetacct.setA_Depreciation_Table_Header_ID(assetgrpacct.getA_Depreciation_Table_Header_ID());
						assetacct.setA_Depreciation_Variable_Perc(assetgrpacct.getA_Depreciation_Variable_Perc());
						assetacct.setProcessing(false);
						assetacct.save();

						change.setPostingType(assetacct.getPostingType());
						change.setA_Split_Percent(assetacct.getA_Split_Percent());
						change.setConventionType(assetacct.getA_Depreciation_Conv_ID());
						change.setA_Asset_ID(p_A_Asset_ID);								
						change.setDepreciationType(assetacct.getA_Depreciation_ID());
						change.setA_Asset_Spread_Type(assetacct.getA_Asset_Spread_ID());
						change.setA_Period_Start(assetacct.getA_Period_Start());
						change.setA_Period_End(assetacct.getA_Period_End());
						change.setIsInPosession(isOwned());
						change.setIsDisposed(isDisposed());
						change.setIsDepreciated(isDepreciated());
						change.setIsFullyDepreciated(isFullyDepreciated());					
						change.setA_Depreciation_Calc_Type(assetacct.getA_Depreciation_Method_ID());
						change.setA_Asset_Acct(assetacct.getA_Asset_Acct());
						change.setC_AcctSchema_ID(assetacct.getC_AcctSchema_ID());
						change.setA_Accumdepreciation_Acct(assetacct.getA_Accumdepreciation_Acct());
						change.setA_Depreciation_Acct(assetacct.getA_Depreciation_Acct());
						change.setA_Disposal_Revenue(assetacct.getA_Disposal_Revenue());
						change.setA_Disposal_Loss(assetacct.getA_Disposal_Loss());
						change.setA_Reval_Accumdep_Offset_Cur(assetacct.getA_Reval_Accumdep_Offset_Cur());
						change.setA_Reval_Accumdep_Offset_Prior(assetacct.getA_Reval_Accumdep_Offset_Prior());
						if (assetacct.getA_Reval_Cal_Method() == null)
							change.setA_Reval_Cal_Method("DFT");
						else
							change.setA_Reval_Cal_Method(assetacct.getA_Reval_Cal_Method());
						change.setA_Reval_Cost_Offset(assetacct.getA_Reval_Cost_Offset());
						change.setA_Reval_Cost_Offset_Prior(assetacct.getA_Reval_Cost_Offset_Prior());
						change.setA_Reval_Depexp_Offset(assetacct.getA_Reval_Depexp_Offset());
						change.setA_Depreciation_Manual_Amount(assetacct.getA_Depreciation_Manual_Amount());
						change.setA_Depreciation_Manual_Period(assetacct.getA_Depreciation_Manual_Period());
						change.setA_Depreciation_Table_Header_ID(assetacct.getA_Depreciation_Table_Header_ID());
						change.setA_Depreciation_Variable_Perc(assetacct.getA_Depreciation_Variable_Perc());

					}

					String sql2 = "SELECT COUNT(*) FROM A_Depreciation_Workfile WHERE A_Asset_ID=? AND PostingType = ?";
					if (DB.getSQLValue(get_TrxName(), sql2, asset.getA_Asset_ID(),assetgrpacct.getPostingType())== 0) 
					{

						if (isDepreciated()== true | isdepreciate == true)						
						{
							X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), 0, get_TrxName());
							assetwk.setA_Asset_ID(p_A_Asset_ID);		
							assetwk.setA_Life_Period(uselifemonths);
							assetwk.setA_Asset_Life_Years(uselifeyears);
							assetwk.setIsDepreciated(isDepreciated());
							assetwk.setPostingType(assetgrpacct.getPostingType());
							assetwk.setA_Accumulated_Depr(new BigDecimal(0.0));
							assetwk.setA_QTY_Current(new BigDecimal(0.0));
							assetwk.setA_Asset_Cost(new BigDecimal(0.0));
							assetwk.setA_Period_Posted(0);
							assetwk.save();
						}
					}
				}

				change.setA_Asset_ID(p_A_Asset_ID);
				change.setA_Parent_Asset_ID(asset.getA_Parent_Asset_ID());
				change.setChangeType("CRT");
				MRefList RefList = new MRefList (getCtx(), 0, get_TrxName());	
				change.setTextDetails(RefList.getListDescription (getCtx(),"A_Update_Type" , "CRT"));		    
				change.setIsInPosession(isOwned());
				change.setIsDisposed(isDisposed());
				change.setIsDepreciated(isDepreciated());
				change.setIsFullyDepreciated(isFullyDepreciated());
				change.setVersionNo(getVersionNo());
				change.setUseLifeMonths(getUseLifeMonths());
				change.setUseLifeYears(getUseLifeYears());
				change.setLifeUseUnits(getLifeUseUnits());
				change.setAssetDisposalDate(getAssetDisposalDate());
				change.setAssetServiceDate(getAssetServiceDate());
				change.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
				change.setC_BPartner_ID(getC_BPartner_ID());		    
				change.setA_QTY_Current(getA_QTY_Current());
				change.setA_QTY_Original(getA_QTY_Original());
				change.setA_Asset_CreateDate(getA_Asset_CreateDate());
				change.setAD_User_ID(getAD_User_ID());
				change.setC_Location_ID(getC_Location_ID());
				change.save();

				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.info("getAssets"+ e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e)
				{}
				pstmt = null;
			}


		}
		else 
		{
			int uselifemonths = 0;
			int uselifeyears = 0;		
			sql ="SELECT * FROM A_Asset_Acct WHERE A_Asset_ID = ? AND IsActive='Y'";		
			pstmt = null;
			pstmt = DB.prepareStatement(sql,get_TrxName());


			try {

				pstmt.setInt(1, getA_Asset_ID());		
				ResultSet rs = pstmt.executeQuery();	
				while (rs.next()){			
					MAssetAcct assetacct = new MAssetAcct (getCtx(),rs, get_TrxName());			
					assetacct.setProcessing(false);
					assetacct.setA_Asset_ID(p_A_Asset_ID);						
					assetacct.setA_Period_Start(1);			
					if(getUseLifeMonths() == 0){
						assetacct.setA_Period_End(getUseLifeYears()*12);
						setUseLifeMonths(getUseLifeYears()*12);
						uselifemonths = getUseLifeYears()*12;
						uselifeyears = getUseLifeYears();
					}
					else{
						assetacct.setA_Period_End(getUseLifeMonths());
						uselifemonths = getUseLifeMonths();
						uselifeyears = getUseLifeYears();
					}			
					assetacct.save();
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.info("getAssets"+ e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e)
				{}
				pstmt = null;
			}
			sql ="SELECT * FROM A_Depreciation_Workfile WHERE A_Asset_ID=? AND IsActive='Y'";
			pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql,get_TrxName());
				pstmt.setInt(1, p_A_Asset_ID);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()){

					X_A_Depreciation_Workfile assetwk = new X_A_Depreciation_Workfile (getCtx(), rs, get_TrxName());

					assetwk.setA_Asset_ID(p_A_Asset_ID);
					assetwk.setA_Life_Period(uselifemonths);
					assetwk.setA_Asset_Life_Years(uselifeyears);
					assetwk.setIsDepreciated(isDepreciated());
					//assetwk.setA_QTY_Current(getA_QTY_Current());		   
					assetwk.save();		

					if (isProcessing()== true){
						MAssetChange change = new MAssetChange (getCtx(), 0, get_TrxName());		    
						change.setA_Asset_ID(p_A_Asset_ID);
						change.setChangeType("UPD");
						MRefList RefList = new MRefList (getCtx(), 0, get_TrxName());	
						change.setTextDetails(RefList.getListDescription (getCtx(),"A_Update_Type" , "UPD"));
						change.setVersionNo(getVersionNo());
						change.setA_Parent_Asset_ID(getA_Parent_Asset_ID());
						change.setUseLifeMonths(getUseLifeMonths());
						change.setUseLifeYears(getUseLifeYears());
						change.setLifeUseUnits(getLifeUseUnits());
						change.setAssetDisposalDate(getAssetDisposalDate());
						change.setAssetServiceDate(getAssetServiceDate());
						change.setIsInPosession(isOwned());		    
						change.setA_Reval_Cal_Method("DFT");			
						change.setIsDisposed(isDisposed());
						change.setIsDepreciated(isDepreciated());
						change.setIsFullyDepreciated(isFullyDepreciated());
						change.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
						change.setC_BPartner_ID(getC_BPartner_ID());    
						change.setPostingType("A");
						change.setA_QTY_Current(getA_QTY_Current());
						change.setA_QTY_Original(getA_QTY_Original());
						change.setA_Asset_CreateDate(getA_Asset_CreateDate());		    
						change.setAD_User_ID(getAD_User_ID());
						change.setC_Location_ID(getC_Location_ID());
						change.save();		    
					}
					else
					{
						X_A_Asset asset = new X_A_Asset (getCtx(), p_A_Asset_ID, get_TrxName());
						asset.setProcessing(true);
						asset.save();
					}	
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.info("getAssets"+ e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e)
				{}
				pstmt = null;
			}

		}

		return true;

	}	//	afterSave
}	//	MAsset
