package ua.university.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceUtilsTest {
    @Nested
    @DisplayName("Access modifiers tests")
    class AccessModifiersTests {
        @Test
        @DisplayName("All methods should be public static")
        void testAccessModifiers() {
            Method[] methods = RoomUtils.class.getDeclaredMethods();
            for (Method method : methods) {
                assertTrue(Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()), method.getName() + " should be public static");
            }
        }
        @Test
        @DisplayName("Constructor should be private")
        void testConstructorIsPrivate() throws NoSuchMethodException {
            Constructor<RoomUtils> constructor = RoomUtils.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        }

        @Test
        @DisplayName("Constructor should be accessible via reflection")
        void testConstructorAccessibleViaReflection() throws Exception {
            Constructor<RoomUtils> constructor = RoomUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            RoomUtils instance = constructor.newInstance();
            assertNotNull(instance);
        }
    }
}
