package com.gg.proj.consumer;

import com.gg.proj.model.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, Integer> {

}
