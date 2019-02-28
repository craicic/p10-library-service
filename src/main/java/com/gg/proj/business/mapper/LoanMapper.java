package com.gg.proj.business.mapper;

import com.gg.proj.model.LoanEntity;
import com.gg.proj.service.loans.Loan;
import com.gg.proj.service.loans.LoanMin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mappings({
            @Mapping(source = "bookId", target = "book.id"),
            @Mapping(source = "userId", target = "user.id")
    })
    LoanEntity loanToLoanEntity(Loan loan);

    @Mappings({
            @Mapping(source = "loanEntity.book.id", target = "bookId"),
            @Mapping(source = "loanEntity.user.id", target = "userId")
    })
    Loan loanEntityToLoan(LoanEntity loanEntity);

    List<Loan> loanEntityListToLoanList(List<LoanEntity> loanEntities);

    @Mappings({
            @Mapping(source = "bookId", target = "book.id"),
            @Mapping(source = "userId", target = "user.id")
    })
    LoanEntity loanMinToLoanEntity(LoanMin loanMin);
}
