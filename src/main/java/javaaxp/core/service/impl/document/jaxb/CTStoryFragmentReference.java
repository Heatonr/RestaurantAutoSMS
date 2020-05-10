//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.10.23 at 11:22:55 PM EDT 
//


package javaaxp.core.service.impl.document.jaxb;

import javaaxp.core.service.model.document.IStoryFragment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CT_StoryFragmentReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CT_StoryFragmentReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{http://schemas.microsoft.com/xps/2005/06/documentstructure}AG_StoryFragmentReference"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CT_StoryFragmentReference", namespace = "http://schemas.microsoft.com/xps/2005/06/documentstructure")
public class CTStoryFragmentReference implements IStoryFragment {

    @XmlAttribute(name = "FragmentName")
    protected String fragmentName;
    @XmlAttribute(name = "Page", required = true)
    protected int page;

    public CTStoryFragmentReference() {
    	
    }
    
    /* (non-Javadoc)
	 * @see xps.impl.document.jaxb.IStroryFragment#getFragmentName()
	 */
    public String getFragmentName() {
        return fragmentName;
    }

    /**
     * Sets the value of the fragmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFragmentName(String value) {
        this.fragmentName = value;
    }

    /* (non-Javadoc)
	 * @see xps.impl.document.jaxb.IStroryFragment#getPage()
	 */
    public int getPage() {
        return page;
    }

    /**
     * Sets the value of the page property.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

}
