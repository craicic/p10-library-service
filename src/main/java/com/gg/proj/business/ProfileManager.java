package com.gg.proj.business;

import com.gg.proj.business.mapper.ProfileMapper;
import com.gg.proj.consumer.ProfileRepository;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.*;
import com.gg.proj.service.profiles.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class ProfileManager {

    private static final Logger log = LoggerFactory.getLogger(ProfileManager.class);

    private TokenManager tokenManager;

    private ProfileRepository profileRepository;

    private ProfileMapper profileMapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileManager(TokenManager tokenManager, ProfileRepository profileRepository, ProfileMapper profileMapper
            , PasswordEncoder passwordEncoder) {
        this.tokenManager = tokenManager;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> save(User user, String StrUuid) throws InvalidTokenException, OutdatedTokenException {
        Optional<User> optionalFromEndpoint = Optional.ofNullable(user);
        Optional<UserEntity> optionalFromDB;
        UserEntity userEntityFromEndpoint;
        UserEntity userEntityFromDB;

        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(StrUuid));

            if (optionalFromEndpoint.isPresent()) {

                log.warn("This method doesn't allowed the creation of a new user");
                return Optional.empty();
            } else {

                userEntityFromEndpoint = profileMapper.userToUserEntity(optionalFromEndpoint.get());
                optionalFromDB = profileRepository.findById(optionalFromEndpoint.get().getId());

                if (optionalFromDB.isPresent()) {

                    // here we check if the new pseudo and mail address are not being used
                    if (profileRepository.existsByPseudo(optionalFromEndpoint.get().getPseudo()))
                        throw new PseudoAlreadyExistsException("This user pseudo already exists in database");
                    if (profileRepository.existsByMailAddress(optionalFromEndpoint.get().getMailAddress()))
                        throw new MailAddressAlreadyExistsException("This mail address already exists in database");

                    userEntityFromDB = optionalFromDB.get();
                    userEntityFromDB.setPseudo(userEntityFromEndpoint.getPseudo());
                    userEntityFromDB.setPostalCode(userEntityFromEndpoint.getPostalCode());
                    userEntityFromDB.setPhoneNumber(userEntityFromEndpoint.getPhoneNumber());
                    userEntityFromDB.setMailAddress(userEntityFromEndpoint.getMailAddress());
                    userEntityFromDB.setLastName(userEntityFromEndpoint.getLastName());
                    userEntityFromDB.setFirstName(userEntityFromEndpoint.getFirstName());

                    return Optional.ofNullable(profileMapper.userEntityToUser(profileRepository.save(userEntityFromDB)));
                }
                return Optional.ofNullable(profileMapper.userEntityToUser(profileRepository.save(profileMapper.userToUserEntity(user))));
            }
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
        return Optional.empty();
    }

    public void delete(User user, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
            profileRepository.delete(profileMapper.userToUserEntity(user));
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
    }

    public Optional<User> findById(Integer id, UUID uuid) throws InvalidTokenException, OutdatedTokenException {
        UserEntity userEntity = new UserEntity();

        try {
            tokenManager.checkIfValidByUuid(uuid);
            Optional<UserEntity> optional = profileRepository.findById(id);
            userEntity = optional.orElse(null);

            if (optional.isPresent()) {
                log.info("findById : Requesting a user by id : " + id + " => found : " + userEntity);
            } else {
                log.info("findById : Requesting a book by id : " + id + " => id not found in database");
            }
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
        return Optional.ofNullable(profileMapper.userEntityToUser(userEntity));
    }

    public List<User> findAll(UUID uuid) throws InvalidTokenException, OutdatedTokenException {
        List<User> users = new ArrayList<>();
        try {
            tokenManager.checkIfValidByUuid(uuid);
            users.addAll(profileMapper.userEntityListToUserList(profileRepository.findAll()));
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
        return users;
    }
}
