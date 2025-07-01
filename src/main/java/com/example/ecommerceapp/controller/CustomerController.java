package com.example.ecommerceapp.controller;

import com.example.ecommerceapp.dto.CreateCustomerRequest;
import com.example.ecommerceapp.dto.CustomerDto;
import com.example.ecommerceapp.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Müşteri yönetimi API'leri")
public class CustomerController {

    private final CustomerService customerService;

    // Tüm müşterileri getir
    @Operation(summary = "Tüm müşterileri getir",
            description = "Sistemdeki tüm müşterilerin listesini döndürür.")
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Belirli bir müşteriyi ID'ye göre getir
    @Operation(summary = "Müşteriyi ID'ye göre getir",
            description = "Belirli bir müşteri ID'sine sahip müşterinin detaylarını döndürür.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        CustomerDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // Yeni müşteri oluştur
    @Operation(summary = "Yeni müşteri oluştur",
            description = "Yeni bir müşteri kaydı oluşturur.")
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerDto newCustomer = customerService.createCustomer(request);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    // Mevcut müşteriyi güncelle
    @Operation(summary = "Müşteriyi güncelle",
            description = "Mevcut bir müşterinin bilgilerini günceller.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody CreateCustomerRequest request) {
        CustomerDto updatedCustomer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Müşteriyi sil
    @Operation(summary = "Müşteriyi sil",
            description = "Belirli bir müşteri ID'sine sahip müşteriyi siler.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}