//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.03.05 à 10:43:59 AM CET 
//


package com.gg.proj.service.books;

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
 *         &lt;element name="languageName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "tokenUUID",
        "languageName"
})
@XmlRootElement(name = "createLanguageRequest")
public class CreateLanguageRequest {

    @XmlElement(required = true)
    protected String tokenUUID;
    @XmlElement(required = true)
    protected String languageName;

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
     * Obtient la valeur de la propriété languageName.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * Définit la valeur de la propriété languageName.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLanguageName(String value) {
        this.languageName = value;
    }

}
