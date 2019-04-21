package com.gg.proj.consumer;

import com.gg.proj.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface, it extends JpaRepository to benefit spring Data / JPA
 */
public interface ProfileRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByPseudo(String pseudo);

    boolean existsByMailAddress(String mailAddress);

    @Query("SELECT DISTINCT u FROM UserEntity u " +
            "JOIN u.loans as loan " +
            "WHERE loan.loanEndDate < current_date AND loan.closed = false")
    List<UserEntity> findLatecomers();
}
