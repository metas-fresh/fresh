
package de.metas.edi.esb.jaxb.stepcom.orders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DQUAN1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DQUAN1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DOCUMENTID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LINENUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QUANTITYQUAL" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QUANTITY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MEASUREMENTUNIT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DMKDQ1" type="{}DMKDQ1" minOccurs="0"/&gt;
 *         &lt;element name="DPLDQ1" type="{}DPLDQ1" minOccurs="0"/&gt;
 *         &lt;element name="DDTDQ1" type="{}DDTDQ1" minOccurs="0"/&gt;
 *         &lt;element name="DPRDQ1" type="{}DPRDQ1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DQUAN1", propOrder = {
    "documentid",
    "linenumber",
    "quantityqual",
    "quantity",
    "measurementunit",
    "dmkdq1",
    "dpldq1",
    "ddtdq1",
    "dprdq1"
})
public class DQUAN1 {

    @XmlElement(name = "DOCUMENTID", required = true)
    protected String documentid;
    @XmlElement(name = "LINENUMBER", required = true)
    protected String linenumber;
    @XmlElement(name = "QUANTITYQUAL", required = true)
    protected String quantityqual;
    @XmlElement(name = "QUANTITY", required = true)
    protected String quantity;
    @XmlElement(name = "MEASUREMENTUNIT")
    protected String measurementunit;
    @XmlElement(name = "DMKDQ1")
    protected DMKDQ1 dmkdq1;
    @XmlElement(name = "DPLDQ1")
    protected DPLDQ1 dpldq1;
    @XmlElement(name = "DDTDQ1")
    protected DDTDQ1 ddtdq1;
    @XmlElement(name = "DPRDQ1")
    protected DPRDQ1 dprdq1;

    /**
     * Gets the value of the documentid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCUMENTID() {
        return documentid;
    }

    /**
     * Sets the value of the documentid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCUMENTID(String value) {
        this.documentid = value;
    }

    /**
     * Gets the value of the linenumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLINENUMBER() {
        return linenumber;
    }

    /**
     * Sets the value of the linenumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLINENUMBER(String value) {
        this.linenumber = value;
    }

    /**
     * Gets the value of the quantityqual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUANTITYQUAL() {
        return quantityqual;
    }

    /**
     * Sets the value of the quantityqual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUANTITYQUAL(String value) {
        this.quantityqual = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUANTITY() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUANTITY(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the measurementunit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMEASUREMENTUNIT() {
        return measurementunit;
    }

    /**
     * Sets the value of the measurementunit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMEASUREMENTUNIT(String value) {
        this.measurementunit = value;
    }

    /**
     * Gets the value of the dmkdq1 property.
     * 
     * @return
     *     possible object is
     *     {@link DMKDQ1 }
     *     
     */
    public DMKDQ1 getDMKDQ1() {
        return dmkdq1;
    }

    /**
     * Sets the value of the dmkdq1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DMKDQ1 }
     *     
     */
    public void setDMKDQ1(DMKDQ1 value) {
        this.dmkdq1 = value;
    }

    /**
     * Gets the value of the dpldq1 property.
     * 
     * @return
     *     possible object is
     *     {@link DPLDQ1 }
     *     
     */
    public DPLDQ1 getDPLDQ1() {
        return dpldq1;
    }

    /**
     * Sets the value of the dpldq1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DPLDQ1 }
     *     
     */
    public void setDPLDQ1(DPLDQ1 value) {
        this.dpldq1 = value;
    }

    /**
     * Gets the value of the ddtdq1 property.
     * 
     * @return
     *     possible object is
     *     {@link DDTDQ1 }
     *     
     */
    public DDTDQ1 getDDTDQ1() {
        return ddtdq1;
    }

    /**
     * Sets the value of the ddtdq1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DDTDQ1 }
     *     
     */
    public void setDDTDQ1(DDTDQ1 value) {
        this.ddtdq1 = value;
    }

    /**
     * Gets the value of the dprdq1 property.
     * 
     * @return
     *     possible object is
     *     {@link DPRDQ1 }
     *     
     */
    public DPRDQ1 getDPRDQ1() {
        return dprdq1;
    }

    /**
     * Sets the value of the dprdq1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DPRDQ1 }
     *     
     */
    public void setDPRDQ1(DPRDQ1 value) {
        this.dprdq1 = value;
    }

}
