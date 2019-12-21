package com.gg.proj.consumer;

import com.gg.proj.model.BookingEntity;
import com.gg.proj.model.complex.BookingInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    Integer countByBookId(int bookId);

    @Query("SELECT Count(bng) FROM BookingEntity AS bng WHERE bng.bookingTime <= (:bookingTime) AND bng.book.id = (:bookId)")
    Long queryForPositionInQueue(
            @Param("bookId") Integer bookId,
            @Param("bookingTime") LocalDateTime bookingTime);


//    @Query(value = "SELECT new com.gg.proj.model.complex.BookingInfoModel(booking ,booking.book, count(bookingForCount.user), LoanEntity.loanEndDate) " +
//            "FROM BookingEntity AS booking , BookingEntity AS bookingForCount , LoanEntity AS loan " +
//            "WHERE booking.user.id = (:userId)" +
//            "AND EXISTS (" +
//            "SELECT booking2 FROM bookingForCount " +
//            "WHERE booking2.book.id = booking.book.id " +
//            "AND booking2.bookingTime > booking.bookingTime)" +
//            "AND EXISTS (SELECT loan ) ")
//    List<BookingInfoModel> megaQueryByLeoEtGG(@Param("userId") int userId);

    @Query("SELECT new com.gg.proj.model.complex.BookingInfoModel(booking ,booking.book, COUNT(bookingForCount.user), MIN(loan.loanEndDate)) " +
    "FROM BookingEntity AS booking, " +
    "BookingEntity AS bookingForCount, " +
    "LoanEntity AS loan " +
    "WHERE booking.user.id = (:userId) " +
    "AND bookingForCount.book = booking.book " +
    "AND bookingForCount.bookingTime <= booking.bookingTime " +
    "AND booking.book.id = loan.book.id " +
    "GROUP BY booking"
    )
    List<BookingInfoModel> megaQuery(@Param("userId") int userId);

}