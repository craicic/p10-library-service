package com.gg.proj.business.mapper;

import com.gg.proj.model.LibraryEntity;
import com.gg.proj.service.books.Library;
import com.gg.proj.service.books.LibraryMin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    Library libraryEntityToLibrary(LibraryEntity libraryEntity);

    LibraryEntity libraryToLibraryEntity(Library library);

    List<Library> libraryEntityListToLibraryList(List<LibraryEntity> libraryEntities);

    LibraryEntity libraryMinToLibrary(LibraryMin libraryMin);
}
