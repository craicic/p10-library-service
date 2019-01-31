package com.gg.proj.service;

import com.gg.proj.business.BookManager;
import com.gg.proj.service.library.GetBookRequest;
import com.gg.proj.service.library.GetBookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class BookEndpoint {

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
        GetBookResponse response = new GetBookResponse();
        response.setBook(bookManager.getBookById(request.getId()));
        return response;
    }
}
