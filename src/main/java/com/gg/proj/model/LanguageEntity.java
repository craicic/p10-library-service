package com.gg.proj.model;

import javax.persistence.*;
import java.util.List;

/**
 * Base DTO class for the Language model
 */
@Entity
@Table(name="language")
public class LanguageEntity {

    @Id @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "language")
    private List<BookEntity> books;

    public LanguageEntity() {
    }

    public LanguageEntity(String name, List<BookEntity> books) {
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

    public List<BookEntity> getBook() {
        return books;
    }

    public void setBook(List<BookEntity> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "LanguageEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
