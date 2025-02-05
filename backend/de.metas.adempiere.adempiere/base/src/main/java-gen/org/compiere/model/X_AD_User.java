// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_User extends org.compiere.model.PO implements I_AD_User, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1217883274L;

	/** Standard Constructor */
	public X_AD_User (final Properties ctx, final int AD_User_ID, @Nullable final String trxName)
	{
		super (ctx, AD_User_ID, trxName);
	}

	/** Load Constructor */
	public X_AD_User (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super (ctx, rs, trxName);
	}


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/**
	 * AD_Language AD_Reference_ID=327
	 * Reference name: AD_Language System
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	@Override
	public void setAD_Language (final @Nullable java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language()
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class);
	}

	@Override
	public void setAD_Org_Mapping(final org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping)
	{
		set_ValueFromPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class, AD_Org_Mapping);
	}

	@Override
	public void setAD_Org_Mapping_ID (final int AD_Org_Mapping_ID)
	{
		if (AD_Org_Mapping_ID < 1)
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, null);
		else
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, AD_Org_Mapping_ID);
	}

	@Override
	public int getAD_Org_Mapping_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_Mapping_ID);
	}

	@Override
	public void setAD_OrgTrx_ID (final int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1)
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else
			set_Value (COLUMNNAME_AD_OrgTrx_ID, AD_OrgTrx_ID);
	}

	@Override
	public int getAD_OrgTrx_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgTrx_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0)
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAD_User_InCharge_ID (final int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1)
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else
			set_Value (COLUMNNAME_AD_User_InCharge_ID, AD_User_InCharge_ID);
	}

	@Override
	public int getAD_User_InCharge_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_InCharge_ID);
	}

	/**
	 * AlbertaTitle AD_Reference_ID=541318
	 * Reference name: Title_List
	 */
	public static final int ALBERTATITLE_AD_Reference_ID=541318;
	/** Unbekannt = 0 */
	public static final String ALBERTATITLE_Unbekannt = "0";
	/** Dr. = 1 */
	public static final String ALBERTATITLE_Dr = "1";
	/** Prof. Dr. = 2 */
	public static final String ALBERTATITLE_ProfDr = "2";
	/** Dipl. Ing. = 3 */
	public static final String ALBERTATITLE_DiplIng = "3";
	/** Dipl. Med. = 4 */
	public static final String ALBERTATITLE_DiplMed = "4";
	/** Dipl. Psych. = 5 */
	public static final String ALBERTATITLE_DiplPsych = "5";
	/** Dr. Dr. = 6 */
	public static final String ALBERTATITLE_DrDr = "6";
	/** Dr. med. = 7 */
	public static final String ALBERTATITLE_DrMed = "7";
	/** Prof. Dr. Dr. = 8 */
	public static final String ALBERTATITLE_ProfDrDr = "8";
	/** Prof. = 9 */
	public static final String ALBERTATITLE_Prof = "9";
	/** Prof. Dr. med. = 10 */
	public static final String ALBERTATITLE_ProfDrMed = "10";
	/** Rechtsanwalt = 11 */
	public static final String ALBERTATITLE_Rechtsanwalt = "11";
	/** Rechtsanwältin = 12 */
	public static final String ALBERTATITLE_Rechtsanwaeltin = "12";
	/** Schwester (Orden) = 13 */
	public static final String ALBERTATITLE_SchwesterOrden = "13";
	@Override
	public void setAlbertaTitle (final @Nullable java.lang.String AlbertaTitle)
	{
		throw new IllegalArgumentException ("AlbertaTitle is virtual column");	}

	@Override
	public java.lang.String getAlbertaTitle()
	{
		return get_ValueAsString(COLUMNNAME_AlbertaTitle);
	}

	@Override
	public org.compiere.model.I_AD_Image getAvatar()
	{
		return get_ValueAsPO(COLUMNNAME_Avatar_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setAvatar(final org.compiere.model.I_AD_Image Avatar)
	{
		set_ValueFromPO(COLUMNNAME_Avatar_ID, org.compiere.model.I_AD_Image.class, Avatar);
	}

	@Override
	public void setAvatar_ID (final int Avatar_ID)
	{
		if (Avatar_ID < 1)
			set_Value (COLUMNNAME_Avatar_ID, null);
		else
			set_Value (COLUMNNAME_Avatar_ID, Avatar_ID);
	}

	@Override
	public int getAvatar_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Avatar_ID);
	}

	@Override
	public void setBirthday (final @Nullable java.sql.Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	@Override
	public java.sql.Timestamp getBirthday()
	{
		return get_ValueAsTimestamp(COLUMNNAME_Birthday);
	}

	@Override
	public void setC_BPartner_Alt_Location_ID (final int C_BPartner_Alt_Location_ID)
	{
		if (C_BPartner_Alt_Location_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_Alt_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_Alt_Location_ID, C_BPartner_Alt_Location_ID);
	}

	@Override
	public int getC_BPartner_Alt_Location_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Alt_Location_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setCC_User_ID (final int CC_User_ID)
	{
		if (CC_User_ID < 1)
			set_Value (COLUMNNAME_CC_User_ID, null);
		else
			set_Value (COLUMNNAME_CC_User_ID, CC_User_ID);
	}

	@Override
	public int getCC_User_ID()
	{
		return get_ValueAsInt(COLUMNNAME_CC_User_ID);
	}

	@Override
	public void setC_Greeting_ID (final int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1)
			set_Value (COLUMNNAME_C_Greeting_ID, null);
		else
			set_Value (COLUMNNAME_C_Greeting_ID, C_Greeting_ID);
	}

	@Override
	public int getC_Greeting_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Greeting_ID);
	}

	@Override
	public org.compiere.model.I_C_Job getC_Job()
	{
		return get_ValueAsPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class);
	}

	@Override
	public void setC_Job(final org.compiere.model.I_C_Job C_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class, C_Job);
	}

	@Override
	public void setC_Job_ID (final int C_Job_ID)
	{
		if (C_Job_ID < 1)
			set_Value (COLUMNNAME_C_Job_ID, null);
		else
			set_Value (COLUMNNAME_C_Job_ID, C_Job_ID);
	}

	@Override
	public int getC_Job_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Job_ID);
	}

	@Override
	public org.compiere.model.I_C_Title getC_Title()
	{
		return get_ValueAsPO(COLUMNNAME_C_Title_ID, org.compiere.model.I_C_Title.class);
	}

	@Override
	public void setC_Title(final org.compiere.model.I_C_Title C_Title)
	{
		set_ValueFromPO(COLUMNNAME_C_Title_ID, org.compiere.model.I_C_Title.class, C_Title);
	}

	@Override
	public void setC_Title_ID (final int C_Title_ID)
	{
		if (C_Title_ID < 1)
			set_Value (COLUMNNAME_C_Title_ID, null);
		else
			set_Value (COLUMNNAME_C_Title_ID, C_Title_ID);
	}

	@Override
	public int getC_Title_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Title_ID);
	}

	@Override
	public void setComments (final @Nullable java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	@Override
	public java.lang.String getComments()
	{
		return get_ValueAsString(COLUMNNAME_Comments);
	}

	@Override
	public void setCompanyname (final @Nullable java.lang.String Companyname)
	{
		throw new IllegalArgumentException ("Companyname is virtual column");	}

	@Override
	public java.lang.String getCompanyname()
	{
		return get_ValueAsString(COLUMNNAME_Companyname);
	}

	/**
	 * ConnectionProfile AD_Reference_ID=364
	 * Reference name: AD_User ConnectionProfile
	 */
	public static final int CONNECTIONPROFILE_AD_Reference_ID=364;
	/** LAN = L */
	public static final String CONNECTIONPROFILE_LAN = "L";
	/** TerminalServer = T */
	public static final String CONNECTIONPROFILE_TerminalServer = "T";
	/** VPN = V */
	public static final String CONNECTIONPROFILE_VPN = "V";
	/** WAN = W */
	public static final String CONNECTIONPROFILE_WAN = "W";
	@Override
	public void setConnectionProfile (final @Nullable java.lang.String ConnectionProfile)
	{
		set_Value (COLUMNNAME_ConnectionProfile, ConnectionProfile);
	}

	@Override
	public java.lang.String getConnectionProfile()
	{
		return get_ValueAsString(COLUMNNAME_ConnectionProfile);
	}

	/**
	 * ContactLimitation AD_Reference_ID=540089
	 * Reference name: ContactLimitation
	 */
	public static final int CONTACTLIMITATION_AD_Reference_ID=540089;
	/** keine Anrufe = B */
	public static final String CONTACTLIMITATION_KeineAnrufe = "B";
	/** keine Anrufe und eMails = C */
	public static final String CONTACTLIMITATION_KeineAnrufeUndEMails = "C";
	/** keine Anrufe, eMails und Post = D */
	public static final String CONTACTLIMITATION_KeineAnrufeEMailsUndPost = "D";
	/** keine eMail und Post = E */
	public static final String CONTACTLIMITATION_KeineEMailUndPost = "E";
	@Override
	public void setContactLimitation (final @Nullable java.lang.String ContactLimitation)
	{
		set_Value (COLUMNNAME_ContactLimitation, ContactLimitation);
	}

	@Override
	public java.lang.String getContactLimitation()
	{
		return get_ValueAsString(COLUMNNAME_ContactLimitation);
	}

	@Override
	public void setContactLimitationReason (final @Nullable java.lang.String ContactLimitationReason)
	{
		set_Value (COLUMNNAME_ContactLimitationReason, ContactLimitationReason);
	}

	@Override
	public java.lang.String getContactLimitationReason()
	{
		return get_ValueAsString(COLUMNNAME_ContactLimitationReason);
	}

	@Override
	public void setDelDate (final @Nullable java.sql.Timestamp DelDate)
	{
		set_Value (COLUMNNAME_DelDate, DelDate);
	}

	@Override
	public java.sql.Timestamp getDelDate()
	{
		return get_ValueAsTimestamp(COLUMNNAME_DelDate);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription()
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setEMail (final @Nullable java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	@Override
	public java.lang.String getEMail()
	{
		return get_ValueAsString(COLUMNNAME_EMail);
	}

	@Override
	public void setEMail2 (final @Nullable java.lang.String EMail2)
	{
		set_Value (COLUMNNAME_EMail2, EMail2);
	}

	@Override
	public java.lang.String getEMail2()
	{
		return get_ValueAsString(COLUMNNAME_EMail2);
	}

	@Override
	public void setEMail3 (final @Nullable java.lang.String EMail3)
	{
		set_Value (COLUMNNAME_EMail3, EMail3);
	}

	@Override
	public java.lang.String getEMail3()
	{
		return get_ValueAsString(COLUMNNAME_EMail3);

	}

	@Override
	public void setAD_BoilerPlate_Default_ID (final int AD_BoilerPlate_Default_ID)
	{
		if (AD_BoilerPlate_Default_ID < 1)
			set_Value (COLUMNNAME_AD_BoilerPlate_Default_ID, null);
		else
			set_Value (COLUMNNAME_AD_BoilerPlate_Default_ID, AD_BoilerPlate_Default_ID);
	}

	@Override
	public int getAD_BoilerPlate_Default_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_BoilerPlate_Default_ID);
	}

	/**
	 * Alberta_Gender AD_Reference_ID=541317
	 * Reference name: Gender_List
	 */
	public static final int ALBERTA_GENDER_AD_Reference_ID=541317;
	/** Unbekannt = 0 */
	public static final String ALBERTA_GENDER_Unbekannt = "0";
	/** Weiblich = 1 */
	public static final String ALBERTA_GENDER_Weiblich = "1";
	/** Männlich = 2 */
	public static final String ALBERTA_GENDER_Maennlich = "2";
	/** Divers = 3 */
	public static final String ALBERTA_GENDER_Divers = "3";
	@Override
	public void setAlberta_Gender (final @Nullable java.lang.String Alberta_Gender)
	{
		throw new IllegalArgumentException ("Alberta_Gender is virtual column");	}

	@Override
	public java.lang.String getAlberta_Gender()
	{
		return get_ValueAsString(COLUMNNAME_Alberta_Gender);
	}

	@Override
	public void setC_Flatrate_Conditions_Default_ID (final int C_Flatrate_Conditions_Default_ID)
	{
		if (C_Flatrate_Conditions_Default_ID < 1)
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Default_ID, null);
		else
			set_Value (COLUMNNAME_C_Flatrate_Conditions_Default_ID, C_Flatrate_Conditions_Default_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_Default_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_Default_ID);
	}

	@Override
	public void setEmail_private (final @Nullable java.lang.String Email_private)
	{
		set_Value (COLUMNNAME_Email_private, Email_private);
	}

	@Override
	public java.lang.String getEmail_private()
	{
		return get_ValueAsString(COLUMNNAME_Email_private);
	}

	@Override
	public void setEMailUser (final @Nullable java.lang.String EMailUser)
	{
		set_Value (COLUMNNAME_EMailUser, EMailUser);
	}

	@Override
	public java.lang.String getEMailUser()
	{
		return get_ValueAsString(COLUMNNAME_EMailUser);
	}

	@Override
	public void setEMailUserPW (final @Nullable java.lang.String EMailUserPW)
	{
		set_Value (COLUMNNAME_EMailUserPW, EMailUserPW);
	}

	@Override
	public java.lang.String getEMailUserPW()
	{
		return get_ValueAsString(COLUMNNAME_EMailUserPW);
	}

	@Override
	public void setEMailVerify (final @Nullable java.lang.String EMailVerify)
	{
		set_ValueNoCheck (COLUMNNAME_EMailVerify, EMailVerify);
	}

	@Override
	public java.lang.String getEMailVerify()
	{
		return get_ValueAsString(COLUMNNAME_EMailVerify);
	}

	@Override
	public void setEMailVerifyDate (final @Nullable java.sql.Timestamp EMailVerifyDate)
	{
		set_ValueNoCheck (COLUMNNAME_EMailVerifyDate, EMailVerifyDate);
	}

	@Override
	public java.sql.Timestamp getEMailVerifyDate()
	{
		return get_ValueAsTimestamp(COLUMNNAME_EMailVerifyDate);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId()
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setFax (final @Nullable java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	@Override
	public java.lang.String getFax()
	{
		return get_ValueAsString(COLUMNNAME_Fax);
	}

	@Override
	public void setFirstname (final @Nullable java.lang.String Firstname)
	{
		set_Value (COLUMNNAME_Firstname, Firstname);
	}

	@Override
	public java.lang.String getFirstname()
	{
		return get_ValueAsString(COLUMNNAME_Firstname);
	}

	/**
	 * Fresh_xmas_Gift AD_Reference_ID=540504
	 * Reference name: fresh_xmas_list
	 */
	public static final int FRESH_XMAS_GIFT_AD_Reference_ID=540504;
	/** Karte = K */
	public static final String FRESH_XMAS_GIFT_Karte = "K";
	/** Geschenk = G */
	public static final String FRESH_XMAS_GIFT_Geschenk = "G";
	@Override
	public void setFresh_xmas_Gift (final @Nullable java.lang.String Fresh_xmas_Gift)
	{
		set_Value (COLUMNNAME_Fresh_xmas_Gift, Fresh_xmas_Gift);
	}

	@Override
	public java.lang.String getFresh_xmas_Gift()
	{
		return get_ValueAsString(COLUMNNAME_Fresh_xmas_Gift);
	}

	@Override
	public void setHobbies_interests (final @Nullable java.lang.String Hobbies_interests)
	{
		set_Value (COLUMNNAME_Hobbies_interests, Hobbies_interests);
	}

	@Override
	public java.lang.String getHobbies_interests()
	{
		return get_ValueAsString(COLUMNNAME_Hobbies_interests);
	}

	@Override
	public void setIncluded_Tab_ID (final @Nullable java.lang.String Included_Tab_ID)
	{
		set_Value (COLUMNNAME_Included_Tab_ID, Included_Tab_ID);
	}

	@Override
	public java.lang.String getIncluded_Tab_ID()
	{
		return get_ValueAsString(COLUMNNAME_Included_Tab_ID);
	}

	@Override
	public void setIsAccountLocked (final boolean IsAccountLocked)
	{
		set_Value (COLUMNNAME_IsAccountLocked, IsAccountLocked);
	}

	@Override
	public boolean isAccountLocked()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAccountLocked);
	}

	@Override
	public void setIsActivePartner (final boolean IsActivePartner)
	{
		throw new IllegalArgumentException ("IsActivePartner is virtual column");	}

	@Override
	public boolean isActivePartner()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsActivePartner);
	}

	@Override
	public void setIsAuthorizedSignatory (final boolean IsAuthorizedSignatory)
	{
		set_Value (COLUMNNAME_IsAuthorizedSignatory, IsAuthorizedSignatory);
	}

	@Override
	public boolean isAuthorizedSignatory()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAuthorizedSignatory);
	}

	@Override
	public void setIsBackofficeContact (final boolean IsBackofficeContact)
	{
		set_Value (COLUMNNAME_IsBackofficeContact, IsBackofficeContact);
	}

	@Override
	public boolean isBackofficeContact()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBackofficeContact);
	}

	@Override
	public void setIsBillToContact_Default (final boolean IsBillToContact_Default)
	{
		set_Value (COLUMNNAME_IsBillToContact_Default, IsBillToContact_Default);
	}

	@Override
	public boolean isBillToContact_Default()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBillToContact_Default);
	}

	@Override
	public void setIsCustomUserRestriction (final boolean IsCustomUserRestriction)
	{
		throw new IllegalArgumentException ("IsCustomUserRestriction is virtual column");	}

	@Override
	public boolean isCustomUserRestriction()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCustomUserRestriction);
	}

	@Override
	public void setIsDefaultContact (final boolean IsDefaultContact)
	{
		set_Value (COLUMNNAME_IsDefaultContact, IsDefaultContact);
	}

	@Override
	public boolean isDefaultContact()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultContact);
	}

	@Override
	public void setIsDunningDefault (final boolean IsDunningDefault)
	{
		set_Value (COLUMNNAME_IsDunningDefault, IsDunningDefault);
	}

	@Override
	public boolean IsDunningDefault()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDunningDefault);
	}

	@Override
	public void setIsFullBPAccess (final boolean IsFullBPAccess)
	{
		set_Value (COLUMNNAME_IsFullBPAccess, IsFullBPAccess);
	}

	@Override
	public boolean isFullBPAccess()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFullBPAccess);
	}

	@Override
	public void setIsInPayroll (final boolean IsInPayroll)
	{
		set_Value (COLUMNNAME_IsInPayroll, IsInPayroll);
	}

	@Override
	public boolean isInPayroll()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInPayroll);
	}

	/**
	 * IsInvoiceEmailEnabled AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISINVOICEEMAILENABLED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISINVOICEEMAILENABLED_Yes = "Y";
	/** No = N */
	public static final String ISINVOICEEMAILENABLED_No = "N";
	@Override
	public void setIsInvoiceEmailEnabled (final @Nullable java.lang.String IsInvoiceEmailEnabled)
	{
		set_Value (COLUMNNAME_IsInvoiceEmailEnabled, IsInvoiceEmailEnabled);
	}

	@Override
	public java.lang.String getIsInvoiceEmailEnabled()
	{
		return get_ValueAsString(COLUMNNAME_IsInvoiceEmailEnabled);
	}

	@Override
	public void setIsLoginAsHostKey (final boolean IsLoginAsHostKey)
	{
		set_Value (COLUMNNAME_IsLoginAsHostKey, IsLoginAsHostKey);
	}

	@Override
	public boolean isLoginAsHostKey()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsLoginAsHostKey);
	}

	@Override
	public void setIsLogisticContact (final boolean IsLogisticContact)
	{
		set_Value (COLUMNNAME_IsLogisticContact, IsLogisticContact);
	}

	@Override
	public boolean isLogisticContact()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsLogisticContact);
	}

	@Override
	public void setIsMembershipContact (final boolean IsMembershipContact)
	{
		set_Value (COLUMNNAME_IsMembershipContact, IsMembershipContact);
	}

	@Override
	public boolean isMembershipContact()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMembershipContact);
	}

	@Override
	public void setIsNewsletter (final boolean IsNewsletter)
	{
		set_Value (COLUMNNAME_IsNewsletter, IsNewsletter);
	}

	@Override
	public boolean isNewsletter()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNewsletter);
	}

	@Override
	public void setIsPurchaseContact (final boolean IsPurchaseContact)
	{
		set_Value (COLUMNNAME_IsPurchaseContact, IsPurchaseContact);
	}

	@Override
	public boolean isPurchaseContact()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPurchaseContact);
	}

	@Override
	public void setIsPurchaseContact_Default (final boolean IsPurchaseContact_Default)
	{
		set_Value (COLUMNNAME_IsPurchaseContact_Default, IsPurchaseContact_Default);
	}

	@Override
	public boolean isPurchaseContact_Default()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPurchaseContact_Default);
	}

	@Override
	public void setIsSalesContact (final boolean IsSalesContact)
	{
		set_Value (COLUMNNAME_IsSalesContact, IsSalesContact);
	}

	@Override
	public boolean isSalesContact()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSalesContact);
	}

	@Override
	public void setIsSalesContact_Default (final boolean IsSalesContact_Default)
	{
		set_Value (COLUMNNAME_IsSalesContact_Default, IsSalesContact_Default);
	}

	@Override
	public boolean isSalesContact_Default()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSalesContact_Default);
	}

	@Override
	public void setIsShipToContact_Default (final boolean IsShipToContact_Default)
	{
		set_Value (COLUMNNAME_IsShipToContact_Default, IsShipToContact_Default);
	}

	@Override
	public boolean isShipToContact_Default()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShipToContact_Default);
	}

	@Override
	public void setIsSubjectMatterContact (final boolean IsSubjectMatterContact)
	{
		set_Value (COLUMNNAME_IsSubjectMatterContact, IsSubjectMatterContact);
	}

	@Override
	public boolean isSubjectMatterContact()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubjectMatterContact);
	}

	@Override
	public void setIsSystemUser (final boolean IsSystemUser)
	{
		set_Value (COLUMNNAME_IsSystemUser, IsSystemUser);
	}

	@Override
	public boolean isSystemUser()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSystemUser);
	}

	@Override
	public void setLastname (final @Nullable java.lang.String Lastname)
	{
		set_Value (COLUMNNAME_Lastname, Lastname);
	}

	@Override
	public java.lang.String getLastname()
	{
		return get_ValueAsString(COLUMNNAME_Lastname);
	}

	@Override
	public void setLockedFromIP (final @Nullable java.lang.String LockedFromIP)
	{
		set_Value (COLUMNNAME_LockedFromIP, LockedFromIP);
	}

	@Override
	public java.lang.String getLockedFromIP()
	{
		return get_ValueAsString(COLUMNNAME_LockedFromIP);
	}

	@Override
	public void setLogin (final @Nullable java.lang.String Login)
	{
		set_Value (COLUMNNAME_Login, Login);
	}

	@Override
	public java.lang.String getLogin()
	{
		return get_ValueAsString(COLUMNNAME_Login);
	}

	@Override
	public void setLoginFailureCount (final int LoginFailureCount)
	{
		set_Value (COLUMNNAME_LoginFailureCount, LoginFailureCount);
	}

	@Override
	public int getLoginFailureCount()
	{
		return get_ValueAsInt(COLUMNNAME_LoginFailureCount);
	}

	@Override
	public void setLoginFailureDate (final @Nullable java.sql.Timestamp LoginFailureDate)
	{
		set_Value (COLUMNNAME_LoginFailureDate, LoginFailureDate);
	}

	@Override
	public java.sql.Timestamp getLoginFailureDate()
	{
		return get_ValueAsTimestamp(COLUMNNAME_LoginFailureDate);
	}

	@Override
	public void setMemo (final @Nullable java.lang.String Memo)
	{
		set_Value (COLUMNNAME_Memo, Memo);
	}

	@Override
	public java.lang.String getMemo()
	{
		return get_ValueAsString(COLUMNNAME_Memo);
	}

	@Override
	public void setMobilePhone (final @Nullable java.lang.String MobilePhone)
	{
		set_Value (COLUMNNAME_MobilePhone, MobilePhone);
	}

	@Override
	public java.lang.String getMobilePhone()
	{
		return get_ValueAsString(COLUMNNAME_MobilePhone);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	/**
	 * NotificationType AD_Reference_ID=344
	 * Reference name: AD_User NotificationType
	 */
	public static final int NOTIFICATIONTYPE_AD_Reference_ID=344;
	/** EMail = E */
	public static final String NOTIFICATIONTYPE_EMail = "E";
	/** Notice = N */
	public static final String NOTIFICATIONTYPE_Notice = "N";
	/** None = X */
	public static final String NOTIFICATIONTYPE_None = "X";
	/** EMailPlusNotice = B */
	public static final String NOTIFICATIONTYPE_EMailPlusNotice = "B";
	/** NotifyUserInCharge = O */
	public static final String NOTIFICATIONTYPE_NotifyUserInCharge = "O";
	@Override
	public void setNotificationType (final java.lang.String NotificationType)
	{
		set_Value (COLUMNNAME_NotificationType, NotificationType);
	}

	@Override
	public java.lang.String getNotificationType()
	{
		return get_ValueAsString(COLUMNNAME_NotificationType);
	}

	@Override
	public void setPassword (final @Nullable java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	@Override
	public java.lang.String getPassword()
	{
		return get_ValueAsString(COLUMNNAME_Password);
	}

	@Override
	public void setpasswordportal (final @Nullable java.lang.String passwordportal)
	{
		set_Value (COLUMNNAME_passwordportal, passwordportal);
	}

	@Override
	public java.lang.String getpasswordportal()
	{
		return get_ValueAsString(COLUMNNAME_passwordportal);
	}

	@Override
	public void setPasswordResetCode (final @Nullable java.lang.String PasswordResetCode)
	{
		set_Value (COLUMNNAME_PasswordResetCode, PasswordResetCode);
	}

	@Override
	public java.lang.String getPasswordResetCode()
	{
		return get_ValueAsString(COLUMNNAME_PasswordResetCode);
	}

	@Override
	public void setPhone (final @Nullable java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	@Override
	public java.lang.String getPhone()
	{
		return get_ValueAsString(COLUMNNAME_Phone);
	}

	@Override
	public void setPhone2 (final @Nullable java.lang.String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	@Override
	public java.lang.String getPhone2()
	{
		return get_ValueAsString(COLUMNNAME_Phone2);
	}

	@Override
	public org.compiere.model.I_AD_Image getPhoto()
	{
		return get_ValueAsPO(COLUMNNAME_Photo_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setPhoto(final org.compiere.model.I_AD_Image Photo)
	{
		set_ValueFromPO(COLUMNNAME_Photo_ID, org.compiere.model.I_AD_Image.class, Photo);
	}

	@Override
	public void setPhoto_ID (final int Photo_ID)
	{
		if (Photo_ID < 1)
			set_Value (COLUMNNAME_Photo_ID, null);
		else
			set_Value (COLUMNNAME_Photo_ID, Photo_ID);
	}

	@Override
	public int getPhoto_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Photo_ID);
	}

	@Override
	public void setPrevious_company (final @Nullable java.lang.String Previous_company)
	{
		set_Value (COLUMNNAME_Previous_company, Previous_company);
	}

	@Override
	public java.lang.String getPrevious_company()
	{
		return get_ValueAsString(COLUMNNAME_Previous_company);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing()
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setRegistry (final @Nullable java.lang.String Registry)
	{
		set_Value (COLUMNNAME_Registry, Registry);
	}

	@Override
	public java.lang.String getRegistry()
	{
		return get_ValueAsString(COLUMNNAME_Registry);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo()
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setSupervisor_ID (final int Supervisor_ID)
	{
		if (Supervisor_ID < 1)
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else
			set_Value (COLUMNNAME_Supervisor_ID, Supervisor_ID);
	}

	@Override
	public int getSupervisor_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Supervisor_ID);
	}

	@Override
	public void setTimestamp (final @Nullable java.sql.Timestamp Timestamp)
	{
		throw new IllegalArgumentException ("Timestamp is virtual column");	}

	@Override
	public java.sql.Timestamp getTimestamp()
	{
		return get_ValueAsTimestamp(COLUMNNAME_Timestamp);
	}

	@Override
	public void setTitle (final @Nullable java.lang.String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	@Override
	public java.lang.String getTitle()
	{
		return get_ValueAsString(COLUMNNAME_Title);
	}

	@Override
	public void setUnlockAccount (final @Nullable java.lang.String UnlockAccount)
	{
		set_Value (COLUMNNAME_UnlockAccount, UnlockAccount);
	}

	@Override
	public java.lang.String getUnlockAccount()
	{
		return get_ValueAsString(COLUMNNAME_UnlockAccount);
	}

	@Override
	public void setUserPIN (final @Nullable java.lang.String UserPIN)
	{
		set_Value (COLUMNNAME_UserPIN, UserPIN);
	}

	@Override
	public java.lang.String getUserPIN()
	{
		return get_ValueAsString(COLUMNNAME_UserPIN);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue()
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}