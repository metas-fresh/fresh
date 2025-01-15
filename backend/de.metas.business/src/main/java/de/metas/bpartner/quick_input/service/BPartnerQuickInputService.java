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

package de.metas.bpartner.quick_input.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.attributes.BPartnerAttributes;
import de.metas.bpartner.attributes.related.service.BpartnerRelatedRecordsRepository;
import de.metas.bpartner.attributes.service.BPartnerAttributesRepository;
import de.metas.bpartner.attributes.service.BPartnerContactAttributesRepository;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.name.NameAndGreeting;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategies;
import de.metas.bpartner.name.strategy.ComputeNameAndGreetingRequest;
import de.metas.bpartner.quick_input.BPartnerContactQuickInputId;
import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.NewRecordContext;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.greeting.GreetingId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.marketing.base.model.CampaignId;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.exceptions.PriceListNotFoundException;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.PriceListsCollection;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.api.RequestCandidate;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserGroupUserAssignment;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Contact_QuickInput;
import org.compiere.model.I_C_BPartner_Location_QuickInput;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_R_Request;
import org.compiere.model.X_R_Request;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BPartnerQuickInputService
{
	private static final Logger logger = LogManager.getLogger(BPartnerQuickInputService.class);

	private final BPartnerQuickInputRepository bpartnerQuickInputRepository;
	private final BPartnerQuickInputAttributesRepository bpartnerQuickInputAttributesRepository;
	private final BPartnerQuickInputRelatedRecordsRepository bpartnerQuickInputRelatedRecordsRepository;
	private final BPartnerContactQuickInputAttributesRepository bpartnerContactQuickInputAttributesRepository;
	private final BPartnerNameAndGreetingStrategies bpartnerNameAndGreetingStrategies;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;
	private final BPartnerAttributesRepository bpartnerAttributesRepository;
	private final BpartnerRelatedRecordsRepository bpartnerRelatedRecordsRepository;
	private final BPartnerContactAttributesRepository bpartnerContactAttributesRepository;
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;
	private final IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);
	private final IRequestDAO requestDAO = Services.get(IRequestDAO.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final UserGroupRepository userGroupRepository;

	private static final ModelDynAttributeAccessor<I_C_BPartner_QuickInput, Boolean>
			DYNATTR_UPDATING_NAME_AND_GREETING = new ModelDynAttributeAccessor<>("UPDATING_NAME_AND_GREETING", Boolean.class);

	private final AdMessageKey MSG_C_BPartnerCreatedFromAnotherOrg = AdMessageKey.of("C_BPartnerCreatedFromAnotherOrg");

	public Optional<AdWindowId> getNewBPartnerWindowId()
	{
		return RecordWindowFinder.newInstance(I_C_BPartner_QuickInput.Table_Name, customizedWindowInfoMapRepository)
				.ignoreExcludeFromZoomTargetsFlag()
				.findAdWindowId();
	}

	public void updateNameAndGreeting(@NonNull final BPartnerQuickInputId bpartnerQuickInputId)
	{
		final I_C_BPartner_QuickInput bpartner = bpartnerQuickInputRepository.getById(bpartnerQuickInputId);
		final boolean doSave = true;
		updateNameAndGreeting(bpartner, doSave);
	}

	public void updateNameAndGreetingNoSave(@NonNull final I_C_BPartner_QuickInput bpartner)
	{
		final boolean doSave = false;
		updateNameAndGreeting(bpartner, doSave);
	}

	private void updateNameAndGreeting(
			@NonNull final I_C_BPartner_QuickInput bpartner,
			final boolean doSave)
	{
		if (DYNATTR_UPDATING_NAME_AND_GREETING.is(bpartner, true))
		{
			return;
		}

		computeBPartnerNameAndGreeting(bpartner)
				.ifPresent(nameAndGreeting -> {
					bpartner.setBPartnerName(nameAndGreeting.getName());
					bpartner.setC_Greeting_ID(GreetingId.toRepoId(nameAndGreeting.getGreetingId()));
					if (doSave)
					{
						try (final IAutoCloseable ignored = DYNATTR_UPDATING_NAME_AND_GREETING.temporarySetValue(bpartner, true))
						{
							bpartnerQuickInputRepository.save(bpartner);
						}
					}
				})
				.ifAbsent(reason -> logger.debug("Skip updating {} because: {}", bpartner, reason.getDefaultValue()));
	}

	public ExplainedOptional<NameAndGreeting> computeBPartnerNameAndGreeting(final I_C_BPartner_QuickInput bpartner)
	{
		if (bpartner.isCompany())
		{
			final String companyname = bpartner.getCompanyname();
			if (companyname == null || Check.isBlank(companyname))
			{
				return ExplainedOptional.emptyBecause("Companyname is not set");
			}
			else
			{
				return ExplainedOptional.of(NameAndGreeting.builder()
						.name(companyname)
						.greetingId(GreetingId.ofRepoIdOrNull(bpartner.getC_Greeting_ID())) // preserve current greeting
						.build());
			}
		}
		else
		{
			final BPGroupId bpGroupId = BPGroupId.ofRepoIdOrNull(bpartner.getC_BP_Group_ID());
			if (bpGroupId == null)
			{
				return ExplainedOptional.emptyBecause("C_BP_Group_ID was not set");
			}

			final List<I_C_BPartner_Contact_QuickInput> contacts = bpartnerQuickInputRepository.retrieveContactsByQuickInputId(extractBpartnerQuickInputId(bpartner));
			if (contacts.isEmpty())
			{
				return ExplainedOptional.emptyBecause("no contacts");
			}

			return bpartnerNameAndGreetingStrategies.compute(
					bpGroupDAO.getBPartnerNameAndGreetingStrategyId(bpGroupId),
					toComputeNameAndGreetingRequest(contacts, bpartner.getAD_Language()));
		}
	}

	private static ComputeNameAndGreetingRequest toComputeNameAndGreetingRequest(
			@NonNull final List<I_C_BPartner_Contact_QuickInput> contacts,
			@Nullable final String adLanguage)
	{
		final ComputeNameAndGreetingRequest.ComputeNameAndGreetingRequestBuilder requestBuilder = ComputeNameAndGreetingRequest.builder()
				.adLanguage(adLanguage);

		for (int i = 0; i < contacts.size(); i++)
		{
			final I_C_BPartner_Contact_QuickInput contact = contacts.get(i);
			requestBuilder.contact(
					ComputeNameAndGreetingRequest.Contact.builder()
							.greetingId(GreetingId.ofRepoIdOrNull(contact.getC_Greeting_ID()))
							.firstName(contact.getFirstname())
							.lastName(contact.getLastname())
							.seqNo(i + 1)
							.isDefaultContact(i == 0)
							.isMembershipContact(contact.isMembershipContact())
							.build());
		}

		return requestBuilder.build();
	}

	/**
	 * Creates BPartner, Location and contacts from given template.
	 * <p>
	 * Task https://github.com/metasfresh/metasfresh/issues/1090
	 */
	public BPartnerId createBPartnerFromTemplate(@NonNull final I_C_BPartner_QuickInput template,
												 @NonNull final NewRecordContext newRecordContext)
	{
		Check.assume(!template.isProcessed(), "{} not already processed", template);

		final BooleanWithReason canCreateNewBPartner = Env.getUserRolePermissions()
				.checkCanCreateNewRecord(
						ClientId.ofRepoId(template.getAD_Client_ID()),
						OrgId.ofRepoId(template.getAD_Org_ID()),
						adTableDAO.retrieveAdTableId(I_C_BPartner.Table_Name));
		if (canCreateNewBPartner.isFalse())
		{
			throw new AdempiereException(canCreateNewBPartner.getReason());
		}

		trxManager.assertThreadInheritedTrxExists();

		final BPartnerComposite bpartnerComposite = toBPartnerComposite(template);
		bpartnerCompositeRepository.save(bpartnerComposite, true);

		//
		// Update the location of all contacts
		final BPartnerLocationId bpartnerLocationId = bpartnerComposite.getLocations().get(0).getId();
		bpartnerComposite
				.getContacts()
				.forEach(contact -> contact.setBPartnerLocationId(bpartnerLocationId));
		bpartnerCompositeRepository.save(bpartnerComposite, true);
		final BPartnerId bpartnerId = bpartnerComposite.getBpartner().getId();

		createRequestAndNotifyUserGroupIfNeeded(bpartnerComposite,
				newRecordContext);

		//
		// Copy BPartner Attributes
		bpartnerAttributesRepository.saveAttributes(
				bpartnerQuickInputAttributesRepository.getByBPartnerQuickInputId(extractBpartnerQuickInputId(template)),
				bpartnerId);

		//
		// Copy BPartner Related records
		bpartnerRelatedRecordsRepository.saveRelatedRecords(
				bpartnerQuickInputRelatedRecordsRepository.getByBPartnerQuickInputId(extractBpartnerQuickInputId(template)),
				bpartnerId);

		//
		// Copy Contact attributes
		for (final BPartnerContact contact : bpartnerComposite.getContacts())
		{
			final BPartnerContactQuickInputId bpartnerContactQuickInputId = TransientIdConverter.fromTransientId(contact.getTransientId());
			final BPartnerAttributes contactAttributes = bpartnerContactQuickInputAttributesRepository.getByBPartnerContactQuickInputId(bpartnerContactQuickInputId);
			bpartnerContactAttributesRepository.saveAttributes(contactAttributes, contact.getId());
		}

		//
		// Update the template and mark it as processed
		template.setC_BPartner_ID(bpartnerId.getRepoId());
		template.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
		template.setProcessed(true);
		bpartnerQuickInputRepository.save(template);

		//
		return bpartnerId;
	}

	private void createRequestAndNotifyUserGroupIfNeeded(final BPartnerComposite bpartnerComposite,
														 final @NonNull NewRecordContext newRecordContext)
	{
		final OrgId partnerOrgId = bpartnerComposite.getOrgId();

		final OrgId loginOrgId = newRecordContext.getLoginOrgId();

		final UserId loggedUserId = newRecordContext.getLoggedUserId();

		final String loginLanguage = newRecordContext.getLoginLanguage();

		if (loginOrgId.equals(partnerOrgId))
		{
			//nothing to do
			return;
		}

		final String loginUserName = userDAO.retrieveUserFullName(loggedUserId);
		final String loginOrgName = orgDAO.retrieveOrgName(loginOrgId);
		final String partnerName = bpartnerComposite.getBpartner().getName();
		final String partnerOrgName = orgDAO.retrieveOrgName(partnerOrgId);

		final String summary = TranslatableStrings.adMessage(MSG_C_BPartnerCreatedFromAnotherOrg,
				loginUserName,
				loginOrgName,
				partnerName,
				partnerOrgName).translate(loginLanguage);

		final RequestTypeId requestTypeId = requestTypeDAO.retrieveBPartnerCreatedFromAnotherOrgRequestTypeId();

		final BPartnerId bPartnerId = bpartnerComposite.getBpartner().getId();
		final I_R_Request partnerCreatedFromAnotherOrgRequest = createPartnerCreatedFromAnotherOrgRequest(partnerOrgId, summary, requestTypeId, bPartnerId);

		final UserNotificationRequest.TargetRecordAction targetRecordAction = UserNotificationRequest
				.TargetRecordAction
				.of(I_R_Request.Table_Name, partnerCreatedFromAnotherOrgRequest.getR_Request_ID());

		final OrgId requestOrgId = OrgId.ofRepoId(partnerCreatedFromAnotherOrgRequest.getAD_Org_ID());
		final UserGroupId userGroupId = orgDAO.getPartnerCreatedFromAnotherOrgNotifyUserGroupID(requestOrgId);

		if (userGroupId == null)
		{
			// nobody to notify
			return;
		}

		userGroupRepository
				.getByUserGroupId(userGroupId)
				.streamAssignmentsFor(userGroupId, Instant.now())
				.map(UserGroupUserAssignment::getUserId)
				.map(userId -> UserNotificationRequest.builder()
						.recipientUserId(userId)
						.contentADMessage(MSG_C_BPartnerCreatedFromAnotherOrg)
						.contentADMessageParam(loginUserName)
						.contentADMessageParam(loginOrgName)
						.contentADMessageParam(partnerName)
						.contentADMessageParam(partnerOrgName)
						.targetAction(targetRecordAction)
						.build())
				.forEach(notificationBL::send);
	}

	private I_R_Request createPartnerCreatedFromAnotherOrgRequest(final OrgId partnerOrgId, final String summary, final RequestTypeId requestTypeId, final BPartnerId bPartnerId)
	{
		final RequestCandidate requestCandidate = RequestCandidate.builder()
				.summary(summary)
				.confidentialType(X_R_Request.CONFIDENTIALTYPE_PartnerConfidential)
				.orgId(partnerOrgId)
				.recordRef(TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId))
				.requestTypeId(requestTypeId)
				.partnerId(bPartnerId)
				.dateDelivered(SystemTime.asZonedDateTime())

				.build();

		return requestDAO.createRequest(requestCandidate);
	}

	@NonNull
	private static BPartnerQuickInputId extractBpartnerQuickInputId(final @NonNull I_C_BPartner_QuickInput template)
	{
		return BPartnerQuickInputId.ofRepoId(template.getC_BPartner_QuickInput_ID());
	}

	private BPartnerComposite toBPartnerComposite(@NonNull final I_C_BPartner_QuickInput template)
	{
		final BPGroupId groupId = BPGroupId.ofRepoIdOrNull(template.getC_BP_Group_ID());
		if (groupId == null)
		{
			throw new FillMandatoryException("C_BP_Group_ID");
		}

		final ArrayList<BPartnerLocation> locations = getBPartnerLocations(template);
		final BPartnerLocation uniqueLocationOfBPartnerTemplate = getUniqueLocationOfBPartnerTemplate(template);
		if (uniqueLocationOfBPartnerTemplate != null)
		{
			locations.add(uniqueLocationOfBPartnerTemplate);
		}

		if (locations.isEmpty())
		{
			throw new FillMandatoryException(I_C_BPartner_QuickInput.COLUMNNAME_C_Location_ID);
		}

		validateMandatoryPricingSystems(template, locations);

		//
		// BPartner (header)
		final BPartner bpartner = getBPartner(template);

		final ArrayList<BPartnerContact> contacts = getBPartnerContacts(template);

		return BPartnerComposite.builder()
				.orgId(OrgId.ofRepoId(template.getAD_Org_ID()))
				.bpartner(bpartner)
				.locations(locations)
				.contacts(contacts)
				.build();
	}

	private void validateMandatoryPricingSystems(final @NonNull I_C_BPartner_QuickInput template, final ArrayList<BPartnerLocation> locations)
	{
		final Optional<CountryId> shipToDefaultCountry = locations
				.stream()
				.filter(BPartnerLocation::isActive)
				.filter(location -> location.getLocationType().getIsShipToDefaultOr(false))
				.map(BPartnerLocation::getExistingLocationId)
				.map(locationDAO::getCountryIdByLocationId)
				.findFirst();

		final Optional<CountryId> billToDefaultCountry = locations
				.stream()
				.filter(BPartnerLocation::isActive)
				.filter(location -> location.getLocationType().getIsBillToDefaultOr(false))
				.map(BPartnerLocation::getExistingLocationId)
				.map(locationDAO::getCountryIdByLocationId)
				.findFirst();

		final ImmutableList<CountryId> shipToLocationCountryIds = locations
				.stream()
				.filter(BPartnerLocation::isActive)
				.filter(location -> location.getLocationType().getIsShipToOr(false))
				.map(BPartnerLocation::getExistingLocationId)
				.map(locationDAO::getCountryIdByLocationId)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<CountryId> billToLocationCountryIds = locations
				.stream()
				.filter(BPartnerLocation::isActive)
				.filter(location -> location.getLocationType().getIsBillToOr(false))
				.map(BPartnerLocation::getExistingLocationId)
				.map(locationDAO::getCountryIdByLocationId)
				.collect(ImmutableList.toImmutableList());

		final PricingSystemId customerPricingSystemId = PricingSystemId.ofRepoIdOrNull(template.getM_PricingSystem_ID());
		final PricingSystemId vendorPricingSystemId = PricingSystemId.ofRepoIdOrNull(template.getPO_PricingSystem_ID());

		if (customerPricingSystemId != null && template.isCustomer())
		{

			validatePricesForCountries(shipToDefaultCountry, shipToLocationCountryIds, customerPricingSystemId, SOTrx.SALES);

		}

		if (vendorPricingSystemId != null && template.isVendor())
		{
			validatePricesForCountries(billToDefaultCountry, billToLocationCountryIds, vendorPricingSystemId, SOTrx.PURCHASE);
		}
	}

	private void validatePricesForCountries(final Optional<CountryId> possibleDefaultCountryId,
											final ImmutableList<CountryId> nonDefaultCountryIds,
											final PricingSystemId pricingSystemId,
											final SOTrx soTrx)
	{
		final PriceListsCollection salesPriceLists = priceListDAO.retrievePriceListsCollectionByPricingSystemId(pricingSystemId);

		final List<CountryId> countryIdsWithNoPrices = new ArrayList<>();

		if (possibleDefaultCountryId.isPresent())
		{
			final CountryId defaultCountryId = possibleDefaultCountryId.get();
			final ImmutableList<I_M_PriceList> defaultPriceLists = salesPriceLists.filterAndList(defaultCountryId, soTrx);

			if (Check.isEmpty(defaultPriceLists))
			{
				countryIdsWithNoPrices.add(defaultCountryId);
			}
		}
		else
		{
			final ArrayList<CountryId> nonDefaultCountriesWithoutPrices = new ArrayList<>();
			boolean onePriceWasFound = false;
			for (final CountryId countryId : nonDefaultCountryIds)
			{
				final ImmutableList<I_M_PriceList> nonDefaultPriceLists = salesPriceLists.filterAndList(countryId, soTrx);
				if (Check.isEmpty(nonDefaultPriceLists))
				{
					nonDefaultCountriesWithoutPrices.add(countryId);
				}
				else
				{
					onePriceWasFound = true;
					break;
				}
			}

			if (!onePriceWasFound)
			{
				countryIdsWithNoPrices.addAll(nonDefaultCountriesWithoutPrices);
			}
		}

		if (!Check.isEmpty(countryIdsWithNoPrices))
		{
			final String pricingSystemName = priceListDAO.getPricingSystemName(pricingSystemId);

			final ImmutableList<ITranslatableString> countriesWithNoPrices = countryIdsWithNoPrices.stream()
					.map(countryDAO::getCountryNameById)
					.collect(ImmutableList.toImmutableList());

			throw new PriceListNotFoundException(pricingSystemName, soTrx, countriesWithNoPrices);
		}

	}

	private @Nullable
	BPartnerLocation getUniqueLocationOfBPartnerTemplate(final I_C_BPartner_QuickInput template)
	{
		final LocationId uniqueLocationIdOfBPartnerTemplate = LocationId.ofRepoIdOrNull(template.getC_Location_ID());

		if (uniqueLocationIdOfBPartnerTemplate == null)
		{
			return null;
		}

		return BPartnerLocation.builder()
				.locationType(BPartnerLocationType.builder()
						.billTo(true)
						.billToDefault(true)
						.shipTo(true)
						.shipToDefault(true)
						.build())
				.name(".")
				.phone(template.getC_BPartner_Location_Phone())
				.mobile(template.getC_BPartner_Location_Mobile())
				.fax(template.getC_BPartner_Location_Fax())
				.email(template.getC_BPartner_Location_Email())
				.existingLocationId(uniqueLocationIdOfBPartnerTemplate)
				.build();
	}

	private BPartner getBPartner(final @NonNull I_C_BPartner_QuickInput template)
	{
		final BPGroupId groupId = BPGroupId.ofRepoIdOrNull(template.getC_BP_Group_ID());
		final PricingSystemId customerPricingSystemId = PricingSystemId.ofRepoIdOrNull(template.getM_PricingSystem_ID());
		final PricingSystemId vendorPricingSystemId = PricingSystemId.ofRepoIdOrNull(template.getPO_PricingSystem_ID());

		final BPartner bpartner = BPartner.builder()
				.value(null) // to be generated
				.name(template.getBPartnerName())
				.greetingId(GreetingId.ofRepoIdOrNull(template.getC_Greeting_ID()))
				.name2(template.getName2())
				.company(template.isCompany())
				.companyName(template.getCompanyname())
				.groupId(groupId)
				.language(Language.asLanguage(template.getAD_Language()))
				.phone(StringUtils.trimBlankToNull(template.getPhone()))
				// Customer:
				.customer(template.isCustomer())
				.customerPricingSystemId(customerPricingSystemId)
				.customerPaymentTermId(PaymentTermId.ofRepoIdOrNull(template.getC_PaymentTerm_ID()))
				// Vendor:
				.vendor(template.isVendor())
				.vendorPricingSystemId(vendorPricingSystemId)
				.vendorPaymentTermId(PaymentTermId.ofRepoIdOrNull(template.getPO_PaymentTerm_ID()))
				//
				.excludeFromPromotions(template.isExcludeFromPromotions())
				.referrer(template.getReferrer())
				.campaignId(CampaignId.ofRepoIdOrNull(template.getMKTG_Campaign_ID()))

				.paymentRule(PaymentRule.ofNullableCode(template.getPaymentRule()))

				.soDocTypeTargetId(DocTypeId.ofRepoIdOrNull(template.getC_DocTypeTarget_ID()))

				.firstName(template.getFirstname())
				.lastName(template.getLastname())
				.vatId(template.getVATaxID())

				//
				.build();

		return bpartner;
	}

	@NonNull
	private ArrayList<BPartnerContact> getBPartnerContacts(final @NonNull I_C_BPartner_QuickInput template)
	{
		final BPartnerQuickInputId bpartnerQuickInputId = extractBpartnerQuickInputId(template);
		final ArrayList<BPartnerContact> contacts = new ArrayList<>();

		final List<I_C_BPartner_Contact_QuickInput> contactTemplates = bpartnerQuickInputRepository.retrieveContactsByQuickInputId(bpartnerQuickInputId);
		for (final I_C_BPartner_Contact_QuickInput contactTemplate : contactTemplates)
		{
			final BPartnerContactQuickInputId partnerContactQuickInputId = BPartnerContactQuickInputId.ofRepoId(contactTemplate.getC_BPartner_Contact_QuickInput_ID());
			final String transientId = TransientIdConverter.toTransientId(partnerContactQuickInputId);

			final boolean isDefaultContact = contacts.isEmpty();
			final boolean isSalesContact = template.isCustomer();
			final boolean isPurchaseContact = template.isVendor();

			contacts.add(BPartnerContact.builder()
					.transientId(transientId)
					.contactType(BPartnerContactType.builder()
							.defaultContact(isDefaultContact)
							.billToDefault(isDefaultContact)
							.shipToDefault(isDefaultContact)
							.sales(isSalesContact)
							.salesDefault(isSalesContact && isDefaultContact)
							.purchase(isPurchaseContact)
							.purchaseDefault(isPurchaseContact && isDefaultContact)
							.build())
					.newsletter(contactTemplate.isNewsletter())
					.membershipContact(contactTemplate.isMembershipContact())
					.firstName(contactTemplate.getFirstname())
					.lastName(contactTemplate.getLastname())
					.name(IUserBL.buildContactName(contactTemplate.getFirstname(), contactTemplate.getLastname()))
					.greetingId(GreetingId.ofRepoIdOrNull(contactTemplate.getC_Greeting_ID()))
					.phone(StringUtils.trimBlankToNull(contactTemplate.getPhone()))
					.email(StringUtils.trimBlankToNull(contactTemplate.getEMail()))
					.birthday(TimeUtil.asLocalDate(contactTemplate.getBirthday(), orgDAO.getTimeZone(OrgId.ofRepoIdOrAny(contactTemplate.getAD_Org_ID()))))
					.invoiceEmailEnabled(de.metas.common.util.StringUtils.toBoolean(contactTemplate.getIsInvoiceEmailEnabled(), null))
					.phone2(StringUtils.trimBlankToNull(contactTemplate.getPhone2()))
					.title(StringUtils.trimBlankToNull(contactTemplate.getTitle()))
					.build());
		}

		return contacts;
	}

	private ArrayList<BPartnerLocation> getBPartnerLocations(final @NonNull I_C_BPartner_QuickInput template)
	{
		final BPartnerQuickInputId bpartnerQuickInputId = extractBpartnerQuickInputId(template);

		final ArrayList<BPartnerLocation> bpartnerLocations = new ArrayList<>();

		final List<I_C_BPartner_Location_QuickInput> bpartnerLocationTemplates = bpartnerQuickInputRepository.retrieveLocationsByQuickInputId(bpartnerQuickInputId);
		for (final I_C_BPartner_Location_QuickInput bpartnerLocationTemplate : bpartnerLocationTemplates)
		{
			bpartnerLocations.add(BPartnerLocation.builder()
					.locationType(BPartnerLocationType.builder()
							.billTo(bpartnerLocationTemplate.isBillTo())
							.billToDefault(bpartnerLocationTemplate.isBillToDefault())
							.shipTo(bpartnerLocationTemplate.isShipTo())
							.shipToDefault(bpartnerLocationTemplate.isShipToDefault())
							.build())
					.active(bpartnerLocationTemplate.isActive())
					.email(bpartnerLocationTemplate.getEMail())
					.fax(bpartnerLocationTemplate.getFax())
					.mobile(bpartnerLocationTemplate.getPhone2())
					.phone(bpartnerLocationTemplate.getPhone())
					.name(bpartnerLocationTemplate.getName())
					.gln(GLN.ofNullableString(bpartnerLocationTemplate.getGLN()))
					.existingLocationId(LocationId.ofRepoId(bpartnerLocationTemplate.getC_Location_ID()))
					.bpartnerName(bpartnerLocationTemplate.getBPartnerName())
					.setupPlaceNo(bpartnerLocationTemplate.getSetup_Place_No())
					.replicationLookupDefault(bpartnerLocationTemplate.isReplicationLookupDefault())
					.remitTo(bpartnerLocationTemplate.isRemitTo())
					.handOverLocation(bpartnerLocationTemplate.isHandOverLocation())
					.visitorsAddress(bpartnerLocationTemplate.isVisitorsAddress())
					.build());
		}

		return bpartnerLocations;
	}

	//
	//
	//
	//
	//

	private static class TransientIdConverter
	{
		public static String toTransientId(@NonNull final BPartnerContactQuickInputId id)
		{
			return String.valueOf(id.getRepoId());
		}

		public static BPartnerContactQuickInputId fromTransientId(@NonNull final String transientId)
		{
			return BPartnerContactQuickInputId.ofRepoId(NumberUtils.asInt(transientId, -1));
		}
	}
}
