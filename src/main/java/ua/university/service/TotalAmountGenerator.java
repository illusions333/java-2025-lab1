package ua.university.service;

import ua.university.model.Reservation;
import ua.university.model.Service;

public class TotalAmountGenerator {
    public static String generateTotalAmount(Reservation reservation, Service[] services) {
        double total;
        StringBuilder totalAmount = new StringBuilder();
        if (reservation == null || reservation.getRoom() == null) {
            total = 0;
            totalAmount.append("Room price - unknown\n");
        }
        else {
            total = reservation.getRoom().getPrice();
            totalAmount.append(String.format("Room price - %f\n", total));
        }

        if (services != null && services.length > 0) {
            for (Service service : services) {
                if (service != null) {
                    total += service.getPrice();
                    totalAmount.append(String.format("Service - service name: %s, price: %f\n", service.getName(), service.getPrice()));
                }
            }
        }
        totalAmount.append(String.format("Total amount - %f\n", total));
        return totalAmount.toString();
    }
}
