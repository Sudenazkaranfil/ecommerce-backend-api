package com.example.ecommerceapp.repository;

import com.example.ecommerceapp.entity.Cart;
import com.example.ecommerceapp.entity.CartItem;
import com.example.ecommerceapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Belirli bir sepet ve ürün için CartItem'ı bulur
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}