package ua.university.model;

import ua.university.exception.InvalidDataException;
import ua.university.utils.RoomUtils;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Room implements Comparable<Room> {
    private int roomNumber;
    private String type;
    private int capacity;
    private double price;
    private RoomStatus roomStatus;
    private static final Logger logger = Logger.getLogger(Room.class.getName());

    public Room(int roomNumber, String type, int capacity, double price, RoomStatus roomStatus) {
        setRoomNumber(roomNumber);
        setType(type);
        setCapacity(capacity);
        setPrice(price);
        this.roomStatus = roomStatus;
        logger.log(Level.FINE, "Room was created successfully!");
    }

    public static Room createRoom(int roomNumber, String type, int capacity, double price, RoomStatus roomStatus) {
        if (type == null){
            logger.log(Level.WARNING, "Room type is null!");
        }
        if (!RoomUtils.isValidRoomNumber(roomNumber)) {
            logger.log(Level.SEVERE, "Room number is invalid: {0} (should be >= 1)", roomNumber);
            throw new InvalidDataException("Room number is invalid!");
        }
        if (!RoomUtils.isValidRoomType(type)){
            logger.log(Level.SEVERE, "Room type is invalid: {0}", type);
            throw new InvalidDataException("Room type is invalid!");
        }
        if (!RoomUtils.isValidCapacity(capacity)){
            logger.log(Level.SEVERE, "Room capacity is invalid: {0} (should be between 1 and 6)", capacity);
            throw new InvalidDataException("Room capacity is invalid!");
        }
        if (!RoomUtils.isValidPrice(price)){
            logger.log(Level.SEVERE, "Room price is invalid: {0} (should be >= 0)", price);
            throw new InvalidDataException("Room price is invalid!");
        }
        return new Room(roomNumber, type, capacity, price, roomStatus);
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        if (!RoomUtils.isValidRoomNumber(roomNumber)){
            logger.log(Level.SEVERE, String.format("Can't set the room number, because it is invalid: '%d' (should be >= 1)", roomNumber));
            throw new InvalidDataException("Room number is invalid!");
        }
        logger.log(Level.FINE, "Room number was set successfully!");
        this.roomNumber = roomNumber;
    }
    public String getType() {
        if (type != null) return type;
        else return "No room type provided";
    }
    public void setType(String type) {
        if (!RoomUtils.isValidRoomType(type)){
            logger.log(Level.SEVERE, String.format("Can't set the room type, because it is invalid: '%s'", type));
            throw new InvalidDataException("Room type is invalid!");
        }
        logger.log(Level.FINE, "Room type was set successfully!");
        this.type = type;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        if (!RoomUtils.isValidCapacity(capacity)){
            logger.log(Level.SEVERE, String.format("Can't set the room capacity, because it is invalid: '%d' (should be between 1 and 6)", capacity));
            throw new InvalidDataException("Room capacity is invalid!");
        }
        logger.log(Level.FINE, "Capacity was set successfully!");
        this.capacity = capacity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (!RoomUtils.isValidPrice(price)){
            logger.log(Level.SEVERE, String.format("Can't set the room price, because it is invalid: '%f' (should be >= 0)", price));
            throw new InvalidDataException("Room price is invalid!");
        }
        logger.log(Level.FINE, "Price was set successfully!");
        this.price = price;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }
    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomSize(){
        if (!RoomUtils.isValidCapacity(capacity)){
            logger.log(Level.WARNING, String.format("Capacity is invalid, so can't get room size: '%d' (capacity should be between 1 and 6)", capacity));
            return "unknown capacity";
        }
        return switch (capacity){
            case 1, 2, 3 -> "small";
            default -> "big";
        };
    }

    @Override
    public int compareTo(Room room){
        return getRoomNumber() - room.getRoomNumber();
    }

    @Override
    public String toString() {
        return "Room {room number: " + roomNumber + ", type: " + type + ", capacity: " + capacity + ", price: " + price + ", room status: " + RoomUtils.formatRoomStatus(roomStatus) + "}";
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