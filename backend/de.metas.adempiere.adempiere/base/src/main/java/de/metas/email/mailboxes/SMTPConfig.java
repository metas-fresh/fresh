package de.metas.email.mailboxes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@ToString(exclude = "password")
public class SMTPConfig
{
	private static final int DEFAULT_SMTP_PORT = 25;
	private static final int DEFAULT_SMTPS_PORT = 587;

	@NonNull String smtpHost;
	int smtpPort;

	boolean smtpAuthorization;
	String username;
	String password;

	boolean startTLS;

	@Builder(toBuilder = true)
	@Jacksonized
	private SMTPConfig(
			@NonNull final String smtpHost,
			final int smtpPort,
			final boolean smtpAuthorization,
			final String username,
			final String password,
			final boolean startTLS)
	{
		this.smtpHost = StringUtils.trimBlankToOptional(smtpHost).orElseThrow(() -> new AdempiereException("smtpHost is empty"));
		this.smtpPort = smtpPort > 0 ? smtpPort : getDefaultSMTPPort(startTLS);

		if (smtpAuthorization)
		{
			this.smtpAuthorization = true;
			this.username = StringUtils.trimBlankToOptional(username).orElseThrow(() -> new AdempiereException("username is empty"));
			this.password = Check.assumeNotEmpty(password, "password is empty");
		}
		else
		{
			this.smtpAuthorization = false;
			this.username = null;
			this.password = null;
		}

		this.startTLS = startTLS;
	}

	private static int getDefaultSMTPPort(final boolean startTLS)
	{
		return startTLS ? DEFAULT_SMTPS_PORT : DEFAULT_SMTP_PORT;
	}

	public SMTPConfig mergeFrom(@NonNull final UserEMailConfig userEmailConfig)
	{
		return toBuilder()
				.username(Check.assumeNotEmpty(userEmailConfig.getUsername(), "username is set: {}", userEmailConfig))
				.password(userEmailConfig.getPassword())
				.build();

	}

}
