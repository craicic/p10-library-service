package com.gg.proj.business;

import com.gg.proj.consumer.BookRepository;
import com.gg.proj.model.BookEntity;
import com.gg.proj.service.library.Book;
import com.gg.proj.util.MapperWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * This class apply business method
 */
@Component
@Transactional
public class BookManager {

    private static final Logger log = LoggerFactory.getLogger(BookManager.class);

    private MapperWorker mapperWorker;

    private BookRepository bookRepository;

    @Autowired
    public BookManager(MapperWorker mapperWorker, BookRepository bookRepository) {
        this.mapperWorker = mapperWorker;
        this.bookRepository = bookRepository;
    }

    /**
     * This method calls the repository in order to get a book by id. If a such book is present in database, it perform
     * a mapping operation then return the book. Else it returns null.
     *
     * @param id this id of the book
     * @return a unique book mapped into a Book
     */
    public Book getBookById(Integer id) {

        if (!bookRepository.findById(id).isPresent()) {
            log.info("getBookById : Requesting a book by id : " + id + " => id not found in database");
            return null;
        }
        BookEntity bookEntity = bookRepository.findById(id).get();

        log.info("getBookById : Requesting a book by id : " + id + " => found : ");
        // Mapping
        return mapperWorker.bookEntityToBook(bookEntity);
    }
}
