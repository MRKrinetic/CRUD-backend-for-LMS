package com.edulib.library.service;

import java.util.List;
import java.util.Optional;

import com.edulib.library.dto.UserStatsDTO;
import com.edulib.library.model.User;

public interface UserService {
    
    List<User> getAllUsers();
    
    Optional<User> getUserById(Long id);
    
    Optional<User> getUserByEmail(String email);
    
    Optional<User> getUserByStudentId(String studentId);
    
    User saveUser(User user);
    
    void deleteUser(Long id);
    
    List<User> searchUsers(String keyword);
    
    List<User> getUsersByType(User.UserType userType);
    
    void activateUser(Long id);
    
    void deactivateUser(Long id);
    
    boolean canBorrowMoreBooks(Long userId);
    
    int getCurrentBorrowedBooksCount(Long userId);
    
    UserStatsDTO getUserStats(Long userId);
} 