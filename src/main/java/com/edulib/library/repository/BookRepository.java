package com.edulib.library.repository;

import com.edulib.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);
    
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    List<Book> findByCategoryIgnoreCase(String category);
    
    @Query("SELECT b FROM Book b WHERE b.availableQuantity > 0")
    List<Book> findAllAvailableBooks();
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(b.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBooks(String keyword);
} 