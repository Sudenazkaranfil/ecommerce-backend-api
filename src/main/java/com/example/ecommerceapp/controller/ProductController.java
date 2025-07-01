package com.example.ecommerceapp.controller;

import com.example.ecommerceapp.dto.CreateProductRequest;
import com.example.ecommerceapp.dto.ProductDto;
import com.example.ecommerceapp.dto.UpdateProductRequest;
import com.example.ecommerceapp.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bu sınıfın bir REST Controller olduğunu belirtir.
@RequestMapping("/api/products") // Tüm metotların "/api/products" yolundan erişileceğini belirtir.
@RequiredArgsConstructor // Lombok: Tüm final alanlar için bir constructor oluşturur, bu da dependency injection için kullanılır.
@Tag(name = "Product", description = "Ürün yönetimi API'leri") // Swagger UI için etiket ve açıklama.
public class ProductController {

    private final ProductService productService; // ProductService sınıfını enjekte et.

    // Tüm ürünleri getir
    @Operation(summary = "Tüm ürünleri getir",
            description = "Sistemdeki tüm ürünlerin listesini döndürür.")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // 200 OK yanıtı ile ürün listesini döndür.
    }

    // Belirli bir ürünü ID'ye göre getir
    @Operation(summary = "Ürünü ID'ye göre getir",
            description = "Belirli bir ürün ID'sine sahip ürünün detaylarını döndürür.")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product); // 200 OK yanıtı ile ürün detaylarını döndür.
    }

    // Yeni ürün oluştur
    @Operation(summary = "Yeni ürün oluştur",
            description = "Yeni bir ürün kaydı oluşturur.")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductDto newProduct = productService.createProduct(request);
        // Yeni bir kaynak oluşturulduğunda 201 Created yanıtı döndürmek iyi bir pratiktir.
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // Mevcut ürünü güncelle
    @Operation(summary = "Ürünü güncelle",
            description = "Mevcut bir ürünün bilgilerini günceller.")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        ProductDto updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct); // 200 OK yanıtı ile güncellenmiş ürünü döndür.
    }

    // Ürünü sil
    @Operation(summary = "Ürünü sil",
            description = "Belirli bir ürün ID'sine sahip ürünü siler.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        // Kaynak başarıyla silindiğinde 204 No Content yanıtı döndürmek iyi bir pratiktir.
        return ResponseEntity.noContent().build();
    }
}