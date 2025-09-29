package ua.university.service;

import ua.university.model.Room;

public class RoomListGenerator {
    private static String formatRoomStatus(Room room) {
        if (room.getRoomStatus() != null) {
            return switch (room.getRoomStatus()) {
                case AVAILABLE -> "available";
                case CLEANING -> "cleaning";
                case OCCUPIED -> "occupied";
                case MAINTENANCE -> "maintenance";
            };
        }
        else return "unknown";
    }

    public static String generateRoomList(Room[] rooms) {
        StringBuilder result = new StringBuilder();
        for (Room room : rooms) {
            result.append(String.format("Room #%d: size - %s, capacity - %d, room status - %s, price - %f%n",
                    room.getRoomNumber(), room.getRoomSize(), room.getCapacity(), formatRoomStatus(room), room.getPrice()));
        }
        return result.toString();
    }
}
