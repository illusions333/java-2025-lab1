package ua.university.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GuestUtils {
    private GuestUtils() {}

    public static boolean isValidCheckInDate(LocalDate date) {
        return ValidationHelper.isValidDate(date);
    }
    public static String dateFormat(LocalDate date) {
        if (date == null) return "";
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}