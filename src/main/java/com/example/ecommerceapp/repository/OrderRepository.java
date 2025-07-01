package com.example.ecommerceapp.repository;

import com.example.ecommerceapp.entity.Order;
import com.example.ecommerceapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Belirli bir müşterinin tüm siparişlerini bulur
    List<Order> findByCustomer(Customer customer);
}