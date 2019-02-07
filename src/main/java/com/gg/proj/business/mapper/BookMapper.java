package com.gg.proj.business.mapper;

import com.gg.proj.model.*;
import com.gg.proj.service.library.*;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * This is the interface that drive MapStruct framework, you simply add the method signature and the framework drive the
 * whole mapping by generating implementation classes.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    //    BOOK
    Book bookEntityToBook(BookEntity bookEntity);

    List<Book> bookEntityListToBookList(List<BookEntity> bookEntities);

    List<Language> languageEntityListToLanguageList(List<LanguageEntity> languageEntities);

    List<Topic> topicEntityListToTopicList(List<TopicEntity> topicEntities);

    List<Library> libraryEntityListToLibraryList(List<LibraryEntity> libraryEntities);

}
