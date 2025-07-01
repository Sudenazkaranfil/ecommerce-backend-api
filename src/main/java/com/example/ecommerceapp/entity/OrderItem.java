package com.example.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY) // Bir OrderItem'ın sadece bir siparişi olabilir.
    @JoinColumn(name = "order_id", nullable = false) // Sipariş ID'si ile bağlantı kurar.
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY) // Bir OrderItem'ın sadece bir ürünü olabilir.
    @JoinColumn(name = "product_id", nullable = false) // Ürün ID'si ile bağlantı kurar.
    private Product product;

    @Column(nullable = false)
    private Integer quantity; // Üründen kaç adet olduğu.

    @Column(nullable = false)
    private Double purchasedPrice; // Ürünün sipariş verildiği andaki birim fiyatı (önemli!).

    // Lombok sayesinde Getter ve Setter'lar, Constructor'lar otomatik oluşur.
}