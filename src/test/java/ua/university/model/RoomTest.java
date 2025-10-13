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
import ua.university.fileReaders.RoomFileReader;
import org.assertj.core.api.SoftAssertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    @TempDir
    Path tempDir;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("rooms.csv");
    }
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        static Stream<Arguments> validDataForConstructor() {
            return Stream.of(
                    Arguments.of(121, "presidential", 4, 125, RoomStatus.OCCUPIED, 121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                    Arguments.of(121, "presidential", 4, 125, RoomStatus.AVAILABLE, 121, "presidential", 4, 125, RoomStatus.AVAILABLE)
            );
        }

        @ParameterizedTest
        @MethodSource("validDataForConstructor")
        @DisplayName("Constructor with correct values")
        void testWithCorrectValues(int roomNumberEntry, String typeEntry, int capacityEntry, double priceEntry, RoomStatus roomStatusEntry,
                                   int roomNumberExpected, String typeExpected, int capacityExpected, double priceExpected, RoomStatus roomStatusExpected) {
            Room room = new Room(roomNumberEntry, typeEntry, capacityEntry, priceEntry, roomStatusEntry);
            assertAll(
                    () -> assertEquals(roomNumberExpected, room.getRoomNumber()),
                    () -> assertEquals(typeExpected, room.getType()),
                    () -> assertEquals(capacityExpected, room.getCapacity()),
                    () -> assertEquals(priceExpected, room.getPrice()),
                    () -> assertEquals(roomStatusExpected, room.getRoomStatus())
            );
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, -100})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidRoomNumber(int invalidRoomNumber) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Room(invalidRoomNumber, "presidential", 4, 125, RoomStatus.OCCUPIED);
            });
            assertTrue(exception.getMessage().contains("Room number is invalid!"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "     "})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidType(String invalidType) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Room(121, invalidType, 4, 125, RoomStatus.OCCUPIED);
            });
            assertTrue(exception.getMessage().contains("Room type is invalid!"));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 7, -2})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidCapacity(int invalidCapacity) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Room(121, "presidential", invalidCapacity, 125, RoomStatus.OCCUPIED);
            });
            assertTrue(exception.getMessage().contains("Room capacity is invalid!"));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-1.5, -7.25, -2})
        @DisplayName("Testing constructor with invalid data")
        void testConstructorWithInvalidPrice(double invalidPrice) {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
                new Room(121, "presidential", 4, invalidPrice, RoomStatus.OCCUPIED);
            });
            assertTrue(exception.getMessage().contains("Room price is invalid!"));
        }
    }

    @Nested
    @DisplayName("getRoomSize method testing")
    class GetRoomSize{
        static Stream<Arguments> dataForGetRoomSize(){
            return Stream.of(
                    Arguments.of(new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), "big"),
                    Arguments.of(new Room(121, "presidential", 1, 125, RoomStatus.AVAILABLE), "small")
            );
        }

        @ParameterizedTest
        @MethodSource("dataForGetRoomSize")
        @DisplayName("Testing method getRoomSize")
        void testGetRoomSize(Room room, String type){
            assertEquals(room.getRoomSize(), type);
        }
    }


    @Test
    void testFileNotFound() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            RoomFileReader.readFromFile("nonexistent.csv");
        });
        assertTrue(exception.getMessage().contains("File not found"), exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideValidCSVTestData")
    void testParseValidCSVFile(String csvContent, int expectedSize, int firstRoomNumber, String firstType, double firstPrice) throws IOException, InvalidDataException {
        Files.writeString(testFile, csvContent);
        List<Room> rooms = RoomFileReader.readFromFile(testFile.toString());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rooms).hasSize(expectedSize);
        if (expectedSize > 0) {
            softly.assertThat(rooms.get(0).getRoomNumber()).isEqualTo(firstRoomNumber);
            softly.assertThat(rooms.get(0).getType()).isEqualTo(firstType);
            softly.assertThat(rooms.get(0).getPrice()).isEqualTo(firstPrice);
        }
        softly.assertAll();
    }
    private static Stream<Arguments> provideValidCSVTestData() {
        return Stream.of(
                    Arguments.of("# University dormitory room list\n101,Single,1,75.50,AVAILABLE\n102,Double,2,120.00,OCCUPIED", 2, 101, "Single", 75.50),
                    Arguments.of("# Format: roomNumber,type,capacity,price,roomStatus\n\n201,Deluxe,2,180.00,MAINTENANCE\n\n\n202,Family,4,300.00,OCCUPIED\n203,Suite,3,250.00,AVAILABLE", 3, 201, "Deluxe", 180.00)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCSVTestData")
    void testParseInvalidCSVFile(String csvContent, int expectedValidRooms) throws IOException {
        Files.writeString(testFile, csvContent);
        assertDoesNotThrow(() -> {
            List<Room> rooms = RoomFileReader.readFromFile(testFile.toString());
            assertEquals(expectedValidRooms, rooms.size());
        });
    }

    private static Stream<Arguments> provideInvalidCSVTestData() {
        return Stream.of(
                Arguments.of("101,Single,1,75.50,AVAILABLE\nInvalid Line\n102,Double,2,120.00,OCCUPIED", 2),
                Arguments.of("abc,Suite,3,250.00,AVAILABLE\n201,Deluxe,2,180.00,MAINTENANCE", 1),
                Arguments.of("101,Single,1,xyz,AVAILABLE\n202,Family,4,300.00,OCCUPIED", 1),
                Arguments.of("101,Double,2,120.00\n203,Suite,3,250.00,AVAILABLE", 1),
                Arguments.of("101,Single,1,75.50,INVALID_STATUS\n102,Double,2,120.00,OCCUPIED", 1)
        );
    }
    @Nested
    @DisplayName("toString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should format toString correctly with all fields")
        void testToStringWithAllFields() {
            Room room = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);
            String expectedString = "Room {room number: 121, type: presidential, capacity: 4, price: 125.0, room status: occupied}";

            assertEquals(expectedString, room.toString(),
                    () -> String.format("Expected toString to be '%s' but was '%s'", expectedString, room.toString()));
        }
    }

    @Nested
    @DisplayName("equals and hashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal to itself")
        void testEqualsReflexive() {
            Room room = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);

            assertTrue(room.equals(room), "Room should be equal to itself");
        }

        @Test
        @DisplayName("Should be equal to person with same data")
        void testEqualsSymmetric() {
            Room room1 = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);
            Room room2 = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);

            assertTrue(room1.equals(room2),
                    "Rooms with same data should be equal");
            assertTrue(room2.equals(room1),
                    "Equality should be symmetric");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void testEqualsWithNull() {
            Room room = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);

            assertFalse(room.equals(null), "Room should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void testEqualsWithDifferentClass() {
            Room room = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);
            String notARoom = "Not a room";

            assertFalse(room.equals(notARoom), "Room should not be equal to different class");
        }

        @ParameterizedTest
        @MethodSource("differentRoomsProvider")
        @DisplayName("Should not be equal to person with different data")
        void testNotEqualsWithDifferentData(Room room1, Room room2, String reason) {
            assertFalse(room1.equals(room2),
                    () -> String.format("Rooms should not be equal: %s", reason));
        }

        @Test
        @DisplayName("Equal persons should have same hashCode")
        void testHashCodeConsistency() {
            Room room1 = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);
            Room room2 = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);

            assertTrue(room1.equals(room2), "Rooms should be equal");
            assertEquals(room1.hashCode(), room2.hashCode(),
                    "Equal rooms should have same hashCode");
        }

        static Stream<Arguments> differentRoomsProvider() {
            Room baseRoom = new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED);
            return Stream.of(
                    Arguments.of(baseRoom, new Room(122, "presidential", 4, 125, RoomStatus.OCCUPIED), "different roomNumber"),
                    Arguments.of(baseRoom, new Room(121, "deluxe", 4, 125, RoomStatus.OCCUPIED), "different type"),
                    Arguments.of(baseRoom, new Room(121, "presidential", 6, 125, RoomStatus.OCCUPIED), "different capacity"),
                    Arguments.of(baseRoom, new Room(121, "presidential", 4, 1250, RoomStatus.OCCUPIED), "different price"),
                    Arguments.of(baseRoom, new Room(121, "presidential", 4, 125, RoomStatus.MAINTENANCE), "different roomStatus")
            );
        }
    }
}
