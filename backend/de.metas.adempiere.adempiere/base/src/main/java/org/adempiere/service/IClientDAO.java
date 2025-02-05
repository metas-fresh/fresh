package org.adempiere.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.templates.ClientMailTemplates;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.util.Env;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface IClientDAO extends ISingletonService
{
	/**
	 * System AD_Client_ID (i.e. AD_Client_ID=0)
	 */
	int SYSTEM_CLIENT_ID = ClientId.SYSTEM.getRepoId();

	I_AD_Client getById(ClientId adClientId);

	default I_AD_Client getById(int adClientId)
	{
		return getById(ClientId.ofRepoId(adClientId));
	}

	List<I_AD_Client> getByIds(@NonNull Set<ClientId> adClientIds);

	@Deprecated
	I_AD_Client retriveClient(Properties ctx, int adClientId);

	/**
	 * Retrieves currently login {@link I_AD_Client}.
	 *
	 * @return context client
	 * @see Env#getAD_Client_ID(Properties)
	 */
	I_AD_Client retriveClient(Properties ctx);

	List<I_AD_Client> retrieveAllClients(Properties ctx);

	/** @return client/tenant info for context AD_Client_ID; never returns null */
	I_AD_ClientInfo retrieveClientInfo(Properties ctx);

	/** @return client/tenant info for given AD_Client_ID; never returns null */
	I_AD_ClientInfo retrieveClientInfo(Properties ctx, int adClientId);

	ClientEMailConfig getEMailConfigById(ClientId clientId);

	ClientMailTemplates getClientMailTemplatesById(ClientId clientId);

	boolean isMultilingualDocumentsEnabled(ClientId adClientId);

	String getClientNameById(@NonNull ClientId clientId);
}
