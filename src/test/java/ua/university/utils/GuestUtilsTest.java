package ua.university.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GuestUtilsTest {
    @Nested
    @DisplayName("Access modifiers tests")
    class AccessModifiersTests {
        @Test
        @DisplayName("All methods should be public static")
        void testAccessModifiers() {
            Method[] methods = GuestUtils.class.getDeclaredMethods();
            for (Method method : methods) {
                assertTrue(Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()), method.getName() + " should be public static");
            }
        }
        @Test
        @DisplayName("Constructor should be private")
        void testConstructorIsPrivate() throws NoSuchMethodException {
            Constructor<GuestUtils> constructor = GuestUtils.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        }

        @Test
        @DisplayName("Constructor should be accessible via reflection")
        void testConstructorAccessibleViaReflection() throws Exception {
            Constructor<GuestUtils> constructor = GuestUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            GuestUtils instance = constructor.newInstance();
            assertNotNull(instance);
        }
    }

    @Nested
    @DisplayName("dateFormat method testing")
    class DateFormatTests {
        @Test
        @DisplayName("Empty output when date is null")
        void testEmptyWhenDateIsNull() {
            assertEquals(GuestUtils.dateFormat(null), "");
        }

        @Test
        @DisplayName("Test for correct output")
        void testForCorrectOutput() {
            assertEquals(GuestUtils.dateFormat(LocalDate.of(2025, 8, 11)), "11-08-2025");
        }
    }
}
