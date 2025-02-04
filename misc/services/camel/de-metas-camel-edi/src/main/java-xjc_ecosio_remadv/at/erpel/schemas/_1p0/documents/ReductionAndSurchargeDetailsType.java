
package at.erpel.schemas._1p0.documents;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;
import at.erpel.schemas._1p0.documents.ext.ReductionAndSurchargeDetailsExtensionType;


/**
 * <p>Java class for ReductionAndSurchargeDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReductionAndSurchargeDetailsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element ref="{http://erpel.at/schemas/1p0/documents}Reduction" minOccurs="0"/&gt;
 *           &lt;element ref="{http://erpel.at/schemas/1p0/documents}Surcharge" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}TotalOfReductionAndSurcharge" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}ReductionAndSurchargeDetailsExtension" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReductionAndSurchargeDetailsType", propOrder = {
    "reductionOrSurcharge",
    "totalOfReductionAndSurcharge",
    "reductionAndSurchargeDetailsExtension"
})
public class ReductionAndSurchargeDetailsType {

    @XmlElementRefs({
        @XmlElementRef(name = "Reduction", namespace = "http://erpel.at/schemas/1p0/documents", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Surcharge", namespace = "http://erpel.at/schemas/1p0/documents", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<ReductionAndSurchargeType>> reductionOrSurcharge;
    @XmlElement(name = "TotalOfReductionAndSurcharge")
    protected BigDecimal totalOfReductionAndSurcharge;
    @XmlElement(name = "ReductionAndSurchargeDetailsExtension", namespace = "http://erpel.at/schemas/1p0/documents/ext")
    protected ReductionAndSurchargeDetailsExtensionType reductionAndSurchargeDetailsExtension;

    /**
     * Gets the value of the reductionOrSurcharge property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reductionOrSurcharge property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReductionOrSurcharge().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link ReductionAndSurchargeType }{@code >}
     * {@link JAXBElement }{@code <}{@link ReductionAndSurchargeType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<ReductionAndSurchargeType>> getReductionOrSurcharge() {
        if (reductionOrSurcharge == null) {
            reductionOrSurcharge = new ArrayList<JAXBElement<ReductionAndSurchargeType>>();
        }
        return this.reductionOrSurcharge;
    }

    /**
     * Gets the value of the totalOfReductionAndSurcharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalOfReductionAndSurcharge() {
        return totalOfReductionAndSurcharge;
    }

    /**
     * Sets the value of the totalOfReductionAndSurcharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalOfReductionAndSurcharge(BigDecimal value) {
        this.totalOfReductionAndSurcharge = value;
    }

    /**
     * Gets the value of the reductionAndSurchargeDetailsExtension property.
     * 
     * @return
     *     possible object is
     *     {@link ReductionAndSurchargeDetailsExtensionType }
     *     
     */
    public ReductionAndSurchargeDetailsExtensionType getReductionAndSurchargeDetailsExtension() {
        return reductionAndSurchargeDetailsExtension;
    }

    /**
     * Sets the value of the reductionAndSurchargeDetailsExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReductionAndSurchargeDetailsExtensionType }
     *     
     */
    public void setReductionAndSurchargeDetailsExtension(ReductionAndSurchargeDetailsExtensionType value) {
        this.reductionAndSurchargeDetailsExtension = value;
    }

}
