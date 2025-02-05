package org.adempiere.service.impl;

import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.email.EMailAddress;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.SMTPConfig;
import de.metas.email.templates.ClientMailTemplates;
import de.metas.email.templates.MailTemplateId;
import de.metas.i18n.ExplainedOptional;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.util.Env;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class ClientDAO implements IClientDAO
{
	private final CCache<ClientId, ClientEMailConfig> emailConfigCache = CCache.<ClientId, ClientEMailConfig>builder()
			.tableName(I_AD_Client.Table_Name)
			.build();
	private final CCache<ClientId, ClientMailTemplates> emailTemplatesCache = CCache.<ClientId, ClientMailTemplates>builder()
			.tableName(I_AD_Client.Table_Name)
			.build();

	@Override
	public I_AD_Client getById(@NonNull final ClientId adClientId)
	{
		return loadOutOfTrx(adClientId, I_AD_Client.class);
	}

	@Override
	public List<I_AD_Client> getByIds(@NonNull final Set<ClientId> adClientIds)
	{
		return InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx(adClientIds, I_AD_Client.class);
	}

	@Override
	public I_AD_Client retriveClient(Properties ctx, int adClientId)
	{
		// NOTE: we assume table level caching is configured for AD_Client table
		// see org.adempiere.model.validator.AdempiereBaseValidator.setupCaching(IModelCacheService)
		return InterfaceWrapperHelper.create(ctx, adClientId, I_AD_Client.class, ITrx.TRXNAME_None);
	}

	@Override
	public I_AD_Client retriveClient(final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		return retriveClient(ctx, adClientId);
	}

	@Override
	public List<I_AD_Client> retrieveAllClients(Properties ctx)
	{
		final IQueryBuilder<I_AD_Client> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Client.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter() // metas-ts: only return active clients (note that we have an inactive "trash" client with AD_Client_ID=99)
				// .addOnlyContextClient() // NO! we want all of them
				;

		queryBuilder.orderBy()
				.addColumn(I_AD_Client.COLUMNNAME_AD_Client_ID) // to have a predictable order
		;

		return queryBuilder.create().list();
	}

	@Override
	@Cached(cacheName = I_AD_ClientInfo.Table_Name)
	public I_AD_ClientInfo retrieveClientInfo(@CacheCtx final Properties ctx, final int adClientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ClientInfo.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_ClientInfo.COLUMN_AD_Client_ID, adClientId)
				.create()
				.firstOnlyNotNull(I_AD_ClientInfo.class);
	}    // get

	@Override
	public I_AD_ClientInfo retrieveClientInfo(final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		return retrieveClientInfo(ctx, adClientId);
	}    // get

	@Override
	public ClientEMailConfig getEMailConfigById(@NonNull final ClientId clientId)
	{
		return emailConfigCache.getOrLoad(clientId, this::retrieveEMailConfigById);
	}

	private ClientEMailConfig retrieveEMailConfigById(final @NonNull ClientId clientId)
	{
		final I_AD_Client record = getById(clientId);
		return toClientEMailConfig(record);
	}

	public static ClientEMailConfig toClientEMailConfig(@NonNull final I_AD_Client client)
	{
		return ClientEMailConfig.builder()
				.clientId(ClientId.ofRepoId(client.getAD_Client_ID()))
				.mailbox(extractMailbox(client))
				.build();
	}

	private static ExplainedOptional<Mailbox> extractMailbox(@NonNull final I_AD_Client client)
	{
		final EMailAddress email = EMailAddress.ofNullableString(client.getRequestEMail());
		if (email == null)
		{
			return ExplainedOptional.emptyBecause("AD_Client.RequestEMail not set");
		}

		return ExplainedOptional.of(
				Mailbox.builder()
						.type(MailboxType.SMTP)
						.email(email)
						.smtpConfig(extractSMTPConfig(client))
						.build()
		);
	}

	private static SMTPConfig extractSMTPConfig(@NonNull final I_AD_Client client)
	{
		return SMTPConfig.builder()
				.smtpHost(client.getSMTPHost())
				.smtpPort(client.getSMTPPort())
				.smtpAuthorization(client.isSmtpAuthorization())
				.username(client.getRequestUser())
				.password(client.getRequestUserPW())
				.startTLS(client.isStartTLS())
				.build();
	}

	@Override
	public ClientMailTemplates getClientMailTemplatesById(final ClientId clientId)
	{
		return emailTemplatesCache.getOrLoad(clientId, this::retrieveClientMailTemplatesById);
	}

	private ClientMailTemplates retrieveClientMailTemplatesById(final ClientId clientId)
	{
		final I_AD_Client record = getById(clientId);
		return toClientMailTemplates(record);
	}

	private static ClientMailTemplates toClientMailTemplates(@NonNull final I_AD_Client record)
	{
		return ClientMailTemplates.builder()
				.passwordResetMailTemplateId(MailTemplateId.optionalOfRepoId(record.getPasswordReset_MailText_ID()))
				.build();
	}

	@Override
	public boolean isMultilingualDocumentsEnabled(@NonNull final ClientId adClientId)
	{
		final I_AD_Client client = getById(adClientId);
		return client.isMultiLingualDocument();
	}

	@Override
	public String getClientNameById(@NonNull final ClientId clientId)
	{
		return getById(clientId).getName();
	}
}
