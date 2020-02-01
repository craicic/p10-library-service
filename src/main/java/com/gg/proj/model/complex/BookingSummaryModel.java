package com.gg.proj.model.complex;

import com.gg.proj.model.BookingEntity;

import java.time.LocalDate;
import java.util.Objects;

public class BookingSummaryModel {

    protected BookingEntity booking;
    protected Long positionInQueue;
    protected LocalDate nextReturnDate;

    public BookingSummaryModel() {
    }

    public BookingSummaryModel(BookingEntity booking, Long positionInQueue, LocalDate nextReturnDate) {
        this.booking = booking;
        this.positionInQueue = positionInQueue;
        this.nextReturnDate = nextReturnDate;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
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
        BookingSummaryModel that = (BookingSummaryModel) o;
        return booking.equals(that.booking) &&
                positionInQueue.equals(that.positionInQueue) &&
                nextReturnDate.equals(that.nextReturnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking, positionInQueue, nextReturnDate);
    }

    @Override
    public String toString() {
        return "BookingSummaryModel{" +
                "booking=" + booking +
                ", positionInQueue=" + positionInQueue +
                ", nextReturnDate=" + nextReturnDate +
                '}';
    }
}
