package ua.university.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        static Stream<Arguments> dataForConstructor() {
            return Stream.of(
                    Arguments.of(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                                    new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(1),
                                    LocalDate.now().plusDays(3), ReservationStatus.CHECKED_IN,
                            new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                                    new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(1),
                                    LocalDate.now().plusDays(3), ReservationStatus.CHECKED_IN),
                    Arguments.of(new Guest("Oleksandr", "Dubchak", "dubchak.oleksandr@gmail.com", LocalDate.now().plusDays(1)),
                                    new Room(121, "presidential", 4, 125, RoomStatus.AVAILABLE), LocalDate.now().plusDays(1),
                                    LocalDate.now().plusDays(7), ReservationStatus.CONFIRMED,
                            new Guest("Oleksandr", "Dubchak", "dubchak.oleksandr@gmail.com", LocalDate.now().plusDays(1)),
                                    new Room(121, "presidential", 4, 125, RoomStatus.AVAILABLE), LocalDate.now().plusDays(1),
                                    LocalDate.now().plusDays(7), ReservationStatus.CONFIRMED)
            );
        }

        @ParameterizedTest
        @MethodSource("dataForConstructor")
        @DisplayName("Constructor with correct values")
        void testWithCorrectValues(Guest guestEntry, Room roomEntry,  LocalDate startDateEntry, LocalDate endDateEntry, ReservationStatus reservationStatusEntry,
                                   Guest guestExpected, Room roomExpected,  LocalDate startDateExpected, LocalDate endDateExpected, ReservationStatus reservationStatusExpected) {
            Reservation reservation = new Reservation(guestEntry, roomEntry, startDateEntry, endDateEntry, reservationStatusEntry);
            assertEquals(guestExpected, reservation.getGuest());
            assertEquals(roomExpected, reservation.getRoom());
            assertEquals(startDateExpected, reservation.getStartDate());
            assertEquals(endDateExpected, reservation.getEndDate());
            assertEquals(reservationStatusExpected, reservation.getReservationStatus());
        }
    }

    @Nested
    @DisplayName("Access Modifier Tests")
    class AccessModifierTests {
        @ParameterizedTest
        @ValueSource(strings = {"guest", "room", "startDate", "endDate", "reservationStatus"})
        @DisplayName("Fields should be private")
        void testFieldsArePrivate(String fieldName) throws NoSuchFieldException {
            var field = Reservation.class.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    () -> String.format("Expected field '%s' to be private, but was: %s",
                            fieldName, Modifier.toString(field.getModifiers())));
        }

        @Test
        @DisplayName("All methods should be public")
        void testAllMethodsArePublic() {
            Method[] methods = Reservation.class.getDeclaredMethods();
            for (Method method : methods) {
                assertTrue(Modifier.isPublic(method.getModifiers()), () -> String.format("Expected method %s to be public, but was %s:",
                        method.getName(), Modifier.toString(method.getModifiers())));
            }
        }
    }

    @Nested
    @DisplayName("getDuration method testing")
    class getDurationTests {
        @Test
        @DisplayName("getDuration method testing")
        void testGetDuration() {
            assertEquals(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                    new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(1),
                    LocalDate.now().plusDays(3), ReservationStatus.CHECKED_IN).getDuration(), 4);
            assertEquals(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                    new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(4),
                    LocalDate.now().plusDays(6), ReservationStatus.CHECKED_IN).getDuration(), 10);
        }
    }

    @Nested
    @DisplayName("toString method testing")
    class ToStringTests{
        Reservation methodData = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                        LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);

        @Test
        @DisplayName("toString testing")
        void testToString() {
            assertEquals("Reservation {guest: Guest {firstName: O, lastName: D, email: od@gmail.com, checkInDate: 23-10-2025}, " +
                    "\nroom: Room {room number: 121, type: presidential, capacity: 4, price: 125.0, room status: occupied}, \nstartDate: 20-10-2025, endDate: 08-11-2025, \n" +
                    "reservationStatus: checked in}", methodData.toString());
        }
    }

    @Nested
    @DisplayName("equals and hashCode methods testing")
    class EqualsAndHashCodeTests{
        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Reservation reservation = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);

            assertTrue(reservation.equals(reservation), "Person should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to reservation with same data")
        void testEqualsSymmetric() {
            Reservation reservation1 = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);
            Reservation reservation2 = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);

            assertTrue(reservation1.equals(reservation2),
                    "Reservations with same data should be equal");
            assertTrue(reservation2.equals(reservation1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Reservation reservation = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);

            assertFalse(reservation.equals(null), "Reservation should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Reservation reservation = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);
            String notAReservation = "Not a reservation";

            assertFalse(reservation.equals(notAReservation), "Reservation should not be equal to different class");
        }

        @ParameterizedTest
        @MethodSource("differentReservationsProvider")
        @DisplayName("Should not be equal to reservation with different data")
        void testNotEqualsWithDifferentData(Reservation reservation1, Reservation reservation2, String reason) {
            assertFalse(reservation1.equals(reservation2),
                    () -> String.format("Reservations should not be equal: %s", reason));
        }

        @Test
        @DisplayName("Equal reservations should have same hashCode")
        void testHashCodeConsistency() {
            Reservation reservation1 = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);
            Reservation reservation2 = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);

            assertTrue(reservation1.equals(reservation2), "Reservations should be equal");
            assertEquals(reservation1.hashCode(), reservation2.hashCode(),
                    "Equal reservations should have same hashCode");
        }

        static Stream<Arguments> differentReservationsProvider() {
            Reservation baseReservation = new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN);
            return Stream.of(
                    Arguments.of(baseReservation, new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(122, "presidential", 4, 125, RoomStatus.OCCUPIED),
                            LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN), "different room"),
                    Arguments.of(baseReservation, new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                            LocalDate.now().minusDays(7), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN), "different start date"),
                    Arguments.of(baseReservation, new Reservation(new Guest("Oleksandr", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                            LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN), "different guest")
            );
        }
    }
}
