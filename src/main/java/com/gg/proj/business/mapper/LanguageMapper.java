package com.gg.proj.business.mapper;

import com.gg.proj.model.LanguageEntity;
import com.gg.proj.service.books.Language;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
    LanguageEntity languageToLanguageEntity(Language language);

    Language languageEntityToLanguage(LanguageEntity languageEntity);

    List<Language> languageEntityListToLanguageList(List<LanguageEntity> languageEntities);
}
