package ua.university.fileReaders;

import ua.university.exception.InvalidDataException;
import ua.university.model.Room;
import ua.university.model.RoomStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomFileReader {
    private static final Logger logger = Logger.getLogger(RoomFileReader.class.getName());

    // CSV file reader
    public static List<Room> readFromFile(String fileName) throws InvalidDataException {
        List<Room> rooms = new ArrayList<>();
        try {
            logger.log(Level.INFO, "Starting to parse rooms from file: {0}", fileName);

            List<String> lines = Files.readAllLines(Path.of(fileName));

            for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
                String line = lines.get(lineNumber).trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {
                    Room room = parseRoomFromLine(line, lineNumber + 1);
                    rooms.add(room);
                    logger.log(Level.FINE, "Successfully parsed room from line {0}: {1}",
                            new Object[]{lineNumber + 1, room.getRoomNumber()});

                } catch (InvalidDataException e) {
                    logger.log(Level.WARNING, "Failed to parse line {0}: {1}",
                            new Object[]{lineNumber + 1, e.getMessage()});
                }
            }

            logger.log(Level.INFO, "Successfully parsed {0} rooms from file", rooms.size());
            return rooms;

        } catch (NoSuchFileException e) {
            String errorMsg = "File not found: " + fileName;
            logger.log(Level.SEVERE, errorMsg, e);
            throw new InvalidDataException(errorMsg, e);

        } catch (IOException e) {
            String errorMsg = "Error reading file: " + fileName;
            logger.log(Level.SEVERE, errorMsg, e);
            throw new InvalidDataException(errorMsg, e);

        } catch (SecurityException e) {
            String errorMsg = "Access denied to file: " + fileName;
            logger.log(Level.SEVERE, errorMsg, e);
            throw new InvalidDataException(errorMsg, e);
        }
    }
    private static Room parseRoomFromLine(String line, int lineNumber) throws InvalidDataException {
        String[] parts = line.split(",");

        if (parts.length != 5) {
            throw new InvalidDataException(
                    "Line " + lineNumber + ": Expected format 'roomNumber,type,capacity,price,roomStatus' but got: " + line);
        }

        String type = parts[1].trim();

        try {
            int roomNumber = Integer.parseInt(parts[0].trim());
            int capacity = Integer.parseInt(parts[2].trim());
            double price = Double.parseDouble(parts[3].trim());
            RoomStatus roomStatus = RoomStatus.valueOf(parts[4].trim());
            return new Room(roomNumber, type, capacity, price, roomStatus);
        } catch (NumberFormatException e) {
            throw new InvalidDataException(
                    "Line " + lineNumber + ": Invalid number format in the line", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidDataException(
                    "Line " + lineNumber + ": Invalid room status: " + parts[4], e);
        }
    }
}
