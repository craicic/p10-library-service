package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {

    Page<BookEntity> search(String keyWord, Integer languageId, Integer libraryId, Integer topicId, Boolean available, Pageable pageable);
}
