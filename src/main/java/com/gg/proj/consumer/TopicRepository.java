package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.TopicEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TopicRepository extends PagingAndSortingRepository<TopicEntity,Integer> {

    public List<TopicEntity> findDistinctByBooks(List<BookEntity> books);
}
