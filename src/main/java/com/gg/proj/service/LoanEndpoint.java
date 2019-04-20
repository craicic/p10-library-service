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

import java.util.List;
import java.util.Optional;

@Endpoint
public class LoanEndpoint {

    private static final Logger log = LoggerFactory.getLogger(LoanEndpoint.class);

    private static final String NAMESPACE_URI = "http://proj.gg.com/service/loans";

    private LoanManager loanManager;

    @Autowired
    public LoanEndpoint(LoanManager loanManager) {
        this.loanManager = loanManager;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of CreateLoanRequest. It's mapped from the incoming SOAP message.
     * @return CreateLoanResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createLoanRequest")
    @ResponsePayload
    public CreateLoanResponse createLoan(@RequestPayload CreateLoanRequest request) throws ServiceFaultException {
        log.debug("Entering createLoan... ");
        CreateLoanResponse createLoanResponse = new CreateLoanResponse();
        try {
            Optional<Loan> optional = loanManager.create(request.getLoanMin(), request.getTokenUUID());
            optional.ifPresent(createLoanResponse::setLoan);
            optional.orElseThrow(RuntimeException::new);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return createLoanResponse;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of SaveLoanRequest. It's mapped from the incoming SOAP message.
     * @return SaveLoanResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveLoanRequest")
    @ResponsePayload
    public SaveLoanResponse saveLoan(@RequestPayload SaveLoanRequest request) throws ServiceFaultException {
        log.debug("Entering saveLoan... ");
        SaveLoanResponse saveLoanResponse = new SaveLoanResponse();
        try {
            Optional<Loan> optional = loanManager.save(request.getLoan(), request.getTokenUUID());
            optional.ifPresent(saveLoanResponse::setLoan);
            optional.orElseThrow(RuntimeException::new);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return saveLoanResponse;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * <p><b>Method's logic :</b> this method is to be called when a user need to extend the length of his loan, it calls the
     * Business layer, passing it the Loan contained in the request. Once business method did its job, this method
     * returns the Loan.</p>
     *
     * @param request is an instance of ExtendLoanRequest. It's mapped from the incoming SOAP message.
     * @return ExtendLoanResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "extendLoanRequest")
    @ResponsePayload
    public ExtendLoanResponse extendLoan(@RequestPayload ExtendLoanRequest request) throws ServiceFaultException {
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

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of CloseLoanRequest. It's mapped from the incoming SOAP message.
     * @return CloseLoanResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "closeLoanRequest")
    @ResponsePayload
    public CloseLoanResponse closeLoan(@RequestPayload CloseLoanRequest request) {
        log.debug("Entering closeLoan... ");
        CloseLoanResponse closeLoanResponse = new CloseLoanResponse();
        try {
            Optional<Loan> optional = loanManager.close(request.getId(), request.getTokenUUID());
            optional.ifPresent(closeLoanResponse::setLoan);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return closeLoanResponse;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of DeleteLoanRequest. It's mapped from the incoming SOAP message.
     * @return DeleteLoanResponse the output message contains this response.
     */
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
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of GetLoanRequest. It's mapped from the incoming SOAP message.
     * @return GetLoanResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLoanRequest")
    @ResponsePayload
    public GetLoanResponse getLoan(@RequestPayload GetLoanRequest request) throws ServiceFaultException {
        log.debug("Entering getLoan... ");
        GetLoanResponse response = new GetLoanResponse();
        try {
            Optional<Loan> opt = loanManager.findById(request.getId(), request.getTokenUUID());
            opt.ifPresent(response::setLoan);
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     *
     * @param request is an instance of ListAllLoansRequest. It's mapped from the incoming SOAP message.
     * @return ListAllLoansResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listAllLoansRequest")
    @ResponsePayload
    public ListAllLoansResponse listAllLoans(@RequestPayload ListAllLoansRequest request) throws ServiceFaultException {
        log.debug("Entering listAllLoans... ");
        ListAllLoansResponse response = new ListAllLoansResponse();
        List<Loan> loans = response.getLoans();
        try {
            loans.addAll(loanManager.findAll(request.getTokenUUID()));
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }

    /**
     *
     * <p>This methods is exposed. It uses the RequestPayload to do a custom call to the Business layer.</p>
     *
     * <p>There is a verification on token UUID (the user must must possess a valid to perform this
     * method).</p>
     *
     * <p>Exceptions thrown by the Business layer (InvalidTokenException, OutdatedTokenException) are processed by the
     * serviceFaultExceptionHandler : depending the instance of the exception it builds a custom SOAP error.</p>
     * <p><b>Method's logic :</b> this method is to be called when a user request all active loan. You must provide a
     * valid UUID and a userID.It add to the response the list of loans. Then it return this response.</p>
     * @param request is an instance of FindAllLoansByUserIdRequest. It's mapped from the incoming SOAP message.
     * @return FindAllLoansByUserIdResponse the output message contains this response.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllLoansByUserIdRequest")
    @ResponsePayload
    public FindAllLoansByUserIdResponse findAllLoansByUserId(@RequestPayload FindAllLoansByUserIdRequest request) throws ServiceFaultException {
        log.debug("Entering findAllLoansByUserId... ");
        FindAllLoansByUserIdResponse response = new FindAllLoansByUserIdResponse();
        List<LoanDetailed> loans = response.getLoansDetailed();
        try {
            loans.addAll(loanManager.findMyCurrentLoans(request.getTokenUUID(), request.getUserId()));
        } catch (Exception ex) {
            GenericExceptionHelper.serviceFaultExceptionHandler(ex);
        }
        return response;
    }
}
