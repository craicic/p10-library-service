package com.gg.proj.business;

import com.gg.proj.business.mapper.TopicMapper;
import com.gg.proj.consumer.TopicRepository;
import com.gg.proj.model.TopicEntity;
import com.gg.proj.service.books.Topic;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class TopicManager {

    private static final Logger log = LoggerFactory.getLogger(TopicManager.class);

    private TopicRepository topicRepository;

    private TopicMapper topicMapper;

    private TokenManager tokenManager;

    @Autowired
    public TopicManager(TopicRepository topicRepository, TopicMapper topicMapper, TokenManager tokenManager) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
        this.tokenManager = tokenManager;
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

    public Optional<Topic> save(Topic topic, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e)  {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }

        TopicEntity topicEntity = topicRepository.save(topicMapper.topicToTopicEntity(topic));
        return Optional.ofNullable(topicMapper.topicEntityToTopic(topicEntity));
    }


    public void delete(Topic topic, String tokenUUID) throws InvalidTokenException, OutdatedTokenException  {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e)  {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }

        topicRepository.delete(topicMapper.topicToTopicEntity(topic));
    }

    public List<Topic> findAll() {
        List<TopicEntity> topicEntities = topicRepository.findAll();
        return topicMapper.topicEntityListToTopicList(topicEntities);
    }

    public Optional<Topic> create(String topicName, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e) {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setName(topicName);
        topicEntity = topicRepository.save(topicEntity);

        return Optional.ofNullable(topicMapper.topicEntityToTopic(topicEntity));
    }

}
