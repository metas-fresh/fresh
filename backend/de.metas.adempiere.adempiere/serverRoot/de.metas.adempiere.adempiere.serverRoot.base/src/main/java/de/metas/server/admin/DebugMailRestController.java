package de.metas.server.admin;

import de.metas.email.MailService;
import de.metas.email.rest.DebugMailRestControllerTemplate;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.compiere.util.Env;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DebugMailRestController.ENDPOINT)
public class DebugMailRestController extends DebugMailRestControllerTemplate
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/debug/mail";

	public DebugMailRestController(@NonNull final MailService mailService)
	{
		super(mailService);
	}

	@Override
	protected void assertAuth() {/* already asserted */}

	@Override
	protected UserId getLoggedUserId() {return Env.getLoggedUserId();}

	@Override
	protected boolean isLoggedInAsSysAdmin() {return Env.getLoggedRoleId().isSystem();}
}
