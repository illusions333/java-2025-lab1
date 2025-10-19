package ua.university;

import ua.university.exception.InvalidDataException;
import ua.university.model.*;
import ua.university.service.RoomListGenerator;
import ua.university.service.TotalAmountGenerator;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //configuring logger to show logs of level fine and info
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.FINE);

        for (Handler handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.FINE);
        }
        //parsing was tested in the unit-tests, so I don't repeat myself here
        Person person1 = new Person("Oleksandr", "Dubchak", "dubchak.oleksandr@chnu.edu.ua");
        try {
            Person person2 = new Person("Artem", "Shkrebko", "notValidMail");
            System.out.println(person2);
            System.out.println(person2.getEmail());
        } catch (InvalidDataException e){
            System.out.println("Exception was caught");
        } //email is not valid, so it throws InvalidDataException
        System.out.println(person1);

//        System.out.println(person1.getFullName()); getFullName is protected, so we cannot access it through Main
//        System.out.println(person1.firstName); firstName is also protected, so it can't be accessed either

        Person person3 = new Person();
        System.out.println(person3);

        Person person4 = new Person("Oleksandr", "Dubchak", "dubchak.oleksandr@chnu.edu.ua");
        System.out.println(person4.equals(person1));
        System.out.println(person4.equals(person1)); //checking for consistency
        System.out.println(person1.equals(person4)); //checking for commutativity

        Person person5 = Person.createPerson("Oleksandr", "Dubchak");
        System.out.println(person5);


        System.out.println();


        Room room1 = Room.createRoom(121, "Presidential", 5, 1000, RoomStatus.CLEANING);
        System.out.println(room1);
        try {
            Room room2 = new Room(15, "", 200, -1, RoomStatus.MAINTENANCE);
            System.out.println(room2);
        }
        catch (InvalidDataException e){
            System.out.println("Exception was caught");
        }

        Room room3 = new Room(15, "deluxe", 5, 6, RoomStatus.MAINTENANCE);
//        System.out.println(room1.roomNumber) room number is a private field, which can't be accessed outside the class


        System.out.println();


        Service service1 = new Service("Cleaning", 500);
        System.out.println(service1);
        Service service2 = new Service("Fixing the toilet", 1000);
        System.out.println(service2);
        Service service3 = new Service("Fixing the fridge", 1500);
        System.out.println(service3);
        Service service4 = new Service("Providing an AC", 1000);
        System.out.println(service4);


        System.out.println();


        Guest guest = new Guest(person1, LocalDate.of(2025, 8, 11));
        System.out.println(guest);
        System.out.println();


        System.out.println();


        Reservation reservation1 = new Reservation(guest, room1, LocalDate.of(2025, 8, 11), LocalDate.of(2025, 8, 21), ReservationStatus.CONFIRMED);
        System.out.println(reservation1);

        Reservation reservation2 = Reservation.createReservation(guest, room1, LocalDate.of(2025, 8, 11), LocalDate.of(2025, 8, 21), ReservationStatus.CONFIRMED);
        System.out.println(reservation2);

        System.out.println(reservation1.equals(reservation2));
        System.out.println(reservation1.getDuration());


        System.out.println();


        Invoice invoice1 = new Invoice(reservation1, LocalDate.of(2025, 8, 21), 2500);
        System.out.println(invoice1);
        Invoice invoice2 = Invoice.createInvoice(reservation2, LocalDate.of(2025, 8, 21), 2500);
        System.out.println(invoice1.equals(invoice2));
        System.out.println(invoice1.getDailySpending());
/*        boolean notValidBool = ValidationHelper.isValidDate(guest.getCheckInDate()); ValidationHelper is a package-private class and can't be accessed from Main
          other utils are public, that's why we can access them if we import those classes
 */
        System.out.println();
        System.out.println(RoomListGenerator.generateRoomList(new Room[]{room1, room3}));
        System.out.println();
        System.out.println(TotalAmountGenerator.generateTotalAmount(reservation1, new Service[]{service1, service2, service3, service4}));
    }
}