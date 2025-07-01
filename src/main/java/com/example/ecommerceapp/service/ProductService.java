package com.example.ecommerceapp.service;

import com.example.ecommerceapp.dto.CreateProductRequest;
import com.example.ecommerceapp.dto.ProductDto;
import com.example.ecommerceapp.dto.UpdateProductRequest;
import com.example.ecommerceapp.entity.Product;
import com.example.ecommerceapp.exception.ResourceNotFoundException;
import com.example.ecommerceapp.repository.ProductRepository;
import com.example.ecommerceapp.util.DtoConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok: Tüm final alanlar için bir constructor oluşturur, bu da dependency injection için kullanılır.
public class ProductService {

    private final ProductRepository productRepository;
    private final DtoConverter dtoConverter;

    // Tüm ürünleri getirir
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(dtoConverter::convertToProductDto) // Her Product entity'sini ProductDto'ya dönüştür.
                .collect(Collectors.toList());
    }

    // Belirli bir ürünü ID'ye göre getirir
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id)); // Ürün bulunamazsa hata fırlat.
        return dtoConverter.convertToProductDto(product);
    }

    // Yeni ürün oluşturur
    @Transactional // Bu metot bir veritabanı işlemi (transaction) içinde çalışır.
    public ProductDto createProduct(CreateProductRequest request) {
        Product product = dtoConverter.convertToProduct(request); // DTO'dan Entity'ye dönüştür.
        product = productRepository.save(product); // Ürünü veritabanına kaydet.
        return dtoConverter.convertToProductDto(product);
    }

    // Mevcut ürünü günceller
    @Transactional
    public ProductDto updateProduct(Long id, UpdateProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        // Güncellenecek alanları set et
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStockQuantity(request.getStockQuantity());
        existingProduct.setSku(request.getSku());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setUpdatedAt(LocalDateTime.now()); // Güncellenme zamanını set et

        existingProduct = productRepository.save(existingProduct); // Güncellenmiş ürünü kaydet
        return dtoConverter.convertToProductDto(existingProduct);
    }

    // Ürünü siler
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) { // Ürünün var olup olmadığını kontrol et.
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productRepository.deleteById(id); // Ürünü sil.
    }
}