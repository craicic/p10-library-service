//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.02.28 à 06:34:24 PM CET 
//


package com.gg.proj.service.books;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tokenUUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bookMin" type="{http://proj.gg.com/service/books}bookMin"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tokenUUID",
    "bookMin"
})
@XmlRootElement(name = "createBookRequest")
public class CreateBookRequest {

    @XmlElement(required = true)
    protected String tokenUUID;
    @XmlElement(required = true)
    protected BookMin bookMin;

    /**
     * Obtient la valeur de la propriété tokenUUID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenUUID() {
        return tokenUUID;
    }

    /**
     * Définit la valeur de la propriété tokenUUID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenUUID(String value) {
        this.tokenUUID = value;
    }

    /**
     * Obtient la valeur de la propriété bookMin.
     * 
     * @return
     *     possible object is
     *     {@link BookMin }
     *     
     */
    public BookMin getBookMin() {
        return bookMin;
    }

    /**
     * Définit la valeur de la propriété bookMin.
     * 
     * @param value
     *     allowed object is
     *     {@link BookMin }
     *     
     */
    public void setBookMin(BookMin value) {
        this.bookMin = value;
    }

}
