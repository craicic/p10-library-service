package com.gg.proj.business;

import com.gg.proj.business.mapper.LibraryMapper;
import com.gg.proj.consumer.LibraryRepository;
import com.gg.proj.model.LibraryEntity;
import com.gg.proj.service.books.Library;
import com.gg.proj.service.books.LibraryMin;
import com.gg.proj.service.exceptions.GenericExceptionHelper;
import com.gg.proj.service.exceptions.InvalidTokenException;
import com.gg.proj.service.exceptions.OutdatedTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class LibraryManager {

    private static final Logger log = LoggerFactory.getLogger(LibraryManager.class);

    private LibraryRepository libraryRepository;

    private LibraryMapper libraryMapper;

    private TokenManager tokenManager;

    @Autowired
    public LibraryManager(LibraryRepository libraryRepository, LibraryMapper libraryMapper, TokenManager tokenManager) {
        this.libraryRepository = libraryRepository;
        this.libraryMapper = libraryMapper;
        this.tokenManager = tokenManager;
    }

    // CRUD Methods
    public Optional<Library> findById(Integer id) {
        Optional<LibraryEntity> optional = libraryRepository.findById(id);
        LibraryEntity libraryEntity = optional.orElse(null);

        if (optional.isPresent()) {
            log.info("findById : Requesting a library by id : " + id + " => found : " + libraryEntity);
        } else {
            log.info("findById : Requesting a library by id : " + id + " => id not found in database");
        }

        return Optional.of(libraryMapper.libraryEntityToLibrary(libraryEntity));
    }

    public Optional<Library> save(Library library, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e) {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }

        LibraryEntity libraryEntity = libraryRepository.save(libraryMapper.libraryToLibraryEntity(library));
        return Optional.ofNullable(libraryMapper.libraryEntityToLibrary(libraryEntity));
    }


    public void delete(Library library, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e) {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }

        libraryRepository.delete(libraryMapper.libraryToLibraryEntity(library));
    }

    public List<Library> findAll() {
        List<LibraryEntity> libraryEntities = libraryRepository.findAll();
        return libraryMapper.libraryEntityListToLibraryList(libraryEntities);
    }

    public Optional<Library> create(LibraryMin libraryMin, String tokenUUID) throws InvalidTokenException, OutdatedTokenException {
        try {
            tokenManager.checkIfValidByUuid(UUID.fromString(tokenUUID));
        } catch (Exception e) {
            GenericExceptionHelper.tokenExceptionHandler(e);
        }

        LibraryEntity libraryEntity = libraryMapper.libraryMinToLibrary(libraryMin);
        libraryEntity = libraryRepository.save(libraryEntity);

        return Optional.ofNullable(libraryMapper.libraryEntityToLibrary(libraryEntity));
    }
}

