//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.03.05 à 10:43:59 AM CET 
//


package com.gg.proj.service.books;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name="books" type="{http://proj.gg.com/service/books}book" maxOccurs="unbounded"/>
 *         &lt;element name="libraries" type="{http://proj.gg.com/service/books}library" maxOccurs="unbounded"/>
 *         &lt;element name="topics" type="{http://proj.gg.com/service/books}topic" maxOccurs="unbounded"/>
 *         &lt;element name="languages" type="{http://proj.gg.com/service/books}language" maxOccurs="unbounded"/>
 *         &lt;element name="keyWord" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "totalPages",
        "books",
        "libraries",
        "topics",
        "languages",
        "keyWord"
})
@XmlRootElement(name = "searchBooksResponse")
public class SearchBooksResponse {

    protected int totalPages;
    @XmlElement(required = true)
    protected List<Book> books;
    @XmlElement(required = true)
    protected List<Library> libraries;
    @XmlElement(required = true)
    protected List<Topic> topics;
    @XmlElement(required = true)
    protected List<Language> languages;
    @XmlElement(required = true)
    protected String keyWord;

    /**
     * Obtient la valeur de la propriété totalPages.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Définit la valeur de la propriété totalPages.
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
     */
    public List<Book> getBooks() {
        if (books == null) {
            books = new ArrayList<Book>();
        }
        return this.books;
    }

    /**
     * Gets the value of the libraries property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the libraries property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLibraries().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Library }
     */
    public List<Library> getLibraries() {
        if (libraries == null) {
            libraries = new ArrayList<Library>();
        }
        return this.libraries;
    }

    /**
     * Gets the value of the topics property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the topics property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTopics().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Topic }
     */
    public List<Topic> getTopics() {
        if (topics == null) {
            topics = new ArrayList<Topic>();
        }
        return this.topics;
    }

    /**
     * Gets the value of the languages property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the languages property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLanguages().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Language }
     */
    public List<Language> getLanguages() {
        if (languages == null) {
            languages = new ArrayList<Language>();
        }
        return this.languages;
    }

    /**
     * Obtient la valeur de la propriété keyWord.
     *
     * @return possible object is
     * {@link String }
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * Définit la valeur de la propriété keyWord.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setKeyWord(String value) {
        this.keyWord = value;
    }

}
