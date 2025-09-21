package ua.university.model;

import ua.university.util.GuestUtils;
import ua.university.util.PersonUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Guest extends Person {
    private LocalDate checkInDate;

    public Guest() {
        super();
    }

    public Guest(String firstName, String lastName, String email, LocalDate checkInDate) {
        super(firstName, lastName, email);
        setCheckInDate(checkInDate);
    }

    public static Guest createGuest(String firstName, String lastName, String email, LocalDate checkInDate) {
        if (PersonUtils.isValidName(firstName) && PersonUtils.isValidName(lastName)
                && PersonUtils.isValidEmail(email)) {
            return new Guest(firstName, lastName, email, checkInDate);
        }
        return null;
    }

    public static Guest createGuest(String firstName, String lastName, LocalDate checkInDate) {
        if (PersonUtils.isValidName(firstName) && PersonUtils.isValidName(lastName)) {
            String email = PersonUtils.generateEmailFromNames(firstName, lastName);
            return new Guest(firstName, lastName, email, checkInDate);
        }
        return null;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        if (GuestUtils.isValidCheckInDate(checkInDate)) this.checkInDate = checkInDate;
    }

    @Override
    public String getFullName() {
        return super.getFullName() + " " + GuestUtils.dateFormat(checkInDate);
    }

    @Override
    public String toString() {
        return "Guest {firstName: " + firstName + ", lastName: " + lastName +
                ", email: " + email + ", checkInDate: " +
                GuestUtils.dateFormat(checkInDate) + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), checkInDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Guest guest = (Guest) obj;
        return Objects.equals(checkInDate, guest.checkInDate);
    }
}
