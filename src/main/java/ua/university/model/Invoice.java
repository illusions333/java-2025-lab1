package ua.university.model;

import ua.university.exception.InvalidDataException;
import ua.university.utils.InvoiceUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Invoice {
    private Reservation reservation;
    private LocalDate issueDate;
    private double totalAmount;
    private static final Logger logger = Logger.getLogger(Invoice.class.getName());
    public Invoice(Reservation reservation, LocalDate issueDate, double totalAmount) {
        setReservation(reservation);
        setIssueDate(issueDate);
        setTotalAmount(totalAmount);
        logger.log(Level.FINE, "Invoice was created successfully!");
    }

    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    public LocalDate getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        if (totalAmount < 0){
            logger.log(Level.SEVERE, "Invalid total amount for the invoice: {0} (should be 0)");
            throw new InvalidDataException("Total amount must be greater than 0");
        }
        logger.log(Level.FINE, "Total amount was set successfully!");
        this.totalAmount = totalAmount;
    }

    public static Invoice createInvoice(Reservation reservation, LocalDate issueDate, double totalAmount) {
        if (reservation == null || issueDate == null){
            logger.log(Level.SEVERE, "Neither of the reservation or issue date could be null!");
            throw new InvalidDataException("None of the objects Reservation or IssueDate can't be null!");
        }
        if (totalAmount < 0){
            logger.log(Level.SEVERE, "Invalid total amount for the invoice: {0} (should be >= 0)");
            throw new InvalidDataException("Total amount must be greater than 0");
        }
        return new Invoice(reservation, issueDate, totalAmount);
    }

    public double getDailySpending(){
        return totalAmount / reservation.getDuration();
    }

    @Override
    public String toString() {
        return "Invoice {reservation: " + reservation + ", issueDate: " + InvoiceUtils.dateFormat(issueDate) + ", totalAmount: " + totalAmount + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation, issueDate, totalAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Invoice invoice = (Invoice) obj;
        return Objects.equals(issueDate, invoice.issueDate) && totalAmount == invoice.totalAmount && Objects.equals(reservation, invoice.reservation);
    }
}