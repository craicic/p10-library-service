package com.gg.proj.consumer;

import com.gg.proj.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByPseudo(String pseudo);

    boolean existsByPseudo(String pseudo);

    boolean existsByMailAddress(String mailaddress);
}
