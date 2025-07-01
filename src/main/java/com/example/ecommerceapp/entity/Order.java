package com.example.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY) // Bir siparişin sadece bir müşterisi olabilir.
    @JoinColumn(name = "customer_id", nullable = false) // Müşteri ID'si ile bağlantı kurar.
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // Bir siparişin birden fazla OrderItem'ı olabilir. Sipariş silindiğinde OrderItem'lar da silinir.
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false)
    private Double totalAmount; // Siparişin toplam tutarı.

    @Enumerated(EnumType.STRING) // Enum değerlerini veritabanına String olarak kaydeder (ENUM olarak değil).
    @Column(nullable = false)
    private OrderStatus status; // Siparişin durumu (PENDING, COMPLETED, CANCELED vb.)

    @Column(nullable = false)
    private LocalDateTime orderDate; // Siparişin verildiği tarih ve saat.

    private String shippingAddress;
    private String billingAddress;

    // Lombok sayesinde Getter ve Setter'lar, Constructor'lar otomatik oluşur.
}