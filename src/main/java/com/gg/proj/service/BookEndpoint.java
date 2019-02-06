package com.gg.proj.service;

import com.gg.proj.business.BookManager;
import com.gg.proj.business.UserManager;
import com.gg.proj.service.library.*;
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

    private UserManager userManager;

    @Autowired
    public BookEndpoint(BookManager bookManager, UserManager userManager) {
        this.bookManager = bookManager;
        this.userManager = userManager;
    }


    /**
     * This method takes a request, then build a response : it calls the business to get a book by id.
     *
     * @param request a GetBookRequest from the network
     * @return a GetBookResponse.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBook(@RequestPayload GetBookRequest request) {
        log.debug("getBook : calling the BookManager to fetch a book by id");
        GetBookResponse response = new GetBookResponse();
        response.setBook(bookManager.getBookById(request.getId()));
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginUserRequest")
    @ResponsePayload
    public LoginUserResponse loginUser(@RequestPayload LoginUserRequest request) {
        log.debug("loginUser : calling the userManager to log a user in");
        LoginUserResponse response = new LoginUserResponse();
        response.setUser(userManager.loginUser(request.getPseudo(), request.getPasswordHash()));
        return response;
    }
}
