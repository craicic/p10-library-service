package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A custom Repository interface, it's method are implemented into {@link com.gg.proj.consumer.implementation.BookRepositoryImpl}
 */
public interface BookRepositoryCustom {

    Page<BookEntity> search(String keyWord, Integer languageId, Integer libraryId, Integer topicId, Boolean available, Pageable pageable);
}
