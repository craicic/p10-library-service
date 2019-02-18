package com.gg.proj.consumer;

import com.gg.proj.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    TokenEntity findByUserId(Integer userEntityId);

    TokenEntity findByToken(UUID tokenUUID);
}
