package com.edulib.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDTO {
    private Long userId;
    private Double finePending;
    private Integer issuedBooks;
    private String message;
} 