package de.metas.email.rest;

import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.email.MailService;
import de.metas.email.test.TestMailRequest;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.PlainStringLoggable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
public abstract class DebugMailRestControllerTemplate
{
	@NonNull private final MailService mailService;

	protected abstract void assertAuth();

	protected abstract UserId getLoggedUserId();

	protected abstract boolean isLoggedInAsSysAdmin();

	@GetMapping("/send")
	public ResponseEntity<String> sendMail(
			@RequestParam(name = "AD_Client_ID", required = false, defaultValue = "-1") int adClientId,
			@RequestParam(name = "AD_Org_ID", required = false, defaultValue = "-1") int adOrgId,
			@RequestParam(name = "AD_Process_ID", required = false, defaultValue = "-1") int adProcessId,
			@RequestParam(name = "EMailCustomType", required = false) String emailCustomType,
			@RequestParam(name = "From_User_ID", required = false, defaultValue = "-1") int fromUserRepoId,
			@RequestParam(name = "to") String to,
			@RequestParam(name = "subject", required = false) String subject,
			@RequestParam(name = "message", required = false) String message,
			@RequestParam(name = "isHtml", required = false, defaultValue = "false") boolean isHtml)
	{
		assertAuth();

		final PlainStringLoggable loggable = Loggables.newPlainStringLoggable();
		final TestMailRequest request = TestMailRequest.builder()
				.clientId(adClientId >= 0 ? ClientId.ofRepoId(adClientId) : ClientId.METASFRESH)
				.orgId(OrgId.ofRepoIdOrAny(adOrgId))
				.processId(AdProcessId.ofRepoIdOrNull(adProcessId))
				.emailCustomType(EMailCustomType.ofNullableCode(emailCustomType))
				.fromUserId(UserId.ofRepoIdOrNull(fromUserRepoId))
				.mailTo(EMailAddress.ofString(to))
				.subject(subject)
				.message(message)
				.isHtml(isHtml)
				.loggable(loggable)
				.build();
		assertPermissions(request);

		mailService.test(request);

		return ResponseEntity.ok()
				.contentType(MediaType.TEXT_PLAIN)
				.body(loggable.getConcatenatedMessages());
	}

	private void assertPermissions(@NonNull final TestMailRequest request)
	{
		if (isLoggedInAsSysAdmin())
		{
			return;
		}

		final UserId fromUserId = request.getFromUserId();
		if (fromUserId != null && !UserId.equals(fromUserId, getLoggedUserId()))
		{
			throw new AdempiereException("From_User_ID must be either not set/negative or " + getLoggedUserId().getRepoId());
		}

		if (!ClientId.equals(request.getClientId(), ClientId.METASFRESH))
		{
			throw new AdempiereException("AD_Client_ID must be either not set/negative or " + ClientId.METASFRESH.getRepoId());
		}
	}
}
