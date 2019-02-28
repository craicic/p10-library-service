package com.gg.proj.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "loan")
public class LoanEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    //    @ManyToOne(cascade=CascadeType.PERSIST)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "loan_start_date", nullable = false)
    private LocalDate loanStartDate;

    @Column(name = "loan_end_date", nullable = false)
    private LocalDate loanEndDate;

    @Column(nullable = false)
    private boolean extended;

    @Column(nullable = false)
    private boolean closed;

    public LoanEntity() {
    }

    public LoanEntity(BookEntity book, UserEntity user, LocalDate loanStartDate, LocalDate loanEndDate, boolean extended, boolean closed) {
        this.book = book;
        this.user = user;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        this.extended = extended;
        this.closed = closed;
    }

    @PrePersist
    public void prePersist() {
        this.loanStartDate = LocalDate.now();
        if (this.loanEndDate == null) {
            this.loanEndDate = LocalDate.now().plus(4, ChronoUnit.WEEKS);
        }
        this.extended = false;
        this.closed = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDate getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(LocalDate loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public LocalDate getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanEntity that = (LoanEntity) o;
        return extended == that.extended &&
                id.equals(that.id) &&
                book.equals(that.book) &&
                user.equals(that.user) &&
                loanStartDate.equals(that.loanStartDate) &&
                loanEndDate.equals(that.loanEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, loanStartDate, loanEndDate, extended);
    }

    @Override
    public String toString() {
        return "LoanEntity{" +
                "id=" + id +
                ", loanStartDate=" + loanStartDate +
                ", loanEndDate=" + loanEndDate +
                ", extended=" + extended +
                ", closed=" + closed +
                '}';
    }
}
