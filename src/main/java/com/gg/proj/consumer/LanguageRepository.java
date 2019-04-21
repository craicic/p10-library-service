package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface, it extends JpaRepository to benefit spring Data / JPA
 */
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {

    List<LanguageEntity> findDistinctByBooksIn(List<BookEntity> books);

}
