package com.edulib.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number should be valid")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "student_id")
    private String studentId;
    
    @Column(name = "max_books_allowed")
    private Integer maxBooksAllowed;
    
    @Column(name = "active")
    private boolean active = true;
    
    @Column(name = "total_fine_pending")
    private Double totalFinePending = 0.0;
    
    @Column(name = "current_issued_books")
    private Integer currentIssuedBooks = 0;
    
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Borrowing> borrowings = new ArrayList<>();
    
    // Enum for user types
    public enum UserType {
        STUDENT,
        FACULTY,
        ADMIN,
        LIBRARIAN
    }
    
    // Method to update fine pending
    public void updateFinePending(Double amount) {
        if (this.totalFinePending == null) {
            this.totalFinePending = 0.0;
        }
        this.totalFinePending += amount;
    }
    
    // Method to increment issued books count
    public void incrementIssuedBooks() {
        if (this.currentIssuedBooks == null) {
            this.currentIssuedBooks = 0;
        }
        this.currentIssuedBooks++;
    }
    
    // Method to decrement issued books count
    public void decrementIssuedBooks() {
        if (this.currentIssuedBooks == null || this.currentIssuedBooks <= 0) {
            this.currentIssuedBooks = 0;
        } else {
            this.currentIssuedBooks--;
        }
    }
} 