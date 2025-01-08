/**
 * 
 */

package de.metas.letters.report;

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

import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailSentStatus;
import de.metas.email.impl.EMailSendException;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.i18n.AdMessageId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.letters.model.IEMailEditor;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.MClient;
import org.compiere.model.MNote;
import org.compiere.model.Query;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Send BoilerPlate to selected contacts
 * 
 * @author teo_sarca
 *
 */
public class AD_BoilderPlate_SendToUsers extends JavaProcess
{
	private static final AdMessageKey AD_Message_UserNotifyError = AdMessageKey.of("de.metas.letters.UserNotifyError");

	/** From User (sender) */
	private UserId p_AD_User_ID;
	private int p_AD_BoilerPlate_ID = -1;
	private String p_WhereClause = null;
	private int p_SMTPRetriesNo = 3;

	private UserEMailConfig fromUserEmailConfig = null;

	private int m_count_notes = 0;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{

			}
			else if ("AD_User_ID".equals(name))
			{
				p_AD_User_ID = para.getParameterAsRepoId(UserId::ofRepoIdOrNull);
			}
			else if (MADBoilerPlate.COLUMNNAME_AD_BoilerPlate_ID.equals(name))
			{
				p_AD_BoilerPlate_ID = para.getParameterAsInt();
			}
			else if ("WhereClause".equals(name))
			{
				p_WhereClause = para.getParameter().toString();
			}
			else if ("Retry".equals(name))
			{
				p_SMTPRetriesNo = para.getParameterAsInt();
			}
		}
	}

	private UserEMailConfig getFromUserEMailConfig()
	{
		UserEMailConfig fromUserEmailConfig = this.fromUserEmailConfig;
		if (fromUserEmailConfig == null)
		{
			if (p_AD_User_ID == null)
			{
				throw new FillMandatoryException("AD_User_ID");
			}

			fromUserEmailConfig = this.fromUserEmailConfig = Services.get(IUserBL.class).getEmailConfigById(p_AD_User_ID);
		}

		return fromUserEmailConfig;
	}

	@Override
	protected String doIt()
	{
		//
		if (p_AD_BoilerPlate_ID <= 0)
		{ // metas-ts: handle missing or wrong AD_BoilerPlate_ID
			throw new FillMandatoryException(MADBoilerPlate.COLUMNNAME_AD_BoilerPlate_ID);
		}
		final MADBoilerPlate text = MADBoilerPlate.get(getCtx(), p_AD_BoilerPlate_ID);
		if (text == null)
		{
			final String msg = "Missing record; AD_BoilerPlate_ID=" + p_AD_BoilerPlate_ID;
			addLog(msg);
			throw new AdempiereException(msg);
		}
		int count_ok = 0;
		int count_all = 0;
		for (I_AD_User user : getUsers())
		{
			if (notify(text, user))
			{
				count_ok++;
			}
			count_all++;
		}
		return "@Sent@ #" + count_ok + "/" + count_all
				+ "<br>@AD_Note_ID@ #" + m_count_notes;
	}

	private List<I_AD_User> getUsers()
	{
		return new Query(getCtx(), I_AD_User.Table_Name, p_WhereClause, get_TrxName())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list(I_AD_User.class);
	}

	private boolean notify(final MADBoilerPlate text, final I_AD_User user)
	{
		boolean ok = true;
		try
		{
			notifyEMail(text, user);
		}
		catch (Exception e)
		{
			createNote(text, user, e);
			ok = false;
			if (LogManager.isLevelFine())
			{
				e.printStackTrace();
			}
		}
		return ok;
	}

	private void notifyEMail(final MADBoilerPlate text, final I_AD_User user)
	{
		MADBoilerPlate.sendEMail(new IEMailEditor()
		{
			@Override
			public Object getBaseObject()
			{
				return user;
			}

			@Override
			public int getAD_Table_ID()
			{
				return InterfaceWrapperHelper.getModelTableId(user);
			}

			@Override
			public int getRecord_ID()
			{
				return user.getAD_User_ID();
			}

			@Override
			public EMail sendEMail(I_AD_User from, String toEmail, String subject, final BoilerPlateContext attributes)
			{
				String message = text.getTextSnippetParsed(attributes);
				//
				final StringTokenizer st = new StringTokenizer(toEmail, " ,;", false);
				final EMailAddress to = EMailAddress.ofString(st.nextToken());
				final MClient client = MClient.get(getCtx(), getAD_Client_ID());
				final EMail email = client.createEMail(
						getFromUserEMailConfig(),
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
		});
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
			if (emailSentStatus.isSentConnectionError() && maxRetries > 0 && count < maxRetries)
			{
				log.warn("SMTP error: " + emailSentStatus + " [ Retry " + count + " ]");
			}
			else
			{
				throw new EMailSendException(emailSentStatus);
			}
		}
		while (true);
	}

	private void createNote(MADBoilerPlate text, I_AD_User user, Exception e)
	{
		final AdMessageId adMessageId = Services.get(IMsgBL.class).getIdByAdMessage(AD_Message_UserNotifyError)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @AD_Message_ID@ " + AD_Message_UserNotifyError));

		//
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final String reference = msgBL.parseTranslation(getCtx(), "@AD_BoilerPlate_ID@: " + text.get_Translation(MADBoilerPlate.COLUMNNAME_Name))
				+ ", " + msgBL.parseTranslation(getCtx(), "@AD_User_ID@: " + user.getName())
		// +", "+Msg.parseTranslation(getCtx(), "@AD_PInstance_ID@: "+getAD_PInstance_ID())
		;
		final MNote note = new MNote(getCtx(),
				adMessageId.getRepoId(),
				getFromUserEMailConfig().getUserId().getRepoId(),
				InterfaceWrapperHelper.getModelTableId(user), user.getAD_User_ID(),
				reference,
				e.getLocalizedMessage(),
				get_TrxName());
		note.setAD_Org_ID(0);
		note.saveEx();
		m_count_notes++;
	}
}
