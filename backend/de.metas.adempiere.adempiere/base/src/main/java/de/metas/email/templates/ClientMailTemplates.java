package de.metas.email.templates;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ClientMailTemplates
{
	@NonNull Optional<MailTemplateId> passwordResetMailTemplateId;
}
