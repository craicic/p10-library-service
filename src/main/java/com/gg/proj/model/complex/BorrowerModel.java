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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowerModel that = (BorrowerModel) o;
        return id.equals(that.id) &&
                mailAddress.equals(that.mailAddress) &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                bookName.equals(that.bookName) &&
                libraryName.equals(that.libraryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mailAddress, firstName, lastName, bookName, libraryName);
    }

    @Override
    public String toString() {
        return "BorrowerModel{" +
                "id=" + id +
                ", mailAddress='" + mailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bookName='" + bookName + '\'' +
                ", libraryName='" + libraryName + '\'' +
                '}';
    }
}
