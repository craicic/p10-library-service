package com.gg.proj.consumer;

import com.gg.proj.model.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    @Query("select distinct b from BookEntity b where upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)")
    public Page<BookEntity> search(@Param("x") String keyWord, Pageable pageable);

    @Query("select distinct b from BookEntity b where (upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x))and b.quantity > 0")
    public Page<BookEntity> searchAvailable(@Param("x") String keyWord, Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.topics as t  " +
            "join b.language as lang " +
            "join b.library as lib " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and t.id = :topicId " +
            "and lang.id = (:languageId) " +
            "and lib.id = (:libraryId))")
    public Page<BookEntity> filter(@Param("x") String keyWord,
                                   @Param("languageId") Integer languageId,
                                   @Param("libraryId") Integer libraryId,
                                   @Param("topicId") Integer topicId,
                                   Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.topics as t  " +
            "join b.language as lang " +
            "join b.library as lib " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and t.id = :topicId " +
            "and lang.id = (:languageId) " +
            "and lib.id = (:libraryId)" +
            "and b.quantity > 0 )")
    public Page<BookEntity> filterAvailable(@Param("x") String keyWord,
                                            @Param("languageId") Integer languageId,
                                            @Param("libraryId") Integer libraryId,
                                            @Param("topicId") Integer topicId,
                                            Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.topics as t  " +
            "join b.language as lang " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and t.id = :topicId " +
            "and lang.id = (:languageId))")
    public Page<BookEntity> filterByTopicLanguage(@Param("x") String keyWord,
                                   @Param("languageId") Integer languageId,
                                   @Param("topicId") Integer topicId,
                                   Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.topics as t  " +
            "join b.language as lang " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and t.id = :topicId " +
            "and lang.id = (:languageId) " +
            "and b.quantity > 0 )")
    public Page<BookEntity> filterAvailableByTopicLanguage(@Param("x") String keyWord,
                                   @Param("languageId") Integer languageId,
                                   @Param("topicId") Integer topicId,
                                   Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.language as lang " +
            "join b.library as lib " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and lang.id = (:languageId) " +
            "and lib.id = (:libraryId))")
    public Page<BookEntity> filterByLanguageLibrary(@Param("x") String keyWord,
                                   @Param("languageId") Integer languageId,
                                   @Param("libraryId") Integer libraryId,
                                   Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.language as lang " +
            "join b.library as lib " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and lang.id = (:languageId) " +
            "and lib.id = (:libraryId)" +
            "and b.quantity > 0 )")
    public Page<BookEntity> filterAvailableByLanguageLibrary(@Param("x") String keyWord,
                                            @Param("languageId") Integer languageId,
                                            @Param("libraryId") Integer libraryId,
                                            Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.topics as t  " +
            "join b.library as lib " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and t.id = :topicId " +
            "and lib.id = (:libraryId))")
    public Page<BookEntity> filterByTopicLibrary(@Param("x") String keyWord,
                                   @Param("libraryId") Integer libraryId,
                                   @Param("topicId") Integer topicId,
                                   Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.topics as t  " +
            "join b.library as lib " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and t.id = :topicId " +
            "and lib.id = (:libraryId)" +
            "and b.quantity > 0 )")
    public Page<BookEntity> filterAvailableByTopicLibrary(@Param("x") String keyWord,
                                            @Param("libraryId") Integer libraryId,
                                            @Param("topicId") Integer topicId,
                                            Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.topics as t  " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and t.id = :topicId )")
    public Page<BookEntity> filterByTopic(@Param("x") String keyWord,
                                                 @Param("topicId") Integer topicId,
                                                 Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.topics as t  " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and t.id = :topicId " +
            "and b.quantity > 0 )")
    public Page<BookEntity> filterAvailableByTopic(@Param("x") String keyWord,
                                                          @Param("topicId") Integer topicId,
                                                          Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.library as lib " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and lib.id = (:libraryId))")
    public Page<BookEntity> filterByLibrary(@Param("x") String keyWord,
                                                 @Param("libraryId") Integer libraryId,
                                                 Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.library as lib " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and lib.id = (:libraryId)" +
            "and b.quantity > 0 )")
    public Page<BookEntity> filterAvailableByLibrary(@Param("x") String keyWord,
                                                          @Param("libraryId") Integer libraryId,
                                                          Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.language as lang " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and lang.id = (:languageId))")
    public Page<BookEntity> filterByLanguage(@Param("x") String keyWord,
                                                    @Param("languageId") Integer languageId,
                                                    Pageable pageable);

    @Query("select distinct b  from BookEntity b " +
            "join b.language as lang " +
            "where ((upper(b.title) like upper(:x) or upper(b.author) like upper(:x) or upper(b.summary) like upper(:x)) " +
            "and lang.id = (:languageId) " +
            "and b.quantity > 0 )")
    public Page<BookEntity> filterAvailableByLanguage(@Param("x") String keyWord,
                                                             @Param("languageId") Integer languageId,
                                                             Pageable pageable);

}
