package com.gg.proj.model.complex;

import com.gg.proj.model.BookEntity;
import com.gg.proj.model.BookingEntity;

import java.time.LocalDate;
import java.util.Objects;

public class BookingInfoModel {

    protected BookingEntity booking;
    protected BookEntity book;
    protected Long positionInQueue;
    protected LocalDate nextReturnDate;

    public BookingInfoModel() {
    }

    public BookingInfoModel(BookingEntity booking, BookEntity book, Long positionInQueue, LocalDate nextReturnDate) {
        this.booking = booking;
        this.book = book;

        this.positionInQueue = positionInQueue;
        this.nextReturnDate = nextReturnDate;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public Long getPositionInQueue() {
        return positionInQueue;
    }

    public void setPositionInQueue(Long positionInQueue) {
        this.positionInQueue = positionInQueue;
    }

    public LocalDate getNextReturnDate() {
        return nextReturnDate;
    }

    public void setNextReturnDate(LocalDate nextReturnDate) {
        this.nextReturnDate = nextReturnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingInfoModel that = (BookingInfoModel) o;
        return booking.equals(that.booking) &&
                book.equals(that.book) &&
                positionInQueue.equals(that.positionInQueue) &&
                Objects.equals(nextReturnDate, that.nextReturnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, book, positionInQueue, nextReturnDate);
    }

    @Override
    public String toString() {
        return "BookingInfoModel{" +
                "booking=" + booking +
                ", book=" + book +
                ", positionInQueue=" + positionInQueue +
                ", nextReturnDate=" + nextReturnDate +
                '}';
    }
}
