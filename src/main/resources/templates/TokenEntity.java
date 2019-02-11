package templates;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String token;

    @OneToOne
    private UserEntity userEntity;

    public TokenEntity() {
    }

    public TokenEntity(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
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
}

