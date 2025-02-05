package de.metas.handlingunits.empties.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.empties.EmptiesMovementProducer;
import de.metas.handlingunits.empties.EmptiesMovementProducer.EmptiesMovementDirection;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.inout.returns.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.material.MovementType;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkLine;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.compiere.util.Env.getCtx;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUEmptiesService implements IHUEmptiesService
{
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private WarehouseId getEmptiesWarehouse(@NonNull final I_M_Warehouse warehouse)
	{
		// services
		final DistributionNetworkRepository distributionNetworkRepository = SpringContextHolder.instance.getBean(DistributionNetworkRepository.class);

		// In case the requirements will change and the empties distribution network
		// will be product based, here we will need to get the product gebinde
		// and send it as parameter in the method above
		final DistributionNetwork emptiesDistributionNetwork = distributionNetworkRepository.getEmptiesDistributionNetwork();

		final List<DistributionNetworkLine> lines = emptiesDistributionNetwork.getLinesBySourceWarehouse(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));

		if (lines.isEmpty())
		{   // we did find the empties distribution network, but it contained no line to tell us what the given 'warehouse's empty-warehouse is.
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@ (@IsHUDestroyed@=@Y@): " + warehouse.getName()
					+ "\n @DD_NetworkDistribution_ID@: " + emptiesDistributionNetwork.getName());
		}

		return lines.get(0).getTargetWarehouseId();
	}

	@Override
	public I_M_Locator getEmptiesLocator(final I_M_Warehouse warehouse)
	{
		final WarehouseId emptiesWarehouseId = getEmptiesWarehouse(warehouse);
		return InterfaceWrapperHelper.create(Services.get(IWarehouseBL.class).getOrCreateDefaultLocator(emptiesWarehouseId), I_M_Locator.class);
	}

	@Override
	public EmptiesMovementProducer newEmptiesMovementProducer()
	{
		return EmptiesMovementProducer.newInstance();
	}

	@Override
	public void generateMovementFromEmptiesInout(@NonNull final I_M_InOut emptiesInOut)
	{
		//
		// Fetch shipment/receipt lines and convert them to packing material line candidates.
		final List<HUPackingMaterialDocumentLineCandidate> lines = Services.get(IInOutDAO.class).retrieveLines(emptiesInOut, I_M_InOutLine.class)
				.stream()
				.map(line -> HUPackingMaterialDocumentLineCandidate.of(
						loadOutOfTrx(line.getM_Locator_ID(), I_M_Locator.class),
						loadOutOfTrx(line.getM_Product_ID(), I_M_Product.class),
						line.getMovementQty().intValueExact()))
				.collect(GuavaCollectors.toImmutableList());

		// TODO 1/3 verify that we need to know this
		final boolean emptiesIncoming = !MovementType.ofCode(emptiesInOut.getMovementType()).isOutboundTransaction();

		//
		// Generate the empties movement
		newEmptiesMovementProducer()
				// TODO 2/3 this line seems to me to be at odds with the interface-method's javadoc. If isSOTrx=Y then we generally think of an outgoing empties-InOut, don't we? So the movement-direction should be *from* the emptiesWarehouse..
				.setEmptiesMovementDirection(emptiesInOut.isSOTrx() ? EmptiesMovementDirection.ToEmptiesWarehouse : EmptiesMovementDirection.FromEmptiesWarehouse)
				// TODO 3/3 ..so it should rather be as follows, right?
				// .setEmptiesMovementDirection(emptiesIncoming ? EmptiesMovementDirection.ToEmptiesWarehouse : EmptiesMovementDirection.FromEmptiesWarehouse)
				.setReferencedInOutId(emptiesInOut.getM_InOut_ID())
				.addCandidates(lines)
				.createMovements();
	}

	@Override
	public boolean isEmptiesInOut(@NonNull final I_M_InOut inout)
	{
		final I_C_DocType docType = loadOutOfTrx(inout.getC_DocType_ID(), I_C_DocType.class);
		if (docType == null || docType.getC_DocType_ID() <= 0)
		{
			return false;
		}

		final String docSubType = docType.getDocSubType();
		return X_C_DocType.DOCSUBTYPE_Leergutanlieferung.equals(docSubType)
				|| X_C_DocType.DOCSUBTYPE_Leergutausgabe.equals(docSubType);
	}

	@Override
	public IReturnsInOutProducer newReturnsInOutProducer(final Properties ctx)
	{
		return new EmptiesInOutProducer(ctx);
	}

	@Override
	@Nullable
	public I_M_InOut createDraftEmptiesInOutFromReceiptSchedule(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final String movementType)
	{
		//
		// Create a draft "empties inout" without any line;
		// Lines will be created manually by the user.
		return  newReturnsInOutProducer(getCtx())
				.setMovementType(movementType)
				.setMovementDate(SystemTime.asDayTimestamp())
				.setC_BPartner(receiptScheduleBL.getC_BPartner_Effective(receiptSchedule))
				.setC_BPartner_Location(receiptScheduleBL.getC_BPartner_Location_Effective(receiptSchedule))
				.setM_Warehouse(receiptScheduleBL.getM_Warehouse_Effective(receiptSchedule))
				.setC_Order(receiptSchedule.getC_Order())
				//
				.dontComplete()
				.create();
	}
}
