package ua.university.model;

import ua.university.exception.InvalidDataException;
import ua.university.utils.ServiceUtils;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public record Service (String name, double price)
{
    private static final Logger logger = Logger.getLogger(Service.class.getName());
    public Service {
        if (!ServiceUtils.isValidServiceName(name)){
            logger.log(Level.SEVERE, String.format("Invalid service name: '%s'", name));
            throw new InvalidDataException("Invalid service name: " + name);
        }
        if (!ServiceUtils.isValidPrice(price)){
            logger.log(Level.SEVERE, String.format("Invalid service price: '%f' (should be >= 0)", price));
            throw new InvalidDataException("Invalid service price: " + price);
        }

        if (name != null) {
            name = name.trim();
        }

        logger.log(Level.FINE, "Service was created successfully!");
    }

    public String getName() {
        if (name != null) return name;
        return "No valid name provided";
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Service {name: " + name + ", price: " + price + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Service service = (Service) obj;
        return Objects.equals(name, service.name) && Objects.equals(price, service.price);
    }
}