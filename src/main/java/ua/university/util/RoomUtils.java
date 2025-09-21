package ua.university.util;

public class RoomUtils {
    private RoomUtils() {}

    public static boolean isValidRoomNumber(int roomNumber) {
        return roomNumber >= 1;
    }
    public static boolean isValidRoomType(String type) {
        return ValidationHelper.isStringLengthBetween(type, 1, 100) && ValidationHelper.isStringMatchPattern(type, "[a-zA-Z]+");
    }
    public static boolean isValidCapacity(int capacity) {
        return ValidationHelper.isNumberBetween(capacity, 1, 6);
    }
    public static boolean isValidPrice(double price) {
        return ValidationHelper.isValidPrice(price);
    }
}
