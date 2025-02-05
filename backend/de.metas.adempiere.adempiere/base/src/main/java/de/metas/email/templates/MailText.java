package de.metas.email.templates;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MailText
{
	String adLanguage;
	String mailHeader;
	String fullMailText;
	boolean html;
}
