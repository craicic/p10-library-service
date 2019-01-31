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

    @Column(name="topic_id")
    private Integer topicId;

    @Column(name="language_id")
    private Integer languageId;

    private Integer quantity;

    @Column(name="publication_date")
    private Date publicationDate;

    @Column(name="library_id")
    private Integer libraryId;

    public BookEntity(String title, String author, String isbn, Integer topicId, Integer languageId,
                      Integer quantity, Date publicationDate, Integer libraryId) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.topicId = topicId;
        this.languageId = languageId;
        this.quantity = quantity;
        this.publicationDate = publicationDate;
        this.libraryId = libraryId;
    }

    public BookEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", topicId=" + topicId +
                ", languageId=" + languageId +
                ", quantity=" + quantity +
                ", publicationDate=" + publicationDate +
                ", libraryId=" + libraryId +
                '}';
    }
}
