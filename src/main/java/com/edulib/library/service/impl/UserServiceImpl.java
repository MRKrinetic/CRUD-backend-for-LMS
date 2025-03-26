package com.edulib.library.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edulib.library.dto.UserStatsDTO;
import com.edulib.library.model.User;
import com.edulib.library.repository.BorrowingRepository;
import com.edulib.library.repository.UserRepository;
import com.edulib.library.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BorrowingRepository borrowingRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        // If it's a new user, set registration date
        if (user.getId() == null) {
            user.setRegistrationDate(LocalDate.now());
            
            // Set default max books allowed based on user type
            if (user.getMaxBooksAllowed() == null) {
                switch (user.getUserType()) {
                    case STUDENT -> user.setMaxBooksAllowed(3);
                    case FACULTY -> user.setMaxBooksAllowed(5);
                    case LIBRARIAN, ADMIN -> user.setMaxBooksAllowed(10);
                }
            }
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword);
    }

    @Override
    public List<User> getUsersByType(User.UserType userType) {
        return userRepository.findByUserType(userType);
    }

    @Override
    @Transactional
    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public boolean canBorrowMoreBooks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        
        if (!user.isActive()) {
            return false;
        }
        
        Long currentBorrowedCount = borrowingRepository.countCurrentBorrowingsByUserId(userId);
        return currentBorrowedCount < user.getMaxBooksAllowed();
    }

    @Override
    public int getCurrentBorrowedBooksCount(Long userId) {
        Long count = borrowingRepository.countCurrentBorrowingsByUserId(userId);
        return count.intValue();
    }

    @Override
    public UserStatsDTO getUserStats(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        
        UserStatsDTO stats = new UserStatsDTO();
        stats.setUserId(user.getId());
        stats.setFinePending(user.getTotalFinePending() != null ? user.getTotalFinePending() : 0.0);
        stats.setIssuedBooks(user.getCurrentIssuedBooks() != null ? user.getCurrentIssuedBooks() : 0);
        
        return stats;
    }
} 