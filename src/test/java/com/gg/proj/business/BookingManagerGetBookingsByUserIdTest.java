package com.gg.proj.business;

import com.gg.proj.business.mapper.BookingMapper;
import com.gg.proj.consumer.BookingRepository;
import com.gg.proj.model.complex.BookingInfoModel;
import com.gg.proj.service.bookings.BookingInfo;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import com.gg.proj.util.CustomMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookingManagerGetBookingsByUserIdTest {

    private final static String VALID_UUID = "a914acb7-0c6c-4439-a73f-05f5534077a4";
    private final static String OUTDATED_UUID = "3a66484c-515d-4da3-9a62-de290dded036";
    private final static String NOT_IN_DATABASE_UUID = "7b66484c-515d-4da3-9a62-de290dded036";

    @Mock
    private TokenManager tokenManager;

    @Mock
    private BookManager bookManager;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CustomMailService customMailService;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingManager bookingManager;

    @Test
    public void getBookingsByUserId_shouldContainsExactlyBookingInfo1And2()
            throws OutdatedTokenException, InvalidTokenException {

        final BookingInfoModel bookingInfoModel1 = new BookingInfoModel();
        final BookingInfoModel bookingInfoModel2 = new BookingInfoModel();
        final BookingInfo bookingInfo1 = new BookingInfo();
        final BookingInfo bookingInfo2 = new BookingInfo();

        final List<BookingInfoModel> models = Arrays.asList(
                bookingInfoModel1,
                bookingInfoModel2
        );

        final List<BookingInfo> dtos = Arrays.asList(
                bookingInfo1,
                bookingInfo2
        );

        when(bookingRepository.queryBookingInfo(5)).thenReturn(models);
        when(bookingMapper.modelsToDtos(models)).thenReturn(dtos);

        List<BookingInfo> actual = bookingManager.getBookingsByUserId(5, VALID_UUID);
        assertThat(actual).containsExactly(bookingInfo1, bookingInfo2);
    }

    @Test
    public void getBookingsByUserId_shouldThrowOutdatedTokenException()
            throws OutdatedTokenException, InvalidTokenException {

        doThrow(new OutdatedTokenException("Token has expired, try to reconnect"))
                .when(tokenManager).checkIfValidByUuid(UUID.fromString(OUTDATED_UUID));

        final Throwable thrown = catchThrowable(() -> bookingManager.getBookingsByUserId(5, OUTDATED_UUID));
        assertThat(thrown).isExactlyInstanceOf(OutdatedTokenException.class);
    }

    @Test
    public void getBookingsByUserId_shouldThrowOutdatedTokenException_WithCorrectErrorMessage()
            throws OutdatedTokenException, InvalidTokenException {

        final String EXPECTED_MESSAGE = "Token has expired, try to reconnect";
        doThrow(new OutdatedTokenException(EXPECTED_MESSAGE))
                .when(tokenManager).checkIfValidByUuid(UUID.fromString(OUTDATED_UUID));

        final Throwable thrown = catchThrowable(() -> bookingManager.getBookingsByUserId(5, OUTDATED_UUID));
        assertThat(thrown).hasMessage(EXPECTED_MESSAGE);
    }

    @Test
    public void getBookingsByUserId_shouldThrowInvalidTokenException()
            throws OutdatedTokenException, InvalidTokenException {

        doThrow(new InvalidTokenException("no such token in database"))
                .when(tokenManager).checkIfValidByUuid(UUID.fromString(NOT_IN_DATABASE_UUID));

        final Throwable thrown = catchThrowable(() -> bookingManager.getBookingsByUserId(5, NOT_IN_DATABASE_UUID));
        assertThat(thrown).isExactlyInstanceOf(InvalidTokenException.class);
    }

    @Test
    public void getBookingsByUserId_shouldThrowInvalidTokenException_WithCorrectErrorMessage()
            throws OutdatedTokenException, InvalidTokenException {

        final String EXPECTED_MESSAGE = "no such token in database";
        doThrow(new InvalidTokenException(EXPECTED_MESSAGE))
                .when(tokenManager).checkIfValidByUuid(UUID.fromString(NOT_IN_DATABASE_UUID));

        final Throwable thrown = catchThrowable(() -> bookingManager.getBookingsByUserId(5, NOT_IN_DATABASE_UUID));
        assertThat(thrown).hasMessage(EXPECTED_MESSAGE);
    }
}