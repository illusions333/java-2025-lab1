package ua.university.model;

import ua.university.util.ServiceUtils;
import java.util.Objects;

public class Service {
    private String name;
    private double price;

    public Service() {}
    public Service(String name, double price) {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        if (name != null) return name;
        return "No valid name provided";
    }
    public void setName(String name) {
        if (ServiceUtils.isValidServiceName(name)) this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (ServiceUtils.isValidPrice(price)) this.price = price;
    }

    public static Service createService(String name, double price) {
        if (ServiceUtils.isValidPrice(price) && ServiceUtils.isValidServiceName(name))
            return new Service(name, price);
        return null;
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
        Service reservation = (Service) obj;
        return Objects.equals(name, reservation.name) && Objects.equals(price, reservation.price);
    }
}
