package com.gg.proj.model;

import javax.persistence.*;
import java.util.List;

/**
 * Base DTO class for the Library model
 */
@Entity
@Table(name = "library")
public class LibraryEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy = "library")
    private List<BookEntity> books;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @Column(nullable = false)
    private String adress;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public LibraryEntity() {
    }

    public LibraryEntity(List<BookEntity> books, String name, String city, Integer postalCode, String adress, String phoneNumber) {
        this.books = books;
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<BookEntity> getBook() {
        return books;
    }

    public void setBook(List<BookEntity> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "LibraryEntity{" +
                "id=" + id +
                ", books=" + books +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                ", adress='" + adress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
