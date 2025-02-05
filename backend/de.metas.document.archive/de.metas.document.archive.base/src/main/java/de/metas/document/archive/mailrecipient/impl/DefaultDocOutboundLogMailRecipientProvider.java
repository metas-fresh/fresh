package de.metas.document.archive.mailrecipient.impl;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientProvider;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.email.MailService;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxNotFoundException;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_DocType;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.getValueOrNull;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class DefaultDocOutboundLogMailRecipientProvider implements DocOutboundLogMailRecipientProvider
{
	private final DocOutBoundRecipientRepository docOutBoundRecipientRepository;
	private final MailService mailService;

	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);

	public DefaultDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientRepository userRepository,
			@NonNull final MailService mailService)
	{
		this.docOutBoundRecipientRepository = userRepository;
		this.mailService = mailService;
	}

	/**
	 * returns {@code true}.
	 */
	@Override
	public boolean isDefault()
	{
		return true;
	}

	/**
	 * returns {@code null}.
	 */
	@Override
	public String getTableName()
	{
		return null;
	}

	@Override
	public Optional<DocOutBoundRecipient> provideMailRecipient(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		if (request.getRecordRef() == null)
		{
			Loggables.addLog("provideMailRecipient - docOutboundLogRecord has no AD_Table_ID/Record_ID => return 'no recipient'; request={}", request);
			return Optional.empty();
		}

		final Mailbox mailbox = findMailboxOrNull(request);
		if (mailbox == null)
		{
			Loggables.addLog("provideMailRecipient - return 'no recipient'; mailbox={}", mailbox);
			return Optional.empty();
		}

		// check if the column for the user is specified
		final String userToColumnName = mailbox.getUserToColumnName();
		if (!Check.isEmpty(userToColumnName, true))
		{

			final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
			final String tableName = request.getRecordRef().getTableName();
			final boolean existsColumn = tableName != null && adTableDAO.hasColumnName(tableName, userToColumnName);

			Loggables.addLog("provideMailRecipient - Mail config has userToColumnName={}; column exists: {}; mailbox={}", userToColumnName, existsColumn, mailbox);
			if (existsColumn)
			{
				final IContextAware context = PlainContextAware.newWithThreadInheritedTrx();
				final Object referencedModel = request.getRecordRef().getModel(context);

				// load the column content
				final Integer userRepoId = getValueOrNull(referencedModel, userToColumnName);
				if (userRepoId == null || userRepoId < 0)
				{
					Loggables.addLog("provideMailRecipient - Record model has {}={} => return 'no recipient'", userToColumnName, userRepoId);
					return Optional.empty();
				}
				if (userRepoId == 0)
				{
					Loggables.addLog("provideMailRecipient - Record model has {}={} (system-user) => return 'no recipient'", userToColumnName, userRepoId);
					return Optional.empty();
				}

				final DocOutBoundRecipientId docOutBoundRecipientId = DocOutBoundRecipientId.ofRepoId(userRepoId);
				final DocOutBoundRecipient user = docOutBoundRecipientRepository.getById(docOutBoundRecipientId);
				if (Check.isEmpty(user.getEmailAddress(), true))
				{
					Loggables.addLog("provideMailRecipient - user-id {} has no/empty emailAddress => return 'no recipient'; user={}", user.getId(), user);
					return Optional.empty();
				}
				return Optional.of(user);
			}
		}
		Loggables.addLog("provideMailRecipient - return 'no recipient'; mailbox={}", mailbox);
		return Optional.empty();
	}

	private Mailbox findMailboxOrNull(@NonNull final DocOutboundLogMailRecipientRequest request)
	{
		try
		{
			return mailService.findMailbox(MailboxQuery.builder()
					.clientId(request.getClientId())
					.orgId(request.getOrgId())
					.docBaseAndSubType(extractDocBaseAndSubType(request))
					.build());
		}
		catch (final MailboxNotFoundException e)
		{
			Loggables.addLog("findMailboxOrNull. DefaultDocOutboundLogMailRecipientProvider - Unable to find a mailbox for record (system-user) => return null; exception message: {}; request={}", e.getMessage(), request);
			return null;
		}
	}

	@Nullable
	private DocBaseAndSubType extractDocBaseAndSubType(final DocOutboundLogMailRecipientRequest request)
	{
		final DocTypeId docTypeId = request.getDocTypeId();
		if (docTypeId == null)
		{
			return null;
		}

		final I_C_DocType docType = docTypesRepo.getById(docTypeId);
		return DocBaseAndSubType.of(docType.getDocBaseType(), docType.getDocSubType());
	}
}
