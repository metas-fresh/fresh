package de.metas.email.mailboxes;

import com.google.common.collect.ImmutableList;
import de.metas.document.DocBaseType;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.service.ClientId;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;
import static java.util.Comparator.reverseOrder;

class MailboxRoutingTable
{
	private static final Comparator<MailboxRouting> SPECIFIC_TO_GENERIC_ORDER = Comparator.comparing(MailboxRouting::getClientId, reverseOrder())
			.thenComparing(MailboxRouting::getOrgId, reverseOrder())
			.thenComparing(MailboxRouting::getEmailCustomType, nullsLast(naturalOrder()))
			.thenComparing(MailboxRouting::getAdProcessId, nullsLast(naturalOrder()))
			.thenComparing(MailboxRouting::getDocBaseType, nullsLast(naturalOrder()))
			.thenComparing(MailboxRouting::getDocSubType, nullsLast(naturalOrder()));
	private static final MailboxRoutingTable EMPTY = new MailboxRoutingTable(ImmutableList.of());

	private final ImmutableList<MailboxRouting> listOrdered;

	private MailboxRoutingTable(@NonNull final List<MailboxRouting> list)
	{
		this.listOrdered = list.stream()
				.sorted(SPECIFIC_TO_GENERIC_ORDER)
				.collect(ImmutableList.toImmutableList());
	}

	public static MailboxRoutingTable ofList(@NonNull final List<MailboxRouting> list)
	{
		return list.isEmpty() ? EMPTY : new MailboxRoutingTable(list);
	}

	public Optional<MailboxRouting> findBestMatching(@NonNull final MailboxQuery query)
	{
		return listOrdered.stream()
				.filter(routing -> isMatching(routing, query))
				.findFirst();
	}

	private static boolean isMatching(@NonNull final MailboxRouting routing, @NonNull final MailboxQuery query)
	{
		return ClientId.equals(routing.getClientId(), query.getClientId())
				&& isMatching_OrgId(routing, query)
				&& isMatching_EmailCustomType(routing, query)
				&& isMatching_AdProcessId(routing, query)
				&& isMatching_DocBaseType(routing, query)
				&& isMatching_DocSubType(routing, query);
	}

	private static boolean isMatching_OrgId(@NonNull final MailboxRouting routing, @NonNull final MailboxQuery query)
	{
		return routing.getOrgId().isAny() || OrgId.equals(routing.getOrgId(), query.getOrgId());
	}

	private static boolean isMatching_EmailCustomType(@NonNull final MailboxRouting routing, @NonNull final MailboxQuery query)
	{
		return routing.getEmailCustomType() == null || EMailCustomType.equals(routing.getEmailCustomType(), query.getCustomType());
	}

	private static boolean isMatching_AdProcessId(@NonNull final MailboxRouting routing, @NonNull final MailboxQuery query)
	{
		return routing.getAdProcessId() == null || AdProcessId.equals(routing.getAdProcessId(), query.getAdProcessId());
	}

	private static boolean isMatching_DocBaseType(@NonNull final MailboxRouting routing, @NonNull final MailboxQuery query)
	{
		final DocBaseType queryDocBaseType = query.getDocBaseType();
		return queryDocBaseType == null || DocBaseType.equals(queryDocBaseType, routing.getDocBaseType());
	}

	private static boolean isMatching_DocSubType(@NonNull final MailboxRouting routing, @NonNull final MailboxQuery query)
	{
		final String routingDocSubType = routing.getDocBaseAndSubType() != null ? StringUtils.trimBlankToNull(routing.getDocBaseAndSubType().getDocSubType()) : null;
		if (routingDocSubType == null)
		{
			return true;
		}

		final String queryDocSubType = query.getDocBaseAndSubType() != null ? StringUtils.trimBlankToNull(query.getDocBaseAndSubType().getDocSubType()) : null;
		return Objects.equals(routingDocSubType, queryDocSubType);
	}
}
