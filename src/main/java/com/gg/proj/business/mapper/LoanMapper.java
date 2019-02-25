package com.gg.proj.business.mapper;

import com.gg.proj.model.LoanEntity;
import com.gg.proj.service.loans.Loan;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanEntity loanToLoanEntity(Loan loan);

    Loan loanEntityToLoan(LoanEntity loanEntity);

    List<Loan> loanEntityListToLoanList(List<LoanEntity> loanEntities);
}
