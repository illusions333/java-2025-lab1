package ua.university.fileReaders;

import ua.university.exception.InvalidDataException;
import ua.university.model.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceFileReader {
    private static final Logger logger = Logger.getLogger(ServiceFileReader.class.getName());

    // CSV file reader
    public static List<Service> readFromFile(String fileName) throws InvalidDataException {
        List<Service> services = new ArrayList<>();
        try {
            logger.log(Level.INFO, "Starting to parse services from file: {0}", fileName);

            List<String> lines = Files.readAllLines(Path.of(fileName));

            for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
                String line = lines.get(lineNumber).trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {
                    Service service = parseServiceFromLine(line, lineNumber + 1);
                    services.add(service);
                    logger.log(Level.FINE, "Successfully parsed service from line {0}: {1}",
                            new Object[]{lineNumber + 1, service.name()});

                } catch (InvalidDataException e) {
                    logger.log(Level.WARNING, "Failed to parse line {0}: {1}",
                            new Object[]{lineNumber + 1, e.getMessage()});
                }
            }

            logger.log(Level.INFO, "Successfully parsed {0} services from file", services.size());
            return services;

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
    private static Service parseServiceFromLine(String line, int lineNumber) throws InvalidDataException {
        String[] parts = line.split(",");

        if (parts.length != 2) {
            throw new InvalidDataException(
                    "Line " + lineNumber + ": Expected format 'name,price' but got: " + line);
        }

        String name = parts[0].trim();

        try {
            double price = Double.parseDouble(parts[1].trim());
            return new Service(name, price);
        } catch (NumberFormatException e) {
            throw new InvalidDataException(
                    "Line " + lineNumber + ": Invalid price format: " + parts[1], e);
        }
    }
}
