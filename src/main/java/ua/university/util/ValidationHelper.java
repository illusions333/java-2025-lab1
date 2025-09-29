package ua.university.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

class ValidationHelper {

    private ValidationHelper() {
    }

    static boolean isStringMatchPattern(String text, String pattern) {
        if (text == null || pattern == null) {
            return false;
        }
        return Pattern.matches(pattern, text);
    }

    static boolean isNumberBetween(int number, int min, int max) {
        return number >= min && number <= max;
    }
    static boolean isNumberBetween(long number, long min, long max) {
        return number >= min && number <= max;
    }

    static boolean isStringLengthBetween(String text, int min, int max) {
        if (text == null) {
            return false;
        }
        int length = text.trim().length();
        return length >= min && length <= max;
    }

    static boolean isValidPrice(double price) {
        return price >= 0;
    }

    static boolean isValidDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        return isNumberBetween(date.toEpochDay(), LocalDate.now().minusYears(1).toEpochDay(),
                LocalDate.now().toEpochDay());
    }
}