package com.gg.proj.model;

import javax.persistence.*;

/**
 * Base DTO class for the Library model
 */
@Entity
@Table(name = "library")
public class LibraryEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(mappedBy = "library")
    private BookEntity book;

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

    public LibraryEntity(BookEntity book, String name, String city, Integer postalCode, String adress, String phoneNumber) {
        this.book = book;
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

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
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
                ", book=" + book +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                ", adress='" + adress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
