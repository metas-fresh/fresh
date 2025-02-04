
package at.erpel.schemas._1p0.documents;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetailsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}HeaderDescription" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}ItemList" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}FooterDescription" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailsType", propOrder = {
    "headerDescription",
    "itemList",
    "footerDescription"
})
public class DetailsType {

    @XmlElement(name = "HeaderDescription")
    protected String headerDescription;
    @XmlElement(name = "ItemList")
    protected List<ItemListType> itemList;
    @XmlElement(name = "FooterDescription")
    protected String footerDescription;

    /**
     * A free-text header description with precedes the details section.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeaderDescription() {
        return headerDescription;
    }

    /**
     * Sets the value of the headerDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeaderDescription(String value) {
        this.headerDescription = value;
    }

    /**
     * A list of different line items. Used to group line items of a certain kind together.Gets the value of the itemList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemListType }
     * 
     * 
     */
    public List<ItemListType> getItemList() {
        if (itemList == null) {
            itemList = new ArrayList<ItemListType>();
        }
        return this.itemList;
    }

    /**
     * A free-text footer description with suceeds the details section.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFooterDescription() {
        return footerDescription;
    }

    /**
     * Sets the value of the footerDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFooterDescription(String value) {
        this.footerDescription = value;
    }

}
