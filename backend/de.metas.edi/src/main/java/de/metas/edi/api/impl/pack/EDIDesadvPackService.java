/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.edi.api.impl.pack;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.common.util.SimpleSequence;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.handlingunits.generichumodel.HU;
import de.metas.handlingunits.generichumodel.HURepository;
import de.metas.handlingunits.generichumodel.PackagingCode;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.Check.isNotBlank;
import static org.adempiere.model.InterfaceWrapperHelper.create;

@Service
public class EDIDesadvPackService
{
	private final static Logger logger = LogManager.getLogger(EDIDesadvPackService.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final ISSCC18CodeBL sscc18CodeService = Services.get(ISSCC18CodeBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

	private final HURepository huRepository;
	private final EDIDesadvPackRepository ediDesadvPackRepository;

	public EDIDesadvPackService(
			@NonNull final HURepository huRepository,
			@NonNull final EDIDesadvPackRepository ediDesadvPackRepository)
	{
		this.huRepository = huRepository;
		this.ediDesadvPackRepository = ediDesadvPackRepository;
	}

	@NonNull
	public I_M_HU_PI_Item_Product extractHUPIItemProduct(final I_C_Order order, final I_C_OrderLine orderLine)
	{
		final I_M_HU_PI_Item_Product materialItemProduct;
		if (orderLine.getM_HU_PI_Item_Product_ID() > 0)
		{
			materialItemProduct = orderLine.getM_HU_PI_Item_Product();
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
			final BPartnerId buyerBPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
			final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID()));

			materialItemProduct = hupiItemProductDAO.retrieveMaterialItemProduct(
					productId,
					buyerBPartnerId,
					TimeUtil.asZonedDateTime(order.getDateOrdered(), timeZone),
					X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, true/* allowInfiniteCapacity */);
		}

		return materialItemProduct;
	}

	public void removePackAndItemRecords(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		ediDesadvPackRepository.removePackAndItemRecords(inOutLineRecord);
	}

	public void createPacks(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final BPartnerId recipientBPartnerId,
			@NonNull final SimpleSequence packLineSequence)
	{
		final I_C_OrderLine orderLineRecord = InterfaceWrapperHelper.create(inOutLineRecord.getC_OrderLine(), I_C_OrderLine.class);

		final EDIDesadvLineId desadvLineId = EDIDesadvLineId.ofRepoIdOrNull(orderLineRecord.getEDI_DesadvLine_ID());

		if (desadvLineId == null)
		{
			logger.debug("No EDI_DesadvLine_ID set on C_OrderLine with ID={};",
						 orderLineRecord.getC_OrderLine_ID());

			return;
		}

		final I_EDI_DesadvLine desadvLineRecord = desadvDAO.retrieveLineById(desadvLineId);

		final DesadvLineWithDraftedPackItems desadvLineWithDraftedPackItems = getLineWithDraftedPackItems(desadvLineId);

		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.ofNullableCodeOrNominal(desadvLineRecord.getInvoicableQtyBasedOn());

		StockQtyAndUOMQty remainingQtyToAdd = inOutBL.extractInOutLineQty(inOutLineRecord, invoicableQtyBasedOn);
		final BigDecimal uomToStockRatio = remainingQtyToAdd.getUOMToStockRatio();
		// note that if inOutLineRecord has catch-weight, then logically we can't have HUs
		final List<I_M_HU> topLevelHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(inOutLineRecord);

		for (final I_M_HU topLevelHU : topLevelHUs)
		{
			final StockQtyAndUOMQty addedPackQty = createPackUsingHU(
					desadvLineRecord, 
					inOutLineRecord, 
					topLevelHU, 
					recipientBPartnerId, 
					desadvLineWithDraftedPackItems, 
					invoicableQtyBasedOn, 
					uomToStockRatio,
					packLineSequence);
			remainingQtyToAdd = StockQtyAndUOMQtys.subtract(remainingQtyToAdd, addedPackQty);
		}

		if (remainingQtyToAdd.getStockQty().signum() > 0)
		{
			createPackUsingJustInOutLine(
					inOutLineRecord, 
					orderLineRecord, 
					desadvLineRecord, 
					remainingQtyToAdd, 
					desadvLineWithDraftedPackItems, 
					invoicableQtyBasedOn, 
					uomToStockRatio,
					packLineSequence);
		}
	}

