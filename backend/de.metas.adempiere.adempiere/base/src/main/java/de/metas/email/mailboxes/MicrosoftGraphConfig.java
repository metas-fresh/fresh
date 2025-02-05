package de.metas.email.mailboxes;

import de.metas.email.EMailAddress;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
@ToString(exclude = { "clientSecret" })
public class MicrosoftGraphConfig
{
	@NonNull String clientId;
	@NonNull String tenantId;
	@NonNull String clientSecret;
	@Nullable @With EMailAddress defaultReplyTo;
}
