package com.gg.proj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users",
        indexes = {@Index(name = "users_idx", columnList = "pseudo"),
                @Index(name = "users_idx1", columnList = "mail_address")})
public class UserEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String pseudo;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @Column(nullable = false)
    private String address;

    @Column(name = "mail_address", nullable = false)
    private String mailAddress;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "last_connection", nullable = false)
    private Date lastConnection;

    @Column(name = "register_date", nullable = false)
    private Date registerDate;

    @OneToMany(mappedBy = "user")
    private List<LoanEntity> loans;

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String pseudo, String passwordHash, String city,
                      Integer postalCode, String address, String mailAddress, String phoneNumber, Date lastConnection, Date registerDate, List<LoanEntity> loans) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudo = pseudo;
        this.passwordHash = passwordHash;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
        this.mailAddress = mailAddress;
        this.phoneNumber = phoneNumber;
        this.lastConnection = lastConnection;
        this.registerDate = registerDate;
        this.loans = loans;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public List<LoanEntity> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }
}
