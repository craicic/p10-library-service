package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {

    List<LanguageEntity> findDistinctByBooksIn(List<BookEntity> books);

}
