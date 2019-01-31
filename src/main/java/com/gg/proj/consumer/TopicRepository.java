package com.gg.proj.consumer;

import com.gg.proj.model.TopicEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TopicRepository extends PagingAndSortingRepository<TopicEntity,Integer> {
}
