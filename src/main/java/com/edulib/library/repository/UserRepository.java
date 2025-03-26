package com.edulib.library.repository;

import com.edulib.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByStudentId(String studentId);
    
    List<User> findByUserType(User.UserType userType);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR u.studentId LIKE CONCAT('%', :keyword, '%')")
    List<User> searchUsers(String keyword);
    
    List<User> findByActive(boolean active);
} 