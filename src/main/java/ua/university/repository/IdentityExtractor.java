package ua.university.repository;

@FunctionalInterface
interface IdentityExtractor<T> {
    String extractIdentity(T object);
}
