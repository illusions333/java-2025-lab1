package ua.university.repository;

import ua.university.model.Room;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class RoomRepository extends GenericRepository<Room> {
    private static final Logger logger = Logger.getLogger(RoomRepository.class.getName());
    public RoomRepository() {
        super(room -> String.valueOf(room.getRoomNumber()), "Room");
    }

    public List<Room> sortByType() {
        List<Room> rooms = getAll();
        rooms.sort((r1, r2) -> {
            if (r1.getType().equals(r2.getType())) {
                return r1.getRoomNumber() - r2.getRoomNumber();
            }
            else return r1.getType().compareTo(r2.getType());
        });
        logger.log(Level.INFO, "Sorted {} by type, roomNumber in ascending order", "Room");
        return rooms;
    }

    public List<Room> sortByTypeDesc() {
        List<Room> rooms = getAll();
        rooms.sort((r1, r2) -> {
            if (r1.getType().equals(r2.getType())) {
                return r1.getRoomNumber() - r2.getRoomNumber();
            }
            else return r2.getType().compareTo(r1.getType());
        });
        logger.log(Level.INFO, "Sorted {} by type in descending order, roomNumber in ascending order", "Room");
        return rooms;
    }

    public List<Room> sortByCapacity() {
        List<Room> rooms = getAll();
        rooms.sort((r1, r2) -> {
            if (r1.getCapacity() == r2.getCapacity()) {
                return r1.getRoomNumber() - r2.getRoomNumber();
            }
            else return r1.getCapacity() - r2.getCapacity();
        });
        logger.log(Level.INFO, "Sorted {} by capacity, roomNumber in ascending order", "Room");
        return rooms;
    }

    public List<Room> sortByCapacityDesc() {
        List<Room> rooms = getAll();
        rooms.sort((r1, r2) -> {
            if (r1.getCapacity() == r2.getCapacity()) {
                return r1.getRoomNumber() - r2.getRoomNumber();
            }
            else return r2.getCapacity() - r1.getCapacity();
        });
        logger.log(Level.INFO, "Sorted {} by capacity in descending order, roomNumber in ascending order", "Room");
        return rooms;
    }

    public List<Room> sortByPrice() {
        List<Room> rooms = getAll();
        rooms.sort((r1, r2) -> {
            if (r1.getPrice() == r2.getPrice()) {
                return r1.getRoomNumber() - r2.getRoomNumber();
            }
            else return Double.compare(r1.getPrice(), r2.getPrice());
        });
        logger.log(Level.INFO, "Sorted {} by price, roomNumber in ascending order", "Room");
        return rooms;
    }

    public List<Room> sortByPriceDesc() {
        List<Room> rooms = getAll();
        rooms.sort((r1, r2) -> {
            if (r1.getPrice() == r2.getPrice()) {
                return r1.getRoomNumber() - r2.getRoomNumber();
            }
            else return Double.compare(r2.getPrice(), r1.getPrice());
        });
        logger.log(Level.INFO, "Sorted {} by price in descending order, roomNumber in ascending order", "Room");
        return rooms;
    }
}
