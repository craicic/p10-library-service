package com.gg.proj.business;

import com.gg.proj.business.mapper.UserMapper;
import com.gg.proj.consumer.UserRepository;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.UserNotFoundException;
import com.gg.proj.service.books.CreateUserRequest;
import com.gg.proj.service.books.User;
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

    private UserMapper userMapper;

    private UserRepository userRepository;

    @Autowired
    public UserManager(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public User loginUser(String pseudo, String passwordHash) throws UserNotFoundException {
        log.debug("Entering loginUser method... Requesting database for a user with pseudo : " + pseudo + " and hash : " + passwordHash);
        UserEntity userEntity = userRepository.findByPseudoAndPasswordHash(pseudo, passwordHash);
        if (userEntity == null) {
            log.info("No user " + pseudo + " found un database");
            throw new UserNotFoundException("No such user in database");
        } else {
            log.debug("Found user in database : " + userEntity);
            userEntity.setLastConnection(Timestamp.from(Instant.now()));
            userRepository.save(userEntity);
            return userMapper.userEntityToUser(userEntity);
        }
    }

    public String logoutUser(String pseudo, String passwordHash) throws UserNotFoundException {
        log.debug("Entering logoutUser method... Requesting database for a user with pseudo : " + pseudo + " and hash : " + passwordHash);
        UserEntity userEntity = userRepository.findByPseudoAndPasswordHash(pseudo, passwordHash);
        if (userEntity == null) {
            log.info("No user " + pseudo + " found un database");
            throw new UserNotFoundException("No such user in database");
        } else {
            log.debug("Found user in database : " + userEntity);
            userEntity.setLastConnection(Timestamp.from(Instant.now()));
            userRepository.save(userEntity);
            return "SUCCESS";
        }
    }

    public User createUser(CreateUserRequest request) {
        log.debug("Entering createUser method... ");
        UserEntity userEntity = new UserEntity();

        userEntity.setAddress(request.getAddress());
        userEntity.setCity(request.getCity());
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setMailAddress(request.getMailAddress());
        userEntity.setPasswordHash(request.getPasswordHash());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        userEntity.setPostalCode(request.getPostalCode());
        userEntity.setPseudo(request.getPseudo());
        userEntity.setLastConnection(Timestamp.from(Instant.now()));
        userEntity.setRegisterDate(Timestamp.from(Instant.now()));

        userRepository.save(userEntity);
        log.debug("Saved a new user in database " + userEntity.getPseudo());
        return userMapper.userEntityToUser(userEntity);
    }

    // To complete
//    public User updateUser(UserEntity userEntity){
//        log.debug("Entering updateUser method... ");
//        UserEntity userTemp = userRepository.findByPseudoAndPasswordHash(userEntity.getPseudo(), userEntity.getPasswordHash());
//        userTemp.
//        userRepository.save(userEntity);
//        log.debug("Updated user " + userEntity);
//        return userMapper.userEntityToUser(userEntity);
//    }
}
