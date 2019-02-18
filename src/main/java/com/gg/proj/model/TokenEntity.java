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

    private UUID token;

    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    public TokenEntity() {
    }

    public TokenEntity(UUID token, LocalDate expirationDate, UserEntity user) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    @PrePersist
    public void PrePersist(){
        token = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        expirationDate = today.plus(3, ChronoUnit.WEEKS);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
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

