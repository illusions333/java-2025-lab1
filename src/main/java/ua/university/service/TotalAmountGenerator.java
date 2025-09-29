package ua.university.service;

import ua.university.model.Reservation;
import ua.university.model.Service;

public class TotalAmountGenerator {
    public static String generateTotalAmount(Reservation reservation, Service[] services) {
        double total = reservation.getRoom().getPrice();
        StringBuilder totalAmount = new StringBuilder();
        totalAmount.append(String.format("Room price - %f%n", reservation.getRoom().getPrice()));
        for (Service service : services) {
            total += service.getPrice();
            totalAmount.append(String.format("Service - service name: %s, price: %f%n", service.getName(), service.getPrice()));
        }
        totalAmount.append(String.format("Total amount - %f%n", total));
        return totalAmount.toString();
    }
}
