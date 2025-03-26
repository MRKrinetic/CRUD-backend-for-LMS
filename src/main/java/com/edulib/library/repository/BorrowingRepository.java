package com.edulib.library.repository;

import com.edulib.library.model.Borrowing;
import com.edulib.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    
    List<Borrowing> findByUser(User user);
    
    List<Borrowing> findByUserAndReturnDateIsNull(User user);
    
    @Query("SELECT b FROM Borrowing b WHERE b.returnDate IS NULL AND b.dueDate < :currentDate")
    List<Borrowing> findAllOverdueBooks(LocalDate currentDate);
    
    @Query("SELECT b FROM Borrowing b WHERE b.user.id = :userId AND b.returnDate IS NULL")
    List<Borrowing> findCurrentBorrowingsByUserId(Long userId);
    
    @Query("SELECT b FROM Borrowing b WHERE b.book.id = :bookId AND b.returnDate IS NULL")
    List<Borrowing> findCurrentBorrowingsByBookId(Long bookId);
    
    @Query("SELECT COUNT(b) FROM Borrowing b WHERE b.user.id = :userId AND b.returnDate IS NULL")
    Long countCurrentBorrowingsByUserId(Long userId);
    
    List<Borrowing> findByStatus(Borrowing.BorrowingStatus status);
} 