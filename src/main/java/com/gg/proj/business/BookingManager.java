package com.gg.proj.business;

import com.gg.proj.consumer.BookingRepository;
import com.gg.proj.model.BookEntity;
import com.gg.proj.model.BookingEntity;
import com.gg.proj.model.UserEntity;
import com.gg.proj.service.bookings.BookingSummary;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.InvalidBookingOperationException;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class BookingManager {

    private static final Logger log = LoggerFactory.getLogger(BookingManager.class);

    private TokenManager tokenManager;

    private LoanManager loanManager;

    private BookManager bookManager;

    private BookingRepository bookingRepository;

    @Autowired
    public BookingManager(TokenManager tokenManager, BookingRepository bookingRepository, LoanManager loanManager, BookManager bookManager) {
        this.tokenManager = tokenManager;
        this.loanManager = loanManager;
        this.bookManager = bookManager;
        this.bookingRepository = bookingRepository;
    }


    public Optional<BookingSummary> performBooking(int userId, int bookId, String tokenUUID) throws OutdatedTokenException, InvalidTokenException, InvalidBookingOperationException, DatatypeConfigurationException {
        log.debug("Entering performBooking...");

        // Check UUID
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }

        // Check if all sample are lend
        if (bookManager.getQuantity(bookId) > 0) {
            throw new InvalidBookingOperationException("You can't book if the item is already available");
        }

        // Sample count
        Integer totalAmountOfBook = loanManager.getBookCount(bookId);

        // User count
        Integer totalAmountOfBooker = bookingRepository.countByBookId(bookId);

        // Check queue vs booker
        if (totalAmountOfBooker >= 2 * totalAmountOfBook) {
            //TODO IDEA: could return useful infos
            throw new InvalidBookingOperationException("Too much users booked this item");
        }

        // Mapping
        BookEntity book = new BookEntity();
        book.setId(bookId);

        UserEntity user = new UserEntity();
        user.setId(userId);

        BookingEntity bookingEntity = new BookingEntity(book, user, LocalDateTime.now());

        // Save the booking
        bookingRepository.save(bookingEntity);

        // Set the summary
        BookingSummary summary = new BookingSummary();
        summary.setPositionInQueue(totalAmountOfBooker + 1);

        LocalDate nearestReturnDate = loanManager.getNearestLoanEndDateByBookId(bookId);

        //TODO improveMapping

        // Mapping
        XMLGregorianCalendar xmlGregorianCalendar =
                DatatypeFactory.newInstance().newXMLGregorianCalendar(nearestReturnDate.toString());

        // return the summary
        summary.setNearestReturnDate(xmlGregorianCalendar);

        return Optional.of(summary);
    }
}
