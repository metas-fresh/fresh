//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.02.13 at 01:59:18 PM CET 
//


package de.metas.edi.esb.jaxb.metasfresh;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EDI_Exp_DesadvLine_PackType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EDI_Exp_DesadvLine_PackType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IPA_SSCC18" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LotNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="QtyCU" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="QtyCUsPerLU" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="QtyTU" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="BestBeforeDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="M_HU_PackagingCode_LU_Text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="M_HU_PackagingCode_TU_Text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="C_UOM_ID" type="{}EDI_Exp_C_UOMType" minOccurs="0"/&gt;
 *         &lt;element name="GTIN_LU_PackingMaterial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="GTIN_TU_PackingMaterial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EDI_Exp_DesadvLine_PackType", propOrder = {
    "ipasscc18",
    "lotNumber",
    "qtyCU",
    "qtyCUsPerLU",
    "qtyTU",
    "bestBeforeDate",
    "mhuPackagingCodeLUText",
    "mhuPackagingCodeTUText",
    "cuomid",
    "gtinluPackingMaterial",
    "gtintuPackingMaterial"
})
public class EDIExpDesadvLinePackType {

    @XmlElement(name = "IPA_SSCC18")
    protected String ipasscc18;
    @XmlElement(name = "LotNumber")
    protected String lotNumber;
    @XmlElement(name = "QtyCU")
    protected BigDecimal qtyCU;
    @XmlElement(name = "QtyCUsPerLU")
    protected BigDecimal qtyCUsPerLU;
    @XmlElement(name = "QtyTU")
    protected BigInteger qtyTU;
    @XmlElement(name = "BestBeforeDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar bestBeforeDate;
    @XmlElement(name = "M_HU_PackagingCode_LU_Text")
    protected String mhuPackagingCodeLUText;
    @XmlElement(name = "M_HU_PackagingCode_TU_Text")
    protected String mhuPackagingCodeTUText;
    @XmlElement(name = "C_UOM_ID")
    protected EDIExpCUOMType cuomid;
    @XmlElement(name = "GTIN_LU_PackingMaterial")
    protected String gtinluPackingMaterial;
    @XmlElement(name = "GTIN_TU_PackingMaterial")
    protected String gtintuPackingMaterial;

    /**
     * Gets the value of the ipasscc18 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIPASSCC18() {
        return ipasscc18;
    }

    /**
     * Sets the value of the ipasscc18 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIPASSCC18(String value) {
        this.ipasscc18 = value;
    }

    /**
     * Gets the value of the lotNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotNumber() {
        return lotNumber;
    }

    /**
     * Sets the value of the lotNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotNumber(String value) {
        this.lotNumber = value;
    }

    /**
     * Gets the value of the qtyCU property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQtyCU() {
        return qtyCU;
    }

    /**
     * Sets the value of the qtyCU property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQtyCU(BigDecimal value) {
        this.qtyCU = value;
    }

    /**
     * Gets the value of the qtyCUsPerLU property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQtyCUsPerLU() {
        return qtyCUsPerLU;
    }

    /**
     * Sets the value of the qtyCUsPerLU property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQtyCUsPerLU(BigDecimal value) {
        this.qtyCUsPerLU = value;
    }

    /**
     * Gets the value of the qtyTU property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQtyTU() {
        return qtyTU;
    }

    /**
     * Sets the value of the qtyTU property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQtyTU(BigInteger value) {
        this.qtyTU = value;
    }

    /**
     * Gets the value of the bestBeforeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBestBeforeDate() {
        return bestBeforeDate;
    }

    /**
     * Sets the value of the bestBeforeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBestBeforeDate(XMLGregorianCalendar value) {
        this.bestBeforeDate = value;
    }

    /**
     * Gets the value of the mhuPackagingCodeLUText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMHUPackagingCodeLUText() {
        return mhuPackagingCodeLUText;
    }

    /**
     * Sets the value of the mhuPackagingCodeLUText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMHUPackagingCodeLUText(String value) {
        this.mhuPackagingCodeLUText = value;
    }

    /**
     * Gets the value of the mhuPackagingCodeTUText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMHUPackagingCodeTUText() {
        return mhuPackagingCodeTUText;
    }

    /**
     * Sets the value of the mhuPackagingCodeTUText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMHUPackagingCodeTUText(String value) {
        this.mhuPackagingCodeTUText = value;
    }

    /**
     * Gets the value of the cuomid property.
     * 
     * @return
     *     possible object is
     *     {@link EDIExpCUOMType }
     *     
     */
    public EDIExpCUOMType getCUOMID() {
        return cuomid;
    }

    /**
     * Sets the value of the cuomid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EDIExpCUOMType }
     *     
     */
    public void setCUOMID(EDIExpCUOMType value) {
        this.cuomid = value;
    }

    /**
     * Gets the value of the gtinluPackingMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGTINLUPackingMaterial() {
        return gtinluPackingMaterial;
    }

    /**
     * Sets the value of the gtinluPackingMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGTINLUPackingMaterial(String value) {
        this.gtinluPackingMaterial = value;
    }

    /**
     * Gets the value of the gtintuPackingMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGTINTUPackingMaterial() {
        return gtintuPackingMaterial;
    }

    /**
     * Sets the value of the gtintuPackingMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGTINTUPackingMaterial(String value) {
        this.gtintuPackingMaterial = value;
    }

}