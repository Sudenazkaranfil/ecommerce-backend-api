package com.example.ecommerceapp.exception;

// Genel bir kaynak bulunamadı hatası için (Customer, Product, Order vb.)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}