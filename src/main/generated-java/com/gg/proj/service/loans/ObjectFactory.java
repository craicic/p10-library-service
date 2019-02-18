//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.02.18 à 02:56:09 PM CET 
//


package com.gg.proj.service.loans;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.gg.proj.service.loans package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.gg.proj.service.loans
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListAllLoansRequest }
     * 
     */
    public ListAllLoansRequest createListAllLoansRequest() {
        return new ListAllLoansRequest();
    }

    /**
     * Create an instance of {@link DeleteLoanResponse }
     * 
     */
    public DeleteLoanResponse createDeleteLoanResponse() {
        return new DeleteLoanResponse();
    }

    /**
     * Create an instance of {@link SaveLoanResponse }
     * 
     */
    public SaveLoanResponse createSaveLoanResponse() {
        return new SaveLoanResponse();
    }

    /**
     * Create an instance of {@link Loan }
     * 
     */
    public Loan createLoan() {
        return new Loan();
    }

    /**
     * Create an instance of {@link DeleteLoanRequest }
     * 
     */
    public DeleteLoanRequest createDeleteLoanRequest() {
        return new DeleteLoanRequest();
    }

    /**
     * Create an instance of {@link ListAllLoansResponse }
     * 
     */
    public ListAllLoansResponse createListAllLoansResponse() {
        return new ListAllLoansResponse();
    }

    /**
     * Create an instance of {@link GetLoanRequest }
     * 
     */
    public GetLoanRequest createGetLoanRequest() {
        return new GetLoanRequest();
    }

    /**
     * Create an instance of {@link SaveLoanRequest }
     * 
     */
    public SaveLoanRequest createSaveLoanRequest() {
        return new SaveLoanRequest();
    }

    /**
     * Create an instance of {@link GetLoanResponse }
     * 
     */
    public GetLoanResponse createGetLoanResponse() {
        return new GetLoanResponse();
    }

    /**
     * Create an instance of {@link Library }
     * 
     */
    public Library createLibrary() {
        return new Library();
    }

    /**
     * Create an instance of {@link Book }
     * 
     */
    public Book createBook() {
        return new Book();
    }

    /**
     * Create an instance of {@link Topic }
     * 
     */
    public Topic createTopic() {
        return new Topic();
    }

    /**
     * Create an instance of {@link Language }
     * 
     */
    public Language createLanguage() {
        return new Language();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

}
