package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<BookEntity, Integer>, BookRepositoryCustom {

    @Query("select distinct b from BookEntity b where upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)")
    Page<BookEntity> search(@Param("x") String keyWord, Pageable pageable);

    @Query("select distinct b from BookEntity b where (upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x))and b.quantity > 0")
    Page<BookEntity> searchAvailable(@Param("x") String keyWord, Pageable pageable);

}
