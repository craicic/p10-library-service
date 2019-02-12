package com.gg.proj.consumer.implementation;

import com.gg.proj.consumer.BookRepositoryCustom;
import com.gg.proj.model.BookEntity;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    EntityManager em;

    @Autowired
    public BookRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Page<BookEntity> search(String keyWord, Integer languageId, Integer libraryId, Integer topicId, Boolean available, Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = criteriaBuilder.createQuery(BookEntity.class);
        Root<BookEntity> book = criteriaQuery.from(BookEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        //predicates.add(criteriaBuilder.like(book.get("author,summary,title"), "%" + keyWord + "%")); Test Erwan

        predicates.add(criteriaBuilder.like(book.get("author"), "%" + keyWord + "%"));
        predicates.add(criteriaBuilder.like(book.get("summary"), "%" + keyWord + "%"));
        predicates.add(criteriaBuilder.like(book.get("title"), "%" + keyWord + "%"));

        if (available)
            predicates.add(criteriaBuilder.gt(book.get("quantity"), 0));
        if (languageId != null)
            predicates.add(criteriaBuilder.equal(book.get("language_id"), languageId));
        if (libraryId != null)
            predicates.add(criteriaBuilder.equal(book.get("library_id"), libraryId));
        if (topicId != null)
            predicates.add(criteriaBuilder.equal(book.get("topic_id"), topicId));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<BookEntity> listBook = em.createQuery(criteriaQuery).setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).getResultList();

        return new PageImpl<>(listBook, pageable, listBook.size());
    }
}
