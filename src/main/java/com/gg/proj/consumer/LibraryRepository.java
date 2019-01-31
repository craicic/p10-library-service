package com.gg.proj.consumer;

import com.gg.proj.model.LibraryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LibraryRepository extends PagingAndSortingRepository<LibraryEntity, Integer> {
}
