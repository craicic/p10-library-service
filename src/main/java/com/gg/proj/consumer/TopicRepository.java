package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface, it extends JpaRepository to benefit spring Data / JPA
 */
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {

    List<TopicEntity> findDistinctByBooksIn(List<BookEntity> books);
}
