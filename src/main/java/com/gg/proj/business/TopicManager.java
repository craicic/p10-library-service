package com.gg.proj.business;

import com.gg.proj.business.mapper.TopicMapper;
import com.gg.proj.consumer.TopicRepository;
import com.gg.proj.model.TopicEntity;
import com.gg.proj.service.books.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class TopicManager {

    private static final Logger log = LoggerFactory.getLogger(TopicManager.class);

    private TopicRepository topicRepository;

    private TopicMapper topicMapper;

    @Autowired
    public TopicManager(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    // CRUD Methods
    public Optional<Topic> findById(Integer id) {
        Optional<TopicEntity> optional = topicRepository.findById(id);
        TopicEntity topicEntity = optional.orElse(null);

        if (optional.isPresent()) {
            log.info("findById : Requesting a topic by id : " + id + " => found : " + topicEntity);
        } else {
            log.info("findById : Requesting a topic by id : " + id + " => id not found in database");
        }

        return Optional.of(topicMapper.topicEntityToTopic(topicEntity));
    }

    public Topic save(Topic topic) {
        TopicEntity topicEntity = topicRepository.save(topicMapper.topicToTopicEntity(topic));
        return topicMapper.topicEntityToTopic(topicEntity);
    }


    public void delete(Topic topic) {
        topicRepository.delete(topicMapper.topicToTopicEntity(topic));
    }

    public List<Topic> findAll() {
        List<TopicEntity> topicEntities = topicRepository.findAll();
        return topicMapper.topicEntityListToTopicList(topicEntities);
    }
}
