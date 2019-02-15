package com.gg.proj.business;

import com.gg.proj.business.mapper.BookMapper;
import com.gg.proj.consumer.BookRepository;
import com.gg.proj.consumer.LanguageRepository;
import com.gg.proj.consumer.LibraryRepository;
import com.gg.proj.consumer.TopicRepository;
import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import com.gg.proj.model.LibraryEntity;
import com.gg.proj.service.books.*;
import com.gg.proj.util.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class contains business methods
 */
@Component
@Transactional
public class BookManager {

    private static final Logger log = LoggerFactory.getLogger(BookManager.class);

    private BookMapper bookMapper;

    private BookRepository bookRepository;

    private LanguageRepository languageRepository;

    private TopicRepository topicRepository;

    private LibraryRepository libraryRepository;

    @Autowired
    public BookManager(BookMapper bookMapper, BookRepository bookRepository, LanguageRepository languageRepository,
                       TopicRepository topicRepository, LibraryRepository libraryRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.languageRepository = languageRepository;
        this.topicRepository = topicRepository;
        this.libraryRepository = libraryRepository;
    }

    /**
     * This method take a request as parameter and build a response
     *
     * @param request a {@link SearchBooksResponse}
     * @return a {@link SearchBooksResponse}
     */
    public SearchBooksResponse searchBooks(SearchBooksRequest request) {

        log.debug("searchBooks");
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
        books.addAll(bookMapper.bookEntityListToBookList(bookEntities));

        // Once we have a list of books, we can stream it to extract their OneToOne language and library
        List<LanguageEntity> streamedLanguages = bookEntities.stream()
                .filter(Predicates.distinctByKey(BookEntity::getLanguage))
                .map(BookEntity::getLanguage)
                .collect(Collectors.toList());

        List<LibraryEntity> streamedLibraries = bookEntities.stream()
                .filter(Predicates.distinctByKey(BookEntity::getLibrary))
                .map(BookEntity::getLibrary)
                .collect(Collectors.toList());

        languages.addAll(bookMapper.languageEntityListToLanguageList(streamedLanguages));
        libraries.addAll(bookMapper.libraryEntityListToLibraryList(streamedLibraries));

        // We now fetch all topics
        topics.addAll(bookMapper.topicEntityListToTopicList(topicRepository.findDistinctByBooks(page.getContent())));

        // We add keyWord to the response
        response.setKeyWord(request.getKeyWord());

        return response;
    }

    // CRUD Methods
    public Optional<Book> findById(Integer id) {
        Optional<BookEntity> optional = bookRepository.findById(id);
        BookEntity bookEntity = bookRepository.findById(id).orElse(null);

        if (optional.isPresent()) {
            log.info("findById : Requesting a book by id : " + id + " => found : " + bookEntity);
        } else {
            log.info("findById : Requesting a book by id : " + id + " => id not found in database");
        }
        return Optional.ofNullable(bookMapper.bookEntityToBook(bookEntity));
    }

    public Book save(Book book) {
        BookEntity bookEntity = bookRepository.save(bookMapper.bookToBookEntity(book));
        return bookMapper.bookEntityToBook(bookEntity);
    }


    public void delete(Book book) {
        bookRepository.delete(bookMapper.bookToBookEntity(book));
    }

    public List<Book> findAll() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookMapper.bookEntityListToBookList(bookEntities);
    }

    public FilterBooksResponse search(String keyWord, Integer languageId, Integer libraryId, Integer topicId, boolean available, Integer page, Integer size) {
        FilterBooksResponse response = new FilterBooksResponse();
        List<Book> books = response.getBooks();
        // Preparing a PageRequest in order to get a sorted and paged list
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "title");
        Page<BookEntity> pageBook = bookRepository.search(keyWord, languageId, libraryId, topicId, available, pageRequest);

        // Access via reference
        books.addAll(bookMapper.bookEntityListToBookList(pageBook.getContent()));
        response.setTotalPages(pageBook.getTotalPages());
        return response;
    }
}
