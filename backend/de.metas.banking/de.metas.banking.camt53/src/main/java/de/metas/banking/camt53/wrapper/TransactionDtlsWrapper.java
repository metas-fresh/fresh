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

package de.metas.banking.camt53.wrapper;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.annotation.Nullable;

@NonFinal
@Value
public abstract class TransactionDtlsWrapper implements ITransactionDtlsWrapper
{
	@Override
	@Nullable
	public String getUnstructuredRemittanceInfo()
	{
		return getUnstructuredRemittanceInfo(" \n");
	}

	@Nullable
	protected abstract String getUnstructuredRemittanceInfo(@NonNull final String delimiter);

	@Override
	@NonNull
	public String getLineDescription()
	{
		return getLineDescription(" \n");
	}

	@NonNull
	protected abstract String getLineDescription(@NonNull final String delimiter);

}
