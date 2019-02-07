package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<TopicEntity,Integer> {

    List<TopicEntity> findDistinctByBooks(List<BookEntity> books);
}
