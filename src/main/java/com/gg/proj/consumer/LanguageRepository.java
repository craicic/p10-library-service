package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LanguageRepository extends PagingAndSortingRepository<LanguageEntity, Integer> {

    public List<LanguageEntity> findDistinctByIdIn(List<Integer> ids);
}
