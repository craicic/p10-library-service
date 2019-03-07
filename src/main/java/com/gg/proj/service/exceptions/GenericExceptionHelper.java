package com.gg.proj.service.exceptions;

import com.gg.proj.service.books.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericExceptionHelper {

    private static final Logger log = LoggerFactory.getLogger(GenericExceptionHelper.class);


    public static void tokenExceptionHandler(Exception e) throws OutdatedTokenException, InvalidTokenException {
        if (e instanceof InvalidTokenException) {
            log.warn("invalid connection");
            throw (InvalidTokenException) e;
        } else if (e instanceof OutdatedTokenException) {
            log.info("The token has expired");
            throw (OutdatedTokenException) e;
        }
    }

    public static void serviceFaultExceptionHandler(Exception e) throws ServiceFaultException {
        if (e instanceof InvalidTokenException) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Invalid connection");
            serviceStatus.setStatusCode("CONNECTION_ERROR");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } else if (e instanceof OutdatedTokenException) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Outdated token");
            serviceStatus.setStatusCode("RETRY_LOGIN");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } else if (e instanceof InvalidLoanOperationException) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Invalid operation on a loan");
            serviceStatus.setStatusCode("LOAN_ERROR");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } else if (e instanceof MailAddressAlreadyExistsException) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Mail Address is already in use");
            serviceStatus.setStatusCode("ADDRESS_NOT_AVAILABLE");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } else if (e instanceof PseudoAlreadyExistsException) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Pseudo already used");
            serviceStatus.setStatusCode("PSEUDO_NOT_AVAILABLE");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } else if (e instanceof IllegalArgumentException) {
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Password too short");
            serviceStatus.setStatusCode("NOT_STRONG_PASSWORD");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        } else if (e instanceof RuntimeException) {
            System.out.println("ex : " + e);
            String errorMessage = e.getMessage();
            ServiceStatus serviceStatus = new ServiceStatus();
            serviceStatus.setMessage("Runtime error");
            serviceStatus.setStatusCode("RUNTIME_ERROR");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        }
        String errorMessage = e.getMessage();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setMessage("Generic error");
        serviceStatus.setStatusCode("ERROR");
        throw new ServiceFaultException(errorMessage, serviceStatus);
    }
}
