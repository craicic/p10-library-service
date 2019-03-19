package com.gg.proj.consumer;

import com.gg.proj.model.LoanEntity;
import com.gg.proj.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

    @Query("SELECT l FROM LoanEntity as l WHERE l.user.id = (:userId) AND l.closed = false")
    List<LoanEntity> findDistinctLoanByUserIdAndClosedIsFalse(@Param("userId") Integer userId);
}
