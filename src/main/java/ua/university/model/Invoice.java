package ua.university.model;

import ua.university.util.InvoiceUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Invoice {
    private Reservation reservation;
    private LocalDate issueDate;
    private double totalAmount;

    public Invoice(){}
    public Invoice(Reservation reservation, LocalDate issueDate, double totalAmount) {
        setReservation(reservation);
        setIssueDate(issueDate);
        setTotalAmount(totalAmount);
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
        if (InvoiceUtils.isValidAmount(totalAmount)) this.totalAmount = totalAmount;
    }

    public static Invoice createInvoice(Reservation reservation, LocalDate issueDate, double totalAmount) {
        if (InvoiceUtils.isValidAmount(totalAmount)) return new Invoice(reservation, issueDate, totalAmount);
        return null;
    }

    public double getDailySpending(){
        return totalAmount / reservation.getDuration();
    }

    @Override
    public String toString() {
        return "Invoice {reservation: " + reservation + ", issueDate: " + issueDate + ", totalAmount: " + totalAmount + "}";
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
