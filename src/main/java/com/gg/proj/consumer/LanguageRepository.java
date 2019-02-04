package com.gg.proj.consumer;

import com.gg.proj.model.LanguageEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LanguageRepository extends PagingAndSortingRepository<LanguageEntity, Integer> {

    public List<LanguageEntity> findDistinctById(Integer id);
}
