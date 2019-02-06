package com.gg.proj.model;

import javax.persistence.*;
import java.util.Date;
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

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    @Column(name = "loan_start_date", nullable = false)
    private Date loanStartDate;

    @Column(name = "loan_end_date", nullable = false)
    private Date loanEndDate;

    @Column(nullable = false)
    private boolean extended;

    public LoanEntity() {
    }

    public LoanEntity(BookEntity book, UserEntity user, Date loanStartDate, Date loanEndDate, boolean extended) {
        this.book = book;
        this.user = user;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        this.extended = extended;
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

    public Date getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(Date loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
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
}
