/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.bpartner.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.banking.BankId;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.service.BPBankAcctUse;
import de.metas.bpartner.service.BankAccountQuery;
import de.metas.bpartner.service.IBPBankAccountDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Invoice;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BPBankAccountDAO implements IBPBankAccountDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public List<BPartnerBankAccount> retrieveBankAccountsForPartnerAndCurrency(@NonNull final BPartnerId partnerID,
																			  @Nullable final CurrencyId currencyID)
	{
		final IQueryBuilder<I_C_BP_BankAccount> qb = queryBL
				.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, partnerID);

		if (currencyID != null)
		{
			qb.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_Currency_ID, currencyID);
		}

		return qb.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last) // DESC (Y, then N)
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID)
				.endOrderBy()
				.create()
				.stream()
				.map(this::of)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Optional<I_C_BP_BankAccount> retrieveDefaultBankAccountInTrx(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BP_BankAccount bankAccount = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_C_BP_BankAccount.COLUMNNAME_IsDefault) // DESC (Y, then N)
				.create()
				.first();

		return Optional.ofNullable(bankAccount);
	}

	@Override
	public void deactivateIBANAccountsByBPartnerExcept(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Collection<BPartnerBankAccountId> exceptIds)
	{
		final ICompositeQueryUpdater<I_C_BP_BankAccount> columnUpdater = queryBL
				.createCompositeQueryUpdater(I_C_BP_BankAccount.class)
				.addSetColumnValue(I_C_BP_BankAccount.COLUMNNAME_IsActive, false);

		queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_BP_BankAccount.COLUMNNAME_IBAN)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_C_BP_BankAccount.COLUMN_C_BP_BankAccount_ID, exceptIds)
				.create()
				.update(columnUpdater);
	}

	@Override
	public ImmutableListMultimap<BPartnerId, I_C_BP_BankAccount> getAllByBPartnerIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL.createQueryBuilderOutOfTrx(I_C_BP_BankAccount.class)
				.addInArrayFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> BPartnerId.ofRepoId(record.getC_BPartner_ID()),
						record -> record));
	}

	@Override
	public List<BPartnerBankAccount> getBpartnerBankAccount(final BankAccountQuery query)
	{
		return getBpartnerBankAccountRecords(query)
				.stream()
				.map(this::of)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private List<I_C_BP_BankAccount> getBpartnerBankAccountRecords(final BankAccountQuery query)
	{
		final IQueryBuilder<I_C_BP_BankAccount> queryBuilder = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_C_BP_BankAccount.COLUMNNAME_IsDefault); // DESC (Y, then N)
		if (query.getBPartnerId() != null)
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, query.getBPartnerId());
		}
		else if (query.getInvoiceId() != null)
		{
			queryBuilder.addInSubQueryFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, I_C_Invoice.COLUMNNAME_C_BPartner_ID,
					queryBL.createQueryBuilder(I_C_Invoice.class)
							.addOnlyActiveRecordsFilter()
							.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, query.getInvoiceId())
							.create());
		}

		final Collection<BPBankAcctUse> bankAcctUses = query.getBpBankAcctUses();
		if (bankAcctUses != null && !bankAcctUses.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_BP_BankAccount.COLUMNNAME_BPBankAcctUse, bankAcctUses);
		}
		if (query.isContainsQRIBAN())
		{
			queryBuilder.addNotNull(I_C_BP_BankAccount.COLUMNNAME_QR_IBAN);
		}
		queryBuilder.orderBy(I_C_BP_BankAccount.COLUMN_C_BP_BankAccount_ID);
		return queryBuilder.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private BPartnerBankAccount of(@NonNull final I_C_BP_BankAccount record)
	{
		return BPartnerBankAccount.builder()
				.id(BPartnerBankAccountId.ofRepoId(record.getC_BPartner_ID(), record.getC_BP_BankAccount_ID()))
				.currencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()))
				.active(record.isActive())
				.orgMappingId(OrgMappingId.ofRepoIdOrNull(record.getAD_Org_Mapping_ID()))
				.iban(record.getIBAN())
				.name(record.getName())
				.isDefault(record.isDefault())
				.swiftCode(record.getSwiftCode())
				.qrIban(record.getQR_IBAN())
				.bankId(BankId.ofRepoIdOrNull(record.getC_Bank_ID()))
				.bpBankAcctUse(BPBankAcctUse.ofNullableCode(record.getBPBankAcctUse()))
				//.changeLog()
				.build();
	}
}
