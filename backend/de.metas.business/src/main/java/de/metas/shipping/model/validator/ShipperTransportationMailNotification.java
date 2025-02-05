/**
 *
 */
package de.metas.shipping.model.validator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailSentStatus;
import de.metas.email.impl.EMailSendException;
import de.metas.letters.model.IEMailEditor;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.logging.LogManager;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.MMShipperTransportation;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_PackageLine;
import org.compiere.model.MClient;
import org.compiere.model.MInOut;
import org.compiere.model.MOrder;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Supposed to send an email to a shipment's receiver when the shipper document is completed.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @implNote  <a href="https://metasfresh.atlassian.net/browse/FRESH-407">task</a> - this MV doesn't really work. Whenever you actually need the feature, please let's reimplement it.
 */
@Deprecated
public class ShipperTransportationMailNotification implements ModelValidator
{
	private static final String SYS_CONFIG_SHIP = "SHIP_MAIL_NOTIFICATION";

	private int m_AD_Client_ID = -1;
	private final int p_SMTPRetriesNo = 3;

	private static final Logger log = LogManager.getLogger(ShipperTransportationMailNotification.class);

	@Override
	public String docValidate(PO po, int type)
	{
		if (po instanceof MMShipperTransportation && type == TIMING_AFTER_COMPLETE)
		{
			MMShipperTransportation ship = (MMShipperTransportation)po;
			{
				Properties ctx = Env.getCtx();
				int AD_BoilerPlate_ID = Services.get(ISysConfigBL.class).getIntValue(SYS_CONFIG_SHIP, 0, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
				final MADBoilerPlate text = MADBoilerPlate.get(ctx, AD_BoilerPlate_ID);

				for (I_M_ShippingPackage sp : getShippingPackage(ship.getM_ShipperTransportation_ID()))
				{
					// get user for sendind mail
					I_AD_User user = null;
					I_AD_User orderUser = null;
					// check first user from order
					MOrder order = (MOrder)sp.getM_InOut().getC_Order();
					if (order != null)
					{
						final BPartnerContactId contactId = BPartnerContactId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getAD_User_ID());
						user = contactId != null
								? Services.get(IBPartnerDAO.class).getContactById(contactId)
								: null;
					}
					
					if (user == null)
					{
						user = InterfaceWrapperHelper.load(sp.getM_InOut().getAD_User_ID(), I_AD_User.class);
					}
					else
					{
						orderUser = user;
					}
					//
					final BPartnerId bpartnerId = BPartnerId.ofRepoId(user.getC_BPartner_ID());
					final I_C_BPartner partnerPO = Services.get(IBPartnerDAO.class).getByIdInTrx(bpartnerId);
					if (partnerPO.isShippingNotificationEmail() && user.isDefaultContact())
					{
						String message = sendEMail(text, (MInOut)sp.getM_InOut(), orderUser);
						if (Check.isEmpty(message, true))
						{
							for (I_M_PackageLine pl : getPackageLine(sp.getM_Package_ID()))
							{
								setNotified(pl);
								InterfaceWrapperHelper.save(pl);
							}
						}
					}
				}
			}
		}
		return null;
	}

	private List<I_M_ShippingPackage> getShippingPackage(int M_ShipperTransportation_ID)
	{
		String whereClause = I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID + " = ?";
		return new Query(Env.getCtx(), I_M_ShippingPackage.Table_Name, whereClause, null)
				.setClient_ID()
				.setParameters(M_ShipperTransportation_ID)
				.list(I_M_ShippingPackage.class);
	}

	private List<I_M_PackageLine> getPackageLine(int M_Package_ID)
	{
		String whereClause = I_M_PackageLine.COLUMNNAME_M_Package_ID + " = ?";
		return new Query(Env.getCtx(), I_M_PackageLine.Table_Name, whereClause, null)
				.setClient_ID()
				.setParameters(M_Package_ID)
				.list(I_M_PackageLine.class);
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}
		//
		engine.addDocValidate(MMShipperTransportation.Table_Name, this);

	}

	@Override
	public String login(int arg0, int arg1, int arg2)
	{
		return null;
	}

	@Override
	public String modelChange(PO arg0, int arg1) throws Exception
	{
		return null;
	}

	private String sendEMail(final MADBoilerPlate text, final MInOut io, final I_AD_User orderUser)
	{
		final Properties ctx = Env.getCtx();
		MADBoilerPlate.sendEMail(new IEMailEditor()
		{
			@Override
			public Object getBaseObject()
			{
				return io;
			}

			@Override
			public int getAD_Table_ID()
			{
				return io.get_Table_ID();
			}

			@Override
			public int getRecord_ID()
			{
				return io.get_ID();
			}

			@Override
			public EMail sendEMail(I_AD_User from, String toEmail, String subject, final BoilerPlateContext attributes)
			{
				final BoilerPlateContext attributesEffective = attributes.toBuilder()
						.setSourceDocumentFromObject(io)
						.build();
				//
				String message = text.getTextSnippetParsed(attributesEffective);
				//
				if (Check.isEmpty(message, true))
				{
					return null;
				}
				//
				final StringTokenizer st = new StringTokenizer(toEmail, " ,;", false);
				EMailAddress to = EMailAddress.ofString(st.nextToken());
				MClient client = MClient.get(ctx, Env.getAD_Client_ID(ctx));
				if (orderUser != null)
				{
					to = EMailAddress.ofString(orderUser.getEMail());
				}
				EMail email = client.createEMail(
						to,
						text.getName(),
						message,
						true);
				if (email == null)
				{
					throw new AdempiereException("Cannot create email. Check log.");
				}
				while (st.hasMoreTokens())
				{
					email.addTo(EMailAddress.ofString(st.nextToken()));
				}
				send(email);
				return email;
			}
		}, false);

		return "";
	}

	private void send(EMail email)
	{
		int maxRetries = p_SMTPRetriesNo > 0 ? p_SMTPRetriesNo : 0;
		int count = 0;
		do
		{
			final EMailSentStatus emailSentStatus = email.send();
			count++;
			if (emailSentStatus.isSentOK())
			{
				return;
			}
			// Timeout => retry
			if (emailSentStatus.isSentConnectionError() && count < maxRetries)
			{
				log.warn("SMTP error: {} [ Retry {}/{} ]", emailSentStatus, count, maxRetries);
			}
			else
			{
				throw new EMailSendException(emailSentStatus);
			}
		}
		while (true);
	}

	private void setNotified(I_M_PackageLine packageLine)
	{
		InterfaceWrapperHelper.getPO(packageLine).set_ValueOfColumn("IsSentMailNotification", true);
	}

}
