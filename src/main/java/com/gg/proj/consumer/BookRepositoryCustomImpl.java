package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import org.springframework.data.jpa.domain.JpaSort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<BookEntity> filterSearch( String keyWord,
                                          Integer languageId,
                                          Integer libraryId,
                                          Integer topicId,
                                          Boolean available) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> query = cb.createQuery(BookEntity.class);
        Root<BookEntity> root = query.from(BookEntity.class);

//        if(languageId != -1 && libraryId != -1 && topicId != -1 && available)
//            query.select(root).where(cb.like(root.get("title"))).where(cb.equal(root.get("")));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
