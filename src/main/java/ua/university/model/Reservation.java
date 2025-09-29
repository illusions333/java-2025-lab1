package ua.university.model;

import ua.university.util.ReservationUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private Guest guest;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus reservationStatus;

    public Reservation() {}

    public Reservation(Guest guest, Room room, LocalDate startDate, LocalDate endDate, ReservationStatus reservationStatus) {
        setGuest(guest);
        setRoom(room);
        setStartDate(startDate);
        setEndDate(endDate);
        this.reservationStatus = reservationStatus;
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
    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }
    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public static Reservation createReservation(Guest guest, Room room, LocalDate startDate, LocalDate endDate, ReservationStatus reservationStatus) {
        if (ReservationUtils.isValidStartDate(startDate) && ReservationUtils.isValidEndDate(startDate, endDate))
            return new Reservation(guest, room, startDate, endDate, reservationStatus);
        return null;
    }

    public int getDuration() {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay());
    }

    private String formatReservationStatus(ReservationStatus reservationStatus) {
        if (reservationStatus != null) {
            return switch (reservationStatus) {
                case CONFIRMED -> "confirmed";
                case CANCELED -> "canceled";
                case CHECKED_IN -> "checked in";
                case CHECKED_OUT -> "checked out";
            };
        }
        else return "unknown";
    }

    @Override
    public String toString() {
        return "Reservation {guest: " + guest + ", \nroom: " + room + ", \nstartDate: "
                + ReservationUtils.dateFormat(startDate) + ", endDate: " + ReservationUtils.dateFormat(endDate)
                + ", \nreservationStatus: " + formatReservationStatus(reservationStatus) + "}";
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
                Objects.equals(endDate, reservation.endDate) &&
                reservationStatus == reservation.reservationStatus;
    }
}