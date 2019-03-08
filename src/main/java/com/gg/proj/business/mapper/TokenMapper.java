package com.gg.proj.business.mapper;

import com.gg.proj.model.TokenEntity;
import com.gg.proj.service.users.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface TokenMapper {

    @Mappings({
            @Mapping(target = "tokenUUID", expression = "java(tokenEntity.getTokenUUID().toString())"),
            @Mapping(target = "userId", source = "userEntity.id")})
    Token tokenEntityToToken(TokenEntity tokenEntity);

    @Mapping(target = "tokenUUID", expression = "java(java.util.UUID.fromString(token.getTokenUUID()))")
    TokenEntity tokenToTokenEntity(Token token);
}
