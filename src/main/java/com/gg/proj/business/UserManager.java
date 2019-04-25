package com.gg.proj.business;

import com.gg.proj.business.mapper.TokenMapper;
import com.gg.proj.business.mapper.UserMapper;
import com.gg.proj.consumer.TokenRepository;
import com.gg.proj.consumer.UserRepository;
import com.gg.proj.model.TokenEntity;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.*;
import com.gg.proj.service.users.Token;
import com.gg.proj.service.users.User;
import com.gg.proj.util.CustomStringDecryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

/**
 * This class contains business methods
 */
@Component
@Transactional
public class UserManager {

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private TokenMapper tokenMapper;

    private UserMapper userMapper;

    private UserRepository userRepository;

    private TokenManager tokenManager;

    private TokenRepository tokenRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserManager(TokenMapper tokenMapper, UserMapper userMapper, UserRepository userRepository, TokenManager tokenManager, TokenRepository tokenRepository, PasswordEncoder passwordEncoder) {
        this.tokenMapper = tokenMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * <p>This is the method that login user. It receive a pseudo and a Base64 password. Then it uses a passwordEncoder to
     * check if password matches the hash stored in database.</p>
     *
     * @param pseudo the user pseudo
     * @param encodedPassword the user password coded in Base64 /!\ need to be improved
     * @return a Token containing a valid UUID
     * @throws UserNotFoundException if no user with this pseudo was found
     * @throws IllegalArgumentException if password and hash don't match
     */
    public Token loginUser(String pseudo, String encodedPassword) throws UserNotFoundException, IllegalArgumentException {
        log.debug("Entering loginUser method... Requesting database for a user with pseudo : " + pseudo);
        UserEntity userEntity = userRepository.findByPseudo(pseudo);

        // here we decode the password
        String decodedPassword = CustomStringDecryptor.decrypt(encodedPassword);

        if (userEntity == null) {
            log.info("No user " + pseudo + " found un database");
            throw new UserNotFoundException("No such user in database");
        } // Now we check if password and storedHash match
//        else if (Password.checkPassword(plaintextPassword, userEntity.getPasswordHash())) {
        else if (passwordEncoder.matches(decodedPassword, userEntity.getPasswordHash())) {
            log.debug("Found user in database : " + userEntity);
            // using save method to trigger @PreUpdate and save the last connection infos
//            userRepository.updateLastConnectionById(userEntity.getId());
            TokenEntity tokenEntity = tokenManager.checkByUserThenReturnToken(userEntity);
            return tokenMapper.tokenEntityToToken(tokenEntity);
        } else
            throw new IllegalArgumentException("Password and hash don't match");
    }

    /**
     *
     * <p>This method logout the user</p>
     *
     * @param tokenUUID a valid UUID
     * @return "SUCCESS"
     * @throws UserNotFoundException if no user with this pseudo was found
     * @throws IllegalArgumentException if password and hash don't match
     */
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

    /**
     *
     * <p>This method is called to register a user. Pseudo & address must be unique, password has a minimal length</p>
     *
     * @param user the user to register
     * @return a valid Token
     * @throws PseudoAlreadyExistsException if the pseudo is already stored in DB
     * @throws MailAddressAlreadyExistsException if the mail is already stored in DB
     * @throws IllegalArgumentException if password is too short (6 characters)
     */
    public Token registerUser(User user) throws PseudoAlreadyExistsException, MailAddressAlreadyExistsException, IllegalArgumentException {
        log.debug("Entering registerUser...");
        String hash;
        String plaintextPassword = user.getPassword();
        UserEntity userEntity;
        TokenEntity tokenEntity;

        if (plaintextPassword.length() < 6)
            throw new IllegalArgumentException("Password must contains at least 6 characters");

//        hash = Password.hashPassword(plaintextPassword);
        hash = passwordEncoder.encode(plaintextPassword);
        userEntity = userMapper.userToUserEntity(user);
        userEntity.setPasswordHash(hash);

        if (userRepository.existsByPseudo(userEntity.getPseudo()))
            throw new PseudoAlreadyExistsException("This user pseudo already exists in database");
        if (userRepository.existsByMailAddress(userEntity.getMailAddress()))
            throw new MailAddressAlreadyExistsException("This mail address already exists in database");

        userEntity = userRepository.save(userEntity);
        tokenEntity = tokenManager.generateNewToken(userEntity);
        return tokenMapper.tokenEntityToToken(tokenEntity);
    }

    public void changePassword(String tokenUUID, Integer userId, String oldPassword, String newPassword) throws InvalidTokenException, OutdatedTokenException {

        String newHash;
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
            Optional<UserEntity> optional = userRepository.findById(userId);

            if (optional.isPresent()) {
//                if (Password.checkPassword(oldPassword, optional.get().getPasswordHash())) {
                if (passwordEncoder.matches(oldPassword, optional.get().getPasswordHash())) {
//                    newHash = Password.hashPassword(newPassword);
                    newHash = passwordEncoder.encode(newPassword);
                    optional.get().setPasswordHash(newHash);
                    userRepository.save(optional.get());
                }
            }
        } catch (Exception e) {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }
    }
}
