package de.metas.email.mailboxes;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_AD_MailBox;

@Getter
@RequiredArgsConstructor
public enum MailboxType implements ReferenceListAwareEnum
{
	SMTP(X_AD_MailBox.TYPE_SMTP),
	MICROSOFT_GRAPH(X_AD_MailBox.TYPE_MSGraph),
	;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<MailboxType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static MailboxType ofCode(@NonNull String code) {return index.ofCode(code);}
}
