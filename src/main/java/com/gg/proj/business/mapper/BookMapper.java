package com.gg.proj.business.mapper;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import com.gg.proj.model.LibraryEntity;
import com.gg.proj.model.TopicEntity;
import com.gg.proj.service.books.*;
import org.mapstruct.Mapper;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This is the interface that drive MapStruct framework, you simply add the method signature and the framework drive the
 * whole mapping by generating implementation classes.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    //    BOOK
    default Book bookEntityToBook(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }

        Book book = new Book();

        book.setId(bookEntity.getId());
        book.setTitle(bookEntity.getTitle());
        book.setAuthor(bookEntity.getAuthor());
        book.setIsbn(bookEntity.getIsbn());
        if (bookEntity.getQuantity() != null) {
            book.setQuantity(bookEntity.getQuantity());
        }
        book.setPublicationDate(dateToXmlGregorianCalendar(bookEntity.getPublicationDate()));
        book.setSummary(bookEntity.getSummary());

        book.setLanguageId(bookEntity.getLanguage().getId());
        book.setLibraryId(bookEntity.getLibrary().getId());
        List<Integer> topicIds = new ArrayList<>(bookEntity.getTopics().size());
        for (TopicEntity t : bookEntity.getTopics()) {
            topicIds.add(t.getId());
        }
        book.getTopicIds().addAll(topicIds);
        return book;
    }

    default BookEntity bookToBookEntity(Book book) {
        if (book == null) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();
        TopicEntity topicEntity = new TopicEntity();
        LanguageEntity languageEntity = new LanguageEntity();
        LibraryEntity libraryEntity = new LibraryEntity();

        bookEntity.setId(book.getId());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setQuantity(book.getQuantity());
        bookEntity.setPublicationDate(BookMapper.xmlGregorianCalendarToDate(book.getPublicationDate()));
        bookEntity.setSummary(book.getSummary());

        languageEntity.setId(book.getLanguageId());
        bookEntity.setLanguage(languageEntity);

        libraryEntity.setId(book.getLibraryId());
        bookEntity.setLibrary(libraryEntity);
        List<TopicEntity> topics = new ArrayList<>(book.getTopicIds().size());
        for (Integer topicId : book.getTopicIds()) {
            topicEntity.setId(topicId);
            topics.add(topicEntity);
        }
        bookEntity.getTopics().addAll(topics);

        return bookEntity;
    }

    static XMLGregorianCalendar dateToXmlGregorianCalendar(Date publicationDate) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(publicationDate);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xcal) {
        if (xcal == null) {
            return null;
        }

        return xcal.toGregorianCalendar().getTime();
    }

    List<Book> bookEntityListToBookList(List<BookEntity> bookEntities);

    List<Language> languageEntityListToLanguageList(List<LanguageEntity> languageEntities);

    List<Topic> topicEntityListToTopicList(List<TopicEntity> topicEntities);

    List<Library> libraryEntityListToLibraryList(List<LibraryEntity> libraryEntities);

    BookFull bookEntityToBookFull(BookEntity bookEntity);

    BookEntity bookMinToBook(BookMin bookMin);
}
