/**
 *
 */
package de.metas.email.process;

import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.email.MailService;
import de.metas.email.test.TestMailRequest;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.user.UserId;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;


@SuppressWarnings("unused")
public class EMailConfigTest extends JavaProcess
{
	private final MailService mailService = SpringContextHolder.instance.getBean(MailService.class);

	@Param(parameterName = "AD_Client_ID", mandatory = true)
	private ClientId p_AD_Client_ID;
	@Param(parameterName = "AD_Org_ID", mandatory = true)
	private OrgId p_AD_Org_ID;
	@Param(parameterName = "AD_Process_ID")
	private AdProcessId p_AD_Process_ID;
	@Param(parameterName = "CustomType")
	private String p_CustomType;
	@Param(parameterName = "From_User_ID")
	private UserId p_From_User_ID;
	@Param(parameterName = "EMail_To", mandatory = true)
	private String p_EMail_To;
	@Param(parameterName = "Subject")
	private String p_Subject;
	@Param(parameterName = "Message")
	private String p_Message;
	@Param(parameterName = "IsHtml")
	private boolean p_IsHtml;

	@Override
	protected String doIt()
	{
		mailService.test(
				TestMailRequest.builder()
						.clientId(p_AD_Client_ID)
						.orgId(p_AD_Org_ID)
						.processId(p_AD_Process_ID)
						.emailCustomType(EMailCustomType.ofNullableCode(p_CustomType))
						.fromUserId(p_From_User_ID)
						.mailTo(EMailAddress.ofString(p_EMail_To))
						.subject(p_Subject)
						.message(p_Message)
						.isHtml(p_IsHtml)
						.build()
		);
		
		return MSG_OK;
	}
}
