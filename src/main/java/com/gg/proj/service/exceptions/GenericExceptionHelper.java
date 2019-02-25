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
            serviceStatus.setStatusCode("ERROR");
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
            serviceStatus.setMessage("Invalid operation");
            serviceStatus.setStatusCode("ERROR");
            throw new ServiceFaultException(errorMessage, serviceStatus);
        }
    }


}
