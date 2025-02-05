package de.metas.email.test;

import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.user.UserId;
import de.metas.util.ILoggable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
public class TestMailRequest
{
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@Nullable AdProcessId processId;
	@Nullable EMailCustomType emailCustomType;
	@Nullable UserId fromUserId;
	@NonNull EMailAddress mailTo;
	@Nullable String subject;
	@Nullable String message;
	boolean isHtml;
	
	@Nullable ILoggable loggable;
}
