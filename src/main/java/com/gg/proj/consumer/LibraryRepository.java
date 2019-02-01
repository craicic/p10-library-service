package com.gg.proj.consumer;

import com.gg.proj.model.LibraryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LibraryRepository extends PagingAndSortingRepository<LibraryEntity, Integer> {

    public List<LibraryEntity> findDistinctByIdIn(List<Integer> ids);
}
