package com.gg.proj.business.mapper;

import com.gg.proj.model.TokenEntity;
import com.gg.proj.service.users.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper( TokenMapper.class);

    @Mapping(target= "tokenUUID",  expression = "java(UUID.randomUUID().toString())")
    Token tokenEntityToToken(TokenEntity tokenEntity);

    @Mapping(target= "tokenUUID", expression = "java(UUID.fromString(token.getTokenUUID()))")
    TokenEntity tokenToTokenEntity(Token token);
}
