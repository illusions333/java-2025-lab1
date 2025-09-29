package ua.university.model;

import ua.university.util.GuestUtils;
import ua.university.util.PersonUtils;

import java.time.LocalDate;
import java.util.Objects;

public record Guest(String firstName, String lastName, String email, LocalDate checkInDate) {
    public Guest {
        if (!PersonUtils.isValidName(firstName)){
            System.out.println("\u001B[31m!ATTENTION!\u001B[0m Invalid first name: " + firstName);
        }
        if (!PersonUtils.isValidName(lastName)){
            System.out.println("\u001B[31m!ATTENTION!\u001B[0m Invalid last name: " + lastName);
        }
        if (!PersonUtils.isValidEmail(email)){
            System.out.println("\u001B[31m!ATTENTION!\u001B[0m Invalid email: " + email);
        }
        if (!GuestUtils.isValidCheckInDate(checkInDate)){
            System.out.println("\u001B[31m!ATTENTION!\u001B[0m Invalid check-in date: " + checkInDate);
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

        boolean hasCriticalErrors = !PersonUtils.isValidName(firstName) ||
                !PersonUtils.isValidName(lastName) ||
                !PersonUtils.isValidEmail(email) ||
                !GuestUtils.isValidCheckInDate(checkInDate);

        if (hasCriticalErrors) {
            System.out.println("\u001B[33m!WARNING!\u001B[0m Guest was created with error!");
        }
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
        return Objects.equals(checkInDate, guest.checkInDate);
    }
}