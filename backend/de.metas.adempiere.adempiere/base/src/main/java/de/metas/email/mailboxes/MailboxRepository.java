package de.metas.email.mailboxes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.document.DocBaseAndSubType;
import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.I_AD_MailConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class MailboxRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, MailboxesMap> mailboxesCache = CCache.<Integer, MailboxesMap>builder()
			.tableName(I_AD_MailBox.Table_Name)
			.initialCapacity(1)
			.build();
	private final CCache<Integer, MailboxRoutingTable> routingsCache = CCache.<Integer, MailboxRoutingTable>builder()
			.tableName(I_AD_MailConfig.Table_Name)
			.initialCapacity(1)
			.build();

	@NonNull
	public Optional<Mailbox> findMailBox(@NonNull final MailboxQuery query)
	{
		return getRoutingTable()
				.findBestMatching(query)
				.map(this::getMailbox);
	}

	private Mailbox getMailbox(final MailboxRouting mailRouting)
	{
		return getById(mailRouting.getMailboxId())
				.withUserToColumnName(mailRouting.getUserToColumnName());
	}

	public Mailbox getById(final MailboxId mailboxId)
	{
		return getMap().getById(mailboxId);
	}

	private MailboxesMap getMap() {return mailboxesCache.getOrLoad(0, this::retrieveMap);}

	private MailboxesMap retrieveMap()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_MailBox.class)
				// .addOnlyActiveRecordsFilter() // all
				.create()
				.stream()
				.map(MailboxRepository::fromRecord)
				.collect(GuavaCollectors.collectUsingListAccumulator(MailboxesMap::new));
	}

	private static MailboxEntry fromRecord(final I_AD_MailBox record)
	{
		return MailboxEntry.builder()
				.id(MailboxId.ofRepoId(record.getAD_MailBox_ID()))
				.active(record.isActive())
				.mailbox(extractMailbox(record))
				.build();
	}

	private static Mailbox extractMailbox(final I_AD_MailBox record)
	{
		final MailboxType type = MailboxType.ofCode(record.getType());
		final Mailbox.MailboxBuilder builder = Mailbox.builder()
				.type(type)
				.email(EMailAddress.ofString(record.getEMail()))
				.userToColumnName(null);

		switch (type)
		{
			case SMTP:
			{
				builder.smtpConfig(
						SMTPConfig.builder()
								.smtpHost(StringUtils.trimBlankToOptional(record.getSMTPHost()).orElseThrow(() -> new AdempiereException("SMTPHost not set")))
								.smtpPort(record.getSMTPPort())
								.smtpAuthorization(record.isSmtpAuthorization())
								.username(record.getUserName())
								.password(record.getPassword())
								.startTLS(record.isStartTLS())
								.build()
				);
				break;
			}
			case MICROSOFT_GRAPH:
			{
				builder.microsoftGraphConfig(
						MicrosoftGraphConfig.builder()
								.clientId(StringUtils.trimBlankToOptional(record.getMSGRAPH_ClientId()).orElseThrow(() -> new AdempiereException("MSGRAPH_ClientId not set")))
								.tenantId(StringUtils.trimBlankToOptional(record.getMSGRAPH_TenantId()).orElseThrow(() -> new AdempiereException("MSGRAPH_TenantId not set")))
								.clientSecret(StringUtils.trimBlankToOptional(record.getMSGRAPH_ClientSecret()).orElseThrow(() -> new AdempiereException("MSGRAPH_ClientSecret not set")))
								.build()
				);
				break;
			}
			default:
			{
				throw new AdempiereException("Unknown type: " + type);
			}
		}

		return builder.build();
	}

	private MailboxRoutingTable getRoutingTable()
	{
		return routingsCache.getOrLoad(0, this::retrieveRoutingTable);
	}

	private MailboxRoutingTable retrieveRoutingTable()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_MailConfig.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(MailboxRepository::fromRecord)
				.collect(GuavaCollectors.collectUsingListAccumulator(MailboxRoutingTable::ofList));
	}

	private static MailboxRouting fromRecord(final I_AD_MailConfig record)
	{
		return MailboxRouting.builder()
				.mailboxId(MailboxId.ofRepoId(record.getAD_MailBox_ID()))
				.userToColumnName(StringUtils.trimBlankToNull(record.getColumnUserTo()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoIdOrAny(record.getAD_Org_ID()))
				.docBaseAndSubType(DocBaseAndSubType.ofNullable(record.getDocBaseType(), record.getDocSubType()))
				.adProcessId(AdProcessId.ofRepoIdOrNull(record.getAD_Process_ID()))
				.emailCustomType(EMailCustomType.ofNullableCode(record.getCustomType()))
				.build();
	}

	//
	//
	//
	//
	//
	//

	@Value
	@Builder
	private static class MailboxEntry
	{
		@NonNull MailboxId id;
		boolean active;
		@NonNull Mailbox mailbox;
	}

	private static class MailboxesMap
	{
		private final ImmutableMap<MailboxId, MailboxEntry> byId;

		private MailboxesMap(final List<MailboxEntry> list)
		{
			this.byId = Maps.uniqueIndex(list, MailboxEntry::getId);
		}

		public Mailbox getById(final MailboxId mailboxId)
		{
			return getEntryById(mailboxId).getMailbox();
		}

		private MailboxEntry getEntryById(final MailboxId mailboxId)
		{
			final MailboxEntry entry = byId.get(mailboxId);
			if (entry == null)
			{
				throw new AdempiereException("No mailbox found for " + mailboxId);
			}
			return entry;
		}
	}
}
