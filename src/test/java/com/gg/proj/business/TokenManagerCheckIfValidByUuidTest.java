package com.gg.proj.business;

import com.gg.proj.consumer.TokenRepository;
import com.gg.proj.model.TokenEntity;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TokenManagerCheckIfValidByUuidTest {

    private final static String VALID_UUID = "a914acb7-0c6c-4439-a73f-05f5534077a4";
    private final static String OUTDATED_UUID = "3a66484c-515d-4da3-9a62-de290dded036";
    private final static String NOT_IN_DATABASE_UUID = "7b66484c-515d-4da3-9a62-de290dded036";

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenManager tokenManager;

    @Test
    public void checkIfValidByUuid_shouldNotThrowException() {
        final TokenEntity validTokenEntity = new TokenEntity(
                UUID.fromString(VALID_UUID),
                LocalDate.of(2020, Month.AUGUST, 30),
                new UserEntity());

        when(tokenRepository.findByTokenUUID(UUID.fromString(VALID_UUID))).thenReturn(validTokenEntity);

        // Should not throw exception
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(VALID_UUID));
        } catch (Exception e) {
            Assertions.fail("Fail cause an exepction was thrown");
        }
    }

    @Test
    public void checkIfValidByUuid_shouldThrowInvalidTokenException() {

        when(tokenRepository.findByTokenUUID(UUID.fromString(NOT_IN_DATABASE_UUID))).thenReturn(null);

        final Throwable thrown =  catchThrowable(
                () -> tokenManager.checkIfValidByUuid(UUID.fromString(NOT_IN_DATABASE_UUID)));

        assertThat(thrown).isExactlyInstanceOf(InvalidTokenException.class);
    }

    @Test
    public void checkIfValidByUuid_shouldThrowInvalidTokenException_WithCorrectErrorMessage() {
        final String exceptedMessage = "no such token in database";

        when(tokenRepository.findByTokenUUID(UUID.fromString(NOT_IN_DATABASE_UUID))).thenReturn(null);

        final Throwable thrown =  catchThrowable(
                () -> tokenManager.checkIfValidByUuid(UUID.fromString(NOT_IN_DATABASE_UUID)));

        assertThat(thrown).hasMessage(exceptedMessage);
    }

    @Test
    public void checkIfValidByUuid_shouldThrowOutdatedTokenException() {

        final TokenEntity outdatedTokenEntity = new TokenEntity(
                UUID.fromString(VALID_UUID),
                // this date is past
                LocalDate.of(2015, Month.AUGUST, 30),
                new UserEntity());

        when(tokenRepository.findByTokenUUID(UUID.fromString(OUTDATED_UUID))).thenReturn(outdatedTokenEntity);

        final Throwable thrown =  catchThrowable(
                () -> tokenManager.checkIfValidByUuid(UUID.fromString(OUTDATED_UUID)));

        assertThat(thrown).isExactlyInstanceOf(OutdatedTokenException.class);
    }

    @Test
    public void checkIfValidByUuid_shouldThrowOutdatedTokenException_WithCorrectErrorMessage() {

        final String expectedMessage = "Token has expired, try to reconnect";

        final TokenEntity outdatedTokenEntity = new TokenEntity(
                UUID.fromString(VALID_UUID),
                // this date is past
                LocalDate.of(2015, Month.AUGUST, 30),
                new UserEntity());

        when(tokenRepository.findByTokenUUID(UUID.fromString(OUTDATED_UUID))).thenReturn(outdatedTokenEntity);

        final Throwable thrown =  catchThrowable(
                () -> tokenManager.checkIfValidByUuid(UUID.fromString(OUTDATED_UUID)));

        assertThat(thrown).hasMessage(expectedMessage);
    }
}