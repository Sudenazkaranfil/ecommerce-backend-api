package com.example.ecommerceapp.repository;

import com.example.ecommerceapp.entity.Cart;
import com.example.ecommerceapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Belirli bir müşteri için sepeti bulur
    Optional<Cart> findByCustomer(Customer customer);
}