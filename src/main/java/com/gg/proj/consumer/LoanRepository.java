package com.gg.proj.consumer;

import com.gg.proj.model.LoanEntity;
import com.gg.proj.service.loans.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

}
