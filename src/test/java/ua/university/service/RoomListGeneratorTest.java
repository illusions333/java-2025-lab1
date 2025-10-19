package ua.university.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.university.model.Room;
import ua.university.model.RoomStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoomListGeneratorTest {
    @Nested
    @DisplayName("Access modifiers tests")
    class MethodAccessModifiersTests {
        @Test
        @DisplayName("formatRoomStatus method should be private")
        void testFormatRoomStatusIsPrivate() throws NoSuchMethodException {
            Method method = RoomListGenerator.class.getDeclaredMethod("formatRoomStatus", Room.class);
            assertTrue(Modifier.isPrivate(method.getModifiers()), method.getName() + " should be private");
        }

        @Test
        @DisplayName("generateRoomList method should be public")
        void testGenerateRoomListIsPublic() throws NoSuchMethodException {
            Method method = RoomListGenerator.class.getDeclaredMethod("generateRoomList", Room[].class);
            assertTrue(Modifier.isPublic(method.getModifiers()), method.getName() + " should be public");
        }

        @Test
        @DisplayName("All methods should be static")
        void testMethodsAreStatic() {
            Method[] methods = RoomListGenerator.class.getDeclaredMethods();
            for (Method method : methods) {
                assertTrue(Modifier.isStatic(method.getModifiers()), method.getName() + " should be static");
            }
        }
    }
    @Nested
    @DisplayName("Method results testing")
    class MethodResultsTests{

        static Stream<Arguments> provideRoomData() {
            return Stream.of(
                    Arguments.of(new Room(121, "presidential", 4, 125, RoomStatus.CLEANING), "cleaning"),
                    Arguments.of(new Room(121, "presidential", 4, 125, RoomStatus.MAINTENANCE), "maintenance"),
                    Arguments.of(new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED), "occupied"),
                    Arguments.of(new Room(121, "presidential", 4, 125, RoomStatus.AVAILABLE), "available"),
                    Arguments.of(new Room(121, "presidential", 4, 125, null), "unknown")
            );
        }

        @ParameterizedTest
        @MethodSource("provideRoomData")
        @DisplayName("formatRoomStatus test")
        void testFormatRoomStatusTest(Room room, String result) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method method = RoomListGenerator.class.getDeclaredMethod("formatRoomStatus", Room.class);
            method.setAccessible(true);
            Object testResult = method.invoke(null, room);
            assertEquals(result, testResult);
        }

        @Nested
        @DisplayName("generateRoomList tests")
        class GenerateRoomListTests{
            @Test
            @DisplayName("Null array test")
            void testNullArray() {
                assertEquals(RoomListGenerator.generateRoomList(null), "");
            }

            @Test
            @DisplayName("Empty array test")
            void testEmptyArray() {
                assertEquals(RoomListGenerator.generateRoomList(new Room[0]), "");
            }

            @Test
            @DisplayName("Should return the room list")
            void testGenerateRoomList() {
                assertEquals(RoomListGenerator.generateRoomList(new Room[]{
                        new Room(121, "presidential", 4, 125, RoomStatus.CLEANING),
                        new Room(121, "presidential", 4, 125, RoomStatus.MAINTENANCE),
                        new Room(121, "presidential", 4, 125, RoomStatus.OCCUPIED),
                        new Room(121, "presidential", 4, 125, RoomStatus.AVAILABLE),
                        new Room(121, "presidential", 4, 125, null),
                }), "Room #121: size - big, capacity - 4, room status - cleaning, price - 125.000000\n" +
                        "Room #121: size - big, capacity - 4, room status - maintenance, price - 125.000000\n" +
                        "Room #121: size - big, capacity - 4, room status - occupied, price - 125.000000\n" +
                        "Room #121: size - big, capacity - 4, room status - available, price - 125.000000\n" +
                        "Room #121: size - big, capacity - 4, room status - unknown, price - 125.000000\n");
            }

            @Test
            @DisplayName("Should return 'null' in the result when room is null")
            void testShouldReturnNullInTheResultWhenRoomIsNull() {
                assertEquals(RoomListGenerator.generateRoomList(new Room[] {null, new Room(121, "presidential", 4, 125, RoomStatus.CLEANING)}), "null\n" +
                        "Room #121: size - big, capacity - 4, room status - cleaning, price - 125.000000\n");
            }
        }
    }
}