package com.gg.proj.business;

import com.gg.proj.business.mapper.MapperWorker;
import com.gg.proj.consumer.UserRepository;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.UserNotFoundException;
import com.gg.proj.service.library.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * This class contains business methods
 */
@Component
@Transactional
public class UserManager {

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private MapperWorker mapperWorker;

    private UserRepository userRepository;

    @Autowired
    public UserManager(MapperWorker mapperWorker, UserRepository userRepository) {
        this.mapperWorker = mapperWorker;
        this.userRepository = userRepository;
    }

    public User loginUser(String pseudo, String passwordHash) throws UserNotFoundException {
        log.debug("Entering loginUser method... " +
                "   ... Requesting database for a user with pseudo : " + pseudo + " and hash : " + passwordHash);
        UserEntity userEntity = userRepository.findByPseudoAndPasswordHash(pseudo, passwordHash);
        if (userEntity != null ) {
            log.debug("found user in database : " + userEntity);
            userEntity.setLastConnection(Timestamp.from(Instant.now()));
            userRepository.save(userEntity);
        } else {
            throw new UserNotFoundException("No such user in database");
        }
            return mapperWorker.userEntityToUser(userEntity);
    }
}
