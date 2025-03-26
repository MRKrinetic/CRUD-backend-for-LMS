package com.edulib.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edulib.library.dto.UserStatsDTO;
import com.edulib.library.model.User;
import com.edulib.library.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<User> getUserByStudentId(@PathVariable String studentId) {
        return userService.getUserByStudentId(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }

    @GetMapping("/type/{userType}")
    public ResponseEntity<List<User>> getUsersByType(@PathVariable User.UserType userType) {
        return ResponseEntity.ok(userService.getUsersByType(userType));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.getUserById(id)
                .map(existingUser -> {
                    user.setId(id);
                    return ResponseEntity.ok(userService.saveUser(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    userService.deleteUser(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    userService.activateUser(id);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    userService.deactivateUser(id);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/borrowed-count")
    public ResponseEntity<Integer> getCurrentBorrowedBooksCount(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(userService.getCurrentBorrowedBooksCount(id)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/can-borrow")
    public ResponseEntity<Boolean> canBorrowMoreBooks(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(userService.canBorrowMoreBooks(id)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/fine-pending")
    public ResponseEntity<UserStatsDTO> getFinePending(@PathVariable Long id) {
        try {
            UserStatsDTO stats = userService.getUserStats(id);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            UserStatsDTO errorStats = new UserStatsDTO();
            errorStats.setMessage("Error retrieving fine pending: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorStats);
        }
    }

    @GetMapping("/{id}/issued-books")
    public ResponseEntity<UserStatsDTO> getIssuedBooksCount(@PathVariable Long id) {
        try {
            UserStatsDTO stats = userService.getUserStats(id);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            UserStatsDTO errorStats = new UserStatsDTO();
            errorStats.setMessage("Error retrieving issued books count: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorStats);
        }
    }

    @PutMapping("/{id}/pay-all-fines")
    public ResponseEntity<UserStatsDTO> payAllFines(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            
            Double amountPaid = user.getTotalFinePending() != null ? user.getTotalFinePending() : 0.0;
            user.setTotalFinePending(0.0);
            userService.saveUser(user);
            
            UserStatsDTO stats = userService.getUserStats(id);
            stats.setMessage("All fines paid successfully. Amount paid: " + amountPaid);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            UserStatsDTO errorStats = new UserStatsDTO();
            errorStats.setMessage("Error paying fines: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorStats);
        }
    }
} 