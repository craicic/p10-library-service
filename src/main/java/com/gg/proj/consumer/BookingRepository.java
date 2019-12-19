package com.gg.proj.consumer;

import com.gg.proj.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    Integer countByBookId(int bookId);

    @Query("SELECT Count(B) FROM BookingEntity AS B WHERE B.bookingTime <= (:bookingTime) AND B.book.id = (:bookId)")
    Long queryForPositionInQueue(
            @Param("bookId") Integer bookId,
            @Param("userId") Integer userId,
            @Param("bookingTime") LocalDateTime bookingTime);
}
