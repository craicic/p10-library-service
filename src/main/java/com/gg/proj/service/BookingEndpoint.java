package com.gg.proj.service;

import com.gg.proj.business.BookingManager;
import com.gg.proj.service.bookings.*;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

/**
 * <p>This class is registered with Spring WS as a candidate for processing incoming SOAP messages (via Endpoint annotation).</p>
 *
 * <p>Other annotations you'll find in this class :</p>
 *
 * <p>PayloadRoot is then used by Spring WS to pick the handler method based on the message’s namespace and localPart.</p>
 * <p>RequestPayload indicates that the incoming message will be mapped to the method’s request parameter.</p>
 * <p>ResponsePayload annotation makes Spring WS map the returned value to the response payload.</p>
 */
@Endpoint
public class BookingEndpoint {

    private static final Logger log = LoggerFactory.getLogger(BookingEndpoint.class);

    private static final String NAMESPACE_URI = "http://proj.gg.com/service/bookings";

    private BookingManager bookingManager;

    @Autowired
    public BookingEndpoint(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "performBookingRequest")
    @ResponsePayload
    public PerformBookingResponse performBooking(@RequestPayload PerformBookingRequest request) {
        log.info("Call from network - performBooking : userId=[" + request.getBookingMin().getUserId() + "]"
                + ", bookId=[" + request.getBookingMin().getBookId() + "]"
                + ", tokenUUID=[" + request.getTokenUUID() + "]");
        PerformBookingResponse response = new PerformBookingResponse();

        try {
            Optional<BookingSummary> opt = bookingManager.performBooking(request.getBookingMin(), request.getTokenUUID());
            opt.ifPresent(response::setBookingSummary);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cancelBookingRequest")
    @ResponsePayload
    public CancelBookingResponse cancelBooking(@RequestPayload CancelBookingRequest request) {
        log.info("Call from network - cancelBooking : userId=[" + request.getBooking().getUserId() + "]"
                + ", bookId=[" + request.getBooking().getBookId() + "]"
                + ", bookingId=[" + request.getBooking().getId() + "]"
                + ", tokenUUID=[" + request.getTokenUUID() + "]");
        CancelBookingResponse response = new CancelBookingResponse();
        Integer confirmationCode = -1;
        try {
            confirmationCode = bookingManager.cancelBooking(request.getBooking(), request.getTokenUUID());
            response.setConfirmationCode(confirmationCode);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookingListByUserIdRequest")
    @ResponsePayload
    public GetBookingListByUserIdResponse getBookingListByUserIdResponse(@RequestPayload GetBookingListByUserIdRequest request) {
        log.info("Call from network - getBookingListByUserIdResponse : userId=[" + request.getUserId() + "]"
                + ", tokenUUID=[" + request.getTokenUUID() + "]");
        GetBookingListByUserIdResponse response = new GetBookingListByUserIdResponse();
        try {
            response.getBookingsInfo().addAll(bookingManager.getBookingsByUserId(request.getUserId(), request.getTokenUUID()));
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPlaceInQueueByBookingRequest")
    @ResponsePayload
    public GetPlaceInQueueByBookingResponse getPlaceInQueueByBookingResponse(@RequestPayload GetPlaceInQueueByBookingRequest request) {
        log.info("Call from network - getPlaceInQueueByBookingResponse : bookId=[" + request.getBookId() + "], userId=[" + request.getUserId() + "]"
                + ", tokenUUID=[" + request.getTokenUUID() + "]");
        GetPlaceInQueueByBookingResponse response = new GetPlaceInQueueByBookingResponse();
        try {
            Optional<PlaceInQueue> opt = bookingManager.getPlaceInQueueByBooking(request.getBookId(), request.getUserId(), request.getTokenUUID());
            opt.ifPresent(response::setPlaceInQueue);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }
}
