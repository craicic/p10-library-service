package com.gg.proj.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "loan")
public class LoanEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
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
}
