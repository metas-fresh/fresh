package de.metas.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.JsonObjectMapperHolder;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.SMTPConfig;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Tests if {@link EMail} related objects are JSON serializable.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class EMailJsonTest
{
	private ObjectMapper jsonObjectMapper;
	private Random random;

	@BeforeEach
	public void init()
	{
		Adempiere.enableUnitTestMode(); // needed to display Mailbox passwords

		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		jsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty

		random = new Random(System.currentTimeMillis());
	}

	@Test
	public void test_EMailSentStatus() throws Exception
	{
		testSerializeDeserialize(EMailSentStatus.NOT_SENT);
		testSerializeDeserialize(EMailSentStatus.ok("1234"));
		testSerializeDeserialize(EMailSentStatus.error(new AdempiereException("some error")));
		testSerializeDeserialize(EMailSentStatus.invalid("some invalid error"));
		testSerializeDeserialize(EMailSentStatus.invalid(new AdempiereException("some invalid error")));
	}

	@Test
	public void test_EMailAttachment() throws Exception
	{
		testJsonEquals(EMailAttachment.of("dummy.pdf", generateBytes(10)));
		testJsonEquals(EMailAttachment.of(new URI("http://dummy.org/file.pdf")));
	}

	@Test
	public void test_Mailbox() throws Exception
	{
		testJsonToStringEquals(
				Mailbox.builder()
						.email(EMailAddress.ofString("from@email.com"))
						.userToColumnName("userToColumnName")
						.type(MailboxType.SMTP)
						.smtpConfig(SMTPConfig.builder()
								.smtpHost("smtpHost")
								.smtpAuthorization(true)
								.username("username")
								.password("password111")
								.build())
						.build()
		);
	}

	@Test
	public void test_EMail() throws Exception
	{
		final Mailbox mailbox = Mailbox.builder()
				.email(EMailAddress.ofString("from@email.com"))
				.userToColumnName("userToColumnName")
				.type(MailboxType.SMTP)
				.smtpConfig(SMTPConfig.builder()
						.smtpHost("smtpHost")
						.smtpAuthorization(true)
						.username("username")
						.password("password111")
						.build())
				.build();
		final EMailAddress to = EMailAddress.ofString("to@email.com");
		final String subject = "test email subject";
		final String message = "dummy text message";
		final boolean html = false;
		final EMail email = new EMail(mailbox, to, subject, message, html);
		email.addTo(EMailAddress.ofString("to2@email.com"));
		email.addCc(EMailAddress.ofString("cc1@email.com"));
		email.addCc(EMailAddress.ofString("cc2@email.com"));
		email.addBcc(EMailAddress.ofString("bcc1@email.com"));
		email.addBcc(EMailAddress.ofString("bcc2@email.com"));
		email.setReplyTo(EMailAddress.ofString("reply-to@email.com"));
		email.addAttachment("dummy.pdf", generateBytes(20));
		email.addAttachment(new URI("http://dummy.com/report.pdf"));

		testJsonToStringEquals(email);
	}

	private byte[] generateBytes(final int size)
	{
		final byte[] bytes = new byte[size];
		random.nextBytes(bytes);
		return bytes;
	}

	private <T> void testJsonToStringEquals(final T value) throws Exception
	{
		final T valueDeserialized = testSerializeDeserialize(value);

		final String valueStr = value.toString();
		final String valueDeserializedStr = valueDeserialized == null ? null : valueDeserialized.toString();
		assertThat(valueDeserializedStr).isEqualTo(valueStr);
	}

	private <T> void testJsonEquals(final T value) throws Exception
	{
		final T valueDeserialized = testSerializeDeserialize(value);
		assertThat(valueDeserialized).isEqualTo(value);
	}

	private <T> T testSerializeDeserialize(final T value) throws Exception
	{
		@SuppressWarnings("unchecked") final Class<T> type = (Class<T>)value.getClass();

		final String jsonStr = jsonObjectMapper.writeValueAsString(value);
		System.out.println("\n\n-------------------------------------------------------------------------------------------");
		System.out.println("Value: " + value);
		System.out.println("Type: " + type);
		System.out.println("JSON: " + jsonStr);

		final T valueDeserialized = jsonObjectMapper.readValue(jsonStr, type);
		System.out.println("Value deserialized: " + valueDeserialized);
		System.out.println("-------------------------------------------------------------------------------------------");
		return valueDeserialized;
	}
}
