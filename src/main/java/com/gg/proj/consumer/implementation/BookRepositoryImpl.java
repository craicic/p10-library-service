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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> book = criteriaQuery.from(BookEntity.class);
        Join<BookEntity, LanguageEntity> language = book.join("language");
        Join<BookEntity, LibraryEntity> library = book.join("library");
        Join<BookEntity, TopicEntity> topic = book.join("topics");


        List<Predicate> predicates = new ArrayList<>();

        Predicate authorPredicate = criteriaBuilder.like(book.get("author"), "%" + keyWord + "%");
        Predicate summaryPredicate = criteriaBuilder.like(book.get("summary"), "%" + keyWord + "%");
        Predicate titlePredicate = criteriaBuilder.like(book.get("title"), "%" + keyWord + "%");

        Predicate authorOrSummaryOrTitle = criteriaBuilder.or(authorPredicate, summaryPredicate, titlePredicate);
        predicates.add(authorOrSummaryOrTitle);

        if (available)
            predicates.add(criteriaBuilder.gt(book.get("quantity"), 0));
        if (languageId >= 0)
            predicates.add(criteriaBuilder.equal(language.get("id"), languageId));
        if (libraryId >= 0)
            predicates.add(criteriaBuilder.equal(library.get("id"), libraryId));
        if (topicId >= 0)
            predicates.add(criteriaBuilder.equal(topic.get("id"), topicId));

        criteriaQuery.where(predicates.toArray(new Predicate[0])).distinct(true);

        List<BookEntity> listBook = entityManager.createQuery(criteriaQuery).setFirstResult((pageNumber) * pageSize).setMaxResults(pageSize).getResultList();
        for (BookEntity b : listBook) {
            log.debug("book found : " + b);
        }

        return new PageImpl<>(listBook, pageable, listBook.size());
    }
}
