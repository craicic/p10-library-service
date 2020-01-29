package com.gg.proj.business;

import com.gg.proj.business.mapper.TokenMapper;
import com.gg.proj.business.mapper.UserMapper;
import com.gg.proj.consumer.TokenRepository;
import com.gg.proj.consumer.UserRepository;
import com.gg.proj.model.TokenEntity;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.UserNotFoundException;
import com.gg.proj.service.users.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserManagerLogoutUserTest {

    private final static String VALID_UUID = "a914acb7-0c6c-4439-a73f-05f5534077a4";
    private final static String NOT_IN_DATABASE_UUID = "7b66484c-515d-4da3-9a62-de290dded036";

    @Mock
    private TokenMapper tokenMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenManager tokenManager;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserManager userManager;

    @Test
    public void logoutUser_shouldReturnSuccess()
            throws UserNotFoundException {
        final String expectedString = "SUCCESS";
        final UserEntity userEntity = new UserEntity();
        when(tokenRepository.findByTokenUUID(UUID.fromString(VALID_UUID)))
                .thenReturn(new TokenEntity(UUID.fromString(VALID_UUID),
                        LocalDate.now(),
                        userEntity));

        assertThat(userManager.logoutUser(VALID_UUID)).isEqualTo(expectedString);

    }
}