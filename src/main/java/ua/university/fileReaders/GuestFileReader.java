package ua.university.fileReaders;

import ua.university.exception.InvalidDataException;
import ua.university.model.Guest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuestFileReader {
    private static final Logger logger = Logger.getLogger(GuestFileReader.class.getName());

    // CSV file reader
    public static List<Guest> readFromFile(String fileName) throws InvalidDataException {
        List<Guest> guests = new ArrayList<>();
        try {
            logger.log(Level.INFO, "Starting to parse guests from file: {0}", fileName);

            List<String> lines = Files.readAllLines(Path.of(fileName));

            for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
                String line = lines.get(lineNumber).trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {
                    Guest guest = parseGuestFromLine(line, lineNumber + 1);
                    guests.add(guest);
                    logger.log(Level.FINE, "Successfully parsed guest from line {0}: {1}",
                            new Object[]{lineNumber + 1, guest.getFullName()});

                } catch (InvalidDataException e) {
                    logger.log(Level.WARNING, "Failed to parse line {0}: {1}",
                            new Object[]{lineNumber + 1, e.getMessage()});
                }
            }

            logger.log(Level.INFO, "Successfully parsed {0} guests from file", guests.size());
            return guests;

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
    private static Guest parseGuestFromLine(String line, int lineNumber) throws InvalidDataException {
        String[] parts = line.split(",");

        if (parts.length != 4) {
            throw new InvalidDataException(
                    "Line " + lineNumber + ": Expected format 'firstName,lastName,email,checkInDate' but got: " + line);
        }

        String firstName = parts[0].trim();
        String lastName = parts[1].trim();
        String email = parts[2].trim();
        try {
            LocalDate checkInDate = LocalDate.parse(parts[3].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return new Guest(firstName, lastName, email, checkInDate);
        } catch (DateTimeParseException e) {
            throw new InvalidDataException("Error parsing date in line " + lineNumber + ": " + parts[3].trim() + ", expected format 'dd-MM-yyyy'");
        }
    }
}
