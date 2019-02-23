package com.gg.proj.service;

import com.gg.proj.business.LoanManager;
import com.gg.proj.business.LoanManager;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.ServiceFaultException;
import com.gg.proj.service.loans.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Endpoint
public class LoanEndpoint {

    private static final Logger log = LoggerFactory.getLogger(LoanEndpoint.class);

    private static final String NAMESPACE_URI = "http://proj.gg.com/service/loans";

    private LoanManager loanManager;


    public LoanEndpoint(LoanManager loanManager) {
        this.loanManager = loanManager;
    }

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLoanRequest")
//    @ResponsePayload
//    public SaveLoanResponse saveLoan(@RequestPayload SaveLoanRequest request) throws ServiceFaultException {
//        log.debug("saveLoan : calling the LoanManager to save loan");
//        SaveLoanResponse saveLoanResponse = new SaveLoanResponse();
//        try {
//            saveLoanResponse.setLoan(loanManager.save(request.getLoan(), request.getTokenUUID()));
//        } catch (Exception ex) {
//            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
//        }
//        return saveLoanResponse;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteLoanRequest")
//    @ResponsePayload
//    public DeleteLoanResponse deleteLoan(@RequestPayload DeleteLoanRequest request) throws ServiceFaultException {
//        try {
//            loanManager.delete(request.getLoan(), request.getTokenUUID());
//        } catch (Exception ex) {
//            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
//        }
//        return new DeleteLoanResponse();
//    }
//
//
//    /**
//     * This method takes a request, then build a response : it calls the business to get a loan by id.
//     *
//     * @param request a GetLoanRequest from the network
//     * @return a GetLoanResponse.
//     */
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLoanRequest")
//    @ResponsePayload
//    public GetLoanResponse getLoan(@RequestPayload GetLoanRequest request) throws ServiceFaultException {
//        log.debug("getLoan : calling the LoanManager to fetch a loan by id");
//        GetLoanResponse response = new GetLoanResponse();
//        try {
//            Optional<Loan> opt = loanManager.findById(request.getId(), UUID.fromString(request.getTokenUUID()));
//            opt.ifPresent(response::setLoan);
//        } catch (Exception ex) {
//            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
//        }
//        return response;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllLoansRequest")
//    @ResponsePayload
//    public ListAllLoansResponse listAllLoans(@RequestPayload ListAllLoansRequest request) throws ServiceFaultException {
//        ListAllLoansResponse response = new ListAllLoansResponse();
//        List<Loan> loans = response.getLoans();
//        try {
//            loans.addAll(loanManager.findAll(UUID.fromString(request.getTokenUUID())));
//        } catch (Exception ex) {
//            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
//        }
//        return response;
//    }
}
