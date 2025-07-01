package com.example.ecommerceapp.controller;

import com.example.ecommerceapp.dto.CreateOrderRequest;
import com.example.ecommerceapp.dto.OrderDto;
import com.example.ecommerceapp.entity.OrderStatus;
import com.example.ecommerceapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Sipariş yönetimi API'leri")
public class OrderController {

    private final OrderService orderService;

    // Tüm siparişleri getir
    @Operation(summary = "Tüm siparişleri getir",
            description = "Sistemdeki tüm siparişlerin listesini döndürür.")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Belirli bir siparişi ID'ye göre getir
    @Operation(summary = "Siparişi ID'ye göre getir",
            description = "Belirli bir sipariş ID'sine sahip siparişin detaylarını döndürür.")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Yeni sipariş oluştur (sepetteki ürünlerden)
    @Operation(summary = "Yeni sipariş oluştur",
            description = "Müşterinin sepetindeki ürünlerden yeni bir sipariş oluşturur.")
    @PostMapping
    public ResponseEntity<OrderDto> createOrderFromCart(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto newOrder = orderService.createOrderFromCart(request);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    // Sipariş durumunu güncelle
    @Operation(summary = "Sipariş durumunu güncelle",
            description = "Belirli bir siparişin durumunu günceller (PENDING, SHIPPED vb.).")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        OrderDto updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }

    // Siparişi sil
    @Operation(summary = "Siparişi sil",
            description = "Belirli bir sipariş ID'sine sahip siparişi siler.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}