package ua.university.model;

import ua.university.util.RoomUtils;

import java.util.Objects;

public class Room {
    private int roomNumber;
    private String type;
    private int capacity;
    private double price;

    public Room() {
    }

    public Room(int roomNumber, String type, int capacity, double price) {
        setRoomNumber(roomNumber);
        setType(type);
        setCapacity(capacity);
        setPrice(price);
    }

    public static Room createRoom(int roomNumber, String type, int capacity, double price) {
        if (RoomUtils.isValidRoomNumber(roomNumber)
                && RoomUtils.isValidRoomType(type)
                && RoomUtils.isValidCapacity(capacity)
                && RoomUtils.isValidPrice(price)) return new Room(roomNumber, type, capacity, price);
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

    @Override
    public String toString() {
        return "Room {room number: " + roomNumber + ", type: " + type + ", capacity: " + capacity + ", price: " + price + "}";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return roomNumber == room.roomNumber &&
                Objects.equals(type, room.type) &&
                capacity == room.capacity &&
                price == room.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, type, capacity, price);
    }
}
