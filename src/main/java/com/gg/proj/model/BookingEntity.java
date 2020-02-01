package com.gg.proj.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "booking",
        indexes = {@Index(name = "booking_idx", columnList = "book_id, user_id", unique = true)})
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "time", nullable = false)
    private LocalDateTime bookingTime;

    @Column(name = "notification_time")
    private LocalDateTime notificationTime;

    public BookingEntity() {
    }

    public BookingEntity(BookEntity book, UserEntity user, LocalDateTime bookingTime) {
        this.book = book;
        this.user = user;
        this.bookingTime = bookingTime;
    }


    public BookingEntity(BookEntity book, UserEntity user, LocalDateTime bookingTime, LocalDateTime notificationTime) {
        this.book = book;
        this.user = user;
        this.bookingTime = bookingTime;
        this.notificationTime = notificationTime;
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

    public void setBook(BookEntity bookId) {
        this.book = bookId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userId) {
        this.user = userId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingEntity that = (BookingEntity) o;
        return id.equals(that.id) &&
                book.equals(that.book) &&
                user.equals(that.user) &&
                bookingTime.equals(that.bookingTime) &&
                Objects.equals(notificationTime, that.notificationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, user, bookingTime, notificationTime);
    }

    @Override
    public String toString() {
        return "BookingEntity{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", bookingTime=" + bookingTime +
                ", notificationTime=" + notificationTime +
                '}';
    }
}
