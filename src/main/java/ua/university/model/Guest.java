package ua.university.model;

import ua.university.exception.InvalidDataException;
import ua.university.utils.GuestUtils;
import ua.university.utils.PersonUtils;

import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public record Guest(String firstName, String lastName, String email, LocalDate checkInDate) {
    private static final Logger logger = Logger.getLogger(Guest.class.getName());
    public Guest {
        if (!PersonUtils.isValidName(firstName)){
            logger.log(Level.SEVERE, String.format("Got an error while creating a guest: invalid first name - '%s'", firstName));
            throw new InvalidDataException("Invalid first name for the guest: " + firstName);
        }
        if (!PersonUtils.isValidName(lastName)){
            logger.log(Level.SEVERE, String.format("Got an error while creating a guest: invalid last name - '%s'", lastName));
            throw new InvalidDataException("Invalid last name for the guest: " + lastName);
        }
        if (!PersonUtils.isValidEmail(email)){
            logger.log(Level.SEVERE, String.format("Got an error while creating a guest: invalid email - '%s'", email));
            throw new InvalidDataException("Invalid email for the guest: " + email);
        }
        if (!GuestUtils.isValidCheckInDate(checkInDate)){
            logger.log(Level.SEVERE, String.format("Got an error while creating a guest: invalid check-in date - '%s' (should be between today and 1 year ago)", checkInDate));
            throw new InvalidDataException("Invalid check-in date for the guest: " + checkInDate + " (should be between today and 1 year ago)");
        }

        if (firstName != null){
            firstName = firstName.trim();
        }

        if (lastName != null){
            lastName = lastName.trim();
        }

        if (email != null){
            email = email.trim();
        }

        logger.log(Level.FINE, "Guest was created successfully!");
    }

    public Guest(Person person, LocalDate checkInDate){
        this(person.getFirstName(), person.getLastName(), person.getEmail(), checkInDate);
    }

    public String getFullName() {
        return firstName + " " + lastName + " " + GuestUtils.dateFormat(checkInDate);
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    public String getFirstName() {
        if (firstName != null) return firstName;
        return "No first name provided";
    }
    public String getLastName() {
        if (lastName != null) return lastName;
        return "No last name provided";
    }
    public String getEmail() {
        if (email != null) return email;
        return "No email address provided";
    }

    @Override
    public String toString() {
        return "Guest {firstName: " + firstName + ", lastName: " + lastName +
                ", email: " + email + ", checkInDate: " +
                GuestUtils.dateFormat(checkInDate) + "}";
    }

    @Override
    public int hashCode(){
        return Objects.hash(firstName, lastName, email, checkInDate);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Guest guest = (Guest) obj;
        return Objects.equals(checkInDate, guest.checkInDate) && Objects.equals(firstName, guest.firstName) &&
                Objects.equals(lastName, guest.lastName) && Objects.equals(email, guest.email);
    }
}