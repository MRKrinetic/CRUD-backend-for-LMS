package com.edulib.library.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edulib.library.model.Book;
import com.edulib.library.model.Borrowing;
import com.edulib.library.model.User;
import com.edulib.library.repository.BookRepository;
import com.edulib.library.repository.BorrowingRepository;
import com.edulib.library.repository.UserRepository;
import com.edulib.library.service.BookService;
import com.edulib.library.service.BorrowingService;
import com.edulib.library.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final UserService userService;

    @Override
    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    @Override
    public Optional<Borrowing> getBorrowingById(Long id) {
        return borrowingRepository.findById(id);
    }

    @Override
    public List<Borrowing> getBorrowingsByUser(User user) {
        return borrowingRepository.findByUser(user);
    }

    @Override
    public List<Borrowing> getCurrentBorrowingsByUserId(Long userId) {
        return borrowingRepository.findCurrentBorrowingsByUserId(userId);
    }

    @Override
    public List<Borrowing> getOverdueBooks() {
        return borrowingRepository.findAllOverdueBooks(LocalDate.now());
    }

    @Override
    @Transactional
    public Borrowing borrowBook(Long userId, Long bookId, int borrowDays) {
        // Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        
        // Check if book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        
        // Check if user can borrow more books
        if (!userService.canBorrowMoreBooks(userId)) {
            throw new IllegalStateException("User has reached maximum number of books allowed to borrow");
        }
        
        // Check if book is available
        if (!bookService.isBookAvailable(bookId)) {
            throw new IllegalStateException("Book is not available for borrowing");
        }
        
        // Create new borrowing record
        Borrowing borrowing = new Borrowing();
        borrowing.setUser(user);
        borrowing.setBook(book);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(borrowDays));
        borrowing.setStatus(Borrowing.BorrowingStatus.BORROWED);
        
        // Update book available quantity
        bookService.updateBookQuantity(bookId, -1);
        
        // Update user's issued books count
        user.incrementIssuedBooks();
        userRepository.save(user);
        
        return borrowingRepository.save(borrowing);
    }

    @Override
    @Transactional
    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new EntityNotFoundException("Borrowing record not found with id: " + borrowingId));
        
        // Check if book is already returned
        if (borrowing.getReturnDate() != null) {
            throw new IllegalStateException("Book is already returned");
        }
        
        // Set return date
        borrowing.setReturnDate(LocalDate.now());
        
        // Get user
        User user = borrowing.getUser();
        
        // Calculate fine if overdue
        if (borrowing.isOverdue()) {
            double fine = borrowing.calculateFine();
            borrowing.setFineAmount(fine);
            borrowing.setStatus(Borrowing.BorrowingStatus.OVERDUE);
            
            // Update user's fine pending
            user.updateFinePending(fine);
        } else {
            borrowing.setStatus(Borrowing.BorrowingStatus.RETURNED);
        }
        
        // Update book available quantity
        bookService.updateBookQuantity(borrowing.getBook().getId(), 1);
        
        // Update user's issued books count
        user.decrementIssuedBooks();
        userRepository.save(user);
        
        return borrowingRepository.save(borrowing);
    }

    @Override
    @Transactional
    public void deleteBorrowing(Long id) {
        borrowingRepository.deleteById(id);
    }

    @Override
    public double calculateFine(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new EntityNotFoundException("Borrowing record not found with id: " + borrowingId));
        
        return borrowing.calculateFine();
    }

    @Override
    @Transactional
    public void payFine(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new EntityNotFoundException("Borrowing record not found with id: " + borrowingId));
        
        if (borrowing.getFineAmount() == null || borrowing.getFineAmount() <= 0) {
            throw new IllegalStateException("No fine to pay for this borrowing");
        }
        
        // Get user
        User user = borrowing.getUser();
        
        // Update user's fine pending
        user.updateFinePending(-borrowing.getFineAmount());
        userRepository.save(user);
        
        borrowing.setFinePaid(true);
        borrowingRepository.save(borrowing);
    }

    @Override
    public List<Borrowing> getBorrowingsByStatus(Borrowing.BorrowingStatus status) {
        return borrowingRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public void updateBorrowingStatus(Long borrowingId, Borrowing.BorrowingStatus status) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new EntityNotFoundException("Borrowing record not found with id: " + borrowingId));
        
        borrowing.setStatus(status);
        borrowingRepository.save(borrowing);
    }
} 