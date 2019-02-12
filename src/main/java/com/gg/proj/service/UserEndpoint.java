package com.gg.proj.service;

import com.gg.proj.business.BookManager;
import com.gg.proj.business.UserManager;
import com.gg.proj.service.exceptions.ServiceFaultException;
import com.gg.proj.service.exceptions.UserNotFoundException;
import com.gg.proj.service.books.ServiceStatus;
import com.gg.proj.service.users.User;
import com.gg.proj.service.users.CreateUserRequest;
import com.gg.proj.service.users.CreateUserResponse;
import com.gg.proj.service.users.LoginUserRequest;
import com.gg.proj.service.users.LoginUserResponse;
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
public class UserEndpoint {

    private static final Logger log = LoggerFactory.getLogger(BookEndpoint.class);

    private static final String NAMESPACE_URI = "http://proj.gg.com/service/users";

    private UserManager userManager;

    @Autowired
    public UserEndpoint(UserManager userManager) {
        this.userManager = userManager;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginUserRequest")
    @ResponsePayload
    public LoginUserResponse loginUser(@RequestPayload LoginUserRequest request)  throws ServiceFaultException {
        log.debug("loginUser : calling the userManager to log a user in");
        LoginUserResponse response = new LoginUserResponse();
        try {

            response.setUser(userManager.loginUser(request.getPseudo(), request.getPasswordHash()));
        } catch (UserNotFoundException e) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Invalid credentials");
            serviceStatus.setStatusCode("NOT_FOUND");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request){
        log.debug("createUser : calling the userManager to create a new user");
        CreateUserResponse response = new CreateUserResponse();
        response.setUser(userManager.createUser(request));
        return response;
    }
}
