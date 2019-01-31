package com.gg.proj.util;

import com.gg.proj.model.BookEntity;
import com.gg.proj.service.library.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperWorker {

    Book bookEntityToBook(BookEntity bookEntity);
}
