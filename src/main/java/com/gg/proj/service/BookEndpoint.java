package com.gg.proj.service;

import com.gg.proj.business.BookManager;
import com.gg.proj.service.library.GetBookRequest;
import com.gg.proj.service.library.GetBookResponse;
import com.gg.proj.service.library.SearchBooksRequest;
import com.gg.proj.service.library.SearchBooksResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.transaction.Transactional;

@Endpoint
@Transactional
public class BookEndpoint {

    private static final Logger log = LoggerFactory.getLogger(BookEndpoint.class);

    private static final String NAMESPACE_URI = "http://proj.gg.com/service/library";

    private BookManager bookManager;

    @Autowired
    public BookEndpoint(BookManager bookManager) {
        this.bookManager = bookManager;
    }


    /**
     * This method takes a request, then build a response : it calls the business to get a book by id.
     *
     * @param request a GetBookRequest from the network
     * @return a GetBookResponse.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBook(@RequestPayload GetBookRequest request){
        log.info("getBook : calling the BookManager to fetch a book by id");
        GetBookResponse response = new GetBookResponse();
        response.setBook(bookManager.getBookById(request.getId()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchBooksRequest")
    @ResponsePayload
    public SearchBooksResponse searchBooks(@RequestPayload SearchBooksRequest request){
        SearchBooksResponse response = bookManager.searchBooks(request);
        return response;
    }
}
