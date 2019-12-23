package com.gg.proj.model.complex;

import java.time.LocalDate;
import java.util.Objects;

public class PlaceInQueueModel {

    protected Long positionInQueue;
    protected LocalDate nextReturnDate;

    public PlaceInQueueModel() {
    }

    public PlaceInQueueModel(Long positionInQueue, LocalDate nextReturnDate) {
        this.positionInQueue = positionInQueue;
        this.nextReturnDate = nextReturnDate;
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
        PlaceInQueueModel that = (PlaceInQueueModel) o;
        return positionInQueue.equals(that.positionInQueue) &&
                Objects.equals(nextReturnDate, that.nextReturnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionInQueue, nextReturnDate);
    }

    @Override
    public String toString() {
        return "PlaceInQueue{" +
                "positionInQueue=" + positionInQueue +
                ", nextReturnDate=" + nextReturnDate +
                '}';
    }
}
