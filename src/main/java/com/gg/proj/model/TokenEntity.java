package com.gg.proj.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String token;

    private Date expirationDate;

    @OneToMany
    private UserEntity userEntity;

    public TokenEntity() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

