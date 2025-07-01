package com.example.ecommerceapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // Burayı da eklediğimizden emin olun

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor // Lombok'un boş constructor'ı oluşturmasını sağlar
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}