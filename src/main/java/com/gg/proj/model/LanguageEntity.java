package com.gg.proj.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Base DTO class for the Language model. Unique index on name. OneToMany relation with the book table.
 */
@Entity
@Table(name = "language",
        indexes = {@Index(name = "language_idx", columnList = "name", unique = true)})
public class LanguageEntity {

    @Id
    @GeneratedValue
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

    @Override
    public String toString() {
        return "LanguageEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageEntity that = (LanguageEntity) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
