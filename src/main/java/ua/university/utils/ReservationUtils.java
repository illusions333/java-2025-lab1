package ua.university.utils;

import ua.university.model.ReservationStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationUtils {
    private static final Logger logger = Logger.getLogger(ReservationUtils.class.getName());
    private ReservationUtils() {}

    public static String dateFormat(LocalDate date) {
        if (date != null) return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return "";
    }
    public static String formatReservationStatus(ReservationStatus reservationStatus) {
        if (reservationStatus != null) {
            logger.log(Level.FINE, "Reservation status was converted successfully!");
            return switch (reservationStatus) {
                case CONFIRMED -> "confirmed";
                case CANCELED -> "canceled";
                case CHECKED_IN -> "checked in";
                case CHECKED_OUT -> "checked out";
            };
        }
        else{
            logger.log(Level.WARNING, "Reservation status was null!");
            return "unknown";
        }
    }
    public static boolean isValidStartDate(LocalDate date) {
        return ValidationHelper.isValidDate(date);
    }
    public static boolean isValidEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) return false;
        return isValidStartDate(startDate) && ValidationHelper.isNumberBetween(endDate.toEpochDay(),
                startDate.toEpochDay(), startDate.plusDays(30).toEpochDay());
    }
}