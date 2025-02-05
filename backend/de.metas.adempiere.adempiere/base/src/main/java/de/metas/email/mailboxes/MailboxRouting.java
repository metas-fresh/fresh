package de.metas.email.mailboxes;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
class MailboxRouting
{
	@NonNull MailboxId mailboxId;
	@Nullable String userToColumnName;
	
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@Nullable DocBaseAndSubType docBaseAndSubType;
	@Nullable AdProcessId adProcessId;
	@Nullable EMailCustomType emailCustomType;

	@Nullable public DocBaseType getDocBaseType() { return docBaseAndSubType != null ? docBaseAndSubType.getDocBaseType() : null; }
	@Nullable public String getDocSubType() { return docBaseAndSubType != null ? docBaseAndSubType.getDocSubType() : null; }
}
