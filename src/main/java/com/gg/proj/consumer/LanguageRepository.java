package com.gg.proj.consumer;

import com.gg.proj.model.LanguageEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LanguageRepository extends PagingAndSortingRepository<LanguageEntity, Integer> {
}
