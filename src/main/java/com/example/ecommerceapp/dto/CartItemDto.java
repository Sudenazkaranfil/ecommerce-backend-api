package com.example.ecommerceapp.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal; // Bu öğenin toplam fiyatı (adet * birim fiyat)
}