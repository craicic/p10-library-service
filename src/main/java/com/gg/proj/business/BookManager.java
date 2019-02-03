package com.gg.proj.business;

import com.gg.proj.consumer.BookRepository;
import com.gg.proj.consumer.LanguageRepository;
import com.gg.proj.consumer.LibraryRepository;
import com.gg.proj.consumer.TopicRepository;
import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import com.gg.proj.model.LibraryEntity;
import com.gg.proj.service.library.*;
import com.gg.proj.util.MapperWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.gg.proj.util.Predicates;
/**
 * This class apply business method
 */
@Component
@Transactional
public class BookManager {

    private static final Logger log = LoggerFactory.getLogger(BookManager.class);

    private MapperWorker mapperWorker;

    private BookRepository bookRepository;

    private LanguageRepository languageRepository;

    private TopicRepository topicRepository;

    private LibraryRepository libraryRepository;

    @Autowired
    public BookManager(MapperWorker mapperWorker, BookRepository bookRepository, LanguageRepository languageRepository, TopicRepository topicRepository, LibraryRepository libraryRepository) {
        this.mapperWorker = mapperWorker;
        this.bookRepository = bookRepository;
        this.languageRepository = languageRepository;
        this.topicRepository = topicRepository;
        this.libraryRepository = libraryRepository;
    }

    /**
     * This method calls the repository in order to get a book by id. If a such book is present in database, it perform
     * a mapping operation then return the book. Else it returns null.
     *
     * @param id this id of the book
     * @return a unique book mapped into a Book
     */
    public Book getBookById(Integer id) {

        if (!bookRepository.findById(id).isPresent()) {
            log.info("getBookById : Requesting a book by id : " + id + " => id not found in database");
            return null;
        }
        BookEntity bookEntity = bookRepository.findById(id).get();

        log.info("getBookById : Requesting a book by id : " + id + " => found : ");
        // Mapping
        return mapperWorker.bookEntityToBook(bookEntity);
    }

    /**
     * This method take a request as parameter and build a response
     *
     * @param request a SearchBooksRequest
     * @return a searchBooksResponse
     */
    public SearchBooksResponse searchBooks(SearchBooksRequest request) {

        SearchBooksResponse response = new SearchBooksResponse();
        // We prepare a PageRequest with a default sort value
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.Direction.ASC, "title");
        List<Book> books = response.getBooks();

        List<Language> languages = response.getLanguages();
        List<Topic> topics = response.getTopics();
        List<Library> libraries = response.getLibraries();


        Page<BookEntity> page = bookRepository.search("%" + request.getKeyWord() + "%", pageRequest);
        List<BookEntity> bookEntities = page.getContent();

        // Here we access the book list by reference (no need for setter)
        books.addAll(mapperWorker.bookEntityListToBookList(bookEntities));

        // Once we have a list of books, we can stream it to extract their OneToOne language and library
        List<LanguageEntity> streamedLanguages = bookEntities.stream()
                .filter(Predicates.distinctByKey(BookEntity::getLanguage))
                .map(BookEntity::getLanguage)
                .collect(Collectors.toList());

        List<LibraryEntity> streamedLibraries = bookEntities.stream()
                .filter(Predicates.distinctByKey(BookEntity::getLibrary))
                .map(BookEntity::getLibrary)
                .collect(Collectors.toList());

        languages.addAll(mapperWorker.languageEntityListToLanguageList(streamedLanguages));
        libraries.addAll(mapperWorker.libraryEntityListToLibraryList(streamedLibraries));

        // We now fetch all topics
        topics.addAll(mapperWorker.topicEntityListToTopicList(topicRepository.findDistinctByBooks(page.getContent())));

        // We add keyWord to the response
        response.setKeyWord(request.getKeyWord());

        return response;
    }

    public FilterBooksResponse filterBooks(FilterBooksRequest request){
        FilterBooksResponse response = new FilterBooksResponse();
        return response;
    }
}
