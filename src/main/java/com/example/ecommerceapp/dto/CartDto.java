package com.example.ecommerceapp.dto;

import lombok.Data;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private Long customerId;
    private List<CartItemDto> cartItems;
    private Double totalPrice; // Sepetin toplam fiyatı
}