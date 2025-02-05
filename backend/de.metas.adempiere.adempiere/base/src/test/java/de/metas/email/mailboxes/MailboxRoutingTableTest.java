package de.metas.email.mailboxes;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailboxRoutingTableTest
{
	@Test
	void oneRule_clientLevel()
	{
		final MailboxRouting routing1;
		final MailboxRoutingTable table = MailboxRoutingTable.ofList(ImmutableList.of(
				routing1 = MailboxRouting.builder().mailboxId(MailboxId.ofRepoId(1)).clientId(ClientId.METASFRESH).orgId(OrgId.ANY).build()
		));

		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.SYSTEM).build()))
				.isEmpty();
		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.METASFRESH).orgId(OrgId.MAIN).build()))
				.containsSame(routing1);
	}

	@Test
	void oneRule_orgLevel()
	{
		final MailboxRouting routing1;
		final MailboxRoutingTable table = MailboxRoutingTable.ofList(ImmutableList.of(
				routing1 = MailboxRouting.builder().mailboxId(MailboxId.ofRepoId(1)).clientId(ClientId.METASFRESH).orgId(OrgId.ofRepoId(1)).build()
		));

		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.SYSTEM).build()))
				.isEmpty();
		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.METASFRESH).build()))
				.isEmpty();
		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.METASFRESH).orgId(OrgId.ofRepoId(1)).build()))
				.containsSame(routing1);
		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.METASFRESH).orgId(OrgId.ofRepoId(2)).build()))
				.isEmpty();
	}
}