package com.gg.proj.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "token_uuid")
    private UUID tokenUUID;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public TokenEntity() {
    }

    public TokenEntity(UUID tokenUUID, LocalDate expirationDate, UserEntity user) {
        this.tokenUUID = tokenUUID;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        System.out.println("performing prePersist...");
        tokenUUID = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        expirationDate = today.plus(3, ChronoUnit.WEEKS);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getTokenUUID() {
        return tokenUUID;
    }

    public void setTokenUUID(UUID tokenUUID) {
        this.tokenUUID = tokenUUID;
    }

    public UserEntity getUserEntity() {
        return user;
    }

    public void setUserEntity(UserEntity user) {
        this.user = user;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}

