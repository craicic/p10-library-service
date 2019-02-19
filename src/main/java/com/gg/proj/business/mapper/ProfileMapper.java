package com.gg.proj.business.mapper;


import com.gg.proj.model.UserEntity;
import com.gg.proj.service.profiles.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    UserEntity userToUserEntity(User user);

    User userEntityToUser(UserEntity userEntity);
}
