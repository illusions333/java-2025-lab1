package ua.university.model;

import org.assertj.core.api.SoftAssertions;
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
import ua.university.fileReaders.ServiceFileReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {
    @TempDir
    Path tempDir;
    private Path testFile;

    @BeforeEach
    void setUp() {
        testFile = tempDir.resolve("services.csv");
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        static Stream<Arguments> validDataForConstructor() {
            return Stream.of(
                    Arguments.of("\t\nCleaning   ", 500, "Cleaning", 500),
                    Arguments.of("Fixing the fridge", 1250, "Fixing the fridge", 1250)
            );
        }

        @ParameterizedTest
        @MethodSource("validDataForConstructor")
        @DisplayName("Constructor with correct values")
        void testWithCorrectValues(String nameEntry, double priceEntry, String nameExpected, double priceExpected) {
            Service service = new Service(nameEntry, priceEntry);
            assertAll(
                    () -> assertEquals(nameExpected, service.getName()),
                    () -> assertEquals(priceExpected, service.getPrice())
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"   ", "", "\n"})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidFirstName(String invalidName) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Service(invalidName, 150);
            });
            assertTrue(exception.getMessage().contains("Invalid service name"));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-140.5, -0.001, -3})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidLastName(double invalidPrice) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Service("Cleaning", invalidPrice);
            });
            assertTrue(exception.getMessage().contains("Invalid service price"));
        }
    }

    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should format toString correctly with all fields")
        void testToStringWithAllFields() {
            Service service = new Service("Fixing the TV", 400);
            String expectedString = "Service {name: Fixing the TV, price: 400.0}";

            assertEquals(expectedString, service.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, service.toString()));
        }
    }

    @Nested
    @DisplayName("equals and hashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Service service = new Service("Cleaning", 500);

            assertTrue(service.equals(service), "Service should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to person with same data")
        void testEqualsSymmetric() {
            Service service1 = new Service("Cleaning", 500);
            Service service2 = new Service("Cleaning", 500);

            assertTrue(service2.equals(service1),
                    "Services with same data should be equal");
            assertTrue(service1.equals(service2),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Service service = new Service("Cleaning", 500);

            assertFalse(service.equals(null), "Service should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Service service = new Service("Cleaning", 500);
            String notAService = "Not a service";

            assertFalse(service.equals(notAService), "Service should not be equal to different class");
        }

        @ParameterizedTest
        @MethodSource("differentServicesProvider")
        @DisplayName("Should not be equal to service with different data")
        void testNotEqualsWithDifferentData(Service service1, Service service2, String reason) {
            assertFalse(service1.equals(service2),
                    () -> String.format("Services should not be equal: %s", reason));
        }

        @Test
        @DisplayName("Equal services should have same hashCode")
        void testHashCodeConsistency() {
            Service service1 = new Service("Cleaning", 500);
            Service service2 = new Service("Cleaning", 500);

            assertTrue(service1.equals(service2), "Services should be equal");
            assertEquals(service1.hashCode(), service2.hashCode(),
                    "Equal services should have same hashCode");
        }

        static Stream<Arguments> differentServicesProvider() {
            Service baseService = new Service("Cleaning", 500);
            return Stream.of(
                    Arguments.of(baseService, new Service("Fixing", 500), "different service name"),
                    Arguments.of(baseService, new Service("Cleaning", 5000), "different price")
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
    void testParseValidCSVFile(String csvContent, int expectedSize, String firstName, double firstPrice) throws IOException, InvalidDataException {
        Files.writeString(testFile, csvContent);
        List<Service> services = ServiceFileReader.readFromFile(testFile.toString());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(services).hasSize(expectedSize);
        if (expectedSize > 0) {
            softly.assertThat(services.get(0).getName()).isEqualTo(firstName);
            softly.assertThat(services.get(0).getPrice()).isEqualTo(firstPrice);
        }
        softly.assertAll();
    }

    private static Stream<Arguments> provideValidCSVTestData() {
        return Stream.of(
                Arguments.of("# Service list\nCleaning,50.0\nLaundry,30.0",2,"Cleaning",50.0),
                Arguments.of("# Available services\n\nWiFi,10.0\nBreakfast,25.0\nParking,15.0",3,"WiFi",10.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCSVTestData")
    void testParseInvalidCSVFile(String csvContent, int expectedValidServices) throws IOException {
        Files.writeString(testFile, csvContent);
        assertDoesNotThrow(() -> {
            List<Service> services = ServiceFileReader.readFromFile(testFile.toString());
            assertEquals(expectedValidServices, services.size());
        });
    }

    private static Stream<Arguments> provideInvalidCSVTestData() {
        return Stream.of(
                Arguments.of("Cleaning,50.0\nInvalid Line\nLaundry,30.0",2),
                Arguments.of("WiFi,ten\nBreakfast,25.0",1),
                Arguments.of("Cleaning,\nLaundry,30.0",1),
                Arguments.of("WiFi,10.0,ExtraField\nParking,15.0",1),
                Arguments.of("OnlyName\nBreakfast,25.0",1)
        );
    }

}
