package de.metas.email.mailboxes;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutor;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

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

@Value
@Builder
public class MailboxQuery
{
	@NonNull ClientId clientId;
	@NonNull @Builder.Default OrgId orgId = ProcessExecutor.getCurrentOrgId();
	@Nullable @Builder.Default AdProcessId adProcessId = ProcessExecutor.getCurrentProcessIdOrNull();
	@Nullable DocBaseAndSubType docBaseAndSubType;
	@Nullable EMailCustomType customType;

	@Nullable UserId fromUserId;

	public static MailboxQuery ofClientId(@NonNull final ClientId clientId) {return MailboxQuery.builder().clientId(clientId).build();}

	@Nullable public DocBaseType getDocBaseType() { return docBaseAndSubType != null ? docBaseAndSubType.getDocBaseType() : null; }
	@Nullable public String getDocSubType() { return docBaseAndSubType != null ? docBaseAndSubType.getDocSubType() : null; }
}
