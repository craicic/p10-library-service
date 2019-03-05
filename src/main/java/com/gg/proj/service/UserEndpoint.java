package com.gg.proj.service;

import com.gg.proj.business.UserManager;
import com.gg.proj.service.books.ServiceStatus;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.ServiceFaultException;
import com.gg.proj.service.exceptions.UserNotFoundException;
import com.gg.proj.service.users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
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
    public LoginUserResponse loginUser(@RequestPayload LoginUserRequest request) throws ServiceFaultException {
        log.debug("loginUser : calling the userManager to login user : " + request.getPseudo());
        LoginUserResponse response = new LoginUserResponse();
        try {
            response.setToken(userManager.loginUser(request.getPseudo(), request.getPassword()));
        } catch (UserNotFoundException e) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("No such user");
            serviceStatus.setStatusCode("NOT_FOUND");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Wrong credentials");
            serviceStatus.setStatusCode("WRONG_PASSWORD");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "logoutUserRequest")
    @ResponsePayload
    public LogoutUserResponse logoutUser(@RequestPayload LogoutUserRequest request) throws ServiceFaultException {
        log.debug("logoutUser : calling the userManager to log a user out with token :" + request.getTokenUUID());
        LogoutUserResponse response = new LogoutUserResponse();
        response.setLogoutStatus("FAILURE");
        try {
            response.setLogoutStatus(userManager.logoutUser(request.getTokenUUID()));
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("UUID input is not a valid UUID");
            serviceStatus.setStatusCode("UUID_ERROR");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } catch (UserNotFoundException e) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("User not Found");
            serviceStatus.setStatusCode("NOT_FOUND");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerUserRequest")
    @ResponsePayload
    public RegisterUserResponse registerUser(@RequestPayload RegisterUserRequest request) throws ServiceFaultException {
        log.debug("Entering registerUser with user " + request.getUser());
        RegisterUserResponse response = new RegisterUserResponse();
        try {
            response.setToken(userManager.registerUser(request.getUser()));
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "changePasswordRequest")
    @ResponsePayload
    public ChangePasswordResponse changePassword(@RequestPayload ChangePasswordRequest request) throws ServiceFaultException {
        log.debug("Entering changePassword ... ");
        ChangePasswordResponse response = new ChangePasswordResponse();
        try {
            userManager.changePassword(request.getTokenUUID(), request.getUserId(), request.getOldPassword(), request.getNewPassword());
        } catch (Exception e) {
            GenericExceptionHelper.serviceFaultExceptionHandler(e);
        }
        return response;
    }
}
