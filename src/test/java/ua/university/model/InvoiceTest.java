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

public class InvoiceTest {
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        static Stream<Arguments> dataForConstructor() {
            return Stream.of(
                    Arguments.of(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                                    new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(1),
                                    LocalDate.now().plusDays(3), ReservationStatus.CHECKED_IN), LocalDate.now().plusDays(1), 1250,
                                    new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                                    new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(1),
                                    LocalDate.now().plusDays(3), ReservationStatus.CHECKED_IN), LocalDate.now().plusDays(1), 1250),
                    Arguments.of(new Reservation(new Guest("Oleksandr", "Dubchak", "dubchak.oleksandr@gmail.com", LocalDate.now().plusDays(1)),
                                    new Room(121, "presidential", 4, 125, RoomStatus.AVAILABLE), LocalDate.now().plusDays(1),
                                    LocalDate.now().plusDays(7), ReservationStatus.CONFIRMED), LocalDate.now().plusDays(1), 12500,
                                    new Reservation(new Guest("Oleksandr", "Dubchak", "dubchak.oleksandr@gmail.com", LocalDate.now().plusDays(1)),
                                    new Room(121, "presidential", 4, 125, RoomStatus.AVAILABLE), LocalDate.now().plusDays(1),
                                    LocalDate.now().plusDays(7), ReservationStatus.CONFIRMED), LocalDate.now().plusDays(1), 12500)
            );
        }

        @ParameterizedTest
        @MethodSource("dataForConstructor")
        @DisplayName("Constructor with correct values")
        void testWithCorrectValues(Reservation reservationEntry, LocalDate issueDateEntry, double totalAmountEntry,
                                   Reservation reservationExpected, LocalDate issueDateExpected, double totalAmountExpected) {
            Invoice invoice = new Invoice(reservationEntry, issueDateEntry, totalAmountEntry);
            assertEquals(reservationExpected, invoice.getReservation());
            assertEquals(issueDateExpected, invoice.getIssueDate());
            assertEquals(totalAmountExpected, invoice.getTotalAmount());
        }
    }

    @Nested
    @DisplayName("Access Modifier Tests")
    class AccessModifierTests {
        @ParameterizedTest
        @ValueSource(strings = {"reservation", "issueDate", "totalAmount"})
        @DisplayName("Fields should be private")
        void testFieldsArePrivate(String fieldName) throws NoSuchFieldException {
            var field = Invoice.class.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    () -> String.format("Expected field '%s' to be private, but was: %s",
                            fieldName, Modifier.toString(field.getModifiers())));
        }

        @Test
        @DisplayName("All methods should be public")
        void testAllMethodsArePublic() {
            Method[] methods = Invoice.class.getDeclaredMethods();
            for (Method method : methods) {
                assertTrue(Modifier.isPublic(method.getModifiers()), () -> String.format("Expected method %s to be public, but was %s:",
                        method.getName(), Modifier.toString(method.getModifiers())));
            }
        }
    }

    @Nested
    @DisplayName("getDailySpending method testing")
    class DailySpendingMethodTests {
        static Stream<Arguments> methodData() {
            return Stream.of(Arguments.of(new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                    new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(4),
                    LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN), LocalDate.now().plusDays(1), 12500)),
                    Arguments.of(new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                            new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(1),
                            LocalDate.now().plusDays(3), ReservationStatus.CHECKED_IN), LocalDate.now().plusDays(1), 1250)),
                    Arguments.of(new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                            new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), LocalDate.now().minusDays(1),
                            LocalDate.now().plusDays(8), ReservationStatus.CHECKED_IN), LocalDate.now().plusDays(1), 1234)
            ));
        }


        @ParameterizedTest
        @MethodSource("methodData")
        @DisplayName("Check how method works for different data")
        void testGetDailySpending(Invoice invoice) {
            assertEquals(invoice.getDailySpending(), invoice.getTotalAmount() / invoice.getReservation().getDuration());
        }
    }

    //every other method is not checked, as they throw InvalidDataException providing incorrect data, so there is nothing to test

    @Nested
    @DisplayName("toString method testing")
    class ToStringTests{
        Invoice methodData = new Invoice(
                new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                        LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                LocalDate.now().plusDays(1), 12500);

        @Test
        @DisplayName("toString testing")
        void testToString() {
            assertEquals("Invoice {reservation: Reservation {guest: Guest {firstName: O, lastName: D, email: od@gmail.com, checkInDate: 11-10-2025}, " +
                    "\nroom: Room {room number: 121, type: presidential, capacity: 4, price: 125.0, room status: occupied}, \nstartDate: 08-10-2025, endDate: 27-10-2025, \n" +
                    "reservationStatus: checked in}, issueDate: 13-10-2025, totalAmount: 12500.0}", methodData.toString());
        }
    }

    @Nested
    @DisplayName("equals and hashCode methods testing")
    class EqualsAndHashCodeTests{
        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Invoice invoice = new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                    LocalDate.now().plusDays(1), 12500);

            assertTrue(invoice.equals(invoice), "Invoice should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to invoice with same data")
        void testEqualsSymmetric() {
            Invoice invoice1 = new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                    LocalDate.now().plusDays(1), 12500);
            Invoice invoice2 = new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                    LocalDate.now().plusDays(1), 12500);

            assertTrue(invoice1.equals(invoice2),
                    "Invoices with same data should be equal");
            assertTrue(invoice2.equals(invoice1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Invoice invoice = new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                    LocalDate.now().plusDays(1), 12500);

            assertFalse(invoice.equals(null), "Invoice should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Invoice invoice = new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                    LocalDate.now().plusDays(1), 12500);
            String notAnInvoice = "Not an invoice";

            assertFalse(invoice.equals(notAnInvoice), "Invoice should not be equal to different class");
        }

        @ParameterizedTest
        @MethodSource("differentInvoicesProvider")
        @DisplayName("Should not be equal to invoice with different data")
        void testNotEqualsWithDifferentData(Invoice invoice1, Invoice invoice2, String reason) {
            assertFalse(invoice1.equals(invoice2),
                    () -> String.format("Invoices should not be equal: %s", reason));
        }

        @Test
        @DisplayName("Equal invoices should have same hashCode")
        void testHashCodeConsistency() {
            Invoice invoice1 = new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                    LocalDate.now().plusDays(1), 12500);
            Invoice invoice2 = new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                    LocalDate.now().plusDays(1), 12500);

            assertTrue(invoice1.equals(invoice2), "Invoices should be equal");
            assertEquals(invoice1.hashCode(), invoice2.hashCode(),
                    "Equal invoices should have same hashCode");
        }

        static Stream<Arguments> differentInvoicesProvider() {
            Invoice baseInvoice = new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                    LocalDate.now().plusDays(1), 12500);
            return Stream.of(
                    Arguments.of(baseInvoice, new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                                    LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                                    LocalDate.now().plusDays(1), 12500.1), "different total amount"),
                    Arguments.of(baseInvoice, new Invoice(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                            LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                            LocalDate.now().minusDays(15), 12500), "different issue date"),
                    Arguments.of(baseInvoice, new Invoice(new Reservation(new Guest("Oleksandr", "D", "od@gmail.com", LocalDate.now().minusDays(1)), new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                            LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN),
                            LocalDate.now().plusDays(1), 12500), "different guest in the reservation")
            );
        }
    }
}
