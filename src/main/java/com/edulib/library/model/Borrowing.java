package com.edulib.library.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "borrowings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties("borrowings")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("borrowings")
    private User user;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

    @Column(name = "fine_amount")
    private Double fineAmount;

    @Column(name = "fine_paid")
    private Boolean finePaid = false;

    // Enum for borrowing status
    public enum BorrowingStatus {
        BORROWED,
        RETURNED,
        OVERDUE,
        LOST
    }
    
    // Method to check if the book is overdue
    public boolean isOverdue() {
        return returnDate == null && LocalDate.now().isAfter(dueDate);
    }

    // Method to calculate fine amount
    public double calculateFine() {
        if (returnDate == null && LocalDate.now().isAfter(dueDate)) {
            // Calculate days overdue
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            // Assume fine rate of $1 per day
            return daysOverdue * 1.0;
        } else if (returnDate != null && returnDate.isAfter(dueDate)) {
            // Calculate days overdue
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            // Assume fine rate of $1 per day
            return daysOverdue * 1.0;
        }
        return 0.0;
    }
} 