package com.example.ecommerceapp.repository;

import com.example.ecommerceapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Ürün adına göre arama
    List<Product> findByNameContainingIgnoreCase(String name);

    // Kategoriye göre ürünleri bulma
    List<Product> findByCategory(String category);
}