	private void createPackUsingJustInOutLine(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final I_C_OrderLine orderLineRecord,
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final StockQtyAndUOMQty qtyToAdd,
			@NonNull final DesadvLineWithDraftedPackItems desadvLineWithPacks,
			@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn,
			@Nullable final BigDecimal uomToStockRatio,
			@NonNull final SimpleSequence packLineSequence)
	{
		Check.assume(qtyToAdd.getStockQty().signum() > 0, "Parameter 'qtyToAdd' needs to be >0 for all this to make sense");

		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());

		final I_C_Order orderRecord = create(orderLineRecord.getC_Order(), I_C_Order.class);
		final I_M_HU_PI_Item_Product tuPIItemProduct = extractHUPIItemProduct(orderRecord, orderLineRecord);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());

		final I_M_HU_LUTU_Configuration lutuConfigurationInStockUOM = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				productId,
				qtyToAdd.getStockQty().getUomId(),
				bpartnerId,
				false/* noLUForVirtualTU */);

		final StockQtyAndUOMQty maxQtyCUsPerLU;
		final int requiredLUCount;
		final boolean configCapacityUnspecified = lutuConfigurationInStockUOM.isInfiniteQtyTU() || lutuConfigurationInStockUOM.isInfiniteQtyCU();
		if (configCapacityUnspecified)
		{
			maxQtyCUsPerLU = StockQtyAndUOMQtys.createConvert(
					qtyToAdd.getStockQty(),
					productId,
					qtyToAdd.getUOMQtyNotNull().getUomId());

			requiredLUCount = 1;
		}
		else
		{
			maxQtyCUsPerLU = StockQtyAndUOMQtys.createConvert(
					lutuConfigurationInStockUOM.getQtyCUsPerTU().multiply(lutuConfigurationInStockUOM.getQtyTU()),
					productId,
					qtyToAdd.getUOMQtyNotNull().getUomId());

			requiredLUCount = lutuConfigurationFactory.calculateQtyLUForTotalQtyCUs(
					lutuConfigurationInStockUOM,
					qtyToAdd.getUOMQtyNotNull());
		}

		final Quantity qtyCUsPerTUInStockUOM;
		if (orderLineRecord.getQtyItemCapacity().signum() > 0)
		{
			// we use the capacity which the goods were ordered in
			qtyCUsPerTUInStockUOM = Quantitys.create(orderLineRecord.getQtyItemCapacity(), qtyToAdd.getStockQty().getUomId());
		}
		else if (!lutuConfigurationInStockUOM.isInfiniteQtyCU())
		{
			// we make an educated guess, based on the packing-instruction's information
			qtyCUsPerTUInStockUOM = Quantitys.create(lutuConfigurationInStockUOM.getQtyCUsPerTU(), qtyToAdd.getStockQty().getUomId());
		}
		else
		{
			// we just don't have the info. So we assume that everything was put into one TU
			qtyCUsPerTUInStockUOM = qtyToAdd.getStockQty();
		}

		StockQtyAndUOMQty remainingQty = qtyToAdd;

		for (int i = 0; i < requiredLUCount; i++)
		{
			final CreateEDIDesadvPackRequest.CreateEDIDesadvPackRequestBuilder createEDIDesadvPackRequestBuilder = CreateEDIDesadvPackRequest.builder()
					.line(packLineSequence.next())
					.orgId(OrgId.ofRepoId(desadvLineRecord.getAD_Org_ID()))
					.ediDesadvId(EDIDesadvId.ofRepoId(desadvLineRecord.getEDI_Desadv_ID()));

			final CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest.CreateEDIDesadvPackItemRequestBuilder createEDIDesadvPackItemRequestBuilder =
					CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest.builder()
							.ediDesadvLineId(EDIDesadvLineId.ofRepoId(desadvLineRecord.getEDI_DesadvLine_ID()))
							.qtyItemCapacity(lutuConfigurationInStockUOM.getQtyCUsPerTU())
							.inOutId(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()))
							.inOutLineId(InOutLineId.ofRepoId(inOutLineRecord.getM_InOutLine_ID()));

			// BestBefore
			final Optional<Timestamp> bestBeforeDate = extractBestBeforeDate(inOutLineRecord);
			bestBeforeDate.ifPresent(createEDIDesadvPackItemRequestBuilder::bestBeforeDate);

			// Lot
			final Optional<String> lotNumber = extractLotNumber(inOutLineRecord);
			lotNumber.ifPresent(createEDIDesadvPackItemRequestBuilder::lotNumber);

			// SSCC18
			final String sscc18 = computeSSCC18(OrgId.ofRepoId(inOutLineRecord.getAD_Org_ID()));
			createEDIDesadvPackRequestBuilder.sscc18(sscc18);
			createEDIDesadvPackRequestBuilder.isManualIpaSSCC(true); // because the SSCC string is not coming from any M_HU

			// PackagingCodes and PackagingGTINs
			final int packagingCodeLU_ID = tuPIItemProduct.getM_HU_PackagingCode_LU_Fallback_ID();
			createEDIDesadvPackRequestBuilder.huPackagingCodeLUID(PackagingCodeId.ofRepoIdOrNull(packagingCodeLU_ID));
			createEDIDesadvPackRequestBuilder.gtinLUPackingMaterial(tuPIItemProduct.getGTIN_LU_PackingMaterial_Fallback());

			final int packagingCodeTU_ID = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PackagingCode_ID();
			createEDIDesadvPackItemRequestBuilder.huPackagingCodeTUID(PackagingCodeId.ofRepoIdOrNull(packagingCodeTU_ID));

			final List<I_M_HU_PackingMaterial> huPackingMaterials = packingMaterialDAO.retrievePackingMaterials(tuPIItemProduct);
			if (huPackingMaterials.size() == 1)
			{
				final I_C_BPartner_Product bPartnerProductRecord = bPartnerProductDAO
						.retrieveBPartnerProductAssociation(Env.getCtx(),
															bpartnerId,
															ProductId.ofRepoId(huPackingMaterials.get(0).getM_Product_ID()),
															OrgId.ofRepoId(desadvLineRecord.getAD_Org_ID()));
				if (bPartnerProductRecord != null && isNotBlank(bPartnerProductRecord.getGTIN()))
				{
					createEDIDesadvPackItemRequestBuilder.gtinTUPackingMaterial(bPartnerProductRecord.getGTIN());
				}
			}
			else
			{
				logger.debug("M_HU_PI_Item_Product_ID={} has {} M_HU_PackingMaterials; -> skip setting GTIN_TU_PackingMaterial to EDI_Desadv_Pack_Item! EDI_DesadvLine_ID={} ",
							 tuPIItemProduct.getM_HU_PI_Item_Product_ID(), huPackingMaterials.size(), desadvLineRecord.getEDI_DesadvLine_ID());
			}

			final StockQtyAndUOMQty qtyCUsPerCurrentLU = remainingQty.min(maxQtyCUsPerLU);

			final Quantity currentQtyTU = qtyCUsPerCurrentLU.getStockQty().divide(qtyCUsPerTUInStockUOM.toBigDecimal(), 0, RoundingMode.UP);
			createEDIDesadvPackItemRequestBuilder.qtyTu(currentQtyTU.toBigDecimal().intValue());

			final UomId packUomId = UomId.ofRepoId(desadvLineRecord.getC_UOM_ID());
			final UomId invoiceUomId = UomId.ofRepoIdOrNull(desadvLineRecord.getC_UOM_Invoice_ID());

			final BigDecimal movementQty = qtyCUsPerCurrentLU.getStockQty().toBigDecimal();

			desadvLineWithPacks.popFirstMatching(movementQty).ifPresent(createEDIDesadvPackItemRequestBuilder::ediDesadvPackItemId);

			setQty(createEDIDesadvPackItemRequestBuilder, productId, qtyCUsPerTUInStockUOM, qtyCUsPerCurrentLU, packUomId, invoiceUomId, movementQty, invoicableQtyBasedOn, uomToStockRatio);

			ediDesadvPackRepository.createDesadvPack(createEDIDesadvPackRequestBuilder
															 .createEDIDesadvPackItemRequest(createEDIDesadvPackItemRequestBuilder.build())
															 .build());

			// prepare next iteration within this for-look
			remainingQty = StockQtyAndUOMQtys.subtract(remainingQty, qtyCUsPerCurrentLU);
		}
	}

	@NonNull
	private Optional<Timestamp> extractBestBeforeDate(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inOutLineRecord.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(asiId);
		if (attributeSet.hasAttribute(AttributeConstants.ATTR_BestBeforeDate))
		{
			final Date bestBeforeDate = attributeSet.getValueAsDate(AttributeConstants.ATTR_BestBeforeDate);
			return Optional.ofNullable(TimeUtil.asTimestamp(bestBeforeDate));
		}
		return Optional.empty();
	}

	@NonNull
	private Optional<String> extractLotNumber(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inOutLineRecord.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(asiId);
		if (attributeSet.hasAttribute(AttributeConstants.ATTR_LotNumber))
		{
			final String lotNumber = attributeSet.getValueAsString(AttributeConstants.ATTR_LotNumber);
			return Optional.ofNullable(lotNumber);
		}
		return Optional.empty();
	}

	@NonNull
	private String computeSSCC18(@NonNull final OrgId orgId)
	{
		final SSCC18 sscc18 = sscc18CodeService.generate(orgId);
		return sscc18.asString();
	}

	@NonNull
	private StockQtyAndUOMQty createPackUsingHU(
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final I_M_HU huRecord,
			@NonNull final BPartnerId bPartnerId,
			@NonNull final DesadvLineWithDraftedPackItems desadvLineWithPacks,
			@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn,
			@Nullable final BigDecimal uomToStockRatio,
			@NonNull final SimpleSequence packLineSequence)
	{
		final ProductId productId = ProductId.ofRepoId(desadvLineRecord.getM_Product_ID());

		final HU rootLU = huRepository
				.getById(HuId.ofRepoId(huRecord.getM_HU_ID()))
				.retainProduct(productId) // no need to blindly hope that the HU is homogenous
				.filter(hu -> hu.getType().isLU())
				.orElse(null);

<<<<<<< HEAD
		if (rootLU == null)
=======
		// topLevelHU's quantity can be bigger than the inOutLine's quantity,
		// if there are multiple lines with the same product and if those lines were picked onto the same LU.
		// That's why we need to invoke min(..)
		final StockQtyAndUOMQty qtyCUsPerTopLevelHU = getQuantity(topLevelHU, productId)
				.min(inOutBL.extractInOutLineQty(inOutLineRecord, invoicableQtyBasedOn));

		final RequestParameters parameters = new RequestParameters(topLevelHU,
																   bPartnerId,
				qtyCUsPerTopLevelHU,
																   productId,
																   desadvLineRecord,
																   inOutLineRecord,
																   desadvLineWithPacks,
																   invoicableQtyBasedOn,
																   uomToStockRatio,
																   sequences.getPackSeqNoSequence(),
																   sequences.getPackItemLineSequence());

		final EDIDesadvPack packByHUId = ediDesadvPackRepository.getPackByDesadvLineAndHUId(topLevelHU.getId());

		if (packByHUId == null)
>>>>>>> d3cb0a51c18 (Handle desadv lines with same prod picked onto same HU (#19107))
		{
			// we don't do HU-related stuffs if the HU is not an LU.
			final UomId desadvUomId = UomId.ofRepoId(desadvLineRecord.getC_UOM_ID());
			return StockQtyAndUOMQtys.createZero(productId, desadvUomId);
		}

		final StockQtyAndUOMQty qtyCUsPerLU = getQuantity(rootLU, productId);

		final CreateEDIDesadvPackRequest createEDIDesadvPackRequest = buildCreateDesadvPackRequest(
				rootLU,
				bPartnerId,
				qtyCUsPerLU,
				productId,
				desadvLineRecord,
				inOutLineRecord,
				desadvLineWithPacks,
				invoicableQtyBasedOn,
				uomToStockRatio,
				packLineSequence);

		ediDesadvPackRepository.createDesadvPack(createEDIDesadvPackRequest);

		return qtyCUsPerLU;
	}

	@NonNull
	private StockQtyAndUOMQty getQuantity(
			@NonNull final HU rootHU,
			@NonNull final ProductId productId)
	{
		// note that rootHU only contains children, quantities and weights for productId
		final Quantity qtyInStockUOM = rootHU.getProductQtysInStockUOM().get(productId);
		final Optional<Quantity> weight = rootHU.getWeightNet();
		if (weight.isPresent())
		{
			return StockQtyAndUOMQty.builder()
					.productId(productId)
					.stockQty(qtyInStockUOM)
					.uomQty(weight.get())
					.build();
		}
		else
		{
			return StockQtyAndUOMQtys.createConvert(
					qtyInStockUOM,
					productId,
					qtyInStockUOM.getUomId()); // don't try to convert to the pack's UOM! it might be a TU-uom
		}
	}

	@NonNull
	private CreateEDIDesadvPackRequest buildCreateDesadvPackRequest(
			@NonNull final HU rootHU,
			@NonNull final BPartnerId bPartnerId,
			@NonNull final StockQtyAndUOMQty quantity,
			@NonNull final ProductId productId,
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final DesadvLineWithDraftedPackItems desadvLineWithPacks,
			@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn,
			@Nullable final BigDecimal uomToStockRatio,
			@NonNull final SimpleSequence packLineSequence)
	{
		final CreateEDIDesadvPackRequest.CreateEDIDesadvPackRequestBuilder createDesadvPackRequestBuilder = CreateEDIDesadvPackRequest.builder()
				.line(packLineSequence.next())
				.orgId(OrgId.ofRepoId(desadvLineRecord.getAD_Org_ID()))
				.ediDesadvId(EDIDesadvId.ofRepoId(desadvLineRecord.getEDI_Desadv_ID()))
				.huId(rootHU.getId());

		final Quantity qtyCUInStockUOM = rootHU.extractMedianCUQtyPerChildHU(productId);

		// get minimum best before of all HUs and sub-HUs
		final Date bestBefore = rootHU.extractSingleAttributeValue(
				attrSet -> attrSet.hasAttribute(AttributeConstants.ATTR_BestBeforeDate) ? attrSet.getValueAsDate(AttributeConstants.ATTR_BestBeforeDate) : null,
				TimeUtil::min);

		final CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest.CreateEDIDesadvPackItemRequestBuilder createEDIDesadvPackItemRequestBuilder =
				CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest.builder()
						.ediDesadvLineId(EDIDesadvLineId.ofRepoId(desadvLineRecord.getEDI_DesadvLine_ID()))
						.inOutId(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()))
						.inOutLineId(InOutLineId.ofRepoId(inOutLineRecord.getM_InOutLine_ID()))
						.qtyItemCapacity(qtyCUInStockUOM.toBigDecimal())
						.bestBeforeDate(TimeUtil.asTimestamp(bestBefore))
						.qtyTu(rootHU.getChildHUs().size());

		// Lot
		final String lotNumber = rootHU.getAttributes().getValueAsString(AttributeConstants.ATTR_LotNumber);
		if (!isEmpty(lotNumber, true))
		{
			createEDIDesadvPackItemRequestBuilder.lotNumber(lotNumber);
		}

		final UomId stockUomId = UomId.ofRepoId(desadvLineRecord.getC_UOM_ID());
		final UomId invoiceUomId = UomId.ofRepoIdOrNull(desadvLineRecord.getC_UOM_Invoice_ID());

		final BigDecimal movementQty = quantity.getStockQty().toBigDecimal();

		desadvLineWithPacks.popFirstMatching(movementQty).ifPresent(createEDIDesadvPackItemRequestBuilder::ediDesadvPackItemId);

		setQty(createEDIDesadvPackItemRequestBuilder, productId, qtyCUInStockUOM, quantity, stockUomId, invoiceUomId, movementQty, invoicableQtyBasedOn, uomToStockRatio);

		extractAndSetPackagingCodes(rootHU, createDesadvPackRequestBuilder, createEDIDesadvPackItemRequestBuilder);
		extractAndSetPackagingGTINs(rootHU, bPartnerId, createDesadvPackRequestBuilder, createEDIDesadvPackItemRequestBuilder);

		setSSCC18(rootHU, createDesadvPackRequestBuilder);

		createDesadvPackRequestBuilder.createEDIDesadvPackItemRequest(createEDIDesadvPackItemRequestBuilder.build());

		return createDesadvPackRequestBuilder.build();
	}

	private void setSSCC18(
			@NonNull final HU rootHU,
			@NonNull final CreateEDIDesadvPackRequest.CreateEDIDesadvPackRequestBuilder createEDIDesadvPackRequestBuilder)
	{
		final String sscc18Attribute = rootHU.getAttributes().getValueAsStringOrNull(HUAttributeConstants.ATTR_SSCC18_Value);
		if (Check.isNotBlank(sscc18Attribute))
		{
			createEDIDesadvPackRequestBuilder.sscc18(sscc18Attribute);
			createEDIDesadvPackRequestBuilder.isManualIpaSSCC(false);
		}
		else
		{
			// don't use M_HU_ID because multiple packs can have the same HU and each pack needs an individual SSCC
			final SSCC18 sscc18 = sscc18CodeService.generate(rootHU.getOrgId() /*, rootHU.getId().getRepoId()*/);

			createEDIDesadvPackRequestBuilder.sscc18(sscc18.asString());
			createEDIDesadvPackRequestBuilder.isManualIpaSSCC(true);
		}
	}

	@VisibleForTesting
	public void setQty(
			@NonNull final CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest.CreateEDIDesadvPackItemRequestBuilder createEDIDesadvPackItemRequestBuilder,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyCUsPerTUInStockUOM,
			@NonNull final StockQtyAndUOMQty qtyCUsPerLU,
			@NonNull final UomId packUomId,
			@Nullable final UomId invoiceUomId,
			@NonNull final BigDecimal movementQtyInStockUOM,
			@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn,
			@Nullable final BigDecimal uomToStockRatio)
	{
		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);

		final BigDecimal qtyCUPerTUinPackUOM;
		final BigDecimal qtyCUsPerLUinPackUOM;
		if (uomDAO.isUOMForTUs(packUomId))
		{
			// If the packUomId is a TU-UOM ("TU" or "COLI"), we count the CUs as such.
			// For example if there is one TU with ten CUs then we have qtyCUPerTUinPackUOM=10, even if the UOM is COLI.
			qtyCUPerTUinPackUOM = qtyCUsPerTUInStockUOM.toBigDecimal();
			qtyCUsPerLUinPackUOM = qtyCUsPerLU.getStockQty().toBigDecimal();
		}
		else
		{
			qtyCUPerTUinPackUOM = uomConversionBL
					.convertQuantityTo(
							qtyCUsPerTUInStockUOM,
							conversionCtx,
							packUomId)
					.toBigDecimal();

			qtyCUsPerLUinPackUOM = uomConversionBL
					.convertQuantityTo(
							qtyCUsPerLU.getStockQty(),
							conversionCtx,
							packUomId)
					.toBigDecimal();
		}

		final BigDecimal qtyCUPerTUinInvoiceUOM;
		final BigDecimal qtyCUPerLUinInvoiceUOM;
		if (invoicableQtyBasedOn.isCatchWeight() && !qtyCUsPerLU.getUOMQtyOpt().isPresent())
		{
			if (uomToStockRatio == null)
			{
				throw new AdempiereException("Invoicable Quantity Based on is CatchWeight, but ratio is missing!");
			}

			qtyCUPerTUinInvoiceUOM = qtyCUsPerTUInStockUOM.toBigDecimal().multiply(uomToStockRatio);
			qtyCUPerLUinInvoiceUOM = qtyCUsPerLU.getStockQty().toBigDecimal().multiply(uomToStockRatio);
		}
		else if (invoicableQtyBasedOn.isCatchWeight() && qtyCUsPerLU.getUOMQtyOpt().isPresent())
		{
			qtyCUPerTUinInvoiceUOM = qtyCUsPerTUInStockUOM.toBigDecimal().multiply(qtyCUsPerLU.getUOMToStockRatio());
			qtyCUPerLUinInvoiceUOM = qtyCUsPerLU.getUOMQtyOpt().get().toBigDecimal();
		}
		else if (invoiceUomId != null)
		{
			qtyCUPerTUinInvoiceUOM = uomConversionBL.convertQuantityTo(
							qtyCUsPerTUInStockUOM,
							conversionCtx,
							invoiceUomId)
					.toBigDecimal();
			qtyCUPerLUinInvoiceUOM = uomConversionBL.convertQuantityTo(
							qtyCUsPerLU.getStockQty(),
							conversionCtx,
							invoiceUomId)
					.toBigDecimal();
		}
		else
		{
			qtyCUPerTUinInvoiceUOM = null;
			qtyCUPerLUinInvoiceUOM = null;
		}

		createEDIDesadvPackItemRequestBuilder.movementQtyInStockUOM(movementQtyInStockUOM);
		createEDIDesadvPackItemRequestBuilder.qtyCUsPerTU(qtyCUPerTUinPackUOM);
		createEDIDesadvPackItemRequestBuilder.qtyCUPerTUinInvoiceUOM(qtyCUPerTUinInvoiceUOM);
		createEDIDesadvPackItemRequestBuilder.qtyCUsPerLU(qtyCUsPerLUinPackUOM);
		createEDIDesadvPackItemRequestBuilder.qtyCUsPerLUinInvoiceUOM(qtyCUPerLUinInvoiceUOM);
	}

	@NonNull
	private DesadvLineWithDraftedPackItems getLineWithDraftedPackItems(@NonNull final EDIDesadvLineId desadvLineId)
	{
		final EDIDesadvPackRepository.EdiDesadvPackItemQuery draftedItemsQuery = EDIDesadvPackRepository.EdiDesadvPackItemQuery
				.ofDesadvLineWithNoInOutLine(desadvLineId);

		final List<I_EDI_Desadv_Pack_Item> draftedPackItems = ediDesadvPackRepository.retrievePackItemRecords(draftedItemsQuery);

		return DesadvLineWithDraftedPackItems.builder()
				.desadvLineId(desadvLineId)
				.draftedPackItems(new LinkedList<>(draftedPackItems))
				.build();
	}

	private static void extractAndSetPackagingCodes(
			@NonNull final HU rootHU,
			@NonNull final CreateEDIDesadvPackRequest.CreateEDIDesadvPackRequestBuilder createEDIDesadvPackRequestBuilder,
			@NonNull final CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest.CreateEDIDesadvPackItemRequestBuilder createEDIDesadvPackItemRequestBuilder)
	{
		rootHU.getPackagingCode().ifPresent(code -> createEDIDesadvPackRequestBuilder.huPackagingCodeLUID(code.getId()));

		final PackagingCode tuPackagingCode = CollectionUtils.extractSingleElementOrDefault(
				rootHU.getChildHUs(), // don't iterate all HUs; we just care for the level below our LU (aka TU level).
				hu -> hu.getPackagingCode().orElse(PackagingCode.NONE), // don't use null because CollectionUtils runs with ImmutableList
				PackagingCode.NONE);

		if (!tuPackagingCode.isNone())
		{
			createEDIDesadvPackItemRequestBuilder.huPackagingCodeTUID(tuPackagingCode.getId());
		}
	}

	private static void extractAndSetPackagingGTINs(
			@NonNull final HU rootHU,
			@NonNull final BPartnerId bPartnerId,
			@NonNull final CreateEDIDesadvPackRequest.CreateEDIDesadvPackRequestBuilder createEDIDesadvPackRequestBuilder,
			@NonNull final CreateEDIDesadvPackRequest.CreateEDIDesadvPackItemRequest.CreateEDIDesadvPackItemRequestBuilder createEDIDesadvPackItemRequestBuilder)
	{
		final String packagingGTIN = rootHU.getPackagingGTINs().get(bPartnerId);
		if (isNotBlank(packagingGTIN))
		{
			createEDIDesadvPackRequestBuilder.gtinLUPackingMaterial(packagingGTIN);
		}

		final String tuPackagingGTIN = CollectionUtils.extractSingleElementOrDefault(
				rootHU.getChildHUs(), // don't iterate all HUs; we just care for the level below our LU (aka TU level).
				hu -> coalesce(hu.getPackagingGTINs().get(bPartnerId), ""),
				"");

		if (isNotBlank(tuPackagingGTIN))
		{
			createEDIDesadvPackItemRequestBuilder.gtinTUPackingMaterial(tuPackagingGTIN);
		}
	}
}
