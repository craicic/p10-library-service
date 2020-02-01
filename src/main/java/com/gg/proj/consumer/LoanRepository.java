package com.gg.proj.consumer;

import com.gg.proj.model.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface, it extends JpaRepository to benefit spring Data / JPA
 */
public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

    @Query("SELECT l FROM LoanEntity as l WHERE l.user.id = (:userId) AND l.closed = false")
    List<LoanEntity> findDistinctLoanByUserIdAndClosedIsFalse(@Param("userId") Integer userId);

    Integer countByBookId(Integer bookId);

    @Query("SELECT MIN(L.loanEndDate) FROM LoanEntity AS L WHERE L.book.id = (:bookId)")
    LocalDate getNearestLoanEndDate(@Param("bookId") Integer bookId);
}
