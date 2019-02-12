package com.gg.proj.service;

import com.gg.proj.business.BookManager;
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


    @Autowired
    public BookEndpoint(BookManager bookManager) {
        this.bookManager = bookManager;
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

}
