//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.02.28 à 04:22:09 PM CET 
//


package com.gg.proj.service.profiles;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.gg.proj.service.profiles package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.gg.proj.service.profiles
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetProfileResponse }
     * 
     */
    public GetProfileResponse createGetProfileResponse() {
        return new GetProfileResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link ListAllProfilesRequest }
     * 
     */
    public ListAllProfilesRequest createListAllProfilesRequest() {
        return new ListAllProfilesRequest();
    }

    /**
     * Create an instance of {@link DeleteProfileRequest }
     * 
     */
    public DeleteProfileRequest createDeleteProfileRequest() {
        return new DeleteProfileRequest();
    }

    /**
     * Create an instance of {@link GetProfileRequest }
     * 
     */
    public GetProfileRequest createGetProfileRequest() {
        return new GetProfileRequest();
    }

    /**
     * Create an instance of {@link ListAllProfilesResponse }
     * 
     */
    public ListAllProfilesResponse createListAllProfilesResponse() {
        return new ListAllProfilesResponse();
    }

    /**
     * Create an instance of {@link DeleteProfileResponse }
     * 
     */
    public DeleteProfileResponse createDeleteProfileResponse() {
        return new DeleteProfileResponse();
    }

    /**
     * Create an instance of {@link SaveProfileResponse }
     * 
     */
    public SaveProfileResponse createSaveProfileResponse() {
        return new SaveProfileResponse();
    }

    /**
     * Create an instance of {@link SaveProfileRequest }
     * 
     */
    public SaveProfileRequest createSaveProfileRequest() {
        return new SaveProfileRequest();
    }

}
