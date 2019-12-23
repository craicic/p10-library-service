package com.gg.proj.consumer;

import com.gg.proj.model.BookingEntity;
import com.gg.proj.model.complex.BookingInfoModel;
import com.gg.proj.model.complex.BookingSummaryModel;
import com.gg.proj.model.complex.PlaceInQueueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    Integer countByBookId(int bookId);

    @Query("SELECT new com.gg.proj.model.complex.BookingInfoModel(booking ,booking.book, COUNT(bookingForCount.user), MIN(loan.loanEndDate)) " +
            "FROM BookingEntity AS booking, " +
            "BookingEntity AS bookingForCount, " +
            "LoanEntity AS loan " +
            "WHERE booking.user.id = (:userId) " +
            "AND bookingForCount.book = booking.book " +
            "AND bookingForCount.bookingTime <= booking.bookingTime " +
            "AND  loan.book.id  = booking.book.id " +
            "GROUP BY booking")
    List<BookingInfoModel> queryBookingInfo(@Param("userId") int userId);

    @Query("SELECT new com.gg.proj.model.complex.BookingSummaryModel(booking, COUNT(bookingForCount.user), MIN(loan.loanEndDate)) " +
            "FROM BookingEntity booking," +
            "BookingEntity bookingForCount, " +
            "LoanEntity loan " +
            "WHERE booking.id = (:id) " +
            "AND bookingForCount.book = booking.book " +
            "AND bookingForCount.bookingTime <= booking.bookingTime " +
            "AND loan.book.id = booking.book.id " +
            "GROUP BY booking")
    BookingSummaryModel queryBookingSummary(@Param("id") Integer id);

    @Query("SELECT new com.gg.proj.model.complex.PlaceInQueueModel(COUNT(bookingForCount.user), MIN(loan.loanEndDate)) " +
            "FROM BookingEntity booking, " +
            "BookingEntity bookingForCount," +
            "LoanEntity loan " +
            "WHERE booking.book.id = (:bookId) " +
            "AND booking.user.id = (:userId) " +
            "AND bookingForCount.book = (:bookId) " +
            "AND bookingForCount.bookingTime <= booking.bookingTime " +
            "AND loan.book.id = (:bookId) " +
            "GROUP BY booking ")
    PlaceInQueueModel queryForPlaceInQueue(@Param("bookId") int bookId,@Param("userId") int userId);
}