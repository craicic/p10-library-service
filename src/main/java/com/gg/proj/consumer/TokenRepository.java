package com.gg.proj.consumer;

import com.gg.proj.model.TokenEntity;
import com.gg.proj.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    TokenEntity findByUserId(Integer userEntityId);

    TokenEntity findByTokenUUID(UUID tokenUUID);

    TokenEntity findByUserAndExpirationDateAfter(UserEntity userEntity, LocalDate localDate);

    TokenEntity findByUserIdAndTokenUUID(Integer userId, UUID tokenUUID);

    TokenEntity findByUserAndTokenUUID(UserEntity userEntity, UUID tokenUUID);
}
