//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.02.03 à 04:39:11 PM CET 
//


package com.gg.proj.service.library;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="totalPages" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="books" type="{http://proj.gg.com/service/library}book" maxOccurs="unbounded"/>
 *         &lt;element name="library" type="{http://proj.gg.com/service/library}library"/>
 *         &lt;element name="topic" type="{http://proj.gg.com/service/library}topic"/>
 *         &lt;element name="language" type="{http://proj.gg.com/service/library}language"/>
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
    "totalPages",
    "books",
    "library",
    "topic",
    "language"
})
@XmlRootElement(name = "filterBooksResponse")
public class FilterBooksResponse {

    protected int totalPages;
    @XmlElement(required = true)
    protected List<Book> books;
    @XmlElement(required = true)
    protected Library library;
    @XmlElement(required = true)
    protected Topic topic;
    @XmlElement(required = true)
    protected Language language;

    /**
     * Obtient la valeur de la propriété totalPages.
     * 
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Définit la valeur de la propriété totalPages.
     * 
     */
    public void setTotalPages(int value) {
        this.totalPages = value;
    }

    /**
     * Gets the value of the books property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the books property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBooks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Book }
     * 
     * 
     */
    public List<Book> getBooks() {
        if (books == null) {
            books = new ArrayList<Book>();
        }
        return this.books;
    }

    /**
     * Obtient la valeur de la propriété library.
     * 
     * @return
     *     possible object is
     *     {@link Library }
     *     
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Définit la valeur de la propriété library.
     * 
     * @param value
     *     allowed object is
     *     {@link Library }
     *     
     */
    public void setLibrary(Library value) {
        this.library = value;
    }

    /**
     * Obtient la valeur de la propriété topic.
     * 
     * @return
     *     possible object is
     *     {@link Topic }
     *     
     */
    public Topic getTopic() {
        return topic;
    }

    /**
     * Définit la valeur de la propriété topic.
     * 
     * @param value
     *     allowed object is
     *     {@link Topic }
     *     
     */
    public void setTopic(Topic value) {
        this.topic = value;
    }

    /**
     * Obtient la valeur de la propriété language.
     * 
     * @return
     *     possible object is
     *     {@link Language }
     *     
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Définit la valeur de la propriété language.
     * 
     * @param value
     *     allowed object is
     *     {@link Language }
     *     
     */
    public void setLanguage(Language value) {
        this.language = value;
    }

}
