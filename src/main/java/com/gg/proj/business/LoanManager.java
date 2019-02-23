package com.gg.proj.business;

import com.gg.proj.business.mapper.LoanMapper;
import com.gg.proj.consumer.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


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

}
