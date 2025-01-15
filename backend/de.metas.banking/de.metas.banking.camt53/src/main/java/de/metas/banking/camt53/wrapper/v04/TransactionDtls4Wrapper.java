/*
 * #%L
 * de.metas.banking.camt53
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.banking.camt53.wrapper.v04;

import de.metas.banking.camt53.jaxb.camt053_001_04.EntryTransaction4;
import de.metas.banking.camt53.jaxb.camt053_001_04.PartyIdentification43;
import de.metas.banking.camt53.jaxb.camt053_001_04.RemittanceInformation7;
import de.metas.banking.camt53.jaxb.camt053_001_04.TransactionParties3;
import de.metas.banking.camt53.wrapper.TransactionDtlsWrapper;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import static de.metas.banking.camt53.jaxb.camt053_001_04.CreditDebitCode.CRDT;

@Value
@EqualsAndHashCode(callSuper = true)
public class TransactionDtls4Wrapper extends TransactionDtlsWrapper
{
	@NonNull
	EntryTransaction4 entryDtls;

	@Builder
	private TransactionDtls4Wrapper(@NonNull final EntryTransaction4 entryDtls)
	{
		this.entryDtls = entryDtls;
	}

	@Nullable
	@Override
	public String getAcctSvcrRef()
	{
		return entryDtls.getRefs().getAcctSvcrRef();
	}

	@Override
	public String getDbtrNames()
	{
		final TransactionParties3 party = entryDtls.getRltdPties();
		if (party != null && party.getDbtr() != null)
		{
			final PartyIdentification43 dbtr = party.getDbtr();
			return dbtr.getNm();
		}

		return null;
	}

	@Override
	public String getCdtrNames()
	{

		final TransactionParties3 party = entryDtls.getRltdPties();
		if (party != null && party.getDbtr() != null)
		{
			final PartyIdentification43 cdtr = party.getCdtr();
			return cdtr.getNm();
		}

		return null;
	}

	@Nullable
	@Override
	protected String getUnstructuredRemittanceInfo(final @NonNull String delimiter)
	{
		final RemittanceInformation7 rmtInf = entryDtls.getRmtInf();
		if(rmtInf == null)
		{
			return null;
		}
		return String.join(delimiter, entryDtls.getRmtInf().getUstrd());
	}

	@Override
	protected @NonNull String getLineDescription(final @NonNull String delimiter)
	{
		return entryDtls.getAddtlTxInf();
	}

	@Nullable
	@Override
	public String getCcy()
	{
		return entryDtls.getAmt().getCcy();
	}

	/**
	 * @return true if this is a "credit" line (i.e. we get money)
	 */
	@Override
	public boolean isCRDT()
	{
		return CRDT == entryDtls.getCdtDbtInd();
	}
}
