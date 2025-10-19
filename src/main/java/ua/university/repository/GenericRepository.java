package ua.university.repository;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;

class GenericRepository<T> {
    private static final Logger logger = Logger.getLogger(GenericRepository.class.getName());

    private final List<T> items;
    private final IdentityExtractor<T> identityExtractor;
    private final String entityType;

    public GenericRepository(IdentityExtractor<T> identityExtractor, String entityType) {
        this.items = new ArrayList<>();
        this.identityExtractor = identityExtractor;
        this.entityType = entityType;
        logger.log(Level.INFO, "Created repository for " + entityType);
    }

    public boolean add(T item) {
        if (item == null) {
            logger.log(Level.WARNING, "Attempted to add null " + entityType);
            return false;
        }

        String identity = identityExtractor.extractIdentity(item);
        if (findByIdentity(identity).isPresent()) {
            logger.log(Level.WARNING, "Cannot add " + entityType + " - already exists with identity: " + identity);
            return false;
        }

        boolean added = items.add(item);
        if (added) {
            logger.log(Level.INFO, "Added " + entityType + ": " + identity);
        }
        return added;
    }

    public boolean remove(T item) {
        if (item == null) {
            logger.log(Level.WARNING, "Attempted to remove null " + entityType);
            return false;
        }

        boolean removed = items.remove(item); // Uses equals() internally
        if (removed) {
            logger.log(Level.INFO, "Removed " + entityType + ": " + identityExtractor.extractIdentity(item));
        } else {
            logger.log(Level.WARNING, "Failed to remove " + entityType + ": " + identityExtractor.extractIdentity(item));
        }
        return removed;
    }

    public boolean removeByIdentity(String identity) {
        if (identity == null) {
            logger.log(Level.WARNING, "Attempted to remove " + entityType + " with null identity");
            return false;
        }

        Optional<T> itemToRemove = items.stream()
                .filter(item -> identity.equals(identityExtractor.extractIdentity(item)))
                .findFirst();

        if (itemToRemove.isPresent()) {
            boolean removed = items.remove(itemToRemove.get());
            if (removed) {
                logger.log(Level.INFO, "Removed " + entityType + " by identity: " + identity);
            }
            return removed;
        } else {
            logger.log(Level.WARNING, "No " + entityType + " found with identity: " + identity + " to remove");
            return false;
        }
    }

    public boolean contains(T item) {
        return items.contains(item); // Uses equals() internally
    }

    public boolean containsIdentity(String identity) {
        return findByIdentity(identity).isPresent();
    }

    public Optional<T> findByIdentity(String identity) {
        if (identity == null) {
            logger.log(Level.WARNING, "Attempted to find " + entityType + " with null identity");
            return Optional.empty();
        }

        Optional<T> result = items.stream()
                .filter(item -> identity.equals(identityExtractor.extractIdentity(item)))
                .findFirst();

        if (result.isPresent()) {
            logger.log(Level.INFO, "Found " + entityType + " with identity: " + identity);
        } else {
            logger.log(Level.INFO, "No " + entityType + " found with identity: " + identity);
        }

        return result;
    }

    public List<T> getAll() {
        logger.log(Level.INFO, "Retrieved all " + entityType + " items. Count: " + items.size());
        return new ArrayList<>(items);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        int sizeBefore = items.size();
        items.clear();
        logger.log(Level.INFO, "Cleared repository. Removed " + sizeBefore + " " + entityType + " items");
    }

    List<T> getItemsForTesting() {
        return items;
    }
}