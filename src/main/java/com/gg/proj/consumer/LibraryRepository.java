package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Integer> {

    List<LibraryEntity> findDistinctByBooksIn(List<BookEntity> books);
}
