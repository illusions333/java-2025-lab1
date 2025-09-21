package ua.university.model;

import ua.university.util.ReservationUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private Guest guest;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;

    public Reservation() {}

    public Reservation(Guest guest, Room room, LocalDate startDate, LocalDate endDate) {
        setGuest(guest);
        setRoom(room);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public Guest getGuest() {
        return guest;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        if (ReservationUtils.isValidStartDate(startDate)) this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        if (ReservationUtils.isValidEndDate(startDate, endDate)) this.endDate = endDate;
    }

    public static Reservation createReservation(Guest guest, Room room, LocalDate startDate, LocalDate endDate) {
        if (ReservationUtils.isValidStartDate(startDate) && ReservationUtils.isValidEndDate(startDate, endDate))
            return new Reservation(guest, room, startDate, endDate);
        return null;
    }

    public int getDuration() {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay());
    }

    @Override
    public String toString() {
        return "Reservation {guest: " + guest + ", \nroom: " + room + ", \nstartDate: "
                + ReservationUtils.dateFormat(startDate) + ", endDate: "
                + ReservationUtils.dateFormat(endDate) + "}";
    }
    @Override
    public int hashCode() {
        return Objects.hash(guest, room, startDate, endDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation reservation = (Reservation) obj;
        return Objects.equals(guest, reservation.guest) &&
                Objects.equals(room, reservation.room) &&
                Objects.equals(startDate, reservation.startDate) &&
                Objects.equals(endDate, reservation.endDate);
    }
}
