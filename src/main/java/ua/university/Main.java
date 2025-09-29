package ua.university;

import ua.university.model.*;
import ua.university.service.RoomListGenerator;
import ua.university.service.TotalAmountGenerator;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Oleksandr", "Dubchak", "dubchak.oleksandr@chnu.edu.ua");
        Person person2 = new Person("Artem", "Shkrebko", "notValidMail"); //email is not valid, so it would be replaced by null
        System.out.println(person1);
        System.out.println(person2);

//        System.out.println(person1.getFullName()); getFullName is protected, so we cannot access it through Main
//        System.out.println(person1.firstName); firstName is also protected, so it can't be accessed either

        System.out.println(person2.getEmail());

        Person person3 = new Person();
        System.out.println(person3);

        Person person4 = new Person("Oleksandr", "Dubchak", "dubchak.oleksandr@chnu.edu.ua");
        System.out.println(person4.equals(person1));
        System.out.println(person4.equals(person1)); //checking for consistency
        System.out.println(person1.equals(person4)); //checking for commutativity
        System.out.println(person4.equals(person2));

        Person person5 = Person.createPerson("Oleksandr", "Dubchak");
        System.out.println(person5);


        System.out.println();


        Room room1 = new Room();
        System.out.println(room1);
        Room room2 = Room.createRoom(121, "Presidential", 5, 1000, RoomStatus.CLEANING);
        System.out.println(room2);
        Room room3 = new Room(15, "", 200, -1, RoomStatus.MAINTENANCE);
        /*capacity can't be more than 6, so it would show 0 instead
        price can't also be negative, so 0 would be shown*/
        System.out.println(room3);
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


        Guest guest1 = new Guest(person1, LocalDate.of(2025, 8, 11));
        System.out.println(guest1);
        System.out.println();
        Guest guest2 = new Guest(person2, LocalDate.of(2025, 8, 11));
        System.out.println(guest2);


        System.out.println();


        Reservation reservation1 = new Reservation(guest1, room2, LocalDate.of(2025, 8, 11), LocalDate.of(2025, 8, 21), ReservationStatus.CONFIRMED);
        System.out.println(reservation1);

        Reservation reservation2 = Reservation.createReservation(guest1, room2, LocalDate.of(2025, 8, 11), LocalDate.of(2025, 8, 21), ReservationStatus.CONFIRMED);
        System.out.println(reservation2);

        Reservation reservation3 = new Reservation();
        System.out.println(reservation3);

        System.out.println(reservation1.equals(reservation2));
        System.out.println(reservation1.getDuration());


        System.out.println();


        Invoice invoice1 = new Invoice(reservation1, LocalDate.of(2025, 8, 21), 2500);
        System.out.println(invoice1);
        Invoice invoice2 = Invoice.createInvoice(reservation2, LocalDate.of(2025, 8, 21), 2500);
        System.out.println(invoice1.equals(invoice2));
        System.out.println(invoice1.getDailySpending());
/*        boolean notValidBool = ValidationHelper.isValidDate(guest1.getCheckInDate()); ValidationHelper is a package-private class and can't be accessed from Main
          other utils are public, that's why we can access them if we import those classes
 */
        System.out.println();
        System.out.println(RoomListGenerator.generateRoomList(new Room[]{room1, room2, room3}));
        System.out.println();
        System.out.println(TotalAmountGenerator.generateTotalAmount(reservation1, new Service[]{service1, service2, service3, service4}));
    }
}