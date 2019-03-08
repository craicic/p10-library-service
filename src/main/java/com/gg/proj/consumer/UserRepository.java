package com.gg.proj.consumer;

import com.gg.proj.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByPseudo(String pseudo);

    boolean existsByPseudo(String pseudo);

    boolean existsByMailAddress(String mailaddress);

    @Modifying
    @Query("UPDATE UserEntity u SET u.lastConnection = current_timestamp WHERE u.id = :id ")
    void updateLastConnectionById(@Param("id") Integer id);
}
