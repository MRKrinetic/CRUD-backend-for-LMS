package com.edulib.library.service.impl;

import com.edulib.library.model.Book;
import com.edulib.library.repository.BookRepository;
import com.edulib.library.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    @Transactional
    public Book saveBook(Book book) {
        // If it's a new book, set available quantity equal to total quantity
        if (book.getId() == null) {
            book.setAvailableQuantity(book.getQuantity());
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword);
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Override
    public List<Book> getAvailableBooks() {
        return bookRepository.findAllAvailableBooks();
    }

    @Override
    public boolean isBookAvailable(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        return book.isAvailable();
    }

    @Override
    @Transactional
    public void updateBookQuantity(Long bookId, int quantityChange) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        
        int newAvailableQuantity = book.getAvailableQuantity() + quantityChange;
        
        if (newAvailableQuantity < 0) {
            throw new IllegalStateException("Cannot reduce available quantity below zero");
        }
        
        if (newAvailableQuantity > book.getQuantity()) {
            throw new IllegalStateException("Available quantity cannot exceed total quantity");
        }
        
        book.setAvailableQuantity(newAvailableQuantity);
        bookRepository.save(book);
    }
} 