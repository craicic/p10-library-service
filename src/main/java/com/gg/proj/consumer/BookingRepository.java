package com.gg.proj.consumer;

import com.gg.proj.model.BookingEntity;
import com.gg.proj.model.UserEntity;
import com.gg.proj.model.complex.BookingInfoModel;
import com.gg.proj.model.complex.BookingSummaryModel;
import com.gg.proj.model.complex.BorrowerModel;
import com.gg.proj.model.complex.PlaceInQueueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.SqlResultSetMapping;
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

//    @Query(value = "SELECT u.id, u.mail_address FROM booking AS b " +
//            "JOIN users AS u ON b.user_id = u.id " +
//            "WHERE b.book_id = (:bookId) " +
//            "ORDER BY b.time LIMIT 1 "
//            , nativeQuery = true
//    )
//    UserEntity queryForBorrower(@Param("bookId") int bookId);

    @Query(value = "SELECT new com.gg.proj.model.complex.BorrowerModel(booking.user.id, booking.user.mailAddress, booking.user.firstName, booking.user.lastName, booking.book.title, booking.book.library.name) " +
            "FROM BookingEntity booking " +
            "WHERE booking.book.id = (:bookId) " +
            "ORDER BY booking.bookingTime ASC "
    )
    List<BorrowerModel> queryForBorrower(@Param("bookId") int bookId);

    @Modifying
    @Query(value= "UPDATE BookingEntity booking " +
            "SET booking.notificationTime = CURRENT_TIMESTAMP " +
            "WHERE booking.user.id = (:userId) " +
            "AND booking.book.id = (:bookId) ")
    void updateNotificationTime(@Param("userId") int  userId, @Param("bookId") int bookId);
}