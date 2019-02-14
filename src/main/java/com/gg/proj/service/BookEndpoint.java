package com.gg.proj.service;

import com.gg.proj.business.BookManager;
import com.gg.proj.business.LanguageManager;
import com.gg.proj.business.LibraryManager;
import com.gg.proj.business.TopicManager;
import com.gg.proj.service.books.*;
import com.gg.proj.service.exceptions.ServiceFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Endpoint
@Transactional
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveBookRequest")
    @ResponsePayload
    public SaveBookResponse saveBook(@RequestPayload SaveBookRequest request) {
        log.debug("saveBook : calling the BookManager to save book");
        SaveBookResponse saveBookResponse = new SaveBookResponse();
        saveBookResponse.setBook(bookManager.save(request.getBook()));
        return saveBookResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBookRequest")
    @ResponsePayload
    public DeleteBookResponse deleteBook(@RequestPayload DeleteBookRequest request) {
        bookManager.delete(request.getBook());
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

        Optional<Book> opt = bookManager.findById(request.getId());
        opt.ifPresent(response::setBook);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllBooksRequest")
    @ResponsePayload
    public ListAllBooksResponse listAllBooks(@RequestPayload ListAllBooksRequest request){
        ListAllBooksResponse response = new ListAllBooksResponse();
        List<Book> books = response.getBooks();
        books.addAll(bookManager.findAll());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchBooksRequest")
    @ResponsePayload
    public SearchBooksResponse searchBooks(@RequestPayload SearchBooksRequest request) {
        log.debug("SearchBooks : calling the bookManager to search book");
        SearchBooksResponse response = bookManager.searchBooks(request);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "filterBooksRequest")
    @ResponsePayload
    public FilterBooksResponse filterBooks(@RequestPayload FilterBooksRequest request) {
        log.debug("filterBooks : calling the bookManager to filter book");
        FilterBooksResponse response = bookManager.filterBooks(request);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLanguageRequest")
    @ResponsePayload
    public SaveLanguageResponse saveLanguage(@RequestPayload SaveLanguageRequest request) {
        SaveLanguageResponse response = new SaveLanguageResponse();
        response.setLanguage(languageManager.save(request.getLanguage()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLibraryRequest")
    @ResponsePayload
    public SaveLibraryResponse saveLibrary(@RequestPayload SaveLibraryRequest request) {
        SaveLibraryResponse response = new SaveLibraryResponse();
        response.setLibrary(libraryManager.save(request.getLibrary()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveTopicRequest")
    @ResponsePayload
    public SaveTopicResponse saveTopic(@RequestPayload SaveTopicRequest request) {
        SaveTopicResponse response = new SaveTopicResponse();
        response.setTopic(topicManager.save(request.getTopic()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLanguageRequest")
    @ResponsePayload
    public DeleteLanguageResponse deleteLanguage(@RequestPayload DeleteLanguageRequest request) {
        languageManager.delete(request.getLanguage());
        return new DeleteLanguageResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLibraryRequest")
    @ResponsePayload
    public DeleteLibraryResponse deleteLibrary(@RequestPayload DeleteLibraryRequest request) {
        libraryManager.delete(request.getLibrary());
        return new DeleteLibraryResponse();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteTopicRequest")
    @ResponsePayload
    public DeleteTopicResponse deleteTopic(@RequestPayload DeleteTopicRequest request) {
        topicManager.delete(request.getTopic());
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
    public ListAllLanguagesResponse listAllLanguages(@RequestPayload ListAllLanguagesRequest request){
        ListAllLanguagesResponse response = new ListAllLanguagesResponse();
        List<Language> languages = response.getLanguages();
        languages.addAll(languageManager.findAll());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllLibrariesRequest")
    @ResponsePayload
    public ListAllLibrariesResponse listAllLibraries(@RequestPayload ListAllLibrariesRequest request){
        ListAllLibrariesResponse response = new ListAllLibrariesResponse();
        List<Library> libraries = response.getLibraries();
        libraries.addAll(libraryManager.findAll());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllTopicsRequest")
    @ResponsePayload
    public ListAllTopicsResponse listAllTopics(@RequestPayload ListAllTopicsRequest request){
        ListAllTopicsResponse response = new ListAllTopicsResponse();
        List<Topic> topics = response.getTopics();
        topics.addAll(topicManager.findAll());
        return response;
    }
}
