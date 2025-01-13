// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_DocType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_DocType extends org.compiere.model.PO implements I_C_DocType, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -983742743L;

    /** Standard Constructor */
    public X_C_DocType (final Properties ctx, final int C_DocType_ID, @Nullable final String trxName)
    {
      super (ctx, C_DocType_ID, trxName);
    }

    /** Load Constructor */
    public X_C_DocType (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_BoilerPlate_ID (final int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1) 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, AD_BoilerPlate_ID);
	}

	@Override
	public int getAD_BoilerPlate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BoilerPlate_ID);
	}

	@Override
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(final org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	@Override
	public void setAD_PrintFormat_ID (final int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, AD_PrintFormat_ID);
	}

	@Override
	public int getAD_PrintFormat_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrintFormat_ID);
	}

	@Override
	public void setC_DocTypeDifference_ID (final int C_DocTypeDifference_ID)
	{
		if (C_DocTypeDifference_ID < 1)
			set_Value (COLUMNNAME_C_DocTypeDifference_ID, null);
		else
			set_Value (COLUMNNAME_C_DocTypeDifference_ID, C_DocTypeDifference_ID);
	}

	@Override
	public int getC_DocTypeDifference_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeDifference_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setC_DocTypeInvoice_ID (final int C_DocTypeInvoice_ID)
	{
		if (C_DocTypeInvoice_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, C_DocTypeInvoice_ID);
	}

	@Override
	public int getC_DocTypeInvoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeInvoice_ID);
	}

	@Override
	public void setC_DocTypeProforma_ID (final int C_DocTypeProforma_ID)
	{
		if (C_DocTypeProforma_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeProforma_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeProforma_ID, C_DocTypeProforma_ID);
	}

	@Override
	public int getC_DocTypeProforma_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeProforma_ID);
	}

	@Override
	public void setC_DocTypeShipment_ID (final int C_DocTypeShipment_ID)
	{
		if (C_DocTypeShipment_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeShipment_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeShipment_ID, C_DocTypeShipment_ID);
	}

	@Override
	public int getC_DocTypeShipment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeShipment_ID);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getDefiniteSequence()
	{
		return get_ValueAsPO(COLUMNNAME_DefiniteSequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setDefiniteSequence(final org.compiere.model.I_AD_Sequence DefiniteSequence)
	{
		set_ValueFromPO(COLUMNNAME_DefiniteSequence_ID, org.compiere.model.I_AD_Sequence.class, DefiniteSequence);
	}

	@Override
	public void setDefiniteSequence_ID (final int DefiniteSequence_ID)
	{
		if (DefiniteSequence_ID < 1) 
			set_Value (COLUMNNAME_DefiniteSequence_ID, null);
		else 
			set_Value (COLUMNNAME_DefiniteSequence_ID, DefiniteSequence_ID);
	}

	@Override
	public int getDefiniteSequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DefiniteSequence_ID);
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

	/** 
	 * DocBaseType AD_Reference_ID=183
	 * Reference name: C_DocType DocBaseType
	 */
	public static final int DOCBASETYPE_AD_Reference_ID=183;
	/** GLJournal = GLJ */
	public static final String DOCBASETYPE_GLJournal = "GLJ";
	/** GLDocument = GLD */
	public static final String DOCBASETYPE_GLDocument = "GLD";
	/** APInvoice = API */
	public static final String DOCBASETYPE_APInvoice = "API";
	/** APPayment = APP */
	public static final String DOCBASETYPE_APPayment = "APP";
	/** ARInvoice = ARI */
	public static final String DOCBASETYPE_ARInvoice = "ARI";
	/** ARReceipt = ARR */
	public static final String DOCBASETYPE_ARReceipt = "ARR";
	/** SalesOrder = SOO */
	public static final String DOCBASETYPE_SalesOrder = "SOO";
	/** ARProFormaInvoice = ARF */
	public static final String DOCBASETYPE_ARProFormaInvoice = "ARF";
	/** MaterialDelivery = MMS */
	public static final String DOCBASETYPE_MaterialDelivery = "MMS";
	/** MaterialReceipt = MMR */
	public static final String DOCBASETYPE_MaterialReceipt = "MMR";
	/** MaterialMovement = MMM */
	public static final String DOCBASETYPE_MaterialMovement = "MMM";
	/** PurchaseOrder = POO */
	public static final String DOCBASETYPE_PurchaseOrder = "POO";
	/** PurchaseRequisition = POR */
	public static final String DOCBASETYPE_PurchaseRequisition = "POR";
	/** MaterialPhysicalInventory = MMI */
	public static final String DOCBASETYPE_MaterialPhysicalInventory = "MMI";
	/** APCreditMemo = APC */
	public static final String DOCBASETYPE_APCreditMemo = "APC";
	/** ARCreditMemo = ARC */
	public static final String DOCBASETYPE_ARCreditMemo = "ARC";
	/** BankStatement = CMB */
	public static final String DOCBASETYPE_BankStatement = "CMB";
	/** CashJournal = CMC */
	public static final String DOCBASETYPE_CashJournal = "CMC";
	/** PaymentAllocation = CMA */
	public static final String DOCBASETYPE_PaymentAllocation = "CMA";
	/** MatchInvoice = MXI */
	public static final String DOCBASETYPE_MatchInvoice = "MXI";
	/** MatchPO = MXP */
	public static final String DOCBASETYPE_MatchPO = "MXP";
	/** ProjectIssue = PJI */
	public static final String DOCBASETYPE_ProjectIssue = "PJI";
	/** MaintenanceOrder = MOF */
	public static final String DOCBASETYPE_MaintenanceOrder = "MOF";
	/** ManufacturingOrder = MOP */
	public static final String DOCBASETYPE_ManufacturingOrder = "MOP";
	/** QualityOrder = MQO */
	public static final String DOCBASETYPE_QualityOrder = "MQO";
	/** Payroll = HRP */
	public static final String DOCBASETYPE_Payroll = "HRP";
	/** DistributionOrder = DOO */
	public static final String DOCBASETYPE_DistributionOrder = "DOO";
	/** ManufacturingCostCollector = MCC */
	public static final String DOCBASETYPE_ManufacturingCostCollector = "MCC";
	/** Gehaltsrechnung (Angestellter) = AEI */
	public static final String DOCBASETYPE_GehaltsrechnungAngestellter = "AEI";
	/** Interne Rechnung (Lieferant) = AVI */
	public static final String DOCBASETYPE_InterneRechnungLieferant = "AVI";
	/** ShipperTransportation = MST */
	public static final String DOCBASETYPE_ShipperTransportation = "MST";
	/** CustomerContract = CON */
	public static final String DOCBASETYPE_CustomerContract = "CON";
	/** DunningDoc = DUN */
	public static final String DOCBASETYPE_DunningDoc = "DUN";
	/** Shipment Declaration = SDD */
	public static final String DOCBASETYPE_ShipmentDeclaration = "SDD";
	/** Shipment Declaration Correction = SDC */
	public static final String DOCBASETYPE_ShipmentDeclarationCorrection = "SDC";
	/** Customs Invoice = CUI */
	public static final String DOCBASETYPE_CustomsInvoice = "CUI";
	/** ServiceRepairOrder = MRO */
	public static final String DOCBASETYPE_ServiceRepairOrder = "MRO";
	/** Remittance Advice = RMA */
	public static final String DOCBASETYPE_RemittanceAdvice = "RMA";
	/** BOM & Formula = BOM */
	public static final String DOCBASETYPE_BOMFormula = "BOM";
	@Override
	public void setDocBaseType (final java.lang.String DocBaseType)
	{
		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	@Override
	public java.lang.String getDocBaseType() 
	{
		return get_ValueAsString(COLUMNNAME_DocBaseType);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getDocNoSequence()
	{
		return get_ValueAsPO(COLUMNNAME_DocNoSequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setDocNoSequence(final org.compiere.model.I_AD_Sequence DocNoSequence)
	{
		set_ValueFromPO(COLUMNNAME_DocNoSequence_ID, org.compiere.model.I_AD_Sequence.class, DocNoSequence);
	}

	@Override
	public void setDocNoSequence_ID (final int DocNoSequence_ID)
	{
		if (DocNoSequence_ID < 1) 
			set_Value (COLUMNNAME_DocNoSequence_ID, null);
		else 
			set_Value (COLUMNNAME_DocNoSequence_ID, DocNoSequence_ID);
	}

	@Override
	public int getDocNoSequence_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DocNoSequence_ID);
	}

	/** 
	 * DocSubType AD_Reference_ID=148
	 * Reference name: C_DocType SubType
	 */
	public static final int DOCSUBTYPE_AD_Reference_ID=148;
	/** OnCreditOrder = WI */
	public static final String DOCSUBTYPE_OnCreditOrder = "WI";
	/** POSOrder = WR */
	public static final String DOCSUBTYPE_POSOrder = "WR";
	/** WarehouseOrder = WP */
	public static final String DOCSUBTYPE_WarehouseOrder = "WP";
	/** StandardOrder = SO */
	public static final String DOCSUBTYPE_StandardOrder = "SO";
	/** Proposal = ON */
	public static final String DOCSUBTYPE_Proposal = "ON";
	/** Quotation = OB */
	public static final String DOCSUBTYPE_Quotation = "OB";
	/** ReturnMaterial = RM */
	public static final String DOCSUBTYPE_ReturnMaterial = "RM";
	/** PrepayOrder = PR */
	public static final String DOCSUBTYPE_PrepayOrder = "PR";
	/** Provisionskorrektur = CC */
	public static final String DOCSUBTYPE_Provisionskorrektur = "CC";
	/** CommissionSettlement = CA */
	public static final String DOCSUBTYPE_CommissionSettlement = "CA";
	/** FlatFee = FF */
	public static final String DOCSUBTYPE_FlatFee = "FF";
	/** HoldingFee = HF */
	public static final String DOCSUBTYPE_HoldingFee = "HF";
	/** Subscription = SU */
	public static final String DOCSUBTYPE_Subscription = "SU";
	/** AQ = AQ */
	public static final String DOCSUBTYPE_AQ = "AQ";
	/** AP = AP */
	public static final String DOCSUBTYPE_AP = "AP";
	/** GS - Lieferdifferenz = CQ */
	public static final String DOCSUBTYPE_GS_Lieferdifferenz = "CQ";
	/** GS - Preisdifferenz = CR */
	public static final String DOCSUBTYPE_GS_Preisdifferenz = "CR";
	/** QualityInspection = QI */
	public static final String DOCSUBTYPE_QualityInspection = "QI";
	/** Leergutanlieferung = ER */
	public static final String DOCSUBTYPE_Leergutanlieferung = "ER";
	/** Produktanlieferung = MR */
	public static final String DOCSUBTYPE_Produktanlieferung = "MR";
	/** Produktauslieferung = MS */
	public static final String DOCSUBTYPE_Produktauslieferung = "MS";
	/** Leergutausgabe = ES */
	public static final String DOCSUBTYPE_Leergutausgabe = "ES";
	/** GS - Retoure = CS */
	public static final String DOCSUBTYPE_GS_Retoure = "CS";
	/** VendorInvoice = VI */
	public static final String DOCSUBTYPE_VendorInvoice = "VI";
	/** DownPayment = DP */
	public static final String DOCSUBTYPE_DownPayment = "DP";
	/** Saldokorektur = EC */
	public static final String DOCSUBTYPE_Saldokorektur = "EC";
	/** Internal Use Inventory = IUI */
	public static final String DOCSUBTYPE_InternalUseInventory = "IUI";
	/** Rückvergütungsrechnung = RI */
	public static final String DOCSUBTYPE_Rueckverguetungsrechnung = "RI";
	/** Rückvergütungsgutschrift = RC */
	public static final String DOCSUBTYPE_Rueckverguetungsgutschrift = "RC";
	/** Healthcare_CH-GM = GM */
	public static final String DOCSUBTYPE_Healthcare_CH_GM = "GM";
	/** Healthcare_CH-EA = EA */
	public static final String DOCSUBTYPE_Healthcare_CH_EA = "EA";
	/** Healthcare_CH-KV = KV */
	public static final String DOCSUBTYPE_Healthcare_CH_KV = "KV";
	/** Healthcare_CH-KT = KT */
	public static final String DOCSUBTYPE_Healthcare_CH_KT = "KT";
	/** AggregatedHUInventory = IAH */
	public static final String DOCSUBTYPE_AggregatedHUInventory = "IAH";
	/** SingleHUInventory = ISH */
	public static final String DOCSUBTYPE_SingleHUInventory = "ISH";
	/** NAR = NAR */
	public static final String DOCSUBTYPE_NAR = "NAR";
	/** Cashbook = CB */
	public static final String DOCSUBTYPE_Cashbook = "CB";
	/** Bankstatement = BS */
	public static final String DOCSUBTYPE_Bankstatement = "BS";
	/** Virtual inventory = VIY */
	public static final String DOCSUBTYPE_VirtualInventory = "VIY";
	/** SR = SR */
	public static final String DOCSUBTYPE_SR = "SR";
	/** Requisition = REQ */
	public static final String DOCSUBTYPE_Requisition = "REQ";
	/** Frame Agrement = FA */
	public static final String DOCSUBTYPE_FrameAgrement = "FA";
	/** Order Call = OC */
	public static final String DOCSUBTYPE_OrderCall = "OC";
	/** Mediated = MED */
	public static final String DOCSUBTYPE_Mediated = "MED";
	/** RD = RD */
	public static final String DOCSUBTYPE_RD = "RD";
	/** Cost Estimate = CE */
	public static final String DOCSUBTYPE_CostEstimate = "CE";
	/** Kreditoren Nachbelastung = NBK */
	public static final String DOCSUBTYPE_KreditorenNachbelastung = "NBK";
	/** LS = LS */
	public static final String DOCSUBTYPE_LS = "LS";
	/** Payment service provider invoice = SI */
	public static final String DOCSUBTYPE_PaymentServiceProviderInvoice = "SI";
	/** CallOrder = CAO */
	public static final String DOCSUBTYPE_CallOrder = "CAO";
	/** Delivery Instruction = DI */
	public static final String DOCSUBTYPE_DeliveryInstruction = "DI";
	@Override
	public void setDocSubType (final @Nullable java.lang.String DocSubType)
	{
		set_Value (COLUMNNAME_DocSubType, DocSubType);
	}

	@Override
	public java.lang.String getDocSubType() 
	{
		return get_ValueAsString(COLUMNNAME_DocSubType);
	}

	@Override
	public void setDocumentCopies (final int DocumentCopies)
	{
		set_Value (COLUMNNAME_DocumentCopies, DocumentCopies);
	}

	@Override
	public int getDocumentCopies() 
	{
		return get_ValueAsInt(COLUMNNAME_DocumentCopies);
	}

	@Override
	public void setDocumentNote (final @Nullable java.lang.String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	@Override
	public java.lang.String getDocumentNote() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNote);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public org.compiere.model.I_GL_Category getGL_Category()
	{
		return get_ValueAsPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class);
	}

	@Override
	public void setGL_Category(final org.compiere.model.I_GL_Category GL_Category)
	{
		set_ValueFromPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class, GL_Category);
	}

	@Override
	public void setGL_Category_ID (final int GL_Category_ID)
	{
		if (GL_Category_ID < 1) 
			set_Value (COLUMNNAME_GL_Category_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Category_ID, GL_Category_ID);
	}

	@Override
	public int getGL_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_Category_ID);
	}

	@Override
	public void setHasCharges (final boolean HasCharges)
	{
		set_Value (COLUMNNAME_HasCharges, HasCharges);
	}

	@Override
	public boolean isHasCharges() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasCharges);
	}

	@Override
	public void setHasProforma (final boolean HasProforma)
	{
		set_Value (COLUMNNAME_HasProforma, HasProforma);
	}

	@Override
	public boolean isHasProforma() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasProforma);
	}

	@Override
	public void setIsCopyDescriptionToDocument (final boolean IsCopyDescriptionToDocument)
	{
		set_Value (COLUMNNAME_IsCopyDescriptionToDocument, IsCopyDescriptionToDocument);
	}

	@Override
	public boolean isCopyDescriptionToDocument() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCopyDescriptionToDocument);
	}

	@Override
	public void setIsCreateCounter (final boolean IsCreateCounter)
	{
		set_Value (COLUMNNAME_IsCreateCounter, IsCreateCounter);
	}

	@Override
	public boolean isCreateCounter() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateCounter);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsDefaultCounterDoc (final boolean IsDefaultCounterDoc)
	{
		set_Value (COLUMNNAME_IsDefaultCounterDoc, IsDefaultCounterDoc);
	}

	@Override
	public boolean isDefaultCounterDoc() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultCounterDoc);
	}

	@Override
	public void setIsDocNoControlled (final boolean IsDocNoControlled)
	{
		set_Value (COLUMNNAME_IsDocNoControlled, IsDocNoControlled);
	}

	@Override
	public boolean isDocNoControlled() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDocNoControlled);
	}

	@Override
	public void setIsExcludeFromCommision (final boolean IsExcludeFromCommision)
	{
		set_Value (COLUMNNAME_IsExcludeFromCommision, IsExcludeFromCommision);
	}

	@Override
	public boolean isExcludeFromCommision() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsExcludeFromCommision);
	}

	@Override
	public void setIsIndexed (final boolean IsIndexed)
	{
		set_Value (COLUMNNAME_IsIndexed, IsIndexed);
	}

	@Override
	public boolean isIndexed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsIndexed);
	}

	@Override
	public void setIsInTransit (final boolean IsInTransit)
	{
		set_Value (COLUMNNAME_IsInTransit, IsInTransit);
	}

	@Override
	public boolean isInTransit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInTransit);
	}

	@Override
	public void setIsOverwriteDateOnComplete (final boolean IsOverwriteDateOnComplete)
	{
		set_Value (COLUMNNAME_IsOverwriteDateOnComplete, IsOverwriteDateOnComplete);
	}

	@Override
	public boolean isOverwriteDateOnComplete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOverwriteDateOnComplete);
	}

	@Override
	public void setIsOverwriteSeqOnComplete (final boolean IsOverwriteSeqOnComplete)
	{
		set_Value (COLUMNNAME_IsOverwriteSeqOnComplete, IsOverwriteSeqOnComplete);
	}

	@Override
	public boolean isOverwriteSeqOnComplete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOverwriteSeqOnComplete);
	}

	@Override
	public void setIsPickQAConfirm (final boolean IsPickQAConfirm)
	{
		set_Value (COLUMNNAME_IsPickQAConfirm, IsPickQAConfirm);
	}

	@Override
	public boolean isPickQAConfirm() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPickQAConfirm);
	}

	@Override
	public void setIsShipConfirm (final boolean IsShipConfirm)
	{
		set_Value (COLUMNNAME_IsShipConfirm, IsShipConfirm);
	}

	@Override
	public boolean isShipConfirm() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShipConfirm);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setIsSplitWhenDifference (final boolean IsSplitWhenDifference)
	{
		set_Value (COLUMNNAME_IsSplitWhenDifference, IsSplitWhenDifference);
	}

	@Override
	public boolean isSplitWhenDifference() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSplitWhenDifference);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getLotNo_Sequence()
	{
		return get_ValueAsPO(COLUMNNAME_LotNo_Sequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setLotNo_Sequence(final org.compiere.model.I_AD_Sequence LotNo_Sequence)
	{
		set_ValueFromPO(COLUMNNAME_LotNo_Sequence_ID, org.compiere.model.I_AD_Sequence.class, LotNo_Sequence);
	}

	@Override
	public void setLotNo_Sequence_ID (final int LotNo_Sequence_ID)
	{
		if (LotNo_Sequence_ID < 1)
			set_Value (COLUMNNAME_LotNo_Sequence_ID, null);
		else
			set_Value (COLUMNNAME_LotNo_Sequence_ID, LotNo_Sequence_ID);
	}

	@Override
	public int getLotNo_Sequence_ID()
	{
		return get_ValueAsInt(COLUMNNAME_LotNo_Sequence_ID);
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

	@Override
	public void setPrintName (final java.lang.String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	@Override
	public java.lang.String getPrintName() 
	{
		return get_ValueAsString(COLUMNNAME_PrintName);
	}

	@Override
	public org.compiere.model.I_R_RequestType getR_RequestType()
	{
		return get_ValueAsPO(COLUMNNAME_R_RequestType_ID, org.compiere.model.I_R_RequestType.class);
	}

	@Override
	public void setR_RequestType(final org.compiere.model.I_R_RequestType R_RequestType)
	{
		set_ValueFromPO(COLUMNNAME_R_RequestType_ID, org.compiere.model.I_R_RequestType.class, R_RequestType);
	}

	@Override
	public void setR_RequestType_ID (final int R_RequestType_ID)
	{
		if (R_RequestType_ID < 1) 
			set_Value (COLUMNNAME_R_RequestType_ID, null);
		else 
			set_Value (COLUMNNAME_R_RequestType_ID, R_RequestType_ID);
	}

	@Override
	public int getR_RequestType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_RequestType_ID);
	}
}