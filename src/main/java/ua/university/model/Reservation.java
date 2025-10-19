package ua.university.model;

import ua.university.exception.InvalidDataException;
import ua.university.utils.ReservationUtils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reservation {
    private Guest guest;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus reservationStatus;
    private static final Logger logger = Logger.getLogger(Reservation.class.getName());

    public Reservation(Guest guest, Room room, LocalDate startDate, LocalDate endDate, ReservationStatus reservationStatus) {
        setGuest(guest);
        setRoom(room);
        setStartDate(startDate);
        setEndDate(endDate);
        this.reservationStatus = reservationStatus;
        logger.log(Level.FINE, "Reservation was created successfully");
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
        if (!ReservationUtils.isValidStartDate(startDate)){
            logger.log(Level.SEVERE, "Invalid start date for the reservation: {0} (should be between today and 1 year ago)", startDate);
            throw new InvalidDataException("Invalid start date for the reservation");
        }
        logger.log(Level.FINE, "Start date for the reservation was set successfully!");
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        if (!ReservationUtils.isValidEndDate(startDate, endDate))
        {
            logger.log(Level.SEVERE, "Invalid end date for the reservation: {0} (should be between start date and 30 days from it)", endDate);
            throw new InvalidDataException("Invalid end date for the reservation");
        }
        logger.log(Level.FINE, "End date for the reservation was set successfully!");
        this.endDate = endDate;
    }
    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }
    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public static Reservation createReservation(Guest guest, Room room, LocalDate startDate, LocalDate endDate, ReservationStatus reservationStatus) {
        if (!ReservationUtils.isValidStartDate(startDate)){
            logger.log(Level.SEVERE, "Invalid start date for the reservation: {0} (should be between today and 1 year ago)", startDate);
            throw new InvalidDataException("Invalid start date for the reservation");
        }
        if (!ReservationUtils.isValidEndDate(startDate, endDate))
        {
            logger.log(Level.SEVERE, "Invalid end date for the reservation: {0} (should be between start date and 30 days from it)", endDate);
            throw new InvalidDataException("Invalid end date for the reservation");
        }
        return new Reservation(guest, room, startDate, endDate, reservationStatus);
    }

    public int getDuration() {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay());
    }

    @Override
    public String toString() {
        return "Reservation {guest: " + guest + ", \nroom: " + room + ", \nstartDate: "
                + ReservationUtils.dateFormat(startDate) + ", endDate: " + ReservationUtils.dateFormat(endDate)
                + ", \nreservationStatus: " + ReservationUtils.formatReservationStatus(reservationStatus) + "}";
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