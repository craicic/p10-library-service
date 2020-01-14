package com.gg.proj.business.mapper;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import com.gg.proj.model.LibraryEntity;
import com.gg.proj.model.TopicEntity;
import com.gg.proj.model.complex.BookAndBookingInfoModel;
import com.gg.proj.service.books.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setId(topicId);
            topics.add(topicEntity);
        }
//        List<TopicEntity> topicEntities = new ArrayList<>(topics);
//        bookEntity.setTopics(topicEntities);

        bookEntity.setTopics(new ArrayList<>());
        bookEntity.getTopics().addAll(topics);

        return bookEntity;
    }

    default BookEntity bookMinToBookEntity(BookMin bookMin) {
        if (bookMin == null) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        LanguageEntity languageEntity = new LanguageEntity();
        LibraryEntity libraryEntity = new LibraryEntity();

        bookEntity.setTitle(bookMin.getTitle());
        bookEntity.setAuthor(bookMin.getAuthor());
        bookEntity.setIsbn(bookMin.getIsbn());
        bookEntity.setQuantity(bookMin.getQuantity());
        bookEntity.setPublicationDate(BookMapper.xmlGregorianCalendarToDate(bookMin.getPublicationDate()));
        bookEntity.setSummary(bookMin.getSummary());

        languageEntity.setId(bookMin.getLanguageId());
        bookEntity.setLanguage(languageEntity);

        libraryEntity.setId(bookMin.getLibraryId());
        bookEntity.setLibrary(libraryEntity);
        List<TopicEntity> topics = new ArrayList<>(bookMin.getTopicIds().size());
        for (Integer topicId : bookMin.getTopicIds()) {
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setId(topicId);
            topics.add(topicEntity);
        }
//        List<TopicEntity> topicEntities = new ArrayList<>(topics);
//        bookEntity.setTopics(topicEntities);

        bookEntity.setTopics(new ArrayList<>());
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

    @Mappings({
            @Mapping(source = "bookEntity.id", target = "id"),
            @Mapping(source = "bookEntity.title", target = "title"),
            @Mapping(source = "bookEntity.author", target = "author"),
            @Mapping(source = "bookEntity.isbn", target = "isbn"),
            @Mapping(source = "bookEntity.topics", target = "topics"),
            @Mapping(source = "bookEntity.language", target = "language"),
            @Mapping(source = "bookEntity.quantity", target = "quantity"),
            @Mapping(source = "bookEntity.publicationDate", target = "publicationDate"),
            @Mapping(source = "bookEntity.library", target = "library"),
            @Mapping(source = "bookEntity.summary", target = "summary"),
    })
    BookAndBookingInfo bookAndBookingInfoToDTO(BookAndBookingInfoModel bookAndBookingInfoModel);
}
