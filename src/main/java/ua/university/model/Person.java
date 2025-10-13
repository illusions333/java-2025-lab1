package ua.university.model;

import ua.university.exception.InvalidDataException;
import ua.university.utils.PersonUtils;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Person {
    protected String firstName;
    protected String lastName;
    protected String email;
    private static final Logger logger = Logger.getLogger(Person.class.getName());

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        logger.log(Level.FINE, "Person was created successfully!");
    }

    protected String getFullName() {
        return PersonUtils.formatName(firstName, lastName);
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (!PersonUtils.isValidName(firstName)) {
            logger.log(Level.SEVERE, "First name provided is invalid: {0}", firstName);
            throw new InvalidDataException("First name provided is invalid");
        }
        logger.log(Level.FINE, "First name was set successfully!");
        this.firstName = PersonUtils.capitalizeText(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (!PersonUtils.isValidName(lastName)) {
            logger.log(Level.SEVERE, "Last name provided is invalid: {0}", lastName);
            throw new InvalidDataException("Last name provided is invalid");
        }
        logger.log(Level.FINE, "Last name was set successfully!");
        this.lastName = PersonUtils.capitalizeText(lastName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            logger.log(Level.SEVERE, "Email provided is null: {0}", email);
            throw new InvalidDataException("Email provided is null!");
        }
        String formattedEmail = PersonUtils.formatEmail(email);
        if (!PersonUtils.isValidEmail(formattedEmail)) {
            logger.log(Level.SEVERE, "Email provided is invalid: {0}", email);
            throw new InvalidDataException("Email provided is invalid");
        }
        logger.log(Level.FINE, "Email was set successfully!");
        this.email = formattedEmail;
    }

    public static Person createPerson(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            logger.log(Level.SEVERE, "Can't set firstName or lastName, because they are null!");
            throw new InvalidDataException("Neither firstName nor lastName could be null");
        }
        if (!PersonUtils.isValidName(firstName)) {
            logger.log(Level.SEVERE, "First name provided is invalid: {0}", firstName);
            throw new InvalidDataException("First name of this person is invalid: " + firstName);
        }
        if (!PersonUtils.isValidName(lastName)) {
            logger.log(Level.SEVERE, "Last name provided is invalid: {0}", lastName);
            throw new InvalidDataException("Last name of this person is invalid: " + lastName);
        }
        String email = PersonUtils.generateEmailFromNames(firstName, lastName);
        return new Person(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Person {firstName: " + firstName +
                ", lastName: " + lastName +
                ", email: " + email + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}