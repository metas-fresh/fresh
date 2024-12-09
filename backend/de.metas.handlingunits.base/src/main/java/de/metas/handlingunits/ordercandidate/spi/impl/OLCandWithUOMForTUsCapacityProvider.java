package de.metas.handlingunits.ordercandidate.spi.impl;

import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OLCandHUPackingAware;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
<<<<<<< HEAD
import de.metas.i18n.ITranslatableString;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandWithUOMForTUsCapacityProvider;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
<<<<<<< HEAD
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

=======
import org.springframework.stereotype.Component;

import java.util.Optional;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import static org.adempiere.model.InterfaceWrapperHelper.isNull;

@Component
public class OLCandWithUOMForTUsCapacityProvider implements IOLCandWithUOMForTUsCapacityProvider
{
	private static final AdMessageKey MSG_TU_UOM_CAPACITY_REQUIRED = AdMessageKey.of("de.metas.handlingunits.ordercandidate.UOMForTUsCapacityRequired");

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

	@Override
	public boolean isProviderNeededForOLCand(@NonNull final I_C_OLCand olCand)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(olCand.getM_Product_ID());
		if (productId == null)
		{
			return false; // nothing to do
		}
		if (!isNull(olCand, I_C_OLCand.COLUMNNAME_QtyItemCapacityInternal))
		{
			return false; // already set; nothing to do
		}

		final UomId uomId = olCandEffectiveValuesBL.getEffectiveUomId(olCand);

		return uomDAO.isUOMForTUs(uomId);
	}

	@NonNull
	@Override
<<<<<<< HEAD
	public Quantity computeQtyItemCapacity(@NonNull final I_C_OLCand olCand)
=======
	public Optional<Quantity> computeQtyItemCapacity(@NonNull final I_C_OLCand olCand)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final OLCandHUPackingAware huPackingAware = new OLCandHUPackingAware(olCand);
		final Capacity capacity = huPackingAwareBL.calculateCapacity(huPackingAware);
		if (capacity == null)
		{
<<<<<<< HEAD
			final ITranslatableString msg = msgBL
					.getTranslatableMsgText(MSG_TU_UOM_CAPACITY_REQUIRED,
							uomDAO.getX12DE355ById(extractUomId(olCand)),
							olCand.getC_OLCand_ID());
			throw new AdempiereException(msg).markAsUserValidationError();
=======
			return Optional.empty();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		final ProductId productId = ProductId.ofRepoId(olCand.getM_Product_ID());
		// note that the product's stocking UOM is never a TU-UOM
		if (capacity.isInfiniteCapacity())
		{
<<<<<<< HEAD
			return Quantity.infinite(capacity.getC_UOM());
		}
		return uomConversionBL.convertToProductUOM(capacity.toQuantity(), productId);
=======
			return Optional.of(Quantity.infinite(capacity.getC_UOM()));
		}
		return Optional.of(uomConversionBL.convertToProductUOM(capacity.toQuantity(), productId));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private UomId extractUomId(@NonNull final I_C_OLCand olCand)
	{
		return UomId.ofRepoIdOrNull(olCand.getC_UOM_ID());
	}
}
