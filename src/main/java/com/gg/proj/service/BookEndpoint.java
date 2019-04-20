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

/**
 *
 * This class is registered with Spring WS as a candidate for processing incoming SOAP messages (via Endpoint annotation)
 *
 * Other annotations you'll find in this class :
 *
 * PayloadRoot is then used by Spring WS to pick the handler method based on the message’s namespace and localPart.
 * RequestPayload indicates that the incoming message will be mapped to the method’s request parameter.
 * ResponsePayload annotation makes Spring WS map the returned value to the response payload.
 *
 */
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

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of CreateBookRequest. It's mapped from the incoming SOAP message.
     * @return CreateBookResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createBookRequest")
    @ResponsePayload
    public CreateBookResponse createBook(@RequestPayload CreateBookRequest request) throws ServiceFaultException {
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

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of SaveBookRequest. It's mapped from the incoming SOAP message.
     * @return SaveBookResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveBookRequest")
    @ResponsePayload
    public SaveBookResponse saveBook(@RequestPayload SaveBookRequest request) throws ServiceFaultException {
        log.debug("saveBook : calling the BookManager to save book");
        SaveBookResponse saveBookResponse = new SaveBookResponse();
        try {
            saveBookResponse.setBook(bookManager.save(request.getBook(), request.getTokenUUID()));
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return saveBookResponse;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of DeleteBookRequest. It's mapped from the incoming SOAP message.
     * @return DeleteBookResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBookRequest")
    @ResponsePayload
    public DeleteBookResponse deleteBook(@RequestPayload DeleteBookRequest request) throws ServiceFaultException {
        log.debug("Entering deleteBook with UUID : [" + request.getTokenUUID() + "]");
        try {
            bookManager.delete(request.getBook(), request.getTokenUUID());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return new DeleteBookResponse();
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of GetBookRequest. It's mapped from the incoming SOAP message.
     * @return GetBookResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBook(@RequestPayload GetBookRequest request) {
        log.debug("getBook : calling the BookManager to fetch a book by id");
        GetBookResponse response = new GetBookResponse();
        Optional<BookFull> opt = bookManager.findById(request.getId());
        opt.ifPresent(response::setBookFull);
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of ListAllBooksRequest. It's mapped from the incoming SOAP message.
     * @return ListAllBooksResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllBooksRequest")
    @ResponsePayload
    public ListAllBooksResponse listAllBooks(@RequestPayload ListAllBooksRequest request) {
        ListAllBooksResponse response = new ListAllBooksResponse();
        List<Book> books = response.getBooks();
        books.addAll(bookManager.findAll());
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of SearchBooksRequest. It's mapped from the incoming SOAP message.
     * @return SearchBooksResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchBooksRequest")
    @ResponsePayload
    public SearchBooksResponse searchBooks(@RequestPayload SearchBooksRequest request) {
        log.debug("SearchBooks : calling the bookManager to filterBooks book");
        return bookManager.searchBooks(request.getKeyWord(), request.getPage(), request.getSize());
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of FilterBooksRequest. It's mapped from the incoming SOAP message.
     * @return FilterBooksResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "filterBooksRequest")
    @ResponsePayload
    public FilterBooksResponse filterBooks(@RequestPayload FilterBooksRequest request) {
        log.debug("filterBooks : calling the bookManager to filterBooks book");

        return bookManager.filterBooks(request.getKeyWord(), request.getLanguageId(), request.getLibraryId()
                , request.getTopicId(), request.isAvailable(), request.getPage(), request.getSize());
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of CreateLanguageRequest. It's mapped from the incoming SOAP message.
     * @return CreateLanguageResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createLanguageRequest")
    @ResponsePayload
    public CreateLanguageResponse createLanguage(@RequestPayload CreateLanguageRequest request) throws ServiceFaultException {
        CreateLanguageResponse response = new CreateLanguageResponse();
        try {
            Optional<Language> optional = languageManager.create(request.getLanguageName(), request.getTokenUUID());
            optional.ifPresent(response::setLanguage);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of CreateLibraryRequest. It's mapped from the incoming SOAP message.
     * @return CreateLibraryResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createLibraryRequest")
    @ResponsePayload
    public CreateLibraryResponse createLibrary(@RequestPayload CreateLibraryRequest request) throws ServiceFaultException {
        CreateLibraryResponse response = new CreateLibraryResponse();
        try {
            Optional<Library> optional = libraryManager.create(request.getLibraryMin(), request.getTokenUUID());
            optional.ifPresent(response::setLibrary);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of CreateLibraryRequest. It's mapped from the incoming SOAP message.
     * @return CreateLibraryResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createTopicRequest")
    @ResponsePayload
    public CreateTopicResponse createTopic(@RequestPayload CreateTopicRequest request) throws ServiceFaultException {
        CreateTopicResponse response = new CreateTopicResponse();
        try {
            Optional<Topic> optional = topicManager.create(request.getTopicName(), request.getTokenUUID());
            optional.ifPresent(response::setTopic);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of SaveLanguageRequest. It's mapped from the incoming SOAP message.
     * @return SaveLanguageResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLanguageRequest")
    @ResponsePayload
    public SaveLanguageResponse saveLanguage(@RequestPayload SaveLanguageRequest request) throws ServiceFaultException {
        SaveLanguageResponse response = new SaveLanguageResponse();
        try {
            Optional<Language> optional = languageManager.save(request.getLanguage(), request.getTokenUUID());
            optional.ifPresent(response::setLanguage);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of SaveLibraryRequest. It's mapped from the incoming SOAP message.
     * @return SaveLibraryResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLibraryRequest")
    @ResponsePayload
    public SaveLibraryResponse saveLibrary(@RequestPayload SaveLibraryRequest request) throws ServiceFaultException {
        SaveLibraryResponse response = new SaveLibraryResponse();
        try {
            Optional<Library> optional = libraryManager.save(request.getLibrary(), request.getTokenUUID());
            optional.ifPresent(response::setLibrary);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of SaveTopicRequest. It's mapped from the incoming SOAP message.
     * @return SaveTopicResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveTopicRequest")
    @ResponsePayload
    public SaveTopicResponse saveTopic(@RequestPayload SaveTopicRequest request) throws ServiceFaultException {
        SaveTopicResponse response = new SaveTopicResponse();
        try {
            Optional<Topic> optional = topicManager.save(request.getTopic(), request.getTokenUUID());
            optional.ifPresent(response::setTopic);
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of DeleteLanguageRequest. It's mapped from the incoming SOAP message.
     * @return DeleteLanguageResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLanguageRequest")
    @ResponsePayload
    public DeleteLanguageResponse deleteLanguage(@RequestPayload DeleteLanguageRequest request) throws ServiceFaultException {
        try {
            languageManager.delete(request.getLanguage(), request.getTokenUUID());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return new DeleteLanguageResponse();
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of DeleteLibraryRequest. It's mapped from the incoming SOAP message.
     * @return DeleteLibraryResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLibraryRequest")
    @ResponsePayload
    public DeleteLibraryResponse deleteLibrary(@RequestPayload DeleteLibraryRequest request) throws ServiceFaultException {
        try {
            libraryManager.delete(request.getLibrary(), request.getTokenUUID());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return new DeleteLibraryResponse();
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of DeleteTopicRequest. It's mapped from the incoming SOAP message.
     * @return DeleteTopicResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteTopicRequest")
    @ResponsePayload
    public DeleteTopicResponse deleteTopic(@RequestPayload DeleteTopicRequest request) throws ServiceFaultException {
        try {
            topicManager.delete(request.getTopic(), request.getTokenUUID());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return new DeleteTopicResponse();
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of GetLanguageRequest. It's mapped from the incoming SOAP message.
     * @return GetLanguageResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLanguageRequest")
    @ResponsePayload
    public GetLanguageResponse getLanguage(@RequestPayload GetLanguageRequest request) {
        log.debug("getLanguage : calling the LanguageManager to fetch a book by id");
        GetLanguageResponse response = new GetLanguageResponse();

        Optional<Language> opt = languageManager.findById(request.getId());
        opt.ifPresent(response::setLanguage);
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of GetLibraryRequest. It's mapped from the incoming SOAP message.
     * @return GetLibraryResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLibraryRequest")
    @ResponsePayload
    public GetLibraryResponse getLibrary(@RequestPayload GetLibraryRequest request) {
        log.debug("getLibrary : calling the LibraryManager to fetch a book by id");
        GetLibraryResponse response = new GetLibraryResponse();

        Optional<Library> opt = libraryManager.findById(request.getId());
        opt.ifPresent(response::setLibrary);
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of GetTopicRequest. It's mapped from the incoming SOAP message.
     * @return GetTopicResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTopicRequest")
    @ResponsePayload
    public GetTopicResponse getTopic(@RequestPayload GetTopicRequest request) {
        log.debug("getTopic : calling the TopicManager to fetch a book by id");
        GetTopicResponse response = new GetTopicResponse();

        Optional<Topic> opt = topicManager.findById(request.getId());
        opt.ifPresent(response::setTopic);
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of ListAllLanguagesRequest. It's mapped from the incoming SOAP message.
     * @return ListAllLanguagesResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllLanguagesRequest")
    @ResponsePayload
    public ListAllLanguagesResponse listAllLanguages(@RequestPayload ListAllLanguagesRequest request) {
        ListAllLanguagesResponse response = new ListAllLanguagesResponse();
        List<Language> languages = response.getLanguages();
        languages.addAll(languageManager.findAll());
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of ListAllLibrariesRequest. It's mapped from the incoming SOAP message.
     * @return ListAllLibrariesResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllLibrariesRequest")
    @ResponsePayload
    public ListAllLibrariesResponse listAllLibraries(@RequestPayload ListAllLibrariesRequest request) {
        ListAllLibrariesResponse response = new ListAllLibrariesResponse();
        List<Library> libraries = response.getLibraries();
        libraries.addAll(libraryManager.findAll());
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * @param request is an instance of ListAllTopicsRequest. It's mapped from the incoming SOAP message.
     * @return ListAllTopicsResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllTopicsRequest")
    @ResponsePayload
    public ListAllTopicsResponse listAllTopics(@RequestPayload ListAllTopicsRequest request) {
        ListAllTopicsResponse response = new ListAllTopicsResponse();
        List<Topic> topics = response.getTopics();
        topics.addAll(topicManager.findAll());
        return response;
    }
}
