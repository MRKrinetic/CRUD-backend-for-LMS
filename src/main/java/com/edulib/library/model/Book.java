package com.edulib.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Column(unique = true)
    private String isbn;

    @NotNull(message = "Publication date is required")
    @PastOrPresent(message = "Publication date cannot be in the future")
    private LocalDate publicationDate;

    @NotBlank(message = "Publisher is required")
    private String publisher;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be zero or positive")
    private Integer quantity;

    @NotBlank(message = "Category is required")
    private String category;
    
    private String description;
    
    @Column(name = "available_quantity")
    private Integer availableQuantity;
    
    @Column(name = "shelf_location")
    private String shelfLocation;
    
    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties("book")
    private List<Borrowing> borrowings = new ArrayList<>();
    
    // Method to check if book is available for borrowing
    public boolean isAvailable() {
        return availableQuantity > 0;
    }
} 