package com.gg.proj.model.complex;

import java.util.Objects;

public class BorrowerModel {

    private Integer id;
    private String mailAddress;
    private String firstName;
    private String lastName;
    private String bookName;
    private String libraryName;

    public BorrowerModel() {
    }

    public BorrowerModel(Integer id, String mailAddress, String firstName, String lastName, String bookName, String libraryName) {
        this.id = id;
        this.mailAddress = mailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookName = bookName;
        this.libraryName = libraryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }


}
