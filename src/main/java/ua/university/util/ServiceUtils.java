package ua.university.util;

public class ServiceUtils {
    private ServiceUtils() {}

    public static boolean isValidServiceName(String name) {
        return ValidationHelper.isStringLengthBetween(name, 1, 100);
    }

    public static boolean isValidPrice(double price) {
        return ValidationHelper.isValidPrice(price);
    }
}