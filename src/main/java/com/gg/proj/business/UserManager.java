package com.gg.proj.business;

import com.gg.proj.business.mapper.TokenMapper;
import com.gg.proj.business.mapper.UserMapper;
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
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

/**
 * This class contains business methods
 */
@Component
@Transactional
public class UserManager {

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private UserMapper userMapper;

    private TokenMapper tokenMapper;

    private UserRepository userRepository;

    private TokenRepository tokenRepository;

    @Autowired
    public UserManager(UserMapper userMapper, TokenMapper tokenMapper, UserRepository userRepository, TokenRepository tokenRepository) {
        this.userMapper = userMapper;
        this.tokenMapper = tokenMapper;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }


    public Token loginUser(String pseudo, String passwordHash) throws UserNotFoundException {
        log.debug("Entering loginUser method... Requesting database for a user with pseudo : " + pseudo + " and hash : " + passwordHash);
        UserEntity userEntity = userRepository.findByPseudoAndPasswordHash(pseudo, passwordHash);
        if (userEntity == null) {
            log.info("No user " + pseudo + " found un database");
            throw new UserNotFoundException("No such user in database");
        } else {
            log.debug("Found user in database : " + userEntity);
            userEntity.setLastConnection(Timestamp.from(Instant.now()));
            userRepository.save(userEntity);
            TokenEntity tokenEntity = tokenRepository.findByUserId(userEntity.getId());
            return tokenMapper.tokenEntityToToken(tokenEntity);
        }
    }

    public String logoutUser(String tokenUUID) throws UserNotFoundException, IllegalArgumentException {
        log.debug("Entering logoutUser method... Requesting database for a user with tokenUUID : " + tokenUUID);

        TokenEntity tokenEntity = tokenRepository.findByToken(UUID.fromString(tokenUUID));

        if (tokenEntity == null) {
            log.info("No user with token : " + tokenUUID + " found un database");
            throw new UserNotFoundException("No such user in database");
        } else {
            log.debug("Found user in database");
            UserEntity userEntity = tokenEntity.getUserEntity();
            userEntity.setLastConnection(Timestamp.from(Instant.now()));
            userRepository.save(userEntity);
            return "SUCCESS";
        }
    }
}
