package ua.university.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InvoiceUtils {
    private InvoiceUtils() {}
    public static boolean isValidAmount(double amount) {
        return ValidationHelper.isValidPrice(amount);
    }
    public static String dateFormat(LocalDate date) {
        if (date != null) return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return "";
    }
}