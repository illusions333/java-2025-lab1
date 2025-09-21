package ua.university.util;

public class InvoiceUtils {
    private InvoiceUtils() {}
    public static boolean isValidAmount(double amount) {
        return ValidationHelper.isValidPrice(amount);
    }
}
