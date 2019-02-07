package com.gg.proj.business.mapper;

import com.gg.proj.model.UserEntity;
import com.gg.proj.service.library.User;
import org.mapstruct.Mapper;

/**
 * This is the interface that drive MapStruct framework, you simply add the method signature and the framework drive the
 * whole mapping by generating implementation classes.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    //    USER
    User userEntityToUser(UserEntity userEntity);
}
