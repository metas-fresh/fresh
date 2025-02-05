package de.metas.handlingunits;

import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.currency.CurrencyRepository;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.InOutLineDimensionFactory;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.document.references.zoom_into.NullCustomizedWindowInfoMapRepository;
import de.metas.email.MailService;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesRepository;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesService;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleUpdater;
import de.metas.inoutcandidate.document.dimension.ReceiptScheduleDimensionFactoryTestWrapper;
import de.metas.money.MoneyService;
import de.metas.notification.INotificationRepository;
import de.metas.notification.impl.NotificationRepository;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.product.ProductId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.test.ErrorMessage;
import org.adempiere.warehouse.LocatorId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@ExtendWith(AdempiereTestWatcher.class)
public abstract class AbstractHUTest
{
	protected I_C_UOM uomEach;
	protected I_C_UOM uomKg;

	public LocatorId defaultLocatorId;

	/**
	 * Value: Pallete
	 */
	protected I_M_Product pPallets; // Pallets are our packing material
	protected I_M_HU_PackingMaterial pmPallets; // Pallets are our packing material

	/**
	 * Value: IFCO
	 */
	protected I_M_Product pIFCO; // IFCOs are another kind of packing material
	protected I_M_HU_PackingMaterial pmIFCO; // IFCO Packing Material

	/**
	 * Value: Bag
	 */
	protected I_M_Product pBag; // Bags are another kind of packing material
	protected I_M_HU_PackingMaterial pmBag; // Bag Packing Material

	/**
	 * Value: Paloxe
	 */
	protected I_M_Product pPaloxe; // Paloxes are another kind of packing material
	protected I_M_HU_PackingMaterial pmPaloxe; // Paloxe Packing Material

	/**
	 * Value: Tomato
	 */
	protected I_M_Product pTomato;
	protected ProductId pTomatoId;
	protected I_M_Product pSalad;
	protected ProductId pSaladId;

	protected I_M_Attribute attr_CountryMadeIn;
	protected I_M_Attribute attr_Volume;
	protected I_M_Attribute attr_FragileSticker;

	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_WeightGross}
	 */
	protected I_M_Attribute attr_WeightGross;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_WeightNet}
	 */
	protected I_M_Attribute attr_WeightNet;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_WeightTare}
	 */
	protected I_M_Attribute attr_WeightTare;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_WeightTareAdjust}
	 */
	protected I_M_Attribute attr_WeightTareAdjust;

	protected I_M_Attribute attr_AttributeNotFound;

	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_QualityDiscountPercent}
	 */
	protected I_M_Attribute attr_QualityDiscountPercent;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_QualityNotice}
	 */
	protected I_M_Attribute attr_QualityNotice;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_SubProducerBPartner}
	 */
	protected I_M_Attribute attr_SubProducerBPartner;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_LotNumberDate}
	 */
	protected I_M_Attribute attr_LotNumberDate;

	// #653
	protected I_M_Attribute attr_LotNumber;

	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(false);
	}

	/** HU Test helper */
	public HUTestHelper helper;

	@BeforeEach
	public final void init()
	{

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new OrderLineDimensionFactory());
		dimensionFactories.add(new ReceiptScheduleDimensionFactoryTestWrapper());
		dimensionFactories.add(new InOutLineDimensionFactory());

		SpringContextHolder.registerJUnitBean(new DimensionService(dimensionFactories));

		setupMasterData();

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(MailService.newInstanceForUnitTesting());

		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

		Services.registerService(INotificationRepository.class, new NotificationRepository(attachmentEntryService, NullCustomizedWindowInfoMapRepository.instance));

		Services.registerService(IShipmentScheduleUpdater.class, ShipmentScheduleUpdater.newInstanceForUnitTesting());

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));

		final HUUniqueAttributesRepository huUniqueAttributeRepo = new HUUniqueAttributesRepository();
		SpringContextHolder.registerJUnitBean(new HUUniqueAttributesService(huUniqueAttributeRepo));

		final MoneyService moneyService = new MoneyService(new CurrencyRepository());
		SpringContextHolder.registerJUnitBean(moneyService);

		initialize();
	}

	/**
	 * Balled by the test's {@link Before} method, after the basic master data was set up.
	 */
	abstract protected void initialize();

	protected HUTestHelper createHUTestHelper()
	{
		return new HUTestHelper();
	}

	protected void setupMasterData()
	{
		helper = createHUTestHelper();

		defaultLocatorId = createLocatorId();

		attr_CountryMadeIn = helper.attr_CountryMadeIn;
		attr_Volume = helper.attr_Volume;
		attr_FragileSticker = helper.attr_FragileSticker;

		attr_WeightGross = helper.attr_WeightGross;
		attr_WeightNet = helper.attr_WeightNet;
		attr_WeightTare = helper.attr_WeightTare;
		attr_WeightTareAdjust = helper.attr_WeightTareAdjust;

		attr_QualityDiscountPercent = helper.attr_QualityDiscountPercent;
		attr_QualityNotice = helper.attr_QualityNotice;
		attr_SubProducerBPartner = helper.attr_SubProducerBPartner;

		attr_LotNumberDate = helper.attr_LotNumberDate;

		// #654
		attr_LotNumber = helper.attr_LotNumber;

		uomEach = helper.uomEach;
		uomKg = helper.uomKg;

		pPallets = helper.pPalet;
		pmPallets = helper.pmPalet;

		pIFCO = helper.pIFCO;
		pmIFCO = helper.pmIFCO;

		pPaloxe = helper.pPaloxe;
		pmPaloxe = helper.pmPaloxe;

		pBag = helper.pBag;
		pmBag = helper.pmBag;

		pTomato = helper.pTomato;
		pTomatoId = ProductId.ofRepoId(pTomato.getM_Product_ID());
		pSalad = helper.pSalad;
		pSaladId = ProductId.ofRepoId(pSalad.getM_Product_ID());
	}

	private LocatorId createLocatorId()
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		saveRecord(warehouse);

		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		saveRecord(locator);

		return LocatorId.ofRecord(locator);
	}

	protected ErrorMessage newErrorMessage()
	{
		return ErrorMessage.newInstance();
	}
}
