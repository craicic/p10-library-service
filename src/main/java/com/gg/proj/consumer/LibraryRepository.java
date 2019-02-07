package com.gg.proj.consumer;

import com.gg.proj.model.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Integer> {

}
