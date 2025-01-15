/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_BP_BankAccount;

import javax.annotation.Nullable;

@Getter
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public enum BPBankAcctUse implements ReferenceListAwareEnum
{
	NONE(X_C_BP_BankAccount.BPBANKACCTUSE_Nichts),
	DEBIT_OR_DEPOSIT(X_C_BP_BankAccount.BPBANKACCTUSE_Both),
	DEBIT(X_C_BP_BankAccount.BPBANKACCTUSE_DirectDebit),
	DEPOSIT(X_C_BP_BankAccount.BPBANKACCTUSE_DirectDeposit),
	PROVISION(X_C_BP_BankAccount.BPBANKACCTUSE_Provision);
	private final String code;
	private static final ReferenceListAwareEnums.ValuesIndex<BPBankAcctUse> typesByCode = ReferenceListAwareEnums.index(values());

	BPBankAcctUse(final String code)
	{
		this.code = code;
	}

	public static BPBankAcctUse ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	@Nullable
	public static BPBankAcctUse ofNullableCode(@Nullable final String code) {return typesByCode.ofNullableCode(code);}

}
