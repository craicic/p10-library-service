package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends PagingAndSortingRepository<BookEntity, Integer> {

    @Query("select b from BookEntity b where upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)")
    public Page<BookEntity> search(@Param("x") String keyWord, Pageable pageable);
}
