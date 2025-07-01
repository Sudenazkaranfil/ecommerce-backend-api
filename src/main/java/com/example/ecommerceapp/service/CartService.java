package com.example.ecommerceapp.service;

import com.example.ecommerceapp.dto.AddCartItemRequest;
import com.example.ecommerceapp.dto.CartDto;
import com.example.ecommerceapp.entity.Cart;
import com.example.ecommerceapp.entity.CartItem;
import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.entity.Product;
import com.example.ecommerceapp.exception.CartItemNotFoundException;
import com.example.ecommerceapp.exception.CartNotFoundException;
import com.example.ecommerceapp.exception.CustomerNotFoundException;
import com.example.ecommerceapp.exception.InsufficientStockException;
import com.example.ecommerceapp.exception.ProductNotFoundException;
import com.example.ecommerceapp.repository.CartItemRepository;
import com.example.ecommerceapp.repository.CartRepository;
import com.example.ecommerceapp.repository.CustomerRepository;
import com.example.ecommerceapp.repository.ProductRepository;
import com.example.ecommerceapp.util.DtoConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok: Tüm final alanlar için bir constructor oluşturur, bu da dependency injection için kullanılır.
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final DtoConverter dtoConverter; // DTO ve Entity dönüşümleri için.

    // Yeni bir sepet oluşturur veya mevcut sepeti getirir.
    @Transactional
    public CartDto getOrCreateCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        // Müşterinin sepeti var mı diye kontrol et
        Optional<Cart> existingCart = cartRepository.findByCustomer(customer);

        Cart cart;
        if (existingCart.isPresent()) {
            cart = existingCart.get();
        } else {
            // Yoksa yeni bir sepet oluştur
            cart = new Cart();
            cart.setCustomer(customer);
            cart = cartRepository.save(cart);
        }
        return dtoConverter.convertToCartDto(cart);
    }

    // Sepete ürün ekler veya mevcut ürünün miktarını günceller.
    @Transactional
    public CartDto addProductToCart(Long cartId, AddCartItemRequest request) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + request.getProductId()));

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Not enough stock for product: " + product.getName() + ". Available: " + product.getStockQuantity());
        }

        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        CartItem cartItem;

        if (existingCartItem.isPresent()) {
            // Ürün zaten sepette varsa miktarı güncelle
            cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            if (product.getStockQuantity() < newQuantity) { // Toplam stok kontrolü
                throw new InsufficientStockException("Adding " + request.getQuantity() + " more will exceed stock for product: " + product.getName() + ". Available: " + product.getStockQuantity());
            }
            cartItem.setQuantity(newQuantity);
            cartItem.setUnitPrice(product.getPrice()); // Fiyat değişmiş olabileceği için güncel fiyatı al
        } else {
            // Ürün sepette yoksa yeni bir CartItem oluştur
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUnitPrice(product.getPrice()); // Ürünün o anki fiyatını kaydet
            cart.getCartItems().add(cartItem); // Sepetin öğeler listesine ekle
        }

        cartItemRepository.save(cartItem);
        return dtoConverter.convertToCartDto(cart);
    }

    // Sepetteki ürünün miktarını günceller.
    @Transactional
    public CartDto updateCartItemQuantity(Long cartId, Long cartItemId, Integer newQuantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with id: " + cartItemId));

        if (!cartItem.getCart().getId().equals(cartId)) {
            throw new IllegalArgumentException("Cart item does not belong to the specified cart.");
        }

        Product product = cartItem.getProduct();
        if (product.getStockQuantity() < newQuantity) {
            throw new InsufficientStockException("Not enough stock for product: " + product.getName() + ". Available: " + product.getStockQuantity());
        }

        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
        return dtoConverter.convertToCartDto(cart);
    }


    // Sepetten ürün çıkarır.
    @Transactional
    public void removeProductFromCart(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with id: " + cartItemId));

        if (!cartItem.getCart().getId().equals(cartId)) {
            throw new IllegalArgumentException("Cart item does not belong to the specified cart.");
        }

        cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));
        cartItemRepository.delete(cartItem);
    }

    // Sepeti temizler.
    @Transactional
    public CartDto clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with id: " + cartId));

        cartItemRepository.deleteAll(cart.getCartItems()); // Tüm sepet öğelerini sil
        cart.getCartItems().clear(); // Listeyi de temizle

        return dtoConverter.convertToCartDto(cart);
    }
}