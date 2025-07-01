package com.example.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY) // Bir sepetin sadece bir müşterisi olabilir. FetchType.LAZY, müşteri bilgisi sadece ihtiyaç duyulduğunda yüklenir.
    @JoinColumn(name = "customer_id", unique = true, nullable = false) // Müşteri ID'si ile bağlantı kurar. unique = true, bir müşterinin sadece bir sepeti olmasını sağlar.
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) // Bir sepette birden fazla CartItem olabilir. CascadeType.ALL, sepet silindiğinde CartItem'ları da siler.
    private List<CartItem> cartItems = new ArrayList<>(); // Sepet öğeleri listesi.

    // Lombok sayesinde Getter ve Setter'lar, Constructor'lar otomatik oluşur.
}