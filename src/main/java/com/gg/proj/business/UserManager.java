package com.gg.proj.business;

import com.gg.proj.business.mapper.TokenMapper;
import com.gg.proj.consumer.TokenRepository;
import com.gg.proj.consumer.UserRepository;
import com.gg.proj.model.TokenEntity;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.UserNotFoundException;
import com.gg.proj.service.users.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * This class contains business methods
 */
@Component
@Transactional
public class UserManager {

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private TokenMapper tokenMapper;

    private UserRepository userRepository;

    private TokenManager tokenManager;

    private TokenRepository tokenRepository;

    @Autowired
    public UserManager(TokenMapper tokenMapper, UserRepository userRepository, TokenRepository tokenRepository, TokenManager tokenManager) {
        this.tokenMapper = tokenMapper;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.tokenManager = tokenManager;
    }

    public Token loginUser(String pseudo, String passwordHash) throws UserNotFoundException {
        log.debug("Entering loginUser method... Requesting database for a user with pseudo : " + pseudo + " and hash : " + passwordHash);
        UserEntity userEntity = userRepository.findByPseudoAndPasswordHash(pseudo, passwordHash);
        if (userEntity == null) {
            log.info("No user " + pseudo + " found un database");
            throw new UserNotFoundException("No such user in database");
        } else {
            log.debug("Found user in database : " + userEntity);
            // using save method to trigger @PreUpdate
            userRepository.save(userEntity);
            TokenEntity tokenEntity = tokenManager.checkByUserThenReturnToken(userEntity);
            return tokenMapper.tokenEntityToToken(tokenEntity);
        }
    }

    public String logoutUser(String tokenUUID) throws UserNotFoundException, IllegalArgumentException {
        log.debug("Entering logoutUser method... Requesting database for a user with tokenUUID : " + tokenUUID);

        TokenEntity tokenEntity = tokenRepository.findByTokenUUID(UUID.fromString(tokenUUID));

        if (tokenEntity == null) {
            log.info("No user with token : " + tokenUUID + " found in database");
            throw new UserNotFoundException("No such user in database");
        } else {
            log.debug("Found user in database");
            UserEntity userEntity = tokenEntity.getUserEntity();
            // using save method to trigger @PreUpdate
            userRepository.save(userEntity);
            return "SUCCESS";
        }
    }
}
