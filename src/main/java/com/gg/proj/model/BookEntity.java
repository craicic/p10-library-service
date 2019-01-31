package com.gg.proj.model;

import sun.util.calendar.BaseCalendar;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

/**
 * Base class for the Book model
 */
@Entity
public class BookEntity {

    @Id @GeneratedValue
    private Integer id;

    private String title;

    private String author;

    private String isbn;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private TopicEntity topic;

    @Column(name="language_id")
    private Integer languageId;

    private Integer quantity;

    @Column(name="publication_date")
    private Date publicationDate;

    @Column(name="library_id")
    private Integer libraryId;


}
