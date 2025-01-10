package de.metas.invoice.export;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentEntryService.AttachmentEntryQuery;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.invoice_gateway.spi.esr.ESRPaymentInfoProvider;
import de.metas.invoice_gateway.spi.esr.model.ESRPaymentInfo;
import de.metas.invoice_gateway.spi.model.BPartner;
import de.metas.invoice_gateway.spi.model.GLN;
import de.metas.invoice_gateway.spi.model.InvoiceAttachment;
import de.metas.invoice_gateway.spi.model.InvoiceLine;
import de.metas.invoice_gateway.spi.model.InvoiceTax;
import de.metas.invoice_gateway.spi.model.MetasfreshVersion;
import de.metas.invoice_gateway.spi.model.Money;
import de.metas.invoice_gateway.spi.model.ProductId;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.lang.ExternalIdsUtil;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.util.Check;
import de.metas.util.Check.ExceptionWithOwnHeaderMessage;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.SoftwareVersion;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceTax;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;

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

@Service
public class InvoiceToExportFactory
{
	private static final Logger logger = LogManager.getLogger(InvoiceToExportFactory.class);

	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final AttachmentEntryService attachmentEntryService;
	private final ESRPaymentInfoProvider esrPaymentInfoProvider;
	private final CurrencyRepository currenciesRepo;

	public InvoiceToExportFactory(
			@NonNull final AttachmentEntryService attachmentEntryservice,
			@NonNull final Optional<ESRPaymentInfoProvider> esrPaymentInfoProvider,
			@NonNull final CurrencyRepository currenciesRepo)
	{
		this.attachmentEntryService = attachmentEntryservice;
		this.esrPaymentInfoProvider = esrPaymentInfoProvider.orElse(null);
		this.currenciesRepo = currenciesRepo;
	}

	public Optional<InvoiceToExport> getCreateForId(@NonNull final InvoiceId id)
	{
		try
		{
			return Optional.of(getCreateForId0(id));
		}
		catch (final InvoiceToExportFactoryException e)
		{
			Loggables.addLog("InvoiceToExportFactory - unable to export InvoiceId={}: Message={}", id, e.getMessage());
			throw e.setParameter("C_Invoice_ID", id.getRepoId());
		}
	}

	public InvoiceToExport getCreateForId0(@NonNull final InvoiceId id)
	{
		final I_C_Invoice invoiceRecord = load(id, I_C_Invoice.class);

		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final boolean reversal = invoiceBL.isReversal(invoiceRecord);

		final CurrencyCode currencyCode = extractCurrencyCode(invoiceRecord);
		final Money grandTotal = Money.of(invoiceRecord.getGrandTotal(), currencyCode.toThreeLetterCode());

		final BigDecimal allocatedAmt = CoalesceUtil.coalesce(allocationDAO.retrieveAllocatedAmt(invoiceRecord), ZERO);
		final Money allocatedMoney = Money.of(allocatedAmt, currencyCode.toThreeLetterCode());

		final DocBaseAndSubType docBaseAndSubType = Services.get(IDocTypeDAO.class).getDocBaseAndSubTypeById(DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID()));

		final InvoiceToExport invoiceWithoutEsrInfo = InvoiceToExport
				.builder()
				.id(id)
				.docSubType(docBaseAndSubType.getDocSubType()) // might be null
				.alreadyPaidAmount(allocatedMoney)
				.amount(grandTotal)
				.biller(createBiller(invoiceRecord))
				.documentNumber(invoiceRecord.getDocumentNo())
				.invoiceAttachments(createInvoiceAttachments(invoiceRecord))
				.invoiceDate(createInvoiceDate(invoiceRecord))
				.invoiceLines(createInvoiceLines(invoiceRecord, currencyCode))
				.invoiceTaxes(createInvoiceTax(invoiceRecord))
				.invoiceTimestamp(invoiceRecord.getCreated().toInstant())
				.isReversal(reversal)
				.metasfreshVersion(createMetasfreshVersion())
				.recipient(createRecipient(invoiceRecord))
				.build();

