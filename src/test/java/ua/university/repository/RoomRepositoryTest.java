package ua.university.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import ua.university.model.Room;
import ua.university.model.RoomStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoomRepositoryTest {
    private static final Logger logger = Logger.getLogger(RoomRepositoryTest.class.getName());
    private RoomRepository roomRepository;
    private Room room1, room2, room3, room4, room5;

    @BeforeAll
    void setUp(){
        logger.log(Level.INFO, "Setting up data");
        room1 = new Room(121, "Deluxe", 4, 2250, RoomStatus.AVAILABLE);
        room2 = new Room(21, "Presidential", 2, 5000, RoomStatus.AVAILABLE);
        room3 = new Room(122, "Deluxe", 5, 2500, RoomStatus.AVAILABLE);
        room4 = new Room(12, "SinglePresidential", 1, 2500, RoomStatus.AVAILABLE);
        room5 = new Room(13, "Standard", 5, 2250, RoomStatus.AVAILABLE);
    }

    @BeforeEach
    void setUpRepository() {
        logger.log(Level.INFO, "Setting up repository");
        roomRepository = new RoomRepository();
        roomRepository.add(room1);
        roomRepository.add(room2);
        roomRepository.add(room3);
        roomRepository.add(room4);
        roomRepository.add(room5);
        logger.log(Level.INFO, "Repository has been set correctly");
    }

    @Nested
    @DisplayName("sortByIdentity tests")
    class testSortByIdentity {
        @Test
        @DisplayName("sortByIdentity in ascending order testing")
        public void testSortByIdentityAsc() {
            logger.log(Level.INFO, "Sorting by identity (room number)");
            roomRepository.sortByIdentity(true);
            List<Room> sortedRooms = roomRepository.getAll();

            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #12")
                    .isEqualTo(12);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #122")
                    .isEqualTo(122);


            softly.assertAll();
            logger.info("sortByIdentity test completed successfully");
        }

        @Test
        @DisplayName("sortByIdentity in descending order testing")
        public void testSortByIdentityDesc() {
            logger.log(Level.INFO, "Sorting by identity (room number)");
            roomRepository.sortByIdentity(false);
            List<Room> sortedRooms = roomRepository.getAll();

            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #122")
                    .isEqualTo(122);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #12")
                    .isEqualTo(12);


            softly.assertAll();
            logger.info("sortByIdentity test completed successfully");
        }
    }
    @Nested
    @DisplayName("sortByType tests")
    class testSortByType {
        @Test
        @DisplayName("sortByType in ascending order testing")
        public void testSortByTypeAsc() {
            logger.log(Level.INFO, "Sorting by type");
            List<Room> sortedRooms = roomRepository.sortByType();

            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #122")
                    .isEqualTo(122);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #12")
                    .isEqualTo(12);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #13")
                    .isEqualTo(13);


            softly.assertAll();
            logger.info("sortByType test completed successfully");
        }

        @Test
        @DisplayName("sortByType in descending order testing")
        public void testSortByTypeDesc() {
            logger.log(Level.INFO, "Sorting by identity (room number)");
            List<Room> sortedRooms = roomRepository.sortByTypeDesc();

            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #12")
                    .isEqualTo(12);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #122")
                    .isEqualTo(122);


            softly.assertAll();
            logger.info("sortByType test completed successfully");
        }
    }
    @Nested
    @DisplayName("sortByCapacity tests")
    class testSortByCapacity {
        @Test
        @DisplayName("sortByCapacity in ascending order testing")
        public void testSortByCapacityAsc() {
            logger.log(Level.INFO, "Sorting by capacity");
            List<Room> sortedRooms = roomRepository.sortByCapacity();

            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #12")
                    .isEqualTo(12);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #122")
                    .isEqualTo(122);


            softly.assertAll();
            logger.info("sortByCapacity test completed successfully");
        }

        @Test
        @DisplayName("sortByCapacity in descending order testing")
        public void testSortByCapacityDesc() {
            logger.log(Level.INFO, "Sorting by capacity");
            List<Room> sortedRooms = roomRepository.sortByCapacityDesc();

            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #122")
                    .isEqualTo(122);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #12")
                    .isEqualTo(12);


            softly.assertAll();
            logger.info("sortByCapacity test completed successfully");
        }
    }
    @Nested
    @DisplayName("sortByPrice tests")
    class testSortByPrice {
        @Test
        @DisplayName("sortByPrice in ascending order testing")
        public void testSortByPriceAsc() {
            logger.log(Level.INFO, "Sorting by price");
            List<Room> sortedRooms = roomRepository.sortByPrice();

            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #12")
                    .isEqualTo(12);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #122")
                    .isEqualTo(122);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #21")
                    .isEqualTo(21);


            softly.assertAll();
            logger.info("sortByPrice test completed successfully");
        }

        @Test
        @DisplayName("sortByPrice in descending order testing")
        public void testSortByPriceDesc() {
            logger.log(Level.INFO, "Sorting by price");
            List<Room> sortedRooms = roomRepository.sortByPriceDesc();

            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #12")
                    .isEqualTo(12);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #122")
                    .isEqualTo(122);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #121")
                    .isEqualTo(121);


            softly.assertAll();
            logger.info("sortByPrice test completed successfully");
        }
    }
    @Nested
    @DisplayName("Comparable interface testing")
    class testComparable{
        @Test
        @DisplayName("Testing Comparable<Room> interface for ascending order")
        public void testComparableAsc(){
            logger.log(Level.INFO, "Comparable interface testing");
            List<Room> sortedRooms = roomRepository.getAll();
            Collections.sort(sortedRooms);
            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #12")
                    .isEqualTo(12);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #122")
                    .isEqualTo(122);


            softly.assertAll();
            logger.info("Comparable interface test completed successfully");

        }
        @Test
        @DisplayName("Testing Comparable<Room> interface for descending order")
        public void testComparableDesc(){
            logger.log(Level.INFO, "Comparable interface testing");
            List<Room> sortedRooms = roomRepository.getAll();
            Collections.sort(sortedRooms, Collections.reverseOrder());
            assertThat(sortedRooms).as("Should return all rooms").hasSize(5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(sortedRooms.get(0).getRoomNumber())
                    .as("First room should be #122")
                    .isEqualTo(122);

            softly.assertThat(sortedRooms.get(1).getRoomNumber())
                    .as("Second room should be #121")
                    .isEqualTo(121);

            softly.assertThat(sortedRooms.get(2).getRoomNumber())
                    .as("Third room should be #21")
                    .isEqualTo(21);

            softly.assertThat(sortedRooms.get(3).getRoomNumber())
                    .as("Fourth room should be #13")
                    .isEqualTo(13);

            softly.assertThat(sortedRooms.get(4).getRoomNumber())
                    .as("Fifth room should be #12")
                    .isEqualTo(12);


            softly.assertAll();
            logger.info("Comparable interface test completed successfully");
        }
    }

    @Nested
    @DisplayName("Stream methods testing")
    class testStream{
        @Test
        @DisplayName("findByType method testing")
        public void testFindByType(){
            logger.log(Level.INFO, "Finding by type");
            List<Room> desiredRooms = roomRepository.findByType("deluxe");
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(desiredRooms).as("Should return 2 rooms").hasSize(2);
            softly.assertThat(desiredRooms.get(0).getRoomNumber()).isEqualTo(121);
            softly.assertThat(desiredRooms.get(1).getRoomNumber()).isEqualTo(122);
            softly.assertAll();
            logger.log(Level.INFO, "Finding by type test completed successfully");
        }
        @Test
        @DisplayName("findByCapacityBetweenTwoCapacities method testing")
        public void testFindByCapacityBetweenTwoCapacities(){
            logger.log(Level.INFO, "Finding by capacity between two capacities");
            List<Room> desiredRooms = roomRepository.findByCapacityBetweenTwoCapacities(4, 5);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(desiredRooms).as("Should return 3 rooms").hasSize(3);
            softly.assertThat(desiredRooms.get(0).getRoomNumber()).isEqualTo(121);
            softly.assertThat(desiredRooms.get(1).getRoomNumber()).isEqualTo(122);
            softly.assertThat(desiredRooms.get(2).getRoomNumber()).isEqualTo(13);
            softly.assertAll();
            logger.log(Level.INFO, "Finding by capacity between two capacities test completed successfully");
        }
        @Test
        @DisplayName("increasePriceDueInflation method testing")
        public void testIncreasePriceDueInflation(){
            logger.log(Level.INFO, "Increase price due inflation");
            List<Room> desiredRooms = roomRepository.increasePriceDueInflation(0.5f);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(desiredRooms).as("Should return 5 rooms").hasSize(5);
            softly.assertThat(desiredRooms.get(0).getPrice()).isEqualTo(3375);
            softly.assertThat(desiredRooms.get(1).getPrice()).isEqualTo(7500);
            softly.assertThat(desiredRooms.get(2).getPrice()).isEqualTo(3750);
            softly.assertThat(desiredRooms.get(3).getPrice()).isEqualTo(3750);
            softly.assertThat(desiredRooms.get(4).getPrice()).isEqualTo(3375);
            softly.assertAll();
            logger.log(Level.INFO, "Increase price due inflation test completed successfully");
        }

        @Test
        @DisplayName("findRoomsThatMatchesOneOfTheTypes method testing")
        public void testFindRoomsThatMatchOneOfTheTypes(){
            logger.log(Level.INFO, "Finding rooms that match one of the types method testing");
            List<String> types = List.of("Deluxe", "Presidential");
            List<Room> desiredRooms = roomRepository.findRoomsThatMatchOneOfTheTypes(types);
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(desiredRooms).as("Should return 3 rooms").hasSize(3);
            softly.assertThat(desiredRooms.get(0).getRoomNumber()).isEqualTo(121);
            softly.assertThat(desiredRooms.get(1).getRoomNumber()).isEqualTo(122);
            softly.assertThat(desiredRooms.get(2).getRoomNumber()).isEqualTo(21);
            softly.assertAll();
            logger.log(Level.INFO, "Finding rooms that match one of the types method testing finished completing");
        }

        @Test
        @DisplayName("getMaxOccupancy method testing")
        public void testGetMaxOccupancy(){
            logger.log(Level.INFO, "Get max occupancy method testing");
            int result = roomRepository.getMaxOccupancyOfHotel();
            assertEquals(result, 17);
        }
    }
}
