//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.3-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.10.23 at 11:22:55 PM EDT 
//


package javaaxp.core.service.model.document.page;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for ST_TileMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ST_TileMode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Tile"/>
 *     &lt;enumeration value="FlipX"/>
 *     &lt;enumeration value="FlipY"/>
 *     &lt;enumeration value="FlipXY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum STTileMode {

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Tile")
    TILE("Tile"),
    @XmlEnumValue("FlipX")
    FLIP_X("FlipX"),
    @XmlEnumValue("FlipY")
    FLIP_Y("FlipY"),
    @XmlEnumValue("FlipXY")
    FLIP_XY("FlipXY");
    private final String value;

    STTileMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static STTileMode fromValue(String v) {
        for (STTileMode c: STTileMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
