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
 * <p>This class contains business methods</p>
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
        log.debug("Entering delete method with UUID : [" + tokenUUID + "]");
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

    /**
     *
     * <p>This method calls the Consumer layer several time in order to get following results :</p>
     * <ul>
     *     <li>A paged list of book matching the keyWord,</li>
     *     <li>A list of book matching the keyWord,</li>
     *     <li>All libraries, topics and languages that pair with this list of book (in order to populate advanced
     *     search selectors).</li>
     * </ul>
     *
     * @param keyWord the keyWord of the search
     * @param page the current page
     * @param size the size
     * @return SearchBooksResponse an object that contains several lists (libraries, languages, searched books, etc...) plus other data
     */
    public SearchBooksResponse searchBooks(String keyWord, Integer page, Integer size) {

        SearchBooksResponse response = new SearchBooksResponse();
        // We prepare a PageRequest with a default sort value
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "title");
        List<Book> books = response.getBooks();

        // We prepare three lists (affected by reference)
        List<Language> languages = response.getLanguages();
        List<Topic> topics = response.getTopics();
        List<Library> libraries = response.getLibraries();

        // First consumer call, we get the paged book list matching the keyWord
        Page<BookEntity> pagedBooks = bookRepository.searchPagedBooks("%" + keyWord + "%", pageRequest);
        List<BookEntity> bookEntities = pagedBooks.getContent();
        // Here we access the book list by reference (no need for setter)
        books.addAll(bookMapper.bookEntityListToBookList(bookEntities));

        // Now we fetch all books to get their metadata (same search but without the pagination)
        List<BookEntity> bookEntitiesAllFetched = bookRepository.searchAllBooks("%" + keyWord + "%");
        List<LanguageEntity> languageEntities = languageRepository.findDistinctByBooksIn(bookEntitiesAllFetched);
        List<LibraryEntity> libraryEntities = libraryRepository.findDistinctByBooksIn(bookEntitiesAllFetched);
        List<TopicEntity> topicEntities = topicRepository.findDistinctByBooksIn(bookEntitiesAllFetched);

        // After an object mapping, we add those results into the three lists. Modifying the response object.
        languages.addAll(bookMapper.languageEntityListToLanguageList(languageEntities));
        libraries.addAll(bookMapper.libraryEntityListToLibraryList(libraryEntities));
        topics.addAll(bookMapper.topicEntityListToTopicList(topicEntities));

        response.setKeyWord(keyWord);
        response.setTotalPages(pagedBooks.getTotalPages());

        return response;
    }


    /**
     *
     * <p>This method call the consumer. It ask for a search with several parameters</p>
     * <p>It's design to work in pair with the method searchBooks. filterBooks must be executed after this one.
     * using its result, to give more define results to the user.</p>
     *
     * @param keyWord the keyWord of the search
     * @param languageId a search criteria (a value of -1 means excluded from search)
     * @param libraryId a search criteria (a value of -1 means excluded from search)
     * @param topicId a search criteria (a value of -1 means excluded from search)
     * @param available a search criteria (is the book in stock)
     * @param page the current page
     * @param size the size of the page
     * @return FilterBooksResponse it contains the paged list of book that have been filtered, plus the total pages.
     */
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


    /**
     *
     * This method calls the Consumer layer of a book to decrease its quantity
     *
     * @param bookId the Id of the book
     */
    public void decreaseQuantity(int bookId) {
        bookRepository.decreaseQuantity(bookId);
    }

    /**
     *
     * This method calls the Consumer layer of a book to increase its quantity
     *
     * @param bookId the Id of the book
     */
    public void increaseQuantity(int bookId) {
        bookRepository.increaseQuantity(bookId);
    }

    /**
     *
     * Call the consumer to get the associated quantity of a book
     *
     * @param bookId the book you want to know the quantity
     * @return an Integer, the quantity
     */
    public Integer getQuantity(int bookId) {
        return bookRepository.getBookQuantityById(bookId);
    }
}
