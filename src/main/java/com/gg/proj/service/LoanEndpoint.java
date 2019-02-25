package com.gg.proj.service;

import com.gg.proj.business.LoanManager;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.ServiceFaultException;
import com.gg.proj.service.loans.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.naming.ldap.ExtendedRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Endpoint
public class LoanEndpoint {

    private static final Logger log = LoggerFactory.getLogger(LoanEndpoint.class);

    private static final String NAMESPACE_URI = "http://proj.gg.com/service/loans";

    private LoanManager loanManager;

    @Autowired
    public LoanEndpoint(LoanManager loanManager) {
        this.loanManager = loanManager;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLoanRequest")
    @ResponsePayload
    public SaveLoanResponse saveLoan(@RequestPayload SaveLoanRequest request) throws ServiceFaultException {
        log.debug("Entering saveLoan... ");
        SaveLoanResponse saveLoanResponse = new SaveLoanResponse();
        try {
            Optional<Loan> optional = loanManager.save(request.getLoan(), request.getTokenUUID());
            optional.ifPresent(saveLoanResponse::setLoan);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return saveLoanResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart="extendLoanRequest")
    @ResponsePayload
    public ExtendLoanResponse extendLoan(@RequestPayload ExtendLoanRequest request) {
        log.debug("Entering extendLoan... ");
        ExtendLoanResponse extendLoanResponse = new ExtendLoanResponse();
        try {
            Optional<Loan> optional = loanManager.extend(request.getLoan(), request.getTokenUUID());
            optional.ifPresent(extendLoanResponse::setLoan);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return extendLoanResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart="closeLoanRequest")
    @ResponsePayload
    public CloseLoanResponse closeLoan(@RequestPayload CloseLoanRequest request) {
        log.debug("Entering closeLoan... ");
        CloseLoanResponse closeLoanResponse = new CloseLoanResponse();
        try {
            Optional<Loan> optional = loanManager.close(request.getLoan(), request.getTokenUUID());
            optional.ifPresent(closeLoanResponse::setLoan);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return closeLoanResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLoanRequest")
    @ResponsePayload
    public DeleteLoanResponse deleteLoan(@RequestPayload DeleteLoanRequest request) throws ServiceFaultException {
        log.debug("Entering deleteLoan... ");
        try {
            loanManager.delete(request.getLoan(), request.getTokenUUID());
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return new DeleteLoanResponse();
    }


    /**
     * This method takes a request, then build a response : it calls the business to get a loan by id.
     *
     * @param request a GetLoanRequest from the network
     * @return a GetLoanResponse.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLoanRequest")
    @ResponsePayload
    public GetLoanResponse getLoan(@RequestPayload GetLoanRequest request) throws ServiceFaultException {
        log.debug("getLoan : calling the LoanManager to fetch a loan by id");
        GetLoanResponse response = new GetLoanResponse();
        try {
            Optional<Loan> opt = loanManager.findById(request.getId(), request.getTokenUUID());
            opt.ifPresent(response::setLoan);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllLoansRequest")
    @ResponsePayload
    public ListAllLoansResponse listAllLoans(@RequestPayload ListAllLoansRequest request) throws ServiceFaultException {
        ListAllLoansResponse response = new ListAllLoansResponse();
        List<Loan> loans = response.getLoans();
        try {
            loans.addAll(loanManager.findAll(request.getTokenUUID()));
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }
}
