package com.gg.proj.model;

import javax.persistence.*;
import java.util.List;

/**
 * Base DTO class for the Library model. Has an unique index on name column. Has a OneToMany relation with the book
 * table
 */
@Entity
@Table(name = "library",
        indexes = {@Index(name = "library_idx", columnList = "name", unique = true)})
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
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public LibraryEntity() {
    }

    public LibraryEntity(List<BookEntity> books, String name, String city, Integer postalCode, String address, String phoneNumber) {
        this.books = books;
        this.name = name;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
