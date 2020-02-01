package com.gg.proj.business.mapper;

import com.gg.proj.model.BookingEntity;
import com.gg.proj.model.complex.BookingInfoModel;
import com.gg.proj.model.complex.BookingSummaryModel;
import com.gg.proj.model.complex.PlaceInQueueModel;
import com.gg.proj.service.bookings.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.List;

/**
 * This is the interface that drive MapStruct framework, you simply add the method signature and the framework drive the
 * whole mapping by generating implementation classes.
 */
@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mappings({
            @Mapping(source = "bookId", target = "book.id"),
            @Mapping(source = "userId", target = "user.id")
    })
    BookingEntity bookingMinToEntity(BookingMin booking);

    default XMLGregorianCalendar localToXmlDate(LocalDate date) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
    }

    @Mappings({
            @Mapping(source = "book.id", target = "bookId"),
            @Mapping(source = "user.id", target = "userId")
    })
    Booking bookingEntityToMin(BookingEntity persistedBookingEntity);

    @Mappings({
            @Mapping(source = "bookId", target = "book.id"),
            @Mapping(source = "userId", target = "user.id")
    })
    BookingEntity bookingToEntity(Booking booking);

    @Mappings({
            @Mapping(source = "nextReturnDate", target = "nearestReturnDate"),
            @Mapping(source = "book", target = "bookMin")
    })
    BookingInfo modelToDto(BookingInfoModel model);

    List<BookingInfo> modelsToDtos(List<BookingInfoModel> models);

    @Mappings({
            @Mapping(source = "nextReturnDate", target = "nearestReturnDate"),
    })
    BookingSummary bookingSummaryModelToBookingSummaryDto(BookingSummaryModel bookingSummaryModel);

    @Mappings({
            @Mapping(source = "nextReturnDate", target = "nearestReturnDate"),
    })
    PlaceInQueue placeInQueueModelToDto(PlaceInQueueModel model);
}
