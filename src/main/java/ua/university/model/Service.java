package ua.university.model;

import ua.university.util.ServiceUtils;

import java.util.Objects;

public record Service (String name, double price)
{
    public Service {
        if (!ServiceUtils.isValidServiceName(name)){
            System.out.println("\u001B[31m!ATTENTION!\u001B[0m Invalid service name: " + name);
        }
        if (!ServiceUtils.isValidPrice(price)){
            System.out.println("\u001B[31m!ATTENTION!\u001B[0m Invalid service price: " + price);
        }

        if (name != null) {
            name = name.trim();
        }
        boolean hasCriticalErrors = !ServiceUtils.isValidServiceName(name) ||
                !ServiceUtils.isValidPrice(price);

        if (hasCriticalErrors) {
            System.out.println("\u001B[33m!WARNING!\u001B[0m Service was created with error!");
        }
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