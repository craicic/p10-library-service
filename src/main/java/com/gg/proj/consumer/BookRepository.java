package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<BookEntity, Integer> {


}
