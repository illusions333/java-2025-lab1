package ua.university.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationUtils {
    private ReservationUtils() {}

    public static String dateFormat(LocalDate date) {
        if (date != null) return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return "";
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
