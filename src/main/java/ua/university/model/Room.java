package ua.university.model;

import ua.university.util.RoomUtils;

import java.util.Objects;

public class Room {
    private int roomNumber;
    private String type;
    private int capacity;
    private double price;
    private RoomStatus roomStatus;

    public Room() {
    }

    public Room(int roomNumber, String type, int capacity, double price, RoomStatus roomStatus) {
        setRoomNumber(roomNumber);
        setType(type);
        setCapacity(capacity);
        setPrice(price);
        this.roomStatus = roomStatus;
    }

    public static Room createRoom(int roomNumber, String type, int capacity, double price, RoomStatus roomStatus) {
        if (RoomUtils.isValidRoomNumber(roomNumber)
                && RoomUtils.isValidRoomType(type)
                && RoomUtils.isValidCapacity(capacity)
                && RoomUtils.isValidPrice(price)) return new Room(roomNumber, type, capacity, price, roomStatus);
        return null;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        if (RoomUtils.isValidRoomNumber(roomNumber)) this.roomNumber = roomNumber;
    }
    public String getType() {
        if (type != null) return type;
        else return "No room type provided";
    }
    public void setType(String type) {
        if (RoomUtils.isValidRoomType(type)) this.type = type;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        if (RoomUtils.isValidCapacity(capacity)) this.capacity = capacity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (RoomUtils.isValidPrice(price)) this.price = price;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }
    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomSize(){
        if (!RoomUtils.isValidCapacity(capacity)) return "Unknown capacity";
        return switch (capacity){
            case 1, 2, 3 -> "Small";
            default -> "Big";
        };
    }

    private String formatRoomStatus() {
        if (roomStatus != null) {
            return switch (roomStatus) {
                case AVAILABLE -> "available";
                case CLEANING -> "cleaning";
                case OCCUPIED -> "occupied";
                case MAINTENANCE -> "maintenance";
            };
        }
        else return "unknown";
    }

    @Override
    public String toString() {
        return "Room {room number: " + roomNumber + ", type: " + type + ", capacity: " + capacity + ", price: " + price + ", room status: " + formatRoomStatus() + "}";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return roomNumber == room.roomNumber &&
                Objects.equals(type, room.type) &&
                capacity == room.capacity &&
                price == room.price &&
                roomStatus == room.roomStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, type, capacity, price);
    }
}