package com.gg.proj.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users",
        indexes = {@Index(name = "users_idx", columnList = "pseudo", unique = true),
                @Index(name = "users_idx1", columnList = "mail_address", unique = true)})
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
    private LocalDateTime lastConnection;

    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

    @OneToMany(mappedBy = "user")
    private List<LoanEntity> loans;

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> token;

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String pseudo, String passwordHash, String city,
                      Integer postalCode, String address, String mailAddress, String phoneNumber,
                      LocalDateTime lastConnection, LocalDate registerDate, List<LoanEntity> loans) {
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

    @PreUpdate
    public void preUpdate() {
        this.lastConnection = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.registerDate = LocalDate.now();
        this.lastConnection = LocalDateTime.now();
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

    public LocalDateTime getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(LocalDateTime lastConnection) {
        this.lastConnection = lastConnection;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public List<LoanEntity> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }

    public List<TokenEntity> getToken() {
        return token;
    }

    public void setToken(List<TokenEntity> token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                ", address='" + address + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", lastConnection=" + lastConnection +
                ", registerDate=" + registerDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id.equals(that.id) &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                pseudo.equals(that.pseudo) &&
                city.equals(that.city) &&
                postalCode.equals(that.postalCode) &&
                address.equals(that.address) &&
                mailAddress.equals(that.mailAddress) &&
                phoneNumber.equals(that.phoneNumber) &&
                lastConnection.equals(that.lastConnection) &&
                registerDate.equals(that.registerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, pseudo, city, postalCode, address, mailAddress, phoneNumber, lastConnection, registerDate);
    }
}
