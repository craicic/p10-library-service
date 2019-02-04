package com.gg.proj.util;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import com.gg.proj.model.LibraryEntity;
import com.gg.proj.model.TopicEntity;
import com.gg.proj.service.library.Book;
import com.gg.proj.service.library.Language;
import com.gg.proj.service.library.Library;
import com.gg.proj.service.library.Topic;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperWorker {

    Book bookEntityToBook(BookEntity bookEntity);

    List<Book> bookEntityListToBookList(List<BookEntity> bookEntities);

    List<Language> languageEntityListToLanguageList(List<LanguageEntity> languageEntities);

    List<Topic> topicEntityListToTopicList(List<TopicEntity> topicEntities);

    List<Library> libraryEntityListToLibraryList(List<LibraryEntity> libraryEntities);

    LibraryEntity libraryToLibraryEntity(Library library);

    LanguageEntity languageToLanguageEntity(Language language);

    TopicEntity topicToTopicEntity(Topic topic);
}
