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

package de.metas.banking.camt53.wrapper.v02;

import de.metas.banking.camt53.jaxb.camt053_001_02.EntryTransaction2;
import de.metas.banking.camt53.jaxb.camt053_001_02.PartyIdentification32;
import de.metas.banking.camt53.jaxb.camt053_001_02.RemittanceInformation5;
import de.metas.banking.camt53.jaxb.camt053_001_02.TransactionParty2;
import de.metas.banking.camt53.wrapper.TransactionDtlsWrapper;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

@Value
@EqualsAndHashCode(callSuper = true)
public class TransactionDtls2Wrapper extends TransactionDtlsWrapper
{
	@NonNull
	EntryTransaction2 entryDtls;

	@Builder
	private TransactionDtls2Wrapper(@NonNull final EntryTransaction2 entryDtls)
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
		final TransactionParty2 party = entryDtls.getRltdPties();
		if (party != null && party.getDbtr() != null)
		{
			final PartyIdentification32 dbtr = party.getDbtr();
			return dbtr.getNm();
		}

		return null;
	}

	@Override
	public String getCdtrNames()
	{

		final TransactionParty2 party = entryDtls.getRltdPties();
		if (party != null && party.getDbtr() != null)
		{
			final PartyIdentification32 cdtr = party.getCdtr();
			return cdtr.getNm();
		}

		return null;
	}

	@Override
	protected @Nullable String getUnstructuredRemittanceInfo(final @NonNull String delimiter)
	{
		final RemittanceInformation5 rmtInf = entryDtls.getRmtInf();
		if(rmtInf == null)
		{
			return null;
		}
		return String.join(delimiter, rmtInf.getUstrd());
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
		return entryDtls.getAmtDtls().getInstdAmt().getAmt().getCcy();
	}

	/**
	 * @return true if this is a "credit" line (i.e. we get money)
	 */
	@Override
	public boolean isCRDT()
	{
		final TransactionParty2 party = entryDtls.getRltdPties();
		return party.getCdtr() != null;
	}
}
