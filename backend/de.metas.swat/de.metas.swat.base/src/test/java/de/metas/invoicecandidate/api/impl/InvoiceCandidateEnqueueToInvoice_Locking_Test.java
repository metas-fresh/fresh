package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.async.api.NOPWorkpackageLogsRepository;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.impl.BPartnerStatisticsUpdater;
import de.metas.costing.impl.ChargeRepository;
import de.metas.currency.CurrencyRepository;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolRepository;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolService;
import de.metas.email.MailService;
import de.metas.greeting.GreetingRepository;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.user.UserGroupRepository;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

/**
 * Makes sure the invoice candidates get the right locks along the "enqueue to invoice" process.
 */
public class InvoiceCandidateEnqueueToInvoice_Locking_Test extends InvoiceCandidateEnqueueToInvoiceTestBase
{
	@BeforeEach
	public void registerService()
	{
		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		SpringContextHolder.registerJUnitBean(new ChargeRepository());
		SpringContextHolder.registerJUnitBean(new UserGroupRepository());

		final BPartnerStatisticsUpdater asyncBPartnerStatisticsUpdater = new BPartnerStatisticsUpdater();
		Services.registerService(IBPartnerStatisticsUpdater.class, asyncBPartnerStatisticsUpdater);
		SpringContextHolder.registerJUnitBean(MailService.newInstanceForUnitTesting());
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new GreetingRepository());
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
		NOPWorkpackageLogsRepository.registerToSpringContext();
		SpringContextHolder.registerJUnitBean(new DocTypeInvoicingPoolService(new DocTypeInvoicingPoolRepository()));
		SpringContextHolder.registerJUnitBean(MatchInvoiceService.newInstanceForUnitTesting());
	}

	@Override
	protected List<I_C_Invoice_Candidate> step10_createInvoiceCandidates()
	{
		final I_C_Invoice_Candidate ic1 = icTestSupport.createInvoiceCandidate()
				.setBillBPartner(bpartner1)
				.setPriceEntered(1)
				.setQtyOrdered(10)
				.setManual(true)
				.setSOTrx(true)
				.build();
		Assertions.assertThat(InterfaceWrapperHelper.load(bpartner1.getC_BPartner_ID(), I_C_BPartner.class).getName()).isNotNull();
		icTestSupport.updateInvalidCandidates();

		return ImmutableList.of(ic1);
	}
}
