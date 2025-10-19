package ua.university.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.university.exception.InvalidDataException;
import ua.university.fileReaders.GuestFileReader;
import ua.university.fileReaders.RoomFileReader;
import org.assertj.core.api.SoftAssertions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GuestTest {
    @TempDir
    Path tempDir;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("guests.csv");
    }
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        static Stream<Arguments> validDataForConstructor() {
            return Stream.of(
                    Arguments.of("   Oleksandr\t", "Dubchak", "od@gmail.com", LocalDate.now().minusDays(1), "Oleksandr", "Dubchak", "od@gmail.com", LocalDate.now().minusDays(1)),
                    Arguments.of("Artem", "Shkrebko", "as@gmail.com", LocalDate.now().minusDays(3), "Artem", "Shkrebko", "as@gmail.com", LocalDate.now().minusDays(3))
            );
        }

        @ParameterizedTest
        @MethodSource("validDataForConstructor")
        @DisplayName("Constructor with correct values")
        void testWithCorrectValues(String firstNameEntry, String lastNameEntry, String emailEntry, LocalDate checkInDateEntry,
                                   String firstNameExpected, String lastNameExpected, String emailExpected, LocalDate checkInDateExpected) {
            Guest guest = new Guest(firstNameEntry, lastNameEntry, emailEntry, checkInDateEntry);
            assertAll(
                    () -> assertEquals(firstNameExpected, guest.getFirstName()),
                    () -> assertEquals(lastNameExpected, guest.getLastName()),
                    () -> assertEquals(emailExpected, guest.getEmail()),
                    () -> assertEquals(checkInDateExpected, guest.getCheckInDate())
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"   ", "", "\n"})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidFirstName(String invalidFirstName) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Guest(invalidFirstName, "Dubchak", "od@gmail.com", LocalDate.now());
            });
            assertTrue(exception.getMessage().contains("Invalid first name"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"   ", "", "\n"})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidLastName(String invalidLastName) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Guest("Oleksandr", invalidLastName, "od@gmail.com", LocalDate.now());
            });
            assertTrue(exception.getMessage().contains("Invalid last name"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"invalid email", " ", "@"})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidEmail(String invalidEmail) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Guest("Oleksandr", "Dubchak", invalidEmail, LocalDate.now());
            });
            assertTrue(exception.getMessage().contains("Invalid email"));
        }

        static Stream<Arguments> checkInDateTesting(){
            return Stream.of(
                    Arguments.of(LocalDate.of(2023, 8, 24)),
                    Arguments.of(LocalDate.of(2025, 11, 12))
            );
        }

        @ParameterizedTest
        @MethodSource("checkInDateTesting")
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidCheckInDate(LocalDate invalidCheckInDate) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Guest("Oleksandr", "Dubchak", "od@gmail.com", invalidCheckInDate);
            });
            assertTrue(exception.getMessage().contains("Invalid check-in date"));
        }
    }

    @Nested
    @DisplayName("getFullName Tests")
    class GetFullNameTests {

        @Test
        @DisplayName("Should format full name with both names set")
        void testGetFullNameWithBothNames() throws Exception {
            Guest guest = new Guest("John     ", "Doe", "john.doe@gmail.com", LocalDate.now());

            Method getFullNameMethod = Guest.class.getDeclaredMethod("getFullName");
            getFullNameMethod.setAccessible(true);
            String actualFullName = (String) getFullNameMethod.invoke(guest);

            assertEquals("John Doe 19-10-2025", actualFullName,
                    () -> String.format("Expected full name to be '%s' but was '%s'", "John Doe", actualFullName));
        }
    }

    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should format toString correctly with all fields")
        void testToStringWithAllFields() {
            Guest guest = new Guest("John     ", "Doe", "john.doe@gmail.com", LocalDate.now());
            String expectedString = "Guest {firstName: John, lastName: Doe, email: john.doe@gmail.com, checkInDate: 19-10-2025}";

            assertEquals(expectedString, guest.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, guest.toString()));
        }
    }

    @Nested
    @DisplayName("equals and hashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Guest guest = new Guest("John", "Doe", "john.doe@gmail.com", LocalDate.now());

            assertTrue(guest.equals(guest), "Guest should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to guest with same data")
        void testEqualsSymmetric() {
            Guest guest1 = new Guest("John     ", "Doe", "john.doe@gmail.com", LocalDate.now());
            Guest guest2 = new Guest("John     ", "Doe", "john.doe@gmail.com", LocalDate.now());

            assertTrue(guest1.equals(guest2),
                    "Guests with same data should be equal");
            assertTrue(guest2.equals(guest1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Guest guest = new Guest("John     ", "Doe", "john.doe@gmail.com", LocalDate.now());

            assertFalse(guest.equals(null), "Guest should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Guest guest = new Guest("John     ", "Doe", "john.doe@gmail.com", LocalDate.now());
            String notAGuest = "Not a guest";

            assertFalse(guest.equals(notAGuest), "Guest should not be equal to different class");
        }

        @ParameterizedTest
        @MethodSource("differentGuestsProvider")
        @DisplayName("Should not be equal to guest with different data")
        void testNotEqualsWithDifferentData(Guest guest1, Guest guest2, String reason) {
            assertFalse(guest1.equals(guest2),
                    () -> String.format("Guests should not be equal: %s", reason));
        }

        @Test
        @DisplayName("Equal guests should have same hashCode")
        void testHashCodeConsistency() {
            Guest guest1 = new Guest("John     ", "Doe", "john.doe@gmail.com", LocalDate.now());
            Guest guest2 = new Guest("John     ", "Doe", "john.doe@gmail.com", LocalDate.now());

            assertTrue(guest1.equals(guest2), "Guests should be equal");
            assertEquals(guest1.hashCode(), guest2.hashCode(),
                    "Equal guests should have same hashCode");
        }

        static Stream<Arguments> differentGuestsProvider() {
            Guest baseGuest = new Guest("John", "Doe", "john.doe@gmail.com", LocalDate.now());
            return Stream.of(
                    Arguments.of(baseGuest, new Guest("Johnny", "Doe", "john.doe@gmail.com", LocalDate.now()), "different firstName"),
                    Arguments.of(baseGuest, new Guest("John", "Dove", "john.doe@gmail.com", LocalDate.now()), "different lastName"),
                    Arguments.of(baseGuest, new Guest("John", "Doe", "john.dove@gmail.com", LocalDate.now()), "different email"),
                    Arguments.of(baseGuest, new Guest("John", "Doe", "john.doe@gmail.com", LocalDate.now().minusDays(1)), "different checkInDate")
            );
        }
    }

    @Test
    void testFileNotFound() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            GuestFileReader.readFromFile("nonexistent.csv");
        });
        assertTrue(exception.getMessage().contains("File not found"), exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideValidCSVTestData")
    void testParseValidCSVFile(String csvContent, int expectedSize, String firstFirstName, String firstLastName, String firstEmail) throws IOException, InvalidDataException {
        Files.writeString(testFile, csvContent);
        List<Guest> guests = GuestFileReader.readFromFile(testFile.toString());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(guests).hasSize(expectedSize);
        if (expectedSize > 0) {
            softly.assertThat(guests.get(0).getFirstName()).isEqualTo(firstFirstName);
            softly.assertThat(guests.get(0).getLastName()).isEqualTo(firstLastName);
            softly.assertThat(guests.get(0).getEmail()).isEqualTo(firstEmail);
        }
        softly.assertAll();
    }
    private static Stream<Arguments> provideValidCSVTestData() {
        return Stream.of(
                Arguments.of(
                        "# Guest list for October\nJohn,Doe,john.doe@example.com,01-10-2025\nJane,Smith,jane.smith@example.com,03-10-2025",
                        2, "John", "Doe", "john.doe@example.com"
                ),

                Arguments.of(
                        "# Checked-in guests\n\nAlex,Johnson,alex.johnson@example.com,28-09-2025\n\nMaria,Lopez,maria.lopez@example.com,02-10-2025\nRobert,Brown,robert.brown@example.com,05-10-2025",
                        3, "Alex", "Johnson", "alex.johnson@example.com"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCSVTestData")
    void testParseInvalidCSVFile(String csvContent, int expectedValidGuests) throws IOException {
        Files.writeString(testFile, csvContent);
        assertDoesNotThrow(() -> {
            List<Guest> guests = GuestFileReader.readFromFile(testFile.toString());
            assertEquals(expectedValidGuests, guests.size());
        });
    }

    private static Stream<Arguments> provideInvalidCSVTestData() {
        return Stream.of(
                Arguments.of("John,Doe,john.doe@example.com,01-10-2025\nInvalid Line\nJane,Smith,jane.smith@example.com,03-10-2025",2),
                Arguments.of("Alex,Johnson,,28-09-2025\nMaria,Lopez,maria.lopez@example.com,02-10-2025",1),
                Arguments.of("John,Doe,john.doe@example.com,not-a-date\nJane,Smith,jane.smith@example.com,03-10-2025",1),
                Arguments.of("John,Doe,john.doe@example.com\nRobert,Brown,robert.brown@example.com,05-10-2025",1),
                Arguments.of("John,Doe,john.doe@example.com,01-10-2025,ExtraField\nJane,Smith,jane.smith@example.com,03-10-2025",1)
        );
    }
}
