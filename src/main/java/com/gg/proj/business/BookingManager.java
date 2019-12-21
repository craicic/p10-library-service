package com.gg.proj.business;

import com.gg.proj.business.mapper.BookingMapper;
import com.gg.proj.consumer.BookingRepository;
import com.gg.proj.model.BookingEntity;
import com.gg.proj.service.bookings.*;
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
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    private BookingMapper bookingMapper;

    @Autowired
    public BookingManager(TokenManager tokenManager, BookingRepository bookingRepository, LoanManager loanManager, BookManager bookManager, BookingMapper bookingMapper) {
        this.tokenManager = tokenManager;
        this.loanManager = loanManager;
        this.bookManager = bookManager;
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    public Optional<BookingSummary> performBooking(BookingMin booking, String tokenUUID) throws OutdatedTokenException, InvalidTokenException, InvalidBookingOperationException, DatatypeConfigurationException {
        log.debug("Entering performBooking...");

        // Check UUID
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }

        // Mapping
        BookingEntity bookingEntity = bookingMapper.bookingMinToEntity(booking);

        // Check if all sample are lend
        if (bookManager.getQuantity(bookingEntity.getBook().getId()) > 0) {
            throw new InvalidBookingOperationException("You can't book if the item is already available");
        }

        // Sample count
        Integer totalAmountOfBook = bookManager.getQuantity(bookingEntity.getBook().getId()) + loanManager.getBookCount(bookingEntity.getBook().getId());

        // User count
        Integer totalAmountOfBooker = bookingRepository.countByBookId(bookingEntity.getBook().getId());

        // Check queue vs booker
        if (totalAmountOfBooker >= 2 * totalAmountOfBook) {
            //TODO IDEA: could return useful infos
            throw new InvalidBookingOperationException("Too much users booked this item");
        }

        // Set the timestamp
        bookingEntity.setBookingTime(LocalDateTime.now());

        // Save the booking
        BookingEntity persistedBookingEntity = bookingRepository.save(bookingEntity);

        // Create a PlaceInQueue object, it contains the place in queue and the next return date
        PlaceInQueue place = new PlaceInQueue();

        // Place in queue
        Long positionLong = bookingRepository.queryForPositionInQueue(
                persistedBookingEntity.getBook().getId(),
                persistedBookingEntity.getBookingTime());

        int position = Math.toIntExact(positionLong);
        place.setPositionInQueue(position);

        // The next return date
        LocalDate nextDate = loanManager.getNearestLoanEndDateByBookId(bookingEntity.getBook().getId());

        // Mapping it to XML date format and setting it into the PlaceInQueue Object
        XMLGregorianCalendar nextDateXml = bookingMapper.localToXmlDate(nextDate);
        place.setNearestReturnDate(nextDateXml);

        // Set the summary
        BookingSummary summary = new BookingSummary();

        // Mapping the booking object that was return from save method.
        summary.setBooking(bookingMapper.bookingEntityToMin(persistedBookingEntity));
        summary.setPlaceInQueue(place);
        // return the summary

        return Optional.of(summary);
    }

    public Integer cancelBooking(Booking booking, String tokenUUID) throws OutdatedTokenException, InvalidTokenException {
        log.debug("Entering cancelBooking...");

        // Check UUID
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }

        // Mapping
        BookingEntity bookingEntity = bookingMapper.bookingToEntity(booking);

        // Delete the row in database
        bookingRepository.delete(bookingEntity);

        // Returning the confirmation code
        return 1;
    }

    public List<BookingInfo> getBookingsByUserId(int userId, String tokenUUID) throws OutdatedTokenException, InvalidTokenException {
        log.debug("Entering getBookingsByUserId...");

        // Check UUID
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
        BookingInfo bookingInfo = new BookingInfo();
        return null;
    }
}
