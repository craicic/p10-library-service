package com.gg.proj.consumer;

import com.gg.proj.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    Integer countByBookId(int bookId);

    @Query("UPDATE BookingEntity B SET B.book.id = (:userId)")
    void saveBooking(@Param("userId") int userId, @Param("bookId") int bookId);
}
