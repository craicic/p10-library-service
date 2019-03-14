package com.gg.proj.service;

import com.gg.proj.business.BookManager;
import com.gg.proj.business.LanguageManager;
import com.gg.proj.business.LibraryManager;
import com.gg.proj.business.TopicManager;
import com.gg.proj.service.books.*;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.ServiceFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;

@Endpoint
public class BookEndpoint {

    private static final Logger log = LoggerFactory.getLogger(BookEndpoint.class);

    private static final String NAMESPACE_URI = "http://proj.gg.com/service/books";

    private BookManager bookManager;

    private LanguageManager languageManager;

    private LibraryManager libraryManager;

    private TopicManager topicManager;

    @Autowired
    public BookEndpoint(BookManager bookManager, LanguageManager languageManager, LibraryManager libraryManager, TopicManager topicManager) {
        this.bookManager = bookManager;
        this.languageManager = languageManager;
        this.libraryManager = libraryManager;
        this.topicManager = topicManager;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createBookRequest")
    @ResponsePayload
    public CreateBookResponse createBook(@RequestPayload CreateBookRequest request) {
        log.debug("createBook : calling the BookManager to create book");
        CreateBookResponse createBookResponse = new CreateBookResponse();
        try {
            Optional<Book> optional = bookManager.create(request.getBookMin(), request.getTokenUUID());
            optional.ifPresent(createBookResponse::setBook);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return createBookResponse;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveBookRequest")
    @ResponsePayload
    public SaveBookResponse saveBook(@RequestPayload SaveBookRequest request) {
        log.debug("saveBook : calling the BookManager to save book");
        SaveBookResponse saveBookResponse = new SaveBookResponse();
        try {
            saveBookResponse.setBook(bookManager.save(request.getBook(), request.getTokenUUID()));
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return saveBookResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBookRequest")
    @ResponsePayload
    public DeleteBookResponse deleteBook(@RequestPayload DeleteBookRequest request) {
        log.debug("Entering deleteBook with UUID : [" + request.getTokenUUID() + "]");
        try {
            bookManager.delete(request.getBook(), request.getTokenUUID());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return new DeleteBookResponse();
    }


    /**
     * This method takes a request, then build a response : it calls the business to get a book by id.
     *
     * @param request a GetBookRequest from the network
     * @return a GetBookResponse.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBook(@RequestPayload GetBookRequest request) throws ServiceFaultException {
        log.debug("getBook : calling the BookManager to fetch a book by id");
        GetBookResponse response = new GetBookResponse();

        Optional<BookFull> opt = bookManager.findById(request.getId());
        opt.ifPresent(response::setBookFull);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllBooksRequest")
    @ResponsePayload
    public ListAllBooksResponse listAllBooks(@RequestPayload ListAllBooksRequest request) {
        ListAllBooksResponse response = new ListAllBooksResponse();
        List<Book> books = response.getBooks();
        books.addAll(bookManager.findAll());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchBooksRequest")
    @ResponsePayload
    public SearchBooksResponse searchBooks(@RequestPayload SearchBooksRequest request) {
        log.debug("SearchBooks : calling the bookManager to filterBooks book");
        SearchBooksResponse response = bookManager.searchBooks(request.getKeyWord(), request.getPage(), request.getSize());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "filterBooksRequest")
    @ResponsePayload
    public FilterBooksResponse filterBooks(@RequestPayload FilterBooksRequest request) {
        log.debug("filterBooks : calling the bookManager to filterBooks book");

        log.debug("keyWord : [" + request.getKeyWord() + "] ");
        log.debug("languageId : [" + request.getLanguageId() + "] ");
        log.debug("libraryId : [" + request.getLibraryId() + "] ");
        log.debug("topicId : [" + request.getTopicId() + "] ");
        log.debug("available : [" + request.isAvailable() + "] ");
        log.debug("pageNumber : [" + request.getPage() + "] ");

        FilterBooksResponse response = bookManager.filterBooks(request.getKeyWord(), request.getLanguageId(), request.getLibraryId()
                , request.getTopicId(), request.isAvailable(), request.getPage(), request.getSize());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createLanguageRequest")
    @ResponsePayload
    public CreateLanguageResponse createLanguage(@RequestPayload CreateLanguageRequest request) {
        CreateLanguageResponse response = new CreateLanguageResponse();
        try {
            Optional<Language> optional = languageManager.create(request.getLanguageName(), request.getTokenUUID());
            optional.ifPresent(response::setLanguage);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createLibraryRequest")
    @ResponsePayload
    public CreateLibraryResponse createLibrary(@RequestPayload CreateLibraryRequest request) {
        CreateLibraryResponse response = new CreateLibraryResponse();
        try {
            Optional<Library> optional = libraryManager.create(request.getLibraryMin(), request.getTokenUUID());
            optional.ifPresent(response::setLibrary);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createTopicRequest")
    @ResponsePayload
    public CreateTopicResponse createTopic(@RequestPayload CreateTopicRequest request) {
        CreateTopicResponse response = new CreateTopicResponse();
        try {
            Optional<Topic> optional = topicManager.create(request.getTopicName(), request.getTokenUUID());
            optional.ifPresent(response::setTopic);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLanguageRequest")
    @ResponsePayload
    public SaveLanguageResponse saveLanguage(@RequestPayload SaveLanguageRequest request) {
        SaveLanguageResponse response = new SaveLanguageResponse();
        try {
            Optional<Language> optional = languageManager.save(request.getLanguage(), request.getTokenUUID());
            optional.ifPresent(response::setLanguage);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLibraryRequest")
    @ResponsePayload
    public SaveLibraryResponse saveLibrary(@RequestPayload SaveLibraryRequest request) {
        SaveLibraryResponse response = new SaveLibraryResponse();
        try {
            Optional<Library> optional = libraryManager.save(request.getLibrary(), request.getTokenUUID());
            optional.ifPresent(response::setLibrary);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveTopicRequest")
    @ResponsePayload
    public SaveTopicResponse saveTopic(@RequestPayload SaveTopicRequest request) {
        SaveTopicResponse response = new SaveTopicResponse();
        try {
            Optional<Topic> optional = topicManager.save(request.getTopic(), request.getTokenUUID());
            optional.ifPresent(response::setTopic);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLanguageRequest")
    @ResponsePayload
    public DeleteLanguageResponse deleteLanguage(@RequestPayload DeleteLanguageRequest request) {
        try {
            languageManager.delete(request.getLanguage(), request.getTokenUUID());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return new DeleteLanguageResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLibraryRequest")
    @ResponsePayload
    public DeleteLibraryResponse deleteLibrary(@RequestPayload DeleteLibraryRequest request) {
        try {
            libraryManager.delete(request.getLibrary(), request.getTokenUUID());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return new DeleteLibraryResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteTopicRequest")
    @ResponsePayload
    public DeleteTopicResponse deleteTopic(@RequestPayload DeleteTopicRequest request) {
        try {
            topicManager.delete(request.getTopic(), request.getTokenUUID());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return new DeleteTopicResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLanguageRequest")
    @ResponsePayload
    public GetLanguageResponse getLanguage(@RequestPayload GetLanguageRequest request) throws ServiceFaultException {
        log.debug("getLanguage : calling the LanguageManager to fetch a book by id");
        GetLanguageResponse response = new GetLanguageResponse();

        Optional<Language> opt = languageManager.findById(request.getId());
        opt.ifPresent(response::setLanguage);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLibraryRequest")
    @ResponsePayload
    public GetLibraryResponse getLibrary(@RequestPayload GetLibraryRequest request) throws ServiceFaultException {
        log.debug("getLibrary : calling the LibraryManager to fetch a book by id");
        GetLibraryResponse response = new GetLibraryResponse();

        Optional<Library> opt = libraryManager.findById(request.getId());
        opt.ifPresent(response::setLibrary);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTopicRequest")
    @ResponsePayload
    public GetTopicResponse getTopic(@RequestPayload GetTopicRequest request) throws ServiceFaultException {
        log.debug("getTopic : calling the TopicManager to fetch a book by id");
        GetTopicResponse response = new GetTopicResponse();

        Optional<Topic> opt = topicManager.findById(request.getId());
        opt.ifPresent(response::setTopic);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllLanguagesRequest")
    @ResponsePayload
    public ListAllLanguagesResponse listAllLanguages(@RequestPayload ListAllLanguagesRequest request) {
        ListAllLanguagesResponse response = new ListAllLanguagesResponse();
        List<Language> languages = response.getLanguages();
        languages.addAll(languageManager.findAll());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllLibrariesRequest")
    @ResponsePayload
    public ListAllLibrariesResponse listAllLibraries(@RequestPayload ListAllLibrariesRequest request) {
        ListAllLibrariesResponse response = new ListAllLibrariesResponse();
        List<Library> libraries = response.getLibraries();
        libraries.addAll(libraryManager.findAll());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllTopicsRequest")
    @ResponsePayload
    public ListAllTopicsResponse listAllTopics(@RequestPayload ListAllTopicsRequest request) {
        ListAllTopicsResponse response = new ListAllTopicsResponse();
        List<Topic> topics = response.getTopics();
        topics.addAll(topicManager.findAll());
        return response;
    }
}
