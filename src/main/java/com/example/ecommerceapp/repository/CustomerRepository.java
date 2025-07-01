package com.example.ecommerceapp.repository;

import com.example.ecommerceapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // E-posta adresine göre müşteri bulur (benzersiz olduğu için Optional kullanırız)
    Optional<Customer> findByEmail(String email);
}