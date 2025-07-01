package com.example.ecommerceapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // Bu sınıfın bir veritabanı tablosuna eşlendiğini belirtir.
@Table(name = "products") // Veritabanındaki tablo adını belirtir.
@Data // Lombok: Getter, Setter, toString, equals ve hashCode metotlarını otomatik oluşturur.
@NoArgsConstructor // Lombok: Parametresiz constructor'ı otomatik oluşturur.
@AllArgsConstructor // Lombok: Tüm alanları içeren constructor'ı otomatik oluşturur.
@EqualsAndHashCode(callSuper = true) // Lombok: equals ve hashCode metotlarını üst sınıftaki alanları da kullanarak oluşturur.
public class Product extends BaseEntity {

    @Column(nullable = false) // Bu alanın veritabanında null olamayacağını belirtir.
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stockQuantity;

    private String sku; // Stock Keeping Unit - Ürün stok birimi kodu

    private String category;

    // Lombok sayesinde Getter ve Setter'lar, Constructor'lar otomatik oluşturulur.
}