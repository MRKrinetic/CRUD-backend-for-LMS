package com.edulib.library.service;

import java.util.List;
import java.util.Optional;

import com.edulib.library.model.Borrowing;
import com.edulib.library.model.User;

public interface BorrowingService {
    
    List<Borrowing> getAllBorrowings();
    
    Optional<Borrowing> getBorrowingById(Long id);
    
    List<Borrowing> getBorrowingsByUser(User user);
    
    List<Borrowing> getCurrentBorrowingsByUserId(Long userId);
    
    List<Borrowing> getOverdueBooks();
    
    Borrowing borrowBook(Long userId, Long bookId, int borrowDays);
    
    Borrowing returnBook(Long borrowingId);
    
    void deleteBorrowing(Long id);
    
    double calculateFine(Long borrowingId);
    
    void payFine(Long borrowingId);
    
    List<Borrowing> getBorrowingsByStatus(Borrowing.BorrowingStatus status);
    
    void updateBorrowingStatus(Long borrowingId, Borrowing.BorrowingStatus status);
} 