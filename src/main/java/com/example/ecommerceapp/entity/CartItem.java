package com.example.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CartItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY) // Bir CartItem'ın sadece bir sepeti olabilir.
    @JoinColumn(name = "cart_id", nullable = false) // Sepet ID'si ile bağlantı kurar.
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY) // Bir CartItem'ın sadece bir ürünü olabilir.
    @JoinColumn(name = "product_id", nullable = false) // Ürün ID'si ile bağlantı kurar.
    private Product product;

    @Column(nullable = false)
    private Integer quantity; // Üründen kaç adet olduğu.

    @Column(nullable = false)
    private Double unitPrice; // Ürünün sepete eklendiği andaki birim fiyatı (fiyat değişimi durumlarına karşı).

    // Lombok sayesinde Getter ve Setter'lar, Constructor'lar otomatik oluşur.
}