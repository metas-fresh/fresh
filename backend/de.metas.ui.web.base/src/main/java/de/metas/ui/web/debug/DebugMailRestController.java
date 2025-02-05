package de.metas.ui.web.debug;

import de.metas.email.MailService;
import de.metas.email.rest.DebugMailRestControllerTemplate;
import de.metas.ui.web.session.UserSession;
import de.metas.user.UserId;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DebugMailRestController.ENDPOINT)
public class DebugMailRestController extends DebugMailRestControllerTemplate
{
	public static final String ENDPOINT = DebugRestController.ENDPOINT + "/mail";

	@NonNull private final UserSession userSession;

	public DebugMailRestController(
			@NonNull final UserSession userSession,
			@NonNull final MailService mailService)
	{
		super(mailService);
		this.userSession = userSession;
	}

	@Override
	protected void assertAuth() {userSession.assertLoggedIn();}

	@Override
	protected UserId getLoggedUserId() {return userSession.getLoggedUserId();}

	@Override
	protected boolean isLoggedInAsSysAdmin() {return userSession.isLoggedInAsSysAdmin();}
}
