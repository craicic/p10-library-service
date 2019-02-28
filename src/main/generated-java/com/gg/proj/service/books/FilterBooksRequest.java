//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.02.28 à 06:34:24 PM CET 
//


package com.gg.proj.service.books;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="page" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="keyWord" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="libraryId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="topicId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="languageId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="available" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "page",
    "size",
    "keyWord",
    "libraryId",
    "topicId",
    "languageId",
    "available"
})
@XmlRootElement(name = "filterBooksRequest")
public class FilterBooksRequest {

    protected int page;
    protected int size;
    protected String keyWord;
    protected Integer libraryId;
    protected Integer topicId;
    protected Integer languageId;
    protected boolean available;

    /**
     * Obtient la valeur de la propriété page.
     * 
     */
    public int getPage() {
        return page;
    }

    /**
     * Définit la valeur de la propriété page.
     * 
     */
    public void setPage(int value) {
        this.page = value;
    }

    /**
     * Obtient la valeur de la propriété size.
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * Définit la valeur de la propriété size.
     * 
     */
    public void setSize(int value) {
        this.size = value;
    }

    /**
     * Obtient la valeur de la propriété keyWord.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * Définit la valeur de la propriété keyWord.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyWord(String value) {
        this.keyWord = value;
    }

    /**
     * Obtient la valeur de la propriété libraryId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLibraryId() {
        return libraryId;
    }

    /**
     * Définit la valeur de la propriété libraryId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLibraryId(Integer value) {
        this.libraryId = value;
    }

    /**
     * Obtient la valeur de la propriété topicId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTopicId() {
        return topicId;
    }

    /**
     * Définit la valeur de la propriété topicId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTopicId(Integer value) {
        this.topicId = value;
    }

    /**
     * Obtient la valeur de la propriété languageId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLanguageId() {
        return languageId;
    }

    /**
     * Définit la valeur de la propriété languageId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLanguageId(Integer value) {
        this.languageId = value;
    }

    /**
     * Obtient la valeur de la propriété available.
     * 
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Définit la valeur de la propriété available.
     * 
     */
    public void setAvailable(boolean value) {
        this.available = value;
    }

}
