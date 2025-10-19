package ua.university.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.university.model.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationUtilsTest {
    @Nested
    @DisplayName("Access modifiers tests")
    class AccessModifiersTests {
        @Test
        @DisplayName("All methods should be public static")
        void testAccessModifiers() {
            Method[] methods = ReservationUtils.class.getDeclaredMethods();
            for (Method method : methods) {
                assertTrue(Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()), method.getName() + " should be public static");
            }
        }
        @Test
        @DisplayName("Constructor should be private")
        void testConstructorIsPrivate() throws NoSuchMethodException {
            Constructor<ReservationUtils> constructor = ReservationUtils.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        }

        @Test
        @DisplayName("Constructor should be accessible via reflection")
        void testConstructorAccessibleViaReflection() throws Exception {
            Constructor<ReservationUtils> constructor = ReservationUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            ReservationUtils instance = constructor.newInstance();
            assertNotNull(instance);
        }
    }
    @Nested
    @DisplayName("formatReservationStatus method testing")
    class ReservationStatusFormatTests {
        static Stream<Arguments> dataTesting(){
            return Stream.of(
                    Arguments.of(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                        new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                        LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_IN), "checked in"),
                    Arguments.of(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                        new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                        LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CHECKED_OUT), "checked out"),
                    Arguments.of(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                            new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                            LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CANCELED), "canceled"),
                    Arguments.of(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                            new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                            LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), ReservationStatus.CONFIRMED), "confirmed"),
                    Arguments.of(new Reservation(new Guest("O", "D", "od@gmail.com", LocalDate.now().minusDays(1)),
                            new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                            LocalDate.now().minusDays(4), LocalDate.now().plusDays(15), null), "unknown")
            );
        }
        @ParameterizedTest
        @MethodSource("dataTesting")
        @DisplayName("Check formatting reservation status")
        void testFormatReservationStatus(Reservation reservation, String expected) {
            assertEquals(ReservationUtils.formatReservationStatus(reservation.getReservationStatus()), expected);
        }
    }
    @Nested
    @DisplayName("dateFormat method testing")
    class DateFormatTests {
        @Test
        @DisplayName("Empty output when date is null")
        void testEmptyWhenDateIsNull() {
            assertEquals(ReservationUtils.dateFormat(null), "");
        }

        @Test
        @DisplayName("Test for correct output")
        void testForCorrectOutput() {
            assertEquals(ReservationUtils.dateFormat(LocalDate.of(2025, 8, 11)), "11-08-2025");
        }
    }

    @Nested
    @DisplayName("isValidEndDate method testing")
    class IsValidEndDateTests {
        @Test
        @DisplayName("Testing when startDate or endDate is null")
        void testWhenDateIsNull() {
            assertAll("Null date testing",
                    () -> assertFalse(ReservationUtils.isValidEndDate(LocalDate.now(), null)),
                    () -> assertFalse(ReservationUtils.isValidEndDate(null, LocalDate.of(2025, 8, 11))),
                    () -> assertFalse(ReservationUtils.isValidEndDate(null, null))
            );
        }

        @Test
        @DisplayName("Testing when startDate is false")
        void testWhenStartDateIsFalse() {
            assertAll(
                    () -> assertFalse(ReservationUtils.isValidEndDate(LocalDate.now().minusYears(2), LocalDate.now())),
                    () -> assertFalse(ReservationUtils.isValidEndDate(LocalDate.now().minusYears(1).minusMonths(1), LocalDate.now())),
                    () -> assertFalse(ReservationUtils.isValidEndDate(LocalDate.now().minusYears(2).minusMonths(1), LocalDate.now().minusYears(2).minusMonths(1).minusDays(2)))
            );
        }

        @Test
        @DisplayName("Testing when endDate is false")
        void testWhenEndDateIsFalse() {
            assertAll(
                    () -> assertFalse(ReservationUtils.isValidEndDate(LocalDate.now().minusMonths(5), LocalDate.now())),
                    () -> assertFalse(ReservationUtils.isValidEndDate(LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(5).minusDays(1)))
            );
        }
        @Test
        @DisplayName("Testing with correct data")
        void testWithCorrectData() {
            assertAll(
                    () -> assertTrue(ReservationUtils.isValidEndDate(LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(4).minusDays(1))),
                    () -> assertTrue(ReservationUtils.isValidEndDate(LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(5)))
            );
        }
    }
}
