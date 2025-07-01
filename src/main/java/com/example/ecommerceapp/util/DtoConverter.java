package com.example.ecommerceapp.util;

import com.example.ecommerceapp.dto.CartDto;
import com.example.ecommerceapp.dto.CartItemDto;
import com.example.ecommerceapp.dto.CreateCustomerRequest;
import com.example.ecommerceapp.dto.CreateProductRequest;
import com.example.ecommerceapp.dto.CustomerDto;
import com.example.ecommerceapp.dto.OrderDto;
import com.example.ecommerceapp.dto.OrderItemDto;
import com.example.ecommerceapp.dto.ProductDto;
import com.example.ecommerceapp.dto.UpdateProductRequest;
import com.example.ecommerceapp.entity.Cart;
import com.example.ecommerceapp.entity.CartItem;
import com.example.ecommerceapp.entity.Customer;
import com.example.ecommerceapp.entity.Order;
import com.example.ecommerceapp.entity.OrderItem;
import com.example.ecommerceapp.entity.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component // Bu sınıfın Spring tarafından yönetilen bir bileşen olduğunu belirtir.
public class DtoConverter {

    // --- Entity to DTO Converters ---

    public ProductDto convertToProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setSku(product.getSku());
        dto.setCategory(product.getCategory());
        return dto;
    }

    public CustomerDto convertToCustomerDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setAddress(customer.getAddress());
        dto.setPhone(customer.getPhone());
        return dto;
    }

    public CartItemDto convertToCartItemDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setQuantity(cartItem.getQuantity());
        dto.setUnitPrice(cartItem.getUnitPrice());
        dto.setSubtotal(cartItem.getQuantity() * cartItem.getUnitPrice()); // Alt toplam hesaplama
        return dto;
    }

    public CartDto convertToCartDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setCustomerId(cart.getCustomer().getId());
        // CartItem'ları DTO'ya dönüştür ve listeye ekle
        dto.setCartItems(cart.getCartItems().stream()
                .map(this::convertToCartItemDto)
                .collect(Collectors.toList()));
        // Toplam fiyatı hesapla
        dto.setTotalPrice(cart.getCartItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum());
        return dto;
    }

    public OrderItemDto convertToOrderItemDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(orderItem.getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPurchasedPrice(orderItem.getPurchasedPrice());
        dto.setSubtotal(orderItem.getQuantity() * orderItem.getPurchasedPrice()); // Alt toplam hesaplama
        return dto;
    }

    public OrderDto convertToOrderDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setBillingAddress(order.getBillingAddress());
        // OrderItem'ları DTO'ya dönüştür ve listeye ekle
        dto.setOrderItems(order.getOrderItems().stream()
                .map(this::convertToOrderItemDto)
                .collect(Collectors.toList()));
        return dto;
    }


    // --- DTO to Entity Converters (Sadece ihtiyaç olanlar) ---

    public Product convertToProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setSku(request.getSku());
        product.setCategory(request.getCategory());
        return product;
    }

    public Customer convertToCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        return customer;
    }
}