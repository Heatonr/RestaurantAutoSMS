//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.10.23 at 11:22:55 PM EDT 
//


package javaaxp.core.service.impl.document.jaxb;

import javaaxp.core.service.model.document.IOutlineEntry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CT_OutlineEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CT_OutlineEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://schemas.microsoft.com/xps/2005/06/documentstructure}AG_OutlineEntry"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CT_OutlineEntry", namespace = "http://schemas.microsoft.com/xps/2005/06/documentstructure")
public class CTOutlineEntry implements IOutlineEntry {

    @XmlAttribute(name = "Description", required = true)
    protected String description;
    @XmlAttribute(name = "OutlineLevel")
    protected Integer outlineLevel;
    @XmlAttribute(name = "OutlineTarget", required = true)
    protected String outlineTarget;
    @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;

    /* (non-Javadoc)
	 * @see xps.impl.document.jaxb.IOutlineEntry#getDescription()
	 */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /* (non-Javadoc)
	 * @see xps.impl.document.jaxb.IOutlineEntry#getOutlineLevel()
	 */
    public int getOutlineLevel() {
        if (outlineLevel == null) {
            return  1;
        } else {
            return outlineLevel;
        }
    }

    /**
     * Sets the value of the outlineLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOutlineLevel(Integer value) {
        this.outlineLevel = value;
    }

    /* (non-Javadoc)
	 * @see xps.impl.document.jaxb.IOutlineEntry#getOutlineTarget()
	 */
    public String getOutlineTarget() {
        return outlineTarget;
    }

    /**
     * Sets the value of the outlineTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlineTarget(String value) {
        this.outlineTarget = value;
    }

    /* (non-Javadoc)
	 * @see xps.impl.document.jaxb.IOutlineEntry#getLang()
	 */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

}
