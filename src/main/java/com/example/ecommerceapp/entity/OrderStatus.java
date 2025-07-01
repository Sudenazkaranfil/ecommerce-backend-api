package com.example.ecommerceapp.entity;

// Sipariş durumlarını tanımlayan bir Enum.
public enum OrderStatus {
    PENDING,      // Beklemede
    PROCESSING,   // İşleniyor
    SHIPPED,      // Kargolandı
    DELIVERED,    // Teslim Edildi
    CANCELED,     // İptal Edildi
    RETURNED      // İade Edildi
}