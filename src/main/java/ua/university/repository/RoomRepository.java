package ua.university.repository;

import ua.university.model.Room;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public List<Room> findByType(String desiredType){
        if (desiredType == null || desiredType.isEmpty()) return Collections.emptyList();
        List<Room> desiredRooms = getAll().stream().filter(r -> r.getType().equalsIgnoreCase(desiredType)).
                collect(Collectors.toList());
        logger.log(Level.INFO, "Found " + desiredRooms.size() + " rooms with the desired type " + desiredType.toLowerCase());
        return desiredRooms;
    }

    public List<Room> findByCapacityBetweenTwoCapacities(int minCapacity, int maxCapacity){
        if (minCapacity > 6 || maxCapacity < 1 || minCapacity > maxCapacity) return Collections.emptyList();
        List<Room> desiredRooms = getAll().stream()
                .filter(r -> r.getCapacity() >= minCapacity && r.getCapacity() <= maxCapacity)
                .collect(Collectors.toList());
        logger.log(Level.INFO, "Found " + desiredRooms.size() + " rooms with the desired capacity between " + minCapacity + " and " + maxCapacity);
        return desiredRooms;
    }

    public List<Room> increasePriceDueInflation(float inflation) {
        if (inflation <= 0 || inflation > 1)
            return Collections.emptyList();
        List<Room> desiredRooms = getAll();
        desiredRooms.forEach(r -> r.setPrice(r.getPrice() * (1 + inflation)));
        logger.info("Increased price by " + inflation * 100 + "%.");
        return desiredRooms;
    }

    public void printAllRoomSizes(){
        getAll().stream().map(r -> "Room " + r.getRoomNumber() + " - " + r.getRoomSize()).forEach(System.out::println);
    }

    public List<Room> findRoomsThatMatchOneOfTheTypes(List<String> types){
        if (types == null || types.isEmpty()) {
            return Collections.emptyList();
        }
        List<Room> desiredRooms = types.stream().flatMap(type -> findByType(type).stream()).collect(Collectors.toList());
        logger.log(Level.INFO, "Found " + desiredRooms.size() + " rooms that match one of the provided types: " + types);
        return desiredRooms;
    }

    public int getMaxOccupancyOfHotel(){
        return getAll().stream().map(Room::getCapacity).reduce(0, Integer::sum);
    }
}
