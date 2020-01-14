package com.gg.proj.model.complex;

import com.gg.proj.model.BookEntity;

import java.time.LocalDate;
import java.util.Objects;

public class BookAndBookingInfoModel {

    private BookEntity bookEntity;
    private LocalDate nextReturnDate;
    private Long bookingQueue;
    private boolean availableToBooking;

    public BookAndBookingInfoModel() {
    }

    public BookAndBookingInfoModel(BookEntity bookEntity, LocalDate nextReturnDate, Long bookingQueue, boolean availableToBooking) {
        this.bookEntity = bookEntity;
        this.nextReturnDate = nextReturnDate;
        this.bookingQueue = bookingQueue;
        this.availableToBooking = availableToBooking;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    public LocalDate getNextReturnDate() {
        return nextReturnDate;
    }

    public void setNextReturnDate(LocalDate nextReturnDate) {
        this.nextReturnDate = nextReturnDate;
    }

    public Long getBookingQueue() {
        return bookingQueue;
    }

    public void setBookingQueue(Long bookingQueue) {
        this.bookingQueue = bookingQueue;
    }

    public boolean isAvailableToBooking() {
        return availableToBooking;
    }

    public void setAvailableToBooking(boolean availableToBooking) {
        this.availableToBooking = availableToBooking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAndBookingInfoModel that = (BookAndBookingInfoModel) o;
        return availableToBooking == that.availableToBooking &&
                bookEntity.equals(that.bookEntity) &&
                Objects.equals(nextReturnDate, that.nextReturnDate) &&
                Objects.equals(bookingQueue, that.bookingQueue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookEntity, nextReturnDate, bookingQueue, availableToBooking);
    }

    @Override
    public String toString() {
        return "BookAndBookingInfoModel{" +
                "bookEntity=" + bookEntity +
                ", nextReturnDate=" + nextReturnDate +
                ", bookingQueue=" + bookingQueue +
                ", availableToBooking=" + availableToBooking +
                '}';
    }
}
