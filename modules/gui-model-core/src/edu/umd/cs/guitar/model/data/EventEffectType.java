//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.26 at 05:34:08 PM EDT 
//


package edu.umd.cs.guitar.model.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventEffectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EventEffectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EventId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EventSet" type="{}EventSetType"/>
 *         &lt;element name="EventSetDis" type="{}EventSetType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventEffectType", propOrder = {
    "eventId",
    "eventSet",
    "eventSetDis"
})
public class EventEffectType {

    @XmlElement(name = "EventId", required = true)
    protected String eventId;
    @XmlElement(name = "EventSet", required = true)
    protected EventSetType eventSet;
    @XmlElement(name = "EventSetDis", required = true)
    protected EventSetType eventSetDis;

    /**
     * Gets the value of the eventId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets the value of the eventId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventId(String value) {
        this.eventId = value;
    }

    /**
     * Gets the value of the eventSet property.
     * 
     * @return
     *     possible object is
     *     {@link EventSetType }
     *     
     */
    public EventSetType getEventSet() {
        return eventSet;
    }

    /**
     * Sets the value of the eventSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventSetType }
     *     
     */
    public void setEventSet(EventSetType value) {
        this.eventSet = value;
    }

    /**
     * Gets the value of the eventSetDis property.
     * 
     * @return
     *     possible object is
     *     {@link EventSetType }
     *     
     */
    public EventSetType getEventSetDis() {
        return eventSetDis;
    }

    /**
     * Sets the value of the eventSetDis property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventSetType }
     *     
     */
    public void setEventSetDis(EventSetType value) {
        this.eventSetDis = value;
    }

}
