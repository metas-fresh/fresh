package de.metas.payment.paymentterm.impl;

import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.util.Check.isNotBlank;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PaymentTermRepository implements IPaymentTermRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_PaymentTerm getById(@NonNull final PaymentTermId paymentTermId)
	{
		return loadOutOfTrx(paymentTermId, I_C_PaymentTerm.class);
	}

	@Override
	public Percent getPaymentTermDiscount(@Nullable final PaymentTermId paymentTermId)
	{
		if (paymentTermId == null)
		{
			return Percent.ZERO;
		}

		final I_C_PaymentTerm paymentTerm = getById(paymentTermId);
		if (paymentTerm == null)
		{
			return Percent.ZERO;
		}

		return Percent.of(paymentTerm.getDiscount());
	}

	// this method is implemented after a code block from MOrder.beforeSave()
	@Override
	public PaymentTermId getDefaultPaymentTermIdOrNull()
	{
		final int contextPaymentTerm = Env.getContextAsInt(Env.getCtx(), "#C_PaymentTerm_ID");
		if (contextPaymentTerm > 0)
		{
			return PaymentTermId.ofRepoId(contextPaymentTerm);
		}

		final int dbPaymentTermId = queryBL
				.createQueryBuilder(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsDefault, true)
				.addOnlyContextClient(Env.getCtx())
				.create()
				.firstId();
		if (dbPaymentTermId > 0)
		{
			return PaymentTermId.ofRepoId(dbPaymentTermId);
		}

		return null;
	}

	@Override
	public Optional<PaymentTermId> retrievePaymentTermId(@NonNull final PaymentTermQuery query)
	{
		final IQueryBuilder<I_C_PaymentTerm> queryBuilder;
		if (query.getBPartnerId() != null)
		{
			final IQueryBuilder<I_C_BPartner> tmp = queryBL
					.createQueryBuilder(I_C_BPartner.class)
					.addEqualsFilter(I_C_BPartner.COLUMN_C_BPartner_ID, query.getBPartnerId());
			if (query.getSoTrx() != null && query.getSoTrx().isPurchase())
			{
				queryBuilder = tmp.andCollect(I_C_BPartner.COLUMNNAME_PO_PaymentTerm_ID, I_C_PaymentTerm.class);
			}
			else
			{
				queryBuilder = tmp.andCollect(I_C_BPartner.COLUMNNAME_C_PaymentTerm_ID, I_C_PaymentTerm.class);
			}
		}
		else
		{
			queryBuilder = queryBL
					.createQueryBuilder(I_C_PaymentTerm.class)
					.addOnlyActiveRecordsFilter();
		}

		if (query.getOrgId() != null)
		{
			queryBuilder.addInArrayFilter(I_C_PaymentTerm.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY);
		}

		if (query.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_ExternalId, query.getExternalId().getValue());
		}

		if (isNotBlank(query.getValue()))
		{
			queryBuilder.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Value, query.getValue());
		}

		try
		{
			final PaymentTermId firstId = queryBuilder
					.create()
					.firstIdOnly(PaymentTermId::ofRepoIdOrNull);

			if (firstId == null && query.isFallBackToDefault())
			{
				return getDefaultPaymentTermId();
			}

			return Optional.ofNullable(firstId);
		}
		catch (final DBMoreThanOneRecordsFoundException e)
		{
			// augment and rethrow
			throw e.appendParametersToMessage().setParameter("paymentTermQuery", query);
		}
	}

	@NonNull
	public Optional<PaymentTermId> getDefaultPaymentTermId()
	{
		final int contextPaymentTerm = Env.getContextAsInt(Env.getCtx(), "#C_PaymentTerm_ID");
		if (contextPaymentTerm > 0)
		{
			return Optional.of(PaymentTermId.ofRepoId(contextPaymentTerm));
		}

		final int dbPaymentTermId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaymentTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_IsDefault, true)
				.addOnlyContextClient(Env.getCtx())
				.create()
				.firstId();
		if (dbPaymentTermId > 0)
		{
			return Optional.of(PaymentTermId.ofRepoId(dbPaymentTermId));
		}

		return Optional.empty();
	}

}
