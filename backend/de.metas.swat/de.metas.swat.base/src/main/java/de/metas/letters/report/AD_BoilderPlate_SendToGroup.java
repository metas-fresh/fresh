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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.callcenter.model.MRGroupProspect;
import de.metas.email.EMail;
import de.metas.email.EMailRequest;
import de.metas.email.MailService;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.letters.model.IEMailEditor;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.RunOutOfTrx;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.MGroup;
import org.compiere.model.Query;
import org.compiere.model.X_R_Group;

import java.util.List;

import static de.metas.letters.report.AD_BoilderPlate_SendToUsers.toEMailAddresses;

/**
 * Send BoilerPlate to Bundle (R_Group)
 * @author teo_sarca
 *
 */
public class AD_BoilderPlate_SendToGroup extends JavaProcess
{
	private final MailService mailService = SpringContextHolder.instance.getBean(MailService.class);

	public static final String CCM_NotificationType_EMail = "E";
	public static final String CCM_NotificationType_Letter = "L";

	private int p_R_Group_ID = -1;
	private String p_CCM_NotificationType = CCM_NotificationType_EMail;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{

			}
			else if (X_R_Group.COLUMNNAME_R_Group_ID.equals(name))
			{
				p_R_Group_ID = para.getParameterAsInt();
			}
			else if ("CCM_NotificationType".equals(name))
			{
				p_CCM_NotificationType = para.getParameter().toString();
			}
		}
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		MGroup group = MGroup.get(getCtx(), p_R_Group_ID);
		if (group == null)
		{
			throw new FillMandatoryException("R_Group_ID");
		}
		int AD_BoilerPlate_ID = group.get_ValueAsInt(MADBoilerPlate.COLUMNNAME_AD_BoilerPlate_ID);
		if (AD_BoilerPlate_ID <= 0)
		{
			throw new FillMandatoryException(MADBoilerPlate.COLUMNNAME_AD_BoilerPlate_ID);
		}
		final MADBoilerPlate text = MADBoilerPlate.get(getCtx(), AD_BoilerPlate_ID);

		int count_ok = 0;
		int count_all = 0;
		for (MRGroupProspect prospect : getProspects(p_R_Group_ID))
		{
			if (notify(text, prospect))
			{
				count_ok++;
			}
			count_all++;
		}
		return "@Sent@ #" + count_ok + "/" + count_all;
	}

	private boolean notify(final MADBoilerPlate text, final MRGroupProspect prospect)
	{
		boolean ok = true;
		try
		{
			if (CCM_NotificationType_EMail.equals(p_CCM_NotificationType))
			{
				notifyEMail(text, prospect);
			}
			else if (CCM_NotificationType_Letter.equals(p_CCM_NotificationType))
			{
				notifyLetter(text, prospect);
			}
			else
			{
				throw new AdempiereException("@NotSupported@ @CCM_NotificationType@ - " + p_CCM_NotificationType);
			}
		}
		catch (Exception e)
		{
			addLog(prospect.toString() + ": Error: " + e.getLocalizedMessage());
			ok = false;
			if (LogManager.isLevelFine())
			{
				e.printStackTrace();
			}
		}
		return ok;
	}

	private void notifyEMail(final MADBoilerPlate text, final MRGroupProspect prospect)
	{
		MADBoilerPlate.sendEMail(new IEMailEditor()
		{
			@Override
			public Object getBaseObject()
			{
				return prospect;
			}

			@Override
			public int getAD_Table_ID()
			{
				return X_R_Group.Table_ID;
			}

			@Override
			public int getRecord_ID()
			{
				return prospect.getR_Group_ID();
			}

			@Override
			public EMail sendEMail(I_AD_User from, String toEmail, String subject, final BoilerPlateContext attributes)
			{
				return mailService.sendEMail(EMailRequest.builder()
						.mailboxQuery(MailboxQuery.builder()
								.clientId(getClientId())
								.orgId(getOrgId())
								.adProcessId(getProcessInfo().getAdProcessId())
								.fromUserId(UserId.ofRepoId(from.getAD_User_ID()))
								.build())
						.toList(toEMailAddresses(toEmail))
						.subject(text.getSubject())
						.message(text.getTextSnippetParsed(attributes))
						.html(true)
						.build());
			}
		});
	}

	private void notifyLetter(MADBoilerPlate text, MRGroupProspect prospect)
	{
		throw new UnsupportedOperationException();
	}

	private List<MRGroupProspect> getProspects(int R_Group_ID)
	{
		final String whereClause = MRGroupProspect.COLUMNNAME_R_Group_ID + "=?";
		return new Query(getCtx(), MRGroupProspect.Table_Name, whereClause, get_TrxName())
				.setParameters(R_Group_ID)
				.list(MRGroupProspect.class);
	}
}
