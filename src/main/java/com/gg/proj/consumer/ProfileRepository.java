package com.gg.proj.consumer;

import com.gg.proj.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserEntity, Integer> {
}
