package com.gg.proj.business;

import com.gg.proj.business.mapper.BookMapper;
import com.gg.proj.consumer.BookRepository;
import com.gg.proj.consumer.LanguageRepository;
import com.gg.proj.consumer.LibraryRepository;
import com.gg.proj.consumer.TopicRepository;
import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import com.gg.proj.model.LibraryEntity;
import com.gg.proj.model.TopicEntity;
import com.gg.proj.service.books.*;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
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
import java.util.UUID;

/**
 * This class contains business methods
 */
@Component
@Transactional
public class BookManager {

    private static final Logger log = LoggerFactory.getLogger(BookManager.class);

    private BookMapper bookMapper;

    private BookRepository bookRepository;

    private TopicRepository topicRepository;

    private LanguageRepository languageRepository;

    private LibraryRepository libraryRepository;

    private TokenManager tokenManager;

    @Autowired
    public BookManager(BookMapper bookMapper, BookRepository bookRepository, TopicRepository topicRepository, LanguageRepository languageRepository, LibraryRepository libraryRepository, TokenManager tokenManager) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.topicRepository = topicRepository;
        this.languageRepository = languageRepository;
        this.libraryRepository = libraryRepository;
        this.tokenManager = tokenManager;
    }

    // CRUD Methods
    public Optional<BookFull> findById(Integer id) {
        Optional<BookEntity> optional = bookRepository.findById(id);
        BookEntity bookEntity = optional.orElse(null);

        if (optional.isPresent()) {
            log.info("findById : Requesting a book by id : " + id + " => found : " + bookEntity);
        } else {
            log.info("findById : Requesting a book by id : " + id + " => id not found in database");
        }
        return Optional.ofNullable(bookMapper.bookEntityToBookFull(bookEntity));
    }

    public Book save(Book book, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {

        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e) {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }

        BookEntity bookEntity = bookRepository.save(bookMapper.bookToBookEntity(book));
        return bookMapper.bookEntityToBook(bookEntity);
    }


    public void delete(Book book, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e) {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }
        bookRepository.delete(bookMapper.bookToBookEntity(book));
    }

    public List<Book> findAll() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return bookMapper.bookEntityListToBookList(bookEntities);
    }

    public SearchBooksResponse searchBooks(String keyWord, Integer page, Integer size) {

        log.debug("searchBooks");
        SearchBooksResponse response = new SearchBooksResponse();
        // We prepare a PageRequest with a default sort value
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "title");
        List<Book> books = response.getBooks();

        List<Language> languages = response.getLanguages();
        List<Topic> topics = response.getTopics();
        List<Library> libraries = response.getLibraries();

        Page<BookEntity> pagedBooks = bookRepository.searchPagedBooks("%" + keyWord + "%", pageRequest);
        List<BookEntity> bookEntities = pagedBooks.getContent();
        // Here we access the book list by reference (no need for setter)
        books.addAll(bookMapper.bookEntityListToBookList(bookEntities));

        // Now we fetch all books to extract their metadata
        List<BookEntity> bookEntitiesAllFetched = bookRepository.searchAllBooks("%" + keyWord + "%");
        List<LanguageEntity> languageEntities = languageRepository.findDistinctByBooksIn(bookEntitiesAllFetched);
        List<LibraryEntity> libraryEntities = libraryRepository.findDistinctByBooksIn(bookEntitiesAllFetched);
        List<TopicEntity> topicEntities = topicRepository.findDistinctByBooksIn(bookEntitiesAllFetched);

        languages.addAll(bookMapper.languageEntityListToLanguageList(languageEntities));
        libraries.addAll(bookMapper.libraryEntityListToLibraryList(libraryEntities));
        topics.addAll(bookMapper.topicEntityListToTopicList(topicEntities));

        response.setKeyWord(keyWord);
        response.setTotalPages(pagedBooks.getTotalPages());

        return response;
    }


    public FilterBooksResponse filterBooks(String keyWord, Integer languageId, Integer libraryId, Integer topicId, boolean available, Integer page, Integer size) {
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

    public Optional<Book> create(BookMin bookMin, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e) {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }

        BookEntity bookEntity = bookMapper.bookMinToBookEntity(bookMin);
        bookEntity = bookRepository.save(bookEntity);
        return Optional.ofNullable(bookMapper.bookEntityToBook(bookEntity));
    }
}
