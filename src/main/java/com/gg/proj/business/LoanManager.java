package com.gg.proj.business;

import com.gg.proj.business.mapper.LoanMapper;
import com.gg.proj.consumer.LoanRepository;
import com.gg.proj.model.LoanEntity;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.InvalidLoanOperationException;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import com.gg.proj.service.loans.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
@Transactional
public class LoanManager {

    private static final Logger log = LoggerFactory.getLogger(LoanManager.class);

    private LoanRepository loanRepository;

    private TokenManager tokenManager;

    private LoanMapper loanMapper;

    @Autowired
    public LoanManager(LoanRepository loanRepository, TokenManager tokenManager, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.tokenManager = tokenManager;
        this.loanMapper = loanMapper;
    }


    public Optional<Loan> save(Loan loan, String tokenUUID) throws OutdatedTokenException, InvalidTokenException {
        Optional<Loan> optionalFromEndpoint = Optional.ofNullable(loan);
        Optional<LoanEntity> optionalFromDB;
        LoanEntity loanEntityFromDB;

        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));

            if (optionalFromEndpoint.isPresent()) {
                LoanEntity loanEntityFromEndpoint = loanMapper.loanToLoanEntity(optionalFromEndpoint.get());
                if (loanEntityFromEndpoint.getId() == null) {
                    return Optional.of(loanMapper.loanEntityToLoan(loanRepository.save(loanEntityFromEndpoint)));
                }

                optionalFromDB = loanRepository.findById(loanEntityFromEndpoint.getId());
                loanEntityFromDB = optionalFromDB.orElse(null);

                if (optionalFromDB.isPresent()) {
                    loanEntityFromDB.setBook(loanEntityFromEndpoint.getBook());
                    loanEntityFromDB.setUser(loanEntityFromEndpoint.getUser());
                    loanEntityFromDB.setClosed(loanEntityFromEndpoint.isClosed());
                    loanEntityFromDB.setExtended(loanEntityFromEndpoint.isExtended());
                    loanEntityFromDB.setLoanStartDate(loanEntityFromEndpoint.getLoanStartDate());
                    loanEntityFromDB.setLoanEndDate(loanEntityFromEndpoint.getLoanEndDate());

                    return Optional.ofNullable(loanMapper.loanEntityToLoan(loanRepository.save(loanEntityFromDB)));
                }
            }

        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
        return Optional.empty();
    }

    public Optional<Loan> extend(Loan loan, String tokenUUID) throws OutdatedTokenException, InvalidTokenException {
        Optional<Loan> optionalFromEndpoint = Optional.ofNullable(loan);
        Optional<LoanEntity> optionalFromDB;
        LocalDate newEndDate;

        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));

            if (optionalFromEndpoint.isPresent()) {
                LoanEntity loanEntityFromEndpoint = loanMapper.loanToLoanEntity(optionalFromEndpoint.get());
                optionalFromDB = loanRepository.findById(loanEntityFromEndpoint.getId());
                LoanEntity loanEntityFromDB = optionalFromDB.orElse(null);

                if (optionalFromDB.isPresent()) {
                    if (loanEntityFromDB.isExtended()) {
                        throw new InvalidLoanOperationException("This loan has already been extended");
                    }
                    if (loanEntityFromDB.getLoanEndDate().equals(loanEntityFromEndpoint.getLoanEndDate()) || loanEntityFromEndpoint.getLoanEndDate() == null) {
                        newEndDate = loanEntityFromDB.getLoanEndDate().plus(4, ChronoUnit.WEEKS);
                    } else if (loanEntityFromDB.getLoanEndDate().isBefore(loanEntityFromEndpoint.getLoanEndDate())) {
                        newEndDate = loanEntityFromEndpoint.getLoanEndDate();
                    } else {
                        throw new InvalidLoanOperationException("The new endDate must be after the old one");
                    }
                    loanEntityFromDB.setLoanStartDate(newEndDate);
                }
            }
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
        return Optional.empty();
    }

    public Optional<Loan> close(Loan loan, String tokenUUID) throws OutdatedTokenException, InvalidTokenException {
        Optional<Loan> optionalFromEndpoint = Optional.ofNullable(loan);
        Optional<LoanEntity> optionalFromDB;

        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));

            if (optionalFromEndpoint.isPresent()) {
                optionalFromDB = loanRepository.findById(optionalFromEndpoint.get().getId());
                if (optionalFromDB.isPresent()) {
                    optionalFromDB.get().setClosed(true);
                    return Optional.ofNullable(loanMapper.loanEntityToLoan(loanRepository.save(optionalFromDB.get())));
                }
            } else throw new InvalidLoanOperationException("Invalid loan");
        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
        return Optional.empty();
    }

    public void delete(Loan loan, String tokenUUID) throws OutdatedTokenException, InvalidTokenException {
        Optional<Loan> optional = Optional.ofNullable(loan);

        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));

            if (optional.isPresent()) {
                if (optional.get().getId() != null) {
                    loanRepository.delete(loanMapper.loanToLoanEntity(optional.get()));
                }
            }

        } catch (Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }
    }


    public Optional<Loan> findById(Integer loanId, String tokenUUID) throws OutdatedTokenException, InvalidTokenException {
        Optional<LoanEntity> optionalFromDB;

        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));

            optionalFromDB = loanRepository.findById(loanId);
            if (optionalFromDB.isPresent())
                return Optional.ofNullable(loanMapper.loanEntityToLoan(optionalFromDB.get()));
            else throw new InvalidLoanOperationException("No loan found in database");
        } catch (
                Exception ex) {
            GenericExceptionHelper.tokenExceptionHandler(ex);
        }

        return Optional.empty();
    }


    public List<Loan> findAll(String tokenUUID) {
        List<LoanEntity> loanEntities= loanRepository.findAll();
        return new ArrayList<>(loanMapper.loanEntityListToLoanList(loanEntities));
    }
}