		return addCustomInvoicePayload(invoiceWithoutEsrInfo);
	}

	private CurrencyCode extractCurrencyCode(final I_C_Invoice invoiceRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID());
		return currenciesRepo.getCurrencyCodeById(currencyId);
	}

	private InvoiceToExport addCustomInvoicePayload(@NonNull final InvoiceToExport invoiceWithoutEsrInfo)
	{
		if (esrPaymentInfoProvider == null)
		{
			return invoiceWithoutEsrInfo;
		}

		final ESRPaymentInfo esrPaymentInfo = esrPaymentInfoProvider.provideCustomPayload(invoiceWithoutEsrInfo);
		return invoiceWithoutEsrInfo
				.toBuilder()
				.customInvoicePayload(esrPaymentInfo) // might be null which is OK
				.build();
	}

	private ImmutableList<InvoiceLine> createInvoiceLines(
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final CurrencyCode currentyCode)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final ImmutableList.Builder<InvoiceLine> invoiceLines = ImmutableList.builder();
		final List<I_C_InvoiceLine> invoiceLineRecords = invoiceDAO.retrieveLines(invoiceRecord);

		for (final I_C_InvoiceLine lineRecord : invoiceLineRecords)
		{
			final InvoiceLine invoiceLine = InvoiceLine
					.builder()
					.lineAmount(Money.of(lineRecord.getLineNetAmt(), currentyCode.toThreeLetterCode()))
					.productId(ProductId.ofId(lineRecord.getM_Product_ID()))
					.externalIds(ExternalIdsUtil.splitExternalIds(lineRecord.getExternalIds()))
					.build();
			invoiceLines.add(invoiceLine);
		}

		return invoiceLines.build();
	}

	private ImmutableList<InvoiceTax> createInvoiceTax(final I_C_Invoice invoiceRecord)
	{
		final ImmutableList.Builder<InvoiceTax> invoiceTaxes = ImmutableList.builder();
		final List<I_C_InvoiceTax> invoiceTaxRecords = Services.get(IInvoiceDAO.class).retrieveTaxes(invoiceRecord);

		for (final I_C_InvoiceTax invoiceTaxRecord : invoiceTaxRecords)
		{
			final Tax tax = taxDAO.getTaxById(invoiceTaxRecord.getC_Tax_ID());
			final InvoiceTax invoiceTax = InvoiceTax.builder()
					.baseAmount(invoiceTaxRecord.getTaxBaseAmt())
					.ratePercent(tax.getRate())
					.vatAmount(invoiceTaxRecord.getTaxAmt())
					.build();
			invoiceTaxes.add(invoiceTax);
		}
		return invoiceTaxes.build();
	}

	private GregorianCalendar createInvoiceDate(@NonNull final I_C_Invoice invoiceRecord)
	{
		final GregorianCalendar invoiceDate = new GregorianCalendar();
		invoiceDate.setTime(invoiceRecord.getDateInvoiced());
		return invoiceDate;
	}

	private ImmutableList<InvoiceAttachment> createInvoiceAttachments(@NonNull final I_C_Invoice invoiceRecord)
	{
		final AttachmentEntryQuery query = AttachmentEntryQuery
				.builder()
				.referencedRecord(invoiceRecord)
				.tagSetToAnyValue(InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER)
				.build();
		final List<AttachmentEntry> attachments = attachmentEntryService.getByQuery(query);

		final ImmutableList.Builder<InvoiceAttachment> invoiceAttachments = ImmutableList.builder();
		for (final AttachmentEntry attachment : attachments)
		{
			final byte[] attachmentData = attachmentEntryService.retrieveData(attachment.getId());

			final InvoiceAttachment invoiceAttachment = InvoiceAttachment.builder()
					.fileName(attachment.getFilename())
					.mimeType(attachment.getMimeType())
					.data(attachmentData)
					.tags(attachment.getTags().toMap())
					.build();
			invoiceAttachments.add(invoiceAttachment);
		}
		return invoiceAttachments.build();

	}

	private MetasfreshVersion createMetasfreshVersion()
	{
		final SoftwareVersion metasfreshBuildVersion = Adempiere.getBuildVersion();
		final MetasfreshVersion metasfreshVersion = MetasfreshVersion.builder()
				.major(metasfreshBuildVersion.getMajor())
				.minor(metasfreshBuildVersion.getMinor())
				.fullVersion(Adempiere.getBuildAndDateVersion())
				.build();
		return metasfreshVersion;
	}

	private BPartner createRecipient(@NonNull final I_C_Invoice invoiceRecord)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner_Location bPartnerLocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(invoiceRecord.getC_BPartner_ID(), invoiceRecord.getC_BPartner_Location_ID()));

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID());

		final GLN gln; // may be null, but that's the specific ExportClient's business to check
		if (Check.isBlank(bPartnerLocationRecord.getGLN()))
		{
			logger.debug("The given invoice's C_BPartner_Location has no GLN; -> unable to set a recipient GLN; invoiceRecord={}; bpartnerLocation={}",
					invoiceRecord, bPartnerLocationRecord);
			gln = null;
		}
		else
		{
			gln = GLN.of(bPartnerLocationRecord.getGLN());
		}

		final BPartner recipient = BPartner.builder()
				.id(bPartnerId)
				.gln(gln).build();
		return recipient;
	}

	private BPartner createBiller(@NonNull final I_C_Invoice invoiceRecord)
	{
		final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final I_C_BPartner orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(invoiceRecord.getAD_Org_ID());

		Check.assumeNotNull(orgBPartner, InvoiceToExportFactoryException.class,
				"The given invoice's org needs to have a linked bPartner; AD_Org_ID={}; invoiceRecord={};", invoiceRecord.getAD_Org_ID(), invoiceRecord);

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final BPartnerLocationQuery query = BPartnerLocationQuery
				.builder()
				.type(Type.REMIT_TO)
				.bpartnerId(de.metas.bpartner.BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID()))
				.build();
		final I_C_BPartner_Location remittoLocation = bPartnerDAO.retrieveBPartnerLocation(query);

		final GLN gln;
		if (remittoLocation == null)
		{
			logger.debug("The given invoice's orgBPartner has no remit-to location; -> unable to set a biller GLN; orgBPartner={}; invoiceRecord={}",
					orgBPartner, invoiceRecord);
			gln = null;
		}
		else if (Check.isBlank(remittoLocation.getGLN()))
		{
			logger.debug("The given invoice's orgBPartner's remit-to location has no GLN; -> unable to set a biller GLN; orgBPartner={}; invoiceRecord={}; remittoLocation={}",
					orgBPartner, invoiceRecord, remittoLocation);
			gln = null;
		}
		else
		{
			gln = GLN.of(remittoLocation.getGLN());
		}

		final BPartner biller = BPartner.builder()
				.id(BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID()))
				.gln(gln).build();
		return biller;
	}

	public static final class InvoiceToExportFactoryException extends AdempiereException implements ExceptionWithOwnHeaderMessage
	{
		private static final long serialVersionUID = 5678496542883367180L;

		public InvoiceToExportFactoryException(@NonNull final String msg)
		{
			super(msg);
			this.markAsUserValidationError(); // propagate error to the user
		}
	}
}
