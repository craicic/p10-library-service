package com.gg.proj.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Base DTO class for the Topic model, it has an index on name and it describe a ManyToMany relation between topic
 * and book tables.
 */
@Entity
@Table(name = "topic",
        indexes = {@Index(name = "topic_idx", columnList = "name", unique = true)})
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "topics")
//    @JoinTable(name = "topic_book",
//            joinColumns = @JoinColumn(name = "topic_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
//    )
    private List<BookEntity> books;

    public TopicEntity() {
    }

    public TopicEntity(String name, List<BookEntity> books) {
        this.name = name;
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "TopicEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicEntity that = (TopicEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
