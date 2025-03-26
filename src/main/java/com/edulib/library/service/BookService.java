package com.edulib.library.service;

import com.edulib.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    
    List<Book> getAllBooks();
    
    Optional<Book> getBookById(Long id);
    
    Optional<Book> getBookByIsbn(String isbn);
    
    Book saveBook(Book book);
    
    void deleteBook(Long id);
    
    List<Book> searchBooks(String keyword);
    
    List<Book> getBooksByCategory(String category);
    
    List<Book> getBooksByAuthor(String author);
    
    List<Book> getAvailableBooks();
    
    boolean isBookAvailable(Long bookId);
    
    void updateBookQuantity(Long bookId, int quantityChange);
} 