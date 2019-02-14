package com.gg.proj.business;

import com.gg.proj.business.mapper.LanguageMapper;
import com.gg.proj.consumer.LanguageRepository;
import com.gg.proj.model.LanguageEntity;
import com.gg.proj.service.books.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class LanguageManager {

    private static final Logger log = LoggerFactory.getLogger(LanguageManager.class);

    private LanguageRepository languageRepository;

    private LanguageMapper languageMapper;

    @Autowired
    public LanguageManager(LanguageRepository languageRepository, LanguageMapper languageMapper) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
    }

    // CRUD Methods
    public Optional<Language> findById(Integer id) {
        Optional<LanguageEntity> optional = languageRepository.findById(id);
        LanguageEntity languageEntity = optional.orElse(null);

        if (optional.isPresent()) {
            log.info("findById : Requesting a language by id : " + id + " => found : " + languageEntity);
        } else {
            log.info("findById : Requesting a language by id : " + id + " => id not found in database");
        }

        return Optional.of(languageMapper.languageEntityToLanguage(languageEntity));
    }

    public Language save(Language language) {
        LanguageEntity languageEntity = languageRepository.save(languageMapper.languageToLanguageEntity(language));
        return languageMapper.languageEntityToLanguage(languageEntity);
    }


    public void delete(Language language) {
        languageRepository.delete(languageMapper.languageToLanguageEntity(language));
    }

    public List<Language> findAll() {
        List<LanguageEntity> languageEntities = languageRepository.findAll();
        return languageMapper.languageEntityListToLanguageList(languageEntities);
    }

}
