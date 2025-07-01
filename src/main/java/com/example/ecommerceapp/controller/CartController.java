package com.example.ecommerceapp.controller;

import com.example.ecommerceapp.dto.AddCartItemRequest;
import com.example.ecommerceapp.dto.CartDto;
import com.example.ecommerceapp.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bu sınıfın bir REST Controller olduğunu belirtir.
@RequestMapping("/api/carts") // Tüm metotların "/api/carts" yolundan erişileceğini belirtir.
@RequiredArgsConstructor // Lombok: Tüm final alanlar için bir constructor oluşturur.
@Tag(name = "Cart", description = "Sepet yönetimi API'leri") // Swagger UI için etiket ve açıklama.
public class CartController {

    private final CartService cartService;

    // Belirli bir müşteri için sepeti getirir veya oluşturur
    @Operation(summary = "Müşteri sepetini getir veya oluştur",
            description = "Mevcut sepeti getirir, yoksa yeni bir sepet oluşturur.")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CartDto> getOrCreateCart(@PathVariable Long customerId) {
        CartDto cart = cartService.getOrCreateCart(customerId);
        return ResponseEntity.ok(cart);
    }

    // Sepete ürün ekler
    @Operation(summary = "Sepete ürün ekle",
            description = "Belirtilen sepete ürün ekler veya mevcut ürün miktarını günceller.")
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long cartId,
                                                    @Valid @RequestBody AddCartItemRequest request) {
        CartDto updatedCart = cartService.addProductToCart(cartId, request);
        return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
    }

    // Sepetteki ürün miktarını günceller
    @Operation(summary = "Sepetteki ürün miktarını güncelle",
            description = "Sepetteki belirli bir ürünün miktarını günceller.")
    @PutMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<CartDto> updateCartItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long cartItemId,
                                                          @RequestParam Integer quantity) {
        CartDto updatedCart = cartService.updateCartItemQuantity(cartId, cartItemId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    // Sepetten ürün çıkarır
    @Operation(summary = "Sepetten ürün çıkar",
            description = "Belirtilen sepetten belirli bir ürünü çıkarır.")
    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartId,
                                                      @PathVariable Long cartItemId) {
        cartService.removeProductFromCart(cartId, cartItemId);
        return ResponseEntity.noContent().build();
    }

    // Sepeti temizler
    @Operation(summary = "Sepeti temizle",
            description = "Belirtilen sepetteki tüm ürünleri kaldırır.")
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<CartDto> clearCart(@PathVariable Long cartId) {
        CartDto clearedCart = cartService.clearCart(cartId);
        return ResponseEntity.ok(clearedCart);
    }
}