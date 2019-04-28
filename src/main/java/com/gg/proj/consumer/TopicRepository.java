package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface, it extends JpaRepository to benefit spring Data / JPA
 */
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {

    List<TopicEntity> findDistinctByBooksIn(List<BookEntity> books);

    @Query("SELECT DISTINCT t FROM TopicEntity t JOIN t.books b WHERE b.id = (:bookId) ")
    List<TopicEntity> findDistinctByBookId(@Param("bookId") Integer bookId);

}
