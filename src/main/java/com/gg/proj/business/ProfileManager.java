package com.gg.proj.business;

import com.gg.proj.business.mapper.ProfileMapper;
import com.gg.proj.consumer.ProfileRepository;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import com.gg.proj.service.profiles.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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

    public User save(User user, String StrUuid) throws InvalidTokenException , OutdatedTokenException{
        if (user.getId() == null) {
            // If User has no id, it's a new register, we save a new user, then generate a new token for him
            user = profileMapper.userEntityToUser(profileRepository.save(profileMapper.userToUserEntity(user)));
            tokenManager.generateNewToken(profileMapper.userToUserEntity(user));
            return user;
        } else {
            try {
                log.info("uuid : " + StrUuid);
                UUID uuid = UUID.fromString(StrUuid);
                log.info("uuid : " + uuid);
                boolean isAuthenticated = tokenManager.checkIfValidByUuid(profileMapper.userToUserEntity(user), uuid);
                log.info("uuid : " + StrUuid);
            } catch (InvalidTokenException e) {
                log.warn("invalid connection");
                throw e;
            } catch (OutdatedTokenException e) {
                log.info("token is outdated");
                throw e;
            }
            log.info("HEY");
            return profileMapper.userEntityToUser(profileRepository.save(profileMapper.userToUserEntity(user)));

        }
    }
}
