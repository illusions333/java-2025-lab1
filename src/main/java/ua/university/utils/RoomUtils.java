package ua.university.utils;

import ua.university.model.RoomStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomUtils {
    private RoomUtils() {}
    private static final Logger logger = Logger.getLogger(RoomUtils.class.getName());
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

    public static String formatRoomStatus(RoomStatus roomStatus) {
        if (roomStatus != null) {
            logger.log(Level.FINE, "Room status was converted successfully!");
            return switch (roomStatus) {
                case AVAILABLE -> "available";
                case CLEANING -> "cleaning";
                case OCCUPIED -> "occupied";
                case MAINTENANCE -> "maintenance";
            };
        }
        else {
            logger.log(Level.WARNING, "Room status was null!");
            return "unknown";
        }
    }
}