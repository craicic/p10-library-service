package com.gg.proj.model;

import javax.persistence.*;

@Entity
@Table(name="language")
public class LanguageEntity {

    @Id @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "language")
    private BookEntity book;

    public LanguageEntity() {
    }

    public LanguageEntity(String name, BookEntity book) {
        this.name = name;
        this.book = book;
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

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "LanguageEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", book=" + book +
                '}';
    }
}
