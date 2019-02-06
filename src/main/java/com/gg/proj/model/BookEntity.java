package com.gg.proj.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Base DTO class for the Book model
 */
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String isbn;

    @ManyToMany(mappedBy = "books")
    @Column(nullable = false)
    private List<TopicEntity> topics;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity language;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "publication_date",
            nullable = false)
    private Date publicationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "library_id", nullable = false)
    private LibraryEntity library;

    private String summary;

    @OneToMany(mappedBy = "book")
    private List<LoanEntity> loans;

    public BookEntity() {
    }

    public BookEntity(String title, String author, String isbn, List<TopicEntity> topics, LanguageEntity language,
                      Integer quantity, Date publicationDate, LibraryEntity library, String summary, List<LoanEntity> loans) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.topics = topics;
        this.language = language;
        this.quantity = quantity;
        this.publicationDate = publicationDate;
        this.library = library;
        this.summary = summary;
        this.loans = loans;
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

    public List<TopicEntity> getTopics() {
        return topics;
    }

    public LanguageEntity getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEntity language) {
        this.language = language;
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

    public LibraryEntity getLibrary() {
        return library;
    }

    public void setLibrary(LibraryEntity library) {
        this.library = library;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<LoanEntity> getLoans() {
        return loans;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", topics=" + topics +
                ", language=" + language +
                ", quantity=" + quantity +
                ", publicationDate=" + publicationDate +
                ", library=" + library +
                ", summary='" + summary + '\'' +
                ", loans=" + loans +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return title.equals(that.title) &&
                author.equals(that.author) &&
                Objects.equals(isbn, that.isbn) &&
                language.equals(that.language) &&
                quantity.equals(that.quantity) &&
                publicationDate.equals(that.publicationDate) &&
                library.equals(that.library) &&
                Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn, language, quantity, publicationDate, library, summary);
    }
}
