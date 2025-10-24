package ua.university.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ua.university.model.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Service Repository Tests")
public class ServiceGenericRepositoryTest {
    private GenericRepository<Service> serviceRepository;
    private Service service1, service2, service3, service4, service5;

    @BeforeAll
    void setUpData() {
        service1 = new Service("Cleaning", 500);
        service2 = new Service("Breakfast", 1000);
        service3 = new Service("Change the light bulb", 700);
        service4 = new Service("Provide a spare towel", 900);
        service5 = new Service("Fix the wardrobe", 500);
    }

    @BeforeEach
    void setUpRepository() {
        serviceRepository = new GenericRepository<>(Service::getName, "Service");
        serviceRepository.getItemsForTesting().add(service1);
    }

    @Nested
    @DisplayName("Adding to repository tests")
    class AddToRepositoryTests {
        @Test
        @DisplayName("Adding valid services")
        void addValidService() {
            SoftAssertions softly = new SoftAssertions();

            int initialSize = serviceRepository.size();

            boolean added = serviceRepository.add(service2);

            softly.assertThat(added)
                    .as("Should successfully add service %s", service2.getName())
                    .isTrue();

            added = serviceRepository.add(service3);

            softly.assertThat(added)
                    .as("Should successfully add service %s", service3.getName())
                    .isTrue();

            softly.assertThat(serviceRepository.size())
                    .as("Repository size should increase by 2")
                    .isEqualTo(initialSize + 2);

            softly.assertAll();
        }
        @DisplayName("Test getting existing service service1")
        @Test
        void testFoundService() {
            SoftAssertions softly = new SoftAssertions();

            String expectedIdentity = service1.getName();

            Optional<Service> found = serviceRepository.findByIdentity(expectedIdentity);
            softly.assertThat(found.isPresent())
                    .as("Should find added service by identity %s", expectedIdentity)
                    .isTrue();

            if (found.isPresent()) {
                Service foundService = found.get();
                softly.assertThat(foundService.getPrice())
                        .as("Service price should match")
                        .isEqualTo(service1.getPrice());
            }

            softly.assertAll();
        }

        @Test
        @DisplayName("Test duplicate prevention")
        void testDuplicatePrevention() {
            SoftAssertions softly = new SoftAssertions();

            Service duplicateIdService = new Service("Cleaning", 10000);
            boolean duplicateIdAdd = serviceRepository.add(duplicateIdService);
            softly.assertThat(duplicateIdAdd)
                    .as("Adding service with duplicate ID should fail")
                    .isFalse();

            softly.assertThat(serviceRepository.size())
                    .as("Repository should contain only one service")
                    .isEqualTo(1);

            softly.assertAll();
        }

        @Test
        @DisplayName("Test null adding prevention")
        void testNullPrevention() {
            SoftAssertions softly = new SoftAssertions();

            boolean added = serviceRepository.add(null);
            softly.assertThat(added)
                    .as("Add of the null service should fail")
                    .isFalse();
        }
    }
    static Stream<Arguments> serviceIdsProvider() {
        return Stream.of(
                Arguments.of("Cleaning", true, "Valid service ID"),
                Arguments.of("Change the light bulb", true, "Another valid service ID"),
                Arguments.of("Fix the wardrobe", false, "Non-existent service ID"),
                Arguments.of("", false, "Empty string ID"),
                Arguments.of(null, false, "Null ID")
        );
    }


    @ParameterizedTest(name = "Find by ID: {0} (should find: {1}) - {2}")
    @MethodSource("serviceIdsProvider")
    @DisplayName("Test finding services by ID")
    void testFindByIdentity(String serviceId, boolean shouldFind, String description) {
        SoftAssertions softly = new SoftAssertions();

        serviceRepository.getItemsForTesting().add(service2);
        serviceRepository.getItemsForTesting().add(service3);

        Optional<Service> result = serviceRepository.findByIdentity(serviceId);

        softly.assertThat(result.isPresent())
                .as("Find result for %s should be %s", description, shouldFind ? "present" : "absent")
                .isEqualTo(shouldFind);

        if (shouldFind && result.isPresent()) {
            softly.assertThat(result.get().getName())
                    .as("Found service should have correct ID")
                    .isEqualTo(serviceId);
        }

        softly.assertAll();
    }

    @Test
    @DisplayName("Test getAll operation")
    void testGetAllServices() {
        SoftAssertions softly = new SoftAssertions();

        GenericRepository<Service> emptyRepository = new GenericRepository<>(Service::getName, "Service");
        List<Service> emptyList = emptyRepository.getAll();
        softly.assertThat(emptyList)
                .as("Initially should return empty list")
                .isEmpty();


        serviceRepository.getItemsForTesting().add(service2);
        serviceRepository.getItemsForTesting().add(service3);
        serviceRepository.getItemsForTesting().add(service4);
        serviceRepository.getItemsForTesting().add(service5);

        List<Service> allServices = serviceRepository.getAll();

        softly.assertThat(allServices)
                .as("Should return all added services")
                .hasSize(5)
                .contains(service1, service2, service3, service4, service5);

        allServices.clear();

        softly.assertThat(serviceRepository.size())
                .as("Repository size should not be affected by external list modification")
                .isEqualTo(5);

        softly.assertAll();
    }

    @Test
    @DisplayName("Test removing services by identity")
    void testRemoveByIdentity() {
        SoftAssertions softly = new SoftAssertions();
        int initialSize = serviceRepository.size();

        boolean removed = serviceRepository.removeByIdentity(service1.getName());

        softly.assertThat(removed)
                .as("Should successfully remove service %s", service1.getName())
                .isTrue();

        softly.assertThat(serviceRepository.size())
                .as("Repository size should decrease by 1")
                .isEqualTo(initialSize - 1);

        softly.assertAll();
    }

    @Test
    @DisplayName("Test removing non-existent service")
    void testRemoveNonExistentService() {
        SoftAssertions softly = new SoftAssertions();

        int initialSize = serviceRepository.size();

        boolean removed = serviceRepository.removeByIdentity("Fixing the wardrobe");

        softly.assertThat(removed)
                .as("Should not remove non-existent service")
                .isFalse();

        softly.assertThat(serviceRepository.size())
                .as("Repository size should remain unchanged")
                .isEqualTo(initialSize);

        softly.assertAll();
    }

    @Test
    @DisplayName("Test removing with null identity")
    void testRemoveNullIdentity() {
        SoftAssertions softly = new SoftAssertions();

        int initialSize = serviceRepository.size();

        boolean removed = serviceRepository.removeByIdentity(null);

        softly.assertThat(removed)
                .as("Should not remove with null identity")
                .isFalse();

        softly.assertThat(serviceRepository.size())
                .as("Repository size should remain unchanged")
                .isEqualTo(initialSize);

        softly.assertAll();
    }

    @Test
    @DisplayName("Test clear operation")
    void testClearRepository() {
        SoftAssertions softly = new SoftAssertions();

        serviceRepository.add(service2);
        serviceRepository.add(service3);
        serviceRepository.add(service4);
        serviceRepository.add(service5);

        softly.assertThat(serviceRepository.size())
                .as("Should have 5 services before clear")
                .isEqualTo(5);

        serviceRepository.clear();

        softly.assertThat(serviceRepository.size())
                .as("Repository size should be 0 after clear")
                .isEqualTo(0);

        softly.assertThat(serviceRepository.isEmpty())
                .as("Repository should be empty after clear")
                .isTrue();

        softly.assertThat(serviceRepository.getAll())
                .as("GetAll should return empty list after clear")
                .isEmpty();

        softly.assertAll();
    }
}
