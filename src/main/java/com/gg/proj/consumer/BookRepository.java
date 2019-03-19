package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer>, BookRepositoryCustom {

    @Query("select distinct b from BookEntity b " +
            "where upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)")
    Page<BookEntity> searchPagedBooks(@Param("x") String keyWord, Pageable pageable);

    @Query("select distinct b from BookEntity b " +
            "where upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)")
    List<BookEntity> searchAllBooks(@Param("x") String keyWord);

    @Modifying
    @Query("UPDATE BookEntity b SET b.quantity = (b.quantity - 1) WHERE b.id = (:id)")
    void decreaseQuantity(@Param("id") int bookId);

    @Modifying
    @Query("UPDATE BookEntity b SET b.quantity = (b.quantity + 1) WHERE b.id = (:id)")
    void increaseQuantity(@Param("id") int bookId);

//    @Query("select distinct la, li, t from  BookEntity b , LanguageEntity la , LibraryEntity li , TopicEntity t " +
//            " inner join b.language  as la " +
//            " inner join b.library  as li " +
//            " inner join b.topics  as t " +
//            " where(upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x))")
//    List<Object[]> searchAnnexData(@Param("x") String keyWord);

//
//    @Query("select distinct b from BookEntity b where (upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x))and b.quantity > 0")
//    Page<BookEntity> searchAvailable(@Param("x") String keyWord, Pageable pageable);

}
