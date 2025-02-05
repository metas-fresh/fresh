/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.notification;

import de.metas.attachments.AttachmentEntryService;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.references.zoom_into.NullCustomizedWindowInfoMapRepository;
import de.metas.email.MailService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.notification.impl.NotificationRepository;
import de.metas.organization.OrgId;
import de.metas.ui.web.WebuiURLs;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_API_Request_Audit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static de.metas.notification.NotificationMessageFormatter.MSG_EmailOrigin;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificationSenderTemplateTest
{
	private final String MOCK_FE_URL = "MOCK_FE_URL";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private NotificationSenderTemplate sender;
	private MockedMsgBL mockedMsgBL;
	private IDocumentBL mockedDocumentBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		mockedMsgBL = new MockedMsgBL();
		Services.registerService(IMsgBL.class, mockedMsgBL);

		mockedDocumentBL = Mockito.mock(IDocumentBL.class);

		Services.registerService(IDocumentBL.class, mockedDocumentBL);

		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
		Services.registerService(INotificationRepository.class, new NotificationRepository(attachmentEntryService, NullCustomizedWindowInfoMapRepository.instance));

		SpringContextHolder.registerJUnitBean(MailService.newInstanceForUnitTesting());
		SpringContextHolder.registerJUnitBean(new UserGroupRepository());

		sender = new NotificationSenderTemplate();

		Services.get(ISysConfigBL.class)
				.setValue(WebuiURLs.SYSCONFIG_FRONTEND_URL, MOCK_FE_URL, ClientId.SYSTEM, OrgId.ANY);
	}

	@After
	public void after()
	{
		mockedMsgBL = null;
		Services.clear();
	}

	@Test
	public void givenUserNotificationRequest_whenSenderComputesEmail_thenEmailContainsRecordReferenceLinkAndOrigin()
	{
		//prerequisites
		final AdMessageKey testKey = AdMessageKey.of("TestKey");
		mockedMsgBL.putMsgText(testKey, "Test message is {0}.");

		mockedMsgBL.putMsgText(MSG_EmailOrigin, "Email sent from {0}.");

		final UserNotificationsConfig notificationsConfig = UserNotificationsConfig.builder()
				.userId(UserId.METASFRESH)
				.userADLanguage("EN")
				.defaults(UserNotificationsGroup.prepareDefault().notificationType(NotificationType.EMail).build())
				.build();

		final I_API_Request_Audit mockRecord = InterfaceWrapperHelper.newInstance(I_API_Request_Audit.class);
		InterfaceWrapperHelper.save(mockRecord);

		Mockito.when(mockedDocumentBL.getDocumentNo(Mockito.any())).thenReturn(String.valueOf(mockRecord.getAPI_Request_Audit_ID()));

		final I_AD_Window window = InterfaceWrapperHelper.newInstance(I_AD_Window.class);
		InterfaceWrapperHelper.save(window);

		final I_AD_Table reqTable = queryBL.createQueryBuilder(I_AD_Table.class)
				.addEqualsFilter(I_AD_Table.COLUMNNAME_TableName, I_API_Request_Audit.Table_Name)
				.create()
				.firstOnlyNotNull(I_AD_Table.class);

		reqTable.setAD_Window_ID(window.getAD_Window_ID());
		InterfaceWrapperHelper.save(reqTable);

		//given
		final UserNotificationRequest request = UserNotificationRequest.builder()
				.notificationsConfig(notificationsConfig)
				.recipient(Recipient.user(UserId.METASFRESH))
				.contentADMessage(testKey)
				.contentADMessageParam(TableRecordReference.of(I_API_Request_Audit.Table_Name, mockRecord.getAPI_Request_Audit_ID()))
				.build();

		//when
		final String mailSubject = sender.extractMailSubject(request);

		//then
		assertThat(mailSubject).isNotNull();
		assertThat(mailSubject).isEqualTo("Test message is " + mockRecord.getAPI_Request_Audit_ID() + ".");

		//when
		final String mailContent = sender.extractMailContent(request);

		//then
		assertThat(mailContent).isNotNull();
		assertThat(mailContent).isEqualTo("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
												  + "\t<body>\n"
												  + "\t\tTest message is <a href=\"" + MOCK_FE_URL + "/window/" + window.getAD_Window_ID() + "/" + mockRecord.getAPI_Request_Audit_ID() + "\">" + mockRecord.getAPI_Request_Audit_ID() + "</a>.\n"
												  + "\t\t<br/>\n"
												  + "\t\t\n"
												  + "\t\t<br/>\n"
												  + "\t\tEmail sent from " + MOCK_FE_URL + ".\n"
												  + "\t\t<br/>\n"
												  + "\t</body>\n"
												  + "</html>");
	}
}
