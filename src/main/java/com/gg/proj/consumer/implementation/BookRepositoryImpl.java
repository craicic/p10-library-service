package com.gg.proj.consumer.implementation;

import com.gg.proj.consumer.BookRepositoryCustom;
import com.gg.proj.model.BookEntity;
import com.gg.proj.model.LanguageEntity;
import com.gg.proj.model.LibraryEntity;
import com.gg.proj.model.TopicEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(BookRepositoryCustom.class);

    private EntityManager entityManager;

    @Autowired
    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * <p>This method uses criteria API, it build a custom query to get the list of book filter by several criteria</p>
     *
     * @param keyWord    the keyWord of the search
     * @param languageId a search criteria (a value of -1 means excluded from search)
     * @param libraryId  a search criteria (a value of -1 means excluded from search)
     * @param topicId    a search criteria (a value of -1 means excluded from search)
     * @param available  a search criteria (is the book in stock)
     * @param pageable   the Pageable object
     * @return a Page<BookEntity>
     */
    @Override
    public Page<BookEntity> search(String keyWord, Integer languageId, Integer libraryId, Integer topicId, Boolean available, Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.debug("keyWord : [" + keyWord + "] ");
        log.debug("languageId : [" + languageId + "] ");
        log.debug("libraryId : [" + libraryId + "] ");
        log.debug("topicId : [" + topicId + "] ");
        log.debug("available : [" + available + "] ");
        log.debug("pageNumber : [" + pageNumber + "] ");

        // Creating the criteriaQuery
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> book = criteriaQuery.from(BookEntity.class);
        // Join tables.
        Join<BookEntity, LanguageEntity> language = book.join("language");
        Join<BookEntity, LibraryEntity> library = book.join("library");
        Join<BookEntity, TopicEntity> topic = book.join("topics", JoinType.LEFT);

        // We create an array of predicates...
        List<Predicate> predicates = new ArrayList<>();

        // ... It contains fix predicates...
        Predicate authorPredicate = criteriaBuilder.like(criteriaBuilder.upper(book.get("author")), "%" + keyWord.toUpperCase() + "%");
        Predicate summaryPredicate = criteriaBuilder.like(criteriaBuilder.upper(book.get("summary")), "%" + keyWord.toUpperCase() + "%");
        Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.upper(book.get("title")), "%" + keyWord.toUpperCase() + "%");

        Predicate authorOrSummaryOrTitle = criteriaBuilder.or(authorPredicate, summaryPredicate, titlePredicate);
        predicates.add(authorOrSummaryOrTitle);

        // ... And conditional predicates.
        if (available)
            predicates.add(criteriaBuilder.gt(book.get("quantity"), 0));
        if (languageId >= 0)
            predicates.add(criteriaBuilder.equal(language.get("id"), languageId));
        if (libraryId >= 0)
            predicates.add(criteriaBuilder.equal(library.get("id"), libraryId));
        if (topicId >= 0)
            predicates.add(criteriaBuilder.equal(topic.get("id"), topicId));

        // Building a query based on this array.
        criteriaQuery.where(predicates.toArray(new Predicate[0])).distinct(true);

        // Performing the query
        TypedQuery<BookEntity> query = entityManager.createQuery(criteriaQuery);

        int totalRows = query.getResultList().size();
        List<BookEntity> listBook = query.setFirstResult((pageNumber) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        for (BookEntity b : listBook) {
            log.debug("book found : " + b);
        }
        log.debug("found " + listBook.size() + " result(s) -- number of rows : " + totalRows);

        // Building a new PageImpl<> object
        return new PageImpl<>(listBook, pageable, totalRows);
    }
}
