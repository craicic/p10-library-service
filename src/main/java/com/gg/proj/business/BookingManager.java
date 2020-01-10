package com.gg.proj.business;

import com.gg.proj.business.mapper.BookingMapper;
import com.gg.proj.consumer.BookingRepository;
import com.gg.proj.model.BookingEntity;
import com.gg.proj.model.complex.BookingInfoModel;
import com.gg.proj.model.complex.BookingSummaryModel;
import com.gg.proj.model.complex.BorrowerModel;
import com.gg.proj.model.complex.PlaceInQueueModel;
import com.gg.proj.service.bookings.*;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.InvalidBookingOperationException;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import com.gg.proj.util.CustomMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class BookingManager {

    private static final Logger log = LoggerFactory.getLogger(BookingManager.class);

    private TokenManager tokenManager;
    private BookManager bookManager;
    private BookingRepository bookingRepository;
    private BookingMapper bookingMapper;
    private CustomMailService mailService;

    @Autowired
    public BookingManager(TokenManager tokenManager, BookingRepository bookingRepository, BookManager bookManager, BookingMapper bookingMapper, CustomMailService mailService) {
        this.tokenManager = tokenManager;
        this.bookManager = bookManager;
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.mailService = mailService;
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

        // Sample book count
        int totalAmountOfBook = bookManager.getTotalAmountOfBook(bookingEntity.getBook().getId()).intValue();
        // User count
        int totalAmountOfBooker = bookingRepository.countByBookId(bookingEntity.getBook().getId());

        // Check queue vs booker
        if (totalAmountOfBooker >= 2 * totalAmountOfBook) {
            //TODO IDEA: could return useful infos
            throw new InvalidBookingOperationException("Too much users booked this item");
        }

        // Set the timestamp
        bookingEntity.setBookingTime(LocalDateTime.now());

        // Save the booking
        BookingEntity persistedBookingEntity = bookingRepository.save(bookingEntity);

        BookingSummaryModel bookingSummaryModel = bookingRepository.queryBookingSummary(persistedBookingEntity.getId());

        BookingSummary bookingSummaryDto = bookingMapper.bookingSummaryModelToBookingSummaryDto(bookingSummaryModel);

        // return the summary
        return Optional.of(bookingSummaryDto);
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

        // Fetch all bookingInfo
        List<BookingInfoModel> models = bookingRepository.queryBookingInfo(userId);

        // Mapping
        return bookingMapper.modelsToDtos(models);
    }

    public Optional<PlaceInQueue> getPlaceInQueueByBooking(int bookId, int userId, String tokenUUID) throws OutdatedTokenException, InvalidTokenException {
        log.debug("Entering getPlaceInQueueByBooking...");

        // Check UUID
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }

        // Fetching info on the place in queue
        PlaceInQueueModel model = bookingRepository.queryForPlaceInQueue(bookId, userId);

        // Mapping
        PlaceInQueue dto = bookingMapper.placeInQueueModelToDto(model);

        return Optional.ofNullable(dto);
    }

    protected void notifyUserByBookId(int bookId) {
        log.debug("Entering notifyUsersByBookId...");
        // Fetching next user in queue (this method should be public cause the batch could call it)
        BorrowerModel nextBorrower = bookingRepository.queryForBorrower(bookId).get(0);

        // Setting the bookingTime
        if (nextBorrower != null) {
            bookingRepository.updateNotificationTime(nextBorrower.getId(), bookId);
            // Calling a mail util class to send them the mail
            mailService.sendSimpleMail(nextBorrower.getMailAddress(), nextBorrower.getFirstName(), nextBorrower.getLastName(), nextBorrower.getBookName(), nextBorrower.getLibraryName());
        }
    }
}
