package com.example.ecommerceapp.repository;

import com.example.ecommerceapp.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // JpaRepository temel CRUD metotlarını sağlar.
}