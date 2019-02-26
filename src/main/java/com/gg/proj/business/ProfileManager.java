package com.gg.proj.business;

import com.gg.proj.business.mapper.ProfileMapper;
import com.gg.proj.consumer.ProfileRepository;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import com.gg.proj.service.profiles.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProfileManager(TokenManager tokenManager, ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.tokenManager = tokenManager;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public Optional<User> save(User user, String StrUuid) throws InvalidTokenException, OutdatedTokenException {
        Optional<User> optional = Optional.ofNullable(user);
        if (optional.isPresent()) {
            if (optional.get().getId() == null) {
                // If User has no id, it's a new register, we save a new user, then generate a new token
                user = profileMapper.userEntityToUser(profileRepository.save(profileMapper.userToUserEntity(user)));
                tokenManager.generateNewToken(profileMapper.userToUserEntity(user));
                return Optional.of(user);
            } else {
                try {
                    tokenManager.checkIfValidByUuid(UUID.fromString(StrUuid));
                } catch (Exception ex) {
                    GenericExceptionHelper.tokenExceptionHandler(ex);
                }
                return Optional.ofNullable(profileMapper.userEntityToUser(profileRepository.save(profileMapper.userToUserEntity(user))));
            }
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
