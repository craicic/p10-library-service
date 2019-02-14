package com.gg.proj.business.mapper;

import com.gg.proj.model.TopicEntity;
import com.gg.proj.service.books.Topic;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    Topic topicEntityToTopic(TopicEntity topicEntity);

    TopicEntity topicToTopicEntity(Topic topic);

    List<Topic> topicEntityListToTopicList(List<TopicEntity> topicEntities);
}
