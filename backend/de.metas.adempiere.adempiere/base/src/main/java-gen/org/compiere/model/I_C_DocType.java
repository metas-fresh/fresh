package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_DocType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_DocType 
{

	String Table_Name = "C_DocType";

//	/** AD_Table_ID=217 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Boiler Plate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/**
	 * Get Boiler Plate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_BoilerPlate_ID();

	ModelColumn<I_C_DocType, Object> COLUMN_AD_BoilerPlate_ID = new ModelColumn<>(I_C_DocType.class, "AD_BoilerPlate_ID", null);
	String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Print Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Print Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PrintFormat_ID();

	@Nullable org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	void setAD_PrintFormat(@Nullable org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

	ModelColumn<I_C_DocType, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new ModelColumn<>(I_C_DocType.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
	String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set Difference Document.
	 * Document type for generating in dispute Shipments
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeDifference_ID (int C_DocTypeDifference_ID);

	/**
	 * Get Difference Document.
	 * Document type for generating in dispute Shipments
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeDifference_ID();

	String COLUMNNAME_C_DocTypeDifference_ID = "C_DocTypeDifference_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	ModelColumn<I_C_DocType, Object> COLUMN_C_DocType_ID = new ModelColumn<>(I_C_DocType.class, "C_DocType_ID", null);
	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set DocType Invoice.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeInvoice_ID (int C_DocTypeInvoice_ID);

	/**
	 * Get DocType Invoice.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeInvoice_ID();

	String COLUMNNAME_C_DocTypeInvoice_ID = "C_DocTypeInvoice_ID";

	/**
	 * Set Document Type for ProForma.
	 * Document type used for pro forma invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeProforma_ID (int C_DocTypeProforma_ID);

	/**
	 * Get Document Type for ProForma.
	 * Document type used for pro forma invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeProforma_ID();

	String COLUMNNAME_C_DocTypeProforma_ID = "C_DocTypeProforma_ID";

	/**
	 * Set Document Type for Shipment.
	 * Document type used for shipments generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeShipment_ID (int C_DocTypeShipment_ID);

	/**
	 * Get Document Type for Shipment.
	 * Document type used for shipments generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeShipment_ID();

	String COLUMNNAME_C_DocTypeShipment_ID = "C_DocTypeShipment_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_DocType, Object> COLUMN_Created = new ModelColumn<>(I_C_DocType.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Definite Sequence.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefiniteSequence_ID (int DefiniteSequence_ID);

	/**
	 * Get Definite Sequence.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDefiniteSequence_ID();

	@Nullable org.compiere.model.I_AD_Sequence getDefiniteSequence();

	void setDefiniteSequence(@Nullable org.compiere.model.I_AD_Sequence DefiniteSequence);

	ModelColumn<I_C_DocType, org.compiere.model.I_AD_Sequence> COLUMN_DefiniteSequence_ID = new ModelColumn<>(I_C_DocType.class, "DefiniteSequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_DefiniteSequence_ID = "DefiniteSequence_ID";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_DocType, Object> COLUMN_Description = new ModelColumn<>(I_C_DocType.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocBaseType (java.lang.String DocBaseType);

	/**
	 * Get Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocBaseType();

	ModelColumn<I_C_DocType, Object> COLUMN_DocBaseType = new ModelColumn<>(I_C_DocType.class, "DocBaseType", null);
	String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Document Sequence.
	 * Document sequence determines the numbering of documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocNoSequence_ID (int DocNoSequence_ID);

	/**
	 * Get Document Sequence.
	 * Document sequence determines the numbering of documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDocNoSequence_ID();

	@Nullable org.compiere.model.I_AD_Sequence getDocNoSequence();

	void setDocNoSequence(@Nullable org.compiere.model.I_AD_Sequence DocNoSequence);

	ModelColumn<I_C_DocType, org.compiere.model.I_AD_Sequence> COLUMN_DocNoSequence_ID = new ModelColumn<>(I_C_DocType.class, "DocNoSequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_DocNoSequence_ID = "DocNoSequence_ID";

	/**
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocSubType (@Nullable java.lang.String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocSubType();

	ModelColumn<I_C_DocType, Object> COLUMN_DocSubType = new ModelColumn<>(I_C_DocType.class, "DocSubType", null);
	String COLUMNNAME_DocSubType = "DocSubType";

	/**
	 * Set Document Copies.
	 * Number of copies to be printed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentCopies (int DocumentCopies);

	/**
	 * Get Document Copies.
	 * Number of copies to be printed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDocumentCopies();

	ModelColumn<I_C_DocType, Object> COLUMN_DocumentCopies = new ModelColumn<>(I_C_DocType.class, "DocumentCopies", null);
	String COLUMNNAME_DocumentCopies = "DocumentCopies";

	/**
	 * Set Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNote (@Nullable java.lang.String DocumentNote);

	/**
	 * Get Note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNote();

	ModelColumn<I_C_DocType, Object> COLUMN_DocumentNote = new ModelColumn<>(I_C_DocType.class, "DocumentNote", null);
	String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_C_DocType, Object> COLUMN_EntityType = new ModelColumn<>(I_C_DocType.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set GL Category.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_Category_ID (int GL_Category_ID);

	/**
	 * Get GL Category.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_Category_ID();

	org.compiere.model.I_GL_Category getGL_Category();

	void setGL_Category(org.compiere.model.I_GL_Category GL_Category);

	ModelColumn<I_C_DocType, org.compiere.model.I_GL_Category> COLUMN_GL_Category_ID = new ModelColumn<>(I_C_DocType.class, "GL_Category_ID", org.compiere.model.I_GL_Category.class);
	String COLUMNNAME_GL_Category_ID = "GL_Category_ID";

	/**
	 * Set Charges.
	 * Charges can be added to the document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHasCharges (boolean HasCharges);

	/**
	 * Get Charges.
	 * Charges can be added to the document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHasCharges();

	ModelColumn<I_C_DocType, Object> COLUMN_HasCharges = new ModelColumn<>(I_C_DocType.class, "HasCharges", null);
	String COLUMNNAME_HasCharges = "HasCharges";

	/**
	 * Set Pro forma Invoice.
	 * Indicates if Pro Forma Invoices can be generated from this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHasProforma (boolean HasProforma);

	/**
	 * Get Pro forma Invoice.
	 * Indicates if Pro Forma Invoices can be generated from this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isHasProforma();

	ModelColumn<I_C_DocType, Object> COLUMN_HasProforma = new ModelColumn<>(I_C_DocType.class, "HasProforma", null);
	String COLUMNNAME_HasProforma = "HasProforma";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_DocType, Object> COLUMN_IsActive = new ModelColumn<>(I_C_DocType.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Copy description to document.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCopyDescriptionToDocument (boolean IsCopyDescriptionToDocument);

	/**
	 * Get Copy description to document.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCopyDescriptionToDocument();

	ModelColumn<I_C_DocType, Object> COLUMN_IsCopyDescriptionToDocument = new ModelColumn<>(I_C_DocType.class, "IsCopyDescriptionToDocument", null);
	String COLUMNNAME_IsCopyDescriptionToDocument = "IsCopyDescriptionToDocument";

	/**
	 * Set Create Counter Document.
	 * Create Counter Document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreateCounter (boolean IsCreateCounter);

	/**
	 * Get Create Counter Document.
	 * Create Counter Document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreateCounter();

	ModelColumn<I_C_DocType, Object> COLUMN_IsCreateCounter = new ModelColumn<>(I_C_DocType.class, "IsCreateCounter", null);
	String COLUMNNAME_IsCreateCounter = "IsCreateCounter";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_C_DocType, Object> COLUMN_IsDefault = new ModelColumn<>(I_C_DocType.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Default Counter Document.
	 * The document type is the default counter document type
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefaultCounterDoc (boolean IsDefaultCounterDoc);

	/**
	 * Get Default Counter Document.
	 * The document type is the default counter document type
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefaultCounterDoc();

	ModelColumn<I_C_DocType, Object> COLUMN_IsDefaultCounterDoc = new ModelColumn<>(I_C_DocType.class, "IsDefaultCounterDoc", null);
	String COLUMNNAME_IsDefaultCounterDoc = "IsDefaultCounterDoc";

	/**
	 * Set Document is Number Controlled.
	 * The document has a document sequence
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDocNoControlled (boolean IsDocNoControlled);

	/**
	 * Get Document is Number Controlled.
	 * The document has a document sequence
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDocNoControlled();

	ModelColumn<I_C_DocType, Object> COLUMN_IsDocNoControlled = new ModelColumn<>(I_C_DocType.class, "IsDocNoControlled", null);
	String COLUMNNAME_IsDocNoControlled = "IsDocNoControlled";

	/**
	 * Set Exclude from commission.
	 * When activated, no commission instances will be created for the respective document types.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExcludeFromCommision (boolean IsExcludeFromCommision);

	/**
	 * Get Exclude from commission.
	 * When activated, no commission instances will be created for the respective document types.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeFromCommision();

	ModelColumn<I_C_DocType, Object> COLUMN_IsExcludeFromCommision = new ModelColumn<>(I_C_DocType.class, "IsExcludeFromCommision", null);
	String COLUMNNAME_IsExcludeFromCommision = "IsExcludeFromCommision";

	/**
	 * Set Indexed.
	 * Index the document for the internal search engine
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsIndexed (boolean IsIndexed);

	/**
	 * Get Indexed.
	 * Index the document for the internal search engine
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isIndexed();

	ModelColumn<I_C_DocType, Object> COLUMN_IsIndexed = new ModelColumn<>(I_C_DocType.class, "IsIndexed", null);
	String COLUMNNAME_IsIndexed = "IsIndexed";

	/**
	 * Set In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInTransit (boolean IsInTransit);

	/**
	 * Get In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInTransit();

	ModelColumn<I_C_DocType, Object> COLUMN_IsInTransit = new ModelColumn<>(I_C_DocType.class, "IsInTransit", null);
	String COLUMNNAME_IsInTransit = "IsInTransit";

	/**
	 * Set Overwrite document date on complete.
	 * Überschreibt das Belegdatum mit dem aktuellen Datum, wenn der Beleg fertig gestellt wird.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsOverwriteDateOnComplete (boolean IsOverwriteDateOnComplete);

	/**
	 * Get Overwrite document date on complete.
	 * Überschreibt das Belegdatum mit dem aktuellen Datum, wenn der Beleg fertig gestellt wird.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteDateOnComplete();

	ModelColumn<I_C_DocType, Object> COLUMN_IsOverwriteDateOnComplete = new ModelColumn<>(I_C_DocType.class, "IsOverwriteDateOnComplete", null);
	String COLUMNNAME_IsOverwriteDateOnComplete = "IsOverwriteDateOnComplete";

	/**
	 * Set Overwrite Sequence on Complete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsOverwriteSeqOnComplete (boolean IsOverwriteSeqOnComplete);

	/**
	 * Get Overwrite Sequence on Complete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteSeqOnComplete();

	ModelColumn<I_C_DocType, Object> COLUMN_IsOverwriteSeqOnComplete = new ModelColumn<>(I_C_DocType.class, "IsOverwriteSeqOnComplete", null);
	String COLUMNNAME_IsOverwriteSeqOnComplete = "IsOverwriteSeqOnComplete";

	/**
	 * Set Pick/QA Confirmation.
	 * Require Pick or QA Confirmation before processing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickQAConfirm (boolean IsPickQAConfirm);

	/**
	 * Get Pick/QA Confirmation.
	 * Require Pick or QA Confirmation before processing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickQAConfirm();

	ModelColumn<I_C_DocType, Object> COLUMN_IsPickQAConfirm = new ModelColumn<>(I_C_DocType.class, "IsPickQAConfirm", null);
	String COLUMNNAME_IsPickQAConfirm = "IsPickQAConfirm";

	/**
	 * Set Ship/Receipt Confirmation.
	 * Require Ship or Receipt Confirmation before processing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipConfirm (boolean IsShipConfirm);

	/**
	 * Get Ship/Receipt Confirmation.
	 * Require Ship or Receipt Confirmation before processing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShipConfirm();

	ModelColumn<I_C_DocType, Object> COLUMN_IsShipConfirm = new ModelColumn<>(I_C_DocType.class, "IsShipConfirm", null);
	String COLUMNNAME_IsShipConfirm = "IsShipConfirm";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_C_DocType, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_DocType.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Split when Difference.
	 * Split document when there is a difference
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSplitWhenDifference (boolean IsSplitWhenDifference);

	/**
	 * Get Split when Difference.
	 * Split document when there is a difference
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSplitWhenDifference();

	ModelColumn<I_C_DocType, Object> COLUMN_IsSplitWhenDifference = new ModelColumn<>(I_C_DocType.class, "IsSplitWhenDifference", null);
	String COLUMNNAME_IsSplitWhenDifference = "IsSplitWhenDifference";

	/**
	 * Set Serial No. Sequence.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLotNo_Sequence_ID (int LotNo_Sequence_ID);

	/**
	 * Get Serial No. Sequence.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLotNo_Sequence_ID();

	@Nullable org.compiere.model.I_AD_Sequence getLotNo_Sequence();

	void setLotNo_Sequence(@Nullable org.compiere.model.I_AD_Sequence LotNo_Sequence);

	ModelColumn<I_C_DocType, org.compiere.model.I_AD_Sequence> COLUMN_LotNo_Sequence_ID = new ModelColumn<>(I_C_DocType.class, "LotNo_Sequence_ID", org.compiere.model.I_AD_Sequence.class);
	String COLUMNNAME_LotNo_Sequence_ID = "LotNo_Sequence_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_DocType, Object> COLUMN_Name = new ModelColumn<>(I_C_DocType.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Print Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPrintName (java.lang.String PrintName);

	/**
	 * Get Print Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPrintName();

	ModelColumn<I_C_DocType, Object> COLUMN_PrintName = new ModelColumn<>(I_C_DocType.class, "PrintName", null);
	String COLUMNNAME_PrintName = "PrintName";

	/**
	 * Set Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_RequestType_ID (int R_RequestType_ID);

	/**
	 * Get Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_RequestType_ID();

	@Nullable org.compiere.model.I_R_RequestType getR_RequestType();

	void setR_RequestType(@Nullable org.compiere.model.I_R_RequestType R_RequestType);

	ModelColumn<I_C_DocType, org.compiere.model.I_R_RequestType> COLUMN_R_RequestType_ID = new ModelColumn<>(I_C_DocType.class, "R_RequestType_ID", org.compiere.model.I_R_RequestType.class);
	String COLUMNNAME_R_RequestType_ID = "R_RequestType_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_DocType, Object> COLUMN_Updated = new ModelColumn<>(I_C_DocType.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
