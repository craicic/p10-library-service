package com.gg.proj.business;

import com.gg.proj.business.mapper.TokenMapper;
import com.gg.proj.consumer.TokenRepository;
import com.gg.proj.model.TokenEntity;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Transactional
public class TokenManager {

    private static final Logger log = LoggerFactory.getLogger(TokenManager.class);

    private TokenRepository tokenRepository;

    private TokenMapper tokenMapper;

    @Autowired
    public TokenManager(TokenRepository tokenRepository, TokenMapper tokenMapper) {
        this.tokenRepository = tokenRepository;
        this.tokenMapper = tokenMapper;
    }

    /**
     * This method return a valid token to a user, note that you must be sure to authenticate the user first (with password hash)
     *
     * @param userEntity a valid user
     * @return a valid {@link TokenEntity}
     */
    public TokenEntity checkByUserThenReturnToken(UserEntity userEntity) {
        log.debug("Entering checkByUserThenReturnToken() with userEntity : " + userEntity + " ... Requesting the tokenRepository to get a valid token... ");
        // First we check if a token is stored and still valid
        TokenEntity tokenEntity = tokenRepository.findByUserAndExpirationDateAfter(userEntity, LocalDate.now());
        if (tokenEntity != null) {
            log.debug("token found ... valid until : " + tokenEntity.getExpirationDate());
            // If it's the case, we return the tokenEntity
            return tokenEntity;
        }

        // If not we have to persist a new token
        log.debug("no token found, a new one will be created");
        TokenEntity newTokenEntity = new TokenEntity();
        newTokenEntity.setUserEntity(userEntity);

        // On save the repository will generate a new UUID and a new expirationDate.
        // Then we return the newly created token which can't be null
        return tokenRepository.save(newTokenEntity);
    }


    public TokenEntity generateNewToken(UserEntity userEntity) {
        log.debug("Entering generateNewToken() with user : " + userEntity + " ... Requesting Dao to create a new token");
        TokenEntity tokenEntity = new TokenEntity();
        // PrePersist method will add the UUID and the expirationDate
        tokenEntity.setUserEntity(userEntity);
        return tokenRepository.save(tokenEntity);
    }

    public void checkIfValidByUuid(UUID uuid) throws InvalidTokenException, OutdatedTokenException {
        log.debug("Entering checkIfValidByUuid with the uuid : [" + uuid.toString() + "]");
        TokenEntity foundTokenEntity = tokenRepository.findByTokenUUID(uuid);
        if (foundTokenEntity == null) {
            throw new InvalidTokenException("no such token in database");
        }
        if (foundTokenEntity.getExpirationDate().isBefore(LocalDate.now())) {
            // Calling a private method to refresh the token
            log.debug("Refreshing token...");
            refreshToken(foundTokenEntity);
            throw new OutdatedTokenException("Token has expired, try to reconnect");
        }
    }

    private void refreshToken(TokenEntity tokenEntity) {
        TokenEntity freshTokenEntity = new TokenEntity();
        freshTokenEntity.setUserEntity(tokenEntity.getUserEntity());

        tokenRepository.save(freshTokenEntity);
    }
}
