package ua.university.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ua.university.model.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TotalAmountGeneratorTest {
    @Test
    @DisplayName("generateTotalAmount should be public static")
    void testGenerateTotalAmountAccessModifiers() throws NoSuchMethodException {
        Method method = TotalAmountGenerator.class.getDeclaredMethod("generateTotalAmount", Reservation.class, Service[].class);
        assertTrue(Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()), method.getName() + " should be public static");
    }

    @Nested
    @DisplayName("generateTotalAmount result tests")
    class GenerateTotalAmountTests {
        @Test
        @DisplayName("Testing output when reservation is null or its room is null")
        void testReservationIsNullOrItsRoomIsNull() {
            assertAll("Null reservation",
                    () -> assertEquals(TotalAmountGenerator.generateTotalAmount(null, new Service[]{new Service("Cleaning", 500)}),
                        "Room price - unknown\n" +
                        "Service - service name: Cleaning, price: 500.000000\n" +
                        "Total amount - 500.000000\n"),
                    () -> assertEquals(TotalAmountGenerator.generateTotalAmount(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now()), null, LocalDate.now(), LocalDate.now().plusDays(1), ReservationStatus.CANCELED),
                                    new Service[]{new Service("Cleaning", 500)}),
                            "Room price - unknown\n" +
                                    "Service - service name: Cleaning, price: 500.000000\n" +
                                    "Total amount - 500.000000\n"),
                    () -> assertEquals(TotalAmountGenerator.generateTotalAmount(null, null),
                            "Room price - unknown\n" +
                                    "Total amount - 0.000000\n")
            );
        }

        @Test
        @DisplayName("Testing output when service array is null or empty")
        void testServiceArrayIsNullOrEmpty() {
            assertAll("Null service array",
                    () -> assertEquals(TotalAmountGenerator.generateTotalAmount(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now()),
                            new Room(121, "presidential", 4, 125, RoomStatus.CLEANING), LocalDate.now(),
                            LocalDate.now().plusDays(1), ReservationStatus.CANCELED), null), "Room price - 125.000000\nTotal amount - 125.000000\n"),
                    () -> assertEquals(TotalAmountGenerator.generateTotalAmount(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now()),
                            new Room(121, "presidential", 4, 125, RoomStatus.CLEANING), LocalDate.now(),
                            LocalDate.now().plusDays(1), ReservationStatus.CANCELED), new Service[0]), "Room price - 125.000000\nTotal amount - 125.000000\n")
            );
        }

        @Test
        @DisplayName("Testing output when service array is null or empty")
        void testServiceIsNull() {
            assertEquals(TotalAmountGenerator.generateTotalAmount(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now()),
                    new Room(121, "presidential", 4, 125, RoomStatus.CLEANING), LocalDate.now(),
                    LocalDate.now().plusDays(1), ReservationStatus.CANCELED), new Service[]{null, new Service("Cleaning", 500)}),
                    "Room price - 125.000000\nService - service name: Cleaning, price: 500.000000\nTotal amount - 625.000000\n");

        }
        @Test
        @DisplayName("Testing output with correct data")
        void testGeneratorWithCorrectData() {
            assertEquals(TotalAmountGenerator.generateTotalAmount(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now()),
                            new Room(121, "presidential", 4, 125, RoomStatus.CLEANING), LocalDate.now(),
                            LocalDate.now().plusDays(1), ReservationStatus.CANCELED), new Service[]{
                                    new Service("Cleaning", 500),
                                    new Service("Fixing the fridge", 1000),
                                    new Service("Fixing the floor", 1500)
                    }),
                    "Room price - 125.000000\nService - service name: Cleaning, price: 500.000000\n" +
                            "Service - service name: Fixing the fridge, price: 1000.000000\n" +
                            "Service - service name: Fixing the floor, price: 1500.000000\n" +
                            "Total amount - 3125.000000\n");
        }
    }
}
