//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.03.05 à 11:11:32 AM CET 
//


package com.gg.proj.service.loans;

import javax.xml.bind.annotation.*;


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
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tokenUUID",
        "id"
})
@XmlRootElement(name = "closeLoanRequest")
public class CloseLoanRequest {

    @XmlElement(required = true)
    protected String tokenUUID;
    protected int id;

    /**
     * Obtient la valeur de la propriété tokenUUID.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTokenUUID() {
        return tokenUUID;
    }

    /**
     * Définit la valeur de la propriété tokenUUID.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTokenUUID(String value) {
        this.tokenUUID = value;
    }

    /**
     * Obtient la valeur de la propriété id.
     */
    public int getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     */
    public void setId(int value) {
        this.id = value;
    }

}